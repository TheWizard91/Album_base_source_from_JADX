package com.facebook.drawee.backends.pipeline.debug;

import androidx.core.internal.view.SupportMenu;
import androidx.core.view.InputDeviceCompat;
import com.facebook.common.internal.ImmutableMap;
import java.util.Map;

public class DebugOverlayImageOriginColor {
    private static final Map<Integer, Integer> IMAGE_ORIGIN_COLOR_MAP;

    static {
        Integer valueOf = Integer.valueOf(SupportMenu.CATEGORY_MASK);
        Integer valueOf2 = Integer.valueOf(InputDeviceCompat.SOURCE_ANY);
        IMAGE_ORIGIN_COLOR_MAP = ImmutableMap.m37of(1, -7829368, 2, valueOf, 3, valueOf2, 4, valueOf2, 5, -16711936, 6, -16711936);
    }

    public static int getImageOriginColor(int imageOrigin) {
        Integer colorFromMap = IMAGE_ORIGIN_COLOR_MAP.get(Integer.valueOf(imageOrigin));
        if (colorFromMap == null) {
            return -1;
        }
        return colorFromMap.intValue();
    }
}
