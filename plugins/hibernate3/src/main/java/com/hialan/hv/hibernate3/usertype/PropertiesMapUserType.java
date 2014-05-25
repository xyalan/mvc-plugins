package com.hialan.hv.hibernate3.usertype;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.collections.Transformer;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.HibernateException;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.usertype.ParameterizedType;
import org.hibernate.usertype.UserType;

/**
 * <dt>自定义类型的完全复制方法,构造返回对象</dt>
 * <p>
 * <li>当nullSafeGet方法调用之后，我们获得了自定义数据对象，在向用户返回自定义数据之前
 * deepCopy方法被调用，它将根据自定义数据对象构造一个完全拷贝，把拷贝返还给客户使用。</li>
 * <li>此时我们就得到了自定义数据对象的两个版本原始版本由hibernate维护，用作脏数据检查依据，
 * 复制版本由用户使用，hibernate将在
 * 脏数据检查过程中比较这两个版本的数据。
 * </li>
 * </p>
 * User: Alan Yang
 * Date: 8/5/13
 * Time: 11:02 AM
 */
public final class PropertiesMapUserType implements UserType, ParameterizedType {

    private static final Log logger = LogFactory.getLog(PropertiesMapUserType.class);

    private static final String VALUE_TYPE = "valueType";
    private static final String KEY_TYPE = "keyType";
    private static final String DATA_CHARSET = "utf-8";
    /* 有几个字段就有几个值*/
    private static final int[] SQL_TYPES = {StandardBasicTypes.TEXT.sqlType()};

    private Constructor<?> valueConstructor;
    private Constructor<?> keyConstructor;

    private Transformer keyTransformer;

    private Transformer valueTransformer;

    private void initTransFormers() {
        this.keyTransformer = new Transformer() {
            @Override
            public Object transform(Object k) {
                try {
                    return keyConstructor.newInstance(k);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                return k;
            }
        };
        this.valueTransformer = new Transformer() {
            @Override
            public Object transform(Object v) {
                try {
                    return valueConstructor.newInstance(v);
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
                return v;
            }
        };
    }

    public PropertiesMapUserType() {
    }

    public PropertiesMapUserType(String keyType, String valueType) {
        this.keyConstructor = getConstructor(keyType);
        this.valueConstructor = getConstructor(valueType);
    }

    /**
     * 告诉Hibernate在成生DDL时对列采用什么样的SQL语法
     * 修改类型对应的SQL类型
     * 使用TEXT
     */
    @Override
    public int[] sqlTypes() {
        return SQL_TYPES;
    }

    /**
     * Hibernate返回什么样的映射类型
     * 使用Map类型
     */
    @Override
    public Class<?> returnedClass() {
        return Map.class;
    }

    /**
     * 当从二级缓存中读取此类型数据时，此方法被调用
     */
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        if (logger.isDebugEnabled()) {
            logger.debug("assemble owner[" + ToStringBuilder.reflectionToString(owner) + "]");
        }
        return deepCopy(cached);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object deepCopy(Object value) throws HibernateException {
        return (value != null) ? new HashMap((Map) value) : null;
    }

    /**
     * 当把此类型数据写入二级缓存时，此方法被调用
     */
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return (Serializable) deepCopy(value);
    }

    /**
     * 自定义数据类型比对方法
     * 用作脏数据检查，x,y为两个副本
     */
    @Override
    public boolean equals(Object x, Object y) throws HibernateException {
        return (x == y) || (x != null && x.equals(y));
    }

    /**
     * 返回给定类型的hashCode
     */
    @Override
    public int hashCode(Object value) throws HibernateException {
        return value.hashCode();
    }

    /**
     * 读取数据转换为自定义类型返回
     * names包含了自定义类型的映射字段名称
     */
    @Override
    @SuppressWarnings({"deprecation", "unchecked"})
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        if (rs.wasNull()) {
            return null;
        }
        if (logger.isDebugEnabled()) {
            logger.debug("nullSafeGet owner[" + ToStringBuilder.reflectionToString(owner) + "]");
        }
        //取得字段名称并查询
        String text = StandardBasicTypes.TEXT.nullSafeGet(rs, names[0]);
        if (text == null) {
            return new HashMap();
        }
        Map result = string2Map(text);
        initTransFormers();
        return MapUtils.transformedMap(result, keyTransformer, valueTransformer);
    }

    /**
     * 表明这个类的实例在创建以后就不可以改变属性。Hibernate能为不可改变的类作一些性能优化
     */
    @Override
    public boolean isMutable() {
        return true;
    }


    /**
     * 数据保存时被调用
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        String text = null;
        if (value != null) {
            Map map = (Map) value;
            if (!map.isEmpty()) {
                text = map2String(map);
            }
        }
        StandardBasicTypes.TEXT.nullSafeSet(st, text, index);
    }

    private String map2String(Map map) {
        PropertiesConfiguration cfg = new PropertiesConfiguration();
        for (Object key : map.keySet()) {
            cfg.addProperty(key.toString(), map.get(key));
        }
        OutputStream os = new ByteArrayOutputStream();
        try {
            cfg.save(os, DATA_CHARSET);
        } catch (ConfigurationException e) {
            throw new IllegalStateException("store [" + map + "] to string failed", e);
        }
        return os.toString();
    }

    @SuppressWarnings("unchecked")
    private Map string2Map(String str) {
        PropertiesConfiguration cfg = new PropertiesConfiguration();
        try {
            cfg.load(new ByteArrayInputStream(str.getBytes(DATA_CHARSET)));
        } catch (Exception e) {
            throw new IllegalStateException("load properties failed");
        }
        Iterator keyIterator = cfg.getKeys();
        Map map = new TreeMap();
        while (keyIterator.hasNext()) {
            String key = (String) keyIterator.next();
            Object value = cfg.getProperty(key);
            map.put(key, value);
        }
        return map;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        if (logger.isDebugEnabled()) {
            logger.debug("replace owner[" + ToStringBuilder.reflectionToString(owner) + "]");
        }
        return deepCopy(original);
    }

    @Override
    public void setParameterValues(Properties properties) {
        String valueType = properties.getProperty(VALUE_TYPE);
        String keyType = properties.getProperty(KEY_TYPE);
        if (keyType == null || valueType == null) {
            throw new IllegalArgumentException("illegal params setting[" + properties + "]");
        }
        this.valueConstructor = getConstructor(valueType);
        this.keyConstructor = getConstructor(keyType);
    }

    private static Constructor<?> getConstructor(String clazzType) {
        try {
            return Class.forName(clazzType).getConstructor(String.class);
        } catch (Exception eStr) {
            logger.warn("get Constructor with String failed");
            try {
                Constructor<?> c = Class.forName(clazzType).getConstructor(Object.class);
                logger.warn("get Constructor with Object successful!");
                return c;
            } catch (Exception eObj) {
                throw new IllegalStateException("Constructor init failed!", eObj);
            }
        }
    }
}
