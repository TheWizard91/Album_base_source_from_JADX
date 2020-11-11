package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.internal.CombineKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00009\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\t"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__MigrationKt$combine$$inlined$unsafeFlow$2", "kotlinx/coroutines/flow/FlowKt__MigrationKt$combine$$inlined$combine$2"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
public final class FlowKt__MigrationKt$combineLatest$$inlined$combine$2 implements Flow<R> {
    final /* synthetic */ Flow[] $flows$inlined;
    final /* synthetic */ Function5 $transform$inlined$1;

    public FlowKt__MigrationKt$combineLatest$$inlined$combine$2(Flow[] flowArr, Function5 function5) {
        this.$flows$inlined = flowArr;
        this.$transform$inlined$1 = function5;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        Continuation continuation = $completion;
        return CombineKt.combineInternal(collector, this.$flows$inlined, new Function0<Object[]>(this) {
            final /* synthetic */ FlowKt__MigrationKt$combineLatest$$inlined$combine$2 this$0;

            {
                this.this$0 = r1;
            }

            public final Object[] invoke() {
                return new Object[this.this$0.$flows$inlined.length];
            }
        }, new C18353((Continuation) null), $completion);
    }

    public Object collect$$forInline(FlowCollector collector, Continuation continuation) {
        InlineMarker.mark(4);
        new ContinuationImpl(this, continuation) {
            Object L$0;
            Object L$1;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ FlowKt__MigrationKt$combineLatest$$inlined$combine$2 this$0;

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
            final /* synthetic */ FlowKt__MigrationKt$combineLatest$$inlined$combine$2 this$0;

            {
                this.this$0 = r1;
            }

            public final Object[] invoke() {
                return new Object[this.this$0.$flows$inlined.length];
            }
        }, new C18353((Continuation) null), continuation);
        InlineMarker.mark(2);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000h\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0004\n\u0002\b\u0004\n\u0002\b\u0004\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0004\b\u0007\u0010\b¨\u0006\u000b"}, mo33671d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "it", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__ZipKt$combine$5$2", "kotlinx/coroutines/flow/FlowKt__MigrationKt$combine$$inlined$unsafeFlow$2$lambda$2", "kotlinx/coroutines/flow/FlowKt__MigrationKt$combine$$inlined$combine$2$3"}, mo33672k = 3, mo33673mv = {1, 1, 15})
    @DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__MigrationKt$combineLatest$$inlined$combine$2$3", mo34305f = "Migration.kt", mo34306i = {0, 0, 0, 0, 1, 1}, mo34307l = {318, 319}, mo34308m = "invokeSuspend", mo34309n = {"$receiver", "it", "continuation", "args", "$receiver", "it"}, mo34310s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1"})
    /* renamed from: kotlinx.coroutines.flow.FlowKt__MigrationKt$combineLatest$$inlined$combine$2$3 */
    /* compiled from: Zip.kt */
    public static final class C18353 extends SuspendLambda implements Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object> {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;

        /* renamed from: p$ */
        private FlowCollector f667p$;
        private Object[] p$0;

        public final Continuation<Unit> create(FlowCollector<? super R> flowCollector, Object[] objArr, Continuation<? super Unit> continuation) {
            Intrinsics.checkParameterIsNotNull(flowCollector, "$this$create");
            Intrinsics.checkParameterIsNotNull(objArr, "it");
            Intrinsics.checkParameterIsNotNull(continuation, "continuation");
            C18353 r0 = new C18353(continuation, this);
            r0.f667p$ = flowCollector;
            r0.p$0 = objArr;
            return r0;
        }

        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return ((C18353) create((FlowCollector) obj, (Object[]) obj2, (Continuation) obj3)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend$$forInline(Object $result) {
            FlowCollector $receiver = this.f667p$;
            Continuation continuation = this;
            Object[] args = this.p$0;
            Function5 function5 = this.$transform$inlined$1;
            Object obj = args[0];
            Object obj2 = args[1];
            Object obj3 = args[2];
            Object obj4 = args[3];
            InlineMarker.mark(0);
            Object invoke = function5.invoke(obj, obj2, obj3, obj4, this);
            InlineMarker.mark(1);
            InlineMarker.mark(0);
            $receiver.emit(invoke, this);
            InlineMarker.mark(2);
            InlineMarker.mark(1);
            return Unit.INSTANCE;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r8v4, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: kotlinx.coroutines.flow.FlowCollector} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(java.lang.Object r14) {
            /*
                r13 = this;
                java.lang.Object r6 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r0 = r13.label
                r1 = 0
                r7 = 2
                r2 = 1
                if (r0 == 0) goto L_0x004a
                r3 = 0
                if (r0 == r2) goto L_0x0029
                if (r0 != r7) goto L_0x0021
                r0 = r3
                r1 = r3
                java.lang.Object r2 = r13.L$1
                r0 = r2
                java.lang.Object[] r0 = (java.lang.Object[]) r0
                java.lang.Object r2 = r13.L$0
                r1 = r2
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                kotlin.ResultKt.throwOnFailure(r14)
                goto L_0x008b
            L_0x0021:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x0029:
                r0 = r3
                r2 = r3
                r4 = r3
                java.lang.Object r5 = r13.L$4
                kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
                java.lang.Object r8 = r13.L$3
                r0 = r8
                java.lang.Object[] r0 = (java.lang.Object[]) r0
                java.lang.Object r8 = r13.L$2
                r3 = r8
                kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
                java.lang.Object r8 = r13.L$1
                r2 = r8
                java.lang.Object[] r2 = (java.lang.Object[]) r2
                java.lang.Object r8 = r13.L$0
                r4 = r8
                kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
                kotlin.ResultKt.throwOnFailure(r14)
                r0 = r14
                r1 = r4
                goto L_0x007d
            L_0x004a:
                kotlin.ResultKt.throwOnFailure(r14)
                kotlinx.coroutines.flow.FlowCollector r8 = r13.f667p$
                java.lang.Object[] r9 = r13.p$0
                r10 = r13
                kotlin.coroutines.Continuation r10 = (kotlin.coroutines.Continuation) r10
                r11 = r9
                r12 = 0
                kotlinx.coroutines.flow.FlowKt__MigrationKt$combineLatest$$inlined$combine$2 r0 = r0
                kotlin.jvm.functions.Function5 r0 = r0.$transform$inlined$1
                r1 = r11[r1]
                r3 = r11[r2]
                r4 = r11[r7]
                r5 = 3
                r5 = r11[r5]
                r13.L$0 = r8
                r13.L$1 = r9
                r13.L$2 = r10
                r13.L$3 = r11
                r13.L$4 = r8
                r13.label = r2
                r2 = r3
                r3 = r4
                r4 = r5
                r5 = r13
                java.lang.Object r0 = r0.invoke(r1, r2, r3, r4, r5)
                if (r0 != r6) goto L_0x007a
                return r6
            L_0x007a:
                r1 = r8
                r5 = r1
                r2 = r9
            L_0x007d:
                r13.L$0 = r1
                r13.L$1 = r2
                r13.label = r7
                java.lang.Object r0 = r5.emit(r0, r13)
                if (r0 != r6) goto L_0x008a
                return r6
            L_0x008a:
                r0 = r2
            L_0x008b:
                kotlin.Unit r2 = kotlin.Unit.INSTANCE
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__MigrationKt$combineLatest$$inlined$combine$2.C18353.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }
}
