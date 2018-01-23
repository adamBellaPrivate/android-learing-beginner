package com.learn.bella.filemanagmentexample;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String content = "My favorite brands are: BMW, Coca-Cola, Apple, Pick\n";

        createFileInRoot("exampleFile1.txt", content);

        readFileByName("exampleFile1.txt");

        readRawById(R.raw.raw_test);
    }

    private void createFileInRoot(String fileName, String content){
        FileOutputStream out = null;
        try {
            out = openFileOutput(fileName, Context.MODE_APPEND);
            out.write(content.getBytes());
            Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void readFileByName(String name) {
        FileInputStream in = null;
        try {
            in = openFileInput(name);
            int ch;
            StringBuilder sb = new StringBuilder();
            while ((ch = in.read()) != -1) {
                sb.append((char) ch);
            }
            Toast.makeText(this, sb.toString(), Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void readRawById(int id) {
        InputStream in = null;
        try {
             Resources resources = getResources();
             in = resources.openRawResource(id);
             int ch;
             StringBuilder sb = new StringBuilder();
             while ((ch=in.read())!=-1) {
                 sb.append((char)ch);
             }
            Toast.makeText(this, sb.toString(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
