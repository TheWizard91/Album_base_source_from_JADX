package kotlinx.coroutines.flow;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.Boxing;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00009\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0002\n\u0002\b\u0003\n\u0002\b\u0003\n\u0002\b\u0004*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00028\u00000\u0001J\u001f\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00028\u00000\u0005H@ø\u0001\u0000¢\u0006\u0002\u0010\u0006\u0002\u0004\n\u0002\b\u0019¨\u0006\u0007¸\u0006\t"}, mo33671d2 = {"kotlinx/coroutines/flow/internal/SafeCollectorKt$unsafeFlow$1", "Lkotlinx/coroutines/flow/Flow;", "collect", "", "collector", "Lkotlinx/coroutines/flow/FlowCollector;", "(Lkotlinx/coroutines/flow/FlowCollector;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "kotlinx-coroutines-core", "kotlinx/coroutines/flow/FlowKt__TransformKt$unsafeTransform$$inlined$unsafeFlow$3", "kotlinx/coroutines/flow/FlowKt__TransformKt$filter$$inlined$unsafeTransform$2"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: SafeCollector.kt */
public final class FlowKt__TransformKt$filterIsInstance$$inlined$filter$1 implements Flow<Object> {
    final /* synthetic */ Flow $this_unsafeTransform$inlined;

    public FlowKt__TransformKt$filterIsInstance$$inlined$filter$1(Flow flow) {
        this.$this_unsafeTransform$inlined = flow;
    }

    public Object collect(FlowCollector collector, Continuation $completion) {
        final FlowCollector $receiver = collector;
        Continuation continuation = $completion;
        Flow $this$collect$iv = this.$this_unsafeTransform$inlined;
        Intrinsics.needClassReification();
        return $this$collect$iv.collect(new FlowCollector<Object>() {
            public Object emit(Object value, Continuation $completion) {
                Continuation continuation = $completion;
                FlowCollector $receiver = $receiver;
                Continuation continuation2 = $completion;
                Object value2 = value;
                Continuation continuation3 = $completion;
                Intrinsics.reifiedOperationMarker(3, "R");
                if (Boxing.boxBoolean(value2 instanceof Object).booleanValue()) {
                    return $receiver.emit(value2, $completion);
                }
                return Unit.INSTANCE;
            }

            public Object emit$$forInline(Object value, Continuation continuation) {
                InlineMarker.mark(4);
                new ContinuationImpl(this, continuation) {
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C18502 this$0;

                    {
                        this.this$0 = r1;
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit((Object) null, this);
                    }
                };
                InlineMarker.mark(5);
                Continuation continuation2 = continuation;
                FlowCollector $receiver = $receiver;
                Continuation continuation3 = continuation;
                Object value2 = value;
                Continuation continuation4 = continuation;
                Intrinsics.reifiedOperationMarker(3, "R");
                if (!(value2 instanceof Object)) {
                    return Unit.INSTANCE;
                }
                InlineMarker.mark(0);
                Object emit = $receiver.emit(value2, continuation);
                InlineMarker.mark(2);
                InlineMarker.mark(1);
                return emit;
            }
        }, $completion);
    }

    public Object collect$$forInline(FlowCollector collector, Continuation continuation) {
        InlineMarker.mark(4);
        new ContinuationImpl(this, continuation) {
            Object L$0;
            Object L$1;
            int label;
            /* synthetic */ Object result;
            final /* synthetic */ FlowKt__TransformKt$filterIsInstance$$inlined$filter$1 this$0;

            {
                this.this$0 = r1;
            }

            public final Object invokeSuspend(Object obj) {
                this.result = obj;
                this.label |= Integer.MIN_VALUE;
                return this.this$0.collect((FlowCollector) null, this);
            }
        };
        InlineMarker.mark(5);
        final FlowCollector $receiver = collector;
        Continuation continuation2 = continuation;
        Flow $this$collect$iv = this.$this_unsafeTransform$inlined;
        Intrinsics.needClassReification();
        InlineMarker.mark(0);
        $this$collect$iv.collect(new FlowCollector<Object>() {
            public Object emit(Object value, Continuation $completion) {
                Continuation continuation = $completion;
                FlowCollector $receiver = $receiver;
                Continuation continuation2 = $completion;
                Object value2 = value;
                Continuation continuation3 = $completion;
                Intrinsics.reifiedOperationMarker(3, "R");
                if (Boxing.boxBoolean(value2 instanceof Object).booleanValue()) {
                    return $receiver.emit(value2, $completion);
                }
                return Unit.INSTANCE;
            }

            public Object emit$$forInline(Object value, Continuation continuation) {
                InlineMarker.mark(4);
                new ContinuationImpl(this, continuation) {
                    int label;
                    /* synthetic */ Object result;
                    final /* synthetic */ C18502 this$0;

                    {
                        this.this$0 = r1;
                    }

                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return this.this$0.emit((Object) null, this);
                    }
                };
                InlineMarker.mark(5);
                Continuation continuation2 = continuation;
                FlowCollector $receiver = $receiver;
                Continuation continuation3 = continuation;
                Object value2 = value;
                Continuation continuation4 = continuation;
                Intrinsics.reifiedOperationMarker(3, "R");
                if (!(value2 instanceof Object)) {
                    return Unit.INSTANCE;
                }
                InlineMarker.mark(0);
                Object emit = $receiver.emit(value2, continuation);
                InlineMarker.mark(2);
                InlineMarker.mark(1);
                return emit;
            }
        }, continuation);
        InlineMarker.mark(2);
        InlineMarker.mark(1);
        return Unit.INSTANCE;
    }
}
