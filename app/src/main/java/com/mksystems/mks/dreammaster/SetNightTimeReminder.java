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
// class SetNightReminder
//

public class SetNightTimeReminder extends AppCompatActivity implements
        AdapterView.OnItemSelectedListener {


    private AlarmManager alarmMgr;
    private PendingIntent alarmIntent;

    private boolean ignoreViewChanges1 = false;
    private boolean ignoreViewChanges2 = false;

    static final int SET_TIME_REQUEST = 1;

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::onCreate
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_night_time_reminder);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Spinner spinner;

        spinner = (Spinner) findViewById(R.id.startTimeSpnr);
        spinner.setOnItemSelectedListener(this);

        spinner = (Spinner) findViewById(R.id.repeatIntervalSpnr);
        spinner.setOnItemSelectedListener(this);

    }

//end of SetNightTimeReminder::onCreate
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::onStart
//

    @Override
    protected void onStart() {

        super.onStart();

        setAlarmStartTextView();

        initEnabledDisabledRadioBtnState();

        initRepeatIntervalSpinner();

        initStartDelaySpinner();

    }

//end of SetNightTimeReminder::onStart
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::setStartTimeFromCurrent
//
// The start time is set to the current time plus the selected delay and updated in the prefs file.
//
// The start time display is updated.
//

    private void setStartTimeFromCurrent() {

            long startTime = calculateAlarmTimeFromDelayChoice();

            PrefsHandler.writeLongToPrefs("Night Time Alarm Start Time", startTime);

            setAlarmStartTextView();

    }

//end of SetDaytimeReminder::setStartTimeFromCurrent
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::setAlarmStartTextView
//
// If the start time has been set, it is displayed in the view. If not set, then "not set" is
// displayed.
//

    private void setAlarmStartTextView() {

        TextView textView = (TextView) findViewById(R.id.starting_time);

        long startTime = readStartTimeFromPrefs();

        if(startTime == -1){
            textView.setText("Current start time: " + "not set");
        }else{
            SimpleDateFormat sdf = new SimpleDateFormat("MM-dd hh:mm a");
            textView.setText("Current start time: " + sdf.format(startTime));
        }

    }

//end of SetNightTimeReminder::setAlarmStartTextView
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::initRepeatIntervalSpinner
//
// Reads the repeat interval value from the prefs file and sets the spinner to match.
//
// Flag ignoreSpinner is set true as setting the selection will trigger the onItemSelected
// call when that is not wanted...the true flag causes the function to do nothing.
//

    private void initRepeatIntervalSpinner() {

        String interval =
                PrefsHandler.readStringFromPrefs("Night Time Alarm Repeat Interval Minutes", "15");

        Spinner spinner = (Spinner) findViewById(R.id.repeatIntervalSpnr);

        ignoreViewChanges1 = true;

        spinner.setSelection(getIndexInIntervalSpinner(spinner, interval));

    }

//end of SetNightTimeReminder::initRepeatIntervalSpinner
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::initStartDelaySpinner
//
// Reads the start delay value from the prefs file and sets the spinner to match.
//
// Flag ignoreSpinner is set true as setting the selection will trigger the onItemSelected
// call when that is not wanted...the true flag causes the function to do nothing.
//

    private void initStartDelaySpinner() {

        String value =
                PrefsHandler.readStringFromPrefs("Night Time Start Delay", "1 hour");

        Spinner spinner = (Spinner) findViewById(R.id.startTimeSpnr);

        ignoreViewChanges2 = true;

        spinner.setSelection(getIndexInIntervalSpinner(spinner, value));

    }

//end of SetNightTimeReminder::initStartDelaySpinner
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::getIndexInIntervalSpinner
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

//end of SetNightTimeReminder::getIndexInIntervalSpinner
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::initEnabledDisabledRadioBtnState
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

//end of SetNightTimeReminder::initEnabledDisabledRadioBtnState
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::readStartTimeFromPrefs
//
// Reads the Night Time Reminder Alert start time from the prefs file and returns it as
// milliseconds in a long.
//
// A named prefs file is used to allow sharing between activities.
//

    private long readStartTimeFromPrefs() {

        return(PrefsHandler.readLongFromPrefs("Night Time Alarm Start Time", -1));

    }

