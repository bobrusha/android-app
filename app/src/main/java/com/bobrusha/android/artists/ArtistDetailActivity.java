package com.bobrusha.android.artists;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobrusha.android.artists.model.ArtistInfo;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ArtistDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_artist_detail);
        Intent intent = getIntent();
        ArtistInfo artistInfo = intent.getParcelableExtra(Constants.EXTRA_ARTIST);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(artistInfo.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        final ImageView imageView = (ImageView) findViewById(R.id.artist_detail_big_img);
        Picasso.with(imageView.getContext())
                .load(artistInfo.getCover().getBig())
                .into(imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                        imageView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError() {
                        //TODO
                    }
                });

        TextView genreTextView = (TextView) findViewById(R.id.artist_detail_genre_text);
        genreTextView.setText(TextUtils.join(", ", artistInfo.getGenres()));

        TextView albumsAndTracksTextView = (TextView) findViewById(R.id.artist_detail_albums_and_tracks);
        String pattern = getString(R.string.artist_detail_albums_and_tracks);
        albumsAndTracksTextView.setText(
                String.format(pattern, artistInfo.getAlbums(), artistInfo.getTracks())
        );

        TextView textOfBiographyTextView = (TextView) findViewById(R.id.artist_detail_biography_text);
        textOfBiographyTextView.setText(artistInfo.getDescription());
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
