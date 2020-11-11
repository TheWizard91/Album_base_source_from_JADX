package com.facebook.imagepipeline.core;

import android.os.Process;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

public class PriorityThreadFactory implements ThreadFactory {
    private final boolean mAddThreadNumber;
    private final String mPrefix;
    private final AtomicInteger mThreadNumber;
    /* access modifiers changed from: private */
    public final int mThreadPriority;

    public PriorityThreadFactory(int threadPriority) {
        this(threadPriority, "PriorityThreadFactory", true);
    }

    public PriorityThreadFactory(int threadPriority, String prefix, boolean addThreadNumber) {
        this.mThreadNumber = new AtomicInteger(1);
        this.mThreadPriority = threadPriority;
        this.mPrefix = prefix;
        this.mAddThreadNumber = addThreadNumber;
    }

    public Thread newThread(final Runnable runnable) {
        String name;
        Runnable wrapperRunnable = new Runnable() {
            public void run() {
                try {
                    Process.setThreadPriority(PriorityThreadFactory.this.mThreadPriority);
                } catch (Throwable th) {
                }
                runnable.run();
            }
        };
        if (this.mAddThreadNumber) {
            name = this.mPrefix + "-" + this.mThreadNumber.getAndIncrement();
        } else {
            name = this.mPrefix;
        }
        return new Thread(wrapperRunnable, name);
    }
}
