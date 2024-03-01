package us.telran.pawnshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.telran.pawnshop.entity.Loan;
import us.telran.pawnshop.validation.ExistingId;

import java.math.BigDecimal;

@Data
public class LoanOrderRequest {

    @ExistingId(entityClass = Loan.class, message = "ID does not exist among loans")
    private Long loanId;

    @NotNull
    private BigDecimal orderAmount;
}
