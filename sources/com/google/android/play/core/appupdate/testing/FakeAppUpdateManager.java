package com.google.android.play.core.appupdate.testing;

import android.app.Activity;
import android.content.Context;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateOptions;
import com.google.android.play.core.appupdate.C2843a;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.install.InstallState;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;

public class FakeAppUpdateManager implements AppUpdateManager {

    /* renamed from: a */
    private final C2843a f859a;

    /* renamed from: b */
    private final Context f860b;

    /* renamed from: c */
    private int f861c = 0;

    /* renamed from: d */
    private int f862d = 0;

    /* renamed from: e */
    private boolean f863e = false;

    /* renamed from: f */
    private int f864f = 0;

    /* renamed from: g */
    private Integer f865g = null;

    /* renamed from: h */
    private int f866h = 0;

    /* renamed from: i */
    private long f867i = 0;

    /* renamed from: j */
    private long f868j = 0;

    /* renamed from: k */
    private boolean f869k = false;

    /* renamed from: l */
    private boolean f870l = false;

    /* renamed from: m */
    private boolean f871m = false;

    /* renamed from: n */
    private Integer f872n;

    /* renamed from: o */
    private Integer f873o;

    public FakeAppUpdateManager(Context context) {
        this.f859a = new C2843a(context);
        this.f860b = context;
    }

    /* renamed from: a */
    private final int m294a() {
        if (!this.f863e) {
            return 1;
        }
        int i = this.f861c;
        return (i == 0 || i == 4 || i == 5 || i == 6) ? 2 : 3;
    }

    /* renamed from: a */
    private final boolean m295a(AppUpdateInfo appUpdateInfo, AppUpdateOptions appUpdateOptions) {
        int i;
        if (!appUpdateInfo.isUpdateTypeAllowed(appUpdateOptions) && (!AppUpdateOptions.defaultOptions(appUpdateOptions.appUpdateType()).equals(appUpdateOptions) || !appUpdateInfo.isUpdateTypeAllowed(appUpdateOptions.appUpdateType()))) {
            return false;
        }
        if (appUpdateOptions.appUpdateType() == 1) {
            this.f870l = true;
            i = 1;
        } else {
            this.f869k = true;
            i = 0;
        }
        this.f873o = i;
        return true;
    }

    /* renamed from: b */
    private final void m296b() {
        this.f859a.mo44198a(InstallState.m612a(this.f861c, this.f867i, this.f868j, this.f862d, this.f860b.getPackageName()));
    }

    public Task<Void> completeUpdate() {
        int i = this.f862d;
        if (i != 0 && i != 1) {
            return Tasks.m1066a((Exception) new InstallException(i));
        }
        int i2 = this.f861c;
        if (i2 != 11) {
            return i2 == 3 ? Tasks.m1066a((Exception) new InstallException(-8)) : Tasks.m1066a((Exception) new InstallException(-7));
        }
        this.f861c = 3;
        this.f871m = true;
        Integer num = 0;
        if (num.equals(this.f873o)) {
            m296b();
        }
        return Tasks.m1067a(null);
    }

    public void downloadCompletes() {
        int i = this.f861c;
        if (i == 2 || i == 1) {
            this.f861c = 11;
            this.f867i = 0;
            this.f868j = 0;
            Integer num = 0;
            if (num.equals(this.f873o)) {
                m296b();
                return;
            }
            Integer num2 = 1;
            if (num2.equals(this.f873o)) {
                completeUpdate();
            }
        }
    }

    public void downloadFails() {
        int i = this.f861c;
        if (i == 1 || i == 2) {
            this.f861c = 5;
            Integer num = 0;
            if (num.equals(this.f873o)) {
                m296b();
            }
            this.f873o = null;
            this.f870l = false;
            this.f861c = 0;
        }
    }

