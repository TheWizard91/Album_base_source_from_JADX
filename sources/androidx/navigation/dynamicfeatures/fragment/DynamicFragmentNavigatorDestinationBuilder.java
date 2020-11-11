package androidx.navigation.dynamicfeatures.fragment;

import androidx.navigation.NavDestination;
import androidx.navigation.NavDestinationBuilder;
import androidx.navigation.NavDestinationDsl;
import androidx.navigation.dynamicfeatures.fragment.DynamicFragmentNavigator;
import androidx.navigation.fragment.FragmentNavigator;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B\u001f\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\b\u0010\u000f\u001a\u00020\u0010H\u0016R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u001c\u0010\n\u001a\u0004\u0018\u00010\bX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000e¨\u0006\u0011"}, mo33671d2 = {"Landroidx/navigation/dynamicfeatures/fragment/DynamicFragmentNavigatorDestinationBuilder;", "Landroidx/navigation/NavDestinationBuilder;", "Landroidx/navigation/fragment/FragmentNavigator$Destination;", "navigator", "Landroidx/navigation/dynamicfeatures/fragment/DynamicFragmentNavigator;", "id", "", "fragmentClassName", "", "(Landroidx/navigation/dynamicfeatures/fragment/DynamicFragmentNavigator;ILjava/lang/String;)V", "moduleName", "getModuleName", "()Ljava/lang/String;", "setModuleName", "(Ljava/lang/String;)V", "build", "Landroidx/navigation/dynamicfeatures/fragment/DynamicFragmentNavigator$Destination;", "navigation-dynamic-features-fragment_release"}, mo33672k = 1, mo33673mv = {1, 1, 16})
@NavDestinationDsl
/* compiled from: DynamicFragmentNavigatorDestinationBuilder.kt */
public final class DynamicFragmentNavigatorDestinationBuilder extends NavDestinationBuilder<FragmentNavigator.Destination> {
    private final String fragmentClassName;
    private String moduleName;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    public DynamicFragmentNavigatorDestinationBuilder(DynamicFragmentNavigator navigator, int id, String fragmentClassName2) {
        super(navigator, id);
        Intrinsics.checkParameterIsNotNull(navigator, "navigator");
        Intrinsics.checkParameterIsNotNull(fragmentClassName2, "fragmentClassName");
        this.fragmentClassName = fragmentClassName2;
    }

    public final String getModuleName() {
        return this.moduleName;
    }

    public final void setModuleName(String str) {
        this.moduleName = str;
    }

    public DynamicFragmentNavigator.Destination build() {
        NavDestination build = super.build();
        if (build != null) {
            DynamicFragmentNavigator.Destination destination = (DynamicFragmentNavigator.Destination) build;
            DynamicFragmentNavigator.Destination destination2 = destination;
            destination2.setClassName(this.fragmentClassName);
            destination2.setModuleName(this.moduleName);
            return destination;
        }
        throw new TypeCastException("null cannot be cast to non-null type androidx.navigation.dynamicfeatures.fragment.DynamicFragmentNavigator.Destination");
    }
}
