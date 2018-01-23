package com.learn.bella.notelist.db;

import android.database.sqlite.SQLiteDatabase;

import com.learn.bella.notelist.bo.DatabaseBuilder;
import com.learn.bella.notelist.bo.enums.DatabaseParameterType;

/**
 * Created by adambella on 1/22/18.
 */

public class NoteTable {
    public static final String TABLE_NOTE_NAME = "note";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DESC = "desc";

    //First time when you create the database
    public static void onCreate(SQLiteDatabase database){
        DatabaseBuilder builder = new DatabaseBuilder(NoteTable.TABLE_NOTE_NAME);
        builder.appendParameter(COLUMN_TITLE, DatabaseParameterType.TEXT,false);
        builder.appendParameter(COLUMN_DESC, DatabaseParameterType.TEXT,false);
        database.execSQL(builder.toString());
    }

    //Drop and recreate the database, in this point you will lost everything from the table
    public static void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion){
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTE_NAME);
        onCreate(database);
    }
}
