package androidx.navigation.dynamicfeatures.fragment.p004ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.navigation.dynamicfeatures.fragment.C2174R;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000P\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 !2\u00020\u0001:\u0001!B\u0005¢\u0006\u0002\u0010\u0002J \u0010\t\u001a\u00020\n2\b\b\u0001\u0010\u000b\u001a\u00020\f2\f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\n0\u000eH\u0002J\u0012\u0010\u000f\u001a\u00020\n2\b\b\u0001\u0010\u000b\u001a\u00020\fH\u0002J\b\u0010\u0010\u001a\u00020\nH\u0014J\b\u0010\u0011\u001a\u00020\nH\u0016J\u0012\u0010\u0012\u001a\u00020\n2\b\b\u0001\u0010\u0013\u001a\u00020\fH\u0014J \u0010\u0014\u001a\u00020\n2\u0006\u0010\u0015\u001a\u00020\f2\u0006\u0010\u0016\u001a\u00020\u00172\u0006\u0010\u0018\u001a\u00020\u0017H\u0014J\u001a\u0010\u0019\u001a\u00020\n2\u0006\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dH\u0016J\u0010\u0010\u001e\u001a\u00020\n2\u0006\u0010\u001f\u001a\u00020 H\u0002R\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u000e¢\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u000e¢\u0006\u0002\n\u0000¨\u0006\""}, mo33671d2 = {"Landroidx/navigation/dynamicfeatures/fragment/ui/DefaultProgressFragment;", "Landroidx/navigation/dynamicfeatures/fragment/ui/AbstractProgressFragment;", "()V", "action", "Landroid/widget/Button;", "progressBar", "Landroid/widget/ProgressBar;", "title", "Landroid/widget/TextView;", "displayAction", "", "text", "", "onClick", "Lkotlin/Function0;", "displayErrorState", "onCancelled", "onDestroyView", "onFailed", "errorCode", "onProgress", "status", "bytesDownloaded", "", "bytesTotal", "onViewCreated", "view", "Landroid/view/View;", "savedInstanceState", "Landroid/os/Bundle;", "setActivityIcon", "activityIcon", "Landroid/widget/ImageView;", "Companion", "navigation-dynamic-features-fragment_release"}, mo33672k = 1, mo33673mv = {1, 1, 16})
/* renamed from: androidx.navigation.dynamicfeatures.fragment.ui.DefaultProgressFragment */
/* compiled from: DefaultProgressFragment.kt */
public final class DefaultProgressFragment extends AbstractProgressFragment {
    public static final Companion Companion = new Companion((DefaultConstructorMarker) null);
    private static final int PROGRESS_MAX = 100;
    private static final String TAG = "DefaultProgressFragment";
    private Button action;
    private ProgressBar progressBar;
    private TextView title;

    public DefaultProgressFragment() {
        super(C2174R.C2178layout.dynamic_feature_install_fragment);
    }

    @Metadata(mo33669bv = {1, 0, 3}, mo33670d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004XT¢\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006XT¢\u0006\u0002\n\u0000¨\u0006\u0007"}, mo33671d2 = {"Landroidx/navigation/dynamicfeatures/fragment/ui/DefaultProgressFragment$Companion;", "", "()V", "PROGRESS_MAX", "", "TAG", "", "navigation-dynamic-features-fragment_release"}, mo33672k = 1, mo33673mv = {1, 1, 16})
    /* renamed from: androidx.navigation.dynamicfeatures.fragment.ui.DefaultProgressFragment$Companion */
    /* compiled from: DefaultProgressFragment.kt */
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker $constructor_marker) {
            this();
        }
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {
        Intrinsics.checkParameterIsNotNull(view, "view");
        View $this$with = view;
        this.title = (TextView) $this$with.findViewById(C2174R.C2177id.progress_title);
        this.progressBar = (ProgressBar) $this$with.findViewById(C2174R.C2177id.installation_progress);
        View findViewById = $this$with.findViewById(C2174R.C2177id.progress_icon);
        Intrinsics.checkExpressionValueIsNotNull(findViewById, "findViewById(R.id.progress_icon)");
        setActivityIcon((ImageView) findViewById);
        this.action = (Button) $this$with.findViewById(C2174R.C2177id.progress_action);
    }

    public void onDestroyView() {
        super.onDestroyView();
        this.title = null;
        this.progressBar = null;
        this.action = null;
    }

    private final void setActivityIcon(ImageView activityIcon) {
        Drawable icon;
        Context requireContext = requireContext();
        Intrinsics.checkExpressionValueIsNotNull(requireContext, "requireContext()");
        PackageManager $this$with = requireContext.getPackageManager();
        try {
            icon = $this$with.getActivityIcon(new ComponentName(requireContext(), requireActivity().getClass()));
        } catch (PackageManager.NameNotFoundException e) {
            icon = $this$with.getDefaultActivityIcon();
        }
        activityIcon.setImageDrawable(icon);
    }

    /* access modifiers changed from: protected */
    public void onProgress(int status, long bytesDownloaded, long bytesTotal) {
        ProgressBar $this$run = this.progressBar;
        if ($this$run != null) {
            $this$run.setVisibility(0);
            if (bytesTotal == 0) {
                $this$run.setIndeterminate(true);
                return;
            }
            $this$run.setProgress((int) ((((long) 100) * bytesDownloaded) / bytesTotal));
            $this$run.setIndeterminate(false);
        }
    }

    /* access modifiers changed from: protected */
    public void onCancelled() {
        displayErrorState(C2174R.string.installation_cancelled);
        displayAction(C2174R.string.retry, new DefaultProgressFragment$onCancelled$1(this));
    }

    /* access modifiers changed from: protected */
    public void onFailed(int errorCode) {
        Log.w(TAG, "Installation failed with error " + errorCode);
        displayErrorState(C2174R.string.installation_failed);
        displayAction(C2174R.string.f726ok, new DefaultProgressFragment$onFailed$1(this));
    }

    private final void displayErrorState(int text) {
        TextView textView = this.title;
        if (textView != null) {
            textView.setText(text);
        }
        ProgressBar progressBar2 = this.progressBar;
        if (progressBar2 != null) {
            progressBar2.setVisibility(4);
        }
    }

    private final void displayAction(int text, Function0<Unit> onClick) {
        Button $this$run = this.action;
        if ($this$run != null) {
            $this$run.setText(text);
            $this$run.setOnClickListener(new DefaultProgressFragment$displayAction$$inlined$run$lambda$1(text, onClick));
            $this$run.setVisibility(0);
        }
    }
}
