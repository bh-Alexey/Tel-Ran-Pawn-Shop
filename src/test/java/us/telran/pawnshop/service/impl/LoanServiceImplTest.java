package us.telran.pawnshop.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import us.telran.pawnshop.dto.LoanCreationRequest;
import us.telran.pawnshop.entity.Loan;
import us.telran.pawnshop.entity.Percentage;
import us.telran.pawnshop.entity.Pledge;
import us.telran.pawnshop.entity.enums.LoanStatus;
import us.telran.pawnshop.entity.enums.LoanTerm;
import us.telran.pawnshop.repository.LoanRepository;
import us.telran.pawnshop.repository.PercentageRepository;
import us.telran.pawnshop.repository.PledgeRepository;
import us.telran.pawnshop.service.LoanOrderService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceImplTest {

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private PledgeRepository pledgeRepository;

    @Mock
    private PercentageRepository percentageRepository;

    @Mock
    private LoanOrderService loanOrderService;

    @InjectMocks
    private LoanServiceImpl underTest;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(underTest, "hundred", new BigDecimal("100"));
        ReflectionTestUtils.setField(underTest, "divisionScale", 8);
    }

    @Test
    void newLoan() {
        //Given
        Pledge pledge = new Pledge();
        pledge.setEstimatedPrice(BigDecimal.valueOf(120));

        Percentage percentage = new Percentage();
        percentage.setInterest(BigDecimal.valueOf(0.05));

        LoanCreationRequest loanCreationRequest = new LoanCreationRequest();
        loanCreationRequest.setPledgeId(1L);
        loanCreationRequest.setLoanAmount(BigDecimal.valueOf(100));
        loanCreationRequest.setTerm(LoanTerm.THREE_WEEKS);

        when(pledgeRepository.findById(1L)).thenReturn(Optional.of(pledge));
        when(percentageRepository.findByTerm(loanCreationRequest.getTerm())).thenReturn(Optional.of(percentage));

        underTest.newLoan(loanCreationRequest);

        verify(loanRepository, times(1)).save(any());
        verify(loanOrderService, times(1)).createLoanExpenseOrder(any());
    }

    @Test
    void willThrowsWhenCreditAmountIsTooHigh() {
        Pledge pledge = new Pledge();
        pledge.setEstimatedPrice(BigDecimal.valueOf(105.8));

        LoanCreationRequest loanCreationRequest = new LoanCreationRequest();
        loanCreationRequest.setPledgeId(1L);
        loanCreationRequest.setLoanAmount(BigDecimal.valueOf(106));
        loanCreationRequest.setTerm(LoanTerm.WEEK);

        when(pledgeRepository.findById(1L)).thenReturn(Optional.of(pledge));

        assertThatThrownBy(() -> underTest.newLoan(loanCreationRequest))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("Amount can't be higher than " + pledge.getEstimatedPrice());
    }

    @Test
    void getAllLoans() {
        //When
        underTest.getAllLoans();

        //Then
        verify(loanRepository).findAll();
    }

    @Test
    void canGetLoanById() {
        //Given
        Long loanId = 1L;
        Loan loan = new Loan();

        //When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        //Then
        assertThat(underTest.getLoanById(loanId)).isEqualTo(loan);
    }

    @Test
    public void getLoanByIdFailureTest() {
        Long unExistingLoanId = 2L;
        when(loanRepository.findById(unExistingLoanId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> underTest.getLoanById(unExistingLoanId))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan with id " + unExistingLoanId + " doesn't exist");
    }
    @Test
    void canUpdateLoan() {
        //Given
        Long loanId = 1L;
        BigDecimal loanAmount = BigDecimal.valueOf(100);
        BigDecimal ransomAmount = BigDecimal.valueOf(108);
        LoanTerm term = LoanTerm.WEEK;
        LoanStatus status = LoanStatus.IN_USE;

        Loan loan = new Loan();

        //When
        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        underTest.updateLoan(loanId, loanAmount, ransomAmount, term, status);

        //Then
        assertThat(loan.getLoanAmount()).isEqualByComparingTo(loanAmount);
        assertThat(loan.getRansomAmount()).isEqualByComparingTo(ransomAmount);
        assertThat(loan.getTerm()).isEqualTo(term);
        assertThat(loan.getStatus()).isEqualTo(status);
    }

    @Test
    void willThrowWhenLoanForUpdateNotFound() {
        Long loanId = 1L;
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        assertThatThrownBy(() ->
                underTest.updateLoan(loanId,
                        BigDecimal.valueOf(1000),
                        BigDecimal.valueOf(800),
                        LoanTerm.WEEK, LoanStatus.IN_USE)
        ).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Credit with id " + loanId + " doesn't exist");
    }

    @Test
    void canDeleteLoan(){
        //Given
        Long loanId = 1L;
        Loan loan = new Loan();
        loan.setLoanId(loanId);

        when(loanRepository.findById(loanId)).thenReturn(Optional.of(loan));

        // when
        underTest.deleteLoan(loanId);

        // then
        verify(loanRepository, times(1)).deleteById(loanId);
    }

    @Test
    void willThrowWhenLoanNotFound() {
        //Given
        Long loanId = 1L;

        //When
        when(loanRepository.findById(loanId)).thenReturn(Optional.empty());

        Throwable thrown = catchThrowable(() -> underTest.deleteLoan(loanId));

        //Then
        assertThat(thrown).isInstanceOf(EntityNotFoundException.class)
                .hasMessage("Loan with id " + loanId + " doesn't exist");
    }

    @Test
    void updateLoanStatus() {
        //Given
        Loan loan = new Loan();
        loan.setExpiredAt(LocalDateTime.now().minusDays(1));

        when(loanRepository.findById(any())).thenReturn(Optional.of(loan));

        //When
        underTest.updateLoanStatus(1L);

        //Then
        verify(loanRepository).save(argThat(
                savedLoan -> savedLoan.getStatus().equals(LoanStatus.EXPIRED)
        ));
    }
}
