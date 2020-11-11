package p019io.reactivex;

/* renamed from: io.reactivex.ObservableConverter */
public interface ObservableConverter<T, R> {
    R apply(Observable<T> observable);
}
