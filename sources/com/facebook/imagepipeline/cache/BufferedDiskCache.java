package com.facebook.imagepipeline.cache;

import bolts.Task;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.WriterCallback;
import com.facebook.cache.disk.FileCache;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.logging.FLog;
import com.facebook.common.memory.PooledByteBuffer;
import com.facebook.common.memory.PooledByteBufferFactory;
import com.facebook.common.memory.PooledByteStreams;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.instrumentation.FrescoInstrumenter;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.annotation.Nullable;

public class BufferedDiskCache {
    /* access modifiers changed from: private */
    public static final Class<?> TAG = BufferedDiskCache.class;
    /* access modifiers changed from: private */
    public final FileCache mFileCache;
    /* access modifiers changed from: private */
    public final ImageCacheStatsTracker mImageCacheStatsTracker;
    private final PooledByteBufferFactory mPooledByteBufferFactory;
    /* access modifiers changed from: private */
    public final PooledByteStreams mPooledByteStreams;
    private final Executor mReadExecutor;
    /* access modifiers changed from: private */
    public final StagingArea mStagingArea = StagingArea.getInstance();
    private final Executor mWriteExecutor;

    public BufferedDiskCache(FileCache fileCache, PooledByteBufferFactory pooledByteBufferFactory, PooledByteStreams pooledByteStreams, Executor readExecutor, Executor writeExecutor, ImageCacheStatsTracker imageCacheStatsTracker) {
        this.mFileCache = fileCache;
        this.mPooledByteBufferFactory = pooledByteBufferFactory;
        this.mPooledByteStreams = pooledByteStreams;
        this.mReadExecutor = readExecutor;
        this.mWriteExecutor = writeExecutor;
        this.mImageCacheStatsTracker = imageCacheStatsTracker;
    }

    public boolean containsSync(CacheKey key) {
        return this.mStagingArea.containsKey(key) || this.mFileCache.hasKeySync(key);
    }

    public Task<Boolean> contains(CacheKey key) {
        if (containsSync(key)) {
            return Task.forResult(true);
        }
        return containsAsync(key);
    }

    private Task<Boolean> containsAsync(final CacheKey key) {
        try {
            final Object token = FrescoInstrumenter.onBeforeSubmitWork("BufferedDiskCache_containsAsync");
            return Task.call(new Callable<Boolean>() {
                public Boolean call() throws Exception {
                    Object currentToken = FrescoInstrumenter.onBeginWork(token, (String) null);
                    try {
                        return Boolean.valueOf(BufferedDiskCache.this.checkInStagingAreaAndFileCache(key));
                    } finally {
                        FrescoInstrumenter.onEndWork(currentToken);
                    }
                }
            }, this.mReadExecutor);
        } catch (Exception exception) {
            FLog.m99w(TAG, (Throwable) exception, "Failed to schedule disk-cache read for %s", key.getUriString());
            return Task.forError(exception);
        }
    }

    public boolean diskCheckSync(CacheKey key) {
        if (containsSync(key)) {
            return true;
        }
        return checkInStagingAreaAndFileCache(key);
    }

    public Task<EncodedImage> get(CacheKey key, AtomicBoolean isCancelled) {
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("BufferedDiskCache#get");
            }
            EncodedImage pinnedImage = this.mStagingArea.get(key);
            if (pinnedImage != null) {
                return foundPinnedImage(key, pinnedImage);
            }
            Task<EncodedImage> async = getAsync(key, isCancelled);
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            return async;
        } finally {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
        }
    }

    /* access modifiers changed from: private */
    public boolean checkInStagingAreaAndFileCache(CacheKey key) {
        EncodedImage result = this.mStagingArea.get(key);
        if (result != null) {
            result.close();
            FLog.m81v(TAG, "Found image for %s in staging area", (Object) key.getUriString());
            this.mImageCacheStatsTracker.onStagingAreaHit(key);
            return true;
        }
        FLog.m81v(TAG, "Did not find image for %s in staging area", (Object) key.getUriString());
        this.mImageCacheStatsTracker.onStagingAreaMiss(key);
        try {
            return this.mFileCache.hasKey(key);
        } catch (Exception e) {
            return false;
        }
    }

    private Task<EncodedImage> getAsync(final CacheKey key, final AtomicBoolean isCancelled) {
        try {
            final Object token = FrescoInstrumenter.onBeforeSubmitWork("BufferedDiskCache_getAsync");
            return Task.call(new Callable<EncodedImage>() {
                @Nullable
                public EncodedImage call() throws Exception {
                    CloseableReference<PooledByteBuffer> ref;
                    Object currentToken = FrescoInstrumenter.onBeginWork(token, (String) null);
                    try {
                        if (!isCancelled.get()) {
                            EncodedImage result = BufferedDiskCache.this.mStagingArea.get(key);
                            if (result != null) {
                                FLog.m81v((Class<?>) BufferedDiskCache.TAG, "Found image for %s in staging area", (Object) key.getUriString());
                                BufferedDiskCache.this.mImageCacheStatsTracker.onStagingAreaHit(key);
                            } else {
                                FLog.m81v((Class<?>) BufferedDiskCache.TAG, "Did not find image for %s in staging area", (Object) key.getUriString());
                                BufferedDiskCache.this.mImageCacheStatsTracker.onStagingAreaMiss(key);
                                try {
                                    PooledByteBuffer buffer = BufferedDiskCache.this.readFromDiskCache(key);
                                    if (buffer == null) {
                                        FrescoInstrumenter.onEndWork(currentToken);
                                        return null;
                                    }
                                    ref = CloseableReference.m124of(buffer);
                                    result = new EncodedImage(ref);
                                    CloseableReference.closeSafely((CloseableReference<?>) ref);
                                } catch (Exception e) {
                                    FrescoInstrumenter.onEndWork(currentToken);
                                    return null;
                                } catch (Throwable th) {
                                    CloseableReference.closeSafely((CloseableReference<?>) ref);
                                    throw th;
                                }
                            }
                            if (!Thread.interrupted()) {
                                return result;
                            }
                            FLog.m80v((Class<?>) BufferedDiskCache.TAG, "Host thread was interrupted, decreasing reference count");
                            if (result != null) {
                                result.close();
                            }
                            throw new InterruptedException();
                        }
                        throw new CancellationException();
                    } finally {
                        FrescoInstrumenter.onEndWork(currentToken);
                    }
                }
            }, this.mReadExecutor);
        } catch (Exception exception) {
            FLog.m99w(TAG, (Throwable) exception, "Failed to schedule disk-cache read for %s", key.getUriString());
            return Task.forError(exception);
        }
    }

    public void put(final CacheKey key, EncodedImage encodedImage) {
        final EncodedImage finalEncodedImage;
        try {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.beginSection("BufferedDiskCache#put");
            }
            Preconditions.checkNotNull(key);
            Preconditions.checkArgument(EncodedImage.isValid(encodedImage));
            this.mStagingArea.put(key, encodedImage);
            finalEncodedImage = EncodedImage.cloneOrNull(encodedImage);
            final Object token = FrescoInstrumenter.onBeforeSubmitWork("BufferedDiskCache_putAsync");
            this.mWriteExecutor.execute(new Runnable() {
                public void run() {
                    Object currentToken = FrescoInstrumenter.onBeginWork(token, (String) null);
                    try {
                        BufferedDiskCache.this.writeToDiskCache(key, finalEncodedImage);
                    } finally {
                        BufferedDiskCache.this.mStagingArea.remove(key, finalEncodedImage);
                        EncodedImage.closeSafely(finalEncodedImage);
                        FrescoInstrumenter.onEndWork(currentToken);
                    }
                }
            });
        } catch (Exception exception) {
            FLog.m99w(TAG, (Throwable) exception, "Failed to schedule disk-cache write for %s", key.getUriString());
            this.mStagingArea.remove(key, encodedImage);
            EncodedImage.closeSafely(finalEncodedImage);
        } catch (Throwable th) {
            if (FrescoSystrace.isTracing()) {
                FrescoSystrace.endSection();
            }
            throw th;
        }
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
    }

    public Task<Void> remove(final CacheKey key) {
        Preconditions.checkNotNull(key);
        this.mStagingArea.remove(key);
        try {
            final Object token = FrescoInstrumenter.onBeforeSubmitWork("BufferedDiskCache_remove");
            return Task.call(new Callable<Void>() {
                public Void call() throws Exception {
                    Object currentToken = FrescoInstrumenter.onBeginWork(token, (String) null);
                    try {
                        BufferedDiskCache.this.mStagingArea.remove(key);
                        BufferedDiskCache.this.mFileCache.remove(key);
                        return null;
                    } finally {
                        FrescoInstrumenter.onEndWork(currentToken);
                    }
                }
            }, this.mWriteExecutor);
        } catch (Exception exception) {
            FLog.m99w(TAG, (Throwable) exception, "Failed to schedule disk-cache remove for %s", key.getUriString());
            return Task.forError(exception);
        }
    }

    public Task<Void> clearAll() {
        this.mStagingArea.clearAll();
        final Object token = FrescoInstrumenter.onBeforeSubmitWork("BufferedDiskCache_clearAll");
        try {
            return Task.call(new Callable<Void>() {
                public Void call() throws Exception {
                    Object currentToken = FrescoInstrumenter.onBeginWork(token, (String) null);
                    try {
                        BufferedDiskCache.this.mStagingArea.clearAll();
                        BufferedDiskCache.this.mFileCache.clearAll();
                        return null;
                    } finally {
                        FrescoInstrumenter.onEndWork(currentToken);
                    }
                }
            }, this.mWriteExecutor);
        } catch (Exception exception) {
            FLog.m99w(TAG, (Throwable) exception, "Failed to schedule disk-cache clear", new Object[0]);
            return Task.forError(exception);
        }
    }

    public long getSize() {
        return this.mFileCache.getSize();
    }

    private Task<EncodedImage> foundPinnedImage(CacheKey key, EncodedImage pinnedImage) {
        FLog.m81v(TAG, "Found image for %s in staging area", (Object) key.getUriString());
        this.mImageCacheStatsTracker.onStagingAreaHit(key);
        return Task.forResult(pinnedImage);
    }

    /* access modifiers changed from: private */
    @Nullable
    public PooledByteBuffer readFromDiskCache(CacheKey key) throws IOException {
        InputStream is;
        try {
            Class<?> cls = TAG;
            FLog.m81v(cls, "Disk cache read for %s", (Object) key.getUriString());
            BinaryResource diskCacheResource = this.mFileCache.getResource(key);
            if (diskCacheResource == null) {
                FLog.m81v(cls, "Disk cache miss for %s", (Object) key.getUriString());
                this.mImageCacheStatsTracker.onDiskCacheMiss(key);
                return null;
            }
            FLog.m81v(cls, "Found entry in disk cache for %s", (Object) key.getUriString());
            this.mImageCacheStatsTracker.onDiskCacheHit(key);
            is = diskCacheResource.openStream();
            PooledByteBuffer byteBuffer = this.mPooledByteBufferFactory.newByteBuffer(is, (int) diskCacheResource.size());
            is.close();
            FLog.m81v(cls, "Successful read from disk cache for %s", (Object) key.getUriString());
            return byteBuffer;
        } catch (IOException ioe) {
            FLog.m99w(TAG, (Throwable) ioe, "Exception reading from cache for %s", key.getUriString());
            this.mImageCacheStatsTracker.onDiskCacheGetFail(key);
            throw ioe;
        } catch (Throwable th) {
            is.close();
            throw th;
        }
    }

    /* access modifiers changed from: private */
    public void writeToDiskCache(CacheKey key, final EncodedImage encodedImage) {
        Class<?> cls = TAG;
        FLog.m81v(cls, "About to write to disk-cache for key %s", (Object) key.getUriString());
        try {
            this.mFileCache.insert(key, new WriterCallback() {
                public void write(OutputStream os) throws IOException {
                    BufferedDiskCache.this.mPooledByteStreams.copy(encodedImage.getInputStream(), os);
                }
            });
            this.mImageCacheStatsTracker.onDiskCachePut(key);
            FLog.m81v(cls, "Successful disk-cache write for key %s", (Object) key.getUriString());
        } catch (IOException ioe) {
            FLog.m99w(TAG, (Throwable) ioe, "Failed to write to disk-cache for key %s", key.getUriString());
        }
    }
}
