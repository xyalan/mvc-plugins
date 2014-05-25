package com.hialan.hv.commons.lang;

/**
 * Auth: alanlhy@gail.com
 * Date Time: 2011-10-19 19:32
 * description
 */
public enum Status {
    NO(0, "NO", "否", "禁用", "启用", "未处理", "未审核", "未推荐"),
    YES(1, "YES", "是", "启用", "禁用", "已处理", "已审核", "已推荐");


    Status(int id, String code, String booleanDesc, String enableDesc, String disableDesc,
           String processDesc, String checkDesc, String recommendDesc) {
        this.id = id;
        this.code = code;
        this.booleanDesc = booleanDesc;
        this.enableDesc = enableDesc;
        this.disableDesc = disableDesc;
        this.processDesc = processDesc;
        this.checkDesc = checkDesc;

        this.recommendDesc = recommendDesc;
    }

    private int id;
    private String code;
    private String booleanDesc;
    private String enableDesc;
    private String disableDesc;
    private String processDesc;
    private String checkDesc;
    private String recommendDesc;

    public boolean isYes() {
        return YES == this;
    }

    public boolean isNo() {
        return NO == this;
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

    public String getBooleanDesc() {
        return booleanDesc;
    }

    public void setBooleanDesc(String booleanDesc) {
        this.booleanDesc = booleanDesc;
    }

    public String getEnableDesc() {
        return enableDesc;
    }

    public void setEnableDesc(String enableDesc) {
        this.enableDesc = enableDesc;
    }

    public String getProcessDesc() {
        return processDesc;
    }

    public void setProcessDesc(String processDesc) {
        this.processDesc = processDesc;
    }

    public String getCheckDesc() {
        return checkDesc;
    }

    public void setCheckDesc(String checkDesc) {
        this.checkDesc = checkDesc;
    }

    public String getDisableDesc() {
        return disableDesc;
    }

    public void setDisableDesc(String disableDesc) {
        this.disableDesc = disableDesc;
    }

    public String getRecommendDesc() {
        return recommendDesc;
    }

    public void setRecommendDesc(String recommendDesc) {
        this.recommendDesc = recommendDesc;
    }
}