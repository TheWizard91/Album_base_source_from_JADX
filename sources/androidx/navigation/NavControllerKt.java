package androidx.navigation;

import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a:\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0003\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00042\u0017\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\b¨\u0006\u000b"}, mo33671d2 = {"createGraph", "Landroidx/navigation/NavGraph;", "Landroidx/navigation/NavController;", "id", "", "startDestination", "builder", "Lkotlin/Function1;", "Landroidx/navigation/NavGraphBuilder;", "", "Lkotlin/ExtensionFunctionType;", "navigation-runtime-ktx_release"}, mo33672k = 2, mo33673mv = {1, 1, 16})
/* compiled from: NavController.kt */
public final class NavControllerKt {
    public static /* synthetic */ NavGraph createGraph$default(NavController $this$createGraph, int id, int startDestination, Function1 builder, int i, Object obj) {
        if ((i & 1) != 0) {
            id = 0;
        }
        Intrinsics.checkParameterIsNotNull($this$createGraph, "$this$createGraph");
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        NavigatorProvider $this$navigation$iv = $this$createGraph.getNavigatorProvider();
        Intrinsics.checkExpressionValueIsNotNull($this$navigation$iv, "navigatorProvider");
        NavGraphBuilder navGraphBuilder = new NavGraphBuilder($this$navigation$iv, id, startDestination);
        builder.invoke(navGraphBuilder);
        return navGraphBuilder.build();
    }

    public static final NavGraph createGraph(NavController $this$createGraph, int id, int startDestination, Function1<? super NavGraphBuilder, Unit> builder) {
        Intrinsics.checkParameterIsNotNull($this$createGraph, "$this$createGraph");
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        NavigatorProvider $this$navigation$iv = $this$createGraph.getNavigatorProvider();
        Intrinsics.checkExpressionValueIsNotNull($this$navigation$iv, "navigatorProvider");
        NavGraphBuilder navGraphBuilder = new NavGraphBuilder($this$navigation$iv, id, startDestination);
        builder.invoke(navGraphBuilder);
        return navGraphBuilder.build();
    }
}
