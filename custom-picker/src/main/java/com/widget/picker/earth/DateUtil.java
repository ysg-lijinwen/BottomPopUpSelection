package com.widget.picker.earth;

import android.content.Context;

import com.widget.picker.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DateUtil {

    public static final String ymdhms = "yyyy-MM-dd HH:mm:ss";
    public static final String ymd = "yyyy-MM-dd";

    public static String monthNumToMonthName(Context context, String month) {
        String m = month;
        if ("1".equals(month)) {
            m = context.getString(R.string.January);
        } else if ("2".equals(month)) {
            m = context.getString(R.string.February);
        } else if ("3".equals(month)) {
            m = context.getString(R.string.March);
        } else if ("4".equals(month)) {
            m = context.getString(R.string.April);
        } else if ("5".equals(month)) {
            m = context.getString(R.string.May);
        } else if ("6".equals(month)) {
            m = context.getString(R.string.June);
        } else if ("7".equals(month)) {
            m = context.getString(R.string.July);
        } else if ("8".equals(month)) {
            m = context.getString(R.string.August);
        } else if ("9".equals(month)) {
            m = context.getString(R.string.September);
        } else if ("10".equals(month)) {
            m = context.getString(R.string.October);
        } else if ("11".equals(month)) {
            m = context.getString(R.string.November);
        } else if ("12".equals(month)) {
            m = context.getString(R.string.December);
        }
        return m;
    }

    public static String getTomorrow() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + (month > 9 ? month : ("0" + month)) + "-" + (day > 9 ? day : ("0" + day));
    }

    public static int getYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    public static String getToday() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return year + "-" + (month > 9 ? month : ("0" + month)) + "-" + (day > 9 ? day : ("0" + day));
    }

    public static List<Integer> getDateForString(String date) {
        String[] dates = date.split("-");
        List<Integer> list = new ArrayList<>();
        list.add(Integer.parseInt(dates[0]));
        list.add(Integer.parseInt(dates[1]));
        list.add(Integer.parseInt(dates[2]));
        return list;
    }

    public static Calendar getCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }


    public static String formatDate(String date, String format) {
        String resultD = date;
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date d = sdf.parse(date);
            resultD = sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultD;
    }

    public static String formatDate(long milliseconds, String format) {
        String resultD = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            Date d = new Date(milliseconds);
            resultD = sdf.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultD;
    }

    public static Date formatDateStr(String date, String format) {
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date1 = null;
        try {
            date1 = sdf.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return date1;
    }

    public static String appendStartTime(String date) {
        return (new StringBuilder(date)).append(" 00:00:00").toString();
    }

    public static String appendEndTime(String date) {
        return (new StringBuilder(date)).append(" 23:59:59").toString();
    }

    /**
     * 通过年份和月份 得到当月的日子
     *
     * @param year
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        month++;
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return 30;
            case 2:
                if (((year % 4 == 0) && (year % 100 != 0)) || (year % 400 == 0)) {
                    return 29;
                } else {
                    return 28;
                }
            default:
                return -1;
        }
    }

    /**
     * 返回当前月份1号位于周几
     *
     * @param year  年份
     * @param month 月份，传入系统获取的，不需要正常的
     * @return 日：1		一：2		二：3		三：4		四：5		五：6		六：7
     */
    public static int getFirstDayWeek(int year, int month) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, 1);
//        Log.d("DateView", "DateView:First:" + calendar.getFirstDayOfWeek());
        return calendar.get(Calendar.DAY_OF_WEEK);
    }

    public static String getDayWeek(Context context, int year, int month, int day) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);
//        Log.d("DateView", "DateView:First:" + calendar.getFirstDayOfWeek());

        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                return context.getString(R.string.Sunday);

            case 2:
                return context.getString(R.string.Monday);

            case 3:
                return context.getString(R.string.Tuesday);

            case 4:
                return context.getString(R.string.Wednesday);

            case 5:
                return context.getString(R.string.Thursday);

            case 6:
                return context.getString(R.string.Friday);

            case 7:
                return context.getString(R.string.Saturday);

            default:
                return "";

        }
    }
}
