package p019io.reactivex.internal.operators.observable;

import java.util.concurrent.Callable;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.functions.BiConsumer;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.observable.ObservableCollect */
public final class ObservableCollect<T, U> extends AbstractObservableWithUpstream<T, U> {
    final BiConsumer<? super U, ? super T> collector;
    final Callable<? extends U> initialSupplier;

    public ObservableCollect(ObservableSource<T> source, Callable<? extends U> initialSupplier2, BiConsumer<? super U, ? super T> collector2) {
        super(source);
        this.initialSupplier = initialSupplier2;
        this.collector = collector2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Observer<? super U> t) {
        try {
            this.source.subscribe(new CollectObserver(t, ObjectHelper.requireNonNull(this.initialSupplier.call(), "The initialSupplier returned a null value"), this.collector));
        } catch (Throwable e) {
            EmptyDisposable.error(e, (Observer<?>) t);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableCollect$CollectObserver */
    static final class CollectObserver<T, U> implements Observer<T>, Disposable {
        final Observer<? super U> actual;
        final BiConsumer<? super U, ? super T> collector;
        boolean done;

        /* renamed from: s */
        Disposable f420s;

        /* renamed from: u */
        final U f421u;

        CollectObserver(Observer<? super U> actual2, U u, BiConsumer<? super U, ? super T> collector2) {
            this.actual = actual2;
            this.collector = collector2;
            this.f421u = u;
        }

        public void onSubscribe(Disposable s) {
            if (DisposableHelper.validate(this.f420s, s)) {
                this.f420s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void dispose() {
            this.f420s.dispose();
        }

        public boolean isDisposed() {
            return this.f420s.isDisposed();
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    this.collector.accept(this.f421u, t);
                } catch (Throwable e) {
                    this.f420s.dispose();
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
            this.actual.onError(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.actual.onNext(this.f421u);
                this.actual.onComplete();
            }
        }
    }
}
