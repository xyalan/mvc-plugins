package com.hialan.hv.struts2.converter;


import com.hialan.hv.commons.util.DateUtils;
import com.opensymphony.xwork2.util.logging.Logger;
import com.opensymphony.xwork2.util.logging.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.util.StrutsTypeConverter;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

public class CalendarConvert extends StrutsTypeConverter {

    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String DATEMIN_PATTERN = "yyyy-MM-dd HH:mm";
    private static final String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    private static final Logger logger = LoggerFactory.getLogger(CalendarConvert.class);

    @Override
    public Object convertFromString(Map context, String[] values, Class toClass) {
        String value = values[0];
        Calendar result = null;
        if (StringUtils.isNotBlank(value)&&toClass.isAssignableFrom(Calendar.class)) {
            try {
                Date date = DateUtils.parseDate(value, new String[]{DATE_PATTERN, DATEMIN_PATTERN, DATETIME_PATTERN});
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(date);
                result = calendar;
            } catch (ParseException e) {
                logger.error("Converting from datetime pattern to Calendar fails! [" + value + ":" + e.getMessage() + "]");
            }

            if (result == null && StringUtils.isNotEmpty(value)) {
                try {
                    Calendar c = Calendar.getInstance();
                    c.setTimeInMillis(Long.valueOf(value));
                    result = c;
                } catch (Exception e) {
                    logger.error("Converting from milliseconds to Date fails! [" + value + ":" + e.getMessage() + "]");
                }

            }

        }
        return result;
    }

    @Override
    public String convertToString(Map context, Object o) {
        if (o instanceof Calendar) {
            try {
                Calendar calendar = (Calendar) o;
                return DateUtils.formatDate(calendar.getTime());
            } catch (Exception e) {
                logger.error("Converting calendar to String failed");
            }
        }
        return "";
    }
}
