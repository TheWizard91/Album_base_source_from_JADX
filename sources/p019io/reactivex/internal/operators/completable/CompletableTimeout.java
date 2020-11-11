package p019io.reactivex.internal.operators.completable;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicBoolean;
import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.CompletableSource;
import p019io.reactivex.Scheduler;
import p019io.reactivex.disposables.CompositeDisposable;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletableTimeout */
public final class CompletableTimeout extends Completable {
    final CompletableSource other;
    final Scheduler scheduler;
    final CompletableSource source;
    final long timeout;
    final TimeUnit unit;

    public CompletableTimeout(CompletableSource source2, long timeout2, TimeUnit unit2, Scheduler scheduler2, CompletableSource other2) {
        this.source = source2;
        this.timeout = timeout2;
        this.unit = unit2;
        this.scheduler = scheduler2;
        this.other = other2;
    }

    public void subscribeActual(CompletableObserver s) {
        CompositeDisposable set = new CompositeDisposable();
        s.onSubscribe(set);
        AtomicBoolean once = new AtomicBoolean();
        set.add(this.scheduler.scheduleDirect(new DisposeTask(once, set, s), this.timeout, this.unit));
        this.source.subscribe(new TimeOutObserver(set, once, s));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableTimeout$TimeOutObserver */
    static final class TimeOutObserver implements CompletableObserver {
        private final AtomicBoolean once;

        /* renamed from: s */
        private final CompletableObserver f250s;
        private final CompositeDisposable set;

        TimeOutObserver(CompositeDisposable set2, AtomicBoolean once2, CompletableObserver s) {
            this.set = set2;
            this.once = once2;
            this.f250s = s;
        }

        public void onSubscribe(Disposable d) {
            this.set.add(d);
        }

        public void onError(Throwable e) {
            if (this.once.compareAndSet(false, true)) {
                this.set.dispose();
                this.f250s.onError(e);
                return;
            }
            RxJavaPlugins.onError(e);
        }

        public void onComplete() {
            if (this.once.compareAndSet(false, true)) {
                this.set.dispose();
                this.f250s.onComplete();
            }
        }
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableTimeout$DisposeTask */
    final class DisposeTask implements Runnable {
        private final AtomicBoolean once;

        /* renamed from: s */
        final CompletableObserver f249s;
        final CompositeDisposable set;

        DisposeTask(AtomicBoolean once2, CompositeDisposable set2, CompletableObserver s) {
            this.once = once2;
            this.set = set2;
            this.f249s = s;
        }

        public void run() {
            if (this.once.compareAndSet(false, true)) {
                this.set.clear();
                if (CompletableTimeout.this.other == null) {
                    this.f249s.onError(new TimeoutException());
                } else {
                    CompletableTimeout.this.other.subscribe(new DisposeObserver());
                }
            }
        }

        /* renamed from: io.reactivex.internal.operators.completable.CompletableTimeout$DisposeTask$DisposeObserver */
        final class DisposeObserver implements CompletableObserver {
            DisposeObserver() {
            }

            public void onSubscribe(Disposable d) {
                DisposeTask.this.set.add(d);
            }

            public void onError(Throwable e) {
                DisposeTask.this.set.dispose();
                DisposeTask.this.f249s.onError(e);
            }

            public void onComplete() {
                DisposeTask.this.set.dispose();
                DisposeTask.this.f249s.onComplete();
            }
        }
    }
}
