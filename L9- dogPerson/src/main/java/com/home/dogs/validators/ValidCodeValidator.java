package com.home.dogs.validators;

import com.home.dogs.dto.DogDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidCodeValidator implements ConstraintValidator<ValidCode, DogDto> {

    @Override
    public boolean isValid(DogDto value, ConstraintValidatorContext constraintValidatorContext) {
        if (value.getHasCode()) {
            return value.getCode() != null;
        }

        return true;
    }
}
