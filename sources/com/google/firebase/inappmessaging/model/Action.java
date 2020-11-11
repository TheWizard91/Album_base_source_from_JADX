package com.google.firebase.inappmessaging.model;

import android.text.TextUtils;
import com.google.firebase.inappmessaging.MessagesProto;
import com.google.firebase.inappmessaging.model.Button;

public class Action {
    private final String actionUrl;
    private final Button button;

    public int hashCode() {
        String str = this.actionUrl;
        int buttonHash = 0;
        int urlHash = str != null ? str.hashCode() : 0;
        Button button2 = this.button;
        if (button2 != null) {
            buttonHash = button2.hashCode();
        }
        return urlHash + buttonHash;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Action)) {
            return false;
        }
        Action a = (Action) o;
        if (hashCode() != a.hashCode()) {
            return false;
        }
        String str = this.actionUrl;
        if ((str == null && a.actionUrl != null) || (str != null && !str.equals(a.actionUrl))) {
            return false;
        }
        Button button2 = this.button;
        if ((button2 != null || a.button != null) && (button2 == null || !button2.equals(a.button))) {
            return false;
        }
        return true;
    }

    private Action(String actionUrl2, Button button2) {
        this.actionUrl = actionUrl2;
        this.button = button2;
    }

    public String getActionUrl() {
        return this.actionUrl;
    }

    public Button getButton() {
        return this.button;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String actionUrl;
        private Button button;

        public Builder setActionUrl(String actionUrl2) {
            if (!TextUtils.isEmpty(actionUrl2)) {
                this.actionUrl = actionUrl2;
            }
            return this;
        }

        public Builder setButton(Button button2) {
            this.button = button2;
            return this;
        }

        public Builder setButton(MessagesProto.Button button2) {
            Button.Builder buttonBuilder = new Button.Builder();
            buttonBuilder.setButtonHexColor(button2.getButtonHexColor());
            buttonBuilder.setText(button2.getText());
            return this;
        }

        public Action build() {
            return new Action(this.actionUrl, this.button);
        }
    }
}
