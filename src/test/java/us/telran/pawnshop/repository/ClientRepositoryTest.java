package us.telran.pawnshop.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.entity.enums.ClientStatus;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


@DataJpaTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository underTest;

    @AfterEach
    void TearDown() {
        underTest.deleteAll();
    }

    @Test
    void itShouldCheckIfClientEmailExists() {
        //given
        String email = "forrest.gump@gmail.com";
        Client client = new Client(ClientStatus.REGULAR,
                123456789,
                LocalDate.of(1991, Month.APRIL, 20),
                "Forrest",
                "Gump",
                email,
                "430 Ocean Pkwy Apt 6B Brooklyn, NY 11218"
        );


        underTest.save(client);

        //when
        Optional<Client> expected = underTest.findClientByEmail(email);

        //then
        assertThat(expected).isPresent();
        assertThat(expected).get().isEqualTo(client);
    }

    @Test
    void itShouldCheckIfClientEmailDoesNotExist() {
        //given
        String email = "forrest.gump@gmail.com";

        //when
        Optional<Client> expected = underTest.findClientByEmail(email);

        //then
        assertThat(expected).isEmpty();
    }

    @Test
    void itShouldCheckIfClientSsnExist() {
        //given
        Client client = new Client(ClientStatus.REGULAR,
                123456789,
                LocalDate.of(1991, Month.APRIL, 20),
                "Forrest",
                "Gump",
                "forrest.gump@gmail.com",
                "430 Ocean Pkwy Apt 6B Brooklyn, NY 11218"
        );

        underTest.save(client);

        //when
        Optional<Client> expected = underTest.findClientBySsn(client.getSocialSecurityNumber());

        //then
        assertThat(expected).isPresent();
        assertThat(expected).get().isEqualTo(client);
    }

    @Test
    void itShouldCheckIfClientSsnDoesNotExist() {
        //given
        int ssn = 123456789;

        //when
        Optional<Client> expected = underTest.findClientBySsn(ssn);

        //then
        assertThat(expected).isEmpty();
    }
}