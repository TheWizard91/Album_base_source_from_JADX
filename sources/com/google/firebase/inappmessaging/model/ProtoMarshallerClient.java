package com.google.firebase.inappmessaging.model;

import android.text.TextUtils;
import com.google.common.base.Preconditions;
import com.google.firebase.inappmessaging.MessagesProto;
import com.google.firebase.inappmessaging.internal.Logging;
import com.google.firebase.inappmessaging.model.Action;
import com.google.firebase.inappmessaging.model.BannerMessage;
import com.google.firebase.inappmessaging.model.Button;
import com.google.firebase.inappmessaging.model.CardMessage;
import com.google.firebase.inappmessaging.model.ImageOnlyMessage;
import com.google.firebase.inappmessaging.model.ModalMessage;
import com.google.firebase.inappmessaging.model.Text;
import java.util.Map;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class ProtoMarshallerClient {
    @Inject
    ProtoMarshallerClient() {
    }

    @Nonnull
    private static ModalMessage.Builder from(MessagesProto.ModalMessage in) {
        ModalMessage.Builder builder = ModalMessage.builder();
        if (!TextUtils.isEmpty(in.getBackgroundHexColor())) {
            builder.setBackgroundHexColor(in.getBackgroundHexColor());
        }
        if (!TextUtils.isEmpty(in.getImageUrl())) {
            builder.setImageData(ImageData.builder().setImageUrl(in.getImageUrl()).build());
        }
        if (in.hasAction()) {
            builder.setAction(decode(in.getAction(), in.getActionButton()));
        }
        if (in.hasBody()) {
            builder.setBody(decode(in.getBody()));
        }
        if (in.hasTitle()) {
            builder.setTitle(decode(in.getTitle()));
        }
        return builder;
    }

    @Nonnull
    private static ImageOnlyMessage.Builder from(MessagesProto.ImageOnlyMessage in) {
        ImageOnlyMessage.Builder builder = ImageOnlyMessage.builder();
        if (!TextUtils.isEmpty(in.getImageUrl())) {
            builder.setImageData(ImageData.builder().setImageUrl(in.getImageUrl()).build());
        }
        if (in.hasAction()) {
            builder.setAction(decode(in.getAction()).build());
        }
        return builder;
    }

    @Nonnull
    private static BannerMessage.Builder from(MessagesProto.BannerMessage in) {
        BannerMessage.Builder builder = BannerMessage.builder();
        if (!TextUtils.isEmpty(in.getBackgroundHexColor())) {
            builder.setBackgroundHexColor(in.getBackgroundHexColor());
        }
        if (!TextUtils.isEmpty(in.getImageUrl())) {
            builder.setImageData(ImageData.builder().setImageUrl(in.getImageUrl()).build());
        }
        if (in.hasAction()) {
            builder.setAction(decode(in.getAction()).build());
        }
        if (in.hasBody()) {
            builder.setBody(decode(in.getBody()));
        }
        if (in.hasTitle()) {
            builder.setTitle(decode(in.getTitle()));
        }
        return builder;
    }

    @Nonnull
    private static CardMessage.Builder from(MessagesProto.CardMessage in) {
        CardMessage.Builder builder = CardMessage.builder();
        if (in.hasTitle()) {
            builder.setTitle(decode(in.getTitle()));
        }
        if (in.hasBody()) {
            builder.setBody(decode(in.getBody()));
        }
        if (!TextUtils.isEmpty(in.getBackgroundHexColor())) {
            builder.setBackgroundHexColor(in.getBackgroundHexColor());
        }
        if (in.hasPrimaryAction() || in.hasPrimaryActionButton()) {
            builder.setPrimaryAction(decode(in.getPrimaryAction(), in.getPrimaryActionButton()));
        }
        if (in.hasSecondaryAction() || in.hasSecondaryActionButton()) {
            builder.setSecondaryAction(decode(in.getSecondaryAction(), in.getSecondaryActionButton()));
        }
        if (!TextUtils.isEmpty(in.getPortraitImageUrl())) {
            builder.setPortraitImageData(ImageData.builder().setImageUrl(in.getPortraitImageUrl()).build());
        }
        if (!TextUtils.isEmpty(in.getLandscapeImageUrl())) {
            builder.setLandscapeImageData(ImageData.builder().setImageUrl(in.getLandscapeImageUrl()).build());
        }
        return builder;
    }

    private static Button decode(MessagesProto.Button in) {
        Button.Builder builder = Button.builder();
        if (!TextUtils.isEmpty(in.getButtonHexColor())) {
            builder.setButtonHexColor(in.getButtonHexColor());
        }
        if (in.hasText()) {
            builder.setText(decode(in.getText()));
        }
        return builder.build();
    }

    private static Action decode(MessagesProto.Action protoAction, MessagesProto.Button protoButton) {
        Action.Builder builder = decode(protoAction);
        if (!protoButton.equals(MessagesProto.Button.getDefaultInstance())) {
            Button.Builder buttonBuilder = Button.builder();
            if (!TextUtils.isEmpty(protoButton.getButtonHexColor())) {
                buttonBuilder.setButtonHexColor(protoButton.getButtonHexColor());
            }
            if (protoButton.hasText()) {
                Text.Builder buttonText = Text.builder();
                MessagesProto.Text text = protoButton.getText();
                if (!TextUtils.isEmpty(text.getText())) {
                    buttonText.setText(text.getText());
                }
                if (!TextUtils.isEmpty(text.getHexColor())) {
                    buttonText.setHexColor(text.getHexColor());
                }
                buttonBuilder.setText(buttonText.build());
            }
            builder.setButton(buttonBuilder.build());
        }
        return builder.build();
    }

    private static Action.Builder decode(MessagesProto.Action protoAction) {
        Action.Builder builder = Action.builder();
        if (!TextUtils.isEmpty(protoAction.getActionUrl())) {
            builder.setActionUrl(protoAction.getActionUrl());
        }
        return builder;
    }

    private static Text decode(MessagesProto.Text in) {
        Text.Builder builder = Text.builder();
        if (!TextUtils.isEmpty(in.getHexColor())) {
            builder.setHexColor(in.getHexColor());
        }
        if (!TextUtils.isEmpty(in.getText())) {
            builder.setText(in.getText());
        }
        return builder.build();
    }

    public static InAppMessage decode(@Nonnull MessagesProto.Content in, String campaignId, String campaignName, boolean isTestMessage, @Nullable Map<String, String> data) {
        Preconditions.checkNotNull(in, "FirebaseInAppMessaging content cannot be null.");
        Preconditions.checkNotNull(campaignId, "FirebaseInAppMessaging campaign id cannot be null.");
        Preconditions.checkNotNull(campaignName, "FirebaseInAppMessaging campaign name cannot be null.");
        Logging.logd("Decoding message: " + in.toString());
        CampaignMetadata campaignMetadata = new CampaignMetadata(campaignId, campaignName, isTestMessage);
        int i = C40042.f1747x95e22605[in.getMessageDetailsCase().ordinal()];
        if (i == 1) {
            return from(in.getBanner()).build(campaignMetadata, data);
        }
        if (i == 2) {
            return from(in.getImageOnly()).build(campaignMetadata, data);
        }
        if (i == 3) {
            return from(in.getModal()).build(campaignMetadata, data);
        }
        if (i != 4) {
            return new InAppMessage(new CampaignMetadata(campaignId, campaignName, isTestMessage), MessageType.UNSUPPORTED, data) {
                public Action getAction() {
                    return null;
                }
            };
        }
        return from(in.getCard()).build(campaignMetadata, data);
    }

    /* renamed from: com.google.firebase.inappmessaging.model.ProtoMarshallerClient$2 */
    static /* synthetic */ class C40042 {

        /* renamed from: $SwitchMap$com$google$firebase$inappmessaging$MessagesProto$Content$MessageDetailsCase */
        static final /* synthetic */ int[] f1747x95e22605;

        static {
            int[] iArr = new int[MessagesProto.Content.MessageDetailsCase.values().length];
            f1747x95e22605 = iArr;
            try {
                iArr[MessagesProto.Content.MessageDetailsCase.BANNER.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1747x95e22605[MessagesProto.Content.MessageDetailsCase.IMAGE_ONLY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1747x95e22605[MessagesProto.Content.MessageDetailsCase.MODAL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                f1747x95e22605[MessagesProto.Content.MessageDetailsCase.CARD.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }
}
