package us.telran.pawnshop.dto;

import lombok.Data;
import us.telran.pawnshop.entity.enums.MetalPurity;

import java.math.BigDecimal;

@Data
public class PreciousMetalPriceCreationRequest {

    private Long categoryId;
    public MetalPurity purity;
    private BigDecimal metalPrice;
}
