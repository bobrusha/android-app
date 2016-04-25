package com.bobrusha.android.artists;

import com.squareup.otto.Bus;

/**
 * Class for providing instance of Bus.
 *
 * @author Aleksandra Bobrova
 * @see Bus
 */
public class BusProvider {
    private static final Bus mInstance = new Bus();

    private BusProvider() {
    }

    public static Bus getInstance() {
        return mInstance;
    }
}
