package com.firebase.p008ui.firestore.paging;

import androidx.lifecycle.GeneratedAdapter;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MethodCallsLogger;

/* renamed from: com.firebase.ui.firestore.paging.FirestorePagingAdapter_LifecycleAdapter */
public class FirestorePagingAdapter_LifecycleAdapter implements GeneratedAdapter {
    final FirestorePagingAdapter mReceiver;

    FirestorePagingAdapter_LifecycleAdapter(FirestorePagingAdapter receiver) {
        this.mReceiver = receiver;
    }

    public void callMethods(LifecycleOwner owner, Lifecycle.Event event, boolean onAny, MethodCallsLogger logger) {
        boolean hasLogger = logger != null;
        if (!onAny) {
            if (event == Lifecycle.Event.ON_START) {
                if (!hasLogger || logger.approveCall("startListening", 1)) {
                    this.mReceiver.startListening();
                }
            } else if (event != Lifecycle.Event.ON_STOP) {
            } else {
                if (!hasLogger || logger.approveCall("stopListening", 1)) {
                    this.mReceiver.stopListening();
                }
            }
        }
    }
}
