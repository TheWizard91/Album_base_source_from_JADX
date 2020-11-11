package kotlinx.coroutines.flow;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.Unit;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.functions.Function6;
import kotlin.jvm.functions.Function7;
import kotlin.ranges.IntRange;
import kotlin.ranges.LongRange;
import kotlin.sequences.Sequence;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.CoroutineStart;
import kotlinx.coroutines.Job;
import kotlinx.coroutines.channels.BroadcastChannel;
import kotlinx.coroutines.channels.ProducerScope;
import kotlinx.coroutines.channels.ReceiveChannel;
import kotlinx.coroutines.channels.SendChannel;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"kotlinx/coroutines/flow/FlowKt__BuildersKt", "kotlinx/coroutines/flow/FlowKt__ChannelsKt", "kotlinx/coroutines/flow/FlowKt__CollectKt", "kotlinx/coroutines/flow/FlowKt__CollectionKt", "kotlinx/coroutines/flow/FlowKt__ContextKt", "kotlinx/coroutines/flow/FlowKt__CountKt", "kotlinx/coroutines/flow/FlowKt__DelayKt", "kotlinx/coroutines/flow/FlowKt__DistinctKt", "kotlinx/coroutines/flow/FlowKt__EmittersKt", "kotlinx/coroutines/flow/FlowKt__ErrorsKt", "kotlinx/coroutines/flow/FlowKt__LimitKt", "kotlinx/coroutines/flow/FlowKt__MergeKt", "kotlinx/coroutines/flow/FlowKt__MigrationKt", "kotlinx/coroutines/flow/FlowKt__ReduceKt", "kotlinx/coroutines/flow/FlowKt__TransformKt", "kotlinx/coroutines/flow/FlowKt__ZipKt"}, mo33672k = 4, mo33673mv = {1, 1, 15})
public final class FlowKt {
    public static final String DEFAULT_CONCURRENCY_PROPERTY_NAME = "kotlinx.coroutines.flow.defaultConcurrency";

    public static /* synthetic */ void DEFAULT_CONCURRENCY_PROPERTY_NAME$annotations() {
    }

    public static final <T> Flow<T> asFlow(Iterable<? extends T> $this$asFlow) {
        return FlowKt__BuildersKt.asFlow($this$asFlow);
    }

    public static final <T> Flow<T> asFlow(Iterator<? extends T> $this$asFlow) {
        return FlowKt__BuildersKt.asFlow($this$asFlow);
    }

    public static final <T> Flow<T> asFlow(Function0<? extends T> $this$asFlow) {
        return FlowKt__BuildersKt.asFlow($this$asFlow);
    }

    public static final <T> Flow<T> asFlow(Function1<? super Continuation<? super T>, ? extends Object> $this$asFlow) {
        return FlowKt__BuildersKt.asFlow($this$asFlow);
    }

    public static final Flow<Integer> asFlow(IntRange $this$asFlow) {
        return FlowKt__BuildersKt.asFlow($this$asFlow);
    }

    public static final Flow<Long> asFlow(LongRange $this$asFlow) {
        return FlowKt__BuildersKt.asFlow($this$asFlow);
    }

    public static final <T> Flow<T> asFlow(Sequence<? extends T> $this$asFlow) {
        return FlowKt__BuildersKt.asFlow($this$asFlow);
    }

    public static final <T> Flow<T> asFlow(BroadcastChannel<T> $this$asFlow) {
        return FlowKt__ChannelsKt.asFlow($this$asFlow);
    }

    public static final Flow<Integer> asFlow(int[] $this$asFlow) {
        return FlowKt__BuildersKt.asFlow($this$asFlow);
    }

    public static final Flow<Long> asFlow(long[] $this$asFlow) {
        return FlowKt__BuildersKt.asFlow($this$asFlow);
    }

    public static final <T> Flow<T> asFlow(T[] $this$asFlow) {
        return FlowKt__BuildersKt.asFlow($this$asFlow);
    }

    public static final <T> BroadcastChannel<T> broadcastIn(Flow<? extends T> $this$broadcastIn, CoroutineScope scope, CoroutineStart start) {
        return FlowKt__ChannelsKt.broadcastIn($this$broadcastIn, scope, start);
    }

