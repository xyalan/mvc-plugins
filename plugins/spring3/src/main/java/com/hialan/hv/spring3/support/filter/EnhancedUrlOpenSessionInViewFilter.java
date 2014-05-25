package com.hialan.hv.spring3.support.filter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;

public class EnhancedUrlOpenSessionInViewFilter extends OpenSessionInViewFilter {

    private static String[] excludeSuffixs;

    private static final String EXCLUDE_SUFFIXS_NAME = "excludeSuffixs";

    private static final String[] DEFAULT_EXCLUDE_SUFFIXS = {".js", ".css", ".jpg", ".gif", ".png", ".txt"};

    @Override
    protected boolean shouldNotFilter(final HttpServletRequest request) throws ServletException {
        String path = request.getServletPath();

        for (String suffix : excludeSuffixs) {
            if (path.endsWith(suffix)) {
                return true;
            }
        }
        return false;
    }

    private static final String DOT = ".";

    private static final String SPLIT = ",";

    @Override
    protected void initFilterBean() throws ServletException {
        String excludeSuffixStr = getFilterConfig().getInitParameter(EXCLUDE_SUFFIXS_NAME);
        if (StringUtils.isNotBlank(excludeSuffixStr)) {
            excludeSuffixs = excludeSuffixStr.split(SPLIT);
            for (int i = 0; i < excludeSuffixs.length; i++) {
                excludeSuffixs[i] = DOT + excludeSuffixs[i];
            }
        } else {
            excludeSuffixs = DEFAULT_EXCLUDE_SUFFIXS;
        }
    }
}
