package us.telran.pawnshop.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static us.telran.pawnshop.entity.enums.LoanTerm.*;

class PercentageCreationRequestTest {

    @Test
    void itShouldCheckCorrectValues() {
        //Given
        PercentageCreationRequest dto = new PercentageCreationRequest();

        //When
        dto.setTerm(MONTH);

        //Then
        assertThat(dto).hasFieldOrPropertyWithValue("term", MONTH);
    }

    @Test
    void itShouldCheckValidation() {
        //Given
        PercentageCreationRequest dto = new PercentageCreationRequest();

        //When
        dto.setTerm(null);

        //Then
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PercentageCreationRequest>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be null"));
        }
    }


}