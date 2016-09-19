package com.steven.util;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;

/**
 * Created by Steven on 2016/3/14.
 *
 */
public class App extends Application {
    public static App mInstance;
    public static ArrayList<Activity> listActivity = new ArrayList<Activity>();

    public App() {
        mInstance = this;
    }

    public static App getApp() {
        if (mInstance != null) {
            return mInstance;
        } else {
            mInstance = new App();
            mInstance.onCreate();
            return mInstance;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        
    }

    public static void exit() {
        try {
            for (Activity activity : listActivity) {
                activity.finish();
            }
            // 结束进程
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
