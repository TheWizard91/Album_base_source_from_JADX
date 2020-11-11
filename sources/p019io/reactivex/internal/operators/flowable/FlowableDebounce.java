package p019io.reactivex.internal.operators.flowable;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.exceptions.MissingBackpressureException;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.internal.util.BackpressureHelper;
import p019io.reactivex.plugins.RxJavaPlugins;
import p019io.reactivex.subscribers.DisposableSubscriber;
import p019io.reactivex.subscribers.SerializedSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableDebounce */
public final class FlowableDebounce<T, U> extends AbstractFlowableWithUpstream<T, T> {
    final Function<? super T, ? extends Publisher<U>> debounceSelector;

    public FlowableDebounce(Flowable<T> source, Function<? super T, ? extends Publisher<U>> debounceSelector2) {
        super(source);
        this.debounceSelector = debounceSelector2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> s) {
        this.source.subscribe(new DebounceSubscriber(new SerializedSubscriber(s), this.debounceSelector));
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDebounce$DebounceSubscriber */
    static final class DebounceSubscriber<T, U> extends AtomicLong implements FlowableSubscriber<T>, Subscription {
        private static final long serialVersionUID = 6725975399620862591L;
        final Subscriber<? super T> actual;
        final Function<? super T, ? extends Publisher<U>> debounceSelector;
        final AtomicReference<Disposable> debouncer = new AtomicReference<>();
        boolean done;
        volatile long index;

        /* renamed from: s */
        Subscription f274s;

        DebounceSubscriber(Subscriber<? super T> actual2, Function<? super T, ? extends Publisher<U>> debounceSelector2) {
            this.actual = actual2;
            this.debounceSelector = debounceSelector2;
        }

        public void onSubscribe(Subscription s) {
            if (SubscriptionHelper.validate(this.f274s, s)) {
                this.f274s = s;
                this.actual.onSubscribe(this);
                s.request(Long.MAX_VALUE);
            }
        }

        public void onNext(T t) {
            if (!this.done) {
                long idx = this.index + 1;
                this.index = idx;
                Disposable d = this.debouncer.get();
                if (d != null) {
                    d.dispose();
                }
                try {
                    Publisher<U> p = (Publisher) ObjectHelper.requireNonNull(this.debounceSelector.apply(t), "The publisher supplied is null");
                    DebounceInnerSubscriber<T, U> dis = new DebounceInnerSubscriber<>(this, idx, t);
                    if (this.debouncer.compareAndSet(d, dis)) {
                        p.subscribe(dis);
                    }
                } catch (Throwable e) {
                    Exceptions.throwIfFatal(e);
                    cancel();
                    this.actual.onError(e);
                }
            }
        }

        public void onError(Throwable t) {
            DisposableHelper.dispose(this.debouncer);
            this.actual.onError(t);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                Disposable d = this.debouncer.get();
                if (!DisposableHelper.isDisposed(d)) {
                    ((DebounceInnerSubscriber) d).emit();
                    DisposableHelper.dispose(this.debouncer);
                    this.actual.onComplete();
                }
            }
        }

        public void request(long n) {
            if (SubscriptionHelper.validate(n)) {
                BackpressureHelper.add(this, n);
            }
        }

        public void cancel() {
            this.f274s.cancel();
            DisposableHelper.dispose(this.debouncer);
        }

        /* access modifiers changed from: package-private */
        public void emit(long idx, T value) {
            if (idx != this.index) {
                return;
            }
            if (get() != 0) {
                this.actual.onNext(value);
                BackpressureHelper.produced(this, 1);
                return;
            }
            cancel();
            this.actual.onError(new MissingBackpressureException("Could not deliver value due to lack of requests"));
        }

        /* renamed from: io.reactivex.internal.operators.flowable.FlowableDebounce$DebounceSubscriber$DebounceInnerSubscriber */
        static final class DebounceInnerSubscriber<T, U> extends DisposableSubscriber<U> {
            boolean done;
            final long index;
            final AtomicBoolean once = new AtomicBoolean();
            final DebounceSubscriber<T, U> parent;
            final T value;

            DebounceInnerSubscriber(DebounceSubscriber<T, U> parent2, long index2, T value2) {
                this.parent = parent2;
                this.index = index2;
                this.value = value2;
            }

            public void onNext(U u) {
                if (!this.done) {
                    this.done = true;
                    cancel();
                    emit();
                }
            }

            /* access modifiers changed from: package-private */
            public void emit() {
                if (this.once.compareAndSet(false, true)) {
                    this.parent.emit(this.index, this.value);
                }
            }

            public void onError(Throwable t) {
                if (this.done) {
                    RxJavaPlugins.onError(t);
                    return;
                }
                this.done = true;
                this.parent.onError(t);
            }

            public void onComplete() {
                if (!this.done) {
                    this.done = true;
                    emit();
                }
            }
        }
    }
}
