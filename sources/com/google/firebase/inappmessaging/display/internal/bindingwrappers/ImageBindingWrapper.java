package com.google.firebase.inappmessaging.display.internal.bindingwrappers;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageView;
import com.google.firebase.inappmessaging.display.C2472R;
import com.google.firebase.inappmessaging.display.internal.InAppMessageLayoutConfig;
import com.google.firebase.inappmessaging.display.internal.layout.FiamFrameLayout;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.ImageOnlyMessage;
import com.google.firebase.inappmessaging.model.InAppMessage;
import com.google.firebase.inappmessaging.model.MessageType;
import java.util.Map;
import javax.inject.Inject;

public class ImageBindingWrapper extends BindingWrapper {
    private Button collapseButton;
    private ViewGroup imageContentRoot;
    private FiamFrameLayout imageRoot;
    private ImageView imageView;

    @Inject
    public ImageBindingWrapper(InAppMessageLayoutConfig config, LayoutInflater inflater, InAppMessage message) {
        super(config, inflater, message);
    }

    public ViewTreeObserver.OnGlobalLayoutListener inflate(Map<Action, View.OnClickListener> actionListeners, View.OnClickListener dismissOnClickListener) {
        int i;
        View v = this.inflater.inflate(C2472R.C2477layout.image, (ViewGroup) null);
        this.imageRoot = (FiamFrameLayout) v.findViewById(C2472R.C2475id.image_root);
        this.imageContentRoot = (ViewGroup) v.findViewById(C2472R.C2475id.image_content_root);
        this.imageView = (ImageView) v.findViewById(C2472R.C2475id.image_view);
        this.collapseButton = (Button) v.findViewById(C2472R.C2475id.collapse_button);
        this.imageView.setMaxHeight(this.config.getMaxImageHeight());
        this.imageView.setMaxWidth(this.config.getMaxImageWidth());
        if (this.message.getMessageType().equals(MessageType.IMAGE_ONLY)) {
            ImageOnlyMessage msg = (ImageOnlyMessage) this.message;
            ImageView imageView2 = this.imageView;
            if (msg.getImageData() == null || TextUtils.isEmpty(msg.getImageData().getImageUrl())) {
                i = 8;
            } else {
                i = 0;
            }
            imageView2.setVisibility(i);
            this.imageView.setOnClickListener(actionListeners.get(msg.getAction()));
        }
        this.imageRoot.setDismissListener(dismissOnClickListener);
        this.collapseButton.setOnClickListener(dismissOnClickListener);
        return null;
    }

    public ImageView getImageView() {
        return this.imageView;
    }

    public ViewGroup getRootView() {
        return this.imageRoot;
    }

    public View getDialogView() {
        return this.imageContentRoot;
    }

    public View getCollapseButton() {
        return this.collapseButton;
    }
}
