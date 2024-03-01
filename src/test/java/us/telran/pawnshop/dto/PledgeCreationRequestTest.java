package us.telran.pawnshop.dto;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static us.telran.pawnshop.entity.enums.ItemType.*;
import static us.telran.pawnshop.entity.enums.MetalPurity.*;

class PledgeCreationRequestTest {

    @Test
    void itShouldCheckCorrectValues() {
        //Given
        PledgeCreationRequest dto = new PledgeCreationRequest();

        //When
        dto.setClientId(1L);
        dto.setProductId(2L);
        dto.setCategoryId(1L);
        dto.setItem(SCRAP);
        dto.setDescription("Test item");
        dto.setItemQuantity(5);
        dto.setPurity(GOLD_585);
        dto.setWeightGross(new BigDecimal("10.0"));
        dto.setWeightNet(new BigDecimal("8.0"));

        //Then
        assertThat(dto).isNotNull();
        assertThat(dto).hasFieldOrPropertyWithValue("clientId",1L);
        assertThat(dto).hasFieldOrPropertyWithValue("productId",2L);
        assertThat(dto).hasFieldOrPropertyWithValue("categoryId",1L);
        assertThat(dto).hasFieldOrPropertyWithValue("item", SCRAP);
        assertThat(dto).hasFieldOrPropertyWithValue("description","Test item");
        assertThat(dto).hasFieldOrPropertyWithValue("itemQuantity",5);
        assertThat(dto).hasFieldOrPropertyWithValue("purity", GOLD_585);
        assertThat(dto).hasFieldOrPropertyWithValue("weightGross", BigDecimal.valueOf(10.0));
        assertThat(dto).hasFieldOrPropertyWithValue("weightNet", BigDecimal.valueOf(8.0));
    }

    @Test
    void itShouldReturnViolationWhenFieldEmpty() {
        //Given
        PledgeCreationRequest dto = new PledgeCreationRequest();

        //When
        dto.setClientId(null);
        dto.setProductId(null);
        dto.setCategoryId(null);
        dto.setItem(null);
        dto.setDescription("");
        dto.setItemQuantity(null);
        dto.setPurity(null);
        dto.setWeightGross(null);
        dto.setWeightNet(null);

        //Then
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {

            Validator validator = factory.getValidator();

            Set<ConstraintViolation<PledgeCreationRequest>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be null"));
        }
    }
}