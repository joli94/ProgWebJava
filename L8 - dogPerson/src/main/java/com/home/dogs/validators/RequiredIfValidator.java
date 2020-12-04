package com.home.dogs.validators;

import com.home.dogs.dto.DogDto;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class RequiredIfValidator implements ConstraintValidator<RequiredIf, DogDto> {

    @Override
    public boolean isValid(DogDto value, ConstraintValidatorContext constraintValidatorContext) {
        if(value.getHasOwner()) {
            return value.getOwner() != null;
        }

        return true;
    }
}
