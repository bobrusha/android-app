package com.bobrusha.android.artists.model;

import android.os.Parcelable;
import android.support.annotation.Nullable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.google.auto.value.AutoValue;

import java.util.List;

/**
 * Model
 *
 * @author Aleksandra Bobrova
 */
@AutoValue
@JsonDeserialize(builder = AutoValue_ArtistInfo.Builder.class)
public abstract class ArtistInfo implements Parcelable {

    public static Builder builder() {
        return new AutoValue_ArtistInfo.Builder();
    }

    public static ArtistInfo create(@JsonProperty("id") long id,
                                    @JsonProperty("name") String name,
                                    @JsonProperty("genres") List<String> genres,
                                    @JsonProperty("tracks") long tracks,
                                    @JsonProperty("albums") long albums,
                                    @JsonProperty("link") @Nullable String link,
                                    @JsonProperty("description") String description,
                                    @JsonProperty("cover") Cover cover) {
        return builder().id(id)
                .name(name)
                .genres(genres)
                .tracks(tracks)
                .albums(albums)
                .link(link)
                .description(description)
                .cover(cover)
                .build();

    }

    // Properties
    @JsonProperty("id")
    public abstract long id();

    @JsonProperty("name")
    public abstract String name();

    @JsonProperty("genres")
    public abstract List<String> genres();

    @JsonProperty("tracks")
    public abstract long tracks();

    @JsonProperty("albums")
    public abstract long albums();

    @JsonProperty("link")
    @Nullable
    public abstract String link();

    @JsonProperty("description")
    public abstract String description();

    @JsonProperty("cover")
    public abstract Cover cover();


    @AutoValue.Builder
    @JsonPOJOBuilder(withPrefix = "")
    public static abstract class Builder {
        @JsonProperty("id")
        public abstract Builder id(long id);

        @JsonProperty("name")
        public abstract Builder name(String name);

        @JsonProperty("genres")
        public abstract Builder genres(List<String> genres);

        @JsonProperty("tracks")
        public abstract Builder tracks(long tracks);

        @JsonProperty("albums")
        public abstract Builder albums(long albums);

        @JsonProperty("link")
        public abstract Builder link(String link);

        @JsonProperty("description")
        public abstract Builder description(String description);

        @JsonProperty("cover")
        public abstract Builder cover(Cover cover);

        @JsonCreator
        public abstract ArtistInfo build();
    }
}
