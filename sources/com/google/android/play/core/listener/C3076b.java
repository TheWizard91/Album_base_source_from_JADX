package com.google.android.play.core.listener;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import com.google.android.play.core.internal.C2989aa;
import com.google.android.play.core.splitcompat.C3082d;
import com.google.android.play.core.splitinstall.C3156v;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/* renamed from: com.google.android.play.core.listener.b */
public abstract class C3076b<StateT> {

    /* renamed from: a */
    protected final C2989aa f1339a;

    /* renamed from: b */
    protected final Set<StateUpdatedListener<StateT>> f1340b = new HashSet();

    /* renamed from: c */
    private final IntentFilter f1341c;

    /* renamed from: d */
    private final Context f1342d;

    /* renamed from: e */
    private C3075a f1343e = null;

    /* renamed from: f */
    private volatile boolean f1344f = false;

    protected C3076b(C2989aa aaVar, IntentFilter intentFilter, Context context) {
        this.f1339a = aaVar;
        this.f1341c = intentFilter;
        this.f1342d = C3156v.m1053a(context);
    }

    /* renamed from: c */
    private final void m863c() {
        C3075a aVar;
        if ((this.f1344f || !this.f1340b.isEmpty()) && this.f1343e == null) {
            C3075a aVar2 = new C3075a(this);
            this.f1343e = aVar2;
            this.f1342d.registerReceiver(aVar2, this.f1341c);
        }
        if (!this.f1344f && this.f1340b.isEmpty() && (aVar = this.f1343e) != null) {
            this.f1342d.unregisterReceiver(aVar);
            this.f1343e = null;
        }
    }

    /* renamed from: a */
    public final synchronized void mo44196a() {
        this.f1339a.mo44090c("clearListeners", new Object[0]);
        this.f1340b.clear();
        m863c();
    }

    /* access modifiers changed from: protected */
    /* renamed from: a */
    public abstract void mo43835a(Context context, Intent intent);

    /* renamed from: a */
    public final synchronized void mo44197a(StateUpdatedListener<StateT> stateUpdatedListener) {
        this.f1339a.mo44090c("registerListener", new Object[0]);
        C3082d.m896a(stateUpdatedListener, (Object) "Registered Play Core listener should not be null.");
        this.f1340b.add(stateUpdatedListener);
        m863c();
    }

    /* renamed from: a */
    public final synchronized void mo44198a(StateT statet) {
        Iterator it = new HashSet(this.f1340b).iterator();
        while (it.hasNext()) {
            ((StateUpdatedListener) it.next()).onStateUpdate(statet);
        }
    }

    /* renamed from: a */
    public final synchronized void mo44199a(boolean z) {
        this.f1344f = z;
        m863c();
    }

    /* renamed from: b */
    public final synchronized void mo44200b(StateUpdatedListener<StateT> stateUpdatedListener) {
        this.f1339a.mo44090c("unregisterListener", new Object[0]);
        C3082d.m896a(stateUpdatedListener, (Object) "Unregistered Play Core listener should not be null.");
        this.f1340b.remove(stateUpdatedListener);
        m863c();
    }

    /* renamed from: b */
    public final synchronized boolean mo44201b() {
        return this.f1343e != null;
    }
}
