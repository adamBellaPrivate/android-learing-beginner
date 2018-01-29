package com.learn.bella.roomdbexample.AsyncTasks;


import android.arch.persistence.room.Room;
import android.content.Context;
import android.os.AsyncTask;

import com.learn.bella.roomdbexample.AppDatabase;
import com.learn.bella.roomdbexample.BO.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adambella on 1/29/18.
 */

public class GetAllUserAsyncTask extends AsyncTask<Void,Void,Boolean> {

    private Context mContext;
    private GetAllUserAsyncTaskResponse mDelegate = null;
    private  List<User> storedUsers = new ArrayList<>();

    public GetAllUserAsyncTask (Context context,GetAllUserAsyncTaskResponse delegate){
        mContext = context;
        mDelegate = delegate;
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        AppDatabase database = Room.databaseBuilder(mContext,AppDatabase.class,"users").build();

        storedUsers = database.userDao().getAll();

        if(storedUsers.size() == 0){
            storedUsers = loadContent(database);
        }

        return true;
    }

    @Override
    protected void onPostExecute(Boolean b) {
        super.onPostExecute(b);
        if(mDelegate != null){
            mDelegate.processFinish(storedUsers);
        }
    }

    private List<User> loadContent(AppDatabase database){
        User user = new User();
        user.setUid(0);
        user.setFirstName("Ádám");
        user.setLastName("Bella");

        User user2 = new User();
        user2.setUid(1);
        user2.setFirstName("Kristóf");
        user2.setLastName("Bella");

        User[] users = new User[]{user,user2};

        database.userDao().insertAll(users);

        return database.userDao().getAll();
    }
}
