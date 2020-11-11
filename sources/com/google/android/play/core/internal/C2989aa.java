package com.google.android.play.core.internal;

import android.os.Process;
import android.text.TextUtils;
import android.util.Log;
import java.util.IllegalFormatException;
import java.util.Locale;

/* renamed from: com.google.android.play.core.internal.aa */
public final class C2989aa {

    /* renamed from: a */
    private final String f1275a;

    public C2989aa(String str) {
        int myUid = Process.myUid();
        int myPid = Process.myPid();
        StringBuilder sb = new StringBuilder(39);
        sb.append("UID: [");
        sb.append(myUid);
        sb.append("]  PID: [");
        sb.append(myPid);
        sb.append("] ");
        String valueOf = String.valueOf(sb.toString());
        String valueOf2 = String.valueOf(str);
        this.f1275a = valueOf2.length() == 0 ? new String(valueOf) : valueOf.concat(valueOf2);
    }

    /* renamed from: a */
    private final int m616a(int i, String str, Object[] objArr) {
        if (Log.isLoggable("PlayCore", i)) {
            return Log.i("PlayCore", m617a(this.f1275a, str, objArr));
        }
        return 0;
    }

    /* renamed from: a */
    private static String m617a(String str, String str2, Object... objArr) {
        if (objArr.length > 0) {
            try {
                str2 = String.format(Locale.US, str2, objArr);
            } catch (IllegalFormatException e) {
                String valueOf = String.valueOf(str2);
                Log.e("PlayCore", valueOf.length() == 0 ? new String("Unable to format ") : "Unable to format ".concat(valueOf), e);
                String join = TextUtils.join(", ", objArr);
                StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 3 + String.valueOf(join).length());
                sb.append(str2);
                sb.append(" [");
                sb.append(join);
                sb.append("]");
                str2 = sb.toString();
            }
        }
        StringBuilder sb2 = new StringBuilder(String.valueOf(str).length() + 3 + String.valueOf(str2).length());
        sb2.append(str);
        sb2.append(" : ");
        sb2.append(str2);
        return sb2.toString();
    }

    /* renamed from: a */
    public final void mo44087a(String str, Object... objArr) {
        m616a(3, str, objArr);
    }

    /* renamed from: a */
    public final void mo44088a(Throwable th, String str, Object... objArr) {
        if (Log.isLoggable("PlayCore", 6)) {
            Log.e("PlayCore", m617a(this.f1275a, str, objArr), th);
        }
    }

    /* renamed from: b */
    public final void mo44089b(String str, Object... objArr) {
        m616a(6, str, objArr);
    }

    /* renamed from: c */
    public final void mo44090c(String str, Object... objArr) {
        m616a(4, str, objArr);
    }

    /* renamed from: d */
    public final void mo44091d(String str, Object... objArr) {
        m616a(5, str, objArr);
    }
}
