package us.telran.pawnshop.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.telran.pawnshop.entity.PledgeCategory;
import us.telran.pawnshop.entity.enums.MetalPurity;
import us.telran.pawnshop.entity.enums.PreciousMetal;
import us.telran.pawnshop.exception.NotValidDTOInputException;
import us.telran.pawnshop.repository.PledgeCategoryRepository;

import java.util.*;

@Component
@NoArgsConstructor
public class MetalPurityValidator implements ConstraintValidator<CorrespondingPurityToCategory, MetalPurityValidatable> {

    @Autowired
    private PledgeCategoryRepository pledgeCategoryRepository;
    private final Map<PreciousMetal, Set<MetalPurity>> metalPurityRules = initializeMetalPurityRules();


    private Map<PreciousMetal, Set<MetalPurity>> initializeMetalPurityRules() {

        Map<PreciousMetal, Set<MetalPurity>> rules = new EnumMap<>(PreciousMetal.class);

        rules.put(PreciousMetal.GOLD, EnumSet.of(MetalPurity.GOLD_375, MetalPurity.GOLD_585,
                                                            MetalPurity.GOLD_750, MetalPurity.GOLD_916,
                                                            MetalPurity.GOLD_999));

        rules.put(PreciousMetal.SILVER, EnumSet.of(MetalPurity.SILVER_800, MetalPurity.SILVER_925));

        return rules;

    }

    @Override
    public boolean isValid(MetalPurityValidatable dto, ConstraintValidatorContext context) {
        if (dto.getCategoryId() == null || dto.getPurity() == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Required fields empty or filled incorrectly")
                    .addConstraintViolation();
            return false;
        }

        Optional<PledgeCategory> categoryOptional = pledgeCategoryRepository.findById(dto.getCategoryId());
        if (categoryOptional.isEmpty()) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Required field is empty or invalid")
                    .addConstraintViolation();
            return false;
        }

        PreciousMetal preciousMetal;
        try {
            preciousMetal = categoryOptional.get().getCategoryName();
        } catch (IllegalArgumentException exception) {
            throw new NotValidDTOInputException("Required field is empty or invalid");
        }

        MetalPurity metalPurity;
        try {
            metalPurity = (dto.getPurity());
        } catch (IllegalArgumentException exception) {
            throw new NotValidDTOInputException("Invalid input: " + exception.getMessage());
        }

        Set<MetalPurity> allowedPurities = metalPurityRules.get(preciousMetal);
        if (allowedPurities == null || !allowedPurities.contains(metalPurity)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate("Invalid purity for the selected metal")
                    .addConstraintViolation();
            return false;
        }

        return true;
    }
}



