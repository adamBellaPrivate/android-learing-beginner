package com.learn.bella.broadcastreceiverexample.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by adambella on 1/22/18.
 */

public class OutComingCallBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String phoneNumber = intent.getExtras().getString(Intent.EXTRA_PHONE_NUMBER);
        Toast.makeText(context, "Phone number: "+ phoneNumber,
                Toast.LENGTH_LONG).show();

    }
}
