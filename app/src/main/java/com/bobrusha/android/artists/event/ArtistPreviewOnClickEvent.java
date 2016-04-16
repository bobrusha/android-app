package com.bobrusha.android.artists.event;

import com.bobrusha.android.artists.model.ArtistInfo;

/**
 * Created by Aleksandra on 13.04.16.
 */
public class ArtistPreviewOnClickEvent {
    private ArtistInfo mArtistInfo;

    public ArtistPreviewOnClickEvent(ArtistInfo artistInfo) {
        mArtistInfo = artistInfo;
    }

    public ArtistInfo getArtistInfo() {
        return mArtistInfo;
    }
}
