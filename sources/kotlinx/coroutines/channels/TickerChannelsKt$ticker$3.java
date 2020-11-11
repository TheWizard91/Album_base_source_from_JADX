package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u0001*\b\u0012\u0004\u0012\u00020\u00010\u0002H@¢\u0006\u0004\b\u0003\u0010\u0004"}, mo33671d2 = {"<anonymous>", "", "Lkotlinx/coroutines/channels/ProducerScope;", "invoke", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;"}, mo33672k = 3, mo33673mv = {1, 1, 15})
@DebugMetadata(mo34304c = "kotlinx.coroutines.channels.TickerChannelsKt$ticker$3", mo34305f = "TickerChannels.kt", mo34306i = {0, 1}, mo34307l = {72, 73}, mo34308m = "invokeSuspend", mo34309n = {"$this$produce", "$this$produce"}, mo34310s = {"L$0", "L$0"})
/* compiled from: TickerChannels.kt */
final class TickerChannelsKt$ticker$3 extends SuspendLambda implements Function2<ProducerScope<? super Unit>, Continuation<? super Unit>, Object> {
    final /* synthetic */ long $delayMillis;
    final /* synthetic */ long $initialDelayMillis;
    final /* synthetic */ TickerMode $mode;
    Object L$0;
    int label;

    /* renamed from: p$ */
    private ProducerScope f645p$;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    TickerChannelsKt$ticker$3(TickerMode tickerMode, long j, long j2, Continuation continuation) {
        super(2, continuation);
        this.$mode = tickerMode;
        this.$delayMillis = j;
        this.$initialDelayMillis = j2;
    }

    public final Continuation<Unit> create(Object obj, Continuation<?> continuation) {
        Intrinsics.checkParameterIsNotNull(continuation, "completion");
        TickerChannelsKt$ticker$3 tickerChannelsKt$ticker$3 = new TickerChannelsKt$ticker$3(this.$mode, this.$delayMillis, this.$initialDelayMillis, continuation);
        ProducerScope producerScope = (ProducerScope) obj;
        tickerChannelsKt$ticker$3.f645p$ = (ProducerScope) obj;
        return tickerChannelsKt$ticker$3;
    }

    public final Object invoke(Object obj, Object obj2) {
        return ((TickerChannelsKt$ticker$3) create(obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v7, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r1v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r0v10, resolved type: kotlinx.coroutines.channels.ProducerScope} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final java.lang.Object invokeSuspend(java.lang.Object r12) {
        /*
            r11 = this;
            java.lang.Object r0 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r1 = r11.label
            r2 = 2
            r3 = 1
            if (r1 == 0) goto L_0x0029
            r0 = 0
            if (r1 == r3) goto L_0x0020
            if (r1 != r2) goto L_0x0018
            java.lang.Object r1 = r11.L$0
            r0 = r1
            kotlinx.coroutines.channels.ProducerScope r0 = (kotlinx.coroutines.channels.ProducerScope) r0
            kotlin.ResultKt.throwOnFailure(r12)
            goto L_0x0052
        L_0x0018:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0020:
            java.lang.Object r1 = r11.L$0
            r0 = r1
            kotlinx.coroutines.channels.ProducerScope r0 = (kotlinx.coroutines.channels.ProducerScope) r0
            kotlin.ResultKt.throwOnFailure(r12)
            goto L_0x006c
        L_0x0029:
            kotlin.ResultKt.throwOnFailure(r12)
            kotlinx.coroutines.channels.ProducerScope r1 = r11.f645p$
            kotlinx.coroutines.channels.TickerMode r4 = r11.$mode
            int[] r5 = kotlinx.coroutines.channels.TickerChannelsKt.WhenMappings.$EnumSwitchMapping$0
            int r4 = r4.ordinal()
            r4 = r5[r4]
            if (r4 == r3) goto L_0x0054
            if (r4 == r2) goto L_0x003d
            goto L_0x006d
        L_0x003d:
            long r5 = r11.$delayMillis
            long r7 = r11.$initialDelayMillis
            kotlinx.coroutines.channels.SendChannel r9 = r1.getChannel()
            r11.L$0 = r1
            r11.label = r2
            r10 = r11
            java.lang.Object r2 = kotlinx.coroutines.channels.TickerChannelsKt.fixedDelayTicker(r5, r7, r9, r10)
            if (r2 != r0) goto L_0x0051
            return r0
        L_0x0051:
            r0 = r1
        L_0x0052:
            r1 = r0
            goto L_0x006d
        L_0x0054:
            long r4 = r11.$delayMillis
            long r6 = r11.$initialDelayMillis
            kotlinx.coroutines.channels.SendChannel r8 = r1.getChannel()
            r11.L$0 = r1
            r11.label = r3
            r2 = r4
            r4 = r6
            r6 = r8
            r7 = r11
            java.lang.Object r2 = kotlinx.coroutines.channels.TickerChannelsKt.fixedPeriodTicker(r2, r4, r6, r7)
            if (r2 != r0) goto L_0x006b
            return r0
        L_0x006b:
            r0 = r1
        L_0x006c:
            r1 = r0
        L_0x006d:
            kotlin.Unit r0 = kotlin.Unit.INSTANCE
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt$ticker$3.invokeSuspend(java.lang.Object):java.lang.Object");
    }
}
