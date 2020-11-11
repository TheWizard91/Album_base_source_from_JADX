package com.google.android.gms.measurement.internal;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import kotlinx.coroutines.DebugKt;

/* compiled from: com.google.android.gms:play-services-measurement-impl@@17.4.4 */
final class zzid implements Application.ActivityLifecycleCallbacks {
    private final /* synthetic */ zzhh zza;

    private zzid(zzhh zzhh) {
        this.zza = zzhh;
    }

    public final void onActivityStarted(Activity activity) {
    }

    public final void onActivityStopped(Activity activity) {
    }

    public final void onActivityCreated(Activity activity, Bundle bundle) {
        try {
            this.zza.zzr().zzx().zza("onActivityCreated");
            Intent intent = activity.getIntent();
            if (intent != null) {
                Uri data = intent.getData();
                if (data != null) {
                    if (data.isHierarchical()) {
                        this.zza.zzp();
                        this.zza.zzq().zza((Runnable) new zzic(this, bundle == null, data, zzkw.zza(intent) ? "gs" : DebugKt.DEBUG_PROPERTY_VALUE_AUTO, data.getQueryParameter("referrer")));
                        this.zza.zzi().zza(activity, bundle);
                        return;
                    }
                }
                this.zza.zzi().zza(activity, bundle);
            }
        } catch (Exception e) {
            this.zza.zzr().zzf().zza("Throwable caught in onActivityCreated", e);
        } finally {
            this.zza.zzi().zza(activity, bundle);
        }
    }

