package com.learn.bella.localnotificationexample;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private static final int NOTIFICATION_ID = 1;
    private static final String INTENT_TYPE = "IntentType";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.notification_button);

        handleIntent(getIntent());

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNotification();
            }
        });
    }

    private void showNotification(){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(INTENT_TYPE,"LocalNotification");

        /* Intent.FLAG_ACTIVITY_CLEAR_TOP:
        * If set, and the activity being launched is already running in the current task,
        * then instead of launching a new instance of that activity,
        * all of the other activities on top of it will be closed and
        * this Intent will be delivered to the (now on top) old activity as a new Intent.
        * https://developer.android.com/reference/android/content/Intent.html
        * */

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this)
                .setContentTitle("Content title")
                .setContentText("text")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setAutoCancel(true)
                .setContentIntent(pIntent)
                .build();

        NotificationManager notificationManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        notificationManager.notify(NOTIFICATION_ID, notification);

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent){

        if(intent != null && intent.getExtras() != null && intent.getExtras().containsKey(INTENT_TYPE) && intent.getStringExtra(INTENT_TYPE).equals("LocalNotification")){
            Toast.makeText(this,"Detect local notification",Toast.LENGTH_SHORT).show();
        }

    }
}
