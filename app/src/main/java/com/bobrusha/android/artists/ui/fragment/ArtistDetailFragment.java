package com.bobrusha.android.artists.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobrusha.android.artists.Constants;
import com.bobrusha.android.artists.R;
import com.bobrusha.android.artists.model.ArtistInfo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Aleksandra on 15/07/16.
 */
public class ArtistDetailFragment extends Fragment {
    private ArtistInfo artist;

    @BindView(R.id.artist_detail_genre_text)
    TextView genre;

    @BindView(R.id.artist_detail_albums_and_tracks)
    TextView albumsAndTracks;

    @BindView(R.id.artist_detail_biography_text)
    TextView bio;

    @BindView(R.id.artist_detail_big_img)
    ImageView imageView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        artist = args.getParcelable(Constants.EXTRA_ARTIST);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View v = inflater.inflate(R.layout.fragment_artist_detail, container, false);
        ButterKnife.bind(this, v);

        imageView = (ImageView) v.findViewById(R.id.artist_detail_big_img);
        Picasso.with(imageView.getContext())
                .load(artist.getCover().getBig())
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        //Stop progressBar and show image
                        v.findViewById(R.id.progress_bar).setVisibility(View.INVISIBLE);
                        v.findViewById(R.id.artist_detail_no_image).setVisibility(View.VISIBLE);

                    }
                });

        genre = (TextView) v.findViewById(R.id.artist_detail_genre_text);
        genre.setText(TextUtils.join(", ", artist.getGenres()));

        albumsAndTracks = (TextView) v.findViewById(R.id.artist_detail_albums_and_tracks);
        String pattern = getString(R.string.artist_detail_albums_and_tracks);
        albumsAndTracks.setText(
                String.format(pattern, artist.getAlbums(), artist.getTracks())
        );

        bio = (TextView) v.findViewById(R.id.artist_detail_biography_text);
        bio.setText(artist.getDescription());

        return v;
    }
}
