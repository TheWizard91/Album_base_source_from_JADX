package com.facebook.drawee.backends.pipeline.info;

import java.util.Collection;

public class ForwardingImagePerfDataListener implements ImagePerfDataListener {
    private final Collection<ImagePerfDataListener> mListeners;

    public ForwardingImagePerfDataListener(Collection<ImagePerfDataListener> listeners) {
        this.mListeners = listeners;
    }

    public void onImageLoadStatusUpdated(ImagePerfData imagePerfData, int imageLoadStatus) {
        for (ImagePerfDataListener listener : this.mListeners) {
            listener.onImageLoadStatusUpdated(imagePerfData, imageLoadStatus);
        }
    }

    public void onImageVisibilityUpdated(ImagePerfData imagePerfData, int visibilityState) {
        for (ImagePerfDataListener listener : this.mListeners) {
            listener.onImageVisibilityUpdated(imagePerfData, visibilityState);
        }
    }
}
