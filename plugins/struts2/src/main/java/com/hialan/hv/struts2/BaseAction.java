package com.hialan.hv.struts2;

import com.hialan.hv.commons.site.SiteUrl;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.Preparable;
import java.util.Map;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.struts2.interceptor.SessionAware;

public abstract class BaseAction extends ActionSupport implements SessionAware, Preparable, UrlPassOnAware, ActionStatics {

    protected Map session;

    protected Long id;

    protected SiteUrl siteUrl;

    protected String backUrl;

    @Override
    public void prepare() throws Exception {
    }

    public void setSession(Map session) {
        this.session = session;
    }

    public Map getSession() {
        return session;
    }

    protected String[] getParameterValues(String parameterName) {
        String[] values = (String[]) ActionContext.getContext().getParameters().get(parameterName);
        if (ArrayUtils.isEmpty(values)) {
            return null;
        }
        return values;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public void setSiteUrl(SiteUrl siteUrl) {
        this.siteUrl = siteUrl;
    }

    public SiteUrl getSiteUrl() {
        return siteUrl;
    }

    public String getBackUrl() {
        return backUrl;
    }

    public void setBackUrl(String backUrl) {
        this.backUrl = backUrl;
    }
}