package com.hialan.hv.commons.site;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 10/5/13
 * Time: 10:18 AM
 */
public class SiteUrl implements Serializable {

    private String backUrl;
    private String currentUrl;
    private String noQueryStringCurrentUrl;

    public SiteUrl() {
    }

    public SiteUrl(String backUrl, String currentUrl, String noQueryStringCurrentUrl) {
        this.backUrl = backUrl;
        this.currentUrl = currentUrl;
        this.noQueryStringCurrentUrl = noQueryStringCurrentUrl;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public String getNoQueryStringCurrentUrl() {
        return noQueryStringCurrentUrl;
    }

    public void setNoQueryStringCurrentUrl(String noQueryStringCurrentUrl) {
        this.noQueryStringCurrentUrl = noQueryStringCurrentUrl;
    }
}
