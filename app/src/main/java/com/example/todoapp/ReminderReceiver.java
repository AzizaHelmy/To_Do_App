package com.example.todoapp;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.todoapp.Fragments.Taskes.HomeFragment;

import static android.app.Notification.EXTRA_NOTIFICATION_ID;

public class ReminderReceiver extends BroadcastReceiver {

    private static final String CHANNEL_ID = "1";
   // String time;
    String Remembertaske;

    private final int notificationId=1;

    @Override
    public void onReceive(Context context, Intent intent) {

       // time = intent.getStringExtra("spicificTime");
        Remembertaske = intent.getStringExtra("Taskmsg");

        createNotificationChannel(context);
        showNotification(context);

    }

    //====================================================
    private void showNotification(Context context) {
//        // Create an explicit intent for an Activity in your app

        Intent snoozeIntent = new Intent(context, ReminderReceiver.class);
        //snoozeIntent.setAction(ACTION_SNOOZE);
//        snoozeIntent.putExtra(EXTRA_NOTIFICATION_ID, 0);
//        PendingIntent snoozePendingIntent =
//                PendingIntent.getBroadcast(context, 0, snoozeIntent, 0);

        Intent intent = new Intent(context, HomeFragment.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.drawable.ic_baseline_notification_important_24);
        builder.setWhen(System.currentTimeMillis());
        builder.setContentTitle("Remeber !");
        builder.setContentText(" it's Time to do this Taske : "  + Remembertaske);
//        builder.addAction(R.drawable.ic_baseline_snooze_24, context.getString(R.string.snooze),
//               snoozePendingIntent);
        builder.addAction(R.drawable.ic_baseline_snooze_24, context.getString(R.string.ok), pendingIntent);
        builder.addAction(R.drawable.ic_baseline_notification_important_24, context.getString(R.string.snooz), null);
       // builder.setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_to_do_list));
        builder.setStyle(new NotificationCompat.BigPictureStyle()
               .bigPicture(BitmapFactory.decodeResource(context.getResources(), R.drawable.head)));
        builder.setPriority(NotificationCompat.PRIORITY_HIGH);
        builder.setContentIntent(pendingIntent)
                .setAutoCancel(false);
        builder.setDefaults(NotificationCompat.DEFAULT_SOUND);
       // builder.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
        notificationManager.notify(notificationId, builder.build());
//
    }

    //=====================================================
    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name ="alarm";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
           notificationManager.createNotificationChannel(channel);
        }
    }
}
