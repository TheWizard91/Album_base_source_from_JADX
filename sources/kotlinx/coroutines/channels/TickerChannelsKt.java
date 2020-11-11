package kotlinx.coroutines.channels;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.GlobalScope;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a/\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H@ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a/\u0010\b\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00010\u0006H@ø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a4\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\u0006\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\u0002\u0004\n\u0002\b\u0019¨\u0006\u000f"}, mo33671d2 = {"fixedDelayTicker", "", "delayMillis", "", "initialDelayMillis", "channel", "Lkotlinx/coroutines/channels/SendChannel;", "(JJLkotlinx/coroutines/channels/SendChannel;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "fixedPeriodTicker", "ticker", "Lkotlinx/coroutines/channels/ReceiveChannel;", "context", "Lkotlin/coroutines/CoroutineContext;", "mode", "Lkotlinx/coroutines/channels/TickerMode;", "kotlinx-coroutines-core"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: TickerChannels.kt */
public final class TickerChannelsKt {

    @Metadata(mo33669bv = {1, 0, 3}, mo33672k = 3, mo33673mv = {1, 1, 15})
    public final /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[TickerMode.values().length];
            $EnumSwitchMapping$0 = iArr;
            iArr[TickerMode.FIXED_PERIOD.ordinal()] = 1;
            iArr[TickerMode.FIXED_DELAY.ordinal()] = 2;
        }
    }

    public static /* synthetic */ ReceiveChannel ticker$default(long j, long j2, CoroutineContext coroutineContext, TickerMode tickerMode, int i, Object obj) {
        if ((i & 2) != 0) {
            j2 = j;
        }
        if ((i & 4) != 0) {
            coroutineContext = EmptyCoroutineContext.INSTANCE;
        }
        if ((i & 8) != 0) {
            tickerMode = TickerMode.FIXED_PERIOD;
        }
        return ticker(j, j2, coroutineContext, tickerMode);
    }

    public static final ReceiveChannel<Unit> ticker(long delayMillis, long initialDelayMillis, CoroutineContext context, TickerMode mode) {
        long j = delayMillis;
        long j2 = initialDelayMillis;
        CoroutineContext coroutineContext = context;
        Intrinsics.checkParameterIsNotNull(coroutineContext, "context");
        Intrinsics.checkParameterIsNotNull(mode, "mode");
        boolean z = true;
        if (j >= 0) {
            if (j2 < 0) {
                z = false;
            }
            if (z) {
                return ProduceKt.produce(GlobalScope.INSTANCE, Dispatchers.getUnconfined().plus(coroutineContext), 0, new TickerChannelsKt$ticker$3(mode, delayMillis, initialDelayMillis, (Continuation) null));
            }
            throw new IllegalArgumentException(("Expected non-negative initial delay, but has " + j2 + " ms").toString());
        }
        throw new IllegalArgumentException(("Expected non-negative delay, but has " + j + " ms").toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:34:0x011a, code lost:
        r13 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r13.nanoTime());
     */
    /* JADX WARNING: Removed duplicated region for block: B:18:0x00bc  */
    /* JADX WARNING: Removed duplicated region for block: B:30:0x010d A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:31:0x010e  */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x0124  */
    /* JADX WARNING: Removed duplicated region for block: B:37:0x0129  */
    /* JADX WARNING: Removed duplicated region for block: B:42:0x0147  */
    /* JADX WARNING: Removed duplicated region for block: B:47:0x0194  */
    /* JADX WARNING: Removed duplicated region for block: B:49:0x01bb A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:50:0x01bc  */
    /* JADX WARNING: Removed duplicated region for block: B:8:0x002d  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static final /* synthetic */ java.lang.Object fixedPeriodTicker(long r26, long r28, kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit> r30, kotlin.coroutines.Continuation<? super kotlin.Unit> r31) {
        /*
            r0 = r28
            r2 = r31
            boolean r3 = r2 instanceof kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1
            if (r3 == 0) goto L_0x0018
            r3 = r2
            kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1 r3 = (kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1) r3
            int r4 = r3.label
            r5 = -2147483648(0xffffffff80000000, float:-0.0)
            r4 = r4 & r5
            if (r4 == 0) goto L_0x0018
            int r4 = r3.label
            int r4 = r4 - r5
            r3.label = r4
            goto L_0x001d
        L_0x0018:
            kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1 r3 = new kotlinx.coroutines.channels.TickerChannelsKt$fixedPeriodTicker$1
            r3.<init>(r2)
        L_0x001d:
            java.lang.Object r4 = r3.result
            java.lang.Object r5 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r6 = r3.label
            r7 = 4
            r8 = 3
            r9 = 2
            r10 = 1
            r11 = 0
            if (r6 == 0) goto L_0x00bc
            if (r6 == r10) goto L_0x00aa
            if (r6 == r9) goto L_0x0090
            if (r6 == r8) goto L_0x0067
            if (r6 != r7) goto L_0x005f
            r13 = r11
            r15 = r11
            r17 = r11
            r19 = r11
            long r7 = r3.J$5
            r21 = r7
            long r6 = r3.J$4
            long r9 = r3.J$3
            long r13 = r3.J$2
            java.lang.Object r8 = r3.L$0
            kotlinx.coroutines.channels.SendChannel r8 = (kotlinx.coroutines.channels.SendChannel) r8
            long r0 = r3.J$1
            long r11 = r3.J$0
            kotlin.ResultKt.throwOnFailure(r4)
            r17 = r9
            r19 = r13
            r9 = r21
            r15 = 3
            r12 = r11
            r11 = r8
            r7 = r6
            r6 = r5
            r5 = r4
            r4 = 4
            goto L_0x01cb
        L_0x005f:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.String r4 = "call to 'resume' before 'invoke' with coroutine"
            r3.<init>(r4)
            throw r3
        L_0x0067:
            r6 = 0
            r8 = r6
            r10 = r6
            r12 = r6
            r19 = r6
            r21 = r6
            long r6 = r3.J$6
            long r10 = r3.J$5
            long r12 = r3.J$4
            r21 = r6
            long r6 = r3.J$3
            long r8 = r3.J$2
            java.lang.Object r14 = r3.L$0
            kotlinx.coroutines.channels.SendChannel r14 = (kotlinx.coroutines.channels.SendChannel) r14
            long r0 = r3.J$1
            r19 = r0
            long r0 = r3.J$0
            kotlin.ResultKt.throwOnFailure(r4)
            r17 = r6
            r6 = r21
            r15 = 3
            goto L_0x0185
        L_0x0090:
            r6 = 0
            r8 = r6
            r10 = r6
            long r10 = r3.J$3
            long r8 = r3.J$2
            java.lang.Object r12 = r3.L$0
            kotlinx.coroutines.channels.SendChannel r12 = (kotlinx.coroutines.channels.SendChannel) r12
            long r0 = r3.J$1
            long r13 = r3.J$0
            kotlin.ResultKt.throwOnFailure(r4)
            r6 = r8
            r9 = r10
            r8 = r12
            r11 = r13
            r14 = 2
            goto L_0x0114
        L_0x00aa:
            r6 = r11
            r8 = r6
            long r6 = r3.J$2
            java.lang.Object r8 = r3.L$0
            kotlinx.coroutines.channels.SendChannel r8 = (kotlinx.coroutines.channels.SendChannel) r8
            long r0 = r3.J$1
            long r9 = r3.J$0
            kotlin.ResultKt.throwOnFailure(r4)
            r11 = r8
            r8 = r9
            goto L_0x00f2
        L_0x00bc:
            kotlin.ResultKt.throwOnFailure(r4)
            kotlinx.coroutines.TimeSource r6 = kotlinx.coroutines.TimeSourceKt.getTimeSource()
            if (r6 == 0) goto L_0x00d4
            long r6 = r6.nanoTime()
            java.lang.Long r6 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r6)
            if (r6 == 0) goto L_0x00d4
            long r6 = r6.longValue()
            goto L_0x00d8
        L_0x00d4:
            long r6 = java.lang.System.nanoTime()
        L_0x00d8:
            long r8 = kotlinx.coroutines.EventLoop_commonKt.delayToNanos(r28)
            long r6 = r6 + r8
            r8 = r26
            r3.J$0 = r8
            r3.J$1 = r0
            r11 = r30
            r3.L$0 = r11
            r3.J$2 = r6
            r3.label = r10
            java.lang.Object r10 = kotlinx.coroutines.DelayKt.delay(r0, r3)
            if (r10 != r5) goto L_0x00f2
            return r5
        L_0x00f2:
            long r12 = kotlinx.coroutines.EventLoop_commonKt.delayToNanos(r8)
        L_0x00f6:
            long r6 = r6 + r12
            kotlin.Unit r10 = kotlin.Unit.INSTANCE
            r3.J$0 = r8
            r3.J$1 = r0
            r3.L$0 = r11
            r3.J$2 = r6
            r3.J$3 = r12
            r14 = 2
            r3.label = r14
            java.lang.Object r10 = r11.send(r10, r3)
            if (r10 != r5) goto L_0x010e
            return r5
        L_0x010e:
            r23 = r8
            r8 = r11
            r9 = r12
            r11 = r23
        L_0x0114:
            kotlinx.coroutines.TimeSource r13 = kotlinx.coroutines.TimeSourceKt.getTimeSource()
            if (r13 == 0) goto L_0x0129
            long r19 = r13.nanoTime()
            java.lang.Long r13 = kotlin.coroutines.jvm.internal.Boxing.boxLong(r19)
            if (r13 == 0) goto L_0x0129
            long r19 = r13.longValue()
            goto L_0x012d
        L_0x0129:
            long r19 = java.lang.System.nanoTime()
        L_0x012d:
            r26 = r19
            r14 = r26
            r26 = r4
            r19 = r5
            long r4 = r6 - r14
            r28 = r14
            r13 = 0
            long r4 = kotlin.ranges.RangesKt.coerceAtLeast((long) r4, (long) r13)
            int r15 = (r4 > r13 ? 1 : (r4 == r13 ? 0 : -1))
            if (r15 != 0) goto L_0x0194
            int r15 = (r9 > r13 ? 1 : (r9 == r13 ? 0 : -1))
            if (r15 == 0) goto L_0x0194
            long r17 = r28 - r6
            long r17 = r17 % r9
            long r13 = r9 - r17
            long r6 = r28 + r13
            r30 = r4
            long r4 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r13)
            r3.J$0 = r11
            r3.J$1 = r0
            r3.L$0 = r8
            r3.J$2 = r6
            r3.J$3 = r9
            r17 = r9
            r9 = r28
            r3.J$4 = r9
            r9 = r30
            r3.J$5 = r9
            r3.J$6 = r13
            r15 = 3
            r3.label = r15
            java.lang.Object r4 = kotlinx.coroutines.DelayKt.delay(r4, r3)
            r5 = r19
            if (r4 != r5) goto L_0x0177
            return r5
        L_0x0177:
            r4 = r26
            r19 = r0
            r0 = r11
            r10 = r9
            r23 = r13
            r14 = r8
            r12 = r28
            r8 = r6
            r6 = r23
        L_0x0185:
            r6 = r5
            r11 = r14
            r12 = r17
            r5 = r4
            r4 = 4
            r23 = r0
            r0 = r19
            r19 = r8
            r8 = r23
            goto L_0x01ce
        L_0x0194:
            r17 = r9
            r15 = 3
            r9 = r4
            r5 = r19
            long r13 = kotlinx.coroutines.EventLoop_commonKt.delayNanosToMillis(r9)
            r3.J$0 = r11
            r3.J$1 = r0
            r3.L$0 = r8
            r3.J$2 = r6
            r19 = r0
            r0 = r17
            r3.J$3 = r0
            r0 = r28
            r3.J$4 = r0
            r3.J$5 = r9
            r4 = 4
            r3.label = r4
            java.lang.Object r13 = kotlinx.coroutines.DelayKt.delay(r13, r3)
            if (r13 != r5) goto L_0x01bc
            return r5
        L_0x01bc:
            r12 = r11
            r11 = r8
            r23 = r5
            r5 = r26
            r24 = r6
            r6 = r23
            r7 = r0
            r0 = r19
            r19 = r24
        L_0x01cb:
            r8 = r12
            r12 = r17
        L_0x01ce:
            r4 = r5
            r5 = r6
            r6 = r19
            goto L_0x00f6
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt.fixedPeriodTicker(long, long, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v3, resolved type: kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit>} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r3v6, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r11v5, resolved type: kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit>} */
    /*  JADX ERROR: JadxOverflowException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxOverflowException: Regions count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    /* JADX WARNING: Multi-variable type inference failed */
    /* JADX WARNING: Removed duplicated region for block: B:20:0x0072 A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:23:0x0081 A[RETURN] */
    static final /* synthetic */ java.lang.Object fixedDelayTicker(long r7, long r9, kotlinx.coroutines.channels.SendChannel<? super kotlin.Unit> r11, kotlin.coroutines.Continuation<? super kotlin.Unit> r12) {
        /*
            boolean r0 = r12 instanceof kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1
            if (r0 == 0) goto L_0x0014
            r0 = r12
            kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1 r0 = (kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r1 = r1 & r2
            if (r1 == 0) goto L_0x0014
            int r1 = r0.label
            int r1 = r1 - r2
            r0.label = r1
            goto L_0x0019
        L_0x0014:
            kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1 r0 = new kotlinx.coroutines.channels.TickerChannelsKt$fixedDelayTicker$1
            r0.<init>(r12)
        L_0x0019:
            java.lang.Object r1 = r0.result
            java.lang.Object r2 = kotlin.coroutines.intrinsics.IntrinsicsKt.getCOROUTINE_SUSPENDED()
            int r3 = r0.label
            r4 = 3
            r5 = 2
            r6 = 1
            if (r3 == 0) goto L_0x004f
            if (r3 == r6) goto L_0x0042
            if (r3 == r5) goto L_0x0035
            if (r3 != r4) goto L_0x002d
            goto L_0x0042
        L_0x002d:
            java.lang.IllegalStateException r0 = new java.lang.IllegalStateException
            java.lang.String r1 = "call to 'resume' before 'invoke' with coroutine"
            r0.<init>(r1)
            throw r0
        L_0x0035:
            java.lang.Object r3 = r0.L$0
            r11 = r3
            kotlinx.coroutines.channels.SendChannel r11 = (kotlinx.coroutines.channels.SendChannel) r11
            long r9 = r0.J$1
            long r7 = r0.J$0
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0073
        L_0x0042:
            java.lang.Object r3 = r0.L$0
            r11 = r3
            kotlinx.coroutines.channels.SendChannel r11 = (kotlinx.coroutines.channels.SendChannel) r11
            long r9 = r0.J$1
            long r7 = r0.J$0
            kotlin.ResultKt.throwOnFailure(r1)
            goto L_0x0061
        L_0x004f:
            kotlin.ResultKt.throwOnFailure(r1)
            r0.J$0 = r7
            r0.J$1 = r9
            r0.L$0 = r11
            r0.label = r6
            java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r9, r0)
            if (r3 != r2) goto L_0x0061
            return r2
        L_0x0061:
            kotlin.Unit r3 = kotlin.Unit.INSTANCE
            r0.J$0 = r7
            r0.J$1 = r9
            r0.L$0 = r11
            r0.label = r5
            java.lang.Object r3 = r11.send(r3, r0)
            if (r3 != r2) goto L_0x0073
            return r2
        L_0x0073:
            r0.J$0 = r7
            r0.J$1 = r9
            r0.L$0 = r11
            r0.label = r4
            java.lang.Object r3 = kotlinx.coroutines.DelayKt.delay(r7, r0)
            if (r3 != r2) goto L_0x0082
            return r2
        L_0x0082:
            goto L_0x0061
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.channels.TickerChannelsKt.fixedDelayTicker(long, long, kotlinx.coroutines.channels.SendChannel, kotlin.coroutines.Continuation):java.lang.Object");
    }
}
