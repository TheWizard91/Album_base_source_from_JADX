package p019io.reactivex.internal.operators.maybe;

import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeDelaySubscriptionOtherPublisher */
public final class MaybeDelaySubscriptionOtherPublisher<T, U> extends AbstractMaybeWithUpstream<T, T> {
    final Publisher<U> other;

    public MaybeDelaySubscriptionOtherPublisher(MaybeSource<T> source, Publisher<U> other2) {
        super(source);
        this.other = other2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.other.subscribe(new OtherSubscriber(observer, this.source));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeDelaySubscriptionOtherPublisher$OtherSubscriber */
    static final class OtherSubscriber<T> implements FlowableSubscriber<Object>, Disposable {
        final DelayMaybeObserver<T> main;

        /* renamed from: s */
        Subscription f376s;
        MaybeSource<T> source;

        OtherSubscriber(MaybeObserver<? super T> actual, MaybeSource<T> source2) {
            this.main = new DelayMaybeObserver<>(actual);
            this.source = source2;
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f376s, s)) {
                this.f376s = s;
                this.main.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(Object t) {
            if (this.f376s != SubscriptionHelper.CANCELLED) {
                this.f376s.cancel();
                this.f376s = SubscriptionHelper.CANCELLED;
                subscribeNext();
            }
        }

        public void onError(Throwable t) {
            if (this.f376s != SubscriptionHelper.CANCELLED) {
                this.f376s = SubscriptionHelper.CANCELLED;
                this.main.actual.onError(t);
                return;
            }
            RxJavaPlugins.onError(t);
        }

        public void onComplete() {
            if (this.f376s != SubscriptionHelper.CANCELLED) {
                this.f376s = SubscriptionHelper.CANCELLED;
                subscribeNext();
            }
        }

        /* access modifiers changed from: package-private */
        public void subscribeNext() {
            MaybeSource<T> src = this.source;
            this.source = null;
            src.subscribe(this.main);
        }

        public boolean isDisposed() {
            return DisposableHelper.isDisposed((Disposable) this.main.get());
        }

        public void dispose() {
            this.f376s.cancel();
            this.f376s = SubscriptionHelper.CANCELLED;
            DisposableHelper.dispose(this.main);
        }
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeDelaySubscriptionOtherPublisher$DelayMaybeObserver */
    static final class DelayMaybeObserver<T> extends AtomicReference<Disposable> implements MaybeObserver<T> {
        private static final long serialVersionUID = 706635022205076709L;
        final MaybeObserver<? super T> actual;

        DelayMaybeObserver(MaybeObserver<? super T> actual2) {
            this.actual = actual2;
        }

        public void onSubscribe(Disposable d) {
            DisposableHelper.setOnce(this, d);
        }

        public void onSuccess(T value) {
            this.actual.onSuccess(value);
        }

        public void onError(Throwable e) {
            this.actual.onError(e);
        }

        public void onComplete() {
            this.actual.onComplete();
        }
    }
}
