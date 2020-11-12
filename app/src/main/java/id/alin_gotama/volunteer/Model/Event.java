package id.alin_gotama.volunteer.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private String id;
    private String user_id;
    private String nama;
    private String deskripsi;
    private String tanggal_mulai;
    private String tanggal_selesai;
    private String maximal_member;
    private String status;

    protected Event(Parcel in) {
        id = in.readString();
        user_id = in.readString();
        nama = in.readString();
        deskripsi = in.readString();
        tanggal_mulai = in.readString();
        tanggal_selesai = in.readString();
        maximal_member = in.readString();
        status = in.readString();
    }

    public static final Creator<Event> CREATOR = new Creator<Event>() {
        @Override
        public Event createFromParcel(Parcel in) {
            return new Event(in);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getTanggal_mulai() {
        return tanggal_mulai;
    }

    public void setTanggal_mulai(String tanggal_mulai) {
        this.tanggal_mulai = tanggal_mulai;
    }

    public String getTanggal_selesai() {
        return tanggal_selesai;
    }

    public void setTanggal_selesai(String tanggal_selesai) {
        this.tanggal_selesai = tanggal_selesai;
    }

    public String getMaximal_member() {
        return maximal_member;
    }

    public void setMaximal_member(String maximal_member) {
        this.maximal_member = maximal_member;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(user_id);
        dest.writeString(nama);
        dest.writeString(deskripsi);
        dest.writeString(tanggal_mulai);
        dest.writeString(tanggal_selesai);
        dest.writeString(maximal_member);
        dest.writeString(status);
    }
}
