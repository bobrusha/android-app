package com.bobrusha.android.artists.ui;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
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
    private static final String TAG = "MainActivity";
    private static final String OPEN_IN_MARKET = "market://details?id=";
    private static final String YA_MUSIC_PACKAGE_NAME = "ru.yandex.music";
    private static final String YA_RADIO_PACKAGE_NAME = "ru.yandex.radio";

    private NavigationView mNavigationView;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle drawerToggle;

    private MusicIntentReceiver myReceiver;

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

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, toolbar,
                R.string.drawer_open, R.string.drawer_close);
        mDrawerLayout.addDrawerListener(drawerToggle);


        myReceiver = new MusicIntentReceiver();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    protected void onResume() {
        super.onResume();
        BusProvider.getInstance().register(this);

        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(myReceiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(myReceiver);
        BusProvider.getInstance().unregister(this);
    }


    /**
     * Method that calling then user chose musician.
     *
     * @param event – event for method invocation using Bus.
     */


    @Subscribe
    public void onArtistSelected(ArtistPreviewOnClickEvent event) {
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
            default:
                fragment = new MainFragment();
        }

        mDrawerLayout.closeDrawers();
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

    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AudioManager.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);
                switch (state) {
                    case 0:
                        Log.d(TAG, "Headset is unplugged");
                        break;
                    case 1:
                        Log.d(TAG, "Headset is plugged");

                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
                        notificationBuilder.setContentTitle("qq").setContentText("qq").setSmallIcon(R.drawable.ic_earphones);

                        createAction(context, notificationBuilder, YA_MUSIC_PACKAGE_NAME, R.drawable.ic_note, R.drawable.ic_download );
                        createAction(context, notificationBuilder, YA_RADIO_PACKAGE_NAME, R.drawable.ic_radio, R.drawable.ic_download );

                        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(1, notificationBuilder.build());

                        break;
                    default:
                        Log.d(TAG, "I have no idea what the headset state is");
                }
            }
        }
    }

    public void createAction(Context context, NotificationCompat.Builder builder, String packageName,
                             @DrawableRes int icOpen, @DrawableRes int icDownload) {
        PackageManager manager = context.getPackageManager();
        Intent intent = manager.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
            builder.addAction(icOpen, "Open ", pi);
        } else {
            PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(
                    Intent.ACTION_VIEW, Uri.parse(OPEN_IN_MARKET + packageName)), 0);
            builder.addAction(icDownload, "Open in market", pi);
        }


    }

}
