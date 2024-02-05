package us.telran.pawnshop.dto;

import lombok.Data;
import us.telran.pawnshop.entity.enums.ItemType;
import us.telran.pawnshop.entity.enums.MetalPurity;

import java.math.BigDecimal;

@Data
public class PledgeCreationRequest {

    private Long clientId;
    private Long managerId;
    private Long productId;
    private Long categoryId;
    private ItemType item;
    private String description;
    private Integer itemQuantity;
    private MetalPurity purity;
    private BigDecimal weightGross;
    private BigDecimal weightNet;

}