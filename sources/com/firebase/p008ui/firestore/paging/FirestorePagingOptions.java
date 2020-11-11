package com.firebase.p008ui.firestore.paging;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.paging.LivePagedListBuilder;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.DiffUtil;
import com.firebase.p008ui.firestore.ClassSnapshotParser;
import com.firebase.p008ui.firestore.SnapshotParser;
import com.firebase.p008ui.firestore.paging.FirestoreDataSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.Source;

/* renamed from: com.firebase.ui.firestore.paging.FirestorePagingOptions */
public final class FirestorePagingOptions<T> {
    private final LiveData<PagedList<DocumentSnapshot>> mData;
    private final DiffUtil.ItemCallback<DocumentSnapshot> mDiffCallback;
    private final LifecycleOwner mOwner;
    private final SnapshotParser<T> mParser;

    private FirestorePagingOptions(LiveData<PagedList<DocumentSnapshot>> data, SnapshotParser<T> parser, DiffUtil.ItemCallback<DocumentSnapshot> diffCallback, LifecycleOwner owner) {
        this.mData = data;
        this.mParser = parser;
        this.mDiffCallback = diffCallback;
        this.mOwner = owner;
    }

    public LiveData<PagedList<DocumentSnapshot>> getData() {
        return this.mData;
    }

    public SnapshotParser<T> getParser() {
        return this.mParser;
    }

    public DiffUtil.ItemCallback<DocumentSnapshot> getDiffCallback() {
        return this.mDiffCallback;
    }

    public LifecycleOwner getOwner() {
        return this.mOwner;
    }

    /* renamed from: com.firebase.ui.firestore.paging.FirestorePagingOptions$Builder */
    public static final class Builder<T> {
        private LiveData<PagedList<DocumentSnapshot>> mData;
        private DiffUtil.ItemCallback<DocumentSnapshot> mDiffCallback;
        private LifecycleOwner mOwner;
        private SnapshotParser<T> mParser;

        public Builder<T> setQuery(Query query, PagedList.Config config, Class<T> modelClass) {
            return setQuery(query, Source.DEFAULT, config, modelClass);
        }

        public Builder<T> setQuery(Query query, PagedList.Config config, SnapshotParser<T> parser) {
            return setQuery(query, Source.DEFAULT, config, parser);
        }

        public Builder<T> setQuery(Query query, Source source, PagedList.Config config, Class<T> modelClass) {
            return setQuery(query, source, config, new ClassSnapshotParser(modelClass));
        }

        public Builder<T> setQuery(Query query, Source source, PagedList.Config config, SnapshotParser<T> parser) {
            this.mData = new LivePagedListBuilder(new FirestoreDataSource.Factory(query, source), config).build();
            this.mParser = parser;
            return this;
        }

        public Builder<T> setDiffCallback(DiffUtil.ItemCallback<DocumentSnapshot> diffCallback) {
            this.mDiffCallback = diffCallback;
            return this;
        }

        public Builder<T> setLifecycleOwner(LifecycleOwner owner) {
            this.mOwner = owner;
            return this;
        }

        public FirestorePagingOptions<T> build() {
            if (this.mData == null || this.mParser == null) {
                throw new IllegalStateException("Must call setQuery() before calling build().");
            }
            if (this.mDiffCallback == null) {
                this.mDiffCallback = new DefaultSnapshotDiffCallback(this.mParser);
            }
            return new FirestorePagingOptions(this.mData, this.mParser, this.mDiffCallback, this.mOwner);
        }
    }
}
