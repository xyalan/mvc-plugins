package com.hialan.hv.hibernate3.dialect;

import org.hibernate.dialect.MySQL5InnoDBDialect;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 2/5/13
 * Time: 5:42 PM
 */
public class MySQL5Utf8InnoDBDialect extends MySQL5InnoDBDialect {

    @Override
    public String getTableTypeString() {
        return super.getTableTypeString() + "  DEFAULT CHARSET=UTF8";
    }

    public boolean supportsCommentOn() {
        return true;
    }

}
