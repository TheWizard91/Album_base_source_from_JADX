package com.google.android.play.core.assetpacks;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.ParcelFileDescriptor;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C2990ab;
import com.google.android.play.core.internal.C2999ak;
import com.google.android.play.core.internal.C3035bt;
import com.google.android.play.core.internal.C3067s;
import com.google.android.play.core.splitinstall.C3156v;
import com.google.android.play.core.tasks.C3169i;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.google.android.play.core.assetpacks.ar */
final class C2875ar implements C2982w {
    /* access modifiers changed from: private */

    /* renamed from: a */
    public static final C2989aa f932a = new C2989aa("AssetPackServiceImpl");

    /* renamed from: b */
    private static final Intent f933b = new Intent("com.google.android.play.core.assetmoduleservice.BIND_ASSET_MODULE_SERVICE").setPackage("com.android.vending");
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final String f934c;
    /* access modifiers changed from: private */

    /* renamed from: d */
    public final C2913cb f935d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public C2999ak<C3067s> f936e;
    /* access modifiers changed from: private */

    /* renamed from: f */
    public C2999ak<C3067s> f937f;
    /* access modifiers changed from: private */

    /* renamed from: g */
    public final AtomicBoolean f938g = new AtomicBoolean();

    C2875ar(Context context, C2913cb cbVar) {
        this.f934c = context.getPackageName();
        this.f935d = cbVar;
        if (C3035bt.m754a(context)) {
            Context a = C3156v.m1053a(context);
            C2989aa aaVar = f932a;
            Intent intent = f933b;
            this.f936e = new C2999ak(a, aaVar, "AssetPackService", intent, C2983x.f1262a);
            this.f937f = new C2999ak(C3156v.m1053a(context), aaVar, "AssetPackService-keepAlive", intent, C2984y.f1263a);
        }
        f932a.mo44087a("AssetPackService initiated.", new Object[0]);
    }

    /* renamed from: a */
    static /* synthetic */ ArrayList m343a(Collection collection) {
        ArrayList arrayList = new ArrayList(collection.size());
        Iterator it = collection.iterator();
        while (it.hasNext()) {
            Bundle bundle = new Bundle();
            bundle.putString("module_name", (String) it.next());
            arrayList.add(bundle);
        }
        return arrayList;
    }

    /* renamed from: a */
    static /* synthetic */ List m344a(C2875ar arVar, List list) {
        ArrayList arrayList = new ArrayList();
        int size = list.size();
        for (int i = 0; i < size; i++) {
            AssetPackState next = AssetPackStates.m306a((Bundle) list.get(i), arVar.f935d).packStates().values().iterator().next();
            if (next == null) {
                f932a.mo44089b("onGetSessionStates: Bundle contained no pack.", new Object[0]);
            }
            if (C2942dd.m528a(next.status())) {
                arrayList.add(next.name());
            }
        }
        return arrayList;
    }

    /* access modifiers changed from: private */
    /* renamed from: a */
    public final void m345a(int i, String str, int i2) {
        if (this.f936e != null) {
            f932a.mo44090c("notifyModuleCompleted", new Object[0]);
            C3169i iVar = new C3169i();
            this.f936e.mo44099a((C2990ab) new C2864ag(this, iVar, i, str, iVar, i2));
            return;
        }
        throw new C2909by("The Play Store app is not installed or is an unofficial version.", i);
    }

    /* renamed from: c */
    static /* synthetic */ Bundle m350c() {
        Bundle bundle = new Bundle();
        bundle.putInt("playcore_version_code", 10702);
        ArrayList arrayList = new ArrayList();
        arrayList.add(0);
        arrayList.add(1);
        bundle.putIntegerArrayList("supported_compression_formats", arrayList);
        bundle.putIntegerArrayList("supported_patch_formats", new ArrayList());
        return bundle;
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public static Bundle m351c(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt("session_id", i);
        return bundle;
    }

    /* access modifiers changed from: private */
    /* renamed from: c */
    public static Bundle m352c(int i, String str) {
        Bundle c = m351c(i);
        c.putString("module_name", str);
        return c;
    }

    /* renamed from: c */
    static /* synthetic */ Bundle m353c(int i, String str, String str2, int i2) {
        Bundle c = m352c(i, str);
        c.putString("slice_id", str2);
        c.putInt("chunk_number", i2);
        return c;
    }

    /* renamed from: e */
    private static <T> Task<T> m357e() {
        f932a.mo44089b("onError(%d)", -11);
        return Tasks.m1066a((Exception) new AssetPackException(-11));
    }

    /* renamed from: a */
    public final Task<List<String>> mo43918a() {
        if (this.f936e == null) {
            return m357e();
        }
        f932a.mo44090c("syncPacks", new Object[0]);
        C3169i iVar = new C3169i();
        this.f936e.mo44099a((C2990ab) new C2861ad(this, iVar, iVar));
        return iVar.mo44328a();
    }

    /* renamed from: a */
    public final Task<AssetPackStates> mo43919a(List<String> list, C2883az azVar) {
        if (this.f936e == null) {
            return m357e();
        }
        f932a.mo44090c("getPackStates(%s)", list);
        C3169i iVar = new C3169i();
        this.f936e.mo44099a((C2990ab) new C2862ae(this, iVar, list, iVar, azVar));
        return iVar.mo44328a();
    }

    /* renamed from: a */
    public final Task<AssetPackStates> mo43920a(List<String> list, List<String> list2) {
        if (this.f936e == null) {
            return m357e();
        }
        f932a.mo44090c("startDownload(%s)", list2);
        C3169i iVar = new C3169i();
        this.f936e.mo44099a((C2990ab) new C2859ab(this, iVar, list2, iVar, list));
        iVar.mo44328a().addOnSuccessListener(new C2985z(this));
        return iVar.mo44328a();
    }

    /* renamed from: a */
    public final void mo43921a(int i) {
        if (this.f936e != null) {
            f932a.mo44090c("notifySessionFailed", new Object[0]);
            C3169i iVar = new C3169i();
            this.f936e.mo44099a((C2990ab) new C2865ah(this, iVar, i, iVar));
            return;
        }
        throw new C2909by("The Play Store app is not installed or is an unofficial version.", i);
    }

    /* renamed from: a */
    public final void mo43922a(int i, String str) {
        m345a(i, str, 10);
    }

    /* renamed from: a */
    public final void mo43923a(int i, String str, String str2, int i2) {
        if (this.f936e != null) {
            f932a.mo44090c("notifyChunkTransferred", new Object[0]);
            C3169i iVar = new C3169i();
            this.f936e.mo44099a((C2990ab) new C2863af(this, iVar, i, str, str2, i2, iVar));
            return;
        }
        throw new C2909by("The Play Store app is not installed or is an unofficial version.", i);
    }

    /* renamed from: a */
    public final void mo43924a(String str) {
        if (this.f936e != null) {
            f932a.mo44090c("removePack(%s)", str);
            C3169i iVar = new C3169i();
            this.f936e.mo44099a((C2990ab) new C2858aa(this, iVar, str, iVar));
        }
    }

    /* renamed from: a */
    public final void mo43925a(List<String> list) {
        if (this.f936e != null) {
            f932a.mo44090c("cancelDownloads(%s)", list);
            C3169i iVar = new C3169i();
            this.f936e.mo44099a((C2990ab) new C2860ac(this, iVar, list, iVar));
        }
    }

    /* renamed from: b */
    public final Task<ParcelFileDescriptor> mo43926b(int i, String str, String str2, int i2) {
        if (this.f936e == null) {
            return m357e();
        }
        f932a.mo44090c("getChunkFileDescriptor(%s, %s, %d, session=%d)", str, str2, Integer.valueOf(i2), Integer.valueOf(i));
        C3169i iVar = new C3169i();
        this.f936e.mo44099a((C2990ab) new C2866ai(this, iVar, i, str, str2, i2, iVar));
        return iVar.mo44328a();
    }

    /* renamed from: b */
    public final synchronized void mo43927b() {
        if (this.f937f != null) {
            C2989aa aaVar = f932a;
            aaVar.mo44090c("keepAlive", new Object[0]);
            if (!this.f938g.compareAndSet(false, true)) {
                aaVar.mo44090c("Service is already kept alive.", new Object[0]);
                return;
            }
            C3169i iVar = new C3169i();
            this.f937f.mo44099a((C2990ab) new C2867aj(this, iVar, iVar));
            return;
        }
        f932a.mo44091d("Keep alive connection manager is not initialized.", new Object[0]);
    }
}
