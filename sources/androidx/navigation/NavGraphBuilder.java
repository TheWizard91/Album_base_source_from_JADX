package androidx.navigation;

import com.google.firebase.analytics.FirebaseAnalytics;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010!\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0002\b\u0006\b\u0017\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001B!\u0012\u0006\u0010\u0003\u001a\u00020\u0004\u0012\b\b\u0001\u0010\u0005\u001a\u00020\u0006\u0012\b\b\u0001\u0010\u0007\u001a\u00020\u0006¢\u0006\u0002\u0010\bJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u000bJ\b\u0010\u0011\u001a\u00020\u0002H\u0016J\u001e\u0010\u0010\u001a\u00020\u000f\"\b\b\u0000\u0010\u0012*\u00020\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u0002H\u00120\u0001J\r\u0010\u0014\u001a\u00020\u000f*\u00020\u000bH\u0002R\u0014\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u000b0\nX\u0004¢\u0006\u0002\n\u0000R\u0011\u0010\u0003\u001a\u00020\u0004¢\u0006\b\n\u0000\u001a\u0004\b\f\u0010\rR\u000e\u0010\u0007\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000¨\u0006\u0015"}, mo33671d2 = {"Landroidx/navigation/NavGraphBuilder;", "Landroidx/navigation/NavDestinationBuilder;", "Landroidx/navigation/NavGraph;", "provider", "Landroidx/navigation/NavigatorProvider;", "id", "", "startDestination", "(Landroidx/navigation/NavigatorProvider;II)V", "destinations", "", "Landroidx/navigation/NavDestination;", "getProvider", "()Landroidx/navigation/NavigatorProvider;", "addDestination", "", "destination", "build", "D", "navDestination", "unaryPlus", "navigation-common-ktx_release"}, mo33672k = 1, mo33673mv = {1, 1, 16})
@NavDestinationDsl
/* compiled from: NavGraphBuilder.kt */
public class NavGraphBuilder extends NavDestinationBuilder<NavGraph> {
    private final List<NavDestination> destinations = new ArrayList();
    private final NavigatorProvider provider;
    private int startDestination;

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public NavGraphBuilder(androidx.navigation.NavigatorProvider r6, int r7, int r8) {
        /*
            r5 = this;
            java.lang.String r0 = "provider"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r6, r0)
            java.lang.Class<androidx.navigation.NavGraphNavigator> r0 = androidx.navigation.NavGraphNavigator.class
            r1 = r6
            r2 = 0
            androidx.navigation.Navigator r3 = r1.getNavigator(r0)
            java.lang.String r4 = "getNavigator(clazz.java)"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r3, r4)
            r5.<init>(r3, r7)
            r5.provider = r6
            r5.startDestination = r8
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            java.util.List r0 = (java.util.List) r0
            r5.destinations = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.navigation.NavGraphBuilder.<init>(androidx.navigation.NavigatorProvider, int, int):void");
    }

    public final NavigatorProvider getProvider() {
        return this.provider;
    }

    public final <D extends NavDestination> void destination(NavDestinationBuilder<? extends D> navDestination) {
        Intrinsics.checkParameterIsNotNull(navDestination, "navDestination");
        this.destinations.add(navDestination.build());
    }

    public final void unaryPlus(NavDestination $this$unaryPlus) {
        Intrinsics.checkParameterIsNotNull($this$unaryPlus, "$this$unaryPlus");
        addDestination($this$unaryPlus);
    }

    public final void addDestination(NavDestination destination) {
        Intrinsics.checkParameterIsNotNull(destination, FirebaseAnalytics.Param.DESTINATION);
        this.destinations.add(destination);
    }

    public NavGraph build() {
        NavDestination build = super.build();
        NavGraph navGraph = (NavGraph) build;
        navGraph.addDestinations((Collection<NavDestination>) this.destinations);
        int i = this.startDestination;
        if (i != 0) {
            navGraph.setStartDestination(i);
            return (NavGraph) build;
        }
        throw new IllegalStateException("You must set a startDestination");
    }
}
