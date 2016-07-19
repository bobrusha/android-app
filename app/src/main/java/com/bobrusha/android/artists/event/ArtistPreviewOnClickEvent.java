package com.bobrusha.android.artists.event;

import com.bobrusha.android.artists.model.ArtistInfo;

/**
 * Event for sending through Bus information about clicking on item in list.
 *
 * @author Aleksandra Bobrova
 * @see com.squareup.otto.Bus
 */
public class ArtistPreviewOnClickEvent {
    private final ArtistInfo artistInfo;

    public ArtistPreviewOnClickEvent(ArtistInfo artistInfo) {
        this.artistInfo = artistInfo;
    }

    public ArtistInfo getArtistInfo() {
        return artistInfo;
    }
}
