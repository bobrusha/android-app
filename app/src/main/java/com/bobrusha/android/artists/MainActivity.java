package com.bobrusha.android.artists;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bobrusha.android.artists.adapter.AtristPreviewAdapter;
import com.bobrusha.android.artists.adapter.DividerItemDecoration;
import com.bobrusha.android.artists.event.ArtistPreviewOnClickEvent;
import com.bobrusha.android.artists.model.ArtistInfo;
import com.squareup.otto.Subscribe;

import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ArtistInfo>> {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportLoaderManager().initLoader(Constants.ARTIST_INFO_LOADER_ID, null, this);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_artists);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new AtristPreviewAdapter();
        mRecyclerView.setAdapter(mAdapter);

        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));
    }


    @Override
    public Loader<List<ArtistInfo>> onCreateLoader(int id, Bundle args) {
        return new ArtistInfoLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<ArtistInfo>> loader, List<ArtistInfo> data) {
        ((AtristPreviewAdapter) mAdapter).setDataset(data);
    }

    @Override
    public void onLoaderReset(Loader<List<ArtistInfo>> loader) {

    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    @Subscribe
    public void onArtistSelected(ArtistPreviewOnClickEvent event) {
        Intent intent = new Intent(this, ArtistDetailActivity.class);
        ArtistInfo artistInfo = event.getArtistInfo();
        intent.putExtra(Constants.EXTRA_ARTIST, artistInfo);
        startActivity(intent);
    }
}
