package com.mksystems.mks.dreammaster;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;


//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------
// class SetDaytimeReminder
//

public class SetDaytimeReminder extends AppCompatActivity {


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;


//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::onCreate
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_daytime_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//end of SetDaytimeReminder::onCreate
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// MainActivity::setDaytimeAlarmManager
//
// Sets the alarm manager to fire at the specified start time and then repeat at the specified
// repeat interval.
//

    public void setDaytimeAlarmManager(View v) {


        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DaytimeAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // set the alarm to start at the specified start time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 11);
        calendar.set(Calendar.MINUTE, 51);

        // set the alarm to repeat at the specified interval
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, alarmIntent);


        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        TextView textView = (TextView) findViewById(R.id.starting_time);
        textView.setText(sdf.format(calendar.getTime()));

    }

//end of MainActivity::setDaytimeAlarmManager
//-----------------------------------------------------------------------------------------------

}// end of class SetDaytimeReminder
//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------