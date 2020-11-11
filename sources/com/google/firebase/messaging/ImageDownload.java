package com.google.firebase.messaging;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.internal.firebase_messaging.zzh;
import com.google.android.gms.internal.firebase_messaging.zzi;
import com.google.android.gms.internal.firebase_messaging.zzk;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.Executor;

/* compiled from: com.google.firebase:firebase-messaging@@20.2.4 */
class ImageDownload implements Closeable {
    private volatile InputStream connectionInputStream;
    private Task<Bitmap> task;
    private final URL url;

    public static ImageDownload create(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            return new ImageDownload(new URL(str));
        } catch (MalformedURLException e) {
            String valueOf = String.valueOf(str);
            Log.w(Constants.TAG, valueOf.length() != 0 ? "Not downloading image, bad URL: ".concat(valueOf) : new String("Not downloading image, bad URL: "));
            return null;
        }
    }

    private ImageDownload(URL url2) {
        this.url = url2;
    }

    public void start(Executor executor) {
        this.task = Tasks.call(executor, new ImageDownload$$Lambda$0(this));
    }

    public Task<Bitmap> getTask() {
        return (Task) Preconditions.checkNotNull(this.task);
    }

    public Bitmap blockingDownload() throws IOException {
        String valueOf = String.valueOf(this.url);
        Log.i(Constants.TAG, new StringBuilder(String.valueOf(valueOf).length() + 22).append("Starting download of: ").append(valueOf).toString());
        byte[] blockingDownloadBytes = blockingDownloadBytes();
        Bitmap decodeByteArray = BitmapFactory.decodeByteArray(blockingDownloadBytes, 0, blockingDownloadBytes.length);
        if (decodeByteArray != null) {
            if (Log.isLoggable(Constants.TAG, 3)) {
                String valueOf2 = String.valueOf(this.url);
                Log.d(Constants.TAG, new StringBuilder(String.valueOf(valueOf2).length() + 31).append("Successfully downloaded image: ").append(valueOf2).toString());
            }
            return decodeByteArray;
        }
        String valueOf3 = String.valueOf(this.url);
        throw new IOException(new StringBuilder(String.valueOf(valueOf3).length() + 24).append("Failed to decode image: ").append(valueOf3).toString());
    }

    private byte[] blockingDownloadBytes() throws IOException {
        URLConnection openConnection = this.url.openConnection();
        if (openConnection.getContentLength() <= 1048576) {
            InputStream inputStream = openConnection.getInputStream();
            try {
                this.connectionInputStream = inputStream;
                byte[] zza = zzh.zza(zzh.zza(inputStream, 1048577));
                if (inputStream != null) {
                    inputStream.close();
                }
                if (Log.isLoggable(Constants.TAG, 2)) {
                    int length = zza.length;
                    String valueOf = String.valueOf(this.url);
                    Log.v(Constants.TAG, new StringBuilder(String.valueOf(valueOf).length() + 34).append("Downloaded ").append(length).append(" bytes from ").append(valueOf).toString());
                }
                if (zza.length <= 1048576) {
                    return zza;
                }
                throw new IOException("Image exceeds max size of 1048576");
            } catch (Throwable th) {
                zzk.zza(th, th);
            }
        } else {
            throw new IOException("Content-Length exceeds max size of 1048576");
        }
        throw th;
    }

    public void close() {
        try {
            zzi.zza(this.connectionInputStream);
        } catch (NullPointerException e) {
            Log.e(Constants.TAG, "Failed to close the image download stream.", e);
        }
    }
}
