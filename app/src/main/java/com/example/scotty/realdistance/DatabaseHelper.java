package com.example.scotty.realdistance;

/**
 * Created by scotty on 11/6/2016.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "workout.db" ;
    // version can be any number. A change in version number
    // give the program the opportunity to recreate or
    // update the db.
    private static final int DATABASE_VERSION = 1;

    public static final String WORKOUT_TABLE = "workout";
    public static final String DAILY_TABLE = "daily";

    // Both tables have an _id field.
    // By convention, the first column is always called _id.
    public static final String FIELD_ID = "_id";

    // Fields defined for Tables
    public static final String FIELD_DATE = "date";
    public static final String FIELD_DISTANCE = "distance";

    // SQL Statement to create a new database tables
    private static final String DATABASE_CREATE_WORKOUT = "create table " +
            WORKOUT_TABLE + " (" + FIELD_ID + " integer primary key autoincrement, "  +
            FIELD_DISTANCE + " real, " + FIELD_DATE + " text not null);";

    private static final String DATABASE_CREATE_DAILY = "create table " +
            DAILY_TABLE + " (" + FIELD_ID + " integer primary key autoincrement, " +
            FIELD_DISTANCE + " real, " + FIELD_DATE + " text not null);";


    public DatabaseHelper(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_CREATE_WORKOUT);
        database.execSQL(DATABASE_CREATE_DAILY);
    }

    // Method is called during an upgrade of the database, e.g. if you increase
    // the database version
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion,
                          int newVersion) {
        Log.w(DatabaseHelper.class.getName(),
                "Upgrading database from version " + oldVersion + " to "
                        + newVersion);
        database.execSQL("DROP TABLE IF EXISTS " + WORKOUT_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + DAILY_TABLE);
        onCreate(database);
    }

}