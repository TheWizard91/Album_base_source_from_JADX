package androidx.fragment.app;

import android.os.Bundle;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\"\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a;\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00012\b\b\u0001\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\b\u001a-\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00012\u0006\u0010\u0006\u001a\u00020\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\b\u001a;\u0010\n\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00012\b\b\u0001\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00072\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\tH\b¨\u0006\u000b"}, mo33671d2 = {"add", "Landroidx/fragment/app/FragmentTransaction;", "F", "Landroidx/fragment/app/Fragment;", "containerViewId", "", "tag", "", "args", "Landroid/os/Bundle;", "replace", "fragment-ktx_release"}, mo33672k = 2, mo33673mv = {1, 1, 15})
/* compiled from: FragmentTransaction.kt */
public final class FragmentTransactionKt {
    public static /* synthetic */ FragmentTransaction add$default(FragmentTransaction $this$add, int containerViewId, String tag, Bundle args, int i, Object obj) {
        if ((i & 2) != 0) {
            tag = null;
        }
        if ((i & 4) != 0) {
            args = null;
        }
        Intrinsics.checkParameterIsNotNull($this$add, "$this$add");
        Intrinsics.reifiedOperationMarker(4, "F");
        FragmentTransaction add = $this$add.add(containerViewId, Fragment.class, args, tag);
        Intrinsics.checkExpressionValueIsNotNull(add, "add(containerViewId, F::class.java, args, tag)");
        return add;
    }

    public static final /* synthetic */ <F extends Fragment> FragmentTransaction add(FragmentTransaction $this$add, int containerViewId, String tag, Bundle args) {
        Intrinsics.checkParameterIsNotNull($this$add, "$this$add");
        Intrinsics.reifiedOperationMarker(4, "F");
        FragmentTransaction add = $this$add.add(containerViewId, Fragment.class, args, tag);
        Intrinsics.checkExpressionValueIsNotNull(add, "add(containerViewId, F::class.java, args, tag)");
        return add;
    }

    public static /* synthetic */ FragmentTransaction add$default(FragmentTransaction $this$add, String tag, Bundle args, int i, Object obj) {
        if ((i & 2) != 0) {
            args = null;
        }
        Intrinsics.checkParameterIsNotNull($this$add, "$this$add");
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.reifiedOperationMarker(4, "F");
        FragmentTransaction add = $this$add.add((Class<? extends Fragment>) Fragment.class, args, tag);
        Intrinsics.checkExpressionValueIsNotNull(add, "add(F::class.java, args, tag)");
        return add;
    }

    public static final /* synthetic */ <F extends Fragment> FragmentTransaction add(FragmentTransaction $this$add, String tag, Bundle args) {
        Intrinsics.checkParameterIsNotNull($this$add, "$this$add");
        Intrinsics.checkParameterIsNotNull(tag, "tag");
        Intrinsics.reifiedOperationMarker(4, "F");
        FragmentTransaction add = $this$add.add((Class<? extends Fragment>) Fragment.class, args, tag);
        Intrinsics.checkExpressionValueIsNotNull(add, "add(F::class.java, args, tag)");
        return add;
    }

    public static /* synthetic */ FragmentTransaction replace$default(FragmentTransaction $this$replace, int containerViewId, String tag, Bundle args, int i, Object obj) {
        if ((i & 2) != 0) {
            tag = null;
        }
        if ((i & 4) != 0) {
            args = null;
        }
        Intrinsics.checkParameterIsNotNull($this$replace, "$this$replace");
        Intrinsics.reifiedOperationMarker(4, "F");
        FragmentTransaction replace = $this$replace.replace(containerViewId, Fragment.class, args, tag);
        Intrinsics.checkExpressionValueIsNotNull(replace, "replace(containerViewId, F::class.java, args, tag)");
        return replace;
    }

    public static final /* synthetic */ <F extends Fragment> FragmentTransaction replace(FragmentTransaction $this$replace, int containerViewId, String tag, Bundle args) {
        Intrinsics.checkParameterIsNotNull($this$replace, "$this$replace");
        Intrinsics.reifiedOperationMarker(4, "F");
        FragmentTransaction replace = $this$replace.replace(containerViewId, Fragment.class, args, tag);
        Intrinsics.checkExpressionValueIsNotNull(replace, "replace(containerViewId, F::class.java, args, tag)");
        return replace;
    }
}
