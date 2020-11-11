package com.firebase.p008ui.firestore.paging;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.Source;
import java.util.List;

/* renamed from: com.firebase.ui.firestore.paging.FirestoreDataSource */
public class FirestoreDataSource extends PageKeyedDataSource<PageKey, DocumentSnapshot> {
    private static final String TAG = "FirestoreDataSource";
    private final Query mBaseQuery;
    /* access modifiers changed from: private */
    public final MutableLiveData<Exception> mException = new MutableLiveData<>();
    /* access modifiers changed from: private */
    public final MutableLiveData<LoadingState> mLoadingState = new MutableLiveData<>();
    /* access modifiers changed from: private */
    public Runnable mRetryRunnable;
    private final Source mSource;

    /* renamed from: com.firebase.ui.firestore.paging.FirestoreDataSource$Factory */
    public static class Factory extends DataSource.Factory<PageKey, DocumentSnapshot> {
        private final Query mQuery;
        private final Source mSource;

        public Factory(Query query, Source source) {
            this.mQuery = query;
            this.mSource = source;
        }

        public DataSource<PageKey, DocumentSnapshot> create() {
            return new FirestoreDataSource(this.mQuery, this.mSource);
        }
    }

    public FirestoreDataSource(Query baseQuery, Source source) {
        this.mBaseQuery = baseQuery;
        this.mSource = source;
    }

    public void loadInitial(final PageKeyedDataSource.LoadInitialParams<PageKey> params, final PageKeyedDataSource.LoadInitialCallback<PageKey, DocumentSnapshot> callback) {
        this.mLoadingState.postValue(LoadingState.LOADING_INITIAL);
        this.mBaseQuery.limit((long) params.requestedLoadSize).get(this.mSource).addOnSuccessListener(new OnLoadSuccessListener() {
            /* access modifiers changed from: protected */
            public void setResult(QuerySnapshot snapshot) {
                callback.onResult(snapshot.getDocuments(), (Object) null, FirestoreDataSource.this.getNextPageKey(snapshot));
            }
        }).addOnFailureListener(new OnLoadFailureListener() {
            /* access modifiers changed from: protected */
            public Runnable getRetryRunnable() {
                return FirestoreDataSource.this.getRetryLoadInitial(params, callback);
            }
        });
    }

    public void loadBefore(PageKeyedDataSource.LoadParams<PageKey> loadParams, PageKeyedDataSource.LoadCallback<PageKey, DocumentSnapshot> loadCallback) {
    }

    public void loadAfter(final PageKeyedDataSource.LoadParams<PageKey> params, final PageKeyedDataSource.LoadCallback<PageKey, DocumentSnapshot> callback) {
        this.mLoadingState.postValue(LoadingState.LOADING_MORE);
        ((PageKey) params.key).getPageQuery(this.mBaseQuery, params.requestedLoadSize).get(this.mSource).addOnSuccessListener(new OnLoadSuccessListener() {
            /* access modifiers changed from: protected */
            public void setResult(QuerySnapshot snapshot) {
                callback.onResult(snapshot.getDocuments(), FirestoreDataSource.this.getNextPageKey(snapshot));
            }
        }).addOnFailureListener(new OnLoadFailureListener() {
            /* access modifiers changed from: protected */
            public Runnable getRetryRunnable() {
                return FirestoreDataSource.this.getRetryLoadAfter(params, callback);
            }
        });
    }

    /* access modifiers changed from: private */
    public PageKey getNextPageKey(QuerySnapshot snapshot) {
        return new PageKey(getLast(snapshot.getDocuments()), (DocumentSnapshot) null);
    }

    public LiveData<LoadingState> getLoadingState() {
        return this.mLoadingState;
    }

    public LiveData<Exception> getLastError() {
        return this.mException;
    }

    public void retry() {
        LoadingState currentState = this.mLoadingState.getValue();
        if (currentState != LoadingState.ERROR) {
            Log.w(TAG, "retry() not valid when in state: " + currentState);
            return;
        }
        Runnable runnable = this.mRetryRunnable;
        if (runnable == null) {
            Log.w(TAG, "retry() called with no eligible retry runnable.");
        } else {
            runnable.run();
        }
    }

    private DocumentSnapshot getLast(List<DocumentSnapshot> data) {
        if (data.isEmpty()) {
            return null;
        }
        return data.get(data.size() - 1);
    }

    /* access modifiers changed from: private */
    public Runnable getRetryLoadAfter(final PageKeyedDataSource.LoadParams<PageKey> params, final PageKeyedDataSource.LoadCallback<PageKey, DocumentSnapshot> callback) {
        return new Runnable() {
            public void run() {
                FirestoreDataSource.this.loadAfter(params, callback);
            }
        };
    }

    /* access modifiers changed from: private */
    public Runnable getRetryLoadInitial(final PageKeyedDataSource.LoadInitialParams<PageKey> params, final PageKeyedDataSource.LoadInitialCallback<PageKey, DocumentSnapshot> callback) {
        return new Runnable() {
            public void run() {
                FirestoreDataSource.this.loadInitial(params, callback);
            }
        };
    }

    /* renamed from: com.firebase.ui.firestore.paging.FirestoreDataSource$OnLoadSuccessListener */
    private abstract class OnLoadSuccessListener implements OnSuccessListener<QuerySnapshot> {
        /* access modifiers changed from: protected */
        public abstract void setResult(QuerySnapshot querySnapshot);

        private OnLoadSuccessListener() {
        }

        public void onSuccess(QuerySnapshot snapshot) {
            setResult(snapshot);
            FirestoreDataSource.this.mLoadingState.postValue(LoadingState.LOADED);
            if (snapshot.getDocuments().isEmpty()) {
                FirestoreDataSource.this.mLoadingState.postValue(LoadingState.FINISHED);
            }
            Runnable unused = FirestoreDataSource.this.mRetryRunnable = null;
        }
    }

    /* renamed from: com.firebase.ui.firestore.paging.FirestoreDataSource$OnLoadFailureListener */
    private abstract class OnLoadFailureListener implements OnFailureListener {
        /* access modifiers changed from: protected */
        public abstract Runnable getRetryRunnable();

        private OnLoadFailureListener() {
        }

        public void onFailure(Exception e) {
            Log.w(FirestoreDataSource.TAG, "load:onFailure", e);
            FirestoreDataSource.this.mLoadingState.postValue(LoadingState.ERROR);
            Runnable unused = FirestoreDataSource.this.mRetryRunnable = getRetryRunnable();
            FirestoreDataSource.this.mException.postValue(e);
        }
    }
}
