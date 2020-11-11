package com.firebase.p008ui.common;

import com.firebase.p008ui.common.BaseChangeEventListener;
import java.util.AbstractList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/* renamed from: com.firebase.ui.common.BaseObservableSnapshotArray */
public abstract class BaseObservableSnapshotArray<S, E, L extends BaseChangeEventListener<S, E>, T> extends AbstractList<T> {
    private final BaseCachingSnapshotParser<S, T> mCachingParser;
    private boolean mHasDataChanged = false;
    private final List<L> mListeners = new CopyOnWriteArrayList();

    /* access modifiers changed from: protected */
    public abstract List<S> getSnapshots();

    public BaseObservableSnapshotArray(BaseCachingSnapshotParser<S, T> parser) {
        this.mCachingParser = (BaseCachingSnapshotParser) Preconditions.checkNotNull(parser);
    }

    public T get(int index) {
        return this.mCachingParser.parseSnapshot(getSnapshot(index));
    }

    public int size() {
        return getSnapshots().size();
    }

    public S getSnapshot(int index) {
        return getSnapshots().get(index);
    }

    public L addChangeEventListener(L listener) {
        Preconditions.checkNotNull(listener);
        boolean wasListening = isListening();
        this.mListeners.add(listener);
        for (int i = 0; i < size(); i++) {
            listener.onChildChanged(ChangeEventType.ADDED, getSnapshot(i), i, -1);
        }
        if (this.mHasDataChanged != 0) {
            listener.onDataChanged();
        }
        if (!wasListening) {
            onCreate();
        }
        return listener;
    }

    public void removeChangeEventListener(L listener) {
        Preconditions.checkNotNull(listener);
        boolean wasListening = isListening();
        this.mListeners.remove(listener);
        if (!isListening() && wasListening) {
            onDestroy();
        }
    }

    public void removeAllListeners() {
        for (L listener : this.mListeners) {
            removeChangeEventListener(listener);
        }
    }

    /* access modifiers changed from: protected */
    public void onCreate() {
    }

    /* access modifiers changed from: protected */
    public void onDestroy() {
        this.mHasDataChanged = false;
        getSnapshots().clear();
        this.mCachingParser.clear();
    }

    public boolean isListening() {
        return !this.mListeners.isEmpty();
    }

    public boolean isListening(L listener) {
        return this.mListeners.contains(listener);
    }

    public void clear() {
        getSnapshots().clear();
        notifyOnDataChanged();
    }

    /* access modifiers changed from: protected */
    public final void notifyOnChildChanged(ChangeEventType type, S snapshot, int newIndex, int oldIndex) {
        if (type == ChangeEventType.CHANGED || type == ChangeEventType.REMOVED) {
            this.mCachingParser.invalidate(snapshot);
        }
        for (L listener : this.mListeners) {
            listener.onChildChanged(type, snapshot, newIndex, oldIndex);
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyOnDataChanged() {
        this.mHasDataChanged = true;
        for (L listener : this.mListeners) {
            listener.onDataChanged();
        }
    }

    /* access modifiers changed from: protected */
    public final void notifyOnError(E e) {
        for (L listener : this.mListeners) {
            listener.onError(e);
        }
    }
}
