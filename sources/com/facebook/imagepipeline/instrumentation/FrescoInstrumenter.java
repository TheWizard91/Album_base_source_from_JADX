package com.facebook.imagepipeline.instrumentation;

public final class FrescoInstrumenter {
    private static volatile Instrumenter sInstance;

    public interface Instrumenter {
        Runnable decorateRunnable(Runnable runnable, String str);

        boolean isTracing();

        Object onBeforeSubmitWork(String str);

        Object onBeginWork(Object obj, String str);

        void onEndWork(Object obj);
    }

    public static void provide(Instrumenter instrumenter) {
        sInstance = instrumenter;
    }

    public static boolean isTracing() {
        Instrumenter instrumenter = sInstance;
        if (instrumenter == null) {
            return false;
        }
        return instrumenter.isTracing();
    }

    public static Object onBeforeSubmitWork(String tag) {
        Instrumenter instrumenter = sInstance;
        if (instrumenter == null || tag == null) {
            return null;
        }
        return instrumenter.onBeforeSubmitWork(tag);
    }

    public static Object onBeginWork(Object token, String tag) {
        Instrumenter instrumenter = sInstance;
        if (instrumenter == null || token == null) {
            return null;
        }
        return instrumenter.onBeginWork(token, tag);
    }

    public static void onEndWork(Object token) {
        Instrumenter instrumenter = sInstance;
        if (instrumenter != null && token != null) {
            instrumenter.onEndWork(token);
        }
    }

    public static Runnable decorateRunnable(Runnable runnable, String tag) {
        Instrumenter instrumenter = sInstance;
        if (instrumenter == null || runnable == null || tag == null) {
            return runnable;
        }
        return instrumenter.decorateRunnable(runnable, tag);
    }
}
