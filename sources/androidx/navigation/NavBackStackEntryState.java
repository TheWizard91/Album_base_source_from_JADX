package androidx.navigation;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.UUID;

final class NavBackStackEntryState implements Parcelable {
    public static final Parcelable.Creator<NavBackStackEntryState> CREATOR = new Parcelable.Creator<NavBackStackEntryState>() {
        public NavBackStackEntryState createFromParcel(Parcel in) {
            return new NavBackStackEntryState(in);
        }

        public NavBackStackEntryState[] newArray(int size) {
            return new NavBackStackEntryState[size];
        }
    };
    private final Bundle mArgs;
    private final int mDestinationId;
    private final Bundle mSavedState;
    private final UUID mUUID;

    NavBackStackEntryState(NavBackStackEntry entry) {
        this.mUUID = entry.mId;
        this.mDestinationId = entry.getDestination().getId();
        this.mArgs = entry.getArguments();
        Bundle bundle = new Bundle();
        this.mSavedState = bundle;
        entry.saveState(bundle);
    }

    NavBackStackEntryState(Parcel in) {
        this.mUUID = UUID.fromString(in.readString());
        this.mDestinationId = in.readInt();
        this.mArgs = in.readBundle(getClass().getClassLoader());
        this.mSavedState = in.readBundle(getClass().getClassLoader());
    }

    /* access modifiers changed from: package-private */
    public UUID getUUID() {
        return this.mUUID;
    }

    /* access modifiers changed from: package-private */
    public int getDestinationId() {
        return this.mDestinationId;
    }

    /* access modifiers changed from: package-private */
    public Bundle getArgs() {
        return this.mArgs;
    }

    /* access modifiers changed from: package-private */
    public Bundle getSavedState() {
        return this.mSavedState;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mUUID.toString());
        parcel.writeInt(this.mDestinationId);
        parcel.writeBundle(this.mArgs);
        parcel.writeBundle(this.mSavedState);
    }
}
