/**
 * Copyright (c) 2002-2013, Derbysoft (www.hialan.com). All Rights Reserved.
 */
package com.hialan.hv.commons.lang;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;
import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 5/7/13
 *         Time: 5:12 PM
 */
public class StringHashMap extends HashMap<String, String> {

    public StringHashMap() {
        super();
    }

    public StringHashMap(Map<? extends String, ? extends String> m) {
        super(m);
    }

    public String put(String key, Object value) {
        String strValue;

        if (value == null) {
            strValue = null;
        } else if (value instanceof String) {
            strValue = (String) value;
        } else if (value instanceof Integer ||
                value instanceof Long ||
                value instanceof Float ||
                value instanceof Double ||
                value instanceof Boolean) {
            strValue = value.toString();
        } else if (value instanceof Date) {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            format.setTimeZone(TimeZone.getTimeZone("GMT+8"));
            strValue = format.format((Date) value);
        } else {
            strValue = value.toString();
        }
        return this.put(key, strValue);
    }

    public String put(String key, String value) {
        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(value)) {
            return super.put(key, value);
        } else {
            return null;
        }
    }
}
