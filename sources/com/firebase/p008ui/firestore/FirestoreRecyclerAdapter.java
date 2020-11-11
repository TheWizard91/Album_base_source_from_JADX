package com.firebase.p008ui.firestore;

import android.util.Log;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.OnLifecycleEvent;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.firebase.p008ui.common.ChangeEventType;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestoreException;

/* renamed from: com.firebase.ui.firestore.FirestoreRecyclerAdapter */
public abstract class FirestoreRecyclerAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> implements ChangeEventListener, LifecycleObserver {
    private static final String TAG = "FirestoreRecycler";
    private FirestoreRecyclerOptions<T> mOptions;
    private ObservableSnapshotArray<T> mSnapshots;

    /* access modifiers changed from: protected */
    public abstract void onBindViewHolder(VH vh, int i, T t);

    public FirestoreRecyclerAdapter(FirestoreRecyclerOptions<T> options) {
        this.mOptions = options;
        this.mSnapshots = options.getSnapshots();
        if (this.mOptions.getOwner() != null) {
            this.mOptions.getOwner().getLifecycle().addObserver(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    public void startListening() {
        if (!this.mSnapshots.isListening(this)) {
            this.mSnapshots.addChangeEventListener(this);
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    public void stopListening() {
        this.mSnapshots.removeChangeEventListener(this);
        notifyDataSetChanged();
    }

    /* access modifiers changed from: package-private */
    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    public void cleanup(LifecycleOwner source) {
        source.getLifecycle().removeObserver(this);
    }

    public ObservableSnapshotArray<T> getSnapshots() {
        return this.mSnapshots;
    }

    public T getItem(int position) {
        return this.mSnapshots.get(position);
    }

    public int getItemCount() {
        if (this.mSnapshots.isListening(this)) {
            return this.mSnapshots.size();
        }
        return 0;
    }

    public void updateOptions(FirestoreRecyclerOptions<T> options) {
        boolean wasListening = this.mSnapshots.isListening(this);
        if (this.mOptions.getOwner() != null) {
            this.mOptions.getOwner().getLifecycle().removeObserver(this);
        }
        this.mSnapshots.clear();
        stopListening();
        this.mOptions = options;
        this.mSnapshots = options.getSnapshots();
        if (options.getOwner() != null) {
            options.getOwner().getLifecycle().addObserver(this);
        }
        if (wasListening) {
            startListening();
        }
    }

    /* renamed from: com.firebase.ui.firestore.FirestoreRecyclerAdapter$1 */
    static /* synthetic */ class C11351 {
        static final /* synthetic */ int[] $SwitchMap$com$firebase$ui$common$ChangeEventType;

        static {
            int[] iArr = new int[ChangeEventType.values().length];
            $SwitchMap$com$firebase$ui$common$ChangeEventType = iArr;
            try {
                iArr[ChangeEventType.ADDED.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$firebase$ui$common$ChangeEventType[ChangeEventType.CHANGED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$firebase$ui$common$ChangeEventType[ChangeEventType.REMOVED.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$firebase$ui$common$ChangeEventType[ChangeEventType.MOVED.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public void onChildChanged(ChangeEventType type, DocumentSnapshot snapshot, int newIndex, int oldIndex) {
        int i = C11351.$SwitchMap$com$firebase$ui$common$ChangeEventType[type.ordinal()];
        if (i == 1) {
            notifyItemInserted(newIndex);
        } else if (i == 2) {
            notifyItemChanged(newIndex);
        } else if (i == 3) {
            notifyItemRemoved(oldIndex);
        } else if (i == 4) {
            notifyItemMoved(oldIndex, newIndex);
        } else {
            throw new IllegalStateException("Incomplete case statement");
        }
    }

    public void onDataChanged() {
    }

    public void onError(FirebaseFirestoreException e) {
        Log.w(TAG, "onError", e);
    }

    public void onBindViewHolder(VH holder, int position) {
        onBindViewHolder(holder, position, getItem(position));
    }
}
