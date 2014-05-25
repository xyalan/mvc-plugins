package com.hialan.hv.hibernate3;

import com.hialan.hv.commons.lang.BusinessLogType;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 17/4/13
 * Time: 5:59 PM
 */
public class BusinessLogQueueHandler implements BusinessLogHandler {

    @Override
    public void handle(AbstractPersistence persistence, BusinessLogType type) {
        System.out.println(type + "-> " + persistence.getMetaData());
    }
}
