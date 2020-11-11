package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.internal.ThreadContextKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000`\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0000\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u00022\u00060\u0003j\u0002`\u00042\b\u0012\u0004\u0012\u0002H\u00010\u0005B\u001b\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0002\u0010\tJ\u0017\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00028\u0000H\u0000¢\u0006\u0004\b\u001c\u0010\u001dJ\u0010\u0010\u001e\u001a\n\u0018\u00010\u001fj\u0004\u0018\u0001` H\u0016J\u0016\u0010!\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00028\u0000H\b¢\u0006\u0002\u0010\u001dJ\u0011\u0010\"\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020$H\bJ\t\u0010%\u001a\u00020&H\bJ\u0016\u0010'\u001a\u00020\u001a2\u0006\u0010\u001b\u001a\u00028\u0000H\b¢\u0006\u0002\u0010\u001dJ\u0011\u0010(\u001a\u00020\u001a2\u0006\u0010#\u001a\u00020$H\bJ\u001e\u0010)\u001a\u00020\u001a2\f\u0010*\u001a\b\u0012\u0004\u0012\u00028\u00000+H\u0016ø\u0001\u0000¢\u0006\u0002\u0010\u001dJ\u000f\u0010,\u001a\u0004\u0018\u00010\u000bH\u0010¢\u0006\u0002\b-J\b\u0010.\u001a\u00020/H\u0016R\u001a\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0000@\u0000X\u000e¢\u0006\b\n\u0000\u0012\u0004\b\f\u0010\rR\u001c\u0010\u000e\u001a\n\u0018\u00010\u0003j\u0004\u0018\u0001`\u0004X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0012\u0010\u0011\u001a\u00020\u0012X\u0005¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0014R\u0016\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00058\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0015\u001a\u00020\u000b8\u0000X\u0004¢\u0006\u0002\n\u0000R\u001a\u0010\u0016\u001a\b\u0012\u0004\u0012\u00028\u00000\u00058PX\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u00060"}, mo33671d2 = {"Lkotlinx/coroutines/DispatchedContinuation;", "T", "Lkotlinx/coroutines/DispatchedTask;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "Lkotlin/coroutines/Continuation;", "dispatcher", "Lkotlinx/coroutines/CoroutineDispatcher;", "continuation", "(Lkotlinx/coroutines/CoroutineDispatcher;Lkotlin/coroutines/Continuation;)V", "_state", "", "_state$annotations", "()V", "callerFrame", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "countOrElement", "delegate", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "dispatchYield", "", "value", "dispatchYield$kotlinx_coroutines_core", "(Ljava/lang/Object;)V", "getStackTraceElement", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "resumeCancellable", "resumeCancellableWithException", "exception", "", "resumeCancelled", "", "resumeUndispatched", "resumeUndispatchedWithException", "resumeWith", "result", "Lkotlin/Result;", "takeState", "takeState$kotlinx_coroutines_core", "toString", "", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Dispatched.kt */
public final class DispatchedContinuation<T> extends DispatchedTask<T> implements CoroutineStackFrame, Continuation<T> {
    public Object _state = DispatchedKt.UNDEFINED;
    private final CoroutineStackFrame callerFrame;
    public final Continuation<T> continuation;
    public final Object countOrElement;
    public final CoroutineDispatcher dispatcher;

    public static /* synthetic */ void _state$annotations() {
    }

