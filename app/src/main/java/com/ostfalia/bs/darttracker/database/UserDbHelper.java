package com.ostfalia.bs.darttracker.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.ostfalia.bs.darttracker.model.Shot;
import com.ostfalia.bs.darttracker.model.User;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by lukas on 21.04.2016.
 */
public class UserDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 13;
    public static final String DATABASE_NAME = "DartTracker.db";

    //Definieren der Tabelle
    public static abstract class TableUser implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_VORNAME = "vorname";
        public static final String COLUMN_NAME_NACHNAME = "nachname";
        public static final String COLUMN_NAME_ALIAS = "alias";
    }

    public static abstract class TableShot implements BaseColumns {
        public static final String TABLE_NAME = "shot";
        public static final String COLUMN_NAME_TIME = "time";
        public static final String COLUMN_NAME_POINTS = "points";
        public static final String COLUMN_NAME_PLAYER = "playerid";
    }

    //Definition von typischen Statements
    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String COMMA_SEP = ",";
    private static final String SEMMICOLON = ";";
    private static final String SQL_CREATE_USER =
            "CREATE TABLE " + TableUser.TABLE_NAME + " (" +
                    TableUser._ID + " INTEGER PRIMARY KEY," +
                    TableUser.COLUMN_NAME_VORNAME + TEXT_TYPE + COMMA_SEP +
                    TableUser.COLUMN_NAME_NACHNAME + TEXT_TYPE + COMMA_SEP +
                    TableUser.COLUMN_NAME_ALIAS + TEXT_TYPE +
                    " )";

    //SQL Queries
    private static final String SQL_CREATE_STATISTIC =
            "CREATE TABLE " + TableShot.TABLE_NAME + " (" +
                    TableShot._ID + " INTEGER PRIMARY KEY," +
                    TableShot.COLUMN_NAME_TIME + " DEFAULT CURRENT_TIMESTAMP " + COMMA_SEP +
                    TableShot.COLUMN_NAME_POINTS + INTEGER_TYPE + COMMA_SEP +
                    TableShot.COLUMN_NAME_PLAYER + INTEGER_TYPE + COMMA_SEP +
                    "FOREIGN KEY(" + TableShot.COLUMN_NAME_PLAYER + ") REFERENCES " +
                    TableUser.TABLE_NAME + "(" +TableUser._ID + ")" +
                    " )";
    private static final String SQL_DELETE_USER = "DROP TABLE IF EXISTS " + TableUser.TABLE_NAME + SEMMICOLON;
    private static final String SQL_DELETE_STATISTIC = "DROP TABLE IF EXISTS " + TableShot.TABLE_NAME + SEMMICOLON;


    public UserDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_USER);
        db.execSQL(SQL_CREATE_STATISTIC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_STATISTIC);
        db.execSQL(SQL_DELETE_USER);
        onCreate(db);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    //---------------------------------- USER Transactions ----------------------------------------

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

    //------------------------------------- Shot Transactions -------------------------------------------

    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public void saveScore(List<Integer> scoreList, long userId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        for (int i = 0; i < scoreList.size(); i++) {
            values.put(TableShot.COLUMN_NAME_TIME,getDateTime());
            values.put(TableShot.COLUMN_NAME_POINTS, scoreList.get(i));
            values.put(TableShot.COLUMN_NAME_PLAYER, userId);
            long newRowId = db.insert(TableShot.TABLE_NAME, null, values);
        }
    }

    public List<Shot> getShots(User user){
        List<Shot> shots = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TableShot.TABLE_NAME + " WHERE " + TableShot.COLUMN_NAME_PLAYER + " = " + user.getId();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if(c.moveToFirst()){
            do {
                Shot shot = new Shot();
                shot.setId(c.getInt(c.getColumnIndex(TableShot._ID)));
                shot.setDate(c.getString(c.getColumnIndex(TableShot.COLUMN_NAME_TIME)));
                shot.setPunkte(c.getInt(c.getColumnIndex(TableShot.COLUMN_NAME_POINTS)));
                shot.setPlayerId(c.getInt(c.getColumnIndex(TableShot.COLUMN_NAME_PLAYER)));
                shots.add(shot);
            }while (c.moveToNext());
        }
        return shots;
    }


}
