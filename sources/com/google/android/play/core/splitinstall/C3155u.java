package com.google.android.play.core.splitinstall;

import android.app.Activity;
import android.content.Context;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.os.Bundle;
import com.google.android.play.core.common.IntentSenderForResultStarter;
import com.google.android.play.core.splitinstall.testing.FakeSplitInstallManagerFactory;
import com.google.android.play.core.tasks.C3169i;
import com.google.android.play.core.tasks.Task;
import java.io.File;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.splitinstall.u */
final class C3155u implements SplitInstallManager {

    /* renamed from: a */
    private final Executor f1513a;

    /* renamed from: b */
    private final Context f1514b;

    /* renamed from: c */
    private volatile SplitInstallManager f1515c;

    /* renamed from: d */
    private final C3104ag f1516d;

    private C3155u(Context context, Executor executor, C3104ag agVar, byte[] bArr) {
        this.f1514b = C3156v.m1053a(context);
        this.f1513a = executor;
        this.f1516d = agVar;
    }

    /* renamed from: a */
    static C3155u m1050a(Context context, Executor executor) {
        return new C3155u(context, executor, new C3104ag((byte[]) null), (byte[]) null);
    }

    /* renamed from: a */
    private final <T> Task<T> m1051a(C3145t<T> tVar) {
        SplitInstallManager splitInstallManager = this.f1515c;
        if (splitInstallManager != null) {
            return tVar.mo44296a(splitInstallManager);
        }
        C3169i iVar = new C3169i();
        C3169i iVar2 = new C3169i();
        iVar2.mo44328a().addOnCompleteListener(new C3132h(tVar, iVar));
        this.f1513a.execute(new C3133i(this, iVar2));
        return iVar.mo44328a();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final synchronized SplitInstallManager mo44305a() {
        SplitInstallManager splitInstallManager = this.f1515c;
        if (splitInstallManager != null) {
            return splitInstallManager;
        }
        Context context = this.f1514b;
        File file = null;
        try {
            Bundle bundle = context.getPackageManager().getApplicationInfo(context.getPackageName(), 128).metaData;
            if (bundle != null) {
                String string = bundle.getString("local_testing_dir");
                if (string != null) {
                    file = new File(context.getExternalFilesDir((String) null), string);
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
        }
        SplitInstallManager afVar = file == null ? new C3103af(new C3123az(context), context) : FakeSplitInstallManagerFactory.create(context, file);
        this.f1515c = afVar;
        return afVar;
    }

    public final Task<Void> cancelInstall(int i) {
        return m1051a(new C3137m(i));
    }

    public final Task<Void> deferredInstall(List<String> list) {
        return m1051a(new C3142q(list));
    }

    public final Task<Void> deferredLanguageInstall(List<Locale> list) {
        return m1051a(new C3143r(list));
    }

    public final Task<Void> deferredLanguageUninstall(List<Locale> list) {
        return m1051a(new C3144s(list));
    }

    public final Task<Void> deferredUninstall(List<String> list) {
        return m1051a(new C3141p(list));
    }

    public final Set<String> getInstalledLanguages() {
        return mo44305a().getInstalledLanguages();
    }

    public final Set<String> getInstalledModules() {
        return mo44305a().getInstalledModules();
    }

    public final Task<SplitInstallSessionState> getSessionState(int i) {
        return m1051a(new C3139n(i));
    }

    public final Task<List<SplitInstallSessionState>> getSessionStates() {
        return m1051a(C3140o.f1468a);
    }

    public final void registerListener(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        m1051a(new C3131g(splitInstallStateUpdatedListener));
    }

    public final boolean startConfirmationDialogForResult(SplitInstallSessionState splitInstallSessionState, Activity activity, int i) throws IntentSender.SendIntentException {
        return mo44305a().startConfirmationDialogForResult(splitInstallSessionState, activity, i);
    }

    public final boolean startConfirmationDialogForResult(SplitInstallSessionState splitInstallSessionState, IntentSenderForResultStarter intentSenderForResultStarter, int i) throws IntentSender.SendIntentException {
        return mo44305a().startConfirmationDialogForResult(splitInstallSessionState, intentSenderForResultStarter, i);
    }

    public final Task<Integer> startInstall(SplitInstallRequest splitInstallRequest) {
        return m1051a(new C3136l(splitInstallRequest));
    }

    public final void unregisterListener(SplitInstallStateUpdatedListener splitInstallStateUpdatedListener) {
        m1051a(new C3135k(splitInstallStateUpdatedListener));
    }
}
