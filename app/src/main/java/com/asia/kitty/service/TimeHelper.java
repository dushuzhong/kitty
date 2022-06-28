package com.asia.kitty.service;

import android.text.TextUtils;
import android.util.Log;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeHelper {

    //将时间戳转化为对应的时间  日-时-分-秒
    public static String timeConversion(long time) {
        long day = 0;
        long hour = 0;
        long minutes = 0;
        long sencond = 0;
        long dayTimp = time % (3600*24);
        long hourTimp = time % 3600;

        if(time >= 86400){
            day = time / (3600*24);
            if(dayTimp != 0){
                time = time-(day * 24 * 60 * 60);
                if(time  >= 3600 && time < 86400){
                    hour = time / 3600;
                    if (hourTimp != 0) {
                        if (hourTimp  >= 60) {
                            minutes = hourTimp / 60;
                            if (hourTimp % 60 != 0) {
                                sencond = hourTimp % 60;
                            }
                        } else if (hourTimp < 60){
                            sencond = hourTimp;
                        }
                    }
                } else if(time < 3600){
                    minutes = time / 60;
                    if (time % 60 != 0) {
                        sencond = time % 60;
                    }
                }
            }
        } else if (time  >= 3600 && time < 86400) {
            hour = time / 3600;
            if (hourTimp != 0) {
                if (hourTimp  >= 60) {
                    minutes = hourTimp / 60;
                    if (hourTimp % 60 != 0) {
                        sencond = hourTimp % 60;
                    }
                } else if (hourTimp < 60){
                    sencond = hourTimp;
                }
            }
        } else if(time < 3600){
            minutes = time / 60;
            if (time % 60 != 0) {
                sencond = time % 60;
            }
        }
        return (day<10?("0"+day):day) + "天" + (hour<10?("0"+hour):hour) + "时" + (minutes<10?("0"+minutes):minutes) + "分" + (sencond<10?("0"+sencond):sencond)+ "秒";
    }

    // 将时间字符串转为时间戳字符串
    public static Long getStringTimestamp(String time) {
        Long longTime = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            longTime = sdf.parse(time).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return longTime;
    }

    // 获取服务器返回的时间戳 转换成"yyyy-MM-dd HH:mm:ss"
    // 计算结束时间 - 开始时间的时间差
    public static long getTimeDiff(String startTime,String endTime) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long diff = 0;
        String startDate = startTime;
        String endDate = endTime;
        if (TextUtils.isEmpty(startTime)) {
            startDate = "2021-07-13 11:09:10";
        }
        if (TextUtils.isEmpty(endDate)) {
            endDate = "2021-07-13 16:39:10";
        }

        try {
            Date end = formatter.parse("2021-07-13 16:33:10");//结束时间
            Date start = formatter.parse("2021-07-13 11:09:10");//开始时间
            // 这样得到的差值是微秒级别
            diff = end.getTime() - start.getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            Log.e("-=-", "剩余: " + days + "天" + hours + "小时" + minutes + "分");
        } catch (Exception e) {
            Log.e("-=-", e.getMessage());
        }
        return diff;
    }
}
