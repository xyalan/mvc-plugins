package com.hialan.hv.struts2;

import com.opensymphony.xwork2.ActionSupport;

/**
 * Auth: alanlhy@gmail.com
 * Date Time: 2013-04-28 14:22
 * description
 *
 * @see com.hialan.hv.struts2.ActionStatics
 * @deprecated
 */
public class CommonAction extends ActionSupport {
    protected static final String ID = "id";
    protected static final String VIEW = "view";
    protected static final String EDIT = "edit";
    protected static final String SAVE = "save";
    protected static final String LIST = "list";
    protected static final String REMOVE = "remove";
    protected static final String BACK_LOCATION = "${siteUrl.backUrl}";
    protected static final String JSON = "json";
    protected static final String STATUS = "status";
    protected static final String RESULT = "result";
    protected static final String TARGET = "target";
    protected static final String EDIT_AJAX = "edit-ajax";
    protected static final String TOGGLE_STATUS_AJAX = "toggle-status-ajax";
    protected static final String REDIRECT_ACTION = "redirectAction";
    protected static final String REDIRECT = "redirect";
    protected static final String NAMESPACE = "namespace";
    protected static final String ACTION_NAME = "actionName";
}
