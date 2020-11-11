package kotlinx.coroutines;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.RestrictedSuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequenceScope;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00030\u0002H@¢\u0006\u0004\b\u0004\u0010\u0005"}, mo33671d2 = {"<anonymous>", "", "Lkotlin/sequences/SequenceScope;", "Lkotlinx/coroutines/ChildJob;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.JobSupport$children$1", mo34305f = "JobSupport.kt", mo34306i = {0, 0, 1, 1, 1, 1, 1, 1}, mo34307l = {897, 899}, mo34308m = "invokeSuspend", mo34309n = {"$this$sequence", "state", "$this$sequence", "state", "list", "this_$iv", "cur$iv", "it"}, mo34310s = {"L$0", "L$1", "L$0", "L$1", "L$2", "L$3", "L$4", "L$5"})
/* compiled from: JobSupport.kt */
final class JobSupport$children$1 extends RestrictedSuspendLambda implements Function2<SequenceScope<? super ChildJob>, Continuation<? super Unit>, Object> {
    Object L$0;
    Object L$1;
    Object L$2;
    Object L$3;
    Object L$4;
    Object L$5;
    int label;

    /* renamed from: p$ */
    private SequenceScope f628p$;
    final /* synthetic */ JobSupport this$0;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    JobSupport$children$1(JobSupport jobSupport, Continuation continuation) {
        super(2, continuation);
        this.this$0 = jobSupport;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        JobSupport$children$1 jobSupport$children$1 = new JobSupport$children$1(this.this$0, continuation);
        SequenceScope sequenceScope = (SequenceScope) obj;
        jobSupport$children$1.f628p$ = (SequenceScope) obj;
        return jobSupport$children$1;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((JobSupport$children$1) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v7, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v8, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v8, resolved type: kotlinx.coroutines.internal.LockFreeLinkedListNode} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v10, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v7, resolved type: kotlinx.coroutines.NodeList} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r13v11, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r2v13, resolved type: kotlin.sequences.SequenceScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x00b1  */
    /* JADX WARNING: Removed duplicated region for block: B:33:0x00e3  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r18) {
        /*
            r17 = this;
            r0 = r17
            java.lang.Object r1 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L_0x0059
            r5 = 0
            if (r2 == r4) goto L_0x004c
            if (r2 != r3) goto L_0x0044
            r2 = r5
            r6 = r5
            r7 = 0
            r8 = r7
            r9 = r7
            r10 = r5
            r11 = r5
            r12 = r5
            java.lang.Object r13 = r0.L$5
            r6 = r13
            kotlinx.coroutines.ChildHandleNode r6 = (kotlinx.coroutines.ChildHandleNode) r6
            java.lang.Object r13 = r0.L$4
            r11 = r13
            kotlinx.coroutines.internal.LockFreeLinkedListNode r11 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r11
            java.lang.Object r13 = r0.L$3
            r10 = r13
            kotlinx.coroutines.NodeList r10 = (kotlinx.coroutines.NodeList) r10
            java.lang.Object r13 = r0.L$2
            r5 = r13
            kotlinx.coroutines.NodeList r5 = (kotlinx.coroutines.NodeList) r5
            java.lang.Object r12 = r0.L$1
            java.lang.Object r13 = r0.L$0
            r2 = r13
            kotlin.sequences.SequenceScope r2 = (kotlin.sequences.SequenceScope) r2
            kotlin.ResultKt.throwOnFailure(r18)
            r13 = r6
            r14 = r12
            r6 = r5
            r12 = r11
            r5 = r2
            r11 = r10
            r2 = r1
            r10 = r9
            r1 = r18
            r9 = r0
            goto L_0x00d9
        L_0x0044:
            java.lang.IllegalStateException r1 = new java.lang.IllegalStateException
            java.lang.String r2 = "call to 'resume' before 'invoke' with coroutine"
            r1.<init>(r2)
            throw r1
        L_0x004c:
            r1 = r5
            r2 = r5
            java.lang.Object r2 = r0.L$1
            java.lang.Object r3 = r0.L$0
            r1 = r3
            kotlin.sequences.SequenceScope r1 = (kotlin.sequences.SequenceScope) r1
            kotlin.ResultKt.throwOnFailure(r18)
            goto L_0x007c
        L_0x0059:
            kotlin.ResultKt.throwOnFailure(r18)
            kotlin.sequences.SequenceScope r2 = r0.f628p$
            kotlinx.coroutines.JobSupport r5 = r0.this$0
            java.lang.Object r5 = r5.getState$kotlinx_coroutines_core()
            boolean r6 = r5 instanceof kotlinx.coroutines.ChildHandleNode
            if (r6 == 0) goto L_0x0082
            r3 = r5
            kotlinx.coroutines.ChildHandleNode r3 = (kotlinx.coroutines.ChildHandleNode) r3
            kotlinx.coroutines.ChildJob r3 = r3.childJob
            r0.L$0 = r2
            r0.L$1 = r5
            r0.label = r4
            java.lang.Object r3 = r2.yield(r3, r0)
            if (r3 != r1) goto L_0x007a
            return r1
        L_0x007a:
            r1 = r2
            r2 = r5
        L_0x007c:
            r8 = r0
            r2 = r1
            r1 = r18
            goto L_0x00f2
        L_0x0082:
            boolean r6 = r5 instanceof kotlinx.coroutines.Incomplete
            if (r6 == 0) goto L_0x00ef
            r6 = r5
            kotlinx.coroutines.Incomplete r6 = (kotlinx.coroutines.Incomplete) r6
            kotlinx.coroutines.NodeList r6 = r6.getList()
            if (r6 == 0) goto L_0x00ef
            r7 = 0
            r8 = r6
            r9 = 0
            java.lang.Object r10 = r8.getNext()
            if (r10 == 0) goto L_0x00e7
            kotlinx.coroutines.internal.LockFreeLinkedListNode r10 = (kotlinx.coroutines.internal.LockFreeLinkedListNode) r10
            r12 = r5
            r11 = r10
            r5 = r2
            r10 = r8
            r8 = r0
            r2 = r1
            r1 = r18
            r16 = r9
            r9 = r7
            r7 = r16
        L_0x00a7:
            r13 = r10
            kotlinx.coroutines.internal.LockFreeLinkedListHead r13 = (kotlinx.coroutines.internal.LockFreeLinkedListHead) r13
            boolean r13 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r11, (java.lang.Object) r13)
            r13 = r13 ^ r4
            if (r13 == 0) goto L_0x00e3
            boolean r13 = r11 instanceof kotlinx.coroutines.ChildHandleNode
            if (r13 == 0) goto L_0x00de
            r13 = r11
            kotlinx.coroutines.ChildHandleNode r13 = (kotlinx.coroutines.ChildHandleNode) r13
            r14 = 0
            kotlinx.coroutines.ChildJob r15 = r13.childJob
            r8.L$0 = r5
            r8.L$1 = r12
            r8.L$2 = r6
            r8.L$3 = r10
            r8.L$4 = r11
            r8.L$5 = r13
            r8.label = r3
            java.lang.Object r15 = r5.yield(r15, r8)
            if (r15 != r2) goto L_0x00d0
            return r2
        L_0x00d0:
            r16 = r9
            r9 = r8
            r8 = r14
            r14 = r12
            r12 = r11
            r11 = r10
            r10 = r16
        L_0x00d9:
            r8 = r9
            r9 = r10
            r10 = r11
            r11 = r12
            r12 = r14
        L_0x00de:
            kotlinx.coroutines.internal.LockFreeLinkedListNode r11 = r11.getNextNode()
            goto L_0x00a7
        L_0x00e3:
            r2 = r5
            goto L_0x00f2
        L_0x00e7:
            kotlin.TypeCastException r1 = new kotlin.TypeCastException
            java.lang.String r3 = "null cannot be cast to non-null type kotlinx.coroutines.internal.Node /* = kotlinx.coroutines.internal.LockFreeLinkedListNode */"
            r1.<init>(r3)
            throw r1
        L_0x00ef:
            r1 = r18
            r8 = r0
        L_0x00f2:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.JobSupport$children$1.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
