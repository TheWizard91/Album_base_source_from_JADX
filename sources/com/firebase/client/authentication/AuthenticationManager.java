package com.firebase.client.authentication;

import androidx.core.app.NotificationCompat;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.firebase.client.AuthData;
import com.firebase.client.CredentialStore;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.core.AuthExpirationBehavior;
import com.firebase.client.core.Context;
import com.firebase.client.core.Path;
import com.firebase.client.core.PersistentConnection;
import com.firebase.client.core.Repo;
import com.firebase.client.core.RepoInfo;
import com.firebase.client.utilities.HttpUtilities;
import com.firebase.client.utilities.LogWrapper;
import com.firebase.client.utilities.Utilities;
import com.firebase.client.utilities.encoding.JsonHelpers;
import java.io.IOException;
import java.net.URI;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Semaphore;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

public class AuthenticationManager {
    static final /* synthetic */ boolean $assertionsDisabled = false;
    private static final String AUTH_DATA_KEY = "authData";
    private static final int CONNECTION_TIMEOUT = 20000;
    private static final String CUSTOM_PROVIDER = "custom";
    private static final String ERROR_KEY = "error";
    private static final String LOG_TAG = "AuthenticationManager";
    private static final String TOKEN_KEY = "token";
    private static final String USER_DATA_KEY = "userData";
    /* access modifiers changed from: private */
    public AuthData authData = null;
    /* access modifiers changed from: private */
    public final PersistentConnection connection;
    private final Context context;
    private AuthAttempt currentAuthAttempt;
    /* access modifiers changed from: private */
    public final Set<Firebase.AuthStateListener> listenerSet;
    /* access modifiers changed from: private */
    public final LogWrapper logger;
    /* access modifiers changed from: private */
    public final Repo repo;
    private final RepoInfo repoInfo;
    private final CredentialStore store;

    private class AuthAttempt {
        /* access modifiers changed from: private */
        public Firebase.AuthResultHandler handler;
        /* access modifiers changed from: private */
        public final Firebase.AuthListener legacyListener;

        AuthAttempt(Firebase.AuthResultHandler handler2) {
            this.handler = handler2;
            this.legacyListener = null;
        }

        AuthAttempt(Firebase.AuthListener legacyListener2) {
            this.legacyListener = legacyListener2;
            this.handler = null;
        }

        public void fireError(final FirebaseError error) {
            if (this.legacyListener != null || this.handler != null) {
                AuthenticationManager.this.fireEvent(new Runnable() {
                    public void run() {
                        if (AuthAttempt.this.legacyListener != null) {
                            AuthAttempt.this.legacyListener.onAuthError(error);
                        } else if (AuthAttempt.this.handler != null) {
                            AuthAttempt.this.handler.onAuthenticationError(error);
                            Firebase.AuthResultHandler unused = AuthAttempt.this.handler = null;
                        }
                    }
                });
            }
        }

        public void fireSuccess(final AuthData authData) {
            if (this.legacyListener != null || this.handler != null) {
                AuthenticationManager.this.fireEvent(new Runnable() {
                    public void run() {
                        if (AuthAttempt.this.legacyListener != null) {
                            AuthAttempt.this.legacyListener.onAuthSuccess(authData);
                        } else if (AuthAttempt.this.handler != null) {
                            AuthAttempt.this.handler.onAuthenticated(authData);
                            Firebase.AuthResultHandler unused = AuthAttempt.this.handler = null;
                        }
                    }
                });
            }
        }

        public void fireRevoked(final FirebaseError error) {
            if (this.legacyListener != null) {
                AuthenticationManager.this.fireEvent(new Runnable() {
                    public void run() {
                        AuthAttempt.this.legacyListener.onAuthRevoked(error);
                    }
                });
            }
        }
    }

    public AuthenticationManager(Context context2, Repo repo2, RepoInfo repoInfo2, PersistentConnection connection2) {
        this.context = context2;
        this.repo = repo2;
        this.repoInfo = repoInfo2;
        this.connection = connection2;
        this.store = context2.getCredentialStore();
        this.logger = context2.getLogger(LOG_TAG);
        this.listenerSet = new HashSet();
    }

