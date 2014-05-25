package com.hialan.hv.commons.annotation;

import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 2/5/13
 * Time: 1:49 PM
 */
@Retention(value = RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD, ElementType.TYPE})
public @interface Comment {
    String value() default "";
}
