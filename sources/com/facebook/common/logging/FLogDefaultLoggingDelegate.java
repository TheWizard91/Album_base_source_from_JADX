package com.facebook.common.logging;

import android.util.Log;
import androidx.core.p003os.EnvironmentCompat;
import java.io.PrintWriter;
import java.io.StringWriter;

public class FLogDefaultLoggingDelegate implements LoggingDelegate {
    public static final FLogDefaultLoggingDelegate sInstance = new FLogDefaultLoggingDelegate();
    private String mApplicationTag = EnvironmentCompat.MEDIA_UNKNOWN;
    private int mMinimumLoggingLevel = 5;

    public static FLogDefaultLoggingDelegate getInstance() {
        return sInstance;
    }

    private FLogDefaultLoggingDelegate() {
    }

    public void setApplicationTag(String tag) {
        this.mApplicationTag = tag;
    }

    public void setMinimumLoggingLevel(int level) {
        this.mMinimumLoggingLevel = level;
    }

    public int getMinimumLoggingLevel() {
        return this.mMinimumLoggingLevel;
    }

    public boolean isLoggable(int level) {
        return this.mMinimumLoggingLevel <= level;
    }

    /* renamed from: v */
    public void mo13038v(String tag, String msg) {
        println(2, tag, msg);
    }

    /* renamed from: v */
    public void mo13039v(String tag, String msg, Throwable tr) {
        println(2, tag, msg, tr);
    }

    /* renamed from: d */
    public void mo13027d(String tag, String msg) {
        println(3, tag, msg);
    }

    /* renamed from: d */
    public void mo13028d(String tag, String msg, Throwable tr) {
        println(3, tag, msg, tr);
    }

    /* renamed from: i */
    public void mo13032i(String tag, String msg) {
        println(4, tag, msg);
    }

    /* renamed from: i */
    public void mo13033i(String tag, String msg, Throwable tr) {
        println(4, tag, msg, tr);
    }

    /* renamed from: w */
    public void mo13040w(String tag, String msg) {
        println(5, tag, msg);
    }

    /* renamed from: w */
    public void mo13041w(String tag, String msg, Throwable tr) {
        println(5, tag, msg, tr);
    }

    /* renamed from: e */
    public void mo13029e(String tag, String msg) {
        println(6, tag, msg);
    }

    /* renamed from: e */
    public void mo13030e(String tag, String msg, Throwable tr) {
        println(6, tag, msg, tr);
    }

    public void wtf(String tag, String msg) {
        println(6, tag, msg);
    }

    public void wtf(String tag, String msg, Throwable tr) {
        println(6, tag, msg, tr);
    }

    public void log(int priority, String tag, String msg) {
        println(priority, tag, msg);
    }

    private void println(int priority, String tag, String msg) {
        Log.println(priority, prefixTag(tag), msg);
    }

    private void println(int priority, String tag, String msg, Throwable tr) {
        Log.println(priority, prefixTag(tag), getMsg(msg, tr));
    }

    private String prefixTag(String tag) {
        if (this.mApplicationTag != null) {
            return this.mApplicationTag + ":" + tag;
        }
        return tag;
    }

    private static String getMsg(String msg, Throwable tr) {
        return msg + 10 + getStackTraceString(tr);
    }

    private static String getStackTraceString(Throwable tr) {
        if (tr == null) {
            return "";
        }
        StringWriter sw = new StringWriter();
        tr.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
