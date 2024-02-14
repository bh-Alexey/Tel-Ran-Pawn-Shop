package us.telran.pawnshop.dto;

import lombok.Data;
import us.telran.pawnshop.entity.enums.LoanTerm;

@Data
public class LoanProlongationRequest {

    Long loanId;
    LoanTerm loanTerm;
}
