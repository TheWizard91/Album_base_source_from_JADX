package androidx.mediarouter.media;

import android.media.MediaRouter;

final class MediaRouterApi24 {

    public static final class RouteInfo {
        public static int getDeviceType(Object routeObj) {
            return ((MediaRouter.RouteInfo) routeObj).getDeviceType();
        }

        private RouteInfo() {
        }
    }

    private MediaRouterApi24() {
    }
}
