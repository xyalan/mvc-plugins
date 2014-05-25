package com.hialan.hv.hibernate3.search;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.*;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 28/4/13
 * Time: 4:29 PM
 */
public abstract class DetachedCriteriaUtils {

    public static void eq(DetachedCriteria detachedCriteria, String property, Object value) {
        if (value == null) {
            return;
        }
        if (String.class.isInstance(value) && StringUtils.isBlank((String) value)) {
            return;
        }
        if (String.class.isInstance(value)) {
            detachedCriteria.add(Restrictions.eq(property, StringUtils.trim((String) value)));
        } else {
            detachedCriteria.add(Restrictions.eq(property, value));
        }
    }

    public static void notEq(DetachedCriteria detachedCriteria, String property, Object value) {
        if (value == null) {
            return;
        }
        if (String.class.isInstance(value) && StringUtils.isBlank((String) value)) {
            return;
        }
        if (String.class.isInstance(value)) {
            detachedCriteria.add(Restrictions.not(Restrictions.eq(property, StringUtils.trim((String) value))));
        } else {
            detachedCriteria.add(Restrictions.not(Restrictions.eq(property, value)));
        }
    }

    public static void ilike(DetachedCriteria detachedCriteria, String property, Object value) {
        if (value == null) {
            return;
        }
        if (String.class.isInstance(value) && StringUtils.isBlank((String) value)) {
            return;
        }
        if (String.class.isInstance(value)) {
            detachedCriteria.add(Restrictions.ilike(property, StringUtils.trim((String) value), MatchMode.ANYWHERE));
        } else {
            detachedCriteria.add(Restrictions.ilike(property, value));
        }
    }

    public static void like(DetachedCriteria detachedCriteria, String property, Object value) {
        if (value == null) {
            return;
        }
        if (String.class.isInstance(value) && StringUtils.isBlank((String) value)) {
            return;
        }
        if (String.class.isInstance(value)) {
            detachedCriteria.add(Restrictions.like(property, StringUtils.trim((String) value), MatchMode.END));
        } else {
            detachedCriteria.add(Restrictions.like(property, value));
        }
    }

    public static void notIlike(DetachedCriteria detachedCriteria, String property, Object value) {
        if (value == null) {
            return;
        }
        if (String.class.isInstance(value) && StringUtils.isBlank((String) value)) {
            return;
        }
        if (String.class.isInstance(value)) {
            detachedCriteria.add(Restrictions.not(Restrictions.ilike(property, StringUtils.trim((String) value), MatchMode.ANYWHERE)));
        } else {
            detachedCriteria.add(Restrictions.not(Restrictions.like(property, value)));
        }
    }

    public static void le(DetachedCriteria detachedCriteria, String property, Object value) {
        if (value == null) {
            return;
        }

        if (String.class.isInstance(value) && StringUtils.isBlank((String) value)) {
            return;
        }
        if (String.class.isInstance(value)) {
            detachedCriteria.add(Restrictions.le(property, StringUtils.trim((String) value)));
        } else {
            detachedCriteria.add(Restrictions.le(property, value));
        }
    }

    public static void lt(DetachedCriteria detachedCriteria, String property, Object value) {
        if (value == null) {
            return;
        }

        if (String.class.isInstance(value) && StringUtils.isBlank((String) value)) {
            return;
        }
        if (String.class.isInstance(value)) {
            detachedCriteria.add(Restrictions.lt(property, StringUtils.trim((String) value)));
        } else {
            detachedCriteria.add(Restrictions.lt(property, value));
        }
    }

    public static void ge(DetachedCriteria detachedCriteria, String property, Object value) {
        if (value == null) {
            return;
        }
        if (String.class.isInstance(value) && StringUtils.isBlank((String) value)) {
            return;
        }
        if (String.class.isInstance(value)) {
            detachedCriteria.add(Restrictions.ge(property, StringUtils.trim((String) value)));
        } else {
            detachedCriteria.add(Restrictions.ge(property, value));
        }
    }

