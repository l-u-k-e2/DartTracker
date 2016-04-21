package com.ostfalia.bs.darttracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.ostfalia.bs.darttracker.model.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by lukas on 21.04.2016.
 */
public class UserDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "DartTracker.db";

    //Definieren der Tabelle
    public static abstract class TableUser implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_VORNAME = "vorname";
        public static final String COLUMN_NAME_NACHNAME = "nachname";
        public static final String COLUMN_NAME_ALIAS = "alias";

    }

    //Definition von typischen Statements
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + TableUser.TABLE_NAME + " (" +
                    TableUser._ID + " INTEGER PRIMARY KEY," +
                    TableUser.COLUMN_NAME_VORNAME + TEXT_TYPE + COMMA_SEP +
                    TableUser.COLUMN_NAME_NACHNAME + TEXT_TYPE + COMMA_SEP +
                    TableUser.COLUMN_NAME_ALIAS + TEXT_TYPE +
                    " )";
    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + TableUser.TABLE_NAME;

    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    public long createUser(String vorname){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TableUser.COLUMN_NAME_VORNAME,vorname);
        values.put(TableUser.COLUMN_NAME_NACHNAME,"standard");
        values.put(TableUser.COLUMN_NAME_ALIAS,"standard");
        long newRowId = db.insert(TableUser.TABLE_NAME, null, values);
        return newRowId;
    }

    public List<User> getAllUser(){
        List<User> allUser = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TableUser.TABLE_NAME;
        Log.d("getAllUser Select: ", selectQuery );
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        //looping über alle Einträge und hinzufügen zur Liste
        if(c.moveToFirst()){
            do {
                User user = new User();
                user.setId(c.getInt((c.getColumnIndex(TableUser._ID))));
                user.setVorname(c.getString(c.getColumnIndex(TableUser.COLUMN_NAME_VORNAME)));

                allUser.add(user);
            }while (c.moveToNext());
        }

        return allUser;
    }

    public User getUser(long id){
        User user = new User();
        String selectQuery = "SELECT * FROM " + TableUser.TABLE_NAME + " WHERE " + TableUser._ID + " = " + id;
        Log.d("getUser SELECT ", selectQuery);
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do {
                user.setId(c.getInt(c.getColumnIndex(TableUser._ID)));
                user.setVorname(c.getString(c.getColumnIndex(TableUser.COLUMN_NAME_VORNAME)));
            }while (c.moveToNext());
        }
        return user;
    }


}
