package restdocs.lib;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
public @interface CToDesc {
     String name() default "";
     String Type() default "";
     boolean isOptional() default false;
     String value() default "";
     String[] desc() default {};
     String[] constraints() default {};
}
