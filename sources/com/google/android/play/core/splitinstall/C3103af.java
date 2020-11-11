package com.google.android.play.core.splitinstall;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/* renamed from: com.google.android.play.core.splitinstall.af */
final class C3103af implements SplitInstallManager {

    /* renamed from: a */
    private final C3123az f1415a;
    /* access modifiers changed from: private */

    /* renamed from: b */
    public final C3100ac f1416b;

    /* renamed from: c */
    private final C3160z f1417c;

    /* renamed from: d */
    private final C3125ba f1418d;

    /* renamed from: e */
    private final Handler f1419e = new Handler(Looper.getMainLooper());

    C3103af(C3123az azVar, Context context) {
        String packageName = context.getPackageName();
        this.f1417c = new C3160z(context, packageName);
        this.f1415a = azVar;
        this.f1416b = C3100ac.m949a(context);
        this.f1418d = new C3125ba(context);
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public static List<String> m954b(List<Locale> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (Locale next : list) {
            if (Build.VERSION.SDK_INT >= 21) {
                arrayList.add(next.toLanguageTag());
            }
        }
        return arrayList;
    }

    public final Task<Void> cancelInstall(int i) {
        return this.f1415a.mo44285b(i);
    }

    public final Task<Void> deferredInstall(List<String> list) {
        return this.f1415a.mo44286b(list);
    }

    public final Task<Void> deferredLanguageInstall(List<Locale> list) {
        return Build.VERSION.SDK_INT < 21 ? Tasks.m1066a((Exception) new SplitInstallException(-5)) : this.f1415a.mo44287c(m954b(list));
    }

    public final Task<Void> deferredLanguageUninstall(List<Locale> list) {
        return Build.VERSION.SDK_INT < 21 ? Tasks.m1066a((Exception) new SplitInstallException(-5)) : this.f1415a.mo44288d(m954b(list));
    }

    public final Task<Void> deferredUninstall(List<String> list) {
        this.f1418d.mo44290a(list);
        return this.f1415a.mo44284a(list);
    }

    public final Set<String> getInstalledLanguages() {
        Set<String> b = this.f1417c.mo44308b();
        return b == null ? Collections.emptySet() : b;
    }

    public final Set<String> getInstalledModules() {
        return this.f1417c.mo44307a();
    }

    public final Task<SplitInstallSessionState> getSessionState(int i) {
        return this.f1415a.mo44282a(i);
    }

    public final Task<List<SplitInstallSessionState>> getSessionStates() {
        return this.f1415a.mo44281a();
    }

    public final synchronized void registerListener(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.f1416b.mo44197a(splitInstallStateUpdatedListener);
    }

    public final boolean startConfirmationDialogForResult(SplitInstallSessionState splitInstallSessionState, Activity activity, int i) throws IntentSender.SendIntentException {
        return startConfirmationDialogForResult(splitInstallSessionState, (IntentSenderForResultStarter) new C3102ae(activity), i);
    }

    public final boolean startConfirmationDialogForResult(SplitInstallSessionState splitInstallSessionState, IntentSenderForResultStarter intentSenderForResultStarter, int i) throws IntentSender.SendIntentException {
        if (splitInstallSessionState.status() != 8 || splitInstallSessionState.resolutionIntent() == null) {
            return false;
        }
        intentSenderForResultStarter.startIntentSenderForResult(splitInstallSessionState.resolutionIntent().getIntentSender(), i, (Intent) null, 0, 0, 0, (Bundle) null);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:13:0x0049, code lost:
        if (r2.containsAll(r3) != false) goto L_0x004b;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final com.google.android.play.core.tasks.Task<java.lang.Integer> startInstall(com.google.android.play.core.splitinstall.SplitInstallRequest r6) {
        /*
            r5 = this;
            java.util.List r0 = r6.getLanguages()
            boolean r0 = r0.isEmpty()
            r1 = 21
            if (r0 != 0) goto L_0x001c
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r1) goto L_0x0011
            goto L_0x001c
        L_0x0011:
            com.google.android.play.core.splitinstall.SplitInstallException r6 = new com.google.android.play.core.splitinstall.SplitInstallException
            r0 = -5
            r6.<init>(r0)
            com.google.android.play.core.tasks.Task r6 = com.google.android.play.core.tasks.Tasks.m1066a((java.lang.Exception) r6)
            return r6
        L_0x001c:
            java.util.List r0 = r6.getLanguages()
            com.google.android.play.core.splitinstall.z r2 = r5.f1417c
            java.util.Set r2 = r2.mo44308b()
            if (r2 == 0) goto L_0x004b
            java.util.HashSet r3 = new java.util.HashSet
            r3.<init>()
            java.util.Iterator r0 = r0.iterator()
        L_0x0031:
            boolean r4 = r0.hasNext()
            if (r4 == 0) goto L_0x0045
            java.lang.Object r4 = r0.next()
            java.util.Locale r4 = (java.util.Locale) r4
            java.lang.String r4 = r4.getLanguage()
            r3.add(r4)
            goto L_0x0031
        L_0x0045:
            boolean r0 = r2.containsAll(r3)
            if (r0 == 0) goto L_0x006f
        L_0x004b:
            java.util.List r0 = r6.getModuleNames()
            java.util.Set r2 = r5.getInstalledModules()
            boolean r0 = r2.containsAll(r0)
            if (r0 != 0) goto L_0x005a
            goto L_0x006f
        L_0x005a:
            int r0 = android.os.Build.VERSION.SDK_INT
            if (r0 < r1) goto L_0x0082
            java.util.List r0 = r6.getModuleNames()
            com.google.android.play.core.splitinstall.ba r1 = r5.f1418d
            java.util.Set r1 = r1.mo44289a()
            boolean r0 = java.util.Collections.disjoint(r0, r1)
            if (r0 == 0) goto L_0x006f
            goto L_0x0082
        L_0x006f:
            com.google.android.play.core.splitinstall.az r0 = r5.f1415a
            java.util.List r1 = r6.getModuleNames()
            java.util.List r6 = r6.getLanguages()
            java.util.List r6 = m954b(r6)
            com.google.android.play.core.tasks.Task r6 = r0.mo44283a(r1, r6)
            return r6
        L_0x0082:
            android.os.Handler r0 = r5.f1419e
            com.google.android.play.core.splitinstall.ad r1 = new com.google.android.play.core.splitinstall.ad
            r1.<init>(r5, r6)
            r0.post(r1)
            r6 = 0
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)
            com.google.android.play.core.tasks.Task r6 = com.google.android.play.core.tasks.Tasks.m1067a(r6)
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.splitinstall.C3103af.startInstall(com.google.android.play.core.splitinstall.SplitInstallRequest):com.google.android.play.core.tasks.Task");
    }

    public final synchronized void unregisterListener(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        this.f1416b.mo44200b(splitInstallStateUpdatedListener);
    }
}
