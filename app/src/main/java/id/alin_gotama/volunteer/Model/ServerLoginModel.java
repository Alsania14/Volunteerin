package id.alin_gotama.volunteer.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ServerLoginModel implements Parcelable {
    private String status;
    private String user_id;
    private String username;
    private String userfullname;
    private String message;
    private String errors;

    protected ServerLoginModel(Parcel in) {
        status = in.readString();
        user_id = in.readString();
        username = in.readString();
        userfullname = in.readString();
        message = in.readString();
        errors = in.readString();
    }

    public static final Creator<ServerLoginModel> CREATOR = new Creator<ServerLoginModel>() {
        @Override
        public ServerLoginModel createFromParcel(Parcel in) {
            return new ServerLoginModel(in);
        }

        @Override
        public ServerLoginModel[] newArray(int size) {
            return new ServerLoginModel[size];
        }
    };

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getErrors() {
        return errors;
    }

    public void setErrors(String errors) {
        this.errors = errors;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserfullname() {
        return userfullname;
    }

    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(user_id);
        dest.writeString(username);
        dest.writeString(userfullname);
        dest.writeString(message);
        dest.writeString(errors);
    }
}
