package com.mksystems.mks.dreammaster;

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

//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------
// class DaytimeAlarmReceiver
//
// This class is invoked when the daytime AlarmManager triggers. It will sound an alert to remind
// the user to perform a reality check.
//


public class DaytimeAlarmReceiver extends BroadcastReceiver

{

//-----------------------------------------------------------------------------------------------
// DaytimeAlarmReceiver::onReceive
//

    @Override
    public void onReceive(Context context, Intent intent)
    {

        ToneGenerator tg;

        try {

            //select tone with volume 100
            tg = new ToneGenerator(AudioManager.STREAM_NOTIFICATION, 100);

            tg.startTone(ToneGenerator.TONE_PROP_BEEP);
            sleep(100);
            tg.startTone(ToneGenerator.TONE_PROP_BEEP2);
            sleep(100);
            tg.startTone(ToneGenerator.TONE_PROP_BEEP);
            sleep(100);

            tg.release();

        }catch(java.lang.RuntimeException e){}
    }

//end of DaytimeAlarmReceiver::onReceive
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// DaytimeAlarmReceiver::sleep
//

    protected void sleep(int pDuration) {

        try {
            Thread.sleep(pDuration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

//end of DaytimeAlarmReceiver::sleep
//-----------------------------------------------------------------------------------------------


}// end of class DaytimeAlarmReceiver
//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------


