package com.google.firebase.inappmessaging.model;

import android.text.TextUtils;
import com.google.firebase.inappmessaging.MessagesProto;

public class Text {
    private final String hexColor;
    private final String text;

    public int hashCode() {
        String str = this.text;
        return str != null ? str.hashCode() + this.hexColor.hashCode() : this.hexColor.hashCode();
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Text)) {
            return false;
        }
        Text t = (Text) o;
        if (hashCode() != t.hashCode()) {
            return false;
        }
        String str = this.text;
        if ((str != null || t.text == null) && ((str == null || str.equals(t.text)) && this.hexColor.equals(t.hexColor))) {
            return true;
        }
        return false;
    }

    private Text(String text2, String hexColor2) {
        this.text = text2;
        this.hexColor = hexColor2;
    }

    public String getText() {
        return this.text;
    }

    public String getHexColor() {
        return this.hexColor;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private String hexColor;
        private String text;

        public Builder setText(String text2) {
            this.text = text2;
            return this;
        }

        public Builder setText(MessagesProto.Text text2) {
            setText(text2.getText());
            setHexColor(text2.getHexColor());
            return this;
        }

        public Builder setHexColor(String hexColor2) {
            this.hexColor = hexColor2;
            return this;
        }

        public Text build() {
            if (!TextUtils.isEmpty(this.hexColor)) {
                return new Text(this.text, this.hexColor);
            }
            throw new IllegalArgumentException("Text model must have a color");
        }
    }
}
