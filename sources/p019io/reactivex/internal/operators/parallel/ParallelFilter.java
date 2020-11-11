package p019io.reactivex.internal.operators.parallel;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Predicate;
import p019io.reactivex.internal.fuseable.ConditionalSubscriber;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.parallel.ParallelFlowable;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.parallel.ParallelFilter */
public final class ParallelFilter<T> extends ParallelFlowable<T> {
    final Predicate<? super T> predicate;
    final ParallelFlowable<T> source;

    public ParallelFilter(ParallelFlowable<T> source2, Predicate<? super T> predicate2) {
        this.source = source2;
        this.predicate = predicate2;
    }

    public void subscribe(Subscriber<? super T>[] subscribers) {
        if (validate(subscribers)) {
            int n = subscribers.length;
            Subscriber<? super T>[] parents = new Subscriber[n];
            for (int i = 0; i < n; i++) {
                ConditionalSubscriber conditionalSubscriber = subscribers[i];
                if (conditionalSubscriber instanceof ConditionalSubscriber) {
                    parents[i] = new ParallelFilterConditionalSubscriber<>(conditionalSubscriber, this.predicate);
                } else {
                    parents[i] = new ParallelFilterSubscriber<>(conditionalSubscriber, this.predicate);
                }
            }
            this.source.subscribe(parents);
        }
    }

    public int parallelism() {
        return this.source.parallelism();
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelFilter$BaseFilterSubscriber */
    static abstract class BaseFilterSubscriber<T> implements ConditionalSubscriber<T>, Subscription {
        boolean done;
        final Predicate<? super T> predicate;

        /* renamed from: s */
        Subscription f522s;

        BaseFilterSubscriber(Predicate<? super T> predicate2) {
            this.predicate = predicate2;
        }

        public final void request(long n) {
            this.f522s.request(n);
        }

        public final void cancel() {
            this.f522s.cancel();
        }

        public final void onNext(T t) {
            if (!tryOnNext(t) && !this.done) {
                this.f522s.request(1);
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelFilter$ParallelFilterSubscriber */
    static final class ParallelFilterSubscriber<T> extends BaseFilterSubscriber<T> {
        final Subscriber<? super T> actual;

        ParallelFilterSubscriber(Subscriber<? super T> actual2, Predicate<? super T> predicate) {
            super(predicate);
            this.actual = actual2;
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f522s, s)) {
                this.f522s = s;
                this.actual.onSubscribe(this);
            }
        }

        public boolean tryOnNext(T t) {
            if (!this.done) {
                try {
                    if (this.predicate.test(t)) {
                        this.actual.onNext(t);
                        return true;
                    }
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    cancel();
                    onError(ex);
                    return false;
                }
            }
            return false;
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
    }

    /* renamed from: io.reactivex.internal.operators.parallel.ParallelFilter$ParallelFilterConditionalSubscriber */
    static final class ParallelFilterConditionalSubscriber<T> extends BaseFilterSubscriber<T> {
        final ConditionalSubscriber<? super T> actual;

        ParallelFilterConditionalSubscriber(ConditionalSubscriber<? super T> actual2, Predicate<? super T> predicate) {
            super(predicate);
            this.actual = actual2;
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f522s, s)) {
                this.f522s = s;
                this.actual.onSubscribe(this);
            }
        }

        public boolean tryOnNext(T t) {
            if (!this.done) {
                try {
                    if (this.predicate.test(t)) {
                        return this.actual.tryOnNext(t);
                    }
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    cancel();
                    onError(ex);
                    return false;
                }
            }
            return false;
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
    }
}