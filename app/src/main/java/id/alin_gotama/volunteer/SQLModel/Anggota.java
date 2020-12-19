package id.alin_gotama.volunteer.SQLModel;

import android.os.Parcel;
import android.os.Parcelable;

public class Anggota implements Parcelable {
    private int id;
    private String full_name;
    private String username;
    private String no_telp;
    private String bio;

    protected Anggota(Parcel in) {
        id = in.readInt();
        full_name = in.readString();
        username = in.readString();
        no_telp = in.readString();
        bio = in.readString();
    }

    public static final Creator<Anggota> CREATOR = new Creator<Anggota>() {
        @Override
        public Anggota createFromParcel(Parcel in) {
            return new Anggota(in);
        }

        @Override
        public Anggota[] newArray(int size) {
            return new Anggota[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(full_name);
        dest.writeString(username);
        dest.writeString(no_telp);
        dest.writeString(bio);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
