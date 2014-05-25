package com.hialan.hv.commons.lang;

import org.apache.commons.lang3.StringUtils;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 28/4/13
 * Time: 3:33 PM
 */
public enum Relation {
    eq("="),
    lt("<"),
    le("<="),
    gt(">"),
    ge(">="),
    like("like"),
    iLike("iLike"),
    in("in"),
    between("between");

    private String exp;

    private Relation(String exp) {
        this.exp = exp;
    }

    public String getExp() {
        return exp;
    }

    public static Relation valueOfExp(String exp) {
        for (Relation relation : Relation.values()) {
            if (StringUtils.equalsIgnoreCase(exp, relation.getExp())) {
                return relation;
            }
        }
        return null;
    }

}
