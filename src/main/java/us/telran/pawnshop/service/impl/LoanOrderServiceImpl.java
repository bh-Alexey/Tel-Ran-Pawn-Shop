package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.LoanOrderRequest;
import us.telran.pawnshop.dto.LoanProlongationRequest;
import us.telran.pawnshop.entity.CashOperation;
import us.telran.pawnshop.entity.Loan;
import us.telran.pawnshop.entity.LoanOrder;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.entity.enums.OrderType;
import us.telran.pawnshop.repository.CashOperationRepository;
import us.telran.pawnshop.repository.LoanOrderRepository;
import us.telran.pawnshop.repository.LoanRepository;
import us.telran.pawnshop.repository.PawnBranchRepository;
import us.telran.pawnshop.service.LoanOrderService;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static us.telran.pawnshop.entity.enums.OrderType.*;

@Service
@RequiredArgsConstructor
public class LoanOrderServiceImpl implements LoanOrderService {

    private final LoanRepository loanRepository;
    private final LoanOrderRepository loanOrderRepository;
    private final CashOperationRepository cashOperationRepository;
    private final PawnBranchRepository pawnBranchRepository;

    @Value("${pawnshop.address}")
    private String currentPawnShop;

    void getRansomAmount() {
    }

    @Override
    @Transactional
    public void createLoanReceiptOrder(LoanOrderRequest loanOrderRequest) {
        LoanOrder loanOrder = createLoanOrder(loanOrderRequest, INCOME);
        createCashOperation(loanOrder, loanOrderRequest);
    }

    @Override
    @Transactional
    public void createLoanExpenseOrder(LoanOrderRequest loanOrderRequest) {
        LoanOrder loanOrder = createLoanOrder(loanOrderRequest, EXPENSE);
        createCashOperation(loanOrder, loanOrderRequest);
    }

    private LoanOrder createLoanOrder(LoanOrderRequest loanOrderRequest, OrderType orderType) {
        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setOrderType(orderType);

        Optional<Loan> loanOptional = loanRepository.findById(loanOrderRequest.getLoanId());
        if (loanOptional.isPresent()) {
            Loan loan = loanOptional.get();
            loanOrder.setOrderAmount(loan.getLoanAmount());
            loanOrder.setLoan(loan);
        }

        return loanOrderRepository.save(loanOrder);
    }
    private CashOperation buildCashOperation(LoanOrder loanOrder) {
        CashOperation cashOperation = new CashOperation();
        cashOperation.setOrderType(loanOrder.getOrderType());
        cashOperation.setOperationAmount(loanOrder.getOrderAmount());
        return cashOperation;
    }
    private void createCashOperation(LoanOrder loanOrder, LoanOrderRequest loanOrderRequest) {

        CashOperation cashOperation = buildCashOperation(loanOrder);
        Loan loan = getLoanFromDB(loanOrderRequest.getLoanId());

        Optional<PawnBranch> pawnBranchOptional = pawnBranchRepository.findByAddress(currentPawnShop);
        if (pawnBranchOptional.isPresent()) {
            PawnBranch pawnBranch = pawnBranchOptional.get();
            cashOperation.setPawnBranch(pawnBranch);

            if (cashOperation.getOrderType().equals(OrderType.INCOME)) {

                pawnBranch.setBalance(pawnBranch.getBalance().add(cashOperation.getOperationAmount()));
                cashOperation.setDescription("Receipt order#" + loanOrder.getOrderId() +
                        " for loanId " + loanOrderRequest.getLoanId());

            }
            else {
                pawnBranch.setBalance(pawnBranch.getBalance().subtract(cashOperation.getOperationAmount()));
                cashOperation.setDescription("Expense order#" + loanOrder.getOrderId() +
                        " for loanId " + loanOrderRequest.getLoanId());
            }
            cashOperationRepository.save(cashOperation);
        }
    }

    private Loan getLoanFromDB(Long loanId){
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    private BigDecimal calculateProlongationAmount(Loan loan){
        return loan.getRansomAmount().subtract(loan.getLoanAmount());
    }

    private void updateLoanTerm(Loan loan, LoanProlongationRequest loanProlongationRequest){
        loan.setTerm(loanProlongationRequest.getLoanTerm());
        loanRepository.save(loan);
    }

    @Override
    public BigDecimal getProlongationAmount(LoanProlongationRequest loanProlongationRequest) {
        Loan loan = getLoanFromDB(loanProlongationRequest.getLoanId());
        BigDecimal prolongationAmount = calculateProlongationAmount(loan);
        updateLoanTerm(loan, loanProlongationRequest);
        return prolongationAmount;
    }

    @Override
    @Transactional
    public void createLoanProlongationOrder(LoanOrderRequest loanOrderRequest) {
        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setOrderType(INCOME);
        loanOrder.setLoan(getLoanFromDB(loanOrderRequest.getLoanId()));
        loanOrder.setOrderAmount(loanOrderRequest.getOrderAmount());
        loanOrderRepository.save(loanOrder);
    }


    @Override
    public List<LoanOrder> getAllLoanOrders() {
        return loanOrderRepository.findAll();
    }


}
