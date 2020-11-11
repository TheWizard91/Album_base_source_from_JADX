package com.facebook.drawee.backends.pipeline;

import android.content.Context;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.systrace.FrescoSystrace;
import javax.annotation.Nullable;

public class Fresco {
    private static final Class<?> TAG = Fresco.class;
    private static PipelineDraweeControllerBuilderSupplier sDraweeControllerBuilderSupplier;
    private static volatile boolean sIsInitialized = false;

    private Fresco() {
    }

    public static void initialize(Context context) {
        initialize(context, (ImagePipelineConfig) null, (DraweeConfig) null);
    }

    public static void initialize(Context context, @Nullable ImagePipelineConfig imagePipelineConfig) {
        initialize(context, imagePipelineConfig, (DraweeConfig) null);
    }

    public static void initialize(Context context, @Nullable ImagePipelineConfig imagePipelineConfig, @Nullable DraweeConfig draweeConfig) {
        initialize(context, imagePipelineConfig, draweeConfig, true);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:16:0x004e, code lost:
        if (com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing() != false) goto L_0x0092;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:22:0x0060, code lost:
        if (com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing() == false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:27:0x0070, code lost:
        if (com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing() == false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:32:0x0080, code lost:
        if (com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing() == false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:37:0x0090, code lost:
        if (com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing() == false) goto L_0x00a0;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:38:0x0092, code lost:
        com.facebook.imagepipeline.systrace.FrescoSystrace.endSection();
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void initialize(android.content.Context r6, @javax.annotation.Nullable com.facebook.imagepipeline.core.ImagePipelineConfig r7, @javax.annotation.Nullable com.facebook.drawee.backends.pipeline.DraweeConfig r8, boolean r9) {
        /*
            boolean r0 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r0 == 0) goto L_0x000b
            java.lang.String r0 = "Fresco#initialize"
            com.facebook.imagepipeline.systrace.FrescoSystrace.beginSection(r0)
        L_0x000b:
            boolean r0 = sIsInitialized
            r1 = 1
            if (r0 == 0) goto L_0x0018
            java.lang.Class<?> r0 = TAG
            java.lang.String r2 = "Fresco has already been initialized! `Fresco.initialize(...)` should only be called 1 single time to avoid memory leaks!"
            com.facebook.common.logging.FLog.m96w((java.lang.Class<?>) r0, (java.lang.String) r2)
            goto L_0x001a
        L_0x0018:
            sIsInitialized = r1
        L_0x001a:
            com.facebook.imagepipeline.core.NativeCodeSetup.setUseNativeCode(r9)
            boolean r0 = com.facebook.soloader.nativeloader.NativeLoader.isInitialized()
            if (r0 != 0) goto L_0x00a0
            boolean r0 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r0 == 0) goto L_0x002e
            java.lang.String r0 = "Fresco.initialize->SoLoader.init"
            com.facebook.imagepipeline.systrace.FrescoSystrace.beginSection(r0)
        L_0x002e:
            java.lang.String r0 = "com.facebook.imagepipeline.nativecode.NativeCodeInitializer"
            java.lang.Class r0 = java.lang.Class.forName(r0)     // Catch:{ ClassNotFoundException -> 0x0083, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x0063, NoSuchMethodException -> 0x0053 }
            java.lang.String r2 = "init"
            java.lang.Class[] r3 = new java.lang.Class[r1]     // Catch:{ ClassNotFoundException -> 0x0083, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x0063, NoSuchMethodException -> 0x0053 }
            java.lang.Class<android.content.Context> r4 = android.content.Context.class
            r5 = 0
            r3[r5] = r4     // Catch:{ ClassNotFoundException -> 0x0083, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x0063, NoSuchMethodException -> 0x0053 }
            java.lang.reflect.Method r2 = r0.getMethod(r2, r3)     // Catch:{ ClassNotFoundException -> 0x0083, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x0063, NoSuchMethodException -> 0x0053 }
            r3 = 0
            java.lang.Object[] r1 = new java.lang.Object[r1]     // Catch:{ ClassNotFoundException -> 0x0083, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x0063, NoSuchMethodException -> 0x0053 }
            r1[r5] = r6     // Catch:{ ClassNotFoundException -> 0x0083, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x0063, NoSuchMethodException -> 0x0053 }
            r2.invoke(r3, r1)     // Catch:{ ClassNotFoundException -> 0x0083, IllegalAccessException -> 0x0073, InvocationTargetException -> 0x0063, NoSuchMethodException -> 0x0053 }
            boolean r0 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r0 == 0) goto L_0x00a0
            goto L_0x0092
        L_0x0051:
            r0 = move-exception
            goto L_0x0096
        L_0x0053:
            r0 = move-exception
            com.facebook.soloader.nativeloader.SystemDelegate r1 = new com.facebook.soloader.nativeloader.SystemDelegate     // Catch:{ all -> 0x0051 }
            r1.<init>()     // Catch:{ all -> 0x0051 }
            com.facebook.soloader.nativeloader.NativeLoader.init(r1)     // Catch:{ all -> 0x0051 }
            boolean r0 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r0 == 0) goto L_0x00a0
            goto L_0x0082
        L_0x0063:
            r0 = move-exception
            com.facebook.soloader.nativeloader.SystemDelegate r1 = new com.facebook.soloader.nativeloader.SystemDelegate     // Catch:{ all -> 0x0051 }
            r1.<init>()     // Catch:{ all -> 0x0051 }
            com.facebook.soloader.nativeloader.NativeLoader.init(r1)     // Catch:{ all -> 0x0051 }
            boolean r0 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r0 == 0) goto L_0x00a0
            goto L_0x0082
        L_0x0073:
            r0 = move-exception
            com.facebook.soloader.nativeloader.SystemDelegate r1 = new com.facebook.soloader.nativeloader.SystemDelegate     // Catch:{ all -> 0x0051 }
            r1.<init>()     // Catch:{ all -> 0x0051 }
            com.facebook.soloader.nativeloader.NativeLoader.init(r1)     // Catch:{ all -> 0x0051 }
            boolean r0 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r0 == 0) goto L_0x00a0
        L_0x0082:
            goto L_0x0092
        L_0x0083:
            r0 = move-exception
            com.facebook.soloader.nativeloader.SystemDelegate r1 = new com.facebook.soloader.nativeloader.SystemDelegate     // Catch:{ all -> 0x0051 }
            r1.<init>()     // Catch:{ all -> 0x0051 }
            com.facebook.soloader.nativeloader.NativeLoader.init(r1)     // Catch:{ all -> 0x0051 }
            boolean r0 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r0 == 0) goto L_0x00a0
        L_0x0092:
            com.facebook.imagepipeline.systrace.FrescoSystrace.endSection()
            goto L_0x00a0
        L_0x0096:
            boolean r1 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r1 == 0) goto L_0x009f
            com.facebook.imagepipeline.systrace.FrescoSystrace.endSection()
        L_0x009f:
            throw r0
        L_0x00a0:
            android.content.Context r6 = r6.getApplicationContext()
            if (r7 != 0) goto L_0x00aa
            com.facebook.imagepipeline.core.ImagePipelineFactory.initialize((android.content.Context) r6)
            goto L_0x00ad
        L_0x00aa:
            com.facebook.imagepipeline.core.ImagePipelineFactory.initialize((com.facebook.imagepipeline.core.ImagePipelineConfig) r7)
        L_0x00ad:
            initializeDrawee(r6, r8)
            boolean r0 = com.facebook.imagepipeline.systrace.FrescoSystrace.isTracing()
            if (r0 == 0) goto L_0x00b9
            com.facebook.imagepipeline.systrace.FrescoSystrace.endSection()
        L_0x00b9:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.drawee.backends.pipeline.Fresco.initialize(android.content.Context, com.facebook.imagepipeline.core.ImagePipelineConfig, com.facebook.drawee.backends.pipeline.DraweeConfig, boolean):void");
    }

    private static void initializeDrawee(Context context, @Nullable DraweeConfig draweeConfig) {
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.beginSection("Fresco.initializeDrawee");
        }
        PipelineDraweeControllerBuilderSupplier pipelineDraweeControllerBuilderSupplier = new PipelineDraweeControllerBuilderSupplier(context, draweeConfig);
        sDraweeControllerBuilderSupplier = pipelineDraweeControllerBuilderSupplier;
        SimpleDraweeView.initialize(pipelineDraweeControllerBuilderSupplier);
        if (FrescoSystrace.isTracing()) {
            FrescoSystrace.endSection();
        }
    }

    public static PipelineDraweeControllerBuilderSupplier getDraweeControllerBuilderSupplier() {
        return sDraweeControllerBuilderSupplier;
    }

    public static PipelineDraweeControllerBuilder newDraweeControllerBuilder() {
        return sDraweeControllerBuilderSupplier.get();
    }

    public static ImagePipelineFactory getImagePipelineFactory() {
        return ImagePipelineFactory.getInstance();
    }

    public static ImagePipeline getImagePipeline() {
        return getImagePipelineFactory().getImagePipeline();
    }

    public static void shutDown() {
        sDraweeControllerBuilderSupplier = null;
        SimpleDraweeView.shutDown();
        ImagePipelineFactory.shutDown();
    }

    public static boolean hasBeenInitialized() {
        return sIsInitialized;
    }
}
