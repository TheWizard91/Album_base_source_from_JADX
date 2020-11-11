package p019io.reactivex.internal.operators.completable;

import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.CompletableSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.CompositeException;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Consumer;

/* renamed from: io.reactivex.internal.operators.completable.CompletableDoOnEvent */
public final class CompletableDoOnEvent extends Completable {
    final Consumer<? super Throwable> onEvent;
    final CompletableSource source;

    public CompletableDoOnEvent(CompletableSource source2, Consumer<? super Throwable> onEvent2) {
        this.source = source2;
        this.onEvent = onEvent2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver s) {
        this.source.subscribe(new DoOnEvent(s));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableDoOnEvent$DoOnEvent */
    final class DoOnEvent implements CompletableObserver {
        private final CompletableObserver observer;

        DoOnEvent(CompletableObserver observer2) {
            this.observer = observer2;
        }

        public void onComplete() {
            try {
                CompletableDoOnEvent.this.onEvent.accept(null);
                this.observer.onComplete();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                this.observer.onError(e);
            }
        }

        public void onError(Throwable e) {
            try {
                CompletableDoOnEvent.this.onEvent.accept(e);
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                e = new CompositeException(e, ex);
            }
            this.observer.onError(e);
        }

        public void onSubscribe(Disposable d) {
            this.observer.onSubscribe(d);
        }
    }
}
