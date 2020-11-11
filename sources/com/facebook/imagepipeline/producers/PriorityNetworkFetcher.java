package com.facebook.imagepipeline.producers;

import com.facebook.common.logging.FLog;
import com.facebook.common.time.MonotonicClock;
import com.facebook.common.time.RealtimeSinceBootClock;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.producers.FetchState;
import com.facebook.imagepipeline.producers.NetworkFetcher;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;

public class PriorityNetworkFetcher<FETCH_STATE extends FetchState> implements NetworkFetcher<PriorityFetchState<FETCH_STATE>> {
    public static final String TAG = PriorityNetworkFetcher.class.getSimpleName();
    private final MonotonicClock mClock;
    private final HashSet<PriorityFetchState<FETCH_STATE>> mCurrentlyFetching;
    private final NetworkFetcher<FETCH_STATE> mDelegate;
    private final LinkedList<PriorityFetchState<FETCH_STATE>> mHiPriQueue;
    private final boolean mIsHiPriFifo;
    private final Object mLock;
    private final LinkedList<PriorityFetchState<FETCH_STATE>> mLowPriQueue;
    private final int mMaxOutstandingHiPri;
    private final int mMaxOutstandingLowPri;

    public PriorityNetworkFetcher(NetworkFetcher<FETCH_STATE> delegate, boolean isHiPriFifo, int maxOutstandingHiPri, int maxOutstandingLowPri) {
        this(delegate, isHiPriFifo, maxOutstandingHiPri, maxOutstandingLowPri, RealtimeSinceBootClock.get());
    }

    public PriorityNetworkFetcher(NetworkFetcher<FETCH_STATE> delegate, boolean isHiPriFifo, int maxOutstandingHiPri, int maxOutstandingLowPri, MonotonicClock clock) {
        this.mLock = new Object();
        this.mHiPriQueue = new LinkedList<>();
        this.mLowPriQueue = new LinkedList<>();
        this.mCurrentlyFetching = new HashSet<>();
        this.mDelegate = delegate;
        this.mIsHiPriFifo = isHiPriFifo;
        this.mMaxOutstandingHiPri = maxOutstandingHiPri;
        this.mMaxOutstandingLowPri = maxOutstandingLowPri;
        if (maxOutstandingHiPri > maxOutstandingLowPri) {
            this.mClock = clock;
            return;
        }
        throw new IllegalArgumentException("maxOutstandingHiPri should be > maxOutstandingLowPri");
    }

    public void fetch(final PriorityFetchState<FETCH_STATE> fetchState, final NetworkFetcher.Callback callback) {
        fetchState.getContext().addCallbacks(new BaseProducerContextCallbacks() {
            public void onCancellationRequested() {
                PriorityNetworkFetcher.this.removeFromQueue(fetchState, "CANCEL");
                callback.onCancellation();
            }

            public void onPriorityChanged() {
                PriorityNetworkFetcher priorityNetworkFetcher = PriorityNetworkFetcher.this;
                PriorityFetchState priorityFetchState = fetchState;
                priorityNetworkFetcher.changePriority(priorityFetchState, priorityFetchState.getContext().getPriority() == Priority.HIGH);
            }
        });
        synchronized (this.mLock) {
            if (this.mCurrentlyFetching.contains(fetchState)) {
                FLog.m60e(TAG, "fetch state was enqueued twice: " + fetchState);
                return;
            }
            boolean isHiPri = fetchState.getContext().getPriority() == Priority.HIGH;
            FLog.m90v(TAG, "enqueue: %s %s", (Object) isHiPri ? "HI-PRI" : "LOW-PRI", (Object) fetchState.getUri());
            fetchState.callback = callback;
            putInQueue(fetchState, isHiPri);
            dequeueIfAvailableSlots();
        }
    }

    public void onFetchCompletion(PriorityFetchState<FETCH_STATE> fetchState, int byteSize) {
        removeFromQueue(fetchState, "SUCCESS");
        this.mDelegate.onFetchCompletion(fetchState.delegatedState, byteSize);
    }

    /* access modifiers changed from: private */
    public void removeFromQueue(PriorityFetchState<FETCH_STATE> fetchState, String reasonForLogging) {
        synchronized (this.mLock) {
            FLog.m90v(TAG, "remove: %s %s", (Object) reasonForLogging, (Object) fetchState.getUri());
            this.mCurrentlyFetching.remove(fetchState);
            if (!this.mHiPriQueue.remove(fetchState)) {
                this.mLowPriQueue.remove(fetchState);
            }
        }
        dequeueIfAvailableSlots();
    }

    private void dequeueIfAvailableSlots() {
        PriorityFetchState<FETCH_STATE> toFetch = null;
        synchronized (this.mLock) {
            int outstandingRequests = this.mCurrentlyFetching.size();
            if (outstandingRequests < this.mMaxOutstandingHiPri) {
                toFetch = this.mHiPriQueue.pollFirst();
            }
            if (toFetch == null && outstandingRequests < this.mMaxOutstandingLowPri) {
                toFetch = this.mLowPriQueue.pollFirst();
            }
            if (toFetch != null) {
                toFetch.dequeuedTimestamp = this.mClock.now();
                this.mCurrentlyFetching.add(toFetch);
                FLog.m92v(TAG, "fetching: %s (concurrent: %s hi-pri queue: %s low-pri queue: %s)", (Object) toFetch.getUri(), (Object) Integer.valueOf(outstandingRequests), (Object) Integer.valueOf(this.mHiPriQueue.size()), (Object) Integer.valueOf(this.mLowPriQueue.size()));
                delegateFetch(toFetch);
            }
        }
    }

