package androidx.navigation.dynamicfeatures.fragment.p004ui;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u001b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\u0000\b\n\u0018\u00002\u00020\u0001J%\u0010\u0002\u001a\u0002H\u0003\"\b\b\u0000\u0010\u0003*\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00030\u0006H\u0016¢\u0006\u0002\u0010\u0007¨\u0006\b"}, mo33671d2 = {"androidx/navigation/dynamicfeatures/fragment/ui/InstallViewModel$Companion$FACTORY$1", "Landroidx/lifecycle/ViewModelProvider$Factory;", "create", "T", "Landroidx/lifecycle/ViewModel;", "modelClass", "Ljava/lang/Class;", "(Ljava/lang/Class;)Landroidx/lifecycle/ViewModel;", "navigation-dynamic-features-fragment_release"}, mo33672k = 1, mo33673mv = {1, 1, 16})
/* renamed from: androidx.navigation.dynamicfeatures.fragment.ui.InstallViewModel$Companion$FACTORY$1 */
/* compiled from: InstallViewModel.kt */
public final class InstallViewModel$Companion$FACTORY$1 implements ViewModelProvider.Factory {
    InstallViewModel$Companion$FACTORY$1() {
    }

    public <T extends ViewModel> T create(Class<T> modelClass) {
        Intrinsics.checkParameterIsNotNull(modelClass, "modelClass");
        return (ViewModel) new InstallViewModel();
    }
}
