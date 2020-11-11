package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.TypeCastException;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.internal.Symbol;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u00032\u0006\u0010\u0004\u001a\u00020\u0005H@¢\u0006\u0004\b\u0006\u0010\u0007¨\u0006\b"}, mo33671d2 = {"<anonymous>", "", "R", "T", "value", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx/coroutines/flow/internal/CombineKt$combineInternal$2$2$2"}, mo33672k = 3, mo33673mv = {1, 1, 15})
/* renamed from: kotlinx.coroutines.flow.internal.CombineKt$combineInternal$2$invokeSuspend$$inlined$select$lambda$1 */
/* compiled from: Combine.kt */
final class C1919x2d5a9c55 extends SuspendLambda implements Function2<Object, Continuation<? super Unit>, Object> {
    final /* synthetic */ ReceiveChannel[] $channels$inlined;

    /* renamed from: $i */
    final /* synthetic */ int f697$i;
    final /* synthetic */ Boolean[] $isClosed$inlined;
    final /* synthetic */ Object[] $latestValues$inlined;
    final /* synthetic */ int $size$inlined;
    Object L$0;
    Object L$1;
    int label;
    private Object p$0;
    final /* synthetic */ CombineKt$combineInternal$2 this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    C1919x2d5a9c55(int i, Continuation continuation, CombineKt$combineInternal$2 combineKt$combineInternal$2, int i2, Boolean[] boolArr, ReceiveChannel[] receiveChannelArr, Object[] objArr) {
        super(2, continuation);
        this.f697$i = i;
        this.this$0 = combineKt$combineInternal$2;
        this.$size$inlined = i2;
        this.$isClosed$inlined = boolArr;
        this.$channels$inlined = receiveChannelArr;
        this.$latestValues$inlined = objArr;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        C1919x2d5a9c55 combineKt$combineInternal$2$invokeSuspend$$inlined$select$lambda$1 = new C1919x2d5a9c55(this.f697$i, continuation, this.this$0, this.$size$inlined, this.$isClosed$inlined, this.$channels$inlined, this.$latestValues$inlined);
        combineKt$combineInternal$2$invokeSuspend$$inlined$select$lambda$1.p$0 = obj;
        return combineKt$combineInternal$2$invokeSuspend$$inlined$select$lambda$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((C1919x2d5a9c55) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    public final Object invokeSuspend(Object $result) {
        Object[] $this$all$iv;
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            Object value = this.p$0;
            this.$latestValues$inlined[this.f697$i] = value;
            Object[] $this$all$iv2 = this.$latestValues$inlined;
            int length = $this$all$iv2.length;
            int i2 = 0;
            int i3 = 0;
            while (true) {
                if (i3 >= length) {
                    $this$all$iv = 1;
                    break;
                }
                if (!Boxing.boxBoolean($this$all$iv2[i3] != null).booleanValue()) {
                    $this$all$iv = null;
                    break;
                }
                i3++;
            }
            if ($this$all$iv != null) {
                Object[] arguments = (Object[]) this.this$0.$arrayFactory.invoke();
                int i4 = this.$size$inlined;
                while (i2 < i4) {
                    int index = i2;
                    Symbol this_$iv = NullSurrogateKt.NULL;
                    Object value$iv = this.$latestValues$inlined[index];
                    if (value$iv == this_$iv) {
                        value$iv = null;
                    }
                    arguments[index] = value$iv;
                    i2 = index + 1;
                }
                Function3 function3 = this.this$0.$transform;
                FlowCollector flowCollector = this.this$0.$this_combineInternal;
                if (arguments != null) {
                    this.L$0 = value;
                    this.L$1 = arguments;
                    this.label = 1;
                    if (function3.invoke(flowCollector, arguments, this) == coroutine_suspended) {
                        return coroutine_suspended;
                    }
                    Object[] objArr = arguments;
                } else {
                    throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                }
            }
        } else if (i == 1) {
            Object[] arguments2 = (Object[]) this.L$1;
            Object value2 = this.L$0;
            ResultKt.throwOnFailure($result);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }
}
