package com.google.firebase.firestore.remote;

import p019io.grpc.Metadata;

public interface GrpcMetadataProvider {
    void updateMetadata(Metadata metadata);
}