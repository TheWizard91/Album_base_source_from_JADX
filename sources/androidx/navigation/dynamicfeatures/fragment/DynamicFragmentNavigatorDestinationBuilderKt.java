package androidx.navigation.dynamicfeatures.fragment;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavGraphBuilder;
import androidx.navigation.Navigator;
import androidx.navigation.dynamicfeatures.DynamicNavGraphBuilder;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.FragmentNavigatorDestinationBuilder;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Reflection;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000,\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\u001a#\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u0006H\b\u001a<\u0010\u0000\u001a\u00020\u0001\"\n\b\u0000\u0010\u0002\u0018\u0001*\u00020\u0003*\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\b¢\u0006\u0002\b\nH\b\u001a8\u0010\u0000\u001a\u00020\u0001*\u00020\u00042\b\b\u0001\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u000b\u001a\u00020\f2\u0017\u0010\u0007\u001a\u0013\u0012\u0004\u0012\u00020\t\u0012\u0004\u0012\u00020\u00010\b¢\u0006\u0002\b\nH\b¨\u0006\r"}, mo33671d2 = {"fragment", "", "F", "Landroidx/fragment/app/Fragment;", "Landroidx/navigation/dynamicfeatures/DynamicNavGraphBuilder;", "id", "", "builder", "Lkotlin/Function1;", "Landroidx/navigation/dynamicfeatures/fragment/DynamicFragmentNavigatorDestinationBuilder;", "Lkotlin/ExtensionFunctionType;", "fragmentClassName", "", "navigation-dynamic-features-fragment_release"}, mo33672k = 2, mo33673mv = {1, 1, 16})
/* compiled from: DynamicFragmentNavigatorDestinationBuilder.kt */
public final class DynamicFragmentNavigatorDestinationBuilderKt {
    public static final /* synthetic */ <F extends Fragment> void fragment(DynamicNavGraphBuilder $this$fragment, int id) {
        Intrinsics.checkParameterIsNotNull($this$fragment, "$this$fragment");
        NavGraphBuilder $this$fragment$iv = $this$fragment;
        Navigator navigator = $this$fragment$iv.getProvider().getNavigator(FragmentNavigator.class);
        Intrinsics.checkExpressionValueIsNotNull(navigator, "getNavigator(clazz.java)");
        Intrinsics.reifiedOperationMarker(4, "F");
        FragmentNavigatorDestinationBuilder fragmentNavigatorDestinationBuilder = new FragmentNavigatorDestinationBuilder((FragmentNavigator) navigator, id, Reflection.getOrCreateKotlinClass(Fragment.class));
        FragmentNavigatorDestinationBuilder fragmentNavigatorDestinationBuilder2 = fragmentNavigatorDestinationBuilder;
        $this$fragment$iv.destination(fragmentNavigatorDestinationBuilder);
    }

    public static final /* synthetic */ <F extends Fragment> void fragment(DynamicNavGraphBuilder $this$fragment, int id, Function1<? super DynamicFragmentNavigatorDestinationBuilder, Unit> builder) {
        Intrinsics.checkParameterIsNotNull($this$fragment, "$this$fragment");
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Intrinsics.reifiedOperationMarker(4, "F");
        String fragmentClassName$iv = Fragment.class.getName();
        Intrinsics.checkExpressionValueIsNotNull(fragmentClassName$iv, "F::class.java.name");
        DynamicNavGraphBuilder $this$fragment$iv = $this$fragment;
        Navigator navigator = $this$fragment$iv.getProvider().getNavigator(DynamicFragmentNavigator.class);
        Intrinsics.checkExpressionValueIsNotNull(navigator, "getNavigator(clazz.java)");
        DynamicFragmentNavigatorDestinationBuilder dynamicFragmentNavigatorDestinationBuilder = new DynamicFragmentNavigatorDestinationBuilder((DynamicFragmentNavigator) navigator, id, fragmentClassName$iv);
        builder.invoke(dynamicFragmentNavigatorDestinationBuilder);
        $this$fragment$iv.destination(dynamicFragmentNavigatorDestinationBuilder);
    }

    public static final void fragment(DynamicNavGraphBuilder $this$fragment, int id, String fragmentClassName, Function1<? super DynamicFragmentNavigatorDestinationBuilder, Unit> builder) {
        Intrinsics.checkParameterIsNotNull($this$fragment, "$this$fragment");
        Intrinsics.checkParameterIsNotNull(fragmentClassName, "fragmentClassName");
        Intrinsics.checkParameterIsNotNull(builder, "builder");
        Navigator navigator = $this$fragment.getProvider().getNavigator(DynamicFragmentNavigator.class);
        Intrinsics.checkExpressionValueIsNotNull(navigator, "getNavigator(clazz.java)");
        DynamicFragmentNavigatorDestinationBuilder dynamicFragmentNavigatorDestinationBuilder = new DynamicFragmentNavigatorDestinationBuilder((DynamicFragmentNavigator) navigator, id, fragmentClassName);
        builder.invoke(dynamicFragmentNavigatorDestinationBuilder);
        $this$fragment.destination(dynamicFragmentNavigatorDestinationBuilder);
    }
}
