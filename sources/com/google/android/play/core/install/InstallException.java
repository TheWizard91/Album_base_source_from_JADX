package com.google.android.play.core.install;

import com.google.android.play.core.install.model.C2987a;
import com.google.android.play.core.tasks.C3170j;

public class InstallException extends C3170j {

    /* renamed from: a */
    private final int f1266a;

    public InstallException(int i) {
        super(String.format("Install Error(%d): %s", new Object[]{Integer.valueOf(i), C2987a.m613a(i)}));
        if (i != 0) {
            this.f1266a = i;
            return;
        }
        throw new IllegalArgumentException("errorCode should not be 0.");
    }

    public int getErrorCode() {
        return this.f1266a;
    }
}