//end of SetNightTimeReminder::readStartTimeFromPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::readEnabledStateFromPrefs
//
// Reads the NightTime Reminder Alert enabled/disabled status from the prefs file and returns it as
// a boolean.
//
// A named prefs file is used to allow sharing between activities.
//

    private boolean readEnabledStateFromPrefs() {

        return(PrefsHandler.readBooleanFromPrefs("Night Time Alarm Enabled", false));

    }

//end of SetNightTimeReminder::readEnabledStateFromPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::writeEnabledStateToPrefs
//
// Writes the Night Time Reminder Alert enabled/disabled status to the prefs file per the state of
// pEnabled.
//
// If Night Time enabled, the Daytime Reminder Alert is disabled as only one can be enabled at
// any given time.
//
// A named prefs file is used to allow sharing between activities.
//

    private void writeEnabledStateToPrefs(boolean pEnabled) {

        PrefsHandler.writeBooleanToPrefs("Night Time Alarm Enabled", pEnabled);

        if(pEnabled) {
            PrefsHandler.writeBooleanToPrefs("Daytime Alarm Enabled", false);
        }

    }

//end of SetNightTimeReminder::writeEnabledStateToPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::setNightTimeAlarmManager
//
// Sets the alarm manager to fire at the specified start time and then repeat at the specified
// repeat interval.
//

    public void setNightTimeAlarmManager(View v) {

        Intent intent = new Intent(this, ChooseTime.class);
        startActivityForResult(intent, SET_TIME_REQUEST);

    }

//end of SetNightTimeReminder::setNightTimeAlarmManager
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
//SetNightTimeReminder::onActivityResult
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

//end of SetNightTimeReminder::onActivityResult
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::activateAlarmManager
//
// Sets the alarm manager to fire at the specified start time. The alarm broadcast receiver code
// will schedule the next alarm at the specified repeat interval.
//
// The function alarmMgr.setExactAndAllowWhileIdle is used rather than alarmMgr.setRepeating as the
// latter is VERY inexact...+/-16 minutes for a 20 minute interval per testing. The former is
// exact and allows alarms even when the device is in low-power mode (Dozing) when unplugged and
// idle. There is no precise repeating alarm function.
//
// If the start time is older than the current time or has never been set, the start time is set
// to the current time and updated in the prefs file.
//

    private void activateAlarmManager() {

        setStartTimeFromCurrent(); //update to start from current time plus user selected delay

        long startTime = readStartTimeFromPrefs();

        parseIntervalAndStoreInPrefs(); //store current interval value for use by the Receiver

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        //set the first alarm -- receiver will schedule repeated alarm -- see notes above
        alarmMgr.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, startTime, alarmIntent);

    }

//end of SetNightTimeReminder::activateAlarmManager
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetDaytimeReminder::parseIntervalAndStoreInPrefs
//
// Parses the alarm interval from the spinner selection and stores it in the prefs file.
//

    private void parseIntervalAndStoreInPrefs() {

        int interval = getSelectedIntFromIntervalSpinner();

        PrefsHandler.writeIntToPrefs("Interval for the Currently Active Alarm", interval);

    }

//end of SetDaytimeReminder::parseIntervalAndStoreInPrefs
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::activateAlarmManagerIfEnabled
//
// Activates the alarm if the user has enabled it.
//

    private void activateAlarmManagerIfEnabled() {

        if (((RadioButton) findViewById(R.id.enabledRadioBtn)).isChecked()) {
            activateAlarmManager();
        }

    }

//end of SetNightTimeReminder::activateAlarmManagerIfEnabled
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::getSelectedStringFromIntervalSpinner
//
// Returns the selected value in the spinner as a String.
//

    private String getSelectedStringFromIntervalSpinner() {

        Spinner spinner = (Spinner) findViewById(R.id.repeatIntervalSpnr);

        return(spinner.getSelectedItem().toString());

    }

//end of SetNightTimeReminder::getSelectedStringFromIntervalSpinner
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::getSelectedIntFromIntervalSpinner
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

//end of SetNightTimeReminder::getSelectedValueFromIntervalSpinner
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// NightTimeReminder::cancelAlarmManager
//
// Cancels any active alarm manager triggers.
//

    private void cancelAlarmManager() {

        alarmMgr = (AlarmManager)this.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlarmReceiver.class);
        alarmIntent = PendingIntent.getBroadcast(this, 0, intent, 0);

        alarmMgr.cancel(alarmIntent);

    }

