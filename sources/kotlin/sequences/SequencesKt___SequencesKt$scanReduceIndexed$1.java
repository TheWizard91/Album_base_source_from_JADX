package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\b\b\u0001\u0010\u0003*\u0002H\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo33671d2 = {"<anonymous>", "", "S", "T", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 16})
@DebugMetadata(mo34304c = "kotlin.sequences.SequencesKt___SequencesKt$scanReduceIndexed$1", mo34305f = "_Sequences.kt", mo34306i = {0, 0, 0, 1, 1, 1, 1}, mo34307l = {1516, 1520}, mo34308m = "invokeSuspend", mo34309n = {"$this$sequence", "iterator", "accumulator", "$this$sequence", "iterator", "accumulator", "index"}, mo34310s = {"L$0", "L$1", "L$2", "L$0", "L$1", "L$2", "I$0"})
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$scanReduceIndexed$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super S>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Function3 $operation;
    final /* synthetic */ Sequence $this_scanReduceIndexed;
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    int label;

    /* renamed from: p$ */
    private SequenceScope f623p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$scanReduceIndexed$1(Sequence sequence, Function3 function3, Continuation continuation) {
        super(2, continuation);
        this.$this_scanReduceIndexed = sequence;
        this.$operation = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        SequencesKt___SequencesKt$scanReduceIndexed$1 sequencesKt___SequencesKt$scanReduceIndexed$1 = new SequencesKt___SequencesKt$scanReduceIndexed$1(this.$this_scanReduceIndexed, this.$operation, continuation);
        SequenceScope sequenceScope = (SequenceScope) obj;
        sequencesKt___SequencesKt$scanReduceIndexed$1.f623p$ = (SequenceScope) obj;
        return sequencesKt___SequencesKt$scanReduceIndexed$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SequencesKt___SequencesKt$scanReduceIndexed$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: java.util.Iterator} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v9, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v5, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v3, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v7, resolved type: java.util.Iterator} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x006d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r11) {
        /*
            r10 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r10.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L_0x0040
            r4 = 0
            if (r1 == r3) goto L_0x002e
            if (r1 != r2) goto L_0x0026
            r1 = r4
            r3 = r4
            r5 = 0
            int r5 = r10.I$0
            java.lang.Object r3 = r10.L$2
            java.lang.Object r6 = r10.L$1
            r4 = r6
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r6 = r10.L$0
            r1 = r6
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r11)
            r6 = r10
            goto L_0x0094
        L_0x0026:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x002e:
            r1 = r4
            r3 = r4
            java.lang.Object r3 = r10.L$2
            java.lang.Object r5 = r10.L$1
            r4 = r5
            java.util.Iterator r4 = (java.util.Iterator) r4
            java.lang.Object r5 = r10.L$0
            r1 = r5
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r11)
            goto L_0x0065
        L_0x0040:
            kotlin.ResultKt.throwOnFailure(r11)
            kotlin.sequences.SequenceScope r1 = r10.f623p$
            kotlin.sequences.Sequence r4 = r10.$this_scanReduceIndexed
            java.util.Iterator r4 = r4.iterator()
            boolean r5 = r4.hasNext()
            if (r5 == 0) goto L_0x0095
            java.lang.Object r5 = r4.next()
            r10.L$0 = r1
            r10.L$1 = r4
            r10.L$2 = r5
            r10.label = r3
            java.lang.Object r3 = r1.yield(r5, r10)
            if (r3 != r0) goto L_0x0064
            return r0
        L_0x0064:
            r3 = r5
        L_0x0065:
            r5 = 1
            r6 = r10
        L_0x0067:
            boolean r7 = r4.hasNext()
            if (r7 == 0) goto L_0x0096
            kotlin.jvm.functions.Function3 r7 = r6.$operation
            int r8 = r5 + 1
            if (r5 >= 0) goto L_0x0076
            kotlin.collections.CollectionsKt.throwIndexOverflow()
        L_0x0076:
            java.lang.Integer r5 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r5)
            java.lang.Object r9 = r4.next()
            java.lang.Object r3 = r7.invoke(r5, r3, r9)
            r6.L$0 = r1
            r6.L$1 = r4
            r6.L$2 = r3
            r6.I$0 = r8
            r6.label = r2
            java.lang.Object r5 = r1.yield(r3, r6)
            if (r5 != r0) goto L_0x0093
            return r0
        L_0x0093:
            r5 = r8
        L_0x0094:
            goto L_0x0067
        L_0x0095:
            r6 = r10
        L_0x0096:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$scanReduceIndexed$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
