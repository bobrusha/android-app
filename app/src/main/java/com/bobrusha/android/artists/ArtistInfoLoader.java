package com.bobrusha.android.artists;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.bobrusha.android.artists.model.ArtistInfo;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Class for fetching and parsing data about musicians.
 *
 * @author Aleksandra Bobrova
 */
public class ArtistInfoLoader extends AsyncTaskLoader<List<ArtistInfo>> {
    private static final String URL = "http://cache-default04f.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";
    private OkHttpClient client = new OkHttpClient();
    private Gson mGson = new Gson();

    private TypeToken<List<ArtistInfo>> artists = new TypeToken<List<ArtistInfo>>() {
    };

    public ArtistInfoLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    /**
     * Method for fetching and parsing data about musicians from server.
     *
     * @return list of ArtistInfo instances if data was fetched and parsed successfully, null in other case.
     * @see ArtistInfo
     */
    @Override
    public List<ArtistInfo> loadInBackground() {
        Request request = new Request.Builder()
                .url(URL)
                .build();

        Response response;
        try {
            response = client.newCall(request).execute();
            ResponseBody responseBody = response.body();
            List<ArtistInfo> artistInfoList = mGson.fromJson(responseBody.charStream(), artists.getType());
            responseBody.close();
            return artistInfoList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
