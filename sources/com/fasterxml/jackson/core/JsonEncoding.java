package com.fasterxml.jackson.core;

import com.bumptech.glide.load.Key;

public enum JsonEncoding {
    UTF8(Key.STRING_CHARSET_NAME, false),
    UTF16_BE("UTF-16BE", true),
    UTF16_LE("UTF-16LE", false),
    UTF32_BE("UTF-32BE", true),
    UTF32_LE("UTF-32LE", false);
    
    protected final boolean _bigEndian;
    protected final String _javaName;

    private JsonEncoding(String str, boolean z) {
        this._javaName = str;
        this._bigEndian = z;
    }

    public String getJavaName() {
        return this._javaName;
    }

    public boolean isBigEndian() {
        return this._bigEndian;
    }
}