    public static final <T> Flow<T> buffer(Flow<? extends T> $this$buffer, int capacity) {
        return FlowKt__ContextKt.buffer($this$buffer, capacity);
    }

    public static final <T> Flow<T> callbackFlow(Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> block) {
        return FlowKt__BuildersKt.callbackFlow(block);
    }

    /* renamed from: catch  reason: not valid java name */
    public static final <T> Flow<T> m2308catch(Flow<? extends T> $this$catch, Function3<? super FlowCollector<? super T>, ? super Throwable, ? super Continuation<? super Unit>, ? extends Object> action) {
        return FlowKt__ErrorsKt.m2309catch($this$catch, action);
    }

    public static final <T> Object catchImpl(Flow<? extends T> $this$catchImpl, FlowCollector<? super T> collector, Continuation<? super Throwable> $completion) {
        return FlowKt__ErrorsKt.catchImpl($this$catchImpl, collector, $completion);
    }

    public static final <T> Flow<T> channelFlow(Function2<? super ProducerScope<? super T>, ? super Continuation<? super Unit>, ? extends Object> block) {
        return FlowKt__BuildersKt.channelFlow(block);
    }

    public static final Object collect(Flow<?> $this$collect, Continuation<? super Unit> $completion) {
        return FlowKt__CollectKt.collect($this$collect, $completion);
    }

    public static final <T> Object collect(Flow<? extends T> $this$collect, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> action, Continuation<? super Unit> $completion) {
        return FlowKt__CollectKt.collect($this$collect, action, $completion);
    }

    private static final Object collect$$forInline(Flow $this$collect, Function2 action, Continuation continuation) {
        return FlowKt__CollectKt.collect($this$collect, action, continuation);
    }

    public static final <T> Object collectIndexed(Flow<? extends T> $this$collectIndexed, Function3<? super Integer, ? super T, ? super Continuation<? super Unit>, ? extends Object> action, Continuation<? super Unit> $completion) {
        return FlowKt__CollectKt.collectIndexed($this$collectIndexed, action, $completion);
    }

    private static final Object collectIndexed$$forInline(Flow $this$collectIndexed, Function3 action, Continuation continuation) {
        return FlowKt__CollectKt.collectIndexed($this$collectIndexed, action, continuation);
    }

    public static final <T> Object collectLatest(Flow<? extends T> $this$collectLatest, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> action, Continuation<? super Unit> $completion) {
        return FlowKt__CollectKt.collectLatest($this$collectLatest, action, $completion);
    }

    public static final <T1, T2, R> Flow<R> combine(Flow<? extends T1> flow, Flow<? extends T2> flow2, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__ZipKt.combine(flow, flow2, transform);
    }

    public static final <T1, T2, T3, R> Flow<R> combine(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Function4<? super T1, ? super T2, ? super T3, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__ZipKt.combine(flow, flow2, flow3, transform);
    }

    public static final <T1, T2, T3, T4, R> Flow<R> combine(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Flow<? extends T4> flow4, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__ZipKt.combine(flow, flow2, flow3, flow4, transform);
    }

    public static final <T1, T2, T3, T4, T5, R> Flow<R> combine(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Flow<? extends T4> flow4, Flow<? extends T5> flow5, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__ZipKt.combine(flow, flow2, flow3, flow4, flow5, transform);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "this.combine(other, transform)", imports = {}))
    public static final <T1, T2, R> Flow<R> combineLatest(Flow<? extends T1> $this$combineLatest, Flow<? extends T2> other, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__MigrationKt.combineLatest($this$combineLatest, other, transform);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "combine(this, other, other2, transform)", imports = {}))
    public static final <T1, T2, T3, R> Flow<R> combineLatest(Flow<? extends T1> $this$combineLatest, Flow<? extends T2> other, Flow<? extends T3> other2, Function4<? super T1, ? super T2, ? super T3, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__MigrationKt.combineLatest($this$combineLatest, other, other2, transform);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "combine(this, other, other2, other3, transform)", imports = {}))
    public static final <T1, T2, T3, T4, R> Flow<R> combineLatest(Flow<? extends T1> $this$combineLatest, Flow<? extends T2> other, Flow<? extends T3> other2, Flow<? extends T4> other3, Function5<? super T1, ? super T2, ? super T3, ? super T4, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__MigrationKt.combineLatest($this$combineLatest, other, other2, other3, transform);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'combineLatest' is 'combine'", replaceWith = @ReplaceWith(expression = "combine(this, other, other2, other3, transform)", imports = {}))
    public static final <T1, T2, T3, T4, T5, R> Flow<R> combineLatest(Flow<? extends T1> $this$combineLatest, Flow<? extends T2> other, Flow<? extends T3> other2, Flow<? extends T4> other3, Flow<? extends T5> other4, Function6<? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__MigrationKt.combineLatest($this$combineLatest, other, other2, other3, other4, transform);
    }

    public static final <T1, T2, R> Flow<R> combineTransform(Flow<? extends T1> flow, Flow<? extends T2> flow2, Function4<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super Continuation<? super Unit>, ? extends Object> transform) {
        return FlowKt__ZipKt.combineTransform(flow, flow2, transform);
    }

    public static final <T1, T2, T3, R> Flow<R> combineTransform(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Function5<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super T3, ? super Continuation<? super Unit>, ? extends Object> transform) {
        return FlowKt__ZipKt.combineTransform(flow, flow2, flow3, transform);
    }

    public static final <T1, T2, T3, T4, R> Flow<R> combineTransform(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Flow<? extends T4> flow4, Function6<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super T3, ? super T4, ? super Continuation<? super Unit>, ? extends Object> transform) {
        return FlowKt__ZipKt.combineTransform(flow, flow2, flow3, flow4, transform);
    }

    public static final <T1, T2, T3, T4, T5, R> Flow<R> combineTransform(Flow<? extends T1> flow, Flow<? extends T2> flow2, Flow<? extends T3> flow3, Flow<? extends T4> flow4, Flow<? extends T5> flow5, Function7<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super T3, ? super T4, ? super T5, ? super Continuation<? super Unit>, ? extends Object> transform) {
        return FlowKt__ZipKt.combineTransform(flow, flow2, flow3, flow4, flow5, transform);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'compose' is 'let'", replaceWith = @ReplaceWith(expression = "let(transformer)", imports = {}))
    public static final <T, R> Flow<R> compose(Flow<? extends T> $this$compose, Function1<? super Flow<? extends T>, ? extends Flow<? extends R>> transformer) {
        return FlowKt__MigrationKt.compose($this$compose, transformer);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'concatMap' is 'flatMapConcat'", replaceWith = @ReplaceWith(expression = "flatMapConcat(mapper)", imports = {}))
    public static final <T, R> Flow<R> concatMap(Flow<? extends T> $this$concatMap, Function1<? super T, ? extends Flow<? extends R>> mapper) {
        return FlowKt__MigrationKt.concatMap($this$concatMap, mapper);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'concatWith' is 'onCompletion'. Use 'onCompletion { emit(value) }'", replaceWith = @ReplaceWith(expression = "onCompletion { emit(value) }", imports = {}))
    public static final <T> Flow<T> concatWith(Flow<? extends T> $this$concatWith, T value) {
        return FlowKt__MigrationKt.concatWith($this$concatWith, value);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'concatWith' is 'onCompletion'. Use 'onCompletion { emitAll(other) }'", replaceWith = @ReplaceWith(expression = "onCompletion { emitAll(other) }", imports = {}))
    public static final <T> Flow<T> concatWith(Flow<? extends T> $this$concatWith, Flow<? extends T> other) {
        return FlowKt__MigrationKt.concatWith($this$concatWith, other);
    }

    public static final <T> Flow<T> conflate(Flow<? extends T> $this$conflate) {
        return FlowKt__ContextKt.conflate($this$conflate);
    }

    public static final <T> Flow<T> consumeAsFlow(ReceiveChannel<? extends T> $this$consumeAsFlow) {
        return FlowKt__ChannelsKt.consumeAsFlow($this$consumeAsFlow);
    }

    public static final <T> Object count(Flow<? extends T> $this$count, Continuation<? super Integer> $completion) {
        return FlowKt__CountKt.count($this$count, $completion);
    }

    public static final <T> Object count(Flow<? extends T> $this$count, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> predicate, Continuation<? super Integer> $completion) {
        return FlowKt__CountKt.count($this$count, predicate, $completion);
    }

    public static final <T> Flow<T> debounce(Flow<? extends T> $this$debounce, long timeoutMillis) {
        return FlowKt__DelayKt.debounce($this$debounce, timeoutMillis);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use 'onEach { delay(timeMillis) }'", replaceWith = @ReplaceWith(expression = "onEach { delay(timeMillis) }", imports = {}))
    public static final <T> Flow<T> delayEach(Flow<? extends T> $this$delayEach, long timeMillis) {
        return FlowKt__MigrationKt.delayEach($this$delayEach, timeMillis);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use 'onStart { delay(timeMillis) }'", replaceWith = @ReplaceWith(expression = "onStart { delay(timeMillis) }", imports = {}))
    public static final <T> Flow<T> delayFlow(Flow<? extends T> $this$delayFlow, long timeMillis) {
        return FlowKt__MigrationKt.delayFlow($this$delayFlow, timeMillis);
    }

    public static final <T> Flow<T> distinctUntilChanged(Flow<? extends T> $this$distinctUntilChanged) {
        return FlowKt__DistinctKt.distinctUntilChanged($this$distinctUntilChanged);
    }

    public static final <T> Flow<T> distinctUntilChanged(Flow<? extends T> $this$distinctUntilChanged, Function2<? super T, ? super T, Boolean> areEquivalent) {
        return FlowKt__DistinctKt.distinctUntilChanged($this$distinctUntilChanged, areEquivalent);
    }

    public static final <T, K> Flow<T> distinctUntilChangedBy(Flow<? extends T> $this$distinctUntilChangedBy, Function1<? super T, ? extends K> keySelector) {
        return FlowKt__DistinctKt.distinctUntilChangedBy($this$distinctUntilChangedBy, keySelector);
    }

    public static final <T> Flow<T> drop(Flow<? extends T> $this$drop, int count) {
        return FlowKt__LimitKt.drop($this$drop, count);
    }

    public static final <T> Flow<T> dropWhile(Flow<? extends T> $this$dropWhile, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return FlowKt__LimitKt.dropWhile($this$dropWhile, predicate);
    }

    public static final <T> Object emitAll(FlowCollector<? super T> $this$emitAll, ReceiveChannel<? extends T> channel, Continuation<? super Unit> $completion) {
        return FlowKt__ChannelsKt.emitAll($this$emitAll, channel, $completion);
    }

    public static final <T> Object emitAll(FlowCollector<? super T> $this$emitAll, Flow<? extends T> flow, Continuation<? super Unit> $completion) {
        return FlowKt__CollectKt.emitAll($this$emitAll, flow, $completion);
    }

    private static final Object emitAll$$forInline(FlowCollector $this$emitAll, Flow flow, Continuation continuation) {
        return FlowKt__CollectKt.emitAll($this$emitAll, flow, continuation);
    }

    public static final <T> Flow<T> emptyFlow() {
        return FlowKt__BuildersKt.emptyFlow();
    }

    public static final <T> Flow<T> filter(Flow<? extends T> $this$filter, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return FlowKt__TransformKt.filter($this$filter, predicate);
    }

    public static final <T> Flow<T> filterNot(Flow<? extends T> $this$filterNot, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return FlowKt__TransformKt.filterNot($this$filterNot, predicate);
    }

    public static final <T> Flow<T> filterNotNull(Flow<? extends T> $this$filterNotNull) {
        return FlowKt__TransformKt.filterNotNull($this$filterNotNull);
    }

    public static final <T> Object first(Flow<? extends T> $this$first, Continuation<? super T> $completion) {
        return FlowKt__ReduceKt.first($this$first, $completion);
    }

    public static final <T> Object first(Flow<? extends T> $this$first, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> predicate, Continuation<? super T> $completion) {
        return FlowKt__ReduceKt.first($this$first, predicate, $completion);
    }

    public static final ReceiveChannel<Unit> fixedPeriodTicker(CoroutineScope $this$fixedPeriodTicker, long delayMillis, long initialDelayMillis) {
        return FlowKt__DelayKt.fixedPeriodTicker($this$fixedPeriodTicker, delayMillis, initialDelayMillis);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue is named flatMapConcat", replaceWith = @ReplaceWith(expression = "flatMapConcat(mapper)", imports = {}))
    public static final <T, R> Flow<R> flatMap(Flow<? extends T> $this$flatMap, Function2<? super T, ? super Continuation<? super Flow<? extends R>>, ? extends Object> mapper) {
        return FlowKt__MigrationKt.flatMap($this$flatMap, mapper);
    }

    public static final <T, R> Flow<R> flatMapConcat(Flow<? extends T> $this$flatMapConcat, Function2<? super T, ? super Continuation<? super Flow<? extends R>>, ? extends Object> transform) {
        return FlowKt__MergeKt.flatMapConcat($this$flatMapConcat, transform);
    }

    public static final <T, R> Flow<R> flatMapLatest(Flow<? extends T> $this$flatMapLatest, Function2<? super T, ? super Continuation<? super Flow<? extends R>>, ? extends Object> transform) {
        return FlowKt__MergeKt.flatMapLatest($this$flatMapLatest, transform);
    }

    public static final <T, R> Flow<R> flatMapMerge(Flow<? extends T> $this$flatMapMerge, int concurrency, Function2<? super T, ? super Continuation<? super Flow<? extends R>>, ? extends Object> transform) {
        return FlowKt__MergeKt.flatMapMerge($this$flatMapMerge, concurrency, transform);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'flatten' is 'flattenConcat'", replaceWith = @ReplaceWith(expression = "flattenConcat()", imports = {}))
    public static final <T> Flow<T> flatten(Flow<? extends Flow<? extends T>> $this$flatten) {
        return FlowKt__MigrationKt.flatten($this$flatten);
    }

    public static final <T> Flow<T> flattenConcat(Flow<? extends Flow<? extends T>> $this$flattenConcat) {
        return FlowKt__MergeKt.flattenConcat($this$flattenConcat);
    }

    public static final <T> Flow<T> flattenMerge(Flow<? extends Flow<? extends T>> $this$flattenMerge, int concurrency) {
        return FlowKt__MergeKt.flattenMerge($this$flattenMerge, concurrency);
    }

    public static final <T> Flow<T> flow(Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> block) {
        return FlowKt__BuildersKt.flow(block);
    }

    public static final <T1, T2, R> Flow<R> flowCombine(Flow<? extends T1> $this$combine, Flow<? extends T2> flow, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__ZipKt.flowCombine($this$combine, flow, transform);
    }

    public static final <T1, T2, R> Flow<R> flowCombineTransform(Flow<? extends T1> $this$combineTransform, Flow<? extends T2> flow, Function4<? super FlowCollector<? super R>, ? super T1, ? super T2, ? super Continuation<? super Unit>, ? extends Object> transform) {
        return FlowKt__ZipKt.flowCombineTransform($this$combineTransform, flow, transform);
    }

    public static final <T> Flow<T> flowOf(T value) {
        return FlowKt__BuildersKt.flowOf(value);
    }

    public static final <T> Flow<T> flowOf(T... elements) {
        return FlowKt__BuildersKt.flowOf(elements);
    }

    public static final <T> Flow<T> flowOn(Flow<? extends T> $this$flowOn, CoroutineContext context) {
        return FlowKt__ContextKt.flowOn($this$flowOn, context);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Use channelFlow with awaitClose { } instead of flowViaChannel and invokeOnClose { }.")
    public static final <T> Flow<T> flowViaChannel(int bufferSize, Function2<? super CoroutineScope, ? super SendChannel<? super T>, Unit> block) {
        return FlowKt__BuildersKt.flowViaChannel(bufferSize, block);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "flowWith is deprecated without replacement, please refer to its KDoc for an explanation")
    public static final <T, R> Flow<R> flowWith(Flow<? extends T> $this$flowWith, CoroutineContext flowContext, int bufferSize, Function1<? super Flow<? extends T>, ? extends Flow<? extends R>> builder) {
        return FlowKt__ContextKt.flowWith($this$flowWith, flowContext, bufferSize, builder);
    }

    public static final <T, R> Object fold(Flow<? extends T> $this$fold, R initial, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> operation, Continuation<? super R> $completion) {
        return FlowKt__ReduceKt.fold($this$fold, initial, operation, $completion);
    }

    private static final Object fold$$forInline(Flow $this$fold, Object initial, Function3 operation, Continuation continuation) {
        return FlowKt__ReduceKt.fold($this$fold, initial, operation, continuation);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'forEach' is 'collect'", replaceWith = @ReplaceWith(expression = "collect(block)", imports = {}))
    public static final <T> void forEach(Flow<? extends T> $this$forEach, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> action) {
        FlowKt__MigrationKt.forEach($this$forEach, action);
    }

    public static final int getDEFAULT_CONCURRENCY() {
        return FlowKt__MergeKt.getDEFAULT_CONCURRENCY();
    }

    public static final <T> Job launchIn(Flow<? extends T> $this$launchIn, CoroutineScope scope) {
        return FlowKt__CollectKt.launchIn($this$launchIn, scope);
    }

    public static final <T, R> Flow<R> map(Flow<? extends T> $this$map, Function2<? super T, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__TransformKt.map($this$map, transform);
    }

    public static final <T, R> Flow<R> mapLatest(Flow<? extends T> $this$mapLatest, Function2<? super T, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__MergeKt.mapLatest($this$mapLatest, transform);
    }

    public static final <T, R> Flow<R> mapNotNull(Flow<? extends T> $this$mapNotNull, Function2<? super T, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__TransformKt.mapNotNull($this$mapNotNull, transform);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'merge' is 'flattenConcat'", replaceWith = @ReplaceWith(expression = "flattenConcat()", imports = {}))
    public static final <T> Flow<T> merge(Flow<? extends Flow<? extends T>> $this$merge) {
        return FlowKt__MigrationKt.merge($this$merge);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Collect flow in the desired context instead")
    public static final <T> Flow<T> observeOn(Flow<? extends T> $this$observeOn, CoroutineContext context) {
        return FlowKt__MigrationKt.observeOn($this$observeOn, context);
    }

    public static final <T> Flow<T> onCompletion(Flow<? extends T> $this$onCompletion, Function3<? super FlowCollector<? super T>, ? super Throwable, ? super Continuation<? super Unit>, ? extends Object> action) {
        return FlowKt__EmittersKt.onCompletion($this$onCompletion, action);
    }

    public static final <T> Flow<T> onEach(Flow<? extends T> $this$onEach, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> action) {
        return FlowKt__TransformKt.onEach($this$onEach, action);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use catch { e -> if (predicate(e)) emitAll(fallback) else throw e }", replaceWith = @ReplaceWith(expression = "catch { e -> if (predicate(e)) emitAll(fallback) else throw e }", imports = {}))
    public static final <T> Flow<T> onErrorCollect(Flow<? extends T> $this$onErrorCollect, Flow<? extends T> fallback, Function1<? super Throwable, Boolean> predicate) {
        return FlowKt__ErrorsKt.onErrorCollect($this$onErrorCollect, fallback, predicate);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emitAll(fallback) }'", replaceWith = @ReplaceWith(expression = "catch { emitAll(fallback) }", imports = {}))
    public static final <T> Flow<T> onErrorResume(Flow<? extends T> $this$onErrorResume, Flow<? extends T> fallback) {
        return FlowKt__MigrationKt.onErrorResume($this$onErrorResume, fallback);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emitAll(fallback) }'", replaceWith = @ReplaceWith(expression = "catch { emitAll(fallback) }", imports = {}))
    public static final <T> Flow<T> onErrorResumeNext(Flow<? extends T> $this$onErrorResumeNext, Flow<? extends T> fallback) {
        return FlowKt__MigrationKt.onErrorResumeNext($this$onErrorResumeNext, fallback);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { emit(fallback) }'", replaceWith = @ReplaceWith(expression = "catch { emit(fallback) }", imports = {}))
    public static final <T> Flow<T> onErrorReturn(Flow<? extends T> $this$onErrorReturn, T fallback) {
        return FlowKt__MigrationKt.onErrorReturn($this$onErrorReturn, fallback);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'onErrorXxx' is 'catch'. Use 'catch { e -> if (predicate(e)) emit(fallback) else throw e }'", replaceWith = @ReplaceWith(expression = "catch { e -> if (predicate(e)) emit(fallback) else throw e }", imports = {}))
    public static final <T> Flow<T> onErrorReturn(Flow<? extends T> $this$onErrorReturn, T fallback, Function1<? super Throwable, Boolean> predicate) {
        return FlowKt__MigrationKt.onErrorReturn($this$onErrorReturn, fallback, predicate);
    }

    public static final <T> Flow<T> onStart(Flow<? extends T> $this$onStart, Function2<? super FlowCollector<? super T>, ? super Continuation<? super Unit>, ? extends Object> action) {
        return FlowKt__EmittersKt.onStart($this$onStart, action);
    }

    public static final <T> ReceiveChannel<T> produceIn(Flow<? extends T> $this$produceIn, CoroutineScope scope) {
        return FlowKt__ChannelsKt.produceIn($this$produceIn, scope);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Collect flow in the desired context instead")
    public static final <T> Flow<T> publishOn(Flow<? extends T> $this$publishOn, CoroutineContext context) {
        return FlowKt__MigrationKt.publishOn($this$publishOn, context);
    }

    public static final <S, T extends S> Object reduce(Flow<? extends T> $this$reduce, Function3<? super S, ? super T, ? super Continuation<? super S>, ? extends Object> operation, Continuation<? super S> $completion) {
        return FlowKt__ReduceKt.reduce($this$reduce, operation, $completion);
    }

    public static final <T> Flow<T> retry(Flow<? extends T> $this$retry, long retries, Function2<? super Throwable, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return FlowKt__ErrorsKt.retry($this$retry, retries, predicate);
    }

    public static final <T> Flow<T> retryWhen(Flow<? extends T> $this$retryWhen, Function4<? super FlowCollector<? super T>, ? super Throwable, ? super Long, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return FlowKt__ErrorsKt.retryWhen($this$retryWhen, predicate);
    }

    public static final <T> Flow<T> sample(Flow<? extends T> $this$sample, long periodMillis) {
        return FlowKt__DelayKt.sample($this$sample, periodMillis);
    }

    public static final <T, R> Flow<R> scan(Flow<? extends T> $this$scan, R initial, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> operation) {
        return FlowKt__TransformKt.scan($this$scan, initial, operation);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow has less verbose 'scan' shortcut", replaceWith = @ReplaceWith(expression = "scan(initial, operation)", imports = {}))
    public static final <T, R> Flow<R> scanFold(Flow<? extends T> $this$scanFold, R initial, Function3<? super R, ? super T, ? super Continuation<? super R>, ? extends Object> operation) {
        return FlowKt__MigrationKt.scanFold($this$scanFold, initial, operation);
    }

    public static final <T> Flow<T> scanReduce(Flow<? extends T> $this$scanReduce, Function3<? super T, ? super T, ? super Continuation<? super T>, ? extends Object> operation) {
        return FlowKt__TransformKt.scanReduce($this$scanReduce, operation);
    }

    public static final <T> Object single(Flow<? extends T> $this$single, Continuation<? super T> $completion) {
        return FlowKt__ReduceKt.single($this$single, $completion);
    }

    public static final <T> Object singleOrNull(Flow<? extends T> $this$singleOrNull, Continuation<? super T> $completion) {
        return FlowKt__ReduceKt.singleOrNull($this$singleOrNull, $completion);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'skip' is 'drop'", replaceWith = @ReplaceWith(expression = "drop(count)", imports = {}))
    public static final <T> Flow<T> skip(Flow<? extends T> $this$skip, int count) {
        return FlowKt__MigrationKt.skip($this$skip, count);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'startWith' is 'onStart'. Use 'onStart { emit(value) }'", replaceWith = @ReplaceWith(expression = "onStart { emit(value) }", imports = {}))
    public static final <T> Flow<T> startWith(Flow<? extends T> $this$startWith, T value) {
        return FlowKt__MigrationKt.startWith($this$startWith, value);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogue of 'startWith' is 'onStart'. Use 'onStart { emitAll(other) }'", replaceWith = @ReplaceWith(expression = "onStart { emitAll(other) }", imports = {}))
    public static final <T> Flow<T> startWith(Flow<? extends T> $this$startWith, Flow<? extends T> other) {
        return FlowKt__MigrationKt.startWith($this$startWith, other);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use launchIn with onEach, onCompletion and catch operators instead")
    public static final <T> void subscribe(Flow<? extends T> $this$subscribe) {
        FlowKt__MigrationKt.subscribe($this$subscribe);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use launchIn with onEach, onCompletion and catch operators instead")
    public static final <T> void subscribe(Flow<? extends T> $this$subscribe, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> onEach) {
        FlowKt__MigrationKt.subscribe($this$subscribe, onEach);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use launchIn with onEach, onCompletion and catch operators instead")
    public static final <T> void subscribe(Flow<? extends T> $this$subscribe, Function2<? super T, ? super Continuation<? super Unit>, ? extends Object> onEach, Function2<? super Throwable, ? super Continuation<? super Unit>, ? extends Object> onError) {
        FlowKt__MigrationKt.subscribe($this$subscribe, onEach, onError);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Use flowOn instead")
    public static final <T> Flow<T> subscribeOn(Flow<? extends T> $this$subscribeOn, CoroutineContext context) {
        return FlowKt__MigrationKt.subscribeOn($this$subscribeOn, context);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "Flow analogues of 'switchMap' are 'transformLatest', 'flatMapLatest' and 'mapLatest'", replaceWith = @ReplaceWith(expression = "this.flatMapLatest(transform)", imports = {}))
    public static final <T, R> Flow<R> switchMap(Flow<? extends T> $this$switchMap, Function2<? super T, ? super Continuation<? super Flow<? extends R>>, ? extends Object> transform) {
        return FlowKt__MigrationKt.switchMap($this$switchMap, transform);
    }

    public static final <T> Flow<T> take(Flow<? extends T> $this$take, int count) {
        return FlowKt__LimitKt.take($this$take, count);
    }

    public static final <T> Flow<T> takeWhile(Flow<? extends T> $this$takeWhile, Function2<? super T, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return FlowKt__LimitKt.takeWhile($this$takeWhile, predicate);
    }

    public static final <T, C extends Collection<? super T>> Object toCollection(Flow<? extends T> $this$toCollection, C destination, Continuation<? super C> $completion) {
        return FlowKt__CollectionKt.toCollection($this$toCollection, destination, $completion);
    }

    public static final <T> Object toList(Flow<? extends T> $this$toList, List<T> destination, Continuation<? super List<? extends T>> $completion) {
        return FlowKt__CollectionKt.toList($this$toList, destination, $completion);
    }

    public static final <T> Object toSet(Flow<? extends T> $this$toSet, Set<T> destination, Continuation<? super Set<? extends T>> $completion) {
        return FlowKt__CollectionKt.toSet($this$toSet, destination, $completion);
    }

    public static final <T, R> Flow<R> transform(Flow<? extends T> $this$transform, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Unit>, ? extends Object> transform) {
        return FlowKt__EmittersKt.transform($this$transform, transform);
    }

    public static final <T, R> Flow<R> transformLatest(Flow<? extends T> $this$transformLatest, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Unit>, ? extends Object> transform) {
        return FlowKt__MergeKt.transformLatest($this$transformLatest, transform);
    }

    public static final <T, R> Flow<R> unsafeTransform(Flow<? extends T> $this$unsafeTransform, Function3<? super FlowCollector<? super R>, ? super T, ? super Continuation<? super Unit>, ? extends Object> transform) {
        return FlowKt__EmittersKt.unsafeTransform($this$unsafeTransform, transform);
    }

    @Deprecated(level = DeprecationLevel.ERROR, message = "withContext in flow body is deprecated, use flowOn instead")
    public static final <T, R> void withContext(FlowCollector<? super T> $this$withContext, CoroutineContext context, Function1<? super Continuation<? super R>, ? extends Object> block) {
        FlowKt__MigrationKt.withContext($this$withContext, context, block);
    }

    public static final <T> Flow<IndexedValue<T>> withIndex(Flow<? extends T> $this$withIndex) {
        return FlowKt__TransformKt.withIndex($this$withIndex);
    }

    public static final <T1, T2, R> Flow<R> zip(Flow<? extends T1> $this$zip, Flow<? extends T2> other, Function3<? super T1, ? super T2, ? super Continuation<? super R>, ? extends Object> transform) {
        return FlowKt__ZipKt.zip($this$zip, other, transform);
    }
}
