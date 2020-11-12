package id.alin_gotama.volunteer.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ServerRespon implements Parcelable {
    public String status;
    public String messages;
    public String errors;

    protected ServerRespon(Parcel in) {
        status = in.readString();
        messages = in.readString();
        errors = in.readString();
    }

    public static final Creator<ServerRespon> CREATOR = new Creator<ServerRespon>() {
        @Override
        public ServerRespon createFromParcel(Parcel in) {
            return new ServerRespon(in);
        }

        @Override
        public ServerRespon[] newArray(int size) {
            return new ServerRespon[size];
        }
    };

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
