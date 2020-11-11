package com.google.android.play.core.internal;

import android.content.Context;
import android.content.Intent;
import android.content.pm.Signature;
import android.util.Log;
import com.google.android.play.core.splitcompat.C3082d;
import com.google.android.play.core.splitcompat.C3083e;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.List;

/* renamed from: com.google.android.play.core.internal.ao */
public final class C3003ao {

    /* renamed from: a */
    private final C3083e f1305a;

    /* renamed from: b */
    private final Context f1306b;

    /* renamed from: c */
    private final C3082d f1307c;

    public C3003ao(Context context, C3083e eVar, C3082d dVar, byte[] bArr) {
        this.f1305a = eVar;
        this.f1307c = dVar;
        this.f1306b = context;
    }

    /* renamed from: a */
    private static X509Certificate m655a(Signature signature) {
        try {
            return (X509Certificate) CertificateFactory.getInstance("X509").generateCertificate(new ByteArrayInputStream(signature.toByteArray()));
        } catch (CertificateException e) {
            Log.e("SplitCompat", "Cannot decode certificate.", e);
            return null;
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:51:?, code lost:
        r5 = new java.lang.StringBuilder(java.lang.String.valueOf(r8).length() + 32);
        r5.append("Downloaded split ");
        r5.append(r8);
        r5.append(" is not signed.");
        r0 = r5.toString();
     */
    /* renamed from: a */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean mo44105a() {
        /*
            r14 = this;
            java.lang.String r0 = " is not signed."
            java.lang.String r1 = "Downloaded split "
            java.lang.String r2 = "SplitCompat"
            r3 = 0
            com.google.android.play.core.splitcompat.e r4 = r14.f1305a     // Catch:{ IOException -> 0x0101 }
            java.io.File r4 = r4.mo44226c()     // Catch:{ IOException -> 0x0101 }
            r5 = 0
            android.content.Context r6 = r14.f1306b     // Catch:{ NameNotFoundException -> 0x0023 }
            android.content.pm.PackageManager r6 = r6.getPackageManager()     // Catch:{ NameNotFoundException -> 0x0023 }
            android.content.Context r7 = r14.f1306b     // Catch:{ NameNotFoundException -> 0x0023 }
            java.lang.String r7 = r7.getPackageName()     // Catch:{ NameNotFoundException -> 0x0023 }
            r8 = 64
            android.content.pm.PackageInfo r6 = r6.getPackageInfo(r7, r8)     // Catch:{ NameNotFoundException -> 0x0023 }
            android.content.pm.Signature[] r6 = r6.signatures     // Catch:{ NameNotFoundException -> 0x0023 }
            goto L_0x0025
        L_0x0023:
            r6 = move-exception
            r6 = r5
        L_0x0025:
            if (r6 == 0) goto L_0x003f
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            int r7 = r6.length
            r8 = r3
        L_0x002e:
            if (r8 >= r7) goto L_0x003f
            r9 = r6[r8]
            java.security.cert.X509Certificate r9 = m655a((android.content.pm.Signature) r9)
            if (r9 != 0) goto L_0x0039
            goto L_0x003c
        L_0x0039:
            r5.add(r9)
        L_0x003c:
            int r8 = r8 + 1
            goto L_0x002e
        L_0x003f:
            if (r5 != 0) goto L_0x0043
            goto L_0x00fb
        L_0x0043:
            boolean r6 = r5.isEmpty()
            if (r6 != 0) goto L_0x00fb
            java.io.File[] r4 = r4.listFiles()
            java.util.Arrays.sort(r4)
            int r6 = r4.length
        L_0x0051:
            int r6 = r6 + -1
            if (r6 < 0) goto L_0x00f9
            r7 = r4[r6]
            java.lang.String r8 = r7.getAbsolutePath()     // Catch:{ Exception -> 0x00f2 }
            java.security.cert.X509Certificate[][] r9 = com.google.android.play.core.internal.C3056h.m793a((java.lang.String) r8)     // Catch:{ Exception -> 0x00cc }
            if (r9 != 0) goto L_0x0062
            goto L_0x00af
        L_0x0062:
            int r10 = r9.length     // Catch:{ Exception -> 0x00f2 }
            if (r10 == 0) goto L_0x00af
            r10 = r9[r3]     // Catch:{ Exception -> 0x00f2 }
            int r10 = r10.length     // Catch:{ Exception -> 0x00f2 }
            if (r10 == 0) goto L_0x00af
            boolean r8 = r5.isEmpty()     // Catch:{ Exception -> 0x00f2 }
            if (r8 == 0) goto L_0x0077
            java.lang.String r0 = "No certificates found for app."
        L_0x0072:
            android.util.Log.e(r2, r0)     // Catch:{ Exception -> 0x00f2 }
            goto L_0x00ec
        L_0x0077:
            java.util.Iterator r8 = r5.iterator()     // Catch:{ Exception -> 0x00f2 }
        L_0x007b:
            boolean r10 = r8.hasNext()     // Catch:{ Exception -> 0x00f2 }
            if (r10 == 0) goto L_0x009e
            java.lang.Object r10 = r8.next()     // Catch:{ Exception -> 0x00f2 }
            java.security.cert.X509Certificate r10 = (java.security.cert.X509Certificate) r10     // Catch:{ Exception -> 0x00f2 }
            int r11 = r9.length     // Catch:{ Exception -> 0x00f2 }
            r12 = r3
        L_0x0089:
            if (r12 < r11) goto L_0x0091
            java.lang.String r0 = "There's an app certificate that doesn't sign the split."
            android.util.Log.i(r2, r0)     // Catch:{ Exception -> 0x00f2 }
            goto L_0x00ec
        L_0x0091:
            r13 = r9[r12]     // Catch:{ Exception -> 0x00f2 }
            r13 = r13[r3]     // Catch:{ Exception -> 0x00f2 }
            boolean r13 = r13.equals(r10)     // Catch:{ Exception -> 0x00f2 }
            if (r13 != 0) goto L_0x007b
            int r12 = r12 + 1
            goto L_0x0089
        L_0x009e:
            com.google.android.play.core.splitcompat.e r8 = r14.f1305a     // Catch:{ IOException -> 0x00a8 }
            java.io.File r8 = r8.mo44219a((java.io.File) r7)     // Catch:{ IOException -> 0x00a8 }
            r7.renameTo(r8)     // Catch:{ IOException -> 0x00a8 }
            goto L_0x0051
        L_0x00a8:
            r0 = move-exception
            java.lang.String r1 = "Cannot write verified split."
            android.util.Log.e(r2, r1, r0)
            return r3
        L_0x00af:
            java.lang.String r4 = java.lang.String.valueOf(r8)     // Catch:{ Exception -> 0x00f2 }
            int r4 = r4.length()     // Catch:{ Exception -> 0x00f2 }
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f2 }
            int r4 = r4 + 32
            r5.<init>(r4)     // Catch:{ Exception -> 0x00f2 }
            r5.append(r1)     // Catch:{ Exception -> 0x00f2 }
            r5.append(r8)     // Catch:{ Exception -> 0x00f2 }
            r5.append(r0)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r0 = r5.toString()     // Catch:{ Exception -> 0x00f2 }
            goto L_0x0072
        L_0x00cc:
            r4 = move-exception
            java.lang.String r5 = java.lang.String.valueOf(r8)     // Catch:{ Exception -> 0x00f2 }
            int r5 = r5.length()     // Catch:{ Exception -> 0x00f2 }
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ Exception -> 0x00f2 }
            int r5 = r5 + 32
            r6.<init>(r5)     // Catch:{ Exception -> 0x00f2 }
            r6.append(r1)     // Catch:{ Exception -> 0x00f2 }
            r6.append(r8)     // Catch:{ Exception -> 0x00f2 }
            r6.append(r0)     // Catch:{ Exception -> 0x00f2 }
            java.lang.String r0 = r6.toString()     // Catch:{ Exception -> 0x00f2 }
            android.util.Log.e(r2, r0, r4)     // Catch:{ Exception -> 0x00f2 }
        L_0x00ec:
            java.lang.String r0 = "Split verification failure."
            android.util.Log.e(r2, r0)     // Catch:{ Exception -> 0x00f2 }
            return r3
        L_0x00f2:
            r0 = move-exception
            java.lang.String r1 = "Split verification error."
            android.util.Log.e(r2, r1, r0)
            return r3
        L_0x00f9:
            r0 = 1
            return r0
        L_0x00fb:
            java.lang.String r0 = "No app certificates found."
            android.util.Log.e(r2, r0)
            return r3
        L_0x0101:
            r0 = move-exception
            java.lang.String r1 = "Cannot access directory for unverified splits."
            android.util.Log.e(r2, r1, r0)
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.internal.C3003ao.mo44105a():boolean");
    }

    /* renamed from: a */
    public final boolean mo44106a(List<Intent> list) throws IOException {
        for (Intent stringExtra : list) {
            if (!this.f1305a.mo44224b(stringExtra.getStringExtra("split_id")).exists()) {
                return false;
            }
        }
        return true;
    }
}
