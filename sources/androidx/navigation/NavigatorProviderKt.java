package androidx.navigation;

import com.google.android.gms.measurement.api.AppMeasurementSdk;
import kotlin.Metadata;
import kotlin.jvm.JvmClassMappingKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.reflect.KClass;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000*\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0004\u001a,\u0010\u0000\u001a\u0002H\u0001\"\u0010\b\u0000\u0010\u0001*\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\n¢\u0006\u0002\u0010\u0007\u001a2\u0010\u0000\u001a\u0002H\u0001\"\u0010\b\u0000\u0010\u0001*\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002*\u00020\u00042\f\u0010\b\u001a\b\u0012\u0004\u0012\u0002H\u00010\tH\n¢\u0006\u0002\u0010\n\u001a\u001d\u0010\u000b\u001a\u00020\f*\u00020\u00042\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002H\n\u001a7\u0010\u000e\u001a\u0014\u0012\u000e\b\u0001\u0012\n \u000f*\u0004\u0018\u00010\u00030\u0003\u0018\u00010\u0002*\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u000e\u0010\r\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00030\u0002H\n¨\u0006\u0010"}, mo33671d2 = {"get", "T", "Landroidx/navigation/Navigator;", "Landroidx/navigation/NavDestination;", "Landroidx/navigation/NavigatorProvider;", "name", "", "(Landroidx/navigation/NavigatorProvider;Ljava/lang/String;)Landroidx/navigation/Navigator;", "clazz", "Lkotlin/reflect/KClass;", "(Landroidx/navigation/NavigatorProvider;Lkotlin/reflect/KClass;)Landroidx/navigation/Navigator;", "plusAssign", "", "navigator", "set", "kotlin.jvm.PlatformType", "navigation-common-ktx_release"}, mo33672k = 2, mo33673mv = {1, 1, 16})
/* compiled from: NavigatorProvider.kt */
public final class NavigatorProviderKt {
    public static final <T extends Navigator<? extends NavDestination>> T get(NavigatorProvider $this$get, String name) {
        Intrinsics.checkParameterIsNotNull($this$get, "$this$get");
        Intrinsics.checkParameterIsNotNull(name, AppMeasurementSdk.ConditionalUserProperty.NAME);
        T navigator = $this$get.getNavigator(name);
        Intrinsics.checkExpressionValueIsNotNull(navigator, "getNavigator(name)");
        return navigator;
    }

    public static final <T extends Navigator<? extends NavDestination>> T get(NavigatorProvider $this$get, KClass<T> clazz) {
        Intrinsics.checkParameterIsNotNull($this$get, "$this$get");
        Intrinsics.checkParameterIsNotNull(clazz, "clazz");
        T navigator = $this$get.getNavigator(JvmClassMappingKt.getJavaClass(clazz));
        Intrinsics.checkExpressionValueIsNotNull(navigator, "getNavigator(clazz.java)");
        return navigator;
    }

    public static final Navigator<? extends NavDestination> set(NavigatorProvider $this$set, String name, Navigator<? extends NavDestination> navigator) {
        Intrinsics.checkParameterIsNotNull($this$set, "$this$set");
        Intrinsics.checkParameterIsNotNull(name, AppMeasurementSdk.ConditionalUserProperty.NAME);
        Intrinsics.checkParameterIsNotNull(navigator, "navigator");
        return $this$set.addNavigator(name, navigator);
    }

    public static final void plusAssign(NavigatorProvider $this$plusAssign, Navigator<? extends NavDestination> navigator) {
        Intrinsics.checkParameterIsNotNull($this$plusAssign, "$this$plusAssign");
        Intrinsics.checkParameterIsNotNull(navigator, "navigator");
        $this$plusAssign.addNavigator(navigator);
    }
}
