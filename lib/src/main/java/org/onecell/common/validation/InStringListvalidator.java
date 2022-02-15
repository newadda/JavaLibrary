package org.onecell.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class InStringListvalidator implements ConstraintValidator<InStringList, String[]> {
    private String[] subset;

    @Override
    public void initialize(InStringList constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(String[] value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}
