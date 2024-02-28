package us.telran.pawnshop.scheduler;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import us.telran.pawnshop.entity.Loan;
import us.telran.pawnshop.entity.enums.LoanStatus;
import us.telran.pawnshop.repository.LoanRepository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class ExpirationCheckSchedulerTest {

    @InjectMocks
    private ExpirationCheckScheduler underTest;

    @Mock
    private LoanRepository loanRepository;


    @Test
    void shouldCheckAndChangeLoanStatus() {
        //Given
        LocalDate today = LocalDate.now();

        Loan loan = new Loan();

        List<Loan> loansToExpire = new ArrayList<>();
        loansToExpire.add(loan);

        given(loanRepository.getLoansExpired(today.atStartOfDay())).willReturn(loansToExpire);

        //When
        underTest.checkAndExpireLoan();

        //Then
        assertThat(loan.getStatus()).isEqualTo(LoanStatus.EXPIRED);
        verify(loanRepository).save(loan);
    }
}