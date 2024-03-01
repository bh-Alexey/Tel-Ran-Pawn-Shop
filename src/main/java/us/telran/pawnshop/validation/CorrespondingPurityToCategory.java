package us.telran.pawnshop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MetalPurityValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface CorrespondingPurityToCategory {
    String message() default "Invalid metal purity for the selected category";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}