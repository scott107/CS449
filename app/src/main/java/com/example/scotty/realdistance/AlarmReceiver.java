package com.example.scotty.realdistance;

import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

public class AlarmReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        Toast.makeText(context, "Alarm !!!!!!!!!!", Toast.LENGTH_LONG).show();
    }
}
