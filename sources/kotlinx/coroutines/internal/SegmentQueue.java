package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.internal.Segment;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\t\n\u0002\b\u0005\n\u0002\u0010\u0002\n\u0002\b\f\b \u0018\u0000*\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u00028\u00000\u00012\u00020\u0003B\u0007¢\u0006\u0004\b\u0004\u0010\u0005J!\u0010\t\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\u0007H\u0004¢\u0006\u0004\b\t\u0010\nJ!\u0010\u000b\u001a\u0004\u0018\u00018\u00002\u0006\u0010\u0006\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\u0007H\u0004¢\u0006\u0004\b\u000b\u0010\nJ\u0017\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00028\u0000H\u0002¢\u0006\u0004\b\u000e\u0010\u000fJ\u0017\u0010\u0010\u001a\u00020\r2\u0006\u0010\f\u001a\u00028\u0000H\u0002¢\u0006\u0004\b\u0010\u0010\u000fJ#\u0010\u0012\u001a\u00028\u00002\u0006\u0010\b\u001a\u00020\u00072\n\b\u0002\u0010\u0011\u001a\u0004\u0018\u00018\u0000H&¢\u0006\u0004\b\u0012\u0010\u0013R\u0016\u0010\u0016\u001a\u00028\u00008D@\u0004X\u0004¢\u0006\u0006\u001a\u0004\b\u0014\u0010\u0015R\u0016\u0010\u0018\u001a\u00028\u00008D@\u0004X\u0004¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0015¨\u0006\u0019"}, mo33671d2 = {"Lkotlinx/coroutines/internal/SegmentQueue;", "Lkotlinx/coroutines/internal/Segment;", "S", "", "<init>", "()V", "startFrom", "", "id", "getSegment", "(Lkotlinx/coroutines/internal/Segment;J)Lkotlinx/coroutines/internal/Segment;", "getSegmentAndMoveHead", "new", "", "moveHeadForward", "(Lkotlinx/coroutines/internal/Segment;)V", "moveTailForward", "prev", "newSegment", "(JLkotlinx/coroutines/internal/Segment;)Lkotlinx/coroutines/internal/Segment;", "getHead", "()Lkotlinx/coroutines/internal/Segment;", "head", "getTail", "tail", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SegmentQueue.kt */
public abstract class SegmentQueue<S extends Segment<S>> {
    private static final AtomicReferenceFieldUpdater _head$FU;
    private static final AtomicReferenceFieldUpdater _tail$FU;
    private volatile Object _head;
    private volatile Object _tail;

    static {
        Class<SegmentQueue> cls = SegmentQueue.class;
        _head$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_head");
        _tail$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_tail");
    }

    public abstract S newSegment(long j, S s);

    public SegmentQueue() {
        Segment initialSegment = newSegment$default(this, 0, (Segment) null, 2, (Object) null);
        this._head = initialSegment;
        this._tail = initialSegment;
    }

    /* access modifiers changed from: protected */
    public final S getHead() {
        return (Segment) this._head;
    }

    /* access modifiers changed from: protected */
    public final S getTail() {
        return (Segment) this._tail;
    }

    public static /* synthetic */ Segment newSegment$default(SegmentQueue segmentQueue, long j, Segment segment, int i, Object obj) {
        if (obj == null) {
            if ((i & 2) != 0) {
                segment = null;
            }
            return segmentQueue.newSegment(j, segment);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: newSegment");
    }

    /* access modifiers changed from: protected */
    public final S getSegment(S startFrom, long id) {
        Segment segment;
        Intrinsics.checkParameterIsNotNull(startFrom, "startFrom");
        Segment cur = startFrom;
        while (cur.getId() < id) {
            Segment curNext = cur.getNext();
            if (curNext == null) {
                Segment newTail = newSegment(cur.getId() + 1, cur);
                if (cur.casNext(null, newTail)) {
                    if (cur.getRemoved()) {
                        cur.remove();
                    }
                    moveTailForward(newTail);
                    segment = newTail;
                } else {
                    segment = cur.getNext();
                    if (segment == null) {
                        Intrinsics.throwNpe();
                    }
                }
                curNext = segment;
            }
            cur = curNext;
        }
        if (cur.getId() != id) {
            return null;
        }
        return cur;
    }

    /* access modifiers changed from: protected */
    public final S getSegmentAndMoveHead(S startFrom, long id) {
        Intrinsics.checkParameterIsNotNull(startFrom, "startFrom");
        if (startFrom.getId() == id) {
            return startFrom;
        }
        Segment s = getSegment(startFrom, id);
        if (s == null) {
            return null;
        }
        moveHeadForward(s);
        return s;
    }

    private final void moveHeadForward(S s) {
        Segment curHead;
        do {
            curHead = (Segment) this._head;
            if (curHead.getId() > s.getId()) {
                return;
            }
        } while (!_head$FU.compareAndSet(this, curHead, s));
        s.prev = null;
    }

    private final void moveTailForward(S s) {
        Segment curTail;
        do {
            curTail = (Segment) this._tail;
            if (curTail.getId() > s.getId() || _tail$FU.compareAndSet(this, curTail, s)) {
            }
            curTail = (Segment) this._tail;
            return;
        } while (_tail$FU.compareAndSet(this, curTail, s));
    }
}
