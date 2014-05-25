package com.hialan.hv.commons.lang;


public enum DayNightAuditStatus {
    CHECK_OUT("已离店", "Check out"), CHECK_IN("已入住", "Check in"),
    NO_SHOW("NO Show", "NO Show"), CANCELE("取消", "CANCEL");
    private String chinese;
    private String english;

    DayNightAuditStatus(String chinese, String english) {
        this.chinese = chinese;
        this.english = english;
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