    /* access modifiers changed from: private */
    public void fireEvent(Runnable r) {
        this.context.getEventTarget().postEvent(r);
    }

    /* access modifiers changed from: private */
    public void fireOnSuccess(final Firebase.ValueResultHandler handler, final Object result) {
        if (handler != null) {
            fireEvent(new Runnable() {
                public void run() {
                    handler.onSuccess(result);
                }
            });
        }
    }

    /* access modifiers changed from: private */
    public void fireOnError(final Firebase.ValueResultHandler handler, final FirebaseError error) {
        if (handler != null) {
            fireEvent(new Runnable() {
                public void run() {
                    handler.onError(error);
                }
            });
        }
    }

    private Firebase.ValueResultHandler ignoreResultValueHandler(final Firebase.ResultHandler handler) {
        return new Firebase.ValueResultHandler() {
            public void onSuccess(Object result) {
                handler.onSuccess();
            }

            public void onError(FirebaseError error) {
                handler.onError(error);
            }
        };
    }

    /* access modifiers changed from: private */
    public void preemptAnyExistingAttempts() {
        if (this.currentAuthAttempt != null) {
            this.currentAuthAttempt.fireError(new FirebaseError(-5, "Due to another authentication attempt, this authentication attempt was aborted before it could complete."));
            this.currentAuthAttempt = null;
        }
    }

    /* access modifiers changed from: private */
    public FirebaseError decodeErrorResponse(Object errorResponse) {
        String code = (String) Utilities.getOrNull(errorResponse, "code", String.class);
        String message = (String) Utilities.getOrNull(errorResponse, "message", String.class);
        String details = (String) Utilities.getOrNull(errorResponse, "details", String.class);
        if (code != null) {
            return FirebaseError.fromStatus(code, message, details);
        }
        return new FirebaseError(-999, message == null ? "Error while authenticating." : message, details);
    }

    /* access modifiers changed from: private */
    public boolean attemptHasBeenPreempted(AuthAttempt attempt) {
        return attempt != this.currentAuthAttempt;
    }

    /* access modifiers changed from: private */
    public AuthAttempt newAuthAttempt(Firebase.AuthResultHandler handler) {
        preemptAnyExistingAttempts();
        AuthAttempt authAttempt = new AuthAttempt(handler);
        this.currentAuthAttempt = authAttempt;
        return authAttempt;
    }

    /* access modifiers changed from: private */
    public AuthAttempt newAuthAttempt(Firebase.AuthListener listener) {
        preemptAnyExistingAttempts();
        AuthAttempt authAttempt = new AuthAttempt(listener);
        this.currentAuthAttempt = authAttempt;
        return authAttempt;
    }

    /* access modifiers changed from: private */
    public void fireAuthErrorIfNotPreempted(final FirebaseError error, final AuthAttempt attempt) {
        if (!attemptHasBeenPreempted(attempt)) {
            if (attempt != null) {
                fireEvent(new Runnable() {
                    public void run() {
                        attempt.fireError(error);
                    }
                });
            }
            this.currentAuthAttempt = null;
        }
    }

    private void checkServerSettings() {
        if (this.repoInfo.isDemoHost()) {
            this.logger.warn("Firebase authentication is supported on production Firebases only (*.firebaseio.com). To secure your Firebase, create a production Firebase at https://www.firebase.com.");
        } else if (this.repoInfo.isCustomHost() && !this.context.isCustomAuthenticationServerSet()) {
            throw new IllegalStateException("For a custom firebase host you must first set your authentication server before using authentication features!");
        }
    }

    private String getFirebaseCredentialIdentifier() {
        return this.repoInfo.host;
    }

