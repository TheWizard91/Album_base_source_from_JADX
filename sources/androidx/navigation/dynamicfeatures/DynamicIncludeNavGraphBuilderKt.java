package androidx.navigation.dynamicfeatures;

import androidx.navigation.Navigator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000(\n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a'\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0001\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u0006H\b\u001a@\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0001\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\u00062\u0017\u0010\b\u001a\u0013\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u00010\t¢\u0006\u0002\b\u000bH\b¨\u0006\f"}, mo33671d2 = {"includeDynamic", "", "Landroidx/navigation/dynamicfeatures/DynamicNavGraphBuilder;", "id", "", "moduleName", "", "graphResourceName", "builder", "Lkotlin/Function1;", "Landroidx/navigation/dynamicfeatures/DynamicIncludeNavGraphBuilder;", "Lkotlin/ExtensionFunctionType;", "navigation-dynamic-features-runtime_release"}, mo33672k = 2, mo33673mv = {1, 1, 16})
/* compiled from: DynamicIncludeNavGraphBuilder.kt */
public final class DynamicIncludeNavGraphBuilderKt {
    public static final void includeDynamic(DynamicNavGraphBuilder $this$includeDynamic, int id, String moduleName, String graphResourceName) {
        Intrinsics.checkParameterIsNotNull($this$includeDynamic, "$this$includeDynamic");
        Intrinsics.checkParameterIsNotNull(moduleName, "moduleName");
        Intrinsics.checkParameterIsNotNull(graphResourceName, "graphResourceName");
        DynamicNavGraphBuilder $this$includeDynamic$iv = $this$includeDynamic;
        Navigator navigator = $this$includeDynamic$iv.getProvider().getNavigator(DynamicIncludeGraphNavigator.class);
        Intrinsics.checkExpressionValueIsNotNull(navigator, "getNavigator(clazz.java)");
        DynamicIncludeNavGraphBuilder dynamicIncludeNavGraphBuilder = new DynamicIncludeNavGraphBuilder((DynamicIncludeGraphNavigator) navigator, id, moduleName, graphResourceName);
        DynamicIncludeNavGraphBuilder dynamicIncludeNavGraphBuilder2 = dynamicIncludeNavGraphBuilder;
        $this$includeDynamic$iv.destination(dynamicIncludeNavGraphBuilder);
    }

    public static final void includeDynamic(DynamicNavGraphBuilder $this$includeDynamic, int id, String moduleName, String graphResourceName, Function1<? super DynamicIncludeNavGraphBuilder, Unit> builder) {
        Intrinsics.checkParameterIsNotNull($this$includeDynamic, "$this$includeDynamic");
        Intrinsics.checkParameterIsNotNull(moduleName, "moduleName");
        Intrinsics.checkParameterIsNotNull(graphResourceName, "graphResourceName");
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Navigator navigator = $this$includeDynamic.getProvider().getNavigator(DynamicIncludeGraphNavigator.class);
        Intrinsics.checkExpressionValueIsNotNull(navigator, "getNavigator(clazz.java)");
        DynamicIncludeNavGraphBuilder dynamicIncludeNavGraphBuilder = new DynamicIncludeNavGraphBuilder((DynamicIncludeGraphNavigator) navigator, id, moduleName, graphResourceName);
        builder.invoke(dynamicIncludeNavGraphBuilder);
        $this$includeDynamic.destination(dynamicIncludeNavGraphBuilder);
    }
}
