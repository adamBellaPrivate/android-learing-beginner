package com.learn.bella.roomdbexample.AsyncTasks;

import com.learn.bella.roomdbexample.BO.User;

import java.util.List;

/**
 * Created by adambella on 1/29/18.
 */

public interface GetAllUserAsyncTaskResponse {
    void processFinish(List<User> storedUsers);
}
