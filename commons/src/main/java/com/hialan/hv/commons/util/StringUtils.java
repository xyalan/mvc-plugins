package com.hialan.hv.commons.util;

import com.hialan.hv.commons.lang.GlobalConstant;
import com.thoughtworks.xstream.XStream;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collection;
import java.util.List;

/**
 * Auth: alanlhy@gmail.com
 * Date Time: 2012-08-20 18:20
 * description
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils {

//    public static String toJson(Object json) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        objectMapper.registerModule(new HibernateModule());
//        try {
//            return objectMapper.writeValueAsString(json);
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//        return null;
//    }

    public static String toXml(Object o) {
        XStream stream = new XStream();
        return stream.toXML(o);
    }

//    public static Map fromJsonToMap(String json) {
//        return fromJson(json, Map.class);
//    }

    public static Object fromXml(String o) {
        XStream stream = new XStream();
        return stream.fromXML(o);
    }

//    public static <T> T fromJson(String json, Class<T> t) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            return objectMapper.readValue(json, t);
//        } catch (IOException e) {
//            log.error(e.getMessage(), e);
//        }
//        return null;
//    }

    public static String generateReservationNumber() {
        return ReservationNumber.generate();
    }
//
    public static String joinComma(Collection hotelList) {
        return join(hotelList, GlobalConstant.COMMA);
    }

    public static String[] splitComma(String str) {
        return str.split(GlobalConstant.COMMA);
    }

    public static String joinVertical(Collection hotelList) {
        return join(hotelList, GlobalConstant.VERTICAL);
    }

    public static String[] splitVertical(String str) {
        return str.split("\\" + GlobalConstant.VERTICAL);
    }

    public static String[] splitBias(String str) {
        return str.split("\\" + GlobalConstant.BIAS);
    }

    public static String getLogStack(Throwable e) {
        StringWriter sw = new StringWriter();
        e.printStackTrace(new PrintWriter(sw, true));
        return sw.toString();
    }

    public static <T> T getFirst(List<T> list) {
        if (null == list || list.size() == 0) {
            return null;
        }
        return list.iterator().next();
    }



}
