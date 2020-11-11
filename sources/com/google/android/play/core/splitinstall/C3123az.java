package com.google.android.play.core.splitinstall;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C2999ak;
import com.google.android.play.core.internal.C3031bp;
import com.google.android.play.core.internal.C3035bt;
import com.google.android.play.core.tasks.C3169i;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/* renamed from: com.google.android.play.core.splitinstall.az */
final class C3123az {
    /* access modifiers changed from: private */

    /* renamed from: b */
    public static final C2989aa f1447b = new C2989aa("SplitInstallService");

    /* renamed from: c */
    private static final Intent f1448c = new Intent("com.google.android.play.core.splitinstall.BIND_SPLIT_INSTALL_SERVICE").setPackage("com.android.vending");

    /* renamed from: a */
    C2999ak<C3031bp> f1449a;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public final String f1450d;

    public C3123az(Context context) {
        this.f1450d = context.getPackageName();
        if (C3035bt.m754a(context)) {
            this.f1449a = new C2999ak(C3156v.m1053a(context), f1447b, "SplitInstallService", f1448c, C3105ah.f1420a);
        }
    }

    /* renamed from: a */
    static /* synthetic */ ArrayList m985a(Collection collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Bundle bundle = new Bundle();
            bundle.putString("module_name", (String) it.next());
            arrayList.add(bundle);
        }
        return arrayList;
    }

    /* renamed from: b */
    static /* synthetic */ Bundle m986b() {
        Bundle bundle = new Bundle();
        bundle.putInt("playcore_version_code", 10702);
        return bundle;
    }

    /* JADX WARNING: type inference failed for: r6v0, types: [java.util.List, java.util.Collection] */
    /* JADX WARNING: Unknown variable types count: 1 */
    /* renamed from: b */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static /* synthetic */ java.util.ArrayList m987b(java.util.Collection r6) {
        /*
            java.util.ArrayList r0 = new java.util.ArrayList
            int r1 = r6.size()
            r0.<init>(r1)
            int r1 = r6.size()
            r2 = 0
        L_0x000e:
            if (r2 >= r1) goto L_0x0026
            java.lang.Object r3 = r6.get(r2)
            java.lang.String r3 = (java.lang.String) r3
            android.os.Bundle r4 = new android.os.Bundle
            r4.<init>()
            java.lang.String r5 = "language"
            r4.putString(r5, r3)
            r0.add(r4)
            int r2 = r2 + 1
            goto L_0x000e
        L_0x0026:
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.splitinstall.C3123az.m987b(java.util.Collection):java.util.ArrayList");
    }

    /* renamed from: d */
    private static <T> Task<T> m989d() {
        f1447b.mo44089b("onError(%d)", -14);
        return Tasks.m1066a((Exception) new SplitInstallException(-14));
    }

    /* renamed from: a */
    public final Task<List<SplitInstallSessionState>> mo44281a() {
        if (this.f1449a == null) {
            return m989d();
        }
        f1447b.mo44090c("getSessionStates", new Object[0]);
        C3169i iVar = new C3169i();
        this.f1449a.mo44099a((C2990ab) new C3112ao(this, iVar, iVar));
        return iVar.mo44328a();
    }

    /* renamed from: a */
    public final Task<SplitInstallSessionState> mo44282a(int i) {
        if (this.f1449a == null) {
            return m989d();
        }
        f1447b.mo44090c("getSessionState(%d)", Integer.valueOf(i));
        C3169i iVar = new C3169i();
        this.f1449a.mo44099a((C2990ab) new C3111an(this, iVar, i, iVar));
        return iVar.mo44328a();
    }

    /* renamed from: a */
    public final Task<Integer> mo44283a(Collection<String> collection, Collection<String> collection2) {
        if (this.f1449a == null) {
            return m989d();
        }
        f1447b.mo44090c("startInstall(%s,%s)", collection, collection2);
        C3169i iVar = new C3169i();
        this.f1449a.mo44099a((C2990ab) new C3106ai(this, iVar, collection, collection2, iVar));
        return iVar.mo44328a();
    }

    /* renamed from: a */
    public final Task<Void> mo44284a(List<String> list) {
        if (this.f1449a == null) {
            return m989d();
        }
        f1447b.mo44090c("deferredUninstall(%s)", list);
        C3169i iVar = new C3169i();
        this.f1449a.mo44099a((C2990ab) new C3107aj(this, iVar, list, iVar));
        return iVar.mo44328a();
    }

    /* renamed from: b */
    public final Task<Void> mo44285b(int i) {
        if (this.f1449a == null) {
            return m989d();
        }
        f1447b.mo44090c("cancelInstall(%d)", Integer.valueOf(i));
        C3169i iVar = new C3169i();
        this.f1449a.mo44099a((C2990ab) new C3113ap(this, iVar, i, iVar));
        return iVar.mo44328a();
    }

    /* renamed from: b */
    public final Task<Void> mo44286b(List<String> list) {
        if (this.f1449a == null) {
            return m989d();
        }
        f1447b.mo44090c("deferredInstall(%s)", list);
        C3169i iVar = new C3169i();
        this.f1449a.mo44099a((C2990ab) new C3108ak(this, iVar, list, iVar));
        return iVar.mo44328a();
    }

    /* renamed from: c */
    public final Task<Void> mo44287c(List<String> list) {
        if (this.f1449a == null) {
            return m989d();
        }
        f1447b.mo44090c("deferredLanguageInstall(%s)", list);
        C3169i iVar = new C3169i();
        this.f1449a.mo44099a((C2990ab) new C3109al(this, iVar, list, iVar));
        return iVar.mo44328a();
    }

    /* renamed from: d */
    public final Task<Void> mo44288d(List<String> list) {
        if (this.f1449a == null) {
            return m989d();
        }
        f1447b.mo44090c("deferredLanguageUninstall(%s)", list);
        C3169i iVar = new C3169i();
        this.f1449a.mo44099a((C2990ab) new C3110am(this, iVar, list, iVar));
        return iVar.mo44328a();
    }
}
