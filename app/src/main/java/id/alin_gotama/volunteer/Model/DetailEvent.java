package id.alin_gotama.volunteer.Model;

import android.os.Parcel;
import android.os.Parcelable;

public class DetailEvent implements Parcelable {
    private String id;
    private String event_id;
    private String user_id;
    private String tanggal_masuk;
    private String tanggal_keluar;
    private String status_member;
    private String created_at;
    private String updated_at;
    private String deleted_at;
    private String event_name;
    private String event_image;
    private String event_deskripsi;


    protected DetailEvent(Parcel in) {
        id = in.readString();
        event_id = in.readString();
        user_id = in.readString();
        tanggal_masuk = in.readString();
        tanggal_keluar = in.readString();
        status_member = in.readString();
        created_at = in.readString();
        updated_at = in.readString();
        deleted_at = in.readString();
        event_name = in.readString();
        event_image = in.readString();
        event_deskripsi = in.readString();
    }

    public static final Creator<DetailEvent> CREATOR = new Creator<DetailEvent>() {
        @Override
        public DetailEvent createFromParcel(Parcel in) {
            return new DetailEvent(in);
        }

        @Override
        public DetailEvent[] newArray(int size) {
            return new DetailEvent[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(event_id);
        dest.writeString(user_id);
        dest.writeString(tanggal_masuk);
        dest.writeString(tanggal_keluar);
        dest.writeString(status_member);
        dest.writeString(created_at);
        dest.writeString(updated_at);
        dest.writeString(deleted_at);
        dest.writeString(event_name);
        dest.writeString(event_image);
        dest.writeString(event_deskripsi);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEvent_id() {
        return event_id;
    }

    public void setEvent_id(String event_id) {
        this.event_id = event_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTanggal_masuk() {
        return tanggal_masuk;
    }

    public void setTanggal_masuk(String tanggal_masuk) {
        this.tanggal_masuk = tanggal_masuk;
    }

    public String getTanggal_keluar() {
        return tanggal_keluar;
    }

    public void setTanggal_keluar(String tanggal_keluar) {
        this.tanggal_keluar = tanggal_keluar;
    }

    public String getStatus_member() {
        return status_member;
    }

    public void setStatus_member(String status_member) {
        this.status_member = status_member;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getEvent_image() {
        return event_image;
    }

    public void setEvent_image(String event_image) {
        this.event_image = event_image;
    }

    public String getEvent_deskripsi() {
        return event_deskripsi;
    }

    public void setEvent_deskripsi(String event_deskripsi) {
        this.event_deskripsi = event_deskripsi;
    }
}
