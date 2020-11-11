package com.google.firebase.inappmessaging.model;

import android.text.TextUtils;
import com.google.firebase.inappmessaging.MessagesProto;
import com.google.firebase.inappmessaging.model.Text;

public class Button {
    private final String buttonHexColor;
    private final Text text;

    public int hashCode() {
        return this.text.hashCode() + this.buttonHexColor.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Button)) {
            return false;
        }
        Button b = (Button) o;
        if (hashCode() == b.hashCode() && this.text.equals(b.text) && this.buttonHexColor.equals(b.buttonHexColor)) {
            return true;
        }
        return false;
    }

    private Button(Text text2, String buttonHexColor2) {
        this.text = text2;
        this.buttonHexColor = buttonHexColor2;
    }

    public Text getText() {
        return this.text;
    }

    public String getButtonHexColor() {
        return this.buttonHexColor;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String buttonHexColor;
        private Text text;

        public Builder setText(Text text2) {
            this.text = text2;
            return this;
        }

        public Builder setText(MessagesProto.Text text2) {
            Text.Builder textBuilder = new Text.Builder();
            textBuilder.setText(text2);
            this.text = textBuilder.build();
            return this;
        }

        public Builder setButtonHexColor(String buttonHexColor2) {
            this.buttonHexColor = buttonHexColor2;
            return this;
        }

        public Button build() {
            if (TextUtils.isEmpty(this.buttonHexColor)) {
                throw new IllegalArgumentException("Button model must have a color");
            } else if (this.text != null) {
                return new Button(this.text, this.buttonHexColor);
            } else {
                throw new IllegalArgumentException("Button model must have text");
            }
        }
    }
}
