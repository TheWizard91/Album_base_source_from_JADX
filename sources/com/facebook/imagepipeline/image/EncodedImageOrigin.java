package com.facebook.imagepipeline.image;

public enum EncodedImageOrigin {
    NOT_SET("not_set"),
    NETWORK("network"),
    DISK("disk"),
    ENCODED_MEM_CACHE("encoded_mem_cache");
    
    private final String mOrigin;

    private EncodedImageOrigin(String origin) {
        this.mOrigin = origin;
    }

    public String toString() {
        return this.mOrigin;
    }
}
