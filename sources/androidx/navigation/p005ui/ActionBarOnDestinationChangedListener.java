package androidx.navigation.p005ui;

import android.graphics.drawable.Drawable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

/* renamed from: androidx.navigation.ui.ActionBarOnDestinationChangedListener */
class ActionBarOnDestinationChangedListener extends AbstractAppBarOnDestinationChangedListener {
    private final AppCompatActivity mActivity;

    ActionBarOnDestinationChangedListener(AppCompatActivity activity, AppBarConfiguration configuration) {
        super(activity.getDrawerToggleDelegate().getActionBarThemedContext(), configuration);
        this.mActivity = activity;
    }

    /* access modifiers changed from: protected */
    public void setTitle(CharSequence title) {
        this.mActivity.getSupportActionBar().setTitle(title);
    }

    /* access modifiers changed from: protected */
    public void setNavigationIcon(Drawable icon, int contentDescription) {
        ActionBar actionBar = this.mActivity.getSupportActionBar();
        if (icon == null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            return;
        }
        actionBar.setDisplayHomeAsUpEnabled(true);
        this.mActivity.getDrawerToggleDelegate().setActionBarUpIndicator(icon, contentDescription);
    }
}
