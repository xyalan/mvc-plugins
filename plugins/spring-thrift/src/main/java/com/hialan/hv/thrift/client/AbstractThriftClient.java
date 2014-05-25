/**
 * Copyright (c) 2002-2013, Derbysoft (www.hialan.com). All Rights Reserved.
 */
package com.hialan.hv.thrift.client;

import java.util.Map;
import org.apache.commons.collections.MapUtils;
import org.apache.thrift.TException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 28/6/13
 *         Time: 4:29 PM
 */
public abstract class AbstractThriftClient<F> implements ApplicationContextAware {

    private ApplicationContext ctx;

    public ApplicationContext getApplicationContext() {
        return this.ctx;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        this.ctx = applicationContext;
    }

    public abstract String getHttpProxyFactoryName();

    @SuppressWarnings("unchecked")
    public F getThriftServiceProxy() throws TException {
        Map<String, ThriftHttpProxyFactoryBean> serviceProxies = ctx.getBeansOfType(ThriftHttpProxyFactoryBean.class);
        Object object = MapUtils.getObject(serviceProxies, "&" + getHttpProxyFactoryName());
        if (object != null) {
            return (F) ((ThriftHttpProxyFactoryBean) object).getIFace();
        }
        throw new TException("Bean with name[" + getHttpProxyFactoryName() + "] cant not been found!");
    }
}