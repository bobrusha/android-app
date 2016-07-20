package com.bobrusha.android.artists.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Aleksandra on 20/07/16.
 */
public class MyDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Artists.db";

    private static final String TEXT_TYPE = " TEXT";
    private static final String INTEGER_TYPE = " INTEGER";
    private static final String SEP = ", ";
    private static final String CREATE_DB = "CREATE TABLE " + Contract.ArtistEntry.TABLE_NAME +
            " (" + Contract.ArtistEntry._ID + " INTEGER PRIMARY KEY " + SEP +
            Contract.ArtistEntry.COLUMN_NAME_ARTIST_ID + INTEGER_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME + TEXT_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_GENRES + TEXT_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_TRACKS + INTEGER_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_ALBUMS + INTEGER_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_LINK + TEXT_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_DESCRIPTION + TEXT_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_COVER_BIG + TEXT_TYPE + SEP +
            Contract.ArtistEntry.COLUMN_NAME_COVER_SMALL + TEXT_TYPE +
            " )";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + Contract.ArtistEntry.TABLE_NAME;


    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        onCreate(sqLiteDatabase);
    }


}
