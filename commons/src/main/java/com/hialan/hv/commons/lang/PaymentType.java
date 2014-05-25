package com.hialan.hv.commons.lang;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 7/2/13
 * Time: 5:58 PM
 * To change this template use File | Settings | File Templates.
 */
public enum PaymentType {
    POA("Pay on Arrival", "现付"), PREPAY("Pre Pay", "预付");
    private String chinese;
    private String english;

    PaymentType(String english, String chinese) {
        this.english = english;
        this.chinese = chinese;
    }

    public boolean isPOA() {
        return this == POA;
    }

    public boolean isPrePay() {
        return this == PREPAY;
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
