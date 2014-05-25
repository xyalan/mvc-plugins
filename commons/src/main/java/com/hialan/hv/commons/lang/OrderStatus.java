package com.hialan.hv.commons.lang;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 7/2/13
 * Time: 4:24 PM
 * To change this template use File | Settings | File Templates.
 */
public enum OrderStatus {
    CONFIRMED("confirmed", "已确认"), CANCELLED("cancelled", "已取消"), FAILED("failed", "失败"), CANCEL_FAILED("cancel failed", "取消失败");
    private String english;
    private String chinese;

    OrderStatus(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
    }

    public String getChinese() {
        return chinese;
    }

    public void setChinese(String chinese) {
        this.chinese = chinese;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }
}
