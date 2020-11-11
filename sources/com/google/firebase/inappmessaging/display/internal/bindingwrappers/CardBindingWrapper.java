package com.google.firebase.inappmessaging.display.internal.bindingwrappers;

import android.graphics.Color;
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
import com.google.firebase.inappmessaging.display.internal.layout.BaseModalLayout;
import com.google.firebase.inappmessaging.display.internal.layout.FiamCardView;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.CardMessage;
import com.google.firebase.inappmessaging.model.InAppMessage;
import com.google.firebase.inappmessaging.model.MessageType;
import java.util.Map;
import javax.inject.Inject;

public class CardBindingWrapper extends BindingWrapper {
    private ScrollView bodyScroll;
    private BaseModalLayout cardContentRoot;
    private CardMessage cardMessage;
    private FiamCardView cardRoot;
    private View.OnClickListener dismissListener;
    /* access modifiers changed from: private */
    public ImageView imageView;
    private ViewTreeObserver.OnGlobalLayoutListener layoutListener = new ScrollViewAdjustableListener();
    private TextView messageBody;
    private TextView messageTitle;
    private Button primaryButton;
    private Button secondaryButton;

    @Inject
    public CardBindingWrapper(InAppMessageLayoutConfig config, LayoutInflater inflater, InAppMessage message) {
        super(config, inflater, message);
    }

    public ViewTreeObserver.OnGlobalLayoutListener inflate(Map<Action, View.OnClickListener> actionListeners, View.OnClickListener dismissOnClickListener) {
        View root = this.inflater.inflate(C2472R.C2477layout.card, (ViewGroup) null);
        this.bodyScroll = (ScrollView) root.findViewById(C2472R.C2475id.body_scroll);
        this.primaryButton = (Button) root.findViewById(C2472R.C2475id.primary_button);
        this.secondaryButton = (Button) root.findViewById(C2472R.C2475id.secondary_button);
        this.imageView = (ImageView) root.findViewById(C2472R.C2475id.image_view);
        this.messageBody = (TextView) root.findViewById(C2472R.C2475id.message_body);
        this.messageTitle = (TextView) root.findViewById(C2472R.C2475id.message_title);
        this.cardRoot = (FiamCardView) root.findViewById(C2472R.C2475id.card_root);
        this.cardContentRoot = (BaseModalLayout) root.findViewById(C2472R.C2475id.card_content_root);
        if (this.message.getMessageType().equals(MessageType.CARD)) {
            CardMessage cardMessage2 = (CardMessage) this.message;
            this.cardMessage = cardMessage2;
            setMessage(cardMessage2);
            setImage(this.cardMessage);
            setButtons(actionListeners);
            setLayoutConfig(this.config);
            setDismissListener(dismissOnClickListener);
            setViewBgColorFromHex(this.cardContentRoot, this.cardMessage.getBackgroundHexColor());
        }
        return this.layoutListener;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public View getScrollView() {
        return this.bodyScroll;
    }

    public View getTitleView() {
        return this.messageTitle;
    }

    public ViewGroup getRootView() {
        return this.cardRoot;
    }

    public View getDialogView() {
        return this.cardContentRoot;
    }

    public InAppMessageLayoutConfig getConfig() {
        return this.config;
    }

    public View.OnClickListener getDismissListener() {
        return this.dismissListener;
    }

    public Button getPrimaryButton() {
        return this.primaryButton;
    }

    public Button getSecondaryButton() {
        return this.secondaryButton;
    }

    private void setMessage(CardMessage message) {
        this.messageTitle.setText(message.getTitle().getText());
        this.messageTitle.setTextColor(Color.parseColor(message.getTitle().getHexColor()));
        if (message.getBody() == null || message.getBody().getText() == null) {
            this.bodyScroll.setVisibility(8);
            this.messageBody.setVisibility(8);
            return;
        }
        this.bodyScroll.setVisibility(0);
        this.messageBody.setVisibility(0);
        this.messageBody.setText(message.getBody().getText());
        this.messageBody.setTextColor(Color.parseColor(message.getBody().getHexColor()));
    }

    private void setButtons(Map<Action, View.OnClickListener> actionListeners) {
        Action primaryAction = this.cardMessage.getPrimaryAction();
        Action secondaryAction = this.cardMessage.getSecondaryAction();
        setupViewButtonFromModel(this.primaryButton, primaryAction.getButton());
        setButtonActionListener(this.primaryButton, actionListeners.get(primaryAction));
        this.primaryButton.setVisibility(0);
        if (secondaryAction == null || secondaryAction.getButton() == null) {
            this.secondaryButton.setVisibility(8);
            return;
        }
        setupViewButtonFromModel(this.secondaryButton, secondaryAction.getButton());
        setButtonActionListener(this.secondaryButton, actionListeners.get(secondaryAction));
        this.secondaryButton.setVisibility(0);
    }

    private void setImage(CardMessage message) {
        if (message.getPortraitImageData() == null && message.getLandscapeImageData() == null) {
            this.imageView.setVisibility(8);
        } else {
            this.imageView.setVisibility(0);
        }
    }

    private void setLayoutConfig(InAppMessageLayoutConfig config) {
        this.imageView.setMaxHeight(config.getMaxImageHeight());
        this.imageView.setMaxWidth(config.getMaxImageWidth());
    }

    private void setDismissListener(View.OnClickListener dismissListener2) {
        this.dismissListener = dismissListener2;
        this.cardRoot.setDismissListener(dismissListener2);
    }

    public void setLayoutListener(ViewTreeObserver.OnGlobalLayoutListener listener) {
        this.layoutListener = listener;
    }

    public class ScrollViewAdjustableListener implements ViewTreeObserver.OnGlobalLayoutListener {
        public ScrollViewAdjustableListener() {
        }

        public void onGlobalLayout() {
            CardBindingWrapper.this.imageView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
        }
    }
}
