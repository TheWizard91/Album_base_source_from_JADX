package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.ThreadContextKt;
import kotlinx.coroutines.scheduling.Task;
import kotlinx.coroutines.scheduling.TaskContext;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u000e\b \u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00060\u0002j\u0002`\u0003B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u001f\u0010\u000b\u001a\u00020\f2\b\u0010\r\u001a\u0004\u0018\u00010\u000e2\u0006\u0010\u000f\u001a\u00020\u0010H\u0010¢\u0006\u0002\b\u0011J\u0019\u0010\u0012\u001a\u0004\u0018\u00010\u00102\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0000¢\u0006\u0002\b\u0013J\u001f\u0010\u0014\u001a\u0002H\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010\r\u001a\u0004\u0018\u00010\u000eH\u0010¢\u0006\u0004\b\u0015\u0010\u0016J!\u0010\u0017\u001a\u00020\f2\b\u0010\u0018\u001a\u0004\u0018\u00010\u00102\b\u0010\u0019\u001a\u0004\u0018\u00010\u0010H\u0000¢\u0006\u0002\b\u001aJ\u0006\u0010\u001b\u001a\u00020\fJ\u000f\u0010\u001c\u001a\u0004\u0018\u00010\u000eH ¢\u0006\u0002\b\u001dR\u0018\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\bX \u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\nR\u0012\u0010\u0004\u001a\u00020\u00058\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u001e"}, mo33671d2 = {"Lkotlinx/coroutines/DispatchedTask;", "T", "Lkotlinx/coroutines/scheduling/Task;", "Lkotlinx/coroutines/SchedulerTask;", "resumeMode", "", "(I)V", "delegate", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "cancelResult", "", "state", "", "cause", "", "cancelResult$kotlinx_coroutines_core", "getExceptionalResult", "getExceptionalResult$kotlinx_coroutines_core", "getSuccessfulResult", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "handleFatalException", "exception", "finallyException", "handleFatalException$kotlinx_coroutines_core", "run", "takeState", "takeState$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Dispatched.kt */
public abstract class DispatchedTask<T> extends Task {
    public int resumeMode;

    public abstract Continuation<T> getDelegate$kotlinx_coroutines_core();

    public abstract Object takeState$kotlinx_coroutines_core();

    public DispatchedTask(int resumeMode2) {
        this.resumeMode = resumeMode2;
    }

    public void cancelResult$kotlinx_coroutines_core(Object state, Throwable cause) {
        Intrinsics.checkParameterIsNotNull(cause, "cause");
    }

    public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object state) {
        return state;
    }

    public final Throwable getExceptionalResult$kotlinx_coroutines_core(Object state) {
        CompletedExceptionally completedExceptionally = (CompletedExceptionally) (!(state instanceof CompletedExceptionally) ? null : state);
        if (completedExceptionally != null) {
            return completedExceptionally.cause;
        }
        return null;
    }

    public final void run() {
        Object result;
        CoroutineContext context;
        Object oldValue$iv;
        TaskContext taskContext = this.taskContext;
        Job job = null;
        Throwable fatalException = null;
        try {
            Continuation delegate$kotlinx_coroutines_core = getDelegate$kotlinx_coroutines_core();
            if (delegate$kotlinx_coroutines_core != null) {
                DispatchedContinuation delegate = (DispatchedContinuation) delegate$kotlinx_coroutines_core;
                Continuation continuation = delegate.continuation;
                context = continuation.getContext();
                Object state = takeState$kotlinx_coroutines_core();
                oldValue$iv = ThreadContextKt.updateThreadContext(context, delegate.countOrElement);
                Throwable exception = getExceptionalResult$kotlinx_coroutines_core(state);
                if (ResumeModeKt.isCancellableMode(this.resumeMode)) {
                    job = (Job) context.get(Job.Key);
                }
                if (exception != null || job == null || job.isActive()) {
                    if (exception != null) {
                        Continuation $this$resumeWithStackTrace$iv = continuation;
                        Result.Companion companion = Result.Companion;
                        $this$resumeWithStackTrace$iv.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(StackTraceRecoveryKt.recoverStackTrace(exception, $this$resumeWithStackTrace$iv))));
                    } else {
                        Object successfulResult$kotlinx_coroutines_core = getSuccessfulResult$kotlinx_coroutines_core(state);
                        Result.Companion companion2 = Result.Companion;
                        continuation.resumeWith(Result.m1289constructorimpl(successfulResult$kotlinx_coroutines_core));
                    }
                } else {
                    CancellationException cause = job.getCancellationException();
                    cancelResult$kotlinx_coroutines_core(state, cause);
                    Continuation $this$resumeWithStackTrace$iv2 = continuation;
                    Result.Companion companion3 = Result.Companion;
                    Job job2 = job;
                    $this$resumeWithStackTrace$iv2.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(StackTraceRecoveryKt.recoverStackTrace(cause, $this$resumeWithStackTrace$iv2))));
                }
                Unit unit = Unit.INSTANCE;
                ThreadContextKt.restoreThreadContext(context, oldValue$iv);
                try {
                    Result.Companion companion4 = Result.Companion;
                    DispatchedTask dispatchedTask = this;
                    taskContext.afterTask();
                    result = Result.m1289constructorimpl(Unit.INSTANCE);
                } catch (Throwable th) {
                    th = th;
                    Result.Companion companion5 = Result.Companion;
                    result = Result.m1289constructorimpl(ResultKt.createFailure(th));
                    handleFatalException$kotlinx_coroutines_core(fatalException, Result.m1292exceptionOrNullimpl(result));
                }
                handleFatalException$kotlinx_coroutines_core(fatalException, Result.m1292exceptionOrNullimpl(result));
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.DispatchedContinuation<T>");
        } catch (Throwable e) {
            fatalException = e;
            try {
                Result.Companion companion6 = Result.Companion;
                DispatchedTask dispatchedTask2 = this;
                taskContext.afterTask();
                result = Result.m1289constructorimpl(Unit.INSTANCE);
            } catch (Throwable th2) {
                th = th2;
                Result.Companion companion52 = Result.Companion;
                result = Result.m1289constructorimpl(ResultKt.createFailure(th));
                handleFatalException$kotlinx_coroutines_core(fatalException, Result.m1292exceptionOrNullimpl(result));
            }
        }
    }

    public final void handleFatalException$kotlinx_coroutines_core(Throwable exception, Throwable finallyException) {
        if (exception != null || finallyException != null) {
            if (!(exception == null || finallyException == null)) {
                ExceptionsKt.addSuppressed(exception, finallyException);
            }
            Throwable cause = exception != null ? exception : finallyException;
            String str = "Fatal exception in coroutines machinery for " + this + ". " + "Please read KDoc to 'handleFatalException' method and report this incident to maintainers";
            if (cause == null) {
                Intrinsics.throwNpe();
            }
            CoroutineExceptionHandlerKt.handleCoroutineException(getDelegate$kotlinx_coroutines_core().getContext(), new CoroutinesInternalError(str, cause));
        }
    }
}
