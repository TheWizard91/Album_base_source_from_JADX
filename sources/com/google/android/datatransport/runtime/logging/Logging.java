package com.google.android.datatransport.runtime.logging;

import android.util.Log;

public final class Logging {
    private Logging() {
    }

    private static String getTag(String tag) {
        return "TransportRuntime." + tag;
    }

    /* renamed from: d */
    public static void m138d(String tag, String message) {
        Log.d(getTag(tag), message);
    }

    /* renamed from: d */
    public static void m139d(String tag, String message, Object arg1) {
        Log.d(getTag(tag), String.format(message, new Object[]{arg1}));
    }

    /* renamed from: d */
    public static void m140d(String tag, String message, Object arg1, Object arg2) {
        Log.d(getTag(tag), String.format(message, new Object[]{arg1, arg2}));
    }

    /* renamed from: d */
    public static void m141d(String tag, String message, Object... args) {
        Log.d(getTag(tag), String.format(message, args));
    }

    /* renamed from: i */
    public static void m143i(String tag, String message) {
        Log.i(getTag(tag), message);
    }

    /* renamed from: e */
    public static void m142e(String tag, String message, Throwable e) {
        Log.e(getTag(tag), message, e);
    }

    /* renamed from: w */
    public static void m144w(String tag, String message, Object arg1) {
        Log.w(getTag(tag), String.format(message, new Object[]{arg1}));
    }
}
