package androidx.mediarouter.app;

import android.app.PendingIntent;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.RemoteException;
import android.os.SystemClock;
import android.support.p000v4.media.MediaDescriptionCompat;
import android.support.p000v4.media.MediaMetadataCompat;
import android.support.p000v4.media.session.MediaControllerCompat;
import android.support.p000v4.media.session.MediaSessionCompat;
import android.support.p000v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.core.util.ObjectsCompat;
import androidx.mediarouter.C2142R;
import androidx.mediarouter.app.OverlayListView;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.palette.graphics.Palette;
import com.facebook.common.util.UriUtil;
import com.google.common.primitives.Ints;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class MediaRouteControllerDialog extends AlertDialog {
    static final int BUTTON_DISCONNECT_RES_ID = 16908314;
    private static final int BUTTON_NEUTRAL_RES_ID = 16908315;
    static final int BUTTON_STOP_RES_ID = 16908313;
    static final int CONNECTION_TIMEOUT_MILLIS = ((int) TimeUnit.SECONDS.toMillis(30));
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    static final String TAG = "MediaRouteCtrlDialog";
    static final int VOLUME_UPDATE_DELAY_MILLIS = 500;
    private Interpolator mAccelerateDecelerateInterpolator;
    final AccessibilityManager mAccessibilityManager;
    int mArtIconBackgroundColor;
    Bitmap mArtIconBitmap;
    boolean mArtIconIsLoaded;
    Bitmap mArtIconLoadedBitmap;
    Uri mArtIconUri;
    private ImageView mArtView;
    private boolean mAttachedToWindow;
    private final MediaRouterCallback mCallback;
    private ImageButton mCloseButton;
    Context mContext;
    MediaControllerCallback mControllerCallback;
    private boolean mCreated;
    private FrameLayout mCustomControlLayout;
    private View mCustomControlView;
    FrameLayout mDefaultControlLayout;
    MediaDescriptionCompat mDescription;
    private LinearLayout mDialogAreaLayout;
    private int mDialogContentWidth;
    private Button mDisconnectButton;
    private View mDividerView;
    private FrameLayout mExpandableAreaLayout;
    private Interpolator mFastOutSlowInInterpolator;
    FetchArtTask mFetchArtTask;
    private MediaRouteExpandCollapseButton mGroupExpandCollapseButton;
    int mGroupListAnimationDurationMs;
    Runnable mGroupListFadeInAnimation;
    private int mGroupListFadeInDurationMs;
    private int mGroupListFadeOutDurationMs;
    private List<MediaRouter.RouteInfo> mGroupMemberRoutes;
    Set<MediaRouter.RouteInfo> mGroupMemberRoutesAdded;
    Set<MediaRouter.RouteInfo> mGroupMemberRoutesAnimatingWithBitmap;
    private Set<MediaRouter.RouteInfo> mGroupMemberRoutesRemoved;
    boolean mHasPendingUpdate;
    private Interpolator mInterpolator;
    boolean mIsGroupExpanded;
    boolean mIsGroupListAnimating;
    boolean mIsGroupListAnimationPending;
    private Interpolator mLinearOutSlowInInterpolator;
    MediaControllerCompat mMediaController;
    private LinearLayout mMediaMainControlLayout;
    boolean mPendingUpdateAnimationNeeded;
    private ImageButton mPlaybackControlButton;
    private RelativeLayout mPlaybackControlLayout;
    final MediaRouter.RouteInfo mRoute;
    MediaRouter.RouteInfo mRouteInVolumeSliderTouched;
    private TextView mRouteNameTextView;
    final MediaRouter mRouter;
    PlaybackStateCompat mState;
    private Button mStopCastingButton;
    private TextView mSubtitleView;
    private TextView mTitleView;
    VolumeChangeListener mVolumeChangeListener;
    private boolean mVolumeControlEnabled;
    private LinearLayout mVolumeControlLayout;
    VolumeGroupAdapter mVolumeGroupAdapter;
    OverlayListView mVolumeGroupList;
    private int mVolumeGroupListItemHeight;
    private int mVolumeGroupListItemIconSize;
    private int mVolumeGroupListMaxHeight;
    private final int mVolumeGroupListPaddingTop;
    SeekBar mVolumeSlider;
    Map<MediaRouter.RouteInfo, SeekBar> mVolumeSliderMap;

    public MediaRouteControllerDialog(Context context) {
        this(context, 0);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MediaRouteControllerDialog(android.content.Context r4, int r5) {
        /*
            r3 = this;
            r0 = 1
            android.content.Context r1 = androidx.mediarouter.app.MediaRouterThemeHelper.createThemedDialogContext(r4, r5, r0)
            r4 = r1
            int r2 = androidx.mediarouter.app.MediaRouterThemeHelper.createThemedDialogStyle(r4)
            r3.<init>(r1, r2)
            r3.mVolumeControlEnabled = r0
            androidx.mediarouter.app.MediaRouteControllerDialog$1 r0 = new androidx.mediarouter.app.MediaRouteControllerDialog$1
            r0.<init>()
            r3.mGroupListFadeInAnimation = r0
            android.content.Context r0 = r3.getContext()
            r3.mContext = r0
            androidx.mediarouter.app.MediaRouteControllerDialog$MediaControllerCallback r0 = new androidx.mediarouter.app.MediaRouteControllerDialog$MediaControllerCallback
            r0.<init>()
            r3.mControllerCallback = r0
            android.content.Context r0 = r3.mContext
            androidx.mediarouter.media.MediaRouter r0 = androidx.mediarouter.media.MediaRouter.getInstance(r0)
            r3.mRouter = r0
            androidx.mediarouter.app.MediaRouteControllerDialog$MediaRouterCallback r1 = new androidx.mediarouter.app.MediaRouteControllerDialog$MediaRouterCallback
            r1.<init>()
            r3.mCallback = r1
            androidx.mediarouter.media.MediaRouter$RouteInfo r1 = r0.getSelectedRoute()
            r3.mRoute = r1
            android.support.v4.media.session.MediaSessionCompat$Token r0 = r0.getMediaSessionToken()
            r3.setMediaSession(r0)
            android.content.Context r0 = r3.mContext
            android.content.res.Resources r0 = r0.getResources()
            int r1 = androidx.mediarouter.C2142R.dimen.mr_controller_volume_group_list_padding_top
            int r0 = r0.getDimensionPixelSize(r1)
            r3.mVolumeGroupListPaddingTop = r0
            android.content.Context r0 = r3.mContext
            java.lang.String r1 = "accessibility"
            java.lang.Object r0 = r0.getSystemService(r1)
            android.view.accessibility.AccessibilityManager r0 = (android.view.accessibility.AccessibilityManager) r0
            r3.mAccessibilityManager = r0
            int r0 = android.os.Build.VERSION.SDK_INT
            r1 = 21
            if (r0 < r1) goto L_0x006f
            int r0 = androidx.mediarouter.C2142R.C2146interpolator.mr_linear_out_slow_in
            android.view.animation.Interpolator r0 = android.view.animation.AnimationUtils.loadInterpolator(r4, r0)
            r3.mLinearOutSlowInInterpolator = r0
            int r0 = androidx.mediarouter.C2142R.C2146interpolator.mr_fast_out_slow_in
            android.view.animation.Interpolator r0 = android.view.animation.AnimationUtils.loadInterpolator(r4, r0)
            r3.mFastOutSlowInInterpolator = r0
        L_0x006f:
            android.view.animation.AccelerateDecelerateInterpolator r0 = new android.view.animation.AccelerateDecelerateInterpolator
            r0.<init>()
            r3.mAccelerateDecelerateInterpolator = r0
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteControllerDialog.<init>(android.content.Context, int):void");
    }

    public MediaRouter.RouteInfo getRoute() {
        return this.mRoute;
    }

    public View onCreateMediaControlView(Bundle savedInstanceState) {
        return null;
    }

    public View getMediaControlView() {
        return this.mCustomControlView;
    }

    public void setVolumeControlEnabled(boolean enable) {
        if (this.mVolumeControlEnabled != enable) {
            this.mVolumeControlEnabled = enable;
            if (this.mCreated) {
                update(false);
            }
        }
    }

    public boolean isVolumeControlEnabled() {
        return this.mVolumeControlEnabled;
    }

    private void setMediaSession(MediaSessionCompat.Token sessionToken) {
        MediaMetadataCompat metadata;
        MediaControllerCompat mediaControllerCompat = this.mMediaController;
        PlaybackStateCompat playbackStateCompat = null;
        if (mediaControllerCompat != null) {
            mediaControllerCompat.unregisterCallback(this.mControllerCallback);
            this.mMediaController = null;
        }
        if (sessionToken != null && this.mAttachedToWindow) {
            try {
                this.mMediaController = new MediaControllerCompat(this.mContext, sessionToken);
            } catch (RemoteException e) {
                Log.e(TAG, "Error creating media controller in setMediaSession.", e);
            }
            MediaControllerCompat mediaControllerCompat2 = this.mMediaController;
            if (mediaControllerCompat2 != null) {
                mediaControllerCompat2.registerCallback(this.mControllerCallback);
            }
            MediaControllerCompat mediaControllerCompat3 = this.mMediaController;
            if (mediaControllerCompat3 == null) {
                metadata = null;
            } else {
                metadata = mediaControllerCompat3.getMetadata();
            }
            this.mDescription = metadata == null ? null : metadata.getDescription();
            MediaControllerCompat mediaControllerCompat4 = this.mMediaController;
            if (mediaControllerCompat4 != null) {
                playbackStateCompat = mediaControllerCompat4.getPlaybackState();
            }
            this.mState = playbackStateCompat;
            updateArtIconIfNeeded();
            update(false);
        }
    }

    public MediaSessionCompat.Token getMediaSession() {
        MediaControllerCompat mediaControllerCompat = this.mMediaController;
        if (mediaControllerCompat == null) {
            return null;
        }
        return mediaControllerCompat.getSessionToken();
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setBackgroundDrawableResource(17170445);
        setContentView(C2142R.C2147layout.mr_controller_material_dialog_b);
        findViewById(BUTTON_NEUTRAL_RES_ID).setVisibility(8);
        ClickListener listener = new ClickListener();
        FrameLayout frameLayout = (FrameLayout) findViewById(C2142R.C2145id.mr_expandable_area);
        this.mExpandableAreaLayout = frameLayout;
        frameLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MediaRouteControllerDialog.this.dismiss();
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(C2142R.C2145id.mr_dialog_area);
        this.mDialogAreaLayout = linearLayout;
        linearLayout.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            }
        });
        int color = MediaRouterThemeHelper.getButtonTextColor(this.mContext);
        Button button = (Button) findViewById(BUTTON_DISCONNECT_RES_ID);
        this.mDisconnectButton = button;
        button.setText(C2142R.string.mr_controller_disconnect);
        this.mDisconnectButton.setTextColor(color);
        this.mDisconnectButton.setOnClickListener(listener);
        Button button2 = (Button) findViewById(BUTTON_STOP_RES_ID);
        this.mStopCastingButton = button2;
        button2.setText(C2142R.string.mr_controller_stop_casting);
        this.mStopCastingButton.setTextColor(color);
        this.mStopCastingButton.setOnClickListener(listener);
        this.mRouteNameTextView = (TextView) findViewById(C2142R.C2145id.mr_name);
        ImageButton imageButton = (ImageButton) findViewById(C2142R.C2145id.mr_close);
        this.mCloseButton = imageButton;
        imageButton.setOnClickListener(listener);
        this.mCustomControlLayout = (FrameLayout) findViewById(C2142R.C2145id.mr_custom_control);
        this.mDefaultControlLayout = (FrameLayout) findViewById(C2142R.C2145id.mr_default_control);
        View.OnClickListener onClickListener = new View.OnClickListener() {
            public void onClick(View v) {
                PendingIntent pi;
                if (MediaRouteControllerDialog.this.mMediaController != null && (pi = MediaRouteControllerDialog.this.mMediaController.getSessionActivity()) != null) {
                    try {
                        pi.send();
                        MediaRouteControllerDialog.this.dismiss();
                    } catch (PendingIntent.CanceledException e) {
                        Log.e(MediaRouteControllerDialog.TAG, pi + " was not sent, it had been canceled.");
                    }
                }
            }
        };
        ImageView imageView = (ImageView) findViewById(C2142R.C2145id.mr_art);
        this.mArtView = imageView;
        imageView.setOnClickListener(onClickListener);
        findViewById(C2142R.C2145id.mr_control_title_container).setOnClickListener(onClickListener);
        this.mMediaMainControlLayout = (LinearLayout) findViewById(C2142R.C2145id.mr_media_main_control);
        this.mDividerView = findViewById(C2142R.C2145id.mr_control_divider);
        this.mPlaybackControlLayout = (RelativeLayout) findViewById(C2142R.C2145id.mr_playback_control);
        this.mTitleView = (TextView) findViewById(C2142R.C2145id.mr_control_title);
        this.mSubtitleView = (TextView) findViewById(C2142R.C2145id.mr_control_subtitle);
        ImageButton imageButton2 = (ImageButton) findViewById(C2142R.C2145id.mr_control_playback_ctrl);
        this.mPlaybackControlButton = imageButton2;
        imageButton2.setOnClickListener(listener);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(C2142R.C2145id.mr_volume_control);
        this.mVolumeControlLayout = linearLayout2;
        linearLayout2.setVisibility(8);
        SeekBar seekBar = (SeekBar) findViewById(C2142R.C2145id.mr_volume_slider);
        this.mVolumeSlider = seekBar;
        seekBar.setTag(this.mRoute);
        VolumeChangeListener volumeChangeListener = new VolumeChangeListener();
        this.mVolumeChangeListener = volumeChangeListener;
        this.mVolumeSlider.setOnSeekBarChangeListener(volumeChangeListener);
        this.mVolumeGroupList = (OverlayListView) findViewById(C2142R.C2145id.mr_volume_group_list);
        this.mGroupMemberRoutes = new ArrayList();
        VolumeGroupAdapter volumeGroupAdapter = new VolumeGroupAdapter(this.mVolumeGroupList.getContext(), this.mGroupMemberRoutes);
        this.mVolumeGroupAdapter = volumeGroupAdapter;
        this.mVolumeGroupList.setAdapter(volumeGroupAdapter);
        this.mGroupMemberRoutesAnimatingWithBitmap = new HashSet();
        MediaRouterThemeHelper.setMediaControlsBackgroundColor(this.mContext, this.mMediaMainControlLayout, this.mVolumeGroupList, this.mRoute.isGroup());
        MediaRouterThemeHelper.setVolumeSliderColor(this.mContext, (MediaRouteVolumeSlider) this.mVolumeSlider, this.mMediaMainControlLayout);
        HashMap hashMap = new HashMap();
        this.mVolumeSliderMap = hashMap;
        hashMap.put(this.mRoute, this.mVolumeSlider);
        MediaRouteExpandCollapseButton mediaRouteExpandCollapseButton = (MediaRouteExpandCollapseButton) findViewById(C2142R.C2145id.mr_group_expand_collapse);
        this.mGroupExpandCollapseButton = mediaRouteExpandCollapseButton;
        mediaRouteExpandCollapseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                mediaRouteControllerDialog.mIsGroupExpanded = !mediaRouteControllerDialog.mIsGroupExpanded;
                if (MediaRouteControllerDialog.this.mIsGroupExpanded) {
                    MediaRouteControllerDialog.this.mVolumeGroupList.setVisibility(0);
                }
                MediaRouteControllerDialog.this.loadInterpolator();
                MediaRouteControllerDialog.this.updateLayoutHeight(true);
            }
        });
        loadInterpolator();
        this.mGroupListAnimationDurationMs = this.mContext.getResources().getInteger(C2142R.integer.mr_controller_volume_group_list_animation_duration_ms);
        this.mGroupListFadeInDurationMs = this.mContext.getResources().getInteger(C2142R.integer.mr_controller_volume_group_list_fade_in_duration_ms);
        this.mGroupListFadeOutDurationMs = this.mContext.getResources().getInteger(C2142R.integer.mr_controller_volume_group_list_fade_out_duration_ms);
        View onCreateMediaControlView = onCreateMediaControlView(savedInstanceState);
        this.mCustomControlView = onCreateMediaControlView;
        if (onCreateMediaControlView != null) {
            this.mCustomControlLayout.addView(onCreateMediaControlView);
            this.mCustomControlLayout.setVisibility(0);
        }
        this.mCreated = true;
        updateLayout();
    }

    /* access modifiers changed from: package-private */
    public void updateLayout() {
        int width = MediaRouteDialogHelper.getDialogWidth(this.mContext);
        getWindow().setLayout(width, -2);
        View decorView = getWindow().getDecorView();
        this.mDialogContentWidth = (width - decorView.getPaddingLeft()) - decorView.getPaddingRight();
        Resources res = this.mContext.getResources();
        this.mVolumeGroupListItemIconSize = res.getDimensionPixelSize(C2142R.dimen.mr_controller_volume_group_list_item_icon_size);
        this.mVolumeGroupListItemHeight = res.getDimensionPixelSize(C2142R.dimen.mr_controller_volume_group_list_item_height);
        this.mVolumeGroupListMaxHeight = res.getDimensionPixelSize(C2142R.dimen.mr_controller_volume_group_list_max_height);
        this.mArtIconBitmap = null;
        this.mArtIconUri = null;
        updateArtIconIfNeeded();
        update(false);
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(MediaRouteSelector.EMPTY, this.mCallback, 2);
        setMediaSession(this.mRouter.getMediaSessionToken());
    }

    public void onDetachedFromWindow() {
        this.mRouter.removeCallback(this.mCallback);
        setMediaSession((MediaSessionCompat.Token) null);
        this.mAttachedToWindow = false;
        super.onDetachedFromWindow();
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 25 && keyCode != 24) {
            return super.onKeyDown(keyCode, event);
        }
        this.mRoute.requestUpdateVolume(keyCode == 25 ? -1 : 1);
        return true;
    }

    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (keyCode == 25 || keyCode == 24) {
            return true;
        }
        return super.onKeyUp(keyCode, event);
    }

    /* access modifiers changed from: package-private */
    public void update(boolean animate) {
        if (this.mRouteInVolumeSliderTouched != null) {
            this.mHasPendingUpdate = true;
            this.mPendingUpdateAnimationNeeded |= animate;
            return;
        }
        int i = 0;
        this.mHasPendingUpdate = false;
        this.mPendingUpdateAnimationNeeded = false;
        if (!this.mRoute.isSelected() || this.mRoute.isDefaultOrBluetooth()) {
            dismiss();
        } else if (this.mCreated) {
            this.mRouteNameTextView.setText(this.mRoute.getName());
            Button button = this.mDisconnectButton;
            if (!this.mRoute.canDisconnect()) {
                i = 8;
            }
            button.setVisibility(i);
            if (this.mCustomControlView == null && this.mArtIconIsLoaded) {
                if (isBitmapRecycled(this.mArtIconLoadedBitmap)) {
                    Log.w(TAG, "Can't set artwork image with recycled bitmap: " + this.mArtIconLoadedBitmap);
                } else {
                    this.mArtView.setImageBitmap(this.mArtIconLoadedBitmap);
                    this.mArtView.setBackgroundColor(this.mArtIconBackgroundColor);
                }
                clearLoadedBitmap();
            }
            updateVolumeControlLayout();
            updatePlaybackControlLayout();
            updateLayoutHeight(animate);
        }
    }

    static boolean isBitmapRecycled(Bitmap bitmap) {
        return bitmap != null && bitmap.isRecycled();
    }

    private boolean canShowPlaybackControlLayout() {
        return this.mCustomControlView == null && !(this.mDescription == null && this.mState == null);
    }

    private int getMainControllerHeight(boolean showPlaybackControl) {
        if (!showPlaybackControl && this.mVolumeControlLayout.getVisibility() != 0) {
            return 0;
        }
        int height = 0 + this.mMediaMainControlLayout.getPaddingTop() + this.mMediaMainControlLayout.getPaddingBottom();
        if (showPlaybackControl) {
            height += this.mPlaybackControlLayout.getMeasuredHeight();
        }
        if (this.mVolumeControlLayout.getVisibility() == 0) {
            height += this.mVolumeControlLayout.getMeasuredHeight();
        }
        if (!showPlaybackControl || this.mVolumeControlLayout.getVisibility() != 0) {
            return height;
        }
        return height + this.mDividerView.getMeasuredHeight();
    }

    private void updateMediaControlVisibility(boolean canShowPlaybackControlLayout) {
        int i = 0;
        this.mDividerView.setVisibility((this.mVolumeControlLayout.getVisibility() != 0 || !canShowPlaybackControlLayout) ? 8 : 0);
        LinearLayout linearLayout = this.mMediaMainControlLayout;
        if (this.mVolumeControlLayout.getVisibility() == 8 && !canShowPlaybackControlLayout) {
            i = 8;
        }
        linearLayout.setVisibility(i);
    }

    /* access modifiers changed from: package-private */
    public void updateLayoutHeight(final boolean animate) {
        this.mDefaultControlLayout.requestLayout();
        this.mDefaultControlLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                MediaRouteControllerDialog.this.mDefaultControlLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                if (MediaRouteControllerDialog.this.mIsGroupListAnimating) {
                    MediaRouteControllerDialog.this.mIsGroupListAnimationPending = true;
                } else {
                    MediaRouteControllerDialog.this.updateLayoutHeightInternal(animate);
                }
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void updateLayoutHeightInternal(boolean animate) {
        Bitmap art;
        int oldHeight = getLayoutHeight(this.mMediaMainControlLayout);
        setLayoutHeight(this.mMediaMainControlLayout, -1);
        updateMediaControlVisibility(canShowPlaybackControlLayout());
        View decorView = getWindow().getDecorView();
        boolean z = false;
        decorView.measure(View.MeasureSpec.makeMeasureSpec(getWindow().getAttributes().width, Ints.MAX_POWER_OF_TWO), 0);
        setLayoutHeight(this.mMediaMainControlLayout, oldHeight);
        int artViewHeight = 0;
        if (this.mCustomControlView == null && (this.mArtView.getDrawable() instanceof BitmapDrawable) && (art = ((BitmapDrawable) this.mArtView.getDrawable()).getBitmap()) != null) {
            artViewHeight = getDesiredArtHeight(art.getWidth(), art.getHeight());
            this.mArtView.setScaleType(art.getWidth() >= art.getHeight() ? ImageView.ScaleType.FIT_XY : ImageView.ScaleType.FIT_CENTER);
        }
        int mainControllerHeight = getMainControllerHeight(canShowPlaybackControlLayout());
        int volumeGroupListCount = this.mGroupMemberRoutes.size();
        int expandedGroupListHeight = this.mRoute.isGroup() ? this.mVolumeGroupListItemHeight * this.mRoute.getMemberRoutes().size() : 0;
        if (volumeGroupListCount > 0) {
            expandedGroupListHeight += this.mVolumeGroupListPaddingTop;
        }
        int visibleGroupListHeight = this.mIsGroupExpanded ? Math.min(expandedGroupListHeight, this.mVolumeGroupListMaxHeight) : 0;
        int desiredControlLayoutHeight = Math.max(artViewHeight, visibleGroupListHeight) + mainControllerHeight;
        Rect visibleRect = new Rect();
        decorView.getWindowVisibleDisplayFrame(visibleRect);
        int maximumControlViewHeight = visibleRect.height() - (this.mDialogAreaLayout.getMeasuredHeight() - this.mDefaultControlLayout.getMeasuredHeight());
        if (this.mCustomControlView != null || artViewHeight <= 0 || desiredControlLayoutHeight > maximumControlViewHeight) {
            if (getLayoutHeight(this.mVolumeGroupList) + this.mMediaMainControlLayout.getMeasuredHeight() >= this.mDefaultControlLayout.getMeasuredHeight()) {
                this.mArtView.setVisibility(8);
            }
            artViewHeight = 0;
            desiredControlLayoutHeight = visibleGroupListHeight + mainControllerHeight;
        } else {
            this.mArtView.setVisibility(0);
            setLayoutHeight(this.mArtView, artViewHeight);
        }
        if (!canShowPlaybackControlLayout() || desiredControlLayoutHeight > maximumControlViewHeight) {
            this.mPlaybackControlLayout.setVisibility(8);
        } else {
            this.mPlaybackControlLayout.setVisibility(0);
        }
        updateMediaControlVisibility(this.mPlaybackControlLayout.getVisibility() == 0);
        if (this.mPlaybackControlLayout.getVisibility() == 0) {
            z = true;
        }
        int mainControllerHeight2 = getMainControllerHeight(z);
        int desiredControlLayoutHeight2 = Math.max(artViewHeight, visibleGroupListHeight) + mainControllerHeight2;
        if (desiredControlLayoutHeight2 > maximumControlViewHeight) {
            visibleGroupListHeight -= desiredControlLayoutHeight2 - maximumControlViewHeight;
            desiredControlLayoutHeight2 = maximumControlViewHeight;
        }
        this.mMediaMainControlLayout.clearAnimation();
        this.mVolumeGroupList.clearAnimation();
        this.mDefaultControlLayout.clearAnimation();
        if (animate) {
            animateLayoutHeight(this.mMediaMainControlLayout, mainControllerHeight2);
            animateLayoutHeight(this.mVolumeGroupList, visibleGroupListHeight);
            animateLayoutHeight(this.mDefaultControlLayout, desiredControlLayoutHeight2);
        } else {
            setLayoutHeight(this.mMediaMainControlLayout, mainControllerHeight2);
            setLayoutHeight(this.mVolumeGroupList, visibleGroupListHeight);
            setLayoutHeight(this.mDefaultControlLayout, desiredControlLayoutHeight2);
        }
        setLayoutHeight(this.mExpandableAreaLayout, visibleRect.height());
        rebuildVolumeGroupList(animate);
    }

    /* access modifiers changed from: package-private */
    public void updateVolumeGroupItemHeight(View item) {
        setLayoutHeight((LinearLayout) item.findViewById(C2142R.C2145id.volume_item_container), this.mVolumeGroupListItemHeight);
        View icon = item.findViewById(C2142R.C2145id.mr_volume_item_icon);
        ViewGroup.LayoutParams lp = icon.getLayoutParams();
        lp.width = this.mVolumeGroupListItemIconSize;
        lp.height = this.mVolumeGroupListItemIconSize;
        icon.setLayoutParams(lp);
    }

    private void animateLayoutHeight(final View view, int targetHeight) {
        final int startValue = getLayoutHeight(view);
        final int endValue = targetHeight;
        Animation anim = new Animation() {
            /* access modifiers changed from: protected */
            public void applyTransformation(float interpolatedTime, Transformation t) {
                int i = startValue;
                MediaRouteControllerDialog.setLayoutHeight(view, i - ((int) (((float) (i - endValue)) * interpolatedTime)));
            }
        };
        anim.setDuration((long) this.mGroupListAnimationDurationMs);
        if (Build.VERSION.SDK_INT >= 21) {
            anim.setInterpolator(this.mInterpolator);
        }
        view.startAnimation(anim);
    }

    /* access modifiers changed from: package-private */
    public void loadInterpolator() {
        if (Build.VERSION.SDK_INT >= 21) {
            this.mInterpolator = this.mIsGroupExpanded ? this.mLinearOutSlowInInterpolator : this.mFastOutSlowInInterpolator;
        } else {
            this.mInterpolator = this.mAccelerateDecelerateInterpolator;
        }
    }

    private void updateVolumeControlLayout() {
        int i = 8;
        if (!isVolumeControlAvailable(this.mRoute)) {
            this.mVolumeControlLayout.setVisibility(8);
        } else if (this.mVolumeControlLayout.getVisibility() == 8) {
            this.mVolumeControlLayout.setVisibility(0);
            this.mVolumeSlider.setMax(this.mRoute.getVolumeMax());
            this.mVolumeSlider.setProgress(this.mRoute.getVolume());
            MediaRouteExpandCollapseButton mediaRouteExpandCollapseButton = this.mGroupExpandCollapseButton;
            if (this.mRoute.isGroup()) {
                i = 0;
            }
            mediaRouteExpandCollapseButton.setVisibility(i);
        }
    }

    private void rebuildVolumeGroupList(boolean animate) {
        List<MediaRouter.RouteInfo> routes = this.mRoute.getMemberRoutes();
        if (routes.isEmpty()) {
            this.mGroupMemberRoutes.clear();
            this.mVolumeGroupAdapter.notifyDataSetChanged();
        } else if (MediaRouteDialogHelper.listUnorderedEquals(this.mGroupMemberRoutes, routes)) {
            this.mVolumeGroupAdapter.notifyDataSetChanged();
        } else {
            HashMap<MediaRouter.RouteInfo, Rect> previousRouteBoundMap = animate ? MediaRouteDialogHelper.getItemBoundMap(this.mVolumeGroupList, this.mVolumeGroupAdapter) : null;
            HashMap<MediaRouter.RouteInfo, BitmapDrawable> previousRouteBitmapMap = animate ? MediaRouteDialogHelper.getItemBitmapMap(this.mContext, this.mVolumeGroupList, this.mVolumeGroupAdapter) : null;
            this.mGroupMemberRoutesAdded = MediaRouteDialogHelper.getItemsAdded(this.mGroupMemberRoutes, routes);
            this.mGroupMemberRoutesRemoved = MediaRouteDialogHelper.getItemsRemoved(this.mGroupMemberRoutes, routes);
            this.mGroupMemberRoutes.addAll(0, this.mGroupMemberRoutesAdded);
            this.mGroupMemberRoutes.removeAll(this.mGroupMemberRoutesRemoved);
            this.mVolumeGroupAdapter.notifyDataSetChanged();
            if (!animate || !this.mIsGroupExpanded || this.mGroupMemberRoutesAdded.size() + this.mGroupMemberRoutesRemoved.size() <= 0) {
                this.mGroupMemberRoutesAdded = null;
                this.mGroupMemberRoutesRemoved = null;
                return;
            }
            animateGroupListItems(previousRouteBoundMap, previousRouteBitmapMap);
        }
    }

    private void animateGroupListItems(final Map<MediaRouter.RouteInfo, Rect> previousRouteBoundMap, final Map<MediaRouter.RouteInfo, BitmapDrawable> previousRouteBitmapMap) {
        this.mVolumeGroupList.setEnabled(false);
        this.mVolumeGroupList.requestLayout();
        this.mIsGroupListAnimating = true;
        this.mVolumeGroupList.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                MediaRouteControllerDialog.this.mVolumeGroupList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                MediaRouteControllerDialog.this.animateGroupListItemsInternal(previousRouteBoundMap, previousRouteBitmapMap);
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void animateGroupListItemsInternal(Map<MediaRouter.RouteInfo, Rect> previousRouteBoundMap, Map<MediaRouter.RouteInfo, BitmapDrawable> previousRouteBitmapMap) {
        boolean listenerRegistered;
        Animation.AnimationListener listener;
        OverlayListView.OverlayObject object;
        Map<MediaRouter.RouteInfo, Rect> map = previousRouteBoundMap;
        Set<MediaRouter.RouteInfo> set = this.mGroupMemberRoutesAdded;
        if (set == null) {
            Map<MediaRouter.RouteInfo, BitmapDrawable> map2 = previousRouteBitmapMap;
        } else if (this.mGroupMemberRoutesRemoved == null) {
            Map<MediaRouter.RouteInfo, BitmapDrawable> map3 = previousRouteBitmapMap;
        } else {
            int groupSizeDelta = set.size() - this.mGroupMemberRoutesRemoved.size();
            boolean listenerRegistered2 = false;
            Animation.AnimationListener listener2 = new Animation.AnimationListener() {
                public void onAnimationStart(Animation animation) {
                    MediaRouteControllerDialog.this.mVolumeGroupList.startAnimationAll();
                    MediaRouteControllerDialog.this.mVolumeGroupList.postDelayed(MediaRouteControllerDialog.this.mGroupListFadeInAnimation, (long) MediaRouteControllerDialog.this.mGroupListAnimationDurationMs);
                }

                public void onAnimationEnd(Animation animation) {
                }

                public void onAnimationRepeat(Animation animation) {
                }
            };
            int first = this.mVolumeGroupList.getFirstVisiblePosition();
            for (int i = 0; i < this.mVolumeGroupList.getChildCount(); i++) {
                View view = this.mVolumeGroupList.getChildAt(i);
                int position = first + i;
                MediaRouter.RouteInfo route = (MediaRouter.RouteInfo) this.mVolumeGroupAdapter.getItem(position);
                Rect previousBounds = map.get(route);
                int currentTop = view.getTop();
                int previousTop = previousBounds != null ? previousBounds.top : (this.mVolumeGroupListItemHeight * groupSizeDelta) + currentTop;
                AnimationSet animSet = new AnimationSet(true);
                Set<MediaRouter.RouteInfo> set2 = this.mGroupMemberRoutesAdded;
                if (set2 == null || !set2.contains(route)) {
                } else {
                    previousTop = currentTop;
                    Animation alphaAnim = new AlphaAnimation(0.0f, 0.0f);
                    int i2 = position;
                    alphaAnim.setDuration((long) this.mGroupListFadeInDurationMs);
                    animSet.addAnimation(alphaAnim);
                }
                Animation translationAnim = new TranslateAnimation(0.0f, 0.0f, (float) (previousTop - currentTop), 0.0f);
                Rect rect = previousBounds;
                int i3 = currentTop;
                translationAnim.setDuration((long) this.mGroupListAnimationDurationMs);
                animSet.addAnimation(translationAnim);
                animSet.setFillAfter(true);
                animSet.setFillEnabled(true);
                animSet.setInterpolator(this.mInterpolator);
                if (!listenerRegistered2) {
                    listenerRegistered2 = true;
                    animSet.setAnimationListener(listener2);
                }
                view.clearAnimation();
                view.startAnimation(animSet);
                map.remove(route);
                previousRouteBitmapMap.remove(route);
            }
            Map<MediaRouter.RouteInfo, BitmapDrawable> map4 = previousRouteBitmapMap;
            for (Map.Entry<MediaRouter.RouteInfo, BitmapDrawable> item : previousRouteBitmapMap.entrySet()) {
                final MediaRouter.RouteInfo route2 = item.getKey();
                BitmapDrawable bitmap = item.getValue();
                Rect bounds = map.get(route2);
                if (this.mGroupMemberRoutesRemoved.contains(route2)) {
                    listenerRegistered = listenerRegistered2;
                    listener = listener2;
                    object = new OverlayListView.OverlayObject(bitmap, bounds).setAlphaAnimation(1.0f, 0.0f).setDuration((long) this.mGroupListFadeOutDurationMs).setInterpolator(this.mInterpolator);
                } else {
                    listenerRegistered = listenerRegistered2;
                    listener = listener2;
                    OverlayListView.OverlayObject object2 = new OverlayListView.OverlayObject(bitmap, bounds).setTranslateYAnimation(this.mVolumeGroupListItemHeight * groupSizeDelta).setDuration((long) this.mGroupListAnimationDurationMs).setInterpolator(this.mInterpolator).setAnimationEndListener(new OverlayListView.OverlayObject.OnAnimationEndListener() {
                        public void onAnimationEnd() {
                            MediaRouteControllerDialog.this.mGroupMemberRoutesAnimatingWithBitmap.remove(route2);
                            MediaRouteControllerDialog.this.mVolumeGroupAdapter.notifyDataSetChanged();
                        }
                    });
                    this.mGroupMemberRoutesAnimatingWithBitmap.add(route2);
                    object = object2;
                }
                this.mVolumeGroupList.addOverlayObject(object);
                listener2 = listener;
                listenerRegistered2 = listenerRegistered;
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void startGroupListFadeInAnimation() {
        clearGroupListAnimation(true);
        this.mVolumeGroupList.requestLayout();
        this.mVolumeGroupList.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            public void onGlobalLayout() {
                MediaRouteControllerDialog.this.mVolumeGroupList.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                MediaRouteControllerDialog.this.startGroupListFadeInAnimationInternal();
            }
        });
    }

    /* access modifiers changed from: package-private */
    public void startGroupListFadeInAnimationInternal() {
        Set<MediaRouter.RouteInfo> set = this.mGroupMemberRoutesAdded;
        if (set == null || set.size() == 0) {
            finishAnimation(true);
        } else {
            fadeInAddedRoutes();
        }
    }

    /* access modifiers changed from: package-private */
    public void finishAnimation(boolean animate) {
        this.mGroupMemberRoutesAdded = null;
        this.mGroupMemberRoutesRemoved = null;
        this.mIsGroupListAnimating = false;
        if (this.mIsGroupListAnimationPending) {
            this.mIsGroupListAnimationPending = false;
            updateLayoutHeight(animate);
        }
        this.mVolumeGroupList.setEnabled(true);
    }

    private void fadeInAddedRoutes() {
        Animation.AnimationListener listener = new Animation.AnimationListener() {
            public void onAnimationStart(Animation animation) {
            }

            public void onAnimationEnd(Animation animation) {
                MediaRouteControllerDialog.this.finishAnimation(true);
            }

            public void onAnimationRepeat(Animation animation) {
            }
        };
        boolean listenerRegistered = false;
        int first = this.mVolumeGroupList.getFirstVisiblePosition();
        for (int i = 0; i < this.mVolumeGroupList.getChildCount(); i++) {
            View view = this.mVolumeGroupList.getChildAt(i);
            VolumeGroupAdapter volumeGroupAdapter = this.mVolumeGroupAdapter;
            if (this.mGroupMemberRoutesAdded.contains((MediaRouter.RouteInfo) volumeGroupAdapter.getItem(first + i))) {
                Animation alphaAnim = new AlphaAnimation(0.0f, 1.0f);
                alphaAnim.setDuration((long) this.mGroupListFadeInDurationMs);
                alphaAnim.setFillEnabled(true);
                alphaAnim.setFillAfter(true);
                if (!listenerRegistered) {
                    listenerRegistered = true;
                    alphaAnim.setAnimationListener(listener);
                }
                view.clearAnimation();
                view.startAnimation(alphaAnim);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void clearGroupListAnimation(boolean exceptAddedRoutes) {
        Set<MediaRouter.RouteInfo> set;
        int first = this.mVolumeGroupList.getFirstVisiblePosition();
        for (int i = 0; i < this.mVolumeGroupList.getChildCount(); i++) {
            View view = this.mVolumeGroupList.getChildAt(i);
            MediaRouter.RouteInfo route = (MediaRouter.RouteInfo) this.mVolumeGroupAdapter.getItem(first + i);
            if (!exceptAddedRoutes || (set = this.mGroupMemberRoutesAdded) == null || !set.contains(route)) {
                ((LinearLayout) view.findViewById(C2142R.C2145id.volume_item_container)).setVisibility(0);
                AnimationSet animSet = new AnimationSet(true);
                Animation alphaAnim = new AlphaAnimation(1.0f, 1.0f);
                alphaAnim.setDuration(0);
                animSet.addAnimation(alphaAnim);
                new TranslateAnimation(0.0f, 0.0f, 0.0f, 0.0f).setDuration(0);
                animSet.setFillAfter(true);
                animSet.setFillEnabled(true);
                view.clearAnimation();
                view.startAnimation(animSet);
            }
        }
        this.mVolumeGroupList.stopAnimationAll();
        if (!exceptAddedRoutes) {
            finishAnimation(false);
        }
    }

    private void updatePlaybackControlLayout() {
        if (canShowPlaybackControlLayout()) {
            MediaDescriptionCompat mediaDescriptionCompat = this.mDescription;
            CharSequence subtitle = null;
            CharSequence title = mediaDescriptionCompat == null ? null : mediaDescriptionCompat.getTitle();
            boolean isPlaying = true;
            boolean hasTitle = !TextUtils.isEmpty(title);
            MediaDescriptionCompat mediaDescriptionCompat2 = this.mDescription;
            if (mediaDescriptionCompat2 != null) {
                subtitle = mediaDescriptionCompat2.getSubtitle();
            }
            boolean hasSubtitle = !TextUtils.isEmpty(subtitle);
            boolean showTitle = false;
            boolean showSubtitle = false;
            if (this.mRoute.getPresentationDisplayId() != -1) {
                this.mTitleView.setText(C2142R.string.mr_controller_casting_screen);
                showTitle = true;
            } else {
                PlaybackStateCompat playbackStateCompat = this.mState;
                if (playbackStateCompat == null || playbackStateCompat.getState() == 0) {
                    this.mTitleView.setText(C2142R.string.mr_controller_no_media_selected);
                    showTitle = true;
                } else if (hasTitle || hasSubtitle) {
                    if (hasTitle) {
                        this.mTitleView.setText(title);
                        showTitle = true;
                    }
                    if (hasSubtitle) {
                        this.mSubtitleView.setText(subtitle);
                        showSubtitle = true;
                    }
                } else {
                    this.mTitleView.setText(C2142R.string.mr_controller_no_info_available);
                    showTitle = true;
                }
            }
            int i = 8;
            this.mTitleView.setVisibility(showTitle ? 0 : 8);
            this.mSubtitleView.setVisibility(showSubtitle ? 0 : 8);
            PlaybackStateCompat playbackStateCompat2 = this.mState;
            if (playbackStateCompat2 != null) {
                if (!(playbackStateCompat2.getState() == 6 || this.mState.getState() == 3)) {
                    isPlaying = false;
                }
                Context playbackControlButtonContext = this.mPlaybackControlButton.getContext();
                boolean visible = true;
                int iconDrawableAttr = 0;
                int iconDescResId = 0;
                if (isPlaying && isPauseActionSupported()) {
                    iconDrawableAttr = C2142R.attr.mediaRoutePauseDrawable;
                    iconDescResId = C2142R.string.mr_controller_pause;
                } else if (isPlaying && isStopActionSupported()) {
                    iconDrawableAttr = C2142R.attr.mediaRouteStopDrawable;
                    iconDescResId = C2142R.string.mr_controller_stop;
                } else if (isPlaying || !isPlayActionSupported()) {
                    visible = false;
                } else {
                    iconDrawableAttr = C2142R.attr.mediaRoutePlayDrawable;
                    iconDescResId = C2142R.string.mr_controller_play;
                }
                ImageButton imageButton = this.mPlaybackControlButton;
                if (visible) {
                    i = 0;
                }
                imageButton.setVisibility(i);
                if (visible) {
                    this.mPlaybackControlButton.setImageResource(MediaRouterThemeHelper.getThemeResource(playbackControlButtonContext, iconDrawableAttr));
                    this.mPlaybackControlButton.setContentDescription(playbackControlButtonContext.getResources().getText(iconDescResId));
                }
            }
        }
    }

    /* access modifiers changed from: package-private */
    public boolean isPlayActionSupported() {
        return (this.mState.getActions() & 516) != 0;
    }

    /* access modifiers changed from: package-private */
    public boolean isPauseActionSupported() {
        return (this.mState.getActions() & 514) != 0;
    }

    /* access modifiers changed from: package-private */
    public boolean isStopActionSupported() {
        return (this.mState.getActions() & 1) != 0;
    }

    /* access modifiers changed from: package-private */
    public boolean isVolumeControlAvailable(MediaRouter.RouteInfo route) {
        return this.mVolumeControlEnabled && route.getVolumeHandling() == 1;
    }

    private static int getLayoutHeight(View view) {
        return view.getLayoutParams().height;
    }

    static void setLayoutHeight(View view, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        view.setLayoutParams(lp);
    }

    private static boolean uriEquals(Uri uri1, Uri uri2) {
        if (uri1 != null && uri1.equals(uri2)) {
            return true;
        }
        if (uri1 == null && uri2 == null) {
            return true;
        }
        return false;
    }

    /* access modifiers changed from: package-private */
    public int getDesiredArtHeight(int originalWidth, int originalHeight) {
        if (originalWidth >= originalHeight) {
            return (int) (((((float) this.mDialogContentWidth) * ((float) originalHeight)) / ((float) originalWidth)) + 0.5f);
        }
        return (int) (((((float) this.mDialogContentWidth) * 9.0f) / 16.0f) + 0.5f);
    }

    /* access modifiers changed from: package-private */
    public void updateArtIconIfNeeded() {
        if (this.mCustomControlView == null && isIconChanged()) {
            FetchArtTask fetchArtTask = this.mFetchArtTask;
            if (fetchArtTask != null) {
                fetchArtTask.cancel(true);
            }
            FetchArtTask fetchArtTask2 = new FetchArtTask();
            this.mFetchArtTask = fetchArtTask2;
            fetchArtTask2.execute(new Void[0]);
        }
    }

    /* access modifiers changed from: package-private */
    public void clearLoadedBitmap() {
        this.mArtIconIsLoaded = false;
        this.mArtIconLoadedBitmap = null;
        this.mArtIconBackgroundColor = 0;
    }

    private boolean isIconChanged() {
        MediaDescriptionCompat mediaDescriptionCompat = this.mDescription;
        Uri newUri = null;
        Bitmap newBitmap = mediaDescriptionCompat == null ? null : mediaDescriptionCompat.getIconBitmap();
        MediaDescriptionCompat mediaDescriptionCompat2 = this.mDescription;
        if (mediaDescriptionCompat2 != null) {
            newUri = mediaDescriptionCompat2.getIconUri();
        }
        FetchArtTask fetchArtTask = this.mFetchArtTask;
        Bitmap oldBitmap = fetchArtTask == null ? this.mArtIconBitmap : fetchArtTask.getIconBitmap();
        FetchArtTask fetchArtTask2 = this.mFetchArtTask;
        Uri oldUri = fetchArtTask2 == null ? this.mArtIconUri : fetchArtTask2.getIconUri();
        if (oldBitmap != newBitmap) {
            return true;
        }
        if (oldBitmap != null || uriEquals(oldUri, newUri)) {
            return false;
        }
        return true;
    }

    private final class MediaRouterCallback extends MediaRouter.Callback {
        MediaRouterCallback() {
        }

        public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo route) {
            MediaRouteControllerDialog.this.update(false);
        }

        public void onRouteChanged(MediaRouter router, MediaRouter.RouteInfo route) {
            MediaRouteControllerDialog.this.update(true);
        }

        public void onRouteVolumeChanged(MediaRouter router, MediaRouter.RouteInfo route) {
            SeekBar volumeSlider = MediaRouteControllerDialog.this.mVolumeSliderMap.get(route);
            int volume = route.getVolume();
            if (MediaRouteControllerDialog.DEBUG) {
                Log.d(MediaRouteControllerDialog.TAG, "onRouteVolumeChanged(), route.getVolume:" + volume);
            }
            if (volumeSlider != null && MediaRouteControllerDialog.this.mRouteInVolumeSliderTouched != route) {
                volumeSlider.setProgress(volume);
            }
        }
    }

    private final class MediaControllerCallback extends MediaControllerCompat.Callback {
        MediaControllerCallback() {
        }

        public void onSessionDestroyed() {
            if (MediaRouteControllerDialog.this.mMediaController != null) {
                MediaRouteControllerDialog.this.mMediaController.unregisterCallback(MediaRouteControllerDialog.this.mControllerCallback);
                MediaRouteControllerDialog.this.mMediaController = null;
            }
        }

        public void onPlaybackStateChanged(PlaybackStateCompat state) {
            MediaRouteControllerDialog.this.mState = state;
            MediaRouteControllerDialog.this.update(false);
        }

        public void onMetadataChanged(MediaMetadataCompat metadata) {
            MediaRouteControllerDialog.this.mDescription = metadata == null ? null : metadata.getDescription();
            MediaRouteControllerDialog.this.updateArtIconIfNeeded();
            MediaRouteControllerDialog.this.update(false);
        }
    }

    private final class ClickListener implements View.OnClickListener {
        ClickListener() {
        }

        public void onClick(View v) {
            int id = v.getId();
            int i = 1;
            if (id == MediaRouteControllerDialog.BUTTON_STOP_RES_ID || id == MediaRouteControllerDialog.BUTTON_DISCONNECT_RES_ID) {
                if (MediaRouteControllerDialog.this.mRoute.isSelected()) {
                    MediaRouter mediaRouter = MediaRouteControllerDialog.this.mRouter;
                    if (id == MediaRouteControllerDialog.BUTTON_STOP_RES_ID) {
                        i = 2;
                    }
                    mediaRouter.unselect(i);
                }
                MediaRouteControllerDialog.this.dismiss();
            } else if (id == C2142R.C2145id.mr_control_playback_ctrl) {
                if (MediaRouteControllerDialog.this.mMediaController != null && MediaRouteControllerDialog.this.mState != null) {
                    if (MediaRouteControllerDialog.this.mState.getState() != 3) {
                        i = 0;
                    }
                    int actionDescResId = 0;
                    if (i != 0 && MediaRouteControllerDialog.this.isPauseActionSupported()) {
                        MediaRouteControllerDialog.this.mMediaController.getTransportControls().pause();
                        actionDescResId = C2142R.string.mr_controller_pause;
                    } else if (i != 0 && MediaRouteControllerDialog.this.isStopActionSupported()) {
                        MediaRouteControllerDialog.this.mMediaController.getTransportControls().stop();
                        actionDescResId = C2142R.string.mr_controller_stop;
                    } else if (i == 0 && MediaRouteControllerDialog.this.isPlayActionSupported()) {
                        MediaRouteControllerDialog.this.mMediaController.getTransportControls().play();
                        actionDescResId = C2142R.string.mr_controller_play;
                    }
                    if (MediaRouteControllerDialog.this.mAccessibilityManager != null && MediaRouteControllerDialog.this.mAccessibilityManager.isEnabled() && actionDescResId != 0) {
                        AccessibilityEvent event = AccessibilityEvent.obtain(16384);
                        event.setPackageName(MediaRouteControllerDialog.this.mContext.getPackageName());
                        event.setClassName(getClass().getName());
                        event.getText().add(MediaRouteControllerDialog.this.mContext.getString(actionDescResId));
                        MediaRouteControllerDialog.this.mAccessibilityManager.sendAccessibilityEvent(event);
                    }
                }
            } else if (id == C2142R.C2145id.mr_close) {
                MediaRouteControllerDialog.this.dismiss();
            }
        }
    }

    private class VolumeChangeListener implements SeekBar.OnSeekBarChangeListener {
        private final Runnable mStopTrackingTouch = new Runnable() {
            public void run() {
                if (MediaRouteControllerDialog.this.mRouteInVolumeSliderTouched != null) {
                    MediaRouteControllerDialog.this.mRouteInVolumeSliderTouched = null;
                    if (MediaRouteControllerDialog.this.mHasPendingUpdate) {
                        MediaRouteControllerDialog.this.update(MediaRouteControllerDialog.this.mPendingUpdateAnimationNeeded);
                    }
                }
            }
        };

        VolumeChangeListener() {
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            if (MediaRouteControllerDialog.this.mRouteInVolumeSliderTouched != null) {
                MediaRouteControllerDialog.this.mVolumeSlider.removeCallbacks(this.mStopTrackingTouch);
            }
            MediaRouteControllerDialog.this.mRouteInVolumeSliderTouched = (MediaRouter.RouteInfo) seekBar.getTag();
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            MediaRouteControllerDialog.this.mVolumeSlider.postDelayed(this.mStopTrackingTouch, 500);
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                MediaRouter.RouteInfo route = (MediaRouter.RouteInfo) seekBar.getTag();
                if (MediaRouteControllerDialog.DEBUG) {
                    Log.d(MediaRouteControllerDialog.TAG, "onProgressChanged(): calling MediaRouter.RouteInfo.requestSetVolume(" + progress + ")");
                }
                route.requestSetVolume(progress);
            }
        }
    }

    private class VolumeGroupAdapter extends ArrayAdapter<MediaRouter.RouteInfo> {
        final float mDisabledAlpha;

        public VolumeGroupAdapter(Context context, List<MediaRouter.RouteInfo> objects) {
            super(context, 0, objects);
            this.mDisabledAlpha = MediaRouterThemeHelper.getDisabledAlpha(context);
        }

        public boolean isEnabled(int position) {
            return false;
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            View v = convertView;
            int i = 0;
            if (v == null) {
                v = LayoutInflater.from(parent.getContext()).inflate(C2142R.C2147layout.mr_controller_volume_item, parent, false);
            } else {
                MediaRouteControllerDialog.this.updateVolumeGroupItemHeight(v);
            }
            MediaRouter.RouteInfo route = (MediaRouter.RouteInfo) getItem(position);
            if (route != null) {
                boolean isEnabled = route.isEnabled();
                TextView routeName = (TextView) v.findViewById(C2142R.C2145id.mr_name);
                routeName.setEnabled(isEnabled);
                routeName.setText(route.getName());
                MediaRouteVolumeSlider volumeSlider = (MediaRouteVolumeSlider) v.findViewById(C2142R.C2145id.mr_volume_slider);
                MediaRouterThemeHelper.setVolumeSliderColor(parent.getContext(), volumeSlider, MediaRouteControllerDialog.this.mVolumeGroupList);
                volumeSlider.setTag(route);
                MediaRouteControllerDialog.this.mVolumeSliderMap.put(route, volumeSlider);
                volumeSlider.setHideThumb(!isEnabled);
                volumeSlider.setEnabled(isEnabled);
                if (isEnabled) {
                    if (MediaRouteControllerDialog.this.isVolumeControlAvailable(route)) {
                        volumeSlider.setMax(route.getVolumeMax());
                        volumeSlider.setProgress(route.getVolume());
                        volumeSlider.setOnSeekBarChangeListener(MediaRouteControllerDialog.this.mVolumeChangeListener);
                    } else {
                        volumeSlider.setMax(100);
                        volumeSlider.setProgress(100);
                        volumeSlider.setEnabled(false);
                    }
                }
                ((ImageView) v.findViewById(C2142R.C2145id.mr_volume_item_icon)).setAlpha(isEnabled ? 255 : (int) (this.mDisabledAlpha * 255.0f));
                LinearLayout container = (LinearLayout) v.findViewById(C2142R.C2145id.volume_item_container);
                if (MediaRouteControllerDialog.this.mGroupMemberRoutesAnimatingWithBitmap.contains(route)) {
                    i = 4;
                }
                container.setVisibility(i);
                if (MediaRouteControllerDialog.this.mGroupMemberRoutesAdded != null && MediaRouteControllerDialog.this.mGroupMemberRoutesAdded.contains(route)) {
                    Animation alphaAnim = new AlphaAnimation(0.0f, 0.0f);
                    alphaAnim.setDuration(0);
                    alphaAnim.setFillEnabled(true);
                    alphaAnim.setFillAfter(true);
                    v.clearAnimation();
                    v.startAnimation(alphaAnim);
                }
            }
            return v;
        }
    }

    private class FetchArtTask extends AsyncTask<Void, Void, Bitmap> {
        private static final long SHOW_ANIM_TIME_THRESHOLD_MILLIS = 120;
        private int mBackgroundColor;
        private final Bitmap mIconBitmap;
        private final Uri mIconUri;
        private long mStartTimeMillis;

        FetchArtTask() {
            Uri uri = null;
            Bitmap bitmap = MediaRouteControllerDialog.this.mDescription == null ? null : MediaRouteControllerDialog.this.mDescription.getIconBitmap();
            if (MediaRouteControllerDialog.isBitmapRecycled(bitmap)) {
                Log.w(MediaRouteControllerDialog.TAG, "Can't fetch the given art bitmap because it's already recycled.");
                bitmap = null;
            }
            this.mIconBitmap = bitmap;
            this.mIconUri = MediaRouteControllerDialog.this.mDescription != null ? MediaRouteControllerDialog.this.mDescription.getIconUri() : uri;
        }

        public Bitmap getIconBitmap() {
            return this.mIconBitmap;
        }

        public Uri getIconUri() {
            return this.mIconUri;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            this.mStartTimeMillis = SystemClock.uptimeMillis();
            MediaRouteControllerDialog.this.clearLoadedBitmap();
        }

        /* access modifiers changed from: protected */
        public Bitmap doInBackground(Void... arg) {
            Bitmap art = null;
            int i = 0;
            if (this.mIconBitmap != null) {
                art = this.mIconBitmap;
            } else {
                Uri uri = this.mIconUri;
                if (uri != null) {
                    InputStream stream = null;
                    try {
                        InputStream openInputStreamByScheme = openInputStreamByScheme(uri);
                        stream = openInputStreamByScheme;
                        if (openInputStreamByScheme == null) {
                            Log.w(MediaRouteControllerDialog.TAG, "Unable to open: " + this.mIconUri);
                            if (stream != null) {
                                try {
                                    stream.close();
                                } catch (IOException e) {
                                }
                            }
                            return null;
                        }
                        BitmapFactory.Options options = new BitmapFactory.Options();
                        options.inJustDecodeBounds = true;
                        BitmapFactory.decodeStream(stream, (Rect) null, options);
                        if (options.outWidth == 0 || options.outHeight == 0) {
                            if (stream != null) {
                                try {
                                    stream.close();
                                } catch (IOException e2) {
                                }
                            }
                            return null;
                        }
                        try {
                            stream.reset();
                        } catch (IOException e3) {
                            stream.close();
                            InputStream openInputStreamByScheme2 = openInputStreamByScheme(this.mIconUri);
                            stream = openInputStreamByScheme2;
                            if (openInputStreamByScheme2 == null) {
                                Log.w(MediaRouteControllerDialog.TAG, "Unable to open: " + this.mIconUri);
                                if (stream != null) {
                                    try {
                                        stream.close();
                                    } catch (IOException e4) {
                                    }
                                }
                                return null;
                            }
                        }
                        options.inJustDecodeBounds = false;
                        options.inSampleSize = Math.max(1, Integer.highestOneBit(options.outHeight / MediaRouteControllerDialog.this.getDesiredArtHeight(options.outWidth, options.outHeight)));
                        if (isCancelled()) {
                            if (stream != null) {
                                try {
                                    stream.close();
                                } catch (IOException e5) {
                                }
                            }
                            return null;
                        }
                        art = BitmapFactory.decodeStream(stream, (Rect) null, options);
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (IOException e6) {
                            }
                        }
                    } catch (IOException e7) {
                        Log.w(MediaRouteControllerDialog.TAG, "Unable to open: " + this.mIconUri, e7);
                        if (stream != null) {
                            stream.close();
                        }
                    } catch (Throwable th) {
                        if (stream != null) {
                            try {
                                stream.close();
                            } catch (IOException e8) {
                            }
                        }
                        throw th;
                    }
                }
            }
            if (MediaRouteControllerDialog.isBitmapRecycled(art)) {
                Log.w(MediaRouteControllerDialog.TAG, "Can't use recycled bitmap: " + art);
                return null;
            }
            if (art != null && art.getWidth() < art.getHeight()) {
                Palette palette = new Palette.Builder(art).maximumColorCount(1).generate();
                if (!palette.getSwatches().isEmpty()) {
                    i = palette.getSwatches().get(0).getRgb();
                }
                this.mBackgroundColor = i;
            }
            return art;
        }

        /* access modifiers changed from: protected */
        public void onPostExecute(Bitmap art) {
            MediaRouteControllerDialog.this.mFetchArtTask = null;
            if (!ObjectsCompat.equals(MediaRouteControllerDialog.this.mArtIconBitmap, this.mIconBitmap) || !ObjectsCompat.equals(MediaRouteControllerDialog.this.mArtIconUri, this.mIconUri)) {
                MediaRouteControllerDialog.this.mArtIconBitmap = this.mIconBitmap;
                MediaRouteControllerDialog.this.mArtIconLoadedBitmap = art;
                MediaRouteControllerDialog.this.mArtIconUri = this.mIconUri;
                MediaRouteControllerDialog.this.mArtIconBackgroundColor = this.mBackgroundColor;
                boolean z = true;
                MediaRouteControllerDialog.this.mArtIconIsLoaded = true;
                MediaRouteControllerDialog mediaRouteControllerDialog = MediaRouteControllerDialog.this;
                if (SystemClock.uptimeMillis() - this.mStartTimeMillis <= SHOW_ANIM_TIME_THRESHOLD_MILLIS) {
                    z = false;
                }
                mediaRouteControllerDialog.update(z);
            }
        }

        private InputStream openInputStreamByScheme(Uri uri) throws IOException {
            InputStream stream;
            String scheme = uri.getScheme().toLowerCase();
            if (UriUtil.QUALIFIED_RESOURCE_SCHEME.equals(scheme) || "content".equals(scheme) || UriUtil.LOCAL_FILE_SCHEME.equals(scheme)) {
                stream = MediaRouteControllerDialog.this.mContext.getContentResolver().openInputStream(uri);
            } else {
                URLConnection conn = new URL(uri.toString()).openConnection();
                conn.setConnectTimeout(MediaRouteControllerDialog.CONNECTION_TIMEOUT_MILLIS);
                conn.setReadTimeout(MediaRouteControllerDialog.CONNECTION_TIMEOUT_MILLIS);
                stream = conn.getInputStream();
            }
            if (stream == null) {
                return null;
            }
            return new BufferedInputStream(stream);
        }
    }
}
