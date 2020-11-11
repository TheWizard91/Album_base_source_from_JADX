package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.internal.CombineKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001a\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006¨\u0006\u0007"}, mo33671d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__ZipKt$combineTransform$5"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$$inlined$combineTransform$2", mo34305f = "Zip.kt", mo34306i = {0}, mo34307l = {260}, mo34308m = "invokeSuspend", mo34309n = {"$this$flow"}, mo34310s = {"L$0"})
/* compiled from: Zip.kt */
public final class FlowKt__ZipKt$combineTransform$$inlined$combineTransform$2 extends SuspendLambda implements Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow[] $flows;
    final /* synthetic */ Function6 $transform$inlined;
    Object L$0;
    int label;

    /* renamed from: p$ */
    private FlowCollector f683p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FlowKt__ZipKt$combineTransform$$inlined$combineTransform$2(Flow[] flowArr, Continuation continuation, Function6 function6) {
        super(2, continuation);
        this.$flows = flowArr;
        this.$transform$inlined = function6;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        FlowKt__ZipKt$combineTransform$$inlined$combineTransform$2 flowKt__ZipKt$combineTransform$$inlined$combineTransform$2 = new FlowKt__ZipKt$combineTransform$$inlined$combineTransform$2(this.$flows, continuation, this.$transform$inlined);
        FlowCollector flowCollector = (FlowCollector) obj;
        flowKt__ZipKt$combineTransform$$inlined$combineTransform$2.f683p$ = (FlowCollector) obj;
        return flowKt__ZipKt$combineTransform$$inlined$combineTransform$2;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt__ZipKt$combineTransform$$inlined$combineTransform$2) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0004\b\u0007\u0010\b¨\u0006\t"}, mo33671d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "it", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__ZipKt$combineTransform$5$2"}, mo33672k = 3, mo33673mv = {1, 1, 15})
    @DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$$inlined$combineTransform$2$2", mo34305f = "Zip.kt", mo34306i = {}, mo34307l = {}, mo34308m = "invokeSuspend", mo34309n = {}, mo34310s = {})
    /* renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$$inlined$combineTransform$2$2 */
    /* compiled from: Zip.kt */
    public static final class C19062 extends SuspendLambda implements Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object> {
        Object L$0;
        Object L$1;
        int label;

        /* renamed from: p$ */
        private FlowCollector f684p$;
        private Object[] p$0;
        final /* synthetic */ FlowKt__ZipKt$combineTransform$$inlined$combineTransform$2 this$0;

        {
            this.this$0 = r1;
        }

        public final Continuation<Unit> create(FlowCollector<? super R> flowCollector, Object[] objArr, Continuation<? super Unit> continuation) {
            Intrinsics.checkParameterIsNotNull(flowCollector, "$this$create");
            Intrinsics.checkParameterIsNotNull(objArr, "it");
            Intrinsics.checkParameterIsNotNull(continuation, "continuation");
            C19062 r0 = new C19062(this.this$0, continuation);
            r0.f684p$ = flowCollector;
            r0.p$0 = objArr;
            return r0;
        }

        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return ((C19062) create((FlowCollector) obj, (Object[]) obj2, (Continuation) obj3)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object $result) {
            IntrinsicsKt.getCOROUTINE_SUSPENDED();
            if (this.label == 0) {
                ResultKt.throwOnFailure($result);
                FlowCollector $receiver = this.f684p$;
                Continuation continuation = this;
                Object[] args = this.p$0;
                this.this$0.$transform$inlined.invoke($receiver, args[0], args[1], args[2], args[3], this);
                return Unit.INSTANCE;
            }
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }

        public final Object invokeSuspend$$forInline(Object $result) {
            FlowCollector $receiver = this.f684p$;
            Continuation continuation = this;
            Object[] args = this.p$0;
            this.this$0.$transform$inlined.invoke($receiver, args[0], args[1], args[2], args[3], this);
            return Unit.INSTANCE;
        }
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            FlowCollector $this$flow = this.f683p$;
            this.L$0 = $this$flow;
            this.label = 1;
            if (CombineKt.combineInternal($this$flow, this.$flows, new Function0<Object[]>(this) {
                final /* synthetic */ FlowKt__ZipKt$combineTransform$$inlined$combineTransform$2 this$0;

                {
                    this.this$0 = r1;
                }

                public final Object[] invoke() {
                    return new Object[this.this$0.$flows.length];
                }
            }, new C19062(this, (Continuation) null), this) == coroutine_suspended) {
                return coroutine_suspended;
            }
            FlowCollector flowCollector = $this$flow;
        } else if (i == 1) {
            FlowCollector $this$flow2 = (FlowCollector) this.L$0;
            ResultKt.throwOnFailure($result);
        } else {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        return Unit.INSTANCE;
    }

    public final Object invokeSuspend$$forInline(Object $result) {
        InlineMarker.mark(0);
        CombineKt.combineInternal(this.f683p$, this.$flows, new Function0<Object[]>(this) {
            final /* synthetic */ FlowKt__ZipKt$combineTransform$$inlined$combineTransform$2 this$0;

            {
                this.this$0 = r1;
            }

            public final Object[] invoke() {
                return new Object[this.this$0.$flows.length];
            }
        }, new C19062(this, (Continuation) null), this);
        InlineMarker.mark(2);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}