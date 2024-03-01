package us.telran.pawnshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.telran.pawnshop.entity.Client;
import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.Product;
import us.telran.pawnshop.entity.enums.ItemType;
import us.telran.pawnshop.entity.enums.MetalPurity;
import us.telran.pawnshop.validation.CorrespondingPurityToCategory;
import us.telran.pawnshop.validation.ExistingId;
import us.telran.pawnshop.validation.MetalPurityValidatable;

import java.math.BigDecimal;

@Data
@CorrespondingPurityToCategory
public class PledgeCreationRequest implements MetalPurityValidatable {

    @ExistingId(entityClass = Client.class, message = "ID does not exist among clients")
    private Long clientId;

    @ExistingId(entityClass = Product.class, message = "ID does not exist among products")
    private Long productId;

    @ExistingId(entityClass = PledgeCategory.class, message = "ID does not exist among categories")
    private Long categoryId;

    @NotNull
    private ItemType item;

    @NotNull
    private String description;

    @NotNull
    private Integer itemQuantity;

    @NotNull
    private MetalPurity purity;

    @NotNull
    private BigDecimal weightGross;

    @NotNull
    private BigDecimal weightNet;

}
