package com.hialan.hv.thrift.client;

import com.hialan.hv.thrift.ThriftHelper;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.UndeclaredThrowableException;
import java.net.ConnectException;
import java.util.UUID;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.StringUtils;
import org.apache.thrift.TApplicationException;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.THttpClient;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.remoting.RemoteConnectFailureException;
import org.springframework.remoting.RemoteProxyFailureException;
import org.springframework.remoting.support.UrlBasedRemoteAccessor;
import org.springframework.util.Assert;
import org.springframework.util.ReflectionUtils;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 25/6/13
 *         Time: 5:48 PM
 */
public abstract class ThriftClientInterceptor<T> extends UrlBasedRemoteAccessor implements MethodInterceptor {

    protected TProtocolFactory protocolFactory;

    protected Object thriftProxy;

    private boolean withTaskIdField = true;

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        prepare();
    }

    private void prepare() {
        Class<T> serviceInterface = getIFaceType();
        if (serviceInterface == null) {
            throw new IllegalArgumentException("property 'serviceInterface' is required.");
        }
        if (protocolFactory == null) {
            throw new IllegalArgumentException("property 'protocolFactory' is required.");
        }
        this.thriftProxy = new ProxyFactory(serviceInterface, this).getProxy(getBeanClassLoader());
    }

    public Object invoke(MethodInvocation invocation) throws Throwable {
        if (this.thriftProxy == null) {
            throw new IllegalStateException("ThriftClientInterceptor is not properly initialized - " +
                    "invoke 'prepare' before attempting any operations");
        }

        ClassLoader originalClassLoader = overrideThreadContextClassLoader();

        Method method = invocation.getMethod();
        Object[] args = invocation.getArguments();
        String name = method.getName();
        int argLen = args.length;
        if (argLen == 0) {
            if ("toString".equals(name)) {
                return "Thrift proxy for service URL [" + getServiceUrl() + "]";
            } else if ("hashCode".equals(name)) {
                return getServiceUrl().hashCode();
            }
        } else if (argLen == 1 && "equals".equals(name)) {
            Object arg0 = args[0];
            return getServiceUrl().equals(arg0);
        }

        Object client = ThriftHelper.buildClient(getServiceInterface(), getProtocolFactory().getProtocol(getTransport()));
        Assert.notNull(client, "the Thrift RPC client was not correctly created. Aborting.");

        fillTaskId(args[0]);

        try {
            return method.invoke(client, args);
        } catch (InvocationTargetException ex) {

            Throwable targetEx = ex.getTargetException();

            if (targetEx instanceof InvocationTargetException) {
                targetEx = ((InvocationTargetException) targetEx).getTargetException();
            }
            if (targetEx instanceof ConnectException) {
                throw convertThriftAccessException(targetEx);
            } else if (targetEx instanceof UndeclaredThrowableException) {
                UndeclaredThrowableException utex = (UndeclaredThrowableException) targetEx;
                throw convertThriftAccessException(utex.getUndeclaredThrowable());
            } else if (targetEx instanceof TApplicationException && ((TApplicationException) targetEx).getType() == TApplicationException.MISSING_RESULT) {
                return null;
            } else {
                throw targetEx;
            }
        } catch (Throwable ex) {
            throw new RemoteProxyFailureException(
                    "Failed to invoke Thrift proxy for remote service [" + getServiceUrl() + "]", ex);
        } finally {
            resetThreadContextClassLoader(originalClassLoader);
        }
    }

    private void fillTaskId(Object arg) {
        if (!isWithTaskIdField()) {
            return;
        }
        Field taskIdField = ReflectionUtils.findField(arg.getClass(), "taskId");
        if (taskIdField != null) {
            String taskId = (String) ReflectionUtils.invokeMethod(ReflectionUtils.findMethod(arg.getClass(), "getTaskId"), arg);
            if (StringUtils.isEmpty(taskId)) {
                ReflectionUtils.setField(taskIdField, arg, UUID.randomUUID().toString());
            }
        }
    }

    @SuppressWarnings("unchecked")
    public T getIFace() {
        return (T) getThriftProxy();
    }

    @SuppressWarnings("unchecked")
    public Class<T> getIFaceType() {
        return (Class<T>) getServiceInterface();
    }

    public Object getThriftProxy() {
        return thriftProxy;
    }

    public TProtocolFactory getProtocolFactory() {
        return protocolFactory;
    }

    public void setProtocolFactory(TProtocolFactory protocolFactory) {
        this.protocolFactory = protocolFactory;
    }

    public void setWithTaskIdField(boolean withTaskIdField) {
        this.withTaskIdField = withTaskIdField;
    }

    public boolean isWithTaskIdField() {
        return withTaskIdField;
    }

    protected TTransport getTransport() throws TTransportException {
        return new THttpClient(getServiceUrl());
    }

    /**
     * Convert the given Thrift access exception to an appropriate
     * Spring RemoteAccessException.
     *
     * @param ex the exception to convert
     * @return the RemoteAccessException to throw
     */
    protected RemoteAccessException convertThriftAccessException(Throwable ex) {
        if (ex instanceof ConnectException) {
            return new RemoteConnectFailureException(
                    "Cannot connect to Thrift remote service at [" + getServiceUrl() + "]", ex);
        } else {
            return new RemoteAccessException(
                    "Cannot access Thrift remote service at [" + getServiceUrl() + "]", ex);
        }
    }
}
