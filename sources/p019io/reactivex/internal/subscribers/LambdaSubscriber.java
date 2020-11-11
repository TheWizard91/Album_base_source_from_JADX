package p019io.reactivex.internal.subscribers;

import java.util.concurrent.atomic.AtomicReference;
import org.reactivestreams.Subscription;
import p019io.reactivex.FlowableSubscriber;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.CompositeException;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Action;
import p019io.reactivex.functions.Consumer;
import p019io.reactivex.internal.functions.Functions;
import p019io.reactivex.internal.subscriptions.SubscriptionHelper;
import p019io.reactivex.observers.LambdaConsumerIntrospection;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.subscribers.LambdaSubscriber */
public final class LambdaSubscriber<T> extends AtomicReference<Subscription> implements FlowableSubscriber<T>, Subscription, Disposable, LambdaConsumerIntrospection {
    private static final long serialVersionUID = -7251123623727029452L;
    final Action onComplete;
    final Consumer<? super Throwable> onError;
    final Consumer<? super T> onNext;
    final Consumer<? super Subscription> onSubscribe;

    public LambdaSubscriber(Consumer<? super T> onNext2, Consumer<? super Throwable> onError2, Action onComplete2, Consumer<? super Subscription> onSubscribe2) {
        this.onNext = onNext2;
        this.onError = onError2;
        this.onComplete = onComplete2;
        this.onSubscribe = onSubscribe2;
    }

    public void onSubscribe(Subscription s) {
        if (SubscriptionHelper.setOnce(this, s)) {
            try {
                this.onSubscribe.accept(this);
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                s.cancel();
                onError(ex);
            }
        }
    }

    public void onNext(T t) {
        if (!isDisposed()) {
            try {
                this.onNext.accept(t);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                ((Subscription) get()).cancel();
                onError(e);
            }
        }
    }

    public void onError(Throwable t) {
        if (get() != SubscriptionHelper.CANCELLED) {
            lazySet(SubscriptionHelper.CANCELLED);
            try {
                this.onError.accept(t);
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(new CompositeException(t, e));
            }
        } else {
            RxJavaPlugins.onError(t);
        }
    }

    public void onComplete() {
        if (get() != SubscriptionHelper.CANCELLED) {
            lazySet(SubscriptionHelper.CANCELLED);
            try {
                this.onComplete.run();
            } catch (Throwable e) {
                Exceptions.throwIfFatal(e);
                RxJavaPlugins.onError(e);
            }
        }
    }

    public void dispose() {
        cancel();
    }

    public boolean isDisposed() {
        return get() == SubscriptionHelper.CANCELLED;
    }

    public void request(long n) {
        ((Subscription) get()).request(n);
    }

    public void cancel() {
        SubscriptionHelper.cancel(this);
    }

    public boolean hasCustomOnError() {
        return this.onError != Functions.ON_ERROR_MISSING;
    }
}
