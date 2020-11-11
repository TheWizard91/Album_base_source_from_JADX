package com.firebase.p008ui.storage.images;

import android.util.Log;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StreamDownloadTask;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.security.MessageDigest;

/* renamed from: com.firebase.ui.storage.images.FirebaseImageLoader */
public class FirebaseImageLoader implements ModelLoader<StorageReference, InputStream> {
    private static final String TAG = "FirebaseImageLoader";

    /* renamed from: com.firebase.ui.storage.images.FirebaseImageLoader$Factory */
    public static class Factory implements ModelLoaderFactory<StorageReference, InputStream> {
        public ModelLoader<StorageReference, InputStream> build(MultiModelLoaderFactory factory) {
            return new FirebaseImageLoader();
        }

        public void teardown() {
        }
    }

    public ModelLoader.LoadData<InputStream> buildLoadData(StorageReference reference, int height, int width, Options options) {
        return new ModelLoader.LoadData<>(new FirebaseStorageKey(reference), new FirebaseStorageFetcher(reference));
    }

    public boolean handles(StorageReference reference) {
        return true;
    }

    /* renamed from: com.firebase.ui.storage.images.FirebaseImageLoader$FirebaseStorageKey */
    private static class FirebaseStorageKey implements Key {
        private StorageReference mRef;

        public FirebaseStorageKey(StorageReference ref) {
            this.mRef = ref;
        }

        public void updateDiskCacheKey(MessageDigest digest) {
            digest.update(this.mRef.getPath().getBytes(Charset.defaultCharset()));
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            return this.mRef.equals(((FirebaseStorageKey) o).mRef);
        }

        public int hashCode() {
            return this.mRef.hashCode();
        }
    }

    /* renamed from: com.firebase.ui.storage.images.FirebaseImageLoader$FirebaseStorageFetcher */
    private static class FirebaseStorageFetcher implements DataFetcher<InputStream> {
        /* access modifiers changed from: private */
        public InputStream mInputStream;
        private StorageReference mRef;
        private StreamDownloadTask mStreamTask;

        public FirebaseStorageFetcher(StorageReference ref) {
            this.mRef = ref;
        }

        public void loadData(Priority priority, final DataFetcher.DataCallback<? super InputStream> callback) {
            StreamDownloadTask stream = this.mRef.getStream();
            this.mStreamTask = stream;
            stream.addOnSuccessListener((OnSuccessListener) new OnSuccessListener<StreamDownloadTask.TaskSnapshot>() {
                public void onSuccess(StreamDownloadTask.TaskSnapshot snapshot) {
                    InputStream unused = FirebaseStorageFetcher.this.mInputStream = snapshot.getStream();
                    callback.onDataReady(FirebaseStorageFetcher.this.mInputStream);
                }
            }).addOnFailureListener((OnFailureListener) new OnFailureListener() {
                public void onFailure(Exception e) {
                    callback.onLoadFailed(e);
                }
            });
        }

        public void cleanup() {
            InputStream inputStream = this.mInputStream;
            if (inputStream != null) {
                try {
                    inputStream.close();
                    this.mInputStream = null;
                } catch (IOException e) {
                    Log.w(FirebaseImageLoader.TAG, "Could not close stream", e);
                }
            }
        }

        public void cancel() {
            StreamDownloadTask streamDownloadTask = this.mStreamTask;
            if (streamDownloadTask != null && streamDownloadTask.isInProgress()) {
                this.mStreamTask.cancel();
            }
        }

        public Class<InputStream> getDataClass() {
            return InputStream.class;
        }

        public DataSource getDataSource() {
            return DataSource.REMOTE;
        }
    }
}
