package com.google.firebase.inappmessaging;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public final class MessagesProto {

    public interface ActionOrBuilder extends MessageLiteOrBuilder {
        String getActionUrl();

        ByteString getActionUrlBytes();
    }

    public interface BannerMessageOrBuilder extends MessageLiteOrBuilder {
        Action getAction();

        String getBackgroundHexColor();

        ByteString getBackgroundHexColorBytes();

        Text getBody();

        String getImageUrl();

        ByteString getImageUrlBytes();

        Text getTitle();

        boolean hasAction();

        boolean hasBody();

        boolean hasTitle();
    }

    public interface ButtonOrBuilder extends MessageLiteOrBuilder {
        String getButtonHexColor();

        ByteString getButtonHexColorBytes();

        Text getText();

        boolean hasText();
    }

    public interface CardMessageOrBuilder extends MessageLiteOrBuilder {
        String getBackgroundHexColor();

        ByteString getBackgroundHexColorBytes();

        Text getBody();

        String getLandscapeImageUrl();

        ByteString getLandscapeImageUrlBytes();

        String getPortraitImageUrl();

        ByteString getPortraitImageUrlBytes();

        Action getPrimaryAction();

        Button getPrimaryActionButton();

        Action getSecondaryAction();

        Button getSecondaryActionButton();

        Text getTitle();

        boolean hasBody();

        boolean hasPrimaryAction();

        boolean hasPrimaryActionButton();

        boolean hasSecondaryAction();

        boolean hasSecondaryActionButton();

        boolean hasTitle();
    }

    public interface ContentOrBuilder extends MessageLiteOrBuilder {
        BannerMessage getBanner();

        CardMessage getCard();

        ImageOnlyMessage getImageOnly();

        Content.MessageDetailsCase getMessageDetailsCase();

        ModalMessage getModal();

        boolean hasBanner();

        boolean hasCard();

        boolean hasImageOnly();

        boolean hasModal();
    }

    public interface ImageOnlyMessageOrBuilder extends MessageLiteOrBuilder {
        Action getAction();

        String getImageUrl();

        ByteString getImageUrlBytes();

        boolean hasAction();
    }

    public interface ModalMessageOrBuilder extends MessageLiteOrBuilder {
        Action getAction();

        Button getActionButton();

        String getBackgroundHexColor();

        ByteString getBackgroundHexColorBytes();

        Text getBody();

        String getImageUrl();

        ByteString getImageUrlBytes();

        Text getTitle();

        boolean hasAction();

        boolean hasActionButton();

        boolean hasBody();

        boolean hasTitle();
    }

    public interface TextOrBuilder extends MessageLiteOrBuilder {
        String getHexColor();

        ByteString getHexColorBytes();

        String getText();

        ByteString getTextBytes();
    }

    private MessagesProto() {
    }

    public static void registerAllExtensions(ExtensionRegistryLite registry) {
    }

    public static final class Content extends GeneratedMessageLite<Content, Builder> implements ContentOrBuilder {
        public static final int BANNER_FIELD_NUMBER = 1;
        public static final int CARD_FIELD_NUMBER = 4;
        /* access modifiers changed from: private */
        public static final Content DEFAULT_INSTANCE;
        public static final int IMAGE_ONLY_FIELD_NUMBER = 3;
        public static final int MODAL_FIELD_NUMBER = 2;
        private static volatile Parser<Content> PARSER;
        private int messageDetailsCase_ = 0;
        private Object messageDetails_;

        private Content() {
        }

        public enum MessageDetailsCase {
            BANNER(1),
            MODAL(2),
            IMAGE_ONLY(3),
            CARD(4),
            MESSAGEDETAILS_NOT_SET(0);
            
            private final int value;

            private MessageDetailsCase(int value2) {
                this.value = value2;
            }

            @Deprecated
            public static MessageDetailsCase valueOf(int value2) {
                return forNumber(value2);
            }

            public static MessageDetailsCase forNumber(int value2) {
                if (value2 == 0) {
                    return MESSAGEDETAILS_NOT_SET;
                }
                if (value2 == 1) {
                    return BANNER;
                }
                if (value2 == 2) {
                    return MODAL;
                }
                if (value2 == 3) {
                    return IMAGE_ONLY;
                }
                if (value2 != 4) {
                    return null;
                }
                return CARD;
            }

            public int getNumber() {
                return this.value;
            }
        }

        public MessageDetailsCase getMessageDetailsCase() {
            return MessageDetailsCase.forNumber(this.messageDetailsCase_);
        }

        /* access modifiers changed from: private */
        public void clearMessageDetails() {
            this.messageDetailsCase_ = 0;
            this.messageDetails_ = null;
        }

        public boolean hasBanner() {
            return this.messageDetailsCase_ == 1;
        }

        public BannerMessage getBanner() {
            if (this.messageDetailsCase_ == 1) {
                return (BannerMessage) this.messageDetails_;
            }
            return BannerMessage.getDefaultInstance();
        }

        /* access modifiers changed from: private */
        public void setBanner(BannerMessage value) {
            value.getClass();
            this.messageDetails_ = value;
            this.messageDetailsCase_ = 1;
        }

        /* access modifiers changed from: private */
        public void mergeBanner(BannerMessage value) {
            value.getClass();
            if (this.messageDetailsCase_ != 1 || this.messageDetails_ == BannerMessage.getDefaultInstance()) {
                this.messageDetails_ = value;
            } else {
                this.messageDetails_ = ((BannerMessage.Builder) BannerMessage.newBuilder((BannerMessage) this.messageDetails_).mergeFrom(value)).buildPartial();
            }
            this.messageDetailsCase_ = 1;
        }

        /* access modifiers changed from: private */
        public void clearBanner() {
            if (this.messageDetailsCase_ == 1) {
                this.messageDetailsCase_ = 0;
                this.messageDetails_ = null;
            }
        }

        public boolean hasModal() {
            return this.messageDetailsCase_ == 2;
        }

        public ModalMessage getModal() {
            if (this.messageDetailsCase_ == 2) {
                return (ModalMessage) this.messageDetails_;
            }
            return ModalMessage.getDefaultInstance();
        }

        /* access modifiers changed from: private */
        public void setModal(ModalMessage value) {
            value.getClass();
            this.messageDetails_ = value;
            this.messageDetailsCase_ = 2;
        }

        /* access modifiers changed from: private */
        public void mergeModal(ModalMessage value) {
            value.getClass();
            if (this.messageDetailsCase_ != 2 || this.messageDetails_ == ModalMessage.getDefaultInstance()) {
                this.messageDetails_ = value;
            } else {
                this.messageDetails_ = ((ModalMessage.Builder) ModalMessage.newBuilder((ModalMessage) this.messageDetails_).mergeFrom(value)).buildPartial();
            }
            this.messageDetailsCase_ = 2;
        }

        /* access modifiers changed from: private */
        public void clearModal() {
            if (this.messageDetailsCase_ == 2) {
                this.messageDetailsCase_ = 0;
                this.messageDetails_ = null;
            }
        }

        public boolean hasImageOnly() {
            return this.messageDetailsCase_ == 3;
        }

        public ImageOnlyMessage getImageOnly() {
            if (this.messageDetailsCase_ == 3) {
                return (ImageOnlyMessage) this.messageDetails_;
            }
            return ImageOnlyMessage.getDefaultInstance();
        }

        /* access modifiers changed from: private */
        public void setImageOnly(ImageOnlyMessage value) {
            value.getClass();
            this.messageDetails_ = value;
            this.messageDetailsCase_ = 3;
        }

        /* access modifiers changed from: private */
        public void mergeImageOnly(ImageOnlyMessage value) {
            value.getClass();
            if (this.messageDetailsCase_ != 3 || this.messageDetails_ == ImageOnlyMessage.getDefaultInstance()) {
                this.messageDetails_ = value;
            } else {
                this.messageDetails_ = ((ImageOnlyMessage.Builder) ImageOnlyMessage.newBuilder((ImageOnlyMessage) this.messageDetails_).mergeFrom(value)).buildPartial();
            }
            this.messageDetailsCase_ = 3;
        }

        /* access modifiers changed from: private */
        public void clearImageOnly() {
            if (this.messageDetailsCase_ == 3) {
                this.messageDetailsCase_ = 0;
                this.messageDetails_ = null;
            }
        }

        public boolean hasCard() {
            return this.messageDetailsCase_ == 4;
        }

        public CardMessage getCard() {
            if (this.messageDetailsCase_ == 4) {
                return (CardMessage) this.messageDetails_;
            }
            return CardMessage.getDefaultInstance();
        }

        /* access modifiers changed from: private */
        public void setCard(CardMessage value) {
            value.getClass();
            this.messageDetails_ = value;
            this.messageDetailsCase_ = 4;
        }

        /* access modifiers changed from: private */
        public void mergeCard(CardMessage value) {
            value.getClass();
            if (this.messageDetailsCase_ != 4 || this.messageDetails_ == CardMessage.getDefaultInstance()) {
                this.messageDetails_ = value;
            } else {
                this.messageDetails_ = ((CardMessage.Builder) CardMessage.newBuilder((CardMessage) this.messageDetails_).mergeFrom(value)).buildPartial();
            }
            this.messageDetailsCase_ = 4;
        }

        /* access modifiers changed from: private */
        public void clearCard() {
            if (this.messageDetailsCase_ == 4) {
                this.messageDetailsCase_ = 0;
                this.messageDetails_ = null;
            }
        }

        public static Content parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Content) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Content parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Content) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Content parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Content) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Content parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Content) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Content parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Content) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Content parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Content) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Content parseFrom(InputStream input) throws IOException {
            return (Content) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Content parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Content) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Content parseDelimitedFrom(InputStream input) throws IOException {
            return (Content) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Content parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Content) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Content parseFrom(CodedInputStream input) throws IOException {
            return (Content) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Content parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Content) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Content prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Content, Builder> implements ContentOrBuilder {
            /* synthetic */ Builder(C39381 x0) {
                this();
            }

            private Builder() {
                super(Content.DEFAULT_INSTANCE);
            }

            public MessageDetailsCase getMessageDetailsCase() {
                return ((Content) this.instance).getMessageDetailsCase();
            }

            public Builder clearMessageDetails() {
                copyOnWrite();
                ((Content) this.instance).clearMessageDetails();
                return this;
            }

            public boolean hasBanner() {
                return ((Content) this.instance).hasBanner();
            }

            public BannerMessage getBanner() {
                return ((Content) this.instance).getBanner();
            }

            public Builder setBanner(BannerMessage value) {
                copyOnWrite();
                ((Content) this.instance).setBanner(value);
                return this;
            }

            public Builder setBanner(BannerMessage.Builder builderForValue) {
                copyOnWrite();
                ((Content) this.instance).setBanner((BannerMessage) builderForValue.build());
                return this;
            }

            public Builder mergeBanner(BannerMessage value) {
                copyOnWrite();
                ((Content) this.instance).mergeBanner(value);
                return this;
            }

            public Builder clearBanner() {
                copyOnWrite();
                ((Content) this.instance).clearBanner();
                return this;
            }

            public boolean hasModal() {
                return ((Content) this.instance).hasModal();
            }

            public ModalMessage getModal() {
                return ((Content) this.instance).getModal();
            }

            public Builder setModal(ModalMessage value) {
                copyOnWrite();
                ((Content) this.instance).setModal(value);
                return this;
            }

            public Builder setModal(ModalMessage.Builder builderForValue) {
                copyOnWrite();
                ((Content) this.instance).setModal((ModalMessage) builderForValue.build());
                return this;
            }

            public Builder mergeModal(ModalMessage value) {
                copyOnWrite();
                ((Content) this.instance).mergeModal(value);
                return this;
            }

            public Builder clearModal() {
                copyOnWrite();
                ((Content) this.instance).clearModal();
                return this;
            }

            public boolean hasImageOnly() {
                return ((Content) this.instance).hasImageOnly();
            }

            public ImageOnlyMessage getImageOnly() {
                return ((Content) this.instance).getImageOnly();
            }

            public Builder setImageOnly(ImageOnlyMessage value) {
                copyOnWrite();
                ((Content) this.instance).setImageOnly(value);
                return this;
            }

            public Builder setImageOnly(ImageOnlyMessage.Builder builderForValue) {
                copyOnWrite();
                ((Content) this.instance).setImageOnly((ImageOnlyMessage) builderForValue.build());
                return this;
            }

            public Builder mergeImageOnly(ImageOnlyMessage value) {
                copyOnWrite();
                ((Content) this.instance).mergeImageOnly(value);
                return this;
            }

            public Builder clearImageOnly() {
                copyOnWrite();
                ((Content) this.instance).clearImageOnly();
                return this;
            }

            public boolean hasCard() {
                return ((Content) this.instance).hasCard();
            }

            public CardMessage getCard() {
                return ((Content) this.instance).getCard();
            }

            public Builder setCard(CardMessage value) {
                copyOnWrite();
                ((Content) this.instance).setCard(value);
                return this;
            }

            public Builder setCard(CardMessage.Builder builderForValue) {
                copyOnWrite();
                ((Content) this.instance).setCard((CardMessage) builderForValue.build());
                return this;
            }

            public Builder mergeCard(CardMessage value) {
                copyOnWrite();
                ((Content) this.instance).mergeCard(value);
                return this;
            }

            public Builder clearCard() {
                copyOnWrite();
                ((Content) this.instance).clearCard();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39381.f1739xa1df5c61[method.ordinal()]) {
                case 1:
                    return new Content();
                case 2:
                    return new Builder((C39381) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0001\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001<\u0000\u0002<\u0000\u0003<\u0000\u0004<\u0000", new Object[]{"messageDetails_", "messageDetailsCase_", BannerMessage.class, ModalMessage.class, ImageOnlyMessage.class, CardMessage.class});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Content> parser = PARSER;
                    if (parser == null) {
                        synchronized (Content.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            Content defaultInstance = new Content();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(Content.class, defaultInstance);
        }

        public static Content getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Content> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* renamed from: com.google.firebase.inappmessaging.MessagesProto$1 */
    static /* synthetic */ class C39381 {

        /* renamed from: $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke */
        static final /* synthetic */ int[] f1739xa1df5c61;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            f1739xa1df5c61 = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1739xa1df5c61[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1739xa1df5c61[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1739xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                f1739xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                f1739xa1df5c61[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
            try {
                f1739xa1df5c61[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static final class CardMessage extends GeneratedMessageLite<CardMessage, Builder> implements CardMessageOrBuilder {
        public static final int BACKGROUND_HEX_COLOR_FIELD_NUMBER = 5;
        public static final int BODY_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final CardMessage DEFAULT_INSTANCE;
        public static final int LANDSCAPE_IMAGE_URL_FIELD_NUMBER = 4;
        private static volatile Parser<CardMessage> PARSER = null;
        public static final int PORTRAIT_IMAGE_URL_FIELD_NUMBER = 3;
        public static final int PRIMARY_ACTION_BUTTON_FIELD_NUMBER = 6;
        public static final int PRIMARY_ACTION_FIELD_NUMBER = 7;
        public static final int SECONDARY_ACTION_BUTTON_FIELD_NUMBER = 8;
        public static final int SECONDARY_ACTION_FIELD_NUMBER = 9;
        public static final int TITLE_FIELD_NUMBER = 1;
        private String backgroundHexColor_ = "";
        private Text body_;
        private String landscapeImageUrl_ = "";
        private String portraitImageUrl_ = "";
        private Button primaryActionButton_;
        private Action primaryAction_;
        private Button secondaryActionButton_;
        private Action secondaryAction_;
        private Text title_;

        private CardMessage() {
        }

        public boolean hasTitle() {
            return this.title_ != null;
        }

        public Text getTitle() {
            Text text = this.title_;
            return text == null ? Text.getDefaultInstance() : text;
        }

        /* access modifiers changed from: private */
        public void setTitle(Text value) {
            value.getClass();
            this.title_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeTitle(Text value) {
            value.getClass();
            Text text = this.title_;
            if (text == null || text == Text.getDefaultInstance()) {
                this.title_ = value;
            } else {
                this.title_ = (Text) ((Text.Builder) Text.newBuilder(this.title_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearTitle() {
            this.title_ = null;
        }

        public boolean hasBody() {
            return this.body_ != null;
        }

        public Text getBody() {
            Text text = this.body_;
            return text == null ? Text.getDefaultInstance() : text;
        }

        /* access modifiers changed from: private */
        public void setBody(Text value) {
            value.getClass();
            this.body_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeBody(Text value) {
            value.getClass();
            Text text = this.body_;
            if (text == null || text == Text.getDefaultInstance()) {
                this.body_ = value;
            } else {
                this.body_ = (Text) ((Text.Builder) Text.newBuilder(this.body_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearBody() {
            this.body_ = null;
        }

        public String getPortraitImageUrl() {
            return this.portraitImageUrl_;
        }

        public ByteString getPortraitImageUrlBytes() {
            return ByteString.copyFromUtf8(this.portraitImageUrl_);
        }

        /* access modifiers changed from: private */
        public void setPortraitImageUrl(String value) {
            value.getClass();
            this.portraitImageUrl_ = value;
        }

        /* access modifiers changed from: private */
        public void clearPortraitImageUrl() {
            this.portraitImageUrl_ = getDefaultInstance().getPortraitImageUrl();
        }

        /* access modifiers changed from: private */
        public void setPortraitImageUrlBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.portraitImageUrl_ = value.toStringUtf8();
        }

        public String getLandscapeImageUrl() {
            return this.landscapeImageUrl_;
        }

        public ByteString getLandscapeImageUrlBytes() {
            return ByteString.copyFromUtf8(this.landscapeImageUrl_);
        }

        /* access modifiers changed from: private */
        public void setLandscapeImageUrl(String value) {
            value.getClass();
            this.landscapeImageUrl_ = value;
        }

        /* access modifiers changed from: private */
        public void clearLandscapeImageUrl() {
            this.landscapeImageUrl_ = getDefaultInstance().getLandscapeImageUrl();
        }

        /* access modifiers changed from: private */
        public void setLandscapeImageUrlBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.landscapeImageUrl_ = value.toStringUtf8();
        }

        public String getBackgroundHexColor() {
            return this.backgroundHexColor_;
        }

        public ByteString getBackgroundHexColorBytes() {
            return ByteString.copyFromUtf8(this.backgroundHexColor_);
        }

        /* access modifiers changed from: private */
        public void setBackgroundHexColor(String value) {
            value.getClass();
            this.backgroundHexColor_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBackgroundHexColor() {
            this.backgroundHexColor_ = getDefaultInstance().getBackgroundHexColor();
        }

        /* access modifiers changed from: private */
        public void setBackgroundHexColorBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.backgroundHexColor_ = value.toStringUtf8();
        }

        public boolean hasPrimaryActionButton() {
            return this.primaryActionButton_ != null;
        }

        public Button getPrimaryActionButton() {
            Button button = this.primaryActionButton_;
            return button == null ? Button.getDefaultInstance() : button;
        }

        /* access modifiers changed from: private */
        public void setPrimaryActionButton(Button value) {
            value.getClass();
            this.primaryActionButton_ = value;
        }

        /* access modifiers changed from: private */
        public void mergePrimaryActionButton(Button value) {
            value.getClass();
            Button button = this.primaryActionButton_;
            if (button == null || button == Button.getDefaultInstance()) {
                this.primaryActionButton_ = value;
            } else {
                this.primaryActionButton_ = (Button) ((Button.Builder) Button.newBuilder(this.primaryActionButton_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearPrimaryActionButton() {
            this.primaryActionButton_ = null;
        }

        public boolean hasPrimaryAction() {
            return this.primaryAction_ != null;
        }

        public Action getPrimaryAction() {
            Action action = this.primaryAction_;
            return action == null ? Action.getDefaultInstance() : action;
        }

        /* access modifiers changed from: private */
        public void setPrimaryAction(Action value) {
            value.getClass();
            this.primaryAction_ = value;
        }

        /* access modifiers changed from: private */
        public void mergePrimaryAction(Action value) {
            value.getClass();
            Action action = this.primaryAction_;
            if (action == null || action == Action.getDefaultInstance()) {
                this.primaryAction_ = value;
            } else {
                this.primaryAction_ = (Action) ((Action.Builder) Action.newBuilder(this.primaryAction_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearPrimaryAction() {
            this.primaryAction_ = null;
        }

        public boolean hasSecondaryActionButton() {
            return this.secondaryActionButton_ != null;
        }

        public Button getSecondaryActionButton() {
            Button button = this.secondaryActionButton_;
            return button == null ? Button.getDefaultInstance() : button;
        }

        /* access modifiers changed from: private */
        public void setSecondaryActionButton(Button value) {
            value.getClass();
            this.secondaryActionButton_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeSecondaryActionButton(Button value) {
            value.getClass();
            Button button = this.secondaryActionButton_;
            if (button == null || button == Button.getDefaultInstance()) {
                this.secondaryActionButton_ = value;
            } else {
                this.secondaryActionButton_ = (Button) ((Button.Builder) Button.newBuilder(this.secondaryActionButton_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearSecondaryActionButton() {
            this.secondaryActionButton_ = null;
        }

        public boolean hasSecondaryAction() {
            return this.secondaryAction_ != null;
        }

        public Action getSecondaryAction() {
            Action action = this.secondaryAction_;
            return action == null ? Action.getDefaultInstance() : action;
        }

        /* access modifiers changed from: private */
        public void setSecondaryAction(Action value) {
            value.getClass();
            this.secondaryAction_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeSecondaryAction(Action value) {
            value.getClass();
            Action action = this.secondaryAction_;
            if (action == null || action == Action.getDefaultInstance()) {
                this.secondaryAction_ = value;
            } else {
                this.secondaryAction_ = (Action) ((Action.Builder) Action.newBuilder(this.secondaryAction_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearSecondaryAction() {
            this.secondaryAction_ = null;
        }

        public static CardMessage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (CardMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CardMessage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CardMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CardMessage parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (CardMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CardMessage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CardMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CardMessage parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (CardMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static CardMessage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (CardMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static CardMessage parseFrom(InputStream input) throws IOException {
            return (CardMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CardMessage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CardMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CardMessage parseDelimitedFrom(InputStream input) throws IOException {
            return (CardMessage) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static CardMessage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CardMessage) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static CardMessage parseFrom(CodedInputStream input) throws IOException {
            return (CardMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static CardMessage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (CardMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(CardMessage prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<CardMessage, Builder> implements CardMessageOrBuilder {
            /* synthetic */ Builder(C39381 x0) {
                this();
            }

            private Builder() {
                super(CardMessage.DEFAULT_INSTANCE);
            }

            public boolean hasTitle() {
                return ((CardMessage) this.instance).hasTitle();
            }

            public Text getTitle() {
                return ((CardMessage) this.instance).getTitle();
            }

            public Builder setTitle(Text value) {
                copyOnWrite();
                ((CardMessage) this.instance).setTitle(value);
                return this;
            }

            public Builder setTitle(Text.Builder builderForValue) {
                copyOnWrite();
                ((CardMessage) this.instance).setTitle((Text) builderForValue.build());
                return this;
            }

            public Builder mergeTitle(Text value) {
                copyOnWrite();
                ((CardMessage) this.instance).mergeTitle(value);
                return this;
            }

            public Builder clearTitle() {
                copyOnWrite();
                ((CardMessage) this.instance).clearTitle();
                return this;
            }

            public boolean hasBody() {
                return ((CardMessage) this.instance).hasBody();
            }

            public Text getBody() {
                return ((CardMessage) this.instance).getBody();
            }

            public Builder setBody(Text value) {
                copyOnWrite();
                ((CardMessage) this.instance).setBody(value);
                return this;
            }

            public Builder setBody(Text.Builder builderForValue) {
                copyOnWrite();
                ((CardMessage) this.instance).setBody((Text) builderForValue.build());
                return this;
            }

            public Builder mergeBody(Text value) {
                copyOnWrite();
                ((CardMessage) this.instance).mergeBody(value);
                return this;
            }

            public Builder clearBody() {
                copyOnWrite();
                ((CardMessage) this.instance).clearBody();
                return this;
            }

            public String getPortraitImageUrl() {
                return ((CardMessage) this.instance).getPortraitImageUrl();
            }

            public ByteString getPortraitImageUrlBytes() {
                return ((CardMessage) this.instance).getPortraitImageUrlBytes();
            }

            public Builder setPortraitImageUrl(String value) {
                copyOnWrite();
                ((CardMessage) this.instance).setPortraitImageUrl(value);
                return this;
            }

            public Builder clearPortraitImageUrl() {
                copyOnWrite();
                ((CardMessage) this.instance).clearPortraitImageUrl();
                return this;
            }

            public Builder setPortraitImageUrlBytes(ByteString value) {
                copyOnWrite();
                ((CardMessage) this.instance).setPortraitImageUrlBytes(value);
                return this;
            }

            public String getLandscapeImageUrl() {
                return ((CardMessage) this.instance).getLandscapeImageUrl();
            }

            public ByteString getLandscapeImageUrlBytes() {
                return ((CardMessage) this.instance).getLandscapeImageUrlBytes();
            }

            public Builder setLandscapeImageUrl(String value) {
                copyOnWrite();
                ((CardMessage) this.instance).setLandscapeImageUrl(value);
                return this;
            }

            public Builder clearLandscapeImageUrl() {
                copyOnWrite();
                ((CardMessage) this.instance).clearLandscapeImageUrl();
                return this;
            }

            public Builder setLandscapeImageUrlBytes(ByteString value) {
                copyOnWrite();
                ((CardMessage) this.instance).setLandscapeImageUrlBytes(value);
                return this;
            }

            public String getBackgroundHexColor() {
                return ((CardMessage) this.instance).getBackgroundHexColor();
            }

            public ByteString getBackgroundHexColorBytes() {
                return ((CardMessage) this.instance).getBackgroundHexColorBytes();
            }

            public Builder setBackgroundHexColor(String value) {
                copyOnWrite();
                ((CardMessage) this.instance).setBackgroundHexColor(value);
                return this;
            }

            public Builder clearBackgroundHexColor() {
                copyOnWrite();
                ((CardMessage) this.instance).clearBackgroundHexColor();
                return this;
            }

            public Builder setBackgroundHexColorBytes(ByteString value) {
                copyOnWrite();
                ((CardMessage) this.instance).setBackgroundHexColorBytes(value);
                return this;
            }

            public boolean hasPrimaryActionButton() {
                return ((CardMessage) this.instance).hasPrimaryActionButton();
            }

            public Button getPrimaryActionButton() {
                return ((CardMessage) this.instance).getPrimaryActionButton();
            }

            public Builder setPrimaryActionButton(Button value) {
                copyOnWrite();
                ((CardMessage) this.instance).setPrimaryActionButton(value);
                return this;
            }

            public Builder setPrimaryActionButton(Button.Builder builderForValue) {
                copyOnWrite();
                ((CardMessage) this.instance).setPrimaryActionButton((Button) builderForValue.build());
                return this;
            }

            public Builder mergePrimaryActionButton(Button value) {
                copyOnWrite();
                ((CardMessage) this.instance).mergePrimaryActionButton(value);
                return this;
            }

            public Builder clearPrimaryActionButton() {
                copyOnWrite();
                ((CardMessage) this.instance).clearPrimaryActionButton();
                return this;
            }

            public boolean hasPrimaryAction() {
                return ((CardMessage) this.instance).hasPrimaryAction();
            }

            public Action getPrimaryAction() {
                return ((CardMessage) this.instance).getPrimaryAction();
            }

            public Builder setPrimaryAction(Action value) {
                copyOnWrite();
                ((CardMessage) this.instance).setPrimaryAction(value);
                return this;
            }

            public Builder setPrimaryAction(Action.Builder builderForValue) {
                copyOnWrite();
                ((CardMessage) this.instance).setPrimaryAction((Action) builderForValue.build());
                return this;
            }

            public Builder mergePrimaryAction(Action value) {
                copyOnWrite();
                ((CardMessage) this.instance).mergePrimaryAction(value);
                return this;
            }

            public Builder clearPrimaryAction() {
                copyOnWrite();
                ((CardMessage) this.instance).clearPrimaryAction();
                return this;
            }

            public boolean hasSecondaryActionButton() {
                return ((CardMessage) this.instance).hasSecondaryActionButton();
            }

            public Button getSecondaryActionButton() {
                return ((CardMessage) this.instance).getSecondaryActionButton();
            }

            public Builder setSecondaryActionButton(Button value) {
                copyOnWrite();
                ((CardMessage) this.instance).setSecondaryActionButton(value);
                return this;
            }

            public Builder setSecondaryActionButton(Button.Builder builderForValue) {
                copyOnWrite();
                ((CardMessage) this.instance).setSecondaryActionButton((Button) builderForValue.build());
                return this;
            }

            public Builder mergeSecondaryActionButton(Button value) {
                copyOnWrite();
                ((CardMessage) this.instance).mergeSecondaryActionButton(value);
                return this;
            }

            public Builder clearSecondaryActionButton() {
                copyOnWrite();
                ((CardMessage) this.instance).clearSecondaryActionButton();
                return this;
            }

            public boolean hasSecondaryAction() {
                return ((CardMessage) this.instance).hasSecondaryAction();
            }

            public Action getSecondaryAction() {
                return ((CardMessage) this.instance).getSecondaryAction();
            }

            public Builder setSecondaryAction(Action value) {
                copyOnWrite();
                ((CardMessage) this.instance).setSecondaryAction(value);
                return this;
            }

            public Builder setSecondaryAction(Action.Builder builderForValue) {
                copyOnWrite();
                ((CardMessage) this.instance).setSecondaryAction((Action) builderForValue.build());
                return this;
            }

            public Builder mergeSecondaryAction(Action value) {
                copyOnWrite();
                ((CardMessage) this.instance).mergeSecondaryAction(value);
                return this;
            }

            public Builder clearSecondaryAction() {
                copyOnWrite();
                ((CardMessage) this.instance).clearSecondaryAction();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39381.f1739xa1df5c61[method.ordinal()]) {
                case 1:
                    return new CardMessage();
                case 2:
                    return new Builder((C39381) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\t\u0000\u0000\u0001\t\t\u0000\u0000\u0000\u0001\t\u0002\t\u0003Ȉ\u0004Ȉ\u0005Ȉ\u0006\t\u0007\t\b\t\t\t", new Object[]{"title_", "body_", "portraitImageUrl_", "landscapeImageUrl_", "backgroundHexColor_", "primaryActionButton_", "primaryAction_", "secondaryActionButton_", "secondaryAction_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<CardMessage> parser = PARSER;
                    if (parser == null) {
                        synchronized (CardMessage.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            CardMessage defaultInstance = new CardMessage();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(CardMessage.class, defaultInstance);
        }

        public static CardMessage getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<CardMessage> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class ImageOnlyMessage extends GeneratedMessageLite<ImageOnlyMessage, Builder> implements ImageOnlyMessageOrBuilder {
        public static final int ACTION_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final ImageOnlyMessage DEFAULT_INSTANCE;
        public static final int IMAGE_URL_FIELD_NUMBER = 1;
        private static volatile Parser<ImageOnlyMessage> PARSER;
        private Action action_;
        private String imageUrl_ = "";

        private ImageOnlyMessage() {
        }

        public String getImageUrl() {
            return this.imageUrl_;
        }

        public ByteString getImageUrlBytes() {
            return ByteString.copyFromUtf8(this.imageUrl_);
        }

        /* access modifiers changed from: private */
        public void setImageUrl(String value) {
            value.getClass();
            this.imageUrl_ = value;
        }

        /* access modifiers changed from: private */
        public void clearImageUrl() {
            this.imageUrl_ = getDefaultInstance().getImageUrl();
        }

        /* access modifiers changed from: private */
        public void setImageUrlBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.imageUrl_ = value.toStringUtf8();
        }

        public boolean hasAction() {
            return this.action_ != null;
        }

        public Action getAction() {
            Action action = this.action_;
            return action == null ? Action.getDefaultInstance() : action;
        }

        /* access modifiers changed from: private */
        public void setAction(Action value) {
            value.getClass();
            this.action_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeAction(Action value) {
            value.getClass();
            Action action = this.action_;
            if (action == null || action == Action.getDefaultInstance()) {
                this.action_ = value;
            } else {
                this.action_ = (Action) ((Action.Builder) Action.newBuilder(this.action_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearAction() {
            this.action_ = null;
        }

        public static ImageOnlyMessage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ImageOnlyMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ImageOnlyMessage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ImageOnlyMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ImageOnlyMessage parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ImageOnlyMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ImageOnlyMessage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ImageOnlyMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ImageOnlyMessage parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ImageOnlyMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ImageOnlyMessage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ImageOnlyMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ImageOnlyMessage parseFrom(InputStream input) throws IOException {
            return (ImageOnlyMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ImageOnlyMessage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ImageOnlyMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ImageOnlyMessage parseDelimitedFrom(InputStream input) throws IOException {
            return (ImageOnlyMessage) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ImageOnlyMessage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ImageOnlyMessage) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ImageOnlyMessage parseFrom(CodedInputStream input) throws IOException {
            return (ImageOnlyMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ImageOnlyMessage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ImageOnlyMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ImageOnlyMessage prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ImageOnlyMessage, Builder> implements ImageOnlyMessageOrBuilder {
            /* synthetic */ Builder(C39381 x0) {
                this();
            }

            private Builder() {
                super(ImageOnlyMessage.DEFAULT_INSTANCE);
            }

            public String getImageUrl() {
                return ((ImageOnlyMessage) this.instance).getImageUrl();
            }

            public ByteString getImageUrlBytes() {
                return ((ImageOnlyMessage) this.instance).getImageUrlBytes();
            }

            public Builder setImageUrl(String value) {
                copyOnWrite();
                ((ImageOnlyMessage) this.instance).setImageUrl(value);
                return this;
            }

            public Builder clearImageUrl() {
                copyOnWrite();
                ((ImageOnlyMessage) this.instance).clearImageUrl();
                return this;
            }

            public Builder setImageUrlBytes(ByteString value) {
                copyOnWrite();
                ((ImageOnlyMessage) this.instance).setImageUrlBytes(value);
                return this;
            }

            public boolean hasAction() {
                return ((ImageOnlyMessage) this.instance).hasAction();
            }

            public Action getAction() {
                return ((ImageOnlyMessage) this.instance).getAction();
            }

            public Builder setAction(Action value) {
                copyOnWrite();
                ((ImageOnlyMessage) this.instance).setAction(value);
                return this;
            }

            public Builder setAction(Action.Builder builderForValue) {
                copyOnWrite();
                ((ImageOnlyMessage) this.instance).setAction((Action) builderForValue.build());
                return this;
            }

            public Builder mergeAction(Action value) {
                copyOnWrite();
                ((ImageOnlyMessage) this.instance).mergeAction(value);
                return this;
            }

            public Builder clearAction() {
                copyOnWrite();
                ((ImageOnlyMessage) this.instance).clearAction();
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39381.f1739xa1df5c61[method.ordinal()]) {
                case 1:
                    return new ImageOnlyMessage();
                case 2:
                    return new Builder((C39381) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002\t", new Object[]{"imageUrl_", "action_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ImageOnlyMessage> parser = PARSER;
                    if (parser == null) {
                        synchronized (ImageOnlyMessage.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            ImageOnlyMessage defaultInstance = new ImageOnlyMessage();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ImageOnlyMessage.class, defaultInstance);
        }

        public static ImageOnlyMessage getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ImageOnlyMessage> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class BannerMessage extends GeneratedMessageLite<BannerMessage, Builder> implements BannerMessageOrBuilder {
        public static final int ACTION_FIELD_NUMBER = 4;
        public static final int BACKGROUND_HEX_COLOR_FIELD_NUMBER = 5;
        public static final int BODY_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final BannerMessage DEFAULT_INSTANCE;
        public static final int IMAGE_URL_FIELD_NUMBER = 3;
        private static volatile Parser<BannerMessage> PARSER = null;
        public static final int TITLE_FIELD_NUMBER = 1;
        private Action action_;
        private String backgroundHexColor_ = "";
        private Text body_;
        private String imageUrl_ = "";
        private Text title_;

        private BannerMessage() {
        }

        public boolean hasTitle() {
            return this.title_ != null;
        }

        public Text getTitle() {
            Text text = this.title_;
            return text == null ? Text.getDefaultInstance() : text;
        }

        /* access modifiers changed from: private */
        public void setTitle(Text value) {
            value.getClass();
            this.title_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeTitle(Text value) {
            value.getClass();
            Text text = this.title_;
            if (text == null || text == Text.getDefaultInstance()) {
                this.title_ = value;
            } else {
                this.title_ = (Text) ((Text.Builder) Text.newBuilder(this.title_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearTitle() {
            this.title_ = null;
        }

        public boolean hasBody() {
            return this.body_ != null;
        }

        public Text getBody() {
            Text text = this.body_;
            return text == null ? Text.getDefaultInstance() : text;
        }

        /* access modifiers changed from: private */
        public void setBody(Text value) {
            value.getClass();
            this.body_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeBody(Text value) {
            value.getClass();
            Text text = this.body_;
            if (text == null || text == Text.getDefaultInstance()) {
                this.body_ = value;
            } else {
                this.body_ = (Text) ((Text.Builder) Text.newBuilder(this.body_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearBody() {
            this.body_ = null;
        }

        public String getImageUrl() {
            return this.imageUrl_;
        }

        public ByteString getImageUrlBytes() {
            return ByteString.copyFromUtf8(this.imageUrl_);
        }

        /* access modifiers changed from: private */
        public void setImageUrl(String value) {
            value.getClass();
            this.imageUrl_ = value;
        }

        /* access modifiers changed from: private */
        public void clearImageUrl() {
            this.imageUrl_ = getDefaultInstance().getImageUrl();
        }

        /* access modifiers changed from: private */
        public void setImageUrlBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.imageUrl_ = value.toStringUtf8();
        }

        public boolean hasAction() {
            return this.action_ != null;
        }

        public Action getAction() {
            Action action = this.action_;
            return action == null ? Action.getDefaultInstance() : action;
        }

        /* access modifiers changed from: private */
        public void setAction(Action value) {
            value.getClass();
            this.action_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeAction(Action value) {
            value.getClass();
            Action action = this.action_;
            if (action == null || action == Action.getDefaultInstance()) {
                this.action_ = value;
            } else {
                this.action_ = (Action) ((Action.Builder) Action.newBuilder(this.action_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearAction() {
            this.action_ = null;
        }

        public String getBackgroundHexColor() {
            return this.backgroundHexColor_;
        }

        public ByteString getBackgroundHexColorBytes() {
            return ByteString.copyFromUtf8(this.backgroundHexColor_);
        }

        /* access modifiers changed from: private */
        public void setBackgroundHexColor(String value) {
            value.getClass();
            this.backgroundHexColor_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBackgroundHexColor() {
            this.backgroundHexColor_ = getDefaultInstance().getBackgroundHexColor();
        }

        /* access modifiers changed from: private */
        public void setBackgroundHexColorBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.backgroundHexColor_ = value.toStringUtf8();
        }

        public static BannerMessage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (BannerMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BannerMessage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BannerMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BannerMessage parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (BannerMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BannerMessage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BannerMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BannerMessage parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (BannerMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static BannerMessage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (BannerMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static BannerMessage parseFrom(InputStream input) throws IOException {
            return (BannerMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BannerMessage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BannerMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BannerMessage parseDelimitedFrom(InputStream input) throws IOException {
            return (BannerMessage) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static BannerMessage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BannerMessage) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static BannerMessage parseFrom(CodedInputStream input) throws IOException {
            return (BannerMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static BannerMessage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (BannerMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(BannerMessage prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<BannerMessage, Builder> implements BannerMessageOrBuilder {
            /* synthetic */ Builder(C39381 x0) {
                this();
            }

            private Builder() {
                super(BannerMessage.DEFAULT_INSTANCE);
            }

            public boolean hasTitle() {
                return ((BannerMessage) this.instance).hasTitle();
            }

            public Text getTitle() {
                return ((BannerMessage) this.instance).getTitle();
            }

            public Builder setTitle(Text value) {
                copyOnWrite();
                ((BannerMessage) this.instance).setTitle(value);
                return this;
            }

            public Builder setTitle(Text.Builder builderForValue) {
                copyOnWrite();
                ((BannerMessage) this.instance).setTitle((Text) builderForValue.build());
                return this;
            }

            public Builder mergeTitle(Text value) {
                copyOnWrite();
                ((BannerMessage) this.instance).mergeTitle(value);
                return this;
            }

            public Builder clearTitle() {
                copyOnWrite();
                ((BannerMessage) this.instance).clearTitle();
                return this;
            }

            public boolean hasBody() {
                return ((BannerMessage) this.instance).hasBody();
            }

            public Text getBody() {
                return ((BannerMessage) this.instance).getBody();
            }

            public Builder setBody(Text value) {
                copyOnWrite();
                ((BannerMessage) this.instance).setBody(value);
                return this;
            }

            public Builder setBody(Text.Builder builderForValue) {
                copyOnWrite();
                ((BannerMessage) this.instance).setBody((Text) builderForValue.build());
                return this;
            }

            public Builder mergeBody(Text value) {
                copyOnWrite();
                ((BannerMessage) this.instance).mergeBody(value);
                return this;
            }

            public Builder clearBody() {
                copyOnWrite();
                ((BannerMessage) this.instance).clearBody();
                return this;
            }

            public String getImageUrl() {
                return ((BannerMessage) this.instance).getImageUrl();
            }

            public ByteString getImageUrlBytes() {
                return ((BannerMessage) this.instance).getImageUrlBytes();
            }

            public Builder setImageUrl(String value) {
                copyOnWrite();
                ((BannerMessage) this.instance).setImageUrl(value);
                return this;
            }

            public Builder clearImageUrl() {
                copyOnWrite();
                ((BannerMessage) this.instance).clearImageUrl();
                return this;
            }

            public Builder setImageUrlBytes(ByteString value) {
                copyOnWrite();
                ((BannerMessage) this.instance).setImageUrlBytes(value);
                return this;
            }

            public boolean hasAction() {
                return ((BannerMessage) this.instance).hasAction();
            }

            public Action getAction() {
                return ((BannerMessage) this.instance).getAction();
            }

            public Builder setAction(Action value) {
                copyOnWrite();
                ((BannerMessage) this.instance).setAction(value);
                return this;
            }

            public Builder setAction(Action.Builder builderForValue) {
                copyOnWrite();
                ((BannerMessage) this.instance).setAction((Action) builderForValue.build());
                return this;
            }

            public Builder mergeAction(Action value) {
                copyOnWrite();
                ((BannerMessage) this.instance).mergeAction(value);
                return this;
            }

            public Builder clearAction() {
                copyOnWrite();
                ((BannerMessage) this.instance).clearAction();
                return this;
            }

            public String getBackgroundHexColor() {
                return ((BannerMessage) this.instance).getBackgroundHexColor();
            }

            public ByteString getBackgroundHexColorBytes() {
                return ((BannerMessage) this.instance).getBackgroundHexColorBytes();
            }

            public Builder setBackgroundHexColor(String value) {
                copyOnWrite();
                ((BannerMessage) this.instance).setBackgroundHexColor(value);
                return this;
            }

            public Builder clearBackgroundHexColor() {
                copyOnWrite();
                ((BannerMessage) this.instance).clearBackgroundHexColor();
                return this;
            }

            public Builder setBackgroundHexColorBytes(ByteString value) {
                copyOnWrite();
                ((BannerMessage) this.instance).setBackgroundHexColorBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39381.f1739xa1df5c61[method.ordinal()]) {
                case 1:
                    return new BannerMessage();
                case 2:
                    return new Builder((C39381) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0005\u0000\u0000\u0001\u0005\u0005\u0000\u0000\u0000\u0001\t\u0002\t\u0003Ȉ\u0004\t\u0005Ȉ", new Object[]{"title_", "body_", "imageUrl_", "action_", "backgroundHexColor_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<BannerMessage> parser = PARSER;
                    if (parser == null) {
                        synchronized (BannerMessage.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            BannerMessage defaultInstance = new BannerMessage();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(BannerMessage.class, defaultInstance);
        }

        public static BannerMessage getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<BannerMessage> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class ModalMessage extends GeneratedMessageLite<ModalMessage, Builder> implements ModalMessageOrBuilder {
        public static final int ACTION_BUTTON_FIELD_NUMBER = 4;
        public static final int ACTION_FIELD_NUMBER = 5;
        public static final int BACKGROUND_HEX_COLOR_FIELD_NUMBER = 6;
        public static final int BODY_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final ModalMessage DEFAULT_INSTANCE;
        public static final int IMAGE_URL_FIELD_NUMBER = 3;
        private static volatile Parser<ModalMessage> PARSER = null;
        public static final int TITLE_FIELD_NUMBER = 1;
        private Button actionButton_;
        private Action action_;
        private String backgroundHexColor_ = "";
        private Text body_;
        private String imageUrl_ = "";
        private Text title_;

        private ModalMessage() {
        }

        public boolean hasTitle() {
            return this.title_ != null;
        }

        public Text getTitle() {
            Text text = this.title_;
            return text == null ? Text.getDefaultInstance() : text;
        }

        /* access modifiers changed from: private */
        public void setTitle(Text value) {
            value.getClass();
            this.title_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeTitle(Text value) {
            value.getClass();
            Text text = this.title_;
            if (text == null || text == Text.getDefaultInstance()) {
                this.title_ = value;
            } else {
                this.title_ = (Text) ((Text.Builder) Text.newBuilder(this.title_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearTitle() {
            this.title_ = null;
        }

        public boolean hasBody() {
            return this.body_ != null;
        }

        public Text getBody() {
            Text text = this.body_;
            return text == null ? Text.getDefaultInstance() : text;
        }

        /* access modifiers changed from: private */
        public void setBody(Text value) {
            value.getClass();
            this.body_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeBody(Text value) {
            value.getClass();
            Text text = this.body_;
            if (text == null || text == Text.getDefaultInstance()) {
                this.body_ = value;
            } else {
                this.body_ = (Text) ((Text.Builder) Text.newBuilder(this.body_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearBody() {
            this.body_ = null;
        }

        public String getImageUrl() {
            return this.imageUrl_;
        }

        public ByteString getImageUrlBytes() {
            return ByteString.copyFromUtf8(this.imageUrl_);
        }

        /* access modifiers changed from: private */
        public void setImageUrl(String value) {
            value.getClass();
            this.imageUrl_ = value;
        }

        /* access modifiers changed from: private */
        public void clearImageUrl() {
            this.imageUrl_ = getDefaultInstance().getImageUrl();
        }

        /* access modifiers changed from: private */
        public void setImageUrlBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.imageUrl_ = value.toStringUtf8();
        }

        public boolean hasActionButton() {
            return this.actionButton_ != null;
        }

        public Button getActionButton() {
            Button button = this.actionButton_;
            return button == null ? Button.getDefaultInstance() : button;
        }

        /* access modifiers changed from: private */
        public void setActionButton(Button value) {
            value.getClass();
            this.actionButton_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeActionButton(Button value) {
            value.getClass();
            Button button = this.actionButton_;
            if (button == null || button == Button.getDefaultInstance()) {
                this.actionButton_ = value;
            } else {
                this.actionButton_ = (Button) ((Button.Builder) Button.newBuilder(this.actionButton_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearActionButton() {
            this.actionButton_ = null;
        }

        public boolean hasAction() {
            return this.action_ != null;
        }

        public Action getAction() {
            Action action = this.action_;
            return action == null ? Action.getDefaultInstance() : action;
        }

        /* access modifiers changed from: private */
        public void setAction(Action value) {
            value.getClass();
            this.action_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeAction(Action value) {
            value.getClass();
            Action action = this.action_;
            if (action == null || action == Action.getDefaultInstance()) {
                this.action_ = value;
            } else {
                this.action_ = (Action) ((Action.Builder) Action.newBuilder(this.action_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearAction() {
            this.action_ = null;
        }

        public String getBackgroundHexColor() {
            return this.backgroundHexColor_;
        }

        public ByteString getBackgroundHexColorBytes() {
            return ByteString.copyFromUtf8(this.backgroundHexColor_);
        }

        /* access modifiers changed from: private */
        public void setBackgroundHexColor(String value) {
            value.getClass();
            this.backgroundHexColor_ = value;
        }

        /* access modifiers changed from: private */
        public void clearBackgroundHexColor() {
            this.backgroundHexColor_ = getDefaultInstance().getBackgroundHexColor();
        }

        /* access modifiers changed from: private */
        public void setBackgroundHexColorBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.backgroundHexColor_ = value.toStringUtf8();
        }

        public static ModalMessage parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (ModalMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ModalMessage parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ModalMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ModalMessage parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (ModalMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ModalMessage parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ModalMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ModalMessage parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (ModalMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static ModalMessage parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (ModalMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static ModalMessage parseFrom(InputStream input) throws IOException {
            return (ModalMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ModalMessage parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ModalMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ModalMessage parseDelimitedFrom(InputStream input) throws IOException {
            return (ModalMessage) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static ModalMessage parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ModalMessage) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static ModalMessage parseFrom(CodedInputStream input) throws IOException {
            return (ModalMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static ModalMessage parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (ModalMessage) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ModalMessage prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ModalMessage, Builder> implements ModalMessageOrBuilder {
            /* synthetic */ Builder(C39381 x0) {
                this();
            }

            private Builder() {
                super(ModalMessage.DEFAULT_INSTANCE);
            }

            public boolean hasTitle() {
                return ((ModalMessage) this.instance).hasTitle();
            }

            public Text getTitle() {
                return ((ModalMessage) this.instance).getTitle();
            }

            public Builder setTitle(Text value) {
                copyOnWrite();
                ((ModalMessage) this.instance).setTitle(value);
                return this;
            }

            public Builder setTitle(Text.Builder builderForValue) {
                copyOnWrite();
                ((ModalMessage) this.instance).setTitle((Text) builderForValue.build());
                return this;
            }

            public Builder mergeTitle(Text value) {
                copyOnWrite();
                ((ModalMessage) this.instance).mergeTitle(value);
                return this;
            }

            public Builder clearTitle() {
                copyOnWrite();
                ((ModalMessage) this.instance).clearTitle();
                return this;
            }

            public boolean hasBody() {
                return ((ModalMessage) this.instance).hasBody();
            }

            public Text getBody() {
                return ((ModalMessage) this.instance).getBody();
            }

            public Builder setBody(Text value) {
                copyOnWrite();
                ((ModalMessage) this.instance).setBody(value);
                return this;
            }

            public Builder setBody(Text.Builder builderForValue) {
                copyOnWrite();
                ((ModalMessage) this.instance).setBody((Text) builderForValue.build());
                return this;
            }

            public Builder mergeBody(Text value) {
                copyOnWrite();
                ((ModalMessage) this.instance).mergeBody(value);
                return this;
            }

            public Builder clearBody() {
                copyOnWrite();
                ((ModalMessage) this.instance).clearBody();
                return this;
            }

            public String getImageUrl() {
                return ((ModalMessage) this.instance).getImageUrl();
            }

            public ByteString getImageUrlBytes() {
                return ((ModalMessage) this.instance).getImageUrlBytes();
            }

            public Builder setImageUrl(String value) {
                copyOnWrite();
                ((ModalMessage) this.instance).setImageUrl(value);
                return this;
            }

            public Builder clearImageUrl() {
                copyOnWrite();
                ((ModalMessage) this.instance).clearImageUrl();
                return this;
            }

            public Builder setImageUrlBytes(ByteString value) {
                copyOnWrite();
                ((ModalMessage) this.instance).setImageUrlBytes(value);
                return this;
            }

            public boolean hasActionButton() {
                return ((ModalMessage) this.instance).hasActionButton();
            }

            public Button getActionButton() {
                return ((ModalMessage) this.instance).getActionButton();
            }

            public Builder setActionButton(Button value) {
                copyOnWrite();
                ((ModalMessage) this.instance).setActionButton(value);
                return this;
            }

            public Builder setActionButton(Button.Builder builderForValue) {
                copyOnWrite();
                ((ModalMessage) this.instance).setActionButton((Button) builderForValue.build());
                return this;
            }

            public Builder mergeActionButton(Button value) {
                copyOnWrite();
                ((ModalMessage) this.instance).mergeActionButton(value);
                return this;
            }

            public Builder clearActionButton() {
                copyOnWrite();
                ((ModalMessage) this.instance).clearActionButton();
                return this;
            }

            public boolean hasAction() {
                return ((ModalMessage) this.instance).hasAction();
            }

            public Action getAction() {
                return ((ModalMessage) this.instance).getAction();
            }

            public Builder setAction(Action value) {
                copyOnWrite();
                ((ModalMessage) this.instance).setAction(value);
                return this;
            }

            public Builder setAction(Action.Builder builderForValue) {
                copyOnWrite();
                ((ModalMessage) this.instance).setAction((Action) builderForValue.build());
                return this;
            }

            public Builder mergeAction(Action value) {
                copyOnWrite();
                ((ModalMessage) this.instance).mergeAction(value);
                return this;
            }

            public Builder clearAction() {
                copyOnWrite();
                ((ModalMessage) this.instance).clearAction();
                return this;
            }

            public String getBackgroundHexColor() {
                return ((ModalMessage) this.instance).getBackgroundHexColor();
            }

            public ByteString getBackgroundHexColorBytes() {
                return ((ModalMessage) this.instance).getBackgroundHexColorBytes();
            }

            public Builder setBackgroundHexColor(String value) {
                copyOnWrite();
                ((ModalMessage) this.instance).setBackgroundHexColor(value);
                return this;
            }

            public Builder clearBackgroundHexColor() {
                copyOnWrite();
                ((ModalMessage) this.instance).clearBackgroundHexColor();
                return this;
            }

            public Builder setBackgroundHexColorBytes(ByteString value) {
                copyOnWrite();
                ((ModalMessage) this.instance).setBackgroundHexColorBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39381.f1739xa1df5c61[method.ordinal()]) {
                case 1:
                    return new ModalMessage();
                case 2:
                    return new Builder((C39381) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0006\u0000\u0000\u0001\u0006\u0006\u0000\u0000\u0000\u0001\t\u0002\t\u0003Ȉ\u0004\t\u0005\t\u0006Ȉ", new Object[]{"title_", "body_", "imageUrl_", "actionButton_", "action_", "backgroundHexColor_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ModalMessage> parser = PARSER;
                    if (parser == null) {
                        synchronized (ModalMessage.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            ModalMessage defaultInstance = new ModalMessage();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(ModalMessage.class, defaultInstance);
        }

        public static ModalMessage getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<ModalMessage> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class Text extends GeneratedMessageLite<Text, Builder> implements TextOrBuilder {
        /* access modifiers changed from: private */
        public static final Text DEFAULT_INSTANCE;
        public static final int HEX_COLOR_FIELD_NUMBER = 2;
        private static volatile Parser<Text> PARSER = null;
        public static final int TEXT_FIELD_NUMBER = 1;
        private String hexColor_ = "";
        private String text_ = "";

        private Text() {
        }

        public String getText() {
            return this.text_;
        }

        public ByteString getTextBytes() {
            return ByteString.copyFromUtf8(this.text_);
        }

        /* access modifiers changed from: private */
        public void setText(String value) {
            value.getClass();
            this.text_ = value;
        }

        /* access modifiers changed from: private */
        public void clearText() {
            this.text_ = getDefaultInstance().getText();
        }

        /* access modifiers changed from: private */
        public void setTextBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.text_ = value.toStringUtf8();
        }

        public String getHexColor() {
            return this.hexColor_;
        }

        public ByteString getHexColorBytes() {
            return ByteString.copyFromUtf8(this.hexColor_);
        }

        /* access modifiers changed from: private */
        public void setHexColor(String value) {
            value.getClass();
            this.hexColor_ = value;
        }

        /* access modifiers changed from: private */
        public void clearHexColor() {
            this.hexColor_ = getDefaultInstance().getHexColor();
        }

        /* access modifiers changed from: private */
        public void setHexColorBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.hexColor_ = value.toStringUtf8();
        }

        public static Text parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Text) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Text parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Text) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Text parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Text) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Text parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Text) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Text parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Text) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Text parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Text) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Text parseFrom(InputStream input) throws IOException {
            return (Text) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Text parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Text) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Text parseDelimitedFrom(InputStream input) throws IOException {
            return (Text) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Text parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Text) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Text parseFrom(CodedInputStream input) throws IOException {
            return (Text) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Text parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Text) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Text prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Text, Builder> implements TextOrBuilder {
            /* synthetic */ Builder(C39381 x0) {
                this();
            }

            private Builder() {
                super(Text.DEFAULT_INSTANCE);
            }

            public String getText() {
                return ((Text) this.instance).getText();
            }

            public ByteString getTextBytes() {
                return ((Text) this.instance).getTextBytes();
            }

            public Builder setText(String value) {
                copyOnWrite();
                ((Text) this.instance).setText(value);
                return this;
            }

            public Builder clearText() {
                copyOnWrite();
                ((Text) this.instance).clearText();
                return this;
            }

            public Builder setTextBytes(ByteString value) {
                copyOnWrite();
                ((Text) this.instance).setTextBytes(value);
                return this;
            }

            public String getHexColor() {
                return ((Text) this.instance).getHexColor();
            }

            public ByteString getHexColorBytes() {
                return ((Text) this.instance).getHexColorBytes();
            }

            public Builder setHexColor(String value) {
                copyOnWrite();
                ((Text) this.instance).setHexColor(value);
                return this;
            }

            public Builder clearHexColor() {
                copyOnWrite();
                ((Text) this.instance).clearHexColor();
                return this;
            }

            public Builder setHexColorBytes(ByteString value) {
                copyOnWrite();
                ((Text) this.instance).setHexColorBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39381.f1739xa1df5c61[method.ordinal()]) {
                case 1:
                    return new Text();
                case 2:
                    return new Builder((C39381) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001Ȉ\u0002Ȉ", new Object[]{"text_", "hexColor_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Text> parser = PARSER;
                    if (parser == null) {
                        synchronized (Text.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            Text defaultInstance = new Text();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(Text.class, defaultInstance);
        }

        public static Text getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Text> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class Button extends GeneratedMessageLite<Button, Builder> implements ButtonOrBuilder {
        public static final int BUTTON_HEX_COLOR_FIELD_NUMBER = 2;
        /* access modifiers changed from: private */
        public static final Button DEFAULT_INSTANCE;
        private static volatile Parser<Button> PARSER = null;
        public static final int TEXT_FIELD_NUMBER = 1;
        private String buttonHexColor_ = "";
        private Text text_;

        private Button() {
        }

        public boolean hasText() {
            return this.text_ != null;
        }

        public Text getText() {
            Text text = this.text_;
            return text == null ? Text.getDefaultInstance() : text;
        }

        /* access modifiers changed from: private */
        public void setText(Text value) {
            value.getClass();
            this.text_ = value;
        }

        /* access modifiers changed from: private */
        public void mergeText(Text value) {
            value.getClass();
            Text text = this.text_;
            if (text == null || text == Text.getDefaultInstance()) {
                this.text_ = value;
            } else {
                this.text_ = (Text) ((Text.Builder) Text.newBuilder(this.text_).mergeFrom(value)).buildPartial();
            }
        }

        /* access modifiers changed from: private */
        public void clearText() {
            this.text_ = null;
        }

        public String getButtonHexColor() {
            return this.buttonHexColor_;
        }

        public ByteString getButtonHexColorBytes() {
            return ByteString.copyFromUtf8(this.buttonHexColor_);
        }

        /* access modifiers changed from: private */
        public void setButtonHexColor(String value) {
            value.getClass();
            this.buttonHexColor_ = value;
        }

        /* access modifiers changed from: private */
        public void clearButtonHexColor() {
            this.buttonHexColor_ = getDefaultInstance().getButtonHexColor();
        }

        /* access modifiers changed from: private */
        public void setButtonHexColorBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.buttonHexColor_ = value.toStringUtf8();
        }

        public static Button parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Button) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Button parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Button) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Button parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Button) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Button parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Button) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Button parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Button) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Button parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Button) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Button parseFrom(InputStream input) throws IOException {
            return (Button) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Button parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Button) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Button parseDelimitedFrom(InputStream input) throws IOException {
            return (Button) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Button parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Button) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Button parseFrom(CodedInputStream input) throws IOException {
            return (Button) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Button parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Button) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Button prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Button, Builder> implements ButtonOrBuilder {
            /* synthetic */ Builder(C39381 x0) {
                this();
            }

            private Builder() {
                super(Button.DEFAULT_INSTANCE);
            }

            public boolean hasText() {
                return ((Button) this.instance).hasText();
            }

            public Text getText() {
                return ((Button) this.instance).getText();
            }

            public Builder setText(Text value) {
                copyOnWrite();
                ((Button) this.instance).setText(value);
                return this;
            }

            public Builder setText(Text.Builder builderForValue) {
                copyOnWrite();
                ((Button) this.instance).setText((Text) builderForValue.build());
                return this;
            }

            public Builder mergeText(Text value) {
                copyOnWrite();
                ((Button) this.instance).mergeText(value);
                return this;
            }

            public Builder clearText() {
                copyOnWrite();
                ((Button) this.instance).clearText();
                return this;
            }

            public String getButtonHexColor() {
                return ((Button) this.instance).getButtonHexColor();
            }

            public ByteString getButtonHexColorBytes() {
                return ((Button) this.instance).getButtonHexColorBytes();
            }

            public Builder setButtonHexColor(String value) {
                copyOnWrite();
                ((Button) this.instance).setButtonHexColor(value);
                return this;
            }

            public Builder clearButtonHexColor() {
                copyOnWrite();
                ((Button) this.instance).clearButtonHexColor();
                return this;
            }

            public Builder setButtonHexColorBytes(ByteString value) {
                copyOnWrite();
                ((Button) this.instance).setButtonHexColorBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39381.f1739xa1df5c61[method.ordinal()]) {
                case 1:
                    return new Button();
                case 2:
                    return new Builder((C39381) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\t\u0002Ȉ", new Object[]{"text_", "buttonHexColor_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Button> parser = PARSER;
                    if (parser == null) {
                        synchronized (Button.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            Button defaultInstance = new Button();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(Button.class, defaultInstance);
        }

        public static Button getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Button> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    public static final class Action extends GeneratedMessageLite<Action, Builder> implements ActionOrBuilder {
        public static final int ACTION_URL_FIELD_NUMBER = 1;
        /* access modifiers changed from: private */
        public static final Action DEFAULT_INSTANCE;
        private static volatile Parser<Action> PARSER;
        private String actionUrl_ = "";

        private Action() {
        }

        public String getActionUrl() {
            return this.actionUrl_;
        }

        public ByteString getActionUrlBytes() {
            return ByteString.copyFromUtf8(this.actionUrl_);
        }

        /* access modifiers changed from: private */
        public void setActionUrl(String value) {
            value.getClass();
            this.actionUrl_ = value;
        }

        /* access modifiers changed from: private */
        public void clearActionUrl() {
            this.actionUrl_ = getDefaultInstance().getActionUrl();
        }

        /* access modifiers changed from: private */
        public void setActionUrlBytes(ByteString value) {
            checkByteStringIsUtf8(value);
            this.actionUrl_ = value.toStringUtf8();
        }

        public static Action parseFrom(ByteBuffer data) throws InvalidProtocolBufferException {
            return (Action) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Action parseFrom(ByteBuffer data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Action) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Action parseFrom(ByteString data) throws InvalidProtocolBufferException {
            return (Action) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Action parseFrom(ByteString data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Action) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Action parseFrom(byte[] data) throws InvalidProtocolBufferException {
            return (Action) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data);
        }

        public static Action parseFrom(byte[] data, ExtensionRegistryLite extensionRegistry) throws InvalidProtocolBufferException {
            return (Action) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, data, extensionRegistry);
        }

        public static Action parseFrom(InputStream input) throws IOException {
            return (Action) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Action parseFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Action) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Action parseDelimitedFrom(InputStream input) throws IOException {
            return (Action) parseDelimitedFrom(DEFAULT_INSTANCE, input);
        }

        public static Action parseDelimitedFrom(InputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Action) parseDelimitedFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Action parseFrom(CodedInputStream input) throws IOException {
            return (Action) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input);
        }

        public static Action parseFrom(CodedInputStream input, ExtensionRegistryLite extensionRegistry) throws IOException {
            return (Action) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, input, extensionRegistry);
        }

        public static Builder newBuilder() {
            return (Builder) DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(Action prototype) {
            return (Builder) DEFAULT_INSTANCE.createBuilder(prototype);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<Action, Builder> implements ActionOrBuilder {
            /* synthetic */ Builder(C39381 x0) {
                this();
            }

            private Builder() {
                super(Action.DEFAULT_INSTANCE);
            }

            public String getActionUrl() {
                return ((Action) this.instance).getActionUrl();
            }

            public ByteString getActionUrlBytes() {
                return ((Action) this.instance).getActionUrlBytes();
            }

            public Builder setActionUrl(String value) {
                copyOnWrite();
                ((Action) this.instance).setActionUrl(value);
                return this;
            }

            public Builder clearActionUrl() {
                copyOnWrite();
                ((Action) this.instance).clearActionUrl();
                return this;
            }

            public Builder setActionUrlBytes(ByteString value) {
                copyOnWrite();
                ((Action) this.instance).setActionUrlBytes(value);
                return this;
            }
        }

        /* access modifiers changed from: protected */
        public final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke method, Object arg0, Object arg1) {
            switch (C39381.f1739xa1df5c61[method.ordinal()]) {
                case 1:
                    return new Action();
                case 2:
                    return new Builder((C39381) null);
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0001\u0000\u0000\u0001\u0001\u0001\u0000\u0000\u0000\u0001Ȉ", new Object[]{"actionUrl_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<Action> parser = PARSER;
                    if (parser == null) {
                        synchronized (Action.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            Action defaultInstance = new Action();
            DEFAULT_INSTANCE = defaultInstance;
            GeneratedMessageLite.registerDefaultInstance(Action.class, defaultInstance);
        }

        public static Action getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        public static Parser<Action> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }
}
