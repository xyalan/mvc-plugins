package com.hialan.hv.commons.util;

import java.util.HashMap;
import java.util.Map;
import ognl.ClassResolver;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 27/4/13
 * Time: 2:22 PM
 */
public class HvClassResolver implements ClassResolver {

    private Map<String, Class> classes = new HashMap<String, Class>(101);

    @Override
    public Class classForName(String className, Map context)
            throws ClassNotFoundException {
        Class result;
        if ((result = classes.get(className)) == null) {
            try {
                result = Class.forName(className);
            } catch (ClassNotFoundException ex) {
                try {
                    result = Class.forName(className);
                } catch (ClassNotFoundException e) {
                    if (className.indexOf('.') == -1) {
                        result = Class.forName("java.lang." + className);
                        classes.put("java.lang." + className, result);
                    }
                }
            }
            classes.put(className, result);
        }
        return result;
    }

}
