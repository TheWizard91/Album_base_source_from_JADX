package p019io.reactivex.internal.operators.maybe;

import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.Maybe;
import p019io.reactivex.MaybeEmitter;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.MaybeOnSubscribe;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Cancellable;
import p019io.reactivex.internal.disposables.CancellableDisposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeCreate */
public final class MaybeCreate<T> extends Maybe<T> {
    final MaybeOnSubscribe<T> source;

    public MaybeCreate(MaybeOnSubscribe<T> source2) {
        this.source = source2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> s) {
        Emitter<T> parent = new Emitter<>(s);
        s.onSubscribe(parent);
        try {
            this.source.subscribe(parent);
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            parent.onError(ex);
        }
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeCreate$Emitter */
    static final class Emitter<T> extends AtomicReference<Disposable> implements MaybeEmitter<T>, Disposable {
        private static final long serialVersionUID = -2467358622224974244L;
        final MaybeObserver<? super T> actual;

        Emitter(MaybeObserver<? super T> actual2) {
            this.actual = actual2;
        }

        public void onSuccess(T value) {
            Disposable d;
            if (get() != DisposableHelper.DISPOSED && (d = (Disposable) getAndSet(DisposableHelper.DISPOSED)) != DisposableHelper.DISPOSED) {
                if (value == null) {
                    try {
                        this.actual.onError(new NullPointerException("onSuccess called with null. Null values are generally not allowed in 2.x operators and sources."));
                    } catch (Throwable th) {
                        if (d != null) {
                            d.dispose();
                        }
                        throw th;
                    }
                } else {
                    this.actual.onSuccess(value);
                }
                if (d != null) {
                    d.dispose();
                }
            }
        }

        public void onError(Throwable t) {
            if (!tryOnError(t)) {
                RxJavaPlugins.onError(t);
            }
        }

        public boolean tryOnError(Throwable t) {
            Disposable d;
            if (t == null) {
                t = new NullPointerException("onError called with null. Null values are generally not allowed in 2.x operators and sources.");
            }
            if (get() == DisposableHelper.DISPOSED || (d = (Disposable) getAndSet(DisposableHelper.DISPOSED)) == DisposableHelper.DISPOSED) {
                return false;
            }
            try {
                this.actual.onError(t);
            } finally {
                if (d != null) {
                    d.dispose();
                }
            }
        }

        public void onComplete() {
            Disposable d;
            if (get() != DisposableHelper.DISPOSED && (d = (Disposable) getAndSet(DisposableHelper.DISPOSED)) != DisposableHelper.DISPOSED) {
                try {
                    this.actual.onComplete();
                } finally {
                    if (d != null) {
                        d.dispose();
                    }
                }
            }
        }

        public void setDisposable(Disposable d) {
            DisposableHelper.set(this, d);
        }

        public void setCancellable(Cancellable c) {
            setDisposable(new CancellableDisposable(c));
        }

        public void dispose() {
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) get());
        }

        public String toString() {
            return String.format("%s{%s}", new Object[]{getClass().getSimpleName(), super.toString()});
        }
    }
}
