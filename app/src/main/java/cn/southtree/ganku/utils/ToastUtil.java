package cn.southtree.ganku.utils;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by zhuo.chen on 2017/12/23.
 */

public class ToastUtil {
    public static void showToast(Activity context,String message){
        Toast.makeText(context, ""+message, Toast.LENGTH_SHORT).show();
    }
}
