package com.example.marqueetext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.widget.Toast;

import java.util.Calendar;

public class EventActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        Intent intent=new Intent(Intent.ACTION_INSERT, CalendarContract.Events.CONTENT_URI);
        Calendar begin = Calendar.getInstance();
        begin.set(2022,0,1,9,30);
        Calendar end= Calendar.getInstance();
        end.set(2022,0,1,10,30);
        intent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, begin.getTime());
        intent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,end.getTimeInMillis());
        intent.putExtra(CalendarContract.Events.TITLE,"Android Classes ");
        intent.putExtra(CalendarContract.Events.EVENT_LOCATION,"Android Classes Venue");
        Toast.makeText(this,"You will be notified for the events in time",Toast.LENGTH_LONG).show();
        startActivity(intent);

    }
}