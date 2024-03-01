package us.telran.pawnshop.dto;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static us.telran.pawnshop.entity.enums.MetalPurity.SILVER_925;

class PreciousMetalPriceCreationRequestTest {

    @Test
    void itShouldCheckCorrectValues() {
        //Given
        PreciousMetalPriceCreationRequest dto = new PreciousMetalPriceCreationRequest();

        //When
        dto.setCategoryId(1L);
        dto.setPurity(SILVER_925);
        dto.setMetalPrice(new BigDecimal("5.0"));

        //Then
        assertThat(dto).hasFieldOrPropertyWithValue("categoryId", 1L);
        assertThat(dto).hasFieldOrPropertyWithValue("purity",SILVER_925);
        assertThat(dto).hasFieldOrPropertyWithValue("metalPrice", new BigDecimal("5.0"));
    }


    @Test
    void itShouldReturnViolationWhenFieldEmpty() {
        //Given
        PreciousMetalPriceCreationRequest dto = new PreciousMetalPriceCreationRequest();

        //When
        dto.setCategoryId(null);
        dto.setPurity(null);
        dto.setMetalPrice(null);

        //Then
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<PreciousMetalPriceCreationRequest>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be null") ||
                      v.getMessage().contains("Required fields empty or filled incorrectly") ||
                      v.getMessage().contains("ID does not exist among categories"));
        }
    }
}