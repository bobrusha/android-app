package com.bobrusha.android.artists.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import com.bobrusha.android.artists.BusProvider;
import com.bobrusha.android.artists.Constants;
import com.bobrusha.android.artists.R;
import com.bobrusha.android.artists.event.ArtistPreviewOnClickEvent;
import com.bobrusha.android.artists.ui.fragment.AboutFragment;
import com.bobrusha.android.artists.ui.fragment.ArtistDetailFragment;
import com.bobrusha.android.artists.ui.fragment.MainFragment;
import com.squareup.otto.Subscribe;


/**
 * Main Activity for managing fragments.
 *
 * @author Aleksandra Bobrova
 */
public class MainActivity extends AppCompatActivity {

    NavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container_for_fragment, new MainFragment());
            transaction.commit();
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNavigationView = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(mNavigationView);

    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        BusProvider.getInstance().unregister(this);
    }

    /**
     * Method that calling then user chose musician.
     *
     * @param event – event for method invocation using Bus.
     */


    @Subscribe
    public void onArtistSelected(ArtistPreviewOnClickEvent event) {
        Log.v(this.getClass().getName(), "In onArtistSelected");

        ArtistDetailFragment fragment = new ArtistDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_ARTIST, event.getArtistInfo());
        fragment.setArguments(args);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container_for_fragment, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        selectDrawerItem(menuItem);
                        return true;
                    }
                });
    }

    private void selectDrawerItem(MenuItem menuItem) {
        Fragment fragment = null;

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new MainFragment();
                break;
            case R.id.nav_about_program:
                fragment = new AboutFragment();
                break;
            case R.id.nav_settings:
                //TODO: add fragment
                break;
            default:
                fragment = new MainFragment();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_for_fragment, fragment).commit();
        menuItem.setChecked(true);

    }


}
