package com.bobrusha.android.artists;

/**
 * @author Aleksandra Bobrova
 */

public interface Constants {
    // Google recommends to use app's package name as a prefix for intent extras
    String PACKAGE_NAME_PREFIX = "com.bobrusha.android.artists.";
    String EXTRA_ARTIST = PACKAGE_NAME_PREFIX + "artist";

    int ARTIST_INFO_LOADER_ID = 10;
    int CACHE_LOADER_ID = 20;

}
