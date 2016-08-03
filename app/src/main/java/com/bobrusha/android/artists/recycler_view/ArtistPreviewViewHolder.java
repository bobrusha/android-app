package com.bobrusha.android.artists.recycler_view;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobrusha.android.artists.R;
import com.bobrusha.android.artists.model.ArtistInfo;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Aleksandra Bobrova
 */
public class ArtistPreviewViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.preview_artist_name)
    TextView artistNameView;

    @BindView(R.id.preview_artist_genre)
    TextView genreTextView;

    @BindView(R.id.preview_artist_albums)
    TextView albumsTextView;

    @BindView(R.id.preview_artist_img)
    ImageView coverImageView;

    public ArtistPreviewViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(final ArtistInfo artistInfo) {
        artistNameView.setText(artistInfo.name());

        if (artistInfo.genres() != null) {
            String genres = TextUtils.join(", ", artistInfo.genres());
            genreTextView.setText(genres);
        }

        String pattern = artistNameView.getContext().getString(R.string.amount_of_albums_and_tracks);
        albumsTextView.setText(String.format(pattern, artistInfo.albums(), artistInfo.tracks()));

        Picasso.with(coverImageView.getContext())
                .load(artistInfo.cover().getSmall())
                .into(coverImageView);
    }
}
