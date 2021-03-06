package kotlinx.coroutines.channels;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.ContinuationKt;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugProbesKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.CancellableContinuationImpl;
import kotlinx.coroutines.CancellableContinuationKt;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.DisposableHandle;
import kotlinx.coroutines.YieldKt;
import kotlinx.coroutines.internal.LockFreeLinkedListHead;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.internal.StackTraceRecoveryKt;
import kotlinx.coroutines.intrinsics.UndispatchedKt;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0001\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000e\n\u0002\b\u0010\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\f\b \u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u00028\u00000\u0002:\u0005_`abcB\u0007¢\u0006\u0004\b\u0003\u0010\u0004J\u0019\u0010\b\u001a\u00020\u00072\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0016¢\u0006\u0004\b\b\u0010\tJ\u000f\u0010\u000b\u001a\u00020\nH\u0002¢\u0006\u0004\b\u000b\u0010\fJ#\u0010\u0010\u001a\u000e\u0012\u0002\b\u00030\u000ej\u0006\u0012\u0002\b\u0003`\u000f2\u0006\u0010\r\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0010\u0010\u0011J#\u0010\u0012\u001a\u000e\u0012\u0002\b\u00030\u000ej\u0006\u0012\u0002\b\u0003`\u000f2\u0006\u0010\r\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0012\u0010\u0011J\u001d\u0010\u0014\u001a\b\u0012\u0004\u0012\u00028\u00000\u00132\u0006\u0010\r\u001a\u00028\u0000H\u0004¢\u0006\u0004\b\u0014\u0010\u0015J\u0019\u0010\u0019\u001a\u0004\u0018\u00010\u00182\u0006\u0010\u0017\u001a\u00020\u0016H\u0002¢\u0006\u0004\b\u0019\u0010\u001aJ\u001b\u0010\u001e\u001a\u00020\u001d2\n\u0010\u001c\u001a\u0006\u0012\u0002\b\u00030\u001bH\u0002¢\u0006\u0004\b\u001e\u0010\u001fJ)\u0010#\u001a\u00020\u001d2\u0018\u0010\"\u001a\u0014\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0012\u0004\u0012\u00020\u001d0 j\u0002`!H\u0016¢\u0006\u0004\b#\u0010$J\u0019\u0010%\u001a\u00020\u001d2\b\u0010\u0006\u001a\u0004\u0018\u00010\u0005H\u0002¢\u0006\u0004\b%\u0010&J\u0015\u0010'\u001a\u00020\u00072\u0006\u0010\r\u001a\u00028\u0000¢\u0006\u0004\b'\u0010(J\u0017\u0010)\u001a\u00020\u00182\u0006\u0010\r\u001a\u00028\u0000H\u0014¢\u0006\u0004\b)\u0010*J#\u0010-\u001a\u00020\u00182\u0006\u0010\r\u001a\u00028\u00002\n\u0010,\u001a\u0006\u0012\u0002\b\u00030+H\u0014¢\u0006\u0004\b-\u0010.J\u0017\u00100\u001a\u00020\u001d2\u0006\u0010\u001c\u001a\u00020/H\u0014¢\u0006\u0004\b0\u00101JX\u00106\u001a\u00020\u001d\"\u0004\b\u0001\u001022\f\u0010,\u001a\b\u0012\u0004\u0012\u00028\u00010+2\u0006\u0010\r\u001a\u00028\u00002(\u00105\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u000104\u0012\u0006\u0012\u0004\u0018\u00010\u001803H\u0002ø\u0001\u0000¢\u0006\u0004\b6\u00107J\u001b\u0010\u0017\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0004\b\u0017\u00108J\u001d\u0010:\u001a\b\u0012\u0002\b\u0003\u0018\u0001092\u0006\u0010\r\u001a\u00028\u0000H\u0004¢\u0006\u0004\b:\u0010;J\u001b\u0010=\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0004\b<\u00108J\u001b\u0010>\u001a\u00020\u001d2\u0006\u0010\r\u001a\u00028\u0000H@ø\u0001\u0000¢\u0006\u0004\b>\u00108J\u0017\u0010?\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u000109H\u0014¢\u0006\u0004\b?\u0010@J\u0011\u0010A\u001a\u0004\u0018\u00010\u0016H\u0004¢\u0006\u0004\bA\u0010BJ\u000f\u0010D\u001a\u00020CH\u0016¢\u0006\u0004\bD\u0010ER\u0016\u0010G\u001a\u00020C8T@\u0014X\u0004¢\u0006\u0006\u001a\u0004\bF\u0010ER\u001c\u0010J\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001b8D@\u0004X\u0004¢\u0006\u0006\u001a\u0004\bH\u0010IR\u001c\u0010L\u001a\b\u0012\u0002\b\u0003\u0018\u00010\u001b8D@\u0004X\u0004¢\u0006\u0006\u001a\u0004\bK\u0010IR\u0016\u0010O\u001a\u00020\u00078B@\u0002X\u0004¢\u0006\u0006\u001a\u0004\bM\u0010NR\u0016\u0010P\u001a\u00020\u00078$@$X¤\u0004¢\u0006\u0006\u001a\u0004\bP\u0010NR\u0016\u0010Q\u001a\u00020\u00078$@$X¤\u0004¢\u0006\u0006\u001a\u0004\bQ\u0010NR\u0013\u0010R\u001a\u00020\u00078F@\u0006¢\u0006\u0006\u001a\u0004\bR\u0010NR\u0013\u0010S\u001a\u00020\u00078F@\u0006¢\u0006\u0006\u001a\u0004\bS\u0010NR%\u0010W\u001a\u0014\u0012\u0004\u0012\u00028\u0000\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u00020T8F@\u0006¢\u0006\u0006\u001a\u0004\bU\u0010VR\u001c\u0010Y\u001a\u00020X8\u0004@\u0004X\u0004¢\u0006\f\n\u0004\bY\u0010Z\u001a\u0004\b[\u0010\\R\u0016\u0010^\u001a\u00020C8B@\u0002X\u0004¢\u0006\u0006\u001a\u0004\b]\u0010E\u0002\u0004\n\u0002\b\u0019¨\u0006d"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel;", "E", "Lkotlinx/coroutines/channels/SendChannel;", "<init>", "()V", "", "cause", "", "close", "(Ljava/lang/Throwable;)Z", "", "countQueueSize", "()I", "element", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/internal/AddLastDesc;", "describeSendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "describeSendConflated", "Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "describeTryOffer", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "Lkotlinx/coroutines/channels/Send;", "send", "", "enqueueSend", "(Lkotlinx/coroutines/channels/Send;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/Closed;", "closed", "", "helpClose", "(Lkotlinx/coroutines/channels/Closed;)V", "Lkotlin/Function1;", "Lkotlinx/coroutines/channels/Handler;", "handler", "invokeOnClose", "(Lkotlin/jvm/functions/Function1;)V", "invokeOnCloseHandler", "(Ljava/lang/Throwable;)V", "offer", "(Ljava/lang/Object;)Z", "offerInternal", "(Ljava/lang/Object;)Ljava/lang/Object;", "Lkotlinx/coroutines/selects/SelectInstance;", "select", "offerSelectInternal", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "onClosedIdempotent", "(Lkotlinx/coroutines/internal/LockFreeLinkedListNode;)V", "R", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "block", "registerSelectSend", "(Lkotlinx/coroutines/selects/SelectInstance;Ljava/lang/Object;Lkotlin/jvm/functions/Function2;)V", "(Ljava/lang/Object;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendBuffered", "(Ljava/lang/Object;)Lkotlinx/coroutines/channels/ReceiveOrClosed;", "sendFair$kotlinx_coroutines_core", "sendFair", "sendSuspend", "takeFirstReceiveOrPeekClosed", "()Lkotlinx/coroutines/channels/ReceiveOrClosed;", "takeFirstSendOrPeekClosed", "()Lkotlinx/coroutines/channels/Send;", "", "toString", "()Ljava/lang/String;", "getBufferDebugString", "bufferDebugString", "getClosedForReceive", "()Lkotlinx/coroutines/channels/Closed;", "closedForReceive", "getClosedForSend", "closedForSend", "getFull", "()Z", "full", "isBufferAlwaysFull", "isBufferFull", "isClosedForSend", "isFull", "Lkotlinx/coroutines/selects/SelectClause2;", "getOnSend", "()Lkotlinx/coroutines/selects/SelectClause2;", "onSend", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueue", "()Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "getQueueDebugStateString", "queueDebugStateString", "SendBuffered", "SendBufferedDesc", "SendConflatedDesc", "SendSelect", "TryOfferDesc", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: AbstractChannel.kt */
public abstract class AbstractSendChannel<E> implements SendChannel<E> {
    private static final AtomicReferenceFieldUpdater onCloseHandler$FU = AtomicReferenceFieldUpdater.newUpdater(AbstractSendChannel.class, Object.class, "onCloseHandler");
    private volatile Object onCloseHandler = null;
    private final LockFreeLinkedListHead queue = new LockFreeLinkedListHead();

