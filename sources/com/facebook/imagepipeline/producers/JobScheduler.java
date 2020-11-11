package com.facebook.imagepipeline.producers;

import android.os.SystemClock;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.instrumentation.FrescoInstrumenter;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class JobScheduler {
    static final String QUEUE_TIME_KEY = "queueTime";
    private final Runnable mDoJobRunnable = new Runnable() {
        public void run() {
            JobScheduler.this.doJob();
        }
    };
    EncodedImage mEncodedImage = null;
    private final Executor mExecutor;
    private final JobRunnable mJobRunnable;
    long mJobStartTime = 0;
    JobState mJobState = JobState.IDLE;
    long mJobSubmitTime = 0;
    private final int mMinimumJobIntervalMs;
    int mStatus = 0;
    private final Runnable mSubmitJobRunnable = new Runnable() {
        public void run() {
            JobScheduler.this.submitJob();
        }
    };

    public interface JobRunnable {
        void run(EncodedImage encodedImage, int i);
    }

    enum JobState {
        IDLE,
        QUEUED,
        RUNNING,
        RUNNING_AND_PENDING
    }

    static class JobStartExecutorSupplier {
        private static ScheduledExecutorService sJobStarterExecutor;

        JobStartExecutorSupplier() {
        }

        static ScheduledExecutorService get() {
            if (sJobStarterExecutor == null) {
                sJobStarterExecutor = Executors.newSingleThreadScheduledExecutor();
            }
            return sJobStarterExecutor;
        }
    }

    public JobScheduler(Executor executor, JobRunnable jobRunnable, int minimumJobIntervalMs) {
        this.mExecutor = executor;
        this.mJobRunnable = jobRunnable;
        this.mMinimumJobIntervalMs = minimumJobIntervalMs;
    }

    public void clearJob() {
        EncodedImage oldEncodedImage;
        synchronized (this) {
            oldEncodedImage = this.mEncodedImage;
            this.mEncodedImage = null;
            this.mStatus = 0;
        }
        EncodedImage.closeSafely(oldEncodedImage);
    }

    public boolean updateJob(EncodedImage encodedImage, int status) {
        EncodedImage oldEncodedImage;
        if (!shouldProcess(encodedImage, status)) {
            return false;
        }
        synchronized (this) {
            oldEncodedImage = this.mEncodedImage;
            this.mEncodedImage = EncodedImage.cloneOrNull(encodedImage);
            this.mStatus = status;
        }
        EncodedImage.closeSafely(oldEncodedImage);
        return true;
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x003f, code lost:
        if (r4 == false) goto L_0x0046;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:17:0x0041, code lost:
        enqueueJob(r2 - r0);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0046, code lost:
        return true;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean scheduleJob() {
        /*
            r11 = this;
            long r0 = android.os.SystemClock.uptimeMillis()
            r2 = 0
            r4 = 0
            monitor-enter(r11)
            com.facebook.imagepipeline.image.EncodedImage r5 = r11.mEncodedImage     // Catch:{ all -> 0x0047 }
            int r6 = r11.mStatus     // Catch:{ all -> 0x0047 }
            boolean r5 = shouldProcess(r5, r6)     // Catch:{ all -> 0x0047 }
            if (r5 != 0) goto L_0x0015
            r5 = 0
            monitor-exit(r11)     // Catch:{ all -> 0x0047 }
            return r5
        L_0x0015:
            int[] r5 = com.facebook.imagepipeline.producers.JobScheduler.C07513.f85xca5c4655     // Catch:{ all -> 0x0047 }
            com.facebook.imagepipeline.producers.JobScheduler$JobState r6 = r11.mJobState     // Catch:{ all -> 0x0047 }
            int r6 = r6.ordinal()     // Catch:{ all -> 0x0047 }
            r5 = r5[r6]     // Catch:{ all -> 0x0047 }
            r6 = 1
            if (r5 == r6) goto L_0x002b
            r7 = 3
            if (r5 == r7) goto L_0x0026
            goto L_0x003e
        L_0x0026:
            com.facebook.imagepipeline.producers.JobScheduler$JobState r5 = com.facebook.imagepipeline.producers.JobScheduler.JobState.RUNNING_AND_PENDING     // Catch:{ all -> 0x0047 }
            r11.mJobState = r5     // Catch:{ all -> 0x0047 }
            goto L_0x003e
        L_0x002b:
            long r7 = r11.mJobStartTime     // Catch:{ all -> 0x0047 }
            int r5 = r11.mMinimumJobIntervalMs     // Catch:{ all -> 0x0047 }
            long r9 = (long) r5     // Catch:{ all -> 0x0047 }
            long r7 = r7 + r9
            long r7 = java.lang.Math.max(r7, r0)     // Catch:{ all -> 0x0047 }
            r2 = r7
            r4 = 1
            r11.mJobSubmitTime = r0     // Catch:{ all -> 0x0047 }
            com.facebook.imagepipeline.producers.JobScheduler$JobState r5 = com.facebook.imagepipeline.producers.JobScheduler.JobState.QUEUED     // Catch:{ all -> 0x0047 }
            r11.mJobState = r5     // Catch:{ all -> 0x0047 }
        L_0x003e:
            monitor-exit(r11)     // Catch:{ all -> 0x0047 }
            if (r4 == 0) goto L_0x0046
            long r7 = r2 - r0
            r11.enqueueJob(r7)
        L_0x0046:
            return r6
        L_0x0047:
            r5 = move-exception
            monitor-exit(r11)     // Catch:{ all -> 0x0047 }
            throw r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.imagepipeline.producers.JobScheduler.scheduleJob():boolean");
    }

    /* renamed from: com.facebook.imagepipeline.producers.JobScheduler$3 */
    static /* synthetic */ class C07513 {

        /* renamed from: $SwitchMap$com$facebook$imagepipeline$producers$JobScheduler$JobState */
        static final /* synthetic */ int[] f85xca5c4655;

        static {
            int[] iArr = new int[JobState.values().length];
            f85xca5c4655 = iArr;
            try {
                iArr[JobState.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f85xca5c4655[JobState.QUEUED.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f85xca5c4655[JobState.RUNNING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f85xca5c4655[JobState.RUNNING_AND_PENDING.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    private void enqueueJob(long delay) {
        Runnable submitJobRunnable = FrescoInstrumenter.decorateRunnable(this.mSubmitJobRunnable, "JobScheduler_enqueueJob");
        if (delay > 0) {
            JobStartExecutorSupplier.get().schedule(submitJobRunnable, delay, TimeUnit.MILLISECONDS);
        } else {
            submitJobRunnable.run();
        }
    }

    /* access modifiers changed from: private */
    public void submitJob() {
        this.mExecutor.execute(FrescoInstrumenter.decorateRunnable(this.mDoJobRunnable, "JobScheduler_submitJob"));
    }

    /* access modifiers changed from: private */
    public void doJob() {
        EncodedImage input;
        int status;
        long now = SystemClock.uptimeMillis();
        synchronized (this) {
            input = this.mEncodedImage;
            status = this.mStatus;
            this.mEncodedImage = null;
            this.mStatus = 0;
            this.mJobState = JobState.RUNNING;
            this.mJobStartTime = now;
        }
        try {
            if (shouldProcess(input, status)) {
                this.mJobRunnable.run(input, status);
            }
        } finally {
            EncodedImage.closeSafely(input);
            onJobFinished();
        }
    }

    private void onJobFinished() {
        long now = SystemClock.uptimeMillis();
        long when = 0;
        boolean shouldEnqueue = false;
        synchronized (this) {
            if (this.mJobState == JobState.RUNNING_AND_PENDING) {
                when = Math.max(this.mJobStartTime + ((long) this.mMinimumJobIntervalMs), now);
                shouldEnqueue = true;
                this.mJobSubmitTime = now;
                this.mJobState = JobState.QUEUED;
            } else {
                this.mJobState = JobState.IDLE;
            }
        }
        if (shouldEnqueue) {
            enqueueJob(when - now);
        }
    }

    private static boolean shouldProcess(EncodedImage encodedImage, int status) {
        return BaseConsumer.isLast(status) || BaseConsumer.statusHasFlag(status, 4) || EncodedImage.isValid(encodedImage);
    }

    public synchronized long getQueuedTime() {
        return this.mJobStartTime - this.mJobSubmitTime;
    }
}
