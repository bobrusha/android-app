package com.bobrusha.android.artists;

import android.app.Application;

import com.bobrusha.android.artists.db.MyDbHelper;

/**
 * Created by Aleksandra on 20/07/16.
 */
public class MyApplication extends Application {
    private static MyDbHelper dbHelper;

    @Override
    public void onCreate() {
        super.onCreate();

        dbHelper = new MyDbHelper(this);
    }

    public static MyDbHelper getDbHelper() {
        return dbHelper;
    }
}
