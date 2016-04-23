package com.bobrusha.android.artists;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

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
 * Created by Aleksandra on 04.04.16.
 */
public class ArtistInfoLoader extends AsyncTaskLoader<List<ArtistInfo>> {
    public static final String URL = "http://cache-default04f.cdn.yandex.net/download.cdn.yandex.net/mobilization-2016/artists.json";
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
            for (ArtistInfo a : artistInfoList) {
                Log.v(ArtistInfo.class.getName(), a.toString());
            }
            return artistInfoList;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
