package com.hialan.hv.hibernate3;

import com.hialan.hv.commons.lang.BusinessLogType;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 17/4/13
 * Time: 5:47 PM
 */
public interface BusinessLogHandler extends Serializable {

    public void handle(AbstractPersistence persistence, BusinessLogType type);
}
