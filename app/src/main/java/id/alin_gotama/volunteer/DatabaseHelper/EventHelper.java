package id.alin_gotama.volunteer.DatabaseHelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

import id.alin_gotama.volunteer.SQLModel.Event;

public class EventHelper {
    private Context context;
    private DatabaseHelper databaseHelper;
    private SQLiteDatabase database;

    public EventHelper(Context context) {
        this.context = context;
    }

    public EventHelper open(){
        databaseHelper = new DatabaseHelper(this.context);
        database = databaseHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        databaseHelper.close();
    }

    public ArrayList<Event> query(){
        ArrayList<Event> events = new ArrayList<Event>();
        Cursor cursor = database.query(DatabaseContract.TABLE_EVENT,null,null,null,null,null,DatabaseContract.EventColumns._ID + " DESC",null);
        cursor.moveToFirst();
        if(cursor.getCount() > 0){
            do {
                Event event = new Event();
                event.setId(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EventColumns._ID)));
                event.setUser_id(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EventColumns.USER_ID)));
                event.setNama(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventColumns.NAMA)));
                event.setDeskripsi(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventColumns.DESKRIPSI)));
                event.setTanggal_mulai(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventColumns.TANGGAL_MULAI)));
                event.setTanggal_selesai(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventColumns.TANGGAL_SELESAI)));
                event.setMaximal_member(cursor.getInt(cursor.getColumnIndexOrThrow(DatabaseContract.EventColumns.MAXIMAL_MEMBER)));
                event.setUpdated_at(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventColumns.UPDATED_AT)));
                event.setCreated_at(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseContract.EventColumns.CREATED_AT)));
                events.add(event);
                cursor.moveToNext();
            }while(!cursor.isAfterLast());
            cursor.close();
        }

        return events;
    }

    public void truncate(){
        database.execSQL(" delete from " + DatabaseContract.TABLE_EVENT);
    }

    public Long insert(Event event){
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.EventColumns._ID,event.getId());
        contentValues.put(DatabaseContract.EventColumns.USER_ID,event.getUser_id());
        contentValues.put(DatabaseContract.EventColumns.NAMA,event.getNama());
        contentValues.put(DatabaseContract.EventColumns.DESKRIPSI,event.getDeskripsi());
        contentValues.put(DatabaseContract.EventColumns.TANGGAL_MULAI,event.getTanggal_mulai());
        contentValues.put(DatabaseContract.EventColumns.TANGGAL_SELESAI,event.getTanggal_selesai());
        contentValues.put(DatabaseContract.EventColumns.MAXIMAL_MEMBER,event.getMaximal_member());
        contentValues.put(DatabaseContract.EventColumns.UPDATED_AT,event.getUpdated_at());
        contentValues.put(DatabaseContract.EventColumns.CREATED_AT,event.getCreated_at());

        return database.insert(DatabaseContract.TABLE_EVENT,null,contentValues);
    }
}
