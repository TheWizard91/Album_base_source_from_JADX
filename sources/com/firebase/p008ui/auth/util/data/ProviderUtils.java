package com.firebase.p008ui.auth.util.data;

import android.text.TextUtils;
import com.firebase.p008ui.auth.AuthUI;
import com.firebase.p008ui.auth.FirebaseUiException;
import com.firebase.p008ui.auth.IdpResponse;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.SignInMethodQueryResult;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.firebase.ui.auth.util.data.ProviderUtils */
public final class ProviderUtils {
    private static final String GITHUB_IDENTITY = "https://github.com";
    private static final String PHONE_IDENTITY = "https://phone.firebase";

    private ProviderUtils() {
        throw new AssertionError("No instance for you!");
    }

    public static AuthCredential getAuthCredential(IdpResponse response) {
        if (response.hasCredentialForLinking()) {
            return response.getCredentialForLinking();
        }
        String providerType = response.getProviderType();
        char c = 65535;
        int hashCode = providerType.hashCode();
        if (hashCode != -1536293812) {
            if (hashCode == -364826023 && providerType.equals("facebook.com")) {
                c = 1;
            }
        } else if (providerType.equals("google.com")) {
            c = 0;
        }
        if (c == 0) {
            return GoogleAuthProvider.getCredential(response.getIdpToken(), (String) null);
        }
        if (c != 1) {
            return null;
        }
        return FacebookAuthProvider.getCredential(response.getIdpToken());
    }

