package com.firebase.p008ui.firestore;

import com.firebase.p008ui.common.BaseCachingSnapshotParser;
import com.firebase.p008ui.common.BaseSnapshotParser;
import com.google.firebase.firestore.DocumentSnapshot;

/* renamed from: com.firebase.ui.firestore.CachingSnapshotParser */
public class CachingSnapshotParser<T> extends BaseCachingSnapshotParser<DocumentSnapshot, T> implements SnapshotParser<T> {
    public CachingSnapshotParser(BaseSnapshotParser<DocumentSnapshot, T> parser) {
        super(parser);
    }

    public String getId(DocumentSnapshot snapshot) {
        return snapshot.getId();
    }
}
