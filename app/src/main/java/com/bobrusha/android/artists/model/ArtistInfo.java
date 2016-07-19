package com.bobrusha.android.artists.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Model
 *
 * @author Aleksandra Bobrova
 */
public class ArtistInfo implements Parcelable {
    public static final Creator<ArtistInfo> CREATOR = new Creator<ArtistInfo>() {
        @Override
        public ArtistInfo createFromParcel(Parcel in) {
            return new ArtistInfo(in);
        }

        @Override
        public ArtistInfo[] newArray(int size) {
            return new ArtistInfo[size];
        }
    };
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

    protected ArtistInfo(Parcel in) {
        id = in.readLong();
        name = in.readString();
        genres = in.createStringArrayList();
        tracks = in.readLong();
        albums = in.readLong();
        link = in.readString();
        description = in.readString();
        cover = new Cover();
        cover.big = in.readString();
        cover.small = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(name);
        dest.writeStringList(genres);
        dest.writeLong(tracks);
        dest.writeLong(albums);
        dest.writeString(link);
        dest.writeString(description);
        dest.writeString(cover.big);
        dest.writeString(cover.small);
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

    public String getDescription() {
        return description;
    }

    public long getTracks() {
        return tracks;
    }

    public class Cover {
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
}
