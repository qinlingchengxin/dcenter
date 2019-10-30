package net.ys.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * User: LiWenC
 * Date: 16-9-8
 */
public class TimeUtil {

    private static SimpleDateFormat yMd_Hms = new SimpleDateFormat("yyyyMMddHHmmss");

    private static SimpleDateFormat yMd = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * 获取今日开始的毫秒值
     *
     * @return
     */
    public static long todayStartMillisecond() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取今日最后的毫秒值
     *
     * @return
     */
    public static long todayEndMillisecond() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * 获取下一年毫秒值
     *
     * @return
     */
    public static long nextYearMillisecond() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTimeInMillis();
    }

    /**
     * 转化时间-开始
     *
     * @param time
     * @return
     */
    public static long toLongStart(String time) {
        try {
            if (time == null || "".equals(time.trim())) {
                return 0L;
            }

            Date date = yMd.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 1);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
        }
        return 0L;
    }

    /**
     * 转化时间-结束
     *
     * @param time
     * @return
     */
    public static long toLongEnd(String time) {
        try {

            if (time == null || "".equals(time.trim())) {
                return 0L;
            }

            Date date = yMd.parse(time);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.set(Calendar.HOUR_OF_DAY, 23);
            calendar.set(Calendar.MINUTE, 59);
            calendar.set(Calendar.SECOND, 59);
            calendar.set(Calendar.MILLISECOND, 999);
            return calendar.getTimeInMillis();
        } catch (ParseException e) {
        }
        return 0L;
    }

    /**
     * 获取世界末日毫秒值
     *
     * @return
     */
    public static long doomsdayMillisecond() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.YEAR, 99);
        calendar.add(Calendar.MONTH, 1);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 1);
        return calendar.getTimeInMillis();
    }

    /**
     * 格式化当前时间
     *
     * @return
     */
    public static String genCurYmdHms() {
        Date date = new Date();
        return yMd_Hms.format(date);
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(nextYearMillisecond());
    }
}
