package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import us.telran.pawnshop.dto.LoanOrderRequest;
import us.telran.pawnshop.dto.LoanProlongationRequest;
import us.telran.pawnshop.entity.*;
import us.telran.pawnshop.entity.enums.OrderType;
import us.telran.pawnshop.repository.*;
import us.telran.pawnshop.security.SecurityUtils;
import us.telran.pawnshop.service.LoanOrderService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import static us.telran.pawnshop.entity.enums.OrderType.*;

@Service
@RequiredArgsConstructor
public class LoanOrderServiceImpl implements LoanOrderService {

    private final LoanRepository loanRepository;
    private final LoanOrderRepository loanOrderRepository;
    private final CashOperationRepository cashOperationRepository;
    private final PawnBranchRepository pawnBranchRepository;
    private final PercentageRepository percentageRepository;
    private final PawnBranch currentBranch;

    @Value("${pawnshop.address}")
    private String currentPawnShop;

    @Value("${pawnshop.hundred.percents}")
    private BigDecimal hundred;

    @Value("${pawnshop.division.scale}")
    private int divisionScale;

    private SecurityUtils securityUtils;
    Long currentManagerId = SecurityUtils.getCurrentManagerId();

    private Loan getLoanFromDB(Long loanId){
        return loanRepository.findById(loanId)
                .orElseThrow(() -> new EntityNotFoundException("Loan not found"));
    }

    @Override
    public BigDecimal giveRansomAmount(Long loanId) {
        Loan loan = getLoanFromDB(loanId);
        return loan.getRansomAmount();
    }

    @Override
    @Transactional
    public void createLoanReceiptOrder(LoanOrderRequest loanOrderRequest) {
        LoanOrder loanOrder = createLoanOrder(getLoanFromDB(loanOrderRequest.getLoanId()), INCOME);
        createIncomeCashOperation(loanOrder);
    }

    private void createIncomeCashOperation(LoanOrder loanOrder) {

        CashOperation cashOperation = buildCashOperation(loanOrder);

        Manager manager = new Manager();
        manager.setManagerId(currentManagerId);

        cashOperation.setManager(manager);
        cashOperation.setPawnBranch(currentBranch);
        currentBranch.setBalance(currentBranch.getBalance().add(cashOperation.getOperationAmount()));
        cashOperation.setDescription("Receipt order#" + loanOrder.getOrderId() +
                " for loanId " + loanOrder.getLoan().getLoanId());

        cashOperationRepository.save(cashOperation);
    }

    private CashOperation buildCashOperation(LoanOrder loanOrder) {
        CashOperation cashOperation = new CashOperation();
        cashOperation.setOrderType(loanOrder.getOrderType());
        cashOperation.setOperationAmount(loanOrder.getOrderAmount());
        return cashOperation;
    }

    private LoanOrder createLoanOrder(Loan loan, OrderType orderType) {
        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setOrderType(orderType);
        loanOrder.setOrderAmount(loan.getLoanAmount());
        loanOrder.setLoan(loan);

        return loanOrderRepository.save(loanOrder);
    }

    @Override
    @Transactional
    public void createLoanExpenseOrder(Loan loan) {
        LoanOrder loanOrder = createLoanOrder(loan, EXPENSE);
        createExpenseCashOperation(loanOrder);
    }

    private void createExpenseCashOperation(LoanOrder loanOrder) {

        CashOperation cashOperation = buildCashOperation(loanOrder);

        Manager manager = new Manager();
        manager.setManagerId(currentManagerId);

        cashOperation.setManager(manager);
        cashOperation.setPawnBranch(currentBranch);
        currentBranch.setBalance(currentBranch.getBalance().subtract(cashOperation.getOperationAmount()));
        cashOperation.setDescription("Expense order#" + loanOrder.getOrderId() +
                " for loanId " + loanOrder.getLoan().getLoanId());

        cashOperationRepository.save(cashOperation);
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

    private BigDecimal calculateProlongationAmount(Loan loan){
        return loan.getRansomAmount().subtract(loan.getLoanAmount());
    }

    public BigDecimal getProlongationAmount(LoanProlongationRequest loanProlongationRequest) {
        Loan loan = getLoanFromDB(loanProlongationRequest.getLoanId());
        return calculateProlongationAmount(loan);
    }

    private Percentage findInterest(LoanProlongationRequest loanProlongationRequest){
        return percentageRepository.findByTerm(loanProlongationRequest.getLoanTerm())
                .orElseThrow(() -> new EntityNotFoundException("Can't find necessary data"));
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

}
