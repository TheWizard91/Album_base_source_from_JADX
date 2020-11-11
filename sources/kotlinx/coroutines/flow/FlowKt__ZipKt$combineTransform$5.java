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
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.internal.CombineKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo33671d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$5", mo34305f = "Zip.kt", mo34306i = {0}, mo34307l = {260}, mo34308m = "invokeSuspend", mo34309n = {"$this$flow"}, mo34310s = {"L$0"})
/* compiled from: Zip.kt */
public final class FlowKt__ZipKt$combineTransform$5 extends SuspendLambda implements Function2<FlowCollector<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Flow[] $flows;
    final /* synthetic */ Function3 $transform;
    Object L$0;
    int label;

    /* renamed from: p$ */
    private FlowCollector f689p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public FlowKt__ZipKt$combineTransform$5(Flow[] flowArr, Function3 function3, Continuation continuation) {
        super(2, continuation);
        this.$flows = flowArr;
        this.$transform = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        FlowKt__ZipKt$combineTransform$5 flowKt__ZipKt$combineTransform$5 = new FlowKt__ZipKt$combineTransform$5(this.$flows, this.$transform, continuation);
        FlowCollector flowCollector = (FlowCollector) obj;
        flowKt__ZipKt$combineTransform$5.f689p$ = (FlowCollector) obj;
        return flowKt__ZipKt$combineTransform$5;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt__ZipKt$combineTransform$5) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0018\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0004\b\u0007\u0010\b"}, mo33671d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "it", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
    @DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$5$2", mo34305f = "Zip.kt", mo34306i = {0, 0}, mo34307l = {260}, mo34308m = "invokeSuspend", mo34309n = {"$receiver", "it"}, mo34310s = {"L$0", "L$1"})
    /* renamed from: kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$5$2 */
    /* compiled from: Zip.kt */
    public static final class C19112 extends SuspendLambda implements Function3<FlowCollector<? super R>, T[], Continuation<? super Unit>, Object> {
        Object L$0;
        Object L$1;
        int label;

        /* renamed from: p$ */
        private FlowCollector f690p$;
        private Object[] p$0;
        final /* synthetic */ FlowKt__ZipKt$combineTransform$5 this$0;

        {
            this.this$0 = r1;
        }

        public final Continuation<Unit> create(FlowCollector<? super R> flowCollector, T[] tArr, Continuation<? super Unit> continuation) {
            Intrinsics.checkParameterIsNotNull(flowCollector, "$this$create");
            Intrinsics.checkParameterIsNotNull(tArr, "it");
            Intrinsics.checkParameterIsNotNull(continuation, "continuation");
            C19112 r0 = new C19112(this.this$0, continuation);
            r0.f690p$ = flowCollector;
            r0.p$0 = tArr;
            return r0;
        }

        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return ((C19112) create((FlowCollector) obj, (Object[]) obj2, (Continuation) obj3)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend(Object $result) {
            Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure($result);
                FlowCollector $receiver = this.f690p$;
                Object[] it = this.p$0;
                Function3 function3 = this.this$0.$transform;
                this.L$0 = $receiver;
                this.L$1 = it;
                this.label = 1;
                if (function3.invoke($receiver, it, this) == coroutine_suspended) {
                    return coroutine_suspended;
                }
                Object[] objArr = it;
            } else if (i == 1) {
                Object[] it2 = (Object[]) this.L$1;
                FlowCollector $receiver2 = (FlowCollector) this.L$0;
                ResultKt.throwOnFailure($result);
            } else {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            return Unit.INSTANCE;
        }

        public final Object invokeSuspend$$forInline(Object $result) {
            this.this$0.$transform.invoke(this.f690p$, this.p$0, this);
            return Unit.INSTANCE;
        }
    }

    public final Object invokeSuspend(Object $result) {
        Object coroutine_suspended = IntrinsicsKt.getCOROUTINE_SUSPENDED();
        int i = this.label;
        if (i == 0) {
            ResultKt.throwOnFailure($result);
            FlowCollector $this$flow = this.f689p$;
            Flow[] flowArr = this.$flows;
            Intrinsics.needClassReification();
            this.L$0 = $this$flow;
            this.label = 1;
            if (CombineKt.combineInternal($this$flow, flowArr, new Function0<T[]>(this) {
                final /* synthetic */ FlowKt__ZipKt$combineTransform$5 this$0;

                {
                    this.this$0 = r1;
                }

                public final T[] invoke() {
                    int length = this.this$0.$flows.length;
                    Intrinsics.reifiedOperationMarker(0, "T?");
                    return new Object[length];
                }
            }, new C19112(this, (Continuation) null), this) == coroutine_suspended) {
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
        FlowCollector $this$flow = this.f689p$;
        Flow[] flowArr = this.$flows;
        Intrinsics.needClassReification();
        InlineMarker.mark(0);
        CombineKt.combineInternal($this$flow, flowArr, new Function0<T[]>(this) {
            final /* synthetic */ FlowKt__ZipKt$combineTransform$5 this$0;

            {
                this.this$0 = r1;
            }

            public final T[] invoke() {
                int length = this.this$0.$flows.length;
                Intrinsics.reifiedOperationMarker(0, "T?");
                return new Object[length];
            }
        }, new C19112(this, (Continuation) null), this);
        InlineMarker.mark(2);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}
