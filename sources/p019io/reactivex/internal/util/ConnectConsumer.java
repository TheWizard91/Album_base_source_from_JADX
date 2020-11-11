package p019io.reactivex.internal.util;

import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.functions.Consumer;

/* renamed from: io.reactivex.internal.util.ConnectConsumer */
public final class ConnectConsumer implements Consumer<Disposable> {
    public Disposable disposable;

    public void accept(Disposable t) throws Exception {
        this.disposable = t;
    }
}
