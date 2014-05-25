package com.hialan.hv.hibernate3.support;

import com.hialan.hv.commons.pagination.Paginate;
import com.hialan.hv.commons.pagination.Pagination;
import org.hibernate.*;
import org.hibernate.criterion.*;
import org.hibernate.impl.CriteriaImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.type.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 18/4/13
 * Time: 11:40 AM
 */
@Repository
public class HibernateDaoSupport {

    private static final int THREE = 3;
    public static final String ID = "id";

    @Autowired
    private SessionFactory sessionFactory;

    private boolean enableQueryCache = true;

    public HibernateDaoSupport(boolean enableQueryCache) {
        this.enableQueryCache = enableQueryCache;
    }

    public HibernateDaoSupport() {

    }

    public boolean isEnableQueryCache() {
        return enableQueryCache;
    }

    public void setEnableQueryCache(boolean enableQueryCache) {
        this.enableQueryCache = enableQueryCache;
    }

    @SuppressWarnings({"unchecked"})
    public <T> T load(Class<T> clazz, Serializable id) {
        return (T) getSession().get(clazz, id);
    }

    public void save(Object entity) {
        getSession().saveOrUpdate(entity);
    }

    public void save(Object entity, boolean isMerge) {
        Session session = getSession();
        if (isMerge) {
            session.merge(entity);
        } else {
            session.saveOrUpdate(entity);
        }
    }

    public void delete(Object entity) {
        getSession().delete(entity);
    }

    public List find(DetachedCriteria criteria) {
        final Criteria criteria1 = createCriteria(criteria);
        criteria1.setCacheable(true);
        return criteria1.list();
    }

    public <T> T load(String queryString, Object... values) {
        return (T) createQuery(queryString, values).uniqueResult();
    }

    public List find(String queryString, Object... values) {
        return createQuery(queryString, values).list();
    }

    public List find(Type type, String queryString, Object value) {
        return createQuery(type, queryString, value).list();
    }

    @SuppressWarnings({"unchecked"})
    public <T> T load(DetachedCriteria criteria) {
        return (T) createCriteria(criteria).uniqueResult();
    }

    public <T> T load(Class<T> clazz, String propertyName, Object propertyValue) {
        return load(clazz, new String[]{propertyName}, new Object[]{propertyValue});
    }

    @SuppressWarnings({"unchecked"})
    public <T> T load(Class<T> clazz, String[] propertyNames, Object[] propertyValues) {
        getSession().setFlushMode(FlushMode.MANUAL);
        T entity = (T) createQuery(createQueryString(clazz, propertyNames), propertyValues).uniqueResult();
        getSession().setFlushMode(FlushMode.AUTO);
        return entity;
    }


    private String createQueryString(Class<?> clazz, String[] propertyNames) {
        String hql = "from " + clazz.getSimpleName() + " _alias";
        StringBuilder buf = new StringBuilder();
        if (propertyNames != null) {
            for (String propertyName : propertyNames) {
                buf.append(" _alias.").append(propertyName).append("=? and");
            }
            hql = hql + " where " + buf.substring(0, buf.length() - THREE);
        }
        return hql;
    }

    public Query createQuery(String queryString, Object... values) {
        return createQuery(null, queryString, values);
    }

    public Query createQuery(Type type, String queryString, Object... values) {
        Query query = getSession().createQuery(queryString);
        if (values != null) {
            if (type != null) {
                query.setParameter(0, values[0], type);
            } else {
                for (int i = 0; i < values.length; i++) {
                    query.setParameter(i, values[i]);
                }
            }
        }
        openCache(query);
        return query;
    }


    private Criteria createCriteria(DetachedCriteria criteria) {
        final Criteria criteria1 = criteria.getExecutableCriteria(getSession()).setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        openCache(criteria1);
        return criteria1;
    }

    private Criteria createCriteria(DetachedCriteria criteria, ResultTransformer resultTransformer) {
        final Criteria criteria1 = criteria.getExecutableCriteria(getSession()).setResultTransformer(resultTransformer);
        openCache(criteria1);
        return criteria1;
    }

