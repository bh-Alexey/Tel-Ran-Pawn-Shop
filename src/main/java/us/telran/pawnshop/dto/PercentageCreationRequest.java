package us.telran.pawnshop.dto;

import lombok.Data;
import us.telran.pawnshop.entity.enums.CreditTerm;

import java.math.BigDecimal;

@Data
public class PercentageCreationRequest {

    private CreditTerm term;

}
