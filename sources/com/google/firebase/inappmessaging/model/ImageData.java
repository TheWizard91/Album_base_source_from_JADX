package com.google.firebase.inappmessaging.model;

import android.graphics.Bitmap;
import android.text.TextUtils;

public class ImageData {
    private final Bitmap bitmapData;
    private final String imageUrl;

    public int hashCode() {
        Bitmap bitmap = this.bitmapData;
        return this.imageUrl.hashCode() + (bitmap != null ? bitmap.hashCode() : 0);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ImageData)) {
            return false;
        }
        ImageData i = (ImageData) o;
        if (hashCode() == i.hashCode() && this.imageUrl.equals(i.imageUrl)) {
            return true;
        }
        return false;
    }

    public ImageData(String imageUrl2, Bitmap bitmapData2) {
        this.imageUrl = imageUrl2;
        this.bitmapData = bitmapData2;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    public Bitmap getBitmapData() {
        return this.bitmapData;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private Bitmap bitmapData;
        private String imageUrl;

        public Builder setImageUrl(String imageUrl2) {
            if (!TextUtils.isEmpty(imageUrl2)) {
                this.imageUrl = imageUrl2;
            }
            return this;
        }

        public Builder setBitmapData(Bitmap bitmapData2) {
            this.bitmapData = bitmapData2;
            return this;
        }

        public ImageData build() {
            if (!TextUtils.isEmpty(this.imageUrl)) {
                return new ImageData(this.imageUrl, this.bitmapData);
            }
            throw new IllegalArgumentException("ImageData model must have an imageUrl");
        }
    }
}
