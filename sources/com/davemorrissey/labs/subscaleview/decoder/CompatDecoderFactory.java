package com.davemorrissey.labs.subscaleview.decoder;

import android.graphics.Bitmap;
import java.lang.reflect.InvocationTargetException;

public class CompatDecoderFactory<T> implements DecoderFactory<T> {
    private final Bitmap.Config bitmapConfig;
    private final Class<? extends T> clazz;

    public CompatDecoderFactory(Class<? extends T> clazz2) {
        this(clazz2, (Bitmap.Config) null);
    }

    public CompatDecoderFactory(Class<? extends T> clazz2, Bitmap.Config bitmapConfig2) {
        this.clazz = clazz2;
        this.bitmapConfig = bitmapConfig2;
    }

    public T make() throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        if (this.bitmapConfig == null) {
            return this.clazz.newInstance();
        }
        return this.clazz.getConstructor(new Class[]{Bitmap.Config.class}).newInstance(new Object[]{this.bitmapConfig});
    }
}
