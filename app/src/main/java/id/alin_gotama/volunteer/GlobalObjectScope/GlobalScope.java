package id.alin_gotama.volunteer.GlobalObjectScope;

import android.os.Parcel;
import android.os.Parcelable;

import id.alin_gotama.volunteer.Fragment.Profile;

public class GlobalScope implements Parcelable {
    public Profile profile;


    public GlobalScope(Parcel in) {
    }

    public static final Creator<GlobalScope> CREATOR = new Creator<GlobalScope>() {
        @Override
        public GlobalScope createFromParcel(Parcel in) {
            return new GlobalScope(in);
        }

        @Override
        public GlobalScope[] newArray(int size) {
            return new GlobalScope[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
