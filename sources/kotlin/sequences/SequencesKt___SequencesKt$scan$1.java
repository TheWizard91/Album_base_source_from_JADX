package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo33671d2 = {"<anonymous>", "", "T", "R", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 16})
@DebugMetadata(mo34304c = "kotlin.sequences.SequencesKt___SequencesKt$scan$1", mo34305f = "_Sequences.kt", mo34306i = {0, 1, 1, 1}, mo34307l = {1427, 1431}, mo34308m = "invokeSuspend", mo34309n = {"$this$sequence", "$this$sequence", "accumulator", "element"}, mo34310s = {"L$0", "L$0", "L$1", "L$2"})
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$scan$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Object $initial;
    final /* synthetic */ Function2 $operation;
    final /* synthetic */ Sequence $this_scan;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* renamed from: p$ */
    private SequenceScope f620p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$scan$1(Sequence sequence, Object obj, Function2 function2, Continuation continuation) {
        super(2, continuation);
        this.$this_scan = sequence;
        this.$initial = obj;
        this.$operation = function2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        SequencesKt___SequencesKt$scan$1 sequencesKt___SequencesKt$scan$1 = new SequencesKt___SequencesKt$scan$1(this.$this_scan, this.$initial, this.$operation, continuation);
        SequenceScope sequenceScope = (SequenceScope) obj;
        sequencesKt___SequencesKt$scan$1.f620p$ = (SequenceScope) obj;
        return sequencesKt___SequencesKt$scan$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SequencesKt___SequencesKt$scan$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0058  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r10) {
        /*
            r9 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r9.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L_0x0036
            r4 = 0
            if (r1 == r3) goto L_0x002c
            if (r1 != r2) goto L_0x0024
            r1 = r4
            r3 = r4
            java.lang.Object r5 = r9.L$3
            java.util.Iterator r5 = (java.util.Iterator) r5
            java.lang.Object r3 = r9.L$2
            java.lang.Object r4 = r9.L$1
            java.lang.Object r6 = r9.L$0
            r1 = r6
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r10)
            r6 = r5
            r5 = r9
            goto L_0x0078
        L_0x0024:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002c:
            r1 = r4
            java.lang.Object r3 = r9.L$0
            r1 = r3
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r10)
            goto L_0x0048
        L_0x0036:
            kotlin.ResultKt.throwOnFailure(r10)
            kotlin.sequences.SequenceScope r1 = r9.f620p$
            java.lang.Object r4 = r9.$initial
            r9.L$0 = r1
            r9.label = r3
            java.lang.Object r3 = r1.yield(r4, r9)
            if (r3 != r0) goto L_0x0048
            return r0
        L_0x0048:
            java.lang.Object r3 = r9.$initial
            kotlin.sequences.Sequence r4 = r9.$this_scan
            java.util.Iterator r4 = r4.iterator()
            r5 = r4
            r4 = r9
        L_0x0052:
            boolean r6 = r5.hasNext()
            if (r6 == 0) goto L_0x007c
            java.lang.Object r6 = r5.next()
            kotlin.jvm.functions.Function2 r7 = r4.$operation
            java.lang.Object r3 = r7.invoke(r3, r6)
            r4.L$0 = r1
            r4.L$1 = r3
            r4.L$2 = r6
            r4.L$3 = r5
            r4.label = r2
            java.lang.Object r7 = r1.yield(r3, r4)
            if (r7 != r0) goto L_0x0073
            return r0
        L_0x0073:
            r8 = r4
            r4 = r3
            r3 = r6
            r6 = r5
            r5 = r8
        L_0x0078:
            r3 = r4
            r4 = r5
            r5 = r6
            goto L_0x0052
        L_0x007c:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$scan$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
