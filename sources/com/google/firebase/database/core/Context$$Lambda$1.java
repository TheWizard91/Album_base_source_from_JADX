package com.google.firebase.database.core;

import com.google.firebase.database.connection.ConnectionAuthTokenProvider;
import com.google.firebase.database.core.AuthTokenProvider;
import java.util.concurrent.ScheduledExecutorService;

/* compiled from: Context */
final /* synthetic */ class Context$$Lambda$1 implements ConnectionAuthTokenProvider {
    private final AuthTokenProvider arg$1;
    private final ScheduledExecutorService arg$2;

    private Context$$Lambda$1(AuthTokenProvider authTokenProvider, ScheduledExecutorService scheduledExecutorService) {
        this.arg$1 = authTokenProvider;
        this.arg$2 = scheduledExecutorService;
    }

    public static ConnectionAuthTokenProvider lambdaFactory$(AuthTokenProvider authTokenProvider, ScheduledExecutorService scheduledExecutorService) {
        return new Context$$Lambda$1(authTokenProvider, scheduledExecutorService);
    }

    public void getToken(boolean z, ConnectionAuthTokenProvider.GetTokenCallback getTokenCallback) {
        this.arg$1.getToken(z, new AuthTokenProvider.GetTokenCompletionListener(this.arg$2, getTokenCallback) {
            public void onSuccess(String token) {
                executorService.execute(Context$1$$Lambda$1.lambdaFactory$(callback, token));
            }

            public void onError(String error) {
                executorService.execute(Context$1$$Lambda$4.lambdaFactory$(callback, error));
            }
        });
    }
}
