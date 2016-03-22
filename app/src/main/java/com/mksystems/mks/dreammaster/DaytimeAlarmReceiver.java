package com.mksystems.mks.dreammaster;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------
// class DaytimeAlarmReceiver
//
// This class is invoked when the daytime AlarmManager triggers. It will sound an alert to remind
// the user to perform a reality check.
//


public class DaytimeAlarmReceiver extends AppCompatActivity {


//-----------------------------------------------------------------------------------------------
// DaytimeAlarmReceiver::onCreate
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daytime_alarm_receiver);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//end of DaytimeAlarmReceiver::onCreate
//-----------------------------------------------------------------------------------------------


}// end of class DaytimeAlarmReceiver
//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------


