package com.facebook.common.logging;

import java.util.Locale;

public class FLog {
    public static final int ASSERT = 7;
    public static final int DEBUG = 3;
    public static final int ERROR = 6;
    public static final int INFO = 4;
    public static final int VERBOSE = 2;
    public static final int WARN = 5;
    private static LoggingDelegate sHandler = FLogDefaultLoggingDelegate.getInstance();

    public static void setLoggingDelegate(LoggingDelegate delegate) {
        if (delegate != null) {
            sHandler = delegate;
            return;
        }
        throw new IllegalArgumentException();
    }

    public static boolean isLoggable(int level) {
        return sHandler.isLoggable(level);
    }

    public static void setMinimumLoggingLevel(int level) {
        sHandler.setMinimumLoggingLevel(level);
    }

    public static int getMinimumLoggingLevel() {
        return sHandler.getMinimumLoggingLevel();
    }

    /* renamed from: v */
    public static void m88v(String tag, String msg) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13038v(tag, msg);
        }
    }

    /* renamed from: v */
    public static void m89v(String tag, String msg, Object arg1) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13038v(tag, formatString(msg, arg1));
        }
    }

    /* renamed from: v */
    public static void m90v(String tag, String msg, Object arg1, Object arg2) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13038v(tag, formatString(msg, arg1, arg2));
        }
    }

    /* renamed from: v */
    public static void m91v(String tag, String msg, Object arg1, Object arg2, Object arg3) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13038v(tag, formatString(msg, arg1, arg2, arg3));
        }
    }

    /* renamed from: v */
    public static void m92v(String tag, String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13038v(tag, formatString(msg, arg1, arg2, arg3, arg4));
        }
    }

    /* renamed from: v */
    public static void m80v(Class<?> cls, String msg) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13038v(getTag(cls), msg);
        }
    }

    /* renamed from: v */
    public static void m81v(Class<?> cls, String msg, Object arg1) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13038v(getTag(cls), formatString(msg, arg1));
        }
    }

    /* renamed from: v */
    public static void m82v(Class<?> cls, String msg, Object arg1, Object arg2) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13038v(getTag(cls), formatString(msg, arg1, arg2));
        }
    }

    /* renamed from: v */
    public static void m83v(Class<?> cls, String msg, Object arg1, Object arg2, Object arg3) {
        if (isLoggable(2)) {
            m80v(cls, formatString(msg, arg1, arg2, arg3));
        }
    }

    /* renamed from: v */
    public static void m84v(Class<?> cls, String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13038v(getTag(cls), formatString(msg, arg1, arg2, arg3, arg4));
        }
    }

    /* renamed from: v */
    public static void m94v(String tag, String msg, Object... args) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13038v(tag, formatString(msg, args));
        }
    }

    /* renamed from: v */
    public static void m95v(String tag, Throwable tr, String msg, Object... args) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13039v(tag, formatString(msg, args), tr);
        }
    }

    /* renamed from: v */
    public static void m86v(Class<?> cls, String msg, Object... args) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13038v(getTag(cls), formatString(msg, args));
        }
    }

    /* renamed from: v */
    public static void m87v(Class<?> cls, Throwable tr, String msg, Object... args) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13039v(getTag(cls), formatString(msg, args), tr);
        }
    }

    /* renamed from: v */
    public static void m93v(String tag, String msg, Throwable tr) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13039v(tag, msg, tr);
        }
    }

    /* renamed from: v */
    public static void m85v(Class<?> cls, String msg, Throwable tr) {
        if (sHandler.isLoggable(2)) {
            sHandler.mo13039v(getTag(cls), msg, tr);
        }
    }

    /* renamed from: d */
    public static void m48d(String tag, String msg) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13027d(tag, msg);
        }
    }

    /* renamed from: d */
    public static void m49d(String tag, String msg, Object arg1) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13027d(tag, formatString(msg, arg1));
        }
    }

    /* renamed from: d */
    public static void m50d(String tag, String msg, Object arg1, Object arg2) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13027d(tag, formatString(msg, arg1, arg2));
        }
    }

    /* renamed from: d */
    public static void m51d(String tag, String msg, Object arg1, Object arg2, Object arg3) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13027d(tag, formatString(msg, arg1, arg2, arg3));
        }
    }

    /* renamed from: d */
    public static void m52d(String tag, String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13027d(tag, formatString(msg, arg1, arg2, arg3, arg4));
        }
    }

    /* renamed from: d */
    public static void m40d(Class<?> cls, String msg) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13027d(getTag(cls), msg);
        }
    }

    /* renamed from: d */
    public static void m41d(Class<?> cls, String msg, Object arg1) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13027d(getTag(cls), formatString(msg, arg1));
        }
    }

    /* renamed from: d */
    public static void m42d(Class<?> cls, String msg, Object arg1, Object arg2) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13027d(getTag(cls), formatString(msg, arg1, arg2));
        }
    }

    /* renamed from: d */
    public static void m43d(Class<?> cls, String msg, Object arg1, Object arg2, Object arg3) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13027d(getTag(cls), formatString(msg, arg1, arg2, arg3));
        }
    }

    /* renamed from: d */
    public static void m44d(Class<?> cls, String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13027d(getTag(cls), formatString(msg, arg1, arg2, arg3, arg4));
        }
    }

    /* renamed from: d */
    public static void m54d(String tag, String msg, Object... args) {
        if (sHandler.isLoggable(3)) {
            m48d(tag, formatString(msg, args));
        }
    }

    /* renamed from: d */
    public static void m55d(String tag, Throwable tr, String msg, Object... args) {
        if (sHandler.isLoggable(3)) {
            m53d(tag, formatString(msg, args), tr);
        }
    }

    /* renamed from: d */
    public static void m46d(Class<?> cls, String msg, Object... args) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13027d(getTag(cls), formatString(msg, args));
        }
    }

    /* renamed from: d */
    public static void m47d(Class<?> cls, Throwable tr, String msg, Object... args) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13028d(getTag(cls), formatString(msg, args), tr);
        }
    }

    /* renamed from: d */
    public static void m53d(String tag, String msg, Throwable tr) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13028d(tag, msg, tr);
        }
    }

    /* renamed from: d */
    public static void m45d(Class<?> cls, String msg, Throwable tr) {
        if (sHandler.isLoggable(3)) {
            sHandler.mo13028d(getTag(cls), msg, tr);
        }
    }

    /* renamed from: i */
    public static void m72i(String tag, String msg) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13032i(tag, msg);
        }
    }

    /* renamed from: i */
    public static void m73i(String tag, String msg, Object arg1) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13032i(tag, formatString(msg, arg1));
        }
    }

    /* renamed from: i */
    public static void m74i(String tag, String msg, Object arg1, Object arg2) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13032i(tag, formatString(msg, arg1, arg2));
        }
    }

    /* renamed from: i */
    public static void m75i(String tag, String msg, Object arg1, Object arg2, Object arg3) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13032i(tag, formatString(msg, arg1, arg2, arg3));
        }
    }

    /* renamed from: i */
    public static void m76i(String tag, String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13032i(tag, formatString(msg, arg1, arg2, arg3, arg4));
        }
    }

    /* renamed from: i */
    public static void m64i(Class<?> cls, String msg) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13032i(getTag(cls), msg);
        }
    }

    /* renamed from: i */
    public static void m65i(Class<?> cls, String msg, Object arg1) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13032i(getTag(cls), formatString(msg, arg1));
        }
    }

    /* renamed from: i */
    public static void m66i(Class<?> cls, String msg, Object arg1, Object arg2) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13032i(getTag(cls), formatString(msg, arg1, arg2));
        }
    }

    /* renamed from: i */
    public static void m67i(Class<?> cls, String msg, Object arg1, Object arg2, Object arg3) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13032i(getTag(cls), formatString(msg, arg1, arg2, arg3));
        }
    }

    /* renamed from: i */
    public static void m68i(Class<?> cls, String msg, Object arg1, Object arg2, Object arg3, Object arg4) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13032i(getTag(cls), formatString(msg, arg1, arg2, arg3, arg4));
        }
    }

    /* renamed from: i */
    public static void m78i(String tag, String msg, Object... args) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13032i(tag, formatString(msg, args));
        }
    }

    /* renamed from: i */
    public static void m79i(String tag, Throwable tr, String msg, Object... args) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13033i(tag, formatString(msg, args), tr);
        }
    }

    /* renamed from: i */
    public static void m70i(Class<?> cls, String msg, Object... args) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13032i(getTag(cls), formatString(msg, args));
        }
    }

    /* renamed from: i */
    public static void m71i(Class<?> cls, Throwable tr, String msg, Object... args) {
        if (isLoggable(4)) {
            sHandler.mo13033i(getTag(cls), formatString(msg, args), tr);
        }
    }

    /* renamed from: i */
    public static void m77i(String tag, String msg, Throwable tr) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13033i(tag, msg, tr);
        }
    }

    /* renamed from: i */
    public static void m69i(Class<?> cls, String msg, Throwable tr) {
        if (sHandler.isLoggable(4)) {
            sHandler.mo13033i(getTag(cls), msg, tr);
        }
    }

    /* renamed from: w */
    public static void m100w(String tag, String msg) {
        if (sHandler.isLoggable(5)) {
            sHandler.mo13040w(tag, msg);
        }
    }

    /* renamed from: w */
    public static void m96w(Class<?> cls, String msg) {
        if (sHandler.isLoggable(5)) {
            sHandler.mo13040w(getTag(cls), msg);
        }
    }

    /* renamed from: w */
    public static void m102w(String tag, String msg, Object... args) {
        if (sHandler.isLoggable(5)) {
            sHandler.mo13040w(tag, formatString(msg, args));
        }
    }

    /* renamed from: w */
    public static void m103w(String tag, Throwable tr, String msg, Object... args) {
        if (sHandler.isLoggable(5)) {
            sHandler.mo13041w(tag, formatString(msg, args), tr);
        }
    }

    /* renamed from: w */
    public static void m98w(Class<?> cls, String msg, Object... args) {
        if (sHandler.isLoggable(5)) {
            sHandler.mo13040w(getTag(cls), formatString(msg, args));
        }
    }

    /* renamed from: w */
    public static void m99w(Class<?> cls, Throwable tr, String msg, Object... args) {
        if (isLoggable(5)) {
            m97w(cls, formatString(msg, args), tr);
        }
    }

    /* renamed from: w */
    public static void m101w(String tag, String msg, Throwable tr) {
        if (sHandler.isLoggable(5)) {
            sHandler.mo13041w(tag, msg, tr);
        }
    }

    /* renamed from: w */
    public static void m97w(Class<?> cls, String msg, Throwable tr) {
        if (sHandler.isLoggable(5)) {
            sHandler.mo13041w(getTag(cls), msg, tr);
        }
    }

    /* renamed from: e */
    public static void m60e(String tag, String msg) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo13029e(tag, msg);
        }
    }

    /* renamed from: e */
    public static void m56e(Class<?> cls, String msg) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo13029e(getTag(cls), msg);
        }
    }

    /* renamed from: e */
    public static void m62e(String tag, String msg, Object... args) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo13029e(tag, formatString(msg, args));
        }
    }

    /* renamed from: e */
    public static void m63e(String tag, Throwable tr, String msg, Object... args) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo13030e(tag, formatString(msg, args), tr);
        }
    }

    /* renamed from: e */
    public static void m58e(Class<?> cls, String msg, Object... args) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo13029e(getTag(cls), formatString(msg, args));
        }
    }

    /* renamed from: e */
    public static void m59e(Class<?> cls, Throwable tr, String msg, Object... args) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo13030e(getTag(cls), formatString(msg, args), tr);
        }
    }

    /* renamed from: e */
    public static void m61e(String tag, String msg, Throwable tr) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo13030e(tag, msg, tr);
        }
    }

    /* renamed from: e */
    public static void m57e(Class<?> cls, String msg, Throwable tr) {
        if (sHandler.isLoggable(6)) {
            sHandler.mo13030e(getTag(cls), msg, tr);
        }
    }

    public static void wtf(String tag, String msg) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(tag, msg);
        }
    }

    public static void wtf(Class<?> cls, String msg) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(getTag(cls), msg);
        }
    }

    public static void wtf(String tag, String msg, Object... args) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(tag, formatString(msg, args));
        }
    }

    public static void wtf(String tag, Throwable tr, String msg, Object... args) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(tag, formatString(msg, args), tr);
        }
    }

    public static void wtf(Class<?> cls, String msg, Object... args) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(getTag(cls), formatString(msg, args));
        }
    }

    public static void wtf(Class<?> cls, Throwable tr, String msg, Object... args) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(getTag(cls), formatString(msg, args), tr);
        }
    }

    public static void wtf(String tag, String msg, Throwable tr) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(tag, msg, tr);
        }
    }

    public static void wtf(Class<?> cls, String msg, Throwable tr) {
        if (sHandler.isLoggable(6)) {
            sHandler.wtf(getTag(cls), msg, tr);
        }
    }

    private static String formatString(String str, Object... args) {
        return String.format((Locale) null, str, args);
    }

    private static String getTag(Class<?> clazz) {
        return clazz.getSimpleName();
    }
}
