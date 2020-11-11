package com.firebase.client;

import com.bumptech.glide.load.Key;
import com.firebase.client.Transaction;
import com.firebase.client.authentication.AuthenticationManager;
import com.firebase.client.core.CompoundWrite;
import com.firebase.client.core.Context;
import com.firebase.client.core.Path;
import com.firebase.client.core.Repo;
import com.firebase.client.core.RepoManager;
import com.firebase.client.core.ValidationPath;
import com.firebase.client.core.view.QueryParams;
import com.firebase.client.snapshot.ChildKey;
import com.firebase.client.snapshot.Node;
import com.firebase.client.snapshot.NodeUtilities;
import com.firebase.client.snapshot.PriorityUtilities;
import com.firebase.client.utilities.ParsedUrl;
import com.firebase.client.utilities.PushIdGenerator;
import com.firebase.client.utilities.Utilities;
import com.firebase.client.utilities.Validation;
import com.firebase.client.utilities.encoding.JsonHelpers;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;

public class Firebase extends Query {
    private static Config defaultConfig;

    @Deprecated
    public interface AuthListener {
        void onAuthError(FirebaseError firebaseError);

        void onAuthRevoked(FirebaseError firebaseError);

        void onAuthSuccess(Object obj);
    }

    public interface AuthResultHandler {
        void onAuthenticated(AuthData authData);

        void onAuthenticationError(FirebaseError firebaseError);
    }

    public interface AuthStateListener {
        void onAuthStateChanged(AuthData authData);
    }

    public interface CompletionListener {
        void onComplete(FirebaseError firebaseError, Firebase firebase2);
    }

    public interface ResultHandler {
        void onError(FirebaseError firebaseError);

        void onSuccess();
    }

    public interface ValueResultHandler<T> {
        void onError(FirebaseError firebaseError);

        void onSuccess(T t);
    }

    private AuthenticationManager getAuthenticationManager() {
        return getRepo().getAuthenticationManager();
    }

    public Firebase(String url) {
        this(Utilities.parseUrl(url));
    }

    public Firebase(Repo repo, Path path) {
        super(repo, path);
    }

    Firebase(String url, Config config) {
        this(Utilities.parseUrl(url), config);
    }

    private Firebase(ParsedUrl parsedUrl, Config config) {
        super(RepoManager.getRepo(config, parsedUrl.repoInfo), parsedUrl.path, QueryParams.DEFAULT_PARAMS, false);
    }

    private Firebase(ParsedUrl parsedUrl) {
        this(parsedUrl, getDefaultConfig());
    }

    public Firebase child(String pathString) {
        if (pathString != null) {
            if (getPath().isEmpty()) {
                Validation.validateRootPathString(pathString);
            } else {
                Validation.validatePathString(pathString);
            }
            return new Firebase(this.repo, getPath().child(new Path(pathString)));
        }
        throw new NullPointerException("Can't pass null for argument 'pathString' in child()");
    }

    public Firebase push() {
        return new Firebase(this.repo, getPath().child(ChildKey.fromString(PushIdGenerator.generatePushChildName(this.repo.getServerTime()))));
    }

    public void setValue(Object value) {
        setValueInternal(value, PriorityUtilities.parsePriority((Object) null), (CompletionListener) null);
    }

    public void setValue(Object value, Object priority) {
        setValueInternal(value, PriorityUtilities.parsePriority(priority), (CompletionListener) null);
    }

    public void setValue(Object value, CompletionListener listener) {
        setValueInternal(value, PriorityUtilities.parsePriority((Object) null), listener);
    }

    public void setValue(Object value, Object priority, CompletionListener listener) {
        setValueInternal(value, PriorityUtilities.parsePriority(priority), listener);
    }

    private void setValueInternal(Object value, Node priority, final CompletionListener listener) {
        Validation.validateWritablePath(getPath());
        ValidationPath.validateWithObject(getPath(), value);
        try {
            Object bouncedValue = JsonHelpers.getMapper().convertValue(value, Object.class);
            Validation.validateWritableObject(bouncedValue);
            final Node node = NodeUtilities.NodeFromJSON(bouncedValue, priority);
            this.repo.scheduleNow(new Runnable() {
                public void run() {
                    Firebase.this.repo.setValue(Firebase.this.getPath(), node, listener);
                }
            });
        } catch (IllegalArgumentException e) {
            throw new FirebaseException("Failed to parse to snapshot", e);
        }
    }

    public void setPriority(Object priority) {
        setPriorityInternal(PriorityUtilities.parsePriority(priority), (CompletionListener) null);
    }

    public void setPriority(Object priority, CompletionListener listener) {
        setPriorityInternal(PriorityUtilities.parsePriority(priority), listener);
    }

