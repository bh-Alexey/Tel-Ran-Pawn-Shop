package us.telran.pawnshop.dto;

import lombok.Data;
import us.telran.pawnshop.entity.Credit;
import us.telran.pawnshop.entity.enums.CreditStatus;

import java.math.BigDecimal;

@Data
public class CreditCreationRequest {

    private Long pledgeId;
    private BigDecimal creditAmount;
    private Credit.CreditTerm term;
    private CreditStatus status;
}
