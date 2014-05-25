package com.hialan.hv.struts2.interceptor;

import com.hialan.hv.commons.pagination.Pagination;
import com.hialan.hv.commons.pagination.PaginationSupport;
import com.hialan.hv.struts2.BaseActionWithPagination;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.Map;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * Author: Alan Yang
 * E-mail:alanlhy@gmail.com
 * Date: 2011-6-22
 * Time: 20:41:32
 * description
 */
public class PaginationInterceptor extends AbstractInterceptor implements StrutsStatics {

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();
        String methodName = invocation.getProxy().getMethod();
        Method method = action.getClass().getMethod(methodName);
        PaginationSupport annotation = method.getAnnotation(PaginationSupport.class);
        if (action instanceof BaseActionWithPagination && null != annotation) {
            BaseActionWithPagination paginationSupport = (BaseActionWithPagination) action;
            final ActionContext context = invocation.getInvocationContext();
            HttpServletRequest request = (HttpServletRequest) context.get(HTTP_REQUEST);
            Pagination pagination = new Pagination();
            initParameter(request, pagination);
            paginationSupport.setPagination(pagination);
            log.debug("run pagination");
        }
        return invocation.invoke();
    }

    private void initParameter(HttpServletRequest request, Pagination pagination) {
        String url = getQueryUrl(request);
        String currentPage = request.getParameter(Pagination.CURRENT_PAGE);
        if (null != currentPage) {
            try {
                Integer integer = Integer.valueOf(currentPage);
                pagination.setCurrentPage(integer);
            } catch (Exception e) {
                log.debug(e.getMessage());
            }
        }
        pagination.setUrl(url);
        String pageSize = request.getParameter(Pagination.PAGE_SIZE);
        if (StringUtils.isNotBlank(pageSize)) {
            Integer size = Integer.valueOf(pageSize);
            if (size > 0 && size <= 100) {//limit
                pagination.setPageSize(size);
            }
        }
        String sortField = request.getParameter(Pagination.SORT_FIELD);
        if (StringUtils.isNotBlank(sortField)) {
            pagination.setSortField(sortField);
        }
        String sortDirection = request.getParameter(Pagination.SORT_DIR);
        if (StringUtils.isNotBlank(sortDirection)) {
            pagination.setSortDirection(Pagination.SORT_ASC.equals(sortDirection));
        }
    }

    /**
     * copy from @author Haulyn Jason(saharabear@gmail.com)
     *
     * @param request
     * @return
     */
    private String getQueryUrl(HttpServletRequest request) {
        StringBuffer urlBuffer = request.getRequestURL();
        urlBuffer.append("?");
        String query = request.getQueryString();
        if (null == query) {
            query = "";
            Map params = request.getParameterMap();
            Set<Map.Entry> paramsSet = params.entrySet();
            for (Map.Entry entry : paramsSet) {
                String str = ((String[]) entry.getValue())[0];
                try {
                    str = URLEncoder.encode(str, "UTF-8");
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
                }
                query = query.concat("&")
                        .concat((String) entry.getKey())
                        .concat("=")
                        .concat(str);
            }
        }
        if (query != null) {
            // Remove possible #foobar suffix
            int idx = query.lastIndexOf('#');
            if (idx != -1) {
                query = query.substring(0, idx);
            }
            query = subParameterForUrl(query, Pagination.CURRENT_PAGE);
            //删除所有query前面的&
            boolean needDeletePrefix = true;
            if (!query.startsWith("&")) {
                needDeletePrefix = false;
            }
            while (needDeletePrefix) {
                query = query.substring(1, query.length());
                if (!query.startsWith("&") || query.length() == 0) {
                    needDeletePrefix = false;
                }
            }

            //删除所有query前面的&
            boolean needDeleteExt = true;
            if (!query.endsWith("&")) {
                needDeleteExt = false;
            }
            while (needDeleteExt) {
                query = query.substring(0, query.length() - 1);
                if (!query.endsWith("&") || query.length() == 0) {
                    needDeleteExt = false;
                }
            }

            //结束URL的参数的配置，将Query字符串前面所有的&删除，如果query有值则在后面添加一个&
            if (query.length() > 0) {
                urlBuffer.append(query).append("&");
            }
        }
        //完成获取完整URL
        String url = urlBuffer.toString();
        return url;
    }

    /**
     * copy from @author Haulyn Jason(saharabear@gmail.com)
     *
     * @param string
     * @param paramName
     * @return
     */
    private String subParameterForUrl(String string, String paramName) {
        int beginIndex = string.indexOf(paramName.concat("="));
        if (-1 != beginIndex) {
            int endIndex = string.indexOf("&", beginIndex);
            if (endIndex == -1) {
                endIndex = string.length();
            }
            //这里需要处理一下性能问题
            String token = string.substring(beginIndex, endIndex);
            string = string.replaceAll(token, "");
        }
        return string;
    }

    private static final Log log = LogFactory.getLog(PaginationInterceptor.class);
}
