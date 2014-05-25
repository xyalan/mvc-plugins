package com.hialan.hv.thrift.server;

import com.hialan.hv.thrift.ThriftHelper;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.thrift.TProcessor;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.protocol.TProtocolFactory;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TTransport;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.util.Assert;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 25/6/13
 *         Time: 5:12 PM
 */
public abstract class ThriftExporter extends RemoteExporter implements InitializingBean {

    public static final String CONTENT_TYPE_THRIFT = "application/x-thrift";

    protected TProcessor processor;

    protected TProtocolFactory inputProtocolFactory;

    protected TProtocolFactory outputProtocolFactory;

    @Override
    public void afterPropertiesSet() throws Exception {
        prepare();
    }

    /**
     * Initialize this exporter.
     */
    public void prepare() throws Exception {
        checkService();
        checkServiceInterface();
        checkProtocolFactory();
        this.processor = ThriftHelper.buildProcessor(getServiceInterface(), getService());
    }

    protected void checkProtocolFactory() throws IllegalArgumentException {
        if (inputProtocolFactory == null) {
            throw new IllegalArgumentException("Property 'inputProtocolFactory' is required");
        }
        if (outputProtocolFactory == null) {
            throw new IllegalArgumentException("Property 'outputProtocolFactory' is required");
        }
    }


    /**
     * Perform an invocation on the exported object.
     *
     * @param inputStream  the request stream
     * @param outputStream the response stream
     * @throws Throwable if invocation failed
     */
    public void invoke(InputStream inputStream, OutputStream outputStream) throws Throwable {
        Assert.notNull(this.processor, "Thrift exporter has not been initialized");
        doInvoke(this.processor, inputStream, outputStream);
    }

    protected void doInvoke(TProcessor processor, InputStream in, OutputStream out) throws Throwable {
        ClassLoader originalClassLoader = overrideThreadContextClassLoader();

        try {

            TTransport transport = new TIOStreamTransport(in, out);

            TProtocol inProtocol = inputProtocolFactory.getProtocol(transport);
            TProtocol outProtocol = outputProtocolFactory.getProtocol(transport);

            try {
                processor.process(inProtocol, outProtocol);
                out.flush();
            } finally {
                try {
                    in.close();
                } catch (IOException ex) {
                    // ignore
                }
                try {
                    out.close();
                } catch (IOException ex) {
                    // ignore
                }
            }

        } finally {
            resetThreadContextClassLoader(originalClassLoader);
        }
    }

    public TProtocolFactory getInputProtocolFactory() {
        return inputProtocolFactory;
    }

    public void setInputProtocolFactory(TProtocolFactory inputProtocolFactory) {
        this.inputProtocolFactory = inputProtocolFactory;
    }

    public TProtocolFactory getOutputProtocolFactory() {
        return outputProtocolFactory;
    }

    public void setOutputProtocolFactory(TProtocolFactory outputProtocolFactory) {
        this.outputProtocolFactory = outputProtocolFactory;
    }

}
