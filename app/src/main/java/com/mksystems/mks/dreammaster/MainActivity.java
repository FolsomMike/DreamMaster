package com.mksystems.mks.dreammaster;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;


//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------
// class MainActivity
//


public class MainActivity extends AppCompatActivity {


//-----------------------------------------------------------------------------------------------
// MainActivity::onCreate
//

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

// end of MainActivity::onCreate
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


}//end of class MainActivity
//-----------------------------------------------------------------------------------------------
//-----------------------------------------------------------------------------------------------
