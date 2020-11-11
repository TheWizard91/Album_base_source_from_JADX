package com.google.firebase.inappmessaging.internal;

import android.app.Application;
import com.google.protobuf.AbstractMessageLite;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Parser;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import p019io.reactivex.Completable;
import p019io.reactivex.Maybe;

public class ProtoStorageClient {
    private final Application application;
    private final String fileName;

    public ProtoStorageClient(Application application2, String fileName2) {
        this.application = application2;
        this.fileName = fileName2;
    }

    public Completable write(AbstractMessageLite messageLite) {
        return Completable.fromCallable(ProtoStorageClient$$Lambda$1.lambdaFactory$(this, messageLite));
    }

    static /* synthetic */ Object lambda$write$0(ProtoStorageClient protoStorageClient, AbstractMessageLite messageLite) throws Exception {
        synchronized (protoStorageClient) {
            FileOutputStream output = protoStorageClient.application.openFileOutput(protoStorageClient.fileName, 0);
            try {
                output.write(messageLite.toByteArray());
                if (output != null) {
                    output.close();
                }
            } catch (Throwable th) {
            }
        }
        return messageLite;
        throw th;
    }

    public <T extends AbstractMessageLite> Maybe<T> read(Parser<T> parser) {
        return Maybe.fromCallable(ProtoStorageClient$$Lambda$2.lambdaFactory$(this, parser));
    }

    static /* synthetic */ AbstractMessageLite lambda$read$1(ProtoStorageClient protoStorageClient, Parser parser) throws Exception {
        FileInputStream inputStream;
        AbstractMessageLite abstractMessageLite;
        synchronized (protoStorageClient) {
            try {
                inputStream = protoStorageClient.application.openFileInput(protoStorageClient.fileName);
                abstractMessageLite = (AbstractMessageLite) parser.parseFrom((InputStream) inputStream);
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (InvalidProtocolBufferException | FileNotFoundException e) {
                Logging.logi("Recoverable exception while reading cache: " + e.getMessage());
                return null;
            } catch (Throwable th) {
            }
        }
        return abstractMessageLite;
        throw th;
    }
}
