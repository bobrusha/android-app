package com.bobrusha.android.artists;

import com.squareup.otto.Bus;

/**
 * Class for providing INSTANCE of Bus.
 *
 * @author Aleksandra Bobrova
 * @see Bus
 */
public class BusProvider {
    private static final Bus INSTANCE = new Bus();

    private BusProvider() {
    }

    public static Bus getInstance() {
        return INSTANCE;
    }
}
