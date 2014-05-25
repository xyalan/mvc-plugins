package com.hialan.hv.commons.lang;

import org.apache.commons.lang3.StringUtils;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 29/5/13
 *         Time: 4:50 PM
 */
public enum BreakfastType {

    NONE("不含早", "info.breakfast_type", 0),
    INCLUDED("含早", "", -2),
    SINGLE("单早", "", 1),
    TWICE("双早", "", 2);

    private String desc;
    private String key;
    private Integer value;

    private BreakfastType(String desc, String key, Integer value) {
        this.desc = desc;
        this.key = key;
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public String getKey() {
        return key;
    }

    public Integer getValue() {
        return value;
    }

    public static BreakfastType valueOfIgnoreCase(String name) {
        if (StringUtils.isEmpty(name)) {
            return null;
        }
        for (BreakfastType breakfastType : BreakfastType.values()) {
            if (StringUtils.equalsIgnoreCase(breakfastType.name(), name)) {
                return breakfastType;
            }
        }
        return null;
    }
}
