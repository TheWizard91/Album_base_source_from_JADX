package p019io.reactivex.flowables;

import p019io.reactivex.Flowable;

/* renamed from: io.reactivex.flowables.GroupedFlowable */
public abstract class GroupedFlowable<K, T> extends Flowable<T> {
    final K key;

    protected GroupedFlowable(K key2) {
        this.key = key2;
    }

    public K getKey() {
        return this.key;
    }
}
