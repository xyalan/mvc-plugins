package com.hialan.hv.hibernate3.support;

import com.hialan.hv.commons.pagination.Paginate;
import com.hialan.hv.commons.pagination.Pagination;
import com.hialan.hv.commons.search.SearchBean;
import com.hialan.hv.commons.util.ReflectionUtils;
import com.hialan.hv.hibernate3.AbstractCondition;
import com.hialan.hv.hibernate3.search.CriteriaSearchFactory;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 18/4/13
 * Time: 11:38 AM
 */
@Transactional(readOnly = true)
public abstract class HibernateServiceSupport<T> {

    @Autowired
    protected HibernateDaoSupport hibernateDaoSupport;

    protected Class<T> clazz;

    @SuppressWarnings("unchecked")
    protected HibernateServiceSupport() {
        this.clazz = ReflectionUtils.getSuperClassGenericType(getClass());
    }

    public T load(Long id) {
        return id == null ? null : getHibernateDaoSupport().load(clazz, id);
    }

    public T load(String sql, Object... value) {
        return getHibernateDaoSupport().load(sql, value);
    }

    @Transactional(readOnly = false)
    public void save(T o) {
        getHibernateDaoSupport().save(o);
    }

    @Transactional(readOnly = false)
    public void saveWithMerge(T o) {
        getHibernateDaoSupport().save(o, true);
    }

    @Transactional(readOnly = false)
    public void delete(T o) {
        getHibernateDaoSupport().delete(o);
    }

    @SuppressWarnings({"unchecked"})
    public List<T> find(DetachedCriteria detachedCriteria) {
        return getHibernateDaoSupport().find(detachedCriteria);
    }

    @SuppressWarnings({"unchecked"})
    public List<T> find(String sql, Object... value) {
        return getHibernateDaoSupport().find(sql, value);
    }

    @SuppressWarnings({"unchecked"})
    public List<T> find(Type type, String sql, Object value) {
        return getHibernateDaoSupport().find(type, sql, value);
    }

    public List<T> findAll() {
        return getHibernateDaoSupport().loadAll(clazz);
    }

    @SuppressWarnings("unchecked")
    public List<Object> findWithProjection(DetachedCriteria detachedCriteria) {
        return getHibernateDaoSupport().findWithProjection(detachedCriteria);
    }

    public T load(DetachedCriteria detachedCriteria) {
        return (T) getHibernateDaoSupport().load(detachedCriteria);
    }

    public T load(String propertyName, Object propertyValue) {
        return getHibernateDaoSupport().load(clazz, propertyName, propertyValue);
    }

    public T load(String[] propertyNames, Object[] properties) {
        return getHibernateDaoSupport().load(clazz, propertyNames, properties);
    }


    public Paginate pagination(AbstractCondition condition, Paginate paginate) {
        if (condition == null) {
            return pagination(DetachedCriteria.forClass(clazz), paginate);
        }
        return pagination(condition.createCriteria(), paginate);
    }

    public Paginate pagination(DetachedCriteria criteria, Paginate paginate) {
        return getHibernateDaoSupport().paginate(criteria, paginate);
    }

    public Pagination pagination(DetachedCriteria criteria, Pagination pagination) {
        return getHibernateDaoSupport().pagination(criteria, pagination);
    }

    public Paginate pagination(SearchBean[] searchBeans, Paginate paginate) {
        return getHibernateDaoSupport().paginate(CriteriaSearchFactory.generateCriteria(clazz, searchBeans), paginate);
    }

    public Paginate pagination(List<SearchBean> searchBeans, Paginate paginate) {
        return getHibernateDaoSupport().paginate(CriteriaSearchFactory.generateCriteria(clazz, searchBeans), paginate);
    }

    public Paginate pagination(String hql, Paginate paginate, Object... value) {
        return getHibernateDaoSupport().paginate(hql, paginate, value);
    }

    public int count(DetachedCriteria criteria) {
        return getHibernateDaoSupport().count(criteria);
    }

    public int count(String sql, Object... value) {
        return getHibernateDaoSupport().count(sql, value);
    }

    public int count(AbstractCondition condition) {
        if (condition == null) {
            return count(DetachedCriteria.forClass(clazz));
        }
        return count(condition.createCriteria());
    }

    public HibernateDaoSupport getHibernateDaoSupport() {
        return hibernateDaoSupport;
    }

    @Transactional(readOnly = false)
    public void batchSave(List<T> objects) {
        int i = 0;
        for (T o : objects) {
            hibernateDaoSupport.save(o, true);
            if ((i++ % 5) == 0) {
                hibernateDaoSupport.flush();
                hibernateDaoSupport.clear();
            }
        }
    }

    @Transactional(readOnly = false)
    public void executeSql(String sql, Object... values) {
        getHibernateDaoSupport().executeSql(sql, values);
    }

    public boolean checkFiledIsUnique(String property, String value, Long execuldeId) {
        String field = StringUtils.trimToEmpty(property);
        String sql = "select count(p.id) from " + clazz.getSimpleName() + " p where p." + field + "=? ";
        if (execuldeId != null) {
            sql += " and p.id<> ? ";
            return count(sql, value, execuldeId) > 0;
        }
        return count(sql, value) > 0;
    }
}