    public static String idpResponseToAccountType(IdpResponse response) {
        if (response == null) {
            return null;
        }
        return providerIdToAccountType(response.getProviderType());
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String signInMethodToProviderId(java.lang.String r8) {
        /*
            int r0 = r8.hashCode()
            java.lang.String r1 = "emailLink"
            java.lang.String r2 = "github.com"
            java.lang.String r3 = "password"
            java.lang.String r4 = "phone"
            java.lang.String r5 = "facebook.com"
            java.lang.String r6 = "google.com"
            java.lang.String r7 = "twitter.com"
            switch(r0) {
                case -1830313082: goto L_0x0047;
                case -1536293812: goto L_0x003f;
                case -364826023: goto L_0x0037;
                case 106642798: goto L_0x002f;
                case 1216985755: goto L_0x0027;
                case 1985010934: goto L_0x001f;
                case 2120171958: goto L_0x0017;
                default: goto L_0x0016;
            }
        L_0x0016:
            goto L_0x004f
        L_0x0017:
            boolean r0 = r8.equals(r1)
            if (r0 == 0) goto L_0x0016
            r0 = 6
            goto L_0x0050
        L_0x001f:
            boolean r0 = r8.equals(r2)
            if (r0 == 0) goto L_0x0016
            r0 = 3
            goto L_0x0050
        L_0x0027:
            boolean r0 = r8.equals(r3)
            if (r0 == 0) goto L_0x0016
            r0 = 5
            goto L_0x0050
        L_0x002f:
            boolean r0 = r8.equals(r4)
            if (r0 == 0) goto L_0x0016
            r0 = 4
            goto L_0x0050
        L_0x0037:
            boolean r0 = r8.equals(r5)
            if (r0 == 0) goto L_0x0016
            r0 = 1
            goto L_0x0050
        L_0x003f:
            boolean r0 = r8.equals(r6)
            if (r0 == 0) goto L_0x0016
            r0 = 0
            goto L_0x0050
        L_0x0047:
            boolean r0 = r8.equals(r7)
            if (r0 == 0) goto L_0x0016
            r0 = 2
            goto L_0x0050
        L_0x004f:
            r0 = -1
        L_0x0050:
            switch(r0) {
                case 0: goto L_0x005a;
                case 1: goto L_0x0059;
                case 2: goto L_0x0058;
                case 3: goto L_0x0057;
                case 4: goto L_0x0056;
                case 5: goto L_0x0055;
                case 6: goto L_0x0054;
                default: goto L_0x0053;
            }
        L_0x0053:
            return r8
        L_0x0054:
            return r1
        L_0x0055:
            return r3
        L_0x0056:
            return r4
        L_0x0057:
            return r2
        L_0x0058:
            return r7
        L_0x0059:
            return r5
        L_0x005a:
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p008ui.auth.util.data.ProviderUtils.signInMethodToProviderId(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String providerIdToAccountType(java.lang.String r5) {
        /*
            int r0 = r5.hashCode()
            r1 = 4
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r0) {
                case -1830313082: goto L_0x003e;
                case -1536293812: goto L_0x0034;
                case -364826023: goto L_0x002a;
                case 106642798: goto L_0x0020;
                case 1216985755: goto L_0x0016;
                case 1985010934: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x0049
        L_0x000c:
            java.lang.String r0 = "github.com"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000b
            r0 = r2
            goto L_0x004a
        L_0x0016:
            java.lang.String r0 = "password"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000b
            r0 = 5
            goto L_0x004a
        L_0x0020:
            java.lang.String r0 = "phone"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000b
            r0 = r1
            goto L_0x004a
        L_0x002a:
            java.lang.String r0 = "facebook.com"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000b
            r0 = r4
            goto L_0x004a
        L_0x0034:
            java.lang.String r0 = "google.com"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000b
            r0 = 0
            goto L_0x004a
        L_0x003e:
            java.lang.String r0 = "twitter.com"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000b
            r0 = r3
            goto L_0x004a
        L_0x0049:
            r0 = -1
        L_0x004a:
            if (r0 == 0) goto L_0x0062
            if (r0 == r4) goto L_0x005f
            if (r0 == r3) goto L_0x005c
            if (r0 == r2) goto L_0x0059
            if (r0 == r1) goto L_0x0056
            r0 = 0
            return r0
        L_0x0056:
            java.lang.String r0 = "https://phone.firebase"
            return r0
        L_0x0059:
            java.lang.String r0 = "https://github.com"
            return r0
        L_0x005c:
            java.lang.String r0 = "https://twitter.com"
            return r0
        L_0x005f:
            java.lang.String r0 = "https://www.facebook.com"
            return r0
        L_0x0062:
            java.lang.String r0 = "https://accounts.google.com"
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p008ui.auth.util.data.ProviderUtils.providerIdToAccountType(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String accountTypeToProviderId(java.lang.String r5) {
        /*
            int r0 = r5.hashCode()
            r1 = 4
            r2 = 3
            r3 = 2
            r4 = 1
            switch(r0) {
                case -1534095099: goto L_0x0034;
                case -1294469354: goto L_0x002a;
                case -376862683: goto L_0x0020;
                case 746549591: goto L_0x0016;
                case 1721158175: goto L_0x000c;
                default: goto L_0x000b;
            }
        L_0x000b:
            goto L_0x003e
        L_0x000c:
            java.lang.String r0 = "https://www.facebook.com"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000b
            r0 = r4
            goto L_0x003f
        L_0x0016:
            java.lang.String r0 = "https://twitter.com"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000b
            r0 = r3
            goto L_0x003f
        L_0x0020:
            java.lang.String r0 = "https://accounts.google.com"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000b
            r0 = 0
            goto L_0x003f
        L_0x002a:
            java.lang.String r0 = "https://phone.firebase"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000b
            r0 = r1
            goto L_0x003f
        L_0x0034:
            java.lang.String r0 = "https://github.com"
            boolean r0 = r5.equals(r0)
            if (r0 == 0) goto L_0x000b
            r0 = r2
            goto L_0x003f
        L_0x003e:
            r0 = -1
        L_0x003f:
            if (r0 == 0) goto L_0x0058
            if (r0 == r4) goto L_0x0055
            if (r0 == r3) goto L_0x0051
            if (r0 == r2) goto L_0x004e
            if (r0 == r1) goto L_0x004b
            r0 = 0
            return r0
        L_0x004b:
            java.lang.String r0 = "phone"
            return r0
        L_0x004e:
            java.lang.String r0 = "github.com"
            return r0
        L_0x0051:
            java.lang.String r0 = "twitter.com"
            return r0
        L_0x0055:
            java.lang.String r0 = "facebook.com"
            return r0
        L_0x0058:
            java.lang.String r0 = "google.com"
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p008ui.auth.util.data.ProviderUtils.accountTypeToProviderId(java.lang.String):java.lang.String");
    }

    /* JADX WARNING: Can't fix incorrect switch cases order */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static java.lang.String providerIdToProviderName(java.lang.String r2) {
        /*
            int r0 = r2.hashCode()
            switch(r0) {
                case -1830313082: goto L_0x0044;
                case -1536293812: goto L_0x003a;
                case -364826023: goto L_0x0030;
                case 106642798: goto L_0x0026;
                case 1216985755: goto L_0x001c;
                case 1985010934: goto L_0x0012;
                case 2120171958: goto L_0x0008;
                default: goto L_0x0007;
            }
        L_0x0007:
            goto L_0x004f
        L_0x0008:
            java.lang.String r0 = "emailLink"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 6
            goto L_0x0050
        L_0x0012:
            java.lang.String r0 = "github.com"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 3
            goto L_0x0050
        L_0x001c:
            java.lang.String r0 = "password"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 5
            goto L_0x0050
        L_0x0026:
            java.lang.String r0 = "phone"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 4
            goto L_0x0050
        L_0x0030:
            java.lang.String r0 = "facebook.com"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 1
            goto L_0x0050
        L_0x003a:
            java.lang.String r0 = "google.com"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 0
            goto L_0x0050
        L_0x0044:
            java.lang.String r0 = "twitter.com"
            boolean r0 = r2.equals(r0)
            if (r0 == 0) goto L_0x0007
            r0 = 2
            goto L_0x0050
        L_0x004f:
            r0 = -1
        L_0x0050:
            switch(r0) {
                case 0: goto L_0x008c;
                case 1: goto L_0x0081;
                case 2: goto L_0x0076;
                case 3: goto L_0x006b;
                case 4: goto L_0x0060;
                case 5: goto L_0x0055;
                case 6: goto L_0x0055;
                default: goto L_0x0053;
            }
        L_0x0053:
            r0 = 0
            return r0
        L_0x0055:
            android.content.Context r0 = com.firebase.p008ui.auth.AuthUI.getApplicationContext()
            int r1 = com.firebase.p008ui.auth.C2354R.string.fui_idp_name_email
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x0060:
            android.content.Context r0 = com.firebase.p008ui.auth.AuthUI.getApplicationContext()
            int r1 = com.firebase.p008ui.auth.C2354R.string.fui_idp_name_phone
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x006b:
            android.content.Context r0 = com.firebase.p008ui.auth.AuthUI.getApplicationContext()
            int r1 = com.firebase.p008ui.auth.C2354R.string.fui_idp_name_github
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x0076:
            android.content.Context r0 = com.firebase.p008ui.auth.AuthUI.getApplicationContext()
            int r1 = com.firebase.p008ui.auth.C2354R.string.fui_idp_name_twitter
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x0081:
            android.content.Context r0 = com.firebase.p008ui.auth.AuthUI.getApplicationContext()
            int r1 = com.firebase.p008ui.auth.C2354R.string.fui_idp_name_facebook
            java.lang.String r0 = r0.getString(r1)
            return r0
        L_0x008c:
            android.content.Context r0 = com.firebase.p008ui.auth.AuthUI.getApplicationContext()
            int r1 = com.firebase.p008ui.auth.C2354R.string.fui_idp_name_google
            java.lang.String r0 = r0.getString(r1)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p008ui.auth.util.data.ProviderUtils.providerIdToProviderName(java.lang.String):java.lang.String");
    }

    public static AuthUI.IdpConfig getConfigFromIdps(List<AuthUI.IdpConfig> idps, String id) {
        for (AuthUI.IdpConfig idp : idps) {
            if (idp.getProviderId().equals(id)) {
                return idp;
            }
        }
        return null;
    }

    public static AuthUI.IdpConfig getConfigFromIdpsOrThrow(List<AuthUI.IdpConfig> idps, String id) {
        AuthUI.IdpConfig config = getConfigFromIdps(idps, id);
        if (config != null) {
            return config;
        }
        throw new IllegalStateException("Provider " + id + " not found.");
    }

    public static Task<List<String>> fetchSortedProviders(FirebaseAuth auth, final FlowParameters params, String email) {
        if (TextUtils.isEmpty(email)) {
            return Tasks.forException(new NullPointerException("Email cannot be empty"));
        }
        return auth.fetchSignInMethodsForEmail(email).continueWithTask(new Continuation<SignInMethodQueryResult, Task<List<String>>>() {
            public Task<List<String>> then(Task<SignInMethodQueryResult> task) {
                List<String> methods = task.getResult().getSignInMethods();
                if (methods == null) {
                    methods = new ArrayList<>();
                }
                List<String> allowedProviders = new ArrayList<>(params.providers.size());
                for (AuthUI.IdpConfig provider : params.providers) {
                    allowedProviders.add(provider.getProviderId());
                }
                List<String> lastSignedInProviders = new ArrayList<>(methods.size());
                for (String method : methods) {
                    String id = ProviderUtils.signInMethodToProviderId(method);
                    if (allowedProviders.contains(id)) {
                        lastSignedInProviders.add(0, id);
                    }
                }
                if (task.isSuccessful() && lastSignedInProviders.isEmpty() && !methods.isEmpty()) {
                    return Tasks.forException(new FirebaseUiException(3));
                }
                reorderPriorities(lastSignedInProviders);
                return Tasks.forResult(lastSignedInProviders);
            }

            private void reorderPriorities(List<String> providers) {
                changePriority(providers, "password", true);
                changePriority(providers, "google.com", true);
                changePriority(providers, "emailLink", false);
            }

            private void changePriority(List<String> providers, String id, boolean maximizePriority) {
                if (!providers.remove(id)) {
                    return;
                }
                if (maximizePriority) {
                    providers.add(0, id);
                } else {
                    providers.add(id);
                }
            }
        });
    }

    public static Task<String> fetchTopProvider(FirebaseAuth auth, FlowParameters params, String email) {
        return fetchSortedProviders(auth, params, email).continueWithTask(new Continuation<List<String>, Task<String>>() {
            public Task<String> then(Task<List<String>> task) {
                if (!task.isSuccessful()) {
                    return Tasks.forException(task.getException());
                }
                List<String> providers = task.getResult();
                if (providers.isEmpty()) {
                    return Tasks.forResult(null);
                }
                return Tasks.forResult(providers.get(0));
            }
        });
    }
}
