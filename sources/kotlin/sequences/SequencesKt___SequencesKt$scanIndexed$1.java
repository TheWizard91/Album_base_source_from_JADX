package kotlin.sequences;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0003*\b\u0012\u0004\u0012\u0002H\u00030\u0004H@¢\u0006\u0004\b\u0005\u0010\u0006"}, mo33671d2 = {"<anonymous>", "", "T", "R", "Lkotlin/sequences/SequenceScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 16})
@DebugMetadata(mo34304c = "kotlin.sequences.SequencesKt___SequencesKt$scanIndexed$1", mo34305f = "_Sequences.kt", mo34306i = {0, 1, 1, 1, 1}, mo34307l = {1456, 1461}, mo34308m = "invokeSuspend", mo34309n = {"$this$sequence", "$this$sequence", "index", "accumulator", "element"}, mo34310s = {"L$0", "L$0", "I$0", "L$1", "L$2"})
/* compiled from: _Sequences.kt */
final class SequencesKt___SequencesKt$scanIndexed$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super R>, Continuation<? super Unit>, Object> {
    final /* synthetic */ Object $initial;
    final /* synthetic */ Function3 $operation;
    final /* synthetic */ Sequence $this_scanIndexed;
    int I$0;
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    int label;

    /* renamed from: p$ */
    private SequenceScope f621p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    SequencesKt___SequencesKt$scanIndexed$1(Sequence sequence, Object obj, Function3 function3, Continuation continuation) {
        super(2, continuation);
        this.$this_scanIndexed = sequence;
        this.$initial = obj;
        this.$operation = function3;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        SequencesKt___SequencesKt$scanIndexed$1 sequencesKt___SequencesKt$scanIndexed$1 = new SequencesKt___SequencesKt$scanIndexed$1(this.$this_scanIndexed, this.$initial, this.$operation, continuation);
        SequenceScope sequenceScope = (SequenceScope) obj;
        sequencesKt___SequencesKt$scanIndexed$1.f621p$ = (SequenceScope) obj;
        return sequencesKt___SequencesKt$scanIndexed$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((SequencesKt___SequencesKt$scanIndexed$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r7v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v10, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x005f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r11.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L_0x003c
            r4 = 0
            if (r1 == r3) goto L_0x0032
            if (r1 != r2) goto L_0x002a
            r1 = r4
            r3 = r4
            r5 = 0
            java.lang.Object r6 = r11.L$3
            java.util.Iterator r6 = (java.util.Iterator) r6
            java.lang.Object r4 = r11.L$2
            java.lang.Object r3 = r11.L$1
            int r5 = r11.I$0
            java.lang.Object r7 = r11.L$0
            r1 = r7
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r12)
            r7 = r6
            r6 = r11
            r10 = r5
            r5 = r3
            r3 = r10
            goto L_0x008c
        L_0x002a:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0032:
            r1 = r4
            java.lang.Object r3 = r11.L$0
            r1 = r3
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r12)
            goto L_0x004e
        L_0x003c:
            kotlin.ResultKt.throwOnFailure(r12)
            kotlin.sequences.SequenceScope r1 = r11.f621p$
            java.lang.Object r4 = r11.$initial
            r11.L$0 = r1
            r11.label = r3
            java.lang.Object r3 = r1.yield(r4, r11)
            if (r3 != r0) goto L_0x004e
            return r0
        L_0x004e:
            r3 = 0
            java.lang.Object r4 = r11.$initial
            kotlin.sequences.Sequence r5 = r11.$this_scanIndexed
            java.util.Iterator r5 = r5.iterator()
            r6 = r5
            r5 = r11
        L_0x0059:
            boolean r7 = r6.hasNext()
            if (r7 == 0) goto L_0x0090
            java.lang.Object r7 = r6.next()
            kotlin.jvm.functions.Function3 r8 = r5.$operation
            int r9 = r3 + 1
            if (r3 >= 0) goto L_0x006c
            kotlin.collections.CollectionsKt.throwIndexOverflow()
        L_0x006c:
            java.lang.Integer r3 = kotlin.coroutines.jvm.internal.Boxing.boxInt(r3)
            java.lang.Object r3 = r8.invoke(r3, r4, r7)
            r5.L$0 = r1
            r5.I$0 = r9
            r5.L$1 = r3
            r5.L$2 = r7
            r5.L$3 = r6
            r5.label = r2
            java.lang.Object r4 = r1.yield(r3, r5)
            if (r4 != r0) goto L_0x0087
            return r0
        L_0x0087:
            r4 = r7
            r7 = r6
            r6 = r5
            r5 = r3
            r3 = r9
        L_0x008c:
            r4 = r5
            r5 = r6
            r6 = r7
            goto L_0x0059
        L_0x0090:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.sequences.SequencesKt___SequencesKt$scanIndexed$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
