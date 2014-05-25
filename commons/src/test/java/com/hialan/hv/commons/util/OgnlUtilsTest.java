package com.hialan.hv.commons.util;

import com.hialan.hv.commons.AbstractTest;
import java.util.HashMap;
import java.util.Map;
import ognl.OgnlException;
import org.apache.commons.chain.Context;
import org.apache.commons.chain.impl.ContextBase;
import org.junit.Test;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 27/4/13
 * Time: 2:52 PM
 */
public class OgnlUtilsTest extends AbstractTest {

    @Test
    @SuppressWarnings("unchecked")
    public void testParseExpression() {
        Context context = new ContextBase();
        context.put("table", "hello");
        String expr = "select * from ${table} where name=:test";
        Map ognlExpressionMap = (Map) context.get(OgnlUtils.OGNL_EXPRESSION_MAP_KEY);
        if (ognlExpressionMap == null) {
            ognlExpressionMap = new HashMap();
            context.put(OgnlUtils.OGNL_EXPRESSION_MAP_KEY, ognlExpressionMap);
        }
        try {
            String result = OgnlUtils.parseExpression(expr, context, ognlExpressionMap);
            assertEquals(result, "select * from hello where name=:test");
        } catch (OgnlException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testParseExpressionWithoutVarible() {
        Context context = new ContextBase();
        context.put("table", "hello");
        String expr = "select * from table where name=:cys";
        Map ognlExpressionMap = (Map) context.get(OgnlUtils.OGNL_EXPRESSION_MAP_KEY);
        if (ognlExpressionMap == null) {
            ognlExpressionMap = new HashMap();
            context.put(OgnlUtils.OGNL_EXPRESSION_MAP_KEY, ognlExpressionMap);
        }
        try {
            String result = OgnlUtils.parseExpression(expr, context, ognlExpressionMap);
            assertEquals(result, "select * from table where name=:cys");
        } catch (OgnlException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testParseExpressionWithMultiVarible() {
        Context context = new ContextBase();
        context.put("table", "hello");
        context.put("name", "name");
        context.put("sex", "sex");
        context.put("desc", "description");
        String expr = "select * from ${table} where ${name}=:cys and ${sex}=:male order by ${desc}";
        Map ognlExpressionMap = (Map) context.get(OgnlUtils.OGNL_EXPRESSION_MAP_KEY);
        if (ognlExpressionMap == null) {
            ognlExpressionMap = new HashMap();
            context.put(OgnlUtils.OGNL_EXPRESSION_MAP_KEY, ognlExpressionMap);
        }
        try {
            String result = OgnlUtils.parseExpression(expr, context, ognlExpressionMap);
            assertEquals(result, "select * from hello where name=:cys and sex=:male order by description");
        } catch (OgnlException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testSetExpressionValue(){
        Context context = new ContextBase();
        String expr = "table";
        Map ognlExpressionMap = (Map) context.get(OgnlUtils.OGNL_EXPRESSION_MAP_KEY);
        if (ognlExpressionMap == null) {
            ognlExpressionMap = new HashMap();
            context.put(OgnlUtils.OGNL_EXPRESSION_MAP_KEY, ognlExpressionMap);
        }
        try {
            OgnlUtils.setExpressionValue(context,expr,"hello");
        } catch (OgnlException e) {
            e.printStackTrace();
            fail(e.toString());
        }
    }
}
