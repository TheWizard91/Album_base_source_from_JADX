package com.google.android.gms.common.util;

import android.os.Process;
import android.os.StrictMode;
import java.io.BufferedReader;
import java.io.Closeable;
import java.io.FileReader;
import java.io.IOException;
import javax.annotation.Nullable;

/* compiled from: com.google.android.gms:play-services-basement@@17.2.1 */
public class ProcessUtils {
    private static String zzhv = null;
    private static int zzhw = 0;

    private ProcessUtils() {
    }

    @Nullable
    public static String getMyProcessName() {
        if (zzhv == null) {
            if (zzhw == 0) {
                zzhw = Process.myPid();
            }
            zzhv = zzd(zzhw);
        }
        return zzhv;
    }

    @Nullable
    private static String zzd(int i) {
        BufferedReader bufferedReader;
        Throwable th;
        String str = null;
        if (i <= 0) {
            return null;
        }
        try {
            bufferedReader = zzk(new StringBuilder(25).append("/proc/").append(i).append("/cmdline").toString());
            try {
                str = bufferedReader.readLine().trim();
                IOUtils.closeQuietly((Closeable) bufferedReader);
            } catch (IOException e) {
                IOUtils.closeQuietly((Closeable) bufferedReader);
                return str;
            } catch (Throwable th2) {
                th = th2;
                IOUtils.closeQuietly((Closeable) bufferedReader);
                throw th;
            }
        } catch (IOException e2) {
            bufferedReader = null;
            IOUtils.closeQuietly((Closeable) bufferedReader);
            return str;
        } catch (Throwable th3) {
            th = th3;
            bufferedReader = null;
            IOUtils.closeQuietly((Closeable) bufferedReader);
            throw th;
        }
        return str;
    }

    private static BufferedReader zzk(String str) throws IOException {
        StrictMode.ThreadPolicy allowThreadDiskReads = StrictMode.allowThreadDiskReads();
        try {
            return new BufferedReader(new FileReader(str));
        } finally {
            StrictMode.setThreadPolicy(allowThreadDiskReads);
        }
    }
}
