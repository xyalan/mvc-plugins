package com.hialan.hv.struts2.converter;

import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;
import org.joda.time.LocalDate;

public class LocalDateConverter extends StrutsTypeConverter {

    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        String value = values[0];
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return new LocalDate(value);
    }


    @Override
    public String convertToString(Map context, Object o) {
        return o.toString();
    }
}
