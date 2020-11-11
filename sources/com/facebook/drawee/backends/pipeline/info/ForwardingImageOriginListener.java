package com.facebook.drawee.backends.pipeline.info;

import com.facebook.common.logging.FLog;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class ForwardingImageOriginListener implements ImageOriginListener {
    private static final String TAG = "ForwardingImageOriginListener";
    private final List<ImageOriginListener> mImageOriginListeners;

    public ForwardingImageOriginListener(Set<ImageOriginListener> imageOriginListeners) {
        this.mImageOriginListeners = new ArrayList(imageOriginListeners);
    }

    public ForwardingImageOriginListener(ImageOriginListener... imageOriginListeners) {
        ArrayList arrayList = new ArrayList(imageOriginListeners.length);
        this.mImageOriginListeners = arrayList;
        Collections.addAll(arrayList, imageOriginListeners);
    }

    public synchronized void addImageOriginListener(ImageOriginListener listener) {
        this.mImageOriginListeners.add(listener);
    }

    public synchronized void removeImageOriginListener(ImageOriginListener listener) {
        this.mImageOriginListeners.remove(listener);
    }

    public synchronized void onImageLoaded(String controllerId, int imageOrigin, boolean successful, String ultimateProducerName) {
        int numberOfListeners = this.mImageOriginListeners.size();
        for (int i = 0; i < numberOfListeners; i++) {
            ImageOriginListener listener = this.mImageOriginListeners.get(i);
            if (listener != null) {
                try {
                    listener.onImageLoaded(controllerId, imageOrigin, successful, ultimateProducerName);
                } catch (Exception e) {
                    FLog.m61e(TAG, "InternalListener exception in onImageLoaded", (Throwable) e);
                }
            }
        }
    }
}
