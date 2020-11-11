package androidx.mediarouter.media;

import android.os.Bundle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class MediaRouteProviderDescriptor {
    private static final String KEY_ROUTES = "routes";
    private static final String KEY_SUPPORTS_DYNAMIC_GROUP_ROUTE = "supportsDynamicGroupRoute";
    Bundle mBundle;
    final List<MediaRouteDescriptor> mRoutes;
    final boolean mSupportsDynamicGroupRoute;

    MediaRouteProviderDescriptor(List<MediaRouteDescriptor> routes, boolean supportsDynamicGroupRoute) {
        this.mRoutes = routes == null ? Collections.emptyList() : routes;
        this.mSupportsDynamicGroupRoute = supportsDynamicGroupRoute;
    }

    public List<MediaRouteDescriptor> getRoutes() {
        return this.mRoutes;
    }

    public boolean isValid() {
        int routeCount = getRoutes().size();
        for (int i = 0; i < routeCount; i++) {
            MediaRouteDescriptor route = this.mRoutes.get(i);
            if (route == null || !route.isValid()) {
                return false;
            }
        }
        return true;
    }

    public boolean supportsDynamicGroupRoute() {
        return this.mSupportsDynamicGroupRoute;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("MediaRouteProviderDescriptor{ ");
        result.append("routes=").append(Arrays.toString(getRoutes().toArray()));
        result.append(", isValid=").append(isValid());
        result.append(" }");
        return result.toString();
    }

    public Bundle asBundle() {
        Bundle bundle = this.mBundle;
        if (bundle != null) {
            return bundle;
        }
        this.mBundle = new Bundle();
        List<MediaRouteDescriptor> list = this.mRoutes;
        if (list != null && !list.isEmpty()) {
            int count = this.mRoutes.size();
            ArrayList<Bundle> routeBundles = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                routeBundles.add(this.mRoutes.get(i).asBundle());
            }
            this.mBundle.putParcelableArrayList(KEY_ROUTES, routeBundles);
        }
        this.mBundle.putBoolean(KEY_SUPPORTS_DYNAMIC_GROUP_ROUTE, this.mSupportsDynamicGroupRoute);
        return this.mBundle;
    }

    public static MediaRouteProviderDescriptor fromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        List<MediaRouteDescriptor> routes = null;
        ArrayList<Bundle> routeBundles = bundle.getParcelableArrayList(KEY_ROUTES);
        if (routeBundles != null && !routeBundles.isEmpty()) {
            int count = routeBundles.size();
            routes = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                routes.add(MediaRouteDescriptor.fromBundle(routeBundles.get(i)));
            }
        }
        return new MediaRouteProviderDescriptor(routes, bundle.getBoolean(KEY_SUPPORTS_DYNAMIC_GROUP_ROUTE, false));
    }

    public static final class Builder {
        private List<MediaRouteDescriptor> mRoutes;
        private boolean mSupportsDynamicGroupRoute = false;

        public Builder() {
        }

        public Builder(MediaRouteProviderDescriptor descriptor) {
            if (descriptor != null) {
                this.mRoutes = descriptor.mRoutes;
                this.mSupportsDynamicGroupRoute = descriptor.mSupportsDynamicGroupRoute;
                return;
            }
            throw new IllegalArgumentException("descriptor must not be null");
        }

        public Builder addRoute(MediaRouteDescriptor route) {
            if (route != null) {
                List<MediaRouteDescriptor> list = this.mRoutes;
                if (list == null) {
                    this.mRoutes = new ArrayList();
                } else if (list.contains(route)) {
                    throw new IllegalArgumentException("route descriptor already added");
                }
                this.mRoutes.add(route);
                return this;
            }
            throw new IllegalArgumentException("route must not be null");
        }

        public Builder addRoutes(Collection<MediaRouteDescriptor> routes) {
            if (routes != null) {
                if (!routes.isEmpty()) {
                    for (MediaRouteDescriptor route : routes) {
                        addRoute(route);
                    }
                }
                return this;
            }
            throw new IllegalArgumentException("routes must not be null");
        }

        /* access modifiers changed from: package-private */
        public Builder setRoutes(Collection<MediaRouteDescriptor> routes) {
            if (routes == null || routes.isEmpty()) {
                this.mRoutes = null;
            } else {
                this.mRoutes = new ArrayList(routes);
            }
            return this;
        }

        public Builder setSupportsDynamicGroupRoute(boolean value) {
            this.mSupportsDynamicGroupRoute = value;
            return this;
        }

        public MediaRouteProviderDescriptor build() {
            return new MediaRouteProviderDescriptor(this.mRoutes, this.mSupportsDynamicGroupRoute);
        }
    }
}
