package com.google.firebase.inappmessaging.display.internal;

import android.util.Log;

public class Logging {
    private static final String TAG = "FIAM.Display";

    public static void logdNumber(String label, float num) {
        logd(label + ": " + num);
    }

    public static void logdPair(String label, float fst, float snd) {
        logd(label + ": (" + fst + ", " + snd + ")");
    }

    public static void logdHeader(String label) {
        logd("============ " + label + " ============");
    }

    public static void logd(String message) {
        if (Log.isLoggable(TAG, 3)) {
            Log.d(TAG, message);
        }
    }

    public static void loge(String message) {
        Log.e(TAG, message);
    }

    public static void logi(String message) {
        if (Log.isLoggable(TAG, 4)) {
            Log.i(TAG, message);
        }
    }
}
