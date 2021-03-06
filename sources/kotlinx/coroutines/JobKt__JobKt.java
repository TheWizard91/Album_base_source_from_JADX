package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.Job;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000B\n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\u001a\u0019\u0010\u0004\u001a\u00020\u00052\u000e\b\u0004\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u0007H\b\u001a\u0012\u0010\t\u001a\u00020\n2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\f\u001a\u0019\u0010\r\u001a\u00020\f2\n\b\u0002\u0010\u000b\u001a\u0004\u0018\u00010\fH\u0007¢\u0006\u0002\b\t\u001a\f\u0010\u000e\u001a\u00020\b*\u00020\u0002H\u0007\u001a\u0018\u0010\u000e\u001a\u00020\u0001*\u00020\u00022\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0007\u001a\u001c\u0010\u000e\u001a\u00020\b*\u00020\u00022\u0010\b\u0002\u0010\u000f\u001a\n\u0018\u00010\u0011j\u0004\u0018\u0001`\u0012\u001a\u001e\u0010\u000e\u001a\u00020\b*\u00020\f2\u0006\u0010\u0013\u001a\u00020\u00142\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010\u001a\u0015\u0010\u0015\u001a\u00020\b*\u00020\fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0016\u001a\f\u0010\u0017\u001a\u00020\b*\u00020\u0002H\u0007\u001a\u0018\u0010\u0017\u001a\u00020\b*\u00020\u00022\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0007\u001a\u001c\u0010\u0017\u001a\u00020\b*\u00020\u00022\u0010\b\u0002\u0010\u000f\u001a\n\u0018\u00010\u0011j\u0004\u0018\u0001`\u0012\u001a\f\u0010\u0017\u001a\u00020\b*\u00020\fH\u0007\u001a\u0018\u0010\u0017\u001a\u00020\b*\u00020\f2\n\b\u0002\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0007\u001a\u001c\u0010\u0017\u001a\u00020\b*\u00020\f2\u0010\b\u0002\u0010\u000f\u001a\n\u0018\u00010\u0011j\u0004\u0018\u0001`\u0012\u001a\u0014\u0010\u0018\u001a\u00020\u0005*\u00020\f2\u0006\u0010\u0019\u001a\u00020\u0005H\u0000\u001a\n\u0010\u001a\u001a\u00020\b*\u00020\u0002\u001a\n\u0010\u001a\u001a\u00020\b*\u00020\f\"\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00028F¢\u0006\u0006\u001a\u0004\b\u0000\u0010\u0003\u0002\u0004\n\u0002\b\u0019¨\u0006\u001b"}, mo33671d2 = {"isActive", "", "Lkotlin/coroutines/CoroutineContext;", "(Lkotlin/coroutines/CoroutineContext;)Z", "DisposableHandle", "Lkotlinx/coroutines/DisposableHandle;", "block", "Lkotlin/Function0;", "", "Job", "Lkotlinx/coroutines/CompletableJob;", "parent", "Lkotlinx/coroutines/Job;", "Job0", "cancel", "cause", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "message", "", "cancelAndJoin", "(Lkotlinx/coroutines/Job;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "cancelChildren", "disposeOnCompletion", "handle", "ensureActive", "kotlinx-coroutines-core"}, mo33672k = 5, mo33673mv = {1, 1, 15}, mo33676xs = "kotlinx/coroutines/JobKt")
/* compiled from: Job.kt */
final /* synthetic */ class JobKt__JobKt {
    public static final CompletableJob Job(Job parent) {
        return new JobImpl(parent);
    }

    public static final DisposableHandle DisposableHandle(Function0<Unit> block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        return new JobKt__JobKt$DisposableHandle$1(block);
    }

    public static final DisposableHandle disposeOnCompletion(Job $this$disposeOnCompletion, DisposableHandle handle) {
        Intrinsics.checkParameterIsNotNull($this$disposeOnCompletion, "$this$disposeOnCompletion");
        Intrinsics.checkParameterIsNotNull(handle, "handle");
        return $this$disposeOnCompletion.invokeOnCompletion(new DisposeOnCompletion($this$disposeOnCompletion, handle));
    }

    public static final Object cancelAndJoin(Job $this$cancelAndJoin, Continuation<? super Unit> $completion) {
        Job.DefaultImpls.cancel$default($this$cancelAndJoin, (CancellationException) null, 1, (Object) null);
        return $this$cancelAndJoin.join($completion);
    }

    public static /* synthetic */ void cancelChildren$default(Job job, CancellationException cancellationException, int i, Object obj) {
        if ((i & 1) != 0) {
            cancellationException = null;
        }
        JobKt.cancelChildren(job, cancellationException);
    }

    public static final void cancelChildren(Job $this$cancelChildren, CancellationException cause) {
        Intrinsics.checkParameterIsNotNull($this$cancelChildren, "$this$cancelChildren");
        for (Job it : $this$cancelChildren.getChildren()) {
            it.cancel(cause);
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public static final /* synthetic */ void cancelChildren(Job $this$cancelChildren) {
        Intrinsics.checkParameterIsNotNull($this$cancelChildren, "$this$cancelChildren");
        JobKt.cancelChildren($this$cancelChildren, (CancellationException) null);
    }

    public static /* synthetic */ void cancelChildren$default(Job job, Throwable th, int i, Object obj) {
        if ((i & 1) != 0) {
            th = null;
        }
        cancelChildren(job, th);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public static final /* synthetic */ void cancelChildren(Job $this$cancelChildren, Throwable cause) {
        Intrinsics.checkParameterIsNotNull($this$cancelChildren, "$this$cancelChildren");
        for (Job it : $this$cancelChildren.getChildren()) {
            JobSupport jobSupport = (JobSupport) (!(it instanceof JobSupport) ? null : it);
            if (jobSupport != null) {
                jobSupport.cancel(cause);
            }
        }
    }

    public static final boolean isActive(CoroutineContext $this$isActive) {
        Intrinsics.checkParameterIsNotNull($this$isActive, "$this$isActive");
        Job job = (Job) $this$isActive.get(Job.Key);
        return job != null && job.isActive();
    }

    public static /* synthetic */ void cancel$default(CoroutineContext coroutineContext, CancellationException cancellationException, int i, Object obj) {
        if ((i & 1) != 0) {
            cancellationException = null;
        }
        JobKt.cancel(coroutineContext, cancellationException);
    }

    public static final void cancel(CoroutineContext $this$cancel, CancellationException cause) {
        Intrinsics.checkParameterIsNotNull($this$cancel, "$this$cancel");
        Job job = (Job) $this$cancel.get(Job.Key);
        if (job != null) {
            job.cancel(cause);
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public static final /* synthetic */ void cancel(CoroutineContext $this$cancel) {
        Intrinsics.checkParameterIsNotNull($this$cancel, "$this$cancel");
        JobKt.cancel($this$cancel, (CancellationException) null);
    }

    public static final void ensureActive(Job $this$ensureActive) {
        Intrinsics.checkParameterIsNotNull($this$ensureActive, "$this$ensureActive");
        if (!$this$ensureActive.isActive()) {
            throw $this$ensureActive.getCancellationException();
        }
    }

    public static final void ensureActive(CoroutineContext $this$ensureActive) {
        Intrinsics.checkParameterIsNotNull($this$ensureActive, "$this$ensureActive");
        Job job = (Job) $this$ensureActive.get(Job.Key);
        if (job != null) {
            JobKt.ensureActive(job);
            return;
        }
        throw new IllegalStateException(("Context cannot be checked for liveness because it does not have a job: " + $this$ensureActive).toString());
    }

    public static final void cancel(Job $this$cancel, String message, Throwable cause) {
        Intrinsics.checkParameterIsNotNull($this$cancel, "$this$cancel");
        Intrinsics.checkParameterIsNotNull(message, "message");
        $this$cancel.cancel(ExceptionsKt.CancellationException(message, cause));
    }

    public static /* synthetic */ void cancel$default(Job job, String str, Throwable th, int i, Object obj) {
        if ((i & 2) != 0) {
            th = null;
        }
        JobKt.cancel(job, str, th);
    }

    public static /* synthetic */ boolean cancel$default(CoroutineContext coroutineContext, Throwable th, int i, Object obj) {
        if ((i & 1) != 0) {
            th = null;
        }
        return cancel(coroutineContext, th);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public static final /* synthetic */ boolean cancel(CoroutineContext $this$cancel, Throwable cause) {
        Intrinsics.checkParameterIsNotNull($this$cancel, "$this$cancel");
        CoroutineContext.Element element = $this$cancel.get(Job.Key);
        if (!(element instanceof JobSupport)) {
            element = null;
        }
        JobSupport jobSupport = (JobSupport) element;
        if (jobSupport != null) {
            return jobSupport.cancel(cause);
        }
        return false;
    }

    public static /* synthetic */ void cancelChildren$default(CoroutineContext coroutineContext, CancellationException cancellationException, int i, Object obj) {
        if ((i & 1) != 0) {
            cancellationException = null;
        }
        JobKt.cancelChildren(coroutineContext, cancellationException);
    }

    public static final void cancelChildren(CoroutineContext $this$cancelChildren, CancellationException cause) {
        Sequence<Job> $this$forEach$iv;
        Intrinsics.checkParameterIsNotNull($this$cancelChildren, "$this$cancelChildren");
        Job job = (Job) $this$cancelChildren.get(Job.Key);
        if (job != null && ($this$forEach$iv = job.getChildren()) != null) {
            for (Job it : $this$forEach$iv) {
                it.cancel(cause);
            }
        }
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public static final /* synthetic */ void cancelChildren(CoroutineContext $this$cancelChildren) {
        Intrinsics.checkParameterIsNotNull($this$cancelChildren, "$this$cancelChildren");
        JobKt.cancelChildren($this$cancelChildren, (CancellationException) null);
    }

    public static /* synthetic */ void cancelChildren$default(CoroutineContext coroutineContext, Throwable th, int i, Object obj) {
        if ((i & 1) != 0) {
            th = null;
        }
        cancelChildren(coroutineContext, th);
    }

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public static final /* synthetic */ void cancelChildren(CoroutineContext $this$cancelChildren, Throwable cause) {
        Sequence $this$forEach$iv;
        Intrinsics.checkParameterIsNotNull($this$cancelChildren, "$this$cancelChildren");
        Job job = (Job) $this$cancelChildren.get(Job.Key);
        if (job != null && ($this$forEach$iv = job.getChildren()) != null) {
            for (Job it : $this$forEach$iv) {
                JobSupport jobSupport = (JobSupport) (!(it instanceof JobSupport) ? null : it);
                if (jobSupport != null) {
                    jobSupport.cancel(cause);
                }
            }
        }
    }
}
