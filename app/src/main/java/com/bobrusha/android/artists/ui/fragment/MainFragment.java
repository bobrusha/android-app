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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobrusha.android.artists.ArtistInfoLoader;
import com.bobrusha.android.artists.Constants;
import com.bobrusha.android.artists.R;
import com.bobrusha.android.artists.model.ArtistInfo;
import com.bobrusha.android.artists.recycler_view.ArtistPreviewAdapter;
import com.bobrusha.android.artists.recycler_view.DividerItemDecoration;

import java.util.List;

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<ArtistInfo>> {
    private RecyclerView.Adapter mAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private Snackbar mSnackbar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = (RecyclerView) root.findViewById(R.id.recycler_view_artists);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mAdapter = new ArtistPreviewAdapter();
        recyclerView.setAdapter(mAdapter);

        recyclerView.addItemDecoration(new DividerItemDecoration(getActivity(), R.drawable.divider));


        mSwipeRefreshLayout = (SwipeRefreshLayout) root.findViewById(R.id.refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getActivity().getSupportLoaderManager()
                        .restartLoader(Constants.ARTIST_INFO_LOADER_ID, null, MainFragment.this);
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });

        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(Constants.ARTIST_INFO_LOADER_ID, null, this);
    }

    @Override
    public Loader<List<ArtistInfo>> onCreateLoader(int id, Bundle args) {
        return new ArtistInfoLoader(getActivity());
    }

    @Override
    public void onLoadFinished(Loader<List<ArtistInfo>> loader, List<ArtistInfo> data) {
        ((ArtistPreviewAdapter) mAdapter).setDataset(data);
         mSwipeRefreshLayout.setRefreshing(false);

        // Show snackBar if no data was loaded

        if (data == null) {
            if (mSnackbar == null) {
                mSnackbar = Snackbar.make(getActivity().findViewById(R.id.refresh_layout),
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
}
