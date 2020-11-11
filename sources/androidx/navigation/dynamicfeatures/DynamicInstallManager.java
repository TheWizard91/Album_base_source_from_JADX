package androidx.navigation.dynamicfeatures;

import android.content.Context;
import android.os.Bundle;
import androidx.core.app.NotificationCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigator;
import androidx.navigation.NavigatorProvider;
import androidx.navigation.dynamicfeatures.DynamicGraphNavigator;
import com.google.android.play.core.splitcompat.SplitCompat;
import com.google.android.play.core.splitinstall.SplitInstallHelper;
import com.google.android.play.core.splitinstall.SplitInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallRequest;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.firebase.analytics.FirebaseAnalytics;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000H\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\b\u0016\u0018\u0000 \u00172\u00020\u0001:\u0002\u0017\u0018B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005¢\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0007J.\u0010\u000b\u001a\u0004\u0018\u00010\f2\u0006\u0010\r\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u00112\u0006\u0010\u0012\u001a\u00020\nH\u0007J\u0018\u0010\u0013\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\u0016H\u0002R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0019"}, mo33671d2 = {"Landroidx/navigation/dynamicfeatures/DynamicInstallManager;", "", "context", "Landroid/content/Context;", "splitInstallManager", "Lcom/google/android/play/core/splitinstall/SplitInstallManager;", "(Landroid/content/Context;Lcom/google/android/play/core/splitinstall/SplitInstallManager;)V", "needsInstall", "", "module", "", "performInstall", "Landroidx/navigation/NavDestination;", "destination", "args", "Landroid/os/Bundle;", "extras", "Landroidx/navigation/dynamicfeatures/DynamicExtras;", "moduleName", "requestInstall", "", "installMonitor", "Landroidx/navigation/dynamicfeatures/DynamicInstallMonitor;", "Companion", "SplitInstallListenerWrapper", "navigation-dynamic-features-runtime_release"}, mo33672k = 1, mo33673mv = {1, 1, 16})
/* compiled from: DynamicInstallManager.kt */
public class DynamicInstallManager {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    /* access modifiers changed from: private */
    public final Context context;
    /* access modifiers changed from: private */
    public final SplitInstallManager splitInstallManager;

