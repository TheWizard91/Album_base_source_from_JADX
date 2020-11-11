package p019io.reactivex.disposables;

import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.internal.functions.ObjectHelper;

/* renamed from: io.reactivex.disposables.ReferenceDisposable */
abstract class ReferenceDisposable<T> extends AtomicReference<T> implements Disposable {
    private static final long serialVersionUID = 6537757548749041217L;

    /* access modifiers changed from: protected */
    public abstract void onDisposed(T t);

    ReferenceDisposable(T value) {
        super(ObjectHelper.requireNonNull(value, "value is null"));
    }

    public final void dispose() {
        T value;
        if (get() != null && (value = getAndSet((Object) null)) != null) {
            onDisposed(value);
        }
    }

    public final boolean isDisposed() {
        return get() == null;
    }
}
