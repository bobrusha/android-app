package com.bobrusha.android.artists;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bobrusha.android.artists.recycler_view.ArtistPreviewAdapter;
import com.bobrusha.android.artists.recycler_view.DividerItemDecoration;
import com.bobrusha.android.artists.event.ArtistPreviewOnClickEvent;
import com.bobrusha.android.artists.model.ArtistInfo;
import com.squareup.otto.Subscribe;

import java.util.List;

/**
 * Main Activity for showing list of musicians.
 *
 * @author Aleksandra Bobrova
 */
public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<ArtistInfo>> {

    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Snackbar mSnackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view_artists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        mAdapter = new ArtistPreviewAdapter();
        recyclerView.setAdapter(mAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(this, R.drawable.divider));

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getSupportLoaderManager().restartLoader(Constants.ARTIST_INFO_LOADER_ID, null, MainActivity.this);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        getSupportLoaderManager().initLoader(Constants.ARTIST_INFO_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<ArtistInfo>> onCreateLoader(int id, Bundle args) {
        return new ArtistInfoLoader(this);
    }

    @Override
    public void onLoadFinished(Loader<List<ArtistInfo>> loader, List<ArtistInfo> data) {
        ((ArtistPreviewAdapter) mAdapter).setDataset(data);
        mSwipeRefreshLayout.setRefreshing(false);

        // Show snackBar if no data was loaded
        if (data == null) {
            if (mSnackbar == null) {
                mSnackbar = Snackbar.make(findViewById(R.id.refresh_layout),
                        R.string.no_data_to_display,
                        Snackbar.LENGTH_INDEFINITE);
            }
            mSnackbar.show();
        } else {
            if (mSnackbar != null) {
                mSnackbar.dismiss();
            }
        }
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

    /**
     * Method that calling then user chose musician.
     *
     * @param event – event for method invocation using Bus.
     */
    @Subscribe
    public void onArtistSelected(ArtistPreviewOnClickEvent event) {
        Intent intent = new Intent(this, ArtistDetailActivity.class);
        ArtistInfo artistInfo = event.getArtistInfo();
        intent.putExtra(Constants.EXTRA_ARTIST, artistInfo);
        startActivity(intent);
    }
}
