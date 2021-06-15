package org.onecell.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

public class InStringConstraintValidatorInList implements ConstraintValidator<InString, List<String>> {
    String[] matchingList;

    @Override
    public void initialize(InString constraintAnnotation) {
        matchingList = constraintAnnotation.texts();
    }

    @Override
    public boolean isValid(List<String> value, ConstraintValidatorContext context) {
        if(value==null)
        {
            return  true;
        }

        for (String data : value) {
            for (String item : matchingList) {
                if (data.equalsIgnoreCase(item)) {
                    continue;
                }
            }
            return false;
        }

        return true;
    }
}
