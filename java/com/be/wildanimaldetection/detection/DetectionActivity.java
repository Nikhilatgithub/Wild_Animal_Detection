package com.be.wildanimaldetection.detection;

import android.app.KeyguardManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.be.wildanimaldetection.R;

public class DetectionActivity extends AppCompatActivity {

    Button click;
    ImageView imageView;
    TextView txt;
    Intent alertSound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);
        imageView = (ImageView) findViewById(R.id.imageViewDet);
        txt=(TextView) findViewById(R.id.textViewDet);
        click=(Button) findViewById(R.id.buttonDet);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setTurnScreenOn(true);
            KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);
            if(keyguardManager!=null)
                keyguardManager.requestDismissKeyguard(this, null);
        }
        getWindow().addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                        | WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD
                        | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
                        | WindowManager.LayoutParams.FLAG_ALLOW_LOCK_WHILE_SCREEN_ON
        );

        try {
            byte[] byteArray = getIntent().getByteArrayExtra("image");
            Bitmap bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
            imageView.setImageBitmap(bitmap);

            String detection = getIntent().getStringExtra("detection");

            txt.setText("Wild animal detected\nDetection : "+detection);
            alertSound =  getIntent().getParcelableExtra(Intent.EXTRA_INTENT);
        } catch (Exception e) {
            e.printStackTrace();
        }



        click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    stopService(alertSound);

                } catch (Exception e) {
                    e.printStackTrace();
                }try {
                    finish();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                try {
                    int notificationId = getIntent().getIntExtra("notificationId", 0);
                    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                    manager.cancel(notificationId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }


}