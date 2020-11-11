package p019io.reactivex.internal.operators.completable;

import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.Completable;
import p019io.reactivex.CompletableEmitter;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.CompletableOnSubscribe;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Cancellable;
import p019io.reactivex.internal.disposables.CancellableDisposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletableCreate */
public final class CompletableCreate extends Completable {
    final CompletableOnSubscribe source;

    public CompletableCreate(CompletableOnSubscribe source2) {
        this.source = source2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver s) {
        Emitter parent = new Emitter(s);
        s.onSubscribe(parent);
        try {
            this.source.subscribe(parent);
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            parent.onError(ex);
        }
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableCreate$Emitter */
    static final class Emitter extends AtomicReference<Disposable> implements CompletableEmitter, Disposable {
        private static final long serialVersionUID = -2467358622224974244L;
        final CompletableObserver actual;

        Emitter(CompletableObserver actual2) {
            this.actual = actual2;
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
