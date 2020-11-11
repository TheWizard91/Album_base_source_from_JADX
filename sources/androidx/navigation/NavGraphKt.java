package androidx.navigation;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000 \n\u0000\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\u001a\u0017\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0001\u0010\u0003\u001a\u00020\u0004H\u0002\u001a\u0017\u0010\u0005\u001a\u00020\u0006*\u00020\u00022\b\b\u0001\u0010\u0003\u001a\u00020\u0004H\n\u001a\u0015\u0010\u0007\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u0006H\n\u001a\u0015\u0010\n\u001a\u00020\b*\u00020\u00022\u0006\u0010\t\u001a\u00020\u0006H\n\u001a\u0015\u0010\n\u001a\u00020\b*\u00020\u00022\u0006\u0010\u000b\u001a\u00020\u0002H\n¨\u0006\f"}, mo33671d2 = {"contains", "", "Landroidx/navigation/NavGraph;", "id", "", "get", "Landroidx/navigation/NavDestination;", "minusAssign", "", "node", "plusAssign", "other", "navigation-common-ktx_release"}, mo33672k = 2, mo33673mv = {1, 1, 16})
/* compiled from: NavGraph.kt */
public final class NavGraphKt {
    public static final NavDestination get(NavGraph $this$get, int id) {
        Intrinsics.checkParameterIsNotNull($this$get, "$this$get");
        NavDestination findNode = $this$get.findNode(id);
        if (findNode != null) {
            return findNode;
        }
        throw new IllegalArgumentException("No destination for " + id + " was found in " + $this$get);
    }

    public static final boolean contains(NavGraph $this$contains, int id) {
        Intrinsics.checkParameterIsNotNull($this$contains, "$this$contains");
        return $this$contains.findNode(id) != null;
    }

    public static final void plusAssign(NavGraph $this$plusAssign, NavDestination node) {
        Intrinsics.checkParameterIsNotNull($this$plusAssign, "$this$plusAssign");
        Intrinsics.checkParameterIsNotNull(node, "node");
        $this$plusAssign.addDestination(node);
    }

    public static final void plusAssign(NavGraph $this$plusAssign, NavGraph other) {
        Intrinsics.checkParameterIsNotNull($this$plusAssign, "$this$plusAssign");
        Intrinsics.checkParameterIsNotNull(other, "other");
        $this$plusAssign.addAll(other);
    }

    public static final void minusAssign(NavGraph $this$minusAssign, NavDestination node) {
        Intrinsics.checkParameterIsNotNull($this$minusAssign, "$this$minusAssign");
        Intrinsics.checkParameterIsNotNull(node, "node");
        $this$minusAssign.remove(node);
    }
}
