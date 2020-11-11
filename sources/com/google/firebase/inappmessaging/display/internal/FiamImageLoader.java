package com.google.firebase.inappmessaging.display.internal;

import android.widget.ImageView;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;
import javax.inject.Inject;

public class FiamImageLoader {
    private final Picasso picasso;

    @Inject
    FiamImageLoader(Picasso picasso2) {
        this.picasso = picasso2;
    }

    public FiamImageRequestCreator load(String imageUrl) {
        return new FiamImageRequestCreator(this.picasso.load(imageUrl));
    }

    public void cancelTag(Class c) {
        this.picasso.cancelTag(c);
    }

    public static class FiamImageRequestCreator {
        private final RequestCreator mRequestCreator;

        public FiamImageRequestCreator(RequestCreator requestCreator) {
            this.mRequestCreator = requestCreator;
        }

        public FiamImageRequestCreator placeholder(int placeholderResId) {
            this.mRequestCreator.placeholder(placeholderResId);
            return this;
        }

        public FiamImageRequestCreator tag(Class c) {
            this.mRequestCreator.tag(c);
            return this;
        }

        public void into(ImageView imageView, Callback callback) {
            this.mRequestCreator.into(imageView, callback);
        }
    }
}
