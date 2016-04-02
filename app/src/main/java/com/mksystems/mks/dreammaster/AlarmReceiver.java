package com.mksystems.mks.dreammaster;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.ToneGenerator;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.Calendar;

//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------
// class AlarmReceiver
//
// This class is invoked when the AlarmManager triggers. It will sound an alert to remind
// the user to perform a reality check.
//


public class AlarmReceiver extends BroadcastReceiver

{

//-----------------------------------------------------------------------------------------------
// AlarmReceiver::onReceive
//

    @Override
    public void onReceive(Context context, Intent intent)
    {

        ToneGenerator tg;

        try {

            //select tone with volume 100
            tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);

            tg.startTone(ToneGenerator.TONE_PROP_BEEP);
            sleep(200);
            tg.startTone(ToneGenerator.TONE_PROP_ACK);
            sleep(400);
            tg.startTone(ToneGenerator.TONE_PROP_BEEP);
            sleep(300);

            tg.release();

        }catch(java.lang.RuntimeException e){}


        activateAlarmManager(); // activate the next alarm based on the user specified interval

    }

//end of AlarmReceiver::onReceive
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::activateAlarmManager
//
// Sets the alarm manager to fire again after the number of minutes specified by the interval
// stored in the prefs file.
//
// The function alarmMgr.setExactAndAllowWhileIdle is used rather than alarmMgr.setRepeating as the
// latter is VERY inexact...+/-16 minutes for a 20 minute interval per testing. The former is
// exact and allows alarms even when the device is in low-power mode (Dozing) when unplugged and
// idle. There is no precise repeating alarm function.
//

    private void activateAlarmManager() {

        Calendar calendar = Calendar.getInstance();

        int interval = PrefsHandler.readIntFromPrefs("Interval for the Currently Active Alarm", 15);

        long startTime = calendar.getTimeInMillis() + interval * 60 * 1000;

        Context context = MainActivity.getAppContext();

        AlarmManager alarmMgr = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(context, AlarmReceiver.class);
        PendingIntent alarmIntent = PendingIntent.getBroadcast(context, 0, intent, 0);

        //set the first alarm -- receiver will schedule repeated alarm -- see notes above
        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startTime, alarmIntent);

    }

//end of SetDaytimeReminder::activateAlarmManager
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// AlarmReceiver::sleep
//

    protected void sleep(int pDuration) {

        try {
            Thread.sleep(pDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

//end of AlarmReceiver::sleep
//-----------------------------------------------------------------------------------------------


}// end of class AlarmReceiver
//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------


