package com.attra.forgroundservice.Service;

import com.attra.forgroundservice.NotificationHelper.notificationHelper;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyNotificationService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        if(remoteMessage.getNotification()!=null){

            String title=remoteMessage.getNotification().getTitle();
            String body=remoteMessage.getNotification().getBody();
            notificationHelper.Notification1(getApplicationContext(),title,body);
        }
    }
}
