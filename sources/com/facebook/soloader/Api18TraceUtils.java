package com.facebook.soloader;

import android.os.Trace;
import javax.annotation.Nullable;

class Api18TraceUtils {
    private static final int MAX_SECTION_NAME_LENGTH = 127;

    Api18TraceUtils() {
    }

    public static void beginTraceSection(String prefix, @Nullable String middle, String suffix) {
        String sectionName = prefix + middle + suffix;
        if (sectionName.length() > 127 && middle != null) {
            sectionName = prefix + middle.substring(0, (127 - prefix.length()) - suffix.length()) + suffix;
        }
        Trace.beginSection(sectionName);
    }

    public static void endSection() {
        Trace.endSection();
    }
}
