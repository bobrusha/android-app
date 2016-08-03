package com.bobrusha.android.artists.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bobrusha.android.artists.R;
import com.bobrusha.android.artists.model.ArtistInfo;
import com.bobrusha.android.artists.ui.MainActivity;

import java.util.List;

/**
 * @author Aleksandra Bobrova
 */
public class ArtistPreviewAdapter extends RecyclerView.Adapter<ArtistPreviewViewHolder> {
    private List<ArtistInfo> dataset;

    @Override
    public ArtistPreviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.artist_preview, parent, false);
        final ArtistPreviewViewHolder h = new ArtistPreviewViewHolder(v);
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int adapterPosition = h.getAdapterPosition();
                if (adapterPosition != RecyclerView.NO_POSITION) {
                    final Context context = view.getContext();
                    if (context instanceof MainActivity) {
                        ((MainActivity) context).onArtistSelected(dataset.get(adapterPosition));
                    }
                }
            }
        });
        return h;
    }

    @Override
    public void onBindViewHolder(ArtistPreviewViewHolder holder, int position) {
        holder.bind(dataset.get(position));
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
