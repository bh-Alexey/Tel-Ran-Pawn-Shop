package us.telran.pawnshop.entity;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.*;


class ClientTest {

    @Test
    void itShouldGenerateSocialSecurityNumber() {
        //given
        Client client = new Client();
        client.setSsnOrigin(100000000);
        client.setSsnBound(1000000000);

        //when
        client.generateSocialSecurityNumber();

        //then
        assertThat(client.getSocialSecurityNumber()).isBetween(100000000, 1000000000);
    }

}