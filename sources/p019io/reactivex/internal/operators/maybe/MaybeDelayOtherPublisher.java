package p019io.reactivex.internal.operators.maybe;

import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscription;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.MaybeObserver;
import p019io.reactivex.MaybeSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.CompositeException;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;

/* renamed from: io.reactivex.internal.operators.maybe.MaybeDelayOtherPublisher */
public final class MaybeDelayOtherPublisher<T, U> extends AbstractMaybeWithUpstream<T, T> {
    final Publisher<U> other;

    public MaybeDelayOtherPublisher(MaybeSource<T> source, Publisher<U> other2) {
        super(source);
        this.other = other2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(MaybeObserver<? super T> observer) {
        this.source.subscribe(new DelayMaybeObserver(observer, this.other));
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeDelayOtherPublisher$DelayMaybeObserver */
    static final class DelayMaybeObserver<T, U> implements MaybeObserver<T>, Disposable {

        /* renamed from: d */
        Disposable f375d;
        final OtherSubscriber<T> other;
        final Publisher<U> otherSource;

        DelayMaybeObserver(MaybeObserver<? super T> actual, Publisher<U> otherSource2) {
            this.other = new OtherSubscriber<>(actual);
            this.otherSource = otherSource2;
        }

        public void dispose() {
            this.f375d.dispose();
            this.f375d = DisposableHelper.DISPOSED;
            SubscriptionHelper.cancel(this.other);
        }

        public boolean isDisposed() {
            return SubscriptionHelper.isCancelled((Subscription) this.other.get());
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f375d, d)) {
                this.f375d = d;
                this.other.actual.onSubscribe(this);
            }
        }

        public void onSuccess(T value) {
            this.f375d = DisposableHelper.DISPOSED;
            this.other.value = value;
            subscribeNext();
        }

        public void onError(Throwable e) {
            this.f375d = DisposableHelper.DISPOSED;
            this.other.error = e;
            subscribeNext();
        }

        public void onComplete() {
            this.f375d = DisposableHelper.DISPOSED;
            subscribeNext();
        }

        /* access modifiers changed from: package-private */
        public void subscribeNext() {
            this.otherSource.subscribe(this.other);
        }
    }

    /* renamed from: io.reactivex.internal.operators.maybe.MaybeDelayOtherPublisher$OtherSubscriber */
    static final class OtherSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<Object> {
        private static final long serialVersionUID = -1215060610805418006L;
        final MaybeObserver<? super T> actual;
        Throwable error;
        T value;

        OtherSubscriber(MaybeObserver<? super T> actual2) {
            this.actual = actual2;
        }

        public void onSubscribe(Subscription s) {
            SubscriptionHelper.setOnce(this, s, Long.MAX_VALUE);
        }

        public void onNext(Object t) {
            Subscription s = (Subscription) get();
            if (s != SubscriptionHelper.CANCELLED) {
                lazySet(SubscriptionHelper.CANCELLED);
                s.cancel();
                onComplete();
            }
        }

        public void onError(Throwable t) {
            Throwable e = this.error;
            if (e == null) {
                this.actual.onError(t);
                return;
            }
            this.actual.onError(new CompositeException(e, t));
        }

        public void onComplete() {
            Throwable e = this.error;
            if (e != null) {
                this.actual.onError(e);
                return;
            }
            T v = this.value;
            if (v != null) {
                this.actual.onSuccess(v);
            } else {
                this.actual.onComplete();
            }
        }
    }
}
