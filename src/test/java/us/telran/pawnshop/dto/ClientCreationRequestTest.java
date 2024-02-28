package us.telran.pawnshop.dto;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientCreationRequestTest {

    @Test
    public void testClientCreationRequest() {
        //Given
        ClientCreationRequest clientCreationRequest = new ClientCreationRequest();

        //When
        clientCreationRequest.setFirstName("Bob");
        clientCreationRequest.setLastName("Sponge");
        clientCreationRequest.setDateOfBirth(LocalDate.of(1990, 1, 1));
        clientCreationRequest.setEmail("sponge.bob@example.com");
        clientCreationRequest.setAddress("Ocean deep");

        //Then
        assertThat(clientCreationRequest.getFirstName()).isEqualTo("Bob");
        assertThat(clientCreationRequest.getLastName()).isEqualTo("Sponge");
        assertThat(clientCreationRequest.getDateOfBirth()).isEqualTo(LocalDate.of(1990, 1, 1));
        assertThat(clientCreationRequest.getEmail()).isEqualTo("sponge.bob@example.com");
        assertThat(clientCreationRequest.getAddress()).isEqualTo("Ocean deep");
    }
}