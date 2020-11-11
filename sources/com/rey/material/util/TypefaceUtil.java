package com.rey.material.util;

import android.content.Context;
import android.graphics.Typeface;
import java.util.HashMap;

public class TypefaceUtil {
    private static final String PREFIX_ASSET = "asset:";
    private static final HashMap<String, Typeface> sCachedFonts = new HashMap<>();

    private TypefaceUtil() {
    }

    public static Typeface load(Context context, String familyName, int style) {
        if (familyName == null || !familyName.startsWith(PREFIX_ASSET)) {
            return Typeface.create(familyName, style);
        }
        HashMap<String, Typeface> hashMap = sCachedFonts;
        synchronized (hashMap) {
            try {
                if (!hashMap.containsKey(familyName)) {
                    Typeface typeface = Typeface.createFromAsset(context.getAssets(), familyName.substring(PREFIX_ASSET.length()));
                    hashMap.put(familyName, typeface);
                    return typeface;
                }
                Typeface typeface2 = hashMap.get(familyName);
                return typeface2;
            } catch (Exception e) {
                return Typeface.DEFAULT;
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
