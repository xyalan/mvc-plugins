package com.hialan.hv.hibernate3;

import com.hialan.hv.commons.annotation.IgnoreBusinessLog;
import com.hialan.hv.commons.lang.BusinessLogType;
import java.io.Serializable;
import java.util.Date;
import org.hibernate.EmptyInterceptor;
import org.hibernate.type.Type;

public class PersistenceSupportHibernateInterceptor extends EmptyInterceptor {

    private static final String CREATE_TIME_FIELD = "createTime";

    private static final String UPDATE_TIME_FIELD = "updateTime";

    private BusinessLogHandler businessLogHandler;

    public PersistenceSupportHibernateInterceptor(BusinessLogHandler businessLogHandler) {
        this.businessLogHandler = businessLogHandler;
    }

    @Override
    public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types) {
        if (!AbstractPersistence.class.isInstance(entity)) {
            return;
        }
        AbstractPersistence abstractPersistence = (AbstractPersistence) entity;
        saveLog(abstractPersistence, BusinessLogType.DELETE);
    }

    @Override
    public boolean onSave(Object entity, Serializable id, Object[] state,
                          String[] propertyNames, Type[] types) {
        if (!AbstractPersistence.class.isInstance(entity)) {
            return false;
        }
        AbstractPersistence abstractPersistence = (AbstractPersistence) entity;
        boolean modified = false;
        Date currentDate = new Date();
        for (int i = 0; i < propertyNames.length; i++) {
            if (CREATE_TIME_FIELD.equals(propertyNames[i])) {
                state[i] = currentDate;
                abstractPersistence.setCreateTime(currentDate);
                modified = true;
            } else if (UPDATE_TIME_FIELD.equals(propertyNames[i])) {
                state[i] = currentDate;
                abstractPersistence.setUpdateTime(currentDate);
                modified = true;
            }
        }
        saveLog(abstractPersistence, BusinessLogType.ADD);
        return modified;
    }


    @Override
    public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState,
                                Object[] previousState, String[] propertyNames, Type[] types) {

        if (!AbstractPersistence.class.isInstance(entity)) {
            return false;
        }
        AbstractPersistence support = (AbstractPersistence) entity;
        boolean modified = false;
        Date currentDate = new Date();
        for (int i = 0; i < propertyNames.length; i++) {
            if (UPDATE_TIME_FIELD.equals(propertyNames[i])) {
                currentState[i] = currentDate;
                support.setUpdateTime(currentDate);
                modified = true;
                break;
            }
        }
        saveLog(support, BusinessLogType.UPDATE);
        return modified;
    }

    private void saveLog(AbstractPersistence object, BusinessLogType type) {
        if (object.getClass().isAnnotationPresent(IgnoreBusinessLog.class)) {
            return;
        }
        businessLogHandler.handle(object, type);
    }

    public BusinessLogHandler getBusinessLogHandler() {
        return businessLogHandler;
    }

    public void setBusinessLogHandler(BusinessLogHandler businessLogHandler) {
        this.businessLogHandler = businessLogHandler;
    }
}
