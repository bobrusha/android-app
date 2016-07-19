package com.bobrusha.android.artists.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobrusha.android.artists.R;
import com.bobrusha.android.artists.model.ArtistInfo;

import java.util.List;

/**
 * @author Aleksandra Bobrova
 */
public class ArtistPreviewAdapter extends RecyclerView.Adapter<ArtistPreviewViewHolder> {
    private List<ArtistInfo> dataset;


    @Override
    public ArtistPreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_preview, parent, false);
        return new ArtistPreviewViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ArtistPreviewViewHolder holder, int position) {
        if (position < dataset.size()) {
            holder.bind(dataset.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return dataset == null ? 0 : dataset.size();
    }

    public void setDataset(List<ArtistInfo> dataset) {
        this.dataset = dataset;
        notifyDataSetChanged();
    }
}
