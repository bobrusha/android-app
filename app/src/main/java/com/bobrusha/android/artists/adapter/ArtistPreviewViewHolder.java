package com.bobrusha.android.artists.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobrusha.android.artists.R;
import com.bobrusha.android.artists.model.ArtistInfo;

/**
 * Created by Aleksandra on 01.04.16.
 */
public class ArtistPreviewViewHolder extends RecyclerView.ViewHolder {
    private TextView mArtistNameView;
    private ImageView mImageView;

    public ArtistPreviewViewHolder(View itemView) {
        super(itemView);
        mArtistNameView = (TextView) itemView.findViewById(R.id.preview_artist_name);
    }

    public void bind(ArtistInfo artistInfo) {
        mArtistNameView.setText(artistInfo.getName());
    }
}
