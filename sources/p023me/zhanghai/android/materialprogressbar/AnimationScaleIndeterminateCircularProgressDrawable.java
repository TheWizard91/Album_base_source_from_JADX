package p023me.zhanghai.android.materialprogressbar;

import android.content.Context;
import android.graphics.drawable.Drawable;
import p023me.zhanghai.android.materialprogressbar.internal.AnimationScaleListDrawableCompat;

/* renamed from: me.zhanghai.android.materialprogressbar.AnimationScaleIndeterminateCircularProgressDrawable */
public class AnimationScaleIndeterminateCircularProgressDrawable extends AnimationScaleListDrawableCompat implements MaterialProgressDrawable, IntrinsicPaddingDrawable, TintableDrawable {
    public AnimationScaleIndeterminateCircularProgressDrawable(Context context) {
        super(new Drawable[]{new StaticIndeterminateCircularProgressDrawable(context), new IndeterminateCircularProgressDrawable(context)});
    }

    public boolean getUseIntrinsicPadding() {
        return getIntrinsicPaddingDrawable().getUseIntrinsicPadding();
    }

    public void setUseIntrinsicPadding(boolean useIntrinsicPadding) {
        getIntrinsicPaddingDrawable().setUseIntrinsicPadding(useIntrinsicPadding);
    }

    private IntrinsicPaddingDrawable getIntrinsicPaddingDrawable() {
        return (IntrinsicPaddingDrawable) getCurrent();
    }
}
