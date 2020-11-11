package kotlinx.coroutines.channels;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.channels.AbstractChannel;
import kotlinx.coroutines.channels.AbstractSendChannel;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000L\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0004\b\u0010\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\r\u0012\u0006\u0010\u0003\u001a\u00020\u0004¢\u0006\u0002\u0010\u0005J\b\u0010\u001b\u001a\u00020\u001cH\u0014J\u0010\u0010\u001d\u001a\u00020\u001c2\u0006\u0010\u001e\u001a\u00020\u0004H\u0002J\u0015\u0010\u001f\u001a\u00020\b2\u0006\u0010 \u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010!J!\u0010\"\u001a\u00020\b2\u0006\u0010 \u001a\u00028\u00002\n\u0010#\u001a\u0006\u0012\u0002\b\u00030$H\u0014¢\u0006\u0002\u0010%J\n\u0010&\u001a\u0004\u0018\u00010\bH\u0014J\u0016\u0010'\u001a\u0004\u0018\u00010\b2\n\u0010#\u001a\u0006\u0012\u0002\b\u00030$H\u0014R\u0018\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\b0\u0007X\u000e¢\u0006\u0004\n\u0002\u0010\tR\u0014\u0010\n\u001a\u00020\u000b8TX\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\rR\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u000e\u0010\u0010\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0011\u001a\u00020\u00128DX\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R\u0014\u0010\u0014\u001a\u00020\u00128DX\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0013R\u0014\u0010\u0015\u001a\u00020\u00128DX\u0004¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0013R\u0014\u0010\u0016\u001a\u00020\u00128DX\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0013R\u0012\u0010\u0017\u001a\u00060\u0018j\u0002`\u0019X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001a\u001a\u00020\u0004X\u000e¢\u0006\u0002\n\u0000¨\u0006("}, mo33671d2 = {"Lkotlinx/coroutines/channels/ArrayChannel;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "capacity", "", "(I)V", "buffer", "", "", "[Ljava/lang/Object;", "bufferDebugString", "", "getBufferDebugString", "()Ljava/lang/String;", "getCapacity", "()I", "head", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "lock", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "size", "cleanupSendQueueOnCancel", "", "ensureCapacity", "currentSize", "offerInternal", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "pollInternal", "pollSelectInternal", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: ArrayChannel.kt */
public class ArrayChannel<E> extends AbstractChannel<E> {
    private Object[] buffer;
    private final int capacity;
    private int head;
    private final ReentrantLock lock;
    private volatile int size;

    public ArrayChannel(int capacity2) {
        this.capacity = capacity2;
        if (capacity2 < 1 ? false : true) {
            this.lock = new ReentrantLock();
            this.buffer = new Object[Math.min(capacity2, 8)];
            return;
        }
        throw new IllegalArgumentException(("ArrayChannel capacity must be at least 1, but " + capacity2 + " was specified").toString());
    }

    public final int getCapacity() {
        return this.capacity;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferAlwaysEmpty() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferEmpty() {
        return this.size == 0;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferAlwaysFull() {
        return false;
    }

    /* access modifiers changed from: protected */
    public final boolean isBufferFull() {
        return this.size == this.capacity;
    }

    /* access modifiers changed from: protected */
    public Object offerInternal(E element) {
        ReceiveOrClosed receiveOrClosed;
        Object token;
        ReceiveOrClosed receiveOrClosed2 = null;
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            int size2 = this.size;
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                return closedForSend;
            } else if (size2 < this.capacity) {
                this.size = size2 + 1;
                if (size2 == 0) {
                    do {
                        ReceiveOrClosed takeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
                        if (takeFirstReceiveOrPeekClosed != null) {
                            receiveOrClosed = takeFirstReceiveOrPeekClosed;
                            if (receiveOrClosed instanceof Closed) {
                                this.size = size2;
                                if (receiveOrClosed == null) {
                                    Intrinsics.throwNpe();
                                }
                                lock2.unlock();
                                return receiveOrClosed;
                            }
                            if (receiveOrClosed == null) {
                                Intrinsics.throwNpe();
                            }
                            token = receiveOrClosed.tryResumeReceive(element, (Object) null);
                        }
                    } while (token == null);
                    this.size = size2;
                    Unit unit = Unit.INSTANCE;
                    lock2.unlock();
                    if (receiveOrClosed == null) {
                        Intrinsics.throwNpe();
                    }
                    receiveOrClosed.completeResumeReceive(token);
                    if (receiveOrClosed == null) {
                        Intrinsics.throwNpe();
                    }
                    return receiveOrClosed.getOfferResult();
                }
                ensureCapacity(size2);
                Object[] objArr = this.buffer;
                objArr[(this.head + size2) % objArr.length] = element;
                Object obj = AbstractChannelKt.OFFER_SUCCESS;
                lock2.unlock();
                return obj;
            } else {
                Object obj2 = AbstractChannelKt.OFFER_FAILED;
                lock2.unlock();
                return obj2;
            }
        } finally {
            lock2.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public Object offerSelectInternal(E element, SelectInstance<?> select) {
        Intrinsics.checkParameterIsNotNull(select, "select");
        ReceiveOrClosed receiveOrClosed = null;
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            int size2 = this.size;
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                return closedForSend;
            } else if (size2 < this.capacity) {
                this.size = size2 + 1;
                if (size2 == 0) {
                    AbstractSendChannel.TryOfferDesc offerOp = describeTryOffer(element);
                    Object failure = select.performAtomicTrySelect(offerOp);
                    if (failure == null) {
                        this.size = size2;
                        ReceiveOrClosed receiveOrClosed2 = (ReceiveOrClosed) offerOp.getResult();
                        Object token = offerOp.resumeToken;
                        if (DebugKt.getASSERTIONS_ENABLED()) {
                            if (!(token != null)) {
                                throw new AssertionError();
                            }
                        }
                        Unit unit = Unit.INSTANCE;
                        lock2.unlock();
                        if (receiveOrClosed2 == null) {
                            Intrinsics.throwNpe();
                        }
                        if (token == null) {
                            Intrinsics.throwNpe();
                        }
                        receiveOrClosed2.completeResumeReceive(token);
                        if (receiveOrClosed2 == null) {
                            Intrinsics.throwNpe();
                        }
                        return receiveOrClosed2.getOfferResult();
                    } else if (failure != AbstractChannelKt.OFFER_FAILED) {
                        if (failure != SelectKt.getALREADY_SELECTED()) {
                            if (!(failure instanceof Closed)) {
                                throw new IllegalStateException(("performAtomicTrySelect(describeTryOffer) returned " + failure).toString());
                            }
                        }
                        this.size = size2;
                        lock2.unlock();
                        return failure;
                    }
                }
                if (!select.trySelect((Object) null)) {
                    this.size = size2;
                    Object already_selected = SelectKt.getALREADY_SELECTED();
                    lock2.unlock();
                    return already_selected;
                }
                ensureCapacity(size2);
                Object[] objArr = this.buffer;
                objArr[(this.head + size2) % objArr.length] = element;
                Object obj = AbstractChannelKt.OFFER_SUCCESS;
                lock2.unlock();
                return obj;
            } else {
                Object obj2 = AbstractChannelKt.OFFER_FAILED;
                lock2.unlock();
                return obj2;
            }
        } finally {
            lock2.unlock();
        }
    }

    private final void ensureCapacity(int currentSize) {
        Object[] objArr = this.buffer;
        if (currentSize >= objArr.length) {
            Object[] newBuffer = new Object[Math.min(objArr.length * 2, this.capacity)];
            for (int i = 0; i < currentSize; i++) {
                Object[] objArr2 = this.buffer;
                newBuffer[i] = objArr2[(this.head + i) % objArr2.length];
            }
            this.buffer = newBuffer;
            this.head = 0;
        }
    }

    /* access modifiers changed from: protected */
    public Object pollInternal() {
        Send send = null;
        Object token = null;
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            int size2 = this.size;
            if (size2 == 0) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                return closedForSend;
            }
            Object[] objArr = this.buffer;
            int i = this.head;
            Object result = objArr[i];
            objArr[i] = null;
            this.size = size2 - 1;
            Object replacement = AbstractChannelKt.POLL_FAILED;
            if (size2 == this.capacity) {
                while (true) {
                    Send takeFirstSendOrPeekClosed = takeFirstSendOrPeekClosed();
                    if (takeFirstSendOrPeekClosed == null) {
                        break;
                    }
                    send = takeFirstSendOrPeekClosed;
                    if (send == null) {
                        Intrinsics.throwNpe();
                    }
                    token = send.tryResumeSend((Object) null);
                    if (token != null) {
                        if (send == null) {
                            Intrinsics.throwNpe();
                        }
                        replacement = send.getPollResult();
                    }
                }
            }
            if (replacement != AbstractChannelKt.POLL_FAILED && !(replacement instanceof Closed)) {
                this.size = size2;
                Object[] objArr2 = this.buffer;
                objArr2[(this.head + size2) % objArr2.length] = replacement;
            }
            this.head = (this.head + 1) % this.buffer.length;
            Unit unit = Unit.INSTANCE;
            lock2.unlock();
            if (token != null) {
                if (send == null) {
                    Intrinsics.throwNpe();
                }
                send.completeResumeSend(token);
            }
            return result;
        } finally {
            lock2.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public Object pollSelectInternal(SelectInstance<?> select) {
        SelectInstance<?> selectInstance = select;
        Intrinsics.checkParameterIsNotNull(selectInstance, "select");
        Send send = null;
        Object token = null;
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            int size2 = this.size;
            if (size2 == 0) {
                Object closedForSend = getClosedForSend();
                if (closedForSend == null) {
                    closedForSend = AbstractChannelKt.POLL_FAILED;
                }
                return closedForSend;
            }
            Object[] objArr = this.buffer;
            int i = this.head;
            Object result = objArr[i];
            objArr[i] = null;
            this.size = size2 - 1;
            Object replacement = AbstractChannelKt.POLL_FAILED;
            if (size2 == this.capacity) {
                AbstractChannel.TryPollDesc pollOp = describeTryPoll();
                Object failure = selectInstance.performAtomicTrySelect(pollOp);
                if (failure == null) {
                    send = (Send) pollOp.getResult();
                    token = pollOp.resumeToken;
                    if (DebugKt.getASSERTIONS_ENABLED()) {
                        if (!(token != null)) {
                            throw new AssertionError();
                        }
                    }
                    if (send == null) {
                        Intrinsics.throwNpe();
                    }
                    replacement = send.getPollResult();
                } else if (failure != AbstractChannelKt.POLL_FAILED) {
                    if (failure == SelectKt.getALREADY_SELECTED()) {
                        this.size = size2;
                        this.buffer[this.head] = result;
                        lock2.unlock();
                        return failure;
                    } else if (failure instanceof Closed) {
                        send = failure;
                        token = ((Closed) failure).tryResumeSend((Object) null);
                        replacement = failure;
                    } else {
                        throw new IllegalStateException(("performAtomicTrySelect(describeTryOffer) returned " + failure).toString());
                    }
                }
            }
            if (replacement != AbstractChannelKt.POLL_FAILED && !(replacement instanceof Closed)) {
                this.size = size2;
                Object[] objArr2 = this.buffer;
                objArr2[(this.head + size2) % objArr2.length] = replacement;
            } else if (!selectInstance.trySelect((Object) null)) {
                this.size = size2;
                this.buffer[this.head] = result;
                Object already_selected = SelectKt.getALREADY_SELECTED();
                lock2.unlock();
                return already_selected;
            }
            this.head = (this.head + 1) % this.buffer.length;
            Unit unit = Unit.INSTANCE;
            lock2.unlock();
            if (token != null) {
                if (send == null) {
                    Intrinsics.throwNpe();
                }
                send.completeResumeSend(token);
            }
            return result;
        } finally {
            lock2.unlock();
        }
    }

    /* JADX INFO: finally extract failed */
    /* access modifiers changed from: protected */
    public void cleanupSendQueueOnCancel() {
        Lock lock2 = this.lock;
        lock2.lock();
        try {
            int i = this.size;
            for (int i2 = 0; i2 < i; i2++) {
                int i3 = i2;
                this.buffer[this.head] = 0;
                this.head = (this.head + 1) % this.buffer.length;
            }
            this.size = 0;
            Unit unit = Unit.INSTANCE;
            lock2.unlock();
            super.cleanupSendQueueOnCancel();
        } catch (Throwable th) {
            lock2.unlock();
            throw th;
        }
    }

    /* access modifiers changed from: protected */
    public String getBufferDebugString() {
        return "(buffer:capacity=" + this.capacity + ",size=" + this.size + ')';
    }
}
