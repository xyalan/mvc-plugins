package com.hialan.hv.thrift.client;

import org.springframework.beans.factory.FactoryBean;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 25/6/13
 *         Time: 5:50 PM
 */
public class ThriftHttpProxyFactoryBean<T> extends ThriftClientInterceptor<T> implements FactoryBean<Object> {

    private Object serviceProxy;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        this.serviceProxy = thriftProxy;
    }

    public Object getObject() {
        return this.serviceProxy;
    }

    public Class<?> getObjectType() {
        return getServiceInterface();
    }

    public boolean isSingleton() {
        return true;
    }
}
