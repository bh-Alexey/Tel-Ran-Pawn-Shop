package us.telran.pawnshop.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.LoanOrderRequest;
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

import java.util.List;
import java.util.Optional;

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
    }

    @Override
    @Transactional
    public void createLoanExpenseOrder(LoanOrderRequest loanOrderRequest) {
        LoanOrder loanOrder = createLoanOrder(loanOrderRequest);
        createCashOperation(loanOrder, loanOrderRequest);
    }

    @Override
    @Transactional
    public void createLoanProlongationOrder(LoanOrderRequest loanOrderRequest) {
    }

    @Override
    public List<LoanOrder> getAllLoanOrders() {
        return loanOrderRepository.findAll();
    }

    private LoanOrder createLoanOrder(LoanOrderRequest loanOrderRequest) {
        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setOrderType(OrderType.EXPENSE);
        loanOrder.setOperationAmount(loanOrderRequest.getOrderAmount());

        Optional<Loan> loanOptional = loanRepository.findById(loanOrderRequest.getLoanId());
        if (loanOptional.isPresent()) {
            Loan loan = loanOptional.get();
            loanOrder.setLoan(loan);
        }

        return loanOrderRepository.save(loanOrder);
    }

    private void createCashOperation(LoanOrder loanOrder, LoanOrderRequest loanOrderRequest) {

        CashOperation cashOperation = new CashOperation();
        cashOperation.setOrderType(loanOrder.getOrderType());
        cashOperation.setOperationAmount(loanOrder.getOperationAmount());

        Optional<PawnBranch> pawnBranchOptional = pawnBranchRepository.findByAddress(currentPawnShop);
        if (pawnBranchOptional.isPresent()) {
            PawnBranch pawnBranch = pawnBranchOptional.get();
            cashOperation.setPawnBranch(pawnBranch);
            pawnBranch.setBalance(pawnBranch.getBalance().subtract(cashOperation.getOperationAmount()));
            pawnBranchRepository.save(pawnBranch);
        }

        cashOperation.setDescription("Expense order#" + loanOrder.getOrderId() +
                " for loanId " + loanOrderRequest.getLoanId());

        cashOperationRepository.save(cashOperation);
    }
}
