package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.DebugStringsKt;
import kotlinx.coroutines.channels.BroadcastChannel;
import kotlinx.coroutines.channels.BroadcastKt;
import kotlinx.coroutines.channels.ProduceKt;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\\\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0010\u0000\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0003\b'\u0018\u0000*\u0004\b\u0000\u0010\u00012\b\u0012\u0004\u0012\u0002H\u00010\u0002B\u0015\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\u0006\u0010\u0005\u001a\u00020\u0006¢\u0006\u0002\u0010\u0007J\b\u0010\u0013\u001a\u00020\u0014H\u0016J\u001e\u0010\u0015\u001a\b\u0012\u0004\u0012\u00028\u00000\u00162\u0006\u0010\u0017\u001a\u00020\u00182\u0006\u0010\u0019\u001a\u00020\u001aH\u0016J\u001f\u0010\u001b\u001a\u00020\f2\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00028\u00000\u001dH@ø\u0001\u0000¢\u0006\u0002\u0010\u001eJ\u001f\u0010\u001f\u001a\u00020\f2\f\u0010\u0017\u001a\b\u0012\u0004\u0012\u00028\u00000\nH¤@ø\u0001\u0000¢\u0006\u0002\u0010 J\u001e\u0010!\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H$J\u0016\u0010\"\u001a\b\u0012\u0004\u0012\u00028\u00000#2\u0006\u0010\u0017\u001a\u00020\u0018H\u0016J\b\u0010$\u001a\u00020\u0014H\u0016J \u0010%\u001a\b\u0012\u0004\u0012\u00028\u00000\u00002\b\b\u0002\u0010\u0003\u001a\u00020\u00042\b\b\u0002\u0010\u0005\u001a\u00020\u0006R\u0010\u0010\u0005\u001a\u00020\u00068\u0006X\u0004¢\u0006\u0002\n\u0000R9\u0010\b\u001a$\b\u0001\u0012\n\u0012\b\u0012\u0004\u0012\u00028\u00000\n\u0012\n\u0012\b\u0012\u0004\u0012\u00020\f0\u000b\u0012\u0006\u0012\u0004\u0018\u00010\r0\t8@X\u0004ø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u000e\u0010\u000fR\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\u00020\u00068BX\u0004¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0012\u0002\u0004\n\u0002\b\u0019¨\u0006&"}, mo33671d2 = {"Lkotlinx/coroutines/flow/internal/ChannelFlow;", "T", "Lkotlinx/coroutines/flow/Flow;", "context", "Lkotlin/coroutines/CoroutineContext;", "capacity", "", "(Lkotlin/coroutines/CoroutineContext;I)V", "collectToFun", "Lkotlin/Function2;", "Lkotlinx/coroutines/channels/ProducerScope;", "Lkotlin/coroutines/Continuation;", "", "", "getCollectToFun$kotlinx_coroutines_core", "()Lkotlin/jvm/functions/Function2;", "produceCapacity", "getProduceCapacity", "()I", "additionalToStringProps", "", "broadcastImpl", "Lkotlinx/coroutines/channels/BroadcastChannel;", "scope", "Lkotlinx/coroutines/CoroutineScope;", "start", "Lkotlinx/coroutines/CoroutineStart;", "collect", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "collectTo", "(Lkotlinx/coroutines/channels/ProducerScope;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "create", "produceImpl", "Lkotlinx/coroutines/channels/ReceiveChannel;", "toString", "update", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: ChannelFlow.kt */
public abstract class ChannelFlow<T> implements Flow<T> {
    public final int capacity;
    public final CoroutineContext context;

    public Object collect(FlowCollector<? super T> flowCollector, Continuation<? super Unit> continuation) {
        return CoroutineScopeKt.coroutineScope(new ChannelFlow$collect$2(this, flowCollector, (Continuation) null), continuation);
    }

    /* access modifiers changed from: protected */
    public abstract Object collectTo(ProducerScope<? super T> producerScope, Continuation<? super Unit> continuation);

    /* access modifiers changed from: protected */
    public abstract ChannelFlow<T> create(CoroutineContext coroutineContext, int i);

    public ChannelFlow(CoroutineContext context2, int capacity2) {
        Intrinsics.checkParameterIsNotNull(context2, "context");
        this.context = context2;
        this.capacity = capacity2;
    }

    public static /* synthetic */ ChannelFlow update$default(ChannelFlow channelFlow, CoroutineContext coroutineContext, int i, int i2, Object obj) {
        if (obj == null) {
            if ((i2 & 1) != 0) {
                coroutineContext = EmptyCoroutineContext.INSTANCE;
            }
            if ((i2 & 2) != 0) {
                i = -3;
            }
            return channelFlow.update(coroutineContext, i);
        }
        throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: update");
    }

