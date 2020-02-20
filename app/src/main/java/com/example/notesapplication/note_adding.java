package com.example.notesapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class note_adding extends AppCompatActivity {

    Context mContext;
    int layoutresources;
    Calendar calendar;

    String todayDate;
    String currenttime;

    EditText noteName , Description;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_adding);

        noteName = findViewById(R.id.edittextnotename);
        Description = findViewById(R.id.edittextdesc);

        noteName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if(s.length() != 0){
                    getSupportActionBar().setTitle(s);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        calendar = Calendar.getInstance();
        todayDate= calendar.get(Calendar.YEAR)+"/"+(calendar.get(Calendar.MONTH)+1)+"/"+calendar.get(Calendar.DAY_OF_MONTH);
        Log.d("DATE", "Date: "+ todayDate);
        currenttime = pad(calendar.get(Calendar.HOUR))+":"+pad(calendar.get(Calendar.MINUTE));
        Log.d("TIME", "Time: "+ currenttime);
    }
    private String pad(int time) {
        if(time < 10)
            return "0"+time;
        return String.valueOf(time);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.save){
            if(noteName.getText().length() != 0){
                Notes note = new Notes(noteName.getText().toString(),Description.getText().toString(),todayDate,currenttime);
                DatabaseNotes sDB = new DatabaseNotes(this);
               // int id = sDB.addNote(note);
               // Notes check = sDB.addNote(id);
               // Log.d("inserted", "Note: "+ id + " -> Title:" + check.getNameofnote()+" Date: "+ check.getDate());
                onBackPressed();

                Toast.makeText(this, "Note Saved.", Toast.LENGTH_SHORT).show();
            }else {
                noteName.setError("Title Can not be Blank.");
            }

        }else if(item.getItemId() == R.id.delete){
            Toast.makeText(this, "Canceled", Toast.LENGTH_SHORT).show();
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}
