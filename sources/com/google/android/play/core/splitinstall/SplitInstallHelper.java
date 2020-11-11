package com.google.android.play.core.splitinstall;

import android.content.Context;
import com.google.android.play.core.internal.C2989aa;
import java.io.File;

public class SplitInstallHelper {

    /* renamed from: a */
    private static final C2989aa f1387a = new C2989aa("SplitInstallHelper");

    private SplitInstallHelper() {
    }

    public static void loadLibrary(Context context, String str) throws UnsatisfiedLinkError {
        try {
            synchronized (C3156v.class) {
                System.loadLibrary(str);
            }
        } catch (UnsatisfiedLinkError e) {
            try {
                String str2 = context.getApplicationInfo().nativeLibraryDir;
                String mapLibraryName = System.mapLibraryName(str);
                StringBuilder sb = new StringBuilder(String.valueOf(str2).length() + 1 + String.valueOf(mapLibraryName).length());
                sb.append(str2);
                sb.append("/");
                sb.append(mapLibraryName);
                String sb2 = sb.toString();
                if (new File(sb2).exists()) {
                    System.load(sb2);
                    return;
                }
                throw e;
            } catch (UnsatisfiedLinkError e2) {
                throw e2;
            }
        }
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v3, resolved type: java.lang.Object[]} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void updateAppInfo(android.content.Context r8) {
        /*
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 25
            if (r0 <= r1) goto L_0x0079
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 28
            if (r0 >= r1) goto L_0x0079
            com.google.android.play.core.internal.aa r0 = f1387a
            r1 = 0
            java.lang.Object[] r2 = new java.lang.Object[r1]
            java.lang.String r3 = "Calling dispatchPackageBroadcast"
            r0.mo44090c(r3, r2)
            java.lang.String r2 = "android.app.ActivityThread"
            java.lang.Class r2 = java.lang.Class.forName(r2)     // Catch:{ Exception -> 0x006f }
            java.lang.String r3 = "currentActivityThread"
            java.lang.Class[] r4 = new java.lang.Class[r1]     // Catch:{ Exception -> 0x006f }
            java.lang.reflect.Method r3 = r2.getMethod(r3, r4)     // Catch:{ Exception -> 0x006f }
            r4 = 1
            r3.setAccessible(r4)     // Catch:{ Exception -> 0x006f }
            java.lang.Object[] r5 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x006f }
            r6 = 0
            java.lang.Object r3 = r3.invoke(r6, r5)     // Catch:{ Exception -> 0x006f }
            java.lang.String r5 = "mAppThread"
            java.lang.reflect.Field r2 = r2.getDeclaredField(r5)     // Catch:{ Exception -> 0x006f }
            r2.setAccessible(r4)     // Catch:{ Exception -> 0x006f }
            java.lang.Object r2 = r2.get(r3)     // Catch:{ Exception -> 0x006f }
            java.lang.Class r3 = r2.getClass()     // Catch:{ Exception -> 0x006f }
            r5 = 2
            java.lang.Class[] r6 = new java.lang.Class[r5]     // Catch:{ Exception -> 0x006f }
            java.lang.Class r7 = java.lang.Integer.TYPE     // Catch:{ Exception -> 0x006f }
            r6[r1] = r7     // Catch:{ Exception -> 0x006f }
            java.lang.Class<java.lang.String[]> r7 = java.lang.String[].class
            r6[r4] = r7     // Catch:{ Exception -> 0x006f }
            java.lang.String r7 = "dispatchPackageBroadcast"
            java.lang.reflect.Method r3 = r3.getMethod(r7, r6)     // Catch:{ Exception -> 0x006f }
            java.lang.Object[] r5 = new java.lang.Object[r5]     // Catch:{ Exception -> 0x006f }
            r6 = 3
            java.lang.Integer r6 = java.lang.Integer.valueOf(r6)     // Catch:{ Exception -> 0x006f }
            r5[r1] = r6     // Catch:{ Exception -> 0x006f }
            java.lang.String[] r6 = new java.lang.String[r4]     // Catch:{ Exception -> 0x006f }
            java.lang.String r8 = r8.getPackageName()     // Catch:{ Exception -> 0x006f }
            r6[r1] = r8     // Catch:{ Exception -> 0x006f }
            r5[r4] = r6     // Catch:{ Exception -> 0x006f }
            r3.invoke(r2, r5)     // Catch:{ Exception -> 0x006f }
            java.lang.String r8 = "Called dispatchPackageBroadcast"
            java.lang.Object[] r2 = new java.lang.Object[r1]     // Catch:{ Exception -> 0x006f }
            r0.mo44090c(r8, r2)     // Catch:{ Exception -> 0x006f }
            return
        L_0x006f:
            r8 = move-exception
            com.google.android.play.core.internal.aa r0 = f1387a
            java.lang.Object[] r1 = new java.lang.Object[r1]
            java.lang.String r2 = "Update app info with dispatchPackageBroadcast failed!"
            r0.mo44088a((java.lang.Throwable) r8, (java.lang.String) r2, (java.lang.Object[]) r1)
        L_0x0079:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.play.core.splitinstall.SplitInstallHelper.updateAppInfo(android.content.Context):void");
    }
}
