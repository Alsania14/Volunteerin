package id.alin_gotama.volunteer.DatabaseHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "dbvolunteerin";
    private static final int DATABASE_VERSION = 4;
    private static final String COMMA = ",";

    private static final String SQL_CREATE_TABL_NOTE=
            "create table " + DatabaseContract.TABLE_EVENT + " (" +
                DatabaseContract.EventColumns._ID + " INTEGER," +
                DatabaseContract.EventColumns.USER_ID + " INTEGER," +
                DatabaseContract.EventColumns.NAMA + " VARCHAR(100)," +
                DatabaseContract.EventColumns.DESKRIPSI + " VARCHAR(200)," +
                DatabaseContract.EventColumns.TANGGAL_MULAI + " DATE," +
                DatabaseContract.EventColumns.TANGGAL_SELESAI + " DATE," +
                DatabaseContract.EventColumns.MAXIMAL_MEMBER + " INT," +
                DatabaseContract.EventColumns.STATUS + " VARCHAR(100)," +
                DatabaseContract.EventColumns.UPDATED_AT + " DATETIME," +
                DatabaseContract.EventColumns.CREATED_AT + " DATETIME)";


    public DatabaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d("table",SQL_CREATE_TABL_NOTE);
        db.execSQL(SQL_CREATE_TABL_NOTE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_EVENT);
        onCreate(db);
    }
}