    /* JADX WARNING: Removed duplicated region for block: B:35:0x006e A[RETURN] */
    /* JADX WARNING: Removed duplicated region for block: B:36:0x006f  */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public final kotlinx.coroutines.flow.internal.ChannelFlow<T> update(kotlin.coroutines.CoroutineContext r6, int r7) {
        /*
            r5 = this;
            java.lang.String r0 = "context"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r6, r0)
            kotlin.coroutines.CoroutineContext r0 = r5.context
            kotlin.coroutines.CoroutineContext r0 = r6.plus(r0)
            int r1 = r5.capacity
            r2 = -3
            r3 = -1
            if (r1 != r2) goto L_0x0013
            goto L_0x0019
        L_0x0013:
            if (r7 != r2) goto L_0x0016
            goto L_0x0061
        L_0x0016:
            r2 = -2
            if (r1 != r2) goto L_0x001b
        L_0x0019:
            r1 = r7
            goto L_0x0061
        L_0x001b:
            if (r7 != r2) goto L_0x001e
            goto L_0x0061
        L_0x001e:
            if (r1 != r3) goto L_0x0022
            r1 = r3
            goto L_0x0061
        L_0x0022:
            if (r7 != r3) goto L_0x0026
            r1 = r3
            goto L_0x0061
        L_0x0026:
            boolean r1 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            r2 = 1
            r3 = 0
            if (r1 == 0) goto L_0x0041
            r1 = 0
            int r4 = r5.capacity
            if (r4 < 0) goto L_0x0035
            r1 = r2
            goto L_0x0036
        L_0x0035:
            r1 = r3
        L_0x0036:
            if (r1 == 0) goto L_0x0039
            goto L_0x0041
        L_0x0039:
            java.lang.AssertionError r1 = new java.lang.AssertionError
            r1.<init>()
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            throw r1
        L_0x0041:
            boolean r1 = kotlinx.coroutines.DebugKt.getASSERTIONS_ENABLED()
            if (r1 == 0) goto L_0x0057
            r1 = 0
            if (r7 < 0) goto L_0x004b
            goto L_0x004c
        L_0x004b:
            r2 = r3
        L_0x004c:
            if (r2 == 0) goto L_0x004f
            goto L_0x0057
        L_0x004f:
            java.lang.AssertionError r1 = new java.lang.AssertionError
            r1.<init>()
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            throw r1
        L_0x0057:
            int r1 = r5.capacity
            int r1 = r1 + r7
            if (r1 < 0) goto L_0x005d
            goto L_0x0061
        L_0x005d:
            r2 = 2147483647(0x7fffffff, float:NaN)
            r1 = r2
        L_0x0061:
            kotlin.coroutines.CoroutineContext r2 = r5.context
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r0, (java.lang.Object) r2)
            if (r2 == 0) goto L_0x006f
            int r2 = r5.capacity
            if (r1 != r2) goto L_0x006f
            return r5
        L_0x006f:
            kotlinx.coroutines.flow.internal.ChannelFlow r2 = r5.create(r0, r1)
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.flow.internal.ChannelFlow.update(kotlin.coroutines.CoroutineContext, int):kotlinx.coroutines.flow.internal.ChannelFlow");
    }

    public final Function2<ProducerScope<? super T>, Continuation<? super Unit>, Object> getCollectToFun$kotlinx_coroutines_core() {
        return new ChannelFlow$collectToFun$1(this, (Continuation) null);
    }

    private final int getProduceCapacity() {
        int i = this.capacity;
        if (i == -3) {
            return -2;
        }
        return i;
    }

    public BroadcastChannel<T> broadcastImpl(CoroutineScope scope, CoroutineStart start) {
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        Intrinsics.checkParameterIsNotNull(start, "start");
        return BroadcastKt.broadcast$default(scope, this.context, getProduceCapacity(), start, (Function1) null, getCollectToFun$kotlinx_coroutines_core(), 8, (Object) null);
    }

    public ReceiveChannel<T> produceImpl(CoroutineScope scope) {
        Intrinsics.checkParameterIsNotNull(scope, "scope");
        return ProduceKt.produce(scope, this.context, getProduceCapacity(), getCollectToFun$kotlinx_coroutines_core());
    }

    public String toString() {
        return DebugStringsKt.getClassSimpleName(this) + '[' + additionalToStringProps() + "context=" + this.context + ", capacity=" + this.capacity + ']';
    }

    public String additionalToStringProps() {
        return "";
    }
}
