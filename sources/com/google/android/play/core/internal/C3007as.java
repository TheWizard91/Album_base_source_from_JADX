package com.google.android.play.core.internal;

import java.io.File;

/* renamed from: com.google.android.play.core.internal.as */
final class C3007as implements C3008at {
    C3007as() {
    }

    /* renamed from: a */
    public final boolean mo44110a(Object obj, File file, File file2) {
        return new File((String) C3027bl.m712a(obj.getClass(), "optimizedPathFor", String.class, File.class, file, File.class, file2)).exists();
    }
}
