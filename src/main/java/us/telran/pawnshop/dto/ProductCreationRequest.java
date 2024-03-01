package us.telran.pawnshop.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;
import us.telran.pawnshop.entity.enums.ProductStatus;

import java.math.BigDecimal;

@Data
public class ProductCreationRequest {

    @NotBlank
    private String productName;

    @NotNull
    private ProductStatus status;

    @PositiveOrZero
    private BigDecimal interestRate;

}
