package kotlin.p022native.concurrent;

import java.lang.annotation.ElementType;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import kotlin.Metadata;
import kotlin.annotation.AnnotationRetention;
import kotlin.annotation.AnnotationTarget;
import kotlin.annotation.Retention;

@Target({ElementType.TYPE})
@kotlin.annotation.Target(allowedTargets = {AnnotationTarget.PROPERTY, AnnotationTarget.CLASS})
@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\n\n\u0002\u0018\u0002\n\u0002\u0010\u001b\n\u0000\b\"\u0018\u00002\u00020\u0001B\u0000¨\u0006\u0002"}, mo33671d2 = {"Lkotlin/native/concurrent/ThreadLocal;", "", "kotlin-stdlib"}, mo33672k = 1, mo33673mv = {1, 1, 16})
@Retention(AnnotationRetention.BINARY)
@java.lang.annotation.Retention(RetentionPolicy.CLASS)
/* renamed from: kotlin.native.concurrent.ThreadLocal */
/* compiled from: NativeAnnotationsH.kt */
@interface ThreadLocal {
}