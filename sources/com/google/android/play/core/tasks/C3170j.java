package com.google.android.play.core.tasks;

/* renamed from: com.google.android.play.core.tasks.j */
public abstract class C3170j extends RuntimeException {
    public C3170j(String str) {
        super(str);
    }

    public C3170j(Throwable th) {
        super(th);
    }

    public abstract int getErrorCode();
}
