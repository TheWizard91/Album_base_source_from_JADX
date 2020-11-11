package com.facebook.soloader;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.StrictMode;
import android.util.Log;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import javax.annotation.Nullable;

public class ApplicationSoSource extends SoSource {
    private Context applicationContext;
    private int flags;
    private DirectorySoSource soSource;

    public ApplicationSoSource(Context context, int flags2) {
        Context applicationContext2 = context.getApplicationContext();
        this.applicationContext = applicationContext2;
        if (applicationContext2 == null) {
            Log.w("SoLoader", "context.getApplicationContext returned null, holding reference to original context.");
            this.applicationContext = context;
        }
        this.flags = flags2;
        this.soSource = new DirectorySoSource(new File(this.applicationContext.getApplicationInfo().nativeLibraryDir), flags2);
    }

    public boolean checkAndMaybeUpdate() throws IOException {
        try {
            File nativeLibDir = this.soSource.soDirectory;
            Context context = this.applicationContext;
            Context updatedContext = context.createPackageContext(context.getPackageName(), 0);
            File updatedNativeLibDir = new File(updatedContext.getApplicationInfo().nativeLibraryDir);
            if (nativeLibDir.equals(updatedNativeLibDir)) {
                return false;
            }
            Log.d("SoLoader", "Native library directory updated from " + nativeLibDir + " to " + updatedNativeLibDir);
            this.flags |= 1;
            DirectorySoSource directorySoSource = new DirectorySoSource(updatedNativeLibDir, this.flags);
            this.soSource = directorySoSource;
            directorySoSource.prepare(this.flags);
            this.applicationContext = updatedContext;
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public int loadLibrary(String soName, int loadFlags, StrictMode.ThreadPolicy threadPolicy) throws IOException {
        return this.soSource.loadLibrary(soName, loadFlags, threadPolicy);
    }

    @Nullable
    public String getLibraryPath(String soName) throws IOException {
        return this.soSource.getLibraryPath(soName);
    }

    @Nullable
    public String[] getLibraryDependencies(String soName) throws IOException {
        return this.soSource.getLibraryDependencies(soName);
    }

    @Nullable
    public File unpackLibrary(String soName) throws IOException {
        return this.soSource.unpackLibrary(soName);
    }

    /* access modifiers changed from: protected */
    public void prepare(int flags2) throws IOException {
        this.soSource.prepare(flags2);
    }

    public void addToLdLibraryPath(Collection<String> paths) {
        this.soSource.addToLdLibraryPath(paths);
    }

    public String toString() {
        return this.soSource.toString();
    }
}
