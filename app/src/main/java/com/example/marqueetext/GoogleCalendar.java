package com.example.marqueetext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

public class GoogleCalendar extends AppCompatActivity {
EditText e1,e2,e3;
Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_calendar);
        e1=findViewById(R.id.editTextTextPersonName);
        e2=findViewById(R.id.editTextTextPersonName2);
        e3=findViewById(R.id.editTextTextPersonName3);
        button=findViewById(R.id.button4);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if (!e1.getText().toString().isEmpty() && !e2.getText().toString().isEmpty() && !e3.getText().toString().isEmpty()){
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);
                    intent.putExtra(CalendarContract.Events.TITLE, e1.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, e2.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, e3.getText().toString());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, true);
                    intent.putExtra(Intent.EXTRA_EMAIL, "priyankanigam25041999@gmail.com,test@yahoo.com,test2@yahoo.com");
                    if (intent.resolveActivity(getPackageManager()) != null) {
                        startActivity(intent);
                    } else {
                        Toast.makeText(GoogleCalendar.this, "There is no app that can support this action", Toast.LENGTH_LONG).show();
                    }
                }else {
                   Toast.makeText(GoogleCalendar.this, "Please Fill All the Fields !!!", Toast.LENGTH_LONG).show();
               }
            }
        });
    }
}