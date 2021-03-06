package androidx.navigation.dynamicfeatures;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.google.android.play.core.splitinstall.SplitInstallException;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.tasks.OnFailureListener;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, mo33671d2 = {"<anonymous>", "", "exception", "Ljava/lang/Exception;", "kotlin.jvm.PlatformType", "onFailure"}, mo33672k = 3, mo33673mv = {1, 1, 16})
/* compiled from: DynamicInstallManager.kt */
final class DynamicInstallManager$requestInstall$3 implements OnFailureListener {
    final /* synthetic */ DynamicInstallMonitor $installMonitor;
    final /* synthetic */ String $module;
    final /* synthetic */ MutableLiveData $status;

    DynamicInstallManager$requestInstall$3(String str, DynamicInstallMonitor dynamicInstallMonitor, MutableLiveData mutableLiveData) {
        this.$module = str;
        this.$installMonitor = dynamicInstallMonitor;
        this.$status = mutableLiveData;
    }

    public final void onFailure(Exception exception) {
        int i;
        Log.i("DynamicInstallManager", "Error requesting install of " + this.$module + ": " + exception.getMessage());
        this.$installMonitor.setException$navigation_dynamic_features_runtime_release(exception);
        MutableLiveData mutableLiveData = this.$status;
        if (exception instanceof SplitInstallException) {
            i = ((SplitInstallException) exception).getErrorCode();
        } else {
            i = -100;
        }
        mutableLiveData.setValue(SplitInstallSessionState.create(0, 6, i, 0, 0, CollectionsKt.listOf(this.$module), CollectionsKt.emptyList()));
        DynamicInstallManager.Companion.terminateLiveData$navigation_dynamic_features_runtime_release(this.$status);
    }
}
