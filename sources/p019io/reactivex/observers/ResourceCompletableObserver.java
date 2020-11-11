package p019io.reactivex.observers;

import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.disposables.ListCompositeDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.util.EndConsumerHelper;

/* renamed from: io.reactivex.observers.ResourceCompletableObserver */
public abstract class ResourceCompletableObserver implements CompletableObserver, Disposable {
    private final ListCompositeDisposable resources = new ListCompositeDisposable();

    /* renamed from: s */
    private final AtomicReference<Disposable> f597s = new AtomicReference<>();

    public final void add(Disposable resource) {
        ObjectHelper.requireNonNull(resource, "resource is null");
        this.resources.add(resource);
    }

    public final void onSubscribe(Disposable s) {
        if (EndConsumerHelper.setOnce(this.f597s, s, getClass())) {
            onStart();
        }
    }

    /* access modifiers changed from: protected */
    public void onStart() {
    }

    public final void dispose() {
        if (DisposableHelper.dispose(this.f597s)) {
            this.resources.dispose();
        }
    }

    public final boolean isDisposed() {
        return DisposableHelper.isDisposed(this.f597s.get());
    }
}