package p019io.reactivex.internal.operators.maybe;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import p019io.reactivex.Maybe;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.disposables.Disposables;
import p019io.reactivex.exceptions.Exceptions;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeFromFuture */
public final class MaybeFromFuture<T> extends Maybe<T> {
    final Future<? extends T> future;
    final long timeout;
    final TimeUnit unit;

    public MaybeFromFuture(Future<? extends T> future2, long timeout2, TimeUnit unit2) {
        this.future = future2;
        this.timeout = timeout2;
        this.unit = unit2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> observer) {
        T v;
        Disposable d = Disposables.empty();
        observer.onSubscribe(d);
        if (!d.isDisposed()) {
            try {
                long j = this.timeout;
                if (j <= 0) {
                    v = this.future.get();
                } else {
                    v = this.future.get(j, this.unit);
                }
                if (d.isDisposed()) {
                    return;
                }
                if (v == null) {
                    observer.onComplete();
                } else {
                    observer.onSuccess(v);
                }
            } catch (Throwable th) {
                ex = th;
                if (ex instanceof ExecutionException) {
                    ex = ex.getCause();
                }
                Exceptions.throwIfFatal(ex);
                if (!d.isDisposed()) {
                    observer.onError(ex);
                }
            }
        }
    }
}
