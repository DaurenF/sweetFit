package kz.sweet.fit.util;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.hibernate.validator.constraints.UniqueElements;

@Constraint(validatedBy = UniqueElementsValidator.class)
@UniqueElements(message = "The collection must contain unique elements")
public @interface UniqueElementsConstraint {
    String message() default "The collection must contain unique elements";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}