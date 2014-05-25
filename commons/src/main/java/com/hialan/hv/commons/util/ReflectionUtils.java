package com.hialan.hv.commons.util;


import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 18/4/13
 * Time: 12:53 PM
 */
public abstract class ReflectionUtils extends org.springframework.util.ReflectionUtils {

    public static <T> Class<T> getSuperClassGenericType(Class clazz) {
        return getSuperClassGenericType(clazz.getGenericSuperclass());
    }

    public static <T> Class<T> getClassGenericType(Class clazz) {
        return getClassGenericType(clazz, 0);
    }

    public static <T> Class<T> getClassGenericType(Class clazz, int idx) {
        return getClassGenericType(clazz.getGenericInterfaces()[0], idx);
    }

    public static <T> Class<T> getSuperClassGenericType(Type type) {
        return getSuperClassGenericType(type, 0);
    }

    public static <T> Class<T> getSuperClassGenericType(Type type, int idx) {
        return getClassGenericType(type, idx);
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getClassGenericType(Type type, int idx) {
        if (type instanceof ParameterizedType) {
            return (Class) ((ParameterizedType) type).getActualTypeArguments()[idx];
        } else if (type instanceof Class) {
            return getSuperClassGenericType((Class) type);
        } else {
            throw new RuntimeException(type.getClass().getName() + " should be parameterized!");
        }
    }

    public static Object invokeGetterMethod(Object target, String propertyName) {
        String getterMethodName = "get" + StringUtils.capitalize(propertyName);
        return invokeMethod(findMethod(target.getClass(), getterMethodName), target);
    }

    public static Object invokeSetterMethod(Object target, String propertyName, Class<?> parameterType, Object value) {
        String setterMethodName = "set" + StringUtils.capitalize(propertyName);
        return invokeMethod(findMethod(target.getClass(), setterMethodName, parameterType), target, value);
    }


}