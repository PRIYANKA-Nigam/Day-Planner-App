package com.example.marqueetext;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.BatteryManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.Date;

public class AlarmActivity extends AppCompatActivity {
    TimePicker timePicker;
    TextView textView;int hrs,min;
    Button button; private Ringtone ringtone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        timePicker=(TimePicker)findViewById(R.id.tp);
        textView=(TextView)findViewById(R.id.tt);
        button=(Button)findViewById(R.id.button3);
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                hrs=hourOfDay;
                min=minute;
                textView.setText("Alarm Set for - "+hrs +":"+min);
            }
        });
    }

    public void setTime(View view) {
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        Date date=new Date();
        Calendar calendar= Calendar.getInstance();
        Calendar cal_now=Calendar.getInstance();
        cal_now.setTime(date);
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY,hrs);
        calendar.set(Calendar.MINUTE,min);
        calendar.set(Calendar.SECOND,0);
        if(calendar.before(cal_now)){
            calendar.add(Calendar.DATE,1);
        }
        Intent intent= new Intent(AlarmActivity.this,MyBroadCastReceiver.class);
        PendingIntent pendingIntent=PendingIntent.getBroadcast(AlarmActivity.this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
    }

    public void CancelTime(View view) {
        Intent intent=new Intent(AlarmActivity.this,MyBroadCastReceiver.class);
        AlarmManager alarmManager=(AlarmManager)getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent=PendingIntent.getActivity(AlarmActivity.this,1,intent,PendingIntent.FLAG_UPDATE_CURRENT);
        if (pendingIntent!=null)
       alarmManager.cancel(pendingIntent);pendingIntent.cancel();
    }

}