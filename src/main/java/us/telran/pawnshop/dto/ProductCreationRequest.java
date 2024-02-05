package us.telran.pawnshop.dto;

import lombok.Data;
import us.telran.pawnshop.entity.enums.ProductName;
import us.telran.pawnshop.entity.enums.ProductStatus;

import java.math.BigDecimal;

@Data
public class ProductCreationRequest {

    private ProductName productName;
    private ProductStatus status;
    private BigDecimal interestRate;

}
