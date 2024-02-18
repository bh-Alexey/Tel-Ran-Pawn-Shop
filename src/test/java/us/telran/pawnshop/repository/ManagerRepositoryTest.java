package us.telran.pawnshop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import us.telran.pawnshop.entity.Manager;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static us.telran.pawnshop.entity.enums.ManagerStatus.*;


@DataJpaTest
class ManagerRepositoryTest {

    @Autowired
    private ManagerRepository underTest;

    @AfterEach
    void TearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfManagerEmailExist() {
        //given
        String email = "jason.jay@gmail.com";
        Manager manager = new Manager("Jason",
                "Jay",
                email,
                EXPERT_APPRAISER);

        underTest.save(manager);

        //when
        Optional<Manager> expected = underTest.findManagerByEmail(email);

        //then
        assertThat(expected).isPresent();
        assertThat(expected).get().isEqualTo(manager);
    }

    @Test
    void itShouldCheckIfManagerEmailDoesNotExist() {
        //given
        String email = "jason.jay@gmail.com";

        //when
        Optional<Manager> expected = underTest.findManagerByEmail(email);

        //then
        assertThat(expected).isEmpty();
    }
}