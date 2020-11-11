package androidx.mediarouter.app;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.os.SystemClock;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.support.p000v4.media.MediaDescriptionCompat;
import android.support.p000v4.media.MediaMetadataCompat;
import android.support.p000v4.media.session.MediaControllerCompat;
import android.support.p000v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatDialog;
import androidx.core.util.ObjectsCompat;
import androidx.mediarouter.C2142R;
import androidx.mediarouter.media.MediaRouteProvider;
import androidx.mediarouter.media.MediaRouteSelector;
import androidx.mediarouter.media.MediaRouter;
import androidx.palette.graphics.Palette;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.facebook.common.util.UriUtil;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MediaRouteDynamicControllerDialog extends AppCompatDialog {
    private static final int BLUR_RADIUS = 10;
    private static final int COLOR_WHITE_ON_DARK_BACKGROUND = -1;
    private static final int CONNECTION_TIMEOUT_MS = 30000;
    static final boolean DEBUG = Log.isLoggable(TAG, 3);
    private static final int MIN_UNMUTED_VOLUME = 1;
    private static final int MSG_UPDATE_ROUTES_VIEW = 1;
    private static final int MSG_UPDATE_ROUTE_VOLUME_BY_USER = 2;
    private static final int MUTED_VOLUME = 0;
    private static final String TAG = "MediaRouteCtrlDialog";
    private static final int UPDATE_ROUTES_VIEW_DELAY_MS = 300;
    private static final int UPDATE_VOLUME_DELAY_MS = 500;
    RecyclerAdapter mAdapter;
    int mArtIconBackgroundColor;
    Bitmap mArtIconBitmap;
    boolean mArtIconIsLoaded;
    Bitmap mArtIconLoadedBitmap;
    Uri mArtIconUri;
    ImageView mArtView;
    private boolean mAttachedToWindow;
    private final MediaRouterCallback mCallback;
    private ImageButton mCloseButton;
    Context mContext;
    MediaControllerCallback mControllerCallback;
    private boolean mCreated;
    MediaDescriptionCompat mDescription;
    FetchArtTask mFetchArtTask;
    final List<MediaRouter.RouteInfo> mGroupableRoutes;
    final Handler mHandler;
    boolean mIsAnimatingVolumeSliderLayout;
    boolean mIsSelectingRoute;
    private long mLastUpdateTime;
    MediaControllerCompat mMediaController;
    final List<MediaRouter.RouteInfo> mMemberRoutes;
    private ImageView mMetadataBackground;
    private View mMetadataBlackScrim;
    RecyclerView mRecyclerView;
    MediaRouter.RouteInfo mRouteForVolumeUpdatingByUser;
    final MediaRouter mRouter;
    MediaRouter.RouteInfo mSelectedRoute;
    private MediaRouteSelector mSelector;
    private Button mStopCastingButton;
    private TextView mSubtitleView;
    private String mTitlePlaceholder;
    private TextView mTitleView;
    final List<MediaRouter.RouteInfo> mTransferableRoutes;
    final List<MediaRouter.RouteInfo> mUngroupableRoutes;
    Map<String, Integer> mUnmutedVolumeMap;
    private boolean mUpdateMetadataViewsDeferred;
    private boolean mUpdateRoutesViewDeferred;
    VolumeChangeListener mVolumeChangeListener;
    Map<String, MediaRouteVolumeSliderHolder> mVolumeSliderHolderMap;

    public MediaRouteDynamicControllerDialog(Context context) {
        this(context, 0);
    }

    /* JADX WARNING: Illegal instructions before constructor call */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public MediaRouteDynamicControllerDialog(android.content.Context r3, int r4) {
        /*
            r2 = this;
            r0 = 0
            android.content.Context r0 = androidx.mediarouter.app.MediaRouterThemeHelper.createThemedDialogContext(r3, r4, r0)
            r3 = r0
            int r1 = androidx.mediarouter.app.MediaRouterThemeHelper.createThemedDialogStyle(r3)
            r2.<init>(r0, r1)
            androidx.mediarouter.media.MediaRouteSelector r0 = androidx.mediarouter.media.MediaRouteSelector.EMPTY
            r2.mSelector = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2.mMemberRoutes = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2.mGroupableRoutes = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2.mTransferableRoutes = r0
            java.util.ArrayList r0 = new java.util.ArrayList
            r0.<init>()
            r2.mUngroupableRoutes = r0
            androidx.mediarouter.app.MediaRouteDynamicControllerDialog$1 r0 = new androidx.mediarouter.app.MediaRouteDynamicControllerDialog$1
            r0.<init>()
            r2.mHandler = r0
            android.content.Context r0 = r2.getContext()
            r2.mContext = r0
            androidx.mediarouter.media.MediaRouter r0 = androidx.mediarouter.media.MediaRouter.getInstance(r0)
            r2.mRouter = r0
            androidx.mediarouter.app.MediaRouteDynamicControllerDialog$MediaRouterCallback r1 = new androidx.mediarouter.app.MediaRouteDynamicControllerDialog$MediaRouterCallback
            r1.<init>()
            r2.mCallback = r1
            androidx.mediarouter.media.MediaRouter$RouteInfo r1 = r0.getSelectedRoute()
            r2.mSelectedRoute = r1
            androidx.mediarouter.app.MediaRouteDynamicControllerDialog$MediaControllerCallback r1 = new androidx.mediarouter.app.MediaRouteDynamicControllerDialog$MediaControllerCallback
            r1.<init>()
            r2.mControllerCallback = r1
            android.support.v4.media.session.MediaSessionCompat$Token r0 = r0.getMediaSessionToken()
            r2.setMediaSession(r0)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.mediarouter.app.MediaRouteDynamicControllerDialog.<init>(android.content.Context, int):void");
    }

    private void setMediaSession(MediaSessionCompat.Token sessionToken) {
        MediaMetadataCompat metadata;
        MediaControllerCompat mediaControllerCompat = this.mMediaController;
        MediaDescriptionCompat mediaDescriptionCompat = null;
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
            if (metadata != null) {
                mediaDescriptionCompat = metadata.getDescription();
            }
            this.mDescription = mediaDescriptionCompat;
            reloadIconIfNeeded();
            updateMetadataViews();
        }
    }

    public MediaSessionCompat.Token getMediaSession() {
        MediaControllerCompat mediaControllerCompat = this.mMediaController;
        if (mediaControllerCompat == null) {
            return null;
        }
        return mediaControllerCompat.getSessionToken();
    }

    public MediaRouteSelector getRouteSelector() {
        return this.mSelector;
    }

    public void setRouteSelector(MediaRouteSelector selector) {
        if (selector == null) {
            throw new IllegalArgumentException("selector must not be null");
        } else if (!this.mSelector.equals(selector)) {
            this.mSelector = selector;
            if (this.mAttachedToWindow) {
                this.mRouter.removeCallback(this.mCallback);
                this.mRouter.addCallback(selector, this.mCallback, 1);
                updateRoutes();
            }
        }
    }

    public void onFilterRoutes(List<MediaRouter.RouteInfo> routes) {
        for (int i = routes.size() - 1; i >= 0; i--) {
            if (!onFilterRoute(routes.get(i))) {
                routes.remove(i);
            }
        }
    }

    public boolean onFilterRoute(MediaRouter.RouteInfo route) {
        return !route.isDefaultOrBluetooth() && route.isEnabled() && route.matchesSelector(this.mSelector) && this.mSelectedRoute != route;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(C2142R.C2147layout.mr_cast_dialog);
        MediaRouterThemeHelper.setDialogBackgroundColor(this.mContext, this);
        ImageButton imageButton = (ImageButton) findViewById(C2142R.C2145id.mr_cast_close_button);
        this.mCloseButton = imageButton;
        imageButton.setColorFilter(-1);
        this.mCloseButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                MediaRouteDynamicControllerDialog.this.dismiss();
            }
        });
        Button button = (Button) findViewById(C2142R.C2145id.mr_cast_stop_button);
        this.mStopCastingButton = button;
        button.setTextColor(-1);
        this.mStopCastingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (MediaRouteDynamicControllerDialog.this.mSelectedRoute.isSelected()) {
                    MediaRouteDynamicControllerDialog.this.mRouter.unselect(2);
                }
                MediaRouteDynamicControllerDialog.this.dismiss();
            }
        });
        this.mAdapter = new RecyclerAdapter();
        RecyclerView recyclerView = (RecyclerView) findViewById(C2142R.C2145id.mr_cast_list);
        this.mRecyclerView = recyclerView;
        recyclerView.setAdapter(this.mAdapter);
        this.mRecyclerView.setLayoutManager(new LinearLayoutManager(this.mContext));
        this.mVolumeChangeListener = new VolumeChangeListener();
        this.mVolumeSliderHolderMap = new HashMap();
        this.mUnmutedVolumeMap = new HashMap();
        this.mMetadataBackground = (ImageView) findViewById(C2142R.C2145id.mr_cast_meta_background);
        this.mMetadataBlackScrim = findViewById(C2142R.C2145id.mr_cast_meta_black_scrim);
        this.mArtView = (ImageView) findViewById(C2142R.C2145id.mr_cast_meta_art);
        TextView textView = (TextView) findViewById(C2142R.C2145id.mr_cast_meta_title);
        this.mTitleView = textView;
        textView.setTextColor(-1);
        TextView textView2 = (TextView) findViewById(C2142R.C2145id.mr_cast_meta_subtitle);
        this.mSubtitleView = textView2;
        textView2.setTextColor(-1);
        this.mTitlePlaceholder = this.mContext.getResources().getString(C2142R.string.mr_cast_dialog_title_view_placeholder);
        this.mCreated = true;
        updateLayout();
    }

    /* access modifiers changed from: package-private */
    public void updateLayout() {
        getWindow().setLayout(MediaRouteDialogHelper.getDialogWidthForDynamicGroup(this.mContext), MediaRouteDialogHelper.getDialogHeight(this.mContext));
        this.mArtIconBitmap = null;
        this.mArtIconUri = null;
        reloadIconIfNeeded();
        updateMetadataViews();
        updateRoutesView();
    }

    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.mAttachedToWindow = true;
        this.mRouter.addCallback(this.mSelector, this.mCallback, 1);
        updateRoutes();
        setMediaSession(this.mRouter.getMediaSessionToken());
    }

    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.mAttachedToWindow = false;
        this.mRouter.removeCallback(this.mCallback);
        this.mHandler.removeCallbacksAndMessages((Object) null);
        setMediaSession((MediaSessionCompat.Token) null);
    }

    static boolean isBitmapRecycled(Bitmap bitmap) {
        return bitmap != null && bitmap.isRecycled();
    }

    /* access modifiers changed from: package-private */
    public void reloadIconIfNeeded() {
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
        if (oldBitmap != newBitmap || (oldBitmap == null && !ObjectsCompat.equals(oldUri, newUri))) {
            FetchArtTask fetchArtTask3 = this.mFetchArtTask;
            if (fetchArtTask3 != null) {
                fetchArtTask3.cancel(true);
            }
            FetchArtTask fetchArtTask4 = new FetchArtTask();
            this.mFetchArtTask = fetchArtTask4;
            fetchArtTask4.execute(new Void[0]);
        }
    }

    /* access modifiers changed from: package-private */
    public void clearLoadedBitmap() {
        this.mArtIconIsLoaded = false;
        this.mArtIconLoadedBitmap = null;
        this.mArtIconBackgroundColor = 0;
    }

    private boolean shouldDeferUpdateViews() {
        if (this.mRouteForVolumeUpdatingByUser != null || this.mIsSelectingRoute || this.mIsAnimatingVolumeSliderLayout) {
            return true;
        }
        return !this.mCreated;
    }

    /* access modifiers changed from: package-private */
    public void updateViewsIfNeeded() {
        if (this.mUpdateRoutesViewDeferred) {
            updateRoutesView();
        }
        if (this.mUpdateMetadataViewsDeferred) {
            updateMetadataViews();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateMetadataViews() {
        if (shouldDeferUpdateViews()) {
            this.mUpdateMetadataViewsDeferred = true;
            return;
        }
        this.mUpdateMetadataViewsDeferred = false;
        if (!this.mSelectedRoute.isSelected() || this.mSelectedRoute.isDefaultOrBluetooth()) {
            dismiss();
        }
        CharSequence subtitle = null;
        if (!this.mArtIconIsLoaded || isBitmapRecycled(this.mArtIconLoadedBitmap) || this.mArtIconLoadedBitmap == null) {
            if (isBitmapRecycled(this.mArtIconLoadedBitmap)) {
                Log.w(TAG, "Can't set artwork image with recycled bitmap: " + this.mArtIconLoadedBitmap);
            }
            this.mArtView.setVisibility(8);
            this.mMetadataBlackScrim.setVisibility(8);
            this.mMetadataBackground.setImageBitmap((Bitmap) null);
        } else {
            this.mArtView.setVisibility(0);
            this.mArtView.setImageBitmap(this.mArtIconLoadedBitmap);
            this.mArtView.setBackgroundColor(this.mArtIconBackgroundColor);
            this.mMetadataBlackScrim.setVisibility(0);
            if (Build.VERSION.SDK_INT >= 17) {
                this.mMetadataBackground.setImageBitmap(blurBitmap(this.mArtIconLoadedBitmap, 10.0f, this.mContext));
            } else {
                this.mMetadataBackground.setImageBitmap(Bitmap.createBitmap(this.mArtIconLoadedBitmap));
            }
        }
        clearLoadedBitmap();
        MediaDescriptionCompat mediaDescriptionCompat = this.mDescription;
        CharSequence title = mediaDescriptionCompat == null ? null : mediaDescriptionCompat.getTitle();
        boolean hasTitle = !TextUtils.isEmpty(title);
        MediaDescriptionCompat mediaDescriptionCompat2 = this.mDescription;
        if (mediaDescriptionCompat2 != null) {
            subtitle = mediaDescriptionCompat2.getSubtitle();
        }
        boolean hasSubtitle = true ^ TextUtils.isEmpty(subtitle);
        if (hasTitle) {
            this.mTitleView.setText(title);
        } else {
            this.mTitleView.setText(this.mTitlePlaceholder);
        }
        if (hasSubtitle) {
            this.mSubtitleView.setText(subtitle);
            this.mSubtitleView.setVisibility(0);
            return;
        }
        this.mSubtitleView.setVisibility(8);
    }

    static void setLayoutHeight(View view, int height) {
        ViewGroup.LayoutParams lp = view.getLayoutParams();
        lp.height = height;
        view.setLayoutParams(lp);
    }

    private class VolumeChangeListener implements SeekBar.OnSeekBarChangeListener {
        VolumeChangeListener() {
        }

        public void onStartTrackingTouch(SeekBar seekBar) {
            if (MediaRouteDynamicControllerDialog.this.mRouteForVolumeUpdatingByUser != null) {
                MediaRouteDynamicControllerDialog.this.mHandler.removeMessages(2);
            }
            MediaRouteDynamicControllerDialog.this.mRouteForVolumeUpdatingByUser = (MediaRouter.RouteInfo) seekBar.getTag();
        }

        public void onStopTrackingTouch(SeekBar seekBar) {
            MediaRouteDynamicControllerDialog.this.mHandler.sendEmptyMessageDelayed(2, 500);
        }

        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
            if (fromUser) {
                MediaRouter.RouteInfo route = (MediaRouter.RouteInfo) seekBar.getTag();
                MediaRouteVolumeSliderHolder holder = MediaRouteDynamicControllerDialog.this.mVolumeSliderHolderMap.get(route.getId());
                if (holder != null) {
                    holder.setMute(progress == 0);
                }
                route.requestSetVolume(progress);
            }
        }
    }

    /* access modifiers changed from: package-private */
    public List<MediaRouter.RouteInfo> getCurrentGroupableRoutes() {
        List<MediaRouter.RouteInfo> groupableRoutes = new ArrayList<>();
        if (this.mSelectedRoute.getDynamicGroupState() != null) {
            for (MediaRouter.RouteInfo route : this.mSelectedRoute.getProvider().getRoutes()) {
                MediaRouter.RouteInfo.DynamicGroupState state = route.getDynamicGroupState();
                if (state != null && state.isGroupable()) {
                    groupableRoutes.add(route);
                }
            }
        }
        return groupableRoutes;
    }

    /* access modifiers changed from: package-private */
    public void updateRoutesView() {
        if (!this.mAttachedToWindow) {
            return;
        }
        if (SystemClock.uptimeMillis() - this.mLastUpdateTime < 300) {
            this.mHandler.removeMessages(1);
            this.mHandler.sendEmptyMessageAtTime(1, this.mLastUpdateTime + 300);
        } else if (shouldDeferUpdateViews()) {
            this.mUpdateRoutesViewDeferred = true;
        } else {
            this.mUpdateRoutesViewDeferred = false;
            if (!this.mSelectedRoute.isSelected() || this.mSelectedRoute.isDefaultOrBluetooth()) {
                dismiss();
            }
            this.mLastUpdateTime = SystemClock.uptimeMillis();
            this.mAdapter.notifyAdapterDataSetChanged();
        }
    }

    /* access modifiers changed from: package-private */
    public void updateRoutes() {
        this.mMemberRoutes.clear();
        this.mGroupableRoutes.clear();
        this.mTransferableRoutes.clear();
        this.mMemberRoutes.addAll(this.mSelectedRoute.getMemberRoutes());
        if (this.mSelectedRoute.getDynamicGroupState() != null) {
            for (MediaRouter.RouteInfo route : this.mSelectedRoute.getProvider().getRoutes()) {
                MediaRouter.RouteInfo.DynamicGroupState state = route.getDynamicGroupState();
                if (state != null) {
                    if (state.isGroupable()) {
                        this.mGroupableRoutes.add(route);
                    }
                    if (state.isTransferable()) {
                        this.mTransferableRoutes.add(route);
                    }
                }
            }
        }
        onFilterRoutes(this.mGroupableRoutes);
        onFilterRoutes(this.mTransferableRoutes);
        Collections.sort(this.mMemberRoutes, RouteComparator.sInstance);
        Collections.sort(this.mGroupableRoutes, RouteComparator.sInstance);
        Collections.sort(this.mTransferableRoutes, RouteComparator.sInstance);
        this.mAdapter.updateItems();
    }

    private static Bitmap blurBitmap(Bitmap bitmap, float radius, Context context) {
        RenderScript rs = RenderScript.create(context);
        Allocation allocation = Allocation.createFromBitmap(rs, bitmap);
        Allocation blurAllocation = Allocation.createTyped(rs, allocation.getType());
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        blurScript.setRadius(radius);
        blurScript.setInput(allocation);
        blurScript.forEach(blurAllocation);
        blurAllocation.copyTo(bitmap);
        allocation.destroy();
        blurAllocation.destroy();
        blurScript.destroy();
        rs.destroy();
        return bitmap;
    }

    private abstract class MediaRouteVolumeSliderHolder extends RecyclerView.ViewHolder {
        final ImageButton mMuteButton;
        MediaRouter.RouteInfo mRoute;
        final MediaRouteVolumeSlider mVolumeSlider;

        MediaRouteVolumeSliderHolder(View itemView, ImageButton muteButton, MediaRouteVolumeSlider volumeSlider) {
            super(itemView);
            this.mMuteButton = muteButton;
            this.mVolumeSlider = volumeSlider;
            muteButton.setImageDrawable(MediaRouterThemeHelper.getMuteButtonDrawableIcon(MediaRouteDynamicControllerDialog.this.mContext));
            MediaRouterThemeHelper.setVolumeSliderColor(MediaRouteDynamicControllerDialog.this.mContext, volumeSlider);
        }

        /* access modifiers changed from: package-private */
        public void bindRouteVolumeSliderHolder(MediaRouter.RouteInfo route) {
            this.mRoute = route;
            int volume = route.getVolume();
            this.mMuteButton.setActivated(volume == 0);
            this.mMuteButton.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    if (MediaRouteDynamicControllerDialog.this.mRouteForVolumeUpdatingByUser != null) {
                        MediaRouteDynamicControllerDialog.this.mHandler.removeMessages(2);
                    }
                    MediaRouteDynamicControllerDialog.this.mRouteForVolumeUpdatingByUser = MediaRouteVolumeSliderHolder.this.mRoute;
                    boolean mute = !v.isActivated();
                    int volume = mute ? 0 : MediaRouteVolumeSliderHolder.this.getUnmutedVolume();
                    MediaRouteVolumeSliderHolder.this.setMute(mute);
                    MediaRouteVolumeSliderHolder.this.mVolumeSlider.setProgress(volume);
                    MediaRouteVolumeSliderHolder.this.mRoute.requestSetVolume(volume);
                    MediaRouteDynamicControllerDialog.this.mHandler.sendEmptyMessageDelayed(2, 500);
                }
            });
            this.mVolumeSlider.setTag(this.mRoute);
            this.mVolumeSlider.setMax(route.getVolumeMax());
            this.mVolumeSlider.setProgress(volume);
            this.mVolumeSlider.setOnSeekBarChangeListener(MediaRouteDynamicControllerDialog.this.mVolumeChangeListener);
        }

        /* access modifiers changed from: package-private */
        public void updateVolume() {
            int volume = this.mRoute.getVolume();
            setMute(volume == 0);
            this.mVolumeSlider.setProgress(volume);
        }

        /* access modifiers changed from: package-private */
        public void setMute(boolean mute) {
            if (this.mMuteButton.isActivated() != mute) {
                this.mMuteButton.setActivated(mute);
                if (mute) {
                    MediaRouteDynamicControllerDialog.this.mUnmutedVolumeMap.put(this.mRoute.getId(), Integer.valueOf(this.mVolumeSlider.getProgress()));
                } else {
                    MediaRouteDynamicControllerDialog.this.mUnmutedVolumeMap.remove(this.mRoute.getId());
                }
            }
        }

        /* access modifiers changed from: package-private */
        public int getUnmutedVolume() {
            Integer beforeMuteVolume = MediaRouteDynamicControllerDialog.this.mUnmutedVolumeMap.get(this.mRoute.getId());
            if (beforeMuteVolume == null) {
                return 1;
            }
            return Math.max(1, beforeMuteVolume.intValue());
        }
    }

    private final class RecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private static final int ITEM_TYPE_GROUP = 4;
        private static final int ITEM_TYPE_GROUP_VOLUME = 1;
        private static final int ITEM_TYPE_HEADER = 2;
        private static final int ITEM_TYPE_ROUTE = 3;
        private final Interpolator mAccelerateDecelerateInterpolator;
        private final Drawable mDefaultIcon;
        private Item mGroupVolumeItem;
        private final LayoutInflater mInflater;
        private final ArrayList<Item> mItems = new ArrayList<>();
        private final int mLayoutAnimationDurationMs;
        private final Drawable mSpeakerGroupIcon;
        private final Drawable mSpeakerIcon;
        private final Drawable mTvIcon;

        RecyclerAdapter() {
            this.mInflater = LayoutInflater.from(MediaRouteDynamicControllerDialog.this.mContext);
            this.mDefaultIcon = MediaRouterThemeHelper.getDefaultDrawableIcon(MediaRouteDynamicControllerDialog.this.mContext);
            this.mTvIcon = MediaRouterThemeHelper.getTvDrawableIcon(MediaRouteDynamicControllerDialog.this.mContext);
            this.mSpeakerIcon = MediaRouterThemeHelper.getSpeakerDrawableIcon(MediaRouteDynamicControllerDialog.this.mContext);
            this.mSpeakerGroupIcon = MediaRouterThemeHelper.getSpeakerGroupDrawableIcon(MediaRouteDynamicControllerDialog.this.mContext);
            this.mLayoutAnimationDurationMs = MediaRouteDynamicControllerDialog.this.mContext.getResources().getInteger(C2142R.integer.mr_cast_volume_slider_layout_animation_duration_ms);
            this.mAccelerateDecelerateInterpolator = new AccelerateDecelerateInterpolator();
            updateItems();
        }

        /* access modifiers changed from: package-private */
        public boolean isGroupVolumeNeeded() {
            return MediaRouteDynamicControllerDialog.this.mSelectedRoute.getMemberRoutes().size() > 1;
        }

        /* access modifiers changed from: package-private */
        public void animateLayoutHeight(final View view, int targetHeight) {
            final int startValue = view.getLayoutParams().height;
            final int endValue = targetHeight;
            Animation anim = new Animation() {
                /* access modifiers changed from: protected */
                public void applyTransformation(float interpolatedTime, Transformation t) {
                    int i = endValue;
                    int i2 = startValue;
                    MediaRouteDynamicControllerDialog.setLayoutHeight(view, i2 + ((int) (((float) (i - i2)) * interpolatedTime)));
                }
            };
            anim.setAnimationListener(new Animation.AnimationListener() {
                public void onAnimationRepeat(Animation animation) {
                }

                public void onAnimationStart(Animation animation) {
                    MediaRouteDynamicControllerDialog.this.mIsAnimatingVolumeSliderLayout = true;
                }

                public void onAnimationEnd(Animation animation) {
                    MediaRouteDynamicControllerDialog.this.mIsAnimatingVolumeSliderLayout = false;
                    MediaRouteDynamicControllerDialog.this.updateViewsIfNeeded();
                }
            });
            anim.setDuration((long) this.mLayoutAnimationDurationMs);
            anim.setInterpolator(this.mAccelerateDecelerateInterpolator);
            view.startAnimation(anim);
        }

        /* access modifiers changed from: package-private */
        public void mayUpdateGroupVolume(MediaRouter.RouteInfo route, boolean selected) {
            List<MediaRouter.RouteInfo> members = MediaRouteDynamicControllerDialog.this.mSelectedRoute.getMemberRoutes();
            boolean shouldShow = true;
            int memberCount = Math.max(1, members.size());
            int i = -1;
            if (route.isGroup()) {
                for (MediaRouter.RouteInfo changedRoute : route.getMemberRoutes()) {
                    if (members.contains(changedRoute) != selected) {
                        memberCount += selected ? 1 : -1;
                    }
                }
            } else {
                if (selected) {
                    i = 1;
                }
                memberCount += i;
            }
            boolean wasShown = isGroupVolumeNeeded();
            int i2 = 0;
            if (memberCount < 2) {
                shouldShow = false;
            }
            if (wasShown != shouldShow) {
                RecyclerView.ViewHolder viewHolder = MediaRouteDynamicControllerDialog.this.mRecyclerView.findViewHolderForAdapterPosition(0);
                if (viewHolder instanceof GroupVolumeViewHolder) {
                    GroupVolumeViewHolder groupVolumeHolder = (GroupVolumeViewHolder) viewHolder;
                    View view = groupVolumeHolder.itemView;
                    if (shouldShow) {
                        i2 = groupVolumeHolder.getExpandedHeight();
                    }
                    animateLayoutHeight(view, i2);
                }
            }
        }

        /* access modifiers changed from: package-private */
        public void updateItems() {
            this.mItems.clear();
            this.mGroupVolumeItem = new Item(MediaRouteDynamicControllerDialog.this.mSelectedRoute, 1);
            if (!MediaRouteDynamicControllerDialog.this.mMemberRoutes.isEmpty()) {
                for (MediaRouter.RouteInfo memberRoute : MediaRouteDynamicControllerDialog.this.mMemberRoutes) {
                    this.mItems.add(new Item(memberRoute, 3));
                }
            } else {
                this.mItems.add(new Item(MediaRouteDynamicControllerDialog.this.mSelectedRoute, 3));
            }
            if (!MediaRouteDynamicControllerDialog.this.mGroupableRoutes.isEmpty()) {
                boolean headerAdded = false;
                for (MediaRouter.RouteInfo groupableRoute : MediaRouteDynamicControllerDialog.this.mGroupableRoutes) {
                    if (!MediaRouteDynamicControllerDialog.this.mMemberRoutes.contains(groupableRoute)) {
                        if (!headerAdded) {
                            MediaRouteProvider.DynamicGroupRouteController controller = MediaRouteDynamicControllerDialog.this.mSelectedRoute.getDynamicGroupController();
                            String title = controller != null ? controller.getGroupableSelectionTitle() : null;
                            if (TextUtils.isEmpty(title)) {
                                title = MediaRouteDynamicControllerDialog.this.mContext.getString(C2142R.string.mr_dialog_groupable_header);
                            }
                            this.mItems.add(new Item(title, 2));
                            headerAdded = true;
                        }
                        this.mItems.add(new Item(groupableRoute, 3));
                    }
                }
            }
            if (!MediaRouteDynamicControllerDialog.this.mTransferableRoutes.isEmpty()) {
                boolean headerAdded2 = false;
                for (MediaRouter.RouteInfo transferableRoute : MediaRouteDynamicControllerDialog.this.mTransferableRoutes) {
                    if (MediaRouteDynamicControllerDialog.this.mSelectedRoute != transferableRoute) {
                        if (!headerAdded2) {
                            headerAdded2 = true;
                            MediaRouteProvider.DynamicGroupRouteController controller2 = MediaRouteDynamicControllerDialog.this.mSelectedRoute.getDynamicGroupController();
                            String title2 = controller2 != null ? controller2.getTransferableSectionTitle() : null;
                            if (TextUtils.isEmpty(title2)) {
                                title2 = MediaRouteDynamicControllerDialog.this.mContext.getString(C2142R.string.mr_dialog_transferable_header);
                            }
                            this.mItems.add(new Item(title2, 2));
                        }
                        this.mItems.add(new Item(transferableRoute, 4));
                    }
                }
            }
            notifyAdapterDataSetChanged();
        }

        /* access modifiers changed from: package-private */
        public void notifyAdapterDataSetChanged() {
            MediaRouteDynamicControllerDialog.this.mUngroupableRoutes.clear();
            MediaRouteDynamicControllerDialog.this.mUngroupableRoutes.addAll(MediaRouteDialogHelper.getItemsRemoved(MediaRouteDynamicControllerDialog.this.mGroupableRoutes, MediaRouteDynamicControllerDialog.this.getCurrentGroupableRoutes()));
            notifyDataSetChanged();
        }

        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            if (viewType == 1) {
                return new GroupVolumeViewHolder(this.mInflater.inflate(C2142R.C2147layout.mr_cast_group_volume_item, parent, false));
            }
            if (viewType == 2) {
                return new HeaderViewHolder(this.mInflater.inflate(C2142R.C2147layout.mr_cast_header_item, parent, false));
            }
            if (viewType == 3) {
                return new RouteViewHolder(this.mInflater.inflate(C2142R.C2147layout.mr_cast_route_item, parent, false));
            }
            if (viewType == 4) {
                return new GroupViewHolder(this.mInflater.inflate(C2142R.C2147layout.mr_cast_group_item, parent, false));
            }
            Log.w(MediaRouteDynamicControllerDialog.TAG, "Cannot create ViewHolder because of wrong view type");
            return null;
        }

        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
            int viewType = getItemViewType(position);
            Item item = getItem(position);
            if (viewType == 1) {
                MediaRouteDynamicControllerDialog.this.mVolumeSliderHolderMap.put(((MediaRouter.RouteInfo) item.getData()).getId(), (MediaRouteVolumeSliderHolder) holder);
                ((GroupVolumeViewHolder) holder).bindGroupVolumeViewHolder(item);
            } else if (viewType == 2) {
                ((HeaderViewHolder) holder).bindHeaderViewHolder(item);
            } else if (viewType == 3) {
                MediaRouteDynamicControllerDialog.this.mVolumeSliderHolderMap.put(((MediaRouter.RouteInfo) item.getData()).getId(), (MediaRouteVolumeSliderHolder) holder);
                ((RouteViewHolder) holder).bindRouteViewHolder(item);
            } else if (viewType != 4) {
                Log.w(MediaRouteDynamicControllerDialog.TAG, "Cannot bind item to ViewHolder because of wrong view type");
            } else {
                ((GroupViewHolder) holder).bindGroupViewHolder(item);
            }
        }

        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            super.onViewRecycled(holder);
            MediaRouteDynamicControllerDialog.this.mVolumeSliderHolderMap.values().remove(holder);
        }

        public int getItemCount() {
            return this.mItems.size() + 1;
        }

        /* access modifiers changed from: package-private */
        public Drawable getIconDrawable(MediaRouter.RouteInfo route) {
            Uri iconUri = route.getIconUri();
            if (iconUri != null) {
                try {
                    Drawable drawable = Drawable.createFromStream(MediaRouteDynamicControllerDialog.this.mContext.getContentResolver().openInputStream(iconUri), (String) null);
                    if (drawable != null) {
                        return drawable;
                    }
                } catch (IOException e) {
                    Log.w(MediaRouteDynamicControllerDialog.TAG, "Failed to load " + iconUri, e);
                }
            }
            return getDefaultIconDrawable(route);
        }

        private Drawable getDefaultIconDrawable(MediaRouter.RouteInfo route) {
            int deviceType = route.getDeviceType();
            if (deviceType == 1) {
                return this.mTvIcon;
            }
            if (deviceType == 2) {
                return this.mSpeakerIcon;
            }
            if (route.isGroup()) {
                return this.mSpeakerGroupIcon;
            }
            return this.mDefaultIcon;
        }

        public int getItemViewType(int position) {
            return getItem(position).getType();
        }

        public Item getItem(int position) {
            if (position == 0) {
                return this.mGroupVolumeItem;
            }
            return this.mItems.get(position - 1);
        }

        private class Item {
            private final Object mData;
            private final int mType;

            Item(Object data, int type) {
                this.mData = data;
                this.mType = type;
            }

            public Object getData() {
                return this.mData;
            }

            public int getType() {
                return this.mType;
            }
        }

        private class GroupVolumeViewHolder extends MediaRouteVolumeSliderHolder {
            private final int mExpandedHeight;
            private final TextView mTextView;

            GroupVolumeViewHolder(View itemView) {
                super(itemView, (ImageButton) itemView.findViewById(C2142R.C2145id.mr_cast_mute_button), (MediaRouteVolumeSlider) itemView.findViewById(C2142R.C2145id.mr_cast_volume_slider));
                this.mTextView = (TextView) itemView.findViewById(C2142R.C2145id.mr_group_volume_route_name);
                Resources res = MediaRouteDynamicControllerDialog.this.mContext.getResources();
                DisplayMetrics metrics = res.getDisplayMetrics();
                TypedValue value = new TypedValue();
                res.getValue(C2142R.dimen.mr_dynamic_volume_group_list_item_height, value, true);
                this.mExpandedHeight = (int) value.getDimension(metrics);
            }

            /* access modifiers changed from: package-private */
            public void bindGroupVolumeViewHolder(Item item) {
                MediaRouteDynamicControllerDialog.setLayoutHeight(this.itemView, RecyclerAdapter.this.isGroupVolumeNeeded() ? this.mExpandedHeight : 0);
                MediaRouter.RouteInfo route = (MediaRouter.RouteInfo) item.getData();
                super.bindRouteVolumeSliderHolder(route);
                this.mTextView.setText(route.getName());
            }

            /* access modifiers changed from: package-private */
            public int getExpandedHeight() {
                return this.mExpandedHeight;
            }
        }

        private class HeaderViewHolder extends RecyclerView.ViewHolder {
            private final TextView mTextView;

            HeaderViewHolder(View itemView) {
                super(itemView);
                this.mTextView = (TextView) itemView.findViewById(C2142R.C2145id.mr_cast_header_name);
            }

            /* access modifiers changed from: package-private */
            public void bindHeaderViewHolder(Item item) {
                this.mTextView.setText(item.getData().toString());
            }
        }

        private class RouteViewHolder extends MediaRouteVolumeSliderHolder {
            final CheckBox mCheckBox;
            final int mCollapsedLayoutHeight;
            final float mDisabledAlpha;
            final int mExpandedLayoutHeight;
            final ImageView mImageView;
            final View mItemView;
            final ProgressBar mProgressBar;
            final TextView mTextView;
            final View.OnClickListener mViewClickListener = new View.OnClickListener() {
                public void onClick(View v) {
                    RouteViewHolder routeViewHolder = RouteViewHolder.this;
                    boolean selected = !routeViewHolder.isSelected(routeViewHolder.mRoute);
                    boolean isGroup = RouteViewHolder.this.mRoute.isGroup();
                    if (selected) {
                        MediaRouteDynamicControllerDialog.this.mRouter.addMemberToDynamicGroup(RouteViewHolder.this.mRoute);
                    } else {
                        MediaRouteDynamicControllerDialog.this.mRouter.removeMemberFromDynamicGroup(RouteViewHolder.this.mRoute);
                    }
                    RouteViewHolder.this.showSelectingProgress(selected, !isGroup);
                    if (isGroup) {
                        List<MediaRouter.RouteInfo> selectedRoutes = MediaRouteDynamicControllerDialog.this.mSelectedRoute.getMemberRoutes();
                        for (MediaRouter.RouteInfo route : RouteViewHolder.this.mRoute.getMemberRoutes()) {
                            if (selectedRoutes.contains(route) != selected) {
                                MediaRouteVolumeSliderHolder volumeSliderHolder = MediaRouteDynamicControllerDialog.this.mVolumeSliderHolderMap.get(route.getId());
                                if (volumeSliderHolder instanceof RouteViewHolder) {
                                    ((RouteViewHolder) volumeSliderHolder).showSelectingProgress(selected, true);
                                }
                            }
                        }
                    }
                    RecyclerAdapter.this.mayUpdateGroupVolume(RouteViewHolder.this.mRoute, selected);
                }
            };
            final RelativeLayout mVolumeSliderLayout;

            RouteViewHolder(View itemView) {
                super(itemView, (ImageButton) itemView.findViewById(C2142R.C2145id.mr_cast_mute_button), (MediaRouteVolumeSlider) itemView.findViewById(C2142R.C2145id.mr_cast_volume_slider));
                this.mItemView = itemView;
                this.mImageView = (ImageView) itemView.findViewById(C2142R.C2145id.mr_cast_route_icon);
                ProgressBar progressBar = (ProgressBar) itemView.findViewById(C2142R.C2145id.mr_cast_route_progress_bar);
                this.mProgressBar = progressBar;
                this.mTextView = (TextView) itemView.findViewById(C2142R.C2145id.mr_cast_route_name);
                this.mVolumeSliderLayout = (RelativeLayout) itemView.findViewById(C2142R.C2145id.mr_cast_volume_layout);
                CheckBox checkBox = (CheckBox) itemView.findViewById(C2142R.C2145id.mr_cast_checkbox);
                this.mCheckBox = checkBox;
                checkBox.setButtonDrawable(MediaRouterThemeHelper.getCheckBoxDrawableIcon(MediaRouteDynamicControllerDialog.this.mContext));
                MediaRouterThemeHelper.setIndeterminateProgressBarColor(MediaRouteDynamicControllerDialog.this.mContext, progressBar);
                this.mDisabledAlpha = MediaRouterThemeHelper.getDisabledAlpha(MediaRouteDynamicControllerDialog.this.mContext);
                Resources res = MediaRouteDynamicControllerDialog.this.mContext.getResources();
                DisplayMetrics metrics = res.getDisplayMetrics();
                TypedValue value = new TypedValue();
                res.getValue(C2142R.dimen.mr_dynamic_dialog_row_height, value, true);
                this.mExpandedLayoutHeight = (int) value.getDimension(metrics);
                this.mCollapsedLayoutHeight = 0;
            }

            /* access modifiers changed from: package-private */
            public boolean isSelected(MediaRouter.RouteInfo route) {
                if (route.isSelected()) {
                    return true;
                }
                MediaRouter.RouteInfo.DynamicGroupState state = route.getDynamicGroupState();
                if (state == null || state.getSelectionState() != 3) {
                    return false;
                }
                return true;
            }

            private boolean isEnabled(MediaRouter.RouteInfo route) {
                if (MediaRouteDynamicControllerDialog.this.mUngroupableRoutes.contains(route)) {
                    return false;
                }
                if (isSelected(route) && MediaRouteDynamicControllerDialog.this.mSelectedRoute.getMemberRoutes().size() < 2) {
                    return false;
                }
                if (!isSelected(route) || MediaRouteDynamicControllerDialog.this.mSelectedRoute.getDynamicGroupState() == null) {
                    return true;
                }
                MediaRouter.RouteInfo.DynamicGroupState state = route.getDynamicGroupState();
                if (state == null || !state.isUnselectable()) {
                    return false;
                }
                return true;
            }

            /* access modifiers changed from: package-private */
            public void bindRouteViewHolder(Item item) {
                MediaRouter.RouteInfo route = (MediaRouter.RouteInfo) item.getData();
                if (route == MediaRouteDynamicControllerDialog.this.mSelectedRoute && route.getMemberRoutes().size() > 0) {
                    Iterator<MediaRouter.RouteInfo> it = route.getMemberRoutes().iterator();
                    while (true) {
                        if (!it.hasNext()) {
                            break;
                        }
                        MediaRouter.RouteInfo memberRoute = it.next();
                        if (!MediaRouteDynamicControllerDialog.this.mGroupableRoutes.contains(memberRoute)) {
                            route = memberRoute;
                            break;
                        }
                    }
                }
                bindRouteVolumeSliderHolder(route);
                this.mImageView.setImageDrawable(RecyclerAdapter.this.getIconDrawable(route));
                this.mTextView.setText(route.getName());
                float f = 1.0f;
                boolean z = false;
                if (MediaRouteDynamicControllerDialog.this.mSelectedRoute.getDynamicGroupState() != null) {
                    this.mCheckBox.setVisibility(0);
                    boolean selected = isSelected(route);
                    boolean enabled = isEnabled(route);
                    this.mCheckBox.setChecked(selected);
                    this.mProgressBar.setVisibility(4);
                    this.mImageView.setVisibility(0);
                    this.mItemView.setEnabled(enabled);
                    this.mCheckBox.setEnabled(enabled);
                    this.mMuteButton.setEnabled(enabled || selected);
                    MediaRouteVolumeSlider mediaRouteVolumeSlider = this.mVolumeSlider;
                    if (enabled || selected) {
                        z = true;
                    }
                    mediaRouteVolumeSlider.setEnabled(z);
                    this.mItemView.setOnClickListener(this.mViewClickListener);
                    this.mCheckBox.setOnClickListener(this.mViewClickListener);
                    MediaRouteDynamicControllerDialog.setLayoutHeight(this.mVolumeSliderLayout, (!selected || this.mRoute.isGroup()) ? this.mCollapsedLayoutHeight : this.mExpandedLayoutHeight);
                    this.mItemView.setAlpha((enabled || selected) ? 1.0f : this.mDisabledAlpha);
                    CheckBox checkBox = this.mCheckBox;
                    if (!enabled && selected) {
                        f = this.mDisabledAlpha;
                    }
                    checkBox.setAlpha(f);
                    return;
                }
                this.mCheckBox.setVisibility(8);
                this.mProgressBar.setVisibility(4);
                this.mImageView.setVisibility(0);
                MediaRouteDynamicControllerDialog.setLayoutHeight(this.mVolumeSliderLayout, this.mExpandedLayoutHeight);
                this.mItemView.setAlpha(1.0f);
            }

            /* access modifiers changed from: package-private */
            public void showSelectingProgress(boolean selected, boolean shouldChangeHeight) {
                this.mCheckBox.setEnabled(false);
                this.mItemView.setEnabled(false);
                this.mCheckBox.setChecked(selected);
                if (selected) {
                    this.mImageView.setVisibility(4);
                    this.mProgressBar.setVisibility(0);
                }
                if (shouldChangeHeight) {
                    RecyclerAdapter.this.animateLayoutHeight(this.mVolumeSliderLayout, selected ? this.mExpandedLayoutHeight : this.mCollapsedLayoutHeight);
                }
            }
        }

        private class GroupViewHolder extends RecyclerView.ViewHolder {
            final float mDisabledAlpha;
            final ImageView mImageView;
            final View mItemView;
            final ProgressBar mProgressBar;
            MediaRouter.RouteInfo mRoute;
            final TextView mTextView;

            GroupViewHolder(View itemView) {
                super(itemView);
                this.mItemView = itemView;
                this.mImageView = (ImageView) itemView.findViewById(C2142R.C2145id.mr_cast_group_icon);
                ProgressBar progressBar = (ProgressBar) itemView.findViewById(C2142R.C2145id.mr_cast_group_progress_bar);
                this.mProgressBar = progressBar;
                this.mTextView = (TextView) itemView.findViewById(C2142R.C2145id.mr_cast_group_name);
                this.mDisabledAlpha = MediaRouterThemeHelper.getDisabledAlpha(MediaRouteDynamicControllerDialog.this.mContext);
                MediaRouterThemeHelper.setIndeterminateProgressBarColor(MediaRouteDynamicControllerDialog.this.mContext, progressBar);
            }

            private boolean isEnabled(MediaRouter.RouteInfo route) {
                if (MediaRouteDynamicControllerDialog.this.mSelectedRoute.getDynamicGroupState() != null) {
                    List<MediaRouter.RouteInfo> currentMemberRoutes = MediaRouteDynamicControllerDialog.this.mSelectedRoute.getMemberRoutes();
                    if (currentMemberRoutes.size() == 1 && currentMemberRoutes.get(0) == route) {
                        return false;
                    }
                    return true;
                }
                return true;
            }

            /* access modifiers changed from: package-private */
            public void bindGroupViewHolder(Item item) {
                MediaRouter.RouteInfo route = (MediaRouter.RouteInfo) item.getData();
                this.mRoute = route;
                this.mImageView.setVisibility(0);
                this.mProgressBar.setVisibility(4);
                this.mItemView.setAlpha(isEnabled(route) ? 1.0f : this.mDisabledAlpha);
                this.mItemView.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        MediaRouteDynamicControllerDialog.this.mIsSelectingRoute = true;
                        GroupViewHolder.this.mRoute.select();
                        GroupViewHolder.this.mImageView.setVisibility(4);
                        GroupViewHolder.this.mProgressBar.setVisibility(0);
                    }
                });
                this.mImageView.setImageDrawable(RecyclerAdapter.this.getIconDrawable(route));
                this.mTextView.setText(route.getName());
            }
        }
    }

    private final class MediaRouterCallback extends MediaRouter.Callback {
        MediaRouterCallback() {
        }

        public void onRouteAdded(MediaRouter router, MediaRouter.RouteInfo info) {
            MediaRouteDynamicControllerDialog.this.updateRoutesView();
        }

        public void onRouteRemoved(MediaRouter router, MediaRouter.RouteInfo info) {
            MediaRouteDynamicControllerDialog.this.updateRoutesView();
        }

        public void onRouteSelected(MediaRouter router, MediaRouter.RouteInfo route) {
            MediaRouteDynamicControllerDialog.this.mSelectedRoute = route;
            MediaRouteDynamicControllerDialog.this.mIsSelectingRoute = false;
            MediaRouteDynamicControllerDialog.this.updateViewsIfNeeded();
            MediaRouteDynamicControllerDialog.this.updateRoutes();
        }

        public void onRouteUnselected(MediaRouter router, MediaRouter.RouteInfo route) {
            MediaRouteDynamicControllerDialog.this.updateRoutesView();
        }

        public void onRouteChanged(MediaRouter router, MediaRouter.RouteInfo route) {
            MediaRouter.RouteInfo.DynamicGroupState state;
            boolean shouldRefreshRoute = false;
            if (route == MediaRouteDynamicControllerDialog.this.mSelectedRoute && route.getDynamicGroupState() != null) {
                Iterator<MediaRouter.RouteInfo> it = route.getProvider().getRoutes().iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    MediaRouter.RouteInfo memberRoute = it.next();
                    if (!MediaRouteDynamicControllerDialog.this.mSelectedRoute.getMemberRoutes().contains(memberRoute) && (state = memberRoute.getDynamicGroupState()) != null && state.isGroupable() && !MediaRouteDynamicControllerDialog.this.mGroupableRoutes.contains(memberRoute)) {
                        shouldRefreshRoute = true;
                        break;
                    }
                }
            }
            if (shouldRefreshRoute) {
                MediaRouteDynamicControllerDialog.this.updateViewsIfNeeded();
                MediaRouteDynamicControllerDialog.this.updateRoutes();
                return;
            }
            MediaRouteDynamicControllerDialog.this.updateRoutesView();
        }

        public void onRouteVolumeChanged(MediaRouter router, MediaRouter.RouteInfo route) {
            MediaRouteVolumeSliderHolder holder;
            int volume = route.getVolume();
            if (MediaRouteDynamicControllerDialog.DEBUG) {
                Log.d(MediaRouteDynamicControllerDialog.TAG, "onRouteVolumeChanged(), route.getVolume:" + volume);
            }
            if (MediaRouteDynamicControllerDialog.this.mRouteForVolumeUpdatingByUser != route && (holder = MediaRouteDynamicControllerDialog.this.mVolumeSliderHolderMap.get(route.getId())) != null) {
                holder.updateVolume();
            }
        }
    }

    private final class MediaControllerCallback extends MediaControllerCompat.Callback {
        MediaControllerCallback() {
        }

        public void onSessionDestroyed() {
            if (MediaRouteDynamicControllerDialog.this.mMediaController != null) {
                MediaRouteDynamicControllerDialog.this.mMediaController.unregisterCallback(MediaRouteDynamicControllerDialog.this.mControllerCallback);
                MediaRouteDynamicControllerDialog.this.mMediaController = null;
            }
        }

        public void onMetadataChanged(MediaMetadataCompat metadata) {
            MediaRouteDynamicControllerDialog.this.mDescription = metadata == null ? null : metadata.getDescription();
            MediaRouteDynamicControllerDialog.this.reloadIconIfNeeded();
            MediaRouteDynamicControllerDialog.this.updateMetadataViews();
        }
    }

    private class FetchArtTask extends AsyncTask<Void, Void, Bitmap> {
        private int mBackgroundColor;
        private final Bitmap mIconBitmap;
        private final Uri mIconUri;

        FetchArtTask() {
            Uri uri = null;
            Bitmap bitmap = MediaRouteDynamicControllerDialog.this.mDescription == null ? null : MediaRouteDynamicControllerDialog.this.mDescription.getIconBitmap();
            if (MediaRouteDynamicControllerDialog.isBitmapRecycled(bitmap)) {
                Log.w(MediaRouteDynamicControllerDialog.TAG, "Can't fetch the given art bitmap because it's already recycled.");
                bitmap = null;
            }
            this.mIconBitmap = bitmap;
            this.mIconUri = MediaRouteDynamicControllerDialog.this.mDescription != null ? MediaRouteDynamicControllerDialog.this.mDescription.getIconUri() : uri;
        }

        /* access modifiers changed from: package-private */
        public Bitmap getIconBitmap() {
            return this.mIconBitmap;
        }

        /* access modifiers changed from: package-private */
        public Uri getIconUri() {
            return this.mIconUri;
        }

        /* access modifiers changed from: protected */
        public void onPreExecute() {
            MediaRouteDynamicControllerDialog.this.clearLoadedBitmap();
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
                            Log.w(MediaRouteDynamicControllerDialog.TAG, "Unable to open: " + this.mIconUri);
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
                                Log.w(MediaRouteDynamicControllerDialog.TAG, "Unable to open: " + this.mIconUri);
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
                        options.inSampleSize = Math.max(1, Integer.highestOneBit(options.outHeight / MediaRouteDynamicControllerDialog.this.mContext.getResources().getDimensionPixelSize(C2142R.dimen.mr_cast_meta_art_size)));
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
                        Log.w(MediaRouteDynamicControllerDialog.TAG, "Unable to open: " + this.mIconUri, e7);
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
            if (MediaRouteDynamicControllerDialog.isBitmapRecycled(art)) {
                Log.w(MediaRouteDynamicControllerDialog.TAG, "Can't use recycled bitmap: " + art);
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
            MediaRouteDynamicControllerDialog.this.mFetchArtTask = null;
            if (!ObjectsCompat.equals(MediaRouteDynamicControllerDialog.this.mArtIconBitmap, this.mIconBitmap) || !ObjectsCompat.equals(MediaRouteDynamicControllerDialog.this.mArtIconUri, this.mIconUri)) {
                MediaRouteDynamicControllerDialog.this.mArtIconBitmap = this.mIconBitmap;
                MediaRouteDynamicControllerDialog.this.mArtIconLoadedBitmap = art;
                MediaRouteDynamicControllerDialog.this.mArtIconUri = this.mIconUri;
                MediaRouteDynamicControllerDialog.this.mArtIconBackgroundColor = this.mBackgroundColor;
                MediaRouteDynamicControllerDialog.this.mArtIconIsLoaded = true;
                MediaRouteDynamicControllerDialog.this.updateMetadataViews();
            }
        }

        private InputStream openInputStreamByScheme(Uri uri) throws IOException {
            InputStream stream;
            String scheme = uri.getScheme().toLowerCase();
            if (UriUtil.QUALIFIED_RESOURCE_SCHEME.equals(scheme) || "content".equals(scheme) || UriUtil.LOCAL_FILE_SCHEME.equals(scheme)) {
                stream = MediaRouteDynamicControllerDialog.this.mContext.getContentResolver().openInputStream(uri);
            } else {
                URLConnection conn = new URL(uri.toString()).openConnection();
                conn.setConnectTimeout(30000);
                conn.setReadTimeout(30000);
                stream = conn.getInputStream();
            }
            if (stream == null) {
                return null;
            }
            return new BufferedInputStream(stream);
        }
    }

    static final class RouteComparator implements Comparator<MediaRouter.RouteInfo> {
        static final RouteComparator sInstance = new RouteComparator();

        RouteComparator() {
        }

        public int compare(MediaRouter.RouteInfo lhs, MediaRouter.RouteInfo rhs) {
            return lhs.getName().compareToIgnoreCase(rhs.getName());
        }
    }
}
