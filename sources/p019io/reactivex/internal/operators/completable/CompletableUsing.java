package p019io.reactivex.internal.operators.completable;

import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicReference;
import p019io.reactivex.Completable;
import p019io.reactivex.CompletableObserver;
import p019io.reactivex.CompletableSource;
import p019io.reactivex.disposables.Disposable;
import p019io.reactivex.exceptions.CompositeException;
import p019io.reactivex.exceptions.Exceptions;
import p019io.reactivex.functions.Consumer;
import p019io.reactivex.functions.Function;
import p019io.reactivex.internal.disposables.DisposableHelper;
import p019io.reactivex.internal.disposables.EmptyDisposable;
import p019io.reactivex.internal.functions.ObjectHelper;
import p019io.reactivex.plugins.RxJavaPlugins;

/* renamed from: io.reactivex.internal.operators.completable.CompletableUsing */
public final class CompletableUsing<R> extends Completable {
    final Function<? super R, ? extends CompletableSource> completableFunction;
    final Consumer<? super R> disposer;
    final boolean eager;
    final Callable<R> resourceSupplier;

    public CompletableUsing(Callable<R> resourceSupplier2, Function<? super R, ? extends CompletableSource> completableFunction2, Consumer<? super R> disposer2, boolean eager2) {
        this.resourceSupplier = resourceSupplier2;
        this.completableFunction = completableFunction2;
        this.disposer = disposer2;
        this.eager = eager2;
    }

    /* access modifiers changed from: protected */
    public void subscribeActual(CompletableObserver observer) {
        try {
            R resource = this.resourceSupplier.call();
            try {
                ((CompletableSource) ObjectHelper.requireNonNull(this.completableFunction.apply(resource), "The completableFunction returned a null CompletableSource")).subscribe(new UsingObserver(observer, resource, this.disposer, this.eager));
            } catch (Throwable exc) {
                Exceptions.throwIfFatal(exc);
                RxJavaPlugins.onError(exc);
            }
        } catch (Throwable ex) {
            Exceptions.throwIfFatal(ex);
            EmptyDisposable.error(ex, observer);
        }
    }

    /* renamed from: io.reactivex.internal.operators.completable.CompletableUsing$UsingObserver */
    static final class UsingObserver<R> extends AtomicReference<Object> implements CompletableObserver, Disposable {
        private static final long serialVersionUID = -674404550052917487L;
        final CompletableObserver actual;

        /* renamed from: d */
        Disposable f251d;
        final Consumer<? super R> disposer;
        final boolean eager;

        UsingObserver(CompletableObserver actual2, R resource, Consumer<? super R> disposer2, boolean eager2) {
            super(resource);
            this.actual = actual2;
            this.disposer = disposer2;
            this.eager = eager2;
        }

        public void dispose() {
            this.f251d.dispose();
            this.f251d = DisposableHelper.DISPOSED;
            disposeResourceAfter();
        }

        /* access modifiers changed from: package-private */
        public void disposeResourceAfter() {
            Object resource = getAndSet(this);
            if (resource != this) {
                try {
                    this.disposer.accept(resource);
                } catch (Throwable ex) {
                    Exceptions.throwIfFatal(ex);
                    RxJavaPlugins.onError(ex);
                }
            }
        }

        public boolean isDisposed() {
            return this.f251d.isDisposed();
        }

        public void onSubscribe(Disposable d) {
            if (DisposableHelper.validate(this.f251d, d)) {
                this.f251d = d;
                this.actual.onSubscribe(this);
            }
        }

        public void onError(Throwable e) {
            this.f251d = DisposableHelper.DISPOSED;
            if (this.eager) {
                Object resource = getAndSet(this);
                if (resource != this) {
                    try {
                        this.disposer.accept(resource);
                    } catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        e = new CompositeException(e, ex);
                    }
                } else {
                    return;
                }
            }
            this.actual.onError(e);
            if (!this.eager) {
                disposeResourceAfter();
            }
        }

        public void onComplete() {
            this.f251d = DisposableHelper.DISPOSED;
            if (this.eager) {
                Object resource = getAndSet(this);
                if (resource != this) {
                    try {
                        this.disposer.accept(resource);
                    } catch (Throwable ex) {
                        Exceptions.throwIfFatal(ex);
                        this.actual.onError(ex);
                        return;
                    }
                } else {
                    return;
                }
            }
            this.actual.onComplete();
            if (!this.eager) {
                disposeResourceAfter();
            }
        }
    }
}
