package com.mksystems.mks.dreammaster;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.TimePicker;

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
// SetDaytimeReminder::onStart
//

    @Override
    protected void onStart() {

        super.onStart();

        setDaytimeAlarmStartTextView();

        initEnabledDisabledRadioBtnState();

    }

//end of SetDaytimeReminder::onStart
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::setDaytimeAlarmStartTextView
//
// If the start time has been set, it is displayed in the view. If not set, then "not set" is
// displayed.
//

    public void setDaytimeAlarmStartTextView() {


        TextView textView = (TextView) findViewById(R.id.starting_time);

        long startTime = readStartTimeFromPrefs();

        if(startTime == -1){
            textView.setText("Start time: " + "not set");
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm a");
            textView.setText("Start time: " + sdf.format(startTime));
        }

    }

//end of SetDaytimeReminder::setDaytimeAlarmStartTextView
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::initEnabledDisabledRadioBtnState
//
// Reads the enabled/disabled state from the prefs file and sets the radio buttons to match.
//

    public void initEnabledDisabledRadioBtnState() {

        boolean enabled = readEnabledStateFromPrefs();

        RadioButton radioBtn;

        if (enabled){
            radioBtn = (RadioButton) findViewById(R.id.enabledRadioBtn);
        }else{
            radioBtn = (RadioButton) findViewById(R.id.disabledRadioBtn);
        }

        radioBtn.setChecked(true);

    }

//end of SetDaytimeReminder::initEnabledDisabledRadioBtnState
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::readStartTimeFromPrefs
//
// Reads the Daytime Reminder Alert start time from the prefs file and returns it as
// milliseconds in a long.
//
// A named prefs file is used to allow sharing between activities.
//

    public long readStartTimeFromPrefs() {

        SharedPreferences sharedPref = this.getSharedPreferences(
                "com.mksystems.dreammaster.APP_PREFERENCES", Context.MODE_PRIVATE);

        return(sharedPref.getLong("Daytime Alarm Start Time", -1));

    }

//end of SetDaytimeReminder::readStartTimeFromPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::readEnabledStateFromPrefs
//
// Reads the Daytime Reminder Alert enabled/disabled status from the prefs file and returns it as
// a boolean.
//
// A named prefs file is used to allow sharing between activities.
//

    public boolean readEnabledStateFromPrefs() {

        SharedPreferences sharedPref = this.getSharedPreferences(
                "com.mksystems.dreammaster.APP_PREFERENCES", Context.MODE_PRIVATE);

        return(sharedPref.getBoolean("Daytime Alarm Enabled", false));

    }

//end of SetDaytimeReminder::readEnabledStateFromPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::writeEnabledStateToPrefs
//
// Writes the Daytime Reminder Alert enabled/disabled status to the prefs file per the state of
// pEnabled.
//
// A named prefs file is used to allow sharing between activities.
//

    public void writeEnabledStateToPrefs(boolean pEnabled) {

        SharedPreferences sharedPref = this.getSharedPreferences(
                "com.mksystems.dreammaster.APP_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("Daytime Alarm Enabled", pEnabled);
        editor.commit();

    }

//end of SetDaytimeReminder::writeEnabledStateToPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::setDaytimeAlarmManager
//
// Sets the alarm manager to fire at the specified start time and then repeat at the specified
// repeat interval.
//
// If the start time has never been set (= -1), no action is taken.
//

    public void setDaytimeAlarmManager(View v) {

        Intent intent = new Intent(this, ChooseTime.class);
        startActivity(intent);

    }

//end of SetDaytimeReminder::setDaytimeAlarmManager
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::enableAlarmManager
//
// Sets the alarm manager to fire at the specified start time and then repeat at the specified
// repeat interval.
//
// Saves the enabled state in the prefs file.
//

    public void enableAlarmManager() {

        long startTime = readStartTimeFromPrefs();

        if (startTime == -1) return;

        Spinner spinner = (Spinner) findViewById(R.id.repeatIntervalSpnr);

        String intervalSelection = spinner.getSelectedItem().toString();

        int interval = 15; //default

        try {
            interval = Integer.parseInt(intervalSelection);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DaytimeAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // set the alarm to repeat at the specified interval
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, startTime, 1000 * 60 * interval, alarmIntent);

        writeEnabledStateToPrefs(true);

    }

//end of SetDaytimeReminder::enableAlarmManager
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// DaytimeReminder::cancelAlarmManager
//
// Cancels any active alarm manager triggers.
//
// Saves the disabled state in the prefs file.
//

    public void cancelAlarmManager() {

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, DaytimeAlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmMgr.cancel(alarmIntent);

        writeEnabledStateToPrefs(false);

    }

//end of SetDaytimeReminder::cancelAlarmManager
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::handleEnableDisableRadioBtns
//
// Handles user selection of the Enable/Disable radio buttons. Enables or disables the daytime
// reminder alerts.
//

    public void handleEnableDisableRadioBtns(View view) {

        // is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // check which radio button was clicked

        switch(view.getId()) {
            case R.id.enabledRadioBtn:
                if (checked)
                    enableAlarmManager();
                    break;
            case R.id.disabledRadioBtn:
                if (checked)
                    cancelAlarmManager();
                    break;
        }
    }

//end of SetDaytimeReminder::handleEnableDisableRadioBtns
//-----------------------------------------------------------------------------------------------

}// end of class SetDaytimeReminder
//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------