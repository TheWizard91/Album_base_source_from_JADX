package kotlinx.coroutines.channels;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.collections.IndexedValue;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlinx.coroutines.selects.SelectClause1;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"kotlinx/coroutines/channels/ChannelsKt__ChannelsKt", "kotlinx/coroutines/channels/ChannelsKt__Channels_commonKt"}, mo33672k = 4, mo33673mv = {1, 1, 15})
public final class ChannelsKt {
    public static final String DEFAULT_CLOSE_MESSAGE = "Channel was closed";

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object all(ReceiveChannel<? extends E> $this$all, Function1<? super E, Boolean> predicate, Continuation<? super Boolean> $completion) {
        return ChannelsKt__Channels_commonKt.all($this$all, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object all$$forInline(ReceiveChannel $this$all, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.all($this$all, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object any(ReceiveChannel<? extends E> $this$any, Continuation<? super Boolean> $completion) {
        return ChannelsKt__Channels_commonKt.any($this$any, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object any(ReceiveChannel<? extends E> $this$any, Function1<? super E, Boolean> predicate, Continuation<? super Boolean> $completion) {
        return ChannelsKt__Channels_commonKt.any($this$any, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object any$$forInline(ReceiveChannel $this$any, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.any($this$any, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, K, V> Object associate(ReceiveChannel<? extends E> $this$associate, Function1<? super E, ? extends Pair<? extends K, ? extends V>> transform, Continuation<? super Map<K, ? extends V>> $completion) {
        return ChannelsKt__Channels_commonKt.associate($this$associate, transform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object associate$$forInline(ReceiveChannel $this$associate, Function1 transform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.associate($this$associate, transform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, K> Object associateBy(ReceiveChannel<? extends E> $this$associateBy, Function1<? super E, ? extends K> keySelector, Continuation<? super Map<K, ? extends E>> $completion) {
        return ChannelsKt__Channels_commonKt.associateBy($this$associateBy, keySelector, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, K, V> Object associateBy(ReceiveChannel<? extends E> $this$associateBy, Function1<? super E, ? extends K> keySelector, Function1<? super E, ? extends V> valueTransform, Continuation<? super Map<K, ? extends V>> $completion) {
        return ChannelsKt__Channels_commonKt.associateBy($this$associateBy, keySelector, valueTransform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object associateBy$$forInline(ReceiveChannel $this$associateBy, Function1 keySelector, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.associateBy($this$associateBy, keySelector, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object associateBy$$forInline(ReceiveChannel $this$associateBy, Function1 keySelector, Function1 valueTransform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.associateBy($this$associateBy, keySelector, valueTransform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, K, M extends Map<? super K, ? super E>> Object associateByTo(ReceiveChannel<? extends E> $this$associateByTo, M destination, Function1<? super E, ? extends K> keySelector, Continuation<? super M> $completion) {
        return ChannelsKt__Channels_commonKt.associateByTo($this$associateByTo, destination, keySelector, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, K, V, M extends Map<? super K, ? super V>> Object associateByTo(ReceiveChannel<? extends E> $this$associateByTo, M destination, Function1<? super E, ? extends K> keySelector, Function1<? super E, ? extends V> valueTransform, Continuation<? super M> $completion) {
        return ChannelsKt__Channels_commonKt.associateByTo($this$associateByTo, destination, keySelector, valueTransform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object associateByTo$$forInline(ReceiveChannel $this$associateByTo, Map destination, Function1 keySelector, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.associateByTo($this$associateByTo, destination, keySelector, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object associateByTo$$forInline(ReceiveChannel $this$associateByTo, Map destination, Function1 keySelector, Function1 valueTransform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.associateByTo($this$associateByTo, destination, keySelector, valueTransform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, K, V, M extends Map<? super K, ? super V>> Object associateTo(ReceiveChannel<? extends E> $this$associateTo, M destination, Function1<? super E, ? extends Pair<? extends K, ? extends V>> transform, Continuation<? super M> $completion) {
        return ChannelsKt__Channels_commonKt.associateTo($this$associateTo, destination, transform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object associateTo$$forInline(ReceiveChannel $this$associateTo, Map destination, Function1 transform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.associateTo($this$associateTo, destination, transform, continuation);
    }

    public static final void cancelConsumed(ReceiveChannel<?> $this$cancelConsumed, Throwable cause) {
        ChannelsKt__Channels_commonKt.cancelConsumed($this$cancelConsumed, cause);
    }

    public static final <E, R> R consume(BroadcastChannel<E> $this$consume, Function1<? super ReceiveChannel<? extends E>, ? extends R> block) {
        return ChannelsKt__Channels_commonKt.consume($this$consume, block);
    }

    public static final <E, R> R consume(ReceiveChannel<? extends E> $this$consume, Function1<? super ReceiveChannel<? extends E>, ? extends R> block) {
        return ChannelsKt__Channels_commonKt.consume($this$consume, block);
    }

    public static final <E> Object consumeEach(BroadcastChannel<E> $this$consumeEach, Function1<? super E, Unit> action, Continuation<? super Unit> $completion) {
        return ChannelsKt__Channels_commonKt.consumeEach($this$consumeEach, action, $completion);
    }

    public static final <E> Object consumeEach(ReceiveChannel<? extends E> $this$consumeEach, Function1<? super E, Unit> action, Continuation<? super Unit> $completion) {
        return ChannelsKt__Channels_commonKt.consumeEach($this$consumeEach, action, $completion);
    }

    private static final Object consumeEach$$forInline(BroadcastChannel $this$consumeEach, Function1 action, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.consumeEach($this$consumeEach, action, (Continuation<? super Unit>) continuation);
    }

    private static final Object consumeEach$$forInline(ReceiveChannel $this$consumeEach, Function1 action, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.consumeEach($this$consumeEach, action, (Continuation<? super Unit>) continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object consumeEachIndexed(ReceiveChannel<? extends E> $this$consumeEachIndexed, Function1<? super IndexedValue<? extends E>, Unit> action, Continuation<? super Unit> $completion) {
        return ChannelsKt__Channels_commonKt.consumeEachIndexed($this$consumeEachIndexed, action, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object consumeEachIndexed$$forInline(ReceiveChannel $this$consumeEachIndexed, Function1 action, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.consumeEachIndexed($this$consumeEachIndexed, action, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final Function1<Throwable, Unit> consumes(ReceiveChannel<?> $this$consumes) {
        return ChannelsKt__Channels_commonKt.consumes($this$consumes);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final Function1<Throwable, Unit> consumesAll(ReceiveChannel<?>... channels) {
        return ChannelsKt__Channels_commonKt.consumesAll(channels);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object count(ReceiveChannel<? extends E> $this$count, Continuation<? super Integer> $completion) {
        return ChannelsKt__Channels_commonKt.count($this$count, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object count(ReceiveChannel<? extends E> $this$count, Function1<? super E, Boolean> predicate, Continuation<? super Integer> $completion) {
        return ChannelsKt__Channels_commonKt.count($this$count, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object count$$forInline(ReceiveChannel $this$count, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.count($this$count, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> distinct(ReceiveChannel<? extends E> $this$distinct) {
        return ChannelsKt__Channels_commonKt.distinct($this$distinct);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, K> ReceiveChannel<E> distinctBy(ReceiveChannel<? extends E> $this$distinctBy, CoroutineContext context, Function2<? super E, ? super Continuation<? super K>, ? extends Object> selector) {
        return ChannelsKt__Channels_commonKt.distinctBy($this$distinctBy, context, selector);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> drop(ReceiveChannel<? extends E> $this$drop, int n, CoroutineContext context) {
        return ChannelsKt__Channels_commonKt.drop($this$drop, n, context);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> dropWhile(ReceiveChannel<? extends E> $this$dropWhile, CoroutineContext context, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return ChannelsKt__Channels_commonKt.dropWhile($this$dropWhile, context, predicate);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object elementAt(ReceiveChannel<? extends E> $this$elementAt, int index, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.elementAt($this$elementAt, index, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object elementAtOrElse(ReceiveChannel<? extends E> $this$elementAtOrElse, int index, Function1<? super Integer, ? extends E> defaultValue, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.elementAtOrElse($this$elementAtOrElse, index, defaultValue, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object elementAtOrElse$$forInline(ReceiveChannel $this$elementAtOrElse, int index, Function1 defaultValue, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.elementAtOrElse($this$elementAtOrElse, index, defaultValue, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object elementAtOrNull(ReceiveChannel<? extends E> $this$elementAtOrNull, int index, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.elementAtOrNull($this$elementAtOrNull, index, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> filter(ReceiveChannel<? extends E> $this$filter, CoroutineContext context, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return ChannelsKt__Channels_commonKt.filter($this$filter, context, predicate);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> filterIndexed(ReceiveChannel<? extends E> $this$filterIndexed, CoroutineContext context, Function3<? super Integer, ? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return ChannelsKt__Channels_commonKt.filterIndexed($this$filterIndexed, context, predicate);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, C extends Collection<? super E>> Object filterIndexedTo(ReceiveChannel<? extends E> $this$filterIndexedTo, C destination, Function2<? super Integer, ? super E, Boolean> predicate, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.filterIndexedTo($this$filterIndexedTo, destination, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, C extends SendChannel<? super E>> Object filterIndexedTo(ReceiveChannel<? extends E> $this$filterIndexedTo, C destination, Function2<? super Integer, ? super E, Boolean> predicate, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.filterIndexedTo($this$filterIndexedTo, destination, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object filterIndexedTo$$forInline(ReceiveChannel $this$filterIndexedTo, Collection destination, Function2 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.filterIndexedTo($this$filterIndexedTo, destination, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object filterIndexedTo$$forInline(ReceiveChannel $this$filterIndexedTo, SendChannel destination, Function2 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.filterIndexedTo($this$filterIndexedTo, destination, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> filterNot(ReceiveChannel<? extends E> $this$filterNot, CoroutineContext context, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return ChannelsKt__Channels_commonKt.filterNot($this$filterNot, context, predicate);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> filterNotNull(ReceiveChannel<? extends E> $this$filterNotNull) {
        return ChannelsKt__Channels_commonKt.filterNotNull($this$filterNotNull);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, C extends Collection<? super E>> Object filterNotNullTo(ReceiveChannel<? extends E> $this$filterNotNullTo, C destination, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.filterNotNullTo($this$filterNotNullTo, destination, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, C extends SendChannel<? super E>> Object filterNotNullTo(ReceiveChannel<? extends E> $this$filterNotNullTo, C destination, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.filterNotNullTo($this$filterNotNullTo, destination, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, C extends Collection<? super E>> Object filterNotTo(ReceiveChannel<? extends E> $this$filterNotTo, C destination, Function1<? super E, Boolean> predicate, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.filterNotTo($this$filterNotTo, destination, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, C extends SendChannel<? super E>> Object filterNotTo(ReceiveChannel<? extends E> $this$filterNotTo, C destination, Function1<? super E, Boolean> predicate, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.filterNotTo($this$filterNotTo, destination, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object filterNotTo$$forInline(ReceiveChannel $this$filterNotTo, Collection destination, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.filterNotTo($this$filterNotTo, destination, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object filterNotTo$$forInline(ReceiveChannel $this$filterNotTo, SendChannel destination, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.filterNotTo($this$filterNotTo, destination, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, C extends Collection<? super E>> Object filterTo(ReceiveChannel<? extends E> $this$filterTo, C destination, Function1<? super E, Boolean> predicate, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.filterTo($this$filterTo, destination, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, C extends SendChannel<? super E>> Object filterTo(ReceiveChannel<? extends E> $this$filterTo, C destination, Function1<? super E, Boolean> predicate, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.filterTo($this$filterTo, destination, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object filterTo$$forInline(ReceiveChannel $this$filterTo, Collection destination, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.filterTo($this$filterTo, destination, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object filterTo$$forInline(ReceiveChannel $this$filterTo, SendChannel destination, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.filterTo($this$filterTo, destination, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object find(ReceiveChannel<? extends E> $this$find, Function1<? super E, Boolean> predicate, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.find($this$find, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object find$$forInline(ReceiveChannel $this$find, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.find($this$find, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object findLast(ReceiveChannel<? extends E> $this$findLast, Function1<? super E, Boolean> predicate, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.findLast($this$findLast, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object findLast$$forInline(ReceiveChannel $this$findLast, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.findLast($this$findLast, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object first(ReceiveChannel<? extends E> $this$first, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.first($this$first, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object first(ReceiveChannel<? extends E> $this$first, Function1<? super E, Boolean> predicate, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.first($this$first, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object first$$forInline(ReceiveChannel $this$first, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.first($this$first, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object firstOrNull(ReceiveChannel<? extends E> $this$firstOrNull, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.firstOrNull($this$firstOrNull, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object firstOrNull(ReceiveChannel<? extends E> $this$firstOrNull, Function1<? super E, Boolean> predicate, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.firstOrNull($this$firstOrNull, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object firstOrNull$$forInline(ReceiveChannel $this$firstOrNull, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.firstOrNull($this$firstOrNull, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> ReceiveChannel<R> flatMap(ReceiveChannel<? extends E> $this$flatMap, CoroutineContext context, Function2<? super E, ? super Continuation<? super ReceiveChannel<? extends R>>, ? extends Object> transform) {
        return ChannelsKt__Channels_commonKt.flatMap($this$flatMap, context, transform);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> Object fold(ReceiveChannel<? extends E> $this$fold, R initial, Function2<? super R, ? super E, ? extends R> operation, Continuation<? super R> $completion) {
        return ChannelsKt__Channels_commonKt.fold($this$fold, initial, operation, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object fold$$forInline(ReceiveChannel $this$fold, Object initial, Function2 operation, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.fold($this$fold, initial, operation, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> Object foldIndexed(ReceiveChannel<? extends E> $this$foldIndexed, R initial, Function3<? super Integer, ? super R, ? super E, ? extends R> operation, Continuation<? super R> $completion) {
        return ChannelsKt__Channels_commonKt.foldIndexed($this$foldIndexed, initial, operation, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object foldIndexed$$forInline(ReceiveChannel $this$foldIndexed, Object initial, Function3 operation, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.foldIndexed($this$foldIndexed, initial, operation, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, K> Object groupBy(ReceiveChannel<? extends E> $this$groupBy, Function1<? super E, ? extends K> keySelector, Continuation<? super Map<K, ? extends List<? extends E>>> $completion) {
        return ChannelsKt__Channels_commonKt.groupBy($this$groupBy, keySelector, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, K, V> Object groupBy(ReceiveChannel<? extends E> $this$groupBy, Function1<? super E, ? extends K> keySelector, Function1<? super E, ? extends V> valueTransform, Continuation<? super Map<K, ? extends List<? extends V>>> $completion) {
        return ChannelsKt__Channels_commonKt.groupBy($this$groupBy, keySelector, valueTransform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object groupBy$$forInline(ReceiveChannel $this$groupBy, Function1 keySelector, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.groupBy($this$groupBy, keySelector, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object groupBy$$forInline(ReceiveChannel $this$groupBy, Function1 keySelector, Function1 valueTransform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.groupBy($this$groupBy, keySelector, valueTransform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, K, M extends Map<? super K, List<E>>> Object groupByTo(ReceiveChannel<? extends E> $this$groupByTo, M destination, Function1<? super E, ? extends K> keySelector, Continuation<? super M> $completion) {
        return ChannelsKt__Channels_commonKt.groupByTo($this$groupByTo, destination, keySelector, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, K, V, M extends Map<? super K, List<V>>> Object groupByTo(ReceiveChannel<? extends E> $this$groupByTo, M destination, Function1<? super E, ? extends K> keySelector, Function1<? super E, ? extends V> valueTransform, Continuation<? super M> $completion) {
        return ChannelsKt__Channels_commonKt.groupByTo($this$groupByTo, destination, keySelector, valueTransform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object groupByTo$$forInline(ReceiveChannel $this$groupByTo, Map destination, Function1 keySelector, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.groupByTo($this$groupByTo, destination, keySelector, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object groupByTo$$forInline(ReceiveChannel $this$groupByTo, Map destination, Function1 keySelector, Function1 valueTransform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.groupByTo($this$groupByTo, destination, keySelector, valueTransform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object indexOf(ReceiveChannel<? extends E> $this$indexOf, E element, Continuation<? super Integer> $completion) {
        return ChannelsKt__Channels_commonKt.indexOf($this$indexOf, element, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object indexOfFirst(ReceiveChannel<? extends E> $this$indexOfFirst, Function1<? super E, Boolean> predicate, Continuation<? super Integer> $completion) {
        return ChannelsKt__Channels_commonKt.indexOfFirst($this$indexOfFirst, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object indexOfFirst$$forInline(ReceiveChannel $this$indexOfFirst, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.indexOfFirst($this$indexOfFirst, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object indexOfLast(ReceiveChannel<? extends E> $this$indexOfLast, Function1<? super E, Boolean> predicate, Continuation<? super Integer> $completion) {
        return ChannelsKt__Channels_commonKt.indexOfLast($this$indexOfLast, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object indexOfLast$$forInline(ReceiveChannel $this$indexOfLast, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.indexOfLast($this$indexOfLast, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object last(ReceiveChannel<? extends E> $this$last, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.last($this$last, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object last(ReceiveChannel<? extends E> $this$last, Function1<? super E, Boolean> predicate, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.last($this$last, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object last$$forInline(ReceiveChannel $this$last, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.last($this$last, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object lastIndexOf(ReceiveChannel<? extends E> $this$lastIndexOf, E element, Continuation<? super Integer> $completion) {
        return ChannelsKt__Channels_commonKt.lastIndexOf($this$lastIndexOf, element, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object lastOrNull(ReceiveChannel<? extends E> $this$lastOrNull, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.lastOrNull($this$lastOrNull, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object lastOrNull(ReceiveChannel<? extends E> $this$lastOrNull, Function1<? super E, Boolean> predicate, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.lastOrNull($this$lastOrNull, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object lastOrNull$$forInline(ReceiveChannel $this$lastOrNull, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.lastOrNull($this$lastOrNull, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> ReceiveChannel<R> map(ReceiveChannel<? extends E> $this$map, CoroutineContext context, Function2<? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        return ChannelsKt__Channels_commonKt.map($this$map, context, transform);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> ReceiveChannel<R> mapIndexed(ReceiveChannel<? extends E> $this$mapIndexed, CoroutineContext context, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        return ChannelsKt__Channels_commonKt.mapIndexed($this$mapIndexed, context, transform);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> ReceiveChannel<R> mapIndexedNotNull(ReceiveChannel<? extends E> $this$mapIndexedNotNull, CoroutineContext context, Function3<? super Integer, ? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        return ChannelsKt__Channels_commonKt.mapIndexedNotNull($this$mapIndexedNotNull, context, transform);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R, C extends Collection<? super R>> Object mapIndexedNotNullTo(ReceiveChannel<? extends E> $this$mapIndexedNotNullTo, C destination, Function2<? super Integer, ? super E, ? extends R> transform, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.mapIndexedNotNullTo($this$mapIndexedNotNullTo, destination, transform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R, C extends SendChannel<? super R>> Object mapIndexedNotNullTo(ReceiveChannel<? extends E> $this$mapIndexedNotNullTo, C destination, Function2<? super Integer, ? super E, ? extends R> transform, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.mapIndexedNotNullTo($this$mapIndexedNotNullTo, destination, transform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapIndexedNotNullTo$$forInline(ReceiveChannel $this$mapIndexedNotNullTo, Collection destination, Function2 transform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.mapIndexedNotNullTo($this$mapIndexedNotNullTo, destination, transform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapIndexedNotNullTo$$forInline(ReceiveChannel $this$mapIndexedNotNullTo, SendChannel destination, Function2 transform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.mapIndexedNotNullTo($this$mapIndexedNotNullTo, destination, transform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R, C extends Collection<? super R>> Object mapIndexedTo(ReceiveChannel<? extends E> $this$mapIndexedTo, C destination, Function2<? super Integer, ? super E, ? extends R> transform, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.mapIndexedTo($this$mapIndexedTo, destination, transform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R, C extends SendChannel<? super R>> Object mapIndexedTo(ReceiveChannel<? extends E> $this$mapIndexedTo, C destination, Function2<? super Integer, ? super E, ? extends R> transform, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.mapIndexedTo($this$mapIndexedTo, destination, transform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapIndexedTo$$forInline(ReceiveChannel $this$mapIndexedTo, Collection destination, Function2 transform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.mapIndexedTo($this$mapIndexedTo, destination, transform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapIndexedTo$$forInline(ReceiveChannel $this$mapIndexedTo, SendChannel destination, Function2 transform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.mapIndexedTo($this$mapIndexedTo, destination, transform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> ReceiveChannel<R> mapNotNull(ReceiveChannel<? extends E> $this$mapNotNull, CoroutineContext context, Function2<? super E, ? super Continuation<? super R>, ? extends Object> transform) {
        return ChannelsKt__Channels_commonKt.mapNotNull($this$mapNotNull, context, transform);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R, C extends Collection<? super R>> Object mapNotNullTo(ReceiveChannel<? extends E> $this$mapNotNullTo, C destination, Function1<? super E, ? extends R> transform, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.mapNotNullTo($this$mapNotNullTo, destination, transform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R, C extends SendChannel<? super R>> Object mapNotNullTo(ReceiveChannel<? extends E> $this$mapNotNullTo, C destination, Function1<? super E, ? extends R> transform, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.mapNotNullTo($this$mapNotNullTo, destination, transform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapNotNullTo$$forInline(ReceiveChannel $this$mapNotNullTo, Collection destination, Function1 transform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.mapNotNullTo($this$mapNotNullTo, destination, transform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapNotNullTo$$forInline(ReceiveChannel $this$mapNotNullTo, SendChannel destination, Function1 transform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.mapNotNullTo($this$mapNotNullTo, destination, transform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R, C extends Collection<? super R>> Object mapTo(ReceiveChannel<? extends E> $this$mapTo, C destination, Function1<? super E, ? extends R> transform, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.mapTo($this$mapTo, destination, transform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R, C extends SendChannel<? super R>> Object mapTo(ReceiveChannel<? extends E> $this$mapTo, C destination, Function1<? super E, ? extends R> transform, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.mapTo($this$mapTo, destination, transform, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapTo$$forInline(ReceiveChannel $this$mapTo, Collection destination, Function1 transform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.mapTo($this$mapTo, destination, transform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object mapTo$$forInline(ReceiveChannel $this$mapTo, SendChannel destination, Function1 transform, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.mapTo($this$mapTo, destination, transform, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R extends Comparable<? super R>> Object maxBy(ReceiveChannel<? extends E> $this$maxBy, Function1<? super E, ? extends R> selector, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.maxBy($this$maxBy, selector, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object maxBy$$forInline(ReceiveChannel $this$maxBy, Function1 selector, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.maxBy($this$maxBy, selector, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object maxWith(ReceiveChannel<? extends E> $this$maxWith, Comparator<? super E> comparator, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.maxWith($this$maxWith, comparator, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R extends Comparable<? super R>> Object minBy(ReceiveChannel<? extends E> $this$minBy, Function1<? super E, ? extends R> selector, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.minBy($this$minBy, selector, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object minBy$$forInline(ReceiveChannel $this$minBy, Function1 selector, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.minBy($this$minBy, selector, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object minWith(ReceiveChannel<? extends E> $this$minWith, Comparator<? super E> comparator, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.minWith($this$minWith, comparator, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object none(ReceiveChannel<? extends E> $this$none, Continuation<? super Boolean> $completion) {
        return ChannelsKt__Channels_commonKt.none($this$none, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object none(ReceiveChannel<? extends E> $this$none, Function1<? super E, Boolean> predicate, Continuation<? super Boolean> $completion) {
        return ChannelsKt__Channels_commonKt.none($this$none, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object none$$forInline(ReceiveChannel $this$none, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.none($this$none, predicate, continuation);
    }

    public static final <E> SelectClause1<E> onReceiveOrNull(ReceiveChannel<? extends E> $this$onReceiveOrNull) {
        return ChannelsKt__Channels_commonKt.onReceiveOrNull($this$onReceiveOrNull);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object partition(ReceiveChannel<? extends E> $this$partition, Function1<? super E, Boolean> predicate, Continuation<? super Pair<? extends List<? extends E>, ? extends List<? extends E>>> $completion) {
        return ChannelsKt__Channels_commonKt.partition($this$partition, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object partition$$forInline(ReceiveChannel $this$partition, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.partition($this$partition, predicate, continuation);
    }

    public static final <E> Object receiveOrNull(ReceiveChannel<? extends E> $this$receiveOrNull, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.receiveOrNull($this$receiveOrNull, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <S, E extends S> Object reduce(ReceiveChannel<? extends E> $this$reduce, Function2<? super S, ? super E, ? extends S> operation, Continuation<? super S> $completion) {
        return ChannelsKt__Channels_commonKt.reduce($this$reduce, operation, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object reduce$$forInline(ReceiveChannel $this$reduce, Function2 operation, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.reduce($this$reduce, operation, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <S, E extends S> Object reduceIndexed(ReceiveChannel<? extends E> $this$reduceIndexed, Function3<? super Integer, ? super S, ? super E, ? extends S> operation, Continuation<? super S> $completion) {
        return ChannelsKt__Channels_commonKt.reduceIndexed($this$reduceIndexed, operation, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object reduceIndexed$$forInline(ReceiveChannel $this$reduceIndexed, Function3 operation, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.reduceIndexed($this$reduceIndexed, operation, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> requireNoNulls(ReceiveChannel<? extends E> $this$requireNoNulls) {
        return ChannelsKt__Channels_commonKt.requireNoNulls($this$requireNoNulls);
    }

    public static final <E> void sendBlocking(SendChannel<? super E> $this$sendBlocking, E element) {
        ChannelsKt__ChannelsKt.sendBlocking($this$sendBlocking, element);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object single(ReceiveChannel<? extends E> $this$single, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.single($this$single, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object single(ReceiveChannel<? extends E> $this$single, Function1<? super E, Boolean> predicate, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.single($this$single, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object single$$forInline(ReceiveChannel $this$single, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.single($this$single, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object singleOrNull(ReceiveChannel<? extends E> $this$singleOrNull, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.singleOrNull($this$singleOrNull, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object singleOrNull(ReceiveChannel<? extends E> $this$singleOrNull, Function1<? super E, Boolean> predicate, Continuation<? super E> $completion) {
        return ChannelsKt__Channels_commonKt.singleOrNull($this$singleOrNull, predicate, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object singleOrNull$$forInline(ReceiveChannel $this$singleOrNull, Function1 predicate, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.singleOrNull($this$singleOrNull, predicate, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object sumBy(ReceiveChannel<? extends E> $this$sumBy, Function1<? super E, Integer> selector, Continuation<? super Integer> $completion) {
        return ChannelsKt__Channels_commonKt.sumBy($this$sumBy, selector, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object sumBy$$forInline(ReceiveChannel $this$sumBy, Function1 selector, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.sumBy($this$sumBy, selector, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object sumByDouble(ReceiveChannel<? extends E> $this$sumByDouble, Function1<? super E, Double> selector, Continuation<? super Double> $completion) {
        return ChannelsKt__Channels_commonKt.sumByDouble($this$sumByDouble, selector, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    private static final Object sumByDouble$$forInline(ReceiveChannel $this$sumByDouble, Function1 selector, Continuation continuation) {
        return ChannelsKt__Channels_commonKt.sumByDouble($this$sumByDouble, selector, continuation);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> take(ReceiveChannel<? extends E> $this$take, int n, CoroutineContext context) {
        return ChannelsKt__Channels_commonKt.take($this$take, n, context);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<E> takeWhile(ReceiveChannel<? extends E> $this$takeWhile, CoroutineContext context, Function2<? super E, ? super Continuation<? super Boolean>, ? extends Object> predicate) {
        return ChannelsKt__Channels_commonKt.takeWhile($this$takeWhile, context, predicate);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, C extends SendChannel<? super E>> Object toChannel(ReceiveChannel<? extends E> $this$toChannel, C destination, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.toChannel($this$toChannel, destination, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, C extends Collection<? super E>> Object toCollection(ReceiveChannel<? extends E> $this$toCollection, C destination, Continuation<? super C> $completion) {
        return ChannelsKt__Channels_commonKt.toCollection($this$toCollection, destination, $completion);
    }

    public static final <E> Object toList(ReceiveChannel<? extends E> $this$toList, Continuation<? super List<? extends E>> $completion) {
        return ChannelsKt__Channels_commonKt.toList($this$toList, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <K, V, M extends Map<? super K, ? super V>> Object toMap(ReceiveChannel<? extends Pair<? extends K, ? extends V>> $this$toMap, M destination, Continuation<? super M> $completion) {
        return ChannelsKt__Channels_commonKt.toMap($this$toMap, destination, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <K, V> Object toMap(ReceiveChannel<? extends Pair<? extends K, ? extends V>> $this$toMap, Continuation<? super Map<K, ? extends V>> $completion) {
        return ChannelsKt__Channels_commonKt.toMap($this$toMap, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object toMutableList(ReceiveChannel<? extends E> $this$toMutableList, Continuation<? super List<E>> $completion) {
        return ChannelsKt__Channels_commonKt.toMutableList($this$toMutableList, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object toMutableSet(ReceiveChannel<? extends E> $this$toMutableSet, Continuation<? super Set<E>> $completion) {
        return ChannelsKt__Channels_commonKt.toMutableSet($this$toMutableSet, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> Object toSet(ReceiveChannel<? extends E> $this$toSet, Continuation<? super Set<? extends E>> $completion) {
        return ChannelsKt__Channels_commonKt.toSet($this$toSet, $completion);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E> ReceiveChannel<IndexedValue<E>> withIndex(ReceiveChannel<? extends E> $this$withIndex, CoroutineContext context) {
        return ChannelsKt__Channels_commonKt.withIndex($this$withIndex, context);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R> ReceiveChannel<Pair<E, R>> zip(ReceiveChannel<? extends E> $this$zip, ReceiveChannel<? extends R> other) {
        return ChannelsKt__Channels_commonKt.zip($this$zip, other);
    }

    @Deprecated(level = DeprecationLevel.WARNING, message = "Channel operators are deprecated in favour of Flow and will be removed in 1.4")
    public static final <E, R, V> ReceiveChannel<V> zip(ReceiveChannel<? extends E> $this$zip, ReceiveChannel<? extends R> other, CoroutineContext context, Function2<? super E, ? super R, ? extends V> transform) {
        return ChannelsKt__Channels_commonKt.zip($this$zip, other, context, transform);
    }
}
