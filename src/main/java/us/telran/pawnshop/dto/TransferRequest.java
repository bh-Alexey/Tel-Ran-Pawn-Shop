package us.telran.pawnshop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import us.telran.pawnshop.entity.PawnBranch;
import us.telran.pawnshop.validation.ExistingId;
import us.telran.pawnshop.validation.UniqueBranchPair;

import java.math.BigDecimal;

@UniqueBranchPair
@Data
public class TransferRequest {

    @ExistingId(entityClass = PawnBranch.class, message = "ID does not exist among pawn branches")
    private Long fromBranchId;

    @ExistingId(entityClass = PawnBranch.class, message = "ID does not exist among pawn branches")
    private Long toBranchId;

    @NotNull
    private BigDecimal transferAmount;
}
