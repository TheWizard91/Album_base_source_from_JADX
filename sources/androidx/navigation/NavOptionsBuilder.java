package androidx.navigation;

import androidx.navigation.NavOptions;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000D\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\n\u0002\u0010\b\n\u0002\b\u0006\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u001f\u0010\u0013\u001a\u00020\u00142\u0017\u0010\u0015\u001a\u0013\u0012\u0004\u0012\u00020\u0017\u0012\u0004\u0012\u00020\u00140\u0016¢\u0006\u0002\b\u0018J\r\u0010\u0019\u001a\u00020\u001aH\u0000¢\u0006\u0002\b\u001bJ)\u0010\u000e\u001a\u00020\u00142\b\b\u0001\u0010\u001c\u001a\u00020\r2\u0017\u0010\u001d\u001a\u0013\u0012\u0004\u0012\u00020\u001e\u0012\u0004\u0012\u00020\u00140\u0016¢\u0006\u0002\b\u0018R\u000e\u0010\u0003\u001a\u00020\u0004X\u0004¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u000e¢\u0006\u0002\n\u0000R\u001a\u0010\u0007\u001a\u00020\u0006X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\b\u0010\t\"\u0004\b\n\u0010\u000bR&\u0010\u000e\u001a\u00020\r2\u0006\u0010\f\u001a\u00020\r8\u0006@FX\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u000f\u0010\u0010\"\u0004\b\u0011\u0010\u0012¨\u0006\u001f"}, mo33671d2 = {"Landroidx/navigation/NavOptionsBuilder;", "", "()V", "builder", "Landroidx/navigation/NavOptions$Builder;", "inclusive", "", "launchSingleTop", "getLaunchSingleTop", "()Z", "setLaunchSingleTop", "(Z)V", "value", "", "popUpTo", "getPopUpTo", "()I", "setPopUpTo", "(I)V", "anim", "", "animBuilder", "Lkotlin/Function1;", "Landroidx/navigation/AnimBuilder;", "Lkotlin/ExtensionFunctionType;", "build", "Landroidx/navigation/NavOptions;", "build$navigation_common_ktx_release", "id", "popUpToBuilder", "Landroidx/navigation/PopUpToBuilder;", "navigation-common-ktx_release"}, mo33672k = 1, mo33673mv = {1, 1, 16})
@NavOptionsDsl
/* compiled from: NavOptionsBuilder.kt */
public final class NavOptionsBuilder {
    private final NavOptions.Builder builder = new NavOptions.Builder();
    private boolean inclusive;
    private boolean launchSingleTop;
    private int popUpTo = -1;

    public final boolean getLaunchSingleTop() {
        return this.launchSingleTop;
    }

    public final void setLaunchSingleTop(boolean z) {
        this.launchSingleTop = z;
    }

    public final int getPopUpTo() {
        return this.popUpTo;
    }

    public final void setPopUpTo(int value) {
        this.popUpTo = value;
        this.inclusive = false;
    }

    public final void popUpTo(int id, Function1<? super PopUpToBuilder, Unit> popUpToBuilder) {
        Intrinsics.checkParameterIsNotNull(popUpToBuilder, "popUpToBuilder");
        setPopUpTo(id);
        PopUpToBuilder popUpToBuilder2 = new PopUpToBuilder();
        popUpToBuilder.invoke(popUpToBuilder2);
        this.inclusive = popUpToBuilder2.getInclusive();
    }

    public final void anim(Function1<? super AnimBuilder, Unit> animBuilder) {
        Intrinsics.checkParameterIsNotNull(animBuilder, "animBuilder");
        AnimBuilder $this$run = new AnimBuilder();
        animBuilder.invoke($this$run);
        this.builder.setEnterAnim($this$run.getEnter()).setExitAnim($this$run.getExit()).setPopEnterAnim($this$run.getPopEnter()).setPopExitAnim($this$run.getPopExit());
    }

    public final NavOptions build$navigation_common_ktx_release() {
        NavOptions.Builder builder2 = this.builder;
        NavOptions.Builder $this$apply = builder2;
        $this$apply.setLaunchSingleTop(this.launchSingleTop);
        $this$apply.setPopUpTo(this.popUpTo, this.inclusive);
        NavOptions build = builder2.build();
        Intrinsics.checkExpressionValueIsNotNull(build, "builder.apply {\n        … inclusive)\n    }.build()");
        return build;
    }
}