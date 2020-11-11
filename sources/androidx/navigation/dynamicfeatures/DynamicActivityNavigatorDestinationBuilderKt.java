package androidx.navigation.dynamicfeatures;

import androidx.navigation.Navigator;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000 \n\u0000\n\u0002\u0010\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\u001a0\u0010\u0000\u001a\u00020\u0001*\u00020\u00022\b\b\u0001\u0010\u0003\u001a\u00020\u00042\u0017\u0010\u0005\u001a\u0013\u0012\u0004\u0012\u00020\u0007\u0012\u0004\u0012\u00020\u00010\u0006¢\u0006\u0002\b\bH\b¨\u0006\t"}, mo33671d2 = {"activity", "", "Landroidx/navigation/dynamicfeatures/DynamicNavGraphBuilder;", "id", "", "builder", "Lkotlin/Function1;", "Landroidx/navigation/dynamicfeatures/DynamicActivityNavigatorDestinationBuilder;", "Lkotlin/ExtensionFunctionType;", "navigation-dynamic-features-runtime_release"}, mo33672k = 2, mo33673mv = {1, 1, 16})
/* compiled from: DynamicActivityNavigatorDestinationBuilder.kt */
public final class DynamicActivityNavigatorDestinationBuilderKt {
    public static final void activity(DynamicNavGraphBuilder $this$activity, int id, Function1<? super DynamicActivityNavigatorDestinationBuilder, Unit> builder) {
        Intrinsics.checkParameterIsNotNull($this$activity, "$this$activity");
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Navigator navigator = $this$activity.getProvider().getNavigator(DynamicActivityNavigator.class);
        Intrinsics.checkExpressionValueIsNotNull(navigator, "getNavigator(clazz.java)");
        DynamicActivityNavigatorDestinationBuilder dynamicActivityNavigatorDestinationBuilder = new DynamicActivityNavigatorDestinationBuilder((DynamicActivityNavigator) navigator, id);
        builder.invoke(dynamicActivityNavigatorDestinationBuilder);
        $this$activity.destination(dynamicActivityNavigatorDestinationBuilder);
    }
}
