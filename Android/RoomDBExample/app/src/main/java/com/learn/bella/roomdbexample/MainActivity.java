package com.learn.bella.roomdbexample;

import android.arch.persistence.room.Room;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.learn.bella.roomdbexample.AsyncTasks.GetAllUserAsyncTask;
import com.learn.bella.roomdbexample.AsyncTasks.GetAllUserAsyncTaskResponse;
import com.learn.bella.roomdbexample.BO.User;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements GetAllUserAsyncTaskResponse {
    //https://developer.android.com/topic/libraries/architecture/adding-components.html

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GetAllUserAsyncTask task = new GetAllUserAsyncTask(getApplicationContext(),this);
        task.execute();
    }

    @Override
    public void processFinish(List<User> storedUsers) {
        for(User storedUser : storedUsers){
            Log.d("User",storedUser.toString());
        }
    }
}
