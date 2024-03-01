package us.telran.pawnshop.dto;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;
import static org.assertj.core.api.Assertions.assertThat;

public class ClientCreationRequestTest {

    @Test
    public void itShouldCheckCorrectValues() {
        //Given
        ClientCreationRequest dto = new ClientCreationRequest();

        //When
        dto.setFirstName("Bob");
        dto.setLastName("Sponge");
        dto.setDateOfBirth(LocalDate.of(1990, 1, 1));
        dto.setEmail("sponge.bob@example.com");
        dto.setAddress("Ocean deep");

        //Then
        assertThat(dto).hasFieldOrPropertyWithValue("firstName","Bob");
        assertThat(dto).hasFieldOrPropertyWithValue("lastName","Sponge");
        assertThat(dto).hasFieldOrPropertyWithValue("dateOfBirth",LocalDate.of(1990, 1, 1));
        assertThat(dto).hasFieldOrPropertyWithValue("email","sponge.bob@example.com");
        assertThat(dto).hasFieldOrPropertyWithValue("address","Ocean deep");
    }
}