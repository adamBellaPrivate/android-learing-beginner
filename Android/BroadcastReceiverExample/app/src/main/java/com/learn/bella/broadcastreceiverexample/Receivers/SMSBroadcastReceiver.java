package com.learn.bella.broadcastreceiverexample.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.widget.Toast;

/**
 * Created by adambella on 1/22/18.
 */

public class SMSBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle extras = intent.getExtras();

        Object[] pdus = (Object[]) extras.get("pdus");
        for (Object pdu : pdus) {
            SmsMessage msg = SmsMessage.createFromPdu((byte[]) pdu);
            String number= msg.getOriginatingAddress();
            String text= msg.getMessageBody();
            Toast.makeText(context, "SMS details:, phone number: " + number + ", content: "+text,
                    Toast.LENGTH_LONG).show();
        }

        //The mail can't receive to inbox, because of:
        //abortBroadcast(); Doesn't work from KitKat.

    }
}
