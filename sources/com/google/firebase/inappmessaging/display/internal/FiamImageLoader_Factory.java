package com.google.firebase.inappmessaging.display.internal;

import com.squareup.picasso.Picasso;
import dagger.internal.Factory;
import javax.inject.Provider;

public final class FiamImageLoader_Factory implements Factory<FiamImageLoader> {
    private final Provider<Picasso> picassoProvider;

    public FiamImageLoader_Factory(Provider<Picasso> picassoProvider2) {
        this.picassoProvider = picassoProvider2;
    }

    public FiamImageLoader get() {
        return new FiamImageLoader(this.picassoProvider.get());
    }

    public static FiamImageLoader_Factory create(Provider<Picasso> picassoProvider2) {
        return new FiamImageLoader_Factory(picassoProvider2);
    }

    public static FiamImageLoader newInstance(Picasso picasso) {
        return new FiamImageLoader(picasso);
    }
}
