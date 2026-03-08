package com.HoopStretchApi.validation.annotations;

import com.HoopStretchApi.validation.ValidationMessages;
import com.HoopStretchApi.validation.validators.MobilityAreaValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MobilityAreaValidator.class)
public @interface ValidMobilityAreas {
    String message() default ValidationMessages.MOBILITY_AREAS_VALIDATION_MESSAGE;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}