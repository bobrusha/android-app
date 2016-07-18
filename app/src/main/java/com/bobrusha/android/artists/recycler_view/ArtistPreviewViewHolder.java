package com.bobrusha.android.artists.recycler_view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobrusha.android.artists.BusProvider;
import com.bobrusha.android.artists.R;
import com.bobrusha.android.artists.event.ArtistPreviewOnClickEvent;
import com.bobrusha.android.artists.model.ArtistInfo;
import com.squareup.picasso.Picasso;

/**
 * @author Aleksandra Bobrova
 */
public class ArtistPreviewViewHolder extends RecyclerView.ViewHolder {
    private final Context context;
    private final TextView mArtistNameView;
    private final TextView mGenreTextView;
    private final TextView mAlbumsTextView;
    private final ImageView mImageView;

    public ArtistPreviewViewHolder(View itemView) {
        super(itemView);
        context = itemView.getContext();
        mArtistNameView = (TextView) itemView.findViewById(R.id.preview_artist_name);
        mGenreTextView = (TextView) itemView.findViewById(R.id.preview_artist_genre);
        mAlbumsTextView = (TextView) itemView.findViewById(R.id.preview_artist_albums);
        mImageView = (ImageView) itemView.findViewById(R.id.preview_artist_img);
    }

    public void bind(final ArtistInfo artistInfo) {
        mArtistNameView.setText(artistInfo.getName());

        String genres = TextUtils.join(", ", artistInfo.getGenres());
        mGenreTextView.setText(genres);

        String pattern = context.getString(R.string.amount_of_albums_and_tracks);
        mAlbumsTextView.setText(String.format(pattern, artistInfo.getAlbums(), artistInfo.getTracks()));

        Picasso.with(context)
                .load(artistInfo.getCover().getSmall())
                .into(mImageView);

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BusProvider.getInstance().post(new ArtistPreviewOnClickEvent(artistInfo));
            }
        });
    }
}
