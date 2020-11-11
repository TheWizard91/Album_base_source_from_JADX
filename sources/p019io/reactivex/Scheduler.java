package p019io.reactivex;

import java.util.concurrent.TimeUnit;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.disposables.SequentialDisposable;
import p019io.reactivex.internal.schedulers.NewThreadWorker;
import p019io.reactivex.internal.schedulers.SchedulerWhen;
import p019io.reactivex.internal.util.ExceptionHelper;
import p019io.reactivex.plugins.RxJavaPlugins;
import p019io.reactivex.schedulers.SchedulerRunnableIntrospection;

/* renamed from: io.reactivex.Scheduler */
public abstract class Scheduler {
    static final long CLOCK_DRIFT_TOLERANCE_NANOSECONDS = TimeUnit.MINUTES.toNanos(Long.getLong("rx2.scheduler.drift-tolerance", 15).longValue());

    public abstract Worker createWorker();

    public static long clockDriftTolerance() {
        return CLOCK_DRIFT_TOLERANCE_NANOSECONDS;
    }

    public long now(TimeUnit unit) {
        return unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
    }

    public void start() {
    }

    public void shutdown() {
    }

    public Disposable scheduleDirect(Runnable run) {
        return scheduleDirect(run, 0, TimeUnit.NANOSECONDS);
    }

    public Disposable scheduleDirect(Runnable run, long delay, TimeUnit unit) {
        Worker w = createWorker();
        DisposeTask task = new DisposeTask(RxJavaPlugins.onSchedule(run), w);
        w.schedule(task, delay, unit);
        return task;
    }

    public Disposable schedulePeriodicallyDirect(Runnable run, long initialDelay, long period, TimeUnit unit) {
        Worker w = createWorker();
        PeriodicDirectTask periodicTask = new PeriodicDirectTask(RxJavaPlugins.onSchedule(run), w);
        Disposable d = w.schedulePeriodically(periodicTask, initialDelay, period, unit);
        if (d == EmptyDisposable.INSTANCE) {
            return d;
        }
        return periodicTask;
    }

    public <S extends Scheduler & Disposable> S when(Function<Flowable<Flowable<Completable>>, Completable> combine) {
        return new SchedulerWhen(combine, this);
    }

    /* renamed from: io.reactivex.Scheduler$Worker */
    public static abstract class Worker implements Disposable {
        public abstract Disposable schedule(Runnable runnable, long j, TimeUnit timeUnit);

        public Disposable schedule(Runnable run) {
            return schedule(run, 0, TimeUnit.NANOSECONDS);
        }

        public Disposable schedulePeriodically(Runnable run, long initialDelay, long period, TimeUnit unit) {
            long j = initialDelay;
            TimeUnit timeUnit = unit;
            SequentialDisposable first = new SequentialDisposable();
            SequentialDisposable sd = new SequentialDisposable(first);
            Runnable decoratedRun = RxJavaPlugins.onSchedule(run);
            long periodInNanoseconds = timeUnit.toNanos(period);
            long firstNowNanoseconds = now(TimeUnit.NANOSECONDS);
            SequentialDisposable first2 = first;
            PeriodicTask periodicTask = r0;
            PeriodicTask periodicTask2 = new PeriodicTask(firstNowNanoseconds + timeUnit.toNanos(j), decoratedRun, firstNowNanoseconds, sd, periodInNanoseconds);
            Disposable d = schedule(periodicTask, j, timeUnit);
            if (d == EmptyDisposable.INSTANCE) {
                return d;
            }
            first2.replace(d);
            return sd;
        }

        public long now(TimeUnit unit) {
            return unit.convert(System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        /* renamed from: io.reactivex.Scheduler$Worker$PeriodicTask */
        final class PeriodicTask implements Runnable, SchedulerRunnableIntrospection {
            long count;
            final Runnable decoratedRun;
            long lastNowNanoseconds;
            final long periodInNanoseconds;

            /* renamed from: sd */
            final SequentialDisposable f201sd;
            long startInNanoseconds;

            PeriodicTask(long firstStartInNanoseconds, Runnable decoratedRun2, long firstNowNanoseconds, SequentialDisposable sd, long periodInNanoseconds2) {
                this.decoratedRun = decoratedRun2;
                this.f201sd = sd;
                this.periodInNanoseconds = periodInNanoseconds2;
                this.lastNowNanoseconds = firstNowNanoseconds;
                this.startInNanoseconds = firstStartInNanoseconds;
            }

            public void run() {
                long nextTick;
                this.decoratedRun.run();
                if (!this.f201sd.isDisposed()) {
                    long nowNanoseconds = Worker.this.now(TimeUnit.NANOSECONDS);
                    long j = this.lastNowNanoseconds;
                    if (Scheduler.CLOCK_DRIFT_TOLERANCE_NANOSECONDS + nowNanoseconds < j || nowNanoseconds >= j + this.periodInNanoseconds + Scheduler.CLOCK_DRIFT_TOLERANCE_NANOSECONDS) {
                        long nextTick2 = this.periodInNanoseconds;
                        long nextTick3 = nowNanoseconds + nextTick2;
                        long j2 = this.count + 1;
                        this.count = j2;
                        this.startInNanoseconds = nextTick3 - (nextTick2 * j2);
                        nextTick = nextTick3;
                    } else {
                        long j3 = this.startInNanoseconds;
                        long j4 = this.count + 1;
                        this.count = j4;
                        nextTick = j3 + (j4 * this.periodInNanoseconds);
                    }
                    this.lastNowNanoseconds = nowNanoseconds;
                    this.f201sd.replace(Worker.this.schedule(this, nextTick - nowNanoseconds, TimeUnit.NANOSECONDS));
                }
            }

            public Runnable getWrappedRunnable() {
                return this.decoratedRun;
            }
        }
    }

    /* renamed from: io.reactivex.Scheduler$PeriodicDirectTask */
    static final class PeriodicDirectTask implements Disposable, Runnable, SchedulerRunnableIntrospection {
        volatile boolean disposed;
        final Runnable run;
        final Worker worker;

        PeriodicDirectTask(Runnable run2, Worker worker2) {
            this.run = run2;
            this.worker = worker2;
        }

        public void run() {
            if (!this.disposed) {
                try {
                    this.run.run();
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    this.worker.dispose();
                    throw ExceptionHelper.wrapOrThrow(ex);
                }
            }
        }

        public void dispose() {
            this.disposed = true;
            this.worker.dispose();
        }

        public boolean isDisposed() {
            return this.disposed;
        }

        public Runnable getWrappedRunnable() {
            return this.run;
        }
    }

    /* renamed from: io.reactivex.Scheduler$DisposeTask */
    static final class DisposeTask implements Disposable, Runnable, SchedulerRunnableIntrospection {
        final Runnable decoratedRun;
        Thread runner;

        /* renamed from: w */
        final Worker f200w;

        DisposeTask(Runnable decoratedRun2, Worker w) {
            this.decoratedRun = decoratedRun2;
            this.f200w = w;
        }

        public void run() {
            this.runner = Thread.currentThread();
            try {
                this.decoratedRun.run();
            } finally {
                dispose();
                this.runner = null;
            }
        }

        public void dispose() {
            if (this.runner == Thread.currentThread()) {
                Worker worker = this.f200w;
                if (worker instanceof NewThreadWorker) {
                    ((NewThreadWorker) worker).shutdown();
                    return;
                }
            }
            this.f200w.dispose();
        }

        public boolean isDisposed() {
            return this.f200w.isDisposed();
        }

        public Runnable getWrappedRunnable() {
            return this.decoratedRun;
        }
    }
}
