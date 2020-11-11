package com.google.android.gms.common;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.measurement.api.AppMeasurementSdk;

/* compiled from: com.google.android.gms:play-services-basement@@17.2.1 */
public class Feature extends AbstractSafeParcelable {
    public static final Parcelable.Creator<Feature> CREATOR = new zzb();
    private final String name;
    @Deprecated
    private final int zzw;
    private final long zzx;

    public Feature(String str, long j) {
        this.name = str;
        this.zzx = j;
        this.zzw = -1;
    }

    public Feature(String str, int i, long j) {
        this.name = str;
        this.zzw = i;
        this.zzx = j;
    }

    public String getName() {
        return this.name;
    }

    public long getVersion() {
        long j = this.zzx;
        return j == -1 ? (long) this.zzw : j;
    }

    public void writeToParcel(Parcel parcel, int i) {
        int beginObjectHeader = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeString(parcel, 1, getName(), false);
        SafeParcelWriter.writeInt(parcel, 2, this.zzw);
        SafeParcelWriter.writeLong(parcel, 3, getVersion());
        SafeParcelWriter.finishObjectHeader(parcel, beginObjectHeader);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof Feature)) {
            return false;
        }
        Feature feature = (Feature) obj;
        if (((getName() == null || !getName().equals(feature.getName())) && (getName() != null || feature.getName() != null)) || getVersion() != feature.getVersion()) {
            return false;
        }
        return true;
    }

    public int hashCode() {
        return Objects.hashCode(getName(), Long.valueOf(getVersion()));
    }

    public String toString() {
        return Objects.toStringHelper(this).add(AppMeasurementSdk.ConditionalUserProperty.NAME, getName()).add("version", Long.valueOf(getVersion())).toString();
    }
}
