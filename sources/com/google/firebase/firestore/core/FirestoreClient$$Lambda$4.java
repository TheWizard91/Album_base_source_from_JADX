package com.google.firebase.firestore.core;

/* compiled from: FirestoreClient */
final /* synthetic */ class FirestoreClient$$Lambda$4 implements Runnable {
    private final FirestoreClient arg$1;

    private FirestoreClient$$Lambda$4(FirestoreClient firestoreClient) {
        this.arg$1 = firestoreClient;
    }

    public static Runnable lambdaFactory$(FirestoreClient firestoreClient) {
        return new FirestoreClient$$Lambda$4(firestoreClient);
    }

    public void run() {
        this.arg$1.remoteStore.enableNetwork();
    }
}
