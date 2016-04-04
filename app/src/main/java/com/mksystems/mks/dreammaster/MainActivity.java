package com.mksystems.mks.dreammaster;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;


//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------
// class MainActivity
//


public class MainActivity extends AppCompatActivity {


    private static Context appContext;

//-----------------------------------------------------------------------------------------------
// MainActivity::onCreate
//

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appContext = getApplicationContext();

    }

// end of MainActivity::onCreate
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// MainActivity::onStart
//

    @Override
    protected void onStart() {

        super.onStart();

        setEnabledIndicators();

    }

//end of MainActivity::onStart
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// MainActivity::setEnabledIndicators
//
// Sets the text views for various options to Enabled or blank depending on the state of the
// corresponding value in the prefs file.
//


    private void setEnabledIndicators() {


        TextView textView;

        textView = (TextView) findViewById(R.id.daytimeEnabledTextView);

        if (PrefsHandler.readBooleanFromPrefs("Daytime Alarm Enabled", false)){
            textView.setText("enabled");
        }else{
            textView.setText("");
        }

        textView = (TextView) findViewById(R.id.nightTimeEnabledTextView);

        if (PrefsHandler.readBooleanFromPrefs("Night Time Alarm Enabled", false)){
            textView.setText("enabled");
        }else{
            textView.setText("");
        }

    }

//end of MainActivity::setEnabledIndicators
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// MainActivity::setDaytimeReminder
//
// Starts an activity to allow user to set the start time, stop time, and interval for the daytime
// reminder.
//

    public void setDaytimeReminder(View v) {

        Intent intent = new Intent(this, SetDaytimeReminder.class);

       //tutorial example of getting a pointer to a EditText object using its resource ID
       // and then retrieving its text contents and adding them to the intent for transmission
       // to the activity to be started:
       // EditText editText = (EditText) findViewById(R.id.edit_message);
       // String message = editText.getText().toString();
       // intent.putExtra(EXTRA_MESSAGE, message);
       // At the top of this class, define the key EXTRA_MESSAGE similar to:
       // public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";

        startActivity(intent);

    }

//end of MainActivity::setDaytimeReminder
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// MainActivity::setNightTimeReminder
//
// Starts an activity to allow user to set the start time, stop time, and interval for the night
// time reminder.
//

    public void setNightTimeReminder(View v) {

        Intent intent = new Intent(this, SetNightTimeReminder.class);

        startActivity(intent);

    }

//end of MainActivity::setNightTimeReminder
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// MainActivity::displayInstructions
//
// Starts an activity to display app instructions and the Dream Guide.
//

    public void displayInstructions(View v) {

        Intent intent = new Intent(this, IndexActivity.class);

        startActivity(intent);

    }

//end of MainActivity::displayInstructions
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// MainActivity::testAlarmReceiver
//

    public void testAlarmReceiver(View v) {

        Intent intent = new Intent(this, AlarmReceiver.class);

        startActivity(intent);

    }

//end of MainActivity::testAlarmReceiver
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// MainActivity::getAppContext
//
// Returns the context for the Main Activity.
//

    public static Context getAppContext () {

        return(appContext);

    }

// end of MainActivity::getAppContext
//-----------------------------------------------------------------------------------------------

}//end of class MainActivity
//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------
