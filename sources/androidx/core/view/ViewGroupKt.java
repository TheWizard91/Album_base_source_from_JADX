package androidx.core.view;

import android.view.View;
import android.view.ViewGroup;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.Sequence;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000L\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010)\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\t\u001a\u0015\u0010\n\u001a\u00020\u000b*\u00020\u00032\u0006\u0010\f\u001a\u00020\u0002H\n\u001a0\u0010\r\u001a\u00020\u000e*\u00020\u00032!\u0010\u000f\u001a\u001d\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u000e0\u0010H\b\u001aE\u0010\u0013\u001a\u00020\u000e*\u00020\u000326\u0010\u000f\u001a2\u0012\u0013\u0012\u00110\u0007¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\u0015\u0012\u0013\u0012\u00110\u0002¢\u0006\f\b\u0011\u0012\b\b\u0012\u0012\u0004\b\b(\f\u0012\u0004\u0012\u00020\u000e0\u0014H\b\u001a\u0015\u0010\u0016\u001a\u00020\u0002*\u00020\u00032\u0006\u0010\u0015\u001a\u00020\u0007H\u0002\u001a\r\u0010\u0017\u001a\u00020\u000b*\u00020\u0003H\b\u001a\r\u0010\u0018\u001a\u00020\u000b*\u00020\u0003H\b\u001a\u0013\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00020\u001a*\u00020\u0003H\u0002\u001a\u0015\u0010\u001b\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\f\u001a\u00020\u0002H\n\u001a\u0015\u0010\u001c\u001a\u00020\u000e*\u00020\u00032\u0006\u0010\f\u001a\u00020\u0002H\n\u001a\u0017\u0010\u001d\u001a\u00020\u000e*\u00020\u001e2\b\b\u0001\u0010\u0006\u001a\u00020\u0007H\b\u001a5\u0010\u001f\u001a\u00020\u000e*\u00020\u001e2\b\b\u0003\u0010 \u001a\u00020\u00072\b\b\u0003\u0010!\u001a\u00020\u00072\b\b\u0003\u0010\"\u001a\u00020\u00072\b\b\u0003\u0010#\u001a\u00020\u0007H\b\u001a5\u0010$\u001a\u00020\u000e*\u00020\u001e2\b\b\u0003\u0010%\u001a\u00020\u00072\b\b\u0003\u0010!\u001a\u00020\u00072\b\b\u0003\u0010&\u001a\u00020\u00072\b\b\u0003\u0010#\u001a\u00020\u0007H\b\"\u001b\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00020\u0001*\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0004\u0010\u0005\"\u0016\u0010\u0006\u001a\u00020\u0007*\u00020\u00038Æ\u0002¢\u0006\u0006\u001a\u0004\b\b\u0010\t¨\u0006'"}, mo33671d2 = {"children", "Lkotlin/sequences/Sequence;", "Landroid/view/View;", "Landroid/view/ViewGroup;", "getChildren", "(Landroid/view/ViewGroup;)Lkotlin/sequences/Sequence;", "size", "", "getSize", "(Landroid/view/ViewGroup;)I", "contains", "", "view", "forEach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "forEachIndexed", "Lkotlin/Function2;", "index", "get", "isEmpty", "isNotEmpty", "iterator", "", "minusAssign", "plusAssign", "setMargins", "Landroid/view/ViewGroup$MarginLayoutParams;", "updateMargins", "left", "top", "right", "bottom", "updateMarginsRelative", "start", "end", "core-ktx_release"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: ViewGroup.kt */
public final class ViewGroupKt {
    public static final View get(ViewGroup $this$get, int index) {
        Intrinsics.checkParameterIsNotNull($this$get, "$this$get");
        View childAt = $this$get.getChildAt(index);
        if (childAt != null) {
            return childAt;
        }
        throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + $this$get.getChildCount());
    }

    public static final boolean contains(ViewGroup $this$contains, View view) {
        Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
        Intrinsics.checkParameterIsNotNull(view, "view");
        return $this$contains.indexOfChild(view) != -1;
    }

    public static final void plusAssign(ViewGroup $this$plusAssign, View view) {
        Intrinsics.checkParameterIsNotNull($this$plusAssign, "$this$plusAssign");
        Intrinsics.checkParameterIsNotNull(view, "view");
        $this$plusAssign.addView(view);
    }

    public static final void minusAssign(ViewGroup $this$minusAssign, View view) {
        Intrinsics.checkParameterIsNotNull($this$minusAssign, "$this$minusAssign");
        Intrinsics.checkParameterIsNotNull(view, "view");
        $this$minusAssign.removeView(view);
    }

    public static final int getSize(ViewGroup $this$size) {
        Intrinsics.checkParameterIsNotNull($this$size, "$this$size");
        return $this$size.getChildCount();
    }

    public static final boolean isEmpty(ViewGroup $this$isEmpty) {
        Intrinsics.checkParameterIsNotNull($this$isEmpty, "$this$isEmpty");
        return $this$isEmpty.getChildCount() == 0;
    }

    public static final boolean isNotEmpty(ViewGroup $this$isNotEmpty) {
        Intrinsics.checkParameterIsNotNull($this$isNotEmpty, "$this$isNotEmpty");
        return $this$isNotEmpty.getChildCount() != 0;
    }

    public static final void forEach(ViewGroup $this$forEach, Function1<? super View, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$forEach, "$this$forEach");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int childCount = $this$forEach.getChildCount();
        for (int index = 0; index < childCount; index++) {
            View childAt = $this$forEach.getChildAt(index);
            Intrinsics.checkExpressionValueIsNotNull(childAt, "getChildAt(index)");
            action.invoke(childAt);
        }
    }

    public static final void forEachIndexed(ViewGroup $this$forEachIndexed, Function2<? super Integer, ? super View, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$forEachIndexed, "$this$forEachIndexed");
        Intrinsics.checkParameterIsNotNull(action, "action");
        int childCount = $this$forEachIndexed.getChildCount();
        for (int index = 0; index < childCount; index++) {
            Integer valueOf = Integer.valueOf(index);
            View childAt = $this$forEachIndexed.getChildAt(index);
            Intrinsics.checkExpressionValueIsNotNull(childAt, "getChildAt(index)");
            action.invoke(valueOf, childAt);
        }
    }

    public static final Iterator<View> iterator(ViewGroup $this$iterator) {
        Intrinsics.checkParameterIsNotNull($this$iterator, "$this$iterator");
        return new ViewGroupKt$iterator$1($this$iterator);
    }

    public static final Sequence<View> getChildren(ViewGroup $this$children) {
        Intrinsics.checkParameterIsNotNull($this$children, "$this$children");
        return new ViewGroupKt$children$1($this$children);
    }

    public static final void setMargins(ViewGroup.MarginLayoutParams $this$setMargins, int size) {
        Intrinsics.checkParameterIsNotNull($this$setMargins, "$this$setMargins");
        $this$setMargins.setMargins(size, size, size, size);
    }

    public static /* synthetic */ void updateMargins$default(ViewGroup.MarginLayoutParams $this$updateMargins, int left, int top, int right, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            left = $this$updateMargins.leftMargin;
        }
        if ((i & 2) != 0) {
            top = $this$updateMargins.topMargin;
        }
        if ((i & 4) != 0) {
            right = $this$updateMargins.rightMargin;
        }
        if ((i & 8) != 0) {
            bottom = $this$updateMargins.bottomMargin;
        }
        Intrinsics.checkParameterIsNotNull($this$updateMargins, "$this$updateMargins");
        $this$updateMargins.setMargins(left, top, right, bottom);
    }

    public static final void updateMargins(ViewGroup.MarginLayoutParams $this$updateMargins, int left, int top, int right, int bottom) {
        Intrinsics.checkParameterIsNotNull($this$updateMargins, "$this$updateMargins");
        $this$updateMargins.setMargins(left, top, right, bottom);
    }

    public static /* synthetic */ void updateMarginsRelative$default(ViewGroup.MarginLayoutParams $this$updateMarginsRelative, int start, int top, int end, int bottom, int i, Object obj) {
        if ((i & 1) != 0) {
            start = $this$updateMarginsRelative.getMarginStart();
        }
        if ((i & 2) != 0) {
            top = $this$updateMarginsRelative.topMargin;
        }
        if ((i & 4) != 0) {
            end = $this$updateMarginsRelative.getMarginEnd();
        }
        if ((i & 8) != 0) {
            bottom = $this$updateMarginsRelative.bottomMargin;
        }
        Intrinsics.checkParameterIsNotNull($this$updateMarginsRelative, "$this$updateMarginsRelative");
        $this$updateMarginsRelative.setMarginStart(start);
        $this$updateMarginsRelative.topMargin = top;
        $this$updateMarginsRelative.setMarginEnd(end);
        $this$updateMarginsRelative.bottomMargin = bottom;
    }

    public static final void updateMarginsRelative(ViewGroup.MarginLayoutParams $this$updateMarginsRelative, int start, int top, int end, int bottom) {
        Intrinsics.checkParameterIsNotNull($this$updateMarginsRelative, "$this$updateMarginsRelative");
        $this$updateMarginsRelative.setMarginStart(start);
        $this$updateMarginsRelative.topMargin = top;
        $this$updateMarginsRelative.setMarginEnd(end);
        $this$updateMarginsRelative.bottomMargin = bottom;
    }
}
