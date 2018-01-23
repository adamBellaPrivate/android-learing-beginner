package com.learn.bella.notelist.helpers;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.learn.bella.notelist.db.NoteTable;

/**
 * Created by adambella on 1/22/18.
 */

public class NoteDBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notetable.db";
    private static final int DATABASE_VERSION = 1;

    public NoteDBHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        NoteTable.onCreate(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        NoteTable.onUpgrade(sqLiteDatabase,i,i1);
    }
}