    @SuppressWarnings({"unchecked"})
    public <T> List<T> loadAll(final Class<T> entityClass) {
        Criteria criteria = getSession().createCriteria(entityClass);
        addOrder(criteria);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        openCache(criteria);
        return criteria.list();
    }

    private void openCache(Criteria criteria) {
        if (criteria != null && enableQueryCache) {
            criteria.setCacheable(true);
        }
    }

    private void openCache(Query query) {
        if (query != null && enableQueryCache) {
            query.setCacheable(true);
        }
    }

    private void addOrder(Criteria criteria) {
        if (null != criteria) {
            criteria.addOrder(orderById());
        }
    }

    private void addOrder(DetachedCriteria criteria) {
        if (null != criteria) {
            criteria.addOrder(orderById());
        }
    }

    private Order orderById() {
        return Order.desc(ID);
    }

    protected Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    public Paginate paginate(DetachedCriteria detachedCriteria, Paginate paginate) {
        sort(detachedCriteria, paginate);
        paginate.setTotalCount(count(detachedCriteria));
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        criteria.setFirstResult(paginate.getFirstResult());
        criteria.setMaxResults(paginate.getMaxResults());
        criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
        List results = criteria.list();
        paginate.setObjects(results);
        return paginate;
    }

    public Pagination pagination(DetachedCriteria detachedCriteria, Pagination pagination) {
        int count = count(detachedCriteria);
        pagination.setTotal(count);
        if (count > 0) {
            sort(detachedCriteria, pagination);
            Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
            criteria.setFirstResult(pagination.getStartRow());
            criteria.setMaxResults(pagination.getPageSize());
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            List results = criteria.list();
            pagination.setList(results);
        }
        return pagination;
    }

    private void sort(DetachedCriteria detachedCriteria, Pagination pagination) {
        if (!pagination.isDefineSort()) {
            detachedCriteria.addOrder(orderById());
            return;
        }
        String sortField = pagination.getSortField();
        if (pagination.isAsc()) {
            detachedCriteria.addOrder(Order.desc(sortField));
        } else{
            detachedCriteria.addOrder(Order.asc(sortField));
        }
    }

    public Paginate paginate(String hql, Paginate paginate, Object... value) {
        Query query = createQuery(hql, value);
        query.setMaxResults(paginate.getMaxResults());
        query.setFirstResult(paginate.getFirstResult());
        List results = query.list();
        paginate.setObjects(results);
        return paginate;
    }

    public int count(DetachedCriteria detachedCriteria) {
        Criteria criteria = detachedCriteria.getExecutableCriteria(getSession());
        openCache(criteria);
        Projection originalProjection = ((CriteriaImpl) criteria).getProjection();
        Object count = criteria.setProjection(Projections.countDistinct(ID)).uniqueResult();
        detachedCriteria.setProjection(originalProjection);
        return ((Number) count).intValue();
    }

    public List findWithProjection(DetachedCriteria detachedCriteria) {
        final Criteria criteria = createCriteria(detachedCriteria, CriteriaSpecification.PROJECTION);
        openCache(criteria);
        return criteria.list();
    }

    public final void flush() {
        getSession().flush();
    }

    public final void clear() {
        getSession().clear();
    }

    public final void executeSql(String sql, Object... values) {
        createQuery(sql, values).executeUpdate();
    }

    public int count(String sql, Object... value) {
        Object count = createQuery(sql
                , value).uniqueResult();
        return ((Number) count).intValue();
    }

    private void sort(DetachedCriteria detachedCriteria, Paginate paginate) {
        if (!paginate.isDefineSort()) {
            detachedCriteria.addOrder(orderById());
            return;
        }
        if ("desc".equalsIgnoreCase(paginate.getSortDirection())) {
            detachedCriteria.addOrder(Order.desc(paginate.getSortField()));
        } else if ("asc".equalsIgnoreCase(paginate.getSortDirection())) {
            detachedCriteria.addOrder(Order.asc(paginate.getSortField()));
        }
    }
}