    public static void gt(DetachedCriteria detachedCriteria, String property, Object value) {
        if (value == null) {
            return;
        }
        if (String.class.isInstance(value) && StringUtils.isBlank((String) value)) {
            return;
        }
        if (String.class.isInstance(value)) {
            detachedCriteria.add(Restrictions.gt(property, StringUtils.trim((String) value)));
        } else {
            detachedCriteria.add(Restrictions.gt(property, value));
        }
    }

    public static void in(DetachedCriteria detachedCriteria, String property, Collection value) {
        if (CollectionUtils.isEmpty(value)) {
            return;
        }
        detachedCriteria.add(Restrictions.in(property, value));
    }

    public static void between(DetachedCriteria detachedCriteria, String property, Collection value) {
        if (CollectionUtils.isEmpty(value)) {
            return;
        }
        if (CollectionUtils.size(value) != 2) {
            return;
        }
        detachedCriteria.add(Restrictions.between(property,
                CollectionUtils.get(value, 0), CollectionUtils.get(value, 1)));
    }

    public static void addOrder(DetachedCriteria criteria, boolean isDesc, String property) {
        if (null != criteria) {
            criteria.addOrder(isDesc ? Order.desc(property) : Order.asc(property));
        }
    }

    public static void notIlike(DetachedCriteria detachedCriteria, MatchMode iLikeMode, String property, Object value) {
        if (value == null) {
            return;
        }
        if (String.class.isInstance(value) && StringUtils.isBlank((String) value)) {
            return;
        }
        if (String.class.isInstance(value)) {
            detachedCriteria.add(Restrictions.not(Restrictions.ilike(property, StringUtils.trim((String) value), iLikeMode)));
        } else {
            detachedCriteria.add(Restrictions.not(Restrictions.like(property, value)));
        }
    }

    public static DetachedCriteria sum(DetachedCriteria detachedCriteria, ProjectionList projectionList, String... sumProps) {

        if (sumProps != null && sumProps.length > 0) {

            for (String sumProp : sumProps) {

                projectionList.add(Projections.sum(sumProp));
            }
        }

        return detachedCriteria;
    }

    public static DetachedCriteria count(DetachedCriteria detachedCriteria, ProjectionList projectionList, String... countProps) {

        if (countProps != null && countProps.length > 0) {

            for (String countProp : countProps) {

                projectionList.add(Projections.count(countProp));
            }
        }

        return detachedCriteria;
    }

    public static DetachedCriteria groupBy(DetachedCriteria detachedCriteria, ProjectionList projectionList, String... groupByProps) {

        if (groupByProps != null && groupByProps.length > 0) {

            for (String groupByProp : groupByProps) {

                projectionList.add(Projections.groupProperty(groupByProp));
            }
        }
        detachedCriteria.setProjection(projectionList);

        return detachedCriteria;
    }

    public static DetachedCriteria or(DetachedCriteria detachedCriteria, Criterion first, Criterion second) {
        detachedCriteria.add(Restrictions.or(first, second));
        return detachedCriteria;
    }

    public static DetachedCriteria or(DetachedCriteria detachedCriteria, List<Criterion> centurions) {
        if (centurions != null) {
            Disjunction disjunction = Restrictions.disjunction();
            for (Criterion criterion : centurions) {
                if (criterion != null) {
                    disjunction.add(criterion);
                }
            }
            if (disjunction.toString().length() > 2) {
                detachedCriteria.add(disjunction);
            }
        }
        return detachedCriteria;
    }

    public static DetachedCriteria isNull(DetachedCriteria detachedCriteria, String property) {

        detachedCriteria.add(Restrictions.isNull(property));

        return detachedCriteria;
    }

    public static DetachedCriteria timeInterval(DetachedCriteria detachedCriteria, String field, Date dateFrom, Date dateTo) {
        if (dateFrom != null && dateTo != null) {
            detachedCriteria.add(Restrictions.ge(field, dateFrom)).add(Restrictions.le(field, dateTo));
        } else if (dateFrom != null) {
            detachedCriteria.add(Restrictions.ge(field, dateFrom));
        } else if (dateTo != null) {
            detachedCriteria.add(Restrictions.le(field, dateTo));
        }
        return detachedCriteria;
    }
}
