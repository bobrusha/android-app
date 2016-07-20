package com.bobrusha.android.artists.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobrusha.android.artists.ArtistInfoLoader;
import com.bobrusha.android.artists.CacheLoader;
import com.bobrusha.android.artists.Constants;
import com.bobrusha.android.artists.R;
import com.bobrusha.android.artists.model.ArtistInfo;
import com.bobrusha.android.artists.recycler_view.ArtistPreviewAdapter;
import com.bobrusha.android.artists.recycler_view.DividerItemDecoration;

import java.util.List;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ArtistInfo>> {
    private List<ArtistInfo> newData;

    private RecyclerView.Adapter adapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Snackbar snackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_artists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        adapter = new ArtistPreviewAdapter();
        recyclerView.setAdapter(adapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider));


        swipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getActivity().getSupportLoaderManager()
                        .restartLoader(Constants.ARTIST_INFO_LOADER_ID, null, MainFragment.this);
                getActivity().getSupportLoaderManager()
                        .restartLoader(Constants.CACHE_LOADER_ID, null, MainFragment.this);

                swipeRefreshLayout.setRefreshing(false);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(Constants.ARTIST_INFO_LOADER_ID, null, this);
        getLoaderManager().initLoader(Constants.CACHE_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<ArtistInfo>> onCreateLoader(int id, Bundle args) {
        switch (id) {
            case Constants.ARTIST_INFO_LOADER_ID:
                newData = null;
                return new ArtistInfoLoader(getActivity());
            case Constants.CACHE_LOADER_ID:
                return new CacheLoader(getActivity());
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<ArtistInfo>> loader, List<ArtistInfo> data) {
        swipeRefreshLayout.setRefreshing(false);

        if (loader.getId() == Constants.CACHE_LOADER_ID && newData == null) {
            ((ArtistPreviewAdapter) adapter).setDataset(data);
            return;
        }

        // Show snackBar if no data was loaded
        if (data == null || data.isEmpty()) {
            if (newData != null && !newData.isEmpty()) {
                ((ArtistPreviewAdapter) adapter).setDataset(newData);
            } else {
                if (snackbar == null) {
                    snackbar = Snackbar.make(swipeRefreshLayout,
                            R.string.no_data_to_display,
                            Snackbar.LENGTH_INDEFINITE);
                }
                snackbar.show();
            }
        } else {
            if (loader.getId() == Constants.ARTIST_INFO_LOADER_ID) {
                newData = data;
            }
            ((ArtistPreviewAdapter) adapter).setDataset(data);
            if (snackbar != null) {
                snackbar.dismiss();
            }
        }

    }

    @Override
    public void onLoaderReset(Loader<List<ArtistInfo>> loader) {

    }
}
