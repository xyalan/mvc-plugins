package com.hialan.hv.commons.lang;

import com.hialan.hv.commons.util.DateUtils;

import java.util.Date;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 20/5/13
 *         Time: 11:48 AM
 */
public enum WeekDay {
    SUNDAY(0, "星期日", "周日", "Sun"),
    MONDAY(1, "星期一", "周一", "Mon"),
    TUESDAY(2, "星期二", "周二", "Tue"),
    WEDNESDAY(3, "星期三", "周三", "Wed"),
    THURSDAY(4, "星期四", "周四", "Thu"),
    FRIDAY(5, "星期五", "周五", "Fri"),
    SATURDAY(6, "星期六", "周六", "Sat");
    private int value;
    private String chinese;
    private String zhShort;
    private String enShort;

    WeekDay(int value, String chinese, String zhShort, String enShort) {
        this.value = value;
        this.zhShort = zhShort;
        this.enShort = enShort;
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getZhShort() {
        return zhShort;
    }

    public void setZhShort(String zhShort) {
        this.zhShort = zhShort;
    }

    public String getEnShort() {
        return enShort;
    }

    public void setEnShort(String enShort) {
        this.enShort = enShort;
    }

    public static WeekDay fromValue(int value) {
        for (WeekDay weekday : values()) {
            if (weekday.getValue() == value) {
                return weekday;
            }
        }
        return null;
    }

    public static WeekDay fromDate(Date date) {
        if (DateUtils.isMonDay(date)) {
            return MONDAY;
        }
        if (DateUtils.isTuesday(date)) {
            return TUESDAY;
        }
        if (DateUtils.isWednesday(date)) {
            return WEDNESDAY;
        }
        if (DateUtils.isThursday(date)) {
            return THURSDAY;
        }
        if (DateUtils.isFriday(date)) {
            return FRIDAY;
        }
        if (DateUtils.isSaturday(date)) {
            return SATURDAY;
        }
        if (DateUtils.isSunday(date)) {
            return SUNDAY;
        }
        return null;
    }

    public int getValue() {
        return value;
    }
}
