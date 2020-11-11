package androidx.navigation.dynamicfeatures;

import androidx.lifecycle.MutableLiveData;
import androidx.navigation.dynamicfeatures.DynamicInstallManager;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.tasks.OnSuccessListener;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0003\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0004\b\u0005\u0010\u0006"}, mo33671d2 = {"<anonymous>", "", "sessionId", "", "kotlin.jvm.PlatformType", "onSuccess", "(Ljava/lang/Integer;)V"}, mo33672k = 3, mo33673mv = {1, 1, 16})
/* compiled from: DynamicInstallManager.kt */
final class DynamicInstallManager$requestInstall$2<ResultT> implements OnSuccessListener<Integer> {
    final /* synthetic */ DynamicInstallMonitor $installMonitor;
    final /* synthetic */ String $module;
    final /* synthetic */ MutableLiveData $status;
    final /* synthetic */ DynamicInstallManager this$0;

    DynamicInstallManager$requestInstall$2(DynamicInstallManager dynamicInstallManager, DynamicInstallMonitor dynamicInstallMonitor, MutableLiveData mutableLiveData, String str) {
        this.this$0 = dynamicInstallManager;
        this.$installMonitor = dynamicInstallMonitor;
        this.$status = mutableLiveData;
        this.$module = str;
    }

    public final void onSuccess(Integer sessionId) {
        DynamicInstallMonitor dynamicInstallMonitor = this.$installMonitor;
        Intrinsics.checkExpressionValueIsNotNull(sessionId, "sessionId");
        dynamicInstallMonitor.setSessionId$navigation_dynamic_features_runtime_release(sessionId.intValue());
        this.$installMonitor.mo8175x5dd0a14f(this.this$0.splitInstallManager);
        if (sessionId.intValue() == 0) {
            this.$status.setValue(SplitInstallSessionState.create(sessionId.intValue(), 5, 0, 0, 0, CollectionsKt.listOf(this.$module), CollectionsKt.emptyList()));
            DynamicInstallManager.Companion.terminateLiveData$navigation_dynamic_features_runtime_release(this.$status);
            return;
        }
        this.this$0.splitInstallManager.registerListener(new DynamicInstallManager.SplitInstallListenerWrapper(this.this$0.context, this.$status, this.$installMonitor));
    }
}
