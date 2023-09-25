package com.be.wildanimaldetection.Activities.ui.notify;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class ButtonReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        System.out.println("ButtonReceiver : called");

        int notificationId = intent.getIntExtra("notificationId", 0);

        // Do what you want were.

        // if you want cancel notification
        try {
            Intent alertSound =  intent.getParcelableExtra(Intent.EXTRA_INTENT);
            context.stopService(alertSound);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.cancel(notificationId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}