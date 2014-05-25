package com.hialan.hv.commons.pagination;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by IntelliJ IDEA.
 * Author: Alan Yang
 * E-mail:alanlhy@gmail.com
 * Date: 2011-6-22
 * Time: 20:44:59
 * description
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PaginationSupport {
}
