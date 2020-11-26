package id.alin_gotama.volunteer.SQLModel;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RequestForJoinRespon implements Parcelable {
    private String status;
    private ArrayList<User> result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<User> getResult() {
        return result;
    }

    public void setResult(ArrayList<User> result) {
        this.result = result;
    }

    protected RequestForJoinRespon(Parcel in) {
        status = in.readString();
        result = in.createTypedArrayList(User.CREATOR);
    }

    public static final Creator<RequestForJoinRespon> CREATOR = new Creator<RequestForJoinRespon>() {
        @Override
        public RequestForJoinRespon createFromParcel(Parcel in) {
            return new RequestForJoinRespon(in);
        }

        @Override
        public RequestForJoinRespon[] newArray(int size) {
            return new RequestForJoinRespon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeTypedList(result);
    }
}
