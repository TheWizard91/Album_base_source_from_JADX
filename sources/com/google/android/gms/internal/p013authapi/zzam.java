package com.google.android.gms.internal.p013authapi;

import com.google.android.gms.common.Feature;

/* renamed from: com.google.android.gms.internal.auth-api.zzam */
/* compiled from: com.google.android.gms:play-services-auth@@18.1.0 */
public final class zzam {
    public static final Feature zzcz;
    public static final Feature zzda;
    private static final Feature zzdb;
    private static final Feature zzdc;
    private static final Feature zzdd;
    private static final Feature zzde;
    private static final Feature zzdf;
    public static final Feature[] zzdg;

    static {
        Feature feature = new Feature("auth_api_credentials_begin_sign_in", 4);
        zzcz = feature;
        Feature feature2 = new Feature("auth_api_credentials_sign_out", 2);
        zzda = feature2;
        Feature feature3 = new Feature("auth_api_credentials_authorize", 1);
        zzdb = feature3;
        Feature feature4 = new Feature("auth_api_credentials_revoke_access", 1);
        zzdc = feature4;
        Feature feature5 = new Feature("auth_api_credentials_save_password", 3);
        zzdd = feature5;
        Feature feature6 = new Feature("auth_api_credentials_get_sign_in_intent", 3);
        zzde = feature6;
        Feature feature7 = new Feature("auth_api_credentials_save_account_linking_token", 2);
        zzdf = feature7;
        zzdg = new Feature[]{feature, feature2, feature3, feature4, feature5, feature6, feature7};
    }
}
