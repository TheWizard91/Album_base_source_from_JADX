package com.google.firebase.inappmessaging.display.internal.injection.modules;

import android.app.Application;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import com.google.firebase.inappmessaging.display.internal.injection.keys.LayoutConfigKey;
import com.google.firebase.inappmessaging.model.MessageType;
import dagger.Module;
import dagger.Provides;
import dagger.multibindings.IntoMap;
import dagger.multibindings.StringKey;

@Module
public class InflaterConfigModule {
    public static int DISABLED_BG_FLAG = 327938;
    public static int DISMISSIBLE_DIALOG_FLAG = 327970;
    private int ENABLED_BG_FLAG = 65824;

    /* renamed from: com.google.firebase.inappmessaging.display.internal.injection.modules.InflaterConfigModule$1 */
    static /* synthetic */ class C39671 {
        static final /* synthetic */ int[] $SwitchMap$com$google$firebase$inappmessaging$model$MessageType;

        static {
            int[] iArr = new int[MessageType.values().length];
            $SwitchMap$com$google$firebase$inappmessaging$model$MessageType = iArr;
            try {
                iArr[MessageType.MODAL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$google$firebase$inappmessaging$model$MessageType[MessageType.CARD.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$google$firebase$inappmessaging$model$MessageType[MessageType.IMAGE_ONLY.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$google$firebase$inappmessaging$model$MessageType[MessageType.BANNER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public static String configFor(MessageType type, int orientation) {
        if (orientation == 1) {
            int i = C39671.$SwitchMap$com$google$firebase$inappmessaging$model$MessageType[type.ordinal()];
            if (i == 1) {
                return LayoutConfigKey.MODAL_PORTRAIT;
            }
            if (i == 2) {
                return LayoutConfigKey.CARD_PORTRAIT;
            }
            if (i == 3) {
                return LayoutConfigKey.IMAGE_ONLY_PORTRAIT;
            }
            if (i != 4) {
                return null;
            }
            return LayoutConfigKey.BANNER_PORTRAIT;
        }
        int i2 = C39671.$SwitchMap$com$google$firebase$inappmessaging$model$MessageType[type.ordinal()];
        if (i2 == 1) {
            return LayoutConfigKey.MODAL_LANDSCAPE;
        }
        if (i2 == 2) {
            return LayoutConfigKey.CARD_LANDSCAPE;
        }
        if (i2 == 3) {
            return LayoutConfigKey.IMAGE_ONLY_LANDSCAPE;
        }
        if (i2 != 4) {
            return null;
        }
        return LayoutConfigKey.BANNER_LANDSCAPE;
    }

    /* access modifiers changed from: package-private */
    @Provides
    public DisplayMetrics providesDisplayMetrics(Application application) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((WindowManager) application.getSystemService("window")).getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    @IntoMap
    @StringKey("IMAGE_ONLY_PORTRAIT")
    @Provides
    public InAppMessageLayoutConfig providesPortraitImageLayoutConfig(DisplayMetrics displayMetrics) {
        InAppMessageLayoutConfig.Builder maxDialogWidthPx = InAppMessageLayoutConfig.builder().setMaxDialogHeightPx(Integer.valueOf((int) (((float) displayMetrics.heightPixels) * 0.9f))).setMaxDialogWidthPx(Integer.valueOf((int) (((float) displayMetrics.widthPixels) * 0.9f)));
        Float valueOf = Float.valueOf(0.8f);
        return maxDialogWidthPx.setMaxImageWidthWeight(valueOf).setMaxImageHeightWeight(valueOf).setViewWindowGravity(17).setWindowFlag(Integer.valueOf(DISABLED_BG_FLAG)).setWindowWidth(-2).setWindowHeight(-2).setBackgroundEnabled(false).setAnimate(false).setAutoDismiss(false).build();
    }

    @IntoMap
    @StringKey("IMAGE_ONLY_LANDSCAPE")
    @Provides
    public InAppMessageLayoutConfig providesLandscapeImageLayoutConfig(DisplayMetrics displayMetrics) {
        InAppMessageLayoutConfig.Builder maxDialogWidthPx = InAppMessageLayoutConfig.builder().setMaxDialogHeightPx(Integer.valueOf((int) (((float) displayMetrics.heightPixels) * 0.9f))).setMaxDialogWidthPx(Integer.valueOf((int) (((float) displayMetrics.widthPixels) * 0.9f)));
        Float valueOf = Float.valueOf(0.8f);
        return maxDialogWidthPx.setMaxImageWidthWeight(valueOf).setMaxImageHeightWeight(valueOf).setViewWindowGravity(17).setWindowFlag(Integer.valueOf(DISABLED_BG_FLAG)).setWindowWidth(-2).setWindowHeight(-2).setBackgroundEnabled(false).setAnimate(false).setAutoDismiss(false).build();
    }

    @IntoMap
    @StringKey("MODAL_LANDSCAPE")
    @Provides
    public InAppMessageLayoutConfig providesModalLandscapeConfig(DisplayMetrics displayMetrics) {
        InAppMessageLayoutConfig.Builder maxImageHeightWeight = InAppMessageLayoutConfig.builder().setMaxDialogHeightPx(Integer.valueOf((int) (((double) displayMetrics.heightPixels) * 0.8d))).setMaxDialogWidthPx(Integer.valueOf(displayMetrics.widthPixels)).setMaxImageHeightWeight(Float.valueOf(1.0f));
        Float valueOf = Float.valueOf(0.4f);
        return maxImageHeightWeight.setMaxImageWidthWeight(valueOf).setMaxBodyHeightWeight(Float.valueOf(0.6f)).setMaxBodyWidthWeight(valueOf).setViewWindowGravity(17).setWindowFlag(Integer.valueOf(DISABLED_BG_FLAG)).setWindowWidth(-1).setWindowHeight(-1).setBackgroundEnabled(false).setAnimate(false).setAutoDismiss(false).build();
    }

    @IntoMap
    @StringKey("MODAL_PORTRAIT")
    @Provides
    public InAppMessageLayoutConfig providesModalPortraitConfig(DisplayMetrics displayMetrics) {
        InAppMessageLayoutConfig.Builder maxBodyHeightWeight = InAppMessageLayoutConfig.builder().setMaxDialogHeightPx(Integer.valueOf((int) (((double) displayMetrics.heightPixels) * 0.8d))).setMaxDialogWidthPx(Integer.valueOf((int) (((float) displayMetrics.widthPixels) * 0.7f))).setMaxImageHeightWeight(Float.valueOf(0.6f)).setMaxBodyHeightWeight(Float.valueOf(0.1f));
        Float valueOf = Float.valueOf(0.9f);
        return maxBodyHeightWeight.setMaxImageWidthWeight(valueOf).setMaxBodyWidthWeight(valueOf).setViewWindowGravity(17).setWindowFlag(Integer.valueOf(DISABLED_BG_FLAG)).setWindowWidth(-1).setWindowHeight(-2).setBackgroundEnabled(false).setAnimate(false).setAutoDismiss(false).build();
    }

    @IntoMap
    @StringKey("CARD_LANDSCAPE")
    @Provides
    public InAppMessageLayoutConfig providesCardLandscapeConfig(DisplayMetrics displayMetrics) {
        return InAppMessageLayoutConfig.builder().setMaxDialogHeightPx(Integer.valueOf((int) (((double) displayMetrics.heightPixels) * 0.8d))).setMaxDialogWidthPx(Integer.valueOf(displayMetrics.widthPixels)).setMaxImageHeightWeight(Float.valueOf(1.0f)).setMaxImageWidthWeight(Float.valueOf(0.5f)).setViewWindowGravity(17).setWindowFlag(Integer.valueOf(DISMISSIBLE_DIALOG_FLAG)).setWindowWidth(-2).setWindowHeight(-2).setBackgroundEnabled(false).setAnimate(false).setAutoDismiss(false).build();
    }

    @IntoMap
    @StringKey("CARD_PORTRAIT")
    @Provides
    public InAppMessageLayoutConfig providesCardPortraitConfig(DisplayMetrics displayMetrics) {
        return InAppMessageLayoutConfig.builder().setMaxDialogHeightPx(Integer.valueOf((int) (((double) displayMetrics.heightPixels) * 0.8d))).setMaxDialogWidthPx(Integer.valueOf((int) (((float) displayMetrics.widthPixels) * 0.7f))).setMaxImageHeightWeight(Float.valueOf(0.6f)).setMaxImageWidthWeight(Float.valueOf(1.0f)).setMaxBodyHeightWeight(Float.valueOf(0.1f)).setMaxBodyWidthWeight(Float.valueOf(0.9f)).setViewWindowGravity(17).setWindowFlag(Integer.valueOf(DISMISSIBLE_DIALOG_FLAG)).setWindowWidth(-2).setWindowHeight(-2).setBackgroundEnabled(false).setAnimate(false).setAutoDismiss(false).build();
    }

    @IntoMap
    @StringKey("BANNER_PORTRAIT")
    @Provides
    public InAppMessageLayoutConfig providesBannerPortraitLayoutConfig(DisplayMetrics displayMetrics) {
        InAppMessageLayoutConfig.Builder builder = InAppMessageLayoutConfig.builder();
        Float valueOf = Float.valueOf(0.3f);
        return builder.setMaxImageHeightWeight(valueOf).setMaxImageWidthWeight(valueOf).setMaxDialogHeightPx(Integer.valueOf((int) (((float) displayMetrics.heightPixels) * 0.5f))).setMaxDialogWidthPx(Integer.valueOf((int) (((float) displayMetrics.widthPixels) * 0.9f))).setViewWindowGravity(48).setWindowFlag(Integer.valueOf(this.ENABLED_BG_FLAG)).setWindowWidth(-1).setWindowHeight(-2).setBackgroundEnabled(true).setAnimate(true).setAutoDismiss(true).build();
    }

    @IntoMap
    @StringKey("BANNER_LANDSCAPE")
    @Provides
    public InAppMessageLayoutConfig providesBannerLandscapeLayoutConfig(DisplayMetrics displayMetrics) {
        InAppMessageLayoutConfig.Builder builder = InAppMessageLayoutConfig.builder();
        Float valueOf = Float.valueOf(0.3f);
        return builder.setMaxImageHeightWeight(valueOf).setMaxImageWidthWeight(valueOf).setMaxDialogHeightPx(Integer.valueOf((int) (((float) displayMetrics.heightPixels) * 0.5f))).setMaxDialogWidthPx(Integer.valueOf((int) (((float) displayMetrics.widthPixels) * 0.9f))).setViewWindowGravity(48).setWindowFlag(Integer.valueOf(this.ENABLED_BG_FLAG)).setWindowWidth(-1).setWindowHeight(-2).setBackgroundEnabled(true).setAnimate(true).setAutoDismiss(true).build();
    }
}
