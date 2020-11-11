package com.facebook.soloader;

import com.facebook.soloader.nativeloader.NativeLoaderDelegate;

public class NativeLoaderToSoLoaderDelegate implements NativeLoaderDelegate {
    public boolean loadLibrary(String shortName) {
        return SoLoader.loadLibrary(shortName);
    }
}
