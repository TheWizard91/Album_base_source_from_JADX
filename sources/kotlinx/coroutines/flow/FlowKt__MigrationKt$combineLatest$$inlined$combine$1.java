package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.internal.CombineKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00009\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\t"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__MigrationKt$combine$$inlined$unsafeFlow$1", "kotlinx/coroutines/flow/FlowKt__MigrationKt$combine$$inlined$combine$1"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
public final class FlowKt__MigrationKt$combineLatest$$inlined$combine$1 implements Flow<R> {
    final /* synthetic */ Flow[] $flows$inlined;
    final /* synthetic */ Function4 $transform$inlined$1;

    public FlowKt__MigrationKt$combineLatest$$inlined$combine$1(Flow[] flowArr, Function4 function4) {
        this.$flows$inlined = flowArr;
        this.$transform$inlined$1 = function4;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        Continuation continuation = $completion;
        return CombineKt.combineInternal(collector, this.$flows$inlined, new Function0<Object[]>(this) {
            final /* synthetic */ FlowKt__MigrationKt$combineLatest$$inlined$combine$1 this$0;

            {
                this.this$0 = r1;
            }

            public final Object[] invoke() {
                return new Object[this.this$0.$flows$inlined.length];
            }
        }, new C18323((Continuation) null), $completion);
    }

