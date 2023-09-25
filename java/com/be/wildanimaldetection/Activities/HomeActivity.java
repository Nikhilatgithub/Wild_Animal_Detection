package com.be.wildanimaldetection.Activities;

import android.app.ActivityManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.be.wildanimaldetection.Activities.ui.broadcast.BroadcastService;
import com.be.wildanimaldetection.MainActivity;
import com.be.wildanimaldetection.R;
import com.be.wildanimaldetection.databinding.ActivityHomeBinding;
import com.be.wildanimaldetection.firestore.ImageFromDBService;
import com.be.wildanimaldetection.manager.UpdateHomeProfile;
import com.google.android.material.navigation.NavigationView;


public class HomeActivity extends AppCompatActivity {

    public static final String CHANNEL_1_ID ="channel_1";
    public static final String CHANNEL_2_ID ="channel_2";

    public static AppCompatActivity activity;
    public static String userEmail = "";
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityHomeBinding binding;
    public static  NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityHomeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        try {
            activity=this;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.Map, R.id.About,R.id.Broadcast,R.id.LiveSteam)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        try {
            Cursor res=MainActivity.dbHelper.getLoginData();
            res.moveToFirst();
            String email=res.getString(0);
            UpdateHomeProfile profile = new UpdateHomeProfile();
            profile.setEmail(email);
            userEmail=email;


        } catch (Exception e) {
            e.printStackTrace();
        }
        createNotificationChannel();     //  creating notification channels;

        try {
            startMsgSync(); //           msg notification when new msg added;
            startImgSync(); //           images sync to server;


        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_home);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


public void startMsgSync()
{
    try {
        if(isMyServiceRunning(ImageFromDBService.class)) {

        }
        else {
            try {
                Intent intent = new Intent(getApplicationContext(), BroadcastService.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    startForegroundService(intent);
                } else {
                    startService(intent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    public void startImgSync()
    {
        try {
            if(isMyServiceRunning(ImageFromDBService.class))
            {

            }
            else{
                try {
                    Intent intent = new Intent(getApplicationContext(), ImageFromDBService.class);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        startForegroundService(intent);
                    }else {
                        startService(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void createNotificationChannel()
    {
        try {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                NotificationChannel channel1 = new NotificationChannel(CHANNEL_1_ID,"channel1", NotificationManager.IMPORTANCE_HIGH);
                channel1.setDescription("Animal Detection Broadcast");
                NotificationChannel channel2 = new NotificationChannel(CHANNEL_2_ID,"channel2", NotificationManager.IMPORTANCE_HIGH);
                channel1.setDescription("Animal Detection Images");
                NotificationManager notificationManager = getSystemService(NotificationManager.class);
                notificationManager.createNotificationChannel(channel1);
                notificationManager.createNotificationChannel(channel2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isMyServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }
}