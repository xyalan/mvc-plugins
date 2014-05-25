package com.hialan.hv.hibernate3.search;

import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 15/5/13
 * Time: 5:03 PM
 */
public enum CriteriaRelation {

    eq("=") {
        @Override
        public void addCriteria(DetachedCriteria criteria, String name, Object value) {
            DetachedCriteriaUtils.eq(criteria, name, value);
        }
    },
    lt("<") {
        @Override
        public void addCriteria(DetachedCriteria criteria, String name, Object value) {
            DetachedCriteriaUtils.lt(criteria, name, value);
        }
    },
    le("<=") {
        @Override
        public void addCriteria(DetachedCriteria criteria, String name, Object value) {
            DetachedCriteriaUtils.le(criteria, name, value);
        }
    },
    gt(">") {
        @Override
        public void addCriteria(DetachedCriteria criteria, String name, Object value) {
            DetachedCriteriaUtils.gt(criteria, name, value);
        }
    },
    ge(">=") {
        @Override
        public void addCriteria(DetachedCriteria criteria, String name, Object value) {
            DetachedCriteriaUtils.ge(criteria, name, value);
        }
    },
    like("like") {
        @Override
        public void addCriteria(DetachedCriteria criteria, String name, Object value) {
            DetachedCriteriaUtils.like(criteria, name, value);
        }
    },
    iLike("iLike") {
        @Override
        public void addCriteria(DetachedCriteria criteria, String name, Object value) {
            DetachedCriteriaUtils.ilike(criteria, name, value);
        }
    },
    in("in") {
        @Override
        public void addCriteria(DetachedCriteria criteria, String name, Object value) {
            DetachedCriteriaUtils.in(criteria, name, (List) value);
        }
    },
    between("between") {
        @Override
        public void addCriteria(DetachedCriteria criteria, String name, Object value) {
            DetachedCriteriaUtils.between(criteria, name, (List) value);
        }
    };

    private String exp;

    private CriteriaRelation(String exp) {
        this.exp = exp;
    }

    public String getExp() {
        return exp;
    }

    public static CriteriaRelation valueOfExp(String exp) {
        for (CriteriaRelation relation : CriteriaRelation.values()) {
            if (StringUtils.equalsIgnoreCase(exp, relation.getExp())) {
                return relation;
            }
        }
        return null;
    }

    public abstract void addCriteria(DetachedCriteria criteria, String name, Object value);
}
