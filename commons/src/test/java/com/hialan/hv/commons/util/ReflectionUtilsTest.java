package com.hialan.hv.commons.util;

import com.hialan.hv.commons.AbstractTest;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 24/4/13
 * Time: 2:09 PM
 */
public class ReflectionUtilsTest extends AbstractTest {

    @Test
    public void testGetSuperClassGenericType() throws Exception {
        Class type = ReflectionUtils.getSuperClassGenericType(ClassB.class);
        assertEquals(type, ClassC.class);
    }

    @Test
    public void testInvokeGetterMethod() throws Exception {
        Object o = ReflectionUtils.invokeGetterMethod(new ClassC("zhangsan"), "name");
        assertEquals("zhangsan", o);
    }

    public static class ClassC {

        private ClassCType classCType;

        ClassC(String name) {
            this.name = name;
        }

        private String name;

        public String getName() {
            return name;
        }

        public ClassCType getClassCType() {
            return classCType;
        }

        public void setClassCType(ClassCType classCType) {
            this.classCType = classCType;
        }
    }

    public static class ClassCType {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class ClassA<T> {

    }


    class ClassB extends ClassA<ClassC> {

    }

}


