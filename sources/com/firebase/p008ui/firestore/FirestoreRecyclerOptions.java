package com.firebase.p008ui.firestore;

import androidx.lifecycle.LifecycleOwner;
import com.firebase.p008ui.common.Preconditions;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;

/* renamed from: com.firebase.ui.firestore.FirestoreRecyclerOptions */
public final class FirestoreRecyclerOptions<T> {
    private static final String ERR_SNAPSHOTS_NULL = "Snapshot array cannot be null. Call one of setSnapshotArray or setQuery";
    private static final String ERR_SNAPSHOTS_SET = "Snapshot array already set. Call only one of setSnapshotArray or setQuery";
    private LifecycleOwner mOwner;
    private ObservableSnapshotArray<T> mSnapshots;

    private FirestoreRecyclerOptions(ObservableSnapshotArray<T> snapshots, LifecycleOwner owner) {
        this.mSnapshots = snapshots;
        this.mOwner = owner;
    }

    public ObservableSnapshotArray<T> getSnapshots() {
        return this.mSnapshots;
    }

    public LifecycleOwner getOwner() {
        return this.mOwner;
    }

    /* renamed from: com.firebase.ui.firestore.FirestoreRecyclerOptions$Builder */
    public static final class Builder<T> {
        private LifecycleOwner mOwner;
        private ObservableSnapshotArray<T> mSnapshots;

        public Builder<T> setSnapshotArray(ObservableSnapshotArray<T> snapshots) {
            Preconditions.assertNull(this.mSnapshots, FirestoreRecyclerOptions.ERR_SNAPSHOTS_SET);
            this.mSnapshots = snapshots;
            return this;
        }

        public Builder<T> setQuery(Query query, SnapshotParser<T> parser) {
            return setQuery(query, MetadataChanges.EXCLUDE, parser);
        }

        public Builder<T> setQuery(Query query, Class<T> modelClass) {
            return setQuery(query, MetadataChanges.EXCLUDE, modelClass);
        }

        public Builder<T> setQuery(Query query, MetadataChanges changes, Class<T> modelClass) {
            return setQuery(query, changes, new ClassSnapshotParser(modelClass));
        }

        public Builder<T> setQuery(Query query, MetadataChanges changes, SnapshotParser<T> parser) {
            Preconditions.assertNull(this.mSnapshots, FirestoreRecyclerOptions.ERR_SNAPSHOTS_SET);
            this.mSnapshots = new FirestoreArray(query, changes, parser);
            return this;
        }

        public Builder<T> setLifecycleOwner(LifecycleOwner owner) {
            this.mOwner = owner;
            return this;
        }

        public FirestoreRecyclerOptions<T> build() {
            Preconditions.assertNonNull(this.mSnapshots, FirestoreRecyclerOptions.ERR_SNAPSHOTS_NULL);
            return new FirestoreRecyclerOptions<>(this.mSnapshots, this.mOwner);
        }
    }
}
