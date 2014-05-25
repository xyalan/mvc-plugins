package com.hialan.hv.commons.search;

import java.io.Serializable;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 9/4/13
 * Time: 4:41 PM
 */
public class SearchBean implements Serializable {
    /**
     * 字段名称
     */
    private String name;
    /**
     * 字段值
     */
    private String value;
    /**
     * 条件关系，包含：=, <, >, <=, >=, like, in
     */
    private String relation;

    public SearchBean() {
    }

    public SearchBean(String name, String value, String relation) {
        this.name = name;
        this.relation = relation;
        this.value = value;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("name", name)
                .append("value", value)
                .append("realtion", relation).toString();
    }

    @Override
    public boolean equals(Object obj) {
        return EqualsBuilder.reflectionEquals(this, obj);
    }

    @Override
    public final int hashCode() {
        return HashCodeBuilder.reflectionHashCode(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getRelation() {
        return relation;
    }

    public void setRelation(String relation) {
        this.relation = relation;
    }
}
