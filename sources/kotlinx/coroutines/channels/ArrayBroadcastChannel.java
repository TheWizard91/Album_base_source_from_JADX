package kotlinx.coroutines.channels;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlinx.coroutines.internal.ConcurrentKt;
import kotlinx.coroutines.selects.SelectInstance;
import kotlinx.coroutines.selects.SelectKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000z\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0004\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0003\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\b\u0000\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003:\u00019B\r\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0012\u0010 \u001a\u00020\u00172\b\u0010!\u001a\u0004\u0018\u00010\"H\u0017J\u0018\u0010 \u001a\u00020#2\u000e\u0010!\u001a\n\u0018\u00010$j\u0004\u0018\u0001`%H\u0016J\u0012\u0010&\u001a\u00020\u00172\b\u0010!\u001a\u0004\u0018\u00010\"H\u0002J\b\u0010'\u001a\u00020#H\u0002J\u0012\u0010(\u001a\u00020\u00172\b\u0010!\u001a\u0004\u0018\u00010\"H\u0016J\b\u0010)\u001a\u00020\u0015H\u0002J\u0015\u0010*\u001a\u00028\u00002\u0006\u0010+\u001a\u00020\u0015H\u0002¢\u0006\u0002\u0010,J\u0015\u0010-\u001a\u00020\t2\u0006\u0010.\u001a\u00028\u0000H\u0014¢\u0006\u0002\u0010/J!\u00100\u001a\u00020\t2\u0006\u0010.\u001a\u00028\u00002\n\u00101\u001a\u0006\u0012\u0002\b\u000302H\u0014¢\u0006\u0002\u00103J\u000e\u00104\u001a\b\u0012\u0004\u0012\u00028\u000005H\u0016J-\u00106\u001a\u00020#2\u0010\b\u0002\u00107\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001d2\u0010\b\u0002\u00108\u001a\n\u0012\u0004\u0012\u00028\u0000\u0018\u00010\u001dH\u0010R\u0018\u0010\u0007\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\t0\bX\u0004¢\u0006\u0004\n\u0002\u0010\nR\u0014\u0010\u000b\u001a\u00020\f8TX\u0004¢\u0006\u0006\u001a\u0004\b\r\u0010\u000eR\u0012\u0010\u000f\u001a\u00060\u0010j\u0002`\u0011X\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u000e¢\u0006\u0002\n\u0000R\u0014\u0010\u0016\u001a\u00020\u00178TX\u0004¢\u0006\u0006\u001a\u0004\b\u0016\u0010\u0018R\u0014\u0010\u0019\u001a\u00020\u00178TX\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0018R\u000e\u0010\u001a\u001a\u00020\u0005X\u000e¢\u0006\u0002\n\u0000R*\u0010\u001b\u001a\u001e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001d0\u001cj\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\u001d`\u001eX\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u001f\u001a\u00020\u0015X\u000e¢\u0006\u0002\n\u0000¨\u0006:"}, mo33671d2 = {"Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "E", "Lkotlinx/coroutines/channels/AbstractSendChannel;", "Lkotlinx/coroutines/channels/BroadcastChannel;", "capacity", "", "(I)V", "buffer", "", "", "[Ljava/lang/Object;", "bufferDebugString", "", "getBufferDebugString", "()Ljava/lang/String;", "bufferLock", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "getCapacity", "()I", "head", "", "isBufferAlwaysFull", "", "()Z", "isBufferFull", "size", "subscribers", "", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "Lkotlinx/coroutines/internal/SubscribersList;", "tail", "cancel", "cause", "", "", "Ljava/util/concurrent/CancellationException;", "Lkotlinx/coroutines/CancellationException;", "cancelInternal", "checkSubOffers", "close", "computeMinHead", "elementAt", "index", "(J)Ljava/lang/Object;", "offerInternal", "element", "(Ljava/lang/Object;)Ljava/lang/Object;", "offerSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "(Ljava/lang/Object;Lkotlinx/coroutines/selects/SelectInstance;)Ljava/lang/Object;", "openSubscription", "Lkotlinx/coroutines/channels/ReceiveChannel;", "updateHead", "addSub", "removeSub", "Subscriber", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: ArrayBroadcastChannel.kt */
public final class ArrayBroadcastChannel<E> extends AbstractSendChannel<E> implements BroadcastChannel<E> {
    private final Object[] buffer;
    private final ReentrantLock bufferLock;
    private final int capacity;
    private volatile long head;
    private volatile int size;
    private final List<Subscriber<E>> subscribers;
    /* access modifiers changed from: private */
    public volatile long tail;

    public ArrayBroadcastChannel(int capacity2) {
        this.capacity = capacity2;
        if (capacity2 < 1 ? false : true) {
            this.bufferLock = new ReentrantLock();
            this.buffer = new Object[capacity2];
            this.subscribers = ConcurrentKt.subscriberList();
            return;
        }
        throw new IllegalArgumentException(("ArrayBroadcastChannel capacity must be at least 1, but " + capacity2 + " was specified").toString());
    }

    public final int getCapacity() {
        return this.capacity;
    }

    /* access modifiers changed from: protected */
    public boolean isBufferAlwaysFull() {
        return false;
    }

    /* access modifiers changed from: protected */
    public boolean isBufferFull() {
        return this.size >= this.capacity;
    }

    public ReceiveChannel<E> openSubscription() {
        Subscriber it = new Subscriber(this);
        updateHead$default(this, it, (Subscriber) null, 2, (Object) null);
        return it;
    }

    public boolean close(Throwable cause) {
        if (!super.close(cause)) {
            return false;
        }
        checkSubOffers();
        return true;
    }

    public void cancel(CancellationException cause) {
        cancel(cause);
    }

    /* access modifiers changed from: private */
    /* renamed from: cancelInternal */
    public final boolean cancel(Throwable cause) {
        boolean close = close(cause);
        boolean z = close;
        for (Subscriber<E> sub : this.subscribers) {
            sub.cancelInternal$kotlinx_coroutines_core(cause);
        }
        return close;
    }

    /* access modifiers changed from: protected */
    public Object offerInternal(E element) {
        Lock lock = this.bufferLock;
        lock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                return closedForSend;
            }
            int size2 = this.size;
            if (size2 >= this.capacity) {
                Object obj = AbstractChannelKt.OFFER_FAILED;
                lock.unlock();
                return obj;
            }
            long tail2 = this.tail;
            this.buffer[(int) (tail2 % ((long) this.capacity))] = element;
            this.size = size2 + 1;
            this.tail = 1 + tail2;
            Unit unit = Unit.INSTANCE;
            lock.unlock();
            checkSubOffers();
            return AbstractChannelKt.OFFER_SUCCESS;
        } finally {
            lock.unlock();
        }
    }

    /* access modifiers changed from: protected */
    public Object offerSelectInternal(E element, SelectInstance<?> select) {
        Intrinsics.checkParameterIsNotNull(select, "select");
        Lock lock = this.bufferLock;
        lock.lock();
        try {
            Closed<?> closedForSend = getClosedForSend();
            if (closedForSend != null) {
                return closedForSend;
            }
            int size2 = this.size;
            if (size2 >= this.capacity) {
                Object obj = AbstractChannelKt.OFFER_FAILED;
                lock.unlock();
                return obj;
            } else if (!select.trySelect((Object) null)) {
                Object already_selected = SelectKt.getALREADY_SELECTED();
                lock.unlock();
                return already_selected;
            } else {
                long tail2 = this.tail;
                this.buffer[(int) (tail2 % ((long) this.capacity))] = element;
                this.size = size2 + 1;
                this.tail = 1 + tail2;
                Unit unit = Unit.INSTANCE;
                lock.unlock();
                checkSubOffers();
                return AbstractChannelKt.OFFER_SUCCESS;
            }
        } finally {
            lock.unlock();
        }
    }

    private final void checkSubOffers() {
        boolean updated = false;
        boolean hasSubs = false;
        for (Subscriber<E> sub : this.subscribers) {
            hasSubs = true;
            if (sub.checkOffer()) {
                updated = true;
            }
        }
        if (updated || !hasSubs) {
            updateHead$default(this, (Subscriber) null, (Subscriber) null, 3, (Object) null);
        }
    }

    static /* synthetic */ void updateHead$default(ArrayBroadcastChannel arrayBroadcastChannel, Subscriber subscriber, Subscriber subscriber2, int i, Object obj) {
        if ((i & 1) != 0) {
            subscriber = null;
        }
        if ((i & 2) != 0) {
            subscriber2 = null;
        }
        arrayBroadcastChannel.updateHead(subscriber, subscriber2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00b8, code lost:
        r4 = r1.buffer;
        r24 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:60:0x00be, code lost:
        r21 = r6;
        r22 = r7;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:62:?, code lost:
        r2 = (int) (r12 % ((long) r1.capacity));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:63:0x00c6, code lost:
        if (r3 == null) goto L_0x00ef;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:64:0x00c8, code lost:
        r4[r2] = r3.getPollResult();
        r1.size = r0 + 1;
        r1.tail = 1 + r12;
        r0 = kotlin.Unit.INSTANCE;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:65:0x00da, code lost:
        r8.unlock();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00de, code lost:
        if (r3 != null) goto L_0x00e3;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:67:0x00e0, code lost:
        kotlin.jvm.internal.Intrinsics.throwNpe();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00f6, code lost:
        throw new kotlin.TypeCastException("null cannot be cast to non-null type kotlinx.coroutines.channels.Send");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00f7, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00f8, code lost:
        r4 = r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void updateHead(kotlinx.coroutines.channels.ArrayBroadcastChannel.Subscriber<E> r24, kotlinx.coroutines.channels.ArrayBroadcastChannel.Subscriber<E> r25) {
        /*
            r23 = this;
            r1 = r23
            r2 = r24
            r3 = r25
        L_0x0006:
            r0 = 0
            r4 = r0
            kotlinx.coroutines.channels.Send r4 = (kotlinx.coroutines.channels.Send) r4
            r5 = 0
            java.util.concurrent.locks.ReentrantLock r6 = r1.bufferLock
            r7 = 0
            r8 = r6
            java.util.concurrent.locks.Lock r8 = (java.util.concurrent.locks.Lock) r8
            r8.lock()
            r9 = 0
            if (r2 == 0) goto L_0x0037
            long r10 = r1.tail     // Catch:{ all -> 0x002c }
            r2.subHead = r10     // Catch:{ all -> 0x002c }
            java.util.List<kotlinx.coroutines.channels.ArrayBroadcastChannel$Subscriber<E>> r10 = r1.subscribers     // Catch:{ all -> 0x002c }
            boolean r10 = r10.isEmpty()     // Catch:{ all -> 0x002c }
            java.util.List<kotlinx.coroutines.channels.ArrayBroadcastChannel$Subscriber<E>> r11 = r1.subscribers     // Catch:{ all -> 0x002c }
            r11.add(r2)     // Catch:{ all -> 0x002c }
            if (r10 != 0) goto L_0x0037
            r8.unlock()
            return
        L_0x002c:
            r0 = move-exception
            r25 = r2
            r18 = r3
            r21 = r6
            r22 = r7
            goto L_0x0166
        L_0x0037:
            if (r3 == 0) goto L_0x004a
            java.util.List<kotlinx.coroutines.channels.ArrayBroadcastChannel$Subscriber<E>> r10 = r1.subscribers     // Catch:{ all -> 0x002c }
            r10.remove(r3)     // Catch:{ all -> 0x002c }
            long r10 = r1.head     // Catch:{ all -> 0x002c }
            long r12 = r3.subHead     // Catch:{ all -> 0x002c }
            int r10 = (r10 > r12 ? 1 : (r10 == r12 ? 0 : -1))
            if (r10 == 0) goto L_0x004a
            r8.unlock()
            return
        L_0x004a:
            long r10 = r23.computeMinHead()     // Catch:{ all -> 0x015d }
            long r12 = r1.tail     // Catch:{ all -> 0x015d }
            long r14 = r1.head     // Catch:{ all -> 0x015d }
            long r16 = kotlin.ranges.RangesKt.coerceAtMost((long) r10, (long) r12)     // Catch:{ all -> 0x015d }
            int r18 = (r16 > r14 ? 1 : (r16 == r14 ? 0 : -1))
            if (r18 > 0) goto L_0x005e
            r8.unlock()
            return
        L_0x005e:
            int r0 = r1.size     // Catch:{ all -> 0x015d }
        L_0x0060:
            int r18 = (r14 > r16 ? 1 : (r14 == r16 ? 0 : -1))
            if (r18 >= 0) goto L_0x0159
            r25 = r2
            java.lang.Object[] r2 = r1.buffer     // Catch:{ all -> 0x014d }
            r18 = r3
            int r3 = r1.capacity     // Catch:{ all -> 0x0143 }
            r19 = r4
            r20 = r5
            long r4 = (long) r3
            long r4 = r14 % r4
            int r4 = (int) r4     // Catch:{ all -> 0x0139 }
            r5 = 0
            r2[r4] = r5     // Catch:{ all -> 0x0139 }
            if (r0 < r3) goto L_0x007b
            r2 = 1
            goto L_0x007c
        L_0x007b:
            r2 = 0
        L_0x007c:
            r3 = 1
            long r14 = r14 + r3
            r1.head = r14     // Catch:{ all -> 0x0139 }
            int r0 = r0 + -1
            r1.size = r0     // Catch:{ all -> 0x0139 }
            if (r2 == 0) goto L_0x0124
            r5 = r20
        L_0x0089:
            kotlinx.coroutines.channels.Send r20 = r23.takeFirstSendOrPeekClosed()     // Catch:{ all -> 0x011c }
            if (r20 == 0) goto L_0x0114
            r19 = r20
            r3 = r19
            boolean r4 = r3 instanceof kotlinx.coroutines.channels.Closed     // Catch:{ all -> 0x010c }
            if (r4 == 0) goto L_0x00a1
            r19 = r3
            r21 = r6
            r22 = r7
            r4 = 0
            goto L_0x012d
        L_0x00a1:
            if (r3 != 0) goto L_0x00af
            kotlin.jvm.internal.Intrinsics.throwNpe()     // Catch:{ all -> 0x00a7 }
            goto L_0x00af
        L_0x00a7:
            r0 = move-exception
            r4 = r3
            r21 = r6
            r22 = r7
            goto L_0x0166
        L_0x00af:
            r4 = 0
            java.lang.Object r19 = r3.tryResumeSend(r4)     // Catch:{ all -> 0x010c }
            r5 = r19
            if (r5 == 0) goto L_0x00fb
            java.lang.Object[] r4 = r1.buffer     // Catch:{ all -> 0x010c }
            r24 = r2
            int r2 = r1.capacity     // Catch:{ all -> 0x010c }
            r21 = r6
            r22 = r7
            long r6 = (long) r2
            long r6 = r12 % r6
            int r2 = (int) r6     // Catch:{ all -> 0x00f7 }
            if (r3 == 0) goto L_0x00ef
            java.lang.Object r6 = r3.getPollResult()     // Catch:{ all -> 0x00f7 }
            r4[r2] = r6     // Catch:{ all -> 0x00f7 }
            int r2 = r0 + 1
            r1.size = r2     // Catch:{ all -> 0x00f7 }
            r6 = 1
            long r6 = r6 + r12
            r1.tail = r6     // Catch:{ all -> 0x00f7 }
            kotlin.Unit r0 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x00f7 }
            r8.unlock()
            if (r3 != 0) goto L_0x00e3
            kotlin.jvm.internal.Intrinsics.throwNpe()
        L_0x00e3:
            r3.completeResumeSend(r5)
            r23.checkSubOffers()
            r0 = 0
            r2 = 0
            r3 = r0
            goto L_0x0006
        L_0x00ef:
            kotlin.TypeCastException r2 = new kotlin.TypeCastException     // Catch:{ all -> 0x00f7 }
            java.lang.String r4 = "null cannot be cast to non-null type kotlinx.coroutines.channels.Send"
            r2.<init>(r4)     // Catch:{ all -> 0x00f7 }
            throw r2     // Catch:{ all -> 0x00f7 }
        L_0x00f7:
            r0 = move-exception
            r4 = r3
            goto L_0x0166
        L_0x00fb:
            r24 = r2
            r21 = r6
            r22 = r7
            r6 = 1
            r19 = r3
            r3 = r6
            r6 = r21
            r7 = r22
            goto L_0x0089
        L_0x010c:
            r0 = move-exception
            r21 = r6
            r22 = r7
            r4 = r3
            goto L_0x0166
        L_0x0114:
            r24 = r2
            r21 = r6
            r22 = r7
            r4 = 0
            goto L_0x012d
        L_0x011c:
            r0 = move-exception
            r21 = r6
            r22 = r7
            r4 = r19
            goto L_0x0166
        L_0x0124:
            r24 = r2
            r21 = r6
            r22 = r7
            r4 = 0
            r5 = r20
        L_0x012d:
            r2 = r25
            r3 = r18
            r4 = r19
            r6 = r21
            r7 = r22
            goto L_0x0060
        L_0x0139:
            r0 = move-exception
            r21 = r6
            r22 = r7
            r4 = r19
            r5 = r20
            goto L_0x0166
        L_0x0143:
            r0 = move-exception
            r19 = r4
            r20 = r5
            r21 = r6
            r22 = r7
            goto L_0x0166
        L_0x014d:
            r0 = move-exception
            r18 = r3
            r19 = r4
            r20 = r5
            r21 = r6
            r22 = r7
            goto L_0x0166
        L_0x0159:
            r8.unlock()
            return
        L_0x015d:
            r0 = move-exception
            r25 = r2
            r18 = r3
            r21 = r6
            r22 = r7
        L_0x0166:
            r8.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.ArrayBroadcastChannel.updateHead(kotlinx.coroutines.channels.ArrayBroadcastChannel$Subscriber, kotlinx.coroutines.channels.ArrayBroadcastChannel$Subscriber):void");
    }

    private final long computeMinHead() {
        long minHead = Long.MAX_VALUE;
        for (Subscriber<E> sub : this.subscribers) {
            minHead = RangesKt.coerceAtMost(minHead, sub.subHead);
        }
        return minHead;
    }

    /* access modifiers changed from: private */
    public final E elementAt(long index) {
        return this.buffer[(int) (index % ((long) this.capacity))];
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0005\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0003\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0002\u0018\u0000*\u0004\b\u0001\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u00022\b\u0012\u0004\u0012\u0002H\u00010\u0003B\u0013\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005¢\u0006\u0002\u0010\u0006J\u0017\u0010\u0012\u001a\u00020\b2\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0010¢\u0006\u0002\b\u0015J\u0006\u0010\u0016\u001a\u00020\bJ\b\u0010\u0017\u001a\u00020\u0018H\u0002J\b\u0010\u0019\u001a\u00020\bH\u0002J\n\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0002J\n\u0010\u001c\u001a\u0004\u0018\u00010\u001bH\u0014J\u0016\u0010\u001d\u001a\u0004\u0018\u00010\u001b2\n\u0010\u001e\u001a\u0006\u0012\u0002\b\u00030\u001fH\u0014R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00010\u0005X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u00020\b8TX\u0004¢\u0006\u0006\u001a\u0004\b\u0007\u0010\tR\u0014\u0010\n\u001a\u00020\b8TX\u0004¢\u0006\u0006\u001a\u0004\b\n\u0010\tR\u0014\u0010\u000b\u001a\u00020\b8TX\u0004¢\u0006\u0006\u001a\u0004\b\u000b\u0010\tR\u0014\u0010\f\u001a\u00020\b8TX\u0004¢\u0006\u0006\u001a\u0004\b\f\u0010\tR\u0012\u0010\r\u001a\u00020\u000e8\u0006@\u0006X\u000e¢\u0006\u0002\n\u0000R\u0012\u0010\u000f\u001a\u00060\u0010j\u0002`\u0011X\u0004¢\u0006\u0002\n\u0000¨\u0006 "}, mo33671d2 = {"Lkotlinx/coroutines/channels/ArrayBroadcastChannel$Subscriber;", "E", "Lkotlinx/coroutines/channels/AbstractChannel;", "Lkotlinx/coroutines/channels/ReceiveChannel;", "broadcastChannel", "Lkotlinx/coroutines/channels/ArrayBroadcastChannel;", "(Lkotlinx/coroutines/channels/ArrayBroadcastChannel;)V", "isBufferAlwaysEmpty", "", "()Z", "isBufferAlwaysFull", "isBufferEmpty", "isBufferFull", "subHead", "", "subLock", "Ljava/util/concurrent/locks/ReentrantLock;", "Lkotlinx/coroutines/internal/ReentrantLock;", "cancelInternal", "cause", "", "cancelInternal$kotlinx_coroutines_core", "checkOffer", "clearBuffer", "", "needsToCheckOfferWithoutLock", "peekUnderLock", "", "pollInternal", "pollSelectInternal", "select", "Lkotlinx/coroutines/selects/SelectInstance;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
    /* compiled from: ArrayBroadcastChannel.kt */
    private static final class Subscriber<E> extends AbstractChannel<E> implements ReceiveChannel<E> {
        private final ArrayBroadcastChannel<E> broadcastChannel;
        public volatile long subHead;
        private final ReentrantLock subLock = new ReentrantLock();

        public Subscriber(ArrayBroadcastChannel<E> broadcastChannel2) {
            Intrinsics.checkParameterIsNotNull(broadcastChannel2, "broadcastChannel");
            this.broadcastChannel = broadcastChannel2;
        }

        /* access modifiers changed from: protected */
        public boolean isBufferAlwaysEmpty() {
            return false;
        }

        /* access modifiers changed from: protected */
        public boolean isBufferEmpty() {
            return this.subHead >= this.broadcastChannel.tail;
        }

        /* access modifiers changed from: protected */
        public boolean isBufferAlwaysFull() {
            throw new IllegalStateException("Should not be used".toString());
        }

        /* access modifiers changed from: protected */
        public boolean isBufferFull() {
            throw new IllegalStateException("Should not be used".toString());
        }

        public boolean cancelInternal$kotlinx_coroutines_core(Throwable cause) {
            boolean closed = close(cause);
            if (closed) {
                ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, (Subscriber) null, this, 1, (Object) null);
            }
            clearBuffer();
            return closed;
        }

        private final void clearBuffer() {
            Lock lock = this.subLock;
            lock.lock();
            try {
                this.subHead = this.broadcastChannel.tail;
                Unit unit = Unit.INSTANCE;
            } finally {
                lock.unlock();
            }
        }

        public final boolean checkOffer() {
            boolean updated = false;
            Closed closed = null;
            while (true) {
                if (!needsToCheckOfferWithoutLock() || !this.subLock.tryLock()) {
                    break;
                }
                try {
                    Object result = peekUnderLock();
                    if (result != AbstractChannelKt.POLL_FAILED) {
                        if (!(result instanceof Closed)) {
                            ReceiveOrClosed takeFirstReceiveOrPeekClosed = takeFirstReceiveOrPeekClosed();
                            if (takeFirstReceiveOrPeekClosed == null) {
                                break;
                            }
                            ReceiveOrClosed receive = takeFirstReceiveOrPeekClosed;
                            if (receive instanceof Closed) {
                                break;
                            }
                            Object token = receive.tryResumeReceive(result, (Object) null);
                            if (token != null) {
                                this.subHead = 1 + this.subHead;
                                updated = true;
                                this.subLock.unlock();
                                if (receive == null) {
                                    Intrinsics.throwNpe();
                                }
                                receive.completeResumeReceive(token);
                            }
                        } else {
                            closed = result;
                            break;
                        }
                    }
                } finally {
                    this.subLock.unlock();
                }
            }
            this.subLock.unlock();
            if (closed != null) {
                close(closed.closeCause);
            }
            return updated;
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: protected */
        public Object pollInternal() {
            boolean updated = false;
            Lock lock = this.subLock;
            lock.lock();
            try {
                Object result = peekUnderLock();
                if (!(result instanceof Closed)) {
                    if (result != AbstractChannelKt.POLL_FAILED) {
                        this.subHead = 1 + this.subHead;
                        updated = true;
                    }
                }
                lock.unlock();
                Object result2 = result;
                Closed it = (Closed) (!(result2 instanceof Closed) ? null : result2);
                if (it != null) {
                    close(it.closeCause);
                }
                if (checkOffer()) {
                    updated = true;
                }
                if (updated) {
                    ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, (Subscriber) null, (Subscriber) null, 3, (Object) null);
                }
                return result2;
            } catch (Throwable th) {
                lock.unlock();
                throw th;
            }
        }

        /* JADX INFO: finally extract failed */
        /* access modifiers changed from: protected */
        public Object pollSelectInternal(SelectInstance<?> select) {
            Intrinsics.checkParameterIsNotNull(select, "select");
            boolean updated = false;
            Lock lock = this.subLock;
            lock.lock();
            try {
                Object result = peekUnderLock();
                if (!(result instanceof Closed)) {
                    if (result != AbstractChannelKt.POLL_FAILED) {
                        if (!select.trySelect((Object) null)) {
                            result = SelectKt.getALREADY_SELECTED();
                        } else {
                            this.subHead = 1 + this.subHead;
                            updated = true;
                        }
                    }
                }
                lock.unlock();
                Object result2 = result;
                Closed it = (Closed) (!(result2 instanceof Closed) ? null : result2);
                if (it != null) {
                    close(it.closeCause);
                }
                if (checkOffer()) {
                    updated = true;
                }
                if (updated) {
                    ArrayBroadcastChannel.updateHead$default(this.broadcastChannel, (Subscriber) null, (Subscriber) null, 3, (Object) null);
                }
                return result2;
            } catch (Throwable th) {
                lock.unlock();
                throw th;
            }
        }

        private final boolean needsToCheckOfferWithoutLock() {
            if (getClosedForReceive() != null) {
                return false;
            }
            if (!isBufferEmpty() || this.broadcastChannel.getClosedForReceive() != null) {
                return true;
            }
            return false;
        }

        private final Object peekUnderLock() {
            long subHead2 = this.subHead;
            Closed closedBroadcast = this.broadcastChannel.getClosedForReceive();
            if (subHead2 >= this.broadcastChannel.tail) {
                Closed closedForReceive = closedBroadcast != null ? closedBroadcast : getClosedForReceive();
                return closedForReceive != null ? closedForReceive : AbstractChannelKt.POLL_FAILED;
            }
            Object result = this.broadcastChannel.elementAt(subHead2);
            Closed closedSub = getClosedForReceive();
            if (closedSub != null) {
                return closedSub;
            }
            return result;
        }
    }

    /* access modifiers changed from: protected */
    public String getBufferDebugString() {
        return "(buffer:capacity=" + this.buffer.length + ",size=" + this.size + ')';
    }
}
