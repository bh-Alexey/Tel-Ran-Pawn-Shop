package us.telran.pawnshop.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;


class LoanOrderRequestTest {

    @Test
    void itShouldCheckFields() {
        //Given
        LoanOrderRequest dto = new LoanOrderRequest();

        //When
        dto.setLoanId(1L);
        dto.setOrderAmount(BigDecimal.TEN);

        //Then
        assertThat(dto).hasFieldOrPropertyWithValue("loanId", 1L);
        assertThat(dto).hasFieldOrPropertyWithValue("orderAmount", BigDecimal.TEN);
    }

    @Test
    void itShouldReturnViolationWhenFieldEmpty() {
        //Given
        LoanOrderRequest dto = new LoanOrderRequest();

        //When
        dto.setOrderAmount(null);

        //Then
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();

            Set<ConstraintViolation<LoanOrderRequest>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be null"));
        }
    }
}
