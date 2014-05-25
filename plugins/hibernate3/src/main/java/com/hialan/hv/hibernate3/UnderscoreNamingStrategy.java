package com.hialan.hv.hibernate3;

import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.cfg.NamingStrategy;
import org.hibernate.util.StringHelper;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 23/4/13
 * Time: 4:46 PM
 */
public class UnderscoreNamingStrategy extends DefaultNamingStrategy {

    public static final NamingStrategy INSTANCE = new UnderscoreNamingStrategy();

    protected UnderscoreNamingStrategy() {
    }

    public String classToTableName(String className) {
        return addUnderscores(StringHelper.unqualify(className));
    }

    public String propertyToColumnName(String propertyName) {
        return addUnderscores(StringHelper.unqualify(propertyName));
    }

    public String tableName(String tableName) {
        return tableName;
    }

    public String columnName(String columnName) {
        return columnName;
    }

    public String propertyToTableName(String className, String propertyName) {
        return classToTableName(className) + '_' + propertyToColumnName(propertyName);
    }

    private String addUnderscores(String name) {
        StringBuilder buf = new StringBuilder(name.replace('.', '_'));
        for (int i = 1; i < buf.length() - 1; i++) {
            if ('_' != buf.charAt(i - 1) && Character.isUpperCase(buf.charAt(i)) && !Character.isUpperCase(buf.charAt(i + 1))) {
                buf.insert(i++, '_');
            }
        }
        return buf.toString().toLowerCase();
    }
}
