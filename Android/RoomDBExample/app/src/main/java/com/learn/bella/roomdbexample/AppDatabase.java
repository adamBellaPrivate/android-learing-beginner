package com.learn.bella.roomdbexample;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.learn.bella.roomdbexample.BO.User;
import com.learn.bella.roomdbexample.Interface.UserDao;

/**
 * Created by adambella on 1/29/18.
 */

@Database(entities = {User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract UserDao userDao();
}
