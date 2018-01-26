package com.learn.bella.asynctaskexample;

import android.os.AsyncTask;

/**
 * Created by adambella on 1/26/18.
 */

// 1. It is type of parameters in doInBackground
// 2. It is type of parameter in onProgressUpdate
// 3. It is type of parameter in onPostExecute

public class CustomAsyncTask extends AsyncTask<String,Void,String> {
    public AsyncResponse delegate = null;

    private String description;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        description= "";
    }

    @Override
    protected String doInBackground(String... strings) {

        for(String string : strings){
            if(!string.isEmpty()) {
                description += string + "-> Length: " + string.length() + "\n";
            }
        }

        return description;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        if(delegate != null) {
            delegate.processFinish(s);
        }
    }
}
