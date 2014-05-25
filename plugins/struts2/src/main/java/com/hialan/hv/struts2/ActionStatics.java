package com.hialan.hv.struts2;

/**
 * @author <a href="mailto:alanlhy@gmail.com">Alan Yang</a>
 *         Date: 5/6/13
 *         Time: 2:25 PM
 */
public interface ActionStatics {

     String ID = "id";
     String AJAX_SUFFIX = "-ajax";
     String VIEW = "view";
     String EDIT = "edit";
     String SAVE = "save";
     String LIST = "list";
     String REMOVE = "remove";
     String BACK_LOCATION = "${siteUrl.backUrl}";
     String JSON = "json";
     String STATUS = "status";
     String RESULT = "result";
     String TARGET = "target";
     String EDIT_AJAX = EDIT + AJAX_SUFFIX;
     String VIEW_AJAX = VIEW + AJAX_SUFFIX;
     String SAVE_AJAX = SAVE + AJAX_SUFFIX;
     String TOGGLE_STATUS_AJAX = "toggle-status" + AJAX_SUFFIX;
     String REDIRECT_ACTION = "redirectAction";
     String REDIRECT = "redirect";
     String NAMESPACE = "namespace";
     String ACTION_NAME = "actionName";
}
