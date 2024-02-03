package com.robovikes.scoutingapp2024;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.FileWriter;
import java.io.IOException;
import org.json.simple.JSONObject;

//Java doesn't seem to like this import statement
// import org.json.simple.JSONObject;
public class MainActivity extends AppCompatActivity {

    public void QRgen(){
        System.out.println("yipee!");
    }

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
        //declaring edit texts
        Button generateQRCode = (Button)findViewById(R.id.generateQRCODE);
        EditText teamName = (EditText)findViewById(R.id.editTeamName);
        EditText speakerScore = (EditText) findViewById(R.id.editSpeakerScore);
        EditText ampScore = (EditText)findViewById(R.id.editAmpScore);
        CheckBox onStage = (CheckBox)findViewById(R.id.onStageCheckbox);
        CheckBox scoreTrap = (CheckBox)findViewById(R.id.scoreTrapCheckbox);

        generateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("yipee!");
            }
        });
    }
}