package androidx.mediarouter.media;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import androidx.core.content.ContextCompat;
import androidx.core.util.ObjectsCompat;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouteProviderDescriptor;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public abstract class MediaRouteProviderService extends Service {
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final int PRIVATE_MSG_CLIENT_DIED = 1;
    public static final String SERVICE_INTERFACE = "android.media.MediaRouteProviderService";
    static final String TAG = "MediaRouteProviderSrv";
    private final ArrayList<ClientRecord> mClients = new ArrayList<>();
    private MediaRouteDiscoveryRequest mCompositeDiscoveryRequest;
    final PrivateHandler mPrivateHandler;
    MediaRouteProvider mProvider;
    private final ProviderCallback mProviderCallback;
    private final ReceiveHandler mReceiveHandler;
    private final Messenger mReceiveMessenger;

    public abstract MediaRouteProvider onCreateMediaRouteProvider();

    public MediaRouteProviderService() {
        ReceiveHandler receiveHandler = new ReceiveHandler(this);
        this.mReceiveHandler = receiveHandler;
        this.mReceiveMessenger = new Messenger(receiveHandler);
        this.mPrivateHandler = new PrivateHandler();
        this.mProviderCallback = new ProviderCallback();
    }

    public MediaRouteProvider getMediaRouteProvider() {
        return this.mProvider;
    }

    public IBinder onBind(Intent intent) {
        MediaRouteProvider provider;
        if (!intent.getAction().equals("android.media.MediaRouteProviderService")) {
            return null;
        }
        if (this.mProvider == null && (provider = onCreateMediaRouteProvider()) != null) {
            String providerPackage = provider.getMetadata().getPackageName();
            if (providerPackage.equals(getPackageName())) {
                this.mProvider = provider;
                provider.setCallback(this.mProviderCallback);
            } else {
                throw new IllegalStateException("onCreateMediaRouteProvider() returned a provider whose package name does not match the package name of the service.  A media route provider service can only export its own media route providers.  Provider package name: " + providerPackage + ".  Service package name: " + getPackageName() + ".");
            }
        }
        if (this.mProvider != null) {
            return this.mReceiveMessenger.getBinder();
        }
        return null;
    }

    public boolean onUnbind(Intent intent) {
        MediaRouteProvider mediaRouteProvider = this.mProvider;
        if (mediaRouteProvider != null) {
            mediaRouteProvider.setCallback((MediaRouteProvider.Callback) null);
        }
        return super.onUnbind(intent);
    }

    /* access modifiers changed from: package-private */
    public boolean onRegisterClient(Messenger messenger, int requestId, int version) {
        if (version < 1 || findClient(messenger) >= 0) {
            return false;
        }
        ClientRecord client = new ClientRecord(messenger, version);
        if (!client.register()) {
            return false;
        }
        this.mClients.add(client);
        if (DEBUG) {
            Log.d(TAG, client + ": Registered, version=" + version);
        }
        if (requestId != 0) {
            sendReply(messenger, 2, requestId, 2, createDescriptorBundleForClientVersion(this.mProvider.getDescriptor(), client.mVersion), (Bundle) null);
        }
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onUnregisterClient(Messenger messenger, int requestId) {
        int index = findClient(messenger);
        if (index < 0) {
            return false;
        }
        ClientRecord client = this.mClients.remove(index);
        if (DEBUG) {
            Log.d(TAG, client + ": Unregistered");
        }
        client.dispose();
        sendGenericSuccess(messenger, requestId);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void onBinderDied(Messenger messenger) {
        int index = findClient(messenger);
        if (index >= 0) {
            ClientRecord client = this.mClients.remove(index);
            if (DEBUG) {
                Log.d(TAG, client + ": Binder died");
            }
            client.dispose();
        }
    }

    /* access modifiers changed from: package-private */
    public boolean onCreateRouteController(Messenger messenger, int requestId, int controllerId, String routeId, String routeGroupId) {
        ClientRecord client = getClient(messenger);
        if (client == null || !client.createRouteController(routeId, routeGroupId, controllerId)) {
            return false;
        }
        if (DEBUG) {
            Log.d(TAG, client + ": Route controller created, controllerId=" + controllerId + ", routeId=" + routeId + ", routeGroupId=" + routeGroupId);
        }
        sendGenericSuccess(messenger, requestId);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onCreateDynamicGroupRouteController(Messenger messenger, int requestId, int controllerId, String initialMemberRouteId) {
        Bundle bundle;
        ClientRecord client = getClient(messenger);
        if (client == null || (bundle = client.createDynamicGroupRouteController(initialMemberRouteId, controllerId)) == null) {
            return false;
        }
        if (DEBUG) {
            Log.d(TAG, client + ": Route controller created, controllerId=" + controllerId + ", initialMemberRouteId=" + initialMemberRouteId);
        }
        sendReply(messenger, 6, requestId, 2, bundle, (Bundle) null);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onAddMemberRoute(Messenger messenger, int requestId, int controllerId, String memberId) {
        ClientRecord client = getClient(messenger);
        if (client == null) {
            return false;
        }
        MediaRouteProvider.RouteController controller = client.getRouteController(controllerId);
        if (!(controller instanceof MediaRouteProvider.DynamicGroupRouteController)) {
            return false;
        }
        ((MediaRouteProvider.DynamicGroupRouteController) controller).onAddMemberRoute(memberId);
        if (DEBUG) {
            Log.d(TAG, client + ": Added a member route, controllerId=" + controllerId + ", memberId=" + memberId);
        }
        sendGenericSuccess(messenger, requestId);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onRemoveMemberRoute(Messenger messenger, int requestId, int controllerId, String memberId) {
        ClientRecord client = getClient(messenger);
        if (client == null) {
            return false;
        }
        MediaRouteProvider.RouteController controller = client.getRouteController(controllerId);
        if (!(controller instanceof MediaRouteProvider.DynamicGroupRouteController)) {
            return false;
        }
        ((MediaRouteProvider.DynamicGroupRouteController) controller).onRemoveMemberRoute(memberId);
        if (DEBUG) {
            Log.d(TAG, client + ": Removed a member route, controllerId=" + controllerId + ", memberId=" + memberId);
        }
        sendGenericSuccess(messenger, requestId);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onUpdateMemberRoutes(Messenger messenger, int requestId, int controllerId, List<String> memberIds) {
        ClientRecord client = getClient(messenger);
        if (client == null) {
            return false;
        }
        MediaRouteProvider.RouteController controller = client.getRouteController(controllerId);
        if (!(controller instanceof MediaRouteProvider.DynamicGroupRouteController)) {
            return false;
        }
        ((MediaRouteProvider.DynamicGroupRouteController) controller).onUpdateMemberRoutes(memberIds);
        if (DEBUG) {
            Log.d(TAG, client + ": Updated list of member routes, controllerId=" + controllerId + ", memberIds=" + memberIds);
        }
        sendGenericSuccess(messenger, requestId);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onReleaseRouteController(Messenger messenger, int requestId, int controllerId) {
        ClientRecord client = getClient(messenger);
        if (client == null || !client.releaseRouteController(controllerId)) {
            return false;
        }
        if (DEBUG) {
            Log.d(TAG, client + ": Route controller released, controllerId=" + controllerId);
        }
        sendGenericSuccess(messenger, requestId);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onSelectRoute(Messenger messenger, int requestId, int controllerId) {
        MediaRouteProvider.RouteController controller;
        ClientRecord client = getClient(messenger);
        if (client == null || (controller = client.getRouteController(controllerId)) == null) {
            return false;
        }
        controller.onSelect();
        if (DEBUG) {
            Log.d(TAG, client + ": Route selected, controllerId=" + controllerId);
        }
        sendGenericSuccess(messenger, requestId);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onUnselectRoute(Messenger messenger, int requestId, int controllerId, int reason) {
        MediaRouteProvider.RouteController controller;
        ClientRecord client = getClient(messenger);
        if (client == null || (controller = client.getRouteController(controllerId)) == null) {
            return false;
        }
        controller.onUnselect(reason);
        if (DEBUG) {
            Log.d(TAG, client + ": Route unselected, controllerId=" + controllerId);
        }
        sendGenericSuccess(messenger, requestId);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onSetRouteVolume(Messenger messenger, int requestId, int controllerId, int volume) {
        MediaRouteProvider.RouteController controller;
        ClientRecord client = getClient(messenger);
        if (client == null || (controller = client.getRouteController(controllerId)) == null) {
            return false;
        }
        controller.onSetVolume(volume);
        if (DEBUG) {
            Log.d(TAG, client + ": Route volume changed, controllerId=" + controllerId + ", volume=" + volume);
        }
        sendGenericSuccess(messenger, requestId);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onUpdateRouteVolume(Messenger messenger, int requestId, int controllerId, int delta) {
        MediaRouteProvider.RouteController controller;
        ClientRecord client = getClient(messenger);
        if (client == null || (controller = client.getRouteController(controllerId)) == null) {
            return false;
        }
        controller.onUpdateVolume(delta);
        if (DEBUG) {
            Log.d(TAG, client + ": Route volume updated, controllerId=" + controllerId + ", delta=" + delta);
        }
        sendGenericSuccess(messenger, requestId);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onRouteControlRequest(Messenger messenger, int requestId, int controllerId, Intent intent) {
        MediaRouteProvider.RouteController controller;
        ClientRecord client = getClient(messenger);
        if (client == null || (controller = client.getRouteController(controllerId)) == null) {
            return false;
        }
        MediaRouter.ControlRequestCallback callback = null;
        if (requestId != 0) {
            final ClientRecord clientRecord = client;
            final int i = controllerId;
            final Intent intent2 = intent;
            final Messenger messenger2 = messenger;
            final int i2 = requestId;
            callback = new MediaRouter.ControlRequestCallback() {
                public void onResult(Bundle data) {
                    if (MediaRouteProviderService.DEBUG) {
                        Log.d(MediaRouteProviderService.TAG, clientRecord + ": Route control request succeeded, controllerId=" + i + ", intent=" + intent2 + ", data=" + data);
                    }
                    if (MediaRouteProviderService.this.findClient(messenger2) >= 0) {
                        MediaRouteProviderService.sendReply(messenger2, 3, i2, 0, data, (Bundle) null);
                    }
                }

                public void onError(String error, Bundle data) {
                    if (MediaRouteProviderService.DEBUG) {
                        Log.d(MediaRouteProviderService.TAG, clientRecord + ": Route control request failed, controllerId=" + i + ", intent=" + intent2 + ", error=" + error + ", data=" + data);
                    }
                    if (MediaRouteProviderService.this.findClient(messenger2) < 0) {
                        return;
                    }
                    if (error != null) {
                        Bundle bundle = new Bundle();
                        bundle.putString("error", error);
                        MediaRouteProviderService.sendReply(messenger2, 4, i2, 0, data, bundle);
                        return;
                    }
                    MediaRouteProviderService.sendReply(messenger2, 4, i2, 0, data, (Bundle) null);
                }
            };
        }
        if (!controller.onControlRequest(intent, callback)) {
            return false;
        }
        if (!DEBUG) {
            return true;
        }
        Log.d(TAG, client + ": Route control request delivered, controllerId=" + controllerId + ", intent=" + intent);
        return true;
    }

    /* access modifiers changed from: package-private */
    public boolean onSetDiscoveryRequest(Messenger messenger, int requestId, MediaRouteDiscoveryRequest request) {
        ClientRecord client = getClient(messenger);
        if (client == null) {
            return false;
        }
        boolean actuallyChanged = client.setDiscoveryRequest(request);
        if (DEBUG) {
            Log.d(TAG, client + ": Set discovery request, request=" + request + ", actuallyChanged=" + actuallyChanged + ", compositeDiscoveryRequest=" + this.mCompositeDiscoveryRequest);
        }
        sendGenericSuccess(messenger, requestId);
        return true;
    }

    /* access modifiers changed from: package-private */
    public void sendDescriptorChanged(MediaRouteProviderDescriptor descriptor) {
        int count = this.mClients.size();
        for (int i = 0; i < count; i++) {
            ClientRecord client = this.mClients.get(i);
            sendReply(client.mMessenger, 5, 0, 0, createDescriptorBundleForClientVersion(descriptor, client.mVersion), (Bundle) null);
            if (DEBUG) {
                Log.d(TAG, client + ": Sent descriptor change event, descriptor=" + descriptor);
            }
        }
    }

    static Bundle createDescriptorBundleForClientVersion(MediaRouteProviderDescriptor descriptor, int clientVersion) {
        if (descriptor == null) {
            return null;
        }
        MediaRouteProviderDescriptor.Builder builder = new MediaRouteProviderDescriptor.Builder(descriptor);
        builder.setRoutes((Collection<MediaRouteDescriptor>) null);
        for (MediaRouteDescriptor route : descriptor.getRoutes()) {
            if (clientVersion >= route.getMinClientVersion() && clientVersion <= route.getMaxClientVersion()) {
                builder.addRoute(route);
            }
        }
        return builder.build().asBundle();
    }

    /* access modifiers changed from: package-private */
    public boolean updateCompositeDiscoveryRequest() {
        MediaRouteDiscoveryRequest composite = null;
        MediaRouteSelector.Builder selectorBuilder = null;
        boolean activeScan = false;
        int count = this.mClients.size();
        for (int i = 0; i < count; i++) {
            MediaRouteDiscoveryRequest request = this.mClients.get(i).mDiscoveryRequest;
            if (request != null && (!request.getSelector().isEmpty() || request.isActiveScan())) {
                activeScan |= request.isActiveScan();
                if (composite == null) {
                    composite = request;
                } else {
                    if (selectorBuilder == null) {
                        selectorBuilder = new MediaRouteSelector.Builder(composite.getSelector());
                    }
                    selectorBuilder.addSelector(request.getSelector());
                }
            }
        }
        if (selectorBuilder != null) {
            composite = new MediaRouteDiscoveryRequest(selectorBuilder.build(), activeScan);
        }
        if (ObjectsCompat.equals(this.mCompositeDiscoveryRequest, composite)) {
            return false;
        }
        this.mCompositeDiscoveryRequest = composite;
        this.mProvider.setDiscoveryRequest(composite);
        return true;
    }

    private ClientRecord getClient(Messenger messenger) {
        int index = findClient(messenger);
        if (index >= 0) {
            return this.mClients.get(index);
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public int findClient(Messenger messenger) {
        int count = this.mClients.size();
        for (int i = 0; i < count; i++) {
            if (this.mClients.get(i).hasMessenger(messenger)) {
                return i;
            }
        }
        return -1;
    }

    static void sendGenericFailure(Messenger messenger, int requestId) {
        if (requestId != 0) {
            sendReply(messenger, 0, requestId, 0, (Object) null, (Bundle) null);
        }
    }

    private static void sendGenericSuccess(Messenger messenger, int requestId) {
        if (requestId != 0) {
            sendReply(messenger, 1, requestId, 0, (Object) null, (Bundle) null);
        }
    }

    static void sendReply(Messenger messenger, int what, int requestId, int arg, Object obj, Bundle data) {
        Message msg = Message.obtain();
        msg.what = what;
        msg.arg1 = requestId;
        msg.arg2 = arg;
        msg.obj = obj;
        msg.setData(data);
        try {
            messenger.send(msg);
        } catch (DeadObjectException e) {
        } catch (RemoteException ex) {
            Log.e(TAG, "Could not send message to " + getClientId(messenger), ex);
        }
    }

    static String getClientId(Messenger messenger) {
        return "Client connection " + messenger.getBinder().toString();
    }

    private final class PrivateHandler extends Handler {
        PrivateHandler() {
        }

        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                MediaRouteProviderService.this.onBinderDied((Messenger) msg.obj);
            }
        }
    }

    private final class ProviderCallback extends MediaRouteProvider.Callback {
        ProviderCallback() {
        }

        public void onDescriptorChanged(MediaRouteProvider provider, MediaRouteProviderDescriptor descriptor) {
            MediaRouteProviderService.this.sendDescriptorChanged(descriptor);
        }
    }

    private final class ClientRecord implements IBinder.DeathRecipient {
        private final SparseArray<MediaRouteProvider.RouteController> mControllers = new SparseArray<>();
        public MediaRouteDiscoveryRequest mDiscoveryRequest;
        final MediaRouteProvider.DynamicGroupRouteController.OnDynamicRoutesChangedListener mDynamicRoutesChangedListener = new MediaRouteProvider.DynamicGroupRouteController.OnDynamicRoutesChangedListener() {
            public void onRoutesChanged(MediaRouteProvider.DynamicGroupRouteController controller, Collection<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> routes) {
                ClientRecord.this.sendDynamicRouteDescriptors(controller, routes);
            }
        };
        public final Messenger mMessenger;
        public final int mVersion;

        public ClientRecord(Messenger messenger, int version) {
            this.mMessenger = messenger;
            this.mVersion = version;
        }

        public boolean register() {
            try {
                this.mMessenger.getBinder().linkToDeath(this, 0);
                return true;
            } catch (RemoteException e) {
                binderDied();
                return false;
            }
        }

        public void dispose() {
            int count = this.mControllers.size();
            for (int i = 0; i < count; i++) {
                this.mControllers.valueAt(i).onRelease();
            }
            this.mControllers.clear();
            this.mMessenger.getBinder().unlinkToDeath(this, 0);
            setDiscoveryRequest((MediaRouteDiscoveryRequest) null);
        }

        public boolean hasMessenger(Messenger other) {
            return this.mMessenger.getBinder() == other.getBinder();
        }

        public boolean createRouteController(String routeId, String routeGroupId, int controllerId) {
            MediaRouteProvider.RouteController controller;
            if (this.mControllers.indexOfKey(controllerId) >= 0) {
                return false;
            }
            if (routeGroupId == null) {
                controller = MediaRouteProviderService.this.mProvider.onCreateRouteController(routeId);
            } else {
                controller = MediaRouteProviderService.this.mProvider.onCreateRouteController(routeId, routeGroupId);
            }
            if (controller == null) {
                return false;
            }
            this.mControllers.put(controllerId, controller);
            return true;
        }

        public Bundle createDynamicGroupRouteController(String initialMemberRouteId, int controllerId) {
            if (this.mControllers.indexOfKey(controllerId) >= 0) {
                return null;
            }
            MediaRouteProvider.DynamicGroupRouteController controller = MediaRouteProviderService.this.mProvider.onCreateDynamicGroupRouteController(initialMemberRouteId);
            controller.setOnDynamicRoutesChangedListener(ContextCompat.getMainExecutor(MediaRouteProviderService.this.getApplicationContext()), this.mDynamicRoutesChangedListener);
            if (controller == null) {
                return null;
            }
            this.mControllers.put(controllerId, controller);
            Bundle bundle = new Bundle();
            bundle.putString(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_ID, initialMemberRouteId);
            bundle.putString(MediaRouteProviderProtocol.DATA_KEY_GROUPABLE_SECION_TITLE, controller.getGroupableSelectionTitle());
            bundle.putString(MediaRouteProviderProtocol.DATA_KEY_TRANSFERABLE_SECTION_TITLE, controller.getTransferableSectionTitle());
            return bundle;
        }

        public boolean releaseRouteController(int controllerId) {
            MediaRouteProvider.RouteController controller = this.mControllers.get(controllerId);
            if (controller == null) {
                return false;
            }
            this.mControllers.remove(controllerId);
            controller.onRelease();
            return true;
        }

        public MediaRouteProvider.RouteController getRouteController(int controllerId) {
            return this.mControllers.get(controllerId);
        }

        public boolean setDiscoveryRequest(MediaRouteDiscoveryRequest request) {
            if (ObjectsCompat.equals(this.mDiscoveryRequest, request)) {
                return false;
            }
            this.mDiscoveryRequest = request;
            return MediaRouteProviderService.this.updateCompositeDiscoveryRequest();
        }

        public void binderDied() {
            MediaRouteProviderService.this.mPrivateHandler.obtainMessage(1, this.mMessenger).sendToTarget();
        }

        public String toString() {
            return MediaRouteProviderService.getClientId(this.mMessenger);
        }

        /* access modifiers changed from: package-private */
        public void sendDynamicRouteDescriptors(MediaRouteProvider.DynamicGroupRouteController controller, Collection<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> descriptors) {
            int index = this.mControllers.indexOfValue(controller);
            if (index < 0) {
                Log.d(MediaRouteProviderService.TAG, "Ignoring unknown dynamic group route controller: " + controller);
                return;
            }
            int controllerId = this.mControllers.keyAt(index);
            ArrayList arrayList = new ArrayList();
            for (MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor descriptor : descriptors) {
                arrayList.add(descriptor.toBundle());
            }
            Bundle bundle = new Bundle();
            bundle.putParcelableArrayList(MediaRouteProviderProtocol.DATA_KEY_DYNAMIC_ROUTE_DESCRIPTORS, arrayList);
            MediaRouteProviderService.sendReply(this.mMessenger, 7, 0, controllerId, bundle, (Bundle) null);
        }
    }

    private static final class ReceiveHandler extends Handler {
        private final WeakReference<MediaRouteProviderService> mServiceRef;

        public ReceiveHandler(MediaRouteProviderService service) {
            this.mServiceRef = new WeakReference<>(service);
        }

        public void handleMessage(Message msg) {
            Messenger messenger = msg.replyTo;
            if (MediaRouteProviderProtocol.isValidRemoteMessenger(messenger)) {
                int what = msg.what;
                int requestId = msg.arg1;
                int arg = msg.arg2;
                Object obj = msg.obj;
                Bundle data = msg.peekData();
                if (!processMessage(what, messenger, requestId, arg, obj, data)) {
                    if (MediaRouteProviderService.DEBUG) {
                        Log.d(MediaRouteProviderService.TAG, MediaRouteProviderService.getClientId(messenger) + ": Message failed, what=" + what + ", requestId=" + requestId + ", arg=" + arg + ", obj=" + obj + ", data=" + data);
                    }
                    MediaRouteProviderService.sendGenericFailure(messenger, requestId);
                }
            } else if (MediaRouteProviderService.DEBUG) {
                Log.d(MediaRouteProviderService.TAG, "Ignoring message without valid reply messenger.");
            }
        }

        private boolean processMessage(int what, Messenger messenger, int requestId, int arg, Object obj, Bundle data) {
            MediaRouteProviderService service = (MediaRouteProviderService) this.mServiceRef.get();
            int reason = 0;
            if (service != null) {
                switch (what) {
                    case 1:
                        return service.onRegisterClient(messenger, requestId, arg);
                    case 2:
                        return service.onUnregisterClient(messenger, requestId);
                    case 3:
                        String routeId = data.getString(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_ID);
                        String routeGroupId = data.getString(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_LIBRARY_GROUP);
                        if (routeId != null) {
                            return service.onCreateRouteController(messenger, requestId, arg, routeId, routeGroupId);
                        }
                        break;
                    case 4:
                        return service.onReleaseRouteController(messenger, requestId, arg);
                    case 5:
                        return service.onSelectRoute(messenger, requestId, arg);
                    case 6:
                        if (data != null) {
                            reason = data.getInt(MediaRouteProviderProtocol.CLIENT_DATA_UNSELECT_REASON, 0);
                        }
                        return service.onUnselectRoute(messenger, requestId, arg, reason);
                    case 7:
                        int volume = data.getInt(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, -1);
                        if (volume >= 0) {
                            return service.onSetRouteVolume(messenger, requestId, arg, volume);
                        }
                        break;
                    case 8:
                        int delta = data.getInt(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, 0);
                        if (delta != 0) {
                            return service.onUpdateRouteVolume(messenger, requestId, arg, delta);
                        }
                        break;
                    case 9:
                        if (obj instanceof Intent) {
                            return service.onRouteControlRequest(messenger, requestId, arg, (Intent) obj);
                        }
                        break;
                    case 10:
                        if (obj == null || (obj instanceof Bundle)) {
                            MediaRouteDiscoveryRequest request = MediaRouteDiscoveryRequest.fromBundle((Bundle) obj);
                            return service.onSetDiscoveryRequest(messenger, requestId, (request == null || !request.isValid()) ? null : request);
                        }
                        break;
                    case 11:
                        String memberId = data.getString(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_ID);
                        if (memberId != null) {
                            return service.onCreateDynamicGroupRouteController(messenger, requestId, arg, memberId);
                        }
                        break;
                    case 12:
                        String memberId2 = data.getString(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_ID);
                        if (memberId2 != null) {
                            return service.onAddMemberRoute(messenger, requestId, arg, memberId2);
                        }
                        break;
                    case 13:
                        String memberId3 = data.getString(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_ID);
                        if (memberId3 != null) {
                            return service.onRemoveMemberRoute(messenger, requestId, arg, memberId3);
                        }
                        break;
                    case 14:
                        ArrayList<String> memberIds = data.getStringArrayList(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_IDS);
                        if (memberIds != null) {
                            return service.onUpdateMemberRoutes(messenger, requestId, arg, memberIds);
                        }
                        break;
                }
            }
            return false;
        }
    }
}
