package com.hialan.hv.struts2;

import com.hialan.hv.commons.pagination.Paginate;
import com.hialan.hv.commons.search.SearchBean;
import com.opensymphony.xwork2.ActionSupport;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

public abstract class PaginateActionSupport extends ActionSupport implements ActionStatics {

    protected Paginate paginate = new Paginate();

    protected final Log logger = LogFactory.getLog(this.getClass());

    protected List<SearchBean> searchBeans = new ArrayList<SearchBean>();

    @Action("execute")
    @Override
    public String execute() throws Exception {
        return SUCCESS;
    }

    public void setPaginate(Paginate paginate) {
        this.paginate = paginate;
    }

    public Paginate getPaginate() {
        return paginate;
    }

    public List<SearchBean> getSearchBeans() {
        return this.searchBeans;
    }

    public void setSearchBeans(List<SearchBean> searchBeans) {
        this.searchBeans = searchBeans;
    }

    protected void addSearchBeans(SearchBean... beans) {
        CollectionUtils.addAll(searchBeans, beans);
    }
}