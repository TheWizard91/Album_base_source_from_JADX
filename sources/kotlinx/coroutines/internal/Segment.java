package kotlinx.coroutines.internal;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;
import kotlin.Metadata;
import kotlinx.coroutines.DebugKt;
import kotlinx.coroutines.internal.Segment;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u000e\b \u0018\u0000*\u000e\b\u0000\u0010\u0001*\b\u0012\u0004\u0012\u00028\u00000\u00002\u00020\u0002B\u0019\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\u0010\u0005\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\u0006\u0010\u0007J!\u0010\u000b\u001a\u00020\n2\b\u0010\b\u001a\u0004\u0018\u00018\u00002\b\u0010\t\u001a\u0004\u0018\u00018\u0000¢\u0006\u0004\b\u000b\u0010\fJ\u0017\u0010\u000f\u001a\u00020\u000e2\u0006\u0010\r\u001a\u00028\u0000H\u0002¢\u0006\u0004\b\u000f\u0010\u0010J\u0017\u0010\u0011\u001a\u00020\u000e2\u0006\u0010\u0005\u001a\u00028\u0000H\u0002¢\u0006\u0004\b\u0011\u0010\u0010J\r\u0010\u0012\u001a\u00020\u000e¢\u0006\u0004\b\u0012\u0010\u0013R\u0019\u0010\u0004\u001a\u00020\u00038\u0006@\u0006¢\u0006\f\n\u0004\b\u0004\u0010\u0014\u001a\u0004\b\u0015\u0010\u0016R\u0015\u0010\r\u001a\u0004\u0018\u00018\u00008F@\u0006¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0018R\u0016\u0010\u001b\u001a\u00020\n8&@&X¦\u0004¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u001a¨\u0006\u001c"}, mo33671d2 = {"Lkotlinx/coroutines/internal/Segment;", "S", "", "", "id", "prev", "<init>", "(JLkotlinx/coroutines/internal/Segment;)V", "expected", "value", "", "casNext", "(Lkotlinx/coroutines/internal/Segment;Lkotlinx/coroutines/internal/Segment;)Z", "next", "", "moveNextToRight", "(Lkotlinx/coroutines/internal/Segment;)V", "movePrevToLeft", "remove", "()V", "J", "getId", "()J", "getNext", "()Lkotlinx/coroutines/internal/Segment;", "getRemoved", "()Z", "removed", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SegmentQueue.kt */
public abstract class Segment<S extends Segment<S>> {
    private static final AtomicReferenceFieldUpdater _next$FU;
    static final AtomicReferenceFieldUpdater prev$FU;
    private volatile Object _next = null;

    /* renamed from: id */
    private final long f705id;
    volatile Object prev = null;

    static {
        Class<Segment> cls = Segment.class;
        _next$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "_next");
        prev$FU = AtomicReferenceFieldUpdater.newUpdater(cls, Object.class, "prev");
    }

    public abstract boolean getRemoved();

    public Segment(long id, S prev2) {
        this.f705id = id;
        this.prev = prev2;
    }

    public final long getId() {
        return this.f705id;
    }

    public final S getNext() {
        return (Segment) this._next;
    }

    public final boolean casNext(S expected, S value) {
        return _next$FU.compareAndSet(this, expected, value);
    }

    public final void remove() {
        Segment prev2;
        Segment next;
        Segment segment;
        if (!DebugKt.getASSERTIONS_ENABLED() || getRemoved() != 0) {
            Segment next2 = (Segment) this._next;
            if (next2 != null && (prev2 = (Segment) this.prev) != null) {
                prev2.moveNextToRight(next2);
                while (prev2.getRemoved() && (segment = (Segment) prev2.prev) != null) {
                    prev2 = segment;
                    prev2.moveNextToRight(next2);
                }
                next2.movePrevToLeft(prev2);
                while (next2.getRemoved() && (next = next2.getNext()) != null) {
                    next2 = next;
                    next2.movePrevToLeft(prev2);
                }
                return;
            }
            return;
        }
        throw new AssertionError();
    }

    /* JADX WARNING: Removed duplicated region for block: B:0:0x0000 A[LOOP_START, MTH_ENTER_BLOCK] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final void moveNextToRight(S r6) {
        /*
            r5 = this;
        L_0x0000:
            java.lang.Object r0 = r5._next
            if (r0 == 0) goto L_0x001a
            kotlinx.coroutines.internal.Segment r0 = (kotlinx.coroutines.internal.Segment) r0
            long r1 = r6.f705id
            long r3 = r0.f705id
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 > 0) goto L_0x0010
            return
        L_0x0010:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = _next$FU
            boolean r1 = r1.compareAndSet(r5, r0, r6)
            if (r1 == 0) goto L_0x0019
            return
        L_0x0019:
            goto L_0x0000
        L_0x001a:
            kotlin.TypeCastException r0 = new kotlin.TypeCastException
            java.lang.String r1 = "null cannot be cast to non-null type S"
            r0.<init>(r1)
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.Segment.moveNextToRight(kotlinx.coroutines.internal.Segment):void");
    }

    /*  JADX ERROR: StackOverflow in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    private final void movePrevToLeft(S r6) {
        /*
            r5 = this;
        L_0x0000:
            java.lang.Object r0 = r5.prev
            kotlinx.coroutines.internal.Segment r0 = (kotlinx.coroutines.internal.Segment) r0
            if (r0 == 0) goto L_0x001a
            long r1 = r0.f705id
            long r3 = r6.f705id
            int r1 = (r1 > r3 ? 1 : (r1 == r3 ? 0 : -1))
            if (r1 > 0) goto L_0x0010
            return
        L_0x0010:
            java.util.concurrent.atomic.AtomicReferenceFieldUpdater r1 = prev$FU
            boolean r1 = r1.compareAndSet(r5, r0, r6)
            if (r1 == 0) goto L_0x0019
            return
        L_0x0019:
            goto L_0x0000
        L_0x001a:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.Segment.movePrevToLeft(kotlinx.coroutines.internal.Segment):void");
    }
}
