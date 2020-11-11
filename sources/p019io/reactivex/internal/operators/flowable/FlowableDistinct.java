package p019io.reactivex.internal.operators.flowable;

import java.util.Collection;
import java.util.concurrent.Callable;
import org.reactivestreams.Subscriber;
import p019io.reactivex.Flowable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.internal.subscribers.BasicFuseableSubscriber;
import p019io.reactivex.internal.subscriptions.EmptySubscription;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableDistinct */
public final class FlowableDistinct<T, K> extends AbstractFlowableWithUpstream<T, T> {
    final Callable<? extends Collection<? super K>> collectionSupplier;
    final Function<? super T, K> keySelector;

    public FlowableDistinct(Flowable<T> source, Function<? super T, K> keySelector2, Callable<? extends Collection<? super K>> collectionSupplier2) {
        super(source);
        this.keySelector = keySelector2;
        this.collectionSupplier = collectionSupplier2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(Subscriber<? super T> observer) {
        try {
            this.source.subscribe(new DistinctSubscriber(observer, this.keySelector, (Collection) ObjectHelper.requireNonNull(this.collectionSupplier.call(), "The collectionSupplier returned a null collection. Null values are generally not allowed in 2.x operators and sources.")));
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            EmptySubscription.error(ex, observer);
        }
    }

    /* renamed from: io.reactivex.internal.operators.flowable.FlowableDistinct$DistinctSubscriber */
    static final class DistinctSubscriber<T, K> extends BasicFuseableSubscriber<T, T> {
        final Collection<? super K> collection;
        final Function<? super T, K> keySelector;

        DistinctSubscriber(Subscriber<? super T> actual, Function<? super T, K> keySelector2, Collection<? super K> collection2) {
            super(actual);
            this.keySelector = keySelector2;
            this.collection = collection2;
        }

        public void onNext(T value) {
            if (!this.done) {
                if (this.sourceMode == 0) {
                    try {
                        if (this.collection.add(ObjectHelper.requireNonNull(this.keySelector.apply(value), "The keySelector returned a null key"))) {
                            this.actual.onNext(value);
                        } else {
                            this.f565s.request(1);
                        }
                    } catch (Throwable ex) {
                        fail(ex);
                    }
                } else {
                    this.actual.onNext(null);
                }
            }
        }

        public void onError(Throwable e) {
            if (this.done) {
                RxJavaPlugins.onError(e);
                return;
            }
            this.done = true;
            this.collection.clear();
            this.actual.onError(e);
        }

        public void onComplete() {
            if (!this.done) {
                this.done = true;
                this.collection.clear();
                this.actual.onComplete();
            }
        }

        public int requestFusion(int mode) {
            return transitiveBoundaryFusion(mode);
        }

        public T poll() throws Exception {
            T v;
            while (true) {
                v = this.f564qs.poll();
                if (v == null || this.collection.add(ObjectHelper.requireNonNull(this.keySelector.apply(v), "The keySelector returned a null key"))) {
                    return v;
                }
                if (this.sourceMode == 2) {
                    this.f565s.request(1);
                }
            }
            return v;
        }

        public void clear() {
            this.collection.clear();
            super.clear();
        }
    }
}
