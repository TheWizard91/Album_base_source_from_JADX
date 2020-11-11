package p019io.reactivex.internal.operators.observable;

import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.CompositeException;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Action;
import p019io.reactivex.functions.Consumer;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableDoOnEach */
public final class ObservableDoOnEach<T> extends AbstractObservableWithUpstream<T, T> {
    final Action onAfterTerminate;
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;

    public ObservableDoOnEach(ObservableSource<T> source, Consumer<? super T> onNext2, Consumer<? super Throwable> onError2, Action onComplete2, Action onAfterTerminate2) {
        super(source);
        this.onNext = onNext2;
        this.onError = onError2;
        this.onComplete = onComplete2;
        this.onAfterTerminate = onAfterTerminate2;
    }

    public void subscribeActual(Observer<? super T> t) {
        this.source.subscribe(new DoOnEachObserver(t, this.onNext, this.onError, this.onComplete, this.onAfterTerminate));
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableDoOnEach$DoOnEachObserver */
    static final class DoOnEachObserver<T> implements Observer<T>, Disposable {
        final Observer<? super T> actual;
        boolean done;
        final Action onAfterTerminate;
        final Action onComplete;
        final Consumer<? super Throwable> onError;
        final Consumer<? super T> onNext;

        /* renamed from: s */
        Disposable f438s;

        DoOnEachObserver(Observer<? super T> actual2, Consumer<? super T> onNext2, Consumer<? super Throwable> onError2, Action onComplete2, Action onAfterTerminate2) {
            this.actual = actual2;
            this.onNext = onNext2;
            this.onError = onError2;
            this.onComplete = onComplete2;
            this.onAfterTerminate = onAfterTerminate2;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f438s, s)) {
                this.f438s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f438s.dispose();
        }

        public boolean isDisposed() {
            return this.f438s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    this.onNext.accept(t);
                    this.actual.onNext(t);
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    this.f438s.dispose();
                    onError(e);
                }
            }
        }

        public void onError(Throwable t) {
            if (this.done) {
                RxJavaPlugins.onError(t);
                return;
            }
            this.done = true;
            try {
                this.onError.accept(t);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                t = new CompositeException(t, e);
            }
            this.actual.onError(t);
            try {
                this.onAfterTerminate.run();
            } catch (Throwable e2) {
                Exceptions.throwIfFatal(e2);
                RxJavaPlugins.onError(e2);
            }
        }

        public void onComplete() {
            if (!this.done) {
                try {
                    this.onComplete.run();
                    this.done = true;
                    this.actual.onComplete();
                    try {
                        this.onAfterTerminate.run();
                    } catch (Throwable e) {
                        Exceptions.throwIfFatal(e);
                        RxJavaPlugins.onError(e);
                    }
                } catch (Throwable e2) {
                    Exceptions.throwIfFatal(e2);
                    onError(e2);
                }
            }
        }
    }
}
