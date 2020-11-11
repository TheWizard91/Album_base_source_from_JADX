package com.google.android.play.core.appupdate;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.play.core.assetpacks.C2887bc;
import com.google.android.play.core.install.InstallException;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C2999ak;
import com.google.android.play.core.internal.C3035bt;
import com.google.android.play.core.internal.C3062n;
import com.google.android.play.core.splitinstall.C3156v;
import com.google.android.play.core.tasks.C3169i;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;

/* renamed from: com.google.android.play.core.appupdate.k */
final class C2853k {
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static final C2989aa f835b = new C2989aa("AppUpdateService");

    /* renamed from: c */
    private static final Intent f836c = new Intent("com.google.android.play.core.install.BIND_UPDATE_SERVICE").setPackage("com.android.vending");

    /* renamed from: a */
    C2999ak<C3062n> f837a;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public final String f838d;

    /* renamed from: e */
    private final Context f839e;

    /* renamed from: f */
    private final C2887bc f840f;

    public C2853k(Context context) {
        this.f838d = context.getPackageName();
        this.f839e = context;
        if (C3035bt.m754a(context)) {
            this.f837a = new C2999ak(C3156v.m1053a(context), f835b, "AppUpdateService", f836c, C2847e.f823a);
        }
        this.f840f = new C2887bc(context);
    }

    /* renamed from: a */
    static /* synthetic */ Bundle m277a(C2853k kVar, String str) {
        Integer num;
        Bundle bundle = new Bundle();
        bundle.putAll(m283d());
        bundle.putString("package.name", str);
        try {
            num = Integer.valueOf(kVar.f839e.getPackageManager().getPackageInfo(kVar.f839e.getPackageName(), 0).versionCode);
        } catch (PackageManager.NameNotFoundException e) {
            f835b.mo44089b("The current version of the app could not be retrieved", new Object[0]);
            num = null;
        }
        if (num != null) {
            bundle.putInt("app.version.code", num.intValue());
        }
        return bundle;
    }

    /* renamed from: a */
    static /* synthetic */ AppUpdateInfo m278a(C2853k kVar, Bundle bundle, String str) {
        Bundle bundle2 = bundle;
        return AppUpdateInfo.m257a(str, bundle2.getInt("version.code", -1), bundle2.getInt("update.availability"), bundle2.getInt("install.status", 0), bundle2.getInt("client.version.staleness", -1) != -1 ? Integer.valueOf(bundle2.getInt("client.version.staleness")) : null, bundle2.getInt("in.app.update.priority", 0), bundle2.getLong("bytes.downloaded"), bundle2.getLong("total.bytes.to.download"), bundle2.getLong("additional.size.required"), kVar.f840f.mo43966a(), (PendingIntent) bundle2.getParcelable("blocking.intent"), (PendingIntent) bundle2.getParcelable("nonblocking.intent"), (PendingIntent) bundle2.getParcelable("blocking.destructive.intent"), (PendingIntent) bundle2.getParcelable("nonblocking.destructive.intent"));
    }

    /* renamed from: c */
    private static <T> Task<T> m282c() {
        f835b.mo44089b("onError(%d)", -9);
        return Tasks.m1066a((Exception) new InstallException(-9));
    }

    /* access modifiers changed from: private */
    /* renamed from: d */
    public static Bundle m283d() {
        Bundle bundle = new Bundle();
        bundle.putInt("playcore.version.code", 10702);
        return bundle;
    }

    /* renamed from: a */
    public final Task<AppUpdateInfo> mo43842a(String str) {
        if (this.f837a == null) {
            return m282c();
        }
        f835b.mo44090c("requestUpdateInfo(%s)", str);
        C3169i iVar = new C3169i();
        this.f837a.mo44099a((C2990ab) new C2848f(this, iVar, str, iVar));
        return iVar.mo44328a();
    }

    /* renamed from: b */
    public final Task<Void> mo43843b(String str) {
        if (this.f837a == null) {
            return m282c();
        }
        f835b.mo44090c("completeUpdate(%s)", str);
        C3169i iVar = new C3169i();
        this.f837a.mo44099a((C2990ab) new C2849g(this, iVar, iVar, str));
        return iVar.mo44328a();
    }
}
