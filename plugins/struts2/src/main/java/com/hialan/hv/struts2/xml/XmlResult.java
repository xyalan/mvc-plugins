package com.hialan.hv.struts2.xml;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class XmlResult implements Result {
    private String target;
    private ResponseWrapper out = new ResponseWrapper();
    private static final Log log = LogFactory.getLog(XmlResult.class);

    private Object extractTargetObject(ActionInvocation invocation) {
        Object targetObject;
        if (this.target != null) {
            ValueStack stack = invocation.getStack();
            targetObject = stack.findValue(this.target);
            if (log.isTraceEnabled()) {
                log.trace(String.format("Evaluate serializer target %s to %s.",
                        target, targetObject));
            }
        } else {
            targetObject = invocation.getAction();
            if (log.isTraceEnabled()) {
                log.trace("Using action instance as serializer target.");
            }
        }
        return targetObject;
    }

    @Override
    public void execute(ActionInvocation invocation) throws Exception {
        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletResponse response = (HttpServletResponse) actionContext
                .get(StrutsStatics.HTTP_RESPONSE);
        HttpServletRequest request = (HttpServletRequest) actionContext
                .get(StrutsStatics.HTTP_REQUEST);
        Object targetObject = extractTargetObject(invocation);
        if (targetObject != null && String.class.isAssignableFrom(targetObject.getClass())) {
            out.writeResult(request, response, (String) targetObject);
        }

    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public ResponseWrapper getOut() {
        return out;
    }

    public void setOut(ResponseWrapper out) {
        this.out = out;
    }
}
