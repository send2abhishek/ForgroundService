package com.attra.forgroundservice.NotificationHelper;

import android.app.Notification;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

import com.attra.forgroundservice.R;

import static com.attra.forgroundservice.MyApp.CHANNEL1_ID;

public class notificationHelper  {

    public static void Notification1(Context context,String title,String desc) {


        NotificationManagerCompat managerCompat=NotificationManagerCompat.from(context);

            Notification notification = new NotificationCompat.Builder(context, CHANNEL1_ID)
                    .setSmallIcon(R.mipmap.ic_notify)
                    .setContentTitle(title)
                    .setContentText(desc)
                    .setSubText("Media Player")
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                    .build();


            managerCompat.notify(1, notification);


        }
    }