//end of SetNightTimeReminder::cancelAlarmManager
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::handleEnabledDisabledRadioBtns
//
// Handles user selection of the Enable/Disable radio buttons. Enables or disables the NightTime
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

//end of SetNightTimeReminder::handleEnabledDisabledRadioBtns
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::onItemSelected
//
// Handles user selection of the start time or interval selection spinner.
//
// The selected values for both spinners are saved to the prefs file.
//
// The new alarm time is calculated and stored and the time display updated.
//
// If the alarm is enabled, calls to enable it again which will override the previous alarm.
//
// If ignoreViewChanges is true, nothing is done. This allows an view's value to be initialized when
// the activity is opened without triggering other actions.
//

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {

        if (ignoreViewChanges1){ ignoreViewChanges1 = false; return; }
        if (ignoreViewChanges2){ ignoreViewChanges2 = false; return; }

        Spinner spinner;

        spinner = (Spinner) findViewById(R.id.startTimeSpnr);

        String startDelay = (String)spinner.getSelectedItem();

        PrefsHandler.writeStringToPrefs("Night Time Start Delay", startDelay);

        spinner = (Spinner) findViewById(R.id.repeatIntervalSpnr);

        String interval = (String)spinner.getSelectedItem();

        PrefsHandler.writeStringToPrefs("Night Time Alarm Repeat Interval Minutes", interval);

        setStartTimeFromCurrent();

        activateAlarmManagerIfEnabled(); //force update of alarm if it is already enabled

    }

//end of SetNightTimeReminder::onItemSelected
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::onNothingSelected
//
// Handles user selection of the interval selection spinner.
//
// Callback method to be invoked when the selection disappears from this view. The selection can
// disappear for instance when touch is activated or when the adapter becomes empty.
//

    public void onNothingSelected(AdapterView<?> parent) {

    }

//end of SetNightTimeReminder::onNothingSelected
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::calculateAlarmTimeFromDelayChoice
//
// Calculates the alarm time based on the start time chosen by the user -- the start time
// the user selects is actually the delay from when the alarm is enabled.
//
// Returns the date and time in milliseconds.
//

    private long calculateAlarmTimeFromDelayChoice() {

        Calendar calendar = Calendar.getInstance();

        calendar.add(Calendar.HOUR, parseHourDelayFromSpinner());

        calendar.add(Calendar.MINUTE, parseMinuteDelayFromSpinner());

        return(calendar.getTimeInMillis());
    }

//end of SetNightTimeReminder::calculateAlarmTimeFromDelayChoice
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::parseHourDelayFromSpinner
//
// Parses the hour delay from the spinner text entry in the format of:
//          xx hours xx min
//
// Returns the value as an integer.
//

    private int parseHourDelayFromSpinner() {

        Spinner spinner;

        spinner = (Spinner) findViewById(R.id.startTimeSpnr);

        String startDelay = (String)spinner.getSelectedItem();

        //find the start/end points in the string of the hours text

        int value;
        int start = 0;
        int end = startDelay.indexOf(' ', 0); //find first space after hours entry

        try {
            value = Integer.parseInt(startDelay.substring(start,end));
        } catch(NumberFormatException nfe) {
            value = 0;
        }

        return(value);

    }

//end of SetNightTimeReminder::parseHourDelayFromSpinner
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// SetNightTimeReminder::parseMinuteDelayFromSpinner
//
// Parses the minute delay from the spinner text entry in the format of:
//          xx hours xx min
//
// Returns the value as an integer.
//

    private int parseMinuteDelayFromSpinner() {

        Spinner spinner;

        spinner = (Spinner) findViewById(R.id.startTimeSpnr);

        String startDelay = (String)spinner.getSelectedItem();

        //find the start/end points in the string of the hours text

        int value = 0;
        int start = 0, end = 0;

        start = startDelay.indexOf(' ', 0); //skip the first space after the hours entry
        start = startDelay.indexOf(' ', start+1); //find first space after minutes entry
        end = startDelay.indexOf(' ', start+1);

        //if second space not found, there is no minute entry
        if(start == -1 || end == -1) {
            return(0);
        }

        try {
            value = Integer.parseInt(startDelay.substring(start,end).trim());
        } catch(NumberFormatException nfe) {
            value = 0;
        }

        return(value);

    }

//end of SetNightTimeReminder::parseMinuteDelayFromSpinner
//-----------------------------------------------------------------------------------------------


}// end of class SetNightTimeReminder
//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------