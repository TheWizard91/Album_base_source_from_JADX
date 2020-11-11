package com.google.android.play.core.missingsplits;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import com.google.android.play.core.internal.C2989aa;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/* renamed from: com.google.android.play.core.missingsplits.b */
final class C3078b implements MissingSplitsManager {

    /* renamed from: a */
    private static final C2989aa f1349a = new C2989aa("MissingSplitsManagerImpl");

    /* renamed from: b */
    private final Context f1350b;

    /* renamed from: c */
    private final Runtime f1351c;

    /* renamed from: d */
    private final C3077a f1352d;

    /* renamed from: e */
    private final AtomicReference<Boolean> f1353e;

    C3078b(Context context, Runtime runtime, C3077a aVar, AtomicReference<Boolean> atomicReference) {
        this.f1350b = context;
        this.f1351c = runtime;
        this.f1352d = aVar;
        this.f1353e = atomicReference;
    }

    /* renamed from: a */
    private final boolean m878a() {
        try {
            ApplicationInfo applicationInfo = this.f1350b.getPackageManager().getApplicationInfo(this.f1350b.getPackageName(), 128);
            return (applicationInfo == null || applicationInfo.metaData == null || !Boolean.TRUE.equals(applicationInfo.metaData.get("com.android.vending.splits.required"))) ? false : true;
        } catch (PackageManager.NameNotFoundException e) {
            f1349a.mo44091d("App '%s' is not found in the PackageManager", this.f1350b.getPackageName());
            return false;
        }
    }

    /* renamed from: a */
    private static boolean m879a(Set<String> set) {
        return set.isEmpty() || (set.size() == 1 && set.contains(""));
    }

    /* renamed from: b */
    private final Set<String> m880b() {
        if (Build.VERSION.SDK_INT < 21) {
            return Collections.emptySet();
        }
        try {
            PackageInfo packageInfo = this.f1350b.getPackageManager().getPackageInfo(this.f1350b.getPackageName(), 0);
            HashSet hashSet = new HashSet();
            if (packageInfo == null || packageInfo.splitNames == null) {
                return hashSet;
            }
            Collections.addAll(hashSet, packageInfo.splitNames);
            return hashSet;
        } catch (PackageManager.NameNotFoundException e) {
            f1349a.mo44091d("App '%s' is not found in PackageManager", this.f1350b.getPackageName());
            return Collections.emptySet();
        }
    }

    /* renamed from: c */
    private final List<ActivityManager.AppTask> m881c() {
        List<ActivityManager.AppTask> appTasks = ((ActivityManager) this.f1350b.getSystemService("activity")).getAppTasks();
        return appTasks == null ? Collections.emptyList() : appTasks;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0086 A[Catch:{ NameNotFoundException -> 0x006a }] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final boolean disableAppIfMissingRequiredSplits() {
        /*
            r8 = this;
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 21
            r2 = 0
            if (r0 < r1) goto L_0x01d1
            java.util.concurrent.atomic.AtomicReference<java.lang.Boolean> r0 = r8.f1353e
            monitor-enter(r0)
            java.util.concurrent.atomic.AtomicReference<java.lang.Boolean> r3 = r8.f1353e     // Catch:{ all -> 0x01ce }
            java.lang.Object r3 = r3.get()     // Catch:{ all -> 0x01ce }
            java.lang.Boolean r3 = (java.lang.Boolean) r3     // Catch:{ all -> 0x01ce }
            r4 = 1
            if (r3 == 0) goto L_0x0017
            goto L_0x00b1
        L_0x0017:
            java.util.concurrent.atomic.AtomicReference<java.lang.Boolean> r3 = r8.f1353e     // Catch:{ all -> 0x01ce }
            int r5 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x01ce }
            if (r5 < r1) goto L_0x00a9
            android.content.Context r5 = r8.f1350b     // Catch:{ all -> 0x01ce }
            android.content.pm.PackageManager r5 = r5.getPackageManager()     // Catch:{ all -> 0x01ce }
            android.content.Context r6 = r8.f1350b     // Catch:{ NameNotFoundException -> 0x0097 }
            java.lang.String r6 = r6.getPackageName()     // Catch:{ NameNotFoundException -> 0x0097 }
            r7 = 128(0x80, float:1.794E-43)
            android.content.pm.ApplicationInfo r5 = r5.getApplicationInfo(r6, r7)     // Catch:{ NameNotFoundException -> 0x0097 }
            if (r5 == 0) goto L_0x00a9
            android.os.Bundle r6 = r5.metaData     // Catch:{ NameNotFoundException -> 0x0097 }
            if (r6 == 0) goto L_0x00a9
            java.lang.Boolean r6 = java.lang.Boolean.TRUE     // Catch:{ NameNotFoundException -> 0x0097 }
            android.os.Bundle r5 = r5.metaData     // Catch:{ NameNotFoundException -> 0x0097 }
            java.lang.String r7 = "com.android.vending.splits.required"
            java.lang.Object r5 = r5.get(r7)     // Catch:{ NameNotFoundException -> 0x0097 }
            boolean r5 = r6.equals(r5)     // Catch:{ NameNotFoundException -> 0x0097 }
            if (r5 == 0) goto L_0x00a9
            int r5 = android.os.Build.VERSION.SDK_INT     // Catch:{ all -> 0x01ce }
            if (r5 < r1) goto L_0x007c
            android.content.Context r1 = r8.f1350b     // Catch:{ NameNotFoundException -> 0x006a }
            android.content.pm.PackageManager r1 = r1.getPackageManager()     // Catch:{ NameNotFoundException -> 0x006a }
            android.content.Context r5 = r8.f1350b     // Catch:{ NameNotFoundException -> 0x006a }
            java.lang.String r5 = r5.getPackageName()     // Catch:{ NameNotFoundException -> 0x006a }
            android.content.pm.PackageInfo r1 = r1.getPackageInfo(r5, r2)     // Catch:{ NameNotFoundException -> 0x006a }
            java.util.HashSet r5 = new java.util.HashSet     // Catch:{ NameNotFoundException -> 0x006a }
            r5.<init>()     // Catch:{ NameNotFoundException -> 0x006a }
            if (r1 == 0) goto L_0x0080
            java.lang.String[] r6 = r1.splitNames     // Catch:{ NameNotFoundException -> 0x006a }
            if (r6 == 0) goto L_0x0080
            java.lang.String[] r1 = r1.splitNames     // Catch:{ NameNotFoundException -> 0x006a }
            java.util.Collections.addAll(r5, r1)     // Catch:{ NameNotFoundException -> 0x006a }
            goto L_0x0080
        L_0x006a:
            r1 = move-exception
            com.google.android.play.core.internal.aa r1 = f1349a     // Catch:{ all -> 0x01ce }
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x01ce }
            android.content.Context r6 = r8.f1350b     // Catch:{ all -> 0x01ce }
            java.lang.String r6 = r6.getPackageName()     // Catch:{ all -> 0x01ce }
            r5[r2] = r6     // Catch:{ all -> 0x01ce }
            java.lang.String r6 = "App '%s' is not found in PackageManager"
            r1.mo44091d(r6, r5)     // Catch:{ all -> 0x01ce }
        L_0x007c:
            java.util.Set r5 = java.util.Collections.emptySet()     // Catch:{ all -> 0x01ce }
        L_0x0080:
            boolean r1 = r5.isEmpty()     // Catch:{ all -> 0x01ce }
            if (r1 != 0) goto L_0x0095
            int r1 = r5.size()     // Catch:{ all -> 0x01ce }
            if (r1 != r4) goto L_0x00a9
            java.lang.String r1 = ""
            boolean r1 = r5.contains(r1)     // Catch:{ all -> 0x01ce }
            if (r1 != 0) goto L_0x0095
            goto L_0x00a9
        L_0x0095:
            r1 = r4
            goto L_0x00aa
        L_0x0097:
            r1 = move-exception
            com.google.android.play.core.internal.aa r1 = f1349a     // Catch:{ all -> 0x01ce }
            java.lang.Object[] r5 = new java.lang.Object[r4]     // Catch:{ all -> 0x01ce }
            android.content.Context r6 = r8.f1350b     // Catch:{ all -> 0x01ce }
            java.lang.String r6 = r6.getPackageName()     // Catch:{ all -> 0x01ce }
            r5[r2] = r6     // Catch:{ all -> 0x01ce }
            java.lang.String r6 = "App '%s' is not found in the PackageManager"
            r1.mo44091d(r6, r5)     // Catch:{ all -> 0x01ce }
        L_0x00a9:
            r1 = r2
        L_0x00aa:
            java.lang.Boolean r1 = java.lang.Boolean.valueOf(r1)     // Catch:{ all -> 0x01ce }
            r3.set(r1)     // Catch:{ all -> 0x01ce }
        L_0x00b1:
            java.util.concurrent.atomic.AtomicReference<java.lang.Boolean> r1 = r8.f1353e     // Catch:{ all -> 0x01ce }
            java.lang.Object r1 = r1.get()     // Catch:{ all -> 0x01ce }
            java.lang.Boolean r1 = (java.lang.Boolean) r1     // Catch:{ all -> 0x01ce }
            boolean r1 = r1.booleanValue()     // Catch:{ all -> 0x01ce }
            monitor-exit(r0)     // Catch:{ all -> 0x01ce }
            if (r1 == 0) goto L_0x01bb
            java.util.List r0 = r8.m881c()
            java.util.Iterator r0 = r0.iterator()
        L_0x00c8:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x010a
            java.lang.Object r1 = r0.next()
            android.app.ActivityManager$AppTask r1 = (android.app.ActivityManager.AppTask) r1
            android.app.ActivityManager$RecentTaskInfo r3 = r1.getTaskInfo()
            if (r3 == 0) goto L_0x00c8
            android.app.ActivityManager$RecentTaskInfo r3 = r1.getTaskInfo()
            android.content.Intent r3 = r3.baseIntent
            if (r3 == 0) goto L_0x00c8
            android.app.ActivityManager$RecentTaskInfo r3 = r1.getTaskInfo()
            android.content.Intent r3 = r3.baseIntent
            android.content.ComponentName r3 = r3.getComponent()
            if (r3 == 0) goto L_0x00c8
            android.app.ActivityManager$RecentTaskInfo r1 = r1.getTaskInfo()
            android.content.Intent r1 = r1.baseIntent
            android.content.ComponentName r1 = r1.getComponent()
            java.lang.String r1 = r1.getClassName()
            java.lang.Class<com.google.android.play.core.missingsplits.PlayCoreMissingSplitsActivity> r3 = com.google.android.play.core.missingsplits.PlayCoreMissingSplitsActivity.class
            java.lang.String r3 = r3.getName()
            boolean r1 = r3.equals(r1)
            if (r1 == 0) goto L_0x00c8
            goto L_0x01ba
        L_0x010a:
            java.util.List r0 = r8.m881c()
            java.util.Iterator r0 = r0.iterator()
        L_0x0112:
            boolean r1 = r0.hasNext()
            if (r1 == 0) goto L_0x016f
            java.lang.Object r1 = r0.next()
            android.app.ActivityManager$AppTask r1 = (android.app.ActivityManager.AppTask) r1
            android.app.ActivityManager$RecentTaskInfo r1 = r1.getTaskInfo()
            if (r1 == 0) goto L_0x0112
            android.content.Intent r3 = r1.baseIntent
            if (r3 == 0) goto L_0x0112
            android.content.Intent r3 = r1.baseIntent
            android.content.ComponentName r3 = r3.getComponent()
            if (r3 == 0) goto L_0x0112
            android.content.Intent r1 = r1.baseIntent
            android.content.ComponentName r1 = r1.getComponent()
            java.lang.String r3 = r1.getClassName()
            java.lang.Class r1 = java.lang.Class.forName(r3)     // Catch:{ ClassNotFoundException -> 0x0154 }
        L_0x013e:
            if (r1 == 0) goto L_0x0112
            java.lang.Class<android.app.Activity> r3 = android.app.Activity.class
            boolean r3 = r1.equals(r3)
            if (r3 != 0) goto L_0x0152
            java.lang.Class r3 = r1.getSuperclass()
            if (r3 == r1) goto L_0x0150
            r1 = r3
            goto L_0x013e
        L_0x0150:
            r1 = 0
            goto L_0x013e
        L_0x0152:
            r0 = r4
            goto L_0x0170
        L_0x0154:
            r5 = move-exception
            com.google.android.play.core.internal.aa r5 = f1349a
            java.lang.Object[] r6 = new java.lang.Object[r4]
            r6[r2] = r3
            java.lang.String r3 = "ClassNotFoundException when scanning class hierarchy of '%s'"
            r5.mo44091d(r3, r6)
            android.content.Context r3 = r8.f1350b     // Catch:{ NameNotFoundException -> 0x016d }
            android.content.pm.PackageManager r3 = r3.getPackageManager()     // Catch:{ NameNotFoundException -> 0x016d }
            android.content.pm.ActivityInfo r1 = r3.getActivityInfo(r1, r2)     // Catch:{ NameNotFoundException -> 0x016d }
            if (r1 == 0) goto L_0x0112
            goto L_0x0152
        L_0x016d:
            r1 = move-exception
            goto L_0x0112
        L_0x016f:
            r0 = r2
        L_0x0170:
            com.google.android.play.core.missingsplits.a r1 = r8.f1352d
            r1.mo44209b()
            java.util.List r1 = r8.m881c()
            java.util.Iterator r1 = r1.iterator()
        L_0x017d:
            boolean r3 = r1.hasNext()
            if (r3 == 0) goto L_0x018d
            java.lang.Object r3 = r1.next()
            android.app.ActivityManager$AppTask r3 = (android.app.ActivityManager.AppTask) r3
            r3.finishAndRemoveTask()
            goto L_0x017d
        L_0x018d:
            if (r0 == 0) goto L_0x01b5
            android.content.Context r0 = r8.f1350b
            android.content.pm.PackageManager r0 = r0.getPackageManager()
            android.content.ComponentName r1 = new android.content.ComponentName
            android.content.Context r3 = r8.f1350b
            java.lang.Class<com.google.android.play.core.missingsplits.PlayCoreMissingSplitsActivity> r5 = com.google.android.play.core.missingsplits.PlayCoreMissingSplitsActivity.class
            r1.<init>(r3, r5)
            r0.setComponentEnabledSetting(r1, r4, r4)
            android.content.Intent r0 = new android.content.Intent
            android.content.Context r1 = r8.f1350b
            java.lang.Class<com.google.android.play.core.missingsplits.PlayCoreMissingSplitsActivity> r3 = com.google.android.play.core.missingsplits.PlayCoreMissingSplitsActivity.class
            r0.<init>(r1, r3)
            r1 = 884998144(0x34c00000, float:3.5762787E-7)
            android.content.Intent r0 = r0.addFlags(r1)
            android.content.Context r1 = r8.f1350b
            r1.startActivity(r0)
        L_0x01b5:
            java.lang.Runtime r0 = r8.f1351c
            r0.exit(r2)
        L_0x01ba:
            return r4
        L_0x01bb:
            com.google.android.play.core.missingsplits.a r0 = r8.f1352d
            boolean r0 = r0.mo44208a()
            if (r0 == 0) goto L_0x01cd
            com.google.android.play.core.missingsplits.a r0 = r8.f1352d
            r0.mo44210c()
            java.lang.Runtime r0 = r8.f1351c
            r0.exit(r2)
        L_0x01cd:
            return r2
        L_0x01ce:
            r1 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x01ce }
            throw r1
        L_0x01d1:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.missingsplits.C3078b.disableAppIfMissingRequiredSplits():boolean");
    }

    public final boolean isMissingRequiredSplits() {
        boolean booleanValue;
        synchronized (this.f1353e) {
            if (this.f1353e.get() == null) {
                AtomicReference<Boolean> atomicReference = this.f1353e;
                boolean z = false;
                if (Build.VERSION.SDK_INT >= 21 && m878a()) {
                    if (m879a(m880b())) {
                        z = true;
                    }
                }
                atomicReference.set(Boolean.valueOf(z));
            }
            booleanValue = this.f1353e.get().booleanValue();
        }
        return booleanValue;
    }
}