    public Object collect$$forInline(FlowCollector collector, Continuation continuation) {
        InlineMarker.mark(4);
        new ContinuationImpl(this, continuation) {
            Object L$0;
            Object L$1;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ FlowKt__MigrationKt$combineLatest$$inlined$combine$1 this$0;

            {
                this.this$0 = r1;
            }

            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.collect((FlowCollector) null, this);
            }
        };
        InlineMarker.mark(5);
        Continuation continuation2 = continuation;
        InlineMarker.mark(0);
        CombineKt.combineInternal(collector, this.$flows$inlined, new Function0<Object[]>(this) {
            final /* synthetic */ FlowKt__MigrationKt$combineLatest$$inlined$combine$1 this$0;

            {
                this.this$0 = r1;
            }

            public final Object[] invoke() {
                return new Object[this.this$0.$flows$inlined.length];
            }
        }, new C18323((Continuation) null), continuation);
        InlineMarker.mark(2);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000h\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0004\n\u0002\b\u0004\n\u0002\b\u0004\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0004\b\u0007\u0010\b¨\u0006\u000b"}, mo33671d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "it", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__ZipKt$combine$5$2", "kotlinx/coroutines/flow/FlowKt__MigrationKt$combine$$inlined$unsafeFlow$1$lambda$2", "kotlinx/coroutines/flow/FlowKt__MigrationKt$combine$$inlined$combine$1$3"}, mo33672k = 3, mo33673mv = {1, 1, 15})
    @DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__MigrationKt$combineLatest$$inlined$combine$1$3", mo34305f = "Migration.kt", mo34306i = {0, 0, 0, 0, 1, 1}, mo34307l = {317, 318}, mo34308m = "invokeSuspend", mo34309n = {"$receiver", "it", "continuation", "args", "$receiver", "it"}, mo34310s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1"})
    /* renamed from: kotlinx.coroutines.flow.FlowKt__MigrationKt$combineLatest$$inlined$combine$1$3 */
    /* compiled from: Zip.kt */
    public static final class C18323 extends SuspendLambda implements Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object> {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;

        /* renamed from: p$ */
        private FlowCollector f666p$;
        private Object[] p$0;

        public final Continuation<Unit> create(FlowCollector<? super R> flowCollector, Object[] objArr, Continuation<? super Unit> continuation) {
            Intrinsics.checkParameterIsNotNull(flowCollector, "$this$create");
            Intrinsics.checkParameterIsNotNull(objArr, "it");
            Intrinsics.checkParameterIsNotNull(continuation, "continuation");
            C18323 r0 = new C18323(continuation, this);
            r0.f666p$ = flowCollector;
            r0.p$0 = objArr;
            return r0;
        }

        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return ((C18323) create((FlowCollector) obj, (Object[]) obj2, (Continuation) obj3)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend$$forInline(Object $result) {
            FlowCollector $receiver = this.f666p$;
            Continuation continuation = this;
            Object[] args = this.p$0;
            Function4 function4 = this.$transform$inlined$1;
            Object obj = args[0];
            Object obj2 = args[1];
            Object obj3 = args[2];
            InlineMarker.mark(0);
            Object invoke = function4.invoke(obj, obj2, obj3, this);
            InlineMarker.mark(1);
            InlineMarker.mark(0);
            $receiver.emit(invoke, this);
            InlineMarker.mark(2);
            InlineMarker.mark(1);
            return Unit.INSTANCE;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: kotlinx.coroutines.flow.FlowCollector} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(java.lang.Object r13) {
            /*
                r12 = this;
                java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r1 = r12.label
                r2 = 0
                r3 = 2
                r4 = 1
                if (r1 == 0) goto L_0x0049
                r5 = 0
                if (r1 == r4) goto L_0x0028
                if (r1 != r3) goto L_0x0020
                r0 = r5
                r1 = r5
                java.lang.Object r2 = r12.L$1
                r0 = r2
                java.lang.Object[] r0 = (java.lang.Object[]) r0
                java.lang.Object r2 = r12.L$0
                r1 = r2
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                kotlin.ResultKt.throwOnFailure(r13)
                goto L_0x0082
            L_0x0020:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0028:
                r1 = r5
                r4 = r5
                r6 = r5
                java.lang.Object r7 = r12.L$4
                kotlinx.coroutines.flow.FlowCollector r7 = (kotlinx.coroutines.flow.FlowCollector) r7
                java.lang.Object r8 = r12.L$3
                r1 = r8
                java.lang.Object[] r1 = (java.lang.Object[]) r1
                java.lang.Object r8 = r12.L$2
                r5 = r8
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                java.lang.Object r8 = r12.L$1
                r4 = r8
                java.lang.Object[] r4 = (java.lang.Object[]) r4
                java.lang.Object r8 = r12.L$0
                r6 = r8
                kotlinx.coroutines.flow.FlowCollector r6 = (kotlinx.coroutines.flow.FlowCollector) r6
                kotlin.ResultKt.throwOnFailure(r13)
                r2 = r13
                r1 = r4
                goto L_0x0073
            L_0x0049:
                kotlin.ResultKt.throwOnFailure(r13)
                kotlinx.coroutines.flow.FlowCollector r7 = r12.f666p$
                java.lang.Object[] r1 = r12.p$0
                r5 = r12
                kotlin.coroutines.Continuation r5 = (kotlin.coroutines.Continuation) r5
                r6 = r1
                r8 = 0
                kotlinx.coroutines.flow.FlowKt__MigrationKt$combineLatest$$inlined$combine$1 r9 = r0
                kotlin.jvm.functions.Function4 r9 = r9.$transform$inlined$1
                r2 = r6[r2]
                r10 = r6[r4]
                r11 = r6[r3]
                r12.L$0 = r7
                r12.L$1 = r1
                r12.L$2 = r5
                r12.L$3 = r6
                r12.L$4 = r7
                r12.label = r4
                java.lang.Object r2 = r9.invoke(r2, r10, r11, r12)
                if (r2 != r0) goto L_0x0072
                return r0
            L_0x0072:
                r6 = r7
            L_0x0073:
                r12.L$0 = r6
                r12.L$1 = r1
                r12.label = r3
                java.lang.Object r2 = r7.emit(r2, r12)
                if (r2 != r0) goto L_0x0080
                return r0
            L_0x0080:
                r0 = r1
                r1 = r6
            L_0x0082:
                kotlin.Unit r2 = kotlin.Unit.INSTANCE
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__MigrationKt$combineLatest$$inlined$combine$1.C18323.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }
}
