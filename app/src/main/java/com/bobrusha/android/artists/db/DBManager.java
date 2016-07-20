package com.bobrusha.android.artists.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.bobrusha.android.artists.model.ArtistInfo;
import com.bobrusha.android.artists.model.Cover;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Aleksandra on 20/07/16.
 */
public class DBManager {


    public static void putArtist(SQLiteDatabase db, ArtistInfo artist) {
        ContentValues values = new ContentValues();
        values.put(Contract.ArtistEntry.COLUMN_NAME_ARTIST_ID, artist.getId());
        values.put(Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME, artist.getName());
        values.put(Contract.ArtistEntry.COLUMN_NAME_GENRES, TextUtils.join(", ", artist.getGenres()));
        values.put(Contract.ArtistEntry.COLUMN_NAME_TRACKS, artist.getTracks());
        values.put(Contract.ArtistEntry.COLUMN_NAME_ALBUMS, artist.getAlbums());
        values.put(Contract.ArtistEntry.COLUMN_NAME_LINK, artist.getLink());
        values.put(Contract.ArtistEntry.COLUMN_NAME_DESCRIPTION, artist.getDescription());
        values.put(Contract.ArtistEntry.COLUMN_NAME_COVER_BIG, artist.getCover().getBig());
        values.put(Contract.ArtistEntry.COLUMN_NAME_COVER_SMALL, artist.getCover().getSmall());

        String selection = Contract.ArtistEntry.COLUMN_NAME_ARTIST_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(artist.getId())};

        int count = db.update(
                Contract.ArtistEntry.TABLE_NAME,
                values,
                selection,
                selectionArgs);

        if (count == 0) {
            db.insert(Contract.ArtistEntry.TABLE_NAME, null, values);
        }
    }



    public static void putArtists(SQLiteDatabase db, List<ArtistInfo> artists) {
        for (ArtistInfo artist : artists) {
            putArtist(db, artist);
        }

        Cursor c = db.query(Contract.ArtistEntry.TABLE_NAME, null, null, null, null, null, null);
    }


    public static ArtistInfo getArtistFromCursor(Cursor c) {
        ArtistInfo artistInfo = new ArtistInfo();
        artistInfo.setId(c.getLong(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_ARTIST_ID)));
        artistInfo.setName(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME)));
        artistInfo.setAlbums(c.getLong(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_ALBUMS)));
        artistInfo.setDescription(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_DESCRIPTION)));
        String[] genres = c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_GENRES)).split(", ", -1);
        artistInfo.setGenres(Arrays.asList(genres));
        artistInfo.setTracks(c.getLong(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_TRACKS)));
        artistInfo.setLink(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_LINK)));

        Cover cover = new Cover();
        cover.setSmall(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_COVER_SMALL)));
        cover.setBig(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_COVER_BIG)));
        artistInfo.setCover(cover);

        return artistInfo;
    }

}
