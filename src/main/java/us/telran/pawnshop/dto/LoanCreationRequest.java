package us.telran.pawnshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.telran.pawnshop.entity.Pledge;
import us.telran.pawnshop.entity.enums.LoanTerm;
import us.telran.pawnshop.validation.ExistingId;

import java.math.BigDecimal;

@Data
public class LoanCreationRequest {

    @ExistingId(entityClass = Pledge.class, message = "ID does not exist among pledges")
    private Long pledgeId;

    @NotNull
    private BigDecimal loanAmount;

    @NotNull
    private LoanTerm term;
}
