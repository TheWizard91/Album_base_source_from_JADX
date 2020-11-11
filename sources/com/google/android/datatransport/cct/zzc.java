package com.google.android.datatransport.cct;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import com.bumptech.glide.load.Key;
import com.facebook.imagepipeline.producers.HttpUrlConnectionNetworkFetcher;
import com.google.android.datatransport.Encoding;
import com.google.android.datatransport.cct.p011a.zzo;
import com.google.android.datatransport.cct.p011a.zzp;
import com.google.android.datatransport.cct.p011a.zzq;
import com.google.android.datatransport.cct.p011a.zzr;
import com.google.android.datatransport.cct.p011a.zzs;
import com.google.android.datatransport.cct.p011a.zzt;
import com.google.android.datatransport.cct.p011a.zzu;
import com.google.android.datatransport.runtime.EncodedPayload;
import com.google.android.datatransport.runtime.EventInternal;
import com.google.android.datatransport.runtime.backends.BackendRequest;
import com.google.android.datatransport.runtime.backends.BackendResponse;
import com.google.android.datatransport.runtime.backends.TransportBackend;
import com.google.android.datatransport.runtime.logging.Logging;
import com.google.android.datatransport.runtime.retries.Retries;
import com.google.android.datatransport.runtime.time.Clock;
import com.google.common.net.HttpHeaders;
import com.google.firebase.encoders.DataEncoder;
import com.google.firebase.encoders.EncodingException;
import com.google.firebase.encoders.json.JsonDataEncoderBuilder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import p019io.grpc.internal.GrpcUtil;

final class zzc implements TransportBackend {
    private final DataEncoder zza = new JsonDataEncoderBuilder().configureWith(com.google.android.datatransport.cct.p011a.zzb.zza).ignoreNullValues(true).build();
    private final ConnectivityManager zzb;
    final URL zzc;
    private final Clock zzd;
    private final Clock zze;
    private final int zzf;

    static final class zza {
        final URL zza;
        final zzo zzb;
        final String zzc;

        zza(URL url, zzo zzo, String str) {
            this.zza = url;
            this.zzb = zzo;
            this.zzc = str;
        }

        /* access modifiers changed from: package-private */
        public zza zza(URL url) {
            return new zza(url, this.zzb, this.zzc);
        }
    }

    static final class zzb {
        final int zza;
        final URL zzb;
        final long zzc;

        zzb(int i, URL url, long j) {
            this.zza = i;
            this.zzb = url;
            this.zzc = j;
        }
    }

    zzc(Context context, Clock clock, Clock clock2) {
        this.zzb = (ConnectivityManager) context.getSystemService("connectivity");
        this.zzc = zza(CCTDestination.zza);
        this.zzd = clock2;
        this.zze = clock;
        this.zzf = 40000;
    }

    private static URL zza(String str) {
        try {
            return new URL(str);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid url: " + str, e);
        }
    }

    public EventInternal decorate(EventInternal eventInternal) {
        int i;
        int i2;
        NetworkInfo activeNetworkInfo = this.zzb.getActiveNetworkInfo();
        EventInternal.Builder addMetadata = eventInternal.toBuilder().addMetadata("sdk-version", Build.VERSION.SDK_INT).addMetadata("model", Build.MODEL).addMetadata("hardware", Build.HARDWARE).addMetadata("device", Build.DEVICE).addMetadata("product", Build.PRODUCT).addMetadata("os-uild", Build.ID).addMetadata("manufacturer", Build.MANUFACTURER).addMetadata("fingerprint", Build.FINGERPRINT);
        Calendar.getInstance();
        EventInternal.Builder addMetadata2 = addMetadata.addMetadata("tz-offset", (long) (TimeZone.getDefault().getOffset(Calendar.getInstance().getTimeInMillis()) / 1000));
        if (activeNetworkInfo == null) {
            i = zzt.zzc.NONE.zza();
        } else {
            i = activeNetworkInfo.getType();
        }
        EventInternal.Builder addMetadata3 = addMetadata2.addMetadata("net-type", i);
        if (activeNetworkInfo == null) {
            i2 = zzt.zzb.UNKNOWN_MOBILE_SUBTYPE.zza();
        } else {
            i2 = activeNetworkInfo.getSubtype();
            if (i2 == -1) {
                i2 = zzt.zzb.COMBINED.zza();
            } else if (zzt.zzb.zza(i2) == null) {
                i2 = 0;
            }
        }
        return addMetadata3.addMetadata("mobile-subtype", i2).build();
    }