    private void setPriorityInternal(final Node priority, final CompletionListener listener) {
        Validation.validateWritablePath(getPath());
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                Firebase.this.repo.setValue(Firebase.this.getPath().child(ChildKey.getPriorityKey()), priority, listener);
            }
        });
    }

    public void updateChildren(Map<String, Object> update) {
        updateChildren(update, (CompletionListener) null);
    }

    public void updateChildren(final Map<String, Object> update, final CompletionListener listener) {
        if (update != null) {
            final CompoundWrite merge = CompoundWrite.fromPathMerge(Validation.parseAndValidateUpdate(getPath(), update));
            this.repo.scheduleNow(new Runnable() {
                public void run() {
                    Firebase.this.repo.updateChildren(Firebase.this.getPath(), merge, listener, update);
                }
            });
            return;
        }
        throw new NullPointerException("Can't pass null for argument 'update' in updateChildren()");
    }

    public void removeValue() {
        setValue((Object) null);
    }

    public void removeValue(CompletionListener listener) {
        setValue((Object) null, listener);
    }

    public OnDisconnect onDisconnect() {
        Validation.validateWritablePath(getPath());
        return new OnDisconnect(this.repo, getPath());
    }

    @Deprecated
    public void auth(String credential, AuthListener listener) {
        if (listener == null) {
            throw new NullPointerException("Can't pass null for argument 'listener' in auth()");
        } else if (credential != null) {
            getAuthenticationManager().authWithFirebaseToken(credential, listener);
        } else {
            throw new NullPointerException("Can't pass null for argument 'credential' in auth()");
        }
    }

    public void unauth() {
        getAuthenticationManager().unauth();
    }

    @Deprecated
    public void unauth(CompletionListener listener) {
        if (listener != null) {
            getAuthenticationManager().unauth(listener);
            return;
        }
        throw new NullPointerException("Can't pass null for argument 'listener' in unauth()");
    }

    public AuthStateListener addAuthStateListener(AuthStateListener listener) {
        if (listener != null) {
            getAuthenticationManager().addAuthStateListener(listener);
            return listener;
        }
        throw new NullPointerException("Can't pass null for argument 'listener' in addAuthStateListener()");
    }

    public void removeAuthStateListener(AuthStateListener listener) {
        if (listener != null) {
            getAuthenticationManager().removeAuthStateListener(listener);
            return;
        }
        throw new NullPointerException("Can't pass null for argument 'listener' in removeAuthStateListener()");
    }

    public AuthData getAuth() {
        return getAuthenticationManager().getAuth();
    }

    public void authAnonymously(AuthResultHandler handler) {
        getAuthenticationManager().authAnonymously(handler);
    }

    public void authWithPassword(String email, String password, AuthResultHandler handler) {
        if (email == null) {
            throw new NullPointerException("Can't pass null for argument 'email' in authWithPassword()");
        } else if (password != null) {
            getAuthenticationManager().authWithPassword(email, password, handler);
        } else {
            throw new NullPointerException("Can't pass null for argument 'password' in authWithPassword()");
        }
    }

    public void authWithCustomToken(String token, AuthResultHandler handler) {
        if (token != null) {
            getAuthenticationManager().authWithCustomToken(token, handler);
            return;
        }
        throw new NullPointerException("Can't pass null for argument 'token' in authWithCustomToken()");
    }

    public void authWithOAuthToken(String provider, String token, AuthResultHandler handler) {
        if (provider == null) {
            throw new NullPointerException("Can't pass null for argument 'provider' in authWithOAuthToken()");
        } else if (token != null) {
            getAuthenticationManager().authWithOAuthToken(provider, token, handler);
        } else {
            throw new NullPointerException("Can't pass null for argument 'token' in authWithOAuthToken()");
        }
    }

    public void authWithOAuthToken(String provider, Map<String, String> options, AuthResultHandler handler) {
        if (provider == null) {
            throw new NullPointerException("Can't pass null for argument 'provider' in authWithOAuthToken()");
        } else if (options != null) {
            getAuthenticationManager().authWithOAuthToken(provider, options, handler);
        } else {
            throw new NullPointerException("Can't pass null for argument 'options' in authWithOAuthToken()");
        }
    }

    public void createUser(String email, String password, ResultHandler handler) {
        if (email == null) {
            throw new NullPointerException("Can't pass null for argument 'email' in createUser()");
        } else if (password != null) {
            getAuthenticationManager().createUser(email, password, handler);
        } else {
            throw new NullPointerException("Can't pass null for argument 'password' in createUser()");
        }
    }

    public void createUser(String email, String password, ValueResultHandler<Map<String, Object>> handler) {
        if (email == null) {
            throw new NullPointerException("Can't pass null for argument 'email' in createUser()");
        } else if (password != null) {
            getAuthenticationManager().createUser(email, password, handler);
        } else {
            throw new NullPointerException("Can't pass null for argument 'password' in createUser()");
        }
    }

    public void removeUser(String email, String password, ResultHandler handler) {
        if (email == null) {
            throw new NullPointerException("Can't pass null for argument 'email' in removeUser()");
        } else if (password != null) {
            getAuthenticationManager().removeUser(email, password, handler);
        } else {
            throw new NullPointerException("Can't pass null for argument 'password' in removeUser()");
        }
    }

    public void changePassword(String email, String oldPassword, String newPassword, ResultHandler handler) {
        if (email == null) {
            throw new NullPointerException("Can't pass null for argument 'email' in changePassword()");
        } else if (oldPassword == null) {
            throw new NullPointerException("Can't pass null for argument 'oldPassword' in changePassword()");
        } else if (newPassword != null) {
            getAuthenticationManager().changePassword(email, oldPassword, newPassword, handler);
        } else {
            throw new NullPointerException("Can't pass null for argument 'newPassword' in changePassword()");
        }
    }

    public void changeEmail(String oldEmail, String password, String newEmail, ResultHandler handler) {
        if (oldEmail == null) {
            throw new NullPointerException("Can't pass null for argument 'oldEmail' in changeEmail()");
        } else if (password == null) {
            throw new NullPointerException("Can't pass null for argument 'password' in changeEmail()");
        } else if (newEmail != null) {
            getAuthenticationManager().changeEmail(oldEmail, password, newEmail, handler);
        } else {
            throw new NullPointerException("Can't pass null for argument 'newEmail' in changeEmail()");
        }
    }

    public void resetPassword(String email, ResultHandler handler) {
        if (email != null) {
            getAuthenticationManager().resetPassword(email, handler);
            return;
        }
        throw new NullPointerException("Can't pass null for argument 'email' in resetPassword()");
    }

    public void runTransaction(Transaction.Handler handler) {
        runTransaction(handler, true);
    }

    public void runTransaction(final Transaction.Handler handler, final boolean fireLocalEvents) {
        if (handler != null) {
            Validation.validateWritablePath(getPath());
            this.repo.scheduleNow(new Runnable() {
                public void run() {
                    Firebase.this.repo.startTransaction(Firebase.this.getPath(), handler, fireLocalEvents);
                }
            });
            return;
        }
        throw new NullPointerException("Can't pass null for argument 'handler' in runTransaction()");
    }

    public static void goOffline() {
        goOffline(getDefaultConfig());
    }

    static void goOffline(Config config) {
        RepoManager.interrupt((Context) config);
    }

    public static void goOnline() {
        goOnline(getDefaultConfig());
    }

    static void goOnline(Config config) {
        RepoManager.resume((Context) config);
    }

    public FirebaseApp getApp() {
        return this.repo.getFirebaseApp();
    }

    public String toString() {
        Firebase parent = getParent();
        if (parent == null) {
            return this.repo.toString();
        }
        try {
            return parent.toString() + "/" + URLEncoder.encode(getKey(), Key.STRING_CHARSET_NAME).replace("+", "%20");
        } catch (UnsupportedEncodingException e) {
            throw new FirebaseException("Failed to URLEncode key: " + getKey(), e);
        }
    }

    public Firebase getParent() {
        Path parentPath = getPath().getParent();
        if (parentPath != null) {
            return new Firebase(this.repo, parentPath);
        }
        return null;
    }

    public Firebase getRoot() {
        return new Firebase(this.repo, new Path(""));
    }

    public String getKey() {
        if (getPath().isEmpty()) {
            return null;
        }
        return getPath().getBack().asString();
    }

    public boolean equals(Object other) {
        return (other instanceof Firebase) && toString().equals(other.toString());
    }

    public int hashCode() {
        return toString().hashCode();
    }

    public static String getSdkVersion() {
        return Version.SDK_VERSION;
    }

    /* access modifiers changed from: package-private */
    public void setHijackHash(final boolean hijackHash) {
        this.repo.scheduleNow(new Runnable() {
            public void run() {
                Firebase.this.repo.setHijackHash(hijackHash);
            }
        });
    }

    public static synchronized Config getDefaultConfig() {
        Config config;
        synchronized (Firebase.class) {
            if (defaultConfig == null) {
                defaultConfig = new Config();
            }
            config = defaultConfig;
        }
        return config;
    }

    public static synchronized void setDefaultConfig(Config config) {
        synchronized (Firebase.class) {
            Config config2 = defaultConfig;
            if (config2 != null) {
                if (config2.isFrozen()) {
                    throw new FirebaseException("Modifications to Config objects must occur before they are in use");
                }
            }
            defaultConfig = config;
        }
    }

    public static void setAndroidContext(android.content.Context context) {
        if (context != null) {
            Context.setAndroidContext(context);
            return;
        }
        throw new NullPointerException("Can't pass null for argument 'context' in setAndroidContext()");
    }
}
