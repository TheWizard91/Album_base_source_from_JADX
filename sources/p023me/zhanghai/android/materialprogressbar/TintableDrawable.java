package p023me.zhanghai.android.materialprogressbar;

import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import androidx.core.graphics.drawable.TintAwareDrawable;

/* renamed from: me.zhanghai.android.materialprogressbar.TintableDrawable */
public interface TintableDrawable extends TintAwareDrawable {
    void setTint(int i);

    void setTintList(ColorStateList colorStateList);

    void setTintMode(PorterDuff.Mode mode);
}
