package p019io.reactivex.internal.operators.flowable;

import org.reactivestreams.Subscriber;
import p019io.reactivex.Flowable;
import p019io.reactivex.FlowableOperator;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.flowable.FlowableLift */
public final class FlowableLift<R, T> extends AbstractFlowableWithUpstream<T, R> {
    final FlowableOperator<? extends R, ? super T> operator;

    public FlowableLift(Flowable<T> source, FlowableOperator<? extends R, ? super T> operator2) {
        super(source);
        this.operator = operator2;
    }

    public void subscribeActual(Subscriber<? super R> s) {
        try {
            Subscriber<? super Object> apply = this.operator.apply(s);
            if (apply != null) {
                this.source.subscribe(apply);
                return;
            }
            throw new NullPointerException("Operator " + this.operator + " returned a null Subscriber");
        } catch (NullPointerException e) {
            throw e;
        } catch (Throwable e2) {
            Exceptions.throwIfFatal(e2);
            RxJavaPlugins.onError(e2);
            NullPointerException npe = new NullPointerException("Actually not, but can't throw other exceptions due to RS");
            npe.initCause(e2);
            throw npe;
        }
    }
}
