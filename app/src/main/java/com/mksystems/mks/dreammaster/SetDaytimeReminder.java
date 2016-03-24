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

    private void setDaytimeAlarmStartTextView() {


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

    private void initEnabledDisabledRadioBtnState() {

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
// SetDaytimeReminder::getSharedPrefs
//
// Returns a SharedPreferences reference to the app's share preferences file.
//
// A named prefs file is used to allow sharing between activities.
//

    private SharedPreferences getSharedPrefs() {

        return (this.getSharedPreferences(
                            "com.mksystems.dreammaster.APP_PREFERENCES", Context.MODE_PRIVATE));

    }

//end of SetDaytimeReminder::getSharedPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::readLongFromPrefs
//
// Reads a long value for pKey from the prefs file using pDefault as the default.
//
// A named prefs file is used to allow sharing between activities.
//

    private long readLongFromPrefs(String pKey, long pDefault) {

        return(getSharedPrefs().getLong(pKey, pDefault));

    }

//end of SetDaytimeReminder::readLongFromPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::readIntFromPrefs
//
// Reads an int value for pKey from the prefs file using pDefault as the default.
//
// A named prefs file is used to allow sharing between activities.
//

    private int readIntFromPrefs(String pKey, int pDefault) {

        return(getSharedPrefs().getInt(pKey, pDefault));

    }

//end of SetDaytimeReminder::readIntFromPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::readBooleanFromPrefs
//
// Reads a boolean value for pKey from the prefs file using pDefault as the default.
//
// A named prefs file is used to allow sharing between activities.
//

    private boolean readBooleanFromPrefs(String pKey, boolean pDefault) {

        return(getSharedPrefs().getBoolean(pKey, pDefault));

    }

//end of SetDaytimeReminder::readBooleanFromPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::getPrefsEditor
//
// Returns an editor for the app's prefs file.
//
// A named prefs file is used to allow sharing between activities.
//

    private SharedPreferences.Editor getPrefsEditor() {

        SharedPreferences sharedPref = this.getSharedPreferences(
                "com.mksystems.dreammaster.APP_PREFERENCES", Context.MODE_PRIVATE);

        return(sharedPref.edit());

    }

//end of SetDaytimeReminder::getPrefsEditor
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::writeLongToPrefs
//
// Writes long pValue to the prefs file for key pKey.
//
// A named prefs file is used to allow sharing between activities.
//

    private void writeLongToPrefs(String pKey, long pValue) {

        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putLong(pKey, pValue);
        editor.commit();

    }

//end of SetDaytimeReminder::writeLongToPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::writeIntToPrefs
//
// Writes int pValue to the prefs file for key pKey.
//
// A named prefs file is used to allow sharing between activities.
//

    private void writeIntToPrefs(String pKey, int pValue) {

        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putInt(pKey, pValue);
        editor.commit();

    }

//end of SetDaytimeReminder::writeIntToPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::writeBooleanToPrefs
//
// Writes boolean pValue to the prefs file for key pKey.
//
// A named prefs file is used to allow sharing between activities.
//

    private void writeBooleanToPrefs(String pKey, boolean pValue) {

        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putBoolean(pKey, pValue);
        editor.commit();

    }

//end of SetDaytimeReminder::writeBooleanToPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::readStartTimeFromPrefs
//
// Reads the Daytime Reminder Alert start time from the prefs file and returns it as
// milliseconds in a long.
//
// A named prefs file is used to allow sharing between activities.
//

    private long readStartTimeFromPrefs() {

        return(readLongFromPrefs("Daytime Alarm Start Time", -1));

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

    private boolean readEnabledStateFromPrefs() {

        return(readBooleanFromPrefs("Daytime Alarm Enabled", false));

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

    private void writeEnabledStateToPrefs(boolean pEnabled) {

        writeBooleanToPrefs("Daytime Alarm Enabled", pEnabled);

    }

//end of SetDaytimeReminder::writeEnabledStateToPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::setDaytimeAlarmManager
//
// Sets the alarm manager to fire at the specified start time and then repeat at the specified
// repeat interval.
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

    private void enableAlarmManager() {

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

    private void cancelAlarmManager() {

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