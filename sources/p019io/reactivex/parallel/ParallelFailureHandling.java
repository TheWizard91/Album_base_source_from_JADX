package p019io.reactivex.parallel;

import p019io.reactivex.functions.BiFunction;

/* renamed from: io.reactivex.parallel.ParallelFailureHandling */
public enum ParallelFailureHandling implements BiFunction<Long, Throwable, ParallelFailureHandling> {
    STOP,
    ERROR,
    SKIP,
    RETRY;

    public ParallelFailureHandling apply(Long t1, Throwable t2) {
        return this;
    }
}
