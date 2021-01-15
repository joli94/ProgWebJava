package com.home.dogs.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target(TYPE)
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValidCodeValidator.class)
public @interface ValidCode {

    String message() default "Invalid request";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
