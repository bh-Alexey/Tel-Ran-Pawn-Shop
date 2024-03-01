package us.telran.pawnshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.telran.pawnshop.entity.Loan;
import us.telran.pawnshop.entity.enums.LoanTerm;
import us.telran.pawnshop.validation.ExistingId;

@Data
public class LoanProlongationRequest {

    @ExistingId(entityClass = Loan.class, message = "ID does not exist among loans")
    Long loanId;

    @NotNull
    LoanTerm loanTerm;
}
