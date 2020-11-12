package id.alin_gotama.volunteer.SQLModel;

import android.os.Parcel;
import android.os.Parcelable;

public class Event implements Parcelable {
    private int id;
    private int user_id;
    private String nama;
    private String deskripsi;
    private String tanggal_mulai;
    private String tanggal_selesai;
    private int maximal_member;
    private String status;
    private String image;
    private int member;
    private String creator;
    private String updated_at;
    private String created_at;

    public Event() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
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

    public int getMaximal_member() {
        return maximal_member;
    }

    public void setMaximal_member(int maximal_member) {
        this.maximal_member = maximal_member;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getMember() {
        return member;
    }

    public void setMember(int member) {
        this.member = member;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    protected Event(Parcel in) {
        id = in.readInt();
        user_id = in.readInt();
        nama = in.readString();
        deskripsi = in.readString();
        tanggal_mulai = in.readString();
        tanggal_selesai = in.readString();
        maximal_member = in.readInt();
        status = in.readString();
        image = in.readString();
        member = in.readInt();
        creator = in.readString();
        updated_at = in.readString();
        created_at = in.readString();
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(user_id);
        dest.writeString(nama);
        dest.writeString(deskripsi);
        dest.writeString(tanggal_mulai);
        dest.writeString(tanggal_selesai);
        dest.writeInt(maximal_member);
        dest.writeString(status);
        dest.writeString(image);
        dest.writeInt(member);
        dest.writeString(creator);
        dest.writeString(updated_at);
        dest.writeString(created_at);
    }
}