    /* access modifiers changed from: private */
    public void scheduleNow(Runnable r) {
        this.context.getRunLoop().scheduleNow(r);
    }

    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r16v2, resolved type: java.util.HashMap} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v4, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v13, resolved type: java.lang.String} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r6v5, resolved type: java.lang.Object} */
    /* JADX DEBUG: Multi-variable search result rejected for TypeSearchVarInfo{r5v15, resolved type: java.lang.String} */
    /* JADX WARNING: Multi-variable type inference failed */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private com.firebase.client.AuthData parseAuthData(java.lang.String r20, java.util.Map<java.lang.String, java.lang.Object> r21, java.util.Map<java.lang.String, java.lang.Object> r22) {
        /*
            r19 = this;
            r0 = r19
            r1 = r21
            r2 = r22
            java.lang.Class<java.util.Map> r3 = java.util.Map.class
            java.lang.String r4 = "auth"
            java.lang.Object r3 = com.firebase.client.utilities.Utilities.getOrNull(r1, r4, r3)
            java.util.Map r3 = (java.util.Map) r3
            java.lang.String r4 = "Received invalid auth data: "
            if (r3 != 0) goto L_0x002a
            com.firebase.client.utilities.LogWrapper r5 = r0.logger
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.StringBuilder r6 = r6.append(r4)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r6 = r6.toString()
            r5.warn(r6)
        L_0x002a:
            java.lang.String r5 = "expires"
            java.lang.Object r12 = r1.get(r5)
            if (r12 != 0) goto L_0x0036
            r5 = 0
            r13 = r5
            goto L_0x0061
        L_0x0036:
            boolean r5 = r12 instanceof java.lang.Integer
            if (r5 == 0) goto L_0x0044
            r5 = r12
            java.lang.Integer r5 = (java.lang.Integer) r5
            int r5 = r5.intValue()
            long r5 = (long) r5
            r13 = r5
            goto L_0x0061
        L_0x0044:
            boolean r5 = r12 instanceof java.lang.Long
            if (r5 == 0) goto L_0x0051
            r5 = r12
            java.lang.Long r5 = (java.lang.Long) r5
            long r5 = r5.longValue()
            r13 = r5
            goto L_0x0061
        L_0x0051:
            boolean r5 = r12 instanceof java.lang.Double
            if (r5 == 0) goto L_0x005e
            r5 = r12
            java.lang.Double r5 = (java.lang.Double) r5
            long r5 = r5.longValue()
            r13 = r5
            goto L_0x0061
        L_0x005e:
            r5 = 0
            r13 = r5
        L_0x0061:
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            java.lang.String r6 = "uid"
            java.lang.Object r5 = com.firebase.client.utilities.Utilities.getOrNull(r3, r6, r5)
            java.lang.String r5 = (java.lang.String) r5
            if (r5 != 0) goto L_0x0079
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            java.lang.Object r6 = com.firebase.client.utilities.Utilities.getOrNull(r2, r6, r7)
            r5 = r6
            java.lang.String r5 = (java.lang.String) r5
            r15 = r5
            goto L_0x007a
        L_0x0079:
            r15 = r5
        L_0x007a:
            java.lang.Class<java.lang.String> r5 = java.lang.String.class
            java.lang.String r6 = "provider"
            java.lang.Object r5 = com.firebase.client.utilities.Utilities.getOrNull(r3, r6, r5)
            java.lang.String r5 = (java.lang.String) r5
            if (r5 != 0) goto L_0x008f
            java.lang.Class<java.lang.String> r7 = java.lang.String.class
            java.lang.Object r6 = com.firebase.client.utilities.Utilities.getOrNull(r2, r6, r7)
            r5 = r6
            java.lang.String r5 = (java.lang.String) r5
        L_0x008f:
            if (r5 != 0) goto L_0x0095
            java.lang.String r5 = "custom"
            r11 = r5
            goto L_0x0096
        L_0x0095:
            r11 = r5
        L_0x0096:
            if (r15 == 0) goto L_0x009e
            boolean r5 = r15.isEmpty()
            if (r5 == 0) goto L_0x00b4
        L_0x009e:
            com.firebase.client.utilities.LogWrapper r5 = r0.logger
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.StringBuilder r4 = r6.append(r4)
            java.lang.StringBuilder r4 = r4.append(r3)
            java.lang.String r4 = r4.toString()
            r5.warn(r4)
        L_0x00b4:
            java.lang.Class<java.util.Map> r4 = java.util.Map.class
            java.lang.Object r4 = com.firebase.client.utilities.Utilities.getOrNull(r2, r11, r4)
            java.util.Map r4 = (java.util.Map) r4
            if (r4 != 0) goto L_0x00c7
            java.util.HashMap r5 = new java.util.HashMap
            r5.<init>()
            r4 = r5
            r16 = r4
            goto L_0x00c9
        L_0x00c7:
            r16 = r4
        L_0x00c9:
            com.firebase.client.AuthData r17 = new com.firebase.client.AuthData
            r4 = r17
            r5 = r20
            r6 = r13
            r8 = r15
            r9 = r11
            r10 = r3
            r18 = r11
            r11 = r16
            r4.<init>(r5, r6, r8, r9, r10, r11)
            return r17
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.client.authentication.AuthenticationManager.parseAuthData(java.lang.String, java.util.Map, java.util.Map):com.firebase.client.AuthData");
    }

    /* access modifiers changed from: private */
    public void handleBadAuthStatus(FirebaseError error, AuthAttempt attempt, boolean revoked) {
        if ((error.getCode() == -6) && this.context.getAuthExpirationBehavior() == AuthExpirationBehavior.PAUSE_WRITES_UNTIL_REAUTH) {
            if (this.logger.logsDebug()) {
                this.logger.debug("Pausing writes due to expired token.");
            }
            this.connection.pauseWrites();
        } else if (!this.connection.writesPaused()) {
            clearSession();
        } else if (this.context.getAuthExpirationBehavior() != AuthExpirationBehavior.PAUSE_WRITES_UNTIL_REAUTH) {
            throw new AssertionError();
        } else if (this.logger.logsDebug()) {
            this.logger.debug("Invalid auth while writes are paused; keeping existing session.");
        }
        updateAuthState((AuthData) null);
        if (attempt == null) {
            return;
        }
        if (revoked) {
            attempt.fireRevoked(error);
        } else {
            attempt.fireError(error);
        }
    }

    /* access modifiers changed from: private */
    public void handleAuthSuccess(String credential, Map<String, Object> authDataMap, Map<String, Object> optionalUserData, boolean isNewSession, AuthAttempt attempt) {
        if (isNewSession && (!(authDataMap.get("auth") == null && authDataMap.get("expires") == null) && !saveSession(credential, authDataMap, optionalUserData))) {
            this.logger.warn("Failed to store credentials! Authentication will not be persistent!");
        }
        AuthData authData2 = parseAuthData(credential, authDataMap, optionalUserData);
        updateAuthState(authData2);
        if (attempt != null) {
            attempt.fireSuccess(authData2);
        }
        if (this.connection.writesPaused()) {
            if (this.logger.logsDebug()) {
                this.logger.debug("Unpausing writes after successful login.");
            }
            this.connection.unpauseWrites();
        }
    }

    public void resumeSession() {
        try {
            String credentialData = this.store.loadCredential(getFirebaseCredentialIdentifier(), this.context.getSessionPersistenceKey());
            if (credentialData != null) {
                Map<String, Object> credentials = (Map) JsonHelpers.getMapper().readValue(credentialData, (TypeReference) new TypeReference<Map<String, Object>>() {
                });
                final String tokenString = (String) Utilities.getOrNull(credentials, TOKEN_KEY, String.class);
                final Map<String, Object> authDataObj = (Map) Utilities.getOrNull(credentials, AUTH_DATA_KEY, Map.class);
                final Map<String, Object> userData = (Map) Utilities.getOrNull(credentials, USER_DATA_KEY, Map.class);
                if (authDataObj != null) {
                    updateAuthState(parseAuthData(tokenString, authDataObj, userData));
                    this.context.getRunLoop().scheduleNow(new Runnable() {
                        public void run() {
                            AuthenticationManager.this.connection.auth(tokenString, new Firebase.AuthListener() {
                                public void onAuthError(FirebaseError error) {
                                    AuthenticationManager.this.handleBadAuthStatus(error, (AuthAttempt) null, false);
                                }

                                public void onAuthSuccess(Object authData) {
                                    AuthenticationManager.this.handleAuthSuccess(tokenString, authDataObj, userData, false, (AuthAttempt) null);
                                }

                                public void onAuthRevoked(FirebaseError error) {
                                    AuthenticationManager.this.handleBadAuthStatus(error, (AuthAttempt) null, true);
                                }
                            });
                        }
                    });
                }
            }
        } catch (IOException e) {
            this.logger.warn("Failed resuming authentication session!", e);
            clearSession();
        }
    }

    private boolean saveSession(String token, Map<String, Object> authData2, Map<String, Object> userData) {
        String firebaseId = getFirebaseCredentialIdentifier();
        String sessionId = this.context.getSessionPersistenceKey();
        this.store.clearCredential(firebaseId, sessionId);
        Map<String, Object> sessionMap = new HashMap<>();
        sessionMap.put(TOKEN_KEY, token);
        sessionMap.put(AUTH_DATA_KEY, authData2);
        sessionMap.put(USER_DATA_KEY, userData);
        try {
            if (this.logger.logsDebug()) {
                this.logger.debug("Storing credentials for Firebase \"" + firebaseId + "\" and session \"" + sessionId + "\".");
            }
            return this.store.storeCredential(firebaseId, sessionId, JsonHelpers.getMapper().writeValueAsString(sessionMap));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /* access modifiers changed from: private */
    public boolean clearSession() {
        String firebaseId = getFirebaseCredentialIdentifier();
        String sessionId = this.context.getSessionPersistenceKey();
        if (this.logger.logsDebug()) {
            this.logger.debug("Clearing credentials for Firebase \"" + firebaseId + "\" and session \"" + sessionId + "\".");
        }
        return this.store.clearCredential(firebaseId, sessionId);
    }

    /* access modifiers changed from: private */
    public void updateAuthState(final AuthData authData2) {
        AuthData authData3 = this.authData;
        boolean z = true;
        if (authData3 != null ? authData3.equals(authData2) : authData2 == null) {
            z = false;
        }
        boolean changed = z;
        this.authData = authData2;
        if (changed) {
            for (final Firebase.AuthStateListener listener : this.listenerSet) {
                fireEvent(new Runnable() {
                    public void run() {
                        listener.onAuthStateChanged(authData2);
                    }
                });
            }
        }
    }

    private String buildUrlPath(String urlPath) {
        StringBuilder path = new StringBuilder();
        path.append("/v2/");
        path.append(this.repoInfo.namespace);
        if (!urlPath.startsWith("/")) {
            path.append("/");
        }
        path.append(urlPath);
        return path.toString();
    }

    private void makeRequest(String urlPath, HttpUtilities.HttpRequestType type, Map<String, String> urlParams, Map<String, String> requestParams, RequestHandler handler) {
        Map<String, String> actualUrlParams = new HashMap<>(urlParams);
        actualUrlParams.put(NotificationCompat.CATEGORY_TRANSPORT, "json");
        actualUrlParams.put("v", this.context.getPlatformVersion());
        final HttpUriRequest request = HttpUtilities.requestWithType(this.context.getAuthenticationServer(), buildUrlPath(urlPath), type, actualUrlParams, requestParams);
        if (this.logger.logsDebug()) {
            URI uri = request.getURI();
            String scheme = uri.getScheme();
            String authority = uri.getAuthority();
            String path = uri.getPath();
            int numQueryParams = uri.getQuery().split("&").length;
            this.logger.debug(String.format("Sending request to %s://%s%s with %d query params", new Object[]{scheme, authority, path, Integer.valueOf(numQueryParams)}));
        }
        final RequestHandler requestHandler = handler;
        this.context.runBackgroundTask(new Runnable() {
            public void run() {
                HttpParams httpParameters = new BasicHttpParams();
                HttpConnectionParams.setConnectionTimeout(httpParameters, AuthenticationManager.CONNECTION_TIMEOUT);
                HttpConnectionParams.setSoTimeout(httpParameters, AuthenticationManager.CONNECTION_TIMEOUT);
                DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
                httpClient.getParams().setParameter("http.protocol.cookie-policy", "compatibility");
                try {
                    final Map<String, Object> result = (Map) httpClient.execute(request, new JsonBasicResponseHandler());
                    if (result != null) {
                        AuthenticationManager.this.scheduleNow(new Runnable() {
                            public void run() {
                                requestHandler.onResult(result);
                            }
                        });
                        return;
                    }
                    throw new IOException("Authentication server did not respond with a valid response");
                } catch (IOException e) {
                    AuthenticationManager.this.scheduleNow(new Runnable() {
                        public void run() {
                            requestHandler.onError(e);
                        }
                    });
                }
            }
        });
    }

    /* access modifiers changed from: private */
    public void makeAuthenticationRequest(String urlPath, Map<String, String> params, Firebase.AuthResultHandler handler) {
        final AuthAttempt attempt = newAuthAttempt(handler);
        makeRequest(urlPath, HttpUtilities.HttpRequestType.GET, params, Collections.emptyMap(), new RequestHandler() {
            public void onResult(Map<String, Object> result) {
                Object errorResponse = result.get("error");
                String token = (String) Utilities.getOrNull(result, AuthenticationManager.TOKEN_KEY, String.class);
                if (errorResponse != null || token == null) {
                    AuthenticationManager.this.fireAuthErrorIfNotPreempted(AuthenticationManager.this.decodeErrorResponse(errorResponse), attempt);
                } else if (!AuthenticationManager.this.attemptHasBeenPreempted(attempt)) {
                    AuthenticationManager.this.authWithCredential(token, result, attempt);
                }
            }

            public void onError(IOException e) {
                AuthenticationManager.this.fireAuthErrorIfNotPreempted(new FirebaseError(-24, "There was an exception while connecting to the authentication server: " + e.getLocalizedMessage()), attempt);
            }
        });
    }

    /* access modifiers changed from: private */
    public void makeOperationRequest(String urlPath, HttpUtilities.HttpRequestType type, Map<String, String> urlParams, Map<String, String> requestParams, Firebase.ResultHandler handler, boolean logUserOut) {
        makeOperationRequestWithResult(urlPath, type, urlParams, requestParams, ignoreResultValueHandler(handler), logUserOut);
    }

    /* access modifiers changed from: private */
    public void makeOperationRequestWithResult(String urlPath, HttpUtilities.HttpRequestType type, Map<String, String> urlParams, Map<String, String> requestParams, final Firebase.ValueResultHandler<Map<String, Object>> handler, final boolean logUserOut) {
        makeRequest(urlPath, type, urlParams, requestParams, new RequestHandler() {
            public void onResult(final Map<String, Object> result) {
                String uid;
                Object errorResponse = result.get("error");
                if (errorResponse == null) {
                    if (logUserOut && (uid = (String) Utilities.getOrNull(result, "uid", String.class)) != null && AuthenticationManager.this.authData != null && uid.equals(AuthenticationManager.this.authData.getUid())) {
                        AuthenticationManager.this.unauth((Firebase.CompletionListener) null, false);
                    }
                    AuthenticationManager.this.scheduleNow(new Runnable() {
                        public void run() {
                            AuthenticationManager.this.fireOnSuccess(handler, result);
                        }
                    });
                    return;
                }
                AuthenticationManager.this.fireOnError(handler, AuthenticationManager.this.decodeErrorResponse(errorResponse));
            }

            public void onError(IOException e) {
                AuthenticationManager.this.fireOnError(handler, new FirebaseError(-24, "There was an exception while performing the request: " + e.getLocalizedMessage()));
            }
        });
    }

    /* access modifiers changed from: private */
    public void authWithCredential(final String credential, final Map<String, Object> optionalUserData, final AuthAttempt attempt) {
        if (attempt == this.currentAuthAttempt) {
            if (this.logger.logsDebug()) {
                this.logger.debug("Authenticating with credential of length " + credential.length());
            }
            this.currentAuthAttempt = null;
            this.connection.auth(credential, new Firebase.AuthListener() {
                public void onAuthSuccess(Object authData) {
                    AuthenticationManager.this.handleAuthSuccess(credential, (Map) authData, optionalUserData, true, attempt);
                }

                public void onAuthRevoked(FirebaseError error) {
                    AuthenticationManager.this.handleBadAuthStatus(error, attempt, true);
                }

                public void onAuthError(FirebaseError error) {
                    AuthenticationManager.this.handleBadAuthStatus(error, attempt, false);
                }
            });
            return;
        }
        throw new IllegalStateException("Ooops. We messed up tracking which authentications are running!");
    }

    public AuthData getAuth() {
        return this.authData;
    }

    public void unauth() {
        checkServerSettings();
        unauth((Firebase.CompletionListener) null);
    }

    public void unauth(Firebase.CompletionListener listener) {
        unauth(listener, true);
    }

    public void unauth(final Firebase.CompletionListener listener, boolean waitForCompletion) {
        checkServerSettings();
        final Semaphore semaphore = new Semaphore(0);
        scheduleNow(new Runnable() {
            public void run() {
                AuthenticationManager.this.preemptAnyExistingAttempts();
                AuthenticationManager.this.updateAuthState((AuthData) null);
                semaphore.release();
                boolean unused = AuthenticationManager.this.clearSession();
                AuthenticationManager.this.connection.unauth(new Firebase.CompletionListener() {
                    public void onComplete(FirebaseError error, Firebase unusedRef) {
                        if (listener != null) {
                            listener.onComplete(error, new Firebase(AuthenticationManager.this.repo, new Path("")));
                        }
                    }
                });
                if (AuthenticationManager.this.connection.writesPaused()) {
                    if (AuthenticationManager.this.logger.logsDebug()) {
                        AuthenticationManager.this.logger.debug("Unpausing writes after explicit unauth.");
                    }
                    AuthenticationManager.this.connection.unpauseWrites();
                }
            }
        });
        if (waitForCompletion) {
            try {
                semaphore.acquire();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void addAuthStateListener(final Firebase.AuthStateListener listener) {
        checkServerSettings();
        scheduleNow(new Runnable() {
            public void run() {
                AuthenticationManager.this.listenerSet.add(listener);
                final AuthData authData = AuthenticationManager.this.authData;
                AuthenticationManager.this.fireEvent(new Runnable() {
                    public void run() {
                        listener.onAuthStateChanged(authData);
                    }
                });
            }
        });
    }

    public void removeAuthStateListener(final Firebase.AuthStateListener listener) {
        checkServerSettings();
        scheduleNow(new Runnable() {
            public void run() {
                AuthenticationManager.this.listenerSet.remove(listener);
            }
        });
    }

    public void authAnonymously(final Firebase.AuthResultHandler handler) {
        checkServerSettings();
        scheduleNow(new Runnable() {
            public void run() {
                AuthenticationManager.this.makeAuthenticationRequest(Constants.FIREBASE_AUTH_ANONYMOUS_PATH, new HashMap<>(), handler);
            }
        });
    }

    public void authWithPassword(final String email, final String password, final Firebase.AuthResultHandler handler) {
        checkServerSettings();
        scheduleNow(new Runnable() {
            public void run() {
                Map<String, String> params = new HashMap<>();
                params.put("email", email);
                params.put("password", password);
                AuthenticationManager.this.makeAuthenticationRequest(Constants.FIREBASE_AUTH_PASSWORD_PATH, params, handler);
            }
        });
    }

    public void authWithCustomToken(final String token, final Firebase.AuthResultHandler handler) {
        scheduleNow(new Runnable() {
            public void run() {
                AuthenticationManager.this.authWithCredential(token, (Map<String, Object>) null, AuthenticationManager.this.newAuthAttempt(handler));
            }
        });
    }

    public void authWithFirebaseToken(final String token, final Firebase.AuthListener listener) {
        scheduleNow(new Runnable() {
            public void run() {
                AuthenticationManager.this.authWithCredential(token, (Map<String, Object>) null, AuthenticationManager.this.newAuthAttempt(listener));
            }
        });
    }

    public void authWithOAuthToken(String provider, String token, Firebase.AuthResultHandler handler) {
        if (token != null) {
            Map<String, String> params = new HashMap<>();
            params.put("access_token", token);
            authWithOAuthToken(provider, params, handler);
            return;
        }
        throw new IllegalArgumentException("Token must not be null!");
    }

    public void authWithOAuthToken(final String provider, final Map<String, String> params, final Firebase.AuthResultHandler handler) {
        checkServerSettings();
        scheduleNow(new Runnable() {
            public void run() {
                AuthenticationManager.this.makeAuthenticationRequest(String.format(Constants.FIREBASE_AUTH_PROVIDER_PATH_FORMAT, new Object[]{provider}), params, handler);
            }
        });
    }

    public void createUser(String email, String password, Firebase.ResultHandler handler) {
        createUser(email, password, (Firebase.ValueResultHandler<Map<String, Object>>) ignoreResultValueHandler(handler));
    }

    public void createUser(final String email, final String password, final Firebase.ValueResultHandler<Map<String, Object>> handler) {
        checkServerSettings();
        scheduleNow(new Runnable() {
            public void run() {
                HashMap hashMap = new HashMap();
                hashMap.put("email", email);
                hashMap.put("password", password);
                AuthenticationManager.this.makeOperationRequestWithResult(Constants.FIREBASE_AUTH_CREATE_USER_PATH, HttpUtilities.HttpRequestType.POST, Collections.emptyMap(), hashMap, handler, false);
            }
        });
    }

    public void removeUser(final String email, final String password, final Firebase.ResultHandler handler) {
        checkServerSettings();
        scheduleNow(new Runnable() {
            public void run() {
                HashMap hashMap = new HashMap();
                hashMap.put("password", password);
                AuthenticationManager.this.makeOperationRequest(String.format(Constants.FIREBASE_AUTH_REMOVE_USER_PATH_FORMAT, new Object[]{email}), HttpUtilities.HttpRequestType.DELETE, hashMap, Collections.emptyMap(), handler, true);
            }
        });
    }

    public void changePassword(String email, String oldPassword, String newPassword, Firebase.ResultHandler handler) {
        checkServerSettings();
        final String str = oldPassword;
        final String str2 = newPassword;
        final String str3 = email;
        final Firebase.ResultHandler resultHandler = handler;
        scheduleNow(new Runnable() {
            public void run() {
                HashMap hashMap = new HashMap();
                hashMap.put("oldPassword", str);
                Map<String, String> requestParams = new HashMap<>();
                requestParams.put("password", str2);
                AuthenticationManager.this.makeOperationRequest(String.format(Constants.FIREBASE_AUTH_PASSWORD_PATH_FORMAT, new Object[]{str3}), HttpUtilities.HttpRequestType.PUT, hashMap, requestParams, resultHandler, false);
            }
        });
    }

    public void changeEmail(String oldEmail, String password, String newEmail, Firebase.ResultHandler handler) {
        checkServerSettings();
        final String str = password;
        final String str2 = newEmail;
        final String str3 = oldEmail;
        final Firebase.ResultHandler resultHandler = handler;
        scheduleNow(new Runnable() {
            public void run() {
                HashMap hashMap = new HashMap();
                hashMap.put("password", str);
                Map<String, String> requestParams = new HashMap<>();
                requestParams.put("email", str2);
                AuthenticationManager.this.makeOperationRequest(String.format(Constants.FIREBASE_AUTH_EMAIL_PATH_FORMAT, new Object[]{str3}), HttpUtilities.HttpRequestType.PUT, hashMap, requestParams, resultHandler, false);
            }
        });
    }

    public void resetPassword(final String email, final Firebase.ResultHandler handler) {
        checkServerSettings();
        scheduleNow(new Runnable() {
            public void run() {
                String url = String.format(Constants.FIREBASE_AUTH_PASSWORD_PATH_FORMAT, new Object[]{email});
                Map<String, String> params = Collections.emptyMap();
                AuthenticationManager.this.makeOperationRequest(url, HttpUtilities.HttpRequestType.POST, params, params, handler, false);
            }
        });
    }
}
