package com.mksystems.mks.dreammaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


//-----------------------------------------------------------------------------------------------
// IndexActivity::displayUsingTheDreamLog
//
// Starts an activity to display dream log instructions.
//

    public void displayUsingTheDreamLog(View v) {

        Intent intent = new Intent(this, UsingTheDreamLog.class);

        startActivity(intent);

    }

//end of IndexActivity::displayUsingTheDreamLog
//-----------------------------------------------------------------------------------------------

//-----------------------------------------------------------------------------------------------
// IndexActivity::displayUsingTheLucidityAlerts
//
// Starts an activity to display lucidity alert instructions.
//

    public void displayUsingTheLucidityAlerts(View v) {

        Intent intent = new Intent(this, UsingTheLucidityAlerts.class);

        startActivity(intent);

    }

//end of IndexActivity::displayUsingTheLucidityAlerts
//-----------------------------------------------------------------------------------------------


}
