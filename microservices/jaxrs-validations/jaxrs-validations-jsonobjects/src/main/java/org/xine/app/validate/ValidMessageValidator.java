package org.xine.app.validate;

import javax.json.JsonObject;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidMessageValidator implements ConstraintValidator<ValidMessage, javax.json.JsonObject> {

    private ValidMessage annotation;

    @Override
    public void initialize(ValidMessage constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(JsonObject value, ConstraintValidatorContext context) {
        // just a simple example validation, for example the must have a
        // string 'duke'
        // return value.getString("message").contains("duke");
        return value.getString("message").contains(this.annotation.expected());
    }

}
