package com.bobrusha.android.artists;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bobrusha.android.artists.adapter.AtristPreviewAdapter;
import com.bobrusha.android.artists.model.ArtistInfo;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ArtistInfo>> {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(0, null, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_artists);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new AtristPreviewAdapter();
        mRecyclerView.setAdapter(mAdapter);
    }


    @Override
    public Loader<List<ArtistInfo>> onCreateLoader(int id, Bundle args) {
        return new ArtistInfoGetter(this);
    }

    @Override
    public void onLoadFinished(Loader<List<ArtistInfo>> loader, List<ArtistInfo> data) {
        ((AtristPreviewAdapter) mAdapter).setDataset(data);
    }

    @Override
    public void onLoaderReset(Loader<List<ArtistInfo>> loader) {

    }

}
