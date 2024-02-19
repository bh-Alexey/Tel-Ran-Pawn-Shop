package us.telran.pawnshop.entity;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import us.telran.pawnshop.entity.enums.LoanTerm;
import us.telran.pawnshop.repository.LoanRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    @Test
    void expiredAt() {
        //Given
        Loan loan = new Loan();
        loan.setTerm(LoanTerm.WEEK);
        loan.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));

        //When: manually invoking @PrePersist method
        loan.expiredAt();

        //Then
        LocalDateTime expected = loan.getCreatedAt().toLocalDateTime().plusDays(loan.getTerm().getDays());
        assertThat(expected).isEqualTo(loan.getExpiredAt());
    }
}