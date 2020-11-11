package kotlin;

import kotlin.jvm.functions.Function1;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\u001a-\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00060\u0005H\bø\u0001\u0000¢\u0006\u0002\u0010\u0007\u001a\u001f\u0010\b\u001a\u00020\u00012\n\u0010\t\u001a\u00020\u0001\"\u00020\u0006H\bø\u0001\u0000¢\u0006\u0004\b\n\u0010\u000b\u0002\u0004\n\u0002\b\u0019¨\u0006\f"}, mo33671d2 = {"UShortArray", "Lkotlin/UShortArray;", "size", "", "init", "Lkotlin/Function1;", "Lkotlin/UShort;", "(ILkotlin/jvm/functions/Function1;)[S", "ushortArrayOf", "elements", "ushortArrayOf-rL5Bavg", "([S)[S", "kotlin-stdlib"}, mo33672k = 2, mo33673mv = {1, 1, 16})
/* compiled from: UShortArray.kt */
public final class UShortArrayKt {
    private static final short[] UShortArray(int size, Function1<? super Integer, UShort> init) {
        short[] sArr = new short[size];
        for (int index = 0; index < size; index++) {
            sArr[index] = init.invoke(Integer.valueOf(index)).m1581unboximpl();
        }
        return UShortArray.m1584constructorimpl(sArr);
    }

    /* renamed from: ushortArrayOf-rL5Bavg  reason: not valid java name */
    private static final short[] m1599ushortArrayOfrL5Bavg(short... elements) {
        return elements;
    }
}
