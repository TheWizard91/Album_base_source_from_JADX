package androidx.navigation.dynamicfeatures;

import androidx.navigation.NavGraph;
import androidx.navigation.NavigatorProvider;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000&\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\u001a:\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0003\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00042\u0017\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\b\u001a:\u0010\u0000\u001a\u00020\t*\u00020\b2\b\b\u0001\u0010\u0003\u001a\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00042\u0017\u0010\u0006\u001a\u0013\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\t0\u0007¢\u0006\u0002\b\nH\b¨\u0006\u000b"}, mo33671d2 = {"navigation", "Landroidx/navigation/NavGraph;", "Landroidx/navigation/NavigatorProvider;", "id", "", "startDestination", "builder", "Lkotlin/Function1;", "Landroidx/navigation/dynamicfeatures/DynamicNavGraphBuilder;", "", "Lkotlin/ExtensionFunctionType;", "navigation-dynamic-features-runtime_release"}, mo33672k = 2, mo33673mv = {1, 1, 16})
/* compiled from: DynamicNavGraphBuilder.kt */
public final class DynamicNavGraphBuilderKt {
    public static /* synthetic */ NavGraph navigation$default(NavigatorProvider $this$navigation, int id, int startDestination, Function1 builder, int i, Object obj) {
        if ((i & 1) != 0) {
            id = 0;
        }
        Intrinsics.checkParameterIsNotNull($this$navigation, "$this$navigation");
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        DynamicNavGraphBuilder dynamicNavGraphBuilder = new DynamicNavGraphBuilder($this$navigation, id, startDestination);
        builder.invoke(dynamicNavGraphBuilder);
        return dynamicNavGraphBuilder.build();
    }

    public static final NavGraph navigation(NavigatorProvider $this$navigation, int id, int startDestination, Function1<? super DynamicNavGraphBuilder, Unit> builder) {
        Intrinsics.checkParameterIsNotNull($this$navigation, "$this$navigation");
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        DynamicNavGraphBuilder dynamicNavGraphBuilder = new DynamicNavGraphBuilder($this$navigation, id, startDestination);
        builder.invoke(dynamicNavGraphBuilder);
        return dynamicNavGraphBuilder.build();
    }

    public static final void navigation(DynamicNavGraphBuilder $this$navigation, int id, int startDestination, Function1<? super DynamicNavGraphBuilder, Unit> builder) {
        Intrinsics.checkParameterIsNotNull($this$navigation, "$this$navigation");
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        DynamicNavGraphBuilder dynamicNavGraphBuilder = new DynamicNavGraphBuilder($this$navigation.getProvider(), id, startDestination);
        builder.invoke(dynamicNavGraphBuilder);
        $this$navigation.destination(dynamicNavGraphBuilder);
    }
}