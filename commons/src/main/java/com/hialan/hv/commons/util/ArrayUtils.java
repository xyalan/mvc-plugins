package com.hialan.hv.commons.util;


import java.util.ArrayList;
import java.util.List;

public abstract class ArrayUtils {
    public static List<String> toListIgnoreEmptyAndNull(String[] array) {
        ArrayList<String> result = new ArrayList<String>();
        if (array != null) {
            String trimmed;
            for (String element : array) {
                trimmed = StringUtils.trimToNull(element);
                if (trimmed != null)
                    result.add(trimmed);
            }
        }
        return result;
    }
}
