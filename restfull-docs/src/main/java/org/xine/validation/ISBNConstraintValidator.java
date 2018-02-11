package org.xine.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.EnumSet;

public class ISBNConstraintValidator implements ConstraintValidator<ISBN, String> {

    private EnumSet<ISBN.Type> types = EnumSet.noneOf(ISBN.Type.class);

    public void initialize(ISBN constraint) {
        ISBN.Type[] value = constraint.value();
        types.addAll(Arrays.asList(value));
    }

    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        for (ISBN.Type type : types) {
            if (type.getPattern().matcher(value).matches()) {
                return true;
            }
        }

        return false;
    }
}
