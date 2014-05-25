package com.hialan.hv.struts2.converter;

import com.alibaba.fastjson.JSON;
import com.hialan.hv.commons.search.SearchBean;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 27/4/13
 * Time: 10:33 AM
 */
public class SearchBeanTypeConverter extends StrutsTypeConverter {

    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        String value = values[0];
        if (StringUtils.isBlank(value)) {
            return null;
        }
        return JSON.parseObject(value, SearchBean.class);
    }

    @Override
    public String convertToString(Map context, Object o) {
        if (o instanceof SearchBean) {
            SearchBean searchBean = (SearchBean) o;
            return JSON.toJSONString(searchBean);
        }
        return "";
    }
}
