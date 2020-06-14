package com.example.database_app;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity{

    Button openSearch;    // opens second activity where user can browse through database
    EditText titleEntry, artistEntry, albumEntry, durationEntry, viewsEntry;
    Button addRecord;

    DatabaseManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        openSearch = findViewById(R.id.openSearch);

        // opening second activity
        openSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent open = new Intent(getApplicationContext(), SongSearchActivity.class);
                startActivity(open);
            }
        });

        manager = new DatabaseManager(this);

        titleEntry = findViewById(R.id.title_entry);
        artistEntry = findViewById(R.id.artist_entry);
        albumEntry = findViewById(R.id.album_entry);
        durationEntry = findViewById(R.id.duration_entry);
        viewsEntry = findViewById(R.id.views_entry);

        final EditText[] inputs = {titleEntry, artistEntry, albumEntry, durationEntry, viewsEntry}; // array used to minimize code size to clear inputs after successful addition

        addRecord = findViewById(R.id.add_button);   // adds song to database

        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String title = titleEntry.getText().toString();
                String artist = artistEntry.getText().toString();
                String album = albumEntry.getText().toString();
                String duration = durationEntry.getText().toString();
                String views = viewsEntry.getText().toString();   // views as a string to check if input is not empty

                if(!allCorrect(title,artist,album,duration, views))
                    Toast.makeText(MainActivity.this, "No field can remain empty", Toast.LENGTH_SHORT).show();

                else {
                    if(!isFormatted(duration)) {
                        Toast.makeText(MainActivity.this, "Incorrect duration format. mm:ss required", Toast.LENGTH_SHORT).show();
                        durationEntry.setText("");
                    } else {
                        int viewsInt = Integer.parseInt(views);

                        manager.insert(title, artist, album, duration, viewsInt);
                        Toast.makeText(MainActivity.this, "Song added to database", Toast.LENGTH_SHORT).show();

                        // clearing inputs
                        for(EditText e: inputs) {
                            e.setText("");
                        }
                    }
                }
            }
        });
    }

    boolean isFormatted(String s) {
        // checks if entered song duration is correctly formatted (mm:ss)
        return s.matches("[0-9]{1,2}:[0-5][0-9]");
    }
    boolean allCorrect(String t, String ar, String al, String d, String v) {
        // checks if all inputs are correctly filled
        return !(t.isEmpty() || ar.isEmpty() || al.isEmpty() || d.isEmpty() || v.isEmpty());
    }
}
