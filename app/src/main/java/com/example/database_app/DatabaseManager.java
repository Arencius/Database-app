package com.example.database_app;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseManager {
    private SQLiteDatabase database;

    public static final String TABLE_NAME = "songs";
    public static final String TABLE_ROW_TITLE = "title";
    public static final String TABLE_ROW_ARTIST = "artist";
    public static final String TABLE_ROW_ALBUM = "album";
    public static final String TABLE_ROW_DURATION = "duration";
    public static final String TABLE_ROW_VIEWS = "views";

    public static final String DATABASE_NAME = "song_database";
    public static final int DATABASE_VERSION = 1;

    private class CustomSQLiteOpenHelper extends SQLiteOpenHelper {

        public CustomSQLiteOpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String newQuery = String.format("CREATE TABLE %s " +
                    "(%s text," +
                    "%s text," +
                    "%s text," +
                    "%s text," +
                    "%s int);", "songs", TABLE_ROW_TITLE, TABLE_ROW_ARTIST, TABLE_ROW_ALBUM, TABLE_ROW_DURATION, TABLE_ROW_VIEWS);
            db.execSQL(newQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + "songs");
            onCreate(db);
        }
    }

    public DatabaseManager(Context context) {
        CustomSQLiteOpenHelper helper = new CustomSQLiteOpenHelper(context);
        database = helper.getWritableDatabase();
    }

    public void insert(String title, String artist, String album, String dur, int views) {
          ContentValues values = new ContentValues();

          values.put(TABLE_ROW_TITLE, title);
          values.put(TABLE_ROW_ARTIST, artist);
          values.put(TABLE_ROW_ALBUM, album);
          values.put(TABLE_ROW_DURATION, dur);
          values.put(TABLE_ROW_VIEWS, views);

          database.insert(TABLE_NAME, null, values);
    }

    public Cursor selectAll() {
        // returns all elements in the database
        return database.rawQuery(String.format("SELECT * FROM %s", TABLE_NAME), null);
    }

    public Cursor selectOnCondition(String column, String value) {
        // returns all results based on specified condition (e.g. artist=='ARTIST')
        return database.rawQuery(String.format("SELECT * FROM %s WHERE %s='%s'", TABLE_NAME,column, value), null);
    }
}