    private void delegateFetch(final PriorityFetchState<FETCH_STATE> fetchState) {
        try {
            this.mDelegate.fetch(fetchState.delegatedState, new NetworkFetcher.Callback() {
                public void onResponse(InputStream response, int responseLength) throws IOException {
                    fetchState.callback.onResponse(response, responseLength);
                }

                public void onFailure(Throwable throwable) {
                    PriorityNetworkFetcher.this.removeFromQueue(fetchState, "FAIL");
                    fetchState.callback.onFailure(throwable);
                }

                public void onCancellation() {
                    PriorityNetworkFetcher.this.removeFromQueue(fetchState, "CANCEL");
                    fetchState.callback.onCancellation();
                }
            });
        } catch (Exception e) {
            removeFromQueue(fetchState, "FAIL");
        }
    }

    /* access modifiers changed from: private */
    public void changePriority(PriorityFetchState<FETCH_STATE> fetchState, boolean isNewHiPri) {
        LinkedList<PriorityFetchState<FETCH_STATE>> linkedList;
        synchronized (this.mLock) {
            if (isNewHiPri) {
                linkedList = this.mLowPriQueue;
            } else {
                linkedList = this.mHiPriQueue;
            }
            if (linkedList.remove(fetchState)) {
                FLog.m90v(TAG, "change-pri: %s %s", (Object) isNewHiPri ? "HIPRI" : "LOWPRI", (Object) fetchState.getUri());
                putInQueue(fetchState, isNewHiPri);
                dequeueIfAvailableSlots();
            }
        }
    }

    private void putInQueue(PriorityFetchState<FETCH_STATE> entry, boolean isHiPri) {
        if (!isHiPri) {
            this.mLowPriQueue.addLast(entry);
        } else if (this.mIsHiPriFifo) {
            this.mHiPriQueue.addLast(entry);
        } else {
            this.mHiPriQueue.addFirst(entry);
        }
    }

    /* access modifiers changed from: package-private */
    public List<PriorityFetchState<FETCH_STATE>> getHiPriQueue() {
        return this.mHiPriQueue;
    }

    /* access modifiers changed from: package-private */
    public List<PriorityFetchState<FETCH_STATE>> getLowPriQueue() {
        return this.mLowPriQueue;
    }

    /* access modifiers changed from: package-private */
    public HashSet<PriorityFetchState<FETCH_STATE>> getCurrentlyFetching() {
        return this.mCurrentlyFetching;
    }

    public static class PriorityFetchState<FETCH_STATE extends FetchState> extends FetchState {
        NetworkFetcher.Callback callback;
        public final FETCH_STATE delegatedState;
        long dequeuedTimestamp;
        final long enqueuedTimestamp;
        final int hiPriCountWhenCreated;
        final int lowPriCountWhenCreated;

        private PriorityFetchState(Consumer<EncodedImage> consumer, ProducerContext producerContext, FETCH_STATE delegatedState2, long enqueuedTimestamp2, int hiPriCountWhenCreated2, int lowPriCountWhenCreated2) {
            super(consumer, producerContext);
            this.delegatedState = delegatedState2;
            this.enqueuedTimestamp = enqueuedTimestamp2;
            this.hiPriCountWhenCreated = hiPriCountWhenCreated2;
            this.lowPriCountWhenCreated = lowPriCountWhenCreated2;
        }
    }

    public PriorityFetchState<FETCH_STATE> createFetchState(Consumer<EncodedImage> consumer, ProducerContext producerContext) {
        return new PriorityFetchState(consumer, producerContext, this.mDelegate.createFetchState(consumer, producerContext), this.mClock.now(), this.mHiPriQueue.size(), this.mLowPriQueue.size());
    }

    public boolean shouldPropagate(PriorityFetchState<FETCH_STATE> fetchState) {
        return this.mDelegate.shouldPropagate(fetchState.delegatedState);
    }

    @Nullable
    public Map<String, String> getExtraMap(PriorityFetchState<FETCH_STATE> fetchState, int byteSize) {
        HashMap<String, String> extras;
        Map<String, String> delegateExtras = this.mDelegate.getExtraMap(fetchState.delegatedState, byteSize);
        if (delegateExtras == null) {
            extras = new HashMap<>();
        }
        extras.put("pri_queue_time", "" + (fetchState.dequeuedTimestamp - fetchState.enqueuedTimestamp));
        extras.put("hipri_queue_size", "" + fetchState.hiPriCountWhenCreated);
        extras.put("lowpri_queue_size", "" + fetchState.lowPriCountWhenCreated);
        return extras;
    }
}
