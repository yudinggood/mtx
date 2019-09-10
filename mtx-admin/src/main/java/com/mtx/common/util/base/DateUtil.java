package com.mtx.common.util.base;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    //传入时间在n分钟以内
    public static boolean inMinute(Date initDate,int minute){
        Calendar c1= Calendar.getInstance();
        Calendar c2=Calendar.getInstance();
        Calendar c3=Calendar.getInstance();
        c1.setTime(initDate);//要判断的日期
        c2.setTime(new Date());//初始日期
        c3.setTime(new Date());//也给初始日期 把分钟加五
        c3.add(Calendar.MINUTE, minute);
        c2.add(Calendar.MINUTE,-minute);//减去五分钟

        return c1.after(c2)&&c1.before(c3);
    }

    /**
     * 字符串转换成日期
     * @param str
     * @return
     */
    public static Date StrToDate(String str) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = null;
        try {
            date = format.parse(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }
}
