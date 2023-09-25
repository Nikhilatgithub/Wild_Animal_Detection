package com.be.wildanimaldetection.Activities.ui.broadcast;

import static com.be.wildanimaldetection.Activities.HomeActivity.CHANNEL_1_ID;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.be.wildanimaldetection.Activities.HomeActivity;
import com.be.wildanimaldetection.R;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Map;

public class BroadcastService extends Service {
    private FirebaseFirestore db;
    private static final String NOTIF_CHANNEL_ID = "Channel_Id";
    public BroadcastService() {
        try {
            db = FirebaseFirestore.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            startMyOwnForeground();
        else
            startForeground();
    }

    private void startMyOwnForeground(){

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){


            String channelName = "Broadcast Service";
            NotificationChannel chan = new NotificationChannel(NOTIF_CHANNEL_ID, channelName, NotificationManager.IMPORTANCE_NONE);
            chan.setLightColor(Color.BLUE);
            chan.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            assert manager != null;
            manager.createNotificationChannel(chan);

            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIF_CHANNEL_ID);
            Notification notification = notificationBuilder.setOngoing(true)
                    .setSmallIcon(R.drawable.tiger2)
                    .setContentTitle("App is running in background for broadcast")
                    .setPriority(NotificationManager.IMPORTANCE_MIN)
                    .setCategory(Notification.CATEGORY_SERVICE)
                    .build();
            startForeground(2, notification);
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        try {
            db.collection("Messages").orderBy("time", Query.Direction.DESCENDING).limit(1).addSnapshotListener(
                    new EventListener<QuerySnapshot>() {


                        @Override
                        public void onEvent(@Nullable QuerySnapshot value,
                                            @Nullable FirebaseFirestoreException e) {
                            if (e != null) {
                                System.out.println("from broadcast : message not received");
                                return;
                            }


                            for (QueryDocumentSnapshot doc : value) {

                                if (doc.getData() != null) {
                                    Map<String, Object> catdata=doc.getData();
                                    System.out.println("from broadcast : message received");
                                    try {
//
                                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(BroadcastService.this);
                                        NotificationCompat.Builder notification = new NotificationCompat.Builder(BroadcastService.this, CHANNEL_1_ID);
                                        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
                                        bigText.setBigContentTitle("Animal Detection");
                                        bigText.setSummaryText("Broadcast Message");
                                        bigText.bigText(catdata.get("msg").toString());
                                        notification.setSmallIcon(R.mipmap.ic_launcher_round);
                                        notification.setContentTitle(catdata.get("user").toString().concat(" says "));
                                        notification.setContentText(catdata.get("msg").toString());
                                        notification.setPriority(Notification.PRIORITY_MAX);
                                        notification.setAutoCancel(true);
                                        notification.setCategory(Notification.CATEGORY_MESSAGE);
                                        notification.setDefaults(Notification.DEFAULT_SOUND);
                                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                                        notification.setSound(alarmSound);
                                        notification.setStyle(bigText);
                                        notificationManager.notify(1,notification.build() );


                                    } catch (Exception ex) {
                                        System.out.println(ex.getMessage());
                                        Toast.makeText(BroadcastService.this, ex.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    //  catlog.insertInCatlog((String) catdata.get("msg"),(String) catdata.get("user"),doc.getId());
                                }
                            }

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }


        return START_STICKY;
    }
    private void startForeground() {
        Intent notificationIntent = new Intent(this, HomeActivity.class);

        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                notificationIntent, 0);

        startForeground(2, new NotificationCompat.Builder(this,
                NOTIF_CHANNEL_ID) // don't forget create a notification channel first
                .setOngoing(true)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentTitle(getString(R.string.app_name))
                .setContentText("Service is running background")
                .setContentIntent(pendingIntent)
                .build());
    }
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }



}
