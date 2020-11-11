package com.google.firebase.inappmessaging.display.internal.bindingwrappers;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.firebase.inappmessaging.display.C2472R;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import com.google.firebase.inappmessaging.display.internal.ResizableImageView;
import com.google.firebase.inappmessaging.display.internal.layout.FiamFrameLayout;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.BannerMessage;
import com.google.firebase.inappmessaging.model.InAppMessage;
import com.google.firebase.inappmessaging.model.MessageType;
import java.util.Map;
import javax.inject.Inject;

public class BannerBindingWrapper extends BindingWrapper {
    private TextView bannerBody;
    private ViewGroup bannerContentRoot;
    private ResizableImageView bannerImage;
    private FiamFrameLayout bannerRoot;
    private TextView bannerTitle;
    private View.OnClickListener mDismissListener;

    @Inject
    public BannerBindingWrapper(InAppMessageLayoutConfig config, LayoutInflater inflater, InAppMessage message) {
        super(config, inflater, message);
    }

    public ViewTreeObserver.OnGlobalLayoutListener inflate(Map<Action, View.OnClickListener> actionListeners, View.OnClickListener dismissOnClickListener) {
        View root = this.inflater.inflate(C2472R.C2477layout.banner, (ViewGroup) null);
        this.bannerRoot = (FiamFrameLayout) root.findViewById(C2472R.C2475id.banner_root);
        this.bannerContentRoot = (ViewGroup) root.findViewById(C2472R.C2475id.banner_content_root);
        this.bannerBody = (TextView) root.findViewById(C2472R.C2475id.banner_body);
        this.bannerImage = (ResizableImageView) root.findViewById(C2472R.C2475id.banner_image);
        this.bannerTitle = (TextView) root.findViewById(C2472R.C2475id.banner_title);
        if (this.message.getMessageType().equals(MessageType.BANNER)) {
            BannerMessage bannerMessage = (BannerMessage) this.message;
            setMessage(bannerMessage);
            setLayoutConfig(this.config);
            setSwipeDismissListener(dismissOnClickListener);
            setActionListener(actionListeners.get(bannerMessage.getAction()));
        }
        return null;
    }

    private void setMessage(BannerMessage message) {
        int i;
        if (!TextUtils.isEmpty(message.getBackgroundHexColor())) {
            setViewBgColorFromHex(this.bannerContentRoot, message.getBackgroundHexColor());
        }
        ResizableImageView resizableImageView = this.bannerImage;
        if (message.getImageData() == null || TextUtils.isEmpty(message.getImageData().getImageUrl())) {
            i = 8;
        } else {
            i = 0;
        }
        resizableImageView.setVisibility(i);
        if (message.getTitle() != null) {
            if (!TextUtils.isEmpty(message.getTitle().getText())) {
                this.bannerTitle.setText(message.getTitle().getText());
            }
            if (!TextUtils.isEmpty(message.getTitle().getHexColor())) {
                this.bannerTitle.setTextColor(Color.parseColor(message.getTitle().getHexColor()));
            }
        }
        if (message.getBody() != null) {
            if (!TextUtils.isEmpty(message.getBody().getText())) {
                this.bannerBody.setText(message.getBody().getText());
            }
            if (!TextUtils.isEmpty(message.getBody().getHexColor())) {
                this.bannerBody.setTextColor(Color.parseColor(message.getBody().getHexColor()));
            }
        }
    }

    private void setLayoutConfig(InAppMessageLayoutConfig layoutConfig) {
        int bannerWidth = Math.min(layoutConfig.maxDialogWidthPx().intValue(), layoutConfig.maxDialogHeightPx().intValue());
        ViewGroup.LayoutParams params = this.bannerRoot.getLayoutParams();
        if (params == null) {
            params = new ViewGroup.LayoutParams(-1, -2);
        }
        params.width = bannerWidth;
        this.bannerRoot.setLayoutParams(params);
        this.bannerImage.setMaxHeight(layoutConfig.getMaxImageHeight());
        this.bannerImage.setMaxWidth(layoutConfig.getMaxImageWidth());
    }

    private void setSwipeDismissListener(View.OnClickListener dismissListener) {
        this.mDismissListener = dismissListener;
        this.bannerRoot.setDismissListener(dismissListener);
    }

    private void setActionListener(View.OnClickListener actionListener) {
        this.bannerContentRoot.setOnClickListener(actionListener);
    }

    public InAppMessageLayoutConfig getConfig() {
        return this.config;
    }

    public ImageView getImageView() {
        return this.bannerImage;
    }

    public ViewGroup getRootView() {
        return this.bannerRoot;
    }

    public View getDialogView() {
        return this.bannerContentRoot;
    }

    public View.OnClickListener getDismissListener() {
        return this.mDismissListener;
    }

    public boolean canSwipeToDismiss() {
        return true;
    }
}
