package us.telran.pawnshop.scheduler;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import us.telran.pawnshop.entity.Loan;
import us.telran.pawnshop.entity.enums.LoanStatus;
import us.telran.pawnshop.repository.LoanRepository;

import java.time.LocalDate;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ExpirationCheckScheduler {

    private final LoanRepository loanRepository; // Подставьте имя вашего репозитория

    @Scheduled(cron = "0 0 0 * * *") // Run every midnight
    @Transactional
    public void checkAndExpireLoan() {
        LocalDate today = LocalDate.now();
        List<Loan> loansToExpire = loanRepository.findAllByExpiredAtBefore(today.atStartOfDay());
        for (Loan loan : loansToExpire) {
            loan.setStatus(LoanStatus.EXPIRED);
            loanRepository.save(loan);
        }
    }
}