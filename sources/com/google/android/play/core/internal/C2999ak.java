package com.google.android.play.core.internal;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.IInterface;
import android.os.RemoteException;
import com.google.android.play.core.tasks.C3169i;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* renamed from: com.google.android.play.core.internal.ak */
public final class C2999ak<T extends IInterface> {

    /* renamed from: a */
    private static final Map<String, Handler> f1285a = new HashMap();
    /* access modifiers changed from: private */

    /* renamed from: b */
    public final Context f1286b;
    /* access modifiers changed from: private */

    /* renamed from: c */
    public final C2989aa f1287c;

    /* renamed from: d */
    private final String f1288d;
    /* access modifiers changed from: private */

    /* renamed from: e */
    public final List<C2990ab> f1289e = new ArrayList();
    /* access modifiers changed from: private */

    /* renamed from: f */
    public boolean f1290f;

    /* renamed from: g */
    private final Intent f1291g;
    /* access modifiers changed from: private */

    /* renamed from: h */
    public final C2995ag<T> f1292h;

    /* renamed from: i */
    private final WeakReference<C2994af> f1293i;

    /* renamed from: j */
    private final IBinder.DeathRecipient f1294j = new C2991ac(this);
    /* access modifiers changed from: private */

    /* renamed from: k */
    public ServiceConnection f1295k;
    /* access modifiers changed from: private */

    /* renamed from: l */
    public T f1296l;

    public C2999ak(Context context, C2989aa aaVar, String str, Intent intent, C2995ag<T> agVar) {
        this.f1286b = context;
        this.f1287c = aaVar;
        this.f1288d = str;
        this.f1291g = intent;
        this.f1292h = agVar;
        this.f1293i = new WeakReference<>((Object) null);
    }

    /* renamed from: a */
    static /* synthetic */ void m633a(C2999ak akVar, C2990ab abVar) {
        if (akVar.f1296l == null && !akVar.f1290f) {
            akVar.f1287c.mo44090c("Initiate binding to the service.", new Object[0]);
            akVar.f1289e.add(abVar);
            C2998aj ajVar = new C2998aj(akVar);
            akVar.f1295k = ajVar;
            akVar.f1290f = true;
            if (!akVar.f1286b.bindService(akVar.f1291g, ajVar, 1)) {
                akVar.f1287c.mo44090c("Failed to bind to the service.", new Object[0]);
                akVar.f1290f = false;
                List<C2990ab> list = akVar.f1289e;
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    C3169i<?> b = list.get(i).mo44092b();
                    if (b != null) {
                        b.mo44331b((Exception) new C3000al());
                    }
                }
                akVar.f1289e.clear();
            }
        } else if (akVar.f1290f) {
            akVar.f1287c.mo44090c("Waiting to bind to the service.", new Object[0]);
            akVar.f1289e.add(abVar);
        } else {
            abVar.run();
        }
    }

    /* access modifiers changed from: private */
    /* renamed from: b */
    public final void m635b(C2990ab abVar) {
        Handler handler;
        Map<String, Handler> map = f1285a;
        synchronized (map) {
            if (!map.containsKey(this.f1288d)) {
                HandlerThread handlerThread = new HandlerThread(this.f1288d, 10);
                handlerThread.start();
                map.put(this.f1288d, new Handler(handlerThread.getLooper()));
            }
            handler = map.get(this.f1288d);
        }
        handler.post(abVar);
    }

    /* renamed from: f */
    static /* synthetic */ void m640f(C2999ak akVar) {
        akVar.f1287c.mo44090c("linkToDeath", new Object[0]);
        try {
            akVar.f1296l.asBinder().linkToDeath(akVar.f1294j, 0);
        } catch (RemoteException e) {
            akVar.f1287c.mo44088a((Throwable) e, "linkToDeath failed", new Object[0]);
        }
    }

    /* renamed from: h */
    static /* synthetic */ void m642h(C2999ak akVar) {
        akVar.f1287c.mo44090c("unlinkToDeath", new Object[0]);
        akVar.f1296l.asBinder().unlinkToDeath(akVar.f1294j, 0);
    }

    /* renamed from: a */
    public final void mo44098a() {
        m635b((C2990ab) new C2993ae(this));
    }

    /* renamed from: a */
    public final void mo44099a(C2990ab abVar) {
        m635b((C2990ab) new C2992ad(this, abVar.mo44092b(), abVar));
    }

    /* renamed from: b */
    public final T mo44100b() {
        return this.f1296l;
    }

    /* access modifiers changed from: package-private */
    /* renamed from: c */
    public final /* bridge */ /* synthetic */ void mo44101c() {
        this.f1287c.mo44090c("reportBinderDeath", new Object[0]);
        C2994af afVar = (C2994af) this.f1293i.get();
        if (afVar == null) {
            this.f1287c.mo44090c("%s : Binder has died.", this.f1288d);
            List<C2990ab> list = this.f1289e;
            int size = list.size();
            for (int i = 0; i < size; i++) {
                C3169i<?> b = list.get(i).mo44092b();
                if (b != null) {
                    b.mo44331b((Exception) Build.VERSION.SDK_INT < 15 ? new RemoteException() : new RemoteException(String.valueOf(this.f1288d).concat(" : Binder has died.")));
                }
            }
            this.f1289e.clear();
            return;
        }
        this.f1287c.mo44090c("calling onBinderDied", new Object[0]);
        afVar.mo44095a();
    }
}
