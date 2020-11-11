package com.firebase.p008ui.auth.p009ui.idp;

import android.content.Context;
import android.content.Intent;
import com.firebase.p008ui.auth.data.model.FlowParameters;
import com.firebase.p008ui.auth.data.model.User;
import com.firebase.p008ui.auth.p009ui.InvisibleActivityBase;
import com.firebase.p008ui.auth.util.ExtraConstants;
import com.firebase.p008ui.auth.viewmodel.ProviderSignInBase;
import com.firebase.p008ui.auth.viewmodel.idp.SocialProviderResponseHandler;

/* renamed from: com.firebase.ui.auth.ui.idp.SingleSignInActivity */
public class SingleSignInActivity extends InvisibleActivityBase {
    /* access modifiers changed from: private */
    public SocialProviderResponseHandler mHandler;
    private ProviderSignInBase<?> mProvider;

    public static Intent createIntent(Context context, FlowParameters flowParams, User user) {
        return createBaseIntent(context, SingleSignInActivity.class, flowParams).putExtra(ExtraConstants.USER, user);
    }

    /* access modifiers changed from: protected */
    /* JADX WARNING: Code restructure failed: missing block: B:12:0x0074, code lost:
        if (r1.equals("google.com") != false) goto L_0x0078;
     */
    /* JADX WARNING: Removed duplicated region for block: B:15:0x007a  */
    /* JADX WARNING: Removed duplicated region for block: B:22:0x00c2  */
    /* JADX WARNING: Removed duplicated region for block: B:25:0x0101  */
    /* JADX WARNING: Removed duplicated region for block: B:27:? A[RETURN, SYNTHETIC] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void onCreate(android.os.Bundle r10) {
        /*
            r9 = this;
            super.onCreate(r10)
            android.content.Intent r0 = r9.getIntent()
            com.firebase.ui.auth.data.model.User r0 = com.firebase.p008ui.auth.data.model.User.getUser((android.content.Intent) r0)
            java.lang.String r1 = r0.getProviderId()
            com.firebase.ui.auth.data.model.FlowParameters r2 = r9.getFlowParams()
            java.util.List<com.firebase.ui.auth.AuthUI$IdpConfig> r2 = r2.providers
            com.firebase.ui.auth.AuthUI$IdpConfig r2 = com.firebase.p008ui.auth.util.data.ProviderUtils.getConfigFromIdps(r2, r1)
            r3 = 0
            if (r2 != 0) goto L_0x003e
            com.firebase.ui.auth.FirebaseUiException r4 = new com.firebase.ui.auth.FirebaseUiException
            r5 = 3
            java.lang.StringBuilder r6 = new java.lang.StringBuilder
            r6.<init>()
            java.lang.String r7 = "Provider not enabled: "
            java.lang.StringBuilder r6 = r6.append(r7)
            java.lang.StringBuilder r6 = r6.append(r1)
            java.lang.String r6 = r6.toString()
            r4.<init>((int) r5, (java.lang.String) r6)
            android.content.Intent r4 = com.firebase.p008ui.auth.IdpResponse.getErrorIntent(r4)
            r9.finish(r3, r4)
            return
        L_0x003e:
            androidx.lifecycle.ViewModelProvider r4 = androidx.lifecycle.ViewModelProviders.m17of((androidx.fragment.app.FragmentActivity) r9)
            java.lang.Class<com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler> r5 = com.firebase.p008ui.auth.viewmodel.idp.SocialProviderResponseHandler.class
            androidx.lifecycle.ViewModel r5 = r4.get(r5)
            com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler r5 = (com.firebase.p008ui.auth.viewmodel.idp.SocialProviderResponseHandler) r5
            r9.mHandler = r5
            com.firebase.ui.auth.data.model.FlowParameters r6 = r9.getFlowParams()
            r5.init(r6)
            r5 = -1
            int r6 = r1.hashCode()
            r7 = -1536293812(0xffffffffa46e044c, float:-5.1611663E-17)
            r8 = 1
            if (r6 == r7) goto L_0x006e
            r3 = -364826023(0xffffffffea413259, float:-5.839011E25)
            if (r6 == r3) goto L_0x0064
        L_0x0063:
            goto L_0x0077
        L_0x0064:
            java.lang.String r3 = "facebook.com"
            boolean r3 = r1.equals(r3)
            if (r3 == 0) goto L_0x0063
            r3 = r8
            goto L_0x0078
        L_0x006e:
            java.lang.String r6 = "google.com"
            boolean r6 = r1.equals(r6)
            if (r6 == 0) goto L_0x0063
            goto L_0x0078
        L_0x0077:
            r3 = r5
        L_0x0078:
            if (r3 == 0) goto L_0x00c2
            if (r3 == r8) goto L_0x00b4
            android.os.Bundle r3 = r2.getParams()
            java.lang.String r5 = "generic_oauth_provider_id"
            java.lang.String r3 = r3.getString(r5)
            boolean r3 = android.text.TextUtils.isEmpty(r3)
            if (r3 != 0) goto L_0x009b
            java.lang.Class<com.firebase.ui.auth.data.remote.GenericIdpSignInHandler> r3 = com.firebase.p008ui.auth.data.remote.GenericIdpSignInHandler.class
            androidx.lifecycle.ViewModel r3 = r4.get(r3)
            com.firebase.ui.auth.data.remote.GenericIdpSignInHandler r3 = (com.firebase.p008ui.auth.data.remote.GenericIdpSignInHandler) r3
            r3.init(r2)
            r9.mProvider = r3
            goto L_0x00d9
        L_0x009b:
            java.lang.IllegalStateException r3 = new java.lang.IllegalStateException
            java.lang.StringBuilder r5 = new java.lang.StringBuilder
            r5.<init>()
            java.lang.String r6 = "Invalid provider id: "
            java.lang.StringBuilder r5 = r5.append(r6)
            java.lang.StringBuilder r5 = r5.append(r1)
            java.lang.String r5 = r5.toString()
            r3.<init>(r5)
            throw r3
        L_0x00b4:
            java.lang.Class<com.firebase.ui.auth.data.remote.FacebookSignInHandler> r3 = com.firebase.p008ui.auth.data.remote.FacebookSignInHandler.class
            androidx.lifecycle.ViewModel r3 = r4.get(r3)
            com.firebase.ui.auth.data.remote.FacebookSignInHandler r3 = (com.firebase.p008ui.auth.data.remote.FacebookSignInHandler) r3
            r3.init(r2)
            r9.mProvider = r3
            goto L_0x00d9
        L_0x00c2:
            java.lang.Class<com.firebase.ui.auth.data.remote.GoogleSignInHandler> r3 = com.firebase.p008ui.auth.data.remote.GoogleSignInHandler.class
            androidx.lifecycle.ViewModel r3 = r4.get(r3)
            com.firebase.ui.auth.data.remote.GoogleSignInHandler r3 = (com.firebase.p008ui.auth.data.remote.GoogleSignInHandler) r3
            com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params r5 = new com.firebase.ui.auth.data.remote.GoogleSignInHandler$Params
            java.lang.String r6 = r0.getEmail()
            r5.<init>(r2, r6)
            r3.init(r5)
            r9.mProvider = r3
        L_0x00d9:
            com.firebase.ui.auth.viewmodel.ProviderSignInBase<?> r3 = r9.mProvider
            androidx.lifecycle.LiveData r3 = r3.getOperation()
            com.firebase.ui.auth.ui.idp.SingleSignInActivity$1 r5 = new com.firebase.ui.auth.ui.idp.SingleSignInActivity$1
            r5.<init>(r9, r1)
            r3.observe(r9, r5)
            com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler r3 = r9.mHandler
            androidx.lifecycle.LiveData r3 = r3.getOperation()
            com.firebase.ui.auth.ui.idp.SingleSignInActivity$2 r5 = new com.firebase.ui.auth.ui.idp.SingleSignInActivity$2
            r5.<init>(r9)
            r3.observe(r9, r5)
            com.firebase.ui.auth.viewmodel.idp.SocialProviderResponseHandler r3 = r9.mHandler
            androidx.lifecycle.LiveData r3 = r3.getOperation()
            java.lang.Object r3 = r3.getValue()
            if (r3 != 0) goto L_0x0115
            com.firebase.ui.auth.data.model.FlowParameters r3 = r9.getFlowParams()
            java.lang.String r3 = r3.appName
            com.google.firebase.FirebaseApp r3 = com.google.firebase.FirebaseApp.getInstance(r3)
            com.google.firebase.auth.FirebaseAuth r3 = com.google.firebase.auth.FirebaseAuth.getInstance(r3)
            com.firebase.ui.auth.viewmodel.ProviderSignInBase<?> r5 = r9.mProvider
            r5.startSignIn(r3, r9, r1)
        L_0x0115:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.firebase.p008ui.auth.p009ui.idp.SingleSignInActivity.onCreate(android.os.Bundle):void");
    }

    /* access modifiers changed from: protected */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.mHandler.onActivityResult(requestCode, resultCode, data);
        this.mProvider.onActivityResult(requestCode, resultCode, data);
    }
}
