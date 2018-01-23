package com.learn.bella.notelist;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.learn.bella.notelist.bo.Note;
import com.learn.bella.notelist.db.NoteDataSource;

import java.util.List;

public class MainActivity extends ListActivity {
    private NoteDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataSource = new NoteDataSource(this);

        dataSource.open(true);
        List<Note> notes = dataSource.listAllNodes();
        dataSource.close();

        for(Note note : notes) {
            Log.d("NoteSQLiteLog: ",note.toString());
        }

        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_selectable_list_item,notes);
        setListAdapter(adapter);
    }

    public void onClick(View view){
        ArrayAdapter<Note> noteAdapter = (ArrayAdapter<Note>) getListAdapter();
        Note note = null;
        switch (view.getId()){
            case R.id.new_note_button:
                showNewNotePopup();
                break;
            case R.id.delete_note_button:
                deleteNote(0);
                break;
        }
        noteAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        deleteNote(position);
    }

    private void deleteNote(int position){
        ArrayAdapter<Note> noteAdapter = (ArrayAdapter<Note>) getListAdapter();
        if (getListAdapter().getCount() > position) {
            Note note = noteAdapter.getItem(position);
            dataSource.open(true);
            dataSource.deleteNote(note);
            dataSource.close();
            noteAdapter.remove(note);
            noteAdapter.notifyDataSetChanged();
        }
    }

    private void showNewNotePopup(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Please fill out the form");

        Context context = this.getApplicationContext();
        LinearLayout layout = new LinearLayout(context);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText titleInput = new EditText(this);
        titleInput.setHint("Title:");
        titleInput.setInputType(InputType.TYPE_CLASS_TEXT );
        layout.addView(titleInput);

        final EditText descInput = new EditText(this);
        descInput.setHint("Description:");
        descInput.setInputType(InputType.TYPE_CLASS_TEXT );
        layout.addView(descInput);

        builder.setView(layout);
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        final AlertDialog dialog = builder.create();
        dialog.show();

        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String title = titleInput.getText().toString();
                String desc = descInput.getText().toString();
                if(!title.isEmpty() && !desc.isEmpty()) {
                    addNewNote(title, desc);
                    dialog.dismiss();
                }

                if(title.isEmpty()){
                    titleInput.setError("It is required*");
                }

                if(desc.isEmpty()){
                    descInput.setError("It is required*");
                }
            }
        });
    }

    private void addNewNote(String title, String desc){
        dataSource.open(true);
        Note newNote = dataSource.createNote(title,desc);
        dataSource.close();
        ArrayAdapter<Note> noteAdapter = (ArrayAdapter<Note>) getListAdapter();
        noteAdapter.add(newNote);
        noteAdapter.notifyDataSetChanged();
    }
}
