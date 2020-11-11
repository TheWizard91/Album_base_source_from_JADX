package p019io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.Observable;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.subjects.PublishSubject;

/* renamed from: io.reactivex.internal.operators.observable.ObservablePublishSelector */
public final class ObservablePublishSelector<T, R> extends AbstractObservableWithUpstream<T, R> {
    final Function<? super Observable<T>, ? extends ObservableSource<R>> selector;

    public ObservablePublishSelector(ObservableSource<T> source, Function<? super Observable<T>, ? extends ObservableSource<R>> selector2) {
        super(source);
        this.selector = selector2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super R> observer) {
        PublishSubject<T> subject = PublishSubject.create();
        try {
            ObservableSource<? extends R> target = (ObservableSource) ObjectHelper.requireNonNull(this.selector.apply(subject), "The selector returned a null ObservableSource");
            TargetObserver<T, R> o = new TargetObserver<>(observer);
            target.subscribe(o);
            this.source.subscribe(new SourceObserver(subject, o));
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            EmptyDisposable.error(ex, (Observer<?>) observer);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservablePublishSelector$SourceObserver */
    static final class SourceObserver<T, R> implements Observer<T> {
        final PublishSubject<T> subject;
        final AtomicReference<Disposable> target;

        SourceObserver(PublishSubject<T> subject2, AtomicReference<Disposable> target2) {
            this.subject = subject2;
            this.target = target2;
        }

        public void onSubscribe(Disposable d) {
            DisposableHelper.setOnce(this.target, d);
        }

        public void onNext(T value) {
            this.subject.onNext(value);
        }

        public void onError(Throwable e) {
            this.subject.onError(e);
        }

        public void onComplete() {
            this.subject.onComplete();
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservablePublishSelector$TargetObserver */
    static final class TargetObserver<T, R> extends AtomicReference<Disposable> implements Observer<R>, Disposable {
        private static final long serialVersionUID = 854110278590336484L;
        final Observer<? super R> actual;

        /* renamed from: d */
        Disposable f463d;

        TargetObserver(Observer<? super R> actual2) {
            this.actual = actual2;
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f463d, d)) {
                this.f463d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(R value) {
            this.actual.onNext(value);
        }

        public void onError(Throwable e) {
            DisposableHelper.dispose(this);
            this.actual.onError(e);
        }

        public void onComplete() {
            DisposableHelper.dispose(this);
            this.actual.onComplete();
        }

        public void dispose() {
            this.f463d.dispose();
            DisposableHelper.dispose(this);
        }

        public boolean isDisposed() {
            return this.f463d.isDisposed();
        }
    }
}
