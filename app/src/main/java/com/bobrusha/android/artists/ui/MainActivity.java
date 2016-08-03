package com.bobrusha.android.artists.ui;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.bobrusha.android.artists.Constants;
import com.bobrusha.android.artists.R;
import com.bobrusha.android.artists.model.ArtistInfo;
import com.bobrusha.android.artists.receiver.MusicIntentReceiver;
import com.bobrusha.android.artists.ui.fragment.AboutFragment;
import com.bobrusha.android.artists.ui.fragment.ArtistDetailFragment;
import com.bobrusha.android.artists.ui.fragment.MainFragment;
import com.bobrusha.android.artists.ui.fragment.SettingsFragment;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Main Activity for managing fragments.
 *
 * @author Aleksandra Bobrova
 */
public class MainActivity extends AppCompatActivity {
    @BindView(R.id.nvView)
    NavigationView navigationView;

    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private ActionBarDrawerToggle drawerToggle;

    private MusicIntentReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.add(R.id.container_for_fragment, new MainFragment());
            transaction.commit();
        }

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        setupDrawerContent(navigationView);

        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);

        receiver = new MusicIntentReceiver();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
    }


    /**
     * Method that calling then user chose musician.
     *
     * @param artistInfo - object with information for detailed fragment.
     */
    public void onArtistSelected(ArtistInfo artistInfo) {
        ArtistDetailFragment fragment = new ArtistDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(Constants.EXTRA_ARTIST, artistInfo);
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
        Fragment fragment;

        switch (menuItem.getItemId()) {
            case R.id.nav_home:
                fragment = new MainFragment();
                break;
            case R.id.nav_settings:
                fragment = new SettingsFragment();
                break;
            case R.id.nav_about_program:
                fragment = new AboutFragment();
                break;
            default:
                fragment = new MainFragment();
        }

        drawerLayout.closeDrawers();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.container_for_fragment, fragment).commit();
        menuItem.setChecked(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