    public BackendResponse send(BackendRequest backendRequest) {
        zzq.zza zza2;
        HashMap hashMap = new HashMap();
        for (EventInternal next : backendRequest.getEvents()) {
            String transportName = next.getTransportName();
            if (!hashMap.containsKey(transportName)) {
                ArrayList arrayList = new ArrayList();
                arrayList.add(next);
                hashMap.put(transportName, arrayList);
            } else {
                ((List) hashMap.get(transportName)).add(next);
            }
        }
        ArrayList arrayList2 = new ArrayList();
        for (Map.Entry entry : hashMap.entrySet()) {
            EventInternal eventInternal = (EventInternal) ((List) entry.getValue()).get(0);
            zzr.zza zza3 = zzr.zza().zza(zzu.DEFAULT).zza(this.zze.getTime()).zzb(this.zzd.getTime()).zza(zzp.zza().zza(zzp.zzb.ANDROID_FIREBASE).zza(com.google.android.datatransport.cct.p011a.zza.zza().zza(Integer.valueOf(eventInternal.getInteger("sdk-version"))).zze(eventInternal.get("model")).zzc(eventInternal.get("hardware")).zza(eventInternal.get("device")).zzg(eventInternal.get("product")).zzf(eventInternal.get("os-uild")).zzd(eventInternal.get("manufacturer")).zzb(eventInternal.get("fingerprint")).zza()).zza());
            try {
                zza3.zza(Integer.parseInt((String) entry.getKey()));
            } catch (NumberFormatException e) {
                zza3.zzb((String) entry.getKey());
            }
            ArrayList arrayList3 = new ArrayList();
            for (EventInternal eventInternal2 : (List) entry.getValue()) {
                EncodedPayload encodedPayload = eventInternal2.getEncodedPayload();
                Encoding encoding = encodedPayload.getEncoding();
                if (encoding.equals(Encoding.m136of("proto"))) {
                    zza2 = zzq.zza(encodedPayload.getBytes());
                } else if (encoding.equals(Encoding.m136of("json"))) {
                    zza2 = zzq.zza(new String(encodedPayload.getBytes(), Charset.forName(Key.STRING_CHARSET_NAME)));
                } else {
                    Logging.m144w("CctTransportBackend", "Received event of unsupported encoding %s. Skipping...", encoding);
                }
                zza2.zza(eventInternal2.getEventMillis()).zzb(eventInternal2.getUptimeMillis()).zzc(eventInternal2.getLong("tz-offset")).zza(zzt.zza().zza(zzt.zzc.zza(eventInternal2.getInteger("net-type"))).zza(zzt.zzb.zza(eventInternal2.getInteger("mobile-subtype"))).zza());
                if (eventInternal2.getCode() != null) {
                    zza2.zza(eventInternal2.getCode());
                }
                arrayList3.add(zza2.zza());
            }
            zza3.zza((List<zzq>) arrayList3);
            arrayList2.add(zza3.zza());
        }
        zzo zza4 = zzo.zza(arrayList2);
        String str = null;
        URL url = this.zzc;
        if (backendRequest.getExtras() != null) {
            try {
                CCTDestination fromByteArray = CCTDestination.fromByteArray(backendRequest.getExtras());
                if (fromByteArray.getAPIKey() != null) {
                    str = fromByteArray.getAPIKey();
                }
                if (fromByteArray.getEndPoint() != null) {
                    url = zza(fromByteArray.getEndPoint());
                }
            } catch (IllegalArgumentException e2) {
                return BackendResponse.fatalError();
            }
        }
        try {
            zzb zzb2 = (zzb) Retries.retry(5, new zza(url, zza4, str), zza.zza(this), zzb.zza());
            if (zzb2.zza == 200) {
                return BackendResponse.m137ok(zzb2.zzc);
            }
            int i = zzb2.zza;
            if (i < 500) {
                if (i != 404) {
                    return BackendResponse.fatalError();
                }
            }
            return BackendResponse.transientError();
        } catch (IOException e3) {
            Logging.m142e("CctTransportBackend", "Could not make request to the backend", e3);
            return BackendResponse.transientError();
        }
    }

