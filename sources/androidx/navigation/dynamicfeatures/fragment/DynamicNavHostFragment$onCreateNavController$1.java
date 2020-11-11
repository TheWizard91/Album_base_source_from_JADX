package androidx.navigation.dynamicfeatures.fragment;

import androidx.navigation.dynamicfeatures.fragment.DynamicFragmentNavigator;
import androidx.navigation.dynamicfeatures.fragment.p004ui.DefaultProgressFragment;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo33671d2 = {"<anonymous>", "Landroidx/navigation/dynamicfeatures/fragment/DynamicFragmentNavigator$Destination;", "invoke"}, mo33672k = 3, mo33673mv = {1, 1, 16})
/* compiled from: DynamicNavHostFragment.kt */
final class DynamicNavHostFragment$onCreateNavController$1 extends Lambda implements Function0<DynamicFragmentNavigator.Destination> {
    final /* synthetic */ DynamicFragmentNavigator $fragmentNavigator;

    /* JADX INFO: super call moved to the top of the method (can break code semantics) */
    DynamicNavHostFragment$onCreateNavController$1(DynamicFragmentNavigator dynamicFragmentNavigator) {
        super(0);
        this.$fragmentNavigator = dynamicFragmentNavigator;
    }

    public final DynamicFragmentNavigator.Destination invoke() {
        DynamicFragmentNavigator.Destination createDestination = this.$fragmentNavigator.createDestination();
        DynamicFragmentNavigator.Destination $this$apply = createDestination;
        $this$apply.setClassName(DefaultProgressFragment.class.getName());
        $this$apply.setId(C2174R.C2177id.dfn_progress_fragment);
        return createDestination;
    }
}
