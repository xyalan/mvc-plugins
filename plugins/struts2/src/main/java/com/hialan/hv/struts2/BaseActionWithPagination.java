package com.hialan.hv.struts2;

import com.hialan.hv.commons.pagination.Pagination;

/**
 * Created by IntelliJ IDEA.
 * Author: Alan Yang
 * E-mail:alanlhy@gmail.com
 * Date: 2011-6-22
 * Time: 20:42:54
 * description
 */
public class BaseActionWithPagination extends BaseAction {
    private Pagination pagination;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }
}
