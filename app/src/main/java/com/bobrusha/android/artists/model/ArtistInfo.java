package com.bobrusha.android.artists.model;

import java.util.List;

/**
 * Created by Aleksandra on 01.04.16.
 */
public class ArtistInfo {
    private long id;
    private String name;
    private List<String> genres;
    private long tracks;
    private long albums;
    private String link;
    private String description;
    private Cover cover;

    public ArtistInfo() {
    }

    public class Cover{
        private String small;
        private String big;

        public Cover() {
        }

        public String getSmall() {
            return small;
        }

        public String getBig() {
            return big;
        }
    }


    @Override
    public String toString() {
        return "ArtistInfo{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", genres=" + genres +
                ", albums=" + albums +
                ", link='" + link + '\'' +
                ", description='" + description + '\'' +
                ", cover=" + cover +
                '}';
    }

    public String getName() {
        return name;
    }

    public List<String> getGenres() {
        return genres;
    }

    public Cover getCover() {
        return cover;
    }

    public long getAlbums() {
        return albums;
    }

    public long getTracks() {
        return tracks;
    }
}
