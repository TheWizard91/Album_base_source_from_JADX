package com.google.android.play.core.splitinstall;

import android.util.Log;
import com.google.android.gms.measurement.api.AppMeasurementSdk;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* renamed from: com.google.android.play.core.splitinstall.bb */
final class C3126bb {

    /* renamed from: a */
    private final XmlPullParser f1452a;

    /* renamed from: b */
    private final C3129e f1453b = new C3129e();

    C3126bb(XmlPullParser xmlPullParser) {
        this.f1452a = xmlPullParser;
    }

    /* renamed from: a */
    private final String m1004a(String str) {
        for (int i = 0; i < this.f1452a.getAttributeCount(); i++) {
            if (this.f1452a.getAttributeName(i).equals(str)) {
                return this.f1452a.getAttributeValue(i);
            }
        }
        return null;
    }

    /* renamed from: b */
    private final void m1005b() throws IOException, XmlPullParserException {
        int i = 1;
        while (i != 0) {
            int next = this.f1452a.next();
            if (next == 2) {
                i++;
            } else if (next == 3) {
                i--;
            }
        }
    }

    /* access modifiers changed from: package-private */
    /* renamed from: a */
    public final C3130f mo44291a() {
        String a;
        while (this.f1452a.next() != 1) {
            try {
                if (this.f1452a.getEventType() == 2) {
                    if (this.f1452a.getName().equals("splits")) {
                        while (this.f1452a.next() != 3) {
                            if (this.f1452a.getEventType() == 2) {
                                if (!this.f1452a.getName().equals("module") || (a = m1004a(AppMeasurementSdk.ConditionalUserProperty.NAME)) == null) {
                                    m1005b();
                                } else {
                                    while (this.f1452a.next() != 3) {
                                        if (this.f1452a.getEventType() == 2) {
                                            if (this.f1452a.getName().equals("language")) {
                                                while (this.f1452a.next() != 3) {
                                                    if (this.f1452a.getEventType() == 2) {
                                                        if (this.f1452a.getName().equals("entry")) {
                                                            String a2 = m1004a("key");
                                                            String a3 = m1004a("split");
                                                            m1005b();
                                                            if (!(a2 == null || a3 == null)) {
                                                                this.f1453b.mo44294a(a, a2, a3);
                                                            }
                                                        } else {
                                                            m1005b();
                                                        }
                                                    }
                                                }
                                            } else {
                                                m1005b();
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        m1005b();
                    }
                }
            } catch (IOException | IllegalStateException | XmlPullParserException e) {
                Log.e("SplitInstall", "Error while parsing splits.xml", e);
                return null;
            }
        }
        return this.f1453b.mo44293a();
    }
}
