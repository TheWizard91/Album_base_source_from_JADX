package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancelHandler;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.channels.ChannelIterator;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.ValueOrClosed;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause1;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u0007IJKLMNOB\u0005¢\u0006\u0002\u0010\u0004J\u0012\u0010\u0016\u001a\u00020\u00062\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0007J\u0016\u0010\u0016\u001a\u00020\u00192\u000e\u0010\u0017\u001a\n\u0018\u00010\u001aj\u0004\u0018\u0001`\u001bJ\u0017\u0010\u001c\u001a\u00020\u00062\b\u0010\u0017\u001a\u0004\u0018\u00010\u0018H\u0010¢\u0006\u0002\b\u001dJ\b\u0010\u001e\u001a\u00020\u0019H\u0014J\u000e\u0010\u001f\u001a\b\u0012\u0004\u0012\u00028\u00000 H\u0004J\u0016\u0010!\u001a\u00020\u00062\f\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000#H\u0002JR\u0010$\u001a\u00020\u0006\"\u0004\b\u0001\u0010%2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H%0'2$\u0010(\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010*\u0012\n\u0012\b\u0012\u0004\u0012\u0002H%0+\u0012\u0006\u0012\u0004\u0018\u00010*0)2\u0006\u0010,\u001a\u00020-H\u0002ø\u0001\u0000¢\u0006\u0002\u0010.J\u000f\u0010/\u001a\b\u0012\u0004\u0012\u00028\u000000H\u0002J\b\u00101\u001a\u00020\u0019H\u0014J\b\u00102\u001a\u00020\u0019H\u0014J\r\u00103\u001a\u0004\u0018\u00018\u0000¢\u0006\u0002\u00104J\n\u00105\u001a\u0004\u0018\u00010*H\u0014J\u0016\u00106\u001a\u0004\u0018\u00010*2\n\u0010&\u001a\u0006\u0012\u0002\b\u00030'H\u0014J\u0011\u0010\"\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0002\u00107J\u001a\u00108\u001a\b\u0012\u0004\u0012\u00028\u00000\u0012H@ø\u0001\u0000ø\u0001\u0000¢\u0006\u0002\u00107J\u0013\u00109\u001a\u0004\u0018\u00018\u0000H@ø\u0001\u0000¢\u0006\u0002\u00107J\u0019\u0010:\u001a\u0004\u0018\u00018\u00002\b\u0010;\u001a\u0004\u0018\u00010*H\u0002¢\u0006\u0002\u0010<J\u0017\u0010=\u001a\u00028\u00002\b\u0010;\u001a\u0004\u0018\u00010*H\u0002¢\u0006\u0002\u0010<J\u001f\u0010>\u001a\u0002H%\"\u0004\b\u0001\u0010%2\u0006\u0010,\u001a\u00020-H@ø\u0001\u0000¢\u0006\u0002\u0010?JH\u0010@\u001a\u00020\u0019\"\u0004\b\u0001\u0010%2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H%0'2\"\u0010(\u001a\u001e\b\u0001\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u0002H%0+\u0012\u0006\u0012\u0004\u0018\u00010*0)H\u0002ø\u0001\u0000¢\u0006\u0002\u0010AJQ\u0010B\u001a\u00020\u0019\"\u0004\b\u0001\u0010%2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H%0'2(\u0010(\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0012\u0012\n\u0012\b\u0012\u0004\u0012\u0002H%0+\u0012\u0006\u0012\u0004\u0018\u00010*0)H\u0002ø\u0001\u0000ø\u0001\u0000¢\u0006\u0002\u0010AJJ\u0010C\u001a\u00020\u0019\"\u0004\b\u0001\u0010%2\f\u0010&\u001a\b\u0012\u0004\u0012\u0002H%0'2$\u0010(\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00018\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u0002H%0+\u0012\u0006\u0012\u0004\u0018\u00010*0)H\u0002ø\u0001\u0000¢\u0006\u0002\u0010AJ \u0010D\u001a\u00020\u00192\n\u0010E\u001a\u0006\u0012\u0002\b\u00030F2\n\u0010\"\u001a\u0006\u0012\u0002\b\u00030#H\u0002J\u0010\u0010G\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010HH\u0014R\u0014\u0010\u0005\u001a\u00020\u00068DX\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\bR\u0012\u0010\t\u001a\u00020\u0006X¤\u0004¢\u0006\u0006\u001a\u0004\b\t\u0010\bR\u0012\u0010\n\u001a\u00020\u0006X¤\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\bR\u0011\u0010\u000b\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\u000b\u0010\bR\u0011\u0010\f\u001a\u00020\u00068F¢\u0006\u0006\u001a\u0004\b\f\u0010\bR\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00028\u00000\u000e8F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0010R#\u0010\u0011\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00120\u000e8VX\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0010R\u0019\u0010\u0014\u001a\n\u0012\u0006\u0012\u0004\u0018\u00018\u00000\u000e8F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0010\u0002\u0004\n\u0002\b\u0019¨\u0006P"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractChannel;", "E", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/Channel;", "()V", "hasReceiveOrClosed", "", "getHasReceiveOrClosed", "()Z", "isBufferAlwaysEmpty", "isBufferEmpty", "isClosedForReceive", "isEmpty", "onReceive", "Lkotlinx/coroutines/selects/SelectClause1;", "getOnReceive", "()Lkotlinx/coroutines/selects/SelectClause1;", "onReceiveOrClosed", "Lkotlinx/coroutines/channels/ValueOrClosed;", "getOnReceiveOrClosed", "onReceiveOrNull", "getOnReceiveOrNull", "cancel", "cause", "", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "cancelInternal$kotlinx_coroutines_core", "cleanupSendQueueOnCancel", "describeTryPoll", "Lkotlinx/coroutines/channels/AbstractChannel$TryPollDesc;", "enqueueReceive", "receive", "Lkotlinx/coroutines/channels/Receive;", "enqueueReceiveSelect", "R", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "", "Lkotlin/coroutines/Continuation;", "receiveMode", "", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;I)Z", "iterator", "Lkotlinx/coroutines/channels/ChannelIterator;", "onReceiveDequeued", "onReceiveEnqueued", "poll", "()Ljava/lang/Object;", "pollInternal", "pollSelectInternal", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "receiveOrClosed", "receiveOrNull", "receiveOrNullResult", "result", "(Ljava/lang/Object;)Ljava/lang/Object;", "receiveResult", "receiveSuspend", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "registerSelectReceive", "(Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "registerSelectReceiveOrClosed", "registerSelectReceiveOrNull", "removeReceiveOnCancel", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "takeFirstReceiveOrPeekClosed", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "IdempotentTokenValue", "Itr", "ReceiveElement", "ReceiveHasNext", "ReceiveSelect", "RemoveReceiveOnCancel", "TryPollDesc", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: AbstractChannel.kt */
public abstract class AbstractChannel<E> extends AbstractSendChannel<E> implements Channel<E> {
    /* access modifiers changed from: protected */
    public abstract boolean isBufferAlwaysEmpty();

    /* access modifiers changed from: protected */
    public abstract boolean isBufferEmpty();

    @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.2.0, binary compatibility with versions <= 1.1.x")
    public /* synthetic */ void cancel() {
        ReceiveChannel.DefaultImpls.cancel(this);
    }

    /* access modifiers changed from: protected */
    public Object pollInternal() {
        Send send;
        Object token;
        do {
            send = takeFirstSendOrPeekClosed();
            if (send == null) {
                return AbstractChannelKt.POLL_FAILED;
            }
            token = send.tryResumeSend((Object) null);
        } while (token == null);
        send.completeResumeSend(token);
        return send.getPollResult();
    }

    /* access modifiers changed from: protected */
    public Object pollSelectInternal(SelectInstance<?> select) {
        Intrinsics.checkParameterIsNotNull(select, "select");
        TryPollDesc pollOp = describeTryPoll();
        Object failure = select.performAtomicTrySelect(pollOp);
        if (failure != null) {
            return failure;
        }
        Send send = (Send) pollOp.getResult();
        Object obj = pollOp.resumeToken;
        if (obj == null) {
            Intrinsics.throwNpe();
        }
        send.completeResumeSend(obj);
        return pollOp.pollResult;
    }

    /* access modifiers changed from: protected */
    public final boolean getHasReceiveOrClosed() {
        return getQueue().getNextNode() instanceof ReceiveOrClosed;
    }

    public final boolean isClosedForReceive() {
        return getClosedForReceive() != null && isBufferEmpty();
    }

    public final boolean isEmpty() {
        return !(getQueue().getNextNode() instanceof Send) && isBufferEmpty();
    }

    public final Object receive(Continuation<? super E> $completion) {
        Object result = pollInternal();
        if (result != AbstractChannelKt.POLL_FAILED) {
            return receiveResult(result);
        }
        return receiveSuspend(0, $completion);
    }

    private final E receiveResult(Object result) {
        if (!(result instanceof Closed)) {
            return result;
        }
        throw StackTraceRecoveryKt.recoverStackTrace(((Closed) result).getReceiveException());
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ <R> Object receiveSuspend(int receiveMode, Continuation<? super R> $completion) {
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 0);
        CancellableContinuation cont = cancellable$iv;
        ReceiveElement receive = new ReceiveElement(cont, receiveMode);
        while (true) {
            if (!enqueueReceive(receive)) {
                Object result = pollInternal();
                if (!(result instanceof Closed)) {
                    if (result != AbstractChannelKt.POLL_FAILED) {
                        Object resumeValue = receive.resumeValue(result);
                        Result.Companion companion = Result.Companion;
                        cont.resumeWith(Result.m1289constructorimpl(resumeValue));
                        break;
                    }
                } else {
                    receive.resumeReceiveClosed((Closed) result);
                    break;
                }
            } else {
                removeReceiveOnCancel(cont, receive);
                break;
            }
        }
        Object result2 = cancellable$iv.getResult();
        if (result2 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result2;
    }

    /* access modifiers changed from: private */
    public final boolean enqueueReceive(Receive<? super E> receive) {
        boolean z = false;
        if (isBufferAlwaysEmpty()) {
            LockFreeLinkedListNode this_$iv = getQueue();
            while (true) {
                Object prev = this_$iv.getPrev();
                if (prev != null) {
                    LockFreeLinkedListNode prev$iv = (LockFreeLinkedListNode) prev;
                    if (!(prev$iv instanceof Send)) {
                        if (prev$iv.addNext(receive, this_$iv)) {
                            z = true;
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
                }
            }
        } else {
            LockFreeLinkedListHead queue = getQueue();
            LockFreeLinkedListHead lockFreeLinkedListHead = queue;
            LockFreeLinkedListNode.CondAddOp condAdd$iv = new AbstractChannel$enqueueReceive$$inlined$addLastIfPrevAndIf$1(receive, receive, this);
            while (true) {
                Object prev2 = queue.getPrev();
                if (prev2 != null) {
                    LockFreeLinkedListNode prev$iv2 = (LockFreeLinkedListNode) prev2;
                    if (!(!(prev$iv2 instanceof Send))) {
                        break;
                    }
                    int tryCondAddNext = prev$iv2.tryCondAddNext(receive, queue, condAdd$iv);
                    if (tryCondAddNext != 1) {
                        if (tryCondAddNext == 2) {
                            break;
                        }
                    } else {
                        z = true;
                        break;
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
                }
            }
        }
        boolean result = z;
        if (result) {
            onReceiveEnqueued();
        }
        return result;
    }

    public final Object receiveOrNull(Continuation<? super E> $completion) {
        Object result = pollInternal();
        if (result != AbstractChannelKt.POLL_FAILED) {
            return receiveOrNullResult(result);
        }
        return receiveSuspend(1, $completion);
    }

    private final E receiveOrNullResult(Object result) {
        if (!(result instanceof Closed)) {
            return result;
        }
        if (((Closed) result).closeCause == null) {
            return null;
        }
        throw StackTraceRecoveryKt.recoverStackTrace(((Closed) result).closeCause);
    }

    public final Object receiveOrClosed(Continuation<? super ValueOrClosed<? extends E>> $completion) {
        Object obj;
        Object result = pollInternal();
        if (result == AbstractChannelKt.POLL_FAILED) {
            return receiveSuspend(2, $completion);
        }
        Object $this$toResult$iv = result;
        if ($this$toResult$iv instanceof Closed) {
            ValueOrClosed.Companion companion = ValueOrClosed.Companion;
            obj = ValueOrClosed.m2298constructorimpl(new ValueOrClosed.Closed(((Closed) $this$toResult$iv).closeCause));
        } else {
            ValueOrClosed.Companion companion2 = ValueOrClosed.Companion;
            obj = ValueOrClosed.m2298constructorimpl($this$toResult$iv);
        }
        return ValueOrClosed.m2297boximpl(obj);
    }

    public final E poll() {
        Object result = pollInternal();
        if (result == AbstractChannelKt.POLL_FAILED) {
            return null;
        }
        return receiveOrNullResult(result);
    }

    public final void cancel(CancellationException cause) {
        cancel(cause != null ? cause : new CancellationException(DebugStringsKt.getClassSimpleName(this) + " was cancelled"));
    }

    /* renamed from: cancelInternal$kotlinx_coroutines_core */
    public boolean cancel(Throwable cause) {
        boolean close = close(cause);
        boolean z = close;
        cleanupSendQueueOnCancel();
        return close;
    }

    /* access modifiers changed from: protected */
    public void cleanupSendQueueOnCancel() {
        Closed closed = getClosedForSend();
        if (closed != null) {
            while (true) {
                Send send = takeFirstSendOrPeekClosed();
                if (send == null) {
                    throw new IllegalStateException("Cannot happen".toString());
                } else if (!(send instanceof Closed)) {
                    send.resumeSendClosed(closed);
                } else if (DebugKt.getASSERTIONS_ENABLED()) {
                    if (!(send == closed)) {
                        throw new AssertionError();
                    }
                    return;
                } else {
                    return;
                }
            }
        } else {
            throw new IllegalStateException("Cannot happen".toString());
        }
    }

    public final ChannelIterator<E> iterator() {
        return new Itr<>(this);
    }

    /* access modifiers changed from: protected */
    public final TryPollDesc<E> describeTryPoll() {
        return new TryPollDesc<>(getQueue());
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0004\u0018\u0000*\u0004\b\u0001\u0010\u00012\u0012\u0012\u0004\u0012\u00020\u00030\u0002j\b\u0012\u0004\u0012\u00020\u0003`\u0004B\r\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\u0012\u0010\f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0014J\u0010\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u0003H\u0014R\u0016\u0010\b\u001a\u0004\u0018\u00018\u00018\u0006@\u0006X\u000e¢\u0006\u0004\n\u0002\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$TryPollDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/internal/RemoveFirstDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;)V", "pollResult", "Ljava/lang/Object;", "resumeToken", "", "failure", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "validatePrepared", "", "node", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: AbstractChannel.kt */
    protected static final class TryPollDesc<E> extends LockFreeLinkedListNode.RemoveFirstDesc<Send> {
        public E pollResult;
        public Object resumeToken;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public TryPollDesc(LockFreeLinkedListHead queue) {
            super(queue);
            Intrinsics.checkParameterIsNotNull(queue, "queue");
        }

        /* access modifiers changed from: protected */
        public Object failure(LockFreeLinkedListNode affected) {
            Intrinsics.checkParameterIsNotNull(affected, "affected");
            if (affected instanceof Closed) {
                return affected;
            }
            if (!(affected instanceof Send)) {
                return AbstractChannelKt.POLL_FAILED;
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public boolean validatePrepared(Send node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            Object token = node.tryResumeSend(this);
            if (token == null) {
                return false;
            }
            this.resumeToken = token;
            this.pollResult = node.getPollResult();
            return true;
        }
    }

    public final SelectClause1<E> getOnReceive() {
        return new AbstractChannel$onReceive$1(this);
    }

    /* access modifiers changed from: private */
    public final <R> void registerSelectReceive(SelectInstance<? super R> select, Function2<? super E, ? super Continuation<? super R>, ? extends Object> block) {
        while (!select.isSelected()) {
            if (!isEmpty()) {
                Object pollResult = pollSelectInternal(select);
                if (pollResult != SelectKt.getALREADY_SELECTED()) {
                    if (pollResult != AbstractChannelKt.POLL_FAILED) {
                        if (!(pollResult instanceof Closed)) {
                            UndispatchedKt.startCoroutineUnintercepted(block, pollResult, select.getCompletion());
                            return;
                        }
                        throw StackTraceRecoveryKt.recoverStackTrace(((Closed) pollResult).getReceiveException());
                    }
                } else {
                    return;
                }
            } else if (block == null) {
                throw new TypeCastException("null cannot be cast to non-null type suspend (kotlin.Any?) -> R");
            } else if (enqueueReceiveSelect(select, block, 0)) {
                return;
            }
        }
    }

    public final SelectClause1<E> getOnReceiveOrNull() {
        return new AbstractChannel$onReceiveOrNull$1(this);
    }

    /* access modifiers changed from: private */
    public final <R> void registerSelectReceiveOrNull(SelectInstance<? super R> select, Function2<? super E, ? super Continuation<? super R>, ? extends Object> block) {
        while (!select.isSelected()) {
            if (!isEmpty()) {
                Object pollResult = pollSelectInternal(select);
                if (pollResult != SelectKt.getALREADY_SELECTED()) {
                    if (pollResult != AbstractChannelKt.POLL_FAILED) {
                        if (!(pollResult instanceof Closed)) {
                            UndispatchedKt.startCoroutineUnintercepted(block, pollResult, select.getCompletion());
                            return;
                        } else if (((Closed) pollResult).closeCause != null) {
                            throw StackTraceRecoveryKt.recoverStackTrace(((Closed) pollResult).closeCause);
                        } else if (select.trySelect((Object) null)) {
                            UndispatchedKt.startCoroutineUnintercepted(block, null, select.getCompletion());
                            return;
                        } else {
                            return;
                        }
                    }
                } else {
                    return;
                }
            } else if (block == null) {
                throw new TypeCastException("null cannot be cast to non-null type suspend (kotlin.Any?) -> R");
            } else if (enqueueReceiveSelect(select, block, 1)) {
                return;
            }
        }
    }

    public SelectClause1<ValueOrClosed<E>> getOnReceiveOrClosed() {
        return new AbstractChannel$onReceiveOrClosed$1(this);
    }

    /* access modifiers changed from: private */
    public final <R> void registerSelectReceiveOrClosed(SelectInstance<? super R> select, Function2<? super ValueOrClosed<? extends E>, ? super Continuation<? super R>, ? extends Object> block) {
        while (!select.isSelected()) {
            if (!isEmpty()) {
                Object pollResult = pollSelectInternal(select);
                if (pollResult != SelectKt.getALREADY_SELECTED()) {
                    if (pollResult == AbstractChannelKt.POLL_FAILED) {
                        continue;
                    } else if (pollResult instanceof Closed) {
                        ValueOrClosed.Companion companion = ValueOrClosed.Companion;
                        UndispatchedKt.startCoroutineUnintercepted(block, ValueOrClosed.m2297boximpl(ValueOrClosed.m2298constructorimpl(new ValueOrClosed.Closed(((Closed) pollResult).closeCause))), select.getCompletion());
                    } else {
                        ValueOrClosed.Companion companion2 = ValueOrClosed.Companion;
                        UndispatchedKt.startCoroutineUnintercepted(block, ValueOrClosed.m2297boximpl(ValueOrClosed.m2298constructorimpl(pollResult)), select.getCompletion());
                        return;
                    }
                } else {
                    return;
                }
            } else if (block == null) {
                throw new TypeCastException("null cannot be cast to non-null type suspend (kotlin.Any?) -> R");
            } else if (enqueueReceiveSelect(select, block, 2)) {
                return;
            }
        }
    }

    private final <R> boolean enqueueReceiveSelect(SelectInstance<? super R> select, Function2<Object, ? super Continuation<? super R>, ? extends Object> block, int receiveMode) {
        ReceiveSelect node = new ReceiveSelect(this, select, block, receiveMode);
        boolean result = enqueueReceive(node);
        if (result) {
            select.disposeOnSelect(node);
        }
        return result;
    }

    /* access modifiers changed from: protected */
    public ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed() {
        ReceiveOrClosed takeFirstReceiveOrPeekClosed = super.takeFirstReceiveOrPeekClosed();
        ReceiveOrClosed it = takeFirstReceiveOrPeekClosed;
        if (it != null && !(it instanceof Closed)) {
            onReceiveDequeued();
        }
        return takeFirstReceiveOrPeekClosed;
    }

    /* access modifiers changed from: protected */
    public void onReceiveEnqueued() {
    }

    /* access modifiers changed from: protected */
    public void onReceiveDequeued() {
    }

    /* access modifiers changed from: private */
    public final void removeReceiveOnCancel(CancellableContinuation<?> cont, Receive<?> receive) {
        cont.invokeOnCancellation(new RemoveReceiveOnCancel(this, receive));
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0004\u0018\u00002\u00020\u0001B\u0011\u0012\n\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003¢\u0006\u0002\u0010\u0004J\u0013\u0010\u0005\u001a\u00020\u00062\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0002J\b\u0010\t\u001a\u00020\nH\u0016R\u0012\u0010\u0002\u001a\u0006\u0012\u0002\b\u00030\u0003X\u0004¢\u0006\u0002\n\u0000¨\u0006\u000b"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$RemoveReceiveOnCancel;", "Lkotlinx/coroutines/CancelHandler;", "receive", "Lkotlinx/coroutines/channels/Receive;", "(Lkotlinx/coroutines/channels/AbstractChannel;Lkotlinx/coroutines/channels/Receive;)V", "invoke", "", "cause", "", "toString", "", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: AbstractChannel.kt */
    private final class RemoveReceiveOnCancel extends CancelHandler {
        private final Receive<?> receive;
        final /* synthetic */ AbstractChannel this$0;

        public RemoveReceiveOnCancel(AbstractChannel $outer, Receive<?> receive2) {
            Intrinsics.checkParameterIsNotNull(receive2, "receive");
            this.this$0 = $outer;
            this.receive = receive2;
        }

        public /* bridge */ /* synthetic */ Object invoke(Object obj) {
            invoke((Throwable) obj);
            return Unit.INSTANCE;
        }

        public void invoke(Throwable cause) {
            if (this.receive.remove()) {
                this.this$0.onReceiveDequeued();
            }
        }

        public String toString() {
            return "RemoveReceiveOnCancel[" + this.receive + ']';
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0005\n\u0002\u0010\u000b\n\u0002\b\u0005\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0013\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004¢\u0006\u0002\u0010\u0005J\u0011\u0010\u000e\u001a\u00020\u000fHBø\u0001\u0000¢\u0006\u0002\u0010\u0010J\u0012\u0010\u0011\u001a\u00020\u000f2\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0002J\u0011\u0010\u0012\u001a\u00020\u000fH@ø\u0001\u0000¢\u0006\u0002\u0010\u0010J\u000e\u0010\u0013\u001a\u00028\u0001H\u0002¢\u0006\u0002\u0010\u000bR\u0017\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001c\u0010\b\u001a\u0004\u0018\u00010\tX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r\u0002\u0004\n\u0002\b\u0019¨\u0006\u0014"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$Itr;", "E", "Lkotlinx/coroutines/channels/ChannelIterator;", "channel", "Lkotlinx/coroutines/channels/AbstractChannel;", "(Lkotlinx/coroutines/channels/AbstractChannel;)V", "getChannel", "()Lkotlinx/coroutines/channels/AbstractChannel;", "result", "", "getResult", "()Ljava/lang/Object;", "setResult", "(Ljava/lang/Object;)V", "hasNext", "", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "hasNextResult", "hasNextSuspend", "next", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: AbstractChannel.kt */
    private static final class Itr<E> implements ChannelIterator<E> {
        private final AbstractChannel<E> channel;
        private Object result = AbstractChannelKt.POLL_FAILED;

        public Itr(AbstractChannel<E> channel2) {
            Intrinsics.checkParameterIsNotNull(channel2, "channel");
            this.channel = channel2;
        }

        public final AbstractChannel<E> getChannel() {
            return this.channel;
        }

        @Deprecated(level = DeprecationLevel.HIDDEN, message = "Since 1.3.0, binary compatibility with versions <= 1.2.x")
        public /* synthetic */ Object next(Continuation<? super E> continuation) {
            return ChannelIterator.DefaultImpls.next(this, continuation);
        }

        public final Object getResult() {
            return this.result;
        }

        public final void setResult(Object obj) {
            this.result = obj;
        }

        public Object hasNext(Continuation<? super Boolean> $completion) {
            if (this.result != AbstractChannelKt.POLL_FAILED) {
                return Boxing.boxBoolean(hasNextResult(this.result));
            }
            Object pollInternal = this.channel.pollInternal();
            this.result = pollInternal;
            if (pollInternal != AbstractChannelKt.POLL_FAILED) {
                return Boxing.boxBoolean(hasNextResult(this.result));
            }
            return hasNextSuspend($completion);
        }

        private final boolean hasNextResult(Object result2) {
            if (!(result2 instanceof Closed)) {
                return true;
            }
            if (((Closed) result2).closeCause == null) {
                return false;
            }
            throw StackTraceRecoveryKt.recoverStackTrace(((Closed) result2).getReceiveException());
        }

        /* access modifiers changed from: package-private */
        public final /* synthetic */ Object hasNextSuspend(Continuation<? super Boolean> $completion) {
            CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 0);
            CancellableContinuation cont = cancellable$iv;
            ReceiveHasNext receive = new ReceiveHasNext(this, cont);
            while (true) {
                if (!getChannel().enqueueReceive(receive)) {
                    Object result2 = getChannel().pollInternal();
                    setResult(result2);
                    if (!(result2 instanceof Closed)) {
                        if (result2 != AbstractChannelKt.POLL_FAILED) {
                            Boolean boxBoolean = Boxing.boxBoolean(true);
                            Result.Companion companion = Result.Companion;
                            cont.resumeWith(Result.m1289constructorimpl(boxBoolean));
                            break;
                        }
                    } else if (((Closed) result2).closeCause == null) {
                        Boolean boxBoolean2 = Boxing.boxBoolean(false);
                        Result.Companion companion2 = Result.Companion;
                        cont.resumeWith(Result.m1289constructorimpl(boxBoolean2));
                    } else {
                        Throwable receiveException = ((Closed) result2).getReceiveException();
                        Result.Companion companion3 = Result.Companion;
                        cont.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(receiveException)));
                    }
                } else {
                    getChannel().removeReceiveOnCancel(cont, receive);
                    break;
                }
            }
            Object result3 = cancellable$iv.getResult();
            if (result3 == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
                DebugProbesKt.probeCoroutineSuspended($completion);
            }
            return result3;
        }

        public E next() {
            E e = this.result;
            if (e instanceof Closed) {
                throw StackTraceRecoveryKt.recoverStackTrace(((Closed) e).getReceiveException());
            } else if (e != AbstractChannelKt.POLL_FAILED) {
                this.result = AbstractChannelKt.POLL_FAILED;
                return e;
            } else {
                throw new IllegalStateException("'hasNext' should be called prior to 'next' invocation");
            }
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0004\b\u0002\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00002\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u001d\u0012\u000e\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0004\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\u0005H\u0016J\u0014\u0010\f\u001a\u00020\n2\n\u0010\r\u001a\u0006\u0012\u0002\b\u00030\u000eH\u0016J\u0015\u0010\u000f\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0010\u001a\u00028\u0001¢\u0006\u0002\u0010\u0011J\b\u0010\u0012\u001a\u00020\u0013H\u0016J!\u0010\u0014\u001a\u0004\u0018\u00010\u00052\u0006\u0010\u0010\u001a\u00028\u00012\b\u0010\u0015\u001a\u0004\u0018\u00010\u0005H\u0016¢\u0006\u0002\u0010\u0016R\u0018\u0010\u0003\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00048\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\u0006\u001a\u00020\u00078\u0006X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u0017"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveElement;", "E", "Lkotlinx/coroutines/channels/Receive;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "receiveMode", "", "(Lkotlinx/coroutines/CancellableContinuation;I)V", "completeResumeReceive", "", "token", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "resumeValue", "value", "(Ljava/lang/Object;)Ljava/lang/Object;", "toString", "", "tryResumeReceive", "idempotent", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: AbstractChannel.kt */
    private static final class ReceiveElement<E> extends Receive<E> {
        public final CancellableContinuation<Object> cont;
        public final int receiveMode;

        public ReceiveElement(CancellableContinuation<Object> cont2, int receiveMode2) {
            Intrinsics.checkParameterIsNotNull(cont2, "cont");
            this.cont = cont2;
            this.receiveMode = receiveMode2;
        }

        public final Object resumeValue(E value) {
            if (this.receiveMode != 2) {
                return value;
            }
            ValueOrClosed.Companion companion = ValueOrClosed.Companion;
            return ValueOrClosed.m2297boximpl(ValueOrClosed.m2298constructorimpl(value));
        }

        public Object tryResumeReceive(E value, Object idempotent) {
            return this.cont.tryResume(resumeValue(value), idempotent);
        }

        public void completeResumeReceive(Object token) {
            Intrinsics.checkParameterIsNotNull(token, "token");
            this.cont.completeResume(token);
        }

        public void resumeReceiveClosed(Closed<?> closed) {
            Intrinsics.checkParameterIsNotNull(closed, "closed");
            if (this.receiveMode == 1 && closed.closeCause == null) {
                Result.Companion companion = Result.Companion;
                this.cont.resumeWith(Result.m1289constructorimpl((Object) null));
            } else if (this.receiveMode == 2) {
                ValueOrClosed.Companion companion2 = ValueOrClosed.Companion;
                ValueOrClosed r1 = ValueOrClosed.m2297boximpl(ValueOrClosed.m2298constructorimpl(new ValueOrClosed.Closed(closed.closeCause)));
                Result.Companion companion3 = Result.Companion;
                this.cont.resumeWith(Result.m1289constructorimpl(r1));
            } else {
                Throwable receiveException = closed.getReceiveException();
                Result.Companion companion4 = Result.Companion;
                this.cont.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(receiveException)));
            }
        }

        public String toString() {
            return "ReceiveElement[receiveMode=" + this.receiveMode + ']';
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000:\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B!\u0012\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u0004\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006¢\u0006\u0002\u0010\bJ\u0010\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0016J\u0014\u0010\r\u001a\u00020\n2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0016J\b\u0010\u0010\u001a\u00020\u0011H\u0016J!\u0010\u0012\u001a\u0004\u0018\u00010\f2\u0006\u0010\u0013\u001a\u00028\u00012\b\u0010\u0014\u001a\u0004\u0018\u00010\fH\u0016¢\u0006\u0002\u0010\u0015R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0003\u001a\b\u0012\u0004\u0012\u00028\u00010\u00048\u0006X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u0016"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveHasNext;", "E", "Lkotlinx/coroutines/channels/Receive;", "iterator", "Lkotlinx/coroutines/channels/AbstractChannel$Itr;", "cont", "Lkotlinx/coroutines/CancellableContinuation;", "", "(Lkotlinx/coroutines/channels/AbstractChannel$Itr;Lkotlinx/coroutines/CancellableContinuation;)V", "completeResumeReceive", "", "token", "", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeReceive", "value", "idempotent", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: AbstractChannel.kt */
    private static final class ReceiveHasNext<E> extends Receive<E> {
        public final CancellableContinuation<Boolean> cont;
        public final Itr<E> iterator;

        public ReceiveHasNext(Itr<E> iterator2, CancellableContinuation<? super Boolean> cont2) {
            Intrinsics.checkParameterIsNotNull(iterator2, "iterator");
            Intrinsics.checkParameterIsNotNull(cont2, "cont");
            this.iterator = iterator2;
            this.cont = cont2;
        }

        public Object tryResumeReceive(E value, Object idempotent) {
            Object token = this.cont.tryResume(true, idempotent);
            if (token != null) {
                if (idempotent != null) {
                    return new IdempotentTokenValue(token, value);
                }
                this.iterator.setResult(value);
            }
            return token;
        }

        public void completeResumeReceive(Object token) {
            Intrinsics.checkParameterIsNotNull(token, "token");
            if (token instanceof IdempotentTokenValue) {
                this.iterator.setResult(((IdempotentTokenValue) token).value);
                this.cont.completeResume(((IdempotentTokenValue) token).token);
                return;
            }
            this.cont.completeResume(token);
        }

        public void resumeReceiveClosed(Closed<?> closed) {
            Object token;
            Intrinsics.checkParameterIsNotNull(closed, "closed");
            if (closed.closeCause == null) {
                token = CancellableContinuation.DefaultImpls.tryResume$default(this.cont, false, (Object) null, 2, (Object) null);
            } else {
                token = this.cont.tryResumeWithException(StackTraceRecoveryKt.recoverStackTrace(closed.getReceiveException(), this.cont));
            }
            if (token != null) {
                this.iterator.setResult(closed);
                this.cont.completeResume(token);
            }
        }

        public String toString() {
            return "ReceiveHasNext";
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000J\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u0001*\u0004\b\u0002\u0010\u00022\b\u0012\u0004\u0012\u0002H\u00020\u00032\u00020\u0004BR\u0012\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00020\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\b\u0012$\u0010\t\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n\u0012\u0006\u0010\r\u001a\u00020\u000eø\u0001\u0000¢\u0006\u0002\u0010\u000fJ\u0010\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000bH\u0016J\b\u0010\u0014\u001a\u00020\u0012H\u0016J\u0014\u0010\u0015\u001a\u00020\u00122\n\u0010\u0016\u001a\u0006\u0012\u0002\b\u00030\u0017H\u0016J\b\u0010\u0018\u001a\u00020\u0019H\u0016J!\u0010\u001a\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\u001b\u001a\u00028\u00022\b\u0010\u001c\u001a\u0004\u0018\u00010\u000bH\u0016¢\u0006\u0002\u0010\u001dR3\u0010\t\u001a \b\u0001\u0012\u0006\u0012\u0004\u0018\u00010\u000b\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\f\u0012\u0006\u0012\u0004\u0018\u00010\u000b0\n8\u0006X\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u0010R\u0016\u0010\u0005\u001a\b\u0012\u0004\u0012\u00028\u00020\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R\u0010\u0010\r\u001a\u00020\u000e8\u0006X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\b8\u0006X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u001e"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$ReceiveSelect;", "R", "E", "Lkotlinx/coroutines/channels/Receive;", "Lkotlinx/coroutines/DisposableHandle;", "channel", "Lkotlinx/coroutines/channels/AbstractChannel;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "", "Lkotlin/coroutines/Continuation;", "receiveMode", "", "(Lkotlinx/coroutines/channels/AbstractChannel;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;I)V", "Lkotlin/jvm/functions/Function2;", "completeResumeReceive", "", "token", "dispose", "resumeReceiveClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeReceive", "value", "idempotent", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: AbstractChannel.kt */
    private static final class ReceiveSelect<R, E> extends Receive<E> implements DisposableHandle {
        public final Function2<Object, Continuation<? super R>, Object> block;
        public final AbstractChannel<E> channel;
        public final int receiveMode;
        public final SelectInstance<R> select;

        public ReceiveSelect(AbstractChannel<E> channel2, SelectInstance<? super R> select2, Function2<Object, ? super Continuation<? super R>, ? extends Object> block2, int receiveMode2) {
            Intrinsics.checkParameterIsNotNull(channel2, "channel");
            Intrinsics.checkParameterIsNotNull(select2, "select");
            Intrinsics.checkParameterIsNotNull(block2, "block");
            this.channel = channel2;
            this.select = select2;
            this.block = block2;
            this.receiveMode = receiveMode2;
        }

        public Object tryResumeReceive(E value, Object idempotent) {
            if (this.select.trySelect(idempotent)) {
                return value != null ? value : AbstractChannelKt.NULL_VALUE;
            }
            return null;
        }

        public void completeResumeReceive(Object token) {
            Object obj;
            Intrinsics.checkParameterIsNotNull(token, "token");
            Object value = token == AbstractChannelKt.NULL_VALUE ? null : token;
            Function2<Object, Continuation<? super R>, Object> function2 = this.block;
            if (this.receiveMode == 2) {
                ValueOrClosed.Companion companion = ValueOrClosed.Companion;
                obj = ValueOrClosed.m2297boximpl(ValueOrClosed.m2298constructorimpl(value));
            } else {
                obj = value;
            }
            ContinuationKt.startCoroutine(function2, obj, this.select.getCompletion());
        }

        public void resumeReceiveClosed(Closed<?> closed) {
            Intrinsics.checkParameterIsNotNull(closed, "closed");
            if (this.select.trySelect((Object) null)) {
                int i = this.receiveMode;
                if (i == 0) {
                    this.select.resumeSelectCancellableWithException(closed.getReceiveException());
                } else if (i != 1) {
                    if (i == 2) {
                        Function2<Object, Continuation<? super R>, Object> function2 = this.block;
                        ValueOrClosed.Companion companion = ValueOrClosed.Companion;
                        ContinuationKt.startCoroutine(function2, ValueOrClosed.m2297boximpl(ValueOrClosed.m2298constructorimpl(new ValueOrClosed.Closed(closed.closeCause))), this.select.getCompletion());
                    }
                } else if (closed.closeCause == null) {
                    ContinuationKt.startCoroutine(this.block, null, this.select.getCompletion());
                } else {
                    this.select.resumeSelectCancellableWithException(closed.getReceiveException());
                }
            }
        }

        public void dispose() {
            if (remove()) {
                this.channel.onReceiveDequeued();
            }
        }

        public String toString() {
            return "ReceiveSelect[" + this.select + ",receiveMode=" + this.receiveMode + ']';
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u000e\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0005\b\u0002\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\u00020\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0002\u0012\u0006\u0010\u0004\u001a\u00028\u0001¢\u0006\u0002\u0010\u0005R\u0010\u0010\u0003\u001a\u00020\u00028\u0006X\u0004¢\u0006\u0002\n\u0000R\u0012\u0010\u0004\u001a\u00028\u00018\u0006X\u0004¢\u0006\u0004\n\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractChannel$IdempotentTokenValue;", "E", "", "token", "value", "(Ljava/lang/Object;Ljava/lang/Object;)V", "Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: AbstractChannel.kt */
    private static final class IdempotentTokenValue<E> {
        public final Object token;
        public final E value;

        public IdempotentTokenValue(Object token2, E value2) {
            Intrinsics.checkParameterIsNotNull(token2, "token");
            this.token = token2;
            this.value = value2;
        }
    }
}
