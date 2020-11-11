package com.google.android.play.core.internal;

import com.google.android.play.core.listener.StateUpdatedListener;
import java.util.HashSet;
import java.util.Set;

/* renamed from: com.google.android.play.core.internal.z */
public final class C3074z<StateT> {

    /* renamed from: a */
    protected final Set<StateUpdatedListener<StateT>> f1337a = new HashSet();

    /* renamed from: a */
    public final synchronized void mo44192a(StateUpdatedListener<StateT> stateUpdatedListener) {
        this.f1337a.add(stateUpdatedListener);
    }

    /* renamed from: a */
    public final synchronized void mo44193a(StateT statet) {
        for (StateUpdatedListener<StateT> onStateUpdate : this.f1337a) {
            onStateUpdate.onStateUpdate(statet);
        }
    }

    /* renamed from: b */
    public final synchronized void mo44194b(StateUpdatedListener<StateT> stateUpdatedListener) {
        this.f1337a.remove(stateUpdatedListener);
    }
}
