package us.telran.pawnshop.dto;

import lombok.Data;
import us.telran.pawnshop.entity.enums.LoanStatus;
import us.telran.pawnshop.entity.enums.LoanTerm;

import java.math.BigDecimal;

@Data
public class LoanCreationRequest {

    private Long pledgeId;
    private BigDecimal creditAmount;
    private LoanTerm term;
}
