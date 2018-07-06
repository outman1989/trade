package util;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


/**
 * 日期工具类
 *
 * @author lx
 * @since 2012-11-26
 */
public class DateUtil {

    /**
     * 增加小时
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date AddHour(Date date, int hour) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.HOUR, hour);
        return startDT.getTime();
    }

    /**
     * 增加分钟
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date AddMinute(Date date, int minute) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.MINUTE, minute);
        return startDT.getTime();
    }

    /**
     * 增加天数
     *
     * @param date 日期
     * @param num  需增加天的数
     * @return
     */
    public static Date addDay(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.DAY_OF_MONTH, num);
        return startDT.getTime();
    }

    /**
     * 增加月份数;
     *
     * @param date 日期
     * @param num  需增加月的数
     * @return
     */
    public static Date addMonth(Date date, int num) {
        Calendar startDT = Calendar.getInstance();
        startDT.setTime(date);
        startDT.add(Calendar.MONTH, num);
        return startDT.getTime();
    }

    /**
     * 比较日期;
     *
     * @param date
     * @return
     * @throws ParseException
     */
    public static Date CompareToDate(Date date) throws ParseException {
        // 当前日期
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date nowDate = calendar.getTime();
        DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = f.parse(date.toString());
        // 比较
        int i = nowDate.compareTo(date1);
        if (i == 1) {
            // 小于当前日期时，返回当天+1
            date = addDay(nowDate, 1);
        }
        return date;
    }

    /**
     * 验证String是否为日期
     *
     * @param str
     * @param pattern
     * @return
     */
    public static boolean isDate(String str, String pattern) {
        boolean bool = true;
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        try {
            df.parse(str);
            return bool;
        } catch (ParseException e) {
            bool = false;
            return bool;
        }
    }

    public static String getyyyyMMddHHmmssSSSString(Date date) {
        return formatDate(date, "yyyyMMddHHmmssSSS");
    }

    public static int getMILLISECOND() {
        return getCa().get(14);
    }

    public static Date parseDate(String dateString, String pattern) {
        try {
            SimpleDateFormat globolDateFormat = new SimpleDateFormat();
            globolDateFormat.applyPattern(pattern);
            return globolDateFormat.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String formatDate(Date date, String pattern) {
        try {
            SimpleDateFormat globolDateFormat = new SimpleDateFormat();
            globolDateFormat.applyPattern(pattern);
            return globolDateFormat.format(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateByPattern(String dateString, String interval,
                                        String[] strings) {
        StringBuffer sb = new StringBuffer();
        Integer index = Integer.valueOf(0);
        @SuppressWarnings("unused")
        Integer localInteger1;
        for (String str : strings) {
            if (0 != index.intValue()) {
                sb.append(interval);
            }
            sb.append(str);
            localInteger1 = index;
            @SuppressWarnings("unused")
            Integer localInteger2 = index = Integer
                    .valueOf(index.intValue() + 1);
        }
        return parseDate(dateString, sb.toString());
    }

    public static String getStringByPattern(Date date, String interval,
                                            String[] strings) {
        StringBuffer sb = new StringBuffer();
        Integer index = Integer.valueOf(0);
        @SuppressWarnings("unused")
        Integer localInteger1;
        for (String str : strings) {
            if (0 != index.intValue()) {
                sb.append(interval);
            }
            sb.append(str);
            localInteger1 = index;
            @SuppressWarnings("unused")
            Integer localInteger2 = index = Integer
                    .valueOf(index.intValue() + 1);
        }
        return formatDate(date, sb.toString());
    }

    public static Date getDateByPatternStringArray(String dateString,
                                                   String interval, String[] strings) {
        StringBuffer sb = new StringBuffer();
        Integer index = Integer.valueOf(0);
        @SuppressWarnings("unused")
        Integer localInteger1;
        for (String str : strings) {
            if (0 != index.intValue()) {
                sb.append(interval);
            }
            sb.append(str);
            localInteger1 = index;
            @SuppressWarnings("unused")
            Integer localInteger2 = index = Integer
                    .valueOf(index.intValue() + 1);
        }
        return parseDate(dateString, sb.toString());
    }

    public static String getStringByPatternStringArray(Date date,
                                                       String interval, String[] strings) {
        StringBuffer sb = new StringBuffer();
        Integer index = Integer.valueOf(0);
        @SuppressWarnings("unused")
        Integer localInteger1;
        for (String str : strings) {
            if (0 != index.intValue()) {
                sb.append(interval);
            }
            sb.append(str);
            localInteger1 = index;
            @SuppressWarnings("unused")
            Integer localInteger2 = index = Integer
                    .valueOf(index.intValue() + 1);
        }
        return formatDate(date, sb.toString());
    }

    public static Date getDateTimeByPattern(String dateString,
                                            String dateInterval, String[] dateArray, String timeInterval,
                                            String[] timeArray) {
        StringBuffer dateSB = new StringBuffer();
        Integer index = Integer.valueOf(0);
        @SuppressWarnings("unused")
        Integer localInteger1;
        @SuppressWarnings("unused")
        Integer localInteger2;
        for (String str : dateArray) {
            if (0 != index.intValue()) {
                dateSB.append(dateInterval);
            }
            dateSB.append(str);
            localInteger1 = index;
            localInteger2 = index = Integer.valueOf(index.intValue() + 1);
        }
        dateSB.append(" ");
        index = Integer.valueOf(0);
        for (String str : timeArray) {
            if (0 != index.intValue()) {
                dateSB.append(timeInterval);
            }
            dateSB.append(str);
            localInteger1 = index;
            localInteger2 = index = Integer.valueOf(index.intValue() + 1);
        }
        return parseDate(dateString, dateSB.toString());
    }

    public static String getStringByPattern(Date date, String dateInterval,
                                            String[] dateArray, String timeInterval, String[] timeArray) {
        StringBuffer dateSB = new StringBuffer();
        Integer index = Integer.valueOf(0);
        @SuppressWarnings("unused")
        Integer localInteger1;
        @SuppressWarnings("unused")
        Integer localInteger2;
        for (String str : dateArray) {
            if (0 != index.intValue()) {
                dateSB.append(dateInterval);
            }
            dateSB.append(str);
            localInteger1 = index;
            localInteger2 = index = Integer.valueOf(index.intValue() + 1);
        }
        dateSB.append(" ");
        index = Integer.valueOf(0);
        for (String str : timeArray) {
            if (0 != index.intValue()) {
                dateSB.append(timeInterval);
            }
            dateSB.append(str);
            localInteger1 = index;
            localInteger2 = index = Integer.valueOf(index.intValue() + 1);
        }
        return formatDate(date, dateSB.toString());
    }

    public static Date getDateByyyyy_MM_dd(String dateString) {
        return parseDate(dateString, "yyyy-MM-dd");
    }

    public static String getyyyy_MM_ddString(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    public static Date getDateByyyyy_MM(String dataString) {
        return parseDate(dataString, "yyyy-MM");
    }

    public static String getyyyy_MMString(Date date) {
        return formatDate(date, "yyyy-MM");
    }

    public static Date getDateByMM_dd(String dataString) {
        return parseDate(dataString, "MM-dd");
    }

    public static String getMM_ddString(Date date) {
        return formatDate(date, "MM-dd");
    }

    public static Date getDateByyyyyMMdd(String dateString) {
        return parseDate(dateString, "yyyyMMdd");
    }

    public static String getyyyyMMddString(Date date) {
        return formatDate(date, "yyyyMMdd");
    }

    public static Date getDateByyyyyMM(String dataString) {
        return parseDate(dataString, "yyyyMM");
    }

    public static String getyyyyMMString(Date date) {
        return formatDate(date, "yyyyMM");
    }

    public static Date getDateByMMdd(String dataString) {
        return parseDate(dataString, "MMdd");
    }

    public static String getMMddString(Date date) {
        return formatDate(date, "MMdd");
    }

    public static Date getDateByMMyyyySlash(String dateString) {
        return parseDate(dateString, "MM/yyyy");
    }

    public static String getMMyyyySlashString(Date date) {
        return formatDate(date, "MM/yyyy");
    }

    public static Date getDateByddMMyyyySlash(String dateString) {
        return parseDate(dateString, "dd/MM/yyyy");
    }

    public static String getddMMyyyySlashString(Date date) {
        return formatDate(date, "dd/MM/yyyy");
    }

    public static Date getDateByMMddyyyySlash(String dateString) {
        return parseDate(dateString, "MM/dd/yyyy");
    }

    public static String getMMddyyyySlashString(Date date) {
        return formatDate(date, "MM/dd/yyyy");
    }

    public static Date getDateByyyyy_MM_dd_HH_mm(String dateString) {
        return parseDate(dateString, "yyyy-MM-dd HH:mm");
    }

    public static String getyyyy_MM_dd_HH_mmString(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm");
    }

    public static Date getDateByyyyy_MM_dd_HH_mm_ss(String dateString) {
        return parseDate(dateString, "yyyy-MM-dd HH:mm:ss");
    }

    public static String getyyyy_MM_dd_HH_mm_ssString(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static Date getDateByyyyyMMddHHmm(String dateString) {
        return parseDate(dateString, "yyyy-MM-dd HHmm");
    }

    public static String getyyyyMMddHHmmString(Date date) {
        return formatDate(date, "yyyy-MM-dd HHmm");
    }

    public static Date getDateByyyyyMMddHHmmss(String dateString) {
        return parseDate(dateString, "yyyyMMdd HHmmss");
    }

    public static String getyyyyMMddHHmmssString(Date date) {
        return formatDate(date, "yyyyMMddHHmmss");
    }

    public static Date getDateByMMddyyyySlashHHmm(String dateString) {
        return parseDate(dateString, "MM/dd/yyyy HH:mm");
    }

    public static String getDateByMMddyyyySlashHHmmString(Date date) {
        return formatDate(date, "MM/dd/yyyy HH:mm");
    }

    public static Date getDateByMMddyyyySlashHHmmss(String dateString) {
        return parseDate(dateString, "MM/dd/yyyy HH:mm:ss");
    }

    public static String getMMddyyyySlashHHmmssString(Date date) {
        return formatDate(date, "MM/dd/yyyy HH:mm:ss");
    }

    public static Date getDateByddMMyyyySlashHHmm(String dateString) {
        return parseDate(dateString, "dd/MM/yyyy HH:mm");
    }

    public static String getddMMyyyySlashHHmmString(Date date) {
        return formatDate(date, "dd/MM/yyyy HH:mm");
    }

    public static Date getDateByddMMyyyySlashHHmmss(String dateString) {
        return parseDate(dateString, "dd/MM/yyyy HH:mm:ss");
    }

    public static String getddMMyyyySlashHHmmssString(Date date) {
        return formatDate(date, "dd/MM/yyyy HH:mm:ss");
    }

    public static String getHHmm(Date date) {
        return formatDate(date, "HHmm");
    }

    public static String getHH_mm(Date date) {
        return formatDate(date, "HH:mm");
    }

    public static String getHHmmss(Date date) {
        return formatDate(date, "HHmmss");
    }

    public static String getHH_mm_ss(Date date) {
        return formatDate(date, "HH:mm:ss");
    }

    public static Calendar getCa() {
        return Calendar.getInstance();
    }

    public static Date getDate() {
        return new Date();
    }

    public static Calendar getCa(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca;
    }

    public static Integer getYear() {
        return Integer.valueOf(getCa().get(1));
    }

    public static Integer getYear(Date date) {
        return Integer.valueOf(getCa(date).get(1));
    }

    public static Integer getMonth() {
        return Integer.valueOf(getCa().get(2) + 1);
    }

    public static Integer getMonth(Date date) {
        return Integer.valueOf(getCa(date).get(2) + 1);
    }

    public static Integer getDay() {
        return Integer.valueOf(getCa().get(5));
    }

    public static Integer getDay(Date date) {
        return Integer.valueOf(getCa(date).get(5));
    }

    public static Integer getWeek() {
        return Integer.valueOf(getCa().get(7));
    }

    public static Integer getWeek(Date date) {
        return Integer.valueOf(getCa(date).get(7));
    }

    public static Integer getHour() {
        return Integer.valueOf(getCa().get(11));
    }

    public static Integer getMinute() {
        return Integer.valueOf(getCa().get(12));
    }

    public static Integer getSecond() {
        return Integer.valueOf(getCa().get(13));
    }

    public static Integer getDayOfYear() {
        return Integer.valueOf(getCa().get(6));
    }

    public static Integer getDayOfYear(Date date) {
        return Integer.valueOf(getCa(date).get(6));
    }

    public static Date getAddDay(Date date, Integer i) {
        Calendar ca = getCa(date);
        ca.set(5, ca.get(5) + i.intValue());
        return ca.getTime();
    }

    public static Date getAddMonth(Date date, Integer i) {
        Calendar ca = getCa(date);
        ca.set(2, ca.get(2) + i.intValue());
        return ca.getTime();
    }

    public static Date getAddYear(Date date, Integer i) {
        Calendar ca = getCa(date);
        ca.set(1, ca.get(1) + i.intValue());
        return ca.getTime();
    }

    public static Date getAddHour(Date date, Integer i) {
        Calendar ca = getCa(date);
        ca.set(11, ca.get(11) + i.intValue());
        return ca.getTime();
    }

    public static Date getAddMinute(Date date, Integer i) {
        Calendar ca = getCa(date);
        ca.set(12, ca.get(12) + i.intValue());
        return ca.getTime();
    }

    public static Date getAddDay(Integer i) {
        Calendar ca = getCa();
        ca.set(5, ca.get(5) + i.intValue());
        return ca.getTime();
    }

    public static Date getAddMonth(Integer i) {
        Calendar ca = getCa();
        ca.set(2, ca.get(2) + i.intValue());
        return ca.getTime();
    }

    public static Date getAddYear(Integer i) {
        Calendar ca = getCa();
        ca.set(1, ca.get(1) + i.intValue());
        return ca.getTime();
    }

    public static Date getAddHour(Integer i) {
        Calendar ca = getCa();
        ca.set(11, ca.get(11) + i.intValue());
        return ca.getTime();
    }

    public static Date getAddMinute(Integer i) {
        Calendar ca = getCa();
        ca.set(12, ca.get(12) + i.intValue());
        return ca.getTime();
    }

    public static Date getMonthStart(Date datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal.set(5, 1);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getMonthEnd(Date datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal.set(5, cal.getActualMaximum(5));
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 998);
        return cal.getTime();
    }

    public static Date getWeekStart(Date datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal.set(7, 1);
        cal.set(11, 0);
        cal.set(12, 0);
        cal.set(13, 0);
        cal.set(14, 0);
        return cal.getTime();
    }

    public static Date getWeekEnd(Date datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal.set(7, 7);
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 998);
        return cal.getTime();
    }

    /**
     * 获取月份对应的月份代码
     *
     * @param index
     * @return
     */
    public static String getMonthCode(int index) {
        switch (index) {
            case 1:
                return "Jan";
            case 2:
                return "Fen";
            case 3:
                return "Mar";
            case 4:
                return "Apr";
            case 5:
                return "May";
            case 6:
                return "Jun";
            case 7:
                return "Jul";
            case 8:
                return "Aug";
            case 9:
                return "Sep";
            case 10:
                return "Oct";
            case 11:
                return "Nov";
            case 12:
                return "Dec";
        }
        return null;
    }

    public static String getWeekEN(Integer index) {
        try {
            String[] dayNames = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri",
                    "Sat"};
            return dayNames[(index.intValue() - 1)];
        } catch (Exception e) {
        }
        return null;
    }

    public static String getWeekSC(Integer index) {
        try {
            String[] dayNames = {"星期天", "星期一", "星期二", "星期三", "星期四", "星期五",
                    "星期六"};
            return dayNames[(index.intValue() - 1)];
        } catch (Exception e) {
        }
        return null;
    }

    public static Calendar clearTimeFields(Calendar calendar) {
        calendar.set(11, 0);
        calendar.set(12, 0);
        calendar.set(13, 0);
        calendar.set(14, 0);
        return calendar;
    }

    public static Date setTime0(Date datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal = clearTimeFields(cal);
        return cal.getTime();
    }

    public static Date setTime23(Date datetime) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datetime);
        cal.set(11, 23);
        cal.set(12, 59);
        cal.set(13, 59);
        cal.set(14, 998);
        return cal.getTime();
    }

    public static java.sql.Date getSQLDate(Date date) {
        return new java.sql.Date(date.getTime());
    }

    public static GregorianCalendar getGregorianCalendarByString(
            String dateString) {
        GregorianCalendar calendar = new GregorianCalendar();
        try {
            if (null == dateString)
                calendar.setTime(new Date());
            else
                calendar.setTime(getDateByyyyy_MM_dd(dateString));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public static GregorianCalendar getGregorianCalendarByDate(Date date) {
        GregorianCalendar calendar = new GregorianCalendar();
        try {
            if (null == date)
                calendar.setTime(new Date());
            else
                calendar.setTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return calendar;
    }

    public static XMLGregorianCalendar getXMLGregorianCalendarByString(
            String dateString) {
        try {
            GregorianCalendar lvGregorianCalendar = getGregorianCalendarByString(dateString);
            XMLGregorianCalendar lvNewXMLGregorianCalendar = DatatypeFactory
                    .newInstance().newXMLGregorianCalendar(lvGregorianCalendar);
            return lvNewXMLGregorianCalendar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static XMLGregorianCalendar getXMLGregorianCalendarByDate(Date date) {
        try {
            GregorianCalendar lvGregorianCalendar = getGregorianCalendarByDate(date);
            XMLGregorianCalendar lvNewXMLGregorianCalendar = DatatypeFactory
                    .newInstance().newXMLGregorianCalendar(lvGregorianCalendar);
            return lvNewXMLGregorianCalendar;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getDateStringyyyy_MM_dd_HHmmss() {
        return getyyyy_MM_ddString(getDate()) + " " + getHH_mm_ss(getDate());
    }

    public static String getDateStringyyyy_MM_dd_HHmmss(Date date) {
        return getyyyy_MM_ddString(date) + " " + getHH_mm_ss(date);
    }

    /**
     * 输入日期取星期几的方法
     */
    public static String getWeekDay(String DateStr) {
        SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy-MM-dd");// formatYMD表示的是yyyy-MM-dd格式
        SimpleDateFormat formatD = new SimpleDateFormat("E");// "E"表示"day in week"
        Date d = null;
        String weekDay = "";
        try {
            d = formatYMD.parse(DateStr);// 将String 转换为符合格式的日期
            weekDay = formatD.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return weekDay;
    }

    /**
     * 1. 功能：字符串转Date
     * 2. 作者：刘新
     * 3. 创建日期：2014年10月30日14:29:38
     * 4. 修改说明：
     */
    public static Date string2Date(String dateStr, String formatStr) {
        DateFormat dd = new SimpleDateFormat(formatStr);
        Date date = null;
        try {
            date = dd.parse(dateStr);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 1. 功能：Date字转符串
     * 2. 作者：刘新
     * 3. 创建日期：2014年10月30日14:29:38
     * 4. 修改说明：
     */
    public static String dateToString(Date date, String formatStr) {
        SimpleDateFormat dd = new SimpleDateFormat(formatStr);
        String dateStr = dd.format(date);
        return dateStr;
    }

    /**
     * 1. 功能：Vue.js Date字转符串
     * 2. 作者：lx
     * 3. 创建日期：2017年5月8日11:48:00
     * 4. 修改说明：
     */
    public static String formatToDate(String date, String time) {
        date = date.replace("Z", " UTC");// 注意是空格+UTC
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z");// 注意格式化的表达式
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");// 注意格式化的表达式
        Date d = new Date();
        try {
            d = format.parse(date);
        } catch (ParseException e) {
            LogUtil.error("Vue.js Date字转符串", e);
        }
        return sf.format(d) + "" + time;
    }

    /**
     * 获取当前时间
     * 时间格式：2017-07-11 11:44:35
     *
     * @throws ParseException
     * @author lx
     */
    public static String getTime() {
        Date now = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(now);
    }

    public static int getAge(Date birthDay) throws Exception {
        Calendar cal = Calendar.getInstance();

        if (cal.before(birthDay)) {
            throw new IllegalArgumentException(
                    "The birthDay is before Now.It's unbelievable!");
        }
        int yearNow = cal.get(Calendar.YEAR);
        int monthNow = cal.get(Calendar.MONTH);
        int dayOfMonthNow = cal.get(Calendar.DAY_OF_MONTH);
        cal.setTime(birthDay);

        int yearBirth = cal.get(Calendar.YEAR);
        int monthBirth = cal.get(Calendar.MONTH);
        int dayOfMonthBirth = cal.get(Calendar.DAY_OF_MONTH);

        int age = yearNow - yearBirth;

        if (monthNow <= monthBirth) {
            if (monthNow == monthBirth) {
                if (dayOfMonthNow < dayOfMonthBirth)
                    age--;
            } else {
                age--;
            }
        }
        return age;
    }

    public static void main(String[] args) throws Exception {
//		System.out.println(getAge(DateUtil.parseDate("1989-02-09", "yyyy-MM-dd")));
//		System.out.println(DateUtil.addDay(new Date(),0).getTime()-new Date().getTime());
//		DateUtil addDay = new DateUtil();
//		String date = "2012-11-08";
//		DateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//		Date date1 = f.parse(date.toString());
//		System.out.println(addDay.CompareToDate(date1).toString());
//		System.exit(0);
    }
}
