package com.google.android.play.core.internal;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.os.LocaleList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/* renamed from: com.google.android.play.core.internal.bs */
public final class C3034bs {

    /* renamed from: a */
    private final Context f1311a;

    public C3034bs(Context context) {
        this.f1311a = context;
    }

    /* renamed from: a */
    private static String m752a(Locale locale) {
        String str;
        String valueOf = String.valueOf(locale.getLanguage());
        if (!locale.getCountry().isEmpty()) {
            String valueOf2 = String.valueOf(locale.getCountry());
            str = valueOf2.length() == 0 ? new String("_") : "_".concat(valueOf2);
        } else {
            str = "";
        }
        String valueOf3 = String.valueOf(str);
        return valueOf3.length() == 0 ? new String(valueOf) : valueOf.concat(valueOf3);
    }

    /* renamed from: a */
    public final List<String> mo44138a() {
        Configuration configuration = this.f1311a.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT < 24) {
            return Collections.singletonList(m752a(configuration.locale));
        }
        LocaleList locales = configuration.getLocales();
        ArrayList arrayList = new ArrayList(locales.size());
        for (int i = 0; i < locales.size(); i++) {
            arrayList.add(m752a(locales.get(i)));
        }
        return arrayList;
    }
}
