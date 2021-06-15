package org.onecell.common.validation.geometry;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = WKTConstraintValidator.class)
@Target({ElementType.METHOD,ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface WKT {
    WKTType type() default WKTType.POINT;

    String message() default "{org.one.lib.validation.wkt.message}"; // 기본적으로는 ValidationMessages.properties 에서 properties를 검색한다.

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
