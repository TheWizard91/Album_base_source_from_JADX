package com.google.android.play.core.internal;

import android.content.ComponentName;
import android.content.pm.ComponentInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;

/* renamed from: com.google.android.play.core.internal.bl */
public final class C3027bl {
    /* renamed from: a */
    public static <T> C3026bk<T> m710a(Object obj, String str, Class<T> cls) {
        return new C3026bk<>(obj, m721b(obj, str), cls);
    }

    /* renamed from: a */
    public static <R, P0> R m711a(Class cls, String str, Class<R> cls2, Class<P0> cls3, P0 p0) {
        try {
            return cls2.cast(m715a((Class<?>) cls, str, (Class<?>[]) new Class[]{cls3}).invoke((Object) null, new Object[]{p0}));
        } catch (Exception e) {
            throw new C3028bm(String.format("Failed to invoke static method %s on type %s", new Object[]{str, cls}), e);
        }
    }

    /* renamed from: a */
    public static <R, P0, P1> R m712a(Class cls, String str, Class<R> cls2, Class<P0> cls3, P0 p0, Class<P1> cls4, P1 p1) {
        try {
            return cls2.cast(m715a((Class<?>) cls, str, (Class<?>[]) new Class[]{cls3, cls4}).invoke((Object) null, new Object[]{p0, p1}));
        } catch (Exception e) {
            throw new C3028bm(String.format("Failed to invoke static method %s on type %s", new Object[]{str, cls}), e);
        }
    }

    /* renamed from: a */
    public static <R, P0> R m713a(Object obj, String str, Class<R> cls, Class<P0> cls2, P0 p0) {
        try {
            return cls.cast(m716a(obj, str, (Class<?>[]) new Class[]{cls2}).invoke(obj, new Object[]{p0}));
        } catch (Exception e) {
            throw new C3028bm(String.format("Failed to invoke method %s on an object of type %s", new Object[]{str, obj.getClass()}), e);
        }
    }

    /* renamed from: a */
    public static <R, P0, P1, P2> R m714a(Object obj, String str, Class<R> cls, Class<P0> cls2, P0 p0, Class<P1> cls3, P1 p1, Class<P2> cls4, P2 p2) {
        try {
            return cls.cast(m716a(obj, str, (Class<?>[]) new Class[]{cls2, cls3, cls4}).invoke(obj, new Object[]{p0, p1, p2}));
        } catch (Exception e) {
            throw new C3028bm(String.format("Failed to invoke method %s on an object of type %s", new Object[]{str, obj.getClass()}), e);
        }
    }

    /* renamed from: a */
    private static Method m715a(Class<?> cls, String str, Class<?>... clsArr) {
        Class<?> cls2 = cls;
        while (cls2 != null) {
            try {
                Method declaredMethod = cls2.getDeclaredMethod(str, clsArr);
                if (!declaredMethod.isAccessible()) {
                    declaredMethod.setAccessible(true);
                }
                return declaredMethod;
            } catch (NoSuchMethodException e) {
                cls2 = cls2.getSuperclass();
            }
        }
        throw new C3028bm(String.format("Could not find a method named %s with parameters %s in type %s", new Object[]{str, Arrays.asList(clsArr), cls}));
    }

    /* renamed from: a */
    private static Method m716a(Object obj, String str, Class<?>... clsArr) {
        return m715a(obj.getClass(), str, clsArr);
    }

    /* renamed from: a */
    public static void m717a(PackageManager packageManager, ComponentName componentName) {
        ComponentInfo componentInfo;
        int componentEnabledSetting = packageManager.getComponentEnabledSetting(componentName);
        if (componentEnabledSetting != 1) {
            if (componentEnabledSetting != 2) {
                String packageName = componentName.getPackageName();
                String className = componentName.getClassName();
                try {
                    PackageInfo packageInfo = packageManager.getPackageInfo(packageName, 527);
                    ComponentInfo[][] componentInfoArr = {packageInfo.activities, packageInfo.services, packageInfo.providers};
                    int i = 0;
                    loop0:
                    while (true) {
                        if (i >= 3) {
                            componentInfo = null;
                            break;
                        }
                        ComponentInfo[] componentInfoArr2 = componentInfoArr[i];
                        if (componentInfoArr2 != null) {
                            int length = componentInfoArr2.length;
                            for (int i2 = 0; i2 < length; i2++) {
                                componentInfo = componentInfoArr2[i2];
                                if (componentInfo.name.equals(className)) {
                                    break loop0;
                                }
                            }
                            continue;
                        }
                        i++;
                    }
                    if (componentInfo != null && componentInfo.isEnabled()) {
                        return;
                    }
                } catch (PackageManager.NameNotFoundException e) {
                }
            }
            packageManager.setComponentEnabledSetting(componentName, 1, 1);
        }
    }

    /* renamed from: a */
    public static <T> void m718a(T t) {
        if (t == null) {
            throw null;
        }
    }

    /* renamed from: a */
    public static <T> void m719a(T t, String str) {
        if (t == null) {
            throw new NullPointerException(str);
        }
    }

    /* renamed from: b */
    public static <T> C3026bk m720b(Object obj, String str, Class<T> cls) {
        return new C3026bk(obj, m721b(obj, str), cls, (byte[]) null);
    }

    /* renamed from: b */
    private static Field m721b(Object obj, String str) {
        Class cls = obj.getClass();
        while (cls != null) {
            try {
                Field declaredField = cls.getDeclaredField(str);
                if (!declaredField.isAccessible()) {
                    declaredField.setAccessible(true);
                }
                return declaredField;
            } catch (NoSuchFieldException e) {
                cls = cls.getSuperclass();
            }
        }
        throw new C3028bm(String.format("Failed to find a field named %s on an object of instance %s", new Object[]{str, obj.getClass().getName()}));
    }
}
