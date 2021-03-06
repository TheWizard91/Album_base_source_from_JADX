package kotlinx.coroutines.selects;

import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.CoroutineStackFrame;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CompletedExceptionally;
import kotlinx.coroutines.CompletedExceptionallyKt;
import kotlinx.coroutines.CoroutineExceptionHandlerKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.DispatchedKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.JobCancellingNode;
import kotlinx.coroutines.internal.AtomicDesc;
import kotlinx.coroutines.internal.AtomicOp;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.OpDescriptor;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectBuilder;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0004\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0002\b\u000e\b\u0001\u0018\u0000*\u0006\b\u0000\u0010\u0001 \u00002\u00020\u00022\b\u0012\u0004\u0012\u00028\u00000\u00032\b\u0012\u0004\u0012\u00028\u00000\u00042\b\u0012\u0004\u0012\u00028\u00000\u00052\u00060\u0006j\u0002`\u0007:\u0003RSTB\u0015\u0012\f\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005¢\u0006\u0004\b\t\u0010\nJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\u000bH\u0016¢\u0006\u0004\b\u000e\u0010\u000fJ\u000f\u0010\u0010\u001a\u00020\rH\u0002¢\u0006\u0004\b\u0010\u0010\u0011J.\u0010\u0016\u001a\u00020\r2\u000e\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00130\u00122\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\r0\u0012H\b¢\u0006\u0004\b\u0016\u0010\u0017J\u0011\u0010\u0018\u001a\u0004\u0018\u00010\u0013H\u0001¢\u0006\u0004\b\u0018\u0010\u0019J\u0017\u0010\u001c\u001a\n\u0018\u00010\u001aj\u0004\u0018\u0001`\u001bH\u0016¢\u0006\u0004\b\u001c\u0010\u001dJ\u0017\u0010 \u001a\u00020\r2\u0006\u0010\u001f\u001a\u00020\u001eH\u0001¢\u0006\u0004\b \u0010!J\u000f\u0010\"\u001a\u00020\rH\u0002¢\u0006\u0004\b\"\u0010\u0011J8\u0010&\u001a\u00020\r2\u0006\u0010$\u001a\u00020#2\u001c\u0010\u0015\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00130%H\u0016ø\u0001\u0000¢\u0006\u0004\b&\u0010'J\u0019\u0010*\u001a\u0004\u0018\u00010\u00132\u0006\u0010)\u001a\u00020(H\u0016¢\u0006\u0004\b*\u0010+J\u0017\u0010-\u001a\u00020\r2\u0006\u0010,\u001a\u00020\u001eH\u0016¢\u0006\u0004\b-\u0010!J \u00100\u001a\u00020\r2\f\u0010/\u001a\b\u0012\u0004\u0012\u00028\u00000.H\u0016ø\u0001\u0000¢\u0006\u0004\b0\u00101J\u0019\u00104\u001a\u0002032\b\u00102\u001a\u0004\u0018\u00010\u0013H\u0016¢\u0006\u0004\b4\u00105J5\u00107\u001a\u00020\r*\u0002062\u001c\u0010\u0015\u001a\u0018\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00130%H\u0002ø\u0001\u0000¢\u0006\u0004\b7\u00108JG\u00107\u001a\u00020\r\"\u0004\b\u0001\u00109*\b\u0012\u0004\u0012\u00028\u00010:2\"\u0010\u0015\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00130;H\u0002ø\u0001\u0000¢\u0006\u0004\b7\u0010<J[\u00107\u001a\u00020\r\"\u0004\b\u0001\u0010=\"\u0004\b\u0002\u00109*\u000e\u0012\u0004\u0012\u00028\u0001\u0012\u0004\u0012\u00028\u00020>2\u0006\u0010?\u001a\u00028\u00012\"\u0010\u0015\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00130;H\u0002ø\u0001\u0000¢\u0006\u0004\b7\u0010@R\u001e\u0010C\u001a\n\u0018\u00010\u0006j\u0004\u0018\u0001`\u00078V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\bA\u0010BR\u001c\u0010F\u001a\b\u0012\u0004\u0012\u00028\u00000\u00058V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\bD\u0010ER\u0016\u0010J\u001a\u00020G8V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\bH\u0010IR\u0016\u0010K\u001a\u0002038V@\u0016X\u0004¢\u0006\u0006\u001a\u0004\bK\u0010LR\u0018\u0010M\u001a\u0004\u0018\u00010\u000b8\u0002@\u0002X\u000e¢\u0006\u0006\n\u0004\bM\u0010NR\u0018\u0010P\u001a\u0004\u0018\u00010\u00138B@\u0002X\u0004¢\u0006\u0006\u001a\u0004\bO\u0010\u0019R\u001c\u0010\b\u001a\b\u0012\u0004\u0012\u00028\u00000\u00058\u0002@\u0002X\u0004¢\u0006\u0006\n\u0004\b\b\u0010Q\u0002\u0004\n\u0002\b\u0019¨\u0006U"}, mo33671d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl;", "R", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "Lkotlinx/coroutines/selects/SelectBuilder;", "Lkotlinx/coroutines/selects/SelectInstance;", "Lkotlin/coroutines/Continuation;", "Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "Lkotlinx/coroutines/internal/CoroutineStackFrame;", "uCont", "<init>", "(Lkotlin/coroutines/Continuation;)V", "Lkotlinx/coroutines/DisposableHandle;", "handle", "", "disposeOnSelect", "(Lkotlinx/coroutines/DisposableHandle;)V", "doAfterSelect", "()V", "Lkotlin/Function0;", "", "value", "block", "doResume", "(Lkotlin/jvm/functions/Function0;Lkotlin/jvm/functions/Function0;)V", "getResult", "()Ljava/lang/Object;", "Ljava/lang/StackTraceElement;", "Lkotlinx/coroutines/internal/StackTraceElement;", "getStackTraceElement", "()Ljava/lang/StackTraceElement;", "", "e", "handleBuilderException", "(Ljava/lang/Throwable;)V", "initCancellability", "", "timeMillis", "Lkotlin/Function1;", "onTimeout", "(JLkotlin/jvm/functions/Function1;)V", "Lkotlinx/coroutines/internal/AtomicDesc;", "desc", "performAtomicTrySelect", "(Lkotlinx/coroutines/internal/AtomicDesc;)Ljava/lang/Object;", "exception", "resumeSelectCancellableWithException", "Lkotlin/Result;", "result", "resumeWith", "(Ljava/lang/Object;)V", "idempotent", "", "trySelect", "(Ljava/lang/Object;)Z", "Lkotlinx/coroutines/selects/SelectClause0;", "invoke", "(Lkotlinx/coroutines/selects/SelectClause0;Lkotlin/jvm/functions/Function1;)V", "Q", "Lkotlinx/coroutines/selects/SelectClause1;", "Lkotlin/Function2;", "(Lkotlinx/coroutines/selects/SelectClause1;Lkotlin/jvm/functions/Function2;)V", "P", "Lkotlinx/coroutines/selects/SelectClause2;", "param", "(Lkotlinx/coroutines/selects/SelectClause2;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "getCallerFrame", "()Lkotlin/coroutines/jvm/internal/CoroutineStackFrame;", "callerFrame", "getCompletion", "()Lkotlin/coroutines/Continuation;", "completion", "Lkotlin/coroutines/CoroutineContext;", "getContext", "()Lkotlin/coroutines/CoroutineContext;", "context", "isSelected", "()Z", "parentHandle", "Lkotlinx/coroutines/DisposableHandle;", "getState", "state", "Lkotlin/coroutines/Continuation;", "AtomicSelectOp", "DisposeNode", "SelectOnCancelling", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: Select.kt */
public final class SelectBuilderImpl<R> extends LockFreeLinkedListHead implements SelectBuilder<R>, SelectInstance<R>, Continuation<R>, CoroutineStackFrame {
    static final AtomicReferenceFieldUpdater _result$FU;
    static final AtomicReferenceFieldUpdater _state$FU;
    volatile Object _result = SelectKt.UNDECIDED;
    volatile Object _state = this;
    private volatile DisposableHandle parentHandle;
    private final Continuation<R> uCont;

