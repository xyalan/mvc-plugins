package com.hialan.hv.struts2.converter;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts2.util.StrutsTypeConverter;

import java.util.Map;

/*EnumConvert.java
 *Author:Alan Yang(alanlhy@hialan.com)
 *Create Date 2013-04-27 18:46:46
 */
public class EnumConvert extends StrutsTypeConverter {
    @Override
    @SuppressWarnings("unchecked")
    public Object convertFromString(Map context, String[] values, Class toClass) {
        String value;
        if (ArrayUtils.isNotEmpty(values)) {
            value = values[0];
            return Enum.valueOf(toClass, value);
        }
        return null;
    }

    @Override
    public String convertToString(Map context, Object o) {
        return o.toString();
    }
}
