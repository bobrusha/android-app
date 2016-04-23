package com.bobrusha.android.artists;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bobrusha.android.artists.model.ArtistInfo;
import com.squareup.picasso.Picasso;

public class ArtistDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_artist_detail);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Intent intent = getIntent();
        ArtistInfo artistInfo = intent.getParcelableExtra(Constants.EXTRA_ARTIST);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(artistInfo.getName());
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        ImageView imageView = (ImageView) findViewById(R.id.artist_detail_big_img);
        DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        Picasso.with(imageView.getContext())
                .load(artistInfo.getCover().getBig())
                .resize(displaymetrics.widthPixels, displaymetrics.widthPixels * 23 / 36)
                .centerCrop()
                .into(imageView);

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
