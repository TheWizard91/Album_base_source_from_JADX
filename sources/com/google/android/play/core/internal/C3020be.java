package com.google.android.play.core.internal;

import android.util.Log;
import java.io.File;

/* renamed from: com.google.android.play.core.internal.be */
final class C3020be implements C3008at {
    C3020be() {
    }

    /* renamed from: a */
    public final boolean mo44110a(Object obj, File file, File file2) {
        try {
            return !((Boolean) C3027bl.m711a((Class) Class.forName("dalvik.system.DexFile"), "isDexOptNeeded", Boolean.class, String.class, file.getPath())).booleanValue();
        } catch (ClassNotFoundException e) {
            Log.e("SplitCompat", "Unexpected missing dalvik.system.DexFile.");
            return false;
        }
    }
}
