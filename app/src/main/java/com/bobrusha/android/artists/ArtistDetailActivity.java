package com.bobrusha.android.artists;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

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

        ImageView imageView = (ImageView) findViewById(R.id.artist_detail_big_img);
        Picasso.with(imageView.getContext())
                .load(intent.getStringExtra(Constants.EXTRA_BIG_IMG))
                .into(imageView);

        TextView genreTextView = (TextView) findViewById(R.id.artist_detail_genre_text);
        genreTextView.setText(intent.getStringExtra(Constants.EXTRA_GENRE));
    }
}
