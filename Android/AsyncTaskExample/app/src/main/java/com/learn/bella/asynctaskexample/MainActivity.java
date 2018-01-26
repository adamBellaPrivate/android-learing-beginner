package com.learn.bella.asynctaskexample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity implements AsyncResponse {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CustomAsyncTask task = new CustomAsyncTask();
        task.delegate = this;
        task.execute("This is an example text message", "Hello Moto", "Servus", "Ich habe ein Hund");
    }

    @Override
    public void processFinish(String output) {
        Log.d("Result", output);
    }
}

