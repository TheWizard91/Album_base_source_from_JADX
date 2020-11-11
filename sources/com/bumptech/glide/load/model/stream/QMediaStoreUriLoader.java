package com.bumptech.glide.load.model.stream;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.Options;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.data.mediastore.MediaStoreUtil;
import com.bumptech.glide.load.model.ModelLoader;
import com.bumptech.glide.load.model.ModelLoaderFactory;
import com.bumptech.glide.load.model.MultiModelLoaderFactory;
import com.bumptech.glide.signature.ObjectKey;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public final class QMediaStoreUriLoader<DataT> implements ModelLoader<Uri, DataT> {
    private final Context context;
    private final Class<DataT> dataClass;
    private final ModelLoader<File, DataT> fileDelegate;
    private final ModelLoader<Uri, DataT> uriDelegate;

    QMediaStoreUriLoader(Context context2, ModelLoader<File, DataT> fileDelegate2, ModelLoader<Uri, DataT> uriDelegate2, Class<DataT> dataClass2) {
        this.context = context2.getApplicationContext();
        this.fileDelegate = fileDelegate2;
        this.uriDelegate = uriDelegate2;
        this.dataClass = dataClass2;
    }

    public ModelLoader.LoadData<DataT> buildLoadData(Uri uri, int width, int height, Options options) {
        Uri uri2 = uri;
        return new ModelLoader.LoadData<>(new ObjectKey(uri), new QMediaStoreUriFetcher(this.context, this.fileDelegate, this.uriDelegate, uri, width, height, options, this.dataClass));
    }

    public boolean handles(Uri uri) {
        return Build.VERSION.SDK_INT >= 29 && MediaStoreUtil.isMediaStoreUri(uri);
    }

    private static final class QMediaStoreUriFetcher<DataT> implements DataFetcher<DataT> {
        private static final String[] PROJECTION = {"_data"};
        private final Context context;
        private final Class<DataT> dataClass;
        private volatile DataFetcher<DataT> delegate;
        private final ModelLoader<File, DataT> fileDelegate;
        private final int height;
        private volatile boolean isCancelled;
        private final Options options;
        private final Uri uri;
        private final ModelLoader<Uri, DataT> uriDelegate;
        private final int width;

        QMediaStoreUriFetcher(Context context2, ModelLoader<File, DataT> fileDelegate2, ModelLoader<Uri, DataT> uriDelegate2, Uri uri2, int width2, int height2, Options options2, Class<DataT> dataClass2) {
            this.context = context2.getApplicationContext();
            this.fileDelegate = fileDelegate2;
            this.uriDelegate = uriDelegate2;
            this.uri = uri2;
            this.width = width2;
            this.height = height2;
            this.options = options2;
            this.dataClass = dataClass2;
        }

        public void loadData(Priority priority, DataFetcher.DataCallback<? super DataT> callback) {
            try {
                DataFetcher<DataT> local = buildDelegateFetcher();
                if (local == null) {
                    callback.onLoadFailed(new IllegalArgumentException("Failed to build fetcher for: " + this.uri));
                    return;
                }
                this.delegate = local;
                if (this.isCancelled) {
                    cancel();
                } else {
                    local.loadData(priority, callback);
                }
            } catch (FileNotFoundException e) {
                callback.onLoadFailed(e);
            }
        }

        private DataFetcher<DataT> buildDelegateFetcher() throws FileNotFoundException {
            ModelLoader.LoadData<DataT> result = buildDelegateData();
            if (result != null) {
                return result.fetcher;
            }
            return null;
        }

        private ModelLoader.LoadData<DataT> buildDelegateData() throws FileNotFoundException {
            if (Environment.isExternalStorageLegacy()) {
                return this.fileDelegate.buildLoadData(queryForFilePath(this.uri), this.width, this.height, this.options);
            }
            return this.uriDelegate.buildLoadData(isAccessMediaLocationGranted() ? MediaStore.setRequireOriginal(this.uri) : this.uri, this.width, this.height, this.options);
        }

        public void cleanup() {
            DataFetcher<DataT> local = this.delegate;
            if (local != null) {
                local.cleanup();
            }
        }

        public void cancel() {
            this.isCancelled = true;
            DataFetcher<DataT> local = this.delegate;
            if (local != null) {
                local.cancel();
            }
        }

        public Class<DataT> getDataClass() {
            return this.dataClass;
        }

        public DataSource getDataSource() {
            return DataSource.LOCAL;
        }

        private File queryForFilePath(Uri uri2) throws FileNotFoundException {
            Cursor cursor = null;
            try {
                cursor = this.context.getContentResolver().query(uri2, PROJECTION, (String) null, (String[]) null, (String) null);
                if (cursor == null || !cursor.moveToFirst()) {
                    throw new FileNotFoundException("Failed to media store entry for: " + uri2);
                }
                String path = cursor.getString(cursor.getColumnIndexOrThrow("_data"));
                if (!TextUtils.isEmpty(path)) {
                    return new File(path);
                }
                throw new FileNotFoundException("File path was empty in media store for: " + uri2);
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }

        private boolean isAccessMediaLocationGranted() {
            return this.context.checkSelfPermission("android.permission.ACCESS_MEDIA_LOCATION") == 0;
        }
    }

    public static final class InputStreamFactory extends Factory<InputStream> {
        public InputStreamFactory(Context context) {
            super(context, InputStream.class);
        }
    }

    public static final class FileDescriptorFactory extends Factory<ParcelFileDescriptor> {
        public FileDescriptorFactory(Context context) {
            super(context, ParcelFileDescriptor.class);
        }
    }

    private static abstract class Factory<DataT> implements ModelLoaderFactory<Uri, DataT> {
        private final Context context;
        private final Class<DataT> dataClass;

        Factory(Context context2, Class<DataT> dataClass2) {
            this.context = context2;
            this.dataClass = dataClass2;
        }

        public final ModelLoader<Uri, DataT> build(MultiModelLoaderFactory multiFactory) {
            return new QMediaStoreUriLoader(this.context, multiFactory.build(File.class, this.dataClass), multiFactory.build(Uri.class, this.dataClass), this.dataClass);
        }

        public final void teardown() {
        }
    }
}
