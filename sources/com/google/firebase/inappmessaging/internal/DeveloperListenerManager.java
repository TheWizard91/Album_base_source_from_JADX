package com.google.firebase.inappmessaging.internal;

import com.google.firebase.inappmessaging.FirebaseInAppMessagingClickListener;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayCallbacks;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingDisplayErrorListener;
import com.google.firebase.inappmessaging.FirebaseInAppMessagingImpressionListener;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.InAppMessage;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class DeveloperListenerManager {
    private static final ThreadPoolExecutor CALLBACK_QUEUE_EXECUTOR;
    private static final int KEEP_ALIVE_TIME_SECONDS = 15;
    private static final int POOL_SIZE = 1;
    public static DeveloperListenerManager instance = new DeveloperListenerManager();
    private static BlockingQueue<Runnable> mCallbackQueue = new LinkedBlockingQueue();
    private Map<FirebaseInAppMessagingClickListener, ClicksExecutorAndListener> registeredClickListeners = new HashMap();
    private Map<FirebaseInAppMessagingDisplayErrorListener, ErrorsExecutorAndListener> registeredErrorListeners = new HashMap();
    private Map<FirebaseInAppMessagingImpressionListener, ImpressionExecutorAndListener> registeredImpressionListeners = new HashMap();

    static {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(1, 1, 15, TimeUnit.SECONDS, mCallbackQueue, new FIAMThreadFactory("EventListeners-"));
        CALLBACK_QUEUE_EXECUTOR = threadPoolExecutor;
        threadPoolExecutor.allowCoreThreadTimeOut(true);
    }

    public void impressionDetected(InAppMessage inAppMessage) {
        for (ImpressionExecutorAndListener listener : this.registeredImpressionListeners.values()) {
            listener.withExecutor(CALLBACK_QUEUE_EXECUTOR).execute(DeveloperListenerManager$$Lambda$1.lambdaFactory$(listener, inAppMessage));
        }
    }

    public void displayErrorEncountered(InAppMessage inAppMessage, FirebaseInAppMessagingDisplayCallbacks.InAppMessagingErrorReason errorReason) {
        for (ErrorsExecutorAndListener listener : this.registeredErrorListeners.values()) {
            listener.withExecutor(CALLBACK_QUEUE_EXECUTOR).execute(DeveloperListenerManager$$Lambda$2.lambdaFactory$(listener, inAppMessage, errorReason));
        }
    }

    public void messageClicked(InAppMessage inAppMessage, Action action) {
        for (ClicksExecutorAndListener listener : this.registeredClickListeners.values()) {
            listener.withExecutor(CALLBACK_QUEUE_EXECUTOR).execute(DeveloperListenerManager$$Lambda$3.lambdaFactory$(listener, inAppMessage, action));
        }
    }

    public void addImpressionListener(FirebaseInAppMessagingImpressionListener impressionListener) {
        this.registeredImpressionListeners.put(impressionListener, new ImpressionExecutorAndListener(impressionListener));
    }

    public void addClickListener(FirebaseInAppMessagingClickListener clickListener) {
        this.registeredClickListeners.put(clickListener, new ClicksExecutorAndListener(clickListener));
    }

    public void addDisplayErrorListener(FirebaseInAppMessagingDisplayErrorListener displayErrorListener) {
        this.registeredErrorListeners.put(displayErrorListener, new ErrorsExecutorAndListener(displayErrorListener));
    }

    public void addImpressionListener(FirebaseInAppMessagingImpressionListener impressionListener, Executor executor) {
        this.registeredImpressionListeners.put(impressionListener, new ImpressionExecutorAndListener(impressionListener, executor));
    }

    public void addClickListener(FirebaseInAppMessagingClickListener clickListener, Executor executor) {
        this.registeredClickListeners.put(clickListener, new ClicksExecutorAndListener(clickListener, executor));
    }

    public void addDisplayErrorListener(FirebaseInAppMessagingDisplayErrorListener displayErrorListener, Executor executor) {
        this.registeredErrorListeners.put(displayErrorListener, new ErrorsExecutorAndListener(displayErrorListener, executor));
    }

    public void removeImpressionListener(FirebaseInAppMessagingImpressionListener impressionListener) {
        this.registeredImpressionListeners.remove(impressionListener);
    }

    public void removeClickListener(FirebaseInAppMessagingClickListener clickListener) {
        this.registeredClickListeners.remove(clickListener);
    }

    public void removeDisplayErrorListener(FirebaseInAppMessagingDisplayErrorListener displayErrorListener) {
        this.registeredErrorListeners.remove(displayErrorListener);
    }

    public void removeAllListeners() {
        this.registeredClickListeners.clear();
        this.registeredImpressionListeners.clear();
        this.registeredErrorListeners.clear();
    }

    static class FIAMThreadFactory implements ThreadFactory {
        private final String mNameSuffix;
        private final AtomicInteger threadNumber = new AtomicInteger(1);

        FIAMThreadFactory(String suffix) {
            this.mNameSuffix = suffix;
        }

        public Thread newThread(Runnable r) {
            Thread t = new Thread(r, "FIAM-" + this.mNameSuffix + this.threadNumber.getAndIncrement());
            t.setDaemon(false);
            t.setPriority(9);
            return t;
        }
    }

    private static abstract class ExecutorAndListener<T> {
        private final Executor executor;

        public abstract T getListener();

        public Executor withExecutor(Executor defaultExecutor) {
            Executor executor2 = this.executor;
            if (executor2 == null) {
                return defaultExecutor;
            }
            return executor2;
        }

        public ExecutorAndListener(Executor e) {
            this.executor = e;
        }
    }

    private static class ImpressionExecutorAndListener extends ExecutorAndListener<FirebaseInAppMessagingImpressionListener> {
        FirebaseInAppMessagingImpressionListener listener;

        public ImpressionExecutorAndListener(FirebaseInAppMessagingImpressionListener listener2, Executor e) {
            super(e);
            this.listener = listener2;
        }

        public ImpressionExecutorAndListener(FirebaseInAppMessagingImpressionListener listener2) {
            super((Executor) null);
            this.listener = listener2;
        }

        public FirebaseInAppMessagingImpressionListener getListener() {
            return this.listener;
        }
    }

    private static class ClicksExecutorAndListener extends ExecutorAndListener<FirebaseInAppMessagingClickListener> {
        FirebaseInAppMessagingClickListener listener;

        public ClicksExecutorAndListener(FirebaseInAppMessagingClickListener listener2, Executor e) {
            super(e);
            this.listener = listener2;
        }

        public ClicksExecutorAndListener(FirebaseInAppMessagingClickListener listener2) {
            super((Executor) null);
            this.listener = listener2;
        }

        public FirebaseInAppMessagingClickListener getListener() {
            return this.listener;
        }
    }

    private static class ErrorsExecutorAndListener extends ExecutorAndListener<FirebaseInAppMessagingDisplayErrorListener> {
        FirebaseInAppMessagingDisplayErrorListener listener;

        public ErrorsExecutorAndListener(FirebaseInAppMessagingDisplayErrorListener listener2, Executor e) {
            super(e);
            this.listener = listener2;
        }

        public ErrorsExecutorAndListener(FirebaseInAppMessagingDisplayErrorListener listener2) {
            super((Executor) null);
            this.listener = listener2;
        }

        public FirebaseInAppMessagingDisplayErrorListener getListener() {
            return this.listener;
        }
    }
}
