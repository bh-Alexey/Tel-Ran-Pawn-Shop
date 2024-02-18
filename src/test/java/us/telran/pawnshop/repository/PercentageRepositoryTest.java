package us.telran.pawnshop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import us.telran.pawnshop.entity.Percentage;
import us.telran.pawnshop.entity.enums.LoanTerm;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PercentageRepositoryTest {

    @Autowired
    private PercentageRepository underTest;

    @AfterEach
    void TearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckPercentageTermExist() {
        //given
        LoanTerm term = LoanTerm.WEEK;
        Percentage percentage = new Percentage();
        percentage.setTerm(term);
        percentage.setInterest(BigDecimal.valueOf(0.75));

        underTest.save(percentage);

        //when
        Optional<Percentage> expected = underTest.findByTerm(term);

        //then
        assertThat(expected).isPresent();
        assertThat(expected).get().isEqualTo(percentage);
    }

    @Test
    void itShouldCheckPercentageTermDoesntExist() {
        //given
        LoanTerm term = LoanTerm.WEEK;

        //when
        Optional<Percentage> expected = underTest.findByTerm(term);

        //then
        assertThat(expected).isEmpty();
    }

}