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
import android.widget.AdapterView;
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

public class SetDaytimeReminder extends AppCompatActivity implements
                                                            AdapterView.OnItemSelectedListener {


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private boolean ignoreSpinner = false;


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

        Spinner spinner = (Spinner) findViewById(R.id.repeatIntervalSpnr);
        spinner.setOnItemSelectedListener(this);

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

        initRepeatIntervalSpinner();

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
// SetDaytimeReminder::initRepeatIntervalSpinner
//
// Reads the enabled/disabled state from the prefs file and sets the radio buttons to match.
//
// Flag ignoreSpinner is set true as setting the selection will trigger the onItemSelected
// call when that is not wanted...the true flag causes the function to do nothing.
//

    private void initRepeatIntervalSpinner() {

        String interval = readStringFromPrefs("Daytime Alarm Repeat Interval Minutes", "15");

        Spinner spinner = (Spinner) findViewById(R.id.repeatIntervalSpnr);

        ignoreSpinner = true;

        spinner.setSelection(getIndexInIntervalSpinner(spinner, interval));

    }

//end of SetDaytimeReminder::initRepeatIntervalSpinner
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::getIndexInIntervalSpinner
//
// Finds the position in the array of choices for pSpinner of pString. Returns 0 if pString is not
// found in the array.
//

    private int getIndexInIntervalSpinner(Spinner pSpinner, String pString){

        for (int i=0;i<pSpinner.getCount();i++){
            if (pSpinner.getItemAtPosition(i).equals(pString)){
                return(i);
            }
        }

        return(0);

    }

//end of SetDaytimeReminder::getIndexInIntervalSpinner
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
// SetDaytimeReminder::readStringFromPrefs
//
// Reads a String value for pKey from the prefs file using pDefault as the default.
//
// A named prefs file is used to allow sharing between activities.
//

    private String readStringFromPrefs(String pKey, String pDefault) {

        return(getSharedPrefs().getString(pKey, pDefault));

    }

//end of SetDaytimeReminder::readStringFromPrefs
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
// SetDaytimeReminder::writeStringToPrefs
//
// Writes String pValue to the prefs file for key pKey.
//
// A named prefs file is used to allow sharing between activities.
//

    private void writeStringToPrefs(String pKey, String pValue) {

        SharedPreferences.Editor editor = getPrefsEditor();
        editor.putString(pKey, pValue);
        editor.commit();

    }

//end of SetDaytimeReminder::writeStringToPrefs
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

        int interval = getSelectedIntFromIntervalSpinner();

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
// SetDaytimeReminder::getSelectedStringFromIntervalSpinner
//
// Returns the selected value in the spinner as a String.
//

    private String getSelectedStringFromIntervalSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.repeatIntervalSpnr);

        return(spinner.getSelectedItem().toString());

    }

//end of SetDaytimeReminder::getSelectedStringFromIntervalSpinner
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::getSelectedIntFromIntervalSpinner
//
// Returns the selected value in the spinner as an int. Returns default of 15 if the value
// cannot be converted to an int for some reason.
//

    private int getSelectedIntFromIntervalSpinner() {

        String selection = getSelectedStringFromIntervalSpinner();

        int interval = 15; //default

        try {
            interval = Integer.parseInt(selection);
        } catch(NumberFormatException nfe) {
            System.out.println("Could not parse " + nfe);
        }

        return(interval);

    }

//end of SetDaytimeReminder::getSelectedValueFromIntervalSpinner
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

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::onItemSelected
//
// Handles user selection of the interval selection spinner.
//
// If the alarm is enabled, calls to enable it again which will override the previous alarm.
//
// If ignoreSpinner is true, nothing is done except resetting that flag. This allows the
// spinner's value to be initialized when the activity is opened without setting the alarm.
//

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        if (ignoreSpinner){ ignoreSpinner = false; return; }

        String interval = (String)parent.getItemAtPosition(pos);

        writeStringToPrefs("Daytime Alarm Repeat Interval Minutes", interval);

        if (((RadioButton)findViewById(R.id.enabledRadioBtn)).isChecked()){ enableAlarmManager(); }

    }

//end of SetDaytimeReminder::onItemSelected
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::onNothingSelected
//
// Handles user selection of the interval selection spinner.
//
// Callback method to be invoked when the selection disappears from this view. The selection can
// disappear for instance when touch is activated or when the adapter becomes empty.
//

    public void onNothingSelected(AdapterView<?> parent) {

    }

//end of SetDaytimeReminder::onNothingSelected
//-----------------------------------------------------------------------------------------------

}// end of class SetDaytimeReminder
//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------