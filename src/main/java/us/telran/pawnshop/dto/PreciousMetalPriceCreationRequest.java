package us.telran.pawnshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.enums.MetalPurity;
import us.telran.pawnshop.validation.CorrespondingPurityToCategory;
import us.telran.pawnshop.validation.ExistingId;
import us.telran.pawnshop.validation.MetalPurityValidatable;

import java.math.BigDecimal;

@Data
@CorrespondingPurityToCategory
public class PreciousMetalPriceCreationRequest implements MetalPurityValidatable {

    @ExistingId(entityClass = PledgeCategory.class, message = "ID does not exist among categories")
    private Long categoryId;

    @NotNull
    public MetalPurity purity;

    @NotNull
    private BigDecimal metalPrice;
}
