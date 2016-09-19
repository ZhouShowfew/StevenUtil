package com.steven.util.prompt;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.util.Log;

/**
 * Created by linwenhui on 2016/3/24.
 */
public class Logger {
    public static boolean DEBUG = false;

    private Logger() {
    }

    public static void init(Context ctx) {
        DEBUG = isDebuggable(ctx);
    }

    private static boolean isDebuggable(Context ctx) {
        boolean debuggable = false;

        PackageManager pm = ctx.getPackageManager();
        try {
            ApplicationInfo appinfo = pm.getApplicationInfo(ctx.getPackageName(), 0);
            debuggable = (0 != (appinfo.flags & ApplicationInfo.FLAG_DEBUGGABLE));
        } catch (PackageManager.NameNotFoundException e) {
        }

        return debuggable;
    }

    public static void v(String TAG, String msg) {
        if (DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void i(String msg) {
        i(Thread.currentThread().getName(), msg);
    }

    public static void i(String TAG, String msg) {
        if (DEBUG) {
            Log.i(TAG, msg);
        }
    }

    public static void i(String TAG, String msg, Throwable e) {
        if (DEBUG) {
            Log.i(TAG, msg, e);
        }
    }

    public static void d(String TAG, String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void d(String TAG, String msg, Throwable tr) {
        if (DEBUG) {
            Log.e(TAG, msg, tr);
        }
    }

    public static void w(String TAG, String msg) {
        if (DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String TAG, String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

    public static void e(String TAG, String msg, Throwable tr) {
        if (DEBUG) {
            Log.e(TAG, msg, tr);
        }
    }
}
