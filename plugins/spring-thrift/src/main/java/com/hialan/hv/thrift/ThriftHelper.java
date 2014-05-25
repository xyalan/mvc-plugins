package com.hialan.hv.thrift;

import java.lang.reflect.Constructor;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocol;
import org.springframework.util.Assert;
import org.springframework.util.ClassUtils;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 25/6/13
 *         Time: 4:44 PM
 */
public abstract class ThriftHelper {

    public static String PROCESSOR_NAME = "$Processor";

    public static String CLIENT_NAME = "$Client";

    public static Class<?> getThriftServiceInnerClassOrNull(Class<?> thriftServiceClass, String match, boolean isInterface) {
        Class<?>[] declaredClasses = thriftServiceClass.getDeclaredClasses();
        for (Class<?> declaredClass : declaredClasses) {
            if (declaredClass.isInterface()) {
                if (isInterface && declaredClass.getName().contains(match)) {
                    return declaredClass;
                }
            } else {
                if (!isInterface && declaredClass.getName().contains(match)) {
                    return declaredClass;
                }
            }
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static TProcessor buildProcessor(Class<?> svcInterface, Object service) throws Exception {
        Class<TProcessor> processorClass = (Class<TProcessor>) getThriftServiceInnerClassOrNull(svcInterface.getEnclosingClass(), PROCESSOR_NAME, false);
        Assert.notNull(processorClass, "the processor class must not be null");
        Constructor<TProcessor> constructor = ClassUtils.getConstructorIfAvailable(processorClass, svcInterface);
        Assert.notNull(constructor);
        return constructor.newInstance(service);
    }

    public static Constructor<?> getClientConstructor(Class<?> svcInterface) throws Exception {
        Class<?> clientClass = getThriftServiceInnerClassOrNull(svcInterface.getEnclosingClass(), CLIENT_NAME, false);
        Assert.notNull(clientClass, "the client class must not be null");
        Constructor<?> constructor = ClassUtils.getConstructorIfAvailable(clientClass, TProtocol.class);
        Assert.notNull(constructor);
        return constructor;
    }

    @SuppressWarnings("unchecked")
    public static <T> T buildClient(Class<T> svcInterface, TProtocol protocol) throws Exception {
        return (T) getClientConstructor(svcInterface).newInstance(protocol);
    }
}
