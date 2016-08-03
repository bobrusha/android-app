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
        values.put(Contract.ArtistEntry.COLUMN_NAME_ARTIST_ID, artist.id());
        values.put(Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME, artist.name());
        values.put(Contract.ArtistEntry.COLUMN_NAME_GENRES, TextUtils.join(", ", artist.genres()));
        values.put(Contract.ArtistEntry.COLUMN_NAME_TRACKS, artist.tracks());
        values.put(Contract.ArtistEntry.COLUMN_NAME_ALBUMS, artist.albums());
        values.put(Contract.ArtistEntry.COLUMN_NAME_LINK, artist.link());
        values.put(Contract.ArtistEntry.COLUMN_NAME_DESCRIPTION, artist.description());
        values.put(Contract.ArtistEntry.COLUMN_NAME_COVER_BIG, artist.cover().getBig());
        values.put(Contract.ArtistEntry.COLUMN_NAME_COVER_SMALL, artist.cover().getSmall());

        String selection = Contract.ArtistEntry.COLUMN_NAME_ARTIST_ID + " LIKE ?";
        String[] selectionArgs = {String.valueOf(artist.id())};

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
        String[] genres = c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_GENRES)).split(", ", -1);

        Cover cover = new Cover();
        cover.setSmall(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_COVER_SMALL)));
        cover.setBig(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_COVER_BIG)));

        ArtistInfo artistInfo = ArtistInfo.builder()
                .id(c.getLong(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_ARTIST_ID)))
                .name(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_ARTIST_NAME)))
                .genres(Arrays.asList(genres))
                .tracks(c.getLong(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_TRACKS)))
                .albums(c.getLong(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_ALBUMS)))
                .link(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_LINK)))
                .description(c.getString(c.getColumnIndex(Contract.ArtistEntry.COLUMN_NAME_DESCRIPTION)))
                .cover(cover)
                .build();

        return artistInfo;
    }

}
