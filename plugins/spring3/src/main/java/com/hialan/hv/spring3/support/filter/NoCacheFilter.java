package com.hialan.hv.spring3.support.filter;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class NoCacheFilter extends OncePerRequestFilter {

    private static final String EXCLUDE_PREFIXES = "excludePrefixes";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!isExcludedPath(request)) {
            response.addHeader("Pragma", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Expires", "0");
        }
        filterChain.doFilter(request, response);
    }

    private boolean isExcludedPath(HttpServletRequest request) {
        String contextPath = request.getContextPath();
        StringBuffer requestURL = request.getRequestURL();
        String url = requestURL.substring(requestURL.indexOf(contextPath) + contextPath.length());
        if (url == null || url.trim().length() == 0) {
            return true;
        }
        String excludePrefixes = getFilterConfig().getInitParameter(EXCLUDE_PREFIXES);
        String[] parts = StringUtils.split(excludePrefixes, ",");
        if (parts != null) {
            for (String exclude : parts) {
                if (url.startsWith(exclude)) {
                    return true;
                }
            }
        }
        return false;
    }
}