    public CoroutineContext getContext() {
        return this.continuation.getContext();
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DispatchedContinuation(CoroutineDispatcher dispatcher2, Continuation<? super T> continuation2) {
        super(0);
        Intrinsics.checkParameterIsNotNull(dispatcher2, "dispatcher");
        Intrinsics.checkParameterIsNotNull(continuation2, "continuation");
        this.dispatcher = dispatcher2;
        this.continuation = continuation2;
        this.callerFrame = (CoroutineStackFrame) (!(continuation2 instanceof CoroutineStackFrame) ? null : continuation2);
        this.countOrElement = ThreadContextKt.threadContextElements(getContext());
    }

    public CoroutineStackFrame getCallerFrame() {
        return this.callerFrame;
    }

    public StackTraceElement getStackTraceElement() {
        return null;
    }

    public Object takeState$kotlinx_coroutines_core() {
        Object state = this._state;
        if (DebugKt.getASSERTIONS_ENABLED()) {
            if (!(state != DispatchedKt.UNDEFINED)) {
                throw new AssertionError();
            }
        }
        this._state = DispatchedKt.UNDEFINED;
        return state;
    }

    public Continuation<T> getDelegate$kotlinx_coroutines_core() {
        return this;
    }

    public void resumeWith(Object result) {
        CoroutineContext context = this.continuation.getContext();
        Object state = CompletedExceptionallyKt.toState(result);
        if (this.dispatcher.isDispatchNeeded(context)) {
            this._state = state;
            this.resumeMode = 0;
            this.dispatcher.dispatch(context, this);
            Object obj = result;
            return;
        }
        EventLoop eventLoop$iv = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (eventLoop$iv.isUnconfinedLoopActive()) {
            this._state = state;
            this.resumeMode = 0;
            eventLoop$iv.dispatchUnconfined(this);
            Object obj2 = result;
            return;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv$iv = this;
        eventLoop$iv.incrementUseCount(true);
        try {
            CoroutineContext context$iv = getContext();
            Object oldValue$iv = ThreadContextKt.updateThreadContext(context$iv, this.countOrElement);
            try {
            } catch (Throwable th) {
                th = th;
                Object obj3 = result;
                ThreadContextKt.restoreThreadContext(context$iv, oldValue$iv);
                throw th;
            }
            try {
                this.continuation.resumeWith(result);
                Unit unit = Unit.INSTANCE;
                ThreadContextKt.restoreThreadContext(context$iv, oldValue$iv);
                while (eventLoop$iv.processUnconfinedEvent()) {
                }
            } catch (Throwable th2) {
                e$iv$iv = th2;
                try {
                    $this$runUnconfinedEventLoop$iv$iv.handleFatalException$kotlinx_coroutines_core(e$iv$iv, (Throwable) null);
                    eventLoop$iv.decrementUseCount(true);
                } catch (Throwable th3) {
                    Throwable th4 = th3;
                    eventLoop$iv.decrementUseCount(true);
                    throw th4;
                }
            }
        } catch (Throwable th5) {
            e$iv$iv = th5;
            Object obj4 = result;
            $this$runUnconfinedEventLoop$iv$iv.handleFatalException$kotlinx_coroutines_core(e$iv$iv, (Throwable) null);
            eventLoop$iv.decrementUseCount(true);
        }
        eventLoop$iv.decrementUseCount(true);
    }

    public final void resumeCancellable(T value) {
        boolean z;
        boolean z2;
        CoroutineContext context$iv$iv;
        Object oldValue$iv$iv;
        T t = value;
        if (this.dispatcher.isDispatchNeeded(getContext())) {
            this._state = t;
            this.resumeMode = 1;
            this.dispatcher.dispatch(getContext(), this);
            return;
        }
        EventLoop eventLoop$iv = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (eventLoop$iv.isUnconfinedLoopActive()) {
            this._state = t;
            this.resumeMode = 1;
            eventLoop$iv.dispatchUnconfined(this);
            return;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv$iv = this;
        eventLoop$iv.incrementUseCount(true);
        try {
            Job job$iv = (Job) getContext().get(Job.Key);
            if (job$iv == null || job$iv.isActive()) {
                z2 = false;
            } else {
                Result.Companion companion = Result.Companion;
                resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(job$iv.getCancellationException())));
                z2 = true;
            }
            if (!z2) {
                Object value$iv = value;
                context$iv$iv = getContext();
                oldValue$iv$iv = ThreadContextKt.updateThreadContext(context$iv$iv, this.countOrElement);
                Continuation<T> continuation2 = this.continuation;
                Result.Companion companion2 = Result.Companion;
                continuation2.resumeWith(Result.m1289constructorimpl(value$iv));
                Unit unit = Unit.INSTANCE;
                InlineMarker.finallyStart(1);
                ThreadContextKt.restoreThreadContext(context$iv$iv, oldValue$iv$iv);
                InlineMarker.finallyEnd(1);
            }
            do {
            } while (eventLoop$iv.processUnconfinedEvent());
            z = true;
            InlineMarker.finallyStart(1);
        } catch (Throwable e$iv$iv) {
            try {
                $this$runUnconfinedEventLoop$iv$iv.handleFatalException$kotlinx_coroutines_core(e$iv$iv, (Throwable) null);
                z = true;
                InlineMarker.finallyStart(1);
            } catch (Throwable th) {
                Throwable th2 = th;
                InlineMarker.finallyStart(1);
                eventLoop$iv.decrementUseCount(true);
                InlineMarker.finallyEnd(1);
                throw th2;
            }
        }
        eventLoop$iv.decrementUseCount(z);
        InlineMarker.finallyEnd(z ? 1 : 0);
    }

    public final void resumeCancellableWithException(Throwable exception) {
        boolean z;
        Job job$iv;
        CoroutineContext context$iv$iv;
        Object oldValue$iv$iv;
        Throwable th = exception;
        Intrinsics.checkParameterIsNotNull(th, "exception");
        CoroutineContext context = this.continuation.getContext();
        CompletedExceptionally state = new CompletedExceptionally(th, false, 2, (DefaultConstructorMarker) null);
        if (this.dispatcher.isDispatchNeeded(context)) {
            this._state = new CompletedExceptionally(th, false, 2, (DefaultConstructorMarker) null);
            this.resumeMode = 1;
            this.dispatcher.dispatch(context, this);
            return;
        }
        EventLoop eventLoop$iv = ThreadLocalEventLoop.INSTANCE.getEventLoop$kotlinx_coroutines_core();
        if (eventLoop$iv.isUnconfinedLoopActive()) {
            this._state = state;
            this.resumeMode = 1;
            eventLoop$iv.dispatchUnconfined(this);
            return;
        }
        DispatchedTask $this$runUnconfinedEventLoop$iv$iv = this;
        eventLoop$iv.incrementUseCount(true);
        try {
            Job job$iv2 = (Job) getContext().get(Job.Key);
            if (job$iv2 == null || job$iv2.isActive()) {
                job$iv = null;
            } else {
                Result.Companion companion = Result.Companion;
                resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(job$iv2.getCancellationException())));
                job$iv = 1;
            }
            if (job$iv == null) {
                Throwable exception$iv = exception;
                context$iv$iv = getContext();
                oldValue$iv$iv = ThreadContextKt.updateThreadContext(context$iv$iv, this.countOrElement);
                Continuation $this$resumeWithStackTrace$iv$iv = this.continuation;
                Result.Companion companion2 = Result.Companion;
                Throwable exception$iv$iv = exception$iv;
                Throwable th2 = exception$iv$iv;
                $this$resumeWithStackTrace$iv$iv.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(StackTraceRecoveryKt.recoverStackTrace(exception$iv$iv, $this$resumeWithStackTrace$iv$iv))));
                Unit unit = Unit.INSTANCE;
                InlineMarker.finallyStart(1);
                ThreadContextKt.restoreThreadContext(context$iv$iv, oldValue$iv$iv);
                InlineMarker.finallyEnd(1);
            }
            do {
            } while (eventLoop$iv.processUnconfinedEvent());
            z = true;
            InlineMarker.finallyStart(1);
        } catch (Throwable e$iv$iv) {
            try {
                $this$runUnconfinedEventLoop$iv$iv.handleFatalException$kotlinx_coroutines_core(e$iv$iv, (Throwable) null);
                z = true;
                InlineMarker.finallyStart(1);
            } catch (Throwable th3) {
                Throwable th4 = th3;
                InlineMarker.finallyStart(1);
                eventLoop$iv.decrementUseCount(true);
                InlineMarker.finallyEnd(1);
                throw th4;
            }
        }
        eventLoop$iv.decrementUseCount(z);
        InlineMarker.finallyEnd(z ? 1 : 0);
    }

    public final boolean resumeCancelled() {
        Job job = (Job) getContext().get(Job.Key);
        if (job == null || job.isActive()) {
            return false;
        }
        Result.Companion companion = Result.Companion;
        resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(job.getCancellationException())));
        return true;
    }

    public final void resumeUndispatched(T value) {
        CoroutineContext context$iv = getContext();
        Object oldValue$iv = ThreadContextKt.updateThreadContext(context$iv, this.countOrElement);
        try {
            Continuation<T> continuation2 = this.continuation;
            Result.Companion companion = Result.Companion;
            continuation2.resumeWith(Result.m1289constructorimpl(value));
            Unit unit = Unit.INSTANCE;
        } finally {
            InlineMarker.finallyStart(1);
            ThreadContextKt.restoreThreadContext(context$iv, oldValue$iv);
            InlineMarker.finallyEnd(1);
        }
    }

    public final void resumeUndispatchedWithException(Throwable exception) {
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        CoroutineContext context$iv = getContext();
        Object oldValue$iv = ThreadContextKt.updateThreadContext(context$iv, this.countOrElement);
        try {
            Continuation $this$resumeWithStackTrace$iv = this.continuation;
            Result.Companion companion = Result.Companion;
            $this$resumeWithStackTrace$iv.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(StackTraceRecoveryKt.recoverStackTrace(exception, $this$resumeWithStackTrace$iv))));
            Unit unit = Unit.INSTANCE;
        } finally {
            InlineMarker.finallyStart(1);
            ThreadContextKt.restoreThreadContext(context$iv, oldValue$iv);
            InlineMarker.finallyEnd(1);
        }
    }

    public final void dispatchYield$kotlinx_coroutines_core(T value) {
        CoroutineContext context = this.continuation.getContext();
        this._state = value;
        this.resumeMode = 1;
        this.dispatcher.dispatchYield(context, this);
    }

    public String toString() {
        return "DispatchedContinuation[" + this.dispatcher + ", " + DebugStringsKt.toDebugString(this.continuation) + ']';
    }
}
