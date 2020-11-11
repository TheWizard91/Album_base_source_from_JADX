package com.firebase.client;

import java.util.Collections;
import java.util.Map;

public class AuthData {
    private final Map<String, Object> auth;
    private final long expires;
    private final String provider;
    private final Map<String, Object> providerData;
    private final String token;
    private final String uid;

    public AuthData(String token2, long expires2, String uid2, String provider2, Map<String, Object> auth2, Map<String, Object> providerData2) {
        this.token = token2;
        this.expires = expires2;
        this.uid = uid2;
        this.provider = provider2;
        Map<String, Object> map = null;
        this.providerData = providerData2 != null ? Collections.unmodifiableMap(providerData2) : null;
        this.auth = auth2 != null ? Collections.unmodifiableMap(auth2) : map;
    }

    public String getToken() {
        return this.token;
    }

    public long getExpires() {
        return this.expires;
    }

    public String getUid() {
        return this.uid;
    }

    public String getProvider() {
        return this.provider;
    }

    public Map<String, Object> getProviderData() {
        return this.providerData;
    }

    public Map<String, Object> getAuth() {
        return this.auth;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AuthData authData = (AuthData) o;
        String str = this.provider;
        if (str == null ? authData.provider != null : !str.equals(authData.provider)) {
            return false;
        }
        Map<String, Object> map = this.providerData;
        if (map == null ? authData.providerData != null : !map.equals(authData.providerData)) {
            return false;
        }
        Map<String, Object> map2 = this.auth;
        if (map2 == null ? authData.auth != null : !map2.equals(authData.auth)) {
            return false;
        }
        String str2 = this.token;
        if (str2 == null ? authData.token != null : !str2.equals(authData.token)) {
            return false;
        }
        if (this.expires != authData.expires) {
            return false;
        }
        String str3 = this.uid;
        if (str3 == null ? authData.uid == null : str3.equals(authData.uid)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        String str = this.token;
        int i = 0;
        int hashCode = (str != null ? str.hashCode() : 0) * 31;
        String str2 = this.uid;
        int result = (hashCode + (str2 != null ? str2.hashCode() : 0)) * 31;
        String str3 = this.provider;
        int result2 = (result + (str3 != null ? str3.hashCode() : 0)) * 31;
        Map<String, Object> map = this.providerData;
        int result3 = (result2 + (map != null ? map.hashCode() : 0)) * 31;
        Map<String, Object> map2 = this.auth;
        if (map2 != null) {
            i = map2.hashCode();
        }
        return result3 + i;
    }

    public String toString() {
        return "AuthData{uid='" + this.uid + '\'' + ", provider='" + this.provider + '\'' + ", token='" + (this.token == null ? null : "***") + '\'' + ", expires='" + this.expires + '\'' + ", auth='" + this.auth + '\'' + ", providerData='" + this.providerData + '\'' + '}';
    }
}
