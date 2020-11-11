package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.flow.internal.CombineKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00009\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\t"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__MigrationKt$combine$$inlined$unsafeFlow$3", "kotlinx/coroutines/flow/FlowKt__MigrationKt$combine$$inlined$combine$3"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
public final class FlowKt__MigrationKt$combineLatest$$inlined$combine$3 implements Flow<R> {
    final /* synthetic */ Flow[] $flows$inlined;
    final /* synthetic */ Function6 $transform$inlined$1;

    public FlowKt__MigrationKt$combineLatest$$inlined$combine$3(Flow[] flowArr, Function6 function6) {
        this.$flows$inlined = flowArr;
        this.$transform$inlined$1 = function6;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        Continuation continuation = $completion;
        return CombineKt.combineInternal(collector, this.$flows$inlined, new Function0<Object[]>(this) {
            final /* synthetic */ FlowKt__MigrationKt$combineLatest$$inlined$combine$3 this$0;

            {
                this.this$0 = r1;
            }

            public final Object[] invoke() {
                return new Object[this.this$0.$flows$inlined.length];
            }
        }, new C18383((Continuation) null), $completion);
    }

    public Object collect$$forInline(FlowCollector collector, Continuation continuation) {
        InlineMarker.mark(4);
        new ContinuationImpl(this, continuation) {
            Object L$0;
            Object L$1;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ FlowKt__MigrationKt$combineLatest$$inlined$combine$3 this$0;

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
            final /* synthetic */ FlowKt__MigrationKt$combineLatest$$inlined$combine$3 this$0;

            {
                this.this$0 = r1;
            }

            public final Object[] invoke() {
                return new Object[this.this$0.$flows$inlined.length];
            }
        }, new C18383((Continuation) null), continuation);
        InlineMarker.mark(2);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000h\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0004\n\u0002\b\u0004\n\u0002\b\u0004\n\u0002\b\u0005\u0010\u0000\u001a\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0006H@¢\u0006\u0004\b\u0007\u0010\b¨\u0006\u000b"}, mo33671d2 = {"<anonymous>", "", "T", "R", "Lkotlinx/coroutines/flow/FlowCollector;", "it", "", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;", "kotlinx/coroutines/flow/FlowKt__ZipKt$combine$5$2", "kotlinx/coroutines/flow/FlowKt__MigrationKt$combine$$inlined$unsafeFlow$3$lambda$2", "kotlinx/coroutines/flow/FlowKt__MigrationKt$combine$$inlined$combine$3$3"}, mo33672k = 3, mo33673mv = {1, 1, 15})
    @DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__MigrationKt$combineLatest$$inlined$combine$3$3", mo34305f = "Migration.kt", mo34306i = {0, 0, 0, 0, 1, 1}, mo34307l = {319, 320}, mo34308m = "invokeSuspend", mo34309n = {"$receiver", "it", "continuation", "args", "$receiver", "it"}, mo34310s = {"L$0", "L$1", "L$2", "L$3", "L$0", "L$1"})
    /* renamed from: kotlinx.coroutines.flow.FlowKt__MigrationKt$combineLatest$$inlined$combine$3$3 */
    /* compiled from: Zip.kt */
    public static final class C18383 extends SuspendLambda implements Function3<FlowCollector<? super R>, Object[], Continuation<? super Unit>, Object> {
        Object L$0;
        Object L$1;
        Object L$2;
        Object L$3;
        Object L$4;
        int label;

        /* renamed from: p$ */
        private FlowCollector f668p$;
        private Object[] p$0;

        public final Continuation<Unit> create(FlowCollector<? super R> flowCollector, Object[] objArr, Continuation<? super Unit> continuation) {
            Intrinsics.checkParameterIsNotNull(flowCollector, "$this$create");
            Intrinsics.checkParameterIsNotNull(objArr, "it");
            Intrinsics.checkParameterIsNotNull(continuation, "continuation");
            C18383 r0 = new C18383(continuation, this);
            r0.f668p$ = flowCollector;
            r0.p$0 = objArr;
            return r0;
        }

        public final Object invoke(Object obj, Object obj2, Object obj3) {
            return ((C18383) create((FlowCollector) obj, (Object[]) obj2, (Continuation) obj3)).invokeSuspend(Unit.INSTANCE);
        }

        public final Object invokeSuspend$$forInline(Object $result) {
            FlowCollector $receiver = this.f668p$;
            Continuation continuation = this;
            Object[] args = this.p$0;
            Function6 function6 = this.$transform$inlined$1;
            Object obj = args[0];
            Object obj2 = args[1];
            Object obj3 = args[2];
            Object obj4 = args[3];
            Object obj5 = args[4];
            InlineMarker.mark(0);
            Object invoke = function6.invoke(obj, obj2, obj3, obj4, obj5, this);
            InlineMarker.mark(1);
            InlineMarker.mark(0);
            $receiver.emit(invoke, this);
            InlineMarker.mark(2);
            InlineMarker.mark(1);
            return Unit.INSTANCE;
        }

        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: java.lang.Object} */
        /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v3, resolved type: kotlinx.coroutines.flow.FlowCollector} */
        /* JADX WARNING: Multi-variable type inference failed */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public final java.lang.Object invokeSuspend(java.lang.Object r16) {
            /*
                r15 = this;
                r7 = r15
                java.lang.Object r8 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
                int r0 = r7.label
                r1 = 0
                r9 = 2
                r2 = 1
                if (r0 == 0) goto L_0x004c
                r3 = 0
                if (r0 == r2) goto L_0x002a
                if (r0 != r9) goto L_0x0022
                r0 = r3
                r1 = r3
                java.lang.Object r2 = r7.L$1
                r0 = r2
                java.lang.Object[] r0 = (java.lang.Object[]) r0
                java.lang.Object r2 = r7.L$0
                r1 = r2
                kotlinx.coroutines.flow.FlowCollector r1 = (kotlinx.coroutines.flow.FlowCollector) r1
                kotlin.ResultKt.throwOnFailure(r16)
                goto L_0x0091
            L_0x0022:
                java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
                java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
                r0.<init>(r1)
                throw r0
            L_0x002a:
                r0 = r3
                r2 = r3
                r4 = r3
                java.lang.Object r5 = r7.L$4
                kotlinx.coroutines.flow.FlowCollector r5 = (kotlinx.coroutines.flow.FlowCollector) r5
                java.lang.Object r6 = r7.L$3
                r0 = r6
                java.lang.Object[] r0 = (java.lang.Object[]) r0
                java.lang.Object r6 = r7.L$2
                r3 = r6
                kotlin.coroutines.Continuation r3 = (kotlin.coroutines.Continuation) r3
                java.lang.Object r6 = r7.L$1
                r2 = r6
                java.lang.Object[] r2 = (java.lang.Object[]) r2
                java.lang.Object r6 = r7.L$0
                r4 = r6
                kotlinx.coroutines.flow.FlowCollector r4 = (kotlinx.coroutines.flow.FlowCollector) r4
                kotlin.ResultKt.throwOnFailure(r16)
                r0 = r16
                r1 = r4
                goto L_0x0083
            L_0x004c:
                kotlin.ResultKt.throwOnFailure(r16)
                kotlinx.coroutines.flow.FlowCollector r10 = r7.f668p$
                java.lang.Object[] r11 = r7.p$0
                r12 = r7
                kotlin.coroutines.Continuation r12 = (kotlin.coroutines.Continuation) r12
                r13 = r11
                r14 = 0
                kotlinx.coroutines.flow.FlowKt__MigrationKt$combineLatest$$inlined$combine$3 r0 = r0
                kotlin.jvm.functions.Function6 r0 = r0.$transform$inlined$1
                r1 = r13[r1]
                r3 = r13[r2]
                r4 = r13[r9]
                r5 = 3
                r5 = r13[r5]
                r6 = 4
                r6 = r13[r6]
                r7.L$0 = r10
                r7.L$1 = r11
                r7.L$2 = r12
                r7.L$3 = r13
                r7.L$4 = r10
                r7.label = r2
                r2 = r3
                r3 = r4
                r4 = r5
                r5 = r6
                r6 = r15
                java.lang.Object r0 = r0.invoke(r1, r2, r3, r4, r5, r6)
                if (r0 != r8) goto L_0x0080
                return r8
            L_0x0080:
                r1 = r10
                r5 = r1
                r2 = r11
            L_0x0083:
                r7.L$0 = r1
                r7.L$1 = r2
                r7.label = r9
                java.lang.Object r0 = r5.emit(r0, r15)
                if (r0 != r8) goto L_0x0090
                return r8
            L_0x0090:
                r0 = r2
            L_0x0091:
                kotlin.Unit r2 = kotlin.Unit.INSTANCE
                return r2
            */
            throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__MigrationKt$combineLatest$$inlined$combine$3.C18383.invokeSuspend(java.lang.Object):java.lang.Object");
        }
    }
}
