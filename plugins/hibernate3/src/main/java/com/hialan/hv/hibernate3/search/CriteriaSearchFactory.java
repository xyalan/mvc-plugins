package com.hialan.hv.hibernate3.search;

import com.hialan.hv.commons.search.SearchBean;
import com.hialan.hv.commons.search.SearchFactory;
import com.hialan.hv.commons.util.ReflectionUtils;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Created with IntelliJ IDEA.
 * User: Alan Yang
 * Date: 10/4/13
 * Time: 10:21 AM
 */
public class CriteriaSearchFactory extends SearchFactory {

    private static final char SEARCH_CHAR_POINT = '.';

    private static List<Class<?>> SYSTEM_CLASSES = new ArrayList<Class<?>>();

    static {
        SYSTEM_CLASSES.add(boolean.class);
        SYSTEM_CLASSES.add(byte.class);
        SYSTEM_CLASSES.add(short.class);
        SYSTEM_CLASSES.add(char.class);
        SYSTEM_CLASSES.add(int.class);
        SYSTEM_CLASSES.add(float.class);
        SYSTEM_CLASSES.add(double.class);
        SYSTEM_CLASSES.add(long.class);

        SYSTEM_CLASSES.add(Boolean.class);
        SYSTEM_CLASSES.add(Byte.class);
        SYSTEM_CLASSES.add(Short.class);
        SYSTEM_CLASSES.add(Character.class);
        SYSTEM_CLASSES.add(Integer.class);
        SYSTEM_CLASSES.add(Float.class);
        SYSTEM_CLASSES.add(Double.class);
        SYSTEM_CLASSES.add(Long.class);

        SYSTEM_CLASSES.add(Enum.class);
        SYSTEM_CLASSES.add(BigDecimal.class);
        SYSTEM_CLASSES.add(String.class);
        SYSTEM_CLASSES.add(Date.class);

        Collections.unmodifiableList(SYSTEM_CLASSES);
    }

    private static final Predicate SEARCH_BEAN_EMPTY_PREDICATE = new Predicate() {
        @Override
        public boolean evaluate(Object object) {
            if (object == null) {
                return false;
            }
            SearchBean searchBean = (SearchBean) object;
            return StringUtils.isNotBlank(searchBean.getValue());
        }
    };

    public static DetachedCriteria generateCriteria(Class entity, List<SearchBean> originSearchBeans) {

        String rootAlias = entity.getSimpleName();

        DetachedCriteria criteria = DetachedCriteria.forClass(entity, rootAlias);

        List<SearchBean> searchBeans = new ArrayList<SearchBean>(originSearchBeans);

        CollectionUtils.filter(searchBeans, SEARCH_BEAN_EMPTY_PREDICATE);

        if (CollectionUtils.isEmpty(searchBeans)) {
            return criteria;
        }

        createAlias(criteria, rootAlias, searchBeans);

        for (SearchBean searchBean : searchBeans) {
            attachSearchBean(entity, criteria, searchBean);
        }
        return criteria;
    }

    private static void createAlias(DetachedCriteria criteria, String rootAlias, List<SearchBean> searchBeans) {
        Map<String, String> aliasCache = new LinkedHashMap<String, String>();
        for (SearchBean searchBean : searchBeans) {
            String name = searchBean.getName();
            if (StringUtils.contains(name, SEARCH_CHAR_POINT)) {
                int start = 0;
                int mark = name.indexOf(SEARCH_CHAR_POINT);
                if (mark < 0) {
                    continue;
                }
                String prefix = rootAlias + SEARCH_CHAR_POINT;
                while (mark >= 0) {
                    String str = name.substring(start, mark);
                    aliasCache.put(prefix + str, str);

                    start = mark + 1;
                    mark = name.indexOf(SEARCH_CHAR_POINT, start);
                    prefix = str + SEARCH_CHAR_POINT;
                }
            }
        }
        for (Map.Entry<String, String> entry : aliasCache.entrySet()) {
            criteria.createAlias(entry.getKey(), entry.getValue());
        }
    }


    @SuppressWarnings("unchecked")
    private static DetachedCriteria attachSearchBean(Class clazz, DetachedCriteria criteria, SearchBean searchBean) {
        if (criteria == null) {
            criteria = DetachedCriteria.forClass(clazz);
        }
        if (searchBean == null) {
            return criteria;
        }
        String value1 = searchBean.getValue();
        String name = searchBean.getName();
        if (StringUtils.isBlank(name) || StringUtils.isBlank(value1)) {
            return criteria;
        }

        boolean hasPoint = StringUtils.contains(name, SEARCH_CHAR_POINT);

        Field field = hasPoint ?
                getField(null, clazz, StringUtils.split(name, SEARCH_CHAR_POINT)) :
                ReflectionUtils.findField(clazz, name);

        if (field != null) {
            String relation = searchBean.getRelation();
            addRestrictions(criteria, value1, name, field, relation);
        }
        return criteria;
    }

    private static void addRestrictions(DetachedCriteria criteria, String value1, String name, Field field, String relation) {
        Class type = field.getType();
        CriteriaRelation crt = CriteriaRelation.valueOfExp(relation);
        if (crt == null) {
            return;
        }

        boolean hasPoint = StringUtils.contains(name, SEARCH_CHAR_POINT);
        if (hasPoint) {
            String[] parts = StringUtils.split(name, SEARCH_CHAR_POINT);
            if (parts != null && parts.length > 1) {
                ArrayUtils.reverse(parts);
                name = parts[1] + SEARCH_CHAR_POINT + parts[0];
            }
        }
        Object value = convertValue(type, value1, crt);
        crt.addCriteria(criteria, name, value);
    }

    private static Field getField(Field field, Class clazz, Object[] parts) {
        if (ArrayUtils.isEmpty(parts)) {
            return field;
        }
        if (field != null) {
            Class<?> aClass = field.getType();
            if (SYSTEM_CLASSES.contains(aClass)) {
                return field;
            }
            //get Parameter type if the field is a collection field
            if (Arrays.asList(aClass.getInterfaces()).contains(Collection.class)) {
                clazz = ReflectionUtils.getSuperClassGenericType(field.getGenericType());
            }
        }
        field = ReflectionUtils.findField(clazz, (String) parts[0]);
        return getField(field, field.getType(), ArrayUtils.subarray(parts, 1, parts.length));
    }

    @SuppressWarnings("unchecked")
    public static DetachedCriteria generateCriteria(Class clazz, SearchBean[] searchBeans) {
        if (searchBeans != null && searchBeans.length > 0) {
            return generateCriteria(clazz, Arrays.asList(searchBeans));
        }
        return DetachedCriteria.forClass(clazz);
    }

    private static Object convertValue(Class type, String value, CriteriaRelation relation) {
        Object object;
        if (relation == CriteriaRelation.between) {
            List<Object> list = new ArrayList<Object>();
            String[] betValue = StringUtils.split(value, ';');
            if (type == java.util.Date.class) {
                if (betValue[0].length() == 10)
                    betValue[0] += " 00:00:00";
                else
                    betValue[0] += ":00";
                if (betValue[1].length() == 10)
                    betValue[1] += " 23:59:59";
                else
                    betValue[1] += ":59";
                try {
                    list.add(DateUtils.parseDate(betValue[0],
                            new String[]{"yyyy-MM-dd hh:mm:ss"}));
                    list.add(DateUtils.parseDate(betValue[1],
                            new String[]{"yyyy-MM-dd hh:mm:ss"}));
                } catch (Exception e) {
                    throw new RuntimeException("error.date.format");
                }
            } else {
                list.add(OgnlOpsConvert.convertValue(betValue[0], type));
                list.add(OgnlOpsConvert.convertValue(betValue[1], type));
            }
            object = list;
        } else if (relation == CriteriaRelation.in) {
            List<Object> list = new ArrayList<Object>();
            for (String s : StringUtils.split(value, ';')) {
                list.add(OgnlOpsConvert.convertValue(s, type));
            }
            object = list;
        } else if (relation == CriteriaRelation.iLike) {
            object = OgnlOpsConvert.convertValue("%" + value + "%", type);
        } else if (relation == CriteriaRelation.like) {
            object = OgnlOpsConvert.convertValue(value + "%", type);
        } else {
            if (type == java.util.Date.class) {
                if (relation == CriteriaRelation.lt || relation == CriteriaRelation.le) {
                    if (value.length() == 10)
                        value += " 23:59:59";
                    else
                        value += ":59";
                } else {
                    if (value.length() == 10)
                        value += " 00:00:00";
                    else
                        value += ":00";
                }
                try {
                    object = DateUtils.parseDate(value, new String[]{"yyyy-MM-dd hh:mm:ss"});
                } catch (ParseException e) {
                    throw new RuntimeException("Unable to parse the Date");
                }
            } else {
                object = OgnlOpsConvert.convertValue(value, type);
            }

        }
        return object;
    }
}