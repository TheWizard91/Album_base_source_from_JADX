package p019io.reactivex;

/* renamed from: io.reactivex.FlowableConverter */
public interface FlowableConverter<T, R> {
    R apply(Flowable<T> flowable);
}