package com.guoanshequ.dc.das.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;


public class DateUtils {
    /** 
     * 将日期字符串转化为日期。失败返回null。 
     * @param date 日期字符串 
     * @return 日期 
     * @throws Exception 
     */  
    public static Date StringToDate(String date) throws Exception {  
    	SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");  
        return dateformat.parse(date);  
    } 
	
	/** 
     * 获取两个日期相差的天数 
     * @param date 日期字符串 
     * @param otherDate 另一个日期字符串 
     * @return 相差天数。如果失败则返回-1 
	 * @throws Exception 
     */  
    public static int getIntervalDays(String date, String otherDate) throws Exception {  
        return getIntervalDays(StringToDate(date), StringToDate(otherDate));  
    }  
  
    /** 
     * @param date 日期 
     * @param otherDate 另一个日期 
     * @return 相差天数。如果失败则返回-1 
     */  
    public static int getIntervalDays(Date date, Date otherDate) {  
        int num = -1;  
        Date dateTmp = date;  
        Date otherDateTmp = otherDate;  
        if (date != null && otherDate != null) {  
            long time = Math.abs(dateTmp.getTime() - otherDateTmp.getTime());  
            num = (int) (time / (24 * 60 * 60 * 1000));  
        }  
        return num;  
    }  
    
    /** 
     * @param date 当前日期 
     * @return 返回当前日期上月的月初和月末日期
     */ 
    public static Map<String, String> getFirstday_Lastday_Month(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date theDate = (Date) calendar.getTime();
        
        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first);
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }
    
    /** 
     * @param date 当前日期 
     * @return 返回当前日期上月的月初和月末日期
     */ 
    public static Map<String, String> getPreYearMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, -1);
        Date theDate = (Date) calendar.getTime();
        
        //上个月第一天
        GregorianCalendar gcLast = (GregorianCalendar) Calendar.getInstance();
        gcLast.setTime(theDate);
        gcLast.set(Calendar.DAY_OF_MONTH, 1);
        String day_first = df.format(gcLast.getTime());
        StringBuffer str = new StringBuffer().append(day_first);
        day_first = str.toString();

        //上个月最后一天
        calendar.add(Calendar.MONTH, 1);    //加一个月
        calendar.set(Calendar.DATE, 1);        //设置为该月第一天
        calendar.add(Calendar.DATE, -1);    //再减一天即为上个月最后一天
        String day_last = df.format(calendar.getTime());
        StringBuffer endStr = new StringBuffer().append(day_last);
        day_last = endStr.toString();

        Map<String, String> map = new HashMap<String, String>();
        map.put("first", day_first);
        map.put("last", day_last);
        return map;
    }
    
    /** 
     * 
     * @return 返回当前日期前一小时的开始时间和结束时间
     * 如：2017-08-28 09:00:00 >= time < 2017-08-28 10:00:00
     */ 
    public static Map<String, String> getPreHoursTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:00:00");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - 1); 
        String pre_hour_begin = df.format(calendar.getTime());
        String pre_hour_end = df.format(new Date());
        
        Map<String, String> map = new HashMap<String, String>();
        map.put("preHourbegin", pre_hour_begin);
        map.put("preHourend", pre_hour_end);
        System.out.println(map);
        return map;
    }
    
    /** 
     * @param date 当前日期 
     * @return 返回当前日期的前一天
     * 功能说明：
     * 日期2017-09-10，返回2017-09-09;
     * 日期2017-09-01，返回2017-08-31;
     */ 
    public static String getPreDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String preDate = df.format(calendar.getTime());
        return preDate;
    }
    
    /** 
     * @param date 当前日期 
     * @return 返回当前日期的所在月份的1号
     */ 
    public static String getPreDateFirstOfMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String preDateFirst = df.format(calendar.getTime());
        preDateFirst += "-01";
        return preDateFirst;
    }
    
    /** 
     * @param date 当前日期 
     * @return 返回当前日期前一天所在的年月
     * 功能说明：
     * 日期2017-09-10，返回"2017-09";
     * 日期2017-09-01，返回"2017-08";
     */ 
    public static String getPreYearAndMonth(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        String preDateFirst = df.format(calendar.getTime());
        return preDateFirst;
    }
    
    /** 
     * @param date 当前日期 
     * @return 返回当前日期前一天的上月
     * 功能说明：
     * 日期2017-09-10，返回"2017-08";
     * 日期2017-09-01，返回"2017-07";
     */
    public static String getPreMonthofYear(){
    	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        calendar.add(Calendar.MONTH, -1);
        String preDateFirst = df.format(calendar.getTime());
        return preDateFirst;
    }
    
    /** 
     * @param date 当前日期 
     * @return 返回当前日期
     * 功能说明：将当前日期转换成String类型
     */ 
    public static String getCurDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String curDate = df.format(calendar.getTime());
        return curDate;
    }

    /** 
     * @param date 当前时间
     * @return 返回当前时间
     * 功能说明：将当前时间转换成String类型
     */ 
    public static String getCurTime(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        String curTime = df.format(calendar.getTime());
        return curTime;
    }
    
    public static void main(String[] args) {
		System.out.println(getPreMonthofYear());
	}
}
