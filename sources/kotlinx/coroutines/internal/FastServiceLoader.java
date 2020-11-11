package kotlinx.coroutines.internal;

import androidx.core.app.NotificationCompat;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ServiceLoader;
import java.util.Set;
import java.util.jar.JarFile;
import kotlin.ExceptionsKt;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.InlineMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÀ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J1\u0010\u0005\u001a\u0002H\u0006\"\u0004\b\u0000\u0010\u00062\u0006\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\t2\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00060\u000bH\u0002¢\u0006\u0002\u0010\fJ/\u0010\r\u001a\b\u0012\u0004\u0012\u0002H\u00060\u000e\"\u0004\b\u0000\u0010\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00060\u000b2\u0006\u0010\b\u001a\u00020\tH\u0000¢\u0006\u0002\b\u000fJ/\u0010\u0010\u001a\b\u0012\u0004\u0012\u0002H\u00060\u000e\"\u0004\b\u0000\u0010\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u0002H\u00060\u000b2\u0006\u0010\b\u001a\u00020\tH\u0000¢\u0006\u0002\b\u0011J\u0016\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u000e2\u0006\u0010\u0013\u001a\u00020\u0014H\u0002J\u0016\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00040\u000e2\u0006\u0010\u0016\u001a\u00020\u0017H\u0002J,\u0010\u0018\u001a\u0002H\u0019\"\u0004\b\u0000\u0010\u0019*\u00020\u001a2\u0012\u0010\u001b\u001a\u000e\u0012\u0004\u0012\u00020\u001a\u0012\u0004\u0012\u0002H\u00190\u001cH\b¢\u0006\u0002\u0010\u001dR\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000¨\u0006\u001e"}, mo33671d2 = {"Lkotlinx/coroutines/internal/FastServiceLoader;", "", "()V", "PREFIX", "", "getProviderInstance", "S", "name", "loader", "Ljava/lang/ClassLoader;", "service", "Ljava/lang/Class;", "(Ljava/lang/String;Ljava/lang/ClassLoader;Ljava/lang/Class;)Ljava/lang/Object;", "load", "", "load$kotlinx_coroutines_core", "loadProviders", "loadProviders$kotlinx_coroutines_core", "parse", "url", "Ljava/net/URL;", "parseFile", "r", "Ljava/io/BufferedReader;", "use", "R", "Ljava/util/jar/JarFile;", "block", "Lkotlin/Function1;", "(Ljava/util/jar/JarFile;Lkotlin/jvm/functions/Function1;)Ljava/lang/Object;", "kotlinx-coroutines-core"}, mo33672k = 1, mo33673mv = {1, 1, 15})
/* compiled from: FastServiceLoader.kt */
public final class FastServiceLoader {
    public static final FastServiceLoader INSTANCE = new FastServiceLoader();
    private static final String PREFIX = "META-INF/services/";

    private FastServiceLoader() {
    }

    public final <S> List<S> load$kotlinx_coroutines_core(Class<S> service, ClassLoader loader) {
        Intrinsics.checkParameterIsNotNull(service, NotificationCompat.CATEGORY_SERVICE);
        Intrinsics.checkParameterIsNotNull(loader, "loader");
        try {
            return loadProviders$kotlinx_coroutines_core(service, loader);
        } catch (Throwable th) {
            ServiceLoader<S> load = ServiceLoader.load(service, loader);
            Intrinsics.checkExpressionValueIsNotNull(load, "ServiceLoader.load(service, loader)");
            return CollectionsKt.toList(load);
        }
    }

    public final <S> List<S> loadProviders$kotlinx_coroutines_core(Class<S> service, ClassLoader loader) {
        Intrinsics.checkParameterIsNotNull(service, NotificationCompat.CATEGORY_SERVICE);
        Intrinsics.checkParameterIsNotNull(loader, "loader");
        Enumeration urls = loader.getResources(PREFIX + service.getName());
        Intrinsics.checkExpressionValueIsNotNull(urls, "urls");
        ArrayList<T> $this$flatMap$iv = Collections.list(urls);
        Intrinsics.checkExpressionValueIsNotNull($this$flatMap$iv, "java.util.Collections.list(this)");
        Collection destination$iv$iv = new ArrayList();
        for (T it : $this$flatMap$iv) {
            FastServiceLoader fastServiceLoader = INSTANCE;
            Intrinsics.checkExpressionValueIsNotNull(it, "it");
            CollectionsKt.addAll(destination$iv$iv, fastServiceLoader.parse(it));
        }
        Set providers = CollectionsKt.toSet((List) destination$iv$iv);
        if (!providers.isEmpty()) {
            Iterable<String> $this$map$iv = providers;
            Collection destination$iv$iv2 = new ArrayList(CollectionsKt.collectionSizeOrDefault($this$map$iv, 10));
            for (String it2 : $this$map$iv) {
                destination$iv$iv2.add(INSTANCE.getProviderInstance(it2, loader, service));
            }
            return (List) destination$iv$iv2;
        }
        throw new IllegalArgumentException("No providers were loaded with FastServiceLoader".toString());
    }

