package com.firebase.p008ui.firestore;

import com.firebase.p008ui.common.Preconditions;
import com.google.firebase.firestore.DocumentSnapshot;

/* renamed from: com.firebase.ui.firestore.ClassSnapshotParser */
public class ClassSnapshotParser<T> implements SnapshotParser<T> {
    private final Class<T> mModelClass;

    public ClassSnapshotParser(Class<T> modelClass) {
        this.mModelClass = (Class) Preconditions.checkNotNull(modelClass);
    }

    public T parseSnapshot(DocumentSnapshot snapshot) {
        return snapshot.toObject(this.mModelClass);
    }
}
