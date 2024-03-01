package us.telran.pawnshop.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static us.telran.pawnshop.entity.enums.LoanTerm.TWO_WEEKS;


class LoanProlongationRequestTest {

    @Test
    void itShouldCheckFields() {
        //Given
        LoanProlongationRequest dto = new LoanProlongationRequest();

        //When
        dto.setLoanId(1L);
        dto.setLoanTerm(TWO_WEEKS);

        //Then
        assertThat(dto).hasFieldOrPropertyWithValue("loanId", 1L);
        assertThat(dto).hasFieldOrPropertyWithValue("loanTerm", TWO_WEEKS);
    }

    @Test
    void itShouldReturnViolationWhenFieldEmpty() {
        //Given
        LoanProlongationRequest dto = new LoanProlongationRequest();

        //When
        dto.setLoanTerm(null);

        //Then
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();

            Set<ConstraintViolation<LoanProlongationRequest>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be null"));
        }
    }
}