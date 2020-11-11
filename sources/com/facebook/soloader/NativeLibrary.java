package com.facebook.soloader;

import android.util.Log;
import java.util.List;
import javax.annotation.Nullable;

public abstract class NativeLibrary {
    private static final String TAG = NativeLibrary.class.getName();
    private boolean mLibrariesLoaded = false;
    @Nullable
    private List<String> mLibraryNames;
    @Nullable
    private volatile UnsatisfiedLinkError mLinkError = null;
    private Boolean mLoadLibraries = true;
    private final Object mLock = new Object();

    protected NativeLibrary(List<String> libraryNames) {
        this.mLibraryNames = libraryNames;
    }

    @Nullable
    public boolean loadLibraries() {
        synchronized (this.mLock) {
            if (!this.mLoadLibraries.booleanValue()) {
                boolean z = this.mLibrariesLoaded;
                return z;
            }
            try {
                List<String> list = this.mLibraryNames;
                if (list != null) {
                    for (String name : list) {
                        SoLoader.loadLibrary(name);
                    }
                }
                initialNativeCheck();
                this.mLibrariesLoaded = true;
                this.mLibraryNames = null;
            } catch (UnsatisfiedLinkError error) {
                Log.e(TAG, "Failed to load native lib (initial check): ", error);
                this.mLinkError = error;
                this.mLibrariesLoaded = false;
            } catch (Throwable other) {
                Log.e(TAG, "Failed to load native lib (other error): ", other);
                this.mLinkError = new UnsatisfiedLinkError("Failed loading libraries");
                this.mLinkError.initCause(other);
                this.mLibrariesLoaded = false;
            }
            this.mLoadLibraries = false;
            boolean z2 = this.mLibrariesLoaded;
            return z2;
        }
    }

    public void ensureLoaded() throws UnsatisfiedLinkError {
        if (!loadLibraries()) {
            throw this.mLinkError;
        }
    }

    /* access modifiers changed from: protected */
    public void initialNativeCheck() throws UnsatisfiedLinkError {
    }

    @Nullable
    public UnsatisfiedLinkError getError() {
        return this.mLinkError;
    }
}
