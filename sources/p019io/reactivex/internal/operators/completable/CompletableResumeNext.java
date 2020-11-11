package p019io.reactivex.internal.operators.completable;

import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.CompletableSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.CompositeException;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.SequentialDisposable;

/* renamed from: io.reactivex.internal.operators.completable.CompletableResumeNext */
public final class CompletableResumeNext extends Completable {
    final Function<? super Throwable, ? extends CompletableSource> errorMapper;
    final CompletableSource source;

    public CompletableResumeNext(CompletableSource source2, Function<? super Throwable, ? extends CompletableSource> errorMapper2) {
        this.source = source2;
        this.errorMapper = errorMapper2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver s) {
        SequentialDisposable sd = new SequentialDisposable();
        s.onSubscribe(sd);
        this.source.subscribe(new ResumeNext(s, sd));
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableResumeNext$ResumeNext */
    final class ResumeNext implements CompletableObserver {

        /* renamed from: s */
        final CompletableObserver f247s;

        /* renamed from: sd */
        final SequentialDisposable f248sd;

        ResumeNext(CompletableObserver s, SequentialDisposable sd) {
            this.f247s = s;
            this.f248sd = sd;
        }

        public void onComplete() {
            this.f247s.onComplete();
        }

        public void onError(Throwable e) {
            try {
                CompletableSource c = (CompletableSource) CompletableResumeNext.this.errorMapper.apply(e);
                if (c == null) {
                    NullPointerException npe = new NullPointerException("The CompletableConsumable returned is null");
                    npe.initCause(e);
                    this.f247s.onError(npe);
                    return;
                }
                c.subscribe(new OnErrorObserver());
            } catch (Throwable ex) {
                Exceptions.throwIfFatal(ex);
                this.f247s.onError(new CompositeException(ex, e));
            }
        }

        public void onSubscribe(Disposable d) {
            this.f248sd.update(d);
        }

        /* renamed from: io.reactivex.internal.operators.completable.CompletableResumeNext$ResumeNext$OnErrorObserver */
        final class OnErrorObserver implements CompletableObserver {
            OnErrorObserver() {
            }

            public void onComplete() {
                ResumeNext.this.f247s.onComplete();
            }

            public void onError(Throwable e) {
                ResumeNext.this.f247s.onError(e);
            }

            public void onSubscribe(Disposable d) {
                ResumeNext.this.f248sd.update(d);
            }
        }
    }
}
