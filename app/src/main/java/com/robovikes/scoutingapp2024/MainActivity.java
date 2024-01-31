package com.robovikes.scoutingapp2024;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//Java doesn't seem to like this import statement
// import org.json.simple.JSONObject;
public class MainActivity extends AppCompatActivity {
    //JSONObject JSONParser = new JSONObject();
    //autonomous
    boolean auto_canLeaveStartingZone = false;
    boolean auto_canShootNoteintoSpeaker = false;
    boolean auto_canShootintoAmp = false;
    //teleoperated values
    int speakerScore = 0;
    int ampScore = 0;
    boolean onStage = false;
    boolean scoreTrap = false;
    boolean spotlight = false;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}