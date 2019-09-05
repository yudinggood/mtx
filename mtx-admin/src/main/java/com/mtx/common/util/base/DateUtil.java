package com.mtx.common.util.base;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * 日期处理类
 */
public class DateUtil {
    /**
     * mysql的 blob转string
     */
    public static Date longToDate(long time)  {
        return new Date(time);
    }

    public static String getFormat(Date date){
        return DateFormatUtils.format(new Date(), "yyyy/MM/dd");
    }
}
