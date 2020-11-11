package com.facebook.imagepipeline.transcoder;

import java.util.Locale;

public class ImageTranscodeResult {
    private final int mTranscodeStatus;

    public ImageTranscodeResult(int transcodeStatus) {
        this.mTranscodeStatus = transcodeStatus;
    }

    public int getTranscodeStatus() {
        return this.mTranscodeStatus;
    }

    public String toString() {
        return String.format((Locale) null, "Status: %d", new Object[]{Integer.valueOf(this.mTranscodeStatus)});
    }
}
