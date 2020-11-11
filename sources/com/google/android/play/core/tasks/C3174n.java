package com.google.android.play.core.tasks;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* renamed from: com.google.android.play.core.tasks.n */
final class C3174n implements OnSuccessListener, OnFailureListener {

    /* renamed from: a */
    private final CountDownLatch f1551a = new CountDownLatch(1);

    private C3174n() {
    }

    /* synthetic */ C3174n(byte[] bArr) {
    }

    /* renamed from: a */
    public final void mo44339a() throws InterruptedException {
        this.f1551a.await();
    }

    /* renamed from: a */
    public final boolean mo44340a(long j, TimeUnit timeUnit) throws InterruptedException {
        return this.f1551a.await(j, timeUnit);
    }

    public final void onFailure(Exception exc) {
        this.f1551a.countDown();
    }

    public final void onSuccess(Object obj) {
        this.f1551a.countDown();
    }
}
