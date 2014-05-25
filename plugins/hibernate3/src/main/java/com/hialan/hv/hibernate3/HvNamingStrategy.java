package com.hialan.hv.hibernate3;

import org.hibernate.cfg.DefaultNamingStrategy;
import org.hibernate.cfg.NamingStrategy;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 23/4/13
 * Time: 4:42 PM
 */
public class HvNamingStrategy extends DefaultNamingStrategy {

    public static final NamingStrategy INSTANCE = new HvNamingStrategy();

    //todo Define classToTable rule
    @Override
    public String classToTableName(String className) {
        return super.classToTableName(className);
    }
}
