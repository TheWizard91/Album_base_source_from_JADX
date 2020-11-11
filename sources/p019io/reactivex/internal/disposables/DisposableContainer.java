package p019io.reactivex.internal.disposables;

import p019io.reactivex.disposables.Disposable;

/* renamed from: io.reactivex.internal.disposables.DisposableContainer */
public interface DisposableContainer {
    boolean add(Disposable disposable);

    boolean delete(Disposable disposable);

    boolean remove(Disposable disposable);
}
