package kotlinx.coroutines.sync;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CancellableContinuation;
import kotlinx.coroutines.internal.LockFreeLinkedListKt;
import kotlinx.coroutines.internal.LockFreeLinkedListNode;
import kotlinx.coroutines.sync.MutexImpl;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000!\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\u0016\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\u0010\u0004\u001a\u00060\u0005j\u0002`\u0006H\u0016¨\u0006\u0007¸\u0006\t"}, mo33671d2 = {"kotlinx/coroutines/internal/LockFreeLinkedListNode$makeCondAddOp$1", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode$CondAddOp;", "prepare", "", "affected", "Lkotlinx/coroutines/internal/LockFreeLinkedListNode;", "Lkotlinx/coroutines/internal/Node;", "kotlinx-coroutines-core", "kotlinx/coroutines/sync/MutexImpl$$special$$inlined$addLastIf$1", "kotlinx/coroutines/sync/MutexImpl$$special$$inlined$loop$lambda$1"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* renamed from: kotlinx.coroutines.sync.MutexImpl$lockSuspend$$inlined$suspendAtomicCancellableCoroutine$lambda$1 */
/* compiled from: LockFreeLinkedList.kt */
public final class C1927x426da4aa extends LockFreeLinkedListNode.CondAddOp {
    final /* synthetic */ CancellableContinuation $cont$inlined;
    final /* synthetic */ LockFreeLinkedListNode $node;
    final /* synthetic */ Object $owner$inlined;
    final /* synthetic */ Object $state$inlined;
    final /* synthetic */ MutexImpl.LockCont $waiter$inlined;
    final /* synthetic */ MutexImpl this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1927x426da4aa(LockFreeLinkedListNode $captured_local_variable$1, LockFreeLinkedListNode $super_call_param$2, Object obj, CancellableContinuation cancellableContinuation, MutexImpl.LockCont lockCont, MutexImpl mutexImpl, Object obj2) {
        super($super_call_param$2);
        this.$node = $captured_local_variable$1;
        this.$state$inlined = obj;
        this.$cont$inlined = cancellableContinuation;
        this.$waiter$inlined = lockCont;
        this.this$0 = mutexImpl;
        this.$owner$inlined = obj2;
    }

    public Object prepare(LockFreeLinkedListNode affected) {
        Intrinsics.checkParameterIsNotNull(affected, "affected");
        if (this.this$0._state == this.$state$inlined) {
            return null;
        }
        return LockFreeLinkedListKt.getCONDITION_FALSE();
    }
}
