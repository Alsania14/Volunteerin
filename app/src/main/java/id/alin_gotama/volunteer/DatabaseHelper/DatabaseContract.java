package id.alin_gotama.volunteer.DatabaseHelper;

import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String TABLE_EVENT = "events";

    public static class EventColumns implements BaseColumns{
        public static final String _ID = "id";
        public static final String USER_ID = "user_id";
        public static final String NAMA = "nama";
        public static final String DESKRIPSI = "deskripsi";
        public static final String TANGGAL_MULAI = "tanggal_mulai";
        public static final String TANGGAL_SELESAI = "tanggal_selesai";
        public static final String MAXIMAL_MEMBER = "maksimal_member";
        public static final String STATUS = "status";
        public static final String UPDATED_AT = "updated_at";
        public static final String CREATED_AT = "created_at";
    }
}
