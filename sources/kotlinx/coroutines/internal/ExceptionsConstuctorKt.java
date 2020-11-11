package kotlinx.coroutines.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.WeakHashMap;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000.\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\f\u001a*\u0010\n\u001a\u0018\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u0005\u0018\u00010\u0006j\u0004\u0018\u0001`\u00072\n\u0010\u000b\u001a\u0006\u0012\u0002\b\u00030\fH\u0002\u001a1\u0010\r\u001a\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0006j\u0002`\u00072\u0014\b\u0004\u0010\u000e\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\u0006H\b\u001a!\u0010\u000f\u001a\u0004\u0018\u0001H\u0010\"\b\b\u0000\u0010\u0010*\u00020\u00052\u0006\u0010\u0011\u001a\u0002H\u0010H\u0000¢\u0006\u0002\u0010\u0012\u001a\u001b\u0010\u0013\u001a\u00020\t*\u0006\u0012\u0002\b\u00030\u00042\b\b\u0002\u0010\u0014\u001a\u00020\tH\u0010\u001a\u0018\u0010\u0015\u001a\u00020\t*\u0006\u0012\u0002\b\u00030\u00042\u0006\u0010\u0016\u001a\u00020\tH\u0002\"\u000e\u0010\u0000\u001a\u00020\u0001X\u0004¢\u0006\u0002\n\u0000\"4\u0010\u0002\u001a(\u0012\f\u0012\n\u0012\u0006\b\u0001\u0012\u00020\u00050\u0004\u0012\u0016\u0012\u0014\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0006j\u0002`\u00070\u0003X\u0004¢\u0006\u0002\n\u0000\"\u000e\u0010\b\u001a\u00020\tX\u0004¢\u0006\u0002\n\u0000*(\b\u0002\u0010\u0017\"\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00062\u0010\u0012\u0004\u0012\u00020\u0005\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u0006¨\u0006\u0018"}, mo33671d2 = {"cacheLock", "Ljava/util/concurrent/locks/ReentrantReadWriteLock;", "exceptionCtors", "Ljava/util/WeakHashMap;", "Ljava/lang/Class;", "", "Lkotlin/Function1;", "Lkotlinx/coroutines/internal/Ctor;", "throwableFields", "", "createConstructor", "constructor", "Ljava/lang/reflect/Constructor;", "safeCtor", "block", "tryCopyException", "E", "exception", "(Ljava/lang/Throwable;)Ljava/lang/Throwable;", "fieldsCount", "accumulator", "fieldsCountOrDefault", "defaultValue", "Ctor", "kotlinx-coroutines-core"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: ExceptionsConstuctor.kt */
public final class ExceptionsConstuctorKt {
    private static final ReentrantReadWriteLock cacheLock = new ReentrantReadWriteLock();
    private static final WeakHashMap<Class<? extends Throwable>, Function1<Throwable, Throwable>> exceptionCtors = new WeakHashMap<>();
    private static final int throwableFields = fieldsCountOrDefault(Throwable.class, -1);

    /*  JADX ERROR: StackOverflow in pass: MarkFinallyVisitor
        jadx.core.utils.exceptions.JadxOverflowException: 
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:47)
        	at jadx.core.utils.ErrorsCounter.methodError(ErrorsCounter.java:81)
        */
    public static final <E extends java.lang.Throwable> E tryCopyException(E r11) {
        /*
            java.lang.String r0 = "exception"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r11, r0)
            boolean r0 = r11 instanceof kotlinx.coroutines.CopyableThrowable
            r1 = 0
            if (r0 == 0) goto L_0x002f
            kotlin.Result$Companion r0 = kotlin.Result.Companion     // Catch:{ all -> 0x0019 }
            r0 = 0
            r2 = r11
            kotlinx.coroutines.CopyableThrowable r2 = (kotlinx.coroutines.CopyableThrowable) r2     // Catch:{ all -> 0x0019 }
            java.lang.Throwable r2 = r2.createCopy()     // Catch:{ all -> 0x0019 }
            java.lang.Object r0 = kotlin.Result.m1289constructorimpl(r2)     // Catch:{ all -> 0x0019 }
            goto L_0x0024
        L_0x0019:
            r0 = move-exception
            kotlin.Result$Companion r2 = kotlin.Result.Companion
            java.lang.Object r0 = kotlin.ResultKt.createFailure(r0)
            java.lang.Object r0 = kotlin.Result.m1289constructorimpl(r0)
        L_0x0024:
            boolean r2 = kotlin.Result.m1295isFailureimpl(r0)
            if (r2 == 0) goto L_0x002b
            goto L_0x002c
        L_0x002b:
            r1 = r0
        L_0x002c:
            java.lang.Throwable r1 = (java.lang.Throwable) r1
            return r1
        L_0x002f:
            java.util.concurrent.locks.ReentrantReadWriteLock r0 = cacheLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r2 = r0.readLock()
            r2.lock()
            r3 = 0
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.Function1<java.lang.Throwable, java.lang.Throwable>> r4 = exceptionCtors     // Catch:{ all -> 0x013c }
            java.lang.Class r5 = r11.getClass()     // Catch:{ all -> 0x013c }
            java.lang.Object r4 = r4.get(r5)     // Catch:{ all -> 0x013c }
            kotlin.jvm.functions.Function1 r4 = (kotlin.jvm.functions.Function1) r4     // Catch:{ all -> 0x013c }
            r2.unlock()
            if (r4 == 0) goto L_0x0053
            r0 = r4
            r1 = 0
            java.lang.Object r2 = r0.invoke(r11)
            java.lang.Throwable r2 = (java.lang.Throwable) r2
            return r2
        L_0x0053:
            int r2 = throwableFields
            java.lang.Class r3 = r11.getClass()
            r4 = 0
            int r3 = fieldsCountOrDefault(r3, r4)
            if (r2 == r3) goto L_0x00a9
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r2 = r0.readLock()
            int r3 = r0.getWriteHoldCount()
            if (r3 != 0) goto L_0x006f
            int r3 = r0.getReadHoldCount()
            goto L_0x0070
        L_0x006f:
            r3 = r4
        L_0x0070:
            r5 = r4
        L_0x0071:
            if (r5 >= r3) goto L_0x0079
            r2.unlock()
            int r5 = r5 + 1
            goto L_0x0071
        L_0x0079:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r0 = r0.writeLock()
            r0.lock()
            r5 = 0
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.Function1<java.lang.Throwable, java.lang.Throwable>> r6 = exceptionCtors     // Catch:{ all -> 0x009c }
            java.util.Map r6 = (java.util.Map) r6     // Catch:{ all -> 0x009c }
            java.lang.Class r7 = r11.getClass()     // Catch:{ all -> 0x009c }
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$4$1 r8 = kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$4$1.INSTANCE     // Catch:{ all -> 0x009c }
            r6.put(r7, r8)     // Catch:{ all -> 0x009c }
            kotlin.Unit r5 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x009c }
        L_0x0090:
            if (r4 >= r3) goto L_0x0098
            r2.lock()
            int r4 = r4 + 1
            goto L_0x0090
        L_0x0098:
            r0.unlock()
            return r1
        L_0x009c:
            r1 = move-exception
        L_0x009d:
            if (r4 >= r3) goto L_0x00a5
            r2.lock()
            int r4 = r4 + 1
            goto L_0x009d
        L_0x00a5:
            r0.unlock()
            throw r1
        L_0x00a9:
            r0 = r1
            kotlin.jvm.functions.Function1 r0 = (kotlin.jvm.functions.Function1) r0
            java.lang.Class r2 = r11.getClass()
            java.lang.reflect.Constructor[] r2 = r2.getConstructors()
            java.lang.String r3 = "exception.javaClass.constructors"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r2, r3)
            r3 = 0
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$$inlined$sortedByDescending$1 r5 = new kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$$inlined$sortedByDescending$1
            r5.<init>()
            java.util.Comparator r5 = (java.util.Comparator) r5
            java.util.List r2 = kotlin.collections.ArraysKt.sortedWith((T[]) r2, r5)
            java.util.Iterator r3 = r2.iterator()
        L_0x00ca:
            boolean r5 = r3.hasNext()
            if (r5 == 0) goto L_0x00e3
            java.lang.Object r5 = r3.next()
            java.lang.reflect.Constructor r5 = (java.lang.reflect.Constructor) r5
            java.lang.String r6 = "constructor"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r5, r6)
            kotlin.jvm.functions.Function1 r0 = createConstructor(r5)
            if (r0 == 0) goto L_0x00e2
            goto L_0x00e3
        L_0x00e2:
            goto L_0x00ca
        L_0x00e3:
            java.util.concurrent.locks.ReentrantReadWriteLock r3 = cacheLock
            java.util.concurrent.locks.ReentrantReadWriteLock$ReadLock r5 = r3.readLock()
            int r6 = r3.getWriteHoldCount()
            if (r6 != 0) goto L_0x00f4
            int r6 = r3.getReadHoldCount()
            goto L_0x00f5
        L_0x00f4:
            r6 = r4
        L_0x00f5:
            r7 = r4
        L_0x00f6:
            if (r7 >= r6) goto L_0x00fe
            r5.unlock()
            int r7 = r7 + 1
            goto L_0x00f6
        L_0x00fe:
            java.util.concurrent.locks.ReentrantReadWriteLock$WriteLock r3 = r3.writeLock()
            r3.lock()
            r7 = 0
            java.util.WeakHashMap<java.lang.Class<? extends java.lang.Throwable>, kotlin.jvm.functions.Function1<java.lang.Throwable, java.lang.Throwable>> r8 = exceptionCtors     // Catch:{ all -> 0x012f }
            java.util.Map r8 = (java.util.Map) r8     // Catch:{ all -> 0x012f }
            java.lang.Class r9 = r11.getClass()     // Catch:{ all -> 0x012f }
            if (r0 == 0) goto L_0x0112
            r10 = r0
            goto L_0x0116
        L_0x0112:
            kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$5$1 r10 = kotlinx.coroutines.internal.ExceptionsConstuctorKt$tryCopyException$5$1.INSTANCE     // Catch:{ all -> 0x012f }
            kotlin.jvm.functions.Function1 r10 = (kotlin.jvm.functions.Function1) r10     // Catch:{ all -> 0x012f }
        L_0x0116:
            r8.put(r9, r10)     // Catch:{ all -> 0x012f }
            kotlin.Unit r7 = kotlin.Unit.INSTANCE     // Catch:{ all -> 0x012f }
        L_0x011b:
            if (r4 >= r6) goto L_0x0123
            r5.lock()
            int r4 = r4 + 1
            goto L_0x011b
        L_0x0123:
            r3.unlock()
            if (r0 == 0) goto L_0x012e
            java.lang.Object r1 = r0.invoke(r11)
            java.lang.Throwable r1 = (java.lang.Throwable) r1
        L_0x012e:
            return r1
        L_0x012f:
            r1 = move-exception
        L_0x0130:
            if (r4 >= r6) goto L_0x0138
            r5.lock()
            int r4 = r4 + 1
            goto L_0x0130
        L_0x0138:
            r3.unlock()
            throw r1
        L_0x013c:
            r0 = move-exception
            r2.unlock()
            throw r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlinx.coroutines.internal.ExceptionsConstuctorKt.tryCopyException(java.lang.Throwable):java.lang.Throwable");
    }

    private static final Function1<Throwable, Throwable> createConstructor(Constructor<?> constructor) {
        Class[] p = constructor.getParameterTypes();
        int length = p.length;
        if (length == 0) {
            return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$4(constructor);
        }
        if (length == 1) {
            Class cls = p[0];
            if (Intrinsics.areEqual((Object) cls, (Object) Throwable.class)) {
                return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$2(constructor);
            }
            if (Intrinsics.areEqual((Object) cls, (Object) String.class)) {
                return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$3(constructor);
            }
            return null;
        } else if (length == 2 && Intrinsics.areEqual((Object) p[0], (Object) String.class) && Intrinsics.areEqual((Object) p[1], (Object) Throwable.class)) {
            return new ExceptionsConstuctorKt$createConstructor$$inlined$safeCtor$1(constructor);
        } else {
            return null;
        }
    }

    private static final Function1<Throwable, Throwable> safeCtor(Function1<? super Throwable, ? extends Throwable> block) {
        return new ExceptionsConstuctorKt$safeCtor$1(block);
    }

    private static final int fieldsCountOrDefault(Class<?> $this$fieldsCountOrDefault, int defaultValue) {
        Integer num;
        KClass<?> kotlinClass = JvmClassMappingKt.getKotlinClass($this$fieldsCountOrDefault);
        try {
            Result.Companion companion = Result.Companion;
            num = Result.m1289constructorimpl(Integer.valueOf(fieldsCount$default($this$fieldsCountOrDefault, 0, 1, (Object) null)));
        } catch (Throwable th) {
            Result.Companion companion2 = Result.Companion;
            num = Result.m1289constructorimpl(ResultKt.createFailure(th));
        }
        Integer valueOf = Integer.valueOf(defaultValue);
        if (Result.m1295isFailureimpl(num)) {
            num = valueOf;
        }
        return ((Number) num).intValue();
    }

    static /* synthetic */ int fieldsCount$default(Class cls, int i, int i2, Object obj) {
        if ((i2 & 1) != 0) {
            i = 0;
        }
        return fieldsCount(cls, i);
    }

    private static final int fieldsCount(Class<?> $this$fieldsCount, int accumulator) {
        while (true) {
            Field[] declaredFields = $this$fieldsCount.getDeclaredFields();
            Intrinsics.checkExpressionValueIsNotNull(declaredFields, "declaredFields");
            int count$iv = 0;
            for (Field it : declaredFields) {
                Intrinsics.checkExpressionValueIsNotNull(it, "it");
                if (!Modifier.isStatic(it.getModifiers())) {
                    count$iv++;
                }
            }
            int totalFields = accumulator + count$iv;
            Class<?> superClass = $this$fieldsCount.getSuperclass();
            if (superClass == null) {
                return totalFields;
            }
            accumulator = totalFields;
            $this$fieldsCount = superClass;
        }
    }
}
