package com.attra.forgroundservice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class NotificationReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String msg=intent.getStringExtra("MYDATA");
        Toast.makeText(context,msg,Toast.LENGTH_LONG).show();
    }
}
