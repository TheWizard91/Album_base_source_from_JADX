package kotlinx.coroutines.scheduling;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.LockFreeTaskQueue;
import kotlinx.coroutines.internal.LockFreeTaskQueueCore;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0016\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0010\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0003J\u0010\u0010\u0004\u001a\u0004\u0018\u00010\u00022\u0006\u0010\u0005\u001a\u00020\u0006¨\u0006\u0007"}, mo33671d2 = {"Lkotlinx/coroutines/scheduling/GlobalQueue;", "Lkotlinx/coroutines/internal/LockFreeTaskQueue;", "Lkotlinx/coroutines/scheduling/Task;", "()V", "removeFirstWithModeOrNull", "mode", "Lkotlinx/coroutines/scheduling/TaskMode;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Tasks.kt */
public class GlobalQueue extends LockFreeTaskQueue<Task> {
    public GlobalQueue() {
        super(false);
    }

    public final Task removeFirstWithModeOrNull(TaskMode mode) {
        Object obj;
        int $i$f$removeFirstOrNullIf;
        LockFreeTaskQueue $this$loop$iv$iv;
        Object element$iv$iv;
        Object obj2 = mode;
        Intrinsics.checkParameterIsNotNull(obj2, "mode");
        int $i$f$removeFirstOrNullIf2 = 0;
        LockFreeTaskQueue $this$loop$iv$iv2 = this;
        while (true) {
            LockFreeTaskQueueCore cur$iv = (LockFreeTaskQueueCore) $this$loop$iv$iv2._cur$internal;
            LockFreeTaskQueueCore this_$iv$iv = cur$iv;
            LockFreeTaskQueueCore $this$loop$iv$iv$iv = this_$iv$iv;
            while (true) {
                long state$iv$iv = $this$loop$iv$iv$iv._state$internal;
                if ((LockFreeTaskQueueCore.FROZEN_MASK & state$iv$iv) != 0) {
                    obj = LockFreeTaskQueueCore.REMOVE_FROZEN;
                    $i$f$removeFirstOrNullIf = $i$f$removeFirstOrNullIf2;
                    $this$loop$iv$iv = $this$loop$iv$iv2;
                    break;
                }
                long $this$withState$iv$iv$iv = state$iv$iv;
                LockFreeTaskQueueCore.Companion companion = LockFreeTaskQueueCore.Companion;
                int head$iv$iv$iv = (int) (($this$withState$iv$iv$iv & LockFreeTaskQueueCore.HEAD_MASK) >> 0);
                int tail$iv$iv$iv = (int) (($this$withState$iv$iv$iv & LockFreeTaskQueueCore.TAIL_MASK) >> 30);
                int head$iv$iv = head$iv$iv$iv;
                $i$f$removeFirstOrNullIf = $i$f$removeFirstOrNullIf2;
                if ((tail$iv$iv$iv & this_$iv$iv.mask) == (head$iv$iv & this_$iv$iv.mask)) {
                    $this$loop$iv$iv = $this$loop$iv$iv2;
                    obj = null;
                    break;
                }
                Object element$iv$iv2 = this_$iv$iv.array$internal.get(this_$iv$iv.mask & head$iv$iv);
                if (element$iv$iv2 != null) {
                    if (!(element$iv$iv2 instanceof LockFreeTaskQueueCore.Placeholder)) {
                        element$iv$iv = element$iv$iv2;
                        if (((Task) element$iv$iv2).getMode() == obj2) {
                            int newHead$iv$iv = (head$iv$iv + 1) & LockFreeTaskQueueCore.MAX_CAPACITY_MASK;
                            int i = head$iv$iv$iv;
                            $this$loop$iv$iv = $this$loop$iv$iv2;
                            int i2 = tail$iv$iv$iv;
                            int head$iv$iv$iv2 = head$iv$iv;
                            long j = state$iv$iv;
                            if (!LockFreeTaskQueueCore._state$FU$internal.compareAndSet(this_$iv$iv, state$iv$iv, LockFreeTaskQueueCore.Companion.updateHead(state$iv$iv, newHead$iv$iv))) {
                                if (this_$iv$iv.singleConsumer) {
                                    LockFreeTaskQueueCore cur$iv$iv = this_$iv$iv;
                                    while (true) {
                                        LockFreeTaskQueueCore access$removeSlowPath = cur$iv$iv.removeSlowPath(head$iv$iv$iv2, newHead$iv$iv);
                                        if (access$removeSlowPath == null) {
                                            break;
                                        }
                                        cur$iv$iv = access$removeSlowPath;
                                    }
                                }
                            } else {
                                this_$iv$iv.array$internal.set(this_$iv$iv.mask & head$iv$iv$iv2, (Object) null);
                                break;
                            }
                        } else {
                            $this$loop$iv$iv = $this$loop$iv$iv2;
                            obj = null;
                            break;
                        }
                    } else {
                        $this$loop$iv$iv = $this$loop$iv$iv2;
                        obj = null;
                        break;
                    }
                } else if (this_$iv$iv.singleConsumer) {
                    $this$loop$iv$iv = $this$loop$iv$iv2;
                    obj = null;
                    break;
                } else {
                    $this$loop$iv$iv = $this$loop$iv$iv2;
                }
                obj2 = mode;
                $i$f$removeFirstOrNullIf2 = $i$f$removeFirstOrNullIf;
                $this$loop$iv$iv2 = $this$loop$iv$iv;
            }
            obj = element$iv$iv;
            Object result$iv = obj;
            if (result$iv != LockFreeTaskQueueCore.REMOVE_FROZEN) {
                return (Task) result$iv;
            }
            LockFreeTaskQueue._cur$FU$internal.compareAndSet(this, cur$iv, cur$iv.next());
            obj2 = mode;
            $i$f$removeFirstOrNullIf2 = $i$f$removeFirstOrNullIf;
            $this$loop$iv$iv2 = $this$loop$iv$iv;
        }
    }
}
