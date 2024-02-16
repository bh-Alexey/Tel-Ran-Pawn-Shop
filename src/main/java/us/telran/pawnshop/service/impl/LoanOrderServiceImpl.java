package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.LoanOrderRequest;
import us.telran.pawnshop.dto.LoanProlongationRequest;
import us.telran.pawnshop.entity.*;
import us.telran.pawnshop.entity.enums.OrderType;
import us.telran.pawnshop.repository.*;
import us.telran.pawnshop.service.LoanOrderService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static us.telran.pawnshop.entity.enums.OrderType.*;

@Service
@RequiredArgsConstructor
public class LoanOrderServiceImpl implements LoanOrderService {

    private final LoanRepository loanRepository;
    private final LoanOrderRepository loanOrderRepository;
    private final CashOperationRepository cashOperationRepository;
    private final PawnBranchRepository pawnBranchRepository;
    private final PercentageRepository percentageRepository;

    @Value("${pawnshop.address}")
    private String currentPawnShop;

    @Value("${pawnshop.hundred.percents}")
    private BigDecimal hundred;

    @Value("${pawnshop.division.scale}")
    private int divisionScale;

    @Override
    @Transactional
    public void createLoanReceiptOrder(LoanOrderRequest loanOrderRequest) {
        LoanOrder loanOrder = createLoanOrder(getLoanFromDB(loanOrderRequest.getLoanId()), INCOME);
        createIncomeCashOperation(loanOrder);
    }

    @Override
    @Transactional
    public void createLoanExpenseOrder(Loan loan) {
        LoanOrder loanOrder = createLoanOrder(loan, EXPENSE);
        createExpenseCashOperation(loanOrder);
    }

    private LoanOrder createLoanOrder(Loan loan, OrderType orderType) {
        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setOrderType(orderType);
        loanOrder.setOrderAmount(loan.getLoanAmount());
        loanOrder.setLoan(loan);

        return loanOrderRepository.save(loanOrder);
    }
    private CashOperation buildCashOperation(LoanOrder loanOrder) {
        CashOperation cashOperation = new CashOperation();
        cashOperation.setOrderType(loanOrder.getOrderType());
        cashOperation.setOperationAmount(loanOrder.getOrderAmount());
        return cashOperation;
    }


    private void createExpenseCashOperation(LoanOrder loanOrder) {

        CashOperation cashOperation = buildCashOperation(loanOrder);

        PawnBranch pawnBranch = getCurrentBranch();
        cashOperation.setPawnBranch(pawnBranch);
        pawnBranch.setBalance(pawnBranch.getBalance().subtract(cashOperation.getOperationAmount()));
        cashOperation.setDescription("Expense order#" + loanOrder.getOrderId() +
                " for loanId " + loanOrder.getLoan().getLoanId());

        cashOperationRepository.save(cashOperation);
    }

    private void createIncomeCashOperation(LoanOrder loanOrder) {

        CashOperation cashOperation = buildCashOperation(loanOrder);

        PawnBranch pawnBranch = getCurrentBranch();
        cashOperation.setPawnBranch(pawnBranch);
        pawnBranch.setBalance(pawnBranch.getBalance().add(cashOperation.getOperationAmount()));
        cashOperation.setDescription("Receipt order#" + loanOrder.getOrderId() +
                 " for loanId " + loanOrder.getLoan().getLoanId());

        cashOperationRepository.save(cashOperation);

    }


    private PawnBranch getCurrentBranch() {
        return pawnBranchRepository.findByAddress(currentPawnShop)
                .orElseThrow(() -> new NoSuchElementException("No PawnBranch found with the address."));
    }

    private Loan getLoanFromDB(Long loanId){
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new RuntimeException("Loan not found"));
    }

    private Percentage findInterest(LoanProlongationRequest loanProlongationRequest){
        return percentageRepository.findByTerm(loanProlongationRequest.getLoanTerm())
                .orElseThrow(() -> new RuntimeException("Can't find necessary data"));
    }

    private BigDecimal calculateProlongationAmount(Loan loan){
        return loan.getRansomAmount().subtract(loan.getLoanAmount());
    }

    @Override
    @Transactional
    public void createLoanProlongationOrder(LoanProlongationRequest loanProlongationRequest) {
        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setOrderType(INCOME);
        loanOrder.setLoan(getLoanFromDB(loanProlongationRequest.getLoanId()));
        loanOrder.setOrderAmount(getProlongationAmount(loanProlongationRequest));
        loanOrderRepository.save(loanOrder);

        createIncomeCashOperation(loanOrder);

        updateLoan(loanOrder.getLoan(), loanProlongationRequest);
    }

    public BigDecimal getProlongationAmount(LoanProlongationRequest loanProlongationRequest) {
        Loan loan = getLoanFromDB(loanProlongationRequest.getLoanId());
        return calculateProlongationAmount(loan);
    }


    private void updateLoan(Loan loan, LoanProlongationRequest loanProlongationRequest){
        loan.setTerm(loanProlongationRequest.getLoanTerm());
        Percentage percentage = findInterest(loanProlongationRequest);
        loan.setRansomAmount(loan.getLoanAmount().multiply(BigDecimal.ONE
                .add((percentage.getInterest().divide(hundred, divisionScale, RoundingMode.HALF_UP))
                        .multiply(BigDecimal.valueOf(loan.getTerm().getDays())))));
        loan.setExpiredAt(loan.getExpiredAt().plusDays(loan.getTerm().getDays()));
        loanRepository.save(loan);
    }

    @Override
    public List<LoanOrder> getAllLoanOrders() {
        return loanOrderRepository.findAll();
    }

    private BigDecimal getRansomAmount(Long loanId) {
        Loan loan = getLoanFromDB(loanId);
        return loan.getRansomAmount();
    }

}
