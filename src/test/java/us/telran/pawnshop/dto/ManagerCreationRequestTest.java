package us.telran.pawnshop.dto;


import jakarta.validation.*;
import jakarta.validation.ValidatorFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static us.telran.pawnshop.entity.enums.ManagerStatus.EXPERT_APPRAISER;

class ManagerCreationRequestTest {

    @Test
    void itShouldCheckCorrectValues() {
        //Given
        ManagerCreationRequest dto = new ManagerCreationRequest();

        //When
        dto.setFirstName("Jim");
        dto.setLastName("Belushi");
        dto.setEmail("jim.belushi@gmail.com");
        dto.setPassword("PassWord");
        dto.setManagerStatus(EXPERT_APPRAISER);

        //Then
        assertThat(dto).hasFieldOrPropertyWithValue("firstName", "Jim");
        assertThat(dto).hasFieldOrPropertyWithValue("lastName", "Belushi");
        assertThat(dto).hasFieldOrPropertyWithValue("email", "jim.belushi@gmail.com");
        assertThat(dto).hasFieldOrPropertyWithValue("password", "PassWord");
        assertThat(dto).hasFieldOrPropertyWithValue("managerStatus", EXPERT_APPRAISER);
    }

    @Test
    public void itShouldReturnViolationWhenInvalidEmailInput() {
        //Given
        ManagerCreationRequest dto = new ManagerCreationRequest();

        //When

        dto.setPassword("PassWord");
        dto.setLastName("Breitburg");
        dto.setManagerStatus(EXPERT_APPRAISER);
        dto.setEmail("invalidEmail");
        dto.setFirstName("Kim");


        //Then
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();

            Set<ConstraintViolation<ManagerCreationRequest>> violations = validator.validate(dto);

            Assertions.assertThat(violations)
                    .extracting(ConstraintViolation::getPropertyPath)
                    .extracting(Path::toString)
                    .containsOnly("email");
        }
    }

    @Test
    public void itShouldReturnViolationWhenFieldEmpty() {
        //Given
        ManagerCreationRequest dto = new ManagerCreationRequest();

        //When
        dto.setFirstName("");
        dto.setLastName("");
        dto.setManagerStatus(null);
        dto.setEmail("");
        dto.setPassword("");

        //Then
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ManagerCreationRequest>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be blank"));
        }
    }
}