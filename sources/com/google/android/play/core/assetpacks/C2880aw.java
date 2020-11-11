package com.google.android.play.core.assetpacks;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.internal.C3047ce;
import com.google.android.play.core.listener.C3076b;
import java.util.ArrayList;
import java.util.concurrent.Executor;

/* renamed from: com.google.android.play.core.assetpacks.aw */
final class C2880aw extends C3076b<AssetPackState> {

    /* renamed from: c */
    private final C2929cr f948c;

    /* renamed from: d */
    private final C2910bz f949d;

    /* renamed from: e */
    private final C3047ce<C2982w> f950e;

    /* renamed from: f */
    private final C2901bq f951f;

    /* renamed from: g */
    private final C2913cb f952g;

    /* renamed from: h */
    private final C3047ce<Executor> f953h;

    /* renamed from: i */
    private final C3047ce<Executor> f954i;

    /* renamed from: j */
    private final Handler f955j = new Handler(Looper.getMainLooper());

    C2880aw(Context context, C2929cr crVar, C2910bz bzVar, C3047ce<C2982w> ceVar, C2913cb cbVar, C2901bq bqVar, C3047ce<Executor> ceVar2, C3047ce<Executor> ceVar3) {
        super(new C2989aa("AssetPackServiceListenerRegistry"), new IntentFilter("com.google.android.play.core.assetpacks.receiver.ACTION_SESSION_UPDATE"), context);
        this.f948c = crVar;
        this.f949d = bzVar;
        this.f950e = ceVar;
        this.f952g = cbVar;
        this.f951f = bqVar;
        this.f953h = ceVar2;
        this.f954i = ceVar3;
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public final void mo43835a(Context context, Intent intent) {
        Bundle bundleExtra = intent.getBundleExtra("com.google.android.play.core.assetpacks.receiver.EXTRA_SESSION_STATE");
        if (bundleExtra != null) {
            ArrayList<String> stringArrayList = bundleExtra.getStringArrayList("pack_names");
            if (stringArrayList == null || stringArrayList.size() != 1) {
                this.f1339a.mo44089b("Corrupt bundle received from broadcast.", new Object[0]);
                return;
            }
            AssetPackState a = AssetPackState.m303a(bundleExtra, stringArrayList.get(0), this.f952g, C2882ay.f964a);
            this.f1339a.mo44087a("ListenerRegistryBroadcastReceiver.onReceive: %s", a);
            PendingIntent pendingIntent = (PendingIntent) bundleExtra.getParcelable("confirmation_intent");
            if (pendingIntent != null) {
                this.f951f.mo44008a(pendingIntent);
            }
            this.f954i.mo44145a().execute(new C2878au(this, bundleExtra, a));
            this.f953h.mo44145a().execute(new C2879av(this, bundleExtra));
            return;
        }
        this.f1339a.mo44089b("Empty bundle received from broadcast.", new Object[0]);
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo43932a(Bundle bundle) {
        if (this.f948c.mo44023a(bundle)) {
            this.f949d.mo44013a();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final /* synthetic */ void mo43933a(Bundle bundle, AssetPackState assetPackState) {
        if (this.f948c.mo44028b(bundle)) {
            mo43934a(assetPackState);
            this.f950e.mo44145a().mo43927b();
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final void mo43934a(AssetPackState assetPackState) {
        this.f955j.post(new C2877at(this, assetPackState));
    }
}
