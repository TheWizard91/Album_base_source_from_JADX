package com.facebook.imagepipeline.systrace;

import javax.annotation.Nullable;

public class FrescoSystrace {
    public static final ArgsBuilder NO_OP_ARGS_BUILDER = new NoOpArgsBuilder();
    @Nullable
    private static volatile Systrace sInstance = null;

    public interface ArgsBuilder {
        ArgsBuilder arg(String str, double d);

        ArgsBuilder arg(String str, int i);

        ArgsBuilder arg(String str, long j);

        ArgsBuilder arg(String str, Object obj);

        void flush();
    }

    public interface Systrace {
        void beginSection(String str);

        ArgsBuilder beginSectionWithArgs(String str);

        void endSection();

        boolean isTracing();
    }

    private FrescoSystrace() {
    }

    public static void provide(Systrace instance) {
        sInstance = instance;
    }

    public static void beginSection(String name) {
        getInstance().beginSection(name);
    }

    public static ArgsBuilder beginSectionWithArgs(String name) {
        return getInstance().beginSectionWithArgs(name);
    }

    public static void endSection() {
        getInstance().endSection();
    }

    public static boolean isTracing() {
        return getInstance().isTracing();
    }

    private static Systrace getInstance() {
        if (sInstance == null) {
            synchronized (FrescoSystrace.class) {
                if (sInstance == null) {
                    sInstance = new DefaultFrescoSystrace();
                }
            }
        }
        return sInstance;
    }

    private static final class NoOpArgsBuilder implements ArgsBuilder {
        private NoOpArgsBuilder() {
        }

        public void flush() {
        }

        public ArgsBuilder arg(String key, Object value) {
            return this;
        }

        public ArgsBuilder arg(String key, int value) {
            return this;
        }

        public ArgsBuilder arg(String key, long value) {
            return this;
        }

        public ArgsBuilder arg(String key, double value) {
            return this;
        }
    }
}
