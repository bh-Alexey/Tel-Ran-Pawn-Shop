package us.telran.pawnshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import us.telran.pawnshop.dto.TransferRequest;

public class UniqueBranchPairValidator implements ConstraintValidator<UniqueBranchPair, TransferRequest> {

    @Override
    public void initialize(UniqueBranchPair constraintAnnotation) {
    }

    @Override
    public boolean isValid(TransferRequest dto, ConstraintValidatorContext context) {
        if (dto.getFromBranchId() == null || dto.getToBranchId() == null) {
            return false;
        }
        return !dto.getFromBranchId().equals(dto.getToBranchId());
    }
}
