package kotlin.collections.unsigned;

import kotlin.Metadata;
import kotlin.UShortArray;
import kotlin.collections.UShortIterator;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo33671d2 = {"<anonymous>", "Lkotlin/collections/UShortIterator;", "invoke"}, mo33672k = 3, mo33673mv = {1, 1, 16})
/* compiled from: _UArrays.kt */
final class UArraysKt___UArraysKt$withIndex$4 extends Lambda implements Function0<UShortIterator> {
    final /* synthetic */ short[] $this_withIndex;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    UArraysKt___UArraysKt$withIndex$4(short[] sArr) {
        super(0);
        this.$this_withIndex = sArr;
    }

    public final UShortIterator invoke() {
        return UShortArray.m1593iteratorimpl(this.$this_withIndex);
    }
}
