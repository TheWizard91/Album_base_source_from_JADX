package p019io.reactivex.internal.operators.maybe;

import p019io.reactivex.MaybeObserver;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.CompositeException;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Action;
import p019io.reactivex.functions.Consumer;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.maybe.MaybePeek */
public final class MaybePeek<T> extends AbstractMaybeWithUpstream<T, T> {
    final Action onAfterTerminate;
    final Action onCompleteCall;
    final Action onDisposeCall;
    final Consumer<? super Throwable> onErrorCall;
    final Consumer<? super Disposable> onSubscribeCall;
    final Consumer<? super T> onSuccessCall;

    public MaybePeek(MaybeSource<T> source, Consumer<? super Disposable> onSubscribeCall2, Consumer<? super T> onSuccessCall2, Consumer<? super Throwable> onErrorCall2, Action onCompleteCall2, Action onAfterTerminate2, Action onDispose) {
        super(source);
        this.onSubscribeCall = onSubscribeCall2;
        this.onSuccessCall = onSuccessCall2;
        this.onErrorCall = onErrorCall2;
        this.onCompleteCall = onCompleteCall2;
        this.onAfterTerminate = onAfterTerminate2;
        this.onDisposeCall = onDispose;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe(new MaybePeekObserver(observer, this));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybePeek$MaybePeekObserver */
    static final class MaybePeekObserver<T> implements MaybeObserver<T>, Disposable {
        final MaybeObserver<? super T> actual;

        /* renamed from: d */
        Disposable f400d;
        final MaybePeek<T> parent;

        MaybePeekObserver(MaybeObserver<? super T> actual2, MaybePeek<T> parent2) {
            this.actual = actual2;
            this.parent = parent2;
        }

        public void dispose() {
            try {
                this.parent.onDisposeCall.run();
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                RxJavaPlugins.onError(ex);
            }
            this.f400d.dispose();
            this.f400d = DisposableHelper.DISPOSED;
        }

        public boolean isDisposed() {
            return this.f400d.isDisposed();
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f400d, d)) {
                try {
                    this.parent.onSubscribeCall.accept(d);
                    this.f400d = d;
                    this.actual.onSubscribe(this);
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    d.dispose();
                    this.f400d = DisposableHelper.DISPOSED;
                    EmptyDisposable.error(ex, (MaybeObserver<?>) this.actual);
                }
            }
        }

        public void onSuccess(T value) {
            if (this.f400d != DisposableHelper.DISPOSED) {
                try {
                    this.parent.onSuccessCall.accept(value);
                    this.f400d = DisposableHelper.DISPOSED;
                    this.actual.onSuccess(value);
                    onAfterTerminate();
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    onErrorInner(ex);
                }
            }
        }

        public void onError(Throwable e) {
            if (this.f400d == DisposableHelper.DISPOSED) {
                RxJavaPlugins.onError(e);
            } else {
                onErrorInner(e);
            }
        }

        /* access modifiers changed from: package-private */
        public void onErrorInner(Throwable e) {
            try {
                this.parent.onErrorCall.accept(e);
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                e = new CompositeException(e, ex);
            }
            this.f400d = DisposableHelper.DISPOSED;
            this.actual.onError(e);
            onAfterTerminate();
        }

        public void onComplete() {
            if (this.f400d != DisposableHelper.DISPOSED) {
                try {
                    this.parent.onCompleteCall.run();
                    this.f400d = DisposableHelper.DISPOSED;
                    this.actual.onComplete();
                    onAfterTerminate();
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    onErrorInner(ex);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void onAfterTerminate() {
            try {
                this.parent.onAfterTerminate.run();
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                RxJavaPlugins.onError(ex);
            }
        }
    }
}
