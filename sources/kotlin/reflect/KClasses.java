package kotlin.reflect;

import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0002\b\u0002\n\u0002\u0010\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a+\u0010\u0000\u001a\u0002H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002H\u0007¢\u0006\u0002\u0010\u0005\u001a-\u0010\u0006\u001a\u0004\u0018\u0001H\u0001\"\b\b\u0000\u0010\u0001*\u00020\u0002*\b\u0012\u0004\u0012\u0002H\u00010\u00032\b\u0010\u0004\u001a\u0004\u0018\u00010\u0002H\u0007¢\u0006\u0002\u0010\u0005¨\u0006\u0007"}, mo33671d2 = {"cast", "T", "", "Lkotlin/reflect/KClass;", "value", "(Lkotlin/reflect/KClass;Ljava/lang/Object;)Ljava/lang/Object;", "safeCast", "kotlin-stdlib"}, mo33672k = 2, mo33673mv = {1, 1, 16})
/* compiled from: KClasses.kt */
public final class KClasses {
    public static final <T> T cast(KClass<T> $this$cast, Object value) {
        Intrinsics.checkParameterIsNotNull($this$cast, "$this$cast");
        if (!$this$cast.isInstance(value)) {
            throw new ClassCastException("Value cannot be cast to " + $this$cast.getQualifiedName());
        } else if (value != null) {
            return value;
        } else {
            throw new TypeCastException("null cannot be cast to non-null type T");
        }
    }

    public static final <T> T safeCast(KClass<T> $this$safeCast, Object value) {
        Intrinsics.checkParameterIsNotNull($this$safeCast, "$this$safeCast");
        if (!$this$safeCast.isInstance(value)) {
            return null;
        }
        if (value != null) {
            return value;
        }
        throw new TypeCastException("null cannot be cast to non-null type T");
    }
}
