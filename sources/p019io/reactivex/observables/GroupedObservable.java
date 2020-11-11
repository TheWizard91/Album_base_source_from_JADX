package p019io.reactivex.observables;

import p019io.reactivex.Observable;

/* renamed from: io.reactivex.observables.GroupedObservable */
public abstract class GroupedObservable<K, T> extends Observable<T> {
    final K key;

    protected GroupedObservable(K key2) {
        this.key = key2;
    }

    public K getKey() {
        return this.key;
    }
}
