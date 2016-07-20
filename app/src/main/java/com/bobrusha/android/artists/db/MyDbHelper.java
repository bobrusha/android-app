package com.bobrusha.android.artists.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.bobrusha.android.artists.model.ArtistInfo;

import java.util.List;

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
        super(context, null, null, DATABASE_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_DB);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public void putArtist(SQLiteDatabase db, ArtistInfo artist) {
        ContentValues values = new ContentValues();
        values.put(Contract.ArtistEntry.COLUMN_NAME_ARTIST_ID, artist.getId());
        values.put(Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME, artist.getName());
        values.put(Contract.ArtistEntry.COLUMN_NAME_GENRES, " ");
        values.put(Contract.ArtistEntry.COLUMN_NAME_TRACKS, artist.getTracks());
        values.put(Contract.ArtistEntry.COLUMN_NAME_ALBUMS, artist.getAlbums());
        values.put(Contract.ArtistEntry.COLUMN_NAME_LINK, artist.getLink());
        values.put(Contract.ArtistEntry.COLUMN_NAME_DESCRIPTION, artist.getDescription());
        values.put(Contract.ArtistEntry.COLUMN_NAME_COVER_BIG, artist.getCover().getBig());
        values.put(Contract.ArtistEntry.COLUMN_NAME_COVER_SMALL, artist.getCover().getSmall());

        long count = db.insert(
                Contract.ArtistEntry.TABLE_NAME, null,
                values);
        Log.v(this.getClass().getName(), "Updated " + count);
    }

    public void putArtists(SQLiteDatabase db, List<ArtistInfo> artists) {
        for (ArtistInfo artist : artists) {
            putArtist(db, artist);
        }

        Cursor c = db.query(Contract.ArtistEntry.TABLE_NAME, null, null, null, null, null, null);
        Log.v(this.getClass().getName(), "In db " + c.getCount());
    }


}
