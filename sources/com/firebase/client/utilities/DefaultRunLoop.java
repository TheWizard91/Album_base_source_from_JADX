package com.firebase.client.utilities;

import com.firebase.client.RunLoop;
import com.firebase.client.core.ThreadInitializer;
import java.lang.Thread;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public abstract class DefaultRunLoop implements RunLoop {
    private ScheduledThreadPoolExecutor executor;

    public abstract void handleException(Throwable th);

    private class FirebaseThreadFactory implements ThreadFactory {
        private FirebaseThreadFactory() {
        }

        public Thread newThread(Runnable r) {
            Thread thread = DefaultRunLoop.this.getThreadFactory().newThread(r);
            ThreadInitializer initializer = DefaultRunLoop.this.getThreadInitializer();
            initializer.setName(thread, "FirebaseWorker");
            initializer.setDaemon(thread, true);
            initializer.setUncaughtExceptionHandler(thread, new Thread.UncaughtExceptionHandler() {
                public void uncaughtException(Thread t, Throwable e) {
                    DefaultRunLoop.this.handleException(e);
                }
            });
            return thread;
        }
    }

    /* access modifiers changed from: protected */
    public ThreadFactory getThreadFactory() {
        return Executors.defaultThreadFactory();
    }

    /* access modifiers changed from: protected */
    public ThreadInitializer getThreadInitializer() {
        return ThreadInitializer.defaultInstance;
    }

    public DefaultRunLoop() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = new ScheduledThreadPoolExecutor(1, new FirebaseThreadFactory());
        this.executor = scheduledThreadPoolExecutor;
        scheduledThreadPoolExecutor.setKeepAliveTime(3, TimeUnit.SECONDS);
    }

    public void scheduleNow(final Runnable runnable) {
        this.executor.execute(new Runnable() {
            public void run() {
                try {
                    runnable.run();
                } catch (Throwable e) {
                    DefaultRunLoop.this.handleException(e);
                }
            }
        });
    }

    public ScheduledFuture schedule(final Runnable runnable, long milliseconds) {
        return this.executor.schedule(new Runnable() {
            public void run() {
                try {
                    runnable.run();
                } catch (Throwable e) {
                    DefaultRunLoop.this.handleException(e);
                }
            }
        }, milliseconds, TimeUnit.MILLISECONDS);
    }

    public void shutdown() {
        this.executor.setCorePoolSize(0);
    }

    public void restart() {
        this.executor.setCorePoolSize(1);
    }
}
