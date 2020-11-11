package com.facebook.imagepipeline.producers;

import android.util.Pair;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Sets;
import com.facebook.imagepipeline.common.Priority;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.io.Closeable;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;
import javax.annotation.Nullable;

public abstract class MultiplexProducer<K, T extends Closeable> implements Producer<T> {
    /* access modifiers changed from: private */
    public final Producer<T> mInputProducer;
    /* access modifiers changed from: private */
    public final boolean mKeepCancelledFetchAsLowPriority;
    final Map<K, MultiplexProducer<K, T>.Multiplexer> mMultiplexers;
    /* access modifiers changed from: private */
    public final String mProducerName;

    /* access modifiers changed from: protected */
    public abstract T cloneOrNull(T t);

    /* access modifiers changed from: protected */
    public abstract K getKey(ProducerContext producerContext);

    protected MultiplexProducer(Producer<T> inputProducer, String producerName) {
        this(inputProducer, producerName, false);
    }

    protected MultiplexProducer(Producer<T> inputProducer, String producerName, boolean keepCancelledFetchAsLowPriority) {
        this.mInputProducer = inputProducer;
        this.mMultiplexers = new HashMap();
        this.mKeepCancelledFetchAsLowPriority = keepCancelledFetchAsLowPriority;
        this.mProducerName = producerName;
    }

    public void produceResults(Consumer<T> consumer, ProducerContext context) {
        boolean createdNewMultiplexer;
        MultiplexProducer<K, T>.Multiplexer multiplexer;
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("MultiplexProducer#produceResults");
            }
            context.getProducerListener().onProducerStart(context, this.mProducerName);
            K key = getKey(context);
            do {
                createdNewMultiplexer = false;
                synchronized (this) {
                    multiplexer = getExistingMultiplexer(key);
                    if (multiplexer == null) {
                        multiplexer = createAndPutNewMultiplexer(key);
                        createdNewMultiplexer = true;
                    }
                }
            } while (!multiplexer.addNewConsumer(consumer, context));
            if (createdNewMultiplexer) {
                multiplexer.startInputProducerIfHasAttachedConsumers();
            }
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        } catch (Throwable key2) {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            throw key2;
        }
    }

    /* access modifiers changed from: protected */
    public synchronized MultiplexProducer<K, T>.Multiplexer getExistingMultiplexer(K key) {
        return this.mMultiplexers.get(key);
    }

    private synchronized MultiplexProducer<K, T>.Multiplexer createAndPutNewMultiplexer(K key) {
        MultiplexProducer<K, T>.Multiplexer multiplexer;
        multiplexer = new Multiplexer(key);
        this.mMultiplexers.put(key, multiplexer);
        return multiplexer;
    }

    /* access modifiers changed from: protected */
    public synchronized void removeMultiplexer(K key, MultiplexProducer<K, T>.Multiplexer multiplexer) {
        if (this.mMultiplexers.get(key) == multiplexer) {
            this.mMultiplexers.remove(key);
        }
    }

    class Multiplexer {
        /* access modifiers changed from: private */
        public final CopyOnWriteArraySet<Pair<Consumer<T>, ProducerContext>> mConsumerContextPairs = Sets.newCopyOnWriteArraySet();
        @Nullable
        private MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer mForwardingConsumer;
        private final K mKey;
        @Nullable
        private T mLastIntermediateResult;
        private float mLastProgress;
        private int mLastStatus;
        /* access modifiers changed from: private */
        @Nullable
        public BaseProducerContext mMultiplexProducerContext;

        public Multiplexer(K key) {
            this.mKey = key;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x002b, code lost:
            com.facebook.imagepipeline.producers.BaseProducerContext.callOnIsPrefetchChanged(r1);
            com.facebook.imagepipeline.producers.BaseProducerContext.callOnPriorityChanged(r2);
            com.facebook.imagepipeline.producers.BaseProducerContext.callOnIsIntermediateResultExpectedChanged(r3);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x0034, code lost:
            monitor-enter(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            monitor-enter(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:16:0x0038, code lost:
            if (r4 == r8.mLastIntermediateResult) goto L_0x003c;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x003a, code lost:
            r4 = null;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x003c, code lost:
            if (r4 == null) goto L_0x0045;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x003e, code lost:
            r4 = r8.this$0.cloneOrNull(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x0045, code lost:
            monitor-exit(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x0046, code lost:
            if (r4 == null) goto L_0x0056;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:23:0x004b, code lost:
            if (r5 <= 0.0f) goto L_0x0050;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:25:?, code lost:
            r9.onProgressUpdate(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:26:0x0050, code lost:
            r9.onNewResult(r4, r6);
            closeSafely(r4);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0056, code lost:
            monitor-exit(r0);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:28:0x0057, code lost:
            addCallbacks(r0, r10);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:29:0x005b, code lost:
            return true;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public boolean addNewConsumer(com.facebook.imagepipeline.producers.Consumer<T> r9, com.facebook.imagepipeline.producers.ProducerContext r10) {
            /*
                r8 = this;
                android.util.Pair r0 = android.util.Pair.create(r9, r10)
                monitor-enter(r8)
                com.facebook.imagepipeline.producers.MultiplexProducer r1 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x0062 }
                K r2 = r8.mKey     // Catch:{ all -> 0x0062 }
                com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer r1 = r1.getExistingMultiplexer(r2)     // Catch:{ all -> 0x0062 }
                if (r1 == r8) goto L_0x0013
                r1 = 0
                monitor-exit(r8)     // Catch:{ all -> 0x0062 }
                return r1
            L_0x0013:
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r1 = r8.mConsumerContextPairs     // Catch:{ all -> 0x0062 }
                r1.add(r0)     // Catch:{ all -> 0x0062 }
                java.util.List r1 = r8.updateIsPrefetch()     // Catch:{ all -> 0x0062 }
                java.util.List r2 = r8.updatePriority()     // Catch:{ all -> 0x0062 }
                java.util.List r3 = r8.updateIsIntermediateResultExpected()     // Catch:{ all -> 0x0062 }
                T r4 = r8.mLastIntermediateResult     // Catch:{ all -> 0x0062 }
                float r5 = r8.mLastProgress     // Catch:{ all -> 0x0062 }
                int r6 = r8.mLastStatus     // Catch:{ all -> 0x0062 }
                monitor-exit(r8)     // Catch:{ all -> 0x0062 }
                com.facebook.imagepipeline.producers.BaseProducerContext.callOnIsPrefetchChanged(r1)
                com.facebook.imagepipeline.producers.BaseProducerContext.callOnPriorityChanged(r2)
                com.facebook.imagepipeline.producers.BaseProducerContext.callOnIsIntermediateResultExpectedChanged(r3)
                monitor-enter(r0)
                monitor-enter(r8)     // Catch:{ all -> 0x005f }
                T r7 = r8.mLastIntermediateResult     // Catch:{ all -> 0x005c }
                if (r4 == r7) goto L_0x003c
                r4 = 0
                goto L_0x0045
            L_0x003c:
                if (r4 == 0) goto L_0x0045
                com.facebook.imagepipeline.producers.MultiplexProducer r7 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x005c }
                java.io.Closeable r7 = r7.cloneOrNull(r4)     // Catch:{ all -> 0x005c }
                r4 = r7
            L_0x0045:
                monitor-exit(r8)     // Catch:{ all -> 0x005c }
                if (r4 == 0) goto L_0x0056
                r7 = 0
                int r7 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
                if (r7 <= 0) goto L_0x0050
                r9.onProgressUpdate(r5)     // Catch:{ all -> 0x005f }
            L_0x0050:
                r9.onNewResult(r4, r6)     // Catch:{ all -> 0x005f }
                r8.closeSafely(r4)     // Catch:{ all -> 0x005f }
            L_0x0056:
                monitor-exit(r0)     // Catch:{ all -> 0x005f }
                r8.addCallbacks(r0, r10)
                r7 = 1
                return r7
            L_0x005c:
                r7 = move-exception
                monitor-exit(r8)     // Catch:{ all -> 0x005c }
                throw r7     // Catch:{ all -> 0x005f }
            L_0x005f:
                r7 = move-exception
                monitor-exit(r0)     // Catch:{ all -> 0x005f }
                throw r7
            L_0x0062:
                r1 = move-exception
                monitor-exit(r8)     // Catch:{ all -> 0x0062 }
                throw r1
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.addNewConsumer(com.facebook.imagepipeline.producers.Consumer, com.facebook.imagepipeline.producers.ProducerContext):boolean");
        }

        private void addCallbacks(final Pair<Consumer<T>, ProducerContext> consumerContextPair, ProducerContext producerContext) {
            producerContext.addCallbacks(new BaseProducerContextCallbacks() {
                public void onCancellationRequested() {
                    boolean pairWasRemoved;
                    BaseProducerContext contextToCancel = null;
                    List<ProducerContextCallbacks> isPrefetchCallbacks = null;
                    List<ProducerContextCallbacks> priorityCallbacks = null;
                    List<ProducerContextCallbacks> isIntermediateResultExpectedCallbacks = null;
                    synchronized (Multiplexer.this) {
                        pairWasRemoved = Multiplexer.this.mConsumerContextPairs.remove(consumerContextPair);
                        if (pairWasRemoved) {
                            if (Multiplexer.this.mConsumerContextPairs.isEmpty()) {
                                contextToCancel = Multiplexer.this.mMultiplexProducerContext;
                            } else {
                                isPrefetchCallbacks = Multiplexer.this.updateIsPrefetch();
                                priorityCallbacks = Multiplexer.this.updatePriority();
                                isIntermediateResultExpectedCallbacks = Multiplexer.this.updateIsIntermediateResultExpected();
                            }
                        }
                    }
                    BaseProducerContext.callOnIsPrefetchChanged(isPrefetchCallbacks);
                    BaseProducerContext.callOnPriorityChanged(priorityCallbacks);
                    BaseProducerContext.callOnIsIntermediateResultExpectedChanged(isIntermediateResultExpectedCallbacks);
                    if (contextToCancel != null) {
                        if (!MultiplexProducer.this.mKeepCancelledFetchAsLowPriority || contextToCancel.isPrefetch()) {
                            contextToCancel.cancel();
                        } else {
                            BaseProducerContext.callOnPriorityChanged(contextToCancel.setPriorityNoCallbacks(Priority.LOW));
                        }
                    }
                    if (pairWasRemoved) {
                        ((Consumer) consumerContextPair.first).onCancellation();
                    }
                }

                public void onIsPrefetchChanged() {
                    BaseProducerContext.callOnIsPrefetchChanged(Multiplexer.this.updateIsPrefetch());
                }

                public void onIsIntermediateResultExpectedChanged() {
                    BaseProducerContext.callOnIsIntermediateResultExpectedChanged(Multiplexer.this.updateIsIntermediateResultExpected());
                }

                public void onPriorityChanged() {
                    BaseProducerContext.callOnPriorityChanged(Multiplexer.this.updatePriority());
                }
            });
        }

        /* access modifiers changed from: private */
        public void startInputProducerIfHasAttachedConsumers() {
            synchronized (this) {
                boolean z = true;
                Preconditions.checkArgument(this.mMultiplexProducerContext == null);
                if (this.mForwardingConsumer != null) {
                    z = false;
                }
                Preconditions.checkArgument(z);
                if (this.mConsumerContextPairs.isEmpty()) {
                    MultiplexProducer.this.removeMultiplexer(this.mKey, this);
                    return;
                }
                ProducerContext producerContext = (ProducerContext) this.mConsumerContextPairs.iterator().next().second;
                this.mMultiplexProducerContext = new BaseProducerContext(producerContext.getImageRequest(), producerContext.getId(), producerContext.getProducerListener(), producerContext.getCallerContext(), producerContext.getLowestPermittedRequestLevel(), computeIsPrefetch(), computeIsIntermediateResultExpected(), computePriority(), producerContext.getImagePipelineConfig());
                ForwardingConsumer forwardingConsumer = new ForwardingConsumer();
                this.mForwardingConsumer = forwardingConsumer;
                BaseProducerContext multiplexProducerContext = this.mMultiplexProducerContext;
                ForwardingConsumer forwardingConsumer2 = forwardingConsumer;
                MultiplexProducer.this.mInputProducer.produceResults(forwardingConsumer2, multiplexProducerContext);
            }
        }

        /* access modifiers changed from: private */
        @Nullable
        public synchronized List<ProducerContextCallbacks> updateIsPrefetch() {
            BaseProducerContext baseProducerContext = this.mMultiplexProducerContext;
            if (baseProducerContext == null) {
                return null;
            }
            return baseProducerContext.setIsPrefetchNoCallbacks(computeIsPrefetch());
        }

        private synchronized boolean computeIsPrefetch() {
            Iterator<Pair<Consumer<T>, ProducerContext>> it = this.mConsumerContextPairs.iterator();
            while (it.hasNext()) {
                if (!((ProducerContext) it.next().second).isPrefetch()) {
                    return false;
                }
            }
            return true;
        }

        /* access modifiers changed from: private */
        @Nullable
        public synchronized List<ProducerContextCallbacks> updateIsIntermediateResultExpected() {
            BaseProducerContext baseProducerContext = this.mMultiplexProducerContext;
            if (baseProducerContext == null) {
                return null;
            }
            return baseProducerContext.setIsIntermediateResultExpectedNoCallbacks(computeIsIntermediateResultExpected());
        }

        private synchronized boolean computeIsIntermediateResultExpected() {
            Iterator<Pair<Consumer<T>, ProducerContext>> it = this.mConsumerContextPairs.iterator();
            while (it.hasNext()) {
                if (((ProducerContext) it.next().second).isIntermediateResultExpected()) {
                    return true;
                }
            }
            return false;
        }

        /* access modifiers changed from: private */
        @Nullable
        public synchronized List<ProducerContextCallbacks> updatePriority() {
            BaseProducerContext baseProducerContext = this.mMultiplexProducerContext;
            if (baseProducerContext == null) {
                return null;
            }
            return baseProducerContext.setPriorityNoCallbacks(computePriority());
        }

        private synchronized Priority computePriority() {
            Priority priority;
            priority = Priority.LOW;
            Iterator<Pair<Consumer<T>, ProducerContext>> it = this.mConsumerContextPairs.iterator();
            while (it.hasNext()) {
                priority = Priority.getHigherPriority(priority, ((ProducerContext) it.next().second).getPriority());
            }
            return priority;
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0028, code lost:
            r2 = r0.next();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x002e, code lost:
            monitor-enter(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            ((com.facebook.imagepipeline.producers.ProducerContext) r2.second).getProducerListener().onProducerFinishWithFailure((com.facebook.imagepipeline.producers.ProducerContext) r2.second, com.facebook.imagepipeline.producers.MultiplexProducer.access$900(r6.this$0), r8, (java.util.Map<java.lang.String, java.lang.String>) null);
            ((com.facebook.imagepipeline.producers.Consumer) r2.first).onFailure(r8);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x004b, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0050, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0026, code lost:
            if (r0.hasNext() == false) goto L_0x0050;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onFailure(com.facebook.imagepipeline.producers.MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer r7, java.lang.Throwable r8) {
            /*
                r6 = this;
                monitor-enter(r6)
                com.facebook.imagepipeline.producers.MultiplexProducer<K, T>$Multiplexer.ForwardingConsumer r0 = r6.mForwardingConsumer     // Catch:{ all -> 0x0051 }
                if (r0 == r7) goto L_0x0007
                monitor-exit(r6)     // Catch:{ all -> 0x0051 }
                return
            L_0x0007:
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r0 = r6.mConsumerContextPairs     // Catch:{ all -> 0x0051 }
                java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x0051 }
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r1 = r6.mConsumerContextPairs     // Catch:{ all -> 0x0051 }
                r1.clear()     // Catch:{ all -> 0x0051 }
                com.facebook.imagepipeline.producers.MultiplexProducer r1 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x0051 }
                K r2 = r6.mKey     // Catch:{ all -> 0x0051 }
                r1.removeMultiplexer(r2, r6)     // Catch:{ all -> 0x0051 }
                T r1 = r6.mLastIntermediateResult     // Catch:{ all -> 0x0051 }
                r6.closeSafely(r1)     // Catch:{ all -> 0x0051 }
                r1 = 0
                r6.mLastIntermediateResult = r1     // Catch:{ all -> 0x0051 }
                monitor-exit(r6)     // Catch:{ all -> 0x0051 }
            L_0x0022:
                boolean r2 = r0.hasNext()
                if (r2 == 0) goto L_0x0050
                java.lang.Object r2 = r0.next()
                android.util.Pair r2 = (android.util.Pair) r2
                monitor-enter(r2)
                java.lang.Object r3 = r2.second     // Catch:{ all -> 0x004d }
                com.facebook.imagepipeline.producers.ProducerContext r3 = (com.facebook.imagepipeline.producers.ProducerContext) r3     // Catch:{ all -> 0x004d }
                com.facebook.imagepipeline.producers.ProducerListener2 r3 = r3.getProducerListener()     // Catch:{ all -> 0x004d }
                java.lang.Object r4 = r2.second     // Catch:{ all -> 0x004d }
                com.facebook.imagepipeline.producers.ProducerContext r4 = (com.facebook.imagepipeline.producers.ProducerContext) r4     // Catch:{ all -> 0x004d }
                com.facebook.imagepipeline.producers.MultiplexProducer r5 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x004d }
                java.lang.String r5 = r5.mProducerName     // Catch:{ all -> 0x004d }
                r3.onProducerFinishWithFailure(r4, r5, r8, r1)     // Catch:{ all -> 0x004d }
                java.lang.Object r3 = r2.first     // Catch:{ all -> 0x004d }
                com.facebook.imagepipeline.producers.Consumer r3 = (com.facebook.imagepipeline.producers.Consumer) r3     // Catch:{ all -> 0x004d }
                r3.onFailure(r8)     // Catch:{ all -> 0x004d }
                monitor-exit(r2)     // Catch:{ all -> 0x004d }
                goto L_0x0022
            L_0x004d:
                r1 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x004d }
                throw r1
            L_0x0050:
                return
            L_0x0051:
                r0 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x0051 }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.onFailure(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer, java.lang.Throwable):void");
        }

        /* JADX WARNING: Code restructure failed: missing block: B:12:0x0037, code lost:
            if (r1.hasNext() == false) goto L_0x0079;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:0x0039, code lost:
            r2 = r1.next();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x003f, code lost:
            monitor-enter(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:17:0x0044, code lost:
            if (com.facebook.imagepipeline.producers.BaseConsumer.isLast(r9) == false) goto L_0x006d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:18:0x0046, code lost:
            ((com.facebook.imagepipeline.producers.ProducerContext) r2.second).getProducerListener().onProducerFinishWithSuccess((com.facebook.imagepipeline.producers.ProducerContext) r2.second, com.facebook.imagepipeline.producers.MultiplexProducer.access$900(r6.this$0), (java.util.Map<java.lang.String, java.lang.String>) null);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x005d, code lost:
            if (r6.mMultiplexProducerContext == null) goto L_0x006d;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:20:0x005f, code lost:
            ((com.facebook.imagepipeline.producers.ProducerContext) r2.second).setExtra(1, r6.mMultiplexProducerContext.getExtra(1));
         */
        /* JADX WARNING: Code restructure failed: missing block: B:21:0x006d, code lost:
            ((com.facebook.imagepipeline.producers.Consumer) r2.first).onNewResult(r8, r9);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:22:0x0074, code lost:
            monitor-exit(r2);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:27:0x0079, code lost:
            return;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onNextResult(com.facebook.imagepipeline.producers.MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer r7, T r8, int r9) {
            /*
                r6 = this;
                monitor-enter(r6)
                com.facebook.imagepipeline.producers.MultiplexProducer<K, T>$Multiplexer.ForwardingConsumer r0 = r6.mForwardingConsumer     // Catch:{ all -> 0x007a }
                if (r0 == r7) goto L_0x0007
                monitor-exit(r6)     // Catch:{ all -> 0x007a }
                return
            L_0x0007:
                T r0 = r6.mLastIntermediateResult     // Catch:{ all -> 0x007a }
                r6.closeSafely(r0)     // Catch:{ all -> 0x007a }
                r0 = 0
                r6.mLastIntermediateResult = r0     // Catch:{ all -> 0x007a }
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r1 = r6.mConsumerContextPairs     // Catch:{ all -> 0x007a }
                java.util.Iterator r1 = r1.iterator()     // Catch:{ all -> 0x007a }
                boolean r2 = com.facebook.imagepipeline.producers.BaseConsumer.isNotLast(r9)     // Catch:{ all -> 0x007a }
                if (r2 == 0) goto L_0x0026
                com.facebook.imagepipeline.producers.MultiplexProducer r2 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x007a }
                java.io.Closeable r2 = r2.cloneOrNull(r8)     // Catch:{ all -> 0x007a }
                r6.mLastIntermediateResult = r2     // Catch:{ all -> 0x007a }
                r6.mLastStatus = r9     // Catch:{ all -> 0x007a }
                goto L_0x0032
            L_0x0026:
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r2 = r6.mConsumerContextPairs     // Catch:{ all -> 0x007a }
                r2.clear()     // Catch:{ all -> 0x007a }
                com.facebook.imagepipeline.producers.MultiplexProducer r2 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x007a }
                K r3 = r6.mKey     // Catch:{ all -> 0x007a }
                r2.removeMultiplexer(r3, r6)     // Catch:{ all -> 0x007a }
            L_0x0032:
                monitor-exit(r6)     // Catch:{ all -> 0x007a }
            L_0x0033:
                boolean r2 = r1.hasNext()
                if (r2 == 0) goto L_0x0079
                java.lang.Object r2 = r1.next()
                android.util.Pair r2 = (android.util.Pair) r2
                monitor-enter(r2)
                boolean r3 = com.facebook.imagepipeline.producers.BaseConsumer.isLast(r9)     // Catch:{ all -> 0x0076 }
                if (r3 == 0) goto L_0x006d
                java.lang.Object r3 = r2.second     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.ProducerContext r3 = (com.facebook.imagepipeline.producers.ProducerContext) r3     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.ProducerListener2 r3 = r3.getProducerListener()     // Catch:{ all -> 0x0076 }
                java.lang.Object r4 = r2.second     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.ProducerContext r4 = (com.facebook.imagepipeline.producers.ProducerContext) r4     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.MultiplexProducer r5 = com.facebook.imagepipeline.producers.MultiplexProducer.this     // Catch:{ all -> 0x0076 }
                java.lang.String r5 = r5.mProducerName     // Catch:{ all -> 0x0076 }
                r3.onProducerFinishWithSuccess(r4, r5, r0)     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.BaseProducerContext r3 = r6.mMultiplexProducerContext     // Catch:{ all -> 0x0076 }
                if (r3 == 0) goto L_0x006d
                java.lang.Object r3 = r2.second     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.ProducerContext r3 = (com.facebook.imagepipeline.producers.ProducerContext) r3     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.BaseProducerContext r4 = r6.mMultiplexProducerContext     // Catch:{ all -> 0x0076 }
                r5 = 1
                java.lang.String r4 = r4.getExtra(r5)     // Catch:{ all -> 0x0076 }
                r3.setExtra(r5, r4)     // Catch:{ all -> 0x0076 }
            L_0x006d:
                java.lang.Object r3 = r2.first     // Catch:{ all -> 0x0076 }
                com.facebook.imagepipeline.producers.Consumer r3 = (com.facebook.imagepipeline.producers.Consumer) r3     // Catch:{ all -> 0x0076 }
                r3.onNewResult(r8, r9)     // Catch:{ all -> 0x0076 }
                monitor-exit(r2)     // Catch:{ all -> 0x0076 }
                goto L_0x0033
            L_0x0076:
                r0 = move-exception
                monitor-exit(r2)     // Catch:{ all -> 0x0076 }
                throw r0
            L_0x0079:
                return
            L_0x007a:
                r0 = move-exception
                monitor-exit(r6)     // Catch:{ all -> 0x007a }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.onNextResult(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer, java.io.Closeable, int):void");
        }

        public void onCancelled(MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer forwardingConsumer) {
            synchronized (this) {
                if (this.mForwardingConsumer == forwardingConsumer) {
                    this.mForwardingConsumer = null;
                    this.mMultiplexProducerContext = null;
                    closeSafely(this.mLastIntermediateResult);
                    this.mLastIntermediateResult = null;
                    startInputProducerIfHasAttachedConsumers();
                }
            }
        }

        /* JADX WARNING: Code restructure failed: missing block: B:10:0x0016, code lost:
            r1 = r0.next();
         */
        /* JADX WARNING: Code restructure failed: missing block: B:11:0x001c, code lost:
            monitor-enter(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:13:?, code lost:
            ((com.facebook.imagepipeline.producers.Consumer) r1.first).onProgressUpdate(r5);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:14:0x0024, code lost:
            monitor-exit(r1);
         */
        /* JADX WARNING: Code restructure failed: missing block: B:19:0x0029, code lost:
            return;
         */
        /* JADX WARNING: Code restructure failed: missing block: B:9:0x0014, code lost:
            if (r0.hasNext() == false) goto L_0x0029;
         */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public void onProgressUpdate(com.facebook.imagepipeline.producers.MultiplexProducer<K, T>.Multiplexer.ForwardingConsumer r4, float r5) {
            /*
                r3 = this;
                monitor-enter(r3)
                com.facebook.imagepipeline.producers.MultiplexProducer<K, T>$Multiplexer.ForwardingConsumer r0 = r3.mForwardingConsumer     // Catch:{ all -> 0x002a }
                if (r0 == r4) goto L_0x0007
                monitor-exit(r3)     // Catch:{ all -> 0x002a }
                return
            L_0x0007:
                r3.mLastProgress = r5     // Catch:{ all -> 0x002a }
                java.util.concurrent.CopyOnWriteArraySet<android.util.Pair<com.facebook.imagepipeline.producers.Consumer<T>, com.facebook.imagepipeline.producers.ProducerContext>> r0 = r3.mConsumerContextPairs     // Catch:{ all -> 0x002a }
                java.util.Iterator r0 = r0.iterator()     // Catch:{ all -> 0x002a }
                monitor-exit(r3)     // Catch:{ all -> 0x002a }
            L_0x0010:
                boolean r1 = r0.hasNext()
                if (r1 == 0) goto L_0x0029
                java.lang.Object r1 = r0.next()
                android.util.Pair r1 = (android.util.Pair) r1
                monitor-enter(r1)
                java.lang.Object r2 = r1.first     // Catch:{ all -> 0x0026 }
                com.facebook.imagepipeline.producers.Consumer r2 = (com.facebook.imagepipeline.producers.Consumer) r2     // Catch:{ all -> 0x0026 }
                r2.onProgressUpdate(r5)     // Catch:{ all -> 0x0026 }
                monitor-exit(r1)     // Catch:{ all -> 0x0026 }
                goto L_0x0010
            L_0x0026:
                r2 = move-exception
                monitor-exit(r1)     // Catch:{ all -> 0x0026 }
                throw r2
            L_0x0029:
                return
            L_0x002a:
                r0 = move-exception
                monitor-exit(r3)     // Catch:{ all -> 0x002a }
                throw r0
            */
            throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.MultiplexProducer.Multiplexer.onProgressUpdate(com.facebook.imagepipeline.producers.MultiplexProducer$Multiplexer$ForwardingConsumer, float):void");
        }

        private void closeSafely(Closeable obj) {
            if (obj != null) {
                try {
                    obj.close();
                } catch (IOException ioe) {
                    throw new RuntimeException(ioe);
                }
            }
        }

        private class ForwardingConsumer extends BaseConsumer<T> {
            private ForwardingConsumer() {
            }

            /* access modifiers changed from: protected */
            public void onNewResultImpl(T newResult, int status) {
                try {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.beginSection("MultiplexProducer#onNewResult");
                    }
                    Multiplexer.this.onNextResult(this, newResult, status);
                } finally {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void onFailureImpl(Throwable t) {
                try {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.beginSection("MultiplexProducer#onFailure");
                    }
                    Multiplexer.this.onFailure(this, t);
                } finally {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void onCancellationImpl() {
                try {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.beginSection("MultiplexProducer#onCancellation");
                    }
                    Multiplexer.this.onCancelled(this);
                } finally {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                    }
                }
            }

            /* access modifiers changed from: protected */
            public void onProgressUpdateImpl(float progress) {
                try {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.beginSection("MultiplexProducer#onProgressUpdate");
                    }
                    Multiplexer.this.onProgressUpdate(this, progress);
                } finally {
                    if (FrescoSystrace.isTracing()) {
                        FrescoSystrace.endSection();
                    }
                }
            }
        }
    }
}
