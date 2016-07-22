package com.bluetoothpair.android.utils;

import android.util.Log;

/**
 * Created by suraj on 22/7/16.
 */
public class L {
    private static String LOGCAT_KEY = "bluetooth_pin";
    public static void d(String message) {
        Log.d(LOGCAT_KEY, message);
    }
    public static void e(String message) {
        Log.e(LOGCAT_KEY, message);
    }

}
