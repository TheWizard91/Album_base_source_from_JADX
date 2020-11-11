package com.facebook.soloader;

import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.soloader.nativeloader.NativeLoader;
import dalvik.system.BaseDexClassLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Nullable;

public class SoLoader {
    static final boolean DEBUG = false;
    public static final int SOLOADER_ALLOW_ASYNC_INIT = 2;
    public static final int SOLOADER_DISABLE_BACKUP_SOSOURCE = 8;
    public static final int SOLOADER_ENABLE_EXOPACKAGE = 1;
    public static final int SOLOADER_LOOK_IN_ZIP = 4;
    public static final int SOLOADER_SKIP_MERGED_JNI_ONLOAD = 16;
    private static final String SO_STORE_NAME_MAIN = "lib-main";
    private static final String SO_STORE_NAME_SPLIT = "lib-";
    static final boolean SYSTRACE_LIBRARY_LOADING;
    static final String TAG = "SoLoader";
    private static boolean isSystemApp;
    @Nullable
    private static ApplicationSoSource sApplicationSoSource;
    @Nullable
    private static UnpackingSoSource[] sBackupSoSources;
    private static int sFlags;
    private static final Set<String> sLoadedAndMergedLibraries = Collections.newSetFromMap(new ConcurrentHashMap());
    private static final HashSet<String> sLoadedLibraries = new HashSet<>();
    private static final Map<String, Object> sLoadingLibraries = new HashMap();
    @Nullable
    static SoFileLoader sSoFileLoader;
    @Nullable
    private static SoSource[] sSoSources = null;
    private static final ReentrantReadWriteLock sSoSourcesLock = new ReentrantReadWriteLock();
    private static volatile int sSoSourcesVersion = 0;
    @Nullable
    private static SystemLoadLibraryWrapper sSystemLoadLibraryWrapper = null;

    static {
        boolean z = false;
        boolean shouldSystrace = false;
        try {
            if (Build.VERSION.SDK_INT >= 18) {
                z = true;
            }
            shouldSystrace = z;
        } catch (NoClassDefFoundError | UnsatisfiedLinkError e) {
        }
        SYSTRACE_LIBRARY_LOADING = shouldSystrace;
    }

    public static void init(Context context, int flags) throws IOException {
        init(context, flags, (SoFileLoader) null);
    }

    public static void init(Context context, int flags, @Nullable SoFileLoader soFileLoader) throws IOException {
        StrictMode.ThreadPolicy oldPolicy = StrictMode.allowThreadDiskWrites();
        try {
            isSystemApp = checkIfSystemApp(context);
            initSoLoader(soFileLoader);
            initSoSources(context, flags, soFileLoader);
            if (!NativeLoader.isInitialized()) {
                NativeLoader.init(new NativeLoaderToSoLoaderDelegate());
            }
        } finally {
            StrictMode.setThreadPolicy(oldPolicy);
        }
    }

