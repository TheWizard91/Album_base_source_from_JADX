package kotlinx.coroutines;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000¦\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\r\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0011\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\b\u0012\u0004\u0012\u00028\u00000\u00022\b\u0012\u0004\u0012\u00028\u00000\u00032\u00060\u0004j\u0002`\u0005B\u001d\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u0006\u0012\u0006\u0010\t\u001a\u00020\b¢\u0006\u0004\b\n\u0010\u000bJ\u0019\u0010\u000f\u001a\u00020\u000e2\b\u0010\r\u001a\u0004\u0018\u00010\fH\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u0019\u0010\u0014\u001a\u00020\u00132\b\u0010\u0012\u001a\u0004\u0018\u00010\u0011H\u0016¢\u0006\u0004\b\u0014\u0010\u0015J!\u0010\u0019\u001a\u00020\u000e2\b\u0010\u0016\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0012\u001a\u00020\u0011H\u0010¢\u0006\u0004\b\u0017\u0010\u0018J\u0017\u0010\u001b\u001a\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\fH\u0016¢\u0006\u0004\b\u001b\u0010\u0010J\u0017\u0010\u001d\u001a\u00020\u000e2\u0006\u0010\u001c\u001a\u00020\bH\u0002¢\u0006\u0004\b\u001d\u0010\u001eJ\u000f\u0010\u001f\u001a\u00020\u000eH\u0002¢\u0006\u0004\b\u001f\u0010 J\u0017\u0010#\u001a\u00020\u00112\u0006\u0010\"\u001a\u00020!H\u0016¢\u0006\u0004\b#\u0010$J\u0011\u0010%\u001a\u0004\u0018\u00010\fH\u0001¢\u0006\u0004\b%\u0010&J\u0017\u0010)\u001a\n\u0018\u00010'j\u0004\u0018\u0001`(H\u0016¢\u0006\u0004\b)\u0010*J\u001f\u0010-\u001a\u00028\u0001\"\u0004\b\u0001\u0010\u00012\b\u0010\u0016\u001a\u0004\u0018\u00010\fH\u0010¢\u0006\u0004\b+\u0010,J\u000f\u0010.\u001a\u00020\u000eH\u0016¢\u0006\u0004\b.\u0010 J\u000f\u0010/\u001a\u00020\u000eH\u0002¢\u0006\u0004\b/\u0010 J\u001e\u00102\u001a\u00020\u000e2\f\u00101\u001a\b\u0012\u0004\u0012\u00020\u000e00H\b¢\u0006\u0004\b2\u00103J8\u00109\u001a\u00020\u000e2'\u00108\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0011¢\u0006\f\b5\u0012\b\b6\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000e04j\u0002`7H\u0016¢\u0006\u0004\b9\u0010:J8\u0010<\u001a\u00020;2'\u00108\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0011¢\u0006\f\b5\u0012\b\b6\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000e04j\u0002`7H\u0002¢\u0006\u0004\b<\u0010=JB\u0010>\u001a\u00020\u000e2'\u00108\u001a#\u0012\u0015\u0012\u0013\u0018\u00010\u0011¢\u0006\f\b5\u0012\b\b6\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000e04j\u0002`72\b\u0010\u0016\u001a\u0004\u0018\u00010\fH\u0002¢\u0006\u0004\b>\u0010?J\u000f\u0010A\u001a\u00020@H\u0014¢\u0006\u0004\bA\u0010BJ:\u0010E\u001a\u00020\u000e2\u0006\u0010C\u001a\u00028\u00002!\u0010D\u001a\u001d\u0012\u0013\u0012\u00110\u0011¢\u0006\f\b5\u0012\b\b6\u0012\u0004\b\b(\u0012\u0012\u0004\u0012\u00020\u000e04H\u0016¢\u0006\u0004\bE\u0010FJ#\u0010H\u001a\u0004\u0018\u00010G2\b\u0010\r\u001a\u0004\u0018\u00010\f2\u0006\u0010\t\u001a\u00020\bH\u0002¢\u0006\u0004\bH\u0010IJ \u0010L\u001a\u00020\u000e2\f\u0010K\u001a\b\u0012\u0004\u0012\u00028\u00000JH\u0016ø\u0001\u0000¢\u0006\u0004\bL\u0010\u0010J!\u0010P\u001a\u0004\u0018\u00010G2\u0006\u0010M\u001a\u00020\u00112\u0006\u0010\u001c\u001a\u00020\bH\u0000¢\u0006\u0004\bN\u0010OJ\u0011\u0010R\u001a\u0004\u0018\u00010\fH\u0010¢\u0006\u0004\bQ\u0010&J\u000f\u0010S\u001a\u00020@H\u0016¢\u0006\u0004\bS\u0010BJ\u000f\u0010T\u001a\u00020\u0013H\u0002¢\u0006\u0004\bT\u0010UJ#\u0010T\u001a\u0004\u0018\u00010\f2\u0006\u0010C\u001a\u00028\u00002\b\u0010V\u001a\u0004\u0018\u00010\fH\u0016¢\u0006\u0004\bT\u0010WJ\u0019\u0010X\u001a\u0004\u0018\u00010\f2\u0006\u0010M\u001a\u00020\u0011H\u0016¢\u0006\u0004\bX\u0010YJ\u000f\u0010Z\u001a\u00020\u0013H\u0002¢\u0006\u0004\bZ\u0010UJ\u001b\u0010\\\u001a\u00020\u000e*\u00020[2\u0006\u0010C\u001a\u00028\u0000H\u0016¢\u0006\u0004\b\\\u0010]J\u001b\u0010^\u001a\u00020\u000e*\u00020[2\u0006\u0010M\u001a\u00020\u0011H\u0016¢\u0006\u0004\b^\u0010_R\u001e\u0010b\u001a\n\u0018\u00010\u0004j\u0004\u0018\u0001`\u00058V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\b`\u0010aR\u001c\u0010d\u001a\u00020c8\u0016@\u0016X\u0004¢\u0006\f\n\u0004\bd\u0010e\u001a\u0004\bf\u0010gR\"\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00000\u00068\u0000@\u0000X\u0004¢\u0006\f\n\u0004\b\u0007\u0010h\u001a\u0004\bi\u0010jR\u0016\u0010k\u001a\u00020\u00138V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\bk\u0010UR\u0016\u0010l\u001a\u00020\u00138V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\bl\u0010UR\u0016\u0010m\u001a\u00020\u00138V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\bm\u0010UR\u0018\u0010o\u001a\u0004\u0018\u00010n8\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\bo\u0010pR\u0018\u0010\u0016\u001a\u0004\u0018\u00010\f8@@\u0000X\u0004¢\u0006\u0006\u001a\u0004\bq\u0010&\u0002\u0004\n\u0002\b\u0019¨\u0006r"}, mo33671d2 = {"Lkotlinx/coroutines/CancellableContinuationImpl;", "T", "Lkotlinx/coroutines/DispatchedTask;", "Lkotlinx/coroutines/CancellableContinuation;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "Lkotlin/coroutines/Continuation;", "delegate", "", "resumeMode", "<init>", "(Lkotlin/coroutines/Continuation;I)V", "", "proposedUpdate", "", "alreadyResumedError", "(Ljava/lang/Object;)V", "", "cause", "", "cancel", "(Ljava/lang/Throwable;)Z", "state", "cancelResult$kotlinx_coroutines_core", "(Ljava/lang/Object;Ljava/lang/Throwable;)V", "cancelResult", "token", "completeResume", "mode", "dispatchResume", "(I)V", "disposeParentHandle", "()V", "Lkotlinx/coroutines/Job;", "parent", "getContinuationCancellationCause", "(Lkotlinx/coroutines/Job;)Ljava/lang/Throwable;", "getResult", "()Ljava/lang/Object;", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "getSuccessfulResult$kotlinx_coroutines_core", "(Ljava/lang/Object;)Ljava/lang/Object;", "getSuccessfulResult", "initCancellability", "installParentCancellationHandler", "Lkotlin/Function0;", "block", "invokeHandlerSafely", "(Lkotlin/jvm/functions/Function0;)V", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "Lkotlinx/coroutines/CompletionHandler;", "handler", "invokeOnCancellation", "(Lkotlin/jvm/functions/Function1;)V", "Lkotlinx/coroutines/CancelHandler;", "makeHandler", "(Lkotlin/jvm/functions/Function1;)Lkotlinx/coroutines/CancelHandler;", "multipleHandlersError", "(Lkotlin/jvm/functions/Function1;Ljava/lang/Object;)V", "", "nameString", "()Ljava/lang/String;", "value", "onCancellation", "resume", "(Ljava/lang/Object;Lkotlin/jvm/functions/Function1;)V", "Lkotlinx/coroutines/CancelledContinuation;", "resumeImpl", "(Ljava/lang/Object;I)Lkotlinx/coroutines/CancelledContinuation;", "Lkotlin/Result;", "result", "resumeWith", "exception", "resumeWithExceptionMode$kotlinx_coroutines_core", "(Ljava/lang/Throwable;I)Lkotlinx/coroutines/CancelledContinuation;", "resumeWithExceptionMode", "takeState$kotlinx_coroutines_core", "takeState", "toString", "tryResume", "()Z", "idempotent", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "tryResumeWithException", "(Ljava/lang/Throwable;)Ljava/lang/Object;", "trySuspend", "Lkotlinx/coroutines/CoroutineDispatcher;", "resumeUndispatched", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Object;)V", "resumeUndispatchedWithException", "(Lkotlinx/coroutines/CoroutineDispatcher;Ljava/lang/Throwable;)V", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "Lkotlin/coroutines/CoroutineContext;", "context", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "Lkotlin/coroutines/Continuation;", "getDelegate$kotlinx_coroutines_core", "()Lkotlin/coroutines/Continuation;", "isActive", "isCancelled", "isCompleted", "Lkotlinx/coroutines/DisposableHandle;", "parentHandle", "Lkotlinx/coroutines/DisposableHandle;", "getState$kotlinx_coroutines_core", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: CancellableContinuationImpl.kt */
public class CancellableContinuationImpl<T> extends DispatchedTask<T> implements CancellableContinuation<T>, CoroutineStackFrame {
    private static final AtomicIntegerFieldUpdater _decision$FU;
    private static final AtomicReferenceFieldUpdater _state$FU;
    private volatile int _decision = 0;
    private volatile Object _state = Active.INSTANCE;
    private final CoroutineContext context;
    private final Continuation<T> delegate;
    private volatile DisposableHandle parentHandle;

    static {
        Class<CancellableContinuationImpl> cls = CancellableContinuationImpl.class;
        _decision$FU = AtomicIntegerFieldUpdater.newUpdater(cls, "_decision");
        _state$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_state");
    }

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public CancellableContinuationImpl(Continuation<? super T> delegate2, int resumeMode) {
        super(resumeMode);
        Intrinsics.checkParameterIsNotNull(delegate2, "delegate");
        this.delegate = delegate2;
        this.context = delegate2.getContext();
    }

    public final Continuation<T> getDelegate$kotlinx_coroutines_core() {
        return this.delegate;
    }

    public CoroutineContext getContext() {
        return this.context;
    }

    public final Object getState$kotlinx_coroutines_core() {
        return this._state;
    }

    public boolean isActive() {
        return getState$kotlinx_coroutines_core() instanceof NotCompleted;
    }

    public boolean isCompleted() {
        return !(getState$kotlinx_coroutines_core() instanceof NotCompleted);
    }

    public boolean isCancelled() {
        return getState$kotlinx_coroutines_core() instanceof CancelledContinuation;
    }

    public /* synthetic */ void initCancellability() {
    }

    private final void installParentCancellationHandler() {
        Job parent;
        if (!isCompleted() && (parent = (Job) this.delegate.getContext().get(Job.Key)) != null) {
            parent.start();
            DisposableHandle handle = Job.DefaultImpls.invokeOnCompletion$default(parent, true, false, new ChildContinuation(parent, this), 2, (Object) null);
            this.parentHandle = handle;
            if (isCompleted()) {
                handle.dispose();
                this.parentHandle = NonDisposableHandle.INSTANCE;
            }
        }
    }

    public CoroutineStackFrame getCallerFrame() {
        Continuation<T> continuation = this.delegate;
        if (!(continuation instanceof CoroutineStackFrame)) {
            continuation = null;
        }
        return (CoroutineStackFrame) continuation;
    }

    public StackTraceElement getStackTraceElement() {
        return null;
    }

    public Object takeState$kotlinx_coroutines_core() {
        return getState$kotlinx_coroutines_core();
    }

    public void cancelResult$kotlinx_coroutines_core(Object state, Throwable cause) {
        Intrinsics.checkParameterIsNotNull(cause, "cause");
        if (state instanceof CompletedWithCancellation) {
            try {
                ((CompletedWithCancellation) state).onCancellation.invoke(cause);
            } catch (Throwable ex$iv) {
                CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in cancellation handler for " + this, ex$iv));
            }
        }
    }

    public boolean cancel(Throwable cause) {
        Object state;
        do {
            state = this._state;
            if (!(state instanceof NotCompleted)) {
                return false;
            }
        } while (!_state$FU.compareAndSet(this, state, new CancelledContinuation(this, cause, state instanceof CancelHandler)));
        if (state instanceof CancelHandler) {
            try {
                ((CancelHandler) state).invoke(cause);
            } catch (Throwable ex$iv) {
                CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in cancellation handler for " + this, ex$iv));
            }
        }
        disposeParentHandle();
        dispatchResume(0);
        return true;
    }

    private final void invokeHandlerSafely(Function0<Unit> block) {
        try {
            block.invoke();
        } catch (Throwable ex) {
            CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in cancellation handler for " + this, ex));
        }
    }

    public Throwable getContinuationCancellationCause(Job parent) {
        Intrinsics.checkParameterIsNotNull(parent, "parent");
        return parent.getCancellationException();
    }

    private final boolean trySuspend() {
        do {
            int decision = this._decision;
            if (decision != 0) {
                if (decision == 2) {
                    return false;
                }
                throw new IllegalStateException("Already suspended".toString());
            }
        } while (!_decision$FU.compareAndSet(this, 0, 1));
        return true;
    }

    private final boolean tryResume() {
        do {
            int decision = this._decision;
            if (decision != 0) {
                if (decision == 1) {
                    return false;
                }
                throw new IllegalStateException("Already resumed".toString());
            }
        } while (!_decision$FU.compareAndSet(this, 0, 2));
        return true;
    }

    public final Object getResult() {
        Job job;
        installParentCancellationHandler();
        if (trySuspend()) {
            return IntrinsicsKt.getCOROUTINE_SUSPENDED();
        }
        Object state = getState$kotlinx_coroutines_core();
        if (state instanceof CompletedExceptionally) {
            throw StackTraceRecoveryKt.recoverStackTrace(((CompletedExceptionally) state).cause, this);
        } else if (this.resumeMode != 1 || (job = (Job) getContext().get(Job.Key)) == null || job.isActive()) {
            return getSuccessfulResult$kotlinx_coroutines_core(state);
        } else {
            CancellationException cause = job.getCancellationException();
            cancelResult$kotlinx_coroutines_core(state, cause);
            throw StackTraceRecoveryKt.recoverStackTrace(cause, this);
        }
    }

    public void resumeWith(Object result) {
        resumeImpl(CompletedExceptionallyKt.toState(result), this.resumeMode);
    }

    public void resume(T value, Function1<? super Throwable, Unit> onCancellation) {
        Intrinsics.checkParameterIsNotNull(onCancellation, "onCancellation");
        CancelledContinuation cancelled = resumeImpl(new CompletedWithCancellation(value, onCancellation), this.resumeMode);
        if (cancelled != null) {
            try {
                onCancellation.invoke(cancelled.cause);
            } catch (Throwable ex$iv) {
                CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in cancellation handler for " + this, ex$iv));
            }
        }
    }

    public final CancelledContinuation resumeWithExceptionMode$kotlinx_coroutines_core(Throwable exception, int mode) {
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        return resumeImpl(new CompletedExceptionally(exception, false, 2, (DefaultConstructorMarker) null), mode);
    }

    public void invokeOnCancellation(Function1<? super Throwable, Unit> handler) {
        Object handleCache;
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        Throwable cause$iv = null;
        Object handleCache2 = (CancelHandler) null;
        while (true) {
            Object state = this._state;
            if (state instanceof Active) {
                if (handleCache2 != null) {
                    handleCache = handleCache2;
                } else {
                    Object it = makeHandler(handler);
                    Object obj = it;
                    handleCache = it;
                    handleCache2 = obj;
                }
                if (!_state$FU.compareAndSet(this, state, handleCache2)) {
                    handleCache2 = handleCache;
                } else {
                    return;
                }
            } else if (state instanceof CancelHandler) {
                multipleHandlersError(handler, state);
            } else if (state instanceof CancelledContinuation) {
                if (!((CancelledContinuation) state).makeHandled()) {
                    multipleHandlersError(handler, state);
                }
                try {
                    CompletedExceptionally completedExceptionally = (CompletedExceptionally) (!(state instanceof CompletedExceptionally) ? null : state);
                    if (completedExceptionally != null) {
                        cause$iv = completedExceptionally.cause;
                    }
                    handler.invoke(cause$iv);
                    return;
                } catch (Throwable ex$iv) {
                    CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), new CompletionHandlerException("Exception in cancellation handler for " + this, ex$iv));
                    return;
                }
            } else {
                return;
            }
        }
    }

    private final void multipleHandlersError(Function1<? super Throwable, Unit> handler, Object state) {
        throw new IllegalStateException(("It's prohibited to register multiple handlers, tried to register " + handler + ", already has " + state).toString());
    }

    private final CancelHandler makeHandler(Function1<? super Throwable, Unit> handler) {
        return handler instanceof CancelHandler ? (CancelHandler) handler : new InvokeOnCancel(handler);
    }

    private final void dispatchResume(int mode) {
        if (!tryResume()) {
            DispatchedKt.dispatch(this, mode);
        }
    }

    private final CancelledContinuation resumeImpl(Object proposedUpdate, int resumeMode) {
        while (true) {
            Object state = this._state;
            if (state instanceof NotCompleted) {
                if (_state$FU.compareAndSet(this, state, proposedUpdate)) {
                    disposeParentHandle();
                    dispatchResume(resumeMode);
                    return null;
                }
            } else if ((state instanceof CancelledContinuation) && ((CancelledContinuation) state).makeResumed()) {
                return (CancelledContinuation) state;
            } else {
                alreadyResumedError(proposedUpdate);
            }
        }
    }

    private final void alreadyResumedError(Object proposedUpdate) {
        throw new IllegalStateException(("Already resumed, but proposed with update " + proposedUpdate).toString());
    }

    private final void disposeParentHandle() {
        DisposableHandle it = this.parentHandle;
        if (it != null) {
            it.dispose();
            this.parentHandle = NonDisposableHandle.INSTANCE;
        }
    }

    public Object tryResume(T value, Object idempotent) {
        Object state;
        Object update;
        do {
            state = this._state;
            if (state instanceof NotCompleted) {
                if (idempotent == null) {
                    update = value;
                } else {
                    update = new CompletedIdempotentResult(idempotent, value, (NotCompleted) state);
                }
            } else if (!(state instanceof CompletedIdempotentResult) || ((CompletedIdempotentResult) state).idempotentResume != idempotent) {
                return null;
            } else {
                if (DebugKt.getASSERTIONS_ENABLED()) {
                    if (!(((CompletedIdempotentResult) state).result == value)) {
                        throw new AssertionError();
                    }
                }
                return ((CompletedIdempotentResult) state).token;
            }
        } while (!_state$FU.compareAndSet(this, state, update));
        disposeParentHandle();
        return state;
    }

    public Object tryResumeWithException(Throwable exception) {
        Object state;
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        do {
            state = this._state;
            if (!(state instanceof NotCompleted)) {
                return null;
            }
        } while (!_state$FU.compareAndSet(this, state, new CompletedExceptionally(exception, false, 2, (DefaultConstructorMarker) null)));
        disposeParentHandle();
        return state;
    }

    public void completeResume(Object token) {
        Intrinsics.checkParameterIsNotNull(token, "token");
        dispatchResume(this.resumeMode);
    }

    public void resumeUndispatched(CoroutineDispatcher $this$resumeUndispatched, T value) {
        Intrinsics.checkParameterIsNotNull($this$resumeUndispatched, "$this$resumeUndispatched");
        Continuation<T> continuation = this.delegate;
        CoroutineDispatcher coroutineDispatcher = null;
        if (!(continuation instanceof DispatchedContinuation)) {
            continuation = null;
        }
        DispatchedContinuation dc = (DispatchedContinuation) continuation;
        if (dc != null) {
            coroutineDispatcher = dc.dispatcher;
        }
        resumeImpl(value, coroutineDispatcher == $this$resumeUndispatched ? 3 : this.resumeMode);
    }

    public void resumeUndispatchedWithException(CoroutineDispatcher $this$resumeUndispatchedWithException, Throwable exception) {
        Intrinsics.checkParameterIsNotNull($this$resumeUndispatchedWithException, "$this$resumeUndispatchedWithException");
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        Continuation<T> continuation = this.delegate;
        CoroutineDispatcher coroutineDispatcher = null;
        if (!(continuation instanceof DispatchedContinuation)) {
            continuation = null;
        }
        DispatchedContinuation dc = (DispatchedContinuation) continuation;
        CompletedExceptionally completedExceptionally = new CompletedExceptionally(exception, false, 2, (DefaultConstructorMarker) null);
        if (dc != null) {
            coroutineDispatcher = dc.dispatcher;
        }
        resumeImpl(completedExceptionally, coroutineDispatcher == $this$resumeUndispatchedWithException ? 3 : this.resumeMode);
    }

    public <T> T getSuccessfulResult$kotlinx_coroutines_core(Object state) {
        if (state instanceof CompletedIdempotentResult) {
            return ((CompletedIdempotentResult) state).result;
        }
        if (state instanceof CompletedWithCancellation) {
            return ((CompletedWithCancellation) state).result;
        }
        return state;
    }

    public String toString() {
        return nameString() + '(' + DebugStringsKt.toDebugString(this.delegate) + "){" + getState$kotlinx_coroutines_core() + "}@" + DebugStringsKt.getHexAddress(this);
    }

    /* access modifiers changed from: protected */
    public String nameString() {
        return "CancellableContinuation";
    }
}
