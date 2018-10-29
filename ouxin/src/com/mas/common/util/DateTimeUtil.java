package com.mas.common.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.mas.common.verify.VerifyUtil;

public class DateTimeUtil {
        public static final String YYYY_MM_DD = "yyyy-MM-dd";
        public static final String YYYYMMDD = "yyyyMMdd";
        public static final String HHMMSSS = " HHmmsss";
        public static final String YYYY_MM_DD_HH_MM_SSS = "yyyy-MM-dd HH:mm:sss";
        public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
        public static final String DD_MM_YYYY = "dd-MM-yyyy";
        public static final String MM_DD_YYYY = "MM-dd-yyyy";
        public static final String HH_MM_SS = " HH:mm:ss";
        public static final String YYYYMMDD_HHMMSSS = "yyyyMMdd HHmmssSSS";
        public static final String YYYYMMDDHHMMSSS = "yyyyMMddHHmmssSSS";
        public static final String YYYYMMDDHHMM = "yyyyMMddHHmm";
        // 每月天数(非润年)
        static int daysInMonth[] = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31,
                        30, 31 };
        // 闰年的特殊月份
        static final int MONTH_FEBRUARY = 2;
        public static final int PRECISE_YEAR = 1;
        public static final int PRECISE_MONTH = 2;
        public static final int PRECISE_DAY = 3;
        public static final int PRECISE_HOUR = 4;
        public static final int PRECISE_MINUTE = 5;
        public static final int PRECISE_SECOND = 6;
        public static final int PRECISE_MilliSECOND = 7;

        private static Calendar getInstance() {
                return Calendar.getInstance();
        }

        /**
         * 格式化
         * 
         * @param format
         *                默认: yyyy-MM-dd HH:mm:ss
         * @param date
         * @return
         */
        public static String format(final String format, java.util.Date date) {
                return SimpleDateFormat(format).format(date);
        }

        /**
         * 格式化类
         * 
         * @param format
         *                默认: yyyy-MM-dd HH:mm:ss
         * @return
         */
        public static SimpleDateFormat SimpleDateFormat(final String format) {
                if (VerifyUtil.isBlank(format))
                        return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SSS);
                return new SimpleDateFormat(format);
        }

        /**
         * 格式化类
         * 
         * @return
         */
        public static SimpleDateFormat SDF() {
                return new SimpleDateFormat(YYYY_MM_DD_HH_MM_SSS);
        }

        /**
         * 格式化Date
         * 
         * @param format
         * @param date
         * @return
         */
        public static String toString(final String format, java.util.Date date) {
                return format(format, date);
        }

        /**
         * 格式化java.sql.Timestamp
         * 
         * @param format
         * @param timestamp
         * @return
         */
        public static String toString(final String format,
                        java.sql.Timestamp timestamp) {
                return format(format, new Date(timestamp.getTime()));
        }

        /**
         * 字符串转Date
         * 
         * @param date
         * @return
         */
        public static java.util.Date toDate(String date) {
                try {
                        return SDF().parse(date);
                } catch (ParseException e) {
                        e.printStackTrace();
                }
                return null;
        }

        /**
         * sqlDate 转 util Date
         * 
         * @param strDate
         * @return
         */
        public static java.sql.Date toSqlDate(String strDate) {
                Date date = toDate(strDate);
                if (null != date)
                        return new java.sql.Date(date.getTime());
                return null;
        }

        
        
        /**
         * 字符串 转 sql Time
         * 
         * @param format
         * @param strDate
         * @return
         */
        public static java.sql.Time toSqlTime(final String format,
                        String strDate) {
                Date date = toDate(strDate);
                if (null != date)
                        return new java.sql.Time(date.getTime());
                return null;
        }

        /**
         * 字符串 转 sql Time
         * 
         * @param format
         * @param strDate
         * @return
         */
        public static java.sql.Time toSqlTime(String strDate) {
                Date date = toDate(strDate);
                if (null != date)
                        return new java.sql.Time(date.getTime());
                return null;
        }

        /**
         * 字符串转 timestamp
         * 
         * @param format
         * @param strDate
         * @return
         */
        public static java.sql.Timestamp toTimestamp(final String format,
                        String strDate) {
                return toTimestamp(toDate(strDate));
        }

        /**
         * util date to sql timestamp
         * 
         * @param date
         * @return
         */
        public static java.sql.Timestamp toTimestamp(java.util.Date date) {
                if (null != date)
                        return new java.sql.Timestamp(date.getTime());
                return null;
        }

        /**
         * 字符串 转 timestamp
         * 
         * @param strDate
         * @return
         */
        public static java.sql.Timestamp toTimestamp(String strDate) {
                return toTimestamp(toDate(strDate));
        }

        /**
         * 获取时间戳
         * 
         * @return java.sql.Timestamp
         */
        public static java.sql.Timestamp timestamp() {
                return new java.sql.Timestamp(System.currentTimeMillis());
        }

        /**
         * 获取格式化的时间戳 (yyyy-MM-dd HH:mm:ss)
         * 
         * @return java.sql.Timestamp
         */
        public static java.sql.Timestamp formatTimestamp() {
                return Timestamp.valueOf(format(YYYY_MM_DD_HH_MM_SSS,
                                new java.util.Date()));
        }

        /**
         * now date
         * 
         * @return
         */
        public static java.util.Date nowDate() {
                try {
                        SimpleDateFormat dateFormat = SDF();
                        return dateFormat.parse(dateFormat
                                        .format(new java.util.Date()));
                } catch (ParseException e) {
                        e.printStackTrace();
                }
                return null;
        }

        /**
         * now date
         * 
         * @return string by format
         */
        public static String format(String format) {
                return format(VerifyUtil.isBlank(format) ? YYYYMMDD_HHMMSSS
                                : format, new java.util.Date());
        }

        /**
         * sql timestamp to util date
         * 
         * @param timestamp
         * @return
         */
        public static java.util.Date timestampToDate(
                        java.sql.Timestamp timestamp) {
                return new Date(timestamp.getTime());
        }

        /**
         * util date to sql timestamp
         * 
         * @param date
         * @return
         */
        public static java.sql.Timestamp dateToTimestamp(java.util.Date date) {
                return new java.sql.Timestamp(date.getTime());
        }

        /**
         * date 的凌晨零点
         * 
         * @param date
         * @return
         */
        public static java.util.Date getDayStart(java.util.Date date) {
                return getDayStart(date, 0);
        }

        /**
         * 距离 指定 date daysLater 天的凌晨零点
         * 
         * @param date
         * @param daysLater
         * @return
         */
        public static java.util.Date getDayStart(java.util.Date date,
                        int daysLater) {
                Calendar cd = getInstance();
                cd.setTime(date);
                cd.set(cd.get(Calendar.YEAR), cd.get(Calendar.MONTH),
                                cd.get(Calendar.DAY_OF_MONTH), 0, 0, 0);
                cd.add(Calendar.DAY_OF_MONTH, daysLater);
                return cd.getTime();
        }

        /**
         * 距离date 的明天凌晨零点
         * 
         * @param date
         * @return
         */
        public static java.util.Date getNextDayStart(java.util.Date date) {
                return getDayStart(date, 1);
        }

        /**
         * date 当天的结束时间
         * 
         * @param date
         * @return
         */
        public static java.util.Date getDayEnd(java.util.Date date) {
                return getDayEnd(date, 0);
        }

        /**
         * 距离date daysLater 天的结束时间
         * 
         * @param date
         * @param daysLater
         * @return
         */
        public static java.util.Date getDayEnd(java.util.Date date,
                        int daysLater) {
                Calendar cd = getInstance();
                cd.setTime(date);
                cd.set(cd.get(Calendar.YEAR), cd.get(Calendar.MONTH),
                                cd.get(Calendar.DAY_OF_MONTH), 23, 59, 59);
                cd.add(Calendar.DAY_OF_MONTH, daysLater);
                return cd.getTime();
        }

        /**
         * 获得当前年份
         * 
         * @return
         */
        public static int getYear() {
                return Calendar.getInstance().get(Calendar.YEAR);
        }

        /**
         * 获得当前月份
         * 
         * @return
         */
        public static int getMonth() {
                return Calendar.getInstance().get(Calendar.MONTH) + 1;
        }

        /**
         * 获得今天在本年的第几天
         * 
         * @return
         */
        public static int getDayOfYear() {
                return Calendar.getInstance().get(Calendar.DAY_OF_YEAR);
        }

        /**
         * 判断是否闰月，用于计算当前时间加上分钟后的时间
         * 
         * @param year
         *                年份
         * @return
         */
        public static boolean isLeapYear(int year) {
                // 能被100整除, 不能被400整除的年份, 不是闰年.
                // 能被100整除, 也能被400整除的年份, 是闰年.
                if ((year % 100) == 0) {
                        return ((year % 400) == 0);
                } else {
                        // 不能被100整除, 能被4整除的年份是闰年.
                        return ((year % 4) == 0);
                }
        }

        /**
         * 获得今天在本月的第几天(获得当前日)
         * 
         * @return
         */
        public static int getDayOfMonth() {
                return Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        }

        /**
         * 获得今天在本周的第几天
         * 
         * @return
         */
        public static int getDayOfWeek() {
                return Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        }

        /**
         * 获得今天是当前月的第几周
         * 
         * @return
         */
        public static int getWeekOfMonth() {
                return Calendar.getInstance()
                                .get(Calendar.DAY_OF_WEEK_IN_MONTH);
        }

        /**
         * 获得半年后的日期 暂无
         * 
         * @return
         * @throws Exception
         */
        public static Date getTimeYearNext() {
                Calendar calendar = getInstance();
                calendar.add(Calendar.DAY_OF_YEAR, 183);
                return calendar.getTime();
        }

        /**
         * 根据一个日期，返回是星期几的字符串
         * 
         * @param args
         * @return
         */
        public static String getWeek(String args) {
                // 再转换为时间
                Date date = toDate(args);
                Calendar c = getInstance();
                c.setTime(date);
                // int hour=c.get(Calendar.DAY_OF_WEEK);
                // hour中存的就是星期几了，其范围 1~7
                // 1=星期日 7=星期六，其他类推
                return SimpleDateFormat("EEEE").format(c.getTime());
        }

        /**
         * 计算当月最后一天
         * 
         * @param format
         * @return
         */
        public static String getDefaultDay(String format) {
                Calendar lastDate = getInstance();
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
                lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
                return format(format, lastDate.getTime());
        }

        /**
         * 计算当月最后一天
         * 
         * @param format
         * @return
         */
        public static Date getDefaultDay() {
                Calendar lastDate = getInstance();
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                lastDate.add(Calendar.MONTH, 1);// 加一个月，变为下月的1号
                lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
                return lastDate.getTime();
        }

        /**
         * 上月第一天
         * 
         * @param format
         * @return
         */
        public static String getPreviousMonthFirst(String format) {
                Calendar lastDate = getInstance();
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                lastDate.add(Calendar.MONTH, -1);// 减一个月，变为下月的1号
                // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
                return format(format, lastDate.getTime());
        }

        /**
         * 上月最后一天
         * 
         * @param format
         * @return
         */
        public static String getPreviousMonthEnd(String format) {
                Calendar lastDate = getInstance();
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                // lastDate.add(Calendar.MONTH, num+1);// 减一个月，变为下月的1号
                lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
                return format(format, lastDate.getTime());
        }

        /**
         * 获取相距（num）月 的时间
         * 
         * @param num
         *                表示相距的月数 例如（1 或-3）
         * @param format
         * @return
         */
        public static String getBetweenMonth(int num, String format) {
                Calendar calendar = getInstance();
                calendar.add(Calendar.MONTH, num); // 调整月数
                return format(format, calendar.getTime());
        }

        /**
         * 某月第一天
         * 
         * @param num
         *                距离本月的月数 如上个月（-1）
         * @param format
         * @return
         */
        public static String getMonthFirst(int num, String format) {
                Calendar lastDate = getInstance();
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                lastDate.add(Calendar.MONTH, num);// 减一个月，变为下月的1号
                // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
                return format(format, lastDate.getTime());
        }

        /**
         * 某月第一天
         * 
         * @param date
         * @param num
         *                距离本月的月数 如上个月（-1）
         * @param format
         * @return
         */
        public static String getMonthFirst(String date, String format) {
                Calendar lastDate = getInstance();
                lastDate.setTime(toDate(date));
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                lastDate.add(Calendar.MONTH, 0);// 减一个月，变为下月的1号
                // // lastDate.add(Calendar.DATE,-1);//减去一天，变为当月最后一天
                return format(format, lastDate.getTime());
        }

        /**
         * 某月第一天
         * 
         * @param num
         *                距离本月的月数 如上个月（-1）
         * @param format
         * @return
         */
        public static Date getMonthFirst(int num) {
                Calendar lastDate = getInstance();
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                lastDate.add(Calendar.MONTH, num);// 减一个月，变为下月的1号
                return lastDate.getTime();
        }

        /**
         * 某月最后一天
         * 
         * @param num
         *                距离本月的月数 如上个月（-1）
         * @param format
         * @return
         */
        public static String getMonthEnd(int num, String format) {
                Calendar lastDate = getInstance();
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                lastDate.add(Calendar.MONTH, num + 1);// 减一个月，变为下月的1号
                lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
                return format(format, lastDate.getTime());
        }

        /**
         * 某月最后一天
         * 
         * @param date
         * @param format
         * @return
         */
        public static String getMonthEnd(String date, String format) {
                Calendar lastDate = getInstance();
                lastDate.setTime(toDate(date));
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                lastDate.add(Calendar.MONTH, 1);// 减一个月，变为下月的1号
                lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
                return format(format, lastDate.getTime());
        }

        /**
         * 某月最后一天
         * 
         * @param num
         * @param format
         * @return
         */
        public static Date getMonthEnd(int num) {
                Calendar lastDate = getInstance();
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                lastDate.add(Calendar.MONTH, num + 1);// 减一个月，变为下月的1号
                lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
                return lastDate.getTime();
        }

        /**
         * 获取当月第一天
         * 
         * @param format
         * @return
         */
        public static String getFirstDayOfMonth(String format) {
                Calendar lastDate = getInstance();
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                return format(format, lastDate.getTime());
        }

        /**
         * 获取当月第一天
         * 
         * @param format
         * @return
         */
        public static Date getFirstDayOfMonth() {
                Calendar lastDate = getInstance();
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                return lastDate.getTime();
        }

        /**
         * 获得本周星期日的日期
         * 
         * @return
         */
        public static String getCurrentWeekday(String format) {
                // weeks = 0;
                int mondayPlus = getMondayPlus();
                GregorianCalendar currentDate = new GregorianCalendar();
                currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
                Date monday = currentDate.getTime();
                return toString(format, monday);
        }

        /**
         * 获得本周星期日的日期
         * 
         * @return
         */
        public static String getCurrentWeekday() {
                // weeks = 0;
                int mondayPlus = getMondayPlus();
                GregorianCalendar currentDate = new GregorianCalendar();
                currentDate.add(GregorianCalendar.DATE, mondayPlus + 6);
                Date monday = currentDate.getTime();
                return DateFormat.getDateInstance().format(monday);
        }

        /**
         * 获得当前日期与本周日相差的天数
         * 
         * @return
         */
        public static int getMondayPlus() {
                Calendar cd = getInstance();
                // 获得今天是一周的第几天，星期日是第一天，星期二是第二天......
                int dayOfWeek = cd.get(Calendar.DAY_OF_WEEK) - 1; // 因为按中国礼拜一作为第一天所以这里减1
                if (dayOfWeek == 1) {
                        return 0;
                } else {
                        return 1 - dayOfWeek;
                }
        }

        /**
         * 获得本周一的日期
         * 
         * @return
         */
        public static String getMondayOFWeek() {
                // weeks = 0;
                int mondayPlus = getMondayPlus();
                GregorianCalendar currentDate = new GregorianCalendar();
                currentDate.add(GregorianCalendar.DATE, mondayPlus);
                Date monday = currentDate.getTime();
                return DateFormat.getDateInstance().format(monday);
        }

        /**
         * 获得某月天数
         * 
         * @param args
         * @param separator
         *                "/" "-" 时间格式的分隔符
         * @return
         */
        @SuppressWarnings("static-access")
        public static int getMonthDay(String args, String separator) {
                String[] str = args.split(separator);
                Calendar calendar = getInstance();
                int months = Integer.parseInt(str[1]);
                int years = Integer.parseInt(str[0]);
                calendar.set(years, months - 1, 1);
                return calendar.getActualMaximum(calendar.DAY_OF_MONTH);
        }

        /**
         * 两个字符时间相隔的天数
         * 
         * @param argsBegin
         * @param argsEnd
         * @param format
         *                "yyyy-MM-dd"
         * @return
         */
        /*
         * public static long dateDiff( String argsBegin, String argsEnd ) {
         * Date dateBegin = toDate( argsBegin ); Date dateEnd = toDate( argsEnd
         * ); long timeBegin = dateBegin.getTime(); long timeEnd =
         * dateEnd.getTime(); long diff = Math.abs( timeBegin - timeEnd ); diff
         * /= 3600 * 1000 * 24; return diff; }
         */
        
       
        /**
         * 两个字符时间相隔的天数
         * 
         * @param argsBegin
         * @param argsEnd
         * @param format
         *                "yyyy-MM-dd"
         * @return
         */
        public static long dateDiff(String argsBegin, String argsEnd) {
                Date dateBegin = null;
                Date dateEnd = null;
                try {
                        dateBegin = new SimpleDateFormat(YYYY_MM_DD)
                                        .parse(argsBegin);
                        dateEnd = new SimpleDateFormat(YYYY_MM_DD)
                                        .parse(argsEnd);

                        long timeBegin = dateBegin.getTime();
                        long timeEnd = dateEnd.getTime();
                        long diff = Math.abs(timeBegin - timeEnd);
                        diff /= 3600 * 1000 * 24;
                        return diff;
                } catch (ParseException e) {
                        e.printStackTrace();
                }

                return -1;
        }
        
        public static void main(String[] args) {
            //long I = DateTimeUtil.dateDiff(DateTimeUtil.format(YYYY_MM_DD_HH_MM_SSS), "2017-06-18 HH:mm:sss");
            //System.out.println(I + "");
            
            String i = "0.0";
            float e = Float.parseFloat(i);
        }
        

        /**
         * 获得相隔当天的时间
         * 
         * @param num
         *                -n 或 n
         * @return String "yyyy-MM-dd HH:mm:ss"
         */
        public static String getYesterday(int num, String format) {
                Calendar calendar = getInstance();
                GregorianCalendar gc = new GregorianCalendar(
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MARCH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                gc.add(Calendar.DATE, num);
                return format(format, gc.getTime());
        }

        /**
         * 获得相隔当天的时间
         * 
         * @param num
         *                -n 或 n
         * @return Date "yyyy-MM-dd HH:mm:ss"
         */
        public static Date getYesterday(String format, int num) {
                Calendar calendar = getInstance();
                GregorianCalendar gc = new GregorianCalendar(
                                calendar.get(Calendar.YEAR),
                                calendar.get(Calendar.MARCH),
                                calendar.get(Calendar.DAY_OF_MONTH));
                gc.add(Calendar.DATE, num);
                return gc.getTime();
        }

        /**
         * 获取相对当前周某周周一日期
         * 
         * @param num
         * @param format
         * @return
         */
        public static String getWeekday(int num, String format) {
                Calendar calendar = getInstance();
                calendar.add(Calendar.DATE, num * 7);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                return format(format, calendar.getTime());
        }

        /**
         * 获取相对当前周某周周日日期
         * 
         * @param num
         *                -1 上一周 0 本周 1 下一周
         * @param format
         *                "yyyy-MM-dd"
         * @return
         */
        public static String getWeekSunday(int num, String format) {
                Calendar calendar = getInstance();
                calendar.setFirstDayOfWeek(Calendar.MONDAY);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                calendar.add(Calendar.WEEK_OF_MONTH, num);
                return format(format, calendar.getTime());
        }

        /**
         * 获取上周一日期
         * 
         * @param num
         * @param format
         * @return
         */
        public static String getPreviousWeekday(String format) {
                Calendar calendar = getInstance();
                calendar.add(Calendar.DATE, -1 * 7);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                return format(format, calendar.getTime());
        }

        /**
         * 获取上周日日期
         * 
         * @param num
         *                -1 上一周 0 本周 1 下一周
         * @param format
         *                "yyyy-MM-dd"
         * @return
         */
        public static String getPreviousWeekSunday(String format) {
                Calendar calendar = getInstance();
                calendar.setFirstDayOfWeek(Calendar.MONDAY);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                calendar.add(Calendar.WEEK_OF_MONTH, -1);
                return format(format, calendar.getTime());
        }

        /**
         * 获取下周一日期
         * 
         * @param format
         * @return
         */
        public static String getNextMonday(String format) {
                Calendar calendar = getInstance();
                calendar.add(Calendar.DATE, 1 * 7);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                return format(format, calendar.getTime());
        }

        /**
         * 获取下周日日期
         * 
         * @param format
         *                "yyyy-MM-dd"
         * @return
         */
        public static String getNextSunday(String format) {
                Calendar calendar = getInstance();
                calendar.setFirstDayOfWeek(Calendar.MONDAY);
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                calendar.add(Calendar.WEEK_OF_MONTH, 1);
                return format(format, calendar.getTime());
        }

        /**
         * 下月第一天
         * 
         * @param format
         * @return
         */
        public static String getNextMonthFirst(String format) {
                Calendar lastDate = getInstance();
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                lastDate.add(Calendar.MONTH, 1);//
                return format(format, lastDate.getTime());
        }

        /**
         * 下月最后一天
         * 
         * @param format
         * @return
         */
        public static String getNextMonthEnd(String format) {
                Calendar lastDate = getInstance();
                lastDate.set(Calendar.DATE, 1);// 设为当前月的1号
                lastDate.add(Calendar.MONTH, 2);
                lastDate.add(Calendar.DATE, -1);// 减去一天，变为当月最后一天
                return format(format, lastDate.getTime());
        }

        /**
         * 获得一个日期所在周的星期几的日期
         * 
         * @param args
         * @param num
         *                星期一 对应1 以此类推
         * @param format
         * @return
         */
        public static String getWeek(String args, String num, String format) {
                Date date = toDate(args);
                Calendar calendar = getInstance();
                calendar.setTime(date);
                if ("1".equals(num)) {
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                } else if ("2".equals(num)) {
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.TUESDAY);
                } else if ("3".equals(num)) {
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.WEDNESDAY);
                } else if ("4".equals(num)) {
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.THURSDAY);
                } else if ("5".equals(num)) {
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
                } else if ("6".equals(num)) {
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
                } else if ("0".equals(num)) {
                        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                }
                return format(format, calendar.getTime());
        }

        /**
         * 比较两个时间的大小 args1 > args2 返回true
         * 
         * @param args1
         * @param args2
         * @param format
         * @return
         */
        public static boolean compareTo(String args1, String args2,
                        String format) {
                Date date1 = toDate(args1);
                Date date2 = toDate(args2);
                if (null == date1 || null == date2)
                        return false;
                return compareTo(date1, date2);
        }

        /**
         * 时间 args1 是否小于 时间args2 一天以上
         * 
         * @param args1
         * @param args2
         * @return
         */
        public static boolean compareToLessThan(Date args1, Date args2) {
                return !compareToGreater(args1, args2);
        }

        /**
         * 时间 args1 是否大于 时间args2 一天以上
         * 
         * @param args1
         * @param args2
         * @return
         */
        public static boolean compareToGreater(Date args1, Date args2) {
                long time1 = args1.getTime();
                long time2 = args2.getTime();
                if (time1 < time2)
                        return false;
                long diff = Math.abs(time1 - time2);
                diff /= 3600 * 1000 * 24;
                return (diff > 0);
        }

        /**
         * 比较两个时间的大小 date1 > date2 返回true
         * 
         * @param date1
         * @param date2
         * @return
         */
        public static boolean compareTo(Date date1, Date date2) {
                return date1.compareTo(date2) == 1;
        }

        /**
         * 获取某年第一天日期
         * 
         * @param year
         *                年份(2013)
         * @return
         */
        public static String getYearFirst(int year, String fromat) {
                Calendar calendar = getInstance();
                calendar.clear();
                calendar.set(Calendar.YEAR, year);
                return format(fromat, calendar.getTime());
        }

        /**
         * 获取某年最后一天
         * 
         * @param year
         *                年份(2013)
         * @return
         */
        public static String getYearLast(int year, String fromat) {
                Calendar calendar = getInstance();
                calendar.clear();
                calendar.set(Calendar.YEAR, year);
                calendar.roll(Calendar.DAY_OF_YEAR, -1);
                return format(fromat, calendar.getTime());
        }

        /**
         * date 是否大于min 小于max (time)
         * 
         * @param min
         * @param max
         * @param date
         * @return
         */
        public static boolean compare(Date min, Date max, Date date) {
                return (date.getTime() >= min.getTime())
                                && (date.getTime() <= max.getTime());
        }

        /**
         * date 是否大于min 小于max (time)
         * 
         * @param min
         * @param max
         * @param date
         * @return
         */
        public static boolean compare(String min, String max, Date date) {
                long d = date.getTime();
                return (d >= getTime(min)) && (d <= getTime(max));
        }

        public static long getTime(String date) {
                return toDate(date).getTime();
        }

        /**
         * date 是否大于min 小于max (time)
         * 
         * @param min
         * @param max
         * @param date
         * @return
         */
        public static boolean compare(String min, String max, String date) {
                Long d = getTime(date);
                return (d >= getTime(min)) && (d <= getTime(max));
        }

        public static Date addYears(Date date, int amount) {
                return add(date, 1, amount);
        }

        public static Date addMonths(Date date, int amount) {
                return add(date, 2, amount);
        }

        public static Date addWeeks(Date date, int amount) {
                return add(date, 3, amount);
        }

        public static Date addDays(Date date, int amount) {
                return add(date, 5, amount);
        }

        public static Date addHours(Date date, int amount) {
                return add(date, 11, amount);
        }

        public static Date addMinutes(Date date, int amount) {
                return add(date, 12, amount);
        }

        public static Date addSeconds(Date date, int amount) {
                return add(date, 13, amount);
        }

        public static Date addMilliseconds(Date date, int amount) {
                return add(date, 14, amount);
        }

        public static Date add(Date date, int calendarField, int amount) {
                if (date == null)
                        throw new IllegalArgumentException(
                                        "The date must not be null");
                Calendar c = getInstance();
                c.setTime(date);
                c.add(calendarField, amount);
                return c.getTime();
        }

        public static Date before(Date date, int month) {
                Calendar c = Calendar.getInstance();
                c.setTime(date);
                int nowMonth = c.get(Calendar.MONTH);
                c.set(Calendar.MONTH, nowMonth - month);
                return c.getTime();
        }

        /*
         * public static void main(String[] args) { String f =
         * DateTimeUtil.YYYY_MM_DD_HH_MM_SSS; Date d = new Date(); Date d2 =
         * DateTimeUtil.toDate("2013-07-04 10:03:06"); String s =
         * DateTimeUtil.toString(f, d2); String s2 = DateTimeUtil.toString(f,
         * d); Timestamp t = new Timestamp(System.currentTimeMillis()); //
         * P(DateTimeUtil.toString(f,t)); // P(DateTimeUtil.toString(f,t)); //
         * P(DateTimeUtil.format(f,d)); // P(DateTimeUtil.timestamp()); //
         * P(DateTimeUtil.toDate(s)); // P(DateTimeUtil.toSqlDate(s)); //
         * P(DateTimeUtil.toSqlTime(f,s)); // P(DateTimeUtil.toSqlTime(s)); //
         * P(DateTimeUtil.toTimestamp(d)); // P(DateTimeUtil.toTimestamp(f,s));
         * // P(DateTimeUtil.toTimestamp(s)); //
         * P(DateTimeUtil.formatTimestamp()); P(DateTimeUtil.nowDate()); //
         * P(DateTimeUtil.timestampToDate(t)); //
         * P(DateTimeUtil.dateToTimestamp(d)); //
         * P(DateTimeUtil.getDayStart(d)); // P(DateTimeUtil.getDayStart(d,1));
         * // P(DateTimeUtil.getNextDayStart(d)); //
         * P(DateTimeUtil.getDayEnd(d,2)); // P(DateTimeUtil.getDayEnd(d)); //
         * P(DateTimeUtil.getYear()); // P(DateTimeUtil.getMonth()); //
         * P(DateTimeUtil.getDayOfYear()); // P(DateTimeUtil.isLeapYear(2012));
         * // P(DateTimeUtil.getDayOfMonth()); //
         * P(DateTimeUtil.getDayOfWeek()); // P(DateTimeUtil.getWeekOfMonth());
         * // P(DateTimeUtil.getTimeYearNext()); P(DateTimeUtil.getWeek(s2));
         * P(DateTimeUtil.getWeek(s,"1",f)); // P(DateTimeUtil.getDefaultDay());
         * // P(DateTimeUtil.getDefaultDay(f)); //
         * P(DateTimeUtil.getPreviousMonthFirst(f)); //
         * P(DateTimeUtil.getPreviousMonthEnd(f)); //
         * P(DateTimeUtil.getMonthFirst(1)); //
         * P(DateTimeUtil.getMonthFirst(-1,f)); //
         * P(DateTimeUtil.getMonthEnd(1)); // P(DateTimeUtil.getMonthEnd(-1,f));
         * // P(DateTimeUtil.getFirstDayOfMonth(f)); //
         * P(DateTimeUtil.getFirstDayOfMonth()); //
         * P(DateTimeUtil.getCurrentWeekday(f)); //
         * P(DateTimeUtil.getCurrentWeekday()); //
         * P(DateTimeUtil.getMondayPlus()); //
         * P(DateTimeUtil.getMondayOFWeek()); //
         * P(DateTimeUtil.getMonthDay(s,"-")); //
         * P(DateTimeUtil.dateDiff(s,s2)); //
         * P(DateTimeUtil.getYesterday(20,f)); //
         * P(DateTimeUtil.getWeekday(-1,f)); //
         * P(DateTimeUtil.getWeekSunday(-1,f)); //
         * P(DateTimeUtil.getPreviousWeekday(f)); //
         * P(DateTimeUtil.getPreviousWeekSunday(f)); //
         * P(DateTimeUtil.getNextMonday(f)); //
         * P(DateTimeUtil.getNextSunday(f)); //
         * P(DateTimeUtil.getNextMonthFirst(f)); //
         * P(DateTimeUtil.getNextMonthEnd(f)); //
         * P(DateTimeUtil.getTimeStepSize(d,d2)); //
         * P(DateTimeUtil.getTimeStepSize(s2,s,f)); //
         * P(DateTimeUtil.getYearFirst(2013,f)); //
         * P(DateTimeUtil.getYearLast(2012,f));
         * 
         * }
         */
        public static void P(Object obj) {
                System.out.println(obj);
        }
}