    public static void init(Context context, boolean nativeExopackage) {
        try {
            init(context, nativeExopackage ? 1 : 0);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private static void initSoSources(Context context, int flags, @Nullable SoFileLoader soFileLoader) throws IOException {
        int apkSoSourceFlags;
        Context context2 = context;
        sSoSourcesLock.writeLock().lock();
        try {
            if (sSoSources == null) {
                Log.d(TAG, "init start");
                sFlags = flags;
                ArrayList<SoSource> soSources = new ArrayList<>();
                String LD_LIBRARY_PATH = System.getenv("LD_LIBRARY_PATH");
                if (LD_LIBRARY_PATH == null) {
                    LD_LIBRARY_PATH = "/vendor/lib:/system/lib";
                }
                String[] systemLibraryDirectories = LD_LIBRARY_PATH.split(":");
                for (int i = 0; i < systemLibraryDirectories.length; i++) {
                    Log.d(TAG, "adding system library source: " + systemLibraryDirectories[i]);
                    soSources.add(new DirectorySoSource(new File(systemLibraryDirectories[i]), 2));
                }
                if (context2 == null) {
                    String[] strArr = systemLibraryDirectories;
                } else if ((flags & 1) != 0) {
                    sBackupSoSources = null;
                    Log.d(TAG, "adding exo package source: lib-main");
                    soSources.add(0, new ExoSoSource(context2, SO_STORE_NAME_MAIN));
                    String str = LD_LIBRARY_PATH;
                    String[] strArr2 = systemLibraryDirectories;
                } else {
                    if (isSystemApp) {
                        apkSoSourceFlags = 0;
                    } else {
                        apkSoSourceFlags = 1;
                        int ourSoSourceFlags = 0;
                        if (Build.VERSION.SDK_INT <= 17) {
                            ourSoSourceFlags = 0 | 1;
                        }
                        sApplicationSoSource = new ApplicationSoSource(context2, ourSoSourceFlags);
                        Log.d(TAG, "adding application source: " + sApplicationSoSource.toString());
                        soSources.add(0, sApplicationSoSource);
                    }
                    if ((sFlags & 8) != 0) {
                        sBackupSoSources = null;
                        String str2 = LD_LIBRARY_PATH;
                        String[] strArr3 = systemLibraryDirectories;
                    } else {
                        File mainApkDir = new File(context.getApplicationInfo().sourceDir);
                        ArrayList<UnpackingSoSource> backupSources = new ArrayList<>();
                        ApkSoSource mainApkSource = new ApkSoSource(context2, mainApkDir, SO_STORE_NAME_MAIN, apkSoSourceFlags);
                        backupSources.add(mainApkSource);
                        Log.d(TAG, "adding backup source from : " + mainApkSource.toString());
                        if (Build.VERSION.SDK_INT < 21) {
                            String[] strArr4 = systemLibraryDirectories;
                            ApkSoSource apkSoSource = mainApkSource;
                        } else if (context.getApplicationInfo().splitSourceDirs != null) {
                            Log.d(TAG, "adding backup sources from split apks");
                            int splitIndex = 0;
                            String[] strArr5 = context.getApplicationInfo().splitSourceDirs;
                            int length = strArr5.length;
                            int i2 = 0;
                            while (i2 < length) {
                                String LD_LIBRARY_PATH2 = LD_LIBRARY_PATH;
                                ApkSoSource splitApkSource = new ApkSoSource(context2, new File(strArr5[i2]), SO_STORE_NAME_SPLIT + splitIndex, apkSoSourceFlags);
                                Log.d(TAG, "adding backup source: " + splitApkSource.toString());
                                backupSources.add(splitApkSource);
                                i2++;
                                splitIndex++;
                                LD_LIBRARY_PATH = LD_LIBRARY_PATH2;
                                systemLibraryDirectories = systemLibraryDirectories;
                                mainApkSource = mainApkSource;
                            }
                            String[] strArr6 = systemLibraryDirectories;
                            ApkSoSource apkSoSource2 = mainApkSource;
                        } else {
                            String[] strArr7 = systemLibraryDirectories;
                            ApkSoSource apkSoSource3 = mainApkSource;
                        }
                        sBackupSoSources = (UnpackingSoSource[]) backupSources.toArray(new UnpackingSoSource[backupSources.size()]);
                        soSources.addAll(0, backupSources);
                    }
                }
                SoSource[] finalSoSources = (SoSource[]) soSources.toArray(new SoSource[soSources.size()]);
                int prepareFlags = makePrepareFlags();
                int i3 = finalSoSources.length;
                while (true) {
                    int i4 = i3 - 1;
                    if (i3 <= 0) {
                        break;
                    }
                    Log.d(TAG, "Preparing SO source: " + finalSoSources[i4]);
                    finalSoSources[i4].prepare(prepareFlags);
                    i3 = i4;
                }
                sSoSources = finalSoSources;
                sSoSourcesVersion++;
                Log.d(TAG, "init finish: " + sSoSources.length + " SO sources prepared");
            }
        } finally {
            Log.d(TAG, "init exiting");
            sSoSourcesLock.writeLock().unlock();
        }
    }

    private static int makePrepareFlags() {
        int prepareFlags = 0;
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.writeLock().lock();
        try {
            if ((sFlags & 2) != 0) {
                prepareFlags = 0 | 1;
            }
            reentrantReadWriteLock.writeLock().unlock();
            return prepareFlags;
        } catch (Throwable th) {
            sSoSourcesLock.writeLock().unlock();
            throw th;
        }
    }

    private static synchronized void initSoLoader(@Nullable SoFileLoader soFileLoader) {
        synchronized (SoLoader.class) {
            if (soFileLoader != null) {
                sSoFileLoader = soFileLoader;
                return;
            }
            final Runtime runtime = Runtime.getRuntime();
            Method nativeLoadRuntimeMethod = getNativeLoadRuntimeMethod();
            boolean hasNativeLoadMethod = nativeLoadRuntimeMethod != null;
            final String localLdLibraryPath = hasNativeLoadMethod ? Api14Utils.getClassLoaderLdLoadLibrary() : null;
            final String localLdLibraryPathNoZips = makeNonZipPath(localLdLibraryPath);
            final boolean z = hasNativeLoadMethod;
            final Method method = nativeLoadRuntimeMethod;
            sSoFileLoader = new SoFileLoader() {
                public void load(String pathToSoFile, int loadFlags) {
                    String error = null;
                    if (z) {
                        String path = (loadFlags & 4) == 4 ? localLdLibraryPath : localLdLibraryPathNoZips;
                        try {
                            synchronized (runtime) {
                                error = (String) method.invoke(runtime, new Object[]{pathToSoFile, SoLoader.class.getClassLoader(), path});
                                if (error != null) {
                                    throw new UnsatisfiedLinkError(error);
                                }
                            }
                            if (error != null) {
                                Log.e(SoLoader.TAG, "Error when loading lib: " + error + " lib hash: " + getLibHash(pathToSoFile) + " search path is " + path);
                            }
                        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                            try {
                                throw new RuntimeException("Error: Cannot load " + pathToSoFile, e);
                            } catch (Throwable th) {
                                if (error != null) {
                                    Log.e(SoLoader.TAG, "Error when loading lib: " + error + " lib hash: " + getLibHash(pathToSoFile) + " search path is " + path);
                                }
                                throw th;
                            }
                        }
                    } else {
                        System.load(pathToSoFile);
                    }
                }

                /* JADX WARNING: Code restructure failed: missing block: B:15:0x003b, code lost:
                    r4 = move-exception;
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:17:?, code lost:
                    r2.close();
                 */
                /* JADX WARNING: Code restructure failed: missing block: B:21:0x0044, code lost:
                    throw r4;
                 */
                /* Code decompiled incorrectly, please refer to instructions dump. */
                private java.lang.String getLibHash(java.lang.String r12) {
                    /*
                        r11 = this;
                        java.io.File r0 = new java.io.File     // Catch:{ IOException -> 0x0051, SecurityException -> 0x004b, NoSuchAlgorithmException -> 0x0045 }
                        r0.<init>(r12)     // Catch:{ IOException -> 0x0051, SecurityException -> 0x004b, NoSuchAlgorithmException -> 0x0045 }
                        java.lang.String r1 = "MD5"
                        java.security.MessageDigest r1 = java.security.MessageDigest.getInstance(r1)     // Catch:{ IOException -> 0x0051, SecurityException -> 0x004b, NoSuchAlgorithmException -> 0x0045 }
                        java.io.FileInputStream r2 = new java.io.FileInputStream     // Catch:{ IOException -> 0x0051, SecurityException -> 0x004b, NoSuchAlgorithmException -> 0x0045 }
                        r2.<init>(r0)     // Catch:{ IOException -> 0x0051, SecurityException -> 0x004b, NoSuchAlgorithmException -> 0x0045 }
                        r3 = 4096(0x1000, float:5.74E-42)
                        byte[] r3 = new byte[r3]     // Catch:{ all -> 0x0039 }
                    L_0x0014:
                        int r4 = r2.read(r3)     // Catch:{ all -> 0x0039 }
                        r5 = r4
                        r6 = 0
                        if (r4 <= 0) goto L_0x0020
                        r1.update(r3, r6, r5)     // Catch:{ all -> 0x0039 }
                        goto L_0x0014
                    L_0x0020:
                        java.lang.String r4 = "%32x"
                        r7 = 1
                        java.lang.Object[] r8 = new java.lang.Object[r7]     // Catch:{ all -> 0x0039 }
                        java.math.BigInteger r9 = new java.math.BigInteger     // Catch:{ all -> 0x0039 }
                        byte[] r10 = r1.digest()     // Catch:{ all -> 0x0039 }
                        r9.<init>(r7, r10)     // Catch:{ all -> 0x0039 }
                        r8[r6] = r9     // Catch:{ all -> 0x0039 }
                        java.lang.String r4 = java.lang.String.format(r4, r8)     // Catch:{ all -> 0x0039 }
                        r3 = r4
                        r2.close()     // Catch:{ IOException -> 0x0051, SecurityException -> 0x004b, NoSuchAlgorithmException -> 0x0045 }
                        goto L_0x0056
                    L_0x0039:
                        r3 = move-exception
                        throw r3     // Catch:{ all -> 0x003b }
                    L_0x003b:
                        r4 = move-exception
                        r2.close()     // Catch:{ all -> 0x0040 }
                        goto L_0x0044
                    L_0x0040:
                        r5 = move-exception
                        r3.addSuppressed(r5)     // Catch:{ IOException -> 0x0051, SecurityException -> 0x004b, NoSuchAlgorithmException -> 0x0045 }
                    L_0x0044:
                        throw r4     // Catch:{ IOException -> 0x0051, SecurityException -> 0x004b, NoSuchAlgorithmException -> 0x0045 }
                    L_0x0045:
                        r0 = move-exception
                        java.lang.String r3 = r0.toString()
                        goto L_0x0057
                    L_0x004b:
                        r0 = move-exception
                        java.lang.String r3 = r0.toString()
                        goto L_0x0056
                    L_0x0051:
                        r0 = move-exception
                        java.lang.String r3 = r0.toString()
                    L_0x0056:
                    L_0x0057:
                        return r3
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.C07851.getLibHash(java.lang.String):java.lang.String");
                }
            };
        }
    }

    @Nullable
    private static Method getNativeLoadRuntimeMethod() {
        if (Build.VERSION.SDK_INT < 23 || Build.VERSION.SDK_INT > 27) {
            return null;
        }
        try {
            Method method = Runtime.class.getDeclaredMethod("nativeLoad", new Class[]{String.class, ClassLoader.class, String.class});
            method.setAccessible(true);
            return method;
        } catch (NoSuchMethodException | SecurityException e) {
            Log.w(TAG, "Cannot get nativeLoad method", e);
            return null;
        }
    }

    private static boolean checkIfSystemApp(Context context) {
        return (context == null || (context.getApplicationInfo().flags & 129) == 0) ? false : true;
    }

    public static void setInTestMode() {
        setSoSources(new SoSource[]{new NoopSoSource()});
    }

    public static void deinitForTest() {
        setSoSources((SoSource[]) null);
    }

    static void setSoSources(SoSource[] sources) {
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.writeLock().lock();
        try {
            sSoSources = sources;
            sSoSourcesVersion++;
            reentrantReadWriteLock.writeLock().unlock();
        } catch (Throwable th) {
            sSoSourcesLock.writeLock().unlock();
            throw th;
        }
    }

    static void setSoFileLoader(SoFileLoader loader) {
        sSoFileLoader = loader;
    }

    static void resetStatus() {
        synchronized (SoLoader.class) {
            sLoadedLibraries.clear();
            sLoadingLibraries.clear();
            sSoFileLoader = null;
        }
        setSoSources((SoSource[]) null);
    }

    public static void setSystemLoadLibraryWrapper(SystemLoadLibraryWrapper wrapper) {
        sSystemLoadLibraryWrapper = wrapper;
    }

    public static final class WrongAbiError extends UnsatisfiedLinkError {
        WrongAbiError(Throwable cause, String machine) {
            super("APK was built for a different platform. Supported ABIs: " + Arrays.toString(SysUtil.getSupportedAbis()) + " error: " + machine);
            initCause(cause);
        }
    }

    @Nullable
    public static String getLibraryPath(String libName) throws IOException {
        String libPath = null;
        sSoSourcesLock.readLock().lock();
        try {
            if (sSoSources != null) {
                int i = 0;
                while (libPath == null) {
                    SoSource[] soSourceArr = sSoSources;
                    if (i >= soSourceArr.length) {
                        break;
                    }
                    libPath = soSourceArr[i].getLibraryPath(libName);
                    i++;
                }
            }
            return libPath;
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    @Nullable
    public static String[] getLibraryDependencies(String libName) throws IOException {
        String[] deps = null;
        sSoSourcesLock.readLock().lock();
        try {
            if (sSoSources != null) {
                int i = 0;
                while (deps == null) {
                    SoSource[] soSourceArr = sSoSources;
                    if (i >= soSourceArr.length) {
                        break;
                    }
                    deps = soSourceArr[i].getLibraryDependencies(libName);
                    i++;
                }
            }
            return deps;
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    public static boolean loadLibrary(String shortName) {
        return loadLibrary(shortName, 0);
    }

    public static boolean loadLibrary(String shortName, int loadFlags) throws UnsatisfiedLinkError {
        SystemLoadLibraryWrapper systemLoadLibraryWrapper;
        boolean needsLoad;
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.readLock().lock();
        try {
            if (sSoSources == null) {
                if ("http://www.android.com/".equals(System.getProperty("java.vendor.url"))) {
                    assertInitialized();
                } else {
                    synchronized (SoLoader.class) {
                        needsLoad = true ^ sLoadedLibraries.contains(shortName);
                        if (needsLoad) {
                            SystemLoadLibraryWrapper systemLoadLibraryWrapper2 = sSystemLoadLibraryWrapper;
                            if (systemLoadLibraryWrapper2 != null) {
                                systemLoadLibraryWrapper2.loadLibrary(shortName);
                            } else {
                                System.loadLibrary(shortName);
                            }
                        }
                    }
                    reentrantReadWriteLock.readLock().unlock();
                    return needsLoad;
                }
            }
            reentrantReadWriteLock.readLock().unlock();
            if (!isSystemApp || (systemLoadLibraryWrapper = sSystemLoadLibraryWrapper) == null) {
                String mergedLibName = MergedSoMapping.mapLibName(shortName);
                return loadLibraryBySoName(System.mapLibraryName(mergedLibName != null ? mergedLibName : shortName), shortName, mergedLibName, loadFlags | 2, (StrictMode.ThreadPolicy) null);
            }
            systemLoadLibraryWrapper.loadLibrary(shortName);
            return true;
        } catch (Throwable th) {
            sSoSourcesLock.readLock().unlock();
            throw th;
        }
    }

    static void loadLibraryBySoName(String soName, int loadFlags, StrictMode.ThreadPolicy oldPolicy) {
        loadLibraryBySoName(soName, (String) null, (String) null, loadFlags, oldPolicy);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:19:0x0038, code lost:
        monitor-enter(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:0x0039, code lost:
        if (r1 != false) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:?, code lost:
        monitor-enter(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:25:0x0040, code lost:
        if (r3.contains(r8) == false) goto L_0x0048;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:26:0x0042, code lost:
        if (r10 != null) goto L_0x0047;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0044, code lost:
        monitor-exit(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:?, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:30:0x0046, code lost:
        return false;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:31:0x0047, code lost:
        r1 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:33:?, code lost:
        monitor-exit(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:0x0049, code lost:
        if (r1 != false) goto L_0x00b5;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:?, code lost:
        android.util.Log.d(TAG, "About to load: " + r8);
        doLoadLibraryBySoName(r8, r11, r12);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:?, code lost:
        monitor-enter(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:?, code lost:
        android.util.Log.d(TAG, "Loaded: " + r8);
        r3.add(r8);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x0083, code lost:
        monitor-exit(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:49:0x0088, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:50:0x0089, code lost:
        r2 = r0.getMessage();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:51:0x008d, code lost:
        if (r2 == null) goto L_0x00aa;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:55:0x00a8, code lost:
        throw new com.facebook.soloader.SoLoader.WrongAbiError(r0, r2.substring(r2.lastIndexOf("unexpected e_machine:")));
     */
    /* JADX WARNING: Code restructure failed: missing block: B:56:0x00aa, code lost:
        throw r0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:57:0x00ab, code lost:
        r0 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:59:0x00b1, code lost:
        throw new java.lang.RuntimeException(r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:66:0x00b7, code lost:
        if ((r11 & 16) != 0) goto L_0x0113;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:68:0x00be, code lost:
        if (android.text.TextUtils.isEmpty(r9) != false) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:70:0x00c6, code lost:
        if (sLoadedAndMergedLibraries.contains(r9) == false) goto L_0x00c9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:71:0x00c8, code lost:
        r2 = true;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:72:0x00c9, code lost:
        r0 = r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:73:0x00ca, code lost:
        if (r10 == null) goto L_0x0113;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:74:0x00cc, code lost:
        if (r0 != false) goto L_0x0113;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:75:0x00ce, code lost:
        r2 = SYSTRACE_LIBRARY_LOADING;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:76:0x00d0, code lost:
        if (r2 == false) goto L_0x00d9;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:77:0x00d2, code lost:
        com.facebook.soloader.Api18TraceUtils.beginTraceSection("MergedSoMapping.invokeJniOnload[", r9, "]");
     */
    /* JADX WARNING: Code restructure failed: missing block: B:80:?, code lost:
        android.util.Log.d(TAG, "About to merge: " + r9 + " / " + r8);
        com.facebook.soloader.MergedSoMapping.invokeJniOnload(r9);
        sLoadedAndMergedLibraries.add(r9);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:81:0x0103, code lost:
        if (r2 == false) goto L_0x0113;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:83:?, code lost:
        com.facebook.soloader.Api18TraceUtils.endSection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:84:0x0109, code lost:
        r2 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:86:0x010c, code lost:
        if (SYSTRACE_LIBRARY_LOADING != false) goto L_0x010e;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:87:0x010e, code lost:
        com.facebook.soloader.Api18TraceUtils.endSection();
     */
    /* JADX WARNING: Code restructure failed: missing block: B:88:0x0112, code lost:
        throw r2;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:89:0x0113, code lost:
        monitor-exit(r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:91:0x0116, code lost:
        return !r1;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static boolean loadLibraryBySoName(java.lang.String r8, @javax.annotation.Nullable java.lang.String r9, @javax.annotation.Nullable java.lang.String r10, int r11, @javax.annotation.Nullable android.os.StrictMode.ThreadPolicy r12) {
        /*
            java.lang.Class<com.facebook.soloader.SoLoader> r0 = com.facebook.soloader.SoLoader.class
            boolean r1 = android.text.TextUtils.isEmpty(r9)
            r2 = 0
            if (r1 != 0) goto L_0x0012
            java.util.Set<java.lang.String> r1 = sLoadedAndMergedLibraries
            boolean r1 = r1.contains(r9)
            if (r1 == 0) goto L_0x0012
            return r2
        L_0x0012:
            r1 = 0
            monitor-enter(r0)
            java.util.HashSet<java.lang.String> r3 = sLoadedLibraries     // Catch:{ all -> 0x011a }
            boolean r4 = r3.contains(r8)     // Catch:{ all -> 0x011a }
            if (r4 == 0) goto L_0x0021
            if (r10 != 0) goto L_0x0020
            monitor-exit(r0)     // Catch:{ all -> 0x011a }
            return r2
        L_0x0020:
            r1 = 1
        L_0x0021:
            java.util.Map<java.lang.String, java.lang.Object> r4 = sLoadingLibraries     // Catch:{ all -> 0x011a }
            boolean r5 = r4.containsKey(r8)     // Catch:{ all -> 0x011a }
            if (r5 == 0) goto L_0x002e
            java.lang.Object r4 = r4.get(r8)     // Catch:{ all -> 0x011a }
            goto L_0x0037
        L_0x002e:
            java.lang.Object r5 = new java.lang.Object     // Catch:{ all -> 0x011a }
            r5.<init>()     // Catch:{ all -> 0x011a }
            r4.put(r8, r5)     // Catch:{ all -> 0x011a }
            r4 = r5
        L_0x0037:
            monitor-exit(r0)     // Catch:{ all -> 0x011a }
            monitor-enter(r4)
            if (r1 != 0) goto L_0x00b5
            monitor-enter(r0)     // Catch:{ all -> 0x0117 }
            boolean r5 = r3.contains(r8)     // Catch:{ all -> 0x00b2 }
            if (r5 == 0) goto L_0x0048
            if (r10 != 0) goto L_0x0047
            monitor-exit(r0)     // Catch:{ all -> 0x00b2 }
            monitor-exit(r4)     // Catch:{ all -> 0x0117 }
            return r2
        L_0x0047:
            r1 = 1
        L_0x0048:
            monitor-exit(r0)     // Catch:{ all -> 0x00b2 }
            if (r1 != 0) goto L_0x00b5
            java.lang.String r5 = "SoLoader"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ IOException -> 0x00ab, UnsatisfiedLinkError -> 0x0088 }
            r6.<init>()     // Catch:{ IOException -> 0x00ab, UnsatisfiedLinkError -> 0x0088 }
            java.lang.String r7 = "About to load: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ IOException -> 0x00ab, UnsatisfiedLinkError -> 0x0088 }
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ IOException -> 0x00ab, UnsatisfiedLinkError -> 0x0088 }
            java.lang.String r6 = r6.toString()     // Catch:{ IOException -> 0x00ab, UnsatisfiedLinkError -> 0x0088 }
            android.util.Log.d(r5, r6)     // Catch:{ IOException -> 0x00ab, UnsatisfiedLinkError -> 0x0088 }
            doLoadLibraryBySoName(r8, r11, r12)     // Catch:{ IOException -> 0x00ab, UnsatisfiedLinkError -> 0x0088 }
            monitor-enter(r0)     // Catch:{ all -> 0x0117 }
            java.lang.String r5 = "SoLoader"
            java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch:{ all -> 0x0085 }
            r6.<init>()     // Catch:{ all -> 0x0085 }
            java.lang.String r7 = "Loaded: "
            java.lang.StringBuilder r6 = r6.append(r7)     // Catch:{ all -> 0x0085 }
            java.lang.StringBuilder r6 = r6.append(r8)     // Catch:{ all -> 0x0085 }
            java.lang.String r6 = r6.toString()     // Catch:{ all -> 0x0085 }
            android.util.Log.d(r5, r6)     // Catch:{ all -> 0x0085 }
            r3.add(r8)     // Catch:{ all -> 0x0085 }
            monitor-exit(r0)     // Catch:{ all -> 0x0085 }
            goto L_0x00b5
        L_0x0085:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x0085 }
            throw r2     // Catch:{ all -> 0x0117 }
        L_0x0088:
            r0 = move-exception
            java.lang.String r2 = r0.getMessage()     // Catch:{ all -> 0x0117 }
            if (r2 == 0) goto L_0x00a9
            java.lang.String r3 = "unexpected e_machine:"
            boolean r3 = r2.contains(r3)     // Catch:{ all -> 0x0117 }
            if (r3 == 0) goto L_0x00a9
            java.lang.String r3 = "unexpected e_machine:"
            int r3 = r2.lastIndexOf(r3)     // Catch:{ all -> 0x0117 }
            java.lang.String r3 = r2.substring(r3)     // Catch:{ all -> 0x0117 }
            com.facebook.soloader.SoLoader$WrongAbiError r5 = new com.facebook.soloader.SoLoader$WrongAbiError     // Catch:{ all -> 0x0117 }
            r5.<init>(r0, r3)     // Catch:{ all -> 0x0117 }
            throw r5     // Catch:{ all -> 0x0117 }
        L_0x00a9:
            throw r0     // Catch:{ all -> 0x0117 }
        L_0x00ab:
            r0 = move-exception
            java.lang.RuntimeException r2 = new java.lang.RuntimeException     // Catch:{ all -> 0x0117 }
            r2.<init>(r0)     // Catch:{ all -> 0x0117 }
            throw r2     // Catch:{ all -> 0x0117 }
        L_0x00b2:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x00b2 }
            throw r2     // Catch:{ all -> 0x0117 }
        L_0x00b5:
            r0 = r11 & 16
            if (r0 != 0) goto L_0x0113
            boolean r0 = android.text.TextUtils.isEmpty(r9)     // Catch:{ all -> 0x0117 }
            if (r0 != 0) goto L_0x00c9
            java.util.Set<java.lang.String> r0 = sLoadedAndMergedLibraries     // Catch:{ all -> 0x0117 }
            boolean r0 = r0.contains(r9)     // Catch:{ all -> 0x0117 }
            if (r0 == 0) goto L_0x00c9
            r2 = 1
        L_0x00c9:
            r0 = r2
            if (r10 == 0) goto L_0x0113
            if (r0 != 0) goto L_0x0113
            boolean r2 = SYSTRACE_LIBRARY_LOADING     // Catch:{ all -> 0x0117 }
            if (r2 == 0) goto L_0x00d9
            java.lang.String r3 = "MergedSoMapping.invokeJniOnload["
            java.lang.String r5 = "]"
            com.facebook.soloader.Api18TraceUtils.beginTraceSection(r3, r9, r5)     // Catch:{ all -> 0x0117 }
        L_0x00d9:
            java.lang.String r3 = "SoLoader"
            java.lang.StringBuilder r5 = new java.lang.StringBuilder     // Catch:{ all -> 0x0109 }
            r5.<init>()     // Catch:{ all -> 0x0109 }
            java.lang.String r6 = "About to merge: "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0109 }
            java.lang.StringBuilder r5 = r5.append(r9)     // Catch:{ all -> 0x0109 }
            java.lang.String r6 = " / "
            java.lang.StringBuilder r5 = r5.append(r6)     // Catch:{ all -> 0x0109 }
            java.lang.StringBuilder r5 = r5.append(r8)     // Catch:{ all -> 0x0109 }
            java.lang.String r5 = r5.toString()     // Catch:{ all -> 0x0109 }
            android.util.Log.d(r3, r5)     // Catch:{ all -> 0x0109 }
            com.facebook.soloader.MergedSoMapping.invokeJniOnload(r9)     // Catch:{ all -> 0x0109 }
            java.util.Set<java.lang.String> r3 = sLoadedAndMergedLibraries     // Catch:{ all -> 0x0109 }
            r3.add(r9)     // Catch:{ all -> 0x0109 }
            if (r2 == 0) goto L_0x0113
            com.facebook.soloader.Api18TraceUtils.endSection()     // Catch:{ all -> 0x0117 }
            goto L_0x0113
        L_0x0109:
            r2 = move-exception
            boolean r3 = SYSTRACE_LIBRARY_LOADING     // Catch:{ all -> 0x0117 }
            if (r3 == 0) goto L_0x0111
            com.facebook.soloader.Api18TraceUtils.endSection()     // Catch:{ all -> 0x0117 }
        L_0x0111:
            throw r2     // Catch:{ all -> 0x0117 }
        L_0x0113:
            monitor-exit(r4)     // Catch:{ all -> 0x0117 }
            r0 = r1 ^ 1
            return r0
        L_0x0117:
            r0 = move-exception
            monitor-exit(r4)     // Catch:{ all -> 0x0117 }
            throw r0
        L_0x011a:
            r2 = move-exception
            monitor-exit(r0)     // Catch:{ all -> 0x011a }
            throw r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.loadLibraryBySoName(java.lang.String, java.lang.String, java.lang.String, int, android.os.StrictMode$ThreadPolicy):boolean");
    }

    public static File unpackLibraryAndDependencies(String shortName) throws UnsatisfiedLinkError {
        assertInitialized();
        try {
            return unpackLibraryBySoName(System.mapLibraryName(shortName));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    /* JADX INFO: finally extract failed */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x005e, code lost:
        android.util.Log.d(TAG, "Trying backup SoSource for " + r1);
        r12 = sBackupSoSources;
        r15 = r12.length;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:28:0x0077, code lost:
        r17 = r5;
        r5 = 0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:29:0x007d, code lost:
        if (r5 >= r15) goto L_0x00ac;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0081, code lost:
        r16 = r11;
        r11 = r12[r5];
     */
    /* JADX WARNING: Code restructure failed: missing block: B:34:?, code lost:
        r11.prepare(r1);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:35:0x008e, code lost:
        r18 = r11;
        r11 = r11.loadLibrary(r1, r2, r6);
        r19 = r12;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:36:0x0097, code lost:
        if (r11 != 1) goto L_0x009b;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0099, code lost:
        r5 = r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x009b, code lost:
        r5 = r5 + 1;
        r11 = r16;
        r12 = r19;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:39:0x00a2, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:40:0x00a3, code lost:
        r5 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x00a6, code lost:
        r0 = th;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x00a7, code lost:
        r16 = r11;
        r5 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00ac, code lost:
        r16 = r11;
        r5 = r17;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:52:0x00d1, code lost:
        r16 = r11;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private static void doLoadLibraryBySoName(java.lang.String r20, int r21, android.os.StrictMode.ThreadPolicy r22) throws java.io.IOException {
        /*
            r1 = r20
            r2 = r21
            java.lang.String r3 = " caused by: "
            java.lang.String r4 = " result: "
            r5 = 0
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = sSoSourcesLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r6 = r0.readLock()
            r6.lock()
            com.facebook.soloader.SoSource[] r6 = sSoSources     // Catch:{ all -> 0x0219 }
            java.lang.String r7 = "couldn't find DSO to load: "
            java.lang.String r8 = "SoLoader"
            if (r6 == 0) goto L_0x01e6
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r0 = r0.readLock()
            r0.unlock()
            r0 = 0
            if (r22 != 0) goto L_0x002c
            android.os.StrictMode$ThreadPolicy r6 = android.os.StrictMode.allowThreadDiskReads()
            r0 = 1
            r9 = r0
            goto L_0x002f
        L_0x002c:
            r6 = r22
            r9 = r0
        L_0x002f:
            boolean r0 = SYSTRACE_LIBRARY_LOADING
            if (r0 == 0) goto L_0x003a
            java.lang.String r0 = "SoLoader.loadLibrary["
            java.lang.String r10 = "]"
            com.facebook.soloader.Api18TraceUtils.beginTraceSection(r0, r1, r10)
        L_0x003a:
            r10 = 0
        L_0x003b:
            r11 = 0
            r12 = 3
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = sSoSourcesLock     // Catch:{ all -> 0x017f }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r0 = r0.readLock()     // Catch:{ all -> 0x017f }
            r0.lock()     // Catch:{ all -> 0x017f }
            int r0 = sSoSourcesVersion     // Catch:{ all -> 0x017f }
            r13 = r0
            r0 = 0
        L_0x004a:
            if (r5 != 0) goto L_0x00d1
            com.facebook.soloader.SoSource[] r15 = sSoSources     // Catch:{ all -> 0x00c3 }
            int r14 = r15.length     // Catch:{ all -> 0x00c3 }
            if (r0 >= r14) goto L_0x00d1
            r14 = r15[r0]     // Catch:{ all -> 0x00c3 }
            int r15 = r14.loadLibrary(r1, r2, r6)     // Catch:{ all -> 0x00c3 }
            r5 = r15
            if (r5 != r12) goto L_0x00b7
            com.facebook.soloader.UnpackingSoSource[] r15 = sBackupSoSources     // Catch:{ all -> 0x00b1 }
            if (r15 == 0) goto L_0x00b7
            java.lang.StringBuilder r15 = new java.lang.StringBuilder     // Catch:{ all -> 0x00b1 }
            r15.<init>()     // Catch:{ all -> 0x00b1 }
            java.lang.String r12 = "Trying backup SoSource for "
            java.lang.StringBuilder r12 = r15.append(r12)     // Catch:{ all -> 0x00b1 }
            java.lang.StringBuilder r12 = r12.append(r1)     // Catch:{ all -> 0x00b1 }
            java.lang.String r12 = r12.toString()     // Catch:{ all -> 0x00b1 }
            android.util.Log.d(r8, r12)     // Catch:{ all -> 0x00b1 }
            com.facebook.soloader.UnpackingSoSource[] r12 = sBackupSoSources     // Catch:{ all -> 0x00b1 }
            int r15 = r12.length     // Catch:{ all -> 0x00b1 }
            r16 = 0
            r17 = r5
            r5 = r16
        L_0x007d:
            if (r5 >= r15) goto L_0x00ac
            r16 = r12[r5]     // Catch:{ all -> 0x00a6 }
            r18 = r16
            r16 = r11
            r11 = r18
            r11.prepare((java.lang.String) r1)     // Catch:{ all -> 0x00a2 }
            int r18 = r11.loadLibrary(r1, r2, r6)     // Catch:{ all -> 0x00a2 }
            r19 = r18
            r18 = r11
            r11 = r19
            r19 = r12
            r12 = 1
            if (r11 != r12) goto L_0x009b
            r5 = r11
            goto L_0x00b0
        L_0x009b:
            int r5 = r5 + 1
            r11 = r16
            r12 = r19
            goto L_0x007d
        L_0x00a2:
            r0 = move-exception
            r5 = r17
            goto L_0x00c6
        L_0x00a6:
            r0 = move-exception
            r16 = r11
            r5 = r17
            goto L_0x00c6
        L_0x00ac:
            r16 = r11
            r5 = r17
        L_0x00b0:
            goto L_0x00d3
        L_0x00b1:
            r0 = move-exception
            r17 = r5
            r16 = r11
            goto L_0x00c6
        L_0x00b7:
            r17 = r5
            r16 = r11
            int r0 = r0 + 1
            r11 = r16
            r5 = r17
            r12 = 3
            goto L_0x004a
        L_0x00c3:
            r0 = move-exception
            r16 = r11
        L_0x00c6:
            java.util.concurrent.locks.ReentrantReadWriteLock r11 = sSoSourcesLock     // Catch:{ all -> 0x017f }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r11 = r11.readLock()     // Catch:{ all -> 0x017f }
            r11.unlock()     // Catch:{ all -> 0x017f }
            throw r0     // Catch:{ all -> 0x017f }
        L_0x00d1:
            r16 = r11
        L_0x00d3:
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = sSoSourcesLock     // Catch:{ all -> 0x017f }
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r11 = r0.readLock()     // Catch:{ all -> 0x017f }
            r11.unlock()     // Catch:{ all -> 0x017f }
            r11 = r2 & 2
            r12 = 2
            if (r11 != r12) goto L_0x0117
            if (r5 != 0) goto L_0x0117
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r11 = r0.writeLock()     // Catch:{ all -> 0x017f }
            r11.lock()     // Catch:{ all -> 0x017f }
            com.facebook.soloader.ApplicationSoSource r11 = sApplicationSoSource     // Catch:{ all -> 0x010b }
            if (r11 == 0) goto L_0x00fb
            boolean r11 = r11.checkAndMaybeUpdate()     // Catch:{ all -> 0x010b }
            if (r11 == 0) goto L_0x00fb
            int r11 = sSoSourcesVersion     // Catch:{ all -> 0x010b }
            r12 = 1
            int r11 = r11 + r12
            sSoSourcesVersion = r11     // Catch:{ all -> 0x010b }
        L_0x00fb:
            int r11 = sSoSourcesVersion     // Catch:{ all -> 0x010b }
            if (r11 == r13) goto L_0x0101
            r11 = 1
            goto L_0x0103
        L_0x0101:
            r11 = r16
        L_0x0103:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()     // Catch:{ all -> 0x017f }
            r0.unlock()     // Catch:{ all -> 0x017f }
            goto L_0x0119
        L_0x010b:
            r0 = move-exception
            java.util.concurrent.locks.ReentrantReadWriteLock r11 = sSoSourcesLock     // Catch:{ all -> 0x017f }
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r11 = r11.writeLock()     // Catch:{ all -> 0x017f }
            r11.unlock()     // Catch:{ all -> 0x017f }
            throw r0     // Catch:{ all -> 0x017f }
        L_0x0117:
            r11 = r16
        L_0x0119:
            if (r11 != 0) goto L_0x003b
            boolean r0 = SYSTRACE_LIBRARY_LOADING
            if (r0 == 0) goto L_0x0122
            com.facebook.soloader.Api18TraceUtils.endSection()
        L_0x0122:
            if (r9 == 0) goto L_0x0127
            android.os.StrictMode.setThreadPolicy(r6)
        L_0x0127:
            if (r5 == 0) goto L_0x012c
            r11 = 3
            if (r5 != r11) goto L_0x0193
        L_0x012c:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r7)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            if (r10 == 0) goto L_0x0161
            java.lang.String r7 = r10.getMessage()
            if (r7 != 0) goto L_0x0149
            java.lang.String r7 = r10.toString()
        L_0x0149:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.StringBuilder r11 = r11.append(r0)
            java.lang.StringBuilder r3 = r11.append(r3)
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.String r0 = r3.toString()
            r10.printStackTrace()
        L_0x0161:
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r0 = r3.toString()
            android.util.Log.e(r8, r0)
            java.lang.UnsatisfiedLinkError r3 = new java.lang.UnsatisfiedLinkError
            r3.<init>(r0)
            throw r3
        L_0x017f:
            r0 = move-exception
            r10 = r0
            boolean r0 = SYSTRACE_LIBRARY_LOADING
            if (r0 == 0) goto L_0x0188
            com.facebook.soloader.Api18TraceUtils.endSection()
        L_0x0188:
            if (r9 == 0) goto L_0x018d
            android.os.StrictMode.setThreadPolicy(r6)
        L_0x018d:
            if (r5 == 0) goto L_0x0194
            r11 = 3
            if (r5 != r11) goto L_0x0193
            goto L_0x0194
        L_0x0193:
            return
        L_0x0194:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>()
            java.lang.StringBuilder r0 = r0.append(r7)
            java.lang.StringBuilder r0 = r0.append(r1)
            java.lang.String r0 = r0.toString()
            java.lang.String r7 = r10.getMessage()
            if (r7 != 0) goto L_0x01b0
            java.lang.String r7 = r10.toString()
        L_0x01b0:
            java.lang.StringBuilder r11 = new java.lang.StringBuilder
            r11.<init>()
            java.lang.StringBuilder r11 = r11.append(r0)
            java.lang.StringBuilder r3 = r11.append(r3)
            java.lang.StringBuilder r3 = r3.append(r7)
            java.lang.String r0 = r3.toString()
            r10.printStackTrace()
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            r3.<init>()
            java.lang.StringBuilder r3 = r3.append(r0)
            java.lang.StringBuilder r3 = r3.append(r4)
            java.lang.StringBuilder r3 = r3.append(r5)
            java.lang.String r0 = r3.toString()
            android.util.Log.e(r8, r0)
            java.lang.UnsatisfiedLinkError r3 = new java.lang.UnsatisfiedLinkError
            r3.<init>(r0)
            throw r3
        L_0x01e6:
            java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch:{ all -> 0x0219 }
            r0.<init>()     // Catch:{ all -> 0x0219 }
            java.lang.String r3 = "Could not load: "
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x0219 }
            java.lang.StringBuilder r0 = r0.append(r1)     // Catch:{ all -> 0x0219 }
            java.lang.String r3 = " because no SO source exists"
            java.lang.StringBuilder r0 = r0.append(r3)     // Catch:{ all -> 0x0219 }
            java.lang.String r0 = r0.toString()     // Catch:{ all -> 0x0219 }
            android.util.Log.e(r8, r0)     // Catch:{ all -> 0x0219 }
            java.lang.UnsatisfiedLinkError r0 = new java.lang.UnsatisfiedLinkError     // Catch:{ all -> 0x0219 }
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch:{ all -> 0x0219 }
            r3.<init>()     // Catch:{ all -> 0x0219 }
            java.lang.StringBuilder r3 = r3.append(r7)     // Catch:{ all -> 0x0219 }
            java.lang.StringBuilder r3 = r3.append(r1)     // Catch:{ all -> 0x0219 }
            java.lang.String r3 = r3.toString()     // Catch:{ all -> 0x0219 }
            r0.<init>(r3)     // Catch:{ all -> 0x0219 }
            throw r0     // Catch:{ all -> 0x0219 }
        L_0x0219:
            r0 = move-exception
            java.util.concurrent.locks.ReentrantReadWriteLock r3 = sSoSourcesLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r3 = r3.readLock()
            r3.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.soloader.SoLoader.doLoadLibraryBySoName(java.lang.String, int, android.os.StrictMode$ThreadPolicy):void");
    }

    @Nullable
    public static String makeNonZipPath(String localLdLibraryPath) {
        if (localLdLibraryPath == null) {
            return null;
        }
        String[] paths = localLdLibraryPath.split(":");
        ArrayList<String> pathsWithoutZip = new ArrayList<>(paths.length);
        for (String path : paths) {
            if (!path.contains("!")) {
                pathsWithoutZip.add(path);
            }
        }
        return TextUtils.join(":", pathsWithoutZip);
    }

    static File unpackLibraryBySoName(String soName) throws IOException {
        sSoSourcesLock.readLock().lock();
        int i = 0;
        while (true) {
            try {
                SoSource[] soSourceArr = sSoSources;
                if (i < soSourceArr.length) {
                    File unpacked = soSourceArr[i].unpackLibrary(soName);
                    if (unpacked != null) {
                        return unpacked;
                    }
                    i++;
                } else {
                    sSoSourcesLock.readLock().unlock();
                    throw new FileNotFoundException(soName);
                }
            } finally {
                sSoSourcesLock.readLock().unlock();
            }
        }
    }

    private static void assertInitialized() {
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.readLock().lock();
        try {
            if (sSoSources != null) {
                reentrantReadWriteLock.readLock().unlock();
                return;
            }
            throw new RuntimeException("SoLoader.init() not yet called");
        } catch (Throwable th) {
            sSoSourcesLock.readLock().unlock();
            throw th;
        }
    }

    public static int getSoSourcesVersion() {
        return sSoSourcesVersion;
    }

    public static void prependSoSource(SoSource extraSoSource) throws IOException {
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.writeLock().lock();
        try {
            Log.d(TAG, "Prepending to SO sources: " + extraSoSource);
            assertInitialized();
            extraSoSource.prepare(makePrepareFlags());
            SoSource[] soSourceArr = sSoSources;
            SoSource[] newSoSources = new SoSource[(soSourceArr.length + 1)];
            newSoSources[0] = extraSoSource;
            System.arraycopy(soSourceArr, 0, newSoSources, 1, soSourceArr.length);
            sSoSources = newSoSources;
            sSoSourcesVersion++;
            Log.d(TAG, "Prepended to SO sources: " + extraSoSource);
            reentrantReadWriteLock.writeLock().unlock();
        } catch (Throwable th) {
            sSoSourcesLock.writeLock().unlock();
            throw th;
        }
    }

    public static String makeLdLibraryPath() {
        sSoSourcesLock.readLock().lock();
        try {
            assertInitialized();
            Log.d(TAG, "makeLdLibraryPath");
            ArrayList<String> pathElements = new ArrayList<>();
            SoSource[] soSources = sSoSources;
            for (SoSource addToLdLibraryPath : soSources) {
                addToLdLibraryPath.addToLdLibraryPath(pathElements);
            }
            String joinedPaths = TextUtils.join(":", pathElements);
            Log.d(TAG, "makeLdLibraryPath final path: " + joinedPaths);
            return joinedPaths;
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    public static boolean areSoSourcesAbisSupported() {
        ReentrantReadWriteLock reentrantReadWriteLock = sSoSourcesLock;
        reentrantReadWriteLock.readLock().lock();
        try {
            if (sSoSources == null) {
                reentrantReadWriteLock.readLock().unlock();
                return false;
            }
            String[] supportedAbis = SysUtil.getSupportedAbis();
            int i = 0;
            while (true) {
                SoSource[] soSourceArr = sSoSources;
                if (i < soSourceArr.length) {
                    String[] soSourceAbis = soSourceArr[i].getSoSourceAbis();
                    for (int j = 0; j < soSourceAbis.length; j++) {
                        boolean soSourceSupported = false;
                        for (int k = 0; k < supportedAbis.length && !soSourceSupported; k++) {
                            soSourceSupported = soSourceAbis[j].equals(supportedAbis[k]);
                        }
                        if (!soSourceSupported) {
                            Log.e(TAG, "abi not supported: " + soSourceAbis[j]);
                            return false;
                        }
                    }
                    i++;
                } else {
                    sSoSourcesLock.readLock().unlock();
                    return true;
                }
            }
        } finally {
            sSoSourcesLock.readLock().unlock();
        }
    }

    private static class Api14Utils {
        private Api14Utils() {
        }

        public static String getClassLoaderLdLoadLibrary() {
            ClassLoader classLoader = SoLoader.class.getClassLoader();
            if (classLoader instanceof BaseDexClassLoader) {
                try {
                    return (String) BaseDexClassLoader.class.getMethod("getLdLibraryPath", new Class[0]).invoke((BaseDexClassLoader) classLoader, new Object[0]);
                } catch (Exception e) {
                    throw new RuntimeException("Cannot call getLdLibraryPath", e);
                }
            } else {
                throw new IllegalStateException("ClassLoader " + classLoader.getClass().getName() + " should be of type BaseDexClassLoader");
            }
        }
    }
}