    /* access modifiers changed from: private */
    public zzb zza(zza zza2) throws IOException {
        GZIPOutputStream gZIPOutputStream;
        InputStream gZIPInputStream;
        Logging.m139d("CctTransportBackend", "Making request to: %s", (Object) zza2.zza);
        HttpURLConnection httpURLConnection = (HttpURLConnection) zza2.zza.openConnection();
        httpURLConnection.setConnectTimeout(HttpUrlConnectionNetworkFetcher.HTTP_DEFAULT_TIMEOUT);
        httpURLConnection.setReadTimeout(this.zzf);
        httpURLConnection.setDoOutput(true);
        httpURLConnection.setInstanceFollowRedirects(false);
        httpURLConnection.setRequestMethod(GrpcUtil.HTTP_METHOD);
        httpURLConnection.setRequestProperty(HttpHeaders.USER_AGENT, String.format("datatransport/%s android/", new Object[]{"2.2.3"}));
        httpURLConnection.setRequestProperty(HttpHeaders.CONTENT_ENCODING, "gzip");
        httpURLConnection.setRequestProperty(HttpHeaders.CONTENT_TYPE, "application/json");
        httpURLConnection.setRequestProperty(HttpHeaders.ACCEPT_ENCODING, "gzip");
        String str = zza2.zzc;
        if (str != null) {
            httpURLConnection.setRequestProperty("X-Goog-Api-Key", str);
        }
        try {
            OutputStream outputStream = httpURLConnection.getOutputStream();
            try {
                gZIPOutputStream = new GZIPOutputStream(outputStream);
                this.zza.encode(zza2.zzb, new BufferedWriter(new OutputStreamWriter(gZIPOutputStream)));
                gZIPOutputStream.close();
                if (outputStream != null) {
                    outputStream.close();
                }
                int responseCode = httpURLConnection.getResponseCode();
                Logging.m143i("CctTransportBackend", "Status Code: " + responseCode);
                Logging.m143i("CctTransportBackend", "Content-Type: " + httpURLConnection.getHeaderField(HttpHeaders.CONTENT_TYPE));
                Logging.m143i("CctTransportBackend", "Content-Encoding: " + httpURLConnection.getHeaderField(HttpHeaders.CONTENT_ENCODING));
                if (responseCode == 302 || responseCode == 301 || responseCode == 307) {
                    return new zzb(responseCode, new URL(httpURLConnection.getHeaderField(HttpHeaders.LOCATION)), 0);
                }
                if (responseCode != 200) {
                    return new zzb(responseCode, (URL) null, 0);
                }
                InputStream inputStream = httpURLConnection.getInputStream();
                try {
                    gZIPInputStream = "gzip".equals(httpURLConnection.getHeaderField(HttpHeaders.CONTENT_ENCODING)) ? new GZIPInputStream(inputStream) : inputStream;
                    zzb zzb2 = new zzb(responseCode, (URL) null, zzs.zza(new BufferedReader(new InputStreamReader(gZIPInputStream))).zza());
                    if (gZIPInputStream != null) {
                        gZIPInputStream.close();
                    }
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    return zzb2;
                } catch (Throwable th) {
                    if (inputStream != null) {
                        try {
                            inputStream.close();
                        } catch (Throwable th2) {
                        }
                    }
                    throw th;
                }
                throw th;
                throw th;
            } catch (Throwable th3) {
                if (outputStream != null) {
                    outputStream.close();
                }
                throw th3;
            }
        } catch (EncodingException | IOException e) {
            Logging.m142e("CctTransportBackend", "Couldn't encode request, returning with 400", e);
            return new zzb(400, (URL) null, 0);
        } catch (Throwable th4) {
        }
    }

    static /* synthetic */ zza zza(zza zza2, zzb zzb2) {
        URL url = zzb2.zzb;
        if (url == null) {
            return null;
        }
        Logging.m139d("CctTransportBackend", "Following redirect to: %s", (Object) url);
        return zza2.zza(zzb2.zzb);
    }
}
