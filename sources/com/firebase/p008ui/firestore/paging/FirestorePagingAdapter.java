package com.firebase.p008ui.firestore.paging;

import android.util.Log;
import androidx.arch.core.util.Function;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.lifecycle.Transformations;
import androidx.paging.PagedList;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.firebase.p008ui.firestore.SnapshotParser;
import com.google.firebase.firestore.DocumentSnapshot;

/* renamed from: com.firebase.ui.firestore.paging.FirestorePagingAdapter */
public abstract class FirestorePagingAdapter<T, VH extends RecyclerView.ViewHolder> extends PagedListAdapter<DocumentSnapshot, VH> implements LifecycleObserver {
    private static final String TAG = "FirestorePagingAdapter";
    private final Observer<PagedList<DocumentSnapshot>> mDataObserver = new Observer<PagedList<DocumentSnapshot>>() {
        public void onChanged(PagedList<DocumentSnapshot> snapshots) {
            if (snapshots != null) {
                FirestorePagingAdapter.this.submitList(snapshots);
            }
        }
    };
    private LiveData<FirestoreDataSource> mDataSource;
    private final Observer<FirestoreDataSource> mDataSourceObserver = new Observer<FirestoreDataSource>() {
        public void onChanged(FirestoreDataSource source) {
        }
    };
    private final Observer<Exception> mErrorObserver = new Observer<Exception>() {
        public void onChanged(Exception e) {
            FirestorePagingAdapter.this.onError(e);
        }
    };
    private LiveData<Exception> mException;
    private LiveData<LoadingState> mLoadingState;
    private FirestorePagingOptions<T> mOptions;
    private SnapshotParser<T> mParser;
    private LiveData<PagedList<DocumentSnapshot>> mSnapshots;
    private final Observer<LoadingState> mStateObserver = new Observer<LoadingState>() {
        public void onChanged(LoadingState state) {
            if (state != null) {
                FirestorePagingAdapter.this.onLoadingStateChanged(state);
            }
        }
    };

    /* access modifiers changed from: protected */
    public abstract void onBindViewHolder(VH vh, int i, T t);

    public FirestorePagingAdapter(FirestorePagingOptions<T> options) {
        super(options.getDiffCallback());
        this.mOptions = options;
        init();
    }

    private void init() {
        LiveData<PagedList<DocumentSnapshot>> data = this.mOptions.getData();
        this.mSnapshots = data;
        this.mLoadingState = Transformations.switchMap(data, new Function<PagedList<DocumentSnapshot>, LiveData<LoadingState>>() {
            public LiveData<LoadingState> apply(PagedList<DocumentSnapshot> input) {
                return input.getDataSource().getLoadingState();
            }
        });
        this.mDataSource = Transformations.map(this.mSnapshots, new Function<PagedList<DocumentSnapshot>, FirestoreDataSource>() {
            public FirestoreDataSource apply(PagedList<DocumentSnapshot> input) {
                return input.getDataSource();
            }
        });
        this.mException = Transformations.switchMap(this.mSnapshots, new Function<PagedList<DocumentSnapshot>, LiveData<Exception>>() {
            public LiveData<Exception> apply(PagedList<DocumentSnapshot> input) {
                return input.getDataSource().getLastError();
            }
        });
        this.mParser = this.mOptions.getParser();
        if (this.mOptions.getOwner() != null) {
            this.mOptions.getOwner().getLifecycle().addObserver(this);
        }
    }

    public void retry() {
        FirestoreDataSource source = this.mDataSource.getValue();
        if (source == null) {
            Log.w(TAG, "Called retry() when FirestoreDataSource is null!");
        } else {
            source.retry();
        }
    }

    public void refresh() {
        FirestoreDataSource mFirebaseDataSource = this.mDataSource.getValue();
        if (mFirebaseDataSource == null) {
            Log.w(TAG, "Called refresh() when FirestoreDataSource is null!");
        } else {
            mFirebaseDataSource.invalidate();
        }
    }

    public void updateOptions(FirestorePagingOptions<T> options) {
        this.mOptions = options;
        boolean hasObservers = this.mSnapshots.hasObservers();
        if (this.mOptions.getOwner() != null) {
            this.mOptions.getOwner().getLifecycle().removeObserver(this);
        }
        stopListening();
        init();
        if (hasObservers) {
            startListening();
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void startListening() {
        this.mSnapshots.observeForever(this.mDataObserver);
        this.mLoadingState.observeForever(this.mStateObserver);
        this.mDataSource.observeForever(this.mDataSourceObserver);
        this.mException.observeForever(this.mErrorObserver);
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopListening() {
        this.mSnapshots.removeObserver(this.mDataObserver);
        this.mLoadingState.removeObserver(this.mStateObserver);
        this.mDataSource.removeObserver(this.mDataSourceObserver);
        this.mException.removeObserver(this.mErrorObserver);
    }

    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, position, this.mParser.parseSnapshot((DocumentSnapshot) getItem(position)));
    }

    /* access modifiers changed from: protected */
    public void onLoadingStateChanged(LoadingState state) {
    }

    /* access modifiers changed from: protected */
    public void onError(Exception e) {
        Log.w(TAG, "onError", e);
    }
}
