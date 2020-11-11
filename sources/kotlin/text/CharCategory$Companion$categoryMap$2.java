package kotlin.text;

import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.MapsKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;
import kotlin.ranges.RangesKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010$\n\u0002\u0010\b\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00030\u0001H\n¢\u0006\u0002\b\u0004"}, mo33671d2 = {"<anonymous>", "", "", "Lkotlin/text/CharCategory;", "invoke"}, mo33672k = 3, mo33673mv = {1, 1, 16})
/* compiled from: CharCategory.kt */
final class CharCategory$Companion$categoryMap$2 extends Lambda implements Function0<Map<Integer, ? extends CharCategory>> {
    public static final CharCategory$Companion$categoryMap$2 INSTANCE = new CharCategory$Companion$categoryMap$2();

    CharCategory$Companion$categoryMap$2() {
        super(0);
    }

    public final Map<Integer, CharCategory> invoke() {
        CharCategory[] values = CharCategory.values();
        Map destination$iv$iv = new LinkedHashMap(RangesKt.coerceAtLeast(MapsKt.mapCapacity(values.length), 16));
        for (CharCategory it : values) {
            destination$iv$iv.put(Integer.valueOf(it.getValue()), it);
        }
        return destination$iv$iv;
    }
}