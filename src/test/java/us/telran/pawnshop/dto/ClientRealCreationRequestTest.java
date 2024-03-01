package us.telran.pawnshop.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;



import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ClientRealCreationRequestTest {

    @Test
    void itShouldCheckCorrectValues() {
        //Given
        ClientRealCreationRequest dto = new ClientRealCreationRequest();

        //When
        dto.setFirstName("Nick");
        dto.setLastName("Lowes");
        dto.setSocialSecurityNumber(123454321);
        dto.setDateOfBirth(LocalDate.of(1980, 5, 25));
        dto.setEmail("nick.lowes@example.com");
        dto.setAddress("308 Stillwell Ave 32R");

        //Then
        assertThat(dto).hasFieldOrPropertyWithValue("firstName", "Nick");
        assertThat(dto).hasFieldOrPropertyWithValue("lastName", "Lowes");
        assertThat(dto).hasFieldOrPropertyWithValue("socialSecurityNumber", 123454321);
        assertThat(dto).hasFieldOrPropertyWithValue("dateOfBirth", LocalDate.of(1980, 5, 25));
        assertThat(dto).hasFieldOrPropertyWithValue("email", "nick.lowes@example.com");
        assertThat(dto).hasFieldOrPropertyWithValue("address", "308 Stillwell Ave 32R");
    }

    @Test
    public void itShouldReturnViolationWhenFieldEmpty() {
        //Given
        ClientRealCreationRequest dto = new ClientRealCreationRequest();

        //Then
        dto.setFirstName("");
        dto.setLastName("");
        dto.setEmail("");
        dto.setDateOfBirth(LocalDate.now());
        dto.setAddress("");

        //Then
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ClientRealCreationRequest>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be blank"));
        }
    }
}
