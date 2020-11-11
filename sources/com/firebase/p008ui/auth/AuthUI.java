package com.firebase.p008ui.auth;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.Log;
import com.facebook.login.LoginManager;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.firebase.p008ui.auth.util.CredentialUtils;
import com.firebase.p008ui.auth.util.ExtraConstants;
import com.firebase.p008ui.auth.util.GoogleApiUtils;
import com.firebase.p008ui.auth.util.Preconditions;
import com.firebase.p008ui.auth.util.data.PhoneNumberUtils;
import com.firebase.p008ui.auth.util.data.ProviderAvailability;
import com.firebase.p008ui.auth.util.data.ProviderUtils;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.google.android.gms.auth.api.credentials.CredentialRequestResponse;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.ActionCodeSettings;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthProvider;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

/* renamed from: com.firebase.ui.auth.AuthUI */
public final class AuthUI {
    public static final String ANONYMOUS_PROVIDER = "anonymous";
    public static final String APPLE_PROVIDER = "apple.com";
    public static final String EMAIL_LINK_PROVIDER = "emailLink";
    private static final IdentityHashMap<FirebaseApp, AuthUI> INSTANCES = new IdentityHashMap<>();
    public static final String MICROSOFT_PROVIDER = "microsoft.com";
    public static final int NO_LOGO = -1;
    public static final Set<String> SOCIAL_PROVIDERS = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"google.com", "facebook.com", "github.com"})));
    public static final Set<String> SUPPORTED_OAUTH_PROVIDERS = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{MICROSOFT_PROVIDER, YAHOO_PROVIDER, APPLE_PROVIDER, "twitter.com", "github.com"})));
    public static final Set<String> SUPPORTED_PROVIDERS = Collections.unmodifiableSet(new HashSet(Arrays.asList(new String[]{"google.com", "facebook.com", "twitter.com", "github.com", "password", "phone", ANONYMOUS_PROVIDER, "emailLink"})));
    public static final String TAG = "AuthUI";
    public static final String UNCONFIGURED_CONFIG_VALUE = "CHANGE-ME";
    public static final String YAHOO_PROVIDER = "yahoo.com";
    private static Context sApplicationContext;
    /* access modifiers changed from: private */
    public final FirebaseApp mApp;
    /* access modifiers changed from: private */
    public final FirebaseAuth mAuth;

    @Retention(RetentionPolicy.SOURCE)
    /* renamed from: com.firebase.ui.auth.AuthUI$SupportedProvider */
    public @interface SupportedProvider {
    }

    private AuthUI(FirebaseApp app) {
        this.mApp = app;
        FirebaseAuth instance = FirebaseAuth.getInstance(app);
        this.mAuth = instance;
        try {
            instance.setFirebaseUIVersion("6.2.1");
        } catch (Exception e) {
            Log.e(TAG, "Couldn't set the FUI version.", e);
        }
        this.mAuth.useAppLanguage();
    }

    public static Context getApplicationContext() {
        return sApplicationContext;
    }

    public static void setApplicationContext(Context context) {
        sApplicationContext = ((Context) Preconditions.checkNotNull(context, "App context cannot be null.", new Object[0])).getApplicationContext();
    }

    public static AuthUI getInstance() {
        return getInstance(FirebaseApp.getInstance());
    }

    public static AuthUI getInstance(FirebaseApp app) {
        AuthUI authUi;
        if (ProviderAvailability.IS_TWITTER_AVAILABLE) {
            Log.w(TAG, String.format("Beginning with FirebaseUI 6.2.0 you no longer need to include %s to sign in with %s. Go to %s for more information", new Object[]{"the TwitterKit SDK", "Twitter", "https://github.com/firebase/FirebaseUI-Android/releases/tag/6.2.0"}));
        }
        if (ProviderAvailability.IS_GITHUB_AVAILABLE) {
            Log.w(TAG, String.format("Beginning with FirebaseUI 6.2.0 you no longer need to include %s to sign in with %s. Go to %s for more information", new Object[]{"com.firebaseui:firebase-ui-auth-github", "GitHub", "https://github.com/firebase/FirebaseUI-Android/releases/tag/6.2.0"}));
        }
        IdentityHashMap<FirebaseApp, AuthUI> identityHashMap = INSTANCES;
        synchronized (identityHashMap) {
            authUi = identityHashMap.get(app);
            if (authUi == null) {
                authUi = new AuthUI(app);
                identityHashMap.put(app, authUi);
            }
        }
        return authUi;
    }

    public static boolean canHandleIntent(Intent intent) {
        if (intent == null || intent.getData() == null) {
            return false;
        }
        return FirebaseAuth.getInstance().isSignInWithEmailLink(intent.getData().toString());
    }

    public static int getDefaultTheme() {
        return C2354R.C2360style.FirebaseUI;
    }

    private static List<Credential> getCredentialsFromFirebaseUser(FirebaseUser user) {
        if (TextUtils.isEmpty(user.getEmail()) && TextUtils.isEmpty(user.getPhoneNumber())) {
            return Collections.emptyList();
        }
        List<Credential> credentials = new ArrayList<>();
        for (UserInfo userInfo : user.getProviderData()) {
            if (!FirebaseAuthProvider.PROVIDER_ID.equals(userInfo.getProviderId())) {
                String type = ProviderUtils.providerIdToAccountType(userInfo.getProviderId());
                if (type == null) {
                    credentials.add(CredentialUtils.buildCredentialOrThrow(user, "pass", (String) null));
                } else {
                    credentials.add(CredentialUtils.buildCredentialOrThrow(user, (String) null, type));
                }
            }
        }
        return credentials;
    }

    public Task<AuthResult> silentSignIn(Context context, List<IdpConfig> configs) {
        final GoogleSignInOptions googleOptions;
        if (this.mAuth.getCurrentUser() == null) {
            final Context appContext = context.getApplicationContext();
            IdpConfig google = ProviderUtils.getConfigFromIdps(configs, "google.com");
            IdpConfig email = ProviderUtils.getConfigFromIdps(configs, "password");
            if (google == null && email == null) {
                throw new IllegalArgumentException("No supported providers were supplied. Add either Google or email support.");
            }
            String str = null;
            if (google == null) {
                googleOptions = null;
            } else {
                GoogleSignInAccount last = GoogleSignIn.getLastSignedInAccount(appContext);
                if (last != null && last.getIdToken() != null) {
                    return this.mAuth.signInWithCredential(GoogleAuthProvider.getCredential(last.getIdToken(), (String) null));
                }
                googleOptions = (GoogleSignInOptions) google.getParams().getParcelable(ExtraConstants.GOOGLE_SIGN_IN_OPTIONS);
            }
            CredentialsClient credentialsClient = GoogleApiUtils.getCredentialsClient(context);
            CredentialRequest.Builder passwordLoginSupported = new CredentialRequest.Builder().setPasswordLoginSupported(email != null);
            String[] strArr = new String[1];
            if (google != null) {
                str = ProviderUtils.providerIdToAccountType("google.com");
            }
            strArr[0] = str;
            return credentialsClient.request(passwordLoginSupported.setAccountTypes(strArr).build()).continueWithTask(new Continuation<CredentialRequestResponse, Task<AuthResult>>() {
                public Task<AuthResult> then(Task<CredentialRequestResponse> task) {
                    Credential credential = task.getResult().getCredential();
                    String email = credential.getId();
                    String password = credential.getPassword();
                    if (TextUtils.isEmpty(password)) {
                        return GoogleSignIn.getClient(appContext, new GoogleSignInOptions.Builder(googleOptions).setAccountName(email).build()).silentSignIn().continueWithTask(new Continuation<GoogleSignInAccount, Task<AuthResult>>() {
                            public Task<AuthResult> then(Task<GoogleSignInAccount> task) {
                                return AuthUI.this.mAuth.signInWithCredential(GoogleAuthProvider.getCredential(task.getResult().getIdToken(), (String) null));
                            }
                        });
                    }
                    return AuthUI.this.mAuth.signInWithEmailAndPassword(email, password);
                }
            });
        }
        throw new IllegalArgumentException("User already signed in!");
    }

    public Task<Void> signOut(Context context) {
        return Tasks.whenAll((Task<?>[]) new Task[]{signOutIdps(context), GoogleApiUtils.getCredentialsClient(context).disableAutoSignIn().continueWith(new Continuation<Void, Void>() {
            public Void then(Task<Void> task) {
                Exception e = task.getException();
                if (!(e instanceof ApiException) || ((ApiException) e).getStatusCode() != 16) {
                    return task.getResult();
                }
                Log.w(AuthUI.TAG, "Could not disable auto-sign in, maybe there are no SmartLock accounts available?", e);
                return null;
            }
        })}).continueWith(new Continuation<Void, Void>() {
            public Void then(Task<Void> task) {
                task.getResult();
                AuthUI.this.mAuth.signOut();
                return null;
            }
        });
    }

    public Task<Void> delete(Context context) {
        final FirebaseUser currentUser = this.mAuth.getCurrentUser();
        if (currentUser == null) {
            return Tasks.forException(new FirebaseAuthInvalidUserException(String.valueOf(4), "No currently signed in user."));
        }
        final List<Credential> credentials = getCredentialsFromFirebaseUser(currentUser);
        final CredentialsClient client = GoogleApiUtils.getCredentialsClient(context);
        return signOutIdps(context).continueWithTask(new Continuation<Void, Task<Void>>() {
            public Task<Void> then(Task<Void> task) {
                task.getResult();
                List<Task<?>> credentialTasks = new ArrayList<>();
                for (Credential credential : credentials) {
                    credentialTasks.add(client.delete(credential));
                }
                return Tasks.whenAll((Collection<? extends Task<?>>) credentialTasks).continueWith(new Continuation<Void, Void>() {
                    public Void then(Task<Void> task) {
                        Exception e = task.getException();
                        Throwable t = e == null ? null : e.getCause();
                        if (!(t instanceof ApiException) || ((ApiException) t).getStatusCode() != 16) {
                            return task.getResult();
                        }
                        return null;
                    }
                });
            }
        }).continueWithTask(new Continuation<Void, Task<Void>>() {
            public Task<Void> then(Task<Void> task) {
                task.getResult();
                return currentUser.delete();
            }
        });
    }

    private Task<Void> signOutIdps(Context context) {
        if (ProviderAvailability.IS_FACEBOOK_AVAILABLE) {
            LoginManager.getInstance().logOut();
        }
        return GoogleSignIn.getClient(context, GoogleSignInOptions.DEFAULT_SIGN_IN).signOut();
    }

    public SignInIntentBuilder createSignInIntentBuilder() {
        return new SignInIntentBuilder();
    }

    /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig */
    public static final class IdpConfig implements Parcelable {
        public static final Parcelable.Creator<IdpConfig> CREATOR = new Parcelable.Creator<IdpConfig>() {
            public IdpConfig createFromParcel(Parcel in) {
                return new IdpConfig(in);
            }

            public IdpConfig[] newArray(int size) {
                return new IdpConfig[size];
            }
        };
        private final Bundle mParams;
        private final String mProviderId;

        private IdpConfig(String providerId, Bundle params) {
            this.mProviderId = providerId;
            this.mParams = new Bundle(params);
        }

        private IdpConfig(Parcel in) {
            this.mProviderId = in.readString();
            this.mParams = in.readBundle(getClass().getClassLoader());
        }

        public String getProviderId() {
            return this.mProviderId;
        }

        public Bundle getParams() {
            return new Bundle(this.mParams);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString(this.mProviderId);
            parcel.writeBundle(this.mParams);
        }

        public final boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            return this.mProviderId.equals(((IdpConfig) o).mProviderId);
        }

        public final int hashCode() {
            return this.mProviderId.hashCode();
        }

        public String toString() {
            return "IdpConfig{mProviderId='" + this.mProviderId + '\'' + ", mParams=" + this.mParams + '}';
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$Builder */
        public static class Builder {
            private final Bundle mParams = new Bundle();
            /* access modifiers changed from: private */
            public String mProviderId;

            protected Builder(String providerId) {
                if (AuthUI.SUPPORTED_PROVIDERS.contains(providerId) || AuthUI.SUPPORTED_OAUTH_PROVIDERS.contains(providerId)) {
                    this.mProviderId = providerId;
                    return;
                }
                throw new IllegalArgumentException("Unknown provider: " + providerId);
            }

            /* access modifiers changed from: protected */
            public final Bundle getParams() {
                return this.mParams;
            }

            /* access modifiers changed from: protected */
            public void setProviderId(String providerId) {
                this.mProviderId = providerId;
            }

            public IdpConfig build() {
                return new IdpConfig(this.mProviderId, this.mParams);
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$EmailBuilder */
        public static final class EmailBuilder extends Builder {
            public EmailBuilder() {
                super("password");
            }

            public EmailBuilder setAllowNewAccounts(boolean allow) {
                getParams().putBoolean(ExtraConstants.ALLOW_NEW_EMAILS, allow);
                return this;
            }

            public EmailBuilder setRequireName(boolean requireName) {
                getParams().putBoolean(ExtraConstants.REQUIRE_NAME, requireName);
                return this;
            }

            public EmailBuilder enableEmailLinkSignIn() {
                setProviderId("emailLink");
                return this;
            }

            public EmailBuilder setActionCodeSettings(ActionCodeSettings actionCodeSettings) {
                getParams().putParcelable(ExtraConstants.ACTION_CODE_SETTINGS, actionCodeSettings);
                return this;
            }

            public EmailBuilder setForceSameDevice() {
                getParams().putBoolean(ExtraConstants.FORCE_SAME_DEVICE, true);
                return this;
            }

            public IdpConfig build() {
                if (this.mProviderId.equals("emailLink")) {
                    ActionCodeSettings actionCodeSettings = (ActionCodeSettings) getParams().getParcelable(ExtraConstants.ACTION_CODE_SETTINGS);
                    Preconditions.checkNotNull(actionCodeSettings, "ActionCodeSettings cannot be null when using email link sign in.", new Object[0]);
                    if (!actionCodeSettings.canHandleCodeInApp()) {
                        throw new IllegalStateException("You must set canHandleCodeInApp in your ActionCodeSettings to true for Email-Link Sign-in.");
                    }
                }
                return super.build();
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$PhoneBuilder */
        public static final class PhoneBuilder extends Builder {
            public PhoneBuilder() {
                super("phone");
            }

            public PhoneBuilder setDefaultNumber(String number) {
                Preconditions.checkUnset(getParams(), "Cannot overwrite previously set phone number", ExtraConstants.PHONE, ExtraConstants.COUNTRY_ISO, ExtraConstants.NATIONAL_NUMBER);
                if (PhoneNumberUtils.isValid(number)) {
                    getParams().putString(ExtraConstants.PHONE, number);
                    return this;
                }
                throw new IllegalStateException("Invalid phone number: " + number);
            }

            public PhoneBuilder setDefaultNumber(String iso, String number) {
                Preconditions.checkUnset(getParams(), "Cannot overwrite previously set phone number", ExtraConstants.PHONE, ExtraConstants.COUNTRY_ISO, ExtraConstants.NATIONAL_NUMBER);
                if (PhoneNumberUtils.isValidIso(iso)) {
                    getParams().putString(ExtraConstants.COUNTRY_ISO, iso);
                    getParams().putString(ExtraConstants.NATIONAL_NUMBER, number);
                    return this;
                }
                throw new IllegalStateException("Invalid country iso: " + iso);
            }

            public PhoneBuilder setDefaultCountryIso(String iso) {
                Preconditions.checkUnset(getParams(), "Cannot overwrite previously set phone number", ExtraConstants.PHONE, ExtraConstants.COUNTRY_ISO, ExtraConstants.NATIONAL_NUMBER);
                if (PhoneNumberUtils.isValidIso(iso)) {
                    getParams().putString(ExtraConstants.COUNTRY_ISO, iso.toUpperCase(Locale.getDefault()));
                    return this;
                }
                throw new IllegalStateException("Invalid country iso: " + iso);
            }

            public PhoneBuilder setWhitelistedCountries(List<String> whitelistedCountries) {
                if (!getParams().containsKey(ExtraConstants.BLACKLISTED_COUNTRIES)) {
                    Preconditions.checkNotNull(whitelistedCountries, String.format("Invalid argument: Only non-%s whitelists are valid. To specify no whitelist, do not call this method.", new Object[]{"null"}), new Object[0]);
                    Preconditions.checkArgument(!whitelistedCountries.isEmpty(), String.format("Invalid argument: Only non-%s whitelists are valid. To specify no whitelist, do not call this method.", new Object[]{"empty"}));
                    addCountriesToBundle(whitelistedCountries, ExtraConstants.WHITELISTED_COUNTRIES);
                    return this;
                }
                throw new IllegalStateException("You can either whitelist or blacklist country codes for phone authentication.");
            }

            public PhoneBuilder setBlacklistedCountries(List<String> blacklistedCountries) {
                if (!getParams().containsKey(ExtraConstants.WHITELISTED_COUNTRIES)) {
                    Preconditions.checkNotNull(blacklistedCountries, String.format("Invalid argument: Only non-%s blacklists are valid. To specify no blacklist, do not call this method.", new Object[]{"null"}), new Object[0]);
                    Preconditions.checkArgument(!blacklistedCountries.isEmpty(), String.format("Invalid argument: Only non-%s blacklists are valid. To specify no blacklist, do not call this method.", new Object[]{"empty"}));
                    addCountriesToBundle(blacklistedCountries, ExtraConstants.BLACKLISTED_COUNTRIES);
                    return this;
                }
                throw new IllegalStateException("You can either whitelist or blacklist country codes for phone authentication.");
            }

            public IdpConfig build() {
                validateInputs();
                return super.build();
            }

            private void addCountriesToBundle(List<String> CountryIsos, String CountryIsoType) {
                ArrayList<String> uppercaseCodes = new ArrayList<>();
                for (String code : CountryIsos) {
                    uppercaseCodes.add(code.toUpperCase(Locale.getDefault()));
                }
                getParams().putStringArrayList(CountryIsoType, uppercaseCodes);
            }

            private void validateInputs() {
                List<String> whitelistedCountries = getParams().getStringArrayList(ExtraConstants.WHITELISTED_COUNTRIES);
                List<String> blacklistedCountries = getParams().getStringArrayList(ExtraConstants.BLACKLISTED_COUNTRIES);
                if (whitelistedCountries != null && blacklistedCountries != null) {
                    throw new IllegalStateException("You can either whitelist or blacklist country codes for phone authentication.");
                } else if (whitelistedCountries != null) {
                    validateInputs(whitelistedCountries, true);
                } else if (blacklistedCountries != null) {
                    validateInputs(blacklistedCountries, false);
                }
            }

            private void validateInputs(List<String> countries, boolean whitelisted) {
                validateCountryInput(countries);
                validateDefaultCountryInput(countries, whitelisted);
            }

            private void validateCountryInput(List<String> codes) {
                for (String code : codes) {
                    if (!PhoneNumberUtils.isValidIso(code) && !PhoneNumberUtils.isValid(code)) {
                        throw new IllegalArgumentException("Invalid input: You must provide a valid country iso (alpha-2) or code (e-164). e.g. 'us' or '+1'.");
                    }
                }
            }

            private void validateDefaultCountryInput(List<String> codes, boolean whitelisted) {
                if (!getParams().containsKey(ExtraConstants.COUNTRY_ISO) && !getParams().containsKey(ExtraConstants.PHONE)) {
                    return;
                }
                if (!validateDefaultCountryIso(codes, whitelisted) || !validateDefaultPhoneIsos(codes, whitelisted)) {
                    throw new IllegalArgumentException("Invalid default country iso. Make sure it is either part of the whitelisted list or that you haven't blacklisted it.");
                }
            }

            private boolean validateDefaultCountryIso(List<String> codes, boolean whitelisted) {
                return isValidDefaultIso(codes, getDefaultIso(), whitelisted);
            }

            private boolean validateDefaultPhoneIsos(List<String> codes, boolean whitelisted) {
                List<String> phoneIsos = getPhoneIsosFromCode();
                for (String iso : phoneIsos) {
                    if (isValidDefaultIso(codes, iso, whitelisted)) {
                        return true;
                    }
                }
                return phoneIsos.isEmpty();
            }

            private boolean isValidDefaultIso(List<String> codes, String iso, boolean whitelisted) {
                if (iso == null) {
                    return true;
                }
                boolean containsIso = containsCountryIso(codes, iso);
                if (containsIso && whitelisted) {
                    return true;
                }
                if (containsIso || whitelisted) {
                    return false;
                }
                return true;
            }

            private boolean containsCountryIso(List<String> codes, String iso) {
                String iso2 = iso.toUpperCase(Locale.getDefault());
                for (String code : codes) {
                    if (PhoneNumberUtils.isValidIso(code)) {
                        if (code.equals(iso2)) {
                            return true;
                        }
                    } else if (PhoneNumberUtils.getCountryIsosFromCountryCode(code).contains(iso2)) {
                        return true;
                    }
                }
                return false;
            }

            private List<String> getPhoneIsosFromCode() {
                List<String> isosToAdd;
                List<String> isos = new ArrayList<>();
                String phone = getParams().getString(ExtraConstants.PHONE);
                if (!(phone == null || !phone.startsWith("+") || (isosToAdd = PhoneNumberUtils.getCountryIsosFromCountryCode("+" + PhoneNumberUtils.getPhoneNumber(phone).getCountryCode())) == null)) {
                    isos.addAll(isosToAdd);
                }
                return isos;
            }

            private String getDefaultIso() {
                if (getParams().containsKey(ExtraConstants.COUNTRY_ISO)) {
                    return getParams().getString(ExtraConstants.COUNTRY_ISO);
                }
                return null;
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$GoogleBuilder */
        public static final class GoogleBuilder extends Builder {
            public GoogleBuilder() {
                super("google.com");
                Preconditions.checkConfigured(AuthUI.getApplicationContext(), "Check your google-services plugin configuration, the default_web_client_id string wasn't populated.", C2354R.string.default_web_client_id);
            }

            public GoogleBuilder setScopes(List<String> scopes) {
                GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail();
                for (String scope : scopes) {
                    builder.requestScopes(new Scope(scope), new Scope[0]);
                }
                return setSignInOptions(builder.build());
            }

            public GoogleBuilder setSignInOptions(GoogleSignInOptions options) {
                Preconditions.checkUnset(getParams(), "Cannot overwrite previously set sign-in options.", ExtraConstants.GOOGLE_SIGN_IN_OPTIONS);
                GoogleSignInOptions.Builder builder = new GoogleSignInOptions.Builder(options);
                builder.requestEmail().requestIdToken(AuthUI.getApplicationContext().getString(C2354R.string.default_web_client_id));
                getParams().putParcelable(ExtraConstants.GOOGLE_SIGN_IN_OPTIONS, builder.build());
                return this;
            }

            public IdpConfig build() {
                if (!getParams().containsKey(ExtraConstants.GOOGLE_SIGN_IN_OPTIONS)) {
                    setScopes(Collections.emptyList());
                }
                return super.build();
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$FacebookBuilder */
        public static final class FacebookBuilder extends Builder {
            private static final String TAG = "FacebookBuilder";

            public FacebookBuilder() {
                super("facebook.com");
                if (ProviderAvailability.IS_FACEBOOK_AVAILABLE) {
                    Preconditions.checkConfigured(AuthUI.getApplicationContext(), "Facebook provider unconfigured. Make sure to add a `facebook_application_id` string. See the docs for more info: https://github.com/firebase/FirebaseUI-Android/blob/master/auth/README.md#facebook", C2354R.string.facebook_application_id);
                    if (AuthUI.getApplicationContext().getString(C2354R.string.facebook_login_protocol_scheme).equals("fbYOUR_APP_ID")) {
                        Log.w(TAG, "Facebook provider unconfigured for Chrome Custom Tabs.");
                        return;
                    }
                    return;
                }
                throw new RuntimeException("Facebook provider cannot be configured without dependency. Did you forget to add 'com.facebook.android:facebook-login:VERSION' dependency?");
            }

            public FacebookBuilder setPermissions(List<String> permissions) {
                getParams().putStringArrayList(ExtraConstants.FACEBOOK_PERMISSIONS, new ArrayList(permissions));
                return this;
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$AnonymousBuilder */
        public static final class AnonymousBuilder extends Builder {
            public AnonymousBuilder() {
                super(AuthUI.ANONYMOUS_PROVIDER);
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$TwitterBuilder */
        public static final class TwitterBuilder extends GenericOAuthProviderBuilder {
            private static final String PROVIDER_NAME = "Twitter";

            public TwitterBuilder() {
                super("twitter.com", PROVIDER_NAME, C2354R.C2359layout.fui_idp_button_twitter);
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$GitHubBuilder */
        public static final class GitHubBuilder extends GenericOAuthProviderBuilder {
            private static final String PROVIDER_NAME = "Github";

            public GitHubBuilder() {
                super("github.com", PROVIDER_NAME, C2354R.C2359layout.fui_idp_button_github);
            }

            @Deprecated
            public GitHubBuilder setPermissions(List<String> permissions) {
                setScopes(permissions);
                return this;
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$AppleBuilder */
        public static final class AppleBuilder extends GenericOAuthProviderBuilder {
            private static final String PROVIDER_NAME = "Apple";

            public AppleBuilder() {
                super(AuthUI.APPLE_PROVIDER, PROVIDER_NAME, C2354R.C2359layout.fui_idp_button_apple);
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$MicrosoftBuilder */
        public static final class MicrosoftBuilder extends GenericOAuthProviderBuilder {
            private static final String PROVIDER_NAME = "Microsoft";

            public MicrosoftBuilder() {
                super(AuthUI.MICROSOFT_PROVIDER, PROVIDER_NAME, C2354R.C2359layout.fui_idp_button_microsoft);
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$YahooBuilder */
        public static final class YahooBuilder extends GenericOAuthProviderBuilder {
            private static final String PROVIDER_NAME = "Yahoo";

            public YahooBuilder() {
                super(AuthUI.YAHOO_PROVIDER, PROVIDER_NAME, C2354R.C2359layout.fui_idp_button_yahoo);
            }
        }

        /* renamed from: com.firebase.ui.auth.AuthUI$IdpConfig$GenericOAuthProviderBuilder */
        public static class GenericOAuthProviderBuilder extends Builder {
            GenericOAuthProviderBuilder(String providerId, String providerName, int buttonId) {
                super(providerId);
                Preconditions.checkNotNull(providerId, "The provider ID cannot be null.", new Object[0]);
                Preconditions.checkNotNull(providerName, "The provider name cannot be null.", new Object[0]);
                getParams().putString(ExtraConstants.GENERIC_OAUTH_PROVIDER_ID, providerId);
                getParams().putString(ExtraConstants.GENERIC_OAUTH_PROVIDER_NAME, providerName);
                getParams().putInt(ExtraConstants.GENERIC_OAUTH_BUTTON_ID, buttonId);
            }

            public GenericOAuthProviderBuilder setScopes(List<String> scopes) {
                getParams().putStringArrayList(ExtraConstants.GENERIC_OAUTH_SCOPES, new ArrayList(scopes));
                return this;
            }

            public GenericOAuthProviderBuilder setCustomParameters(Map<String, String> customParameters) {
                getParams().putSerializable(ExtraConstants.GENERIC_OAUTH_CUSTOM_PARAMETERS, new HashMap(customParameters));
                return this;
            }
        }
    }

    /* renamed from: com.firebase.ui.auth.AuthUI$AuthIntentBuilder */
    private abstract class AuthIntentBuilder<T extends AuthIntentBuilder> {
        boolean mAlwaysShowProviderChoice;
        AuthMethodPickerLayout mAuthMethodPickerLayout;
        boolean mEnableCredentials;
        boolean mEnableHints;
        int mLogo;
        String mPrivacyPolicyUrl;
        final List<IdpConfig> mProviders;
        int mTheme;
        String mTosUrl;

        /* access modifiers changed from: protected */
        public abstract FlowParameters getFlowParams();

        private AuthIntentBuilder() {
            this.mProviders = new ArrayList();
            this.mLogo = -1;
            this.mTheme = AuthUI.getDefaultTheme();
            this.mAlwaysShowProviderChoice = false;
            this.mEnableCredentials = true;
            this.mEnableHints = true;
            this.mAuthMethodPickerLayout = null;
        }

        public T setTheme(int theme) {
            this.mTheme = Preconditions.checkValidStyle(AuthUI.this.mApp.getApplicationContext(), theme, "theme identifier is unknown or not a style definition", new Object[0]);
            return this;
        }

        public T setLogo(int logo) {
            this.mLogo = logo;
            return this;
        }

        @Deprecated
        public T setTosUrl(String tosUrl) {
            this.mTosUrl = tosUrl;
            return this;
        }

        @Deprecated
        public T setPrivacyPolicyUrl(String privacyPolicyUrl) {
            this.mPrivacyPolicyUrl = privacyPolicyUrl;
            return this;
        }

        public T setTosAndPrivacyPolicyUrls(String tosUrl, String privacyPolicyUrl) {
            Preconditions.checkNotNull(tosUrl, "tosUrl cannot be null", new Object[0]);
            Preconditions.checkNotNull(privacyPolicyUrl, "privacyPolicyUrl cannot be null", new Object[0]);
            this.mTosUrl = tosUrl;
            this.mPrivacyPolicyUrl = privacyPolicyUrl;
            return this;
        }

        public T setAvailableProviders(List<IdpConfig> idpConfigs) {
            Preconditions.checkNotNull(idpConfigs, "idpConfigs cannot be null", new Object[0]);
            if (idpConfigs.size() != 1 || !idpConfigs.get(0).getProviderId().equals(AuthUI.ANONYMOUS_PROVIDER)) {
                this.mProviders.clear();
                for (IdpConfig config : idpConfigs) {
                    if (!this.mProviders.contains(config)) {
                        this.mProviders.add(config);
                    } else {
                        throw new IllegalArgumentException("Each provider can only be set once. " + config.getProviderId() + " was set twice.");
                    }
                }
                return this;
            }
            throw new IllegalStateException("Sign in as guest cannot be the only sign in method. In this case, sign the user in anonymously your self; no UI is needed.");
        }

        public T setIsSmartLockEnabled(boolean enabled) {
            return setIsSmartLockEnabled(enabled, enabled);
        }

        public T setIsSmartLockEnabled(boolean enableCredentials, boolean enableHints) {
            this.mEnableCredentials = enableCredentials;
            this.mEnableHints = enableHints;
            return this;
        }

        public T setAuthMethodPickerLayout(AuthMethodPickerLayout authMethodPickerLayout) {
            this.mAuthMethodPickerLayout = authMethodPickerLayout;
            return this;
        }

        public T setAlwaysShowSignInMethodScreen(boolean alwaysShow) {
            this.mAlwaysShowProviderChoice = alwaysShow;
            return this;
        }

        public Intent build() {
            if (this.mProviders.isEmpty()) {
                this.mProviders.add(new IdpConfig.EmailBuilder().build());
            }
            return KickoffActivity.createIntent(AuthUI.this.mApp.getApplicationContext(), getFlowParams());
        }
    }

    /* renamed from: com.firebase.ui.auth.AuthUI$SignInIntentBuilder */
    public final class SignInIntentBuilder extends AuthIntentBuilder<SignInIntentBuilder> {
        private String mEmailLink;
        private boolean mEnableAnonymousUpgrade;

        public /* bridge */ /* synthetic */ Intent build() {
            return super.build();
        }

        private SignInIntentBuilder() {
            super();
        }

        public SignInIntentBuilder setEmailLink(String emailLink) {
            this.mEmailLink = emailLink;
            return this;
        }

        public SignInIntentBuilder enableAnonymousUsersAutoUpgrade() {
            this.mEnableAnonymousUpgrade = true;
            validateEmailBuilderConfig();
            return this;
        }

        private void validateEmailBuilderConfig() {
            int i = 0;
            while (i < this.mProviders.size()) {
                IdpConfig config = (IdpConfig) this.mProviders.get(i);
                if (!config.getProviderId().equals("emailLink") || config.getParams().getBoolean(ExtraConstants.FORCE_SAME_DEVICE, true)) {
                    i++;
                } else {
                    throw new IllegalStateException("You must force the same device flow when using email link sign in with anonymous user upgrade");
                }
            }
        }

        /* access modifiers changed from: protected */
        public FlowParameters getFlowParams() {
            return new FlowParameters(AuthUI.this.mApp.getName(), this.mProviders, this.mTheme, this.mLogo, this.mTosUrl, this.mPrivacyPolicyUrl, this.mEnableCredentials, this.mEnableHints, this.mEnableAnonymousUpgrade, this.mAlwaysShowProviderChoice, this.mEmailLink, this.mAuthMethodPickerLayout);
        }
    }
}
