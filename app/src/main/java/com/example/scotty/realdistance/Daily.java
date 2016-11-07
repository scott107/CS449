package com.example.scotty.realdistance;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import junit.framework.Assert;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by scotty on 11/6/2016.
 */

public class Daily {
    private String date;
    private Context context;
    private Calendar c;

    public Daily(Context context) {
        this.context = context;
    }

    public double insert(double Daily) {
        // insert record into DB
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        c = Calendar.getInstance();

        date = (Integer.toString(c.DAY_OF_MONTH) + '/' + Integer.toString(c.MONTH) + '/' + Integer.toString(c.YEAR));

        values.put(DatabaseHelper.FIELD_DATE, date);
        values.put(DatabaseHelper.FIELD_DISTANCE, Daily);
        long row_id = db.insertOrThrow(DatabaseHelper.DAILY_TABLE, null, values);

        // Return value from INSERT statement (row_id above)
        //   is _id value of last record inserted.
        // Each entry in most SQLite tables (except for WITHOUT ROWID
        // tables) has a unique 64-bit signed integer key called the
        // "rowid". ... If the table has a column of type INTEGER
        // PRIMARY KEY then that column is another alias for the rowid.

        dbHelper.close();
        return Daily;
    }

    //throws exception
    //      in the event that yesterday's daily total is not in the database.
    public double Yesterday() throws Exception{
        c = Calendar.getInstance();
        String YesterdaysDate = (Integer.toString(c.DAY_OF_MONTH) + '/' + Integer.toString(c.MONTH) + '/' + Integer.toString(c.YEAR));

        String dist = "";

        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] from = { DatabaseHelper.FIELD_ID,
                DatabaseHelper.FIELD_DATE,
                DatabaseHelper.FIELD_DISTANCE};
        Cursor cursor = db.query(DatabaseHelper.DAILY_TABLE,
                from,
                DatabaseHelper.FIELD_DATE + "=" + date,
                null,
                null,
                null,
                null);

        if (cursor.moveToNext()) {
            long id = cursor.getLong(0);
            dist = cursor.getString(2);

        }
        Log.v("distance_check", dist);
        if (dist == ""){
            Exception nodata = new Exception("no data from yesterday");
            throw nodata;
        }
        dbHelper.close();
        // Assert warning if yesterday's distance was negative.  log and return zero
        if (AssertSettings.PRIORITY1_ASSERTIONS) {

            Assert.assertTrue(Integer.parseInt(dist) > 0);
            return 0;
        }
        return (Double.parseDouble(dist));
    }
}
