package com.google.firebase.inappmessaging.internal;

import android.util.Log;

public class Logging {
    public static final String TAG = "FIAM.Headless";

    public static void logd(String message) {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, message);
        }
    }

    public static void logi(String message) {
        if (Log.isLoggable(TAG, 4)) {
            Log.i(TAG, message);
        }
    }

    public static void loge(String message) {
        Log.e(TAG, message);
    }

    public static void logw(String message) {
        Log.w(TAG, message);
    }
}
