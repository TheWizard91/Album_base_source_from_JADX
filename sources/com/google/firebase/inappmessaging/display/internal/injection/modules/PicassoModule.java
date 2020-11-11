package com.google.firebase.inappmessaging.display.internal.injection.modules;

import android.app.Application;
import com.google.common.net.HttpHeaders;
import com.google.firebase.inappmessaging.display.internal.PicassoErrorListener;
import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import java.io.IOException;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

@Module
public class PicassoModule {
    /* access modifiers changed from: package-private */
    @Provides
    public Picasso providesFiamController(Application application, PicassoErrorListener picassoErrorListener) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            public Response intercept(Interceptor.Chain chain) throws IOException {
                return chain.proceed(chain.request().newBuilder().addHeader(HttpHeaders.ACCEPT, "image/*").build());
            }
        }).build();
        Picasso.Builder builder = new Picasso.Builder(application);
        builder.listener(picassoErrorListener).downloader(new OkHttp3Downloader(client));
        return builder.build();
    }
}
