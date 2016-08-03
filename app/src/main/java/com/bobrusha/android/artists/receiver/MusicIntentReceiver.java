package com.bobrusha.android.artists.receiver;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.net.Uri;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.support.v7.app.NotificationCompat;

import com.bobrusha.android.artists.R;

/**
 * Created by Aleksandra on 03/08/16.
 */
public class MusicIntentReceiver extends BroadcastReceiver {
    private static final String OPEN_IN_MARKET = "market://details?id=";
    private static final String YA_MUSIC_PACKAGE_NAME = "ru.yandex.music";
    private static final String YA_RADIO_PACKAGE_NAME = "ru.yandex.radio";
    private static final int NOTIFICATION_ID = 1;


    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(AudioManager.ACTION_HEADSET_PLUG)) {
            int state = intent.getIntExtra("state", -1);

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            switch (state) {
                case 0:
                    notificationManager.cancel(NOTIFICATION_ID);
                    break;
                case 1:
                    NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context);
                    notificationBuilder.setContentTitle(context.getString(R.string.notification_title))
                            .setContentText(context.getString(R.string.notification_text))
                            .setSmallIcon(R.drawable.ic_earphones);

                    createAction(context, notificationBuilder, YA_MUSIC_PACKAGE_NAME,
                            R.drawable.ic_note, R.drawable.ic_download, R.string.open_ya_music);
                    createAction(context, notificationBuilder, YA_RADIO_PACKAGE_NAME,
                            R.drawable.ic_radio, R.drawable.ic_download, R.string.open_ya_radio);

                    Notification notification =
                            (new NotificationCompat.BigTextStyle(notificationBuilder)).bigText(
                                    context.getString(R.string.notification_text)
                            ).build();
                    notificationManager.notify(NOTIFICATION_ID, notification);
                    break;
            }
        }
    }

    public void createAction(Context context, NotificationCompat.Builder builder, String packageName,
                             @DrawableRes int icOpen, @DrawableRes int icDownload, @StringRes int name) {
        PackageManager manager = context.getPackageManager();
        Intent intent = manager.getLaunchIntentForPackage(packageName);
        if (intent != null) {
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            PendingIntent pi = PendingIntent.getActivity(context, 0, intent, 0);
            builder.addAction(icOpen, context.getString(name), pi);
        } else {
            PendingIntent pi = PendingIntent.getActivity(context, 0, new Intent(
                    Intent.ACTION_VIEW, Uri.parse(OPEN_IN_MARKET + packageName)), 0);
            builder.addAction(icDownload, context.getString(name), pi);
        }
    }

}
