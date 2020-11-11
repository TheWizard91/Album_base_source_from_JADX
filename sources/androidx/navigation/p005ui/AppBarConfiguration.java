package androidx.navigation.p005ui;

import android.view.Menu;
import androidx.customview.widget.Openable;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavGraph;
import java.util.HashSet;
import java.util.Set;

/* renamed from: androidx.navigation.ui.AppBarConfiguration */
public final class AppBarConfiguration {
    private final OnNavigateUpListener mFallbackOnNavigateUpListener;
    private final Openable mOpenableLayout;
    private final Set<Integer> mTopLevelDestinations;

    /* renamed from: androidx.navigation.ui.AppBarConfiguration$OnNavigateUpListener */
    public interface OnNavigateUpListener {
        boolean onNavigateUp();
    }

    private AppBarConfiguration(Set<Integer> topLevelDestinations, Openable openableLayout, OnNavigateUpListener fallbackOnNavigateUpListener) {
        this.mTopLevelDestinations = topLevelDestinations;
        this.mOpenableLayout = openableLayout;
        this.mFallbackOnNavigateUpListener = fallbackOnNavigateUpListener;
    }

    public Set<Integer> getTopLevelDestinations() {
        return this.mTopLevelDestinations;
    }

    public Openable getOpenableLayout() {
        return this.mOpenableLayout;
    }

    @Deprecated
    public DrawerLayout getDrawerLayout() {
        Openable openable = this.mOpenableLayout;
        if (openable instanceof DrawerLayout) {
            return (DrawerLayout) openable;
        }
        return null;
    }

    public OnNavigateUpListener getFallbackOnNavigateUpListener() {
        return this.mFallbackOnNavigateUpListener;
    }

    /* renamed from: androidx.navigation.ui.AppBarConfiguration$Builder */
    public static final class Builder {
        private OnNavigateUpListener mFallbackOnNavigateUpListener;
        private Openable mOpenableLayout;
        private final Set<Integer> mTopLevelDestinations;

        public Builder(NavGraph navGraph) {
            HashSet hashSet = new HashSet();
            this.mTopLevelDestinations = hashSet;
            hashSet.add(Integer.valueOf(NavigationUI.findStartDestination(navGraph).getId()));
        }

        public Builder(Menu topLevelMenu) {
            this.mTopLevelDestinations = new HashSet();
            int size = topLevelMenu.size();
            for (int index = 0; index < size; index++) {
                this.mTopLevelDestinations.add(Integer.valueOf(topLevelMenu.getItem(index).getItemId()));
            }
        }

        public Builder(int... topLevelDestinationIds) {
            this.mTopLevelDestinations = new HashSet();
            for (int destinationId : topLevelDestinationIds) {
                this.mTopLevelDestinations.add(Integer.valueOf(destinationId));
            }
        }

        public Builder(Set<Integer> topLevelDestinationIds) {
            HashSet hashSet = new HashSet();
            this.mTopLevelDestinations = hashSet;
            hashSet.addAll(topLevelDestinationIds);
        }

        @Deprecated
        public Builder setDrawerLayout(DrawerLayout drawerLayout) {
            this.mOpenableLayout = drawerLayout;
            return this;
        }

        public Builder setOpenableLayout(Openable openableLayout) {
            this.mOpenableLayout = openableLayout;
            return this;
        }

        public Builder setFallbackOnNavigateUpListener(OnNavigateUpListener fallbackOnNavigateUpListener) {
            this.mFallbackOnNavigateUpListener = fallbackOnNavigateUpListener;
            return this;
        }

        public AppBarConfiguration build() {
            return new AppBarConfiguration(this.mTopLevelDestinations, this.mOpenableLayout, this.mFallbackOnNavigateUpListener);
        }
    }
}
