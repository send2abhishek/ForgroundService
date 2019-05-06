package com.attra.forgroundservice;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class MyApp extends Application {

    public static final String CHANNEL1_ID="channel1";
    public static final String CHANNEL2_ID="channel2";
    public static final String CHANNEL3_ID="channel3";
    private NotificationManager manager;

    @Override
    public void onCreate() {
        super.onCreate();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            NotificationChannel channel1=new NotificationChannel(CHANNEL1_ID,
                    "Audio Notification",NotificationManager.IMPORTANCE_LOW);
            channel1.setDescription("Channel to get Audio Notification");


            NotificationChannel channel2=new NotificationChannel(CHANNEL2_ID,
                    "Audio Notification",NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Channel to get Video Notification");


            NotificationChannel channel3=new NotificationChannel(CHANNEL3_ID,
                    "Audio Notification",NotificationManager.IMPORTANCE_HIGH);
            channel1.setDescription("Channel to get Audio and Video Notification");



            manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
            manager.createNotificationChannel(channel2);
            manager.createNotificationChannel(channel3);


        }


    }
}
