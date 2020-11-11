package kotlinx.coroutines.scheduling;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u00002\u00020\u0001B\u0007¢\u0006\u0004\b\u0002\u0010\u0003J\u001d\u0010\t\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\t\u0010\nJ\u001d\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b\u000b\u0010\nJ\u001f\u0010\r\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\r\u0010\u000eJ\u0017\u0010\u0011\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u0006H\u0000¢\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0004\b\u0012\u0010\u0010J\u000f\u0010\u0013\u001a\u0004\u0018\u00010\u0004¢\u0006\u0004\b\u0013\u0010\u0014J(\u0010\u0017\u001a\u0004\u0018\u00010\u00042\u0014\b\u0002\u0010\u0016\u001a\u000e\u0012\u0004\u0012\u00020\u0004\u0012\u0004\u0012\u00020\b0\u0015H\b¢\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u001c\u001a\u00020\u0019H\u0000¢\u0006\u0004\b\u001a\u0010\u001bJ\u0017\u0010\u001d\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0004H\u0002¢\u0006\u0004\b\u001d\u0010\u001eJ\u001d\u0010 \u001a\u00020\b2\u0006\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u0006¢\u0006\u0004\b \u0010!J'\u0010$\u001a\u00020\b2\u0006\u0010#\u001a\u00020\"2\u0006\u0010\u001f\u001a\u00020\u00002\u0006\u0010\u0007\u001a\u00020\u0006H\u0002¢\u0006\u0004\b$\u0010%R\u001e\u0010'\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00040&8\u0002@\u0002X\u0004¢\u0006\u0006\n\u0004\b'\u0010(R\u0016\u0010*\u001a\u00020\u00198@@\u0000X\u0004¢\u0006\u0006\u001a\u0004\b)\u0010\u001b¨\u0006+"}, mo33671d2 = {"Lkotlinx/coroutines/scheduling/WorkQueue;", "", "<init>", "()V", "Lkotlinx/coroutines/scheduling/Task;", "task", "Lkotlinx/coroutines/scheduling/GlobalQueue;", "globalQueue", "", "add", "(Lkotlinx/coroutines/scheduling/Task;Lkotlinx/coroutines/scheduling/GlobalQueue;)Z", "addLast", "", "addToGlobalQueue", "(Lkotlinx/coroutines/scheduling/GlobalQueue;Lkotlinx/coroutines/scheduling/Task;)V", "offloadAllWork$kotlinx_coroutines_core", "(Lkotlinx/coroutines/scheduling/GlobalQueue;)V", "offloadAllWork", "offloadWork", "poll", "()Lkotlinx/coroutines/scheduling/Task;", "Lkotlin/Function1;", "predicate", "pollExternal", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/scheduling/Task;", "", "size$kotlinx_coroutines_core", "()I", "size", "tryAddLast", "(Lkotlinx/coroutines/scheduling/Task;)Z", "victim", "trySteal", "(Lkotlinx/coroutines/scheduling/WorkQueue;Lkotlinx/coroutines/scheduling/GlobalQueue;)Z", "", "time", "tryStealLastScheduled", "(JLkotlinx/coroutines/scheduling/WorkQueue;Lkotlinx/coroutines/scheduling/GlobalQueue;)Z", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "buffer", "Ljava/util/concurrent/atomic/AtomicReferenceArray;", "getBufferSize$kotlinx_coroutines_core", "bufferSize", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: WorkQueue.kt */
public final class WorkQueue {
    static final AtomicIntegerFieldUpdater consumerIndex$FU;
    private static final AtomicReferenceFieldUpdater lastScheduledTask$FU;
    static final AtomicIntegerFieldUpdater producerIndex$FU;
    /* access modifiers changed from: private */
    public final AtomicReferenceArray<Task> buffer = new AtomicReferenceArray<>(128);
    volatile int consumerIndex = 0;
    private volatile Object lastScheduledTask = null;
    volatile int producerIndex = 0;

    static {
        Class<WorkQueue> cls = WorkQueue.class;
        lastScheduledTask$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "lastScheduledTask");
        producerIndex$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "producerIndex");
        consumerIndex$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "consumerIndex");
    }

    public final int getBufferSize$kotlinx_coroutines_core() {
        return this.producerIndex - this.consumerIndex;
    }

    public final Task poll() {
        Task task = (Task) lastScheduledTask$FU.getAndSet(this, (Object) null);
        if (task != null) {
            return task;
        }
        while (true) {
            int tailLocal$iv = this.consumerIndex;
            if (tailLocal$iv - this.producerIndex == 0) {
                return null;
            }
            int index$iv = tailLocal$iv & WorkQueueKt.MASK;
            Task element$iv = (Task) this.buffer.get(index$iv);
            if (element$iv != null) {
                Task task2 = element$iv;
                if (consumerIndex$FU.compareAndSet(this, tailLocal$iv, tailLocal$iv + 1)) {
                    return (Task) this.buffer.getAndSet(index$iv, (Object) null);
                }
            }
        }
    }

    public final boolean add(Task task, GlobalQueue globalQueue) {
        Intrinsics.checkParameterIsNotNull(task, "task");
        Intrinsics.checkParameterIsNotNull(globalQueue, "globalQueue");
        Task previous = (Task) lastScheduledTask$FU.getAndSet(this, task);
        if (previous != null) {
            return addLast(previous, globalQueue);
        }
        return true;
    }

    public final boolean addLast(Task task, GlobalQueue globalQueue) {
        Intrinsics.checkParameterIsNotNull(task, "task");
        Intrinsics.checkParameterIsNotNull(globalQueue, "globalQueue");
        boolean noOffloadingHappened = true;
        while (!tryAddLast(task)) {
            offloadWork(globalQueue);
            noOffloadingHappened = false;
        }
        return noOffloadingHappened;
    }

    public final boolean trySteal(WorkQueue victim, GlobalQueue globalQueue) {
        Task element$iv;
        WorkQueue workQueue = victim;
        GlobalQueue globalQueue2 = globalQueue;
        Intrinsics.checkParameterIsNotNull(workQueue, "victim");
        Intrinsics.checkParameterIsNotNull(globalQueue2, "globalQueue");
        long time = TasksKt.schedulerTimeSource.nanoTime();
        int bufferSize = victim.getBufferSize$kotlinx_coroutines_core();
        if (bufferSize == 0) {
            return tryStealLastScheduled(time, workQueue, globalQueue2);
        }
        boolean wasStolen = false;
        int coerceAtLeast = RangesKt.coerceAtLeast(bufferSize / 2, 1);
        int i = 0;
        while (i < coerceAtLeast) {
            int it = i;
            int i2 = false;
            WorkQueue this_$iv = victim;
            while (true) {
                int tailLocal$iv = this_$iv.consumerIndex;
                if (tailLocal$iv - this_$iv.producerIndex == 0) {
                    int i3 = it;
                    int i4 = i2;
                    element$iv = null;
                    break;
                }
                int index$iv = tailLocal$iv & WorkQueueKt.MASK;
                Task element$iv2 = (Task) this_$iv.buffer.get(index$iv);
                if (element$iv2 != null) {
                    int i5 = i2;
                    int it2 = it;
                    if (!(time - element$iv2.submissionTime >= TasksKt.WORK_STEALING_TIME_RESOLUTION_NS || victim.getBufferSize$kotlinx_coroutines_core() > TasksKt.QUEUE_SIZE_OFFLOAD_THRESHOLD)) {
                        element$iv = null;
                        break;
                    } else if (consumerIndex$FU.compareAndSet(this_$iv, tailLocal$iv, tailLocal$iv + 1)) {
                        element$iv = (Task) this_$iv.buffer.getAndSet(index$iv, (Object) null);
                        break;
                    } else {
                        WorkQueue workQueue2 = victim;
                        it = it2;
                        i2 = i5;
                    }
                } else {
                    int i6 = i2;
                    WorkQueue workQueue3 = victim;
                }
            }
            if (element$iv == null) {
                return wasStolen;
            }
            wasStolen = true;
            add(element$iv, globalQueue2);
            i++;
            WorkQueue workQueue4 = victim;
        }
        return wasStolen;
    }

    private final boolean tryStealLastScheduled(long time, WorkQueue victim, GlobalQueue globalQueue) {
        Task lastScheduled = (Task) victim.lastScheduledTask;
        if (lastScheduled == null || time - lastScheduled.submissionTime < TasksKt.WORK_STEALING_TIME_RESOLUTION_NS || !lastScheduledTask$FU.compareAndSet(victim, lastScheduled, (Object) null)) {
            return false;
        }
        add(lastScheduled, globalQueue);
        return true;
    }

    public final int size$kotlinx_coroutines_core() {
        return this.lastScheduledTask != null ? getBufferSize$kotlinx_coroutines_core() + 1 : getBufferSize$kotlinx_coroutines_core();
    }

    private final void offloadWork(GlobalQueue globalQueue) {
        Task task;
        int coerceAtLeast = RangesKt.coerceAtLeast(getBufferSize$kotlinx_coroutines_core() / 2, 1);
        int i = 0;
        while (i < coerceAtLeast) {
            int i2 = i;
            while (true) {
                int tailLocal$iv = this.consumerIndex;
                if (tailLocal$iv - this.producerIndex == 0) {
                    task = null;
                    break;
                }
                int index$iv = tailLocal$iv & WorkQueueKt.MASK;
                Task element$iv = (Task) this.buffer.get(index$iv);
                if (element$iv != null) {
                    Task task2 = element$iv;
                    if (consumerIndex$FU.compareAndSet(this, tailLocal$iv, tailLocal$iv + 1)) {
                        task = (Task) this.buffer.getAndSet(index$iv, (Object) null);
                        break;
                    }
                }
            }
            if (task != null) {
                addToGlobalQueue(globalQueue, task);
                i++;
            } else {
                return;
            }
        }
    }

    private final void addToGlobalQueue(GlobalQueue globalQueue, Task task) {
        if (!globalQueue.addLast(task)) {
            throw new IllegalStateException("GlobalQueue could not be closed yet".toString());
        }
    }

    public final void offloadAllWork$kotlinx_coroutines_core(GlobalQueue globalQueue) {
        Task task;
        Intrinsics.checkParameterIsNotNull(globalQueue, "globalQueue");
        Task task2 = (Task) lastScheduledTask$FU.getAndSet(this, (Object) null);
        if (task2 != null) {
            addToGlobalQueue(globalQueue, task2);
        }
        while (true) {
            int i = this.consumerIndex;
            if (i - this.producerIndex == 0) {
                task = null;
            } else {
                int i2 = i & WorkQueueKt.MASK;
                if (((Task) this.buffer.get(i2)) != null && consumerIndex$FU.compareAndSet(this, i, i + 1)) {
                    task = (Task) this.buffer.getAndSet(i2, (Object) null);
                }
            }
            if (task != null) {
                addToGlobalQueue(globalQueue, task);
            } else {
                return;
            }
        }
    }

    static /* synthetic */ Task pollExternal$default(WorkQueue workQueue, Function1 predicate, int i, Object obj) {
        if ((i & 1) != 0) {
            predicate = WorkQueue$pollExternal$1.INSTANCE;
        }
        while (true) {
            int tailLocal = workQueue.consumerIndex;
            if (tailLocal - workQueue.producerIndex == 0) {
                return null;
            }
            int index = tailLocal & WorkQueueKt.MASK;
            Task element = (Task) workQueue.buffer.get(index);
            if (element != null) {
                if (!((Boolean) predicate.invoke(element)).booleanValue()) {
                    return null;
                }
                if (consumerIndex$FU.compareAndSet(workQueue, tailLocal, tailLocal + 1)) {
                    return (Task) workQueue.buffer.getAndSet(index, (Object) null);
                }
            }
        }
    }

    private final Task pollExternal(Function1<? super Task, Boolean> predicate) {
        while (true) {
            int tailLocal = this.consumerIndex;
            if (tailLocal - this.producerIndex == 0) {
                return null;
            }
            int index = tailLocal & WorkQueueKt.MASK;
            Task element = (Task) this.buffer.get(index);
            if (element != null) {
                if (!predicate.invoke(element).booleanValue()) {
                    return null;
                }
                if (consumerIndex$FU.compareAndSet(this, tailLocal, tailLocal + 1)) {
                    return (Task) this.buffer.getAndSet(index, (Object) null);
                }
            }
        }
    }

    private final boolean tryAddLast(Task task) {
        if (getBufferSize$kotlinx_coroutines_core() == 127) {
            return false;
        }
        int nextIndex = this.producerIndex & WorkQueueKt.MASK;
        if (this.buffer.get(nextIndex) != null) {
            return false;
        }
        this.buffer.lazySet(nextIndex, task);
        producerIndex$FU.incrementAndGet(this);
        return true;
    }
}
