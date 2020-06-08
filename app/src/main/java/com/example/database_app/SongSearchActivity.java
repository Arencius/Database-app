package com.example.database_app;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class SongSearchActivity extends AppCompatActivity {

    EditText entry;
    RadioGroup radioGroup;
    RadioButton radioButton;
    Button showResults;
    TextView resultsText;

    DatabaseManager manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_search);

        manager = new DatabaseManager(this);
        entry = findViewById(R.id.word_entry);
        radioGroup = findViewById(R.id.radio_group);
        showResults = (Button) findViewById(R.id.show_results);
        resultsText = findViewById(R.id.results_text);

        showResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c;
                String choose = checkedRadioButton();
                String value = entry.getText().toString();

                switch (choose) {
                    case "All songs":
                        c = manager.selectAll();
                        resultsText.setText(showData(c));
                        break;
                    case "Song title":
                        c = manager.selectOnCondition("title", value);
                        resultsText.setText(showData(c));
                        break;

                    case "Artist":
                        c = manager.selectOnCondition("artist", value);
                        resultsText.setText(showData(c));
                        break;
                    case "Song album":
                        c = manager.selectOnCondition("album", value);
                        resultsText.setText(showData(c));
                        break;
                }

            }
        });
    }

    String checkedRadioButton() {
        // returns text of checked radio button to determine which one was pressed
        int radioButtonID = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioButtonID);

        return radioButton.getText().toString();
    }

    String showData(Cursor c) {
        StringBuilder data = new StringBuilder();
        while (c.moveToNext()) {
            Song song = new Song(c.getString(0), c.getString(1), c.getString(2), c.getString(3), c.getInt(4));
            data.append(String.format("%s - %s\n", song.getArtist(), song.getTitle()));
        }
        return data.toString();
    }
}
