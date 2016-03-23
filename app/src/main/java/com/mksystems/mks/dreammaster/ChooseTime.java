package com.mksystems.mks.dreammaster;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TimePicker;

import java.util.Calendar;


//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------
// class ChooseTime
//

public class ChooseTime extends AppCompatActivity {


//-----------------------------------------------------------------------------------------------
// ChooseTime::onCreate
//

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_time);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

//end of MainActivity::onCreate
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// ChooseTime::onPause
//
// Nothing actually done here...the time is only saved if the user presses the "Set" button.
//
// Note that onStop is usually called AFTER the parent activity's onStart is called. If a
// value needs to be saved for use by the parent activity, do that in onPause as well since
// that function seems to be called BEFORE the parent's onStart. For good measure, do it in
// both function.
//

    @Override
    protected void onPause() {

        super.onPause();

    }

//end of MainActivity::onPause
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// ChooseTime::onStop
//
// Nothing actually done here...the time is only saved if the user presses the "Set" button.
//
// Note that onStop is usually called AFTER the parent activity's onStart is called. If a
// value needs to be saved for use by the parent activity, do that in onPause as well since
// that function seems to be called BEFORE the parent's onStart. For good measure, do it in
// both function.
//

    @Override
    protected void onStop() {

        super.onStop();

    }

//end of MainActivity::onStop
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// ChooseTime::setTimeAndGoToParentActivity
//
// Stores the selected time for use as the daytime reminder alert start time.
//
// Closes the activity and returns to the parent activity.
//

    public void setTimeAndGoToParentActivity(View v) {

        saveStartTimeToPrefs();

        NavUtils.navigateUpFromSameTask(this);

    }

//end of MainActivity::setTimeAndGoToParentActivity
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// ChooseTime::saveStartTimeToPrefs
//
// Saves the start time selected by the user on the TimePicker to the prefs file.
//
// A named prefs file is used to allow sharing between activities.
//

    protected void saveStartTimeToPrefs() {

        TimePicker timePicker = (TimePicker) findViewById(R.id.timePicker);

        int hour = timePicker.getHour();
        int minute = timePicker.getMinute();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        //if user has chosen tomorrow for the start date, add one day to the start time

        RadioButton tomorrowBtn = (RadioButton) findViewById(R.id.radioButtonTomorrow);

        if(tomorrowBtn.isChecked()) { calendar.roll(Calendar.DAY_OF_MONTH, true); }

        SharedPreferences sharedPref = this.getSharedPreferences(
                "com.mksystems.dreammaster.APP_PREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putLong("Daytime Alarm Start Time", calendar.getTimeInMillis());
        editor.commit();

    }

//end of MainActivity::saveStartTimeToPrefs
//-----------------------------------------------------------------------------------------------

}// end of class ChooseTime
//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------