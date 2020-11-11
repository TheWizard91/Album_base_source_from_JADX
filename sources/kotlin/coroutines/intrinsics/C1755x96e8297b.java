package kotlin.coroutines.intrinsics;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.functions.Function1;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001f\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J\"\u0010\u0004\u001a\u0004\u0018\u00010\u00052\u000e\u0010\u0006\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0007H\u0014ø\u0001\u0000¢\u0006\u0002\u0010\bR\u000e\u0010\u0002\u001a\u00020\u0003X\u000e¢\u0006\u0002\n\u0000\u0002\u0004\n\u0002\b\u0019¨\u0006\t"}, mo33671d2 = {"kotlin/coroutines/intrinsics/IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$2", "Lkotlin/coroutines/jvm/internal/ContinuationImpl;", "label", "", "invokeSuspend", "", "result", "Lkotlin/Result;", "(Ljava/lang/Object;)Ljava/lang/Object;", "kotlin-stdlib"}, mo33672k = 1, mo33673mv = {1, 1, 16})
/* renamed from: kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineFromSuspendFunction$2 */
/* compiled from: IntrinsicsJvm.kt */
public final class C1755x96e8297b extends ContinuationImpl {
    final /* synthetic */ Function1 $block;
    final /* synthetic */ Continuation $completion;
    final /* synthetic */ CoroutineContext $context;
    private int label;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public C1755x96e8297b(Function1 $captured_local_variable$0, Continuation $captured_local_variable$1, CoroutineContext $captured_local_variable$2, Continuation $super_call_param$3, CoroutineContext $super_call_param$4) {
        super($super_call_param$3, $super_call_param$4);
        this.$block = $captured_local_variable$0;
        this.$completion = $captured_local_variable$1;
        this.$context = $captured_local_variable$2;
    }

    /* access modifiers changed from: protected */
    public Object invokeSuspend(Object result) {
        int i = this.label;
        if (i == 0) {
            this.label = 1;
            ResultKt.throwOnFailure(result);
            return this.$block.invoke(this);
        } else if (i == 1) {
            this.label = 2;
            ResultKt.throwOnFailure(result);
            return result;
        } else {
            throw new IllegalStateException("This coroutine had already completed".toString());
        }
    }
}
