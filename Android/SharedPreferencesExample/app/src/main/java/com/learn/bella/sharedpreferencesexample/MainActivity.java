package com.learn.bella.sharedpreferencesexample;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String NOTIFICATION_ALLOW_STATE = "shared.preferences.notification.allow.state";
    private SharedPreferences ownSharedPreferences;
    private SharedPreferences defaultSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ownSharedPreferences = getSharedPreferences("SharedPreferencesExample",MODE_PRIVATE);
        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences( getBaseContext());

        Button notificationButton = findViewById(R.id.notification_allow_button);

        notificationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean oldValue = ownSharedPreferences.getBoolean(NOTIFICATION_ALLOW_STATE,false);

                SharedPreferences.Editor editor = ownSharedPreferences.edit();
                editor.putBoolean(NOTIFICATION_ALLOW_STATE,!oldValue);
                editor.commit();

                Log.d("Shared Preferences","New state: " + String.valueOf(ownSharedPreferences.getBoolean(NOTIFICATION_ALLOW_STATE,false)));

                //----------

                boolean oldEnabledNotification = defaultSharedPreferences.getBoolean("enableNotification",false);

                editor = defaultSharedPreferences.edit();
                editor.putBoolean("enableNotification",!oldEnabledNotification);
                editor.commit();

                Log.d("Shared Preferences", "New state of default preferences: " + String.valueOf(defaultSharedPreferences.getBoolean("enableNotification",false)));
            }
        });

        Button settingsButton = findViewById(R.id.settings_button);

        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().beginTransaction().addToBackStack("MyPreferenceFragment")
                        .replace(android.R.id.content, new MyPreferenceFragment()).commit();
            }
        });

    }

}