    /* access modifiers changed from: private */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00a7 A[SYNTHETIC, Splitter:B:33:0x00a7] */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0104 A[Catch:{ Exception -> 0x01df }] */
    /* JADX WARNING: Removed duplicated region for block: B:51:0x0115 A[SYNTHETIC, Splitter:B:51:0x0115] */
    /* JADX WARNING: Removed duplicated region for block: B:63:0x0144 A[Catch:{ Exception -> 0x01df }, RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:64:0x0145 A[Catch:{ Exception -> 0x01df }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final void zza(boolean r18, android.net.Uri r19, java.lang.String r20, java.lang.String r21) {
        /*
            r17 = this;
            r1 = r17
            r0 = r20
            r2 = r21
            com.google.android.gms.measurement.internal.zzhh r3 = r1.zza
            r3.zzd()
            com.google.android.gms.measurement.internal.zzhh r3 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzy r3 = r3.zzt()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r4 = com.google.android.gms.measurement.internal.zzaq.zzbh     // Catch:{ Exception -> 0x01df }
            boolean r3 = r3.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r4)     // Catch:{ Exception -> 0x01df }
            java.lang.String r4 = "Activity created with data 'referrer' without required params"
            java.lang.String r5 = "utm_medium"
            java.lang.String r6 = "_cis"
            java.lang.String r7 = "utm_source"
            java.lang.String r8 = "utm_campaign"
            java.lang.String r10 = "gclid"
            if (r3 != 0) goto L_0x0045
            com.google.android.gms.measurement.internal.zzhh r3 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzy r3 = r3.zzt()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r11 = com.google.android.gms.measurement.internal.zzaq.zzbj     // Catch:{ Exception -> 0x01df }
            boolean r3 = r3.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r11)     // Catch:{ Exception -> 0x01df }
            if (r3 != 0) goto L_0x0045
            com.google.android.gms.measurement.internal.zzhh r3 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzy r3 = r3.zzt()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r11 = com.google.android.gms.measurement.internal.zzaq.zzbi     // Catch:{ Exception -> 0x01df }
            boolean r3 = r3.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r11)     // Catch:{ Exception -> 0x01df }
            if (r3 == 0) goto L_0x0043
            goto L_0x0045
        L_0x0043:
            r3 = 0
            goto L_0x00a0
        L_0x0045:
            com.google.android.gms.measurement.internal.zzhh r3 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzkw r3 = r3.zzp()     // Catch:{ Exception -> 0x01df }
            boolean r11 = android.text.TextUtils.isEmpty(r21)     // Catch:{ Exception -> 0x01df }
            if (r11 == 0) goto L_0x0053
            r3 = 0
            goto L_0x009f
        L_0x0053:
            boolean r11 = r2.contains(r10)     // Catch:{ Exception -> 0x01df }
            if (r11 != 0) goto L_0x0078
            boolean r11 = r2.contains(r8)     // Catch:{ Exception -> 0x01df }
            if (r11 != 0) goto L_0x0078
            boolean r11 = r2.contains(r7)     // Catch:{ Exception -> 0x01df }
            if (r11 != 0) goto L_0x0078
            boolean r11 = r2.contains(r5)     // Catch:{ Exception -> 0x01df }
            if (r11 != 0) goto L_0x0078
            com.google.android.gms.measurement.internal.zzez r3 = r3.zzr()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzfb r3 = r3.zzw()     // Catch:{ Exception -> 0x01df }
            r3.zza(r4)     // Catch:{ Exception -> 0x01df }
            r3 = 0
            goto L_0x009f
        L_0x0078:
            java.lang.String r11 = "https://google.com/search?"
            java.lang.String r12 = java.lang.String.valueOf(r21)     // Catch:{ Exception -> 0x01df }
            int r13 = r12.length()     // Catch:{ Exception -> 0x01df }
            if (r13 == 0) goto L_0x0089
            java.lang.String r11 = r11.concat(r12)     // Catch:{ Exception -> 0x01df }
            goto L_0x008f
        L_0x0089:
            java.lang.String r12 = new java.lang.String     // Catch:{ Exception -> 0x01df }
            r12.<init>(r11)     // Catch:{ Exception -> 0x01df }
            r11 = r12
        L_0x008f:
            android.net.Uri r11 = android.net.Uri.parse(r11)     // Catch:{ Exception -> 0x01df }
            android.os.Bundle r3 = r3.zza((android.net.Uri) r11)     // Catch:{ Exception -> 0x01df }
            if (r3 == 0) goto L_0x009e
            java.lang.String r11 = "referrer"
            r3.putString(r6, r11)     // Catch:{ Exception -> 0x01df }
        L_0x009e:
        L_0x009f:
        L_0x00a0:
            r11 = 0
            java.lang.String r12 = "_cmp"
            r13 = 1
            if (r18 == 0) goto L_0x0104
            com.google.android.gms.measurement.internal.zzhh r14 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzkw r14 = r14.zzp()     // Catch:{ Exception -> 0x01df }
            r15 = r19
            android.os.Bundle r14 = r14.zza((android.net.Uri) r15)     // Catch:{ Exception -> 0x01df }
            if (r14 == 0) goto L_0x0105
            java.lang.String r15 = "intent"
            r14.putString(r6, r15)     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzhh r6 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzy r6 = r6.zzt()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r15 = com.google.android.gms.measurement.internal.zzaq.zzbh     // Catch:{ Exception -> 0x01df }
            boolean r6 = r6.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r15)     // Catch:{ Exception -> 0x01df }
            if (r6 == 0) goto L_0x00e9
            boolean r6 = r14.containsKey(r10)     // Catch:{ Exception -> 0x01df }
            if (r6 != 0) goto L_0x00e9
            if (r3 == 0) goto L_0x00e9
            boolean r6 = r3.containsKey(r10)     // Catch:{ Exception -> 0x01df }
            if (r6 == 0) goto L_0x00e9
            java.lang.String r6 = "_cer"
            java.lang.String r15 = "gclid=%s"
            java.lang.Object[] r9 = new java.lang.Object[r13]     // Catch:{ Exception -> 0x01df }
            java.lang.String r16 = r3.getString(r10)     // Catch:{ Exception -> 0x01df }
            r9[r11] = r16     // Catch:{ Exception -> 0x01df }
            java.lang.String r9 = java.lang.String.format(r15, r9)     // Catch:{ Exception -> 0x01df }
            r14.putString(r6, r9)     // Catch:{ Exception -> 0x01df }
        L_0x00e9:
            com.google.android.gms.measurement.internal.zzhh r6 = r1.zza     // Catch:{ Exception -> 0x01df }
            r6.zza((java.lang.String) r0, (java.lang.String) r12, (android.os.Bundle) r14)     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzhh r6 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzy r6 = r6.zzt()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r9 = com.google.android.gms.measurement.internal.zzaq.zzcq     // Catch:{ Exception -> 0x01df }
            boolean r6 = r6.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r9)     // Catch:{ Exception -> 0x01df }
            if (r6 == 0) goto L_0x0105
            com.google.android.gms.measurement.internal.zzhh r6 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzp r6 = r6.zzb     // Catch:{ Exception -> 0x01df }
            r6.zza(r0, r14)     // Catch:{ Exception -> 0x01df }
            goto L_0x0105
        L_0x0104:
            r14 = 0
        L_0x0105:
            com.google.android.gms.measurement.internal.zzhh r6 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzy r6 = r6.zzt()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r9 = com.google.android.gms.measurement.internal.zzaq.zzbj     // Catch:{ Exception -> 0x01df }
            boolean r6 = r6.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r9)     // Catch:{ Exception -> 0x01df }
            java.lang.String r9 = "auto"
            if (r6 == 0) goto L_0x013e
            com.google.android.gms.measurement.internal.zzhh r6 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzy r6 = r6.zzt()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r15 = com.google.android.gms.measurement.internal.zzaq.zzbi     // Catch:{ Exception -> 0x01df }
            boolean r6 = r6.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r15)     // Catch:{ Exception -> 0x01df }
            if (r6 != 0) goto L_0x013e
            if (r3 == 0) goto L_0x013e
            boolean r6 = r3.containsKey(r10)     // Catch:{ Exception -> 0x01df }
            if (r6 == 0) goto L_0x013e
            if (r14 == 0) goto L_0x0133
            boolean r6 = r14.containsKey(r10)     // Catch:{ Exception -> 0x01df }
            if (r6 != 0) goto L_0x013e
        L_0x0133:
            com.google.android.gms.measurement.internal.zzhh r6 = r1.zza     // Catch:{ Exception -> 0x01df }
            java.lang.String r14 = "_lgclid"
            java.lang.String r15 = r3.getString(r10)     // Catch:{ Exception -> 0x01df }
            r6.zza((java.lang.String) r9, (java.lang.String) r14, (java.lang.Object) r15, (boolean) r13)     // Catch:{ Exception -> 0x01df }
        L_0x013e:
            boolean r6 = android.text.TextUtils.isEmpty(r21)     // Catch:{ Exception -> 0x01df }
            if (r6 == 0) goto L_0x0145
            return
        L_0x0145:
            com.google.android.gms.measurement.internal.zzhh r6 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzez r6 = r6.zzr()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzfb r6 = r6.zzw()     // Catch:{ Exception -> 0x01df }
            java.lang.String r14 = "Activity created with referrer"
            r6.zza(r14, r2)     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzhh r6 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzy r6 = r6.zzt()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r14 = com.google.android.gms.measurement.internal.zzaq.zzbi     // Catch:{ Exception -> 0x01df }
            boolean r6 = r6.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r14)     // Catch:{ Exception -> 0x01df }
            java.lang.String r14 = "_ldl"
            if (r6 == 0) goto L_0x0197
            if (r3 == 0) goto L_0x0181
            com.google.android.gms.measurement.internal.zzhh r2 = r1.zza     // Catch:{ Exception -> 0x01df }
            r2.zza((java.lang.String) r0, (java.lang.String) r12, (android.os.Bundle) r3)     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzhh r2 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzy r2 = r2.zzt()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean> r4 = com.google.android.gms.measurement.internal.zzaq.zzcq     // Catch:{ Exception -> 0x01df }
            boolean r2 = r2.zza((com.google.android.gms.measurement.internal.zzeo<java.lang.Boolean>) r4)     // Catch:{ Exception -> 0x01df }
            if (r2 == 0) goto L_0x0190
            com.google.android.gms.measurement.internal.zzhh r2 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzp r2 = r2.zzb     // Catch:{ Exception -> 0x01df }
            r2.zza(r0, r3)     // Catch:{ Exception -> 0x01df }
            goto L_0x0190
        L_0x0181:
            com.google.android.gms.measurement.internal.zzhh r0 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzez r0 = r0.zzr()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzw()     // Catch:{ Exception -> 0x01df }
            java.lang.String r3 = "Referrer does not contain valid parameters"
            r0.zza(r3, r2)     // Catch:{ Exception -> 0x01df }
        L_0x0190:
            com.google.android.gms.measurement.internal.zzhh r0 = r1.zza     // Catch:{ Exception -> 0x01df }
            r2 = 0
            r0.zza((java.lang.String) r9, (java.lang.String) r14, (java.lang.Object) r2, (boolean) r13)     // Catch:{ Exception -> 0x01df }
            return
        L_0x0197:
            boolean r0 = r2.contains(r10)     // Catch:{ Exception -> 0x01df }
            if (r0 == 0) goto L_0x01c2
            boolean r0 = r2.contains(r8)     // Catch:{ Exception -> 0x01df }
            if (r0 != 0) goto L_0x01c0
            boolean r0 = r2.contains(r7)     // Catch:{ Exception -> 0x01df }
            if (r0 != 0) goto L_0x01c0
            boolean r0 = r2.contains(r5)     // Catch:{ Exception -> 0x01df }
            if (r0 != 0) goto L_0x01c0
            java.lang.String r0 = "utm_term"
            boolean r0 = r2.contains(r0)     // Catch:{ Exception -> 0x01df }
            if (r0 != 0) goto L_0x01c0
            java.lang.String r0 = "utm_content"
            boolean r0 = r2.contains(r0)     // Catch:{ Exception -> 0x01df }
            if (r0 == 0) goto L_0x01c2
        L_0x01c0:
            r11 = r13
            goto L_0x01c3
        L_0x01c2:
        L_0x01c3:
            if (r11 != 0) goto L_0x01d3
            com.google.android.gms.measurement.internal.zzhh r0 = r1.zza     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzez r0 = r0.zzr()     // Catch:{ Exception -> 0x01df }
            com.google.android.gms.measurement.internal.zzfb r0 = r0.zzw()     // Catch:{ Exception -> 0x01df }
            r0.zza(r4)     // Catch:{ Exception -> 0x01df }
            return
        L_0x01d3:
            boolean r0 = android.text.TextUtils.isEmpty(r21)     // Catch:{ Exception -> 0x01df }
            if (r0 != 0) goto L_0x01de
            com.google.android.gms.measurement.internal.zzhh r0 = r1.zza     // Catch:{ Exception -> 0x01df }
            r0.zza((java.lang.String) r9, (java.lang.String) r14, (java.lang.Object) r2, (boolean) r13)     // Catch:{ Exception -> 0x01df }
        L_0x01de:
            return
        L_0x01df:
            r0 = move-exception
            com.google.android.gms.measurement.internal.zzhh r2 = r1.zza
            com.google.android.gms.measurement.internal.zzez r2 = r2.zzr()
            com.google.android.gms.measurement.internal.zzfb r2 = r2.zzf()
            java.lang.String r3 = "Throwable caught in handleReferrerForOnActivityCreated"
            r2.zza(r3, r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.measurement.internal.zzid.zza(boolean, android.net.Uri, java.lang.String, java.lang.String):void");
    }

    public final void onActivityDestroyed(Activity activity) {
        this.zza.zzi().zzc(activity);
    }

    public final void onActivityPaused(Activity activity) {
        this.zza.zzi().zzb(activity);
        zzjw zzk = this.zza.zzk();
        zzk.zzq().zza((Runnable) new zzjy(zzk, zzk.zzm().elapsedRealtime()));
    }

    public final void onActivityResumed(Activity activity) {
        zzjw zzk = this.zza.zzk();
        zzk.zzq().zza((Runnable) new zzjz(zzk, zzk.zzm().elapsedRealtime()));
        this.zza.zzi().zza(activity);
    }

    public final void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        this.zza.zzi().zzb(activity, bundle);
    }

    /* synthetic */ zzid(zzhh zzhh, zzhi zzhi) {
        this(zzhh);
    }
}
