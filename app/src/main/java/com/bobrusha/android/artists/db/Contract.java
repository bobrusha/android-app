package com.bobrusha.android.artists.db;

import android.provider.BaseColumns;

/**
 * Created by Aleksandra on 20/07/16.
 */
public final class Contract {

    public static abstract class ArtistEntry implements BaseColumns {
        public static final String TABLE_NAME = "artist";
        public static final String COLUMN_NAME_ARTIST_ID = "artist_id";
        public static final String COLUMN_NAME_ARTIST_NAME = "artist_name";
        public static final String COLUMN_NAME_GENRES = "genres";
        public static final String COLUMN_NAME_TRACKS = "amount_of_tracks";
        public static final String COLUMN_NAME_ALBUMS = "amount_of_albums";
        public static final String COLUMN_NAME_LINK = "link";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_COVER_SMALL = "cover_small";
        public static final String COLUMN_NAME_COVER_BIG = "cover_big";
    }
}
