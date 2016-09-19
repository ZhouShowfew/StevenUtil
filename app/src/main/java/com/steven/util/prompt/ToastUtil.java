package com.steven.util.prompt;

import android.content.Context;

import com.steven.util.App;

/**
 * Created by SkyEyes9 on 2015/12/24.
 * 吐司工具类
 */
public class ToastUtil {
    public static void toast(Context context, String info){
        android.widget.Toast.makeText(context,info, android.widget.Toast.LENGTH_SHORT).show();
    }
    public static void toast(String info){
        android.widget.Toast.makeText(App.getApp(),info, android.widget.Toast.LENGTH_SHORT).show();
    }
    public static void toastL(String info){
        android.widget.Toast.makeText(App.getApp(),info, android.widget.Toast.LENGTH_LONG).show();
    }
    public static void snackBar(String info){
//        android.widget.Toast.makeText(BaseApplication.getApp(),info, android.widget.Toast.LENGTH_LONG).show();
//        new SnackBar(BaseApplication.mInstance,
//                info,
//                "yes", new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//            }
//        }).show();
    }
}
