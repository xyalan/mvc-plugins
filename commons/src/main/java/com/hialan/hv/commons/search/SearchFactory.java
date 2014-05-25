package com.hialan.hv.commons.search;

import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/**
 * 将自定义查询的值封装为SearchBean数组
 * <p/>
 */
public abstract class SearchFactory {

    /**
     * @param queryString 格式：
     *                    [name,value,relation],[name,value,relation],[name,value,relation]
     *                    name:    字段名称
     *                    value:   字段值
     *                    relation:条件关系，包含：=, <, >, <=, >=, like, in
     *                    type:    字段类型，包含：numeric, string, date, boolean
     * @return Array of SearchBean
     */
    public static SearchBean[] getSearchTeam(String queryString) {
        String[] team = detach(queryString, '[', ']');
        if (team == null)
            return null;
        SearchBean[] search = new SearchBean[team.length];
        for (int i = 0; i < team.length; i++) {
            String[] element = StringUtils.split(team[i], ',');
            if (element == null || element.length != 3)
                throw new RuntimeException("error.search.format");
            search[i] = new SearchBean(element[0], element[1], element[2]);
        }
        return search;
    }

    private static String[] detach(String str, char left, char right) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        List<String> result = new ArrayList<String>();

        if (StringUtils.indexOf(str, left) == -1 || StringUtils.indexOf(str, right) == -1) {
            throw new RuntimeException("Unable to parse the string: " + str);
        }

        while (StringUtils.indexOf(str, left) >= 0 && StringUtils.indexOf(str, right) >= 0) {
            int il = StringUtils.indexOf(str, left);
            int ir = StringUtils.indexOf(str, right);
            if (il > ir) {
                str = StringUtils.substring(str, right + 1);
                continue;
            }
            result.add(StringUtils.substring(str, il + 1, ir));
            str = StringUtils.substring(str, ir + 1);
        }
        return result.toArray(new String[result.size()]);
    }
}
