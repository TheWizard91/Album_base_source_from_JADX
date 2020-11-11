package androidx.core.graphics;

import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.Region;
import android.graphics.RegionIterator;
import java.util.Iterator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00004\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010(\n\u0002\b\u0007\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\f\u001a\u0015\u0010\u0000\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\f\u001a\u0015\u0010\u0004\u001a\u00020\u0005*\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u0007H\n\u001a0\u0010\b\u001a\u00020\t*\u00020\u00012!\u0010\n\u001a\u001d\u0012\u0013\u0012\u00110\u0003¢\u0006\f\b\f\u0012\b\b\r\u0012\u0004\b\b(\u000e\u0012\u0004\u0012\u00020\t0\u000bH\b\u001a\u0013\u0010\u000f\u001a\b\u0012\u0004\u0012\u00020\u00030\u0010*\u00020\u0001H\u0002\u001a\u0015\u0010\u0011\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n\u001a\u0015\u0010\u0011\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n\u001a\r\u0010\u0012\u001a\u00020\u0001*\u00020\u0001H\n\u001a\u0015\u0010\u0013\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\f\u001a\u0015\u0010\u0013\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\f\u001a\u0015\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\n\u001a\u0015\u0010\u0014\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\n\u001a\r\u0010\u0015\u001a\u00020\u0001*\u00020\u0001H\n\u001a\u0015\u0010\u0016\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\f\u001a\u0015\u0010\u0016\u001a\u00020\u0001*\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0001H\f¨\u0006\u0017"}, mo33671d2 = {"and", "Landroid/graphics/Region;", "r", "Landroid/graphics/Rect;", "contains", "", "p", "Landroid/graphics/Point;", "forEach", "", "action", "Lkotlin/Function1;", "Lkotlin/ParameterName;", "name", "rect", "iterator", "", "minus", "not", "or", "plus", "unaryMinus", "xor", "core-ktx_release"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: Region.kt */
public final class RegionKt {
    public static final boolean contains(Region $this$contains, Point p) {
        Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
        Intrinsics.checkParameterIsNotNull(p, "p");
        return $this$contains.contains(p.x, p.y);
    }

    public static final Region plus(Region $this$plus, Rect r) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Region $this$apply = new Region($this$plus);
        $this$apply.union(r);
        return $this$apply;
    }

    public static final Region plus(Region $this$plus, Region r) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Region $this$apply = new Region($this$plus);
        $this$apply.op(r, Region.Op.UNION);
        return $this$apply;
    }

    public static final Region minus(Region $this$minus, Rect r) {
        Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Region $this$apply = new Region($this$minus);
        $this$apply.op(r, Region.Op.DIFFERENCE);
        return $this$apply;
    }

    public static final Region minus(Region $this$minus, Region r) {
        Intrinsics.checkParameterIsNotNull($this$minus, "$this$minus");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Region $this$apply = new Region($this$minus);
        $this$apply.op(r, Region.Op.DIFFERENCE);
        return $this$apply;
    }

    public static final Region unaryMinus(Region $this$unaryMinus) {
        Intrinsics.checkParameterIsNotNull($this$unaryMinus, "$this$unaryMinus");
        Region $this$apply = new Region($this$unaryMinus.getBounds());
        $this$apply.op($this$unaryMinus, Region.Op.DIFFERENCE);
        return $this$apply;
    }

    public static final Region not(Region $this$not) {
        Intrinsics.checkParameterIsNotNull($this$not, "$this$not");
        Region $this$unaryMinus$iv = $this$not;
        Region $this$apply$iv = new Region($this$unaryMinus$iv.getBounds());
        $this$apply$iv.op($this$unaryMinus$iv, Region.Op.DIFFERENCE);
        return $this$apply$iv;
    }

    /* renamed from: or */
    public static final Region m9or(Region $this$or, Rect r) {
        Intrinsics.checkParameterIsNotNull($this$or, "$this$or");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Region $this$apply$iv = new Region($this$or);
        $this$apply$iv.union(r);
        return $this$apply$iv;
    }

    /* renamed from: or */
    public static final Region m10or(Region $this$or, Region r) {
        Intrinsics.checkParameterIsNotNull($this$or, "$this$or");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Region $this$apply$iv = new Region($this$or);
        $this$apply$iv.op(r, Region.Op.UNION);
        return $this$apply$iv;
    }

    public static final Region and(Region $this$and, Rect r) {
        Intrinsics.checkParameterIsNotNull($this$and, "$this$and");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Region $this$apply = new Region($this$and);
        $this$apply.op(r, Region.Op.INTERSECT);
        return $this$apply;
    }

    public static final Region and(Region $this$and, Region r) {
        Intrinsics.checkParameterIsNotNull($this$and, "$this$and");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Region $this$apply = new Region($this$and);
        $this$apply.op(r, Region.Op.INTERSECT);
        return $this$apply;
    }

    public static final Region xor(Region $this$xor, Rect r) {
        Intrinsics.checkParameterIsNotNull($this$xor, "$this$xor");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Region $this$apply = new Region($this$xor);
        $this$apply.op(r, Region.Op.XOR);
        return $this$apply;
    }

    public static final Region xor(Region $this$xor, Region r) {
        Intrinsics.checkParameterIsNotNull($this$xor, "$this$xor");
        Intrinsics.checkParameterIsNotNull(r, "r");
        Region $this$apply = new Region($this$xor);
        $this$apply.op(r, Region.Op.XOR);
        return $this$apply;
    }

    public static final void forEach(Region $this$forEach, Function1<? super Rect, Unit> action) {
        Intrinsics.checkParameterIsNotNull($this$forEach, "$this$forEach");
        Intrinsics.checkParameterIsNotNull(action, "action");
        RegionIterator iterator = new RegionIterator($this$forEach);
        while (true) {
            Rect r = new Rect();
            if (iterator.next(r)) {
                action.invoke(r);
            } else {
                return;
            }
        }
    }

    public static final Iterator<Rect> iterator(Region $this$iterator) {
        Intrinsics.checkParameterIsNotNull($this$iterator, "$this$iterator");
        return new RegionKt$iterator$1($this$iterator);
    }
}
