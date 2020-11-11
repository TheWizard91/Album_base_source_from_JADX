package androidx.mediarouter.media;

import android.content.IntentFilter;
import android.content.IntentSender;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public final class MediaRouteDescriptor {
    static final String IS_DYNAMIC_GROUP_ROUTE = "isDynamicGroupRoute";
    static final String KEY_CAN_DISCONNECT = "canDisconnect";
    static final String KEY_CONNECTING = "connecting";
    static final String KEY_CONNECTION_STATE = "connectionState";
    static final String KEY_CONTROL_FILTERS = "controlFilters";
    static final String KEY_DESCRIPTION = "status";
    static final String KEY_DEVICE_TYPE = "deviceType";
    static final String KEY_ENABLED = "enabled";
    static final String KEY_EXTRAS = "extras";
    static final String KEY_GROUP_MEMBER_IDS = "groupMemberIds";
    static final String KEY_ICON_URI = "iconUri";
    static final String KEY_ID = "id";
    static final String KEY_MAX_CLIENT_VERSION = "maxClientVersion";
    static final String KEY_MIN_CLIENT_VERSION = "minClientVersion";
    static final String KEY_NAME = "name";
    static final String KEY_PLAYBACK_STREAM = "playbackStream";
    static final String KEY_PLAYBACK_TYPE = "playbackType";
    static final String KEY_PRESENTATION_DISPLAY_ID = "presentationDisplayId";
    static final String KEY_SETTINGS_INTENT = "settingsIntent";
    static final String KEY_VOLUME = "volume";
    static final String KEY_VOLUME_HANDLING = "volumeHandling";
    static final String KEY_VOLUME_MAX = "volumeMax";
    final Bundle mBundle;
    List<IntentFilter> mControlFilters;
    List<String> mGroupMemberIds;

    MediaRouteDescriptor(Bundle bundle) {
        this.mBundle = bundle;
    }

    public String getId() {
        return this.mBundle.getString(KEY_ID);
    }

    public List<String> getGroupMemberIds() {
        ensureGroupMemberIds();
        return this.mGroupMemberIds;
    }

    /* access modifiers changed from: package-private */
    public void ensureGroupMemberIds() {
        if (this.mGroupMemberIds == null) {
            ArrayList<String> stringArrayList = this.mBundle.getStringArrayList(KEY_GROUP_MEMBER_IDS);
            this.mGroupMemberIds = stringArrayList;
            if (stringArrayList == null) {
                this.mGroupMemberIds = Collections.emptyList();
            }
        }
    }

    public String getName() {
        return this.mBundle.getString("name");
    }

    public String getDescription() {
        return this.mBundle.getString("status");
    }

    public Uri getIconUri() {
        String iconUri = this.mBundle.getString(KEY_ICON_URI);
        if (iconUri == null) {
            return null;
        }
        return Uri.parse(iconUri);
    }

    public boolean isEnabled() {
        return this.mBundle.getBoolean(KEY_ENABLED, true);
    }

    public boolean isDynamicGroupRoute() {
        return this.mBundle.getBoolean(IS_DYNAMIC_GROUP_ROUTE, false);
    }

    @Deprecated
    public boolean isConnecting() {
        return this.mBundle.getBoolean(KEY_CONNECTING, false);
    }

    public int getConnectionState() {
        return this.mBundle.getInt(KEY_CONNECTION_STATE, 0);
    }

    public boolean canDisconnectAndKeepPlaying() {
        return this.mBundle.getBoolean(KEY_CAN_DISCONNECT, false);
    }

    public IntentSender getSettingsActivity() {
        return (IntentSender) this.mBundle.getParcelable(KEY_SETTINGS_INTENT);
    }

    public List<IntentFilter> getControlFilters() {
        ensureControlFilters();
        return this.mControlFilters;
    }

    /* access modifiers changed from: package-private */
    public void ensureControlFilters() {
        if (this.mControlFilters == null) {
            ArrayList parcelableArrayList = this.mBundle.getParcelableArrayList(KEY_CONTROL_FILTERS);
            this.mControlFilters = parcelableArrayList;
            if (parcelableArrayList == null) {
                this.mControlFilters = Collections.emptyList();
            }
        }
    }

    public int getPlaybackType() {
        return this.mBundle.getInt(KEY_PLAYBACK_TYPE, 1);
    }

    public int getPlaybackStream() {
        return this.mBundle.getInt(KEY_PLAYBACK_STREAM, -1);
    }

    public int getDeviceType() {
        return this.mBundle.getInt(KEY_DEVICE_TYPE);
    }

    public int getVolume() {
        return this.mBundle.getInt("volume");
    }

    public int getVolumeMax() {
        return this.mBundle.getInt(KEY_VOLUME_MAX);
    }

    public int getVolumeHandling() {
        return this.mBundle.getInt(KEY_VOLUME_HANDLING, 0);
    }

    public int getPresentationDisplayId() {
        return this.mBundle.getInt(KEY_PRESENTATION_DISPLAY_ID, -1);
    }

    public Bundle getExtras() {
        return this.mBundle.getBundle(KEY_EXTRAS);
    }

    public int getMinClientVersion() {
        return this.mBundle.getInt(KEY_MIN_CLIENT_VERSION, 1);
    }

    public int getMaxClientVersion() {
        return this.mBundle.getInt(KEY_MAX_CLIENT_VERSION, Integer.MAX_VALUE);
    }

    public boolean isValid() {
        ensureControlFilters();
        if (TextUtils.isEmpty(getId()) || TextUtils.isEmpty(getName()) || this.mControlFilters.contains((Object) null)) {
            return false;
        }
        return true;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("MediaRouteDescriptor{ ");
        result.append("id=").append(getId());
        result.append(", groupMemberIds=").append(getGroupMemberIds());
        result.append(", name=").append(getName());
        result.append(", description=").append(getDescription());
        result.append(", iconUri=").append(getIconUri());
        result.append(", isEnabled=").append(isEnabled());
        result.append(", isConnecting=").append(isConnecting());
        result.append(", connectionState=").append(getConnectionState());
        result.append(", controlFilters=").append(Arrays.toString(getControlFilters().toArray()));
        result.append(", playbackType=").append(getPlaybackType());
        result.append(", playbackStream=").append(getPlaybackStream());
        result.append(", deviceType=").append(getDeviceType());
        result.append(", volume=").append(getVolume());
        result.append(", volumeMax=").append(getVolumeMax());
        result.append(", volumeHandling=").append(getVolumeHandling());
        result.append(", presentationDisplayId=").append(getPresentationDisplayId());
        result.append(", extras=").append(getExtras());
        result.append(", isValid=").append(isValid());
        result.append(", minClientVersion=").append(getMinClientVersion());
        result.append(", maxClientVersion=").append(getMaxClientVersion());
        result.append(" }");
        return result.toString();
    }

    public Bundle asBundle() {
        return this.mBundle;
    }

    public static MediaRouteDescriptor fromBundle(Bundle bundle) {
        if (bundle != null) {
            return new MediaRouteDescriptor(bundle);
        }
        return null;
    }

    public static final class Builder {
        private final Bundle mBundle;
        private ArrayList<IntentFilter> mControlFilters;
        private ArrayList<String> mGroupMemberIds;

        public Builder(String id, String name) {
            this.mBundle = new Bundle();
            setId(id);
            setName(name);
        }

        public Builder(MediaRouteDescriptor descriptor) {
            if (descriptor != null) {
                this.mBundle = new Bundle(descriptor.mBundle);
                if (!descriptor.getGroupMemberIds().isEmpty()) {
                    this.mGroupMemberIds = new ArrayList<>(descriptor.getGroupMemberIds());
                }
                if (!descriptor.getControlFilters().isEmpty()) {
                    this.mControlFilters = new ArrayList<>(descriptor.mControlFilters);
                    return;
                }
                return;
            }
            throw new IllegalArgumentException("descriptor must not be null");
        }

        public Builder setId(String id) {
            this.mBundle.putString(MediaRouteDescriptor.KEY_ID, id);
            return this;
        }

        public Builder addGroupMemberId(String groupMemberId) {
            if (!TextUtils.isEmpty(groupMemberId)) {
                if (this.mGroupMemberIds == null) {
                    this.mGroupMemberIds = new ArrayList<>();
                }
                if (!this.mGroupMemberIds.contains(groupMemberId)) {
                    this.mGroupMemberIds.add(groupMemberId);
                }
                return this;
            }
            throw new IllegalArgumentException("groupMemberId must not be empty");
        }

        public Builder addGroupMemberIds(Collection<String> groupMemberIds) {
            if (groupMemberIds != null) {
                if (!groupMemberIds.isEmpty()) {
                    for (String groupMemberId : groupMemberIds) {
                        addGroupMemberId(groupMemberId);
                    }
                }
                return this;
            }
            throw new IllegalArgumentException("groupMemberIds must not be null");
        }

        public Builder removeGroupMemberId(String memberRouteId) {
            if (!TextUtils.isEmpty(memberRouteId)) {
                ArrayList<String> arrayList = this.mGroupMemberIds;
                if (arrayList != null) {
                    arrayList.remove(memberRouteId);
                }
                return this;
            }
            throw new IllegalArgumentException("memberRouteId must not be empty");
        }

        public Builder setName(String name) {
            this.mBundle.putString("name", name);
            return this;
        }

        public Builder setDescription(String description) {
            this.mBundle.putString("status", description);
            return this;
        }

        public Builder setIconUri(Uri iconUri) {
            if (iconUri != null) {
                this.mBundle.putString(MediaRouteDescriptor.KEY_ICON_URI, iconUri.toString());
                return this;
            }
            throw new IllegalArgumentException("iconUri must not be null");
        }

        public Builder setEnabled(boolean enabled) {
            this.mBundle.putBoolean(MediaRouteDescriptor.KEY_ENABLED, enabled);
            return this;
        }

        public Builder setIsDynamicGroupRoute(boolean isDynamicGroupRoute) {
            this.mBundle.putBoolean(MediaRouteDescriptor.IS_DYNAMIC_GROUP_ROUTE, isDynamicGroupRoute);
            return this;
        }

        @Deprecated
        public Builder setConnecting(boolean connecting) {
            this.mBundle.putBoolean(MediaRouteDescriptor.KEY_CONNECTING, connecting);
            return this;
        }

        public Builder setConnectionState(int connectionState) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_CONNECTION_STATE, connectionState);
            return this;
        }

        public Builder setCanDisconnect(boolean canDisconnect) {
            this.mBundle.putBoolean(MediaRouteDescriptor.KEY_CAN_DISCONNECT, canDisconnect);
            return this;
        }

        public Builder setSettingsActivity(IntentSender is) {
            this.mBundle.putParcelable(MediaRouteDescriptor.KEY_SETTINGS_INTENT, is);
            return this;
        }

        public Builder addControlFilter(IntentFilter filter) {
            if (filter != null) {
                if (this.mControlFilters == null) {
                    this.mControlFilters = new ArrayList<>();
                }
                if (!this.mControlFilters.contains(filter)) {
                    this.mControlFilters.add(filter);
                }
                return this;
            }
            throw new IllegalArgumentException("filter must not be null");
        }

        public Builder addControlFilters(Collection<IntentFilter> filters) {
            if (filters != null) {
                if (!filters.isEmpty()) {
                    for (IntentFilter filter : filters) {
                        addControlFilter(filter);
                    }
                }
                return this;
            }
            throw new IllegalArgumentException("filters must not be null");
        }

        public Builder setPlaybackType(int playbackType) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_PLAYBACK_TYPE, playbackType);
            return this;
        }

        public Builder setPlaybackStream(int playbackStream) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_PLAYBACK_STREAM, playbackStream);
            return this;
        }

        public Builder setDeviceType(int deviceType) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_DEVICE_TYPE, deviceType);
            return this;
        }

        public Builder setVolume(int volume) {
            this.mBundle.putInt("volume", volume);
            return this;
        }

        public Builder setVolumeMax(int volumeMax) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_VOLUME_MAX, volumeMax);
            return this;
        }

        public Builder setVolumeHandling(int volumeHandling) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_VOLUME_HANDLING, volumeHandling);
            return this;
        }

        public Builder setPresentationDisplayId(int presentationDisplayId) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_PRESENTATION_DISPLAY_ID, presentationDisplayId);
            return this;
        }

        public Builder setExtras(Bundle extras) {
            this.mBundle.putBundle(MediaRouteDescriptor.KEY_EXTRAS, extras);
            return this;
        }

        public Builder setMinClientVersion(int minVersion) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_MIN_CLIENT_VERSION, minVersion);
            return this;
        }

        public Builder setMaxClientVersion(int maxVersion) {
            this.mBundle.putInt(MediaRouteDescriptor.KEY_MAX_CLIENT_VERSION, maxVersion);
            return this;
        }

        public MediaRouteDescriptor build() {
            ArrayList<IntentFilter> arrayList = this.mControlFilters;
            if (arrayList != null) {
                this.mBundle.putParcelableArrayList(MediaRouteDescriptor.KEY_CONTROL_FILTERS, arrayList);
            }
            ArrayList<String> arrayList2 = this.mGroupMemberIds;
            if (arrayList2 != null) {
                this.mBundle.putStringArrayList(MediaRouteDescriptor.KEY_GROUP_MEMBER_IDS, arrayList2);
            }
            return new MediaRouteDescriptor(this.mBundle);
        }
    }
}
