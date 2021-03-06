package com.davemorrissey.labs.subscaleview.decoder;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.net.Uri;
import android.text.TextUtils;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import java.io.InputStream;
import java.util.List;

public class SkiaImageDecoder implements ImageDecoder {
    private static final String ASSET_PREFIX = "file:///android_asset/";
    private static final String FILE_PREFIX = "file://";
    private static final String RESOURCE_PREFIX = "android.resource://";
    private final Bitmap.Config bitmapConfig;

    public SkiaImageDecoder() {
        this((Bitmap.Config) null);
    }

    public SkiaImageDecoder(Bitmap.Config bitmapConfig2) {
        Bitmap.Config globalBitmapConfig = SubsamplingScaleImageView.getPreferredBitmapConfig();
        if (bitmapConfig2 != null) {
            this.bitmapConfig = bitmapConfig2;
        } else if (globalBitmapConfig != null) {
            this.bitmapConfig = globalBitmapConfig;
        } else {
            this.bitmapConfig = Bitmap.Config.RGB_565;
        }
    }

    public Bitmap decode(Context context, Uri uri) throws Exception {
        Bitmap bitmap;
        Resources res;
        String uriString = uri.toString();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = this.bitmapConfig;
        if (uriString.startsWith(RESOURCE_PREFIX)) {
            String packageName = uri.getAuthority();
            if (context.getPackageName().equals(packageName)) {
                res = context.getResources();
            } else {
                res = context.getPackageManager().getResourcesForApplication(packageName);
            }
            int id = 0;
            List<String> segments = uri.getPathSegments();
            int size = segments.size();
            if (size == 2 && segments.get(0).equals("drawable")) {
                id = res.getIdentifier(segments.get(1), "drawable", packageName);
            } else if (size == 1 && TextUtils.isDigitsOnly(segments.get(0))) {
                try {
                    id = Integer.parseInt(segments.get(0));
                } catch (NumberFormatException e) {
                }
            }
            bitmap = BitmapFactory.decodeResource(context.getResources(), id, options);
        } else if (uriString.startsWith(ASSET_PREFIX)) {
            bitmap = BitmapFactory.decodeStream(context.getAssets().open(uriString.substring(ASSET_PREFIX.length())), (Rect) null, options);
        } else if (uriString.startsWith(FILE_PREFIX)) {
            bitmap = BitmapFactory.decodeFile(uriString.substring(FILE_PREFIX.length()), options);
        } else {
            InputStream inputStream = null;
            try {
                inputStream = context.getContentResolver().openInputStream(uri);
                bitmap = BitmapFactory.decodeStream(inputStream, (Rect) null, options);
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (Exception e2) {
                    }
                }
            }
        }
        if (bitmap != null) {
            return bitmap;
        }
        throw new RuntimeException("Skia image region decoder returned null bitmap - image format may not be supported");
    }
}
