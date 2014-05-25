package com.hialan.hv.commons.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import ognl.ClassResolver;
import ognl.Ognl;
import ognl.OgnlContext;
import ognl.OgnlException;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 27/4/13
 * Time: 2:21 PM
 */
public abstract class OgnlUtils {

    public static final String OGNL_EXPRESSION_MAP_KEY = "ognlExpressionMap";

    public static final String G_OGNL_CONTEXT = "G_OGNL_CONTEXT";

    private static final Map _contexts = new ConcurrentHashMap();

    private static final ClassResolver classResolver = new HvClassResolver();

    @SuppressWarnings("unchecked")
    public static Object getOgnlExpress(String express) throws OgnlException {
        Object o = _contexts.get(express);
        if (o == null) {
            o = Ognl.parseExpression(express);
            _contexts.put(express, o);
        }
        return o;
    }

    @SuppressWarnings("unchecked")
    public static Boolean parseBooleanExpression(Map context, String express) throws Exception {
        try {
            return (Boolean) parseExpressionResult(context, express);
        } catch (Exception e) {
            String err = e.getMessage();
            if (err != null && err.contains("source is null")) {
                return false;
            }
            throw e;
        }
    }

    @SuppressWarnings("unchecked")
    public static Object parseExpressionResult(Map context, String express)
            throws OgnlException {
        int index = express.indexOf("[");
        if (index != -1 && !express.contains(".")) {
            int p = express.indexOf("]");
            if (p != -1) {
                express = express.substring(0, index + 1)
                        + parseExpressionResult(context, express.substring(
                        index + 1, p)) + express.substring(p);
            }
        }
        Object o = context.get(express);
        if (o != null) {
            return o;
        }
        OgnlContext ognlContext = (OgnlContext) context
                .get(G_OGNL_CONTEXT);
        if (ognlContext == null) {
            ognlContext = (OgnlContext) Ognl.createDefaultContext(context);
            ognlContext.setClassResolver(OgnlUtils.classResolver);
            context.put(G_OGNL_CONTEXT, ognlContext);
        }
        return Ognl.getValue(getOgnlExpress(express), ognlContext, context);
    }

    @SuppressWarnings("unchecked")
    public static void setExpressionValue(Map context, String express, Object value) throws OgnlException {
        OgnlContext ognlContext = (OgnlContext) context
                .get(G_OGNL_CONTEXT);
        if (ognlContext == null) {
            ognlContext = (OgnlContext) Ognl.createDefaultContext(context);
            ognlContext.setClassResolver(OgnlUtils.classResolver);
            context.put(G_OGNL_CONTEXT, ognlContext);
        }
        Ognl.setValue(getOgnlExpress(express), ognlContext, context, value);
    }

    @SuppressWarnings("unchecked")
    public static Object getParsedExpression(String expression, Map cache) {
        Object result = cache.get(expression);
        if (result == null) {
            try {
                result = Ognl.parseExpression(expression);
            } catch (Exception ex) {
                throw new RuntimeException("Expression error: '" + expression + "'");
            }
            cache.put(expression, result);
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static synchronized OgnlContext getOgnlContext(Object root) {
        Object result = _contexts.get(root.getClass().getName());
        if (result == null) {
            result = Ognl.createDefaultContext(root);
            ((OgnlContext) result).setClassResolver(classResolver);
            _contexts.put(root.getClass().getName(), result);
        }
        return (OgnlContext) result;
    }


    public static String parseExpression(String expression, Object root, Map cache) throws OgnlException {
        StringBuilder buf = new StringBuilder();
        int start = 0;
        int mark = expression.indexOf("${");
        if (mark < 0)
            return expression;
        while (mark >= 0) {
            buf.append(expression.substring(start, mark));
            int markEnd = expression.indexOf('}', mark);
            start = markEnd + 1;

            String name = expression.substring(mark + 2, markEnd);
            Object value = Ognl.getValue(getParsedExpression(name, cache), getOgnlContext(root), root);
            buf.append(value);

            mark = expression.indexOf("${", markEnd);
        }
        buf.append(expression.substring(start));
        return buf.toString();
    }

}
