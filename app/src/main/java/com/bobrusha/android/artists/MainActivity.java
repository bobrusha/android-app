package com.bobrusha.android.artists;

import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.bobrusha.android.artists.model.ArtistInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ArtistInfo>>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<List<ArtistInfo>> onCreateLoader(int id, Bundle args) {
        return new ArtistInfoGetter(this);
    }

    @Override
    public void onLoadFinished(Loader<List<ArtistInfo>> loader, List<ArtistInfo> data) {

    }

    @Override
    public void onLoaderReset(Loader<List<ArtistInfo>> loader) {

    }

}
