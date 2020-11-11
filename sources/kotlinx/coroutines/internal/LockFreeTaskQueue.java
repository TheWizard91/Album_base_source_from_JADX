package kotlinx.coroutines.internal;

import java.util.List;
import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0010\b\n\u0002\b\u0004\b\u0010\u0018\u0000*\b\b\u0000\u0010\u0002*\u00020\u00012\u00020\u0001B\u000f\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006J\u0015\u0010\b\u001a\u00020\u00032\u0006\u0010\u0007\u001a\u00028\u0000¢\u0006\u0004\b\b\u0010\tJ\r\u0010\u000b\u001a\u00020\n¢\u0006\u0004\b\u000b\u0010\fJ\r\u0010\r\u001a\u00020\u0003¢\u0006\u0004\b\r\u0010\u000eJ-\u0010\u0013\u001a\b\u0012\u0004\u0012\u00028\u00010\u0012\"\u0004\b\u0001\u0010\u000f2\u0012\u0010\u0011\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00028\u00010\u0010¢\u0006\u0004\b\u0013\u0010\u0014J\u000f\u0010\u0015\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\u0015\u0010\u0016J&\u0010\u0018\u001a\u0004\u0018\u00018\u00002\u0012\u0010\u0017\u001a\u000e\u0012\u0004\u0012\u00028\u0000\u0012\u0004\u0012\u00020\u00030\u0010H\b¢\u0006\u0004\b\u0018\u0010\u0019R\u0013\u0010\u001a\u001a\u00020\u00038F@\u0006¢\u0006\u0006\u001a\u0004\b\u001a\u0010\u000eR\u0013\u0010\u001e\u001a\u00020\u001b8F@\u0006¢\u0006\u0006\u001a\u0004\b\u001c\u0010\u001d¨\u0006\u001f"}, mo33671d2 = {"Lkotlinx/coroutines/internal/LockFreeTaskQueue;", "", "E", "", "singleConsumer", "<init>", "(Z)V", "element", "addLast", "(Ljava/lang/Object;)Z", "", "close", "()V", "isClosed", "()Z", "R", "Lkotlin/Function1;", "transform", "", "map", "(Lkotlin/jvm/functions/Function1;)Ljava/util/List;", "removeFirstOrNull", "()Ljava/lang/Object;", "predicate", "removeFirstOrNullIf", "(Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "isEmpty", "", "getSize", "()I", "size", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: LockFreeTaskQueue.kt */
public class LockFreeTaskQueue<E> {
    public static final /* synthetic */ AtomicReferenceFieldUpdater _cur$FU$internal = AtomicReferenceFieldUpdater.newUpdater(LockFreeTaskQueue.class, Object.class, "_cur$internal");
    public volatile /* synthetic */ Object _cur$internal;

    public LockFreeTaskQueue(boolean singleConsumer) {
        this._cur$internal = new LockFreeTaskQueueCore(8, singleConsumer);
    }

    public final boolean isEmpty() {
        return ((LockFreeTaskQueueCore) this._cur$internal).isEmpty();
    }

    public final int getSize() {
        return ((LockFreeTaskQueueCore) this._cur$internal).getSize();
    }

    public final void close() {
        while (true) {
            LockFreeTaskQueueCore cur = (LockFreeTaskQueueCore) this._cur$internal;
            if (!cur.close()) {
                _cur$FU$internal.compareAndSet(this, cur, cur.next());
            } else {
                return;
            }
        }
    }

    public final boolean addLast(E element) {
        Intrinsics.checkParameterIsNotNull(element, "element");
        while (true) {
            LockFreeTaskQueueCore cur = (LockFreeTaskQueueCore) this._cur$internal;
            int addLast = cur.addLast(element);
            if (addLast == 0) {
                return true;
            }
            if (addLast == 1) {
                _cur$FU$internal.compareAndSet(this, cur, cur.next());
            } else if (addLast == 2) {
                return false;
            }
        }
    }

    /* JADX WARNING: Code restructure failed: missing block: B:27:0x00db, code lost:
        r8 = r26;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final E removeFirstOrNull() {
        /*
            r33 = this;
            r0 = r33
            r1 = 0
            r2 = r0
            r3 = 0
        L_0x0005:
            java.lang.Object r4 = r2._cur$internal
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r4 = (kotlinx.coroutines.internal.LockFreeTaskQueueCore) r4
            r5 = 0
            r12 = r4
            r13 = 0
            r14 = r12
            r15 = 0
        L_0x000f:
            long r10 = r14._state$internal
            r16 = 0
            r6 = 1152921504606846976(0x1000000000000000, double:1.2882297539194267E-231)
            long r6 = r6 & r10
            r8 = 0
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 == 0) goto L_0x0027
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.internal.LockFreeTaskQueueCore.REMOVE_FROZEN
            r25 = r1
            r29 = r2
            r30 = r3
            goto L_0x00dd
        L_0x0027:
            kotlinx.coroutines.internal.LockFreeTaskQueueCore$Companion r6 = kotlinx.coroutines.internal.LockFreeTaskQueueCore.Companion
            r17 = r10
            r19 = r6
            r20 = 0
            r6 = 1073741823(0x3fffffff, double:5.304989472E-315)
            long r6 = r17 & r6
            r9 = 0
            long r6 = r6 >> r9
            int r9 = (int) r6
            r6 = 1152921503533105152(0xfffffffc0000000, double:1.2882296003504729E-231)
            long r6 = r17 & r6
            r21 = 30
            long r6 = r6 >> r21
            int r7 = (int) r6
            r6 = r9
            r21 = r7
            r22 = 0
            int r23 = r12.mask
            r8 = r21 & r23
            int r23 = r12.mask
            r25 = r1
            r1 = r6 & r23
            if (r8 != r1) goto L_0x005f
            r29 = r2
            r30 = r3
            r8 = 0
            goto L_0x00dd
        L_0x005f:
            java.util.concurrent.atomic.AtomicReferenceArray r1 = r12.array$internal
            int r8 = r12.mask
            r8 = r8 & r6
            java.lang.Object r1 = r1.get(r8)
            if (r1 != 0) goto L_0x007e
            boolean r8 = r12.singleConsumer
            if (r8 == 0) goto L_0x0079
            r29 = r2
            r30 = r3
            r8 = 0
            goto L_0x00dd
        L_0x0079:
            r29 = r2
            r30 = r3
            goto L_0x00c8
        L_0x007e:
            boolean r8 = r1 instanceof kotlinx.coroutines.internal.LockFreeTaskQueueCore.Placeholder
            if (r8 == 0) goto L_0x0088
            r29 = r2
            r30 = r3
            r8 = 0
            goto L_0x00dd
        L_0x0088:
            r8 = r1
            r23 = 0
            int r8 = r6 + 1
            r23 = 1073741823(0x3fffffff, float:1.9999999)
            r8 = r8 & r23
            java.util.concurrent.atomic.AtomicLongFieldUpdater r23 = kotlinx.coroutines.internal.LockFreeTaskQueueCore._state$FU$internal
            r26 = r1
            kotlinx.coroutines.internal.LockFreeTaskQueueCore$Companion r1 = kotlinx.coroutines.internal.LockFreeTaskQueueCore.Companion
            long r27 = r1.updateHead(r10, r8)
            r1 = r6
            r6 = r23
            r23 = r7
            r7 = r12
            r29 = r2
            r30 = r3
            r2 = r8
            r24 = r9
            r3 = 0
            r8 = r10
            r31 = r10
            r10 = r27
            boolean r6 = r6.compareAndSet(r7, r8, r10)
            if (r6 == 0) goto L_0x00c2
            java.util.concurrent.atomic.AtomicReferenceArray r6 = r12.array$internal
            int r7 = r12.mask
            r7 = r7 & r1
            r6.set(r7, r3)
            goto L_0x00db
        L_0x00c2:
            boolean r3 = r12.singleConsumer
            if (r3 != 0) goto L_0x00d0
        L_0x00c8:
            r1 = r25
            r2 = r29
            r3 = r30
            goto L_0x000f
        L_0x00d0:
            r3 = r12
        L_0x00d1:
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r6 = r3.removeSlowPath(r1, r2)
            if (r6 == 0) goto L_0x00db
            r3 = r6
            goto L_0x00d1
        L_0x00db:
            r8 = r26
        L_0x00dd:
            r1 = r8
            kotlinx.coroutines.internal.Symbol r2 = kotlinx.coroutines.internal.LockFreeTaskQueueCore.REMOVE_FROZEN
            if (r1 == r2) goto L_0x00e3
            return r1
        L_0x00e3:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r2 = _cur$FU$internal
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r3 = r4.next()
            r2.compareAndSet(r0, r4, r3)
            r1 = r25
            r2 = r29
            r3 = r30
            goto L_0x0005
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.LockFreeTaskQueue.removeFirstOrNull():java.lang.Object");
    }

    /* JADX WARNING: Code restructure failed: missing block: B:30:0x00e2, code lost:
        r8 = r28;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final E removeFirstOrNullIf(kotlin.jvm.functions.Function1<? super E, java.lang.Boolean> r33) {
        /*
            r32 = this;
            r0 = r33
            r1 = 0
            java.lang.String r2 = "predicate"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r2)
            r2 = r32
            r3 = 0
        L_0x000b:
            java.lang.Object r4 = r2._cur$internal
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r4 = (kotlinx.coroutines.internal.LockFreeTaskQueueCore) r4
            r5 = 0
            r12 = r4
            r13 = 0
            r14 = r12
            r15 = 0
        L_0x0015:
            long r10 = r14._state$internal
            r16 = 0
            r6 = 1152921504606846976(0x1000000000000000, double:1.2882297539194267E-231)
            long r6 = r6 & r10
            r8 = 0
            int r6 = (r6 > r8 ? 1 : (r6 == r8 ? 0 : -1))
            if (r6 == 0) goto L_0x002b
            kotlinx.coroutines.internal.Symbol r8 = kotlinx.coroutines.internal.LockFreeTaskQueueCore.REMOVE_FROZEN
            r25 = r1
            r29 = r2
            goto L_0x00e4
        L_0x002b:
            kotlinx.coroutines.internal.LockFreeTaskQueueCore$Companion r6 = kotlinx.coroutines.internal.LockFreeTaskQueueCore.Companion
            r17 = r10
            r19 = r6
            r20 = 0
            r6 = 1073741823(0x3fffffff, double:5.304989472E-315)
            long r6 = r17 & r6
            r9 = 0
            long r6 = r6 >> r9
            int r9 = (int) r6
            r6 = 1152921503533105152(0xfffffffc0000000, double:1.2882296003504729E-231)
            long r6 = r17 & r6
            r21 = 30
            long r6 = r6 >> r21
            int r7 = (int) r6
            r6 = r9
            r21 = r7
            r22 = 0
            int r23 = r12.mask
            r8 = r21 & r23
            int r23 = r12.mask
            r25 = r1
            r1 = r6 & r23
            if (r8 != r1) goto L_0x0061
            r29 = r2
            r8 = 0
            goto L_0x00e4
        L_0x0061:
            java.util.concurrent.atomic.AtomicReferenceArray r1 = r12.array$internal
            int r8 = r12.mask
            r8 = r8 & r6
            java.lang.Object r1 = r1.get(r8)
            if (r1 != 0) goto L_0x007c
            boolean r8 = r12.singleConsumer
            if (r8 == 0) goto L_0x0079
            r29 = r2
            r8 = 0
            goto L_0x00e4
        L_0x0079:
            r29 = r2
            goto L_0x00cf
        L_0x007c:
            boolean r8 = r1 instanceof kotlinx.coroutines.internal.LockFreeTaskQueueCore.Placeholder
            if (r8 == 0) goto L_0x0085
            r29 = r2
            r8 = 0
            goto L_0x00e4
        L_0x0085:
            java.lang.Object r8 = r0.invoke(r1)
            java.lang.Boolean r8 = (java.lang.Boolean) r8
            boolean r8 = r8.booleanValue()
            if (r8 != 0) goto L_0x0096
            r29 = r2
            r8 = 0
            goto L_0x00e4
        L_0x0096:
            int r8 = r6 + 1
            r23 = 1073741823(0x3fffffff, float:1.9999999)
            r8 = r8 & r23
            java.util.concurrent.atomic.AtomicLongFieldUpdater r23 = kotlinx.coroutines.internal.LockFreeTaskQueueCore._state$FU$internal
            kotlinx.coroutines.internal.LockFreeTaskQueueCore$Companion r0 = kotlinx.coroutines.internal.LockFreeTaskQueueCore.Companion
            long r26 = r0.updateHead(r10, r8)
            r0 = r6
            r6 = r23
            r23 = r7
            r7 = r12
            r28 = r1
            r29 = r2
            r1 = r8
            r24 = r9
            r2 = 0
            r8 = r10
            r30 = r10
            r10 = r26
            boolean r6 = r6.compareAndSet(r7, r8, r10)
            if (r6 == 0) goto L_0x00c9
            java.util.concurrent.atomic.AtomicReferenceArray r6 = r12.array$internal
            int r7 = r12.mask
            r7 = r7 & r0
            r6.set(r7, r2)
            goto L_0x00e2
        L_0x00c9:
            boolean r2 = r12.singleConsumer
            if (r2 != 0) goto L_0x00d7
        L_0x00cf:
            r0 = r33
            r1 = r25
            r2 = r29
            goto L_0x0015
        L_0x00d7:
            r2 = r12
        L_0x00d8:
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r6 = r2.removeSlowPath(r0, r1)
            if (r6 == 0) goto L_0x00e2
            r2 = r6
            goto L_0x00d8
        L_0x00e2:
            r8 = r28
        L_0x00e4:
            r0 = r8
            kotlinx.coroutines.internal.Symbol r1 = kotlinx.coroutines.internal.LockFreeTaskQueueCore.REMOVE_FROZEN
            if (r0 == r1) goto L_0x00ea
            return r0
        L_0x00ea:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = _cur$FU$internal
            kotlinx.coroutines.internal.LockFreeTaskQueueCore r2 = r4.next()
            r6 = r32
            r1.compareAndSet(r6, r4, r2)
            r0 = r33
            r1 = r25
            r2 = r29
            goto L_0x000b
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.LockFreeTaskQueue.removeFirstOrNullIf(kotlin.jvm.functions.Function1):java.lang.Object");
    }

    public final <R> List<R> map(Function1<? super E, ? extends R> transform) {
        Intrinsics.checkParameterIsNotNull(transform, "transform");
        return ((LockFreeTaskQueueCore) this._cur$internal).map(transform);
    }

    public final boolean isClosed() {
        return ((LockFreeTaskQueueCore) this._cur$internal).isClosed();
    }
}
