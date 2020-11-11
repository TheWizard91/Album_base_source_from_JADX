package kotlinx.coroutines.flow.internal;

import kotlin.Metadata;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\n\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\u001a\u0011\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\b¨\u0006\u0003"}, mo33671d2 = {"checkIndexOverflow", "", "index", "kotlinx-coroutines-core"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: FlowExceptions.common.kt */
public final class FlowExceptions_commonKt {
    public static final int checkIndexOverflow(int index) {
        if (index >= 0) {
            return index;
        }
        throw new ArithmeticException("Index overflow has happened");
    }
}
