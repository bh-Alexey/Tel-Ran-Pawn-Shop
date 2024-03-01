package us.telran.pawnshop.dto;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class TransferRequestTest {


    @Test
    void itShouldCheckCorrectValues() {
        //Given
        TransferRequest dto = new TransferRequest();

        //When
        dto.setFromBranchId(1L);
        dto.setToBranchId(2L);
        dto.setTransferAmount(BigDecimal.valueOf(1000));

        //Then
        assertThat(dto)
                .hasFieldOrPropertyWithValue("fromBranchId", 1L)
                .hasFieldOrPropertyWithValue("toBranchId", 2L)
                .hasFieldOrPropertyWithValue("transferAmount", BigDecimal.valueOf(1000));
    }


    @Test
    void itShouldReturnViolationWhenFieldEmpty() {
        //Given
        TransferRequest dto = new TransferRequest();

        //When
        dto.setFromBranchId(null);
        dto.setToBranchId(null);
        dto.setTransferAmount(null);

        //Then
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            Validator validator = factory.getValidator();
            Set<ConstraintViolation<TransferRequest>> violations = validator.validate(dto);

            assertThat(violations).isNotEmpty();
            assertThat(violations).anyMatch(v -> v.getMessage().contains("must not be null"));

        }
    }
}