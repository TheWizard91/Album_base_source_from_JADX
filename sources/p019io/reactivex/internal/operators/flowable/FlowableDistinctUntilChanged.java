package p019io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import p019io.reactivex.Flowable;
import p019io.reactivex.functions.BiPredicate;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.fuseable.ConditionalSubscriber;
import p019io.reactivex.internal.subscribers.BasicFuseableConditionalSubscriber;
import p019io.reactivex.internal.subscribers.BasicFuseableSubscriber;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableDistinctUntilChanged */
public final class FlowableDistinctUntilChanged<T, K> extends AbstractFlowableWithUpstream<T, T> {
    final BiPredicate<? super K, ? super K> comparer;
    final Function<? super T, K> keySelector;

    public FlowableDistinctUntilChanged(Flowable<T> source, Function<? super T, K> keySelector2, BiPredicate<? super K, ? super K> comparer2) {
        super(source);
        this.keySelector = keySelector2;
        this.comparer = comparer2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> s) {
        if (s instanceof ConditionalSubscriber) {
            this.source.subscribe(new DistinctUntilChangedConditionalSubscriber((ConditionalSubscriber) s, this.keySelector, this.comparer));
        } else {
            this.source.subscribe(new DistinctUntilChangedSubscriber(s, this.keySelector, this.comparer));
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDistinctUntilChanged$DistinctUntilChangedSubscriber */
    static final class DistinctUntilChangedSubscriber<T, K> extends BasicFuseableSubscriber<T, T> implements ConditionalSubscriber<T> {
        final BiPredicate<? super K, ? super K> comparer;
        boolean hasValue;
        final Function<? super T, K> keySelector;
        K last;

        DistinctUntilChangedSubscriber(Subscriber<? super T> actual, Function<? super T, K> keySelector2, BiPredicate<? super K, ? super K> comparer2) {
            super(actual);
            this.keySelector = keySelector2;
            this.comparer = comparer2;
        }

        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.f565s.request(1);
            }
        }

        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                this.actual.onNext(t);
                return true;
            }
            try {
                K key = this.keySelector.apply(t);
                if (this.hasValue) {
                    boolean equal = this.comparer.test(this.last, key);
                    this.last = key;
                    if (equal) {
                        return false;
                    }
                } else {
                    this.hasValue = true;
                    this.last = key;
                }
                this.actual.onNext(t);
                return true;
            } catch (Throwable ex) {
                fail(ex);
                return true;
            }
        }

        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }

        public T poll() throws Exception {
            while (true) {
                T v = this.f564qs.poll();
                if (v == null) {
                    return null;
                }
                K key = this.keySelector.apply(v);
                if (!this.hasValue) {
                    this.hasValue = true;
                    this.last = key;
                    return v;
                } else if (!this.comparer.test(this.last, key)) {
                    this.last = key;
                    return v;
                } else {
                    this.last = key;
                    if (this.sourceMode != 1) {
                        this.f565s.request(1);
                    }
                }
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDistinctUntilChanged$DistinctUntilChangedConditionalSubscriber */
    static final class DistinctUntilChangedConditionalSubscriber<T, K> extends BasicFuseableConditionalSubscriber<T, T> {
        final BiPredicate<? super K, ? super K> comparer;
        boolean hasValue;
        final Function<? super T, K> keySelector;
        K last;

        DistinctUntilChangedConditionalSubscriber(ConditionalSubscriber<? super T> actual, Function<? super T, K> keySelector2, BiPredicate<? super K, ? super K> comparer2) {
            super(actual);
            this.keySelector = keySelector2;
            this.comparer = comparer2;
        }

        public void onNext(T t) {
            if (!tryOnNext(t)) {
                this.f563s.request(1);
            }
        }

        public boolean tryOnNext(T t) {
            if (this.done) {
                return false;
            }
            if (this.sourceMode != 0) {
                return this.actual.tryOnNext(t);
            }
            try {
                K key = this.keySelector.apply(t);
                if (this.hasValue) {
                    boolean equal = this.comparer.test(this.last, key);
                    this.last = key;
                    if (equal) {
                        return false;
                    }
                } else {
                    this.hasValue = true;
                    this.last = key;
                }
                this.actual.onNext(t);
                return true;
            } catch (Throwable ex) {
                fail(ex);
                return true;
            }
        }

        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }

        public T poll() throws Exception {
            while (true) {
                T v = this.f562qs.poll();
                if (v == null) {
                    return null;
                }
                K key = this.keySelector.apply(v);
                if (!this.hasValue) {
                    this.hasValue = true;
                    this.last = key;
                    return v;
                } else if (!this.comparer.test(this.last, key)) {
                    this.last = key;
                    return v;
                } else {
                    this.last = key;
                    if (this.sourceMode != 1) {
                        this.f563s.request(1);
                    }
                }
            }
        }
    }
}