    private final <S> S getProviderInstance(String name, ClassLoader loader, Class<S> service) {
        Class clazz = Class.forName(name, false, loader);
        if (service.isAssignableFrom(clazz)) {
            return service.cast(clazz.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]));
        }
        throw new IllegalArgumentException(("Expected service of class " + service + ", but found " + clazz).toString());
    }

    /* JADX WARNING: Code restructure failed: missing block: B:18:0x0064, code lost:
        r11 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:20:?, code lost:
        kotlin.p020io.CloseableKt.closeFinally(r10, r4);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:21:0x0068, code lost:
        throw r11;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:41:0x009d, code lost:
        r3 = move-exception;
     */
    /* JADX WARNING: Code restructure failed: missing block: B:42:0x009e, code lost:
        kotlin.p020io.CloseableKt.closeFinally(r1, r2);
     */
    /* JADX WARNING: Code restructure failed: missing block: B:43:0x00a1, code lost:
        throw r3;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private final java.util.List<java.lang.String> parse(java.net.URL r15) {
        /*
            r14 = this;
            java.lang.String r0 = r15.toString()
            java.lang.String r1 = "url.toString()"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r1)
            java.lang.String r1 = "jar"
            r2 = 0
            r3 = 2
            r4 = 0
            boolean r1 = kotlin.text.StringsKt.startsWith$default(r0, r1, r2, r3, r4)
            if (r1 == 0) goto L_0x0079
            java.lang.String r1 = "jar:file:"
            java.lang.String r1 = kotlin.text.StringsKt.substringAfter$default((java.lang.String) r0, (java.lang.String) r1, (java.lang.String) r4, (int) r3, (java.lang.Object) r4)
            r5 = 33
            java.lang.String r1 = kotlin.text.StringsKt.substringBefore$default((java.lang.String) r1, (char) r5, (java.lang.String) r4, (int) r3, (java.lang.Object) r4)
            java.lang.String r5 = "!/"
            java.lang.String r3 = kotlin.text.StringsKt.substringAfter$default((java.lang.String) r0, (java.lang.String) r5, (java.lang.String) r4, (int) r3, (java.lang.Object) r4)
            java.util.jar.JarFile r5 = new java.util.jar.JarFile
            r5.<init>(r1, r2)
            r2 = r14
            r6 = 0
            r7 = r4
            java.lang.Throwable r7 = (java.lang.Throwable) r7
            r8 = r5
            r9 = 0
            java.io.BufferedReader r10 = new java.io.BufferedReader     // Catch:{ all -> 0x0069 }
            java.io.InputStreamReader r11 = new java.io.InputStreamReader     // Catch:{ all -> 0x0069 }
            java.util.zip.ZipEntry r12 = new java.util.zip.ZipEntry     // Catch:{ all -> 0x0069 }
            r12.<init>(r3)     // Catch:{ all -> 0x0069 }
            java.io.InputStream r12 = r8.getInputStream(r12)     // Catch:{ all -> 0x0069 }
            java.lang.String r13 = "UTF-8"
            r11.<init>(r12, r13)     // Catch:{ all -> 0x0069 }
            java.io.Reader r11 = (java.io.Reader) r11     // Catch:{ all -> 0x0069 }
            r10.<init>(r11)     // Catch:{ all -> 0x0069 }
            java.io.Closeable r10 = (java.io.Closeable) r10     // Catch:{ all -> 0x0069 }
            java.lang.Throwable r4 = (java.lang.Throwable) r4     // Catch:{ all -> 0x0069 }
            r11 = r10
            java.io.BufferedReader r11 = (java.io.BufferedReader) r11     // Catch:{ all -> 0x0062 }
            r12 = 0
            kotlinx.coroutines.internal.FastServiceLoader r13 = INSTANCE     // Catch:{ all -> 0x0062 }
            java.util.List r13 = r13.parseFile(r11)     // Catch:{ all -> 0x0062 }
            kotlin.p020io.CloseableKt.closeFinally(r10, r4)     // Catch:{ all -> 0x0069 }
            r5.close()     // Catch:{ all -> 0x0060 }
            return r13
        L_0x0060:
            r2 = move-exception
            throw r2
        L_0x0062:
            r4 = move-exception
            throw r4     // Catch:{ all -> 0x0064 }
        L_0x0064:
            r11 = move-exception
            kotlin.p020io.CloseableKt.closeFinally(r10, r4)     // Catch:{ all -> 0x0069 }
            throw r11     // Catch:{ all -> 0x0069 }
        L_0x0069:
            r4 = move-exception
            r7 = r4
            throw r4     // Catch:{ all -> 0x006d }
        L_0x006d:
            r4 = move-exception
            r5.close()     // Catch:{ all -> 0x0073 }
            throw r4
        L_0x0073:
            r4 = move-exception
            kotlin.ExceptionsKt.addSuppressed(r7, r4)
            throw r7
        L_0x0079:
            java.io.BufferedReader r1 = new java.io.BufferedReader
            java.io.InputStreamReader r2 = new java.io.InputStreamReader
            java.io.InputStream r3 = r15.openStream()
            r2.<init>(r3)
            java.io.Reader r2 = (java.io.Reader) r2
            r1.<init>(r2)
            java.io.Closeable r1 = (java.io.Closeable) r1
            java.lang.Throwable r4 = (java.lang.Throwable) r4
            r2 = r1
            java.io.BufferedReader r2 = (java.io.BufferedReader) r2     // Catch:{ all -> 0x009b }
            r3 = 0
            kotlinx.coroutines.internal.FastServiceLoader r5 = INSTANCE     // Catch:{ all -> 0x009b }
            java.util.List r5 = r5.parseFile(r2)     // Catch:{ all -> 0x009b }
            kotlin.p020io.CloseableKt.closeFinally(r1, r4)
            return r5
        L_0x009b:
            r2 = move-exception
            throw r2     // Catch:{ all -> 0x009d }
        L_0x009d:
            r3 = move-exception
            kotlin.p020io.CloseableKt.closeFinally(r1, r2)
            throw r3
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.FastServiceLoader.parse(java.net.URL):java.util.List");
    }

    private final <R> R use(JarFile $this$use, Function1<? super JarFile, ? extends R> block) {
        Throwable cause;
        Throwable th = null;
        try {
            R invoke = block.invoke($this$use);
            InlineMarker.finallyStart(1);
            $this$use.close();
            InlineMarker.finallyEnd(1);
            return invoke;
        } catch (Throwable e) {
            InlineMarker.finallyStart(1);
            try {
                $this$use.close();
                InlineMarker.finallyEnd(1);
                throw e;
            } catch (Throwable closeException) {
                ExceptionsKt.addSuppressed(cause, closeException);
                throw cause;
            }
        }
    }

    private final List<String> parseFile(BufferedReader r) {
        CharSequence $this$all$iv;
        Set names = new LinkedHashSet();
        while (true) {
            String line = r.readLine();
            if (line == null) {
                return CollectionsKt.toList(names);
            }
            String substringBefore$default = StringsKt.substringBefore$default(line, "#", (String) null, 2, (Object) null);
            if (substringBefore$default != null) {
                String serviceName = StringsKt.trim((CharSequence) substringBefore$default).toString();
                CharSequence $this$all$iv2 = serviceName;
                boolean z = false;
                int i = 0;
                while (true) {
                    if (i >= $this$all$iv2.length()) {
                        $this$all$iv = 1;
                        break;
                    }
                    char it = $this$all$iv2.charAt(i);
                    if (((it == '.' || Character.isJavaIdentifierPart(it)) ? (char) 1 : 0) == 0) {
                        $this$all$iv = null;
                        break;
                    }
                    i++;
                }
                if ($this$all$iv != null) {
                    if (serviceName.length() > 0) {
                        z = true;
                    }
                    if (z) {
                        names.add(serviceName);
                    }
                } else {
                    throw new IllegalArgumentException(("Illegal service provider class name: " + serviceName).toString());
                }
            } else {
                throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
            }
        }
    }
}
