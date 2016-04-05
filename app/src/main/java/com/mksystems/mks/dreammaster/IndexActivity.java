package com.mksystems.mks.dreammaster;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
// IndexActivity::displayHowToUseTheApp
//
// Starts an activity to display app instructions.
//

    public void displayHowToUseTheApp(View v) {

        Intent intent = new Intent(this, HowToUseTheApp.class);

        startActivity(intent);

    }

//end of IndexActivity::displayHowToUseTheApp
//-----------------------------------------------------------------------------------------------

}
