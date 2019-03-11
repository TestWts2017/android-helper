package com.wings.helper;

import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

/**
 * Purpose: Create and Send notifications
 *
 * @author NikunjD
 * Created on March 11, 2019
 * Modified on March 11, 2019
 */

public class NotificationHelper {

    public static final String DEFAULT_NOTIFICATION_BUNDLE_KEY = "default_notification_bundle_key";
    public static final String CUSTOM_NOTIFICATION_BUNDLE_KEY = "custom_notification_bundle_key";

    /**
     * Create and Show Notification
     *
     * @param activity             to be open while on click
     * @param context              context of current class
     * @param bundle               pass data through intent - this can be null
     * @param channelId            channel id for above oreo devices
     * @param channelTitle         channel title for above oreo devices
     * @param smallIcon            notification small icon image
     * @param title                notification title
     * @param messageBody          notification message
     * @param isStickyNotification allow or disallow to remove notification while swipe on it
     * @param isSoundEnable        allow or disallow sound while notification appear
     * @param notificationId       notification id (should be unique)
     */
    public void sendDefaultNotification(Activity activity, Context context, Bundle bundle, String channelId,
                                        String channelTitle, int smallIcon,
                                        String title, String messageBody, boolean isStickyNotification,
                                        boolean isSoundEnable, int notificationId) {

        Intent intent = new Intent(context, activity.getClass());
        if (bundle != null) {
            intent.putExtra(DEFAULT_NOTIFICATION_BUNDLE_KEY, bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId);
        notificationBuilder.setSmallIcon(smallIcon);
        notificationBuilder.setContentTitle(title);
        notificationBuilder.setContentText(messageBody);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setOngoing(isStickyNotification);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationBuilder.setContentIntent(pendingIntent);

        if (isSoundEnable) {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder.setSound(defaultSoundUri);
        }
        
        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    channelTitle,
                    NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        if (notificationManager != null) {
            notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
        }
    }


    public void sendCustomNotification(Activity activity, Context context, Bundle bundle,
                                       RemoteViews remoteView, String channelId,
                                       String channelTitle, int smallIcon, boolean isStickyNotification,
                                       boolean isSoundEnable, int notificationId) {

        Intent intent = new Intent(context, activity.getClass());
        if (bundle != null) {
            intent.putExtra(CUSTOM_NOTIFICATION_BUNDLE_KEY, bundle);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context, channelId);
        notificationBuilder.setSmallIcon(smallIcon);
        notificationBuilder.setCustomContentView(remoteView);
        notificationBuilder.setAutoCancel(true);
        notificationBuilder.setOngoing(isStickyNotification);
        notificationBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);
        notificationBuilder.setContentIntent(pendingIntent);

        if (isSoundEnable) {
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            notificationBuilder.setSound(defaultSoundUri);
        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId,
                    channelTitle,
                    NotificationManager.IMPORTANCE_HIGH);
            if (notificationManager != null) {
                notificationManager.createNotificationChannel(channel);
            }
        }

        if (notificationManager != null) {
            notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
        }
    }
}
