package us.telran.pawnshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.telran.pawnshop.entity.enums.LoanTerm;

@Data
public class PercentageCreationRequest {

    @NotNull
    private LoanTerm term;

}
