package com.bobrusha.android.artists;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.bobrusha.android.artists.db.Contract;
import com.bobrusha.android.artists.db.DBManager;
import com.bobrusha.android.artists.model.ArtistInfo;
import com.bobrusha.android.artists.model.Cover;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Aleksandra on 20/07/16.
 */
public class CacheLoader extends AsyncTaskLoader<List<ArtistInfo>> {

    public CacheLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<ArtistInfo> loadInBackground() {
        SQLiteDatabase db = MyApplication.getDbHelper().getReadableDatabase();
        Cursor c = db.query(Contract.ArtistEntry.TABLE_NAME, null, null, null, null, null, null, null);
        List<ArtistInfo> artists = new ArrayList<>();
        for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext()) {
            artists.add(DBManager.getArtistFromCursor(c));
        }
        c.close();
        return artists;
    }

}
