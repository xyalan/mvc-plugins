package com.hialan.hv.hibernate3.usertype;

import java.io.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import org.apache.commons.codec.binary.Hex;
import org.hibernate.HibernateException;
import org.hibernate.usertype.UserType;

/**
 * Object序列化/反序列化
 * 数据库中以hex字符串存储
 * User: Alan Yang
 * Date: 13/5/13
 * Time: 2:31 PM
 */
public class ObjectSerializeUserType implements UserType, Serializable {
    @Override
    public int[] sqlTypes() {
        return new int[]{Types.VARCHAR};
    }

    @Override
    public Class returnedClass() {
        return Object.class;
    }

    @Override
    public boolean equals(Object o, Object o1) throws HibernateException {
        return o == o1 || (o != null && o.equals(o1));

    }

    @Override
    public int hashCode(Object o) throws HibernateException {
        return o.hashCode();
    }


    /**
     * 从JDBC ResultSet读取数据,将其转换为自定义类型后返回
     * (此方法要求对克能出现null值进行处理)
     * names中包含了当前自定义类型的映射字段名称
     *
     * @param names the column names
     * @param owner the containing entity
     * @return Object
     * @throws HibernateException
     * @throws java.sql.SQLException
     */
    @Override
    public Object nullSafeGet(ResultSet rs, String[] names, Object owner) throws HibernateException, SQLException {
        if (rs.wasNull()) {
            return null;
        }
        String hexStr = rs.getString(names[0]);
        if (hexStr == null) {
            return new Object();
        }

        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new ByteArrayInputStream(Hex.decodeHex(hexStr.toCharArray())));
            return ois.readObject();
        } catch (Exception e) {
            throw new HibernateException(e);
        } finally {
            try {
                if (ois != null)
                    ois.close();
            } catch (IOException e) {
                //ignore
            }
        }
    }

    /**
     * 本方法将在Hibernate进行数据保存时被调用
     * 我们可以通过PreparedStatement将自定义数据写入到对应的数据库表字段
     */
    @Override
    public void nullSafeSet(PreparedStatement st, Object value, int index) throws HibernateException, SQLException {
        ObjectOutputStream oos = null;
        if (value == null) {
            st.setNull(index, Types.VARCHAR);
        } else {
            try {
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                oos = new ObjectOutputStream(bos);
                oos.writeObject(value);
                oos.close();

                byte[] objectBytes = bos.toByteArray();
                String hexStr = Hex.encodeHexString(objectBytes);

                st.setString(index, hexStr);
            } catch (Exception e) {
                throw new HibernateException(e);
            } finally {

                try {
                    if (oos != null) oos.close();
                } catch (IOException e) {
                    //ignore
                }
            }
        }
    }


    /**
     * 提供自定义类型的完全复制方法
     * 本方法将用构造返回对象
     * 当nullSafeGet方法调用之后，我们获得了自定义数据对象，在向用户返回自定义数据之前，
     * deepCopy方法将被调用，它将根据自定义数据对象构造一个完全拷贝，并将此拷贝返回给用户
     * 此时我们就得到了自定义数据对象的两个版本，第一个是从数据库读出的原始版本，其二是我们通过
     * deepCopy方法构造的复制版本，原始的版本将有Hibernate维护，复制版由用户使用。原始版本用作
     * 稍后的脏数据检查依据；Hibernate将在脏数据检查过程中将两个版本的数据进行对比（通过调用
     * equals方法），如果数据发生了变化（equals方法返回false），则执行对应的持久化操作
     *
     * @param o Object
     * @return Object
     * @throws HibernateException
     */
    @Override
    public Object deepCopy(Object o) throws HibernateException {
        if (o == null) return null;
        return o;
    }

    @Override
    public boolean isMutable() {
        return true;
    }

    /**
     * 当把此类型数据写入二级缓存时，此方法被调用
     */
    @Override
    public Serializable disassemble(Object value) throws HibernateException {
        return ((Serializable) value);
    }

    /**
     * 当从二级缓存中读取此类型数据时，此方法被调用
     */
    @Override
    public Object assemble(Serializable cached, Object owner) throws HibernateException {
        return cached;
    }

    @Override
    public Object replace(Object original, Object target, Object owner) throws HibernateException {
        return original;
    }

}
