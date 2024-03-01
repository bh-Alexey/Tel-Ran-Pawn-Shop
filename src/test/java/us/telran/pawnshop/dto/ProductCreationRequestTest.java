package us.telran.pawnshop.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static us.telran.pawnshop.entity.enums.ProductStatus.INACTIVE;

class ProductCreationRequestTest {


    @Test
    void itShouldCheckCorrectValues() {
        //Given
        ProductCreationRequest dto = new ProductCreationRequest();

        //When
        dto.setProductName("Test Name");
        dto.setStatus(INACTIVE);
        dto.setInterestRate(BigDecimal.TEN);


        //Then
        assertThat(dto).hasFieldOrPropertyWithValue("productName", "Test Name");
        assertThat(dto).hasFieldOrPropertyWithValue("status", INACTIVE);
        assertThat(dto).hasFieldOrPropertyWithValue("interestRate", BigDecimal.TEN);
    }

    @Test
    void itShouldReturnViolationWhenFieldEmpty() {
        //Given
        ProductCreationRequest dto = new ProductCreationRequest();

        //When
        dto.setProductName("");
        dto.setStatus(null);
        dto.setInterestRate(null);

        //Then
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();
            Set<ConstraintViolation<ProductCreationRequest>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be blank"));
        }
    }
}
