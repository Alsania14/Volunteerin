package id.alin_gotama.volunteer.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class ServerRegisterRespon implements Parcelable {
    private String status;
    private String full_name;
    private String username;
    private String no_telp;
    private String bio;
    private String password;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNo_telp() {
        return no_telp;
    }

    public void setNo_telp(String no_telp) {
        this.no_telp = no_telp;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Creator<ServerRegisterRespon> getCREATOR() {
        return CREATOR;
    }

    protected ServerRegisterRespon(Parcel in) {
        status = in.readString();
        full_name = in.readString();
        username = in.readString();
        no_telp = in.readString();
        bio = in.readString();
        password = in.readString();
    }

    public static final Creator<ServerRegisterRespon> CREATOR = new Creator<ServerRegisterRespon>() {
        @Override
        public ServerRegisterRespon createFromParcel(Parcel in) {
            return new ServerRegisterRespon(in);
        }

        @Override
        public ServerRegisterRespon[] newArray(int size) {
            return new ServerRegisterRespon[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(full_name);
        dest.writeString(username);
        dest.writeString(no_telp);
        dest.writeString(bio);
        dest.writeString(password);
    }
}
