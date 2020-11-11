package kotlinx.coroutines;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.Delay;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;
import kotlinx.coroutines.internal.ThreadSafeHeap;
import kotlinx.coroutines.internal.ThreadSafeHeapNode;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000Z\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0011\b \u0018\u00002\u00020\u00012\u00020\u0002:\u00044567B\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u000f\u0010\u0006\u001a\u00020\u0005H\u0002¢\u0006\u0004\b\u0006\u0010\u0004J\u0017\u0010\t\u001a\n\u0018\u00010\u0007j\u0004\u0018\u0001`\bH\u0002¢\u0006\u0004\b\t\u0010\nJ!\u0010\u000e\u001a\u00020\u00052\u0006\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u00060\u0007j\u0002`\b¢\u0006\u0004\b\u000e\u0010\u000fJ\u0019\u0010\u0011\u001a\u00020\u00052\n\u0010\u0010\u001a\u00060\u0007j\u0002`\b¢\u0006\u0004\b\u0011\u0010\u0012J\u001b\u0010\u0014\u001a\u00020\u00132\n\u0010\u0010\u001a\u00060\u0007j\u0002`\bH\u0002¢\u0006\u0004\b\u0014\u0010\u0015J\u000f\u0010\u0017\u001a\u00020\u0016H\u0016¢\u0006\u0004\b\u0017\u0010\u0018J\u000f\u0010\u0019\u001a\u00020\u0005H\u0002¢\u0006\u0004\b\u0019\u0010\u0004J\u000f\u0010\u001a\u001a\u00020\u0005H\u0004¢\u0006\u0004\b\u001a\u0010\u0004J\u001d\u0010\u001e\u001a\u00020\u00052\u0006\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u001c¢\u0006\u0004\b\u001e\u0010\u001fJ\u001f\u0010!\u001a\u00020 2\u0006\u0010\u001b\u001a\u00020\u00162\u0006\u0010\u001d\u001a\u00020\u001cH\u0002¢\u0006\u0004\b!\u0010\"J#\u0010%\u001a\u00020$2\u0006\u0010#\u001a\u00020\u00162\n\u0010\r\u001a\u00060\u0007j\u0002`\bH\u0004¢\u0006\u0004\b%\u0010&J%\u0010)\u001a\u00020\u00052\u0006\u0010#\u001a\u00020\u00162\f\u0010(\u001a\b\u0012\u0004\u0012\u00020\u00050'H\u0016¢\u0006\u0004\b)\u0010*J\u0017\u0010+\u001a\u00020\u00132\u0006\u0010\u0010\u001a\u00020\u001cH\u0002¢\u0006\u0004\b+\u0010,J\u000f\u0010-\u001a\u00020\u0005H\u0014¢\u0006\u0004\b-\u0010\u0004R\u0016\u0010.\u001a\u00020\u00138\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\b.\u0010/R\u0016\u00100\u001a\u00020\u00138T@\u0014X\u0004¢\u0006\u0006\u001a\u0004\b0\u00101R\u0016\u00103\u001a\u00020\u00168T@\u0014X\u0004¢\u0006\u0006\u001a\u0004\b2\u0010\u0018¨\u00068"}, mo33671d2 = {"Lkotlinx/coroutines/EventLoopImplBase;", "Lkotlinx/coroutines/EventLoopImplPlatform;", "Lkotlinx/coroutines/Delay;", "<init>", "()V", "", "closeQueue", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "dequeue", "()Ljava/lang/Runnable;", "Lkotlin/coroutines/CoroutineContext;", "context", "block", "dispatch", "(Lkotlin/coroutines/CoroutineContext;Ljava/lang/Runnable;)V", "task", "enqueue", "(Ljava/lang/Runnable;)V", "", "enqueueImpl", "(Ljava/lang/Runnable;)Z", "", "processNextEvent", "()J", "rescheduleAllDelayed", "resetAll", "now", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "delayedTask", "schedule", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)V", "", "scheduleImpl", "(JLkotlinx/coroutines/EventLoopImplBase$DelayedTask;)I", "timeMillis", "Lkotlinx/coroutines/DisposableHandle;", "scheduleInvokeOnTimeout", "(JLjava/lang/Runnable;)Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/CancellableContinuation;", "continuation", "scheduleResumeAfterDelay", "(JLkotlinx/coroutines/CancellableContinuation;)V", "shouldUnpark", "(Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;)Z", "shutdown", "isCompleted", "Z", "isEmpty", "()Z", "getNextTime", "nextTime", "DelayedResumeTask", "DelayedRunnableTask", "DelayedTask", "DelayedTaskQueue", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: EventLoop.common.kt */
public abstract class EventLoopImplBase extends EventLoopImplPlatform implements Delay {
    private static final AtomicReferenceFieldUpdater _delayed$FU;
    private static final AtomicReferenceFieldUpdater _queue$FU;
    private volatile Object _delayed = null;
    private volatile Object _queue = null;
    /* access modifiers changed from: private */
    public volatile boolean isCompleted;

    static {
        Class<EventLoopImplBase> cls = EventLoopImplBase.class;
        _queue$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_queue");
        _delayed$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_delayed");
    }

    public Object delay(long time, Continuation<? super Unit> continuation) {
        return Delay.DefaultImpls.delay(this, time, continuation);
    }

    public DisposableHandle invokeOnTimeout(long timeMillis, Runnable block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        return Delay.DefaultImpls.invokeOnTimeout(this, timeMillis, block);
    }

    /* access modifiers changed from: protected */
    public boolean isEmpty() {
        if (!isUnconfinedQueueEmpty()) {
            return false;
        }
        DelayedTaskQueue delayed = (DelayedTaskQueue) this._delayed;
        if (delayed != null && !delayed.isEmpty()) {
            return false;
        }
        Object queue = this._queue;
        if (queue == null) {
            return true;
        }
        if (queue instanceof LockFreeTaskQueueCore) {
            return ((LockFreeTaskQueueCore) queue).isEmpty();
        }
        if (queue == EventLoop_commonKt.CLOSED_EMPTY) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: protected */
    public long getNextTime() {
        DelayedTask nextDelayedTask;
        if (super.getNextTime() == 0) {
            return 0;
        }
        Object queue = this._queue;
        if (queue != null) {
            if (queue instanceof LockFreeTaskQueueCore) {
                if (!((LockFreeTaskQueueCore) queue).isEmpty()) {
                    return 0;
                }
            } else if (queue == EventLoop_commonKt.CLOSED_EMPTY) {
                return Long.MAX_VALUE;
            } else {
                return 0;
            }
        }
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        if (delayedTaskQueue == null || (nextDelayedTask = (DelayedTask) delayedTaskQueue.peek()) == null) {
            return Long.MAX_VALUE;
        }
        long j = nextDelayedTask.nanoTime;
        TimeSource timeSource = TimeSourceKt.getTimeSource();
        return RangesKt.coerceAtLeast(j - (timeSource != null ? timeSource.nanoTime() : System.nanoTime()), 0);
    }

    /* access modifiers changed from: protected */
    public void shutdown() {
        ThreadLocalEventLoop.INSTANCE.resetEventLoop$kotlinx_coroutines_core();
        this.isCompleted = true;
        closeQueue();
        do {
        } while (processNextEvent() <= 0);
        rescheduleAllDelayed();
    }

    public void scheduleResumeAfterDelay(long timeMillis, CancellableContinuation<? super Unit> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "continuation");
        long timeNanos = EventLoop_commonKt.delayToNanos(timeMillis);
        if (timeNanos < 4611686018427387903L) {
            TimeSource timeSource = TimeSourceKt.getTimeSource();
            long now = timeSource != null ? timeSource.nanoTime() : System.nanoTime();
            DelayedResumeTask task = new DelayedResumeTask(this, now + timeNanos, continuation);
            CancellableContinuationKt.disposeOnCancellation(continuation, task);
            schedule(now, task);
        }
    }

    /* access modifiers changed from: protected */
    public final DisposableHandle scheduleInvokeOnTimeout(long timeMillis, Runnable block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        long timeNanos = EventLoop_commonKt.delayToNanos(timeMillis);
        if (timeNanos >= 4611686018427387903L) {
            return NonDisposableHandle.INSTANCE;
        }
        TimeSource timeSource = TimeSourceKt.getTimeSource();
        long now = timeSource != null ? timeSource.nanoTime() : System.nanoTime();
        DelayedRunnableTask task = new DelayedRunnableTask(now + timeNanos, block);
        schedule(now, task);
        return task;
    }

    /* JADX WARNING: Removed duplicated region for block: B:33:0x0066  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public long processNextEvent() {
        /*
            r14 = this;
            boolean r0 = r14.processUnconfinedEvent()
            if (r0 == 0) goto L_0x000b
            long r0 = r14.getNextTime()
            return r0
        L_0x000b:
            java.lang.Object r0 = r14._delayed
            kotlinx.coroutines.EventLoopImplBase$DelayedTaskQueue r0 = (kotlinx.coroutines.EventLoopImplBase.DelayedTaskQueue) r0
            if (r0 == 0) goto L_0x0060
            boolean r1 = r0.isEmpty()
            if (r1 != 0) goto L_0x0060
            kotlinx.coroutines.TimeSource r1 = kotlinx.coroutines.TimeSourceKt.getTimeSource()
            if (r1 == 0) goto L_0x0022
            long r1 = r1.nanoTime()
            goto L_0x0026
        L_0x0022:
            long r1 = java.lang.System.nanoTime()
        L_0x0026:
            r3 = r0
            r4 = 0
            r5 = r3
            r6 = 0
            monitor-enter(r5)
            r7 = 0
            kotlinx.coroutines.internal.ThreadSafeHeapNode r8 = r3.firstImpl()     // Catch:{ all -> 0x005d }
            r9 = 0
            if (r8 == 0) goto L_0x0056
            r10 = r8
            kotlinx.coroutines.EventLoopImplBase$DelayedTask r10 = (kotlinx.coroutines.EventLoopImplBase.DelayedTask) r10     // Catch:{ all -> 0x005d }
            r11 = 0
            boolean r12 = r10.timeToExecute(r1)     // Catch:{ all -> 0x005d }
            r13 = 0
            if (r12 == 0) goto L_0x0047
            r12 = r10
            java.lang.Runnable r12 = (java.lang.Runnable) r12     // Catch:{ all -> 0x005d }
            boolean r12 = r14.enqueueImpl(r12)     // Catch:{ all -> 0x005d }
            goto L_0x0048
        L_0x0047:
            r12 = r13
        L_0x0048:
            if (r12 == 0) goto L_0x0050
            kotlinx.coroutines.internal.ThreadSafeHeapNode r9 = r3.removeAtImpl(r13)     // Catch:{ all -> 0x005d }
            goto L_0x0051
        L_0x0050:
        L_0x0051:
            monitor-exit(r5)
            goto L_0x0057
        L_0x0056:
            monitor-exit(r5)
        L_0x0057:
            kotlinx.coroutines.EventLoopImplBase$DelayedTask r9 = (kotlinx.coroutines.EventLoopImplBase.DelayedTask) r9
            if (r9 == 0) goto L_0x005c
            goto L_0x0026
        L_0x005c:
            goto L_0x0060
        L_0x005d:
            r7 = move-exception
            monitor-exit(r5)
            throw r7
        L_0x0060:
            java.lang.Runnable r1 = r14.dequeue()
            if (r1 == 0) goto L_0x0069
            r1.run()
        L_0x0069:
            long r1 = r14.getNextTime()
            return r1
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.EventLoopImplBase.processNextEvent():long");
    }

    public final void dispatch(CoroutineContext context, Runnable block) {
        Intrinsics.checkParameterIsNotNull(context, "context");
        Intrinsics.checkParameterIsNotNull(block, "block");
        enqueue(block);
    }

    public final void enqueue(Runnable task) {
        Intrinsics.checkParameterIsNotNull(task, "task");
        if (enqueueImpl(task)) {
            unpark();
        } else {
            DefaultExecutor.INSTANCE.enqueue(task);
        }
    }

    private final boolean enqueueImpl(Runnable task) {
        while (true) {
            Object queue = this._queue;
            if (this.isCompleted) {
                return false;
            }
            if (queue == null) {
                if (_queue$FU.compareAndSet(this, (Object) null, task)) {
                    return true;
                }
            } else if (queue instanceof LockFreeTaskQueueCore) {
                if (queue != null) {
                    int addLast = ((LockFreeTaskQueueCore) queue).addLast(task);
                    if (addLast == 0) {
                        return true;
                    }
                    if (addLast == 1) {
                        _queue$FU.compareAndSet(this, queue, ((LockFreeTaskQueueCore) queue).next());
                    } else if (addLast == 2) {
                        return false;
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Queue<kotlinx.coroutines.Runnable /* = java.lang.Runnable */> /* = kotlinx.coroutines.internal.LockFreeTaskQueueCore<kotlinx.coroutines.Runnable /* = java.lang.Runnable */> */");
                }
            } else if (queue == EventLoop_commonKt.CLOSED_EMPTY) {
                return false;
            } else {
                LockFreeTaskQueueCore newQueue = new LockFreeTaskQueueCore(8, true);
                if (queue != null) {
                    newQueue.addLast((Runnable) queue);
                    newQueue.addLast(task);
                    if (_queue$FU.compareAndSet(this, queue, newQueue)) {
                        return true;
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Runnable /* = java.lang.Runnable */");
                }
            }
        }
    }

    private final Runnable dequeue() {
        while (true) {
            Object queue = this._queue;
            if (queue == null) {
                return null;
            }
            if (queue instanceof LockFreeTaskQueueCore) {
                if (queue != null) {
                    Object result = ((LockFreeTaskQueueCore) queue).removeFirstOrNull();
                    if (result != LockFreeTaskQueueCore.REMOVE_FROZEN) {
                        return (Runnable) result;
                    }
                    _queue$FU.compareAndSet(this, queue, ((LockFreeTaskQueueCore) queue).next());
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Queue<kotlinx.coroutines.Runnable /* = java.lang.Runnable */> /* = kotlinx.coroutines.internal.LockFreeTaskQueueCore<kotlinx.coroutines.Runnable /* = java.lang.Runnable */> */");
                }
            } else if (queue == EventLoop_commonKt.CLOSED_EMPTY) {
                return null;
            } else {
                if (_queue$FU.compareAndSet(this, queue, (Object) null)) {
                    if (queue != null) {
                        return (Runnable) queue;
                    }
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Runnable /* = java.lang.Runnable */");
                }
            }
        }
    }

    private final void closeQueue() {
        if (!DebugKt.getASSERTIONS_ENABLED() || this.isCompleted != 0) {
            while (true) {
                Object queue = this._queue;
                if (queue == null) {
                    if (_queue$FU.compareAndSet(this, (Object) null, EventLoop_commonKt.CLOSED_EMPTY)) {
                        return;
                    }
                } else if (queue instanceof LockFreeTaskQueueCore) {
                    ((LockFreeTaskQueueCore) queue).close();
                    return;
                } else if (queue != EventLoop_commonKt.CLOSED_EMPTY) {
                    LockFreeTaskQueueCore newQueue = new LockFreeTaskQueueCore(8, true);
                    if (queue != null) {
                        newQueue.addLast((Runnable) queue);
                        if (_queue$FU.compareAndSet(this, queue, newQueue)) {
                            return;
                        }
                    } else {
                        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.Runnable /* = java.lang.Runnable */");
                    }
                } else {
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public final void schedule(long now, DelayedTask delayedTask) {
        Intrinsics.checkParameterIsNotNull(delayedTask, "delayedTask");
        int scheduleImpl = scheduleImpl(now, delayedTask);
        if (scheduleImpl != 0) {
            if (scheduleImpl == 1) {
                reschedule(now, delayedTask);
            } else if (scheduleImpl != 2) {
                throw new IllegalStateException("unexpected result".toString());
            }
        } else if (shouldUnpark(delayedTask)) {
            unpark();
        }
    }

    private final boolean shouldUnpark(DelayedTask task) {
        DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
        return (delayedTaskQueue != null ? (DelayedTask) delayedTaskQueue.peek() : null) == task;
    }

    private final int scheduleImpl(long now, DelayedTask delayedTask) {
        if (this.isCompleted) {
            return 1;
        }
        DelayedTaskQueue delayedQueue = (DelayedTaskQueue) this._delayed;
        if (delayedQueue == null) {
            EventLoopImplBase $this$run = this;
            _delayed$FU.compareAndSet($this$run, (Object) null, new DelayedTaskQueue(now));
            Object obj = $this$run._delayed;
            if (obj == null) {
                Intrinsics.throwNpe();
            }
            delayedQueue = (DelayedTaskQueue) obj;
        }
        return delayedTask.scheduleTask(now, delayedQueue, this);
    }

    /* access modifiers changed from: protected */
    public final void resetAll() {
        this._queue = null;
        this._delayed = null;
    }

    private final void rescheduleAllDelayed() {
        DelayedTask delayedTask;
        TimeSource timeSource = TimeSourceKt.getTimeSource();
        long now = timeSource != null ? timeSource.nanoTime() : System.nanoTime();
        while (true) {
            DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) this._delayed;
            if (delayedTaskQueue != null && (delayedTask = (DelayedTask) delayedTaskQueue.removeFirstOrNull()) != null) {
                reschedule(now, delayedTask);
            } else {
                return;
            }
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\b \u0018\u00002\u00060\u0001j\u0002`\u00022\b\u0012\u0004\u0012\u00020\u00000\u00032\u00020\u00042\u00020\u0005B\r\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0011\u0010\u0018\u001a\u00020\u00132\u0006\u0010\u0019\u001a\u00020\u0000H\u0002J\u0006\u0010\u001a\u001a\u00020\u001bJ\u001e\u0010\u001c\u001a\u00020\u00132\u0006\u0010\u001d\u001a\u00020\u00072\u0006\u0010\u001e\u001a\u00020\u001f2\u0006\u0010 \u001a\u00020!J\u000e\u0010\"\u001a\u00020#2\u0006\u0010\u001d\u001a\u00020\u0007J\b\u0010$\u001a\u00020%H\u0016R\u0010\u0010\t\u001a\u0004\u0018\u00010\nX\u000e¢\u0006\u0002\n\u0000R0\u0010\r\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f2\f\u0010\u000b\u001a\b\u0012\u0002\b\u0003\u0018\u00010\f8V@VX\u000e¢\u0006\f\u001a\u0004\b\u000e\u0010\u000f\"\u0004\b\u0010\u0010\u0011R\u001a\u0010\u0012\u001a\u00020\u0013X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0014\u0010\u0015\"\u0004\b\u0016\u0010\u0017R\u0012\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006&"}, mo33671d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "", "Lkotlinx/coroutines/DisposableHandle;", "Lkotlinx/coroutines/internal/ThreadSafeHeapNode;", "nanoTime", "", "(J)V", "_heap", "", "value", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "heap", "getHeap", "()Lkotlinx/coroutines/internal/ThreadSafeHeap;", "setHeap", "(Lkotlinx/coroutines/internal/ThreadSafeHeap;)V", "index", "", "getIndex", "()I", "setIndex", "(I)V", "compareTo", "other", "dispose", "", "scheduleTask", "now", "delayed", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "eventLoop", "Lkotlinx/coroutines/EventLoopImplBase;", "timeToExecute", "", "toString", "", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: EventLoop.common.kt */
    public static abstract class DelayedTask implements Runnable, Comparable<DelayedTask>, DisposableHandle, ThreadSafeHeapNode {
        private Object _heap;
        private int index = -1;
        public long nanoTime;

        public DelayedTask(long nanoTime2) {
            this.nanoTime = nanoTime2;
        }

        public ThreadSafeHeap<?> getHeap() {
            Object obj = this._heap;
            if (!(obj instanceof ThreadSafeHeap)) {
                obj = null;
            }
            return (ThreadSafeHeap) obj;
        }

        public void setHeap(ThreadSafeHeap<?> value) {
            if (this._heap != EventLoop_commonKt.DISPOSED_TASK) {
                this._heap = value;
                return;
            }
            throw new IllegalArgumentException("Failed requirement.".toString());
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int i) {
            this.index = i;
        }

        public int compareTo(DelayedTask other) {
            Intrinsics.checkParameterIsNotNull(other, "other");
            long dTime = this.nanoTime - other.nanoTime;
            if (dTime > 0) {
                return 1;
            }
            if (dTime < 0) {
                return -1;
            }
            return 0;
        }

        public final boolean timeToExecute(long now) {
            return now - this.nanoTime >= 0;
        }

        public final synchronized int scheduleTask(long now, DelayedTaskQueue delayed, EventLoopImplBase eventLoop) {
            long j = now;
            DelayedTaskQueue delayedTaskQueue = delayed;
            synchronized (this) {
                Intrinsics.checkParameterIsNotNull(delayedTaskQueue, "delayed");
                Intrinsics.checkParameterIsNotNull(eventLoop, "eventLoop");
                if (this._heap == EventLoop_commonKt.DISPOSED_TASK) {
                    return 2;
                }
                ThreadSafeHeapNode node$iv = this;
                ThreadSafeHeap this_$iv = delayed;
                synchronized (this_$iv) {
                    try {
                        DelayedTask firstTask = (DelayedTask) this_$iv.firstImpl();
                        if (eventLoop.isCompleted) {
                            return 1;
                        }
                        if (firstTask == null) {
                            try {
                                delayedTaskQueue.timeNow = j;
                                DelayedTask delayedTask = firstTask;
                            } catch (Throwable th) {
                                th = th;
                                throw th;
                            }
                        } else {
                            long firstTime = firstTask.nanoTime;
                            DelayedTask delayedTask2 = firstTask;
                            long minTime = firstTime - j >= 0 ? j : firstTime;
                            if (minTime - delayedTaskQueue.timeNow > 0) {
                                try {
                                    delayedTaskQueue.timeNow = minTime;
                                } catch (Throwable th2) {
                                    th = th2;
                                    throw th;
                                }
                            }
                        }
                        if (this.nanoTime - delayedTaskQueue.timeNow < 0) {
                            this.nanoTime = delayedTaskQueue.timeNow;
                        }
                        this_$iv.addImpl(node$iv);
                        return 0;
                    } catch (Throwable th3) {
                        th = th3;
                        throw th;
                    }
                }
            }
        }

        public final synchronized void dispose() {
            Object heap = this._heap;
            if (heap != EventLoop_commonKt.DISPOSED_TASK) {
                DelayedTaskQueue delayedTaskQueue = (DelayedTaskQueue) (!(heap instanceof DelayedTaskQueue) ? null : heap);
                if (delayedTaskQueue != null) {
                    delayedTaskQueue.remove(this);
                }
                this._heap = EventLoop_commonKt.DISPOSED_TASK;
            }
        }

        public String toString() {
            return "Delayed[nanos=" + this.nanoTime + ']';
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\u0006H\u0016J\b\u0010\t\u001a\u00020\nH\u0016R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, mo33671d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedResumeTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/EventLoopImplBase;JLkotlinx/coroutines/CancellableContinuation;)V", "run", "toString", "", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: EventLoop.common.kt */
    private final class DelayedResumeTask extends DelayedTask {
        private final CancellableContinuation<Unit> cont;
        final /* synthetic */ EventLoopImplBase this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public DelayedResumeTask(EventLoopImplBase $outer, long nanoTime, CancellableContinuation<? super Unit> cont2) {
            super(nanoTime);
            Intrinsics.checkParameterIsNotNull(cont2, "cont");
            this.this$0 = $outer;
            this.cont = cont2;
        }

        public void run() {
            this.cont.resumeUndispatched(this.this$0, Unit.INSTANCE);
        }

        public String toString() {
            return super.toString() + this.cont.toString();
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0002\u0018\u00002\u00020\u0001B\u0019\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J\b\u0010\n\u001a\u00020\u000bH\u0016R\u0012\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\f"}, mo33671d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedRunnableTask;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "nanoTime", "", "block", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "(JLjava/lang/Runnable;)V", "run", "", "toString", "", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: EventLoop.common.kt */
    private static final class DelayedRunnableTask extends DelayedTask {
        private final Runnable block;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public DelayedRunnableTask(long nanoTime, Runnable block2) {
            super(nanoTime);
            Intrinsics.checkParameterIsNotNull(block2, "block");
            this.block = block2;
        }

        public void run() {
            this.block.run();
        }

        public String toString() {
            return super.toString() + this.block.toString();
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\b\u0000\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005R\u0012\u0010\u0003\u001a\u00020\u00048\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0006"}, mo33671d2 = {"Lkotlinx/coroutines/EventLoopImplBase$DelayedTaskQueue;", "Lkotlinx/coroutines/internal/ThreadSafeHeap;", "Lkotlinx/coroutines/EventLoopImplBase$DelayedTask;", "timeNow", "", "(J)V", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: EventLoop.common.kt */
    public static final class DelayedTaskQueue extends ThreadSafeHeap<DelayedTask> {
        public long timeNow;

        public DelayedTaskQueue(long timeNow2) {
            this.timeNow = timeNow2;
        }
    }
}
