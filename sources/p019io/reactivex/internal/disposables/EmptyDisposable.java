package p019io.reactivex.internal.disposables;

import p019io.reactivex.CompletableObserver;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.Observer;
import p019io.reactivex.SingleObserver;
import p019io.reactivex.internal.fuseable.QueueDisposable;

/* renamed from: io.reactivex.internal.disposables.EmptyDisposable */
public enum EmptyDisposable implements QueueDisposable<Object> {
    INSTANCE,
    NEVER;

    public void dispose() {
    }

    public boolean isDisposed() {
        return this == INSTANCE;
    }

    public static void complete(Observer<?> s) {
        s.onSubscribe(INSTANCE);
        s.onComplete();
    }

    public static void complete(MaybeObserver<?> s) {
        s.onSubscribe(INSTANCE);
        s.onComplete();
    }

    public static void error(Throwable e, Observer<?> s) {
        s.onSubscribe(INSTANCE);
        s.onError(e);
    }

    public static void complete(CompletableObserver s) {
        s.onSubscribe(INSTANCE);
        s.onComplete();
    }

    public static void error(Throwable e, CompletableObserver s) {
        s.onSubscribe(INSTANCE);
        s.onError(e);
    }

    public static void error(Throwable e, SingleObserver<?> s) {
        s.onSubscribe(INSTANCE);
        s.onError(e);
    }

    public static void error(Throwable e, MaybeObserver<?> s) {
        s.onSubscribe(INSTANCE);
        s.onError(e);
    }

    public boolean offer(Object value) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    public boolean offer(Object v1, Object v2) {
        throw new UnsupportedOperationException("Should not be called!");
    }

    public Object poll() throws Exception {
        return null;
    }

    public boolean isEmpty() {
        return true;
    }

    public void clear() {
    }

    public int requestFusion(int mode) {
        return mode & 2;
    }
}
