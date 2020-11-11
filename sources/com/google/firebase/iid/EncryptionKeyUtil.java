package com.google.firebase.iid;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.spec.ECGenParameterSpec;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/* compiled from: com.google.firebase:firebase-iid@@20.2.3 */
public class EncryptionKeyUtil {
    public static KeyPair generateEcP256KeyPair() {
        return getEcKeyGen().generateKeyPair();
    }

    public static boolean isEcP256Supported() {
        try {
            getEcKeyGen();
            return false;
        } catch (Exception e) {
            return true;
        }
    }

    private static KeyPairGenerator getEcKeyGen() {
        try {
            KeyPairGenerator instance = KeyPairGenerator.getInstance("EC");
            try {
                instance.initialize(new ECGenParameterSpec("prime256v1"));
                return instance;
            } catch (InvalidAlgorithmParameterException e) {
                try {
                    instance.initialize(new ECGenParameterSpec("secp256r1"));
                    return instance;
                } catch (InvalidAlgorithmParameterException e2) {
                    throw new RuntimeException("Unable to find the NIST P-256 curve");
                }
            }
        } catch (NoSuchAlgorithmException e3) {
            throw new RuntimeException(e3);
        }
    }

    public static KeyPair generateRSA2048KeyPair() {
        try {
            KeyPairGenerator instance = KeyPairGenerator.getInstance("RSA");
            instance.initialize(2048);
            return instance.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    public static SecretKey parseAESKey(byte[] bArr) {
        return new SecretKeySpec(bArr, "AES");
    }
}