    static {
        Class<SelectBuilderImpl> cls = SelectBuilderImpl.class;
        _state$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_state");
        _result$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_result");
    }

    public SelectBuilderImpl(Continuation<? super R> uCont2) {
        Intrinsics.checkParameterIsNotNull(uCont2, "uCont");
        this.uCont = uCont2;
    }

    public <P, Q> void invoke(SelectClause2<? super P, ? extends Q> $this$invoke, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> block) {
        Intrinsics.checkParameterIsNotNull($this$invoke, "$this$invoke");
        Intrinsics.checkParameterIsNotNull(block, "block");
        SelectBuilder.DefaultImpls.invoke(this, $this$invoke, block);
    }

    public CoroutineStackFrame getCallerFrame() {
        Continuation<R> continuation = this.uCont;
        if (!(continuation instanceof CoroutineStackFrame)) {
            continuation = null;
        }
        return (CoroutineStackFrame) continuation;
    }

    public StackTraceElement getStackTraceElement() {
        return null;
    }

    public CoroutineContext getContext() {
        return this.uCont.getContext();
    }

    public Continuation<R> getCompletion() {
        return this;
    }

    private final void doResume(Function0<? extends Object> value, Function0<Unit> block) {
        if (!DebugKt.getASSERTIONS_ENABLED() || isSelected() != 0) {
            while (true) {
                Object result = this._result;
                if (result == SelectKt.UNDECIDED) {
                    if (_result$FU.compareAndSet(this, SelectKt.UNDECIDED, value.invoke())) {
                        return;
                    }
                } else if (result != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    throw new IllegalStateException("Already resumed");
                } else if (_result$FU.compareAndSet(this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), SelectKt.RESUMED)) {
                    block.invoke();
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public void resumeWith(Object result) {
        if (!DebugKt.getASSERTIONS_ENABLED() || isSelected() != 0) {
            while (true) {
                Object result$iv = this._result;
                if (result$iv == SelectKt.UNDECIDED) {
                    if (_result$FU.compareAndSet(this, SelectKt.UNDECIDED, CompletedExceptionallyKt.toState(result))) {
                        return;
                    }
                } else if (result$iv != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    throw new IllegalStateException("Already resumed");
                } else if (_result$FU.compareAndSet(this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), SelectKt.RESUMED)) {
                    if (Result.m1295isFailureimpl(result)) {
                        Continuation $this$resumeWithStackTrace$iv = this.uCont;
                        Throwable exception$iv = Result.m1292exceptionOrNullimpl(result);
                        if (exception$iv == null) {
                            Intrinsics.throwNpe();
                        }
                        Result.Companion companion = Result.Companion;
                        $this$resumeWithStackTrace$iv.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(StackTraceRecoveryKt.recoverStackTrace(exception$iv, $this$resumeWithStackTrace$iv))));
                        return;
                    }
                    this.uCont.resumeWith(result);
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public void resumeSelectCancellableWithException(Throwable exception) {
        Intrinsics.checkParameterIsNotNull(exception, "exception");
        if (!DebugKt.getASSERTIONS_ENABLED() || isSelected() != 0) {
            while (true) {
                Object result$iv = this._result;
                if (result$iv == SelectKt.UNDECIDED) {
                    if (_result$FU.compareAndSet(this, SelectKt.UNDECIDED, new CompletedExceptionally(exception, false, 2, (DefaultConstructorMarker) null))) {
                        return;
                    }
                } else if (result$iv != IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                    throw new IllegalStateException("Already resumed");
                } else if (_result$FU.compareAndSet(this, IntrinsicsKt.getCOROUTINE_SUSPENDED(), SelectKt.RESUMED)) {
                    DispatchedKt.resumeCancellableWithException(IntrinsicsKt.intercepted(this.uCont), exception);
                    return;
                }
            }
        } else {
            throw new AssertionError();
        }
    }

    public final Object getResult() {
        if (!isSelected()) {
            initCancellability();
        }
        Object result = this._result;
        if (result == SelectKt.UNDECIDED) {
            if (_result$FU.compareAndSet(this, SelectKt.UNDECIDED, IntrinsicsKt.getCOROUTINE_SUSPENDED())) {
                return IntrinsicsKt.getCOROUTINE_SUSPENDED();
            }
            result = this._result;
        }
        if (result == SelectKt.RESUMED) {
            throw new IllegalStateException("Already resumed");
        } else if (!(result instanceof CompletedExceptionally)) {
            return result;
        } else {
            throw ((CompletedExceptionally) result).cause;
        }
    }

    private final void initCancellability() {
        Job parent = (Job) getContext().get(Job.Key);
        if (parent != null) {
            DisposableHandle newRegistration = Job.DefaultImpls.invokeOnCompletion$default(parent, true, false, new SelectOnCancelling(this, parent), 2, (Object) null);
            this.parentHandle = newRegistration;
            if (isSelected()) {
                newRegistration.dispose();
            }
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0004\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0002¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0016¨\u0006\u000b"}, mo33671d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$SelectOnCancelling;", "Lkotlinx/coroutines/JobCancellingNode;", "Lkotlinx/coroutines/Job;", "job", "(Lkotlinx/coroutines/selects/SelectBuilderImpl;Lkotlinx/coroutines/Job;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: Select.kt */
    private final class SelectOnCancelling extends JobCancellingNode<Job> {
        final /* synthetic */ SelectBuilderImpl this$0;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public SelectOnCancelling(SelectBuilderImpl $outer, Job job) {
            super(job);
            Intrinsics.checkParameterIsNotNull(job, "job");
            this.this$0 = $outer;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return Unit.INSTANCE;
        }

        public void invoke(Throwable cause) {
            if (this.this$0.trySelect((Object) null)) {
                this.this$0.resumeSelectCancellableWithException(this.job.getCancellationException());
            }
        }

        public String toString() {
            return "SelectOnCancelling[" + this.this$0 + ']';
        }
    }

    private final Object getState() {
        while (true) {
            Object state = this._state;
            if (!(state instanceof OpDescriptor)) {
                return state;
            }
            ((OpDescriptor) state).perform(this);
        }
    }

    public final void handleBuilderException(Throwable e) {
        Intrinsics.checkParameterIsNotNull(e, "e");
        if (trySelect((Object) null)) {
            Result.Companion companion = Result.Companion;
            resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(e)));
        } else if (!(e instanceof CancellationException)) {
            Object result = getResult();
            if (!(result instanceof CompletedExceptionally) || StackTraceRecoveryKt.unwrap(((CompletedExceptionally) result).cause) != StackTraceRecoveryKt.unwrap(e)) {
                CoroutineExceptionHandlerKt.handleCoroutineException(getContext(), e);
            }
        }
    }

    public boolean isSelected() {
        return getState() != this;
    }

    public void disposeOnSelect(DisposableHandle handle) {
        Intrinsics.checkParameterIsNotNull(handle, "handle");
        DisposeNode node = new DisposeNode(handle);
        if (!isSelected()) {
            addLast(node);
            if (!isSelected()) {
                return;
            }
        }
        handle.dispose();
    }

    /* access modifiers changed from: private */
    public final void doAfterSelect() {
        DisposableHandle disposableHandle = this.parentHandle;
        if (disposableHandle != null) {
            disposableHandle.dispose();
        }
        Object next = getNext();
        if (next != null) {
            for (LockFreeLinkedListNode cur$iv = (LockFreeLinkedListNode) next; !Intrinsics.areEqual((Object) cur$iv, (Object) this); cur$iv = cur$iv.getNextNode()) {
                if (cur$iv instanceof DisposeNode) {
                    ((DisposeNode) cur$iv).handle.dispose();
                }
            }
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }

    public boolean trySelect(Object idempotent) {
        if (!DebugKt.getASSERTIONS_ENABLED() || ((idempotent instanceof OpDescriptor) ^ 1) != 0) {
            do {
                Object state = getState();
                if (state != this) {
                    if (idempotent != null && state == idempotent) {
                        return true;
                    }
                    return false;
                }
            } while (!_state$FU.compareAndSet(this, this, idempotent));
            doAfterSelect();
            return true;
        }
        throw new AssertionError();
    }

    public Object performAtomicTrySelect(AtomicDesc desc) {
        Intrinsics.checkParameterIsNotNull(desc, "desc");
        return new AtomicSelectOp(this, desc).perform((Object) null);
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0004\u0018\u00002\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\u001c\u0010\u0006\u001a\u00020\u00072\b\u0010\b\u001a\u0004\u0018\u00010\u00022\b\u0010\t\u001a\u0004\u0018\u00010\u0002H\u0016J\u0012\u0010\n\u001a\u00020\u00072\b\u0010\t\u001a\u0004\u0018\u00010\u0002H\u0002J\u0014\u0010\u000b\u001a\u0004\u0018\u00010\u00022\b\u0010\b\u001a\u0004\u0018\u00010\u0002H\u0016J\b\u0010\f\u001a\u0004\u0018\u00010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, mo33671d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$AtomicSelectOp;", "Lkotlinx/coroutines/internal/AtomicOp;", "", "desc", "Lkotlinx/coroutines/internal/AtomicDesc;", "(Lkotlinx/coroutines/selects/SelectBuilderImpl;Lkotlinx/coroutines/internal/AtomicDesc;)V", "complete", "", "affected", "failure", "completeSelect", "prepare", "prepareIfNotSelected", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: Select.kt */
    private final class AtomicSelectOp extends AtomicOp<Object> {
        public final AtomicDesc desc;
        final /* synthetic */ SelectBuilderImpl this$0;

        public AtomicSelectOp(SelectBuilderImpl $outer, AtomicDesc desc2) {
            Intrinsics.checkParameterIsNotNull(desc2, "desc");
            this.this$0 = $outer;
            this.desc = desc2;
        }

        public Object prepare(Object affected) {
            Object it;
            if (affected != null || (it = prepareIfNotSelected()) == null) {
                return this.desc.prepare(this);
            }
            return it;
        }

        public void complete(Object affected, Object failure) {
            completeSelect(failure);
            this.desc.complete(this, failure);
        }

        public final Object prepareIfNotSelected() {
            SelectBuilderImpl $this$loop$iv = this.this$0;
            while (true) {
                Object state = $this$loop$iv._state;
                if (state == this) {
                    return null;
                }
                if (state instanceof OpDescriptor) {
                    ((OpDescriptor) state).perform(this.this$0);
                } else {
                    SelectBuilderImpl selectBuilderImpl = this.this$0;
                    if (state != selectBuilderImpl) {
                        return SelectKt.getALREADY_SELECTED();
                    }
                    if (SelectBuilderImpl._state$FU.compareAndSet(selectBuilderImpl, this.this$0, this)) {
                        return null;
                    }
                }
            }
        }

        private final void completeSelect(Object failure) {
            boolean selectSuccess = failure == null;
            if (SelectBuilderImpl._state$FU.compareAndSet(this.this$0, this, selectSuccess ? null : this.this$0) && selectSuccess) {
                this.this$0.doAfterSelect();
            }
        }
    }

    public void invoke(SelectClause0 $this$invoke, Function1<? super Continuation<? super R>, ? extends Object> block) {
        Intrinsics.checkParameterIsNotNull($this$invoke, "$this$invoke");
        Intrinsics.checkParameterIsNotNull(block, "block");
        $this$invoke.registerSelectClause0(this, block);
    }

    public <Q> void invoke(SelectClause1<? extends Q> $this$invoke, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> block) {
        Intrinsics.checkParameterIsNotNull($this$invoke, "$this$invoke");
        Intrinsics.checkParameterIsNotNull(block, "block");
        $this$invoke.registerSelectClause1(this, block);
    }

    public <P, Q> void invoke(SelectClause2<? super P, ? extends Q> $this$invoke, P param, Function2<? super Q, ? super Continuation<? super R>, ? extends Object> block) {
        Intrinsics.checkParameterIsNotNull($this$invoke, "$this$invoke");
        Intrinsics.checkParameterIsNotNull(block, "block");
        $this$invoke.registerSelectClause2(this, param, block);
    }

    public void onTimeout(long timeMillis, Function1<? super Continuation<? super R>, ? extends Object> block) {
        Intrinsics.checkParameterIsNotNull(block, "block");
        if (timeMillis > 0) {
            disposeOnSelect(DelayKt.getDelay(getContext()).invokeOnTimeout(timeMillis, new SelectBuilderImpl$onTimeout$$inlined$Runnable$1(this, block)));
        } else if (trySelect((Object) null)) {
            UndispatchedKt.startCoroutineUnintercepted(block, getCompletion());
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, mo33671d2 = {"Lkotlinx/coroutines/selects/SelectBuilderImpl$DisposeNode;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "handle", "Lkotlinx/coroutines/DisposableHandle;", "(Lkotlinx/coroutines/DisposableHandle;)V", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: Select.kt */
    private static final class DisposeNode extends LockFreeLinkedListNode {
        public final DisposableHandle handle;

        public DisposeNode(DisposableHandle handle2) {
            Intrinsics.checkParameterIsNotNull(handle2, "handle");
            this.handle = handle2;
        }
    }
}