    /* access modifiers changed from: protected */
    public abstract boolean isBufferAlwaysFull();

    /* access modifiers changed from: protected */
    public abstract boolean isBufferFull();

    /* access modifiers changed from: protected */
    public final LockFreeLinkedListHead getQueue() {
        return this.queue;
    }

    /* access modifiers changed from: protected */
    public Object offerInternal(E element) {
        ReceiveOrClosed receive;
        Object token;
        do {
            receive = takeFirstReceiveOrPeekClosed();
            if (receive == null) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            token = receive.tryResumeReceive(element, (Object) null);
        } while (token == null);
        receive.completeResumeReceive(token);
        return receive.getOfferResult();
    }

    /* access modifiers changed from: protected */
    public Object offerSelectInternal(E element, SelectInstance<?> select) {
        Intrinsics.checkParameterIsNotNull(select, "select");
        TryOfferDesc offerOp = describeTryOffer(element);
        Object failure = select.performAtomicTrySelect(offerOp);
        if (failure != null) {
            return failure;
        }
        ReceiveOrClosed receive = (ReceiveOrClosed) offerOp.getResult();
        Object obj = offerOp.resumeToken;
        if (obj == null) {
            Intrinsics.throwNpe();
        }
        receive.completeResumeReceive(obj);
        return receive.getOfferResult();
    }

