package com.bobrusha.android.artists.adapter;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobrusha.android.artists.R;
import com.bobrusha.android.artists.model.ArtistInfo;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;

/**
 * Created by Aleksandra on 01.04.16.
 */
public class ArtistPreviewViewHolder extends RecyclerView.ViewHolder {
    private TextView mArtistNameView;
    private TextView mGenreTextView;
    private ImageView mImageView;

    public ArtistPreviewViewHolder(View itemView) {
        super(itemView);
        mArtistNameView = (TextView) itemView.findViewById(R.id.preview_artist_name);
        mGenreTextView = (TextView) itemView.findViewById(R.id.preview_artist_genre);
        mImageView = (ImageView) itemView.findViewById(R.id.preview_artist_img);

    }

    public void bind(ArtistInfo artistInfo) {
        mArtistNameView.setText(artistInfo.getName());
        String genres = TextUtils.join(", ", artistInfo.getGenres());
        mGenreTextView.setText(genres);
        Picasso.with(mImageView.getContext())
                .load(artistInfo.getCover().getSmall())
                .into(mImageView);
    }
}
