package com.learn.bella.notelist.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.learn.bella.notelist.bo.Note;
import com.learn.bella.notelist.helpers.NoteDBHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by adambella on 1/22/18.
 */

public class NoteDataSource {
    private SQLiteDatabase database;
    private NoteDBHelper dbHelper;
    private String[] allColumns = {NoteTable.COLUMN_ID, NoteTable.COLUMN_TITLE, NoteTable.COLUMN_DESC};

    public NoteDataSource(Context context){
        dbHelper = new NoteDBHelper(context);
    }

    public void open(boolean isWritable) throws SQLException{
        if(database != null && database.isOpen()){
            close();
        }

        if(isWritable) {
            database = dbHelper.getWritableDatabase();
        }else{
            database = dbHelper.getReadableDatabase();
        }
    }

    public void close(){
        dbHelper.close();
    }

    public Note createNote(String aTitle, String aDesc) throws SQLiteException{
        ContentValues values = new ContentValues();
        values.put(NoteTable.COLUMN_TITLE,aTitle);
        values.put(NoteTable.COLUMN_DESC,aDesc);

        if(!existsTable(NoteTable.TABLE_NOTE_NAME)){
            dbHelper.onCreate(database);
            if(!existsTable(NoteTable.TABLE_NOTE_NAME)){
                return null;
            }
        }

        long insertId = database.insert(NoteTable.TABLE_NOTE_NAME,null,values);
        Cursor cursor = database.query(NoteTable.TABLE_NOTE_NAME,allColumns,NoteTable.COLUMN_ID + " = " + insertId,null,null,null,null);
        cursor.moveToFirst();
        Note newNote = cursorToNote(cursor);
        cursor.close();
        return newNote;
    }

    private boolean existsTable(String name){
        List<String> tables = new ArrayList<>();

        Cursor c =  database.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);
        if (c.moveToFirst()) {
            while ( !c.isAfterLast() ) {
                tables.add(c.getString(0));
                c.moveToNext();
            }
        }
        c.close();

        return tables.contains(name);
    }

    private Note cursorToNote(Cursor cursor){
        Note note = new Note();

        note.set_id(cursor.getLong(cursor.getColumnIndex(NoteTable.COLUMN_ID)));
        note.setTitle(cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN_TITLE)));
        note.setDesc(cursor.getString(cursor.getColumnIndex(NoteTable.COLUMN_DESC)));

        return note;
    }

    public void deleteNote(Note note){
        database.delete(NoteTable.TABLE_NOTE_NAME,NoteTable.COLUMN_ID + " = " + note.get_id(),null);
    }

    public List<Note> listAllNodes(){
        List<Note> notes = new ArrayList<>();
        Cursor cursor = database.query(NoteTable.TABLE_NOTE_NAME,allColumns,null,null,null,null,null);
        cursor.moveToFirst();
        while(!cursor.isAfterLast()){
            Note note = cursorToNote(cursor);
            notes.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return notes;
    }
}
