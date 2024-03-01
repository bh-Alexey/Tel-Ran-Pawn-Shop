package us.telran.pawnshop.dto;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static us.telran.pawnshop.entity.enums.LoanTerm.*;

class LoanCreationRequestTest {

    @Test
    void itShouldCheckCorrectValues() {
        //Given
        LoanCreationRequest dto = new LoanCreationRequest();

        //When
        dto.setPledgeId(1L);
        dto.setLoanAmount(BigDecimal.TEN);
        dto.setTerm(WEEK);

        //Then
        assertThat(dto).hasFieldOrPropertyWithValue("pledgeId", 1L);
        assertThat(dto).hasFieldOrPropertyWithValue("loanAmount", BigDecimal.TEN);
        assertThat(dto).hasFieldOrPropertyWithValue("term", WEEK);
    }

    @Test
    void itShouldReturnViolationWhenFieldEmpty() {
        //Given
        LoanCreationRequest dto = new LoanCreationRequest();

        //When
        dto.setLoanAmount(null);
        dto.setTerm(null);

        //Then
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();

            Set<ConstraintViolation<LoanCreationRequest>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be null"));
        }
    }
}