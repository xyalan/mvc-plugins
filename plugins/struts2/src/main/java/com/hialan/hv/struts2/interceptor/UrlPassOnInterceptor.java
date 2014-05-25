package com.hialan.hv.struts2.interceptor;

import com.hialan.hv.commons.site.SiteUrl;
import com.hialan.hv.struts2.PluginStatics;
import com.hialan.hv.struts2.UrlPassOnAware;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.ServletActionContext;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 9/5/13
 * Time: 4:08 PM
 */
public class UrlPassOnInterceptor extends AbstractInterceptor {

    private static final String[] DEFAULT_EXCLUDE_PARAMETER_PATTERN = new String[]{
            "\\&\\w*page=\\d+",
            "\\?\\w*pageSize=\\d+",
            "\\?\\w*dir=\\d+",
            "\\&\\w*sort=\\d+"
    };

    private String[] excludeParameterPattern = DEFAULT_EXCLUDE_PARAMETER_PATTERN;

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();
        if (action instanceof UrlPassOnAware) {

            HttpServletRequest request = ServletActionContext.getRequest();
            if (request.getAttribute(PluginStatics.CONTEXT_PATH) == null) {
                request.setAttribute(PluginStatics.CONTEXT_PATH, request.getContextPath());
            }
            if (request.getAttribute(PluginStatics.CURRENT_URL) == null) {
                request.setAttribute(PluginStatics.CURRENT_URL, extractCurrentURL(request, true));
            }
            if (request.getAttribute(PluginStatics.NO_QUERYSTRING_CURRENT_URL) == null) {
                request.setAttribute(PluginStatics.NO_QUERYSTRING_CURRENT_URL, extractCurrentURL(request, false));
            }
            if (request.getAttribute(PluginStatics.BACK_URL) == null) {
                request.setAttribute(PluginStatics.BACK_URL, extractBackURL(request));
            }

            SiteUrl siteUrl = new SiteUrl(getString(request, PluginStatics.BACK_URL), getString(request, PluginStatics.CURRENT_URL), getString(request, PluginStatics.NO_QUERYSTRING_CURRENT_URL));
            ((UrlPassOnAware) action).setSiteUrl(siteUrl);

        }
        return invocation.invoke();
    }

    private String getString(HttpServletRequest request, String name) {
        return (String) request.getAttribute(name);
    }

    private String extractCurrentURL(HttpServletRequest request, boolean needQueryString) {
        String url = request.getRequestURI();
        String queryString = request.getQueryString();
        if (org.springframework.util.StringUtils.hasLength(queryString)) {
            queryString = "?" + queryString;
            for (String pattern : excludeParameterPattern) {
                queryString = queryString.replaceAll(pattern, "");
            }
            if (queryString.startsWith("&")) {
                queryString = "?" + queryString.substring(1);
            }
        }
        if (org.springframework.util.StringUtils.hasLength(queryString) && needQueryString) {
            url = url + queryString;
        }
        return getBasePath(request) + url;
    }

    /**
     * 上一次请求的地址
     * 1、先从request.parameter中查找BackURL
     * 2、获取header中的 Referer
     *
     * @param request HttpServletRequest
     * @return url
     */
    private String extractBackURL(HttpServletRequest request) {
        String url = request.getParameter(PluginStatics.BACK_URL);

        if (StringUtils.isEmpty(url)) {
            url = request.getHeader("Referer");
        }
        if (StringUtils.startsWith(url, request.getContextPath())) {
            url = getBasePath(request) + url;
        }
        return url;
    }

    private String getBasePath(HttpServletRequest req) {
        StringBuilder baseUrl = new StringBuilder();
        String scheme = req.getScheme();
        int port = req.getServerPort();

        baseUrl.append(scheme);
        baseUrl.append("://");
        baseUrl.append(req.getServerName());
        if ((scheme.equals("http") && port != 80) || (scheme.equals("https") && port != 443)) {
            baseUrl.append(':');
            baseUrl.append(req.getServerPort());
        }
        return baseUrl.toString();
    }
}
