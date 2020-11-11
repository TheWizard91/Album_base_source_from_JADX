package com.facebook.soloader.nativeloader;

public class SystemDelegate implements NativeLoaderDelegate {
    public boolean loadLibrary(String shortName) {
        System.loadLibrary(shortName);
        return true;
    }
}
