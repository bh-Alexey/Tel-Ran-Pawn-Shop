package us.telran.pawnshop.service.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import us.telran.pawnshop.dto.LoanOrderRequest;
import us.telran.pawnshop.dto.LoanProlongationRequest;
import us.telran.pawnshop.entity.*;
import us.telran.pawnshop.entity.enums.LoanTerm;
import us.telran.pawnshop.entity.enums.OrderType;
import us.telran.pawnshop.repository.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanOrderServiceImplTest {

    @Mock
    private  LoanRepository loanRepository;

    @Mock
    private  LoanOrderRepository loanOrderRepository;

    @Mock
    private  CashOperationRepository cashOperationRepository;

    @Mock
    private PawnBranch currentBranch;

    @Mock
    private PercentageRepository percentageRepository;

    @InjectMocks
    private LoanOrderServiceImpl underTest;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(underTest, "hundred", new BigDecimal(100));
        ReflectionTestUtils.setField(underTest, "divisionScale",8);
    }

    @Test
    void canGiveRansomAmount() {
        Loan loan = new Loan();
        loan.setRansomAmount(new BigDecimal(1000));

        //When
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));

        BigDecimal ransomAmount = underTest.giveRansomAmount(1L);

        //Then
        assertThat(ransomAmount).isEqualByComparingTo(loan.getRansomAmount());
    }

    @Test
    void canCreateLoanReceiptOrder() {
        //Given
        BigDecimal initialBalance = new BigDecimal("10000");

        Long loanId = 1L;
        LoanOrderRequest loanOrderRequest = new LoanOrderRequest();
        loanOrderRequest.setOrderAmount(BigDecimal.valueOf(1000));
        loanOrderRequest.setLoanId(loanId);

        Loan loan = new Loan();
        loan.setLoanId(loanId);

        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setLoan(loan);
        loanOrder.setOrderType(OrderType.INCOME);
        loanOrder.setOrderAmount(loanOrderRequest.getOrderAmount());

        CashOperation cashOperation = new CashOperation();
        cashOperation.setOperationAmount(loanOrder.getOrderAmount());

        //When
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));
        when(loanOrderRepository.save(any(LoanOrder.class))).thenReturn(loanOrder);
        when(cashOperationRepository.save(any(CashOperation.class))).thenReturn(cashOperation);
        when(currentBranch.getBalance()).thenReturn(initialBalance)
                .thenReturn(initialBalance, initialBalance.add(cashOperation.getOperationAmount()));

        underTest.createLoanReceiptOrder(loanOrderRequest);

        //Then
        verify(loanRepository, times(1)).findById(loanOrderRequest.getLoanId());
        verify(loanOrderRepository, times(1)).save(any(LoanOrder.class));
        verify(cashOperationRepository, times(1)).save(any(CashOperation.class));
        assertThat(initialBalance.add(cashOperation.getOperationAmount())).isGreaterThan(new BigDecimal("10000"));
    }

    @Test
    void canCreateLoanExpenseOrder() {
        //Given
        BigDecimal initialBalance = new BigDecimal("10000");

        Loan loan = new Loan();
        loan.setLoanId(1L);

        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setLoan(loan);
        loanOrder.setOrderAmount(BigDecimal.valueOf(1000));

        CashOperation cashOperation = new CashOperation();
        cashOperation.setOperationAmount(loanOrder.getOrderAmount());

        //When
        when(loanOrderRepository.save(any(LoanOrder.class))).thenReturn(loanOrder);
        when(cashOperationRepository.save(any(CashOperation.class))).thenReturn(cashOperation);
        when(currentBranch.getBalance()).thenReturn(initialBalance)
                .thenReturn(initialBalance, initialBalance.subtract(cashOperation.getOperationAmount()));

        underTest.createLoanExpenseOrder(loan);

        //Then
        verify(loanOrderRepository, times(1)).save(any(LoanOrder.class));
        verify(cashOperationRepository, times(1)).save(any(CashOperation.class));
        assertThat(initialBalance.subtract(cashOperation.getOperationAmount())).isLessThan(new BigDecimal("10000"));
    }

    @Test
    void canCreateLoanProlongationOrder() {
        //Given
        BigDecimal initialBalance = new BigDecimal("10000");

        LoanProlongationRequest loanProlongRequest = new LoanProlongationRequest();
        loanProlongRequest.setLoanId(1L);

        LoanTerm loanTerm = LoanTerm.WEEK;
        loanProlongRequest.setLoanTerm(loanTerm);

        Loan loan = new Loan();
        loan.setLoanId(1L);
        loan.setLoanAmount(BigDecimal.valueOf(100));
        loan.setRansomAmount(BigDecimal.valueOf(120));
        loan.setExpiredAt(LocalDateTime.now());

        LoanOrder loanOrder = new LoanOrder();
        loanOrder.setLoan(loan);
        loanOrder.setOrderAmount(BigDecimal.valueOf(20));

        Percentage percentage = new Percentage();
        percentage.setInterest(BigDecimal.valueOf(0.9));
        CashOperation cashOperation = new CashOperation();
        cashOperation.setOperationAmount(loanOrder.getOrderAmount());

        //When
        when(loanRepository.findById(anyLong())).thenReturn(Optional.of(loan));
        when(loanOrderRepository.save(any(LoanOrder.class))).thenReturn(loanOrder);
        when(cashOperationRepository.save(any(CashOperation.class))).thenReturn(cashOperation);
        when(percentageRepository.findByTerm(any(LoanTerm.class))).thenReturn(Optional.of(percentage));
        when(currentBranch.getBalance()).thenReturn(initialBalance)
                .thenReturn(initialBalance, initialBalance.add(cashOperation.getOperationAmount()));

        underTest.createLoanProlongationOrder(loanProlongRequest);

        //Then
        verify(loanOrderRepository, times(1)).save(any(LoanOrder.class));
        verify(cashOperationRepository, times(1)).save(any(CashOperation.class));
        verify(loanRepository, times(2)).findById(anyLong());
        verify(percentageRepository, times(1)).findByTerm(any(LoanTerm.class));
        assertThat(loan.getTerm()).isEqualTo(loanProlongRequest.getLoanTerm());
        assertThat(initialBalance.add(cashOperation.getOperationAmount())).isGreaterThan(new BigDecimal("10000"));
    }

    @Test
    void getAllLoanOrders() {
        //When
        underTest.getAllLoanOrders();

        //Then
        verify(loanOrderRepository).findAll();
    }
}