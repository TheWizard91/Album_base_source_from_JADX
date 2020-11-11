package androidx.navigation.dynamicfeatures.fragment.p004ui;

import androidx.lifecycle.ViewModelProvider;
import kotlin.Metadata;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Lambda;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\b\n\u0000\n\u0002\u0018\u0002\n\u0000\u0010\u0000\u001a\u00020\u0001H\n¢\u0006\u0002\b\u0002"}, mo33671d2 = {"<anonymous>", "Landroidx/lifecycle/ViewModelProvider$Factory;", "invoke"}, mo33672k = 3, mo33673mv = {1, 1, 16})
/* renamed from: androidx.navigation.dynamicfeatures.fragment.ui.AbstractProgressFragment$installViewModel$2 */
/* compiled from: AbstractProgressFragment.kt */
final class AbstractProgressFragment$installViewModel$2 extends Lambda implements Function0<ViewModelProvider.Factory> {
    public static final AbstractProgressFragment$installViewModel$2 INSTANCE = new AbstractProgressFragment$installViewModel$2();

    AbstractProgressFragment$installViewModel$2() {
        super(0);
    }

    public final ViewModelProvider.Factory invoke() {
        return InstallViewModel.Companion.getFACTORY();
    }
}
