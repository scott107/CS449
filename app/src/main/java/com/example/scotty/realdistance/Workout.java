package com.example.scotty.realdistance;

/**
 * Created by scotty on 11/6/2016.
 */
import java.util.ArrayList;
import java.util.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Workout {
    private Context context;

    public Workout(Context context) {
        this.context = context;
    }

    public long insert(Integer workout) {
        // insert record into DB
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        Calendar c = Calendar.getInstance();
        String date = Integer.toString(c.DAY_OF_MONTH + '/' + c.MONTH + '/' + c.YEAR);

        values.put(DatabaseHelper.FIELD_DATE, date);
        values.put(DatabaseHelper.FIELD_DISTANCE, workout);
        long row_id = db.insertOrThrow(DatabaseHelper.WORKOUT_TABLE, null, values);

        // Return value from INSERT statement (row_id above)
        //   is _id value of last record inserted.
        // Each entry in most SQLite tables (except for WITHOUT ROWID
        // tables) has a unique 64-bit signed integer key called the
        // "rowid". ... If the table has a column of type INTEGER
        // PRIMARY KEY then that column is another alias for the rowid.

        dbHelper.close();
        return row_id; // return the primary key created by the DB
    }

    public Integer LastWorkout(int id) { // id is primary key for record
        return id;
    }
}