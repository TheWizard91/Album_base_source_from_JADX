package androidx.mediarouter.media;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.p000v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.Display;
import androidx.core.app.ActivityManagerCompat;
import androidx.core.content.ContextCompat;
import androidx.core.hardware.display.DisplayManagerCompat;
import androidx.core.util.ObjectsCompat;
import androidx.core.util.Pair;
import androidx.media.VolumeProviderCompat;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.RegisteredMediaRouteProviderWatcher;
import androidx.mediarouter.media.RemoteControlClientCompat;
import androidx.mediarouter.media.SystemMediaRouteProvider;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public final class MediaRouter {
    public static final int AVAILABILITY_FLAG_IGNORE_DEFAULT_ROUTE = 1;
    public static final int AVAILABILITY_FLAG_REQUIRE_MATCH = 2;
    public static final int CALLBACK_FLAG_FORCE_DISCOVERY = 8;
    public static final int CALLBACK_FLAG_PERFORM_ACTIVE_SCAN = 1;
    public static final int CALLBACK_FLAG_REQUEST_DISCOVERY = 4;
    public static final int CALLBACK_FLAG_UNFILTERED_EVENTS = 2;
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final String TAG = "MediaRouter";
    public static final int UNSELECT_REASON_DISCONNECTED = 1;
    public static final int UNSELECT_REASON_ROUTE_CHANGED = 3;
    public static final int UNSELECT_REASON_STOPPED = 2;
    public static final int UNSELECT_REASON_UNKNOWN = 0;
    static GlobalMediaRouter sGlobal;
    final ArrayList<CallbackRecord> mCallbackRecords = new ArrayList<>();
    final Context mContext;

    MediaRouter(Context context) {
        this.mContext = context;
    }

    public static MediaRouter getInstance(Context context) {
        if (context != null) {
            checkCallingThread();
            if (sGlobal == null) {
                GlobalMediaRouter globalMediaRouter = new GlobalMediaRouter(context.getApplicationContext());
                sGlobal = globalMediaRouter;
                globalMediaRouter.start();
            }
            return sGlobal.getRouter(context);
        }
        throw new IllegalArgumentException("context must not be null");
    }

    public List<RouteInfo> getRoutes() {
        checkCallingThread();
        return sGlobal.getRoutes();
    }

    /* access modifiers changed from: package-private */
    public RouteInfo getRoute(String uniqueId) {
        checkCallingThread();
        return sGlobal.getRoute(uniqueId);
    }

    public List<ProviderInfo> getProviders() {
        checkCallingThread();
        return sGlobal.getProviders();
    }

    public RouteInfo getDefaultRoute() {
        checkCallingThread();
        return sGlobal.getDefaultRoute();
    }

    public RouteInfo getBluetoothRoute() {
        checkCallingThread();
        return sGlobal.getBluetoothRoute();
    }

    public RouteInfo getSelectedRoute() {
        checkCallingThread();
        return sGlobal.getSelectedRoute();
    }

    public RouteInfo updateSelectedRoute(MediaRouteSelector selector) {
        if (selector != null) {
            checkCallingThread();
            if (DEBUG) {
                Log.d(TAG, "updateSelectedRoute: " + selector);
            }
            RouteInfo route = sGlobal.getSelectedRoute();
            if (route.isDefaultOrBluetooth() || route.matchesSelector(selector)) {
                return route;
            }
            RouteInfo route2 = sGlobal.chooseFallbackRoute();
            sGlobal.selectRoute(route2);
            return route2;
        }
        throw new IllegalArgumentException("selector must not be null");
    }

    public void selectRoute(RouteInfo route) {
        if (route != null) {
            checkCallingThread();
            if (DEBUG) {
                Log.d(TAG, "selectRoute: " + route);
            }
            sGlobal.selectRoute(route);
            return;
        }
        throw new IllegalArgumentException("route must not be null");
    }

    public void unselect(int reason) {
        if (reason < 0 || reason > 3) {
            throw new IllegalArgumentException("Unsupported reason to unselect route");
        }
        checkCallingThread();
        RouteInfo fallbackRoute = sGlobal.chooseFallbackRoute();
        if (sGlobal.getSelectedRoute() != fallbackRoute) {
            sGlobal.selectRoute(fallbackRoute, reason);
            return;
        }
        GlobalMediaRouter globalMediaRouter = sGlobal;
        globalMediaRouter.selectRoute(globalMediaRouter.getDefaultRoute(), reason);
    }

    public void addMemberToDynamicGroup(RouteInfo route) {
        checkCallingThread();
        sGlobal.addMemberToDynamicGroup(route);
    }

    public void removeMemberFromDynamicGroup(RouteInfo route) {
        checkCallingThread();
        sGlobal.removeMemberFromDynamicGroup(route);
    }

    public boolean isRouteAvailable(MediaRouteSelector selector, int flags) {
        if (selector != null) {
            checkCallingThread();
            return sGlobal.isRouteAvailable(selector, flags);
        }
        throw new IllegalArgumentException("selector must not be null");
    }

    public void addCallback(MediaRouteSelector selector, Callback callback) {
        addCallback(selector, callback, 0);
    }

    public void addCallback(MediaRouteSelector selector, Callback callback, int flags) {
        CallbackRecord record;
        if (selector == null) {
            throw new IllegalArgumentException("selector must not be null");
        } else if (callback != null) {
            checkCallingThread();
            if (DEBUG) {
                Log.d(TAG, "addCallback: selector=" + selector + ", callback=" + callback + ", flags=" + Integer.toHexString(flags));
            }
            int index = findCallbackRecord(callback);
            if (index < 0) {
                record = new CallbackRecord(this, callback);
                this.mCallbackRecords.add(record);
            } else {
                record = this.mCallbackRecords.get(index);
            }
            boolean updateNeeded = false;
            if (((~record.mFlags) & flags) != 0) {
                record.mFlags |= flags;
                updateNeeded = true;
            }
            if (!record.mSelector.contains(selector)) {
                record.mSelector = new MediaRouteSelector.Builder(record.mSelector).addSelector(selector).build();
                updateNeeded = true;
            }
            if (updateNeeded) {
                sGlobal.updateDiscoveryRequest();
            }
        } else {
            throw new IllegalArgumentException("callback must not be null");
        }
    }

    public void removeCallback(Callback callback) {
        if (callback != null) {
            checkCallingThread();
            if (DEBUG) {
                Log.d(TAG, "removeCallback: callback=" + callback);
            }
            int index = findCallbackRecord(callback);
            if (index >= 0) {
                this.mCallbackRecords.remove(index);
                sGlobal.updateDiscoveryRequest();
                return;
            }
            return;
        }
        throw new IllegalArgumentException("callback must not be null");
    }

    private int findCallbackRecord(Callback callback) {
        int count = this.mCallbackRecords.size();
        for (int i = 0; i < count; i++) {
            if (this.mCallbackRecords.get(i).mCallback == callback) {
                return i;
            }
        }
        return -1;
    }

    public void addProvider(MediaRouteProvider providerInstance) {
        if (providerInstance != null) {
            checkCallingThread();
            if (DEBUG) {
                Log.d(TAG, "addProvider: " + providerInstance);
            }
            sGlobal.addProvider(providerInstance);
            return;
        }
        throw new IllegalArgumentException("providerInstance must not be null");
    }

    public void removeProvider(MediaRouteProvider providerInstance) {
        if (providerInstance != null) {
            checkCallingThread();
            if (DEBUG) {
                Log.d(TAG, "removeProvider: " + providerInstance);
            }
            sGlobal.removeProvider(providerInstance);
            return;
        }
        throw new IllegalArgumentException("providerInstance must not be null");
    }

    public void addRemoteControlClient(Object remoteControlClient) {
        if (remoteControlClient != null) {
            checkCallingThread();
            if (DEBUG) {
                Log.d(TAG, "addRemoteControlClient: " + remoteControlClient);
            }
            sGlobal.addRemoteControlClient(remoteControlClient);
            return;
        }
        throw new IllegalArgumentException("remoteControlClient must not be null");
    }

    public void removeRemoteControlClient(Object remoteControlClient) {
        if (remoteControlClient != null) {
            if (DEBUG) {
                Log.d(TAG, "removeRemoteControlClient: " + remoteControlClient);
            }
            sGlobal.removeRemoteControlClient(remoteControlClient);
            return;
        }
        throw new IllegalArgumentException("remoteControlClient must not be null");
    }

    public void setMediaSession(Object mediaSession) {
        if (DEBUG) {
            Log.d(TAG, "addMediaSession: " + mediaSession);
        }
        sGlobal.setMediaSession(mediaSession);
    }

    public void setMediaSessionCompat(MediaSessionCompat mediaSession) {
        if (DEBUG) {
            Log.d(TAG, "addMediaSessionCompat: " + mediaSession);
        }
        sGlobal.setMediaSessionCompat(mediaSession);
    }

    public MediaSessionCompat.Token getMediaSessionToken() {
        return sGlobal.getMediaSessionToken();
    }

    static void checkCallingThread() {
        if (Looper.myLooper() != Looper.getMainLooper()) {
            throw new IllegalStateException("The media router service must only be accessed on the application's main thread.");
        }
    }

    public static class RouteInfo {
        static final int CHANGE_GENERAL = 1;
        static final int CHANGE_PRESENTATION_DISPLAY = 4;
        static final int CHANGE_VOLUME = 2;
        public static final int CONNECTION_STATE_CONNECTED = 2;
        public static final int CONNECTION_STATE_CONNECTING = 1;
        public static final int CONNECTION_STATE_DISCONNECTED = 0;
        public static final int DEVICE_TYPE_BLUETOOTH = 3;
        public static final int DEVICE_TYPE_SPEAKER = 2;
        public static final int DEVICE_TYPE_TV = 1;
        public static final int DEVICE_TYPE_UNKNOWN = 0;
        public static final int PLAYBACK_TYPE_LOCAL = 0;
        public static final int PLAYBACK_TYPE_REMOTE = 1;
        public static final int PLAYBACK_VOLUME_FIXED = 0;
        public static final int PLAYBACK_VOLUME_VARIABLE = 1;
        public static final int PRESENTATION_DISPLAY_ID_NONE = -1;
        static final String SYSTEM_MEDIA_ROUTE_PROVIDER_PACKAGE_NAME = "android";
        private boolean mCanDisconnect;
        private int mConnectionState;
        private final ArrayList<IntentFilter> mControlFilters = new ArrayList<>();
        private String mDescription;
        MediaRouteDescriptor mDescriptor;
        final String mDescriptorId;
        private int mDeviceType;
        MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor mDynamicDescriptor;
        private DynamicGroupState mDynamicGroupState;
        boolean mEnabled;
        private Bundle mExtras;
        private Uri mIconUri;
        private List<RouteInfo> mMemberRoutes = new ArrayList();
        private String mName;
        private int mPlaybackStream;
        private int mPlaybackType;
        private Display mPresentationDisplay;
        private int mPresentationDisplayId = -1;
        private final ProviderInfo mProvider;
        private IntentSender mSettingsIntent;
        final String mUniqueId;
        private int mVolume;
        private int mVolumeHandling;
        private int mVolumeMax;

        RouteInfo(ProviderInfo provider, String descriptorId, String uniqueId) {
            this.mProvider = provider;
            this.mDescriptorId = descriptorId;
            this.mUniqueId = uniqueId;
        }

        public ProviderInfo getProvider() {
            return this.mProvider;
        }

        public String getId() {
            return this.mUniqueId;
        }

        public String getName() {
            return this.mName;
        }

        public String getDescription() {
            return this.mDescription;
        }

        public Uri getIconUri() {
            return this.mIconUri;
        }

        public boolean isEnabled() {
            return this.mEnabled;
        }

        @Deprecated
        public boolean isConnecting() {
            return this.mConnectionState == 1;
        }

        public int getConnectionState() {
            return this.mConnectionState;
        }

        public boolean isSelected() {
            MediaRouter.checkCallingThread();
            return MediaRouter.sGlobal.getSelectedRoute() == this;
        }

        public boolean isDefault() {
            MediaRouter.checkCallingThread();
            return MediaRouter.sGlobal.getDefaultRoute() == this;
        }

        public boolean isBluetooth() {
            MediaRouter.checkCallingThread();
            return MediaRouter.sGlobal.getBluetoothRoute() == this;
        }

        public boolean isDeviceSpeaker() {
            return isDefault() && Resources.getSystem().getText(Resources.getSystem().getIdentifier("default_audio_route_name", "string", "android")).equals(this.mName);
        }

        public List<IntentFilter> getControlFilters() {
            return this.mControlFilters;
        }

        public boolean matchesSelector(MediaRouteSelector selector) {
            if (selector != null) {
                MediaRouter.checkCallingThread();
                return selector.matchesControlFilters(this.mControlFilters);
            }
            throw new IllegalArgumentException("selector must not be null");
        }

        public boolean supportsControlCategory(String category) {
            if (category != null) {
                MediaRouter.checkCallingThread();
                int count = this.mControlFilters.size();
                for (int i = 0; i < count; i++) {
                    if (this.mControlFilters.get(i).hasCategory(category)) {
                        return true;
                    }
                }
                return false;
            }
            throw new IllegalArgumentException("category must not be null");
        }

        public boolean supportsControlAction(String category, String action) {
            if (category == null) {
                throw new IllegalArgumentException("category must not be null");
            } else if (action != null) {
                MediaRouter.checkCallingThread();
                int count = this.mControlFilters.size();
                for (int i = 0; i < count; i++) {
                    IntentFilter filter = this.mControlFilters.get(i);
                    if (filter.hasCategory(category) && filter.hasAction(action)) {
                        return true;
                    }
                }
                return false;
            } else {
                throw new IllegalArgumentException("action must not be null");
            }
        }

        public boolean supportsControlRequest(Intent intent) {
            if (intent != null) {
                MediaRouter.checkCallingThread();
                ContentResolver contentResolver = MediaRouter.sGlobal.getContentResolver();
                int count = this.mControlFilters.size();
                for (int i = 0; i < count; i++) {
                    if (this.mControlFilters.get(i).match(contentResolver, intent, true, MediaRouter.TAG) >= 0) {
                        return true;
                    }
                }
                return false;
            }
            throw new IllegalArgumentException("intent must not be null");
        }

        public void sendControlRequest(Intent intent, ControlRequestCallback callback) {
            if (intent != null) {
                MediaRouter.checkCallingThread();
                MediaRouter.sGlobal.sendControlRequest(this, intent, callback);
                return;
            }
            throw new IllegalArgumentException("intent must not be null");
        }

        public int getPlaybackType() {
            return this.mPlaybackType;
        }

        public int getPlaybackStream() {
            return this.mPlaybackStream;
        }

        public int getDeviceType() {
            return this.mDeviceType;
        }

        public boolean isDefaultOrBluetooth() {
            if (isDefault() || this.mDeviceType == 3) {
                return true;
            }
            if (!isSystemMediaRouteProvider(this) || !supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO) || supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO)) {
                return false;
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public boolean isSelectable() {
            return this.mDescriptor != null && this.mEnabled;
        }

        private static boolean isSystemMediaRouteProvider(RouteInfo route) {
            return TextUtils.equals(route.getProviderInstance().getMetadata().getPackageName(), "android");
        }

        public int getVolumeHandling() {
            return this.mVolumeHandling;
        }

        public int getVolume() {
            return this.mVolume;
        }

        public int getVolumeMax() {
            return this.mVolumeMax;
        }

        public boolean canDisconnect() {
            return this.mCanDisconnect;
        }

        public void requestSetVolume(int volume) {
            MediaRouter.checkCallingThread();
            MediaRouter.sGlobal.requestSetVolume(this, Math.min(this.mVolumeMax, Math.max(0, volume)));
        }

        public void requestUpdateVolume(int delta) {
            MediaRouter.checkCallingThread();
            if (delta != 0) {
                MediaRouter.sGlobal.requestUpdateVolume(this, delta);
            }
        }

        public Display getPresentationDisplay() {
            MediaRouter.checkCallingThread();
            if (this.mPresentationDisplayId >= 0 && this.mPresentationDisplay == null) {
                this.mPresentationDisplay = MediaRouter.sGlobal.getDisplay(this.mPresentationDisplayId);
            }
            return this.mPresentationDisplay;
        }

        public int getPresentationDisplayId() {
            return this.mPresentationDisplayId;
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public IntentSender getSettingsIntent() {
            return this.mSettingsIntent;
        }

        public void select() {
            MediaRouter.checkCallingThread();
            MediaRouter.sGlobal.selectRoute(this);
        }

        public boolean isGroup() {
            return getMemberRoutes().size() >= 1;
        }

        public DynamicGroupState getDynamicGroupState() {
            if (this.mDynamicGroupState == null && this.mDynamicDescriptor != null) {
                this.mDynamicGroupState = new DynamicGroupState();
            }
            return this.mDynamicGroupState;
        }

        public List<RouteInfo> getMemberRoutes() {
            return Collections.unmodifiableList(this.mMemberRoutes);
        }

        public MediaRouteProvider.DynamicGroupRouteController getDynamicGroupController() {
            MediaRouteProvider.RouteController controller = MediaRouter.sGlobal.mSelectedRouteController;
            if (controller instanceof MediaRouteProvider.DynamicGroupRouteController) {
                return (MediaRouteProvider.DynamicGroupRouteController) controller;
            }
            return null;
        }

        public String toString() {
            if (!isGroup()) {
                return "MediaRouter.RouteInfo{ uniqueId=" + this.mUniqueId + ", name=" + this.mName + ", description=" + this.mDescription + ", iconUri=" + this.mIconUri + ", enabled=" + this.mEnabled + ", connectionState=" + this.mConnectionState + ", canDisconnect=" + this.mCanDisconnect + ", playbackType=" + this.mPlaybackType + ", playbackStream=" + this.mPlaybackStream + ", deviceType=" + this.mDeviceType + ", volumeHandling=" + this.mVolumeHandling + ", volume=" + this.mVolume + ", volumeMax=" + this.mVolumeMax + ", presentationDisplayId=" + this.mPresentationDisplayId + ", extras=" + this.mExtras + ", settingsIntent=" + this.mSettingsIntent + ", providerPackageName=" + this.mProvider.getPackageName() + " }";
            }
            StringBuilder sb = new StringBuilder(super.toString());
            sb.append('[');
            int count = this.mMemberRoutes.size();
            for (int i = 0; i < count; i++) {
                if (i > 0) {
                    sb.append(", ");
                }
                sb.append(this.mMemberRoutes.get(i));
            }
            sb.append(']');
            return sb.toString();
        }

        /* access modifiers changed from: package-private */
        public int maybeUpdateDescriptor(MediaRouteDescriptor descriptor) {
            if (this.mDescriptor != descriptor) {
                return updateDescriptor(descriptor);
            }
            return 0;
        }

        private boolean isSameControlFilters(List<IntentFilter> filters1, List<IntentFilter> filters2) {
            if (filters1 == filters2) {
                return true;
            }
            if (filters1 == null || filters2 == null) {
                return false;
            }
            ListIterator<IntentFilter> e1 = filters1.listIterator();
            ListIterator<IntentFilter> e2 = filters2.listIterator();
            while (e1.hasNext() && e2.hasNext()) {
                if (!isSameControlFilter(e1.next(), e2.next())) {
                    return false;
                }
            }
            if (e1.hasNext() || e2.hasNext()) {
                return false;
            }
            return true;
        }

        private boolean isSameControlFilter(IntentFilter filter1, IntentFilter filter2) {
            int countActions;
            if (filter1 == filter2) {
                return true;
            }
            if (filter1 == null || filter2 == null || (countActions = filter1.countActions()) != filter2.countActions()) {
                return false;
            }
            for (int i = 0; i < countActions; i++) {
                if (!filter1.getAction(i).equals(filter2.getAction(i))) {
                    return false;
                }
            }
            int countCategories = filter1.countCategories();
            if (countCategories != filter2.countCategories()) {
                return false;
            }
            for (int i2 = 0; i2 < countCategories; i2++) {
                if (!filter1.getCategory(i2).equals(filter2.getCategory(i2))) {
                    return false;
                }
            }
            return true;
        }

        /* access modifiers changed from: package-private */
        public int updateDescriptor(MediaRouteDescriptor descriptor) {
            int changes = 0;
            this.mDescriptor = descriptor;
            if (descriptor == null) {
                return 0;
            }
            if (!ObjectsCompat.equals(this.mName, descriptor.getName())) {
                this.mName = descriptor.getName();
                changes = 0 | 1;
            }
            if (!ObjectsCompat.equals(this.mDescription, descriptor.getDescription())) {
                this.mDescription = descriptor.getDescription();
                changes |= 1;
            }
            if (!ObjectsCompat.equals(this.mIconUri, descriptor.getIconUri())) {
                this.mIconUri = descriptor.getIconUri();
                changes |= 1;
            }
            if (this.mEnabled != descriptor.isEnabled()) {
                this.mEnabled = descriptor.isEnabled();
                changes |= 1;
            }
            if (this.mConnectionState != descriptor.getConnectionState()) {
                this.mConnectionState = descriptor.getConnectionState();
                changes |= 1;
            }
            if (!isSameControlFilters(this.mControlFilters, descriptor.getControlFilters())) {
                this.mControlFilters.clear();
                this.mControlFilters.addAll(descriptor.getControlFilters());
                changes |= 1;
            }
            if (this.mPlaybackType != descriptor.getPlaybackType()) {
                this.mPlaybackType = descriptor.getPlaybackType();
                changes |= 1;
            }
            if (this.mPlaybackStream != descriptor.getPlaybackStream()) {
                this.mPlaybackStream = descriptor.getPlaybackStream();
                changes |= 1;
            }
            if (this.mDeviceType != descriptor.getDeviceType()) {
                this.mDeviceType = descriptor.getDeviceType();
                changes |= 1;
            }
            if (this.mVolumeHandling != descriptor.getVolumeHandling()) {
                this.mVolumeHandling = descriptor.getVolumeHandling();
                changes |= 3;
            }
            if (this.mVolume != descriptor.getVolume()) {
                this.mVolume = descriptor.getVolume();
                changes |= 3;
            }
            if (this.mVolumeMax != descriptor.getVolumeMax()) {
                this.mVolumeMax = descriptor.getVolumeMax();
                changes |= 3;
            }
            if (this.mPresentationDisplayId != descriptor.getPresentationDisplayId()) {
                this.mPresentationDisplayId = descriptor.getPresentationDisplayId();
                this.mPresentationDisplay = null;
                changes |= 5;
            }
            if (!ObjectsCompat.equals(this.mExtras, descriptor.getExtras())) {
                this.mExtras = descriptor.getExtras();
                changes |= 1;
            }
            if (!ObjectsCompat.equals(this.mSettingsIntent, descriptor.getSettingsActivity())) {
                this.mSettingsIntent = descriptor.getSettingsActivity();
                changes |= 1;
            }
            if (this.mCanDisconnect != descriptor.canDisconnectAndKeepPlaying()) {
                this.mCanDisconnect = descriptor.canDisconnectAndKeepPlaying();
                changes |= 5;
            }
            boolean memberChanged = false;
            List<String> groupMemberIds = descriptor.getGroupMemberIds();
            List<RouteInfo> routes = new ArrayList<>();
            if (groupMemberIds.size() != this.mMemberRoutes.size()) {
                memberChanged = true;
            }
            for (String groupMemberId : groupMemberIds) {
                RouteInfo groupMember = MediaRouter.sGlobal.getRoute(MediaRouter.sGlobal.getUniqueId(getProvider(), groupMemberId));
                if (groupMember != null) {
                    routes.add(groupMember);
                    if (!memberChanged && !this.mMemberRoutes.contains(groupMember)) {
                        memberChanged = true;
                    }
                }
            }
            if (!memberChanged) {
                return changes;
            }
            this.mMemberRoutes = routes;
            return changes | 1;
        }

        /* access modifiers changed from: package-private */
        public String getDescriptorId() {
            return this.mDescriptorId;
        }

        public MediaRouteProvider getProviderInstance() {
            return this.mProvider.getProviderInstance();
        }

        /* access modifiers changed from: package-private */
        public void updateDescriptors(Collection<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> dynamicDescriptors) {
            this.mMemberRoutes.clear();
            for (MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicDescriptor : dynamicDescriptors) {
                RouteInfo route = findRouteByDynamicRouteDescriptor(dynamicDescriptor);
                if (route != null) {
                    route.mDynamicDescriptor = dynamicDescriptor;
                    if (dynamicDescriptor.getSelectionState() == 2 || dynamicDescriptor.getSelectionState() == 3) {
                        this.mMemberRoutes.add(route);
                    }
                }
            }
            MediaRouter.sGlobal.mCallbackHandler.post(GlobalMediaRouter.CallbackHandler.MSG_ROUTE_CHANGED, this);
        }

        /* access modifiers changed from: package-private */
        public RouteInfo findRouteByDynamicRouteDescriptor(MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor dynamicDescriptor) {
            return getProvider().findRouteByDescriptorId(dynamicDescriptor.getRouteDescriptor().getId());
        }

        public class DynamicGroupState {
            public DynamicGroupState() {
            }

            public int getSelectionState() {
                if (RouteInfo.this.mDynamicDescriptor != null) {
                    return RouteInfo.this.mDynamicDescriptor.getSelectionState();
                }
                return 1;
            }

            public boolean isUnselectable() {
                return RouteInfo.this.mDynamicDescriptor == null || RouteInfo.this.mDynamicDescriptor.isUnselectable();
            }

            public boolean isGroupable() {
                return RouteInfo.this.mDynamicDescriptor != null && RouteInfo.this.mDynamicDescriptor.isGroupable();
            }

            public boolean isTransferable() {
                return RouteInfo.this.mDynamicDescriptor != null && RouteInfo.this.mDynamicDescriptor.isTransferable();
            }
        }
    }

    public static final class ProviderInfo {
        private MediaRouteProviderDescriptor mDescriptor;
        private final MediaRouteProvider.ProviderMetadata mMetadata;
        final MediaRouteProvider mProviderInstance;
        private Resources mResources;
        private boolean mResourcesNotAvailable;
        final List<RouteInfo> mRoutes = new ArrayList();

        ProviderInfo(MediaRouteProvider provider) {
            this.mProviderInstance = provider;
            this.mMetadata = provider.getMetadata();
        }

        public MediaRouteProvider getProviderInstance() {
            MediaRouter.checkCallingThread();
            return this.mProviderInstance;
        }

        public String getPackageName() {
            return this.mMetadata.getPackageName();
        }

        public ComponentName getComponentName() {
            return this.mMetadata.getComponentName();
        }

        public List<RouteInfo> getRoutes() {
            MediaRouter.checkCallingThread();
            return Collections.unmodifiableList(this.mRoutes);
        }

        /* access modifiers changed from: package-private */
        public Resources getResources() {
            if (this.mResources == null && !this.mResourcesNotAvailable) {
                String packageName = getPackageName();
                Context context = MediaRouter.sGlobal.getProviderContext(packageName);
                if (context != null) {
                    this.mResources = context.getResources();
                } else {
                    Log.w(MediaRouter.TAG, "Unable to obtain resources for route provider package: " + packageName);
                    this.mResourcesNotAvailable = true;
                }
            }
            return this.mResources;
        }

        /* access modifiers changed from: package-private */
        public boolean updateDescriptor(MediaRouteProviderDescriptor descriptor) {
            if (this.mDescriptor == descriptor) {
                return false;
            }
            this.mDescriptor = descriptor;
            return true;
        }

        /* access modifiers changed from: package-private */
        public int findRouteIndexByDescriptorId(String id) {
            int count = this.mRoutes.size();
            for (int i = 0; i < count; i++) {
                if (this.mRoutes.get(i).mDescriptorId.equals(id)) {
                    return i;
                }
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public RouteInfo findRouteByDescriptorId(String id) {
            int count = this.mRoutes.size();
            for (int i = 0; i < count; i++) {
                if (this.mRoutes.get(i).mDescriptorId.equals(id)) {
                    return this.mRoutes.get(i);
                }
            }
            return null;
        }

        /* access modifiers changed from: package-private */
        public boolean supportsDynamicGroup() {
            MediaRouteProviderDescriptor mediaRouteProviderDescriptor = this.mDescriptor;
            return mediaRouteProviderDescriptor != null && mediaRouteProviderDescriptor.supportsDynamicGroupRoute();
        }

        public String toString() {
            return "MediaRouter.RouteProviderInfo{ packageName=" + getPackageName() + " }";
        }
    }

    public static abstract class Callback {
        public void onRouteSelected(MediaRouter router, RouteInfo route) {
        }

        public void onRouteUnselected(MediaRouter router, RouteInfo route) {
        }

        public void onRouteUnselected(MediaRouter router, RouteInfo route, int reason) {
            onRouteUnselected(router, route);
        }

        public void onRouteAdded(MediaRouter router, RouteInfo route) {
        }

        public void onRouteRemoved(MediaRouter router, RouteInfo route) {
        }

        public void onRouteChanged(MediaRouter router, RouteInfo route) {
        }

        public void onRouteVolumeChanged(MediaRouter router, RouteInfo route) {
        }

        public void onRoutePresentationDisplayChanged(MediaRouter router, RouteInfo route) {
        }

        public void onProviderAdded(MediaRouter router, ProviderInfo provider) {
        }

        public void onProviderRemoved(MediaRouter router, ProviderInfo provider) {
        }

        public void onProviderChanged(MediaRouter router, ProviderInfo provider) {
        }
    }

    public static abstract class ControlRequestCallback {
        public void onResult(Bundle data) {
        }

        public void onError(String error, Bundle data) {
        }
    }

    private static final class CallbackRecord {
        public final Callback mCallback;
        public int mFlags;
        public final MediaRouter mRouter;
        public MediaRouteSelector mSelector = MediaRouteSelector.EMPTY;

        public CallbackRecord(MediaRouter router, Callback callback) {
            this.mRouter = router;
            this.mCallback = callback;
        }

        public boolean filterRouteEvent(RouteInfo route) {
            return (this.mFlags & 2) != 0 || route.matchesSelector(this.mSelector);
        }
    }

    private static final class GlobalMediaRouter implements SystemMediaRouteProvider.SyncCallback, RegisteredMediaRouteProviderWatcher.Callback {
        final Context mApplicationContext;
        private RouteInfo mBluetoothRoute;
        final CallbackHandler mCallbackHandler = new CallbackHandler();
        private MediaSessionCompat mCompatSession;
        private RouteInfo mDefaultRoute;
        private MediaRouteDiscoveryRequest mDiscoveryRequest;
        private final DisplayManagerCompat mDisplayManager;
        MediaRouteProvider.DynamicGroupRouteController.OnDynamicRoutesChangedListener mDynamicRoutesListener = new MediaRouteProvider.DynamicGroupRouteController.OnDynamicRoutesChangedListener() {
            public void onRoutesChanged(MediaRouteProvider.DynamicGroupRouteController controller, Collection<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> routes) {
                if (controller == GlobalMediaRouter.this.mSelectedRouteController) {
                    GlobalMediaRouter.this.mSelectedRoute.updateDescriptors(routes);
                }
            }
        };
        private final boolean mLowRam;
        private MediaSessionRecord mMediaSession;
        final RemoteControlClientCompat.PlaybackInfo mPlaybackInfo = new RemoteControlClientCompat.PlaybackInfo();
        private final ProviderCallback mProviderCallback = new ProviderCallback();
        private final ArrayList<ProviderInfo> mProviders = new ArrayList<>();
        MediaSessionCompat mRccMediaSession;
        private RegisteredMediaRouteProviderWatcher mRegisteredProviderWatcher;
        private final ArrayList<RemoteControlClientRecord> mRemoteControlClients = new ArrayList<>();
        private final Map<String, MediaRouteProvider.RouteController> mRouteControllerMap = new HashMap();
        final ArrayList<WeakReference<MediaRouter>> mRouters = new ArrayList<>();
        private final ArrayList<RouteInfo> mRoutes = new ArrayList<>();
        RouteInfo mSelectedRoute;
        MediaRouteProvider.RouteController mSelectedRouteController;
        private MediaSessionCompat.OnActiveChangeListener mSessionActiveListener = new MediaSessionCompat.OnActiveChangeListener() {
            public void onActiveChanged() {
                if (GlobalMediaRouter.this.mRccMediaSession == null) {
                    return;
                }
                if (GlobalMediaRouter.this.mRccMediaSession.isActive()) {
                    GlobalMediaRouter globalMediaRouter = GlobalMediaRouter.this;
                    globalMediaRouter.addRemoteControlClient(globalMediaRouter.mRccMediaSession.getRemoteControlClient());
                    return;
                }
                GlobalMediaRouter globalMediaRouter2 = GlobalMediaRouter.this;
                globalMediaRouter2.removeRemoteControlClient(globalMediaRouter2.mRccMediaSession.getRemoteControlClient());
            }
        };
        final SystemMediaRouteProvider mSystemProvider;
        private final Map<Pair<String, String>, String> mUniqueIdMap = new HashMap();

        GlobalMediaRouter(Context applicationContext) {
            this.mApplicationContext = applicationContext;
            this.mDisplayManager = DisplayManagerCompat.getInstance(applicationContext);
            this.mLowRam = ActivityManagerCompat.isLowRamDevice((ActivityManager) applicationContext.getSystemService("activity"));
            this.mSystemProvider = SystemMediaRouteProvider.obtain(applicationContext, this);
        }

        public void start() {
            addProvider(this.mSystemProvider);
            RegisteredMediaRouteProviderWatcher registeredMediaRouteProviderWatcher = new RegisteredMediaRouteProviderWatcher(this.mApplicationContext, this);
            this.mRegisteredProviderWatcher = registeredMediaRouteProviderWatcher;
            registeredMediaRouteProviderWatcher.start();
        }

        public MediaRouter getRouter(Context context) {
            int i = this.mRouters.size();
            while (true) {
                i--;
                if (i >= 0) {
                    MediaRouter router = (MediaRouter) this.mRouters.get(i).get();
                    if (router == null) {
                        this.mRouters.remove(i);
                    } else if (router.mContext == context) {
                        return router;
                    }
                } else {
                    MediaRouter router2 = new MediaRouter(context);
                    this.mRouters.add(new WeakReference(router2));
                    return router2;
                }
            }
        }

        public ContentResolver getContentResolver() {
            return this.mApplicationContext.getContentResolver();
        }

        public Context getProviderContext(String packageName) {
            if (packageName.equals(SystemMediaRouteProvider.PACKAGE_NAME)) {
                return this.mApplicationContext;
            }
            try {
                return this.mApplicationContext.createPackageContext(packageName, 4);
            } catch (PackageManager.NameNotFoundException e) {
                return null;
            }
        }

        public Display getDisplay(int displayId) {
            return this.mDisplayManager.getDisplay(displayId);
        }

        public void sendControlRequest(RouteInfo route, Intent intent, ControlRequestCallback callback) {
            MediaRouteProvider.RouteController routeController;
            if ((route != this.mSelectedRoute || (routeController = this.mSelectedRouteController) == null || !routeController.onControlRequest(intent, callback)) && callback != null) {
                callback.onError((String) null, (Bundle) null);
            }
        }

        public void requestSetVolume(RouteInfo route, int volume) {
            MediaRouteProvider.RouteController controller;
            MediaRouteProvider.RouteController routeController;
            if (route == this.mSelectedRoute && (routeController = this.mSelectedRouteController) != null) {
                routeController.onSetVolume(volume);
            } else if (!this.mRouteControllerMap.isEmpty() && (controller = this.mRouteControllerMap.get(route.mUniqueId)) != null) {
                controller.onSetVolume(volume);
            }
        }

        public void requestUpdateVolume(RouteInfo route, int delta) {
            MediaRouteProvider.RouteController routeController;
            if (route == this.mSelectedRoute && (routeController = this.mSelectedRouteController) != null) {
                routeController.onUpdateVolume(delta);
            }
        }

        public RouteInfo getRoute(String uniqueId) {
            Iterator<RouteInfo> it = this.mRoutes.iterator();
            while (it.hasNext()) {
                RouteInfo info = it.next();
                if (info.mUniqueId.equals(uniqueId)) {
                    return info;
                }
            }
            return null;
        }

        public List<RouteInfo> getRoutes() {
            return this.mRoutes;
        }

        /* access modifiers changed from: package-private */
        public List<ProviderInfo> getProviders() {
            return this.mProviders;
        }

        /* access modifiers changed from: package-private */
        public RouteInfo getDefaultRoute() {
            RouteInfo routeInfo = this.mDefaultRoute;
            if (routeInfo != null) {
                return routeInfo;
            }
            throw new IllegalStateException("There is no default route.  The media router has not yet been fully initialized.");
        }

        /* access modifiers changed from: package-private */
        public RouteInfo getBluetoothRoute() {
            return this.mBluetoothRoute;
        }

        /* access modifiers changed from: package-private */
        public RouteInfo getSelectedRoute() {
            RouteInfo routeInfo = this.mSelectedRoute;
            if (routeInfo != null) {
                return routeInfo;
            }
            throw new IllegalStateException("There is no currently selected route.  The media router has not yet been fully initialized.");
        }

        /* access modifiers changed from: package-private */
        public void selectRoute(RouteInfo route) {
            selectRoute(route, 3);
        }

        /* access modifiers changed from: package-private */
        public void addMemberToDynamicGroup(RouteInfo route) {
            if (this.mSelectedRoute.getDynamicGroupState() == null || !(this.mSelectedRouteController instanceof MediaRouteProvider.DynamicGroupRouteController)) {
                throw new IllegalStateException("There is no currently selected dynamic group route.");
            }
            RouteInfo.DynamicGroupState state = route.getDynamicGroupState();
            if (this.mSelectedRoute.getMemberRoutes().contains(route) || state == null || !state.isGroupable()) {
                Log.w(MediaRouter.TAG, "Ignoring attemp to add a non-groupable route to dynamic group : " + route);
            } else {
                ((MediaRouteProvider.DynamicGroupRouteController) this.mSelectedRouteController).onAddMemberRoute(route.getDescriptorId());
            }
        }

        /* access modifiers changed from: package-private */
        public void removeMemberFromDynamicGroup(RouteInfo route) {
            if (this.mSelectedRoute.getDynamicGroupState() == null || !(this.mSelectedRouteController instanceof MediaRouteProvider.DynamicGroupRouteController)) {
                throw new IllegalStateException("There is no currently selected dynamic group route.");
            }
            RouteInfo.DynamicGroupState state = route.getDynamicGroupState();
            if (!this.mSelectedRoute.getMemberRoutes().contains(route) || state == null || !state.isUnselectable()) {
                Log.w(MediaRouter.TAG, "Ignoring attempt to remove a non-unselectable member route : " + route);
            } else if (this.mSelectedRoute.getMemberRoutes().size() <= 1) {
                Log.w(MediaRouter.TAG, "Ignoring attempt to remove the last member route.");
            } else {
                ((MediaRouteProvider.DynamicGroupRouteController) this.mSelectedRouteController).onRemoveMemberRoute(route.getDescriptorId());
            }
        }

        /* access modifiers changed from: package-private */
        public void selectRoute(RouteInfo route, int unselectReason) {
            if (!this.mRoutes.contains(route)) {
                Log.w(MediaRouter.TAG, "Ignoring attempt to select removed route: " + route);
            } else if (!route.mEnabled) {
                Log.w(MediaRouter.TAG, "Ignoring attempt to select disabled route: " + route);
            } else {
                setSelectedRouteInternal(route, unselectReason);
            }
        }

        public boolean isRouteAvailable(MediaRouteSelector selector, int flags) {
            if (selector.isEmpty()) {
                return false;
            }
            if ((flags & 2) == 0 && this.mLowRam) {
                return true;
            }
            int routeCount = this.mRoutes.size();
            for (int i = 0; i < routeCount; i++) {
                RouteInfo route = this.mRoutes.get(i);
                if (((flags & 1) == 0 || !route.isDefaultOrBluetooth()) && route.matchesSelector(selector)) {
                    return true;
                }
            }
            return false;
        }

        public void updateDiscoveryRequest() {
            boolean discover = false;
            boolean activeScan = false;
            MediaRouteSelector.Builder builder = new MediaRouteSelector.Builder();
            int i = this.mRouters.size();
            while (true) {
                i--;
                if (i < 0) {
                    break;
                }
                MediaRouter router = (MediaRouter) this.mRouters.get(i).get();
                if (router == null) {
                    this.mRouters.remove(i);
                } else {
                    int count = router.mCallbackRecords.size();
                    for (int j = 0; j < count; j++) {
                        CallbackRecord callback = router.mCallbackRecords.get(j);
                        builder.addSelector(callback.mSelector);
                        if ((callback.mFlags & 1) != 0) {
                            activeScan = true;
                            discover = true;
                        }
                        if ((callback.mFlags & 4) != 0 && !this.mLowRam) {
                            discover = true;
                        }
                        if ((callback.mFlags & 8) != 0) {
                            discover = true;
                        }
                    }
                }
            }
            MediaRouteSelector selector = discover ? builder.build() : MediaRouteSelector.EMPTY;
            MediaRouteDiscoveryRequest mediaRouteDiscoveryRequest = this.mDiscoveryRequest;
            if (mediaRouteDiscoveryRequest == null || !mediaRouteDiscoveryRequest.getSelector().equals(selector) || this.mDiscoveryRequest.isActiveScan() != activeScan) {
                if (!selector.isEmpty() || activeScan) {
                    this.mDiscoveryRequest = new MediaRouteDiscoveryRequest(selector, activeScan);
                } else if (this.mDiscoveryRequest != null) {
                    this.mDiscoveryRequest = null;
                } else {
                    return;
                }
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Updated discovery request: " + this.mDiscoveryRequest);
                }
                if (discover && !activeScan && this.mLowRam) {
                    Log.i(MediaRouter.TAG, "Forcing passive route discovery on a low-RAM device, system performance may be affected.  Please consider using CALLBACK_FLAG_REQUEST_DISCOVERY instead of CALLBACK_FLAG_FORCE_DISCOVERY.");
                }
                int providerCount = this.mProviders.size();
                for (int i2 = 0; i2 < providerCount; i2++) {
                    this.mProviders.get(i2).mProviderInstance.setDiscoveryRequest(this.mDiscoveryRequest);
                }
            }
        }

        public void addProvider(MediaRouteProvider providerInstance) {
            if (findProviderInfo(providerInstance) == null) {
                ProviderInfo provider = new ProviderInfo(providerInstance);
                this.mProviders.add(provider);
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Provider added: " + provider);
                }
                this.mCallbackHandler.post(513, provider);
                updateProviderContents(provider, providerInstance.getDescriptor());
                providerInstance.setCallback(this.mProviderCallback);
                providerInstance.setDiscoveryRequest(this.mDiscoveryRequest);
            }
        }

        public void removeProvider(MediaRouteProvider providerInstance) {
            ProviderInfo provider = findProviderInfo(providerInstance);
            if (provider != null) {
                providerInstance.setCallback((MediaRouteProvider.Callback) null);
                providerInstance.setDiscoveryRequest((MediaRouteDiscoveryRequest) null);
                updateProviderContents(provider, (MediaRouteProviderDescriptor) null);
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Provider removed: " + provider);
                }
                this.mCallbackHandler.post(CallbackHandler.MSG_PROVIDER_REMOVED, provider);
                this.mProviders.remove(provider);
            }
        }

        /* access modifiers changed from: package-private */
        public void updateProviderDescriptor(MediaRouteProvider providerInstance, MediaRouteProviderDescriptor descriptor) {
            ProviderInfo provider = findProviderInfo(providerInstance);
            if (provider != null) {
                updateProviderContents(provider, descriptor);
            }
        }

        private ProviderInfo findProviderInfo(MediaRouteProvider providerInstance) {
            int count = this.mProviders.size();
            for (int i = 0; i < count; i++) {
                if (this.mProviders.get(i).mProviderInstance == providerInstance) {
                    return this.mProviders.get(i);
                }
            }
            return null;
        }

        private void updateProviderContents(ProviderInfo provider, MediaRouteProviderDescriptor providerDescriptor) {
            ProviderInfo providerInfo = provider;
            MediaRouteProviderDescriptor mediaRouteProviderDescriptor = providerDescriptor;
            if (provider.updateDescriptor(providerDescriptor)) {
                int targetIndex = 0;
                boolean selectedRouteDescriptorChanged = false;
                if (mediaRouteProviderDescriptor == null || (!providerDescriptor.isValid() && mediaRouteProviderDescriptor != this.mSystemProvider.getDescriptor())) {
                    Log.w(MediaRouter.TAG, "Ignoring invalid provider descriptor: " + mediaRouteProviderDescriptor);
                } else {
                    List<MediaRouteDescriptor> routeDescriptors = providerDescriptor.getRoutes();
                    List<Pair<RouteInfo, MediaRouteDescriptor>> addedGroups = new ArrayList<>();
                    List<Pair<RouteInfo, MediaRouteDescriptor>> updatedGroups = new ArrayList<>();
                    for (MediaRouteDescriptor routeDescriptor : routeDescriptors) {
                        if (routeDescriptor == null || !routeDescriptor.isValid()) {
                            Log.w(MediaRouter.TAG, "Ignoring invalid system route descriptor: " + routeDescriptor);
                            selectedRouteDescriptorChanged = selectedRouteDescriptorChanged;
                        } else {
                            String id = routeDescriptor.getId();
                            int sourceIndex = providerInfo.findRouteIndexByDescriptorId(id);
                            if (sourceIndex < 0) {
                                RouteInfo route = new RouteInfo(providerInfo, id, assignRouteUniqueId(providerInfo, id));
                                boolean selectedRouteDescriptorChanged2 = selectedRouteDescriptorChanged;
                                int targetIndex2 = targetIndex + 1;
                                providerInfo.mRoutes.add(targetIndex, route);
                                this.mRoutes.add(route);
                                if (routeDescriptor.getGroupMemberIds().size() > 0) {
                                    addedGroups.add(new Pair(route, routeDescriptor));
                                } else {
                                    route.maybeUpdateDescriptor(routeDescriptor);
                                    if (MediaRouter.DEBUG) {
                                        Log.d(MediaRouter.TAG, "Route added: " + route);
                                    }
                                    this.mCallbackHandler.post(257, route);
                                }
                                selectedRouteDescriptorChanged = selectedRouteDescriptorChanged2;
                                targetIndex = targetIndex2;
                            } else {
                                boolean selectedRouteDescriptorChanged3 = selectedRouteDescriptorChanged;
                                if (sourceIndex < targetIndex) {
                                    Log.w(MediaRouter.TAG, "Ignoring route descriptor with duplicate id: " + routeDescriptor);
                                    selectedRouteDescriptorChanged = selectedRouteDescriptorChanged3;
                                } else {
                                    RouteInfo route2 = providerInfo.mRoutes.get(sourceIndex);
                                    int targetIndex3 = targetIndex + 1;
                                    Collections.swap(providerInfo.mRoutes, sourceIndex, targetIndex);
                                    if (routeDescriptor.getGroupMemberIds().size() > 0) {
                                        updatedGroups.add(new Pair(route2, routeDescriptor));
                                    } else if (updateRouteDescriptorAndNotify(route2, routeDescriptor) != 0 && route2 == this.mSelectedRoute) {
                                        selectedRouteDescriptorChanged = true;
                                        targetIndex = targetIndex3;
                                    }
                                    targetIndex = targetIndex3;
                                    selectedRouteDescriptorChanged = selectedRouteDescriptorChanged3;
                                }
                            }
                        }
                    }
                    boolean selectedRouteDescriptorChanged4 = selectedRouteDescriptorChanged;
                    for (Pair<RouteInfo, MediaRouteDescriptor> pair : addedGroups) {
                        RouteInfo route3 = (RouteInfo) pair.first;
                        route3.maybeUpdateDescriptor((MediaRouteDescriptor) pair.second);
                        if (MediaRouter.DEBUG) {
                            Log.d(MediaRouter.TAG, "Route added: " + route3);
                        }
                        this.mCallbackHandler.post(257, route3);
                    }
                    for (Pair<RouteInfo, MediaRouteDescriptor> pair2 : updatedGroups) {
                        RouteInfo route4 = (RouteInfo) pair2.first;
                        if (updateRouteDescriptorAndNotify(route4, (MediaRouteDescriptor) pair2.second) != 0 && route4 == this.mSelectedRoute) {
                            selectedRouteDescriptorChanged4 = true;
                        }
                    }
                    selectedRouteDescriptorChanged = selectedRouteDescriptorChanged4;
                }
                for (int i = providerInfo.mRoutes.size() - 1; i >= targetIndex; i--) {
                    RouteInfo route5 = providerInfo.mRoutes.get(i);
                    route5.maybeUpdateDescriptor((MediaRouteDescriptor) null);
                    this.mRoutes.remove(route5);
                }
                updateSelectedRouteIfNeeded(selectedRouteDescriptorChanged);
                for (int i2 = providerInfo.mRoutes.size() - 1; i2 >= targetIndex; i2--) {
                    RouteInfo route6 = providerInfo.mRoutes.remove(i2);
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route removed: " + route6);
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_REMOVED, route6);
                }
                if (MediaRouter.DEBUG != 0) {
                    Log.d(MediaRouter.TAG, "Provider changed: " + providerInfo);
                }
                this.mCallbackHandler.post(CallbackHandler.MSG_PROVIDER_CHANGED, providerInfo);
            }
        }

        private int updateRouteDescriptorAndNotify(RouteInfo route, MediaRouteDescriptor routeDescriptor) {
            int changes = route.maybeUpdateDescriptor(routeDescriptor);
            if (changes != 0) {
                if ((changes & 1) != 0) {
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route changed: " + route);
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_CHANGED, route);
                }
                if ((changes & 2) != 0) {
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route volume changed: " + route);
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_VOLUME_CHANGED, route);
                }
                if ((changes & 4) != 0) {
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route presentation display changed: " + route);
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED, route);
                }
            }
            return changes;
        }

        /* access modifiers changed from: package-private */
        public String assignRouteUniqueId(ProviderInfo provider, String routeDescriptorId) {
            String componentName = provider.getComponentName().flattenToShortString();
            String uniqueId = componentName + ":" + routeDescriptorId;
            if (findRouteByUniqueId(uniqueId) < 0) {
                this.mUniqueIdMap.put(new Pair(componentName, routeDescriptorId), uniqueId);
                return uniqueId;
            }
            Log.w(MediaRouter.TAG, "Either " + routeDescriptorId + " isn't unique in " + componentName + " or we're trying to assign a unique ID for an already added route");
            int i = 2;
            while (true) {
                String newUniqueId = String.format(Locale.US, "%s_%d", new Object[]{uniqueId, Integer.valueOf(i)});
                if (findRouteByUniqueId(newUniqueId) < 0) {
                    this.mUniqueIdMap.put(new Pair(componentName, routeDescriptorId), newUniqueId);
                    return newUniqueId;
                }
                i++;
            }
        }

        private int findRouteByUniqueId(String uniqueId) {
            int count = this.mRoutes.size();
            for (int i = 0; i < count; i++) {
                if (this.mRoutes.get(i).mUniqueId.equals(uniqueId)) {
                    return i;
                }
            }
            return -1;
        }

        /* access modifiers changed from: package-private */
        public String getUniqueId(ProviderInfo provider, String routeDescriptorId) {
            return this.mUniqueIdMap.get(new Pair(provider.getComponentName().flattenToShortString(), routeDescriptorId));
        }

        /* access modifiers changed from: package-private */
        public void updateSelectedRouteIfNeeded(boolean selectedRouteDescriptorChanged) {
            RouteInfo routeInfo = this.mDefaultRoute;
            if (routeInfo != null && !routeInfo.isSelectable()) {
                Log.i(MediaRouter.TAG, "Clearing the default route because it is no longer selectable: " + this.mDefaultRoute);
                this.mDefaultRoute = null;
            }
            if (this.mDefaultRoute == null && !this.mRoutes.isEmpty()) {
                Iterator<RouteInfo> it = this.mRoutes.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    RouteInfo route = it.next();
                    if (isSystemDefaultRoute(route) && route.isSelectable()) {
                        this.mDefaultRoute = route;
                        Log.i(MediaRouter.TAG, "Found default route: " + this.mDefaultRoute);
                        break;
                    }
                }
            }
            RouteInfo routeInfo2 = this.mBluetoothRoute;
            if (routeInfo2 != null && !routeInfo2.isSelectable()) {
                Log.i(MediaRouter.TAG, "Clearing the bluetooth route because it is no longer selectable: " + this.mBluetoothRoute);
                this.mBluetoothRoute = null;
            }
            if (this.mBluetoothRoute == null && !this.mRoutes.isEmpty()) {
                Iterator<RouteInfo> it2 = this.mRoutes.iterator();
                while (true) {
                    if (!it2.hasNext()) {
                        break;
                    }
                    RouteInfo route2 = it2.next();
                    if (isSystemLiveAudioOnlyRoute(route2) && route2.isSelectable()) {
                        this.mBluetoothRoute = route2;
                        Log.i(MediaRouter.TAG, "Found bluetooth route: " + this.mBluetoothRoute);
                        break;
                    }
                }
            }
            RouteInfo routeInfo3 = this.mSelectedRoute;
            if (routeInfo3 == null || !routeInfo3.isEnabled()) {
                Log.i(MediaRouter.TAG, "Unselecting the current route because it is no longer selectable: " + this.mSelectedRoute);
                setSelectedRouteInternal(chooseFallbackRoute(), 0);
            } else if (selectedRouteDescriptorChanged) {
                if (this.mSelectedRoute.isGroup()) {
                    List<RouteInfo> routes = this.mSelectedRoute.getMemberRoutes();
                    Set<String> idSet = new HashSet<>();
                    for (RouteInfo route3 : routes) {
                        idSet.add(route3.mUniqueId);
                    }
                    Iterator<Map.Entry<String, MediaRouteProvider.RouteController>> iter = this.mRouteControllerMap.entrySet().iterator();
                    while (iter.hasNext()) {
                        Map.Entry<String, MediaRouteProvider.RouteController> entry = iter.next();
                        if (!idSet.contains(entry.getKey())) {
                            MediaRouteProvider.RouteController controller = entry.getValue();
                            controller.onUnselect();
                            controller.onRelease();
                            iter.remove();
                        }
                    }
                    for (RouteInfo route4 : routes) {
                        if (!this.mRouteControllerMap.containsKey(route4.mUniqueId)) {
                            MediaRouteProvider.RouteController controller2 = route4.getProviderInstance().onCreateRouteController(route4.mDescriptorId, this.mSelectedRoute.mDescriptorId);
                            controller2.onSelect();
                            this.mRouteControllerMap.put(route4.mUniqueId, controller2);
                        }
                    }
                }
                updatePlaybackInfoFromSelectedRoute();
            }
        }

        /* access modifiers changed from: package-private */
        public RouteInfo chooseFallbackRoute() {
            Iterator<RouteInfo> it = this.mRoutes.iterator();
            while (it.hasNext()) {
                RouteInfo route = it.next();
                if (route != this.mDefaultRoute && isSystemLiveAudioOnlyRoute(route) && route.isSelectable()) {
                    return route;
                }
            }
            return this.mDefaultRoute;
        }

        private boolean isSystemLiveAudioOnlyRoute(RouteInfo route) {
            return route.getProviderInstance() == this.mSystemProvider && route.supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_AUDIO) && !route.supportsControlCategory(MediaControlIntent.CATEGORY_LIVE_VIDEO);
        }

        private boolean isSystemDefaultRoute(RouteInfo route) {
            return route.getProviderInstance() == this.mSystemProvider && route.mDescriptorId.equals(SystemMediaRouteProvider.DEFAULT_ROUTE_ID);
        }

        private void setSelectedRouteInternal(RouteInfo route, int unselectReason) {
            if (MediaRouter.sGlobal == null || (this.mBluetoothRoute != null && route.isDefault())) {
                StackTraceElement[] callStack = Thread.currentThread().getStackTrace();
                StringBuilder sb = new StringBuilder();
                for (int i = 3; i < callStack.length; i++) {
                    StackTraceElement caller = callStack[i];
                    sb.append(caller.getClassName()).append(".").append(caller.getMethodName()).append(":").append(caller.getLineNumber()).append("  ");
                }
                if (MediaRouter.sGlobal == null) {
                    Log.w(MediaRouter.TAG, "setSelectedRouteInternal is called while sGlobal is null: pkgName=" + this.mApplicationContext.getPackageName() + ", callers=" + sb.toString());
                } else {
                    Log.w(MediaRouter.TAG, "Default route is selected while a BT route is available: pkgName=" + this.mApplicationContext.getPackageName() + ", callers=" + sb.toString());
                }
            }
            RouteInfo routeInfo = this.mSelectedRoute;
            if (routeInfo != route) {
                if (routeInfo != null) {
                    if (MediaRouter.DEBUG) {
                        Log.d(MediaRouter.TAG, "Route unselected: " + this.mSelectedRoute + " reason: " + unselectReason);
                    }
                    this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_UNSELECTED, this.mSelectedRoute, unselectReason);
                    MediaRouteProvider.RouteController routeController = this.mSelectedRouteController;
                    if (routeController != null) {
                        routeController.onUnselect(unselectReason);
                        this.mSelectedRouteController.onRelease();
                        this.mSelectedRouteController = null;
                    }
                    if (!this.mRouteControllerMap.isEmpty()) {
                        for (MediaRouteProvider.RouteController controller : this.mRouteControllerMap.values()) {
                            controller.onUnselect(unselectReason);
                            controller.onRelease();
                        }
                        this.mRouteControllerMap.clear();
                    }
                }
                if (route.getProvider().supportsDynamicGroup()) {
                    MediaRouteProvider.DynamicGroupRouteController controller2 = route.getProviderInstance().onCreateDynamicGroupRouteController(route.mDescriptorId);
                    controller2.setOnDynamicRoutesChangedListener(ContextCompat.getMainExecutor(this.mApplicationContext), this.mDynamicRoutesListener);
                    this.mSelectedRouteController = controller2;
                    this.mSelectedRoute = route;
                } else {
                    this.mSelectedRouteController = route.getProviderInstance().onCreateRouteController(route.mDescriptorId);
                    this.mSelectedRoute = route;
                }
                MediaRouteProvider.RouteController routeController2 = this.mSelectedRouteController;
                if (routeController2 != null) {
                    routeController2.onSelect();
                }
                if (MediaRouter.DEBUG) {
                    Log.d(MediaRouter.TAG, "Route selected: " + this.mSelectedRoute);
                }
                this.mCallbackHandler.post(CallbackHandler.MSG_ROUTE_SELECTED, this.mSelectedRoute);
                if (this.mSelectedRoute.isGroup()) {
                    List<RouteInfo> routes = this.mSelectedRoute.getMemberRoutes();
                    this.mRouteControllerMap.clear();
                    for (RouteInfo r : routes) {
                        MediaRouteProvider.RouteController controller3 = r.getProviderInstance().onCreateRouteController(r.mDescriptorId, this.mSelectedRoute.mDescriptorId);
                        controller3.onSelect();
                        this.mRouteControllerMap.put(r.mUniqueId, controller3);
                    }
                }
                updatePlaybackInfoFromSelectedRoute();
            }
        }

        public void onSystemRouteSelectedByDescriptorId(String id) {
            RouteInfo route;
            this.mCallbackHandler.removeMessages(CallbackHandler.MSG_ROUTE_SELECTED);
            ProviderInfo provider = findProviderInfo(this.mSystemProvider);
            if (provider != null && (route = provider.findRouteByDescriptorId(id)) != null) {
                route.select();
            }
        }

        public void addRemoteControlClient(Object rcc) {
            if (findRemoteControlClientRecord(rcc) < 0) {
                this.mRemoteControlClients.add(new RemoteControlClientRecord(rcc));
            }
        }

        public void removeRemoteControlClient(Object rcc) {
            int index = findRemoteControlClientRecord(rcc);
            if (index >= 0) {
                this.mRemoteControlClients.remove(index).disconnect();
            }
        }

        public void setMediaSession(Object session) {
            setMediaSessionRecord(session != null ? new MediaSessionRecord(this, session) : null);
        }

        public void setMediaSessionCompat(MediaSessionCompat session) {
            this.mCompatSession = session;
            if (Build.VERSION.SDK_INT >= 21) {
                setMediaSessionRecord(session != null ? new MediaSessionRecord(session) : null);
            } else if (Build.VERSION.SDK_INT >= 14) {
                MediaSessionCompat mediaSessionCompat = this.mRccMediaSession;
                if (mediaSessionCompat != null) {
                    removeRemoteControlClient(mediaSessionCompat.getRemoteControlClient());
                    this.mRccMediaSession.removeOnActiveChangeListener(this.mSessionActiveListener);
                }
                this.mRccMediaSession = session;
                if (session != null) {
                    session.addOnActiveChangeListener(this.mSessionActiveListener);
                    if (session.isActive()) {
                        addRemoteControlClient(session.getRemoteControlClient());
                    }
                }
            }
        }

        private void setMediaSessionRecord(MediaSessionRecord mediaSessionRecord) {
            MediaSessionRecord mediaSessionRecord2 = this.mMediaSession;
            if (mediaSessionRecord2 != null) {
                mediaSessionRecord2.clearVolumeHandling();
            }
            this.mMediaSession = mediaSessionRecord;
            if (mediaSessionRecord != null) {
                updatePlaybackInfoFromSelectedRoute();
            }
        }

        public MediaSessionCompat.Token getMediaSessionToken() {
            MediaSessionRecord mediaSessionRecord = this.mMediaSession;
            if (mediaSessionRecord != null) {
                return mediaSessionRecord.getToken();
            }
            MediaSessionCompat mediaSessionCompat = this.mCompatSession;
            if (mediaSessionCompat != null) {
                return mediaSessionCompat.getSessionToken();
            }
            return null;
        }

        private int findRemoteControlClientRecord(Object rcc) {
            int count = this.mRemoteControlClients.size();
            for (int i = 0; i < count; i++) {
                if (this.mRemoteControlClients.get(i).getRemoteControlClient() == rcc) {
                    return i;
                }
            }
            return -1;
        }

        private void updatePlaybackInfoFromSelectedRoute() {
            RouteInfo routeInfo = this.mSelectedRoute;
            if (routeInfo != null) {
                this.mPlaybackInfo.volume = routeInfo.getVolume();
                this.mPlaybackInfo.volumeMax = this.mSelectedRoute.getVolumeMax();
                this.mPlaybackInfo.volumeHandling = this.mSelectedRoute.getVolumeHandling();
                this.mPlaybackInfo.playbackStream = this.mSelectedRoute.getPlaybackStream();
                this.mPlaybackInfo.playbackType = this.mSelectedRoute.getPlaybackType();
                int count = this.mRemoteControlClients.size();
                for (int i = 0; i < count; i++) {
                    this.mRemoteControlClients.get(i).updatePlaybackInfo();
                }
                if (this.mMediaSession == null) {
                    return;
                }
                if (this.mSelectedRoute == getDefaultRoute() || this.mSelectedRoute == getBluetoothRoute()) {
                    this.mMediaSession.clearVolumeHandling();
                    return;
                }
                int controlType = 0;
                if (this.mPlaybackInfo.volumeHandling == 1) {
                    controlType = 2;
                }
                this.mMediaSession.configureVolume(controlType, this.mPlaybackInfo.volumeMax, this.mPlaybackInfo.volume);
                return;
            }
            MediaSessionRecord mediaSessionRecord = this.mMediaSession;
            if (mediaSessionRecord != null) {
                mediaSessionRecord.clearVolumeHandling();
            }
        }

        private final class ProviderCallback extends MediaRouteProvider.Callback {
            ProviderCallback() {
            }

            public void onDescriptorChanged(MediaRouteProvider provider, MediaRouteProviderDescriptor descriptor) {
                GlobalMediaRouter.this.updateProviderDescriptor(provider, descriptor);
            }
        }

        private final class MediaSessionRecord {
            private int mControlType;
            private int mMaxVolume;
            private final MediaSessionCompat mMsCompat;
            private VolumeProviderCompat mVpCompat;

            MediaSessionRecord(GlobalMediaRouter globalMediaRouter, Object mediaSession) {
                this(MediaSessionCompat.fromMediaSession(globalMediaRouter.mApplicationContext, mediaSession));
            }

            MediaSessionRecord(MediaSessionCompat mediaSessionCompat) {
                this.mMsCompat = mediaSessionCompat;
            }

            public void configureVolume(int controlType, int max, int current) {
                if (this.mMsCompat != null) {
                    VolumeProviderCompat volumeProviderCompat = this.mVpCompat;
                    if (volumeProviderCompat != null && controlType == this.mControlType && max == this.mMaxVolume) {
                        volumeProviderCompat.setCurrentVolume(current);
                        return;
                    }
                    C03301 r0 = new VolumeProviderCompat(controlType, max, current) {
                        public void onSetVolumeTo(final int volume) {
                            GlobalMediaRouter.this.mCallbackHandler.post(new Runnable() {
                                public void run() {
                                    if (GlobalMediaRouter.this.mSelectedRoute != null) {
                                        GlobalMediaRouter.this.mSelectedRoute.requestSetVolume(volume);
                                    }
                                }
                            });
                        }

                        public void onAdjustVolume(final int direction) {
                            GlobalMediaRouter.this.mCallbackHandler.post(new Runnable() {
                                public void run() {
                                    if (GlobalMediaRouter.this.mSelectedRoute != null) {
                                        GlobalMediaRouter.this.mSelectedRoute.requestUpdateVolume(direction);
                                    }
                                }
                            });
                        }
                    };
                    this.mVpCompat = r0;
                    this.mMsCompat.setPlaybackToRemote(r0);
                }
            }

            public void clearVolumeHandling() {
                MediaSessionCompat mediaSessionCompat = this.mMsCompat;
                if (mediaSessionCompat != null) {
                    mediaSessionCompat.setPlaybackToLocal(GlobalMediaRouter.this.mPlaybackInfo.playbackStream);
                    this.mVpCompat = null;
                }
            }

            public MediaSessionCompat.Token getToken() {
                MediaSessionCompat mediaSessionCompat = this.mMsCompat;
                if (mediaSessionCompat != null) {
                    return mediaSessionCompat.getSessionToken();
                }
                return null;
            }
        }

        private final class RemoteControlClientRecord implements RemoteControlClientCompat.VolumeCallback {
            private boolean mDisconnected;
            private final RemoteControlClientCompat mRccCompat;

            public RemoteControlClientRecord(Object rcc) {
                RemoteControlClientCompat obtain = RemoteControlClientCompat.obtain(GlobalMediaRouter.this.mApplicationContext, rcc);
                this.mRccCompat = obtain;
                obtain.setVolumeCallback(this);
                updatePlaybackInfo();
            }

            public Object getRemoteControlClient() {
                return this.mRccCompat.getRemoteControlClient();
            }

            public void disconnect() {
                this.mDisconnected = true;
                this.mRccCompat.setVolumeCallback((RemoteControlClientCompat.VolumeCallback) null);
            }

            public void updatePlaybackInfo() {
                this.mRccCompat.setPlaybackInfo(GlobalMediaRouter.this.mPlaybackInfo);
            }

            public void onVolumeSetRequest(int volume) {
                if (!this.mDisconnected && GlobalMediaRouter.this.mSelectedRoute != null) {
                    GlobalMediaRouter.this.mSelectedRoute.requestSetVolume(volume);
                }
            }

            public void onVolumeUpdateRequest(int direction) {
                if (!this.mDisconnected && GlobalMediaRouter.this.mSelectedRoute != null) {
                    GlobalMediaRouter.this.mSelectedRoute.requestUpdateVolume(direction);
                }
            }
        }

        private final class CallbackHandler extends Handler {
            public static final int MSG_PROVIDER_ADDED = 513;
            public static final int MSG_PROVIDER_CHANGED = 515;
            public static final int MSG_PROVIDER_REMOVED = 514;
            public static final int MSG_ROUTE_ADDED = 257;
            public static final int MSG_ROUTE_CHANGED = 259;
            public static final int MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED = 261;
            public static final int MSG_ROUTE_REMOVED = 258;
            public static final int MSG_ROUTE_SELECTED = 262;
            public static final int MSG_ROUTE_UNSELECTED = 263;
            public static final int MSG_ROUTE_VOLUME_CHANGED = 260;
            private static final int MSG_TYPE_MASK = 65280;
            private static final int MSG_TYPE_PROVIDER = 512;
            private static final int MSG_TYPE_ROUTE = 256;
            private final ArrayList<CallbackRecord> mTempCallbackRecords = new ArrayList<>();

            CallbackHandler() {
            }

            public void post(int msg, Object obj) {
                obtainMessage(msg, obj).sendToTarget();
            }

            public void post(int msg, Object obj, int arg) {
                Message message = obtainMessage(msg, obj);
                message.arg1 = arg;
                message.sendToTarget();
            }

            public void handleMessage(Message msg) {
                int what = msg.what;
                Object obj = msg.obj;
                int arg = msg.arg1;
                if (what == 259 && GlobalMediaRouter.this.getSelectedRoute().getId().equals(((RouteInfo) obj).getId())) {
                    GlobalMediaRouter.this.updateSelectedRouteIfNeeded(true);
                }
                syncWithSystemProvider(what, obj);
                try {
                    int i = GlobalMediaRouter.this.mRouters.size();
                    while (true) {
                        i--;
                        if (i < 0) {
                            break;
                        }
                        MediaRouter router = (MediaRouter) GlobalMediaRouter.this.mRouters.get(i).get();
                        if (router == null) {
                            GlobalMediaRouter.this.mRouters.remove(i);
                        } else {
                            this.mTempCallbackRecords.addAll(router.mCallbackRecords);
                        }
                    }
                    int callbackCount = this.mTempCallbackRecords.size();
                    for (int i2 = 0; i2 < callbackCount; i2++) {
                        invokeCallback(this.mTempCallbackRecords.get(i2), what, obj, arg);
                    }
                } finally {
                    this.mTempCallbackRecords.clear();
                }
            }

            private void syncWithSystemProvider(int what, Object obj) {
                if (what != 262) {
                    switch (what) {
                        case 257:
                            GlobalMediaRouter.this.mSystemProvider.onSyncRouteAdded((RouteInfo) obj);
                            return;
                        case MSG_ROUTE_REMOVED /*258*/:
                            GlobalMediaRouter.this.mSystemProvider.onSyncRouteRemoved((RouteInfo) obj);
                            return;
                        case MSG_ROUTE_CHANGED /*259*/:
                            GlobalMediaRouter.this.mSystemProvider.onSyncRouteChanged((RouteInfo) obj);
                            return;
                        default:
                            return;
                    }
                } else {
                    GlobalMediaRouter.this.mSystemProvider.onSyncRouteSelected((RouteInfo) obj);
                }
            }

            private void invokeCallback(CallbackRecord record, int what, Object obj, int arg) {
                MediaRouter router = record.mRouter;
                Callback callback = record.mCallback;
                int i = 65280 & what;
                if (i == 256) {
                    RouteInfo route = (RouteInfo) obj;
                    if (record.filterRouteEvent(route)) {
                        switch (what) {
                            case 257:
                                callback.onRouteAdded(router, route);
                                return;
                            case MSG_ROUTE_REMOVED /*258*/:
                                callback.onRouteRemoved(router, route);
                                return;
                            case MSG_ROUTE_CHANGED /*259*/:
                                callback.onRouteChanged(router, route);
                                return;
                            case MSG_ROUTE_VOLUME_CHANGED /*260*/:
                                callback.onRouteVolumeChanged(router, route);
                                return;
                            case MSG_ROUTE_PRESENTATION_DISPLAY_CHANGED /*261*/:
                                callback.onRoutePresentationDisplayChanged(router, route);
                                return;
                            case MSG_ROUTE_SELECTED /*262*/:
                                callback.onRouteSelected(router, route);
                                return;
                            case MSG_ROUTE_UNSELECTED /*263*/:
                                callback.onRouteUnselected(router, route, arg);
                                return;
                            default:
                                return;
                        }
                    }
                } else if (i == 512) {
                    ProviderInfo provider = (ProviderInfo) obj;
                    switch (what) {
                        case 513:
                            callback.onProviderAdded(router, provider);
                            return;
                        case MSG_PROVIDER_REMOVED /*514*/:
                            callback.onProviderRemoved(router, provider);
                            return;
                        case MSG_PROVIDER_CHANGED /*515*/:
                            callback.onProviderChanged(router, provider);
                            return;
                        default:
                            return;
                    }
                }
            }
        }
    }
}