    public void downloadStarts() {
        if (this.f861c == 1) {
            this.f861c = 2;
            Integer num = 0;
            if (num.equals(this.f873o)) {
                m296b();
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:11:0x002e, code lost:
        if (r1.equals(r0.f872n) == false) goto L_0x003f;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0058, code lost:
        if (r1.equals(r0.f872n) == false) goto L_0x0069;
     */
    /* JADX WARNING: Removed duplicated region for block: B:16:0x0047  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0071  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public com.google.android.play.core.tasks.Task<com.google.android.play.core.appupdate.AppUpdateInfo> getAppUpdateInfo() {
        /*
            r24 = this;
            r0 = r24
            int r1 = r0.f862d
            r2 = 1
            if (r1 != 0) goto L_0x0008
            goto L_0x0014
        L_0x0008:
            if (r1 == r2) goto L_0x0014
            com.google.android.play.core.install.InstallException r2 = new com.google.android.play.core.install.InstallException
            r2.<init>(r1)
            com.google.android.play.core.tasks.Task r1 = com.google.android.play.core.tasks.Tasks.m1066a((java.lang.Exception) r2)
            return r1
        L_0x0014:
            int r1 = r24.m294a()
            r3 = 2
            r4 = 0
            r5 = 0
            if (r1 != r3) goto L_0x003f
            int r1 = r0.f862d
            if (r1 != 0) goto L_0x0022
            goto L_0x0031
        L_0x0022:
            if (r1 != r2) goto L_0x003f
            java.lang.Integer r1 = java.lang.Integer.valueOf(r4)
            java.lang.Integer r6 = r0.f872n
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x0031
            goto L_0x003f
        L_0x0031:
            android.content.Context r1 = r0.f860b
            android.content.Intent r6 = new android.content.Intent
            r6.<init>()
            android.app.PendingIntent r1 = android.app.PendingIntent.getBroadcast(r1, r4, r6, r4)
            r21 = r1
            goto L_0x0041
        L_0x003f:
            r21 = r5
        L_0x0041:
            int r1 = r24.m294a()
            if (r1 != r3) goto L_0x0069
            int r1 = r0.f862d
            if (r1 != 0) goto L_0x004c
            goto L_0x005b
        L_0x004c:
            if (r1 != r2) goto L_0x0069
            java.lang.Integer r1 = java.lang.Integer.valueOf(r2)
            java.lang.Integer r6 = r0.f872n
            boolean r1 = r1.equals(r6)
            if (r1 != 0) goto L_0x005b
            goto L_0x0069
        L_0x005b:
            android.content.Context r1 = r0.f860b
            android.content.Intent r6 = new android.content.Intent
            r6.<init>()
            android.app.PendingIntent r1 = android.app.PendingIntent.getBroadcast(r1, r4, r6, r4)
            r20 = r1
            goto L_0x006b
        L_0x0069:
            r20 = r5
        L_0x006b:
            int r1 = r24.m294a()
            if (r1 != r3) goto L_0x0094
            int r1 = r0.f862d
            if (r1 != 0) goto L_0x0076
            goto L_0x0079
        L_0x0076:
            if (r1 == r2) goto L_0x0079
            goto L_0x0094
        L_0x0079:
            android.content.Context r1 = r0.f860b
            android.content.Intent r2 = new android.content.Intent
            r2.<init>()
            android.app.PendingIntent r5 = android.app.PendingIntent.getBroadcast(r1, r4, r2, r4)
            android.content.Context r1 = r0.f860b
            android.content.Intent r2 = new android.content.Intent
            r2.<init>()
            android.app.PendingIntent r1 = android.app.PendingIntent.getBroadcast(r1, r4, r2, r4)
            r22 = r1
            r23 = r5
            goto L_0x0098
        L_0x0094:
            r22 = r5
            r23 = r22
        L_0x0098:
            android.content.Context r1 = r0.f860b
            java.lang.String r6 = r1.getPackageName()
            int r7 = r0.f864f
            int r8 = r24.m294a()
            int r9 = r0.f861c
            java.lang.Integer r10 = r0.f865g
            int r11 = r0.f866h
            long r12 = r0.f867i
            long r14 = r0.f868j
            r16 = 0
            r18 = 0
            com.google.android.play.core.appupdate.AppUpdateInfo r1 = com.google.android.play.core.appupdate.AppUpdateInfo.m257a(r6, r7, r8, r9, r10, r11, r12, r14, r16, r18, r20, r21, r22, r23)
            com.google.android.play.core.tasks.Task r1 = com.google.android.play.core.tasks.Tasks.m1067a(r1)
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.appupdate.testing.FakeAppUpdateManager.getAppUpdateInfo():com.google.android.play.core.tasks.Task");
    }

    public Integer getPartiallyAllowedUpdateType() {
        return this.f872n;
    }

    public Integer getTypeForUpdateInProgress() {
        return this.f873o;
    }

    public void installCompletes() {
        if (this.f861c == 3) {
            this.f861c = 4;
            this.f863e = false;
            this.f864f = 0;
            this.f865g = null;
            this.f866h = 0;
            this.f867i = 0;
            this.f868j = 0;
            this.f870l = false;
            this.f871m = false;
            Integer num = 0;
            if (num.equals(this.f873o)) {
                m296b();
            }
            this.f873o = null;
            this.f861c = 0;
        }
    }

    public void installFails() {
        if (this.f861c == 3) {
            this.f861c = 5;
            Integer num = 0;
            if (num.equals(this.f873o)) {
                m296b();
            }
            this.f873o = null;
            this.f871m = false;
            this.f870l = false;
            this.f861c = 0;
        }
    }

    public boolean isConfirmationDialogVisible() {
        return this.f869k;
    }

    public boolean isImmediateFlowVisible() {
        return this.f870l;
    }

    public boolean isInstallSplashScreenVisible() {
        return this.f871m;
    }

    public void registerListener(InstallStateUpdatedListener installStateUpdatedListener) {
        this.f859a.mo44197a(installStateUpdatedListener);
    }

    public void setBytesDownloaded(long j) {
        if (this.f861c == 2 && j <= this.f868j) {
            this.f867i = j;
            Integer num = 0;
            if (num.equals(this.f873o)) {
                m296b();
            }
        }
    }

    public void setClientVersionStalenessDays(Integer num) {
        if (this.f863e) {
            this.f865g = num;
        }
    }

    public void setInstallErrorCode(int i) {
        this.f862d = i;
    }

    public void setPartiallyAllowedUpdateType(Integer num) {
        this.f872n = num;
        this.f862d = 1;
    }

    public void setTotalBytesToDownload(long j) {
        if (this.f861c == 2) {
            this.f868j = j;
            Integer num = 0;
            if (num.equals(this.f873o)) {
                m296b();
            }
        }
    }

    public void setUpdateAvailable(int i) {
        this.f863e = true;
        this.f864f = i;
    }

    public void setUpdateNotAvailable() {
        this.f863e = false;
        this.f865g = null;
    }

    public void setUpdatePriority(int i) {
        if (this.f863e) {
            this.f866h = i;
        }
    }

    public final Task<Integer> startUpdateFlow(AppUpdateInfo appUpdateInfo, Activity activity, AppUpdateOptions appUpdateOptions) {
        return m295a(appUpdateInfo, appUpdateOptions) ? Tasks.m1067a(-1) : Tasks.m1066a((Exception) new InstallException(-6));
    }

    public boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, int i, Activity activity, int i2) {
        return m295a(appUpdateInfo, AppUpdateOptions.newBuilder(i).build());
    }

    public boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, int i, IntentSenderForResultStarter intentSenderForResultStarter, int i2) {
        return m295a(appUpdateInfo, AppUpdateOptions.newBuilder(i).build());
    }

    public final boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, Activity activity, AppUpdateOptions appUpdateOptions, int i) {
        return m295a(appUpdateInfo, appUpdateOptions);
    }

    public final boolean startUpdateFlowForResult(AppUpdateInfo appUpdateInfo, IntentSenderForResultStarter intentSenderForResultStarter, AppUpdateOptions appUpdateOptions, int i) {
        return m295a(appUpdateInfo, appUpdateOptions);
    }

    public void unregisterListener(InstallStateUpdatedListener installStateUpdatedListener) {
        this.f859a.mo44200b(installStateUpdatedListener);
    }

    public void userAcceptsUpdate() {
        if (this.f869k || this.f870l) {
            this.f869k = false;
            this.f861c = 1;
            Integer num = 0;
            if (num.equals(this.f873o)) {
                m296b();
            }
        }
    }

    public void userCancelsDownload() {
        int i = this.f861c;
        if (i == 1 || i == 2) {
            this.f861c = 6;
            Integer num = 0;
            if (num.equals(this.f873o)) {
                m296b();
            }
            this.f873o = null;
            this.f870l = false;
            this.f861c = 0;
        }
    }

    public void userRejectsUpdate() {
        if (this.f869k || this.f870l) {
            this.f869k = false;
            this.f870l = false;
            this.f873o = null;
            this.f861c = 0;
        }
    }
}
