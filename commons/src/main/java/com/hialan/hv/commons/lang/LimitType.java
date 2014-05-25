package com.hialan.hv.commons.lang;

import java.math.BigDecimal;

public enum LimitType {
    ADD(1, "add", "+"), SUBTRACT(2, "subtract", "-");

    private int id;
    private String code;
    private String title;

    LimitType(int id, String code, String title) {
        this.id = id;
        this.code = code;
        this.title = title;
    }

    public boolean isAdd() {
        return this == ADD;
    }

    public boolean isSubtract() {
        return this == SUBTRACT;
    }

    public BigDecimal getValue(BigDecimal value) {
        if (isSubtract())
            return value.negate();
        return value;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
