package p019io.reactivex.internal.operators.flowable;

import java.util.Iterator;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.BiFunction;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.subscriptions.EmptySubscription;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableZipIterable */
public final class FlowableZipIterable<T, U, V> extends AbstractFlowableWithUpstream<T, V> {
    final Iterable<U> other;
    final BiFunction<? super T, ? super U, ? extends V> zipper;

    public FlowableZipIterable(Flowable<T> source, Iterable<U> other2, BiFunction<? super T, ? super U, ? extends V> zipper2) {
        super(source);
        this.other = other2;
        this.zipper = zipper2;
    }

    public void subscribeActual(Subscriber<? super V> t) {
        try {
            Iterator<U> it = (Iterator) ObjectHelper.requireNonNull(this.other.iterator(), "The iterator returned by other is null");
            try {
                if (!it.hasNext()) {
                    EmptySubscription.complete(t);
                } else {
                    this.source.subscribe(new ZipIterableSubscriber(t, it, this.zipper));
                }
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                EmptySubscription.error(e, t);
            }
        } catch (Throwable e2) {
            Exceptions.throwIfFatal(e2);
            EmptySubscription.error(e2, t);
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableZipIterable$ZipIterableSubscriber */
    static final class ZipIterableSubscriber<T, U, V> implements FlowableSubscriber<T>, Subscription {
        final Subscriber<? super V> actual;
        boolean done;
        final Iterator<U> iterator;

        /* renamed from: s */
        Subscription f372s;
        final BiFunction<? super T, ? super U, ? extends V> zipper;

        ZipIterableSubscriber(Subscriber<? super V> actual2, Iterator<U> iterator2, BiFunction<? super T, ? super U, ? extends V> zipper2) {
            this.actual = actual2;
            this.iterator = iterator2;
            this.zipper = zipper2;
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f372s, s)) {
                this.f372s = s;
                this.actual.onSubscribe(this);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                try {
                    try {
                        this.actual.onNext(ObjectHelper.requireNonNull(this.zipper.apply(t, ObjectHelper.requireNonNull(this.iterator.next(), "The iterator returned a null value")), "The zipper function returned a null value"));
                        try {
                            if (!this.iterator.hasNext()) {
                                this.done = true;
                                this.f372s.cancel();
                                this.actual.onComplete();
                            }
                        } catch (Throwable e) {
                            error(e);
                        }
                    } catch (Throwable e2) {
                        error(e2);
                    }
                } catch (Throwable e3) {
                    error(e3);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void error(Throwable e) {
            Exceptions.throwIfFatal(e);
            this.done = true;
            this.f372s.cancel();
            this.actual.onError(e);
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
                this.actual.onComplete();
            }
        }

        public void request(long n) {
            this.f372s.request(n);
        }

        public void cancel() {
            this.f372s.cancel();
        }
    }
}
