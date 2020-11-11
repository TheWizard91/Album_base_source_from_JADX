package androidx.navigation.dynamicfeatures;

import androidx.navigation.Navigator;
import kotlin.Metadata;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u00002\u00020\u0001B\u001f\b\u0007\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\u0002\u0010\u0005R\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0001¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\t¨\u0006\n"}, mo33671d2 = {"Landroidx/navigation/dynamicfeatures/DynamicExtras;", "Landroidx/navigation/Navigator$Extras;", "installMonitor", "Landroidx/navigation/dynamicfeatures/DynamicInstallMonitor;", "destinationExtras", "(Landroidx/navigation/dynamicfeatures/DynamicInstallMonitor;Landroidx/navigation/Navigator$Extras;)V", "getDestinationExtras", "()Landroidx/navigation/Navigator$Extras;", "getInstallMonitor", "()Landroidx/navigation/dynamicfeatures/DynamicInstallMonitor;", "navigation-dynamic-features-runtime_release"}, mo33672k = 1, mo33673mv = {1, 1, 16})
/* compiled from: DynamicExtras.kt */
public final class DynamicExtras implements Navigator.Extras {
    private final Navigator.Extras destinationExtras;
    private final DynamicInstallMonitor installMonitor;

    public DynamicExtras() {
        this((DynamicInstallMonitor) null, (Navigator.Extras) null, 3, (DefaultConstructorMarker) null);
    }

    public DynamicExtras(DynamicInstallMonitor dynamicInstallMonitor) {
        this(dynamicInstallMonitor, (Navigator.Extras) null, 2, (DefaultConstructorMarker) null);
    }

    public DynamicExtras(DynamicInstallMonitor installMonitor2, Navigator.Extras destinationExtras2) {
        this.installMonitor = installMonitor2;
        this.destinationExtras = destinationExtras2;
    }

    /* JADX INFO: this call moved to the top of the method (can break code semantics) */
    public /* synthetic */ DynamicExtras(DynamicInstallMonitor dynamicInstallMonitor, Navigator.Extras extras, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : dynamicInstallMonitor, (i & 2) != 0 ? null : extras);
    }

    public final DynamicInstallMonitor getInstallMonitor() {
        return this.installMonitor;
    }

    public final Navigator.Extras getDestinationExtras() {
        return this.destinationExtras;
    }
}
