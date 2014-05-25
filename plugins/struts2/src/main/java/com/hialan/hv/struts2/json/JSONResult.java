package com.hialan.hv.struts2.json;

import com.alibaba.fastjson.JSON;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.Result;
import com.opensymphony.xwork2.util.ValueStack;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * User: Alan Yang
 * Date: 1/4/13
 * Time: 5:26 PM
 */
public class JSONResult implements Result {

    private String target;
    private String excludes;

    private static final Log log = LogFactory.getLog(JSONResult.class);

    private ResponseWrapper out = new ResponseWrapper();

    public void setTarget(String target) {
        this.target = target;
    }

    public String getExcludes() {
        return excludes;
    }

    public void setExcludes(String excludes) {
        this.excludes = excludes;
    }

    public ResponseWrapper getOut() {
        return out;
    }

    public void setOut(ResponseWrapper out) {
        this.out = out;
    }

    /**
     * find the target object according the target property(parameter) if not
     * set, the action will be used.
     *
     * @param invocation
     * @return
     */
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

    /**
     * @see com.opensymphony.xwork2.Result#execute(com.opensymphony.xwork2.ActionInvocation)
     */
    @Override
    public void execute(ActionInvocation invocation) throws Exception {

        ActionContext actionContext = invocation.getInvocationContext();
        HttpServletResponse response = (HttpServletResponse) actionContext
                .get(StrutsStatics.HTTP_RESPONSE);
        HttpServletRequest request = (HttpServletRequest) actionContext
                .get(StrutsStatics.HTTP_REQUEST);
        Object targetObject = extractTargetObject(invocation);
        if (targetObject != null) {
            String result = JSON.toJSONString(targetObject);
            out.writeResult(request, response, result);
        }
    }
}
