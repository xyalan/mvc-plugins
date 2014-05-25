package com.hialan.hv.hibernate3;

import org.hibernate.criterion.DetachedCriteria;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 18/4/13
 * Time: 1:16 PM
 */
public interface AbstractCondition {

    public DetachedCriteria createCriteria();
}
