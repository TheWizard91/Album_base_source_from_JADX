package com.google.firebase.inappmessaging.display.internal;

public class InAppMessageLayoutConfig {
    /* access modifiers changed from: private */
    public Boolean animate;
    /* access modifiers changed from: private */
    public Boolean autoDismiss;
    /* access modifiers changed from: private */
    public Boolean backgroundEnabled;
    /* access modifiers changed from: private */
    public Float maxBodyHeightWeight;
    /* access modifiers changed from: private */
    public Float maxBodyWidthWeight;
    /* access modifiers changed from: private */
    public Integer maxDialogHeightPx;
    /* access modifiers changed from: private */
    public Integer maxDialogWidthPx;
    /* access modifiers changed from: private */
    public Float maxImageHeightWeight;
    /* access modifiers changed from: private */
    public Float maxImageWidthWeight;
    /* access modifiers changed from: private */
    public Integer viewWindowGravity;
    /* access modifiers changed from: private */
    public Integer windowFlag;
    /* access modifiers changed from: private */
    public Integer windowHeight;
    /* access modifiers changed from: private */
    public Integer windowWidth;

    public static Builder builder() {
        return new Builder();
    }

    public Float maxImageHeightWeight() {
        return this.maxImageHeightWeight;
    }

    public Float maxImageWidthWeight() {
        return this.maxImageWidthWeight;
    }

    public Float maxBodyHeightWeight() {
        return this.maxBodyHeightWeight;
    }

    public Float maxBodyWidthWeight() {
        return this.maxBodyWidthWeight;
    }

    public Integer maxDialogHeightPx() {
        return this.maxDialogHeightPx;
    }

    public Integer maxDialogWidthPx() {
        return this.maxDialogWidthPx;
    }

    public Integer windowFlag() {
        return this.windowFlag;
    }

    public Integer viewWindowGravity() {
        return this.viewWindowGravity;
    }

    public Integer windowWidth() {
        return this.windowWidth;
    }

    public Integer windowHeight() {
        return this.windowHeight;
    }

    public Boolean backgroundEnabled() {
        return this.backgroundEnabled;
    }

    public Boolean animate() {
        return this.animate;
    }

    public Boolean autoDismiss() {
        return this.autoDismiss;
    }

    public int getMaxImageHeight() {
        return (int) (maxImageHeightWeight().floatValue() * ((float) maxDialogHeightPx().intValue()));
    }

    public int getMaxImageWidth() {
        return (int) (maxImageWidthWeight().floatValue() * ((float) maxDialogWidthPx().intValue()));
    }

    public int getMaxBodyHeight() {
        return (int) (maxBodyHeightWeight().floatValue() * ((float) maxDialogHeightPx().intValue()));
    }

    public int getMaxBodyWidth() {
        return (int) (maxBodyWidthWeight().floatValue() * ((float) maxDialogWidthPx().intValue()));
    }

    public static class Builder {
        private final InAppMessageLayoutConfig config = new InAppMessageLayoutConfig();

        public Builder setMaxImageHeightWeight(Float maxImageHeightWeight) {
            Float unused = this.config.maxImageHeightWeight = maxImageHeightWeight;
            return this;
        }

        public Builder setMaxImageWidthWeight(Float maxImageWidthWeight) {
            Float unused = this.config.maxImageWidthWeight = maxImageWidthWeight;
            return this;
        }

        public Builder setMaxBodyHeightWeight(Float maxBodyHeightWeight) {
            Float unused = this.config.maxBodyHeightWeight = maxBodyHeightWeight;
            return this;
        }

        public Builder setMaxBodyWidthWeight(Float maxBodyWidthWeight) {
            Float unused = this.config.maxBodyWidthWeight = maxBodyWidthWeight;
            return this;
        }

        public Builder setMaxDialogHeightPx(Integer maxDialogHeightPx) {
            Integer unused = this.config.maxDialogHeightPx = maxDialogHeightPx;
            return this;
        }

        public Builder setMaxDialogWidthPx(Integer maxDialogWidthPx) {
            Integer unused = this.config.maxDialogWidthPx = maxDialogWidthPx;
            return this;
        }

        public Builder setViewWindowGravity(Integer viewWindowGravity) {
            Integer unused = this.config.viewWindowGravity = viewWindowGravity;
            return this;
        }

        public Builder setWindowFlag(Integer windowFlag) {
            Integer unused = this.config.windowFlag = windowFlag;
            return this;
        }

        public Builder setWindowWidth(Integer windowWidth) {
            Integer unused = this.config.windowWidth = windowWidth;
            return this;
        }

        public Builder setWindowHeight(Integer windowHeight) {
            Integer unused = this.config.windowHeight = windowHeight;
            return this;
        }

        public Builder setBackgroundEnabled(Boolean backgroundEnabled) {
            Boolean unused = this.config.backgroundEnabled = backgroundEnabled;
            return this;
        }

        public Builder setAnimate(Boolean animate) {
            Boolean unused = this.config.animate = animate;
            return this;
        }

        public Builder setAutoDismiss(Boolean autoDismiss) {
            Boolean unused = this.config.autoDismiss = autoDismiss;
            return this;
        }

        public InAppMessageLayoutConfig build() {
            return this.config;
        }
    }
}
