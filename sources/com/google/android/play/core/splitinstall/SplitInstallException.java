package com.google.android.play.core.splitinstall;

import com.google.android.play.core.splitinstall.model.C3138a;
import com.google.android.play.core.tasks.C3170j;

public class SplitInstallException extends C3170j {

    /* renamed from: a */
    private final int f1386a;

    public SplitInstallException(int i) {
        super(String.format("Split Install Error(%d): %s", new Object[]{Integer.valueOf(i), C3138a.m1016a(i)}));
        if (i != 0) {
            this.f1386a = i;
            return;
        }
        throw new IllegalArgumentException("errorCode should not be 0.");
    }

    public int getErrorCode() {
        return this.f1386a;
    }
}
