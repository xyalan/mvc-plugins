/**
 * Copyright (c) 2002-2013, Derbysoft (www.hialan.com). All Rights Reserved.
 */
package com.hialan.hv.commons.lang;

/**
 * 类型转换统一接口
 *
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 10/7/13
 *         Time: 4:01 PM
 */
public abstract class Translator<S, D> {

    public abstract D doTranslate(S source);

    public D translate(S source) {
        if (source == null) {
            return null;
        }
        return doTranslate(source);
    }

}
