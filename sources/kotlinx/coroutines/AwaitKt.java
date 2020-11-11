package kotlinx.coroutines;

import kotlin.Metadata;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000*\n\u0000\n\u0002\u0010 \n\u0002\b\u0002\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\u001a=\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\u001e\u0010\u0003\u001a\u0010\u0012\f\b\u0001\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\u0004\"\b\u0012\u0004\u0012\u0002H\u00020\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u001a%\u0010\u0007\u001a\u00020\b2\u0012\u0010\t\u001a\n\u0012\u0006\b\u0001\u0012\u00020\n0\u0004\"\u00020\nH@ø\u0001\u0000¢\u0006\u0002\u0010\u000b\u001a-\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u00050\fH@ø\u0001\u0000¢\u0006\u0002\u0010\r\u001a\u001b\u0010\u0007\u001a\u00020\b*\b\u0012\u0004\u0012\u00020\n0\fH@ø\u0001\u0000¢\u0006\u0002\u0010\r\u0002\u0004\n\u0002\b\u0019¨\u0006\u000e"}, mo33671d2 = {"awaitAll", "", "T", "deferreds", "", "Lkotlinx/coroutines/Deferred;", "([Lkotlinx/coroutines/Deferred;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "joinAll", "", "jobs", "Lkotlinx/coroutines/Job;", "([Lkotlinx/coroutines/Job;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "", "(Ljava/util/Collection;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: Await.kt */
public final class AwaitKt {
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object awaitAll(kotlinx.coroutines.Deferred<? extends T>[] r5, kotlin.coroutines.Continuation<? super java.util.List<? extends T>> r6) {
        /*
            boolean r0 = r6 instanceof kotlinx.coroutines.AwaitKt$awaitAll$1
            if (r0 == 0) goto L_0x0014
            r0 = r6
            kotlinx.coroutines.AwaitKt$awaitAll$1 r0 = (kotlinx.coroutines.AwaitKt$awaitAll$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.AwaitKt$awaitAll$1 r0 = new kotlinx.coroutines.AwaitKt$awaitAll$1
            r0.<init>(r6)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            if (r3 == 0) goto L_0x0038
            if (r3 != r4) goto L_0x0030
            java.lang.Object r2 = r0.L$0
            r5 = r2
            kotlinx.coroutines.Deferred[] r5 = (kotlinx.coroutines.Deferred[]) r5
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r1
            goto L_0x0058
        L_0x0030:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0038:
            kotlin.ResultKt.throwOnFailure(r1)
            int r3 = r5.length
            if (r3 != 0) goto L_0x0040
            r3 = r4
            goto L_0x0041
        L_0x0040:
            r3 = 0
        L_0x0041:
            if (r3 == 0) goto L_0x0048
            java.util.List r2 = kotlin.collections.CollectionsKt.emptyList()
            goto L_0x005b
        L_0x0048:
            kotlinx.coroutines.AwaitAll r3 = new kotlinx.coroutines.AwaitAll
            r3.<init>(r5)
            r0.L$0 = r5
            r0.label = r4
            java.lang.Object r3 = r3.await(r0)
            if (r3 != r2) goto L_0x0058
            return r2
        L_0x0058:
            r2 = r3
            java.util.List r2 = (java.util.List) r2
        L_0x005b:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.AwaitKt.awaitAll(kotlinx.coroutines.Deferred[], kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX WARNING: Removed duplicated region for block: B:12:0x0038  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final <T> java.lang.Object awaitAll(java.util.Collection<? extends kotlinx.coroutines.Deferred<? extends T>> r8, kotlin.coroutines.Continuation<? super java.util.List<? extends T>> r9) {
        /*
            boolean r0 = r9 instanceof kotlinx.coroutines.AwaitKt$awaitAll$2
            if (r0 == 0) goto L_0x0014
            r0 = r9
            kotlinx.coroutines.AwaitKt$awaitAll$2 r0 = (kotlinx.coroutines.AwaitKt$awaitAll$2) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.AwaitKt$awaitAll$2 r0 = new kotlinx.coroutines.AwaitKt$awaitAll$2
            r0.<init>(r9)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            if (r3 == 0) goto L_0x0038
            if (r3 != r4) goto L_0x0030
            java.lang.Object r2 = r0.L$0
            r8 = r2
            java.util.Collection r8 = (java.util.Collection) r8
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r1
            goto L_0x0064
        L_0x0030:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0038:
            kotlin.ResultKt.throwOnFailure(r1)
            boolean r3 = r8.isEmpty()
            if (r3 == 0) goto L_0x0046
            java.util.List r2 = kotlin.collections.CollectionsKt.emptyList()
            goto L_0x0067
        L_0x0046:
            r3 = r8
            r5 = 0
            r6 = r3
            r7 = 0
            kotlinx.coroutines.Deferred[] r7 = new kotlinx.coroutines.Deferred[r7]
            java.lang.Object[] r7 = r6.toArray(r7)
            if (r7 == 0) goto L_0x0068
            kotlinx.coroutines.Deferred[] r7 = (kotlinx.coroutines.Deferred[]) r7
            kotlinx.coroutines.AwaitAll r3 = new kotlinx.coroutines.AwaitAll
            r3.<init>(r7)
            r0.L$0 = r8
            r0.label = r4
            java.lang.Object r3 = r3.await(r0)
            if (r3 != r2) goto L_0x0064
            return r2
        L_0x0064:
            r2 = r3
            java.util.List r2 = (java.util.List) r2
        L_0x0067:
            return r2
        L_0x0068:
            kotlin.TypeCastException r2 = new kotlin.TypeCastException
            java.lang.String r4 = "null cannot be cast to non-null type kotlin.Array<T>"
            r2.<init>(r4)
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.AwaitKt.awaitAll(java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v1, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v6, resolved type: kotlinx.coroutines.Job[]} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r12v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r14v3, resolved type: kotlinx.coroutines.Job[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x0053  */
    /* JADX WARNING: Removed duplicated region for block: B:14:0x005d  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0025  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object joinAll(kotlinx.coroutines.Job[] r14, kotlin.coroutines.Continuation<? super kotlin.Unit> r15) {
        /*
            boolean r0 = r15 instanceof kotlinx.coroutines.AwaitKt$joinAll$1
            if (r0 == 0) goto L_0x0014
            r0 = r15
            kotlinx.coroutines.AwaitKt$joinAll$1 r0 = (kotlinx.coroutines.AwaitKt$joinAll$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.AwaitKt$joinAll$1 r0 = new kotlinx.coroutines.AwaitKt$joinAll$1
            r0.<init>(r15)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            r5 = 0
            if (r3 == 0) goto L_0x0053
            if (r3 != r4) goto L_0x004b
            r3 = 0
            r6 = r3
            r7 = r3
            r8 = r5
            java.lang.Object r9 = r0.L$4
            r7 = r9
            kotlinx.coroutines.Job r7 = (kotlinx.coroutines.Job) r7
            java.lang.Object r9 = r0.L$3
            r3 = r9
            kotlinx.coroutines.Job r3 = (kotlinx.coroutines.Job) r3
            int r9 = r0.I$1
            int r10 = r0.I$0
            java.lang.Object r11 = r0.L$2
            kotlinx.coroutines.Job[] r11 = (kotlinx.coroutines.Job[]) r11
            java.lang.Object r12 = r0.L$1
            r6 = r12
            kotlinx.coroutines.Job[] r6 = (kotlinx.coroutines.Job[]) r6
            java.lang.Object r12 = r0.L$0
            r14 = r12
            kotlinx.coroutines.Job[] r14 = (kotlinx.coroutines.Job[]) r14
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x007f
        L_0x004b:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0053:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r14
            r6 = 0
            int r7 = r3.length
            r11 = r3
            r10 = r7
        L_0x005b:
            if (r5 >= r10) goto L_0x0087
            r7 = r11[r5]
            r8 = r7
            r9 = 0
            r0.L$0 = r14
            r0.L$1 = r3
            r0.L$2 = r11
            r0.I$0 = r10
            r0.I$1 = r5
            r0.L$3 = r7
            r0.L$4 = r8
            r0.label = r4
            java.lang.Object r12 = r8.join(r0)
            if (r12 != r2) goto L_0x0078
            return r2
        L_0x0078:
            r13 = r6
            r6 = r3
            r3 = r7
            r7 = r8
            r8 = r9
            r9 = r5
            r5 = r13
        L_0x007f:
            int r3 = r9 + 1
            r13 = r5
            r5 = r3
            r3 = r6
            r6 = r13
            goto L_0x005b
        L_0x0087:
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.AwaitKt.joinAll(kotlinx.coroutines.Job[], kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r10v2, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v2, resolved type: java.util.Collection<? extends kotlinx.coroutines.Job>} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:12:0x004c  */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x0060  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x0024  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.Object joinAll(java.util.Collection<? extends kotlinx.coroutines.Job> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            boolean r0 = r12 instanceof kotlinx.coroutines.AwaitKt$joinAll$3
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.AwaitKt$joinAll$3 r0 = (kotlinx.coroutines.AwaitKt$joinAll$3) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.AwaitKt$joinAll$3 r0 = new kotlinx.coroutines.AwaitKt$joinAll$3
            r0.<init>(r12)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 1
            if (r3 == 0) goto L_0x004c
            if (r3 != r4) goto L_0x0044
            r3 = 0
            r5 = r3
            r6 = r3
            r7 = 0
            r8 = r7
            java.lang.Object r9 = r0.L$4
            r6 = r9
            kotlinx.coroutines.Job r6 = (kotlinx.coroutines.Job) r6
            java.lang.Object r3 = r0.L$3
            java.lang.Object r9 = r0.L$2
            java.util.Iterator r9 = (java.util.Iterator) r9
            java.lang.Object r10 = r0.L$1
            r5 = r10
            java.lang.Iterable r5 = (java.lang.Iterable) r5
            java.lang.Object r10 = r0.L$0
            r11 = r10
            java.util.Collection r11 = (java.util.Collection) r11
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x007b
        L_0x0044:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x004c:
            kotlin.ResultKt.throwOnFailure(r1)
            r3 = r11
            java.lang.Iterable r3 = (java.lang.Iterable) r3
            r5 = 0
            java.util.Iterator r6 = r3.iterator()
            r8 = r5
            r9 = r6
            r5 = r3
        L_0x005a:
            boolean r3 = r9.hasNext()
            if (r3 == 0) goto L_0x007d
            java.lang.Object r3 = r9.next()
            r6 = r3
            kotlinx.coroutines.Job r6 = (kotlinx.coroutines.Job) r6
            r7 = 0
            r0.L$0 = r11
            r0.L$1 = r5
            r0.L$2 = r9
            r0.L$3 = r3
            r0.L$4 = r6
            r0.label = r4
            java.lang.Object r10 = r6.join(r0)
            if (r10 != r2) goto L_0x007b
            return r2
        L_0x007b:
            goto L_0x005a
        L_0x007d:
            kotlin.Unit r2 = kotlin.Unit.INSTANCE
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.AwaitKt.joinAll(java.util.Collection, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
