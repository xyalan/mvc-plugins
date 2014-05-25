package com.hialan.hv.struts2.interceptor;

import com.hialan.hv.commons.pagination.Paginate;
import com.hialan.hv.struts2.PaginateActionSupport;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

public class InitializePaginateInterceptor extends AbstractInterceptor {

    private Log logger = LogFactory.getLog(InitializePaginateInterceptor.class);

    private static final String PAGE_NO = "page";

    private static final String PAGE_SIZE = "pageSize";

    private static final String SORT_FIELD = "sort";

    private static final String SORT_DIRECTION = "dir";

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
        Object action = invocation.getAction();
        if (PaginateActionSupport.class.isInstance(action)) {
            try {
                PaginateActionSupport actionSupport = (PaginateActionSupport) action;
                setPaginateParameters(actionSupport.getPaginate());
            } catch (Exception e) {
                logger.info("Error during parse paginate parameters", e);
            }
        }
        return invocation.invoke();
    }

    public void setPaginateParameters(Paginate paginate) {
        HttpServletRequest request = ServletActionContext.getRequest();
        String pageNo = request.getParameter(PAGE_NO);
        if (StringUtils.isNotBlank(pageNo)) {
            paginate.setPageNo(Integer.parseInt(pageNo));
        }
        String pageSize = request.getParameter(PAGE_SIZE);
        if (StringUtils.isNotBlank(pageSize)) {
            paginate.setPageSize(Integer.parseInt(pageSize));
        }
        String sortField = request.getParameter(SORT_FIELD);
        if (StringUtils.isNotBlank(sortField)) {
            paginate.setSortField(sortField);
        }
        String sortDirection = request.getParameter(SORT_DIRECTION);
        if (StringUtils.isNotBlank(sortDirection)) {
            paginate.setSortDirection(sortDirection);
        }
    }
}
