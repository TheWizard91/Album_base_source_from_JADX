package com.firebase.p008ui.firestore;

import com.firebase.p008ui.common.BaseObservableSnapshotArray;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

/* renamed from: com.firebase.ui.firestore.ObservableSnapshotArray */
public abstract class ObservableSnapshotArray<T> extends BaseObservableSnapshotArray<DocumentSnapshot, FirebaseFirestoreException, ChangeEventListener, T> {
    public ObservableSnapshotArray(SnapshotParser<T> parser) {
        super(new CachingSnapshotParser(parser));
    }
}
