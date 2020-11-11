package com.google.android.play.core.assetpacks;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.core.app.NotificationCompat;
import com.google.android.play.core.common.PlayCoreDialogWrapperActivity;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C3047ce;
import com.google.android.play.core.internal.C3056h;
import com.google.android.play.core.splitinstall.C3160z;
import com.google.android.play.core.tasks.C3169i;
import com.google.android.play.core.tasks.Task;
import com.google.android.play.core.tasks.Tasks;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.i */
final class C2967i implements AssetPackManager {

    /* renamed from: a */
    private static final C2989aa f1228a = new C2989aa("AssetPackManager");

    /* renamed from: b */
    private final C2886bb f1229b;

    /* renamed from: c */
    private final C3047ce<C2982w> f1230c;

    /* renamed from: d */
    private final C2880aw f1231d;

    /* renamed from: e */
    private final C3160z f1232e;

    /* renamed from: f */
    private final C2929cr f1233f;

    /* renamed from: g */
    private final C2913cb f1234g;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public final C2901bq f1235h;

    /* renamed from: i */
    private final C3047ce<Executor> f1236i;

    /* renamed from: j */
    private final Handler f1237j = new Handler(Looper.getMainLooper());

    /* renamed from: k */
    private boolean f1238k;

    C2967i(C2886bb bbVar, C3047ce<C2982w> ceVar, C2880aw awVar, C3160z zVar, C2929cr crVar, C2913cb cbVar, C2901bq bqVar, C3047ce<Executor> ceVar2) {
        this.f1229b = bbVar;
        this.f1230c = ceVar;
        this.f1231d = awVar;
        this.f1232e = zVar;
        this.f1233f = crVar;
        this.f1234g = cbVar;
        this.f1235h = bqVar;
        this.f1236i = ceVar2;
    }

    /* renamed from: b */
    private final void m576b() {
        C2886bb bbVar = this.f1229b;
        bbVar.getClass();
        this.f1230c.mo44145a().mo43918a().addOnSuccessListener(this.f1236i.mo44145a(), C2963e.m573a(bbVar)).addOnFailureListener(this.f1236i.mo44145a(), C2964f.f1224a);
    }

    /* renamed from: c */
    private final void m577c() {
        this.f1236i.mo44145a().execute(new C2965g(this));
        this.f1238k = true;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final int mo44066a(int i, String str) {
        if (!this.f1229b.mo43945a(str) && i == 4) {
            return 8;
        }
        if (!this.f1229b.mo43945a(str) || i == 4) {
            return i;
        }
        return 4;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo44067a() {
        this.f1229b.mo43956d();
        this.f1229b.mo43953c();
        this.f1229b.mo43961e();
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo44068a(String str, C3169i iVar) {
        if (this.f1229b.mo43957d(str)) {
            iVar.mo44330a(null);
            this.f1230c.mo44145a().mo43924a(str);
            return;
        }
        iVar.mo44329a((Exception) new IOException(String.format("Failed to remove pack %s.", new Object[]{str})));
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo44069a(boolean z) {
        boolean b = this.f1231d.mo44201b();
        this.f1231d.mo44199a(z);
        if (z && !b) {
            m576b();
        }
    }

    public final AssetPackStates cancel(List<String> list) {
        Map<String, Integer> a = this.f1233f.mo44019a(list);
        HashMap hashMap = new HashMap();
        for (String next : list) {
            Integer num = a.get(next);
            hashMap.put(next, AssetPackState.m304a(next, num != null ? num.intValue() : 0, 0, 0, 0, 0.0d));
        }
        this.f1230c.mo44145a().mo43925a(list);
        return AssetPackStates.m305a(0, (Map<String, AssetPackState>) hashMap);
    }

    public final void clearListeners() {
        this.f1231d.mo44196a();
    }

    public final Task<AssetPackStates> fetch(List<String> list) {
        ArrayList arrayList = new ArrayList();
        for (String next : list) {
            if (!this.f1229b.mo43945a(next)) {
                arrayList.add(next);
            }
        }
        if (arrayList.isEmpty()) {
            Bundle bundle = new Bundle();
            bundle.putInt("session_id", 0);
            bundle.putInt("error_code", 0);
            for (String next2 : list) {
                bundle.putInt(C3056h.m786a(NotificationCompat.CATEGORY_STATUS, next2), 4);
                bundle.putInt(C3056h.m786a("error_code", next2), 0);
                bundle.putLong(C3056h.m786a("total_bytes_to_download", next2), 0);
                bundle.putLong(C3056h.m786a("bytes_downloaded", next2), 0);
            }
            bundle.putStringArrayList("pack_names", new ArrayList(list));
            bundle.putLong("total_bytes_to_download", 0);
            bundle.putLong("bytes_downloaded", 0);
            return Tasks.m1067a(AssetPackStates.m306a(bundle, this.f1234g));
        }
        ArrayList arrayList2 = new ArrayList(list);
        arrayList2.removeAll(arrayList);
        return this.f1230c.mo44145a().mo43920a((List<String>) arrayList2, (List<String>) arrayList);
    }

    public final AssetLocation getAssetLocation(String str, String str2) {
        AssetPackLocation assetPackLocation;
        if (!this.f1238k) {
            this.f1236i.mo44145a().execute(new C2965g(this));
            this.f1238k = true;
        }
        if (this.f1229b.mo43945a(str)) {
            try {
                assetPackLocation = this.f1229b.mo43946b(str);
            } catch (IOException e) {
            }
        } else {
            if (this.f1232e.mo44307a().contains(str)) {
                assetPackLocation = AssetPackLocation.m301a();
            }
            assetPackLocation = null;
        }
        if (assetPackLocation != null) {
            if (assetPackLocation.packStorageMethod() == 1) {
                return this.f1229b.mo43939a(str, str2);
            }
            if (assetPackLocation.packStorageMethod() == 0) {
                return this.f1229b.mo43940a(str, str2, assetPackLocation);
            }
            f1228a.mo44087a("The asset %s is not present in Asset Pack %s", str2, str);
        }
        return null;
    }

    public final AssetPackLocation getPackLocation(String str) {
        if (!this.f1238k) {
            m577c();
        }
        if (this.f1229b.mo43945a(str)) {
            try {
                return this.f1229b.mo43946b(str);
            } catch (IOException e) {
                return null;
            }
        } else if (this.f1232e.mo44307a().contains(str)) {
            return AssetPackLocation.m301a();
        } else {
            return null;
        }
    }

    public final Map<String, AssetPackLocation> getPackLocations() {
        Map<String, AssetPackLocation> b = this.f1229b.mo43949b();
        HashMap hashMap = new HashMap();
        for (String put : this.f1232e.mo44307a()) {
            hashMap.put(put, AssetPackLocation.m301a());
        }
        b.putAll(hashMap);
        return b;
    }

    public final Task<AssetPackStates> getPackStates(List<String> list) {
        return this.f1230c.mo44145a().mo43919a(list, (C2883az) new C2911c(this));
    }

    public final synchronized void registerListener(AssetPackStateUpdateListener assetPackStateUpdateListener) {
        boolean b = this.f1231d.mo44201b();
        this.f1231d.mo44197a(assetPackStateUpdateListener);
        if (!b) {
            m576b();
        }
    }

    public final Task<Void> removePack(String str) {
        C3169i iVar = new C3169i();
        this.f1236i.mo44145a().execute(new C2938d(this, str, iVar));
        return iVar.mo44328a();
    }

    public final Task<Integer> showCellularDataConfirmation(Activity activity) {
        if (this.f1235h.mo44007a() == null) {
            return Tasks.m1066a((Exception) new AssetPackException(-12));
        }
        Intent intent = new Intent(activity, PlayCoreDialogWrapperActivity.class);
        intent.putExtra("confirmation_intent", this.f1235h.mo44007a());
        C3169i iVar = new C3169i();
        intent.putExtra("result_receiver", new C2966h(this, this.f1237j, iVar));
        activity.startActivity(intent);
        return iVar.mo44328a();
    }

    public final void unregisterListener(AssetPackStateUpdateListener assetPackStateUpdateListener) {
        this.f1231d.mo44200b(assetPackStateUpdateListener);
    }
}
