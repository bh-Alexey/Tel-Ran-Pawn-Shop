package us.telran.pawnshop.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.*;


@Constraint(validatedBy = UniqueBranchPairValidator.class)
@Retention(RUNTIME)
@Target(TYPE)
public @interface UniqueBranchPair {

    String message() default "Departments must be different.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
