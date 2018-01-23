package com.learn.bella.broadcastreceiverexample.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by adambella on 1/22/18.
 */

public class AirplaneModBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        boolean airplaneModState = intent.getBooleanExtra("state",false);
        Toast.makeText(context,"Airplane mod: " + (airplaneModState ? "enabled" : "disabled"),Toast.LENGTH_SHORT).show();
    }
}
