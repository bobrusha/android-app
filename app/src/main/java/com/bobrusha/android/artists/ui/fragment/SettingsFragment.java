package com.bobrusha.android.artists.ui.fragment;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;


import com.bobrusha.android.artists.R;

/**
 * Created by Aleksandra on 19/07/16.
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.settings);
    }
}
