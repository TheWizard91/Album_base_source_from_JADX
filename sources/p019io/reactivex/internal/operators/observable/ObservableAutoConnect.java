package p019io.reactivex.internal.operators.observable;

import java.util.concurrent.atomic.AtomicInteger;
import p019io.reactivex.Observable;
import p019io.reactivex.Observer;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.functions.Consumer;
import p019io.reactivex.observables.ConnectableObservable;

/* renamed from: io.reactivex.internal.operators.observable.ObservableAutoConnect */
public final class ObservableAutoConnect<T> extends Observable<T> {
    final AtomicInteger clients = new AtomicInteger();
    final Consumer<? super Disposable> connection;
    final int numberOfObservers;
    final ConnectableObservable<? extends T> source;

    public ObservableAutoConnect(ConnectableObservable<? extends T> source2, int numberOfObservers2, Consumer<? super Disposable> connection2) {
        this.source = source2;
        this.numberOfObservers = numberOfObservers2;
        this.connection = connection2;
    }

    public void subscribeActual(Observer<? super T> child) {
        this.source.subscribe(child);
        if (this.clients.incrementAndGet() == this.numberOfObservers) {
            this.source.connect(this.connection);
        }
    }
}
