package us.telran.pawnshop.validation;

import us.telran.pawnshop.entity.enums.MetalPurity;

public interface MetalPurityValidatable {
    Long getCategoryId();
    MetalPurity getPurity();
}

