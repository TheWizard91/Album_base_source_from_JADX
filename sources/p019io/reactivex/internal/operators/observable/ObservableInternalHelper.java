package p019io.reactivex.internal.operators.observable;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import p019io.reactivex.Emitter;
import p019io.reactivex.Observable;
import p019io.reactivex.ObservableSource;
import p019io.reactivex.Observer;
import p019io.reactivex.Scheduler;
import p019io.reactivex.functions.Action;
import p019io.reactivex.functions.BiConsumer;
import p019io.reactivex.functions.BiFunction;
import p019io.reactivex.functions.Consumer;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.functions.Functions;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.observables.ConnectableObservable;

/* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper */
public final class ObservableInternalHelper {
    private ObservableInternalHelper() {
        throw new IllegalStateException("No instances!");
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$SimpleGenerator */
    static final class SimpleGenerator<T, S> implements BiFunction<S, Emitter<T>, S> {
        final Consumer<Emitter<T>> consumer;

        SimpleGenerator(Consumer<Emitter<T>> consumer2) {
            this.consumer = consumer2;
        }

        public S apply(S t1, Emitter<T> t2) throws Exception {
            this.consumer.accept(t2);
            return t1;
        }
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleGenerator(Consumer<Emitter<T>> consumer) {
        return new SimpleGenerator(consumer);
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$SimpleBiGenerator */
    static final class SimpleBiGenerator<T, S> implements BiFunction<S, Emitter<T>, S> {
        final BiConsumer<S, Emitter<T>> consumer;

        SimpleBiGenerator(BiConsumer<S, Emitter<T>> consumer2) {
            this.consumer = consumer2;
        }

        public S apply(S t1, Emitter<T> t2) throws Exception {
            this.consumer.accept(t1, t2);
            return t1;
        }
    }

    public static <T, S> BiFunction<S, Emitter<T>, S> simpleBiGenerator(BiConsumer<S, Emitter<T>> consumer) {
        return new SimpleBiGenerator(consumer);
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$ItemDelayFunction */
    static final class ItemDelayFunction<T, U> implements Function<T, ObservableSource<T>> {
        final Function<? super T, ? extends ObservableSource<U>> itemDelay;

        ItemDelayFunction(Function<? super T, ? extends ObservableSource<U>> itemDelay2) {
            this.itemDelay = itemDelay2;
        }

        public ObservableSource<T> apply(T v) throws Exception {
            return new ObservableTake((ObservableSource) ObjectHelper.requireNonNull(this.itemDelay.apply(v), "The itemDelay returned a null ObservableSource"), 1).map(Functions.justFunction(v)).defaultIfEmpty(v);
        }
    }

    public static <T, U> Function<T, ObservableSource<T>> itemDelay(Function<? super T, ? extends ObservableSource<U>> itemDelay) {
        return new ItemDelayFunction(itemDelay);
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$ObserverOnNext */
    static final class ObserverOnNext<T> implements Consumer<T> {
        final Observer<T> observer;

        ObserverOnNext(Observer<T> observer2) {
            this.observer = observer2;
        }

        public void accept(T v) throws Exception {
            this.observer.onNext(v);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$ObserverOnError */
    static final class ObserverOnError<T> implements Consumer<Throwable> {
        final Observer<T> observer;

        ObserverOnError(Observer<T> observer2) {
            this.observer = observer2;
        }

        public void accept(Throwable v) throws Exception {
            this.observer.onError(v);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$ObserverOnComplete */
    static final class ObserverOnComplete<T> implements Action {
        final Observer<T> observer;

        ObserverOnComplete(Observer<T> observer2) {
            this.observer = observer2;
        }

        public void run() throws Exception {
            this.observer.onComplete();
        }
    }

    public static <T> Consumer<T> observerOnNext(Observer<T> observer) {
        return new ObserverOnNext(observer);
    }

    public static <T> Consumer<Throwable> observerOnError(Observer<T> observer) {
        return new ObserverOnError(observer);
    }

    public static <T> Action observerOnComplete(Observer<T> observer) {
        return new ObserverOnComplete(observer);
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$FlatMapWithCombinerInner */
    static final class FlatMapWithCombinerInner<U, R, T> implements Function<U, R> {
        private final BiFunction<? super T, ? super U, ? extends R> combiner;

        /* renamed from: t */
        private final T f455t;

        FlatMapWithCombinerInner(BiFunction<? super T, ? super U, ? extends R> combiner2, T t) {
            this.combiner = combiner2;
            this.f455t = t;
        }

        public R apply(U w) throws Exception {
            return this.combiner.apply(this.f455t, w);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$FlatMapWithCombinerOuter */
    static final class FlatMapWithCombinerOuter<T, R, U> implements Function<T, ObservableSource<R>> {
        private final BiFunction<? super T, ? super U, ? extends R> combiner;
        private final Function<? super T, ? extends ObservableSource<? extends U>> mapper;

        FlatMapWithCombinerOuter(BiFunction<? super T, ? super U, ? extends R> combiner2, Function<? super T, ? extends ObservableSource<? extends U>> mapper2) {
            this.combiner = combiner2;
            this.mapper = mapper2;
        }

        public ObservableSource<R> apply(T t) throws Exception {
            return new ObservableMap((ObservableSource) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null ObservableSource"), new FlatMapWithCombinerInner(this.combiner, t));
        }
    }

    public static <T, U, R> Function<T, ObservableSource<R>> flatMapWithCombiner(Function<? super T, ? extends ObservableSource<? extends U>> mapper, BiFunction<? super T, ? super U, ? extends R> combiner) {
        return new FlatMapWithCombinerOuter(combiner, mapper);
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$FlatMapIntoIterable */
    static final class FlatMapIntoIterable<T, U> implements Function<T, ObservableSource<U>> {
        private final Function<? super T, ? extends Iterable<? extends U>> mapper;

        FlatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> mapper2) {
            this.mapper = mapper2;
        }

        public ObservableSource<U> apply(T t) throws Exception {
            return new ObservableFromIterable((Iterable) ObjectHelper.requireNonNull(this.mapper.apply(t), "The mapper returned a null Iterable"));
        }
    }

    public static <T, U> Function<T, ObservableSource<U>> flatMapIntoIterable(Function<? super T, ? extends Iterable<? extends U>> mapper) {
        return new FlatMapIntoIterable(mapper);
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$MapToInt */
    enum MapToInt implements Function<Object, Object> {
        INSTANCE;

        public Object apply(Object t) throws Exception {
            return 0;
        }
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> parent) {
        return new ReplayCallable(parent);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> parent, int bufferSize) {
        return new BufferedReplayCallable(parent, bufferSize);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> parent, int bufferSize, long time, TimeUnit unit, Scheduler scheduler) {
        return new BufferedTimedReplayCallable(parent, bufferSize, time, unit, scheduler);
    }

    public static <T> Callable<ConnectableObservable<T>> replayCallable(Observable<T> parent, long time, TimeUnit unit, Scheduler scheduler) {
        return new TimedReplayCallable(parent, time, unit, scheduler);
    }

    public static <T, R> Function<Observable<T>, ObservableSource<R>> replayFunction(Function<? super Observable<T>, ? extends ObservableSource<R>> selector, Scheduler scheduler) {
        return new ReplayFunction(selector, scheduler);
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$ZipIterableFunction */
    static final class ZipIterableFunction<T, R> implements Function<List<ObservableSource<? extends T>>, ObservableSource<? extends R>> {
        private final Function<? super Object[], ? extends R> zipper;

        ZipIterableFunction(Function<? super Object[], ? extends R> zipper2) {
            this.zipper = zipper2;
        }

        public ObservableSource<? extends R> apply(List<ObservableSource<? extends T>> list) {
            return Observable.zipIterable(list, this.zipper, false, Observable.bufferSize());
        }
    }

    public static <T, R> Function<List<ObservableSource<? extends T>>, ObservableSource<? extends R>> zipIterable(Function<? super Object[], ? extends R> zipper) {
        return new ZipIterableFunction(zipper);
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$ReplayCallable */
    static final class ReplayCallable<T> implements Callable<ConnectableObservable<T>> {
        private final Observable<T> parent;

        ReplayCallable(Observable<T> parent2) {
            this.parent = parent2;
        }

        public ConnectableObservable<T> call() {
            return this.parent.replay();
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$BufferedReplayCallable */
    static final class BufferedReplayCallable<T> implements Callable<ConnectableObservable<T>> {
        private final int bufferSize;
        private final Observable<T> parent;

        BufferedReplayCallable(Observable<T> parent2, int bufferSize2) {
            this.parent = parent2;
            this.bufferSize = bufferSize2;
        }

        public ConnectableObservable<T> call() {
            return this.parent.replay(this.bufferSize);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$BufferedTimedReplayCallable */
    static final class BufferedTimedReplayCallable<T> implements Callable<ConnectableObservable<T>> {
        private final int bufferSize;
        private final Observable<T> parent;
        private final Scheduler scheduler;
        private final long time;
        private final TimeUnit unit;

        BufferedTimedReplayCallable(Observable<T> parent2, int bufferSize2, long time2, TimeUnit unit2, Scheduler scheduler2) {
            this.parent = parent2;
            this.bufferSize = bufferSize2;
            this.time = time2;
            this.unit = unit2;
            this.scheduler = scheduler2;
        }

        public ConnectableObservable<T> call() {
            return this.parent.replay(this.bufferSize, this.time, this.unit, this.scheduler);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$TimedReplayCallable */
    static final class TimedReplayCallable<T> implements Callable<ConnectableObservable<T>> {
        private final Observable<T> parent;
        private final Scheduler scheduler;
        private final long time;
        private final TimeUnit unit;

        TimedReplayCallable(Observable<T> parent2, long time2, TimeUnit unit2, Scheduler scheduler2) {
            this.parent = parent2;
            this.time = time2;
            this.unit = unit2;
            this.scheduler = scheduler2;
        }

        public ConnectableObservable<T> call() {
            return this.parent.replay(this.time, this.unit, this.scheduler);
        }
    }

    /* renamed from: io.reactivex.internal.operators.observable.ObservableInternalHelper$ReplayFunction */
    static final class ReplayFunction<T, R> implements Function<Observable<T>, ObservableSource<R>> {
        private final Scheduler scheduler;
        private final Function<? super Observable<T>, ? extends ObservableSource<R>> selector;

        ReplayFunction(Function<? super Observable<T>, ? extends ObservableSource<R>> selector2, Scheduler scheduler2) {
            this.selector = selector2;
            this.scheduler = scheduler2;
        }

        public ObservableSource<R> apply(Observable<T> t) throws Exception {
            return Observable.wrap((ObservableSource) ObjectHelper.requireNonNull(this.selector.apply(t), "The selector returned a null ObservableSource")).observeOn(this.scheduler);
        }
    }
}