    /* access modifiers changed from: protected */
    public final Closed<?> getClosedForSend() {
        LockFreeLinkedListNode prevNode = this.queue.getPrevNode();
        if (!(prevNode instanceof Closed)) {
            prevNode = null;
        }
        Closed it = (Closed) prevNode;
        if (it == null) {
            return null;
        }
        helpClose(it);
        return it;
    }

    /* access modifiers changed from: protected */
    public final Closed<?> getClosedForReceive() {
        LockFreeLinkedListNode nextNode = this.queue.getNextNode();
        if (!(nextNode instanceof Closed)) {
            nextNode = null;
        }
        Closed it = (Closed) nextNode;
        if (it == null) {
            return null;
        }
        helpClose(it);
        return it;
    }

    /* access modifiers changed from: protected */
    public final Send takeFirstSendOrPeekClosed() {
        LockFreeLinkedListNode first$iv;
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        while (true) {
            Object next = lockFreeLinkedListHead.getNext();
            if (next != null) {
                first$iv = (LockFreeLinkedListNode) next;
                if (first$iv != lockFreeLinkedListHead) {
                    if (first$iv instanceof Send) {
                        if ((((Send) first$iv) instanceof Closed) || first$iv.remove()) {
                            break;
                        }
                        first$iv.helpDelete();
                    } else {
                        first$iv = null;
                        break;
                    }
                } else {
                    first$iv = null;
                    break;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
            }
        }
        return (Send) first$iv;
    }

    /* access modifiers changed from: protected */
    public final ReceiveOrClosed<?> sendBuffered(E element) {
        LockFreeLinkedListNode prev$iv;
        LockFreeLinkedListNode this_$iv = this.queue;
        LockFreeLinkedListNode node$iv = new SendBuffered(element);
        do {
            Object prev = this_$iv.getPrev();
            if (prev != null) {
                prev$iv = (LockFreeLinkedListNode) prev;
                LockFreeLinkedListNode prev2 = prev$iv;
                if (prev2 instanceof ReceiveOrClosed) {
                    return (ReceiveOrClosed) prev2;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
            }
        } while (!prev$iv.addNext(node$iv, this_$iv));
        return null;
    }

    /* access modifiers changed from: protected */
    public final LockFreeLinkedListNode.AddLastDesc<?> describeSendBuffered(E element) {
        return new SendBufferedDesc<>(this.queue, element);
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0012\u0018\u0000*\u0004\b\u0001\u0010\u00012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0003`\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00028\u0001¢\u0006\u0002\u0010\bJ\u0012\u0010\t\u001a\u0004\u0018\u00010\n2\u0006\u0010\u000b\u001a\u00020\fH\u0014¨\u0006\r"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBufferedDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$AddLastDesc;", "Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "Lkotlinx/coroutines/internal/AddLastDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "element", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;Ljava/lang/Object;)V", "failure", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: AbstractChannel.kt */
    private static class SendBufferedDesc<E> extends LockFreeLinkedListNode.AddLastDesc<SendBuffered<? extends E>> {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public SendBufferedDesc(LockFreeLinkedListHead queue, E element) {
            super(queue, new SendBuffered(element));
            Intrinsics.checkParameterIsNotNull(queue, "queue");
        }

        /* access modifiers changed from: protected */
        public Object failure(LockFreeLinkedListNode affected) {
            Intrinsics.checkParameterIsNotNull(affected, "affected");
            if (affected instanceof Closed) {
                return affected;
            }
            if (affected instanceof ReceiveOrClosed) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            return null;
        }
    }

    /* access modifiers changed from: protected */
    public final LockFreeLinkedListNode.AddLastDesc<?> describeSendConflated(E element) {
        return new SendConflatedDesc<>(this.queue, element);
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00028\u0001¢\u0006\u0002\u0010\u0006J\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\nH\u0014¨\u0006\f"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendConflatedDesc;", "E", "Lkotlinx/coroutines/channels/AbstractSendChannel$SendBufferedDesc;", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "element", "(Lkotlinx/coroutines/internal/LockFreeLinkedListHead;Ljava/lang/Object;)V", "finishOnSuccess", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "next", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: AbstractChannel.kt */
    private static final class SendConflatedDesc<E> extends SendBufferedDesc<E> {
        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public SendConflatedDesc(LockFreeLinkedListHead queue, E element) {
            super(queue, element);
            Intrinsics.checkParameterIsNotNull(queue, "queue");
        }

        /* access modifiers changed from: protected */
        public void finishOnSuccess(LockFreeLinkedListNode affected, LockFreeLinkedListNode next) {
            Intrinsics.checkParameterIsNotNull(affected, "affected");
            Intrinsics.checkParameterIsNotNull(next, "next");
            super.finishOnSuccess(affected, next);
            SendBuffered sendBuffered = (SendBuffered) (!(affected instanceof SendBuffered) ? null : affected);
            if (sendBuffered != null) {
                sendBuffered.remove();
            }
        }
    }

    public final boolean isClosedForSend() {
        return getClosedForSend() != null;
    }

    public final boolean isFull() {
        return getFull();
    }

    /* access modifiers changed from: private */
    public final boolean getFull() {
        return !(this.queue.getNextNode() instanceof ReceiveOrClosed) && isBufferFull();
    }

    public final Object send(E element, Continuation<? super Unit> $completion) {
        if (offer(element)) {
            return Unit.INSTANCE;
        }
        return sendSuspend(element, $completion);
    }

    public final Object sendFair$kotlinx_coroutines_core(E element, Continuation<? super Unit> $completion) {
        if (offer(element)) {
            return YieldKt.yield($completion);
        }
        return sendSuspend(element, $completion);
    }

    public final boolean offer(E element) {
        Throwable it;
        Throwable it2;
        Object result = offerInternal(element);
        if (result == AbstractChannelKt.OFFER_SUCCESS) {
            return true;
        }
        if (result == AbstractChannelKt.OFFER_FAILED) {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend == null || (it = closedForSend.getSendException()) == null || (it2 = StackTraceRecoveryKt.recoverStackTrace(it)) == null) {
                return false;
            }
            throw it2;
        } else if (result instanceof Closed) {
            throw StackTraceRecoveryKt.recoverStackTrace(((Closed) result).getSendException());
        } else {
            throw new IllegalStateException(("offerInternal returned " + result).toString());
        }
    }

    /* access modifiers changed from: package-private */
    public final /* synthetic */ Object sendSuspend(E element, Continuation<? super Unit> $completion) {
        CancellableContinuationImpl cancellable$iv = new CancellableContinuationImpl(IntrinsicsKt.intercepted($completion), 0);
        CancellableContinuation cont = cancellable$iv;
        while (true) {
            if (getFull()) {
                SendElement send = new SendElement(element, cont);
                Object enqueueResult = enqueueSend(send);
                if (enqueueResult == null) {
                    CancellableContinuationKt.removeOnCancellation(cont, send);
                    break;
                } else if (enqueueResult instanceof Closed) {
                    helpClose((Closed) enqueueResult);
                    Throwable sendException = ((Closed) enqueueResult).getSendException();
                    Result.Companion companion = Result.Companion;
                    cont.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(sendException)));
                    break;
                } else if (enqueueResult != AbstractChannelKt.ENQUEUE_FAILED && !(enqueueResult instanceof Receive)) {
                    throw new IllegalStateException(("enqueueSend returned " + enqueueResult).toString());
                }
            }
            Object offerResult = offerInternal(element);
            if (offerResult == AbstractChannelKt.OFFER_SUCCESS) {
                Unit unit = Unit.INSTANCE;
                Result.Companion companion2 = Result.Companion;
                cont.resumeWith(Result.m1289constructorimpl(unit));
                break;
            } else if (offerResult != AbstractChannelKt.OFFER_FAILED) {
                if (offerResult instanceof Closed) {
                    helpClose((Closed) offerResult);
                    Throwable sendException2 = ((Closed) offerResult).getSendException();
                    Result.Companion companion3 = Result.Companion;
                    cont.resumeWith(Result.m1289constructorimpl(ResultKt.createFailure(sendException2)));
                } else {
                    throw new IllegalStateException(("offerInternal returned " + offerResult).toString());
                }
            }
        }
        Object result = cancellable$iv.getResult();
        if (result == IntrinsicsKt.getCOROUTINE_SUSPENDED()) {
            DebugProbesKt.probeCoroutineSuspended($completion);
        }
        return result;
    }

    /* access modifiers changed from: private */
    public final Object enqueueSend(Send send) {
        boolean z;
        LockFreeLinkedListNode prev$iv;
        if (isBufferAlwaysFull()) {
            LockFreeLinkedListNode this_$iv = this.queue;
            do {
                Object prev = this_$iv.getPrev();
                if (prev != null) {
                    prev$iv = (LockFreeLinkedListNode) prev;
                    LockFreeLinkedListNode prev2 = prev$iv;
                    if (prev2 instanceof ReceiveOrClosed) {
                        return prev2;
                    }
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
                }
            } while (!prev$iv.addNext(send, this_$iv));
            return null;
        }
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        LockFreeLinkedListHead lockFreeLinkedListHead2 = lockFreeLinkedListHead;
        LockFreeLinkedListNode.CondAddOp condAdd$iv = new AbstractSendChannel$enqueueSend$$inlined$addLastIfPrevAndIf$1(send, send, this);
        while (true) {
            Object prev3 = lockFreeLinkedListHead.getPrev();
            if (prev3 != null) {
                LockFreeLinkedListNode prev$iv2 = (LockFreeLinkedListNode) prev3;
                LockFreeLinkedListNode prev4 = prev$iv2;
                if (!(prev4 instanceof ReceiveOrClosed)) {
                    int tryCondAddNext = prev$iv2.tryCondAddNext(send, lockFreeLinkedListHead, condAdd$iv);
                    z = true;
                    if (tryCondAddNext != 1) {
                        if (tryCondAddNext == 2) {
                            z = false;
                            break;
                        }
                    } else {
                        break;
                    }
                } else {
                    return prev4;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
            }
        }
        if (!z) {
            return AbstractChannelKt.ENQUEUE_FAILED;
        }
        return null;
    }

    public boolean close(Throwable cause) {
        LockFreeLinkedListNode this_$iv;
        Closed closed = new Closed(cause);
        LockFreeLinkedListNode this_$iv2 = this.queue;
        while (true) {
            Object prev = this_$iv2.getPrev();
            if (prev != null) {
                LockFreeLinkedListNode prev$iv = (LockFreeLinkedListNode) prev;
                if (!(prev$iv instanceof Closed)) {
                    if (prev$iv.addNext(closed, this_$iv2)) {
                        this_$iv = 1;
                        break;
                    }
                } else {
                    this_$iv = null;
                    break;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
            }
        }
        if (this_$iv == null) {
            LockFreeLinkedListNode prevNode = this.queue.getPrevNode();
            if (prevNode != null) {
                helpClose((Closed) prevNode);
                return false;
            }
            throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.Closed<*>");
        }
        helpClose(closed);
        invokeOnCloseHandler(cause);
        return true;
    }

    private final void invokeOnCloseHandler(Throwable cause) {
        Object handler = this.onCloseHandler;
        if (handler != null && handler != AbstractChannelKt.HANDLER_INVOKED && onCloseHandler$FU.compareAndSet(this, handler, AbstractChannelKt.HANDLER_INVOKED)) {
            ((Function1) TypeIntrinsics.beforeCheckcastToFunctionOfArity(handler, 1)).invoke(cause);
        }
    }

    public void invokeOnClose(Function1<? super Throwable, Unit> handler) {
        Intrinsics.checkParameterIsNotNull(handler, "handler");
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = onCloseHandler$FU;
        if (!atomicReferenceFieldUpdater.compareAndSet(this, (Object) null, handler)) {
            Object value = this.onCloseHandler;
            if (value == AbstractChannelKt.HANDLER_INVOKED) {
                throw new IllegalStateException("Another handler was already registered and successfully invoked");
            }
            throw new IllegalStateException("Another handler was already registered: " + value);
        }
        Closed closedToken = getClosedForSend();
        if (closedToken != null && atomicReferenceFieldUpdater.compareAndSet(this, handler, AbstractChannelKt.HANDLER_INVOKED)) {
            handler.invoke(closedToken.closeCause);
        }
    }

    /* access modifiers changed from: private */
    public final void helpClose(Closed<?> closed) {
        while (true) {
            LockFreeLinkedListNode previous = closed.getPrevNode();
            if ((previous instanceof LockFreeLinkedListHead) || !(previous instanceof Receive)) {
                onClosedIdempotent(closed);
            } else if (!previous.remove()) {
                previous.helpRemove();
            } else {
                Receive receive = (Receive) previous;
                ((Receive) previous).resumeReceiveClosed(closed);
            }
        }
        onClosedIdempotent(closed);
    }

    /* access modifiers changed from: protected */
    public void onClosedIdempotent(LockFreeLinkedListNode closed) {
        Intrinsics.checkParameterIsNotNull(closed, "closed");
    }

    /* access modifiers changed from: protected */
    public ReceiveOrClosed<E> takeFirstReceiveOrPeekClosed() {
        LockFreeLinkedListNode first$iv;
        LockFreeLinkedListHead lockFreeLinkedListHead = this.queue;
        while (true) {
            Object next = lockFreeLinkedListHead.getNext();
            if (next != null) {
                first$iv = (LockFreeLinkedListNode) next;
                if (first$iv != lockFreeLinkedListHead) {
                    if (first$iv instanceof ReceiveOrClosed) {
                        if ((((ReceiveOrClosed) first$iv) instanceof Closed) || first$iv.remove()) {
                            break;
                        }
                        first$iv.helpDelete();
                    } else {
                        first$iv = null;
                        break;
                    }
                } else {
                    first$iv = null;
                    break;
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
            }
        }
        return (ReceiveOrClosed) first$iv;
    }

    /* access modifiers changed from: protected */
    public final TryOfferDesc<E> describeTryOffer(E element) {
        return new TryOfferDesc<>(element, this.queue);
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\b\u0004\u0018\u0000*\u0004\b\u0001\u0010\u00012\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u00030\u0002j\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00010\u0003`\u0004B\u0015\u0012\u0006\u0010\u0005\u001a\u00028\u0001\u0012\u0006\u0010\u0006\u001a\u00020\u0007¢\u0006\u0002\u0010\bJ\u0012\u0010\f\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\r\u001a\u00020\u000eH\u0014J\u0016\u0010\u000f\u001a\u00020\u00102\f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00028\u00010\u0003H\u0014R\u0012\u0010\u0005\u001a\u00028\u00018\u0006X\u0004¢\u0006\u0004\n\u0002\u0010\tR\u0014\u0010\n\u001a\u0004\u0018\u00010\u000b8\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0012"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$TryOfferDesc;", "E", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$RemoveFirstDesc;", "Lkotlinx/coroutines/channels/ReceiveOrClosed;", "Lkotlinx/coroutines/internal/RemoveFirstDesc;", "element", "queue", "Lkotlinx/coroutines/internal/LockFreeLinkedListHead;", "(Ljava/lang/Object;Lkotlinx/coroutines/internal/LockFreeLinkedListHead;)V", "Ljava/lang/Object;", "resumeToken", "", "failure", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "validatePrepared", "", "node", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: AbstractChannel.kt */
    protected static final class TryOfferDesc<E> extends LockFreeLinkedListNode.RemoveFirstDesc<ReceiveOrClosed<? super E>> {
        public final E element;
        public Object resumeToken;

        /* JADX INFO: super call moved to the top of the method (can break code semantics) */
        public TryOfferDesc(E element2, LockFreeLinkedListHead queue) {
            super(queue);
            Intrinsics.checkParameterIsNotNull(queue, "queue");
            this.element = element2;
        }

        /* access modifiers changed from: protected */
        public Object failure(LockFreeLinkedListNode affected) {
            Intrinsics.checkParameterIsNotNull(affected, "affected");
            if (affected instanceof Closed) {
                return affected;
            }
            if (!(affected instanceof ReceiveOrClosed)) {
                return AbstractChannelKt.OFFER_FAILED;
            }
            return null;
        }

        /* access modifiers changed from: protected */
        public boolean validatePrepared(ReceiveOrClosed<? super E> node) {
            Intrinsics.checkParameterIsNotNull(node, "node");
            Object token = node.tryResumeReceive(this.element, this);
            if (token == null) {
                return false;
            }
            this.resumeToken = token;
            return true;
        }
    }

    public final SelectClause2<E, SendChannel<E>> getOnSend() {
        return new AbstractSendChannel$onSend$1(this);
    }

    /* access modifiers changed from: private */
    public final <R> void registerSelectSend(SelectInstance<? super R> select, E element, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block) {
        while (!select.isSelected()) {
            if (getFull()) {
                SendSelect node = new SendSelect(element, this, select, block);
                Object enqueueResult = enqueueSend(node);
                if (enqueueResult == null) {
                    select.disposeOnSelect(node);
                    return;
                } else if (enqueueResult instanceof Closed) {
                    helpClose((Closed) enqueueResult);
                    throw StackTraceRecoveryKt.recoverStackTrace(((Closed) enqueueResult).getSendException());
                } else if (enqueueResult != AbstractChannelKt.ENQUEUE_FAILED && !(enqueueResult instanceof Receive)) {
                    throw new IllegalStateException(("enqueueSend returned " + enqueueResult + ' ').toString());
                }
            }
            Object offerResult = offerSelectInternal(element, select);
            if (offerResult != SelectKt.getALREADY_SELECTED()) {
                if (offerResult != AbstractChannelKt.OFFER_FAILED) {
                    if (offerResult == AbstractChannelKt.OFFER_SUCCESS) {
                        UndispatchedKt.startCoroutineUnintercepted(block, this, select.getCompletion());
                        return;
                    } else if (offerResult instanceof Closed) {
                        helpClose((Closed) offerResult);
                        throw StackTraceRecoveryKt.recoverStackTrace(((Closed) offerResult).getSendException());
                    } else {
                        throw new IllegalStateException(("offerSelectInternal returned " + offerResult).toString());
                    }
                }
            } else {
                return;
            }
        }
    }

    public String toString() {
        return DebugStringsKt.getClassSimpleName(this) + '@' + DebugStringsKt.getHexAddress(this) + '{' + getQueueDebugStateString() + '}' + getBufferDebugString();
    }

    private final String getQueueDebugStateString() {
        String result;
        LockFreeLinkedListNode head = this.queue.getNextNode();
        if (head == this.queue) {
            return "EmptyQueue";
        }
        if (head instanceof Closed) {
            result = head.toString();
        } else if (head instanceof Receive) {
            result = "ReceiveQueued";
        } else if (head instanceof Send) {
            result = "SendQueued";
        } else {
            result = "UNEXPECTED:" + head;
        }
        LockFreeLinkedListNode tail = this.queue.getPrevNode();
        if (tail == head) {
            return result;
        }
        String result2 = result + ",queueSize=" + countQueueSize();
        if (tail instanceof Closed) {
            return result2 + ",closedForSend=" + tail;
        }
        return result2;
    }

    private final int countQueueSize() {
        int size = 0;
        LockFreeLinkedListHead this_$iv = this.queue;
        Object next = this_$iv.getNext();
        if (next != null) {
            for (LockFreeLinkedListNode cur$iv = (LockFreeLinkedListNode) next; !Intrinsics.areEqual((Object) cur$iv, (Object) this_$iv); cur$iv = cur$iv.getNextNode()) {
                if (cur$iv instanceof LockFreeLinkedListNode) {
                    LockFreeLinkedListNode lockFreeLinkedListNode = cur$iv;
                    size++;
                }
            }
            return size;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */");
    }

    /* access modifiers changed from: protected */
    public String getBufferDebugString() {
        return "";
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u0001*\u0004\b\u0002\u0010\u00022\u00020\u00032\u00020\u0004BX\u0012\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\b\u0012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00020\n\u0012(\u0010\u000b\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\b\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u00060\fø\u0001\u0000¢\u0006\u0002\u0010\u000eJ\u0010\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\u0006H\u0016J\b\u0010\u0015\u001a\u00020\u0013H\u0016J\u0014\u0010\u0016\u001a\u00020\u00132\n\u0010\u0017\u001a\u0006\u0012\u0002\b\u00030\u0018H\u0016J\b\u0010\u0019\u001a\u00020\u001aH\u0016J\u0014\u0010\u001b\u001a\u0004\u0018\u00010\u00062\b\u0010\u001c\u001a\u0004\u0018\u00010\u0006H\u0016R7\u0010\u000b\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00010\b\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00020\r\u0012\u0006\u0012\u0004\u0018\u00010\u00060\f8\u0006X\u0004ø\u0001\u0000¢\u0006\u0004\n\u0002\u0010\u000fR\u0016\u0010\u0007\u001a\b\u0012\u0004\u0012\u00028\u00010\b8\u0006X\u0004¢\u0006\u0002\n\u0000R\u0016\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0016\u0010\t\u001a\b\u0012\u0004\u0012\u00028\u00020\n8\u0006X\u0004¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\u001d"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendSelect;", "E", "R", "Lkotlinx/coroutines/channels/Send;", "Lkotlinx/coroutines/DisposableHandle;", "pollResult", "", "channel", "Lkotlinx/coroutines/channels/SendChannel;", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "block", "Lkotlin/Function2;", "Lkotlin/coroutines/Continuation;", "(Ljava/lang/Object;Lkotlinx/coroutines/channels/SendChannel;Lkotlinx/coroutines/selects/SelectInstance;Lkotlin/jvm/functions/Function2;)V", "Lkotlin/jvm/functions/Function2;", "getPollResult", "()Ljava/lang/Object;", "completeResumeSend", "", "token", "dispose", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "toString", "", "tryResumeSend", "idempotent", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: AbstractChannel.kt */
    private static final class SendSelect<E, R> extends Send implements DisposableHandle {
        public final Function2<SendChannel<? super E>, Continuation<? super R>, Object> block;
        public final SendChannel<E> channel;
        private final Object pollResult;
        public final SelectInstance<R> select;

        public SendSelect(Object pollResult2, SendChannel<? super E> channel2, SelectInstance<? super R> select2, Function2<? super SendChannel<? super E>, ? super Continuation<? super R>, ? extends Object> block2) {
            Intrinsics.checkParameterIsNotNull(channel2, "channel");
            Intrinsics.checkParameterIsNotNull(select2, "select");
            Intrinsics.checkParameterIsNotNull(block2, "block");
            this.pollResult = pollResult2;
            this.channel = channel2;
            this.select = select2;
            this.block = block2;
        }

        public Object getPollResult() {
            return this.pollResult;
        }

        public Object tryResumeSend(Object idempotent) {
            if (this.select.trySelect(idempotent)) {
                return AbstractChannelKt.SELECT_STARTED;
            }
            return null;
        }

        public void completeResumeSend(Object token) {
            Intrinsics.checkParameterIsNotNull(token, "token");
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(token == AbstractChannelKt.SELECT_STARTED)) {
                    throw new AssertionError();
                }
            }
            ContinuationKt.startCoroutine(this.block, this.channel, this.select.getCompletion());
        }

        public void dispose() {
            remove();
        }

        public void resumeSendClosed(Closed<?> closed) {
            Intrinsics.checkParameterIsNotNull(closed, "closed");
            if (this.select.trySelect((Object) null)) {
                this.select.resumeSelectCancellableWithException(closed.getSendException());
            }
        }

        public String toString() {
            return "SendSelect(" + getPollResult() + ")[" + this.channel + ", " + this.select + ']';
        }
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0000\u0018\u0000*\u0006\b\u0001\u0010\u0001 \u00012\u00020\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00028\u0001¢\u0006\u0002\u0010\u0004J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007H\u0016J\u0014\u0010\r\u001a\u00020\u000b2\n\u0010\u000e\u001a\u0006\u0012\u0002\b\u00030\u000fH\u0016J\u0014\u0010\u0010\u001a\u0004\u0018\u00010\u00072\b\u0010\u0011\u001a\u0004\u0018\u00010\u0007H\u0016R\u0012\u0010\u0003\u001a\u00028\u00018\u0006X\u0004¢\u0006\u0004\n\u0002\u0010\u0005R\u0016\u0010\u0006\u001a\u0004\u0018\u00010\u00078VX\u0004¢\u0006\u0006\u001a\u0004\b\b\u0010\t\u0002\u0004\n\u0002\b\u0019¨\u0006\u0012"}, mo33671d2 = {"Lkotlinx/coroutines/channels/AbstractSendChannel$SendBuffered;", "E", "Lkotlinx/coroutines/channels/Send;", "element", "(Ljava/lang/Object;)V", "Ljava/lang/Object;", "pollResult", "", "getPollResult", "()Ljava/lang/Object;", "completeResumeSend", "", "token", "resumeSendClosed", "closed", "Lkotlinx/coroutines/channels/Closed;", "tryResumeSend", "idempotent", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: AbstractChannel.kt */
    public static final class SendBuffered<E> extends Send {
        public final E element;

        public SendBuffered(E element2) {
            this.element = element2;
        }

        public Object getPollResult() {
            return this.element;
        }

        public Object tryResumeSend(Object idempotent) {
            return AbstractChannelKt.SEND_RESUMED;
        }

        public void completeResumeSend(Object token) {
            Intrinsics.checkParameterIsNotNull(token, "token");
            if (DebugKt.getASSERTIONS_ENABLED()) {
                if (!(token == AbstractChannelKt.SEND_RESUMED)) {
                    throw new AssertionError();
                }
            }
        }

        public void resumeSendClosed(Closed<?> closed) {
            Intrinsics.checkParameterIsNotNull(closed, "closed");
        }
    }
}
