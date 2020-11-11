package com.google.firebase.inappmessaging.display.internal.injection.modules;

import android.app.Application;
import com.google.firebase.inappmessaging.display.internal.PicassoErrorListener;
import com.squareup.picasso.Picasso;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import javax.inject.Provider;

public final class PicassoModule_ProvidesFiamControllerFactory implements Factory<Picasso> {
    private final Provider<Application> applicationProvider;
    private final PicassoModule module;
    private final Provider<PicassoErrorListener> picassoErrorListenerProvider;

    public PicassoModule_ProvidesFiamControllerFactory(PicassoModule module2, Provider<Application> applicationProvider2, Provider<PicassoErrorListener> picassoErrorListenerProvider2) {
        this.module = module2;
        this.applicationProvider = applicationProvider2;
        this.picassoErrorListenerProvider = picassoErrorListenerProvider2;
    }

    public Picasso get() {
        return providesFiamController(this.module, this.applicationProvider.get(), this.picassoErrorListenerProvider.get());
    }

    public static PicassoModule_ProvidesFiamControllerFactory create(PicassoModule module2, Provider<Application> applicationProvider2, Provider<PicassoErrorListener> picassoErrorListenerProvider2) {
        return new PicassoModule_ProvidesFiamControllerFactory(module2, applicationProvider2, picassoErrorListenerProvider2);
    }

    public static Picasso providesFiamController(PicassoModule instance, Application application, PicassoErrorListener picassoErrorListener) {
        return (Picasso) Preconditions.checkNotNull(instance.providesFiamController(application, picassoErrorListener), "Cannot return null from a non-@Nullable @Provides method");
    }
}
