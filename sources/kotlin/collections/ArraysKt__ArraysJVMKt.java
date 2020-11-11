package kotlin.collections;

import java.lang.reflect.Array;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.Collection;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00002\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0007\n\u0002\u0010\u000e\n\u0002\u0010\u0012\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u001e\n\u0002\b\u0002\u001a/\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u00022\f\u0010\u0003\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00012\u0006\u0010\u0004\u001a\u00020\u0005H\u0000¢\u0006\u0002\u0010\u0006\u001a\u0018\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\u00052\u0006\u0010\u0004\u001a\u00020\u0005H\u0001\u001a!\u0010\n\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001H\u0001¢\u0006\u0004\b\u000b\u0010\f\u001a,\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\f\u0012\u0006\b\u0001\u0012\u0002H\u0002\u0018\u00010\u0001H\b¢\u0006\u0002\u0010\u000e\u001a\u0015\u0010\u000f\u001a\u00020\u0010*\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\b\u001a&\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0006\b\u0000\u0010\u0002\u0018\u0001*\b\u0012\u0004\u0012\u0002H\u00020\u0015H\b¢\u0006\u0002\u0010\u0016¨\u0006\u0017"}, mo33671d2 = {"arrayOfNulls", "", "T", "reference", "size", "", "([Ljava/lang/Object;I)[Ljava/lang/Object;", "copyOfRangeToIndexCheck", "", "toIndex", "contentDeepHashCodeImpl", "contentDeepHashCode", "([Ljava/lang/Object;)I", "orEmpty", "([Ljava/lang/Object;)[Ljava/lang/Object;", "toString", "", "", "charset", "Ljava/nio/charset/Charset;", "toTypedArray", "", "(Ljava/util/Collection;)[Ljava/lang/Object;", "kotlin-stdlib"}, mo33672k = 5, mo33673mv = {1, 1, 16}, mo33675xi = 1, mo33676xs = "kotlin/collections/ArraysKt")
/* compiled from: ArraysJVM.kt */
class ArraysKt__ArraysJVMKt {
    public static final /* synthetic */ <T> T[] orEmpty(T[] $this$orEmpty) {
        if ($this$orEmpty != null) {
            return $this$orEmpty;
        }
        Intrinsics.reifiedOperationMarker(0, "T?");
        return new Object[0];
    }

    private static final String toString(byte[] $this$toString, Charset charset) {
        return new String($this$toString, charset);
    }

    public static final /* synthetic */ <T> T[] toTypedArray(Collection<? extends T> $this$toTypedArray) {
        Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
        Intrinsics.reifiedOperationMarker(0, "T?");
        T[] array = $this$toTypedArray.toArray(new Object[0]);
        if (array != null) {
            return array;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    public static final <T> T[] arrayOfNulls(T[] reference, int size) {
        Intrinsics.checkParameterIsNotNull(reference, "reference");
        Object newInstance = Array.newInstance(reference.getClass().getComponentType(), size);
        if (newInstance != null) {
            return (Object[]) newInstance;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
    }

    public static final void copyOfRangeToIndexCheck(int toIndex, int size) {
        if (toIndex > size) {
            throw new IndexOutOfBoundsException("toIndex (" + toIndex + ") is greater than size (" + size + ").");
        }
    }

    public static final <T> int contentDeepHashCode(T[] $this$contentDeepHashCodeImpl) {
        Intrinsics.checkParameterIsNotNull($this$contentDeepHashCodeImpl, "$this$contentDeepHashCodeImpl");
        return Arrays.deepHashCode($this$contentDeepHashCodeImpl);
    }
}
