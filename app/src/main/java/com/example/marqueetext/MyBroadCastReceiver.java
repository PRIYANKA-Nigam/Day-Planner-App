package com.example.marqueetext;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;

import androidx.annotation.RequiresApi;

public class MyBroadCastReceiver extends BroadcastReceiver {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {
        Vibrator vibrator=(Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(2000);
        Notification notifications=new Notification.Builder(context).setContentTitle("Alarm i On")
                .setContentText("You had set up the alarm").setSmallIcon(R.mipmap.ic_launcher).build();
        NotificationManager manager=(NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        final boolean b = notifications.flags != Notification.FLAG_AUTO_CANCEL;
        manager.notify(0,notifications);
        Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        Ringtone r =RingtoneManager.getRingtone(context,notification);
        r.play();

    }
}