    public DynamicInstallManager(Context context2, SplitInstallManager splitInstallManager2) {
        Intrinsics.checkParameterIsNotNull(context2, "context");
        Intrinsics.checkParameterIsNotNull(splitInstallManager2, "splitInstallManager");
        this.context = context2;
        this.splitInstallManager = splitInstallManager2;
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J\u001b\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006H\u0000¢\u0006\u0002\b\b¨\u0006\t"}, mo33671d2 = {"Landroidx/navigation/dynamicfeatures/DynamicInstallManager$Companion;", "", "()V", "terminateLiveData", "", "status", "Landroidx/lifecycle/MutableLiveData;", "Lcom/google/android/play/core/splitinstall/SplitInstallSessionState;", "terminateLiveData$navigation_dynamic_features_runtime_release", "navigation-dynamic-features-runtime_release"}, mo33672k = 1, mo33673mv = {1, 1, 16})
    /* compiled from: DynamicInstallManager.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }

        public final void terminateLiveData$navigation_dynamic_features_runtime_release(MutableLiveData<SplitInstallSessionState> status) {
            Intrinsics.checkParameterIsNotNull(status, NotificationCompat.CATEGORY_STATUS);
            if (!(!status.hasActiveObservers())) {
                throw new IllegalStateException("This DynamicInstallMonitor will not emit any more status updates. You should remove all Observers after null has been emitted.".toString());
            }
        }
    }

    public final NavDestination performInstall(NavDestination destination, Bundle args, DynamicExtras extras, String moduleName) {
        Intrinsics.checkParameterIsNotNull(destination, FirebaseAnalytics.Param.DESTINATION);
        Intrinsics.checkParameterIsNotNull(moduleName, "moduleName");
        if ((extras != null ? extras.getInstallMonitor() : null) != null) {
            requestInstall(moduleName, extras.getInstallMonitor());
            return null;
        }
        Bundle progressArgs = new Bundle();
        Bundle $this$apply = progressArgs;
        $this$apply.putInt(Constants.DESTINATION_ID, destination.getId());
        $this$apply.putBundle(Constants.DESTINATION_ARGS, args);
        DynamicGraphNavigator.DynamicNavGraph dynamicNavGraph = DynamicGraphNavigator.DynamicNavGraph.Companion.getOrThrow$navigation_dynamic_features_runtime_release(destination);
        NavigatorProvider $this$get$iv = dynamicNavGraph.getNavigatorProvider$navigation_dynamic_features_runtime_release();
        String name$iv = dynamicNavGraph.getNavigatorName();
        Intrinsics.checkExpressionValueIsNotNull(name$iv, "dynamicNavGraph.navigatorName");
        Navigator navigator = $this$get$iv.getNavigator(name$iv);
        Intrinsics.checkExpressionValueIsNotNull(navigator, "getNavigator(name)");
        Navigator navigator2 = navigator;
        if (navigator2 instanceof DynamicGraphNavigator) {
            return ((DynamicGraphNavigator) navigator2).mo8138xefe7b17a(dynamicNavGraph, progressArgs);
        }
        throw new IllegalStateException("You must use a DynamicNavGraph to perform a module installation.");
    }

    public final boolean needsInstall(String module) {
        Intrinsics.checkParameterIsNotNull(module, "module");
        return !this.splitInstallManager.getInstalledModules().contains(module);
    }

    private final void requestInstall(String module, DynamicInstallMonitor installMonitor) {
        if (!installMonitor.isUsed$navigation_dynamic_features_runtime_release()) {
            LiveData<SplitInstallSessionState> status = installMonitor.getStatus();
            if (status != null) {
                MutableLiveData status2 = (MutableLiveData) status;
                installMonitor.setInstallRequired$navigation_dynamic_features_runtime_release(true);
                this.splitInstallManager.startInstall(SplitInstallRequest.newBuilder().addModule(module).build()).addOnSuccessListener(new DynamicInstallManager$requestInstall$2(this, installMonitor, status2, module)).addOnFailureListener(new DynamicInstallManager$requestInstall$3(module, installMonitor, status2));
                return;
            }
            throw new TypeCastException("null cannot be cast to non-null type androidx.lifecycle.MutableLiveData<com.google.android.play.core.splitinstall.SplitInstallSessionState>");
        }
        throw new IllegalStateException("You must pass in a fresh DynamicInstallMonitor in DynamicExtras every time you call navigate().".toString());
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\b\u0002\u0018\u00002\u00020\u0001B%\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u0006\u0010\u0007\u001a\u00020\b¢\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\r"}, mo33671d2 = {"Landroidx/navigation/dynamicfeatures/DynamicInstallManager$SplitInstallListenerWrapper;", "Lcom/google/android/play/core/splitinstall/SplitInstallStateUpdatedListener;", "context", "Landroid/content/Context;", "status", "Landroidx/lifecycle/MutableLiveData;", "Lcom/google/android/play/core/splitinstall/SplitInstallSessionState;", "installMonitor", "Landroidx/navigation/dynamicfeatures/DynamicInstallMonitor;", "(Landroid/content/Context;Landroidx/lifecycle/MutableLiveData;Landroidx/navigation/dynamicfeatures/DynamicInstallMonitor;)V", "onStateUpdate", "", "splitInstallSessionState", "navigation-dynamic-features-runtime_release"}, mo33672k = 1, mo33673mv = {1, 1, 16})
    /* compiled from: DynamicInstallManager.kt */
    private static final class SplitInstallListenerWrapper implements SplitInstallStateUpdatedListener {
        private final Context context;
        private final DynamicInstallMonitor installMonitor;
        private final MutableLiveData<SplitInstallSessionState> status;

        public SplitInstallListenerWrapper(Context context2, MutableLiveData<SplitInstallSessionState> status2, DynamicInstallMonitor installMonitor2) {
            Intrinsics.checkParameterIsNotNull(context2, "context");
            Intrinsics.checkParameterIsNotNull(status2, NotificationCompat.CATEGORY_STATUS);
            Intrinsics.checkParameterIsNotNull(installMonitor2, "installMonitor");
            this.context = context2;
            this.status = status2;
            this.installMonitor = installMonitor2;
        }

        public void onStateUpdate(SplitInstallSessionState splitInstallSessionState) {
            Intrinsics.checkParameterIsNotNull(splitInstallSessionState, "splitInstallSessionState");
            if (splitInstallSessionState.sessionId() == this.installMonitor.getSessionId()) {
                if (splitInstallSessionState.status() == 5) {
                    SplitCompat.install(this.context);
                    SplitInstallHelper.updateAppInfo(this.context);
                }
                this.status.setValue(splitInstallSessionState);
                if (splitInstallSessionState.hasTerminalStatus()) {
                    SplitInstallManager splitInstallManager$navigation_dynamic_features_runtime_release = this.installMonitor.mo8168x63963fdb();
                    if (splitInstallManager$navigation_dynamic_features_runtime_release == null) {
                        Intrinsics.throwNpe();
                    }
                    splitInstallManager$navigation_dynamic_features_runtime_release.unregisterListener(this);
                    DynamicInstallManager.Companion.terminateLiveData$navigation_dynamic_features_runtime_release(this.status);
                }
            }
        }
    }
}
