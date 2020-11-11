package com.google.firebase.inappmessaging.internal.injection.modules;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import com.google.common.p028io.BaseEncoding;
import com.google.firebase.FirebaseApp;
import com.google.internal.firebase.inappmessaging.p015v1.sdkserving.InAppMessagingSdkServingGrpc;
import dagger.Module;
import dagger.Provides;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import p019io.grpc.Channel;
import p019io.grpc.ClientInterceptors;
import p019io.grpc.Metadata;
import p019io.grpc.stub.MetadataUtils;

@Module
public class GrpcClientModule {
    private final FirebaseApp firebaseApp;

    public GrpcClientModule(FirebaseApp firebaseApp2) {
        this.firebaseApp = firebaseApp2;
    }

    @Provides
    public Metadata providesApiKeyHeaders() {
        Metadata.Key<String> apiClientKeyHeader = Metadata.Key.m188of("X-Goog-Api-Key", Metadata.ASCII_STRING_MARSHALLER);
        Metadata.Key<String> androidPackageHeader = Metadata.Key.m188of("X-Android-Package", Metadata.ASCII_STRING_MARSHALLER);
        Metadata.Key<String> androidCertHashHeader = Metadata.Key.m188of("X-Android-Cert", Metadata.ASCII_STRING_MARSHALLER);
        Metadata metadata = new Metadata();
        String packageName = this.firebaseApp.getApplicationContext().getPackageName();
        metadata.put(apiClientKeyHeader, this.firebaseApp.getOptions().getApiKey());
        metadata.put(androidPackageHeader, packageName);
        String signature = getSignature(this.firebaseApp.getApplicationContext().getPackageManager(), packageName);
        if (signature != null) {
            metadata.put(androidCertHashHeader, signature);
        }
        return metadata;
    }

    public static String getSignature(PackageManager pm, String packageName) {
        try {
            PackageInfo packageInfo = pm.getPackageInfo(packageName, 64);
            if (!(packageInfo == null || packageInfo.signatures == null || packageInfo.signatures.length == 0)) {
                if (packageInfo.signatures[0] != null) {
                    return signatureDigest(packageInfo.signatures[0]);
                }
            }
            return null;
        } catch (PackageManager.NameNotFoundException e) {
            return null;
        }
    }

    private static String signatureDigest(Signature sig) {
        try {
            return BaseEncoding.base16().upperCase().encode(MessageDigest.getInstance("SHA1").digest(sig.toByteArray()));
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
    }

    @Provides
    public InAppMessagingSdkServingGrpc.InAppMessagingSdkServingBlockingStub providesInAppMessagingSdkServingStub(Channel channel, Metadata metadata) {
        return InAppMessagingSdkServingGrpc.newBlockingStub(ClientInterceptors.intercept(channel, MetadataUtils.newAttachHeadersInterceptor(metadata)));
    }
}
