package com.facebook.common.logging;

public interface LoggingDelegate {
    /* renamed from: d */
    void mo13027d(String str, String str2);

    /* renamed from: d */
    void mo13028d(String str, String str2, Throwable th);

    /* renamed from: e */
    void mo13029e(String str, String str2);

    /* renamed from: e */
    void mo13030e(String str, String str2, Throwable th);

    int getMinimumLoggingLevel();

    /* renamed from: i */
    void mo13032i(String str, String str2);

    /* renamed from: i */
    void mo13033i(String str, String str2, Throwable th);

    boolean isLoggable(int i);

    void log(int i, String str, String str2);

    void setMinimumLoggingLevel(int i);

    /* renamed from: v */
    void mo13038v(String str, String str2);

    /* renamed from: v */
    void mo13039v(String str, String str2, Throwable th);

    /* renamed from: w */
    void mo13040w(String str, String str2);

    /* renamed from: w */
    void mo13041w(String str, String str2, Throwable th);

    void wtf(String str, String str2);

    void wtf(String str, String str2, Throwable th);
}
