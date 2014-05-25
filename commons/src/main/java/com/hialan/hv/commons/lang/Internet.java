package com.hialan.hv.commons.lang;


public enum Internet {
    FREE("免费", "Free"), CHARGED("收费", "Charged"), PARTIALLY_CHARGED("部分收费", "Partially Charged");
    private String chinese;
    private String english;

    Internet(String chinese, String english) {
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
