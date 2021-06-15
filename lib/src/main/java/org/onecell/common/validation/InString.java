package org.onecell.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = {InStringConstraintValidator.class,InStringConstraintValidatorInList.class})
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER,TYPE_USE,TYPE_PARAMETER })  //ANNOTATION_TYPE 이 있어야 ConstraintComposition 가 가능하다.
@Retention(RetentionPolicy.RUNTIME)
public  @interface InString {
    String message() default "{org.one.lib.validation.instring.message}"; // 기본적으로는 ValidationMessages.properties 에서 properties를 검색한다.

    String[] texts() ;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
