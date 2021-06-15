package org.onecell.common.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class InStringConstraintValidator  implements ConstraintValidator<InString, String> {
    String[] matchingList;

    @Override
    public void initialize(InString constraintAnnotation) {
        matchingList = constraintAnnotation.texts();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value==null)
        {
            return  true;
        }


        for(String item:matchingList)
        {
            if(item.equalsIgnoreCase(value))
            {
                return true;
            }
        }

        return false;
    }
}
