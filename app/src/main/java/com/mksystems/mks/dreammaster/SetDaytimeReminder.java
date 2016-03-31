package com.mksystems.mks.dreammaster;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

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

    static final int SET_TIME_REQUEST = 1;

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
// SetDaytimeReminder::setStartTimeToCurrentIfOlder
//
// If the start time is older than the current time or has never been set, the start time is set
// to the current time and updated in the prefs file.
//

    private void setStartTimeToCurrentIfOlder() {

        long startTime = readStartTimeFromPrefs();  // set start time

        Calendar calendar = Calendar.getInstance(); // get current time

        if (startTime < calendar.getTimeInMillis()){ //is start time older than current time?

            startTime = calendar.getTimeInMillis();

            PrefsHandler.writeLongToPrefs("Daytime Alarm Start Time", startTime);

            setDaytimeAlarmStartTextView();

        }

    }

//end of SetDaytimeReminder::setStartTimeToCurrentIfOlder
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

        String interval =
                PrefsHandler.readStringFromPrefs("Daytime Alarm Repeat Interval Minutes", "15");

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
// SetDaytimeReminder::readStartTimeFromPrefs
//
// Reads the Daytime Reminder Alert start time from the prefs file and returns it as
// milliseconds in a long.
//
// A named prefs file is used to allow sharing between activities.
//

    private long readStartTimeFromPrefs() {

        return(PrefsHandler.readLongFromPrefs("Daytime Alarm Start Time", -1));

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

        return(PrefsHandler.readBooleanFromPrefs("Daytime Alarm Enabled", false));

    }

//end of SetDaytimeReminder::readEnabledStateFromPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::writeEnabledStateToPrefs
//
// Writes the Daytime Reminder Alert enabled/disabled status to the prefs file per the state of
// pEnabled.
//
// If Daytime enabled, the Night Time Reminder Alert is disabled as only one can be enabled at any
// given time.
//
// A named prefs file is used to allow sharing between activities.
//

    private void writeEnabledStateToPrefs(boolean pEnabled) {

        PrefsHandler.writeBooleanToPrefs("Daytime Alarm Enabled", pEnabled);

        if(pEnabled) {
            PrefsHandler.writeBooleanToPrefs("Night Time Alarm Enabled", false);
        }

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
        startActivityForResult(intent, SET_TIME_REQUEST);

    }

//end of SetDaytimeReminder::setDaytimeAlarmManager
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
//SetDaytimeReminder::onActivityResult
//
// Handles callbacks from other activities.
//

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SET_TIME_REQUEST) {

            if (resultCode == RESULT_OK) {
                // set a new alarm if TimeChooser requests
                activateAlarmManagerIfEnabled();
            }
        }
    }

//end of SetDaytimeReminder::onActivityResult
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::activateAlarmManager
//
// Sets the alarm manager to fire at the specified start time and then repeat at the specified
// repeat interval.
//
// If the start time is older than the current time or has never been set, the start time is set
// to the current time and updated in the prefs file.
//

    private void activateAlarmManager() {

        long startTime = readStartTimeFromPrefs();

        setStartTimeToCurrentIfOlder(); //or if equals -1 in case i

        int interval = getSelectedIntFromIntervalSpinner();

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        // set the alarm to repeat at the specified interval
        alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP, startTime, 1000*60 * interval, alarmIntent);

    }

//end of SetDaytimeReminder::activateAlarmManager
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::activateAlarmManagerIfEnabled
//
// Activates the alarm if the user has enabled it.
//

    private void activateAlarmManagerIfEnabled() {

        if (((RadioButton) findViewById(R.id.enabledRadioBtn)).isChecked()) {
            activateAlarmManager();
        }

    }

//end of SetDaytimeReminder::activateAlarmManagerIfEnabled
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

    private void cancelAlarmManager() {

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmMgr.cancel(alarmIntent);

    }

//end of SetDaytimeReminder::cancelAlarmManager
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::handleEnabledDisabledRadioBtns
//
// Handles user selection of the Enable/Disable radio buttons. Enables or disables the daytime
// reminder alerts.
//

    public void handleEnabledDisabledRadioBtns(View view) {

        // is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // check which radio button was clicked

        switch(view.getId()) {
            case R.id.enabledRadioBtn:
                if (checked)
                    activateAlarmManager();
                    writeEnabledStateToPrefs(true);
                break;
            case R.id.disabledRadioBtn:
                if (checked)
                    cancelAlarmManager();
                    writeEnabledStateToPrefs(false);
                break;
        }
    }

//end of SetDaytimeReminder::handleEnabledDisabledRadioBtns
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

        PrefsHandler.writeStringToPrefs("Daytime Alarm Repeat Interval Minutes", interval);

        activateAlarmManagerIfEnabled();

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