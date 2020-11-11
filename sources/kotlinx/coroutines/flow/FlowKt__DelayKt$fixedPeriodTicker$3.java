package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.channels.ProducerScope;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H@¢\u0006\u0004\b\u0003\u0010\u0004"}, mo33671d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.flow.FlowKt__DelayKt$fixedPeriodTicker$3", mo34305f = "Delay.kt", mo34306i = {0, 1, 2}, mo34307l = {129, 131, 132}, mo34308m = "invokeSuspend", mo34309n = {"$this$produce", "$this$produce", "$this$produce"}, mo34310s = {"L$0", "L$0", "L$0"})
/* compiled from: Delay.kt */
final class FlowKt__DelayKt$fixedPeriodTicker$3 extends SuspendLambda implements Function2<ProducerScope<? super Unit>, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $delayMillis;
    final /* synthetic */ long $initialDelayMillis;
    Object L$0;
    int label;

    /* renamed from: p$ */
    private ProducerScope f650p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    FlowKt__DelayKt$fixedPeriodTicker$3(long j, long j2, Continuation continuation) {
        super(2, continuation);
        this.$initialDelayMillis = j;
        this.$delayMillis = j2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        FlowKt__DelayKt$fixedPeriodTicker$3 flowKt__DelayKt$fixedPeriodTicker$3 = new FlowKt__DelayKt$fixedPeriodTicker$3(this.$initialDelayMillis, this.$delayMillis, continuation);
        ProducerScope producerScope = (ProducerScope) obj;
        flowKt__DelayKt$fixedPeriodTicker$3.f650p$ = (ProducerScope) obj;
        return flowKt__DelayKt$fixedPeriodTicker$3;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((FlowKt__DelayKt$fixedPeriodTicker$3) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v9, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r4v8, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v13, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:17:0x005d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x006a A[RETURN] */
    public final java.lang.Object invokeSuspend(java.lang.Object r8) {
        /*
            r7 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r7.label
            r2 = 3
            r3 = 2
            r4 = 1
            if (r1 == 0) goto L_0x003a
            r5 = 0
            if (r1 == r4) goto L_0x0030
            if (r1 == r3) goto L_0x0025
            if (r1 != r2) goto L_0x001d
            r1 = r5
            java.lang.Object r4 = r7.L$0
            r1 = r4
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r8)
            r4 = r7
            goto L_0x006b
        L_0x001d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0025:
            r1 = r5
            java.lang.Object r4 = r7.L$0
            r1 = r4
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r8)
            r4 = r7
            goto L_0x005e
        L_0x0030:
            r1 = r5
            java.lang.Object r4 = r7.L$0
            r1 = r4
            kotlinx.coroutines.channels.ProducerScope r1 = (kotlinx.coroutines.channels.ProducerScope) r1
            kotlin.ResultKt.throwOnFailure(r8)
            goto L_0x004c
        L_0x003a:
            kotlin.ResultKt.throwOnFailure(r8)
            kotlinx.coroutines.channels.ProducerScope r1 = r7.f650p$
            long r5 = r7.$initialDelayMillis
            r7.L$0 = r1
            r7.label = r4
            java.lang.Object r4 = kotlinx.coroutines.DelayKt.delay(r5, r7)
            if (r4 != r0) goto L_0x004c
            return r0
        L_0x004c:
            r4 = r7
        L_0x004d:
            kotlinx.coroutines.channels.SendChannel r5 = r1.getChannel()
            kotlin.Unit r6 = kotlin.Unit.INSTANCE
            r4.L$0 = r1
            r4.label = r3
            java.lang.Object r5 = r5.send(r6, r4)
            if (r5 != r0) goto L_0x005e
            return r0
        L_0x005e:
            long r5 = r4.$delayMillis
            r4.L$0 = r1
            r4.label = r2
            java.lang.Object r5 = kotlinx.coroutines.DelayKt.delay(r5, r4)
            if (r5 != r0) goto L_0x006b
            return r0
        L_0x006b:
            goto L_0x004d
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.FlowKt__DelayKt$fixedPeriodTicker$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
