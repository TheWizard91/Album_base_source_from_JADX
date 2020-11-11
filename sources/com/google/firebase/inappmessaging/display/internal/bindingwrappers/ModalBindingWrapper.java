package com.google.firebase.inappmessaging.display.internal.bindingwrappers;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import com.google.firebase.inappmessaging.display.C2472R;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import com.google.firebase.inappmessaging.display.internal.layout.FiamRelativeLayout;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.InAppMessage;
import com.google.firebase.inappmessaging.model.MessageType;
import com.google.firebase.inappmessaging.model.ModalMessage;
import java.util.Map;
import javax.inject.Inject;

public class ModalBindingWrapper extends BindingWrapper {
    private ScrollView bodyScroll;
    private Button button;
    private View collapseImage;
    /* access modifiers changed from: private */
    public ImageView imageView;
    private ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ScrollViewAdjustableListener();
    private TextView messageBody;
    private TextView messageTitle;
    private ViewGroup modalContentRoot;
    private ModalMessage modalMessage;
    private FiamRelativeLayout modalRoot;

    @Inject
    public ModalBindingWrapper(InAppMessageLayoutConfig config, LayoutInflater inflater, InAppMessage message) {
        super(config, inflater, message);
    }

    public ViewTreeObserver.OnGlobalLayoutListener inflate(Map<Action, View.OnClickListener> actionListeners, View.OnClickListener dismissOnClickListener) {
        View root = this.inflater.inflate(C2472R.C2477layout.modal, (ViewGroup) null);
        this.bodyScroll = (ScrollView) root.findViewById(C2472R.C2475id.body_scroll);
        this.button = (Button) root.findViewById(C2472R.C2475id.button);
        this.collapseImage = root.findViewById(C2472R.C2475id.collapse_button);
        this.imageView = (ImageView) root.findViewById(C2472R.C2475id.image_view);
        this.messageBody = (TextView) root.findViewById(C2472R.C2475id.message_body);
        this.messageTitle = (TextView) root.findViewById(C2472R.C2475id.message_title);
        this.modalRoot = (FiamRelativeLayout) root.findViewById(C2472R.C2475id.modal_root);
        this.modalContentRoot = (ViewGroup) root.findViewById(C2472R.C2475id.modal_content_root);
        if (this.message.getMessageType().equals(MessageType.MODAL)) {
            ModalMessage modalMessage2 = (ModalMessage) this.message;
            this.modalMessage = modalMessage2;
            setMessage(modalMessage2);
            setButton(actionListeners);
            setLayoutConfig(this.config);
            setDismissListener(dismissOnClickListener);
            setViewBgColorFromHex(this.modalContentRoot, this.modalMessage.getBackgroundHexColor());
        }
        return this.layoutListener;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public ViewGroup getRootView() {
        return this.modalRoot;
    }

    public View getDialogView() {
        return this.modalContentRoot;
    }

    public View getScrollView() {
        return this.bodyScroll;
    }

    public View getTitleView() {
        return this.messageTitle;
    }

    public InAppMessageLayoutConfig getConfig() {
        return this.config;
    }

    public Button getActionButton() {
        return this.button;
    }

    public View getCollapseButton() {
        return this.collapseImage;
    }

    private void setMessage(ModalMessage message) {
        if (message.getImageData() == null || TextUtils.isEmpty(message.getImageData().getImageUrl())) {
            this.imageView.setVisibility(8);
        } else {
            this.imageView.setVisibility(0);
        }
        if (message.getTitle() != null) {
            if (!TextUtils.isEmpty(message.getTitle().getText())) {
                this.messageTitle.setVisibility(0);
                this.messageTitle.setText(message.getTitle().getText());
            } else {
                this.messageTitle.setVisibility(8);
            }
            if (!TextUtils.isEmpty(message.getTitle().getHexColor())) {
                this.messageTitle.setTextColor(Color.parseColor(message.getTitle().getHexColor()));
            }
        }
        if (message.getBody() == null || TextUtils.isEmpty(message.getBody().getText())) {
            this.bodyScroll.setVisibility(8);
            this.messageBody.setVisibility(8);
            return;
        }
        this.bodyScroll.setVisibility(0);
        this.messageBody.setVisibility(0);
        this.messageBody.setTextColor(Color.parseColor(message.getBody().getHexColor()));
        this.messageBody.setText(message.getBody().getText());
    }

    private void setButton(Map<Action, View.OnClickListener> actionListeners) {
        Action modalAction = this.modalMessage.getAction();
        if (modalAction == null || modalAction.getButton() == null || TextUtils.isEmpty(modalAction.getButton().getText().getText())) {
            this.button.setVisibility(8);
            return;
        }
        setupViewButtonFromModel(this.button, modalAction.getButton());
        setButtonActionListener(this.button, actionListeners.get(this.modalMessage.getAction()));
        this.button.setVisibility(0);
    }

    private void setLayoutConfig(InAppMessageLayoutConfig config) {
        this.imageView.setMaxHeight(config.getMaxImageHeight());
        this.imageView.setMaxWidth(config.getMaxImageWidth());
    }

    private void setDismissListener(View.OnClickListener dismissListener) {
        this.collapseImage.setOnClickListener(dismissListener);
        this.modalRoot.setDismissListener(dismissListener);
    }

    public void setLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener) {
        this.layoutListener = listener;
    }

    public class ScrollViewAdjustableListener implements ViewTreeObserver.OnGlobalLayoutListener {
        public ScrollViewAdjustableListener() {
        }

        public void onGlobalLayout() {
            ModalBindingWrapper.this.imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }
}
