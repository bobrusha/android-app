package com.bobrusha.android.artists.ui;

import android.app.Notification;
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
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.Toolbar;
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
    private static final String OPEN_IN_MARKET = "market://details?id=";
    private static final String YA_MUSIC_PACKAGE_NAME = "ru.yandex.music";
    private static final String YA_RADIO_PACKAGE_NAME = "ru.yandex.radio";
    private static final int NOTIFICATION_ID = 1;

    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
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

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = (NavigationView) findViewById(R.id.nvView);
        setupDrawerContent(navigationView);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

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
        BusProvider.getInstance().register(this);

        IntentFilter filter = new IntentFilter(Intent.ACTION_HEADSET_PLUG);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(receiver);
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
        Fragment fragment;

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

    public void createAction(Context context, NotificationCompat.Builder builder, String packageName,
                             @DrawableRes int icOpen, @DrawableRes int icDownload, @StringRes int name) {
        PackageManager manager = context.getPackageManager();
        Intent intent = manager.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
            builder.addAction(icOpen, getString(name), pi);
        } else {
            PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(
                    Intent.ACTION_VIEW, Uri.parse(OPEN_IN_MARKET + packageName)), 0);
            builder.addAction(icDownload, getString(name), pi);
        }
    }

    private class MusicIntentReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(AudioManager.ACTION_HEADSET_PLUG)) {
                int state = intent.getIntExtra("state", -1);

                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                switch (state) {
                    case 0:
                        notificationManager.cancel(NOTIFICATION_ID);
                        break;
                    case 1:
                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
                        notificationBuilder.setContentTitle(getString(R.string.notification_title))
                                .setContentText(getString(R.string.notification_text))
                                .setSmallIcon(R.drawable.ic_earphones);

                        createAction(context, notificationBuilder, YA_MUSIC_PACKAGE_NAME,
                                R.drawable.ic_note, R.drawable.ic_download, R.string.open_ya_music);
                        createAction(context, notificationBuilder, YA_RADIO_PACKAGE_NAME,
                                R.drawable.ic_radio, R.drawable.ic_download, R.string.open_ya_radio);

                        Notification notification =
                                (new NotificationCompat.BigTextStyle(notificationBuilder)).bigText(
                                        getString(R.string.notification_text)
                                ).build();
                        notificationManager.notify(NOTIFICATION_ID, notification);
                        break;
                }
            }
        }
    }

}
