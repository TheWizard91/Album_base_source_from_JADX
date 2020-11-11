package kotlinx.coroutines.flow;

import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.flow.internal.ChannelFlow;
import kotlinx.coroutines.flow.internal.ChannelFlowOperatorImpl;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000*\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a\u0015\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¢\u0006\u0002\b\u0004\u001a(\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\b\b\u0002\u0010\b\u001a\u00020\tH\u0007\u001a\u001e\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u0006H\u0007\u001a&\u0010\u000b\u001a\b\u0012\u0004\u0012\u0002H\u00070\u0006\"\u0004\b\u0000\u0010\u0007*\b\u0012\u0004\u0012\u0002H\u00070\u00062\u0006\u0010\u0002\u001a\u00020\u0003H\u0007\u001a[\u0010\f\u001a\b\u0012\u0004\u0012\u0002H\r0\u0006\"\u0004\b\u0000\u0010\u0007\"\u0004\b\u0001\u0010\r*\b\u0012\u0004\u0012\u0002H\u00070\u00062\u0006\u0010\u000e\u001a\u00020\u00032\b\b\u0002\u0010\u000f\u001a\u00020\t2#\u0010\u0010\u001a\u001f\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00070\u0006\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\r0\u00060\u0011¢\u0006\u0002\b\u0012H\u0007¨\u0006\u0013"}, mo33671d2 = {"checkFlowContext", "", "context", "Lkotlin/coroutines/CoroutineContext;", "checkFlowContext$FlowKt__ContextKt", "buffer", "Lkotlinx/coroutines/flow/Flow;", "T", "capacity", "", "conflate", "flowOn", "flowWith", "R", "flowContext", "bufferSize", "builder", "Lkotlin/Function1;", "Lkotlin/ExtensionFunctionType;", "kotlinx-coroutines-core"}, mo33672k = 5, mo33673mv = {1, 1, 15}, mo33676xs = "kotlinx/coroutines/flow/FlowKt")
/* compiled from: Context.kt */
final /* synthetic */ class FlowKt__ContextKt {
    public static /* synthetic */ Flow buffer$default(Flow flow, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = -2;
        }
        return FlowKt.buffer(flow, i);
    }

    public static final <T> Flow<T> buffer(Flow<? extends T> $this$buffer, int capacity) {
        Intrinsics.checkParameterIsNotNull($this$buffer, "$this$buffer");
        if (!(capacity >= 0 || capacity == -2 || capacity == -1)) {
            throw new IllegalArgumentException(("Buffer size should be non-negative, BUFFERED, or CONFLATED, but was " + capacity).toString());
        } else if ($this$buffer instanceof ChannelFlow) {
            return ChannelFlow.update$default((ChannelFlow) $this$buffer, (CoroutineContext) null, capacity, 1, (Object) null);
        } else {
            return new ChannelFlowOperatorImpl<>($this$buffer, (CoroutineContext) null, capacity, 2, (DefaultConstructorMarker) null);
        }
    }

    public static final <T> Flow<T> conflate(Flow<? extends T> $this$conflate) {
        Intrinsics.checkParameterIsNotNull($this$conflate, "$this$conflate");
        return FlowKt.buffer($this$conflate, -1);
    }

    public static final <T> Flow<T> flowOn(Flow<? extends T> $this$flowOn, CoroutineContext context) {
        Intrinsics.checkParameterIsNotNull($this$flowOn, "$this$flowOn");
        Intrinsics.checkParameterIsNotNull(context, "context");
        checkFlowContext$FlowKt__ContextKt(context);
        if (Intrinsics.areEqual((Object) context, (Object) EmptyCoroutineContext.INSTANCE)) {
            return $this$flowOn;
        }
        if ($this$flowOn instanceof ChannelFlow) {
            return ChannelFlow.update$default((ChannelFlow) $this$flowOn, context, 0, 2, (Object) null);
        }
        return new ChannelFlowOperatorImpl<>($this$flowOn, context, 0, 4, (DefaultConstructorMarker) null);
    }

    public static /* synthetic */ Flow flowWith$default(Flow flow, CoroutineContext coroutineContext, int i, Function1 function1, int i2, Object obj) {
        if ((i2 & 2) != 0) {
            i = -2;
        }
        return FlowKt.flowWith(flow, coroutineContext, i, function1);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "flowWith is deprecated without replacement, please refer to its KDoc for an explanation")
    public static final <T, R> Flow<R> flowWith(Flow<? extends T> $this$flowWith, CoroutineContext flowContext, int bufferSize, Function1<? super Flow<? extends T>, ? extends Flow<? extends R>> builder) {
        Intrinsics.checkParameterIsNotNull($this$flowWith, "$this$flowWith");
        Intrinsics.checkParameterIsNotNull(flowContext, "flowContext");
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        checkFlowContext$FlowKt__ContextKt(flowContext);
        return new FlowKt__ContextKt$flowWith$$inlined$unsafeFlow$1($this$flowWith, bufferSize, builder, flowContext);
    }

    private static final void checkFlowContext$FlowKt__ContextKt(CoroutineContext context) {
        if (!(context.get(Job.Key) == null)) {
            throw new IllegalArgumentException(("Flow context cannot contain job in it. Had " + context).toString());
        }
    }
}
