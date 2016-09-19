package com.steven.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by SkyEyes5 on 2015/11/11.
 * 时间处理工具类
 */
public class TimeUtils {

    /*时间戳转换成字符窜*/
    public static String getDateToString(long time) {
        SimpleDateFormat sf;
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sf.format(d);
    }

    /*时间戳转换成字符窜*/
    public static String getDateToString1(long time) {
        SimpleDateFormat sf;
        Date d = new Date(time);
        sf = new SimpleDateFormat("yyyy年MM月dd日");
        return sf.format(d);
    }

    /**
     * 发布时间处理
     *
     * @param str1 当前时间
     * @param str2 结束时间
     * @return 返回 几天前 多少小时前 多少分钟前
     */
    public static String diff(String str1, String str2) {
        String final_dif = "";
        Date d1 = null;
        Date d2 = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            d1 = sdf.parse(str1);
            d2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Long diff = null;   //两时间差，精确到毫秒
        if (d2 != null) {
            diff = d2.getTime() - d1.getTime();
        }
        Long day = diff / (1000 * 60 * 60 * 24);          //以天数为单位取整
        Long hour = (diff / (60 * 60 * 1000) - day * 24);             //以小时为单位取整
        Long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);    //以分钟为单位取整
        if (day != 0) {
            final_dif = day + "天";
        } else if (hour != 0) {
            final_dif = final_dif + hour + "小时";
        } else {
            final_dif = final_dif + min + "分钟";
        }
        if (final_dif.contains("-")) {
            final_dif = "00:00:00";
        }
        return final_dif;
    }

    public static String Local_time() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sDateFormat.format(System.currentTimeMillis());
    }

    /**
     * @param year        年
     * @param monthOfYear 月
     * @param dayOfMonth  日
     * @return
     */

    public static String FormatBirthday(int year, int monthOfYear, int dayOfMonth) {
        int month = monthOfYear + 1;
        String m = "-0";
        String d = "-0";
        String date;
        if (month < 10 && dayOfMonth > 9) {
            m = m + month;
            date = year + m + "-" + dayOfMonth;
        } else if (month > 9 && dayOfMonth < 10) {//
            d = d + dayOfMonth;
            date = year + "-" + (month + d);
            ToastUtil.toast(date);
        } else if (month < 10 && dayOfMonth < 10) {
            m = m + month;
            d = d + dayOfMonth;
            date = year + m + d;
        } else {
            date = "" + year + "-" + month + "-" + dayOfMonth;
        }
        return date;
    }

    /**
     * 获取北京时间
     */
    public static void getBeijngTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                URL url;
                try {
                    url = new URL("http://www.baidu.com");
                    URLConnection uc = url.openConnection();// 生成连接对象
                    uc.connect(); // 发出连接
                    long ld = uc.getDate(); // 取得网站日期时间
                    Date date = new Date(ld); // 转换为标准时间对象
                    // 分别取得时间中的小时，分钟和秒，并输出
                    LogUtil.log(date + ", " + date.getHours() + "时" + date.getMinutes() + "分" +
                            date.getSeconds() + "秒");
                } catch (MalformedURLException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }// 取得资源对象
                catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });

    }

    /**
     * @param time 获取的时间看是否超时
     * @return
     */
    public static boolean isTimeOut(long time) {
        if (System.currentTimeMillis() / 1000 - time < 5 ||
                System.currentTimeMillis() / 1000 - time > 0)
            return true;
        else
            return false;
    }


}
