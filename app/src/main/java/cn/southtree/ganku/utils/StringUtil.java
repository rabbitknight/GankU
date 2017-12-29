package cn.southtree.ganku.utils;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhuo.chen
 * @version 2017/12/25.
 */

public class StringUtil {
    /**
     * 字符串为空判断
     * @param string 接收的字符串
     * @return
     */
    public static boolean isNull(String string){
        if (string == null || "".equals(string)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * 2017-11-15T13:23:38.991Z
     *
     * @param string
     * @return
     */
    public static Date str2Date(String string){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date date = null;
        try {
            date = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
    /**
     * 2017-11-15T13:23:38.991Z
     *
     * @param string
     * @return
     */
    public static String strDate2str(String string){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {
            date = sdf.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date!=null){
            return sdf2.format(date);
        }else {
            return null;
        }
    }
}
