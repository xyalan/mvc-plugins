package com.hialan.hv.commons.util;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.StringWriter;

/**
 * Created by developer on 12/27/13.
 */
public class JaxbUtils {
    public static String marshal(Object data) throws JAXBException {
        JAXBContext jc = JAXBContext.newInstance(data.getClass());
        Marshaller m = jc.createMarshaller();
        StringWriter writer = new StringWriter();
        m.marshal(data, writer);
        return writer.toString();
    }
}
