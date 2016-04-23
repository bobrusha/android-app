package com.bobrusha.android.artists;

import com.squareup.otto.Bus;

/**
 * Created by Aleksandra on 13.04.16.
 */
public class BusProvider {
    private static Bus mInstance = new Bus();

    private BusProvider() {
    }

    public static Bus getInstance() {
        return mInstance;
    }
}
