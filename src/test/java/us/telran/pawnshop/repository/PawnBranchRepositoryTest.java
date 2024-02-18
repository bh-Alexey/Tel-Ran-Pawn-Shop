package us.telran.pawnshop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import us.telran.pawnshop.entity.PawnBranch;

import java.math.BigDecimal;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;

@DataJpaTest
class PawnBranchRepositoryTest {

    @Autowired
    private PawnBranchRepository underTest;

    @AfterEach
    void TearDown() {
        underTest.deleteAll();
    }


    @Test
    void itShouldCheckIfBranchAddressExist() {
        //given
        String address = "6010 Main St Flushing, NY 11355";
        PawnBranch pawnBranch = new PawnBranch();
        pawnBranch.setAddress(address);
        pawnBranch.setBalance(BigDecimal.ZERO);

        underTest.save(pawnBranch);

        //when
        Optional<PawnBranch> expected = underTest.findByAddress(address);

        //then
        assertThat(expected).isPresent();
        assertThat(expected).get().isEqualTo(pawnBranch);
    }

    @Test
    void itShouldCheckIfBranchAddressDoesNotExist() {
        //given
        String address = "6010 Main St Flushing, NY 11355";

        //when
        Optional<PawnBranch> expected = underTest.findByAddress(address);

        //then
        assertThat(expected).isEmpty();
    }
}