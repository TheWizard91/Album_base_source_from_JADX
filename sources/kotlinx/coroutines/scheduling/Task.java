package kotlinx.coroutines.scheduling;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\b \u0018\u00002\u00060\u0001j\u0002`\u0002B\u0007\b\u0016¢\u0006\u0002\u0010\u0003B\u0015\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bR\u0011\u0010\t\u001a\u00020\n8F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\fR\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u0006\u001a\u00020\u00078\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\r"}, mo33671d2 = {"Lkotlinx/coroutines/scheduling/Task;", "Ljava/lang/Runnable;", "Lkotlinx/coroutines/Runnable;", "()V", "submissionTime", "", "taskContext", "Lkotlinx/coroutines/scheduling/TaskContext;", "(JLkotlinx/coroutines/scheduling/TaskContext;)V", "mode", "Lkotlinx/coroutines/scheduling/TaskMode;", "getMode", "()Lkotlinx/coroutines/scheduling/TaskMode;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Tasks.kt */
public abstract class Task implements Runnable {
    public long submissionTime;
    public TaskContext taskContext;

    public Task(long submissionTime2, TaskContext taskContext2) {
        Intrinsics.checkParameterIsNotNull(taskContext2, "taskContext");
        this.submissionTime = submissionTime2;
        this.taskContext = taskContext2;
    }

    public Task() {
        this(0, NonBlockingContext.INSTANCE);
    }

    public final TaskMode getMode() {
        return this.taskContext.getTaskMode();
    }
}