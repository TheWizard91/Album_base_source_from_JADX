package com.facebook.imagepipeline.request;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.references.CloseableReference;
import com.facebook.imagepipeline.bitmaps.PlatformBitmapFactory;
import com.facebook.imagepipeline.core.NativeCodeSetup;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import javax.annotation.Nullable;

public abstract class BasePostprocessor implements Postprocessor {
    public static final Bitmap.Config FALLBACK_BITMAP_CONFIGURATION = Bitmap.Config.ARGB_8888;
    private static Method sCopyBitmap;

    public String getName() {
        return "Unknown postprocessor";
    }

    public CloseableReference<Bitmap> process(Bitmap sourceBitmap, PlatformBitmapFactory bitmapFactory) {
        Bitmap.Config sourceBitmapConfig = sourceBitmap.getConfig();
        CloseableReference<Bitmap> destBitmapRef = bitmapFactory.createBitmapInternal(sourceBitmap.getWidth(), sourceBitmap.getHeight(), sourceBitmapConfig != null ? sourceBitmapConfig : FALLBACK_BITMAP_CONFIGURATION);
        try {
            process(destBitmapRef.get(), sourceBitmap);
            return CloseableReference.cloneOrNull(destBitmapRef);
        } finally {
            CloseableReference.closeSafely((CloseableReference<?>) destBitmapRef);
        }
    }

    public void process(Bitmap destBitmap, Bitmap sourceBitmap) {
        internalCopyBitmap(destBitmap, sourceBitmap);
        process(destBitmap);
    }

    public void process(Bitmap bitmap) {
    }

    @Nullable
    public CacheKey getPostprocessorCacheKey() {
        return null;
    }

    private static void internalCopyBitmap(Bitmap destBitmap, Bitmap sourceBitmap) {
        if (!NativeCodeSetup.getUseNativeCode() || destBitmap.getConfig() != sourceBitmap.getConfig()) {
            new Canvas(destBitmap).drawBitmap(sourceBitmap, 0.0f, 0.0f, (Paint) null);
            return;
        }
        try {
            if (sCopyBitmap == null) {
                sCopyBitmap = Class.forName("com.facebook.imagepipeline.nativecode.Bitmaps").getDeclaredMethod("copyBitmap", new Class[]{Bitmap.class, Bitmap.class});
            }
            sCopyBitmap.invoke((Object) null, new Object[]{destBitmap, sourceBitmap});
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Wrong Native code setup, reflection failed.", e);
        } catch (IllegalAccessException e2) {
            throw new RuntimeException("Wrong Native code setup, reflection failed.", e2);
        } catch (NoSuchMethodException e3) {
            throw new RuntimeException("Wrong Native code setup, reflection failed.", e3);
        } catch (InvocationTargetException e4) {
            throw new RuntimeException("Wrong Native code setup, reflection failed.", e4);
        }
    }
}
