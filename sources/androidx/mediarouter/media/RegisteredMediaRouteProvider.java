package androidx.mediarouter.media;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.DeadObjectException;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseArray;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

final class RegisteredMediaRouteProvider extends MediaRouteProvider implements ServiceConnection {
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final String TAG = "MediaRouteProviderProxy";
    private Connection mActiveConnection;
    private boolean mBound;
    private final ComponentName mComponentName;
    private boolean mConnectionReady;
    private final ArrayList<ControllerConnection> mControllerConnections = new ArrayList<>();
    final PrivateHandler mPrivateHandler;
    private boolean mStarted;

    interface ControllerConnection {
        void attachConnection(Connection connection);

        void detachConnection();

        int getControllerId();
    }

    public RegisteredMediaRouteProvider(Context context, ComponentName componentName) {
        super(context, new MediaRouteProvider.ProviderMetadata(componentName));
        this.mComponentName = componentName;
        this.mPrivateHandler = new PrivateHandler();
    }

    public MediaRouteProvider.RouteController onCreateRouteController(String routeId) {
        if (routeId != null) {
            return createRouteController(routeId, (String) null);
        }
        throw new IllegalArgumentException("routeId cannot be null");
    }

    public MediaRouteProvider.RouteController onCreateRouteController(String routeId, String routeGroupId) {
        if (routeId == null) {
            throw new IllegalArgumentException("routeId cannot be null");
        } else if (routeGroupId != null) {
            return createRouteController(routeId, routeGroupId);
        } else {
            throw new IllegalArgumentException("routeGroupId cannot be null");
        }
    }

    public MediaRouteProvider.DynamicGroupRouteController onCreateDynamicGroupRouteController(String initialMemberRouteId) {
        if (initialMemberRouteId != null) {
            return createDynamicGroupRouteController(initialMemberRouteId);
        }
        throw new IllegalArgumentException("initialMemberRouteId cannot be null.");
    }

    public void onDiscoveryRequestChanged(MediaRouteDiscoveryRequest request) {
        if (this.mConnectionReady) {
            this.mActiveConnection.setDiscoveryRequest(request);
        }
        updateBinding();
    }

    public void onServiceConnected(ComponentName name, IBinder service) {
        boolean z = DEBUG;
        if (z) {
            Log.d(TAG, this + ": Connected");
        }
        if (this.mBound) {
            disconnect();
            Messenger messenger = service != null ? new Messenger(service) : null;
            if (MediaRouteProviderProtocol.isValidRemoteMessenger(messenger)) {
                Connection connection = new Connection(messenger);
                if (connection.register()) {
                    this.mActiveConnection = connection;
                } else if (z) {
                    Log.d(TAG, this + ": Registration failed");
                }
            } else {
                Log.e(TAG, this + ": Service returned invalid messenger binder");
            }
        }
    }

    public void onServiceDisconnected(ComponentName name) {
        if (DEBUG) {
            Log.d(TAG, this + ": Service disconnected");
        }
        disconnect();
    }

    public String toString() {
        return "Service connection " + this.mComponentName.flattenToShortString();
    }

    public boolean hasComponentName(String packageName, String className) {
        return this.mComponentName.getPackageName().equals(packageName) && this.mComponentName.getClassName().equals(className);
    }

    public void start() {
        if (!this.mStarted) {
            if (DEBUG) {
                Log.d(TAG, this + ": Starting");
            }
            this.mStarted = true;
            updateBinding();
        }
    }

    public void stop() {
        if (this.mStarted) {
            if (DEBUG) {
                Log.d(TAG, this + ": Stopping");
            }
            this.mStarted = false;
            updateBinding();
        }
    }

    public void rebindIfDisconnected() {
        if (this.mActiveConnection == null && shouldBind()) {
            unbind();
            bind();
        }
    }

    private void updateBinding() {
        if (shouldBind()) {
            bind();
        } else {
            unbind();
        }
    }

    private boolean shouldBind() {
        if (!this.mStarted) {
            return false;
        }
        if (getDiscoveryRequest() == null && this.mControllerConnections.isEmpty()) {
            return false;
        }
        return true;
    }

    private void bind() {
        if (!this.mBound) {
            boolean z = DEBUG;
            if (z) {
                Log.d(TAG, this + ": Binding");
            }
            Intent service = new Intent("android.media.MediaRouteProviderService");
            service.setComponent(this.mComponentName);
            try {
                boolean bindService = getContext().bindService(service, this, 1);
                this.mBound = bindService;
                if (!bindService && z) {
                    Log.d(TAG, this + ": Bind failed");
                }
            } catch (SecurityException ex) {
                if (DEBUG) {
                    Log.d(TAG, this + ": Bind failed", ex);
                }
            }
        }
    }

    private void unbind() {
        if (this.mBound) {
            if (DEBUG) {
                Log.d(TAG, this + ": Unbinding");
            }
            this.mBound = false;
            disconnect();
            try {
                getContext().unbindService(this);
            } catch (IllegalArgumentException ex) {
                Log.e(TAG, this + ": unbindService failed", ex);
            }
        }
    }

    private MediaRouteProvider.RouteController createRouteController(String routeId, String routeGroupId) {
        MediaRouteProviderDescriptor descriptor = getDescriptor();
        if (descriptor == null) {
            return null;
        }
        List<MediaRouteDescriptor> routes = descriptor.getRoutes();
        int count = routes.size();
        for (int i = 0; i < count; i++) {
            if (routes.get(i).getId().equals(routeId)) {
                RegisteredRouteController registeredRouteController = new RegisteredRouteController(routeId, routeGroupId);
                this.mControllerConnections.add(registeredRouteController);
                if (this.mConnectionReady) {
                    registeredRouteController.attachConnection(this.mActiveConnection);
                }
                updateBinding();
                return registeredRouteController;
            }
        }
        return null;
    }

    private MediaRouteProvider.DynamicGroupRouteController createDynamicGroupRouteController(String initialMemberRouteId) {
        MediaRouteProviderDescriptor descriptor = getDescriptor();
        if (descriptor == null) {
            return null;
        }
        List<MediaRouteDescriptor> routes = descriptor.getRoutes();
        int count = routes.size();
        for (int i = 0; i < count; i++) {
            if (routes.get(i).getId().equals(initialMemberRouteId)) {
                RegisteredDynamicController registeredDynamicController = new RegisteredDynamicController(initialMemberRouteId);
                this.mControllerConnections.add(registeredDynamicController);
                if (this.mConnectionReady) {
                    registeredDynamicController.attachConnection(this.mActiveConnection);
                }
                updateBinding();
                return registeredDynamicController;
            }
        }
        return null;
    }

    /* access modifiers changed from: package-private */
    public void onConnectionReady(Connection connection) {
        if (this.mActiveConnection == connection) {
            this.mConnectionReady = true;
            attachControllersToConnection();
            MediaRouteDiscoveryRequest request = getDiscoveryRequest();
            if (request != null) {
                this.mActiveConnection.setDiscoveryRequest(request);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void onConnectionDied(Connection connection) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d(TAG, this + ": Service connection died");
            }
            disconnect();
        }
    }

    /* access modifiers changed from: package-private */
    public void onConnectionError(Connection connection, String error) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d(TAG, this + ": Service connection error - " + error);
            }
            unbind();
        }
    }

    /* access modifiers changed from: package-private */
    public void onConnectionDescriptorChanged(Connection connection, MediaRouteProviderDescriptor descriptor) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d(TAG, this + ": Descriptor changed, descriptor=" + descriptor);
            }
            setDescriptor(descriptor);
        }
    }

    /* access modifiers changed from: package-private */
    public void onDynamicRouteDescriptorChanged(Connection connection, int controllerId, List<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> descriptors) {
        if (this.mActiveConnection == connection) {
            if (DEBUG) {
                Log.d(TAG, this + ": DynamicRouteDescriptors changed, descriptors=" + descriptors);
            }
            ControllerConnection controller = findControllerById(controllerId);
            if (controller instanceof RegisteredDynamicController) {
                ((RegisteredDynamicController) controller).onDynamicRoutesChanged(descriptors);
            }
        }
    }

    private ControllerConnection findControllerById(int id) {
        Iterator<ControllerConnection> it = this.mControllerConnections.iterator();
        while (it.hasNext()) {
            ControllerConnection controller = it.next();
            if (controller.getControllerId() == id) {
                return controller;
            }
        }
        return null;
    }

    private void disconnect() {
        if (this.mActiveConnection != null) {
            setDescriptor((MediaRouteProviderDescriptor) null);
            this.mConnectionReady = false;
            detachControllersFromConnection();
            this.mActiveConnection.dispose();
            this.mActiveConnection = null;
        }
    }

    /* access modifiers changed from: package-private */
    public void onControllerReleased(ControllerConnection controllerConnection) {
        this.mControllerConnections.remove(controllerConnection);
        controllerConnection.detachConnection();
        updateBinding();
    }

    private void attachControllersToConnection() {
        int count = this.mControllerConnections.size();
        for (int i = 0; i < count; i++) {
            this.mControllerConnections.get(i).attachConnection(this.mActiveConnection);
        }
    }

    private void detachControllersFromConnection() {
        int count = this.mControllerConnections.size();
        for (int i = 0; i < count; i++) {
            this.mControllerConnections.get(i).detachConnection();
        }
    }

    private final class RegisteredDynamicController extends MediaRouteProvider.DynamicGroupRouteController implements ControllerConnection {
        private Connection mConnection;
        private int mControllerId = -1;
        MediaRouteProvider.DynamicGroupRouteController.OnDynamicRoutesChangedListener mDynamicRoutesChangedListener;
        String mGroupableSectionTitle;
        private final String mInitialMemberRouteId;
        private Executor mListenerExecutor;
        private int mPendingSetVolume = -1;
        private int mPendingUpdateVolumeDelta;
        private boolean mSelected;
        String mTransferableSectionTitle;

        RegisteredDynamicController(String initialMemberRouteId) {
            this.mInitialMemberRouteId = initialMemberRouteId;
        }

        public int getControllerId() {
            return this.mControllerId;
        }

        public void attachConnection(Connection connection) {
            MediaRouter.ControlRequestCallback callback = new MediaRouter.ControlRequestCallback() {
                public void onResult(Bundle data) {
                    RegisteredDynamicController.this.mGroupableSectionTitle = data.getString(MediaRouteProviderProtocol.DATA_KEY_GROUPABLE_SECION_TITLE);
                    RegisteredDynamicController.this.mTransferableSectionTitle = data.getString(MediaRouteProviderProtocol.DATA_KEY_TRANSFERABLE_SECTION_TITLE);
                }

                public void onError(String error, Bundle data) {
                    Log.d(RegisteredMediaRouteProvider.TAG, "Error: " + error + ", data: " + data);
                }
            };
            this.mConnection = connection;
            int createDynamicGroupRouteController = connection.createDynamicGroupRouteController(this.mInitialMemberRouteId, callback);
            this.mControllerId = createDynamicGroupRouteController;
            if (this.mSelected) {
                connection.selectRoute(createDynamicGroupRouteController);
                int i = this.mPendingSetVolume;
                if (i >= 0) {
                    connection.setVolume(this.mControllerId, i);
                    this.mPendingSetVolume = -1;
                }
                int i2 = this.mPendingUpdateVolumeDelta;
                if (i2 != 0) {
                    connection.updateVolume(this.mControllerId, i2);
                    this.mPendingUpdateVolumeDelta = 0;
                }
            }
        }

        public void detachConnection() {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.releaseRouteController(this.mControllerId);
                this.mConnection = null;
                this.mControllerId = 0;
            }
        }

        public void onRelease() {
            RegisteredMediaRouteProvider.this.onControllerReleased(this);
        }

        public void onSelect() {
            this.mSelected = true;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.selectRoute(this.mControllerId);
            }
        }

        public void onUnselect() {
            onUnselect(0);
        }

        public void onUnselect(int reason) {
            this.mSelected = false;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.unselectRoute(this.mControllerId, reason);
            }
        }

        public void onSetVolume(int volume) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.setVolume(this.mControllerId, volume);
                return;
            }
            this.mPendingSetVolume = volume;
            this.mPendingUpdateVolumeDelta = 0;
        }

        public void onUpdateVolume(int delta) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.updateVolume(this.mControllerId, delta);
            } else {
                this.mPendingUpdateVolumeDelta += delta;
            }
        }

        public boolean onControlRequest(Intent intent, MediaRouter.ControlRequestCallback callback) {
            Connection connection = this.mConnection;
            if (connection != null) {
                return connection.sendControlRequest(this.mControllerId, intent, callback);
            }
            return false;
        }

        public String getGroupableSelectionTitle() {
            return this.mGroupableSectionTitle;
        }

        public String getTransferableSectionTitle() {
            return this.mTransferableSectionTitle;
        }

        public void onUpdateMemberRoutes(List<String> routeIds) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.updateMemberRoutes(this.mControllerId, routeIds);
            }
        }

        public void onAddMemberRoute(String routeId) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.addMemberRoute(this.mControllerId, routeId);
            }
        }

        public void onRemoveMemberRoute(String routeId) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.removeMemberRoute(this.mControllerId, routeId);
            }
        }

        /* access modifiers changed from: package-private */
        public void onDynamicRoutesChanged(List<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> routes) {
            notifyDynamicRoutesChanged(routes);
        }
    }

    private final class RegisteredRouteController extends MediaRouteProvider.RouteController implements ControllerConnection {
        private Connection mConnection;
        private int mControllerId;
        private int mPendingSetVolume = -1;
        private int mPendingUpdateVolumeDelta;
        private final String mRouteGroupId;
        private final String mRouteId;
        private boolean mSelected;

        RegisteredRouteController(String routeId, String routeGroupId) {
            this.mRouteId = routeId;
            this.mRouteGroupId = routeGroupId;
        }

        public int getControllerId() {
            return this.mControllerId;
        }

        public void attachConnection(Connection connection) {
            this.mConnection = connection;
            int createRouteController = connection.createRouteController(this.mRouteId, this.mRouteGroupId);
            this.mControllerId = createRouteController;
            if (this.mSelected) {
                connection.selectRoute(createRouteController);
                int i = this.mPendingSetVolume;
                if (i >= 0) {
                    connection.setVolume(this.mControllerId, i);
                    this.mPendingSetVolume = -1;
                }
                int i2 = this.mPendingUpdateVolumeDelta;
                if (i2 != 0) {
                    connection.updateVolume(this.mControllerId, i2);
                    this.mPendingUpdateVolumeDelta = 0;
                }
            }
        }

        public void detachConnection() {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.releaseRouteController(this.mControllerId);
                this.mConnection = null;
                this.mControllerId = 0;
            }
        }

        public void onRelease() {
            RegisteredMediaRouteProvider.this.onControllerReleased(this);
        }

        public void onSelect() {
            this.mSelected = true;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.selectRoute(this.mControllerId);
            }
        }

        public void onUnselect() {
            onUnselect(0);
        }

        public void onUnselect(int reason) {
            this.mSelected = false;
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.unselectRoute(this.mControllerId, reason);
            }
        }

        public void onSetVolume(int volume) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.setVolume(this.mControllerId, volume);
                return;
            }
            this.mPendingSetVolume = volume;
            this.mPendingUpdateVolumeDelta = 0;
        }

        public void onUpdateVolume(int delta) {
            Connection connection = this.mConnection;
            if (connection != null) {
                connection.updateVolume(this.mControllerId, delta);
            } else {
                this.mPendingUpdateVolumeDelta += delta;
            }
        }

        public boolean onControlRequest(Intent intent, MediaRouter.ControlRequestCallback callback) {
            Connection connection = this.mConnection;
            if (connection != null) {
                return connection.sendControlRequest(this.mControllerId, intent, callback);
            }
            return false;
        }
    }

    private final class Connection implements IBinder.DeathRecipient {
        private int mNextControllerId = 1;
        private int mNextRequestId = 1;
        private final SparseArray<MediaRouter.ControlRequestCallback> mPendingCallbacks = new SparseArray<>();
        private int mPendingRegisterRequestId;
        private final ReceiveHandler mReceiveHandler;
        private final Messenger mReceiveMessenger;
        private final Messenger mServiceMessenger;
        private int mServiceVersion;

        public Connection(Messenger serviceMessenger) {
            this.mServiceMessenger = serviceMessenger;
            ReceiveHandler receiveHandler = new ReceiveHandler(this);
            this.mReceiveHandler = receiveHandler;
            this.mReceiveMessenger = new Messenger(receiveHandler);
        }

        public boolean register() {
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            this.mPendingRegisterRequestId = i;
            if (!sendRequest(1, i, 3, (Object) null, (Bundle) null)) {
                return false;
            }
            try {
                this.mServiceMessenger.getBinder().linkToDeath(this, 0);
                return true;
            } catch (RemoteException e) {
                binderDied();
                return false;
            }
        }

        public void dispose() {
            sendRequest(2, 0, 0, (Object) null, (Bundle) null);
            this.mReceiveHandler.dispose();
            this.mServiceMessenger.getBinder().unlinkToDeath(this, 0);
            RegisteredMediaRouteProvider.this.mPrivateHandler.post(new Runnable() {
                public void run() {
                    Connection.this.failPendingCallbacks();
                }
            });
        }

        /* access modifiers changed from: package-private */
        public void failPendingCallbacks() {
            int count = this.mPendingCallbacks.size();
            for (int i = 0; i < count; i++) {
                this.mPendingCallbacks.valueAt(i).onError((String) null, (Bundle) null);
            }
            this.mPendingCallbacks.clear();
        }

        public boolean onGenericFailure(int requestId) {
            if (requestId == this.mPendingRegisterRequestId) {
                this.mPendingRegisterRequestId = 0;
                RegisteredMediaRouteProvider.this.onConnectionError(this, "Registration failed");
            }
            MediaRouter.ControlRequestCallback callback = this.mPendingCallbacks.get(requestId);
            if (callback == null) {
                return true;
            }
            this.mPendingCallbacks.remove(requestId);
            callback.onError((String) null, (Bundle) null);
            return true;
        }

        public boolean onGenericSuccess(int requestId) {
            return true;
        }

        public boolean onRegistered(int requestId, int serviceVersion, Bundle descriptorBundle) {
            if (this.mServiceVersion != 0 || requestId != this.mPendingRegisterRequestId || serviceVersion < 1) {
                return false;
            }
            this.mPendingRegisterRequestId = 0;
            this.mServiceVersion = serviceVersion;
            RegisteredMediaRouteProvider.this.onConnectionDescriptorChanged(this, MediaRouteProviderDescriptor.fromBundle(descriptorBundle));
            RegisteredMediaRouteProvider.this.onConnectionReady(this);
            return true;
        }

        public boolean onDescriptorChanged(Bundle descriptorBundle) {
            if (this.mServiceVersion == 0) {
                return false;
            }
            RegisteredMediaRouteProvider.this.onConnectionDescriptorChanged(this, MediaRouteProviderDescriptor.fromBundle(descriptorBundle));
            return true;
        }

        public boolean onDynamicRouteDescriptorsChanged(int controllerId, Bundle descriptorsBundle) {
            if (this.mServiceVersion == 0) {
                return false;
            }
            ArrayList<Bundle> bundles = descriptorsBundle.getParcelableArrayList(MediaRouteProviderProtocol.DATA_KEY_DYNAMIC_ROUTE_DESCRIPTORS);
            List<MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor> descriptors = new ArrayList<>();
            Iterator<Bundle> it = bundles.iterator();
            while (it.hasNext()) {
                descriptors.add(MediaRouteProvider.DynamicGroupRouteController.DynamicRouteDescriptor.fromBundle(it.next()));
            }
            RegisteredMediaRouteProvider.this.onDynamicRouteDescriptorChanged(this, controllerId, descriptors);
            return true;
        }

        public boolean onControlRequestSucceeded(int requestId, Bundle data) {
            MediaRouter.ControlRequestCallback callback = this.mPendingCallbacks.get(requestId);
            if (callback == null) {
                return false;
            }
            this.mPendingCallbacks.remove(requestId);
            callback.onResult(data);
            return true;
        }

        public boolean onControlRequestFailed(int requestId, String error, Bundle data) {
            MediaRouter.ControlRequestCallback callback = this.mPendingCallbacks.get(requestId);
            if (callback == null) {
                return false;
            }
            this.mPendingCallbacks.remove(requestId);
            callback.onError(error, data);
            return true;
        }

        public void onDynamicGroupRouteControllerCreated(int requestId, Bundle data) {
            MediaRouter.ControlRequestCallback callback = this.mPendingCallbacks.get(requestId);
            if (data == null || !data.containsKey(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_ID)) {
                callback.onError("DynamicGroupRouteController is created without valid route id.", data);
                return;
            }
            this.mPendingCallbacks.remove(requestId);
            callback.onResult(data);
        }

        public void binderDied() {
            RegisteredMediaRouteProvider.this.mPrivateHandler.post(new Runnable() {
                public void run() {
                    RegisteredMediaRouteProvider.this.onConnectionDied(Connection.this);
                }
            });
        }

        public int createRouteController(String routeId, String routeGroupId) {
            int controllerId = this.mNextControllerId;
            this.mNextControllerId = controllerId + 1;
            Bundle data = new Bundle();
            data.putString(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_ID, routeId);
            data.putString(MediaRouteProviderProtocol.CLIENT_DATA_ROUTE_LIBRARY_GROUP, routeGroupId);
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            sendRequest(3, i, controllerId, (Object) null, data);
            return controllerId;
        }

        public int createDynamicGroupRouteController(String initialMemberRouteId, MediaRouter.ControlRequestCallback callback) {
            int controllerId = this.mNextControllerId;
            this.mNextControllerId = controllerId + 1;
            int requestId = this.mNextRequestId;
            this.mNextRequestId = requestId + 1;
            Bundle data = new Bundle();
            data.putString(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_ID, initialMemberRouteId);
            sendRequest(11, requestId, controllerId, (Object) null, data);
            this.mPendingCallbacks.put(requestId, callback);
            return controllerId;
        }

        public void releaseRouteController(int controllerId) {
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            sendRequest(4, i, controllerId, (Object) null, (Bundle) null);
        }

        public void selectRoute(int controllerId) {
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            sendRequest(5, i, controllerId, (Object) null, (Bundle) null);
        }

        public void unselectRoute(int controllerId, int reason) {
            Bundle extras = new Bundle();
            extras.putInt(MediaRouteProviderProtocol.CLIENT_DATA_UNSELECT_REASON, reason);
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            sendRequest(6, i, controllerId, (Object) null, extras);
        }

        public void setVolume(int controllerId, int volume) {
            Bundle data = new Bundle();
            data.putInt(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, volume);
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            sendRequest(7, i, controllerId, (Object) null, data);
        }

        public void updateVolume(int controllerId, int delta) {
            Bundle data = new Bundle();
            data.putInt(MediaRouteProviderProtocol.CLIENT_DATA_VOLUME, delta);
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            sendRequest(8, i, controllerId, (Object) null, data);
        }

        public boolean sendControlRequest(int controllerId, Intent intent, MediaRouter.ControlRequestCallback callback) {
            int requestId = this.mNextRequestId;
            this.mNextRequestId = requestId + 1;
            if (!sendRequest(9, requestId, controllerId, intent, (Bundle) null)) {
                return false;
            }
            if (callback == null) {
                return true;
            }
            this.mPendingCallbacks.put(requestId, callback);
            return true;
        }

        public void updateMemberRoutes(int controllerId, List<String> memberRouteIds) {
            Bundle data = new Bundle();
            data.putStringArrayList(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_IDS, new ArrayList(memberRouteIds));
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            sendRequest(14, i, controllerId, (Object) null, data);
        }

        public void addMemberRoute(int controllerId, String memberRouteId) {
            Bundle data = new Bundle();
            data.putString(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_ID, memberRouteId);
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            sendRequest(12, i, controllerId, (Object) null, data);
        }

        public void removeMemberRoute(int controllerId, String memberRouteId) {
            Bundle data = new Bundle();
            data.putString(MediaRouteProviderProtocol.CLIENT_DATA_MEMBER_ROUTE_ID, memberRouteId);
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            sendRequest(13, i, controllerId, (Object) null, data);
        }

        public void setDiscoveryRequest(MediaRouteDiscoveryRequest request) {
            int i = this.mNextRequestId;
            this.mNextRequestId = i + 1;
            sendRequest(10, i, 0, request != null ? request.asBundle() : null, (Bundle) null);
        }

        private boolean sendRequest(int what, int requestId, int arg, Object obj, Bundle data) {
            Message msg = Message.obtain();
            msg.what = what;
            msg.arg1 = requestId;
            msg.arg2 = arg;
            msg.obj = obj;
            msg.setData(data);
            msg.replyTo = this.mReceiveMessenger;
            try {
                this.mServiceMessenger.send(msg);
                return true;
            } catch (DeadObjectException e) {
                return false;
            } catch (RemoteException ex) {
                if (what == 2) {
                    return false;
                }
                Log.e(RegisteredMediaRouteProvider.TAG, "Could not send message to service.", ex);
                return false;
            }
        }
    }

    private static final class PrivateHandler extends Handler {
        PrivateHandler() {
        }
    }

    private static final class ReceiveHandler extends Handler {
        private final WeakReference<Connection> mConnectionRef;

        public ReceiveHandler(Connection connection) {
            this.mConnectionRef = new WeakReference<>(connection);
        }

        public void dispose() {
            this.mConnectionRef.clear();
        }

        public void handleMessage(Message msg) {
            Connection connection = (Connection) this.mConnectionRef.get();
            if (connection != null) {
                if (!processMessage(connection, msg.what, msg.arg1, msg.arg2, msg.obj, msg.peekData()) && RegisteredMediaRouteProvider.DEBUG) {
                    Log.d(RegisteredMediaRouteProvider.TAG, "Unhandled message from server: " + msg);
                }
            }
        }

        private boolean processMessage(Connection connection, int what, int requestId, int arg, Object obj, Bundle data) {
            String error;
            switch (what) {
                case 0:
                    connection.onGenericFailure(requestId);
                    return true;
                case 1:
                    connection.onGenericSuccess(requestId);
                    return true;
                case 2:
                    if (obj == null || (obj instanceof Bundle)) {
                        return connection.onRegistered(requestId, arg, (Bundle) obj);
                    }
                    return false;
                case 3:
                    if (obj == null || (obj instanceof Bundle)) {
                        return connection.onControlRequestSucceeded(requestId, (Bundle) obj);
                    }
                    return false;
                case 4:
                    if (obj != null && !(obj instanceof Bundle)) {
                        return false;
                    }
                    if (data == null) {
                        error = null;
                    } else {
                        error = data.getString("error");
                    }
                    return connection.onControlRequestFailed(requestId, error, (Bundle) obj);
                case 5:
                    if (obj == null || (obj instanceof Bundle)) {
                        return connection.onDescriptorChanged((Bundle) obj);
                    }
                    return false;
                case 6:
                    if (obj instanceof Bundle) {
                        connection.onDynamicGroupRouteControllerCreated(requestId, (Bundle) obj);
                        return false;
                    }
                    Log.w(RegisteredMediaRouteProvider.TAG, "No further information on the dynamic group controller");
                    return false;
                case 7:
                    if (obj == null || (obj instanceof Bundle)) {
                        return connection.onDynamicRouteDescriptorsChanged(arg, (Bundle) obj);
                    }
                    return false;
                default:
                    return false;
            }
        }
    }
}
