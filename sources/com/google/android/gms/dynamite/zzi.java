package com.google.android.gms.dynamite;

import dalvik.system.PathClassLoader;

/* compiled from: com.google.android.gms:play-services-basement@@17.2.1 */
final class zzi extends PathClassLoader {
    zzi(String str, ClassLoader classLoader) {
        super(str, classLoader);
    }

    /* access modifiers changed from: protected */
    public final Class<?> loadClass(String str, boolean z) throws ClassNotFoundException {
        if (!str.startsWith("java.") && !str.startsWith("android.")) {
            try {
                return findClass(str);
            } catch (ClassNotFoundException e) {
            }
        }
        return super.loadClass(str, z);
    }
}
