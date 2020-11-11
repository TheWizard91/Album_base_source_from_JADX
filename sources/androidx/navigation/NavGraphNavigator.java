package androidx.navigation;

import android.os.Bundle;
import androidx.navigation.Navigator;

@Navigator.Name("navigation")
public class NavGraphNavigator extends Navigator<NavGraph> {
    private final NavigatorProvider mNavigatorProvider;

    public NavGraphNavigator(NavigatorProvider navigatorProvider) {
        this.mNavigatorProvider = navigatorProvider;
    }

    public NavGraph createDestination() {
        return new NavGraph(this);
    }

    public NavDestination navigate(NavGraph destination, Bundle args, NavOptions navOptions, Navigator.Extras navigatorExtras) {
        int startId = destination.getStartDestination();
        if (startId != 0) {
            NavDestination startDestination = destination.findNode(startId, false);
            if (startDestination != null) {
                return this.mNavigatorProvider.getNavigator(startDestination.getNavigatorName()).navigate(startDestination, startDestination.addInDefaultArgs(args), navOptions, navigatorExtras);
            }
            throw new IllegalArgumentException("navigation destination " + destination.getStartDestDisplayName() + " is not a direct child of this NavGraph");
        }
        throw new IllegalStateException("no start destination defined via app:startDestination for " + destination.getDisplayName());
    }

    public boolean popBackStack() {
        return true;
    }
}
