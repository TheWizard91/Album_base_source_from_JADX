package com.google.android.gms.common.logging;

import android.util.Log;
import com.google.android.gms.common.internal.GmsLogger;
import java.util.Locale;

/* compiled from: com.google.android.gms:play-services-basement@@17.2.1 */
public class Logger {
    private final String mTag;
    private final String zzeu;
    private final GmsLogger zzfl;
    private final int zzfm;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public Logger(java.lang.String r7, java.lang.String... r8) {
        /*
            r6 = this;
            int r0 = r8.length
            if (r0 != 0) goto L_0x0007
            java.lang.String r8 = ""
            goto L_0x0038
        L_0x0007:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            r1 = 91
            r0.append(r1)
            int r1 = r8.length
            r2 = 0
        L_0x0013:
            if (r2 >= r1) goto L_0x0029
            r3 = r8[r2]
            int r4 = r0.length()
            r5 = 1
            if (r4 <= r5) goto L_0x0023
            java.lang.String r4 = ","
            r0.append(r4)
        L_0x0023:
            r0.append(r3)
            int r2 = r2 + 1
            goto L_0x0013
        L_0x0029:
            r8 = 93
            java.lang.StringBuilder r8 = r0.append(r8)
            r1 = 32
            r8.append(r1)
            java.lang.String r8 = r0.toString()
        L_0x0038:
            r6.<init>((java.lang.String) r7, (java.lang.String) r8)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.logging.Logger.<init>(java.lang.String, java.lang.String[]):void");
    }

    private Logger(String str, String str2) {
        this.zzeu = str2;
        this.mTag = str;
        this.zzfl = new GmsLogger(str);
        int i = 2;
        while (7 >= i && !Log.isLoggable(this.mTag, i)) {
            i++;
        }
        this.zzfm = i;
    }

    public String getTag() {
        return this.mTag;
    }

    public boolean isLoggable(int i) {
        return this.zzfm <= i;
    }

    /* renamed from: v */
    public void mo21048v(String str, Object... objArr) {
        if (isLoggable(2)) {
            Log.v(this.mTag, format(str, objArr));
        }
    }

    /* renamed from: v */
    public void mo21047v(String str, Throwable th, Object... objArr) {
        if (isLoggable(2)) {
            Log.v(this.mTag, format(str, objArr), th);
        }
    }

    /* renamed from: d */
    public void mo21040d(String str, Object... objArr) {
        if (isLoggable(3)) {
            Log.d(this.mTag, format(str, objArr));
        }
    }

    /* renamed from: d */
    public void mo21039d(String str, Throwable th, Object... objArr) {
        if (isLoggable(3)) {
            Log.d(this.mTag, format(str, objArr), th);
        }
    }

    /* renamed from: i */
    public void mo21045i(String str, Object... objArr) {
        Log.i(this.mTag, format(str, objArr));
    }

    /* renamed from: w */
    public void mo21049w(String str, Object... objArr) {
        Log.w(this.mTag, format(str, objArr));
    }

    /* renamed from: e */
    public void mo21042e(String str, Object... objArr) {
        Log.e(this.mTag, format(str, objArr));
    }

    /* renamed from: e */
    public void mo21041e(String str, Throwable th, Object... objArr) {
        Log.e(this.mTag, format(str, objArr), th);
    }

    public void wtf(String str, Throwable th, Object... objArr) {
        Log.wtf(this.mTag, format(str, objArr), th);
    }

    public void wtf(Throwable th) {
        Log.wtf(this.mTag, th);
    }

    /* access modifiers changed from: protected */
    public String format(String str, Object... objArr) {
        if (objArr != null && objArr.length > 0) {
            str = String.format(Locale.US, str, objArr);
        }
        return this.zzeu.concat(str);
    }
}
