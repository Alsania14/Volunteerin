package id.alin_gotama.volunteer.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ServerDefaultRespon implements Parcelable {
    private String status;
    private String messages;
    private String errors;

    public ServerDefaultRespon() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessages() {
        return messages;
    }

    public void setMessages(String messages) {
        this.messages = messages;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    protected ServerDefaultRespon(Parcel in) {
        status = in.readString();
        messages = in.readString();
        errors = in.readString();
    }

    public static final Creator<ServerDefaultRespon> CREATOR = new Creator<ServerDefaultRespon>() {
        @Override
        public ServerDefaultRespon createFromParcel(Parcel in) {
            return new ServerDefaultRespon(in);
        }

        @Override
        public ServerDefaultRespon[] newArray(int size) {
            return new ServerDefaultRespon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(messages);
        dest.writeString(errors);
    }
}
