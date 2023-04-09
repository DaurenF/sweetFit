package kz.sweet.fit.util;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Collection;
import java.util.HashSet;

class UniqueElementsValidator implements ConstraintValidator<UniqueElementsConstraint, Collection<?>> {

    @Override
    public void initialize(UniqueElementsConstraint constraintAnnotation) {
    }

    @Override
    public boolean isValid(Collection<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        return value.size() == new HashSet<>(value).size();
    }
}