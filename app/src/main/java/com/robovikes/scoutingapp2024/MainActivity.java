package com.robovikes.scoutingapp2024;

import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Random;
import java.util.Scanner;

import pl.droidsonroids.gif.GifImageView;


public class MainActivity extends AppCompatActivity {
    //declaration statements

    //information
    //scoring type
    //robot build
    //input type
    //mechanical problems
    //driveability
    //park
    //auto speciality
    //auto pathing to notes (which ones?) for 8 notes
    //assign positions

    //up and down arrows

    //OVERALL
    //Average points
    //Standard deviation of points
    String teamName;
    String appView = "main";

    int cookies = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //declaring edit texts
        Button generateQRCode_pit = (Button)findViewById(R.id.generateQRCODE_pit);
        Button generateQRCode_match = (Button)findViewById(R.id.generateQRCODE_match);
        EditText TeamName = (EditText)findViewById(R.id.editTeamName);
        //qr code
        ImageView QRCodeImage = (ImageView) findViewById(R.id.QRCODE);
        Queue combo = new Queue();

        EditText RobotWidth = findViewById(R.id.robotWidth);
        EditText RobotWidthBumper = findViewById(R.id.robotWidthBumper);
        EditText RobotLength = findViewById(R.id.robotLength);
        EditText RobotLengthBumper = findViewById(R.id.robotLengthBumper);
        EditText CycleTime = findViewById(R.id.cycleTime);
        EditText DriveType = findViewById(R.id.driveType);
        CheckBox CanHangOnChain = findViewById(R.id.pitChain);
        CheckBox CanSpeaker = findViewById(R.id.pitSpeaker);
        CheckBox CanAmp = findViewById(R.id.pitAmp);
        CheckBox CanPickNotesGround = findViewById(R.id.pitGroundNotes);
        CheckBox CanPickNotesLoading = findViewById(R.id.pitLoadingNotes);

        //team
        RadioButton teamBlue = findViewById(R.id.blueteam);
        RadioButton teamRed = findViewById(R.id.redteam);

        //auto
        AdditionButton AutoAmpsScored = new AdditionButton(findViewById(R.id.autoAmpsScoredPlus),findViewById(R.id.autoAmpsScoredMinus),findViewById(R.id.autoAmpsScored),"Auto: Notes in Amp Scored");
        AdditionButton AutoSpeakersScored = new AdditionButton(findViewById(R.id.autoSpeakerScoredPlus),findViewById(R.id.autoSpeakerScoredMinus),findViewById(R.id.autoSpeakerScored),"Auto: Notes in Speaker Scored");
        AdditionButton[] autoAdditionButtons = {AutoAmpsScored,AutoSpeakersScored};
        CheckBox AutoLeftStartingArea = findViewById(R.id.autoLeftArea);

        for (AdditionButton widget : autoAdditionButtons){
            widget.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    widget.value++;
                    widget.text.setText(String.valueOf(widget.value));
                }
            });
            widget.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    widget.value--;
                    widget.text.setText(String.valueOf(widget.value));
                }
            });
        }
        //teleop
        AdditionButton AmpsScored = new AdditionButton(findViewById(R.id.AmpsScoredPlus),findViewById(R.id.AmpsScoredMinus),findViewById(R.id.AmpsScored),"Teleop: Notes in Amp Scored");
        AdditionButton SpeakersScored = new AdditionButton(findViewById(R.id.SpeakerScoredPlus),findViewById(R.id.SpeakerScoredMinus),findViewById(R.id.SpeakerScored),"Teleop: Notes in Speaker Scored");
        AdditionButton ShotsMissed = new AdditionButton(findViewById(R.id.ShotsMissedPlus),findViewById(R.id.ShotsMissedMinus),findViewById(R.id.ShotsMissed),"Teleop: Shots Missed");
        AdditionButton FeederShots = new AdditionButton(findViewById(R.id.FeederShotsPlus),findViewById(R.id.FeederShotsMinus),findViewById(R.id.FeederShotsView),"Teleop: Feeder Shots");


        CheckBox CompletedClimb = findViewById(R.id.Climb);
        CheckBox CompletedTrap = findViewById(R.id.Trap);
        CheckBox BrokeDownFully = findViewById(R.id.BrokeDownFully);
        CheckBox BrokeDownRestarted = findViewById(R.id.BrokeDownRestarted);
        AdditionButton[] teleopAdditionButtons = {AmpsScored,SpeakersScored,ShotsMissed,FeederShots};

        EditText matchNumber = findViewById(R.id.matchnumber);
        for (AdditionButton widget : teleopAdditionButtons){
            widget.plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    widget.value++;
                    widget.text.setText(String.valueOf(widget.value));
                }
            });
            widget.minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    widget.value--;
                    widget.text.setText(String.valueOf(widget.value));
                }
            });
        }


        //section buttons
        SectionButton PitSectionButton = new SectionButton(findViewById(R.id.toPitButton),findViewById(R.id.pitData), new ScrollView[]{findViewById(R.id.autoData), findViewById(R.id.teleopData)},"1");
        SectionButton AutoSectionButton = new SectionButton(findViewById(R.id.toAutoButton),findViewById(R.id.autoData), new ScrollView[]{findViewById(R.id.pitData), findViewById(R.id.teleopData)},"2");
        SectionButton TeleopSection = new SectionButton(findViewById(R.id.toTeleopButton),findViewById(R.id.teleopData), new ScrollView[]{findViewById(R.id.pitData), findViewById(R.id.autoData)},"3");


        SectionButton[] sectionButtons = {PitSectionButton,AutoSectionButton,TeleopSection};
        GifImageView AMONGUS = findViewById(R.id.amongus);
        AMONGUS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AMONGUS.setVisibility(View.GONE);
            }
        });
        for (SectionButton widget : sectionButtons){
            widget.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    widget.toSection.setVisibility(View.VISIBLE);
                    PitSectionButton.button.setTextColor(Color.BLACK);
                    AutoSectionButton.button.setTextColor(Color.BLACK);
                    TeleopSection.button.setTextColor(Color.BLACK);
                    widget.button.setTextColor(Color.WHITE);
                    for (ScrollView hide : widget.awaySection){
                        hide.setVisibility(View.GONE);
                    }
                    //the funny
                    combo.push(widget.id);
                    //TextView TITLE = findViewById(R.id.TESTINGTITLE);
                    //TITLE.setText(Arrays.toString(combo.list));
                    if (Arrays.equals(new String[]{"1", "2", "3", "1", "1", "2", "1", "3", "1", "1"},combo.list)){
                        findViewById(R.id.cat).setVisibility(View.VISIBLE);
                    }
                    if (Arrays.equals(new String[]{"1", "3", "3", "3", "2", "2", "1", "3", "2", "2"},combo.list)){
                        findViewById(R.id.dataLayout).setVisibility(View.GONE);
                        findViewById(R.id.cookie).setVisibility(View.VISIBLE);
                    }
                    if (new Random().nextInt(100) < 3) AMONGUS.setVisibility(View.VISIBLE);
                }
            });

        }
        ImageView FNAF = findViewById(R.id.fnaf);
        TeamName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (String.valueOf(TeamName.getText()).equals("1987")) {
                    FNAF.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        FNAF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FNAF.setVisibility(View.GONE);
            }
        });

        findViewById(R.id.cat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.cat).setVisibility(View.GONE);
            }
        });
        //map buttons
        ImageButton MapButtonToMap = (ImageButton) findViewById(R.id.mapButtonToMap);
        ImageButton MapButtonToMain = (ImageButton) findViewById(R.id.mapButtonToMain);
        ImageButton ButtonToTeams = (ImageButton) findViewById(R.id.buttonToTeams);
        MapButtonToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.dataLayout).setVisibility(View.GONE);
                findViewById(R.id.mapLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.teamsLayout).setVisibility(View.GONE);
            }
        });

        MapButtonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.dataLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.mapLayout).setVisibility(View.GONE);
                findViewById(R.id.teamsLayout).setVisibility(View.GONE);
            }
        });
        ButtonToTeams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.dataLayout).setVisibility(View.GONE);
                findViewById(R.id.mapLayout).setVisibility(View.GONE);
                findViewById(R.id.teamsLayout).setVisibility(View.VISIBLE);
            }
        });
        findViewById(R.id.TeamsToMain).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.dataLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.mapLayout).setVisibility(View.GONE);
                findViewById(R.id.teamsLayout).setVisibility(View.GONE);
            }
        });
        findViewById(R.id.leavecookie).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.dataLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.mapLayout).setVisibility(View.GONE);
                findViewById(R.id.cookie).setVisibility(View.GONE); //REMOVE LATER
            }
        });
        //extract data for robots
        ArrayList<Robot> robotArray = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(getAssets().open("ASRobots.txt")));
            String mLine;

            while ((mLine = reader.readLine()) != null) {
                String teamName = mLine.substring(10,mLine.indexOf(",Speaker Scores,"));
                String speakerScoresString = mLine.substring(mLine.indexOf("Speaker Scores,[")+"Speaker Scores,[".length(),mLine.indexOf(",Amp Scores")-1);
                String ampScoresString = mLine.substring(mLine.indexOf("Amp Scores,[")+"Amp Scores,[".length(),mLine.indexOf(",Defense,")-1);
                String defenseScoreString = mLine.substring(mLine.indexOf("Defense,[")+"Defense,[".length(),mLine.indexOf(",Auto Path,")-1);
                String autoPathString = mLine.substring(mLine.indexOf("Auto Path,[")+"Auto Path,[".length(),mLine.indexOf(",Scoring,")-1);
                String scoringString = mLine.substring(mLine.indexOf("Scoring,")+"Scoring,".length());

                //turn strings into arrays
                String[] speakerStringArray = speakerScoresString.split(", ");
                int[] speakerArray = new int[speakerStringArray.length];
                for (int i = 0; i < speakerStringArray.length;i++){
                    speakerArray[i] = Integer.parseInt(speakerStringArray[i]);
                }

                String[] ampStringArray = ampScoresString.split(", ");
                int[] ampArray = new int[ampStringArray.length];
                for (int i = 0; i < ampStringArray.length;i++){
                    ampArray[i] = Integer.parseInt(ampStringArray[i]);
                }

                String[] defenseStringArray = defenseScoreString.split(", ");
                int[] defenseArray = new int[defenseStringArray.length];
                for (int i = 0; i < defenseStringArray.length;i++){
                    defenseArray[i] = Integer.parseInt(defenseStringArray[i]);
                }

                String[] autoPathStringArray = autoPathString.split(", ");
                int[] autoPathArray = new int[autoPathStringArray.length];
                for (int i = 0; i < autoPathStringArray.length;i++){
                    autoPathArray[i] = Integer.parseInt(autoPathStringArray[i]);
                }

                double scoring = Double.parseDouble(scoringString);


                //String teamName,int[] ampPoints, int[] speakerPoints,int[] defensePoints,int[] autoPathPoints,int[] scoringPoints){
                robotArray.add(new Robot(teamName,ampArray,speakerArray,defenseArray,autoPathArray,scoring));
            }
            reader.close();
        } catch(IOException e){
            e.printStackTrace();
        }

        //team picking section
        HashMap<Button,ScrollView> teamMapHash = new HashMap<>();
        ArrayList<SectionObject> sectionObjectArrayList = new ArrayList<>();

        //@parameters 1=ampScore,2=speakerScore,3=defenseScore,4=consistencyScore,5=scoringScore
        QRCode.sortByValue(robotArray,1);
        sectionObjectArrayList.add(new SectionObject(findViewById(R.id.teamsAmpButton),findViewById(R.id.teamsAmpView),this,QRCode.sortByValue(robotArray,1),1));
        sectionObjectArrayList.add(new SectionObject(findViewById(R.id.teamsSpeakerButton),findViewById(R.id.teamsSpeakerView),this,QRCode.sortByValue(robotArray,2),2));
        sectionObjectArrayList.add(new SectionObject(findViewById(R.id.teamsDefenseButton),findViewById(R.id.teamsDefenseView),this,QRCode.sortByValue(robotArray,3),3));
        sectionObjectArrayList.add(new SectionObject(findViewById(R.id.teamsAutoButton),findViewById(R.id.teamsAutoView),this,QRCode.sortByValue(robotArray,4),4));
        sectionObjectArrayList.add(new SectionObject(findViewById(R.id.teamsScoringButton),findViewById(R.id.teamsScoringView),this,QRCode.sortByValue(robotArray,5),5));

        for (SectionObject element : sectionObjectArrayList){
            element.sectionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (SectionObject EME: sectionObjectArrayList) EME.layout.setVisibility(View.GONE);
                    element.layout.setVisibility(View.VISIBLE);
                }
            });
            element.layout.setVisibility(View.GONE);
        }
        sectionObjectArrayList.get(0).layout.setVisibility(View.VISIBLE);

        //rotate cookie
        RotateAnimation rotateRight = new RotateAnimation(0, 70,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);


        ImageView cookievike = findViewById(R.id.cookievike);
        TextView cookieText = findViewById(R.id.cookiesText);

        cookievike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RotateAnimation rotate = new RotateAnimation(0, new Random().nextInt(360),
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                        0.5f);
                rotate.setDuration(100);
                cookievike.setAnimation(rotate);

                cookievike.setVisibility(View.GONE);
                cookievike.setVisibility(View.VISIBLE);

                cookies++;
                cookieText.setText("COOKIES: " + cookies);
                if (cookies % 10 == 0){
                    cookievike.setScaleX((float) (cookievike.getScaleX()*1.02));
                    cookievike.setScaleY((float) (cookievike.getScaleY()*1.02));
                }
            }
        });
        //QR code buttons
        generateQRCode_pit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String[]> textMap = new ArrayList<String[]>();
                textMap.add(new String[]{"Team Name",String.valueOf(TeamName.getText())});
                textMap.add(new String[]{"Robot Width w/o bumper",String.valueOf(RobotWidth.getText())});
                textMap.add(new String[]{"Robot Width w/ bumper",String.valueOf(RobotWidthBumper.getText())});
                textMap.add(new String[]{"Robot Length w/o bumper",String.valueOf(RobotLengthBumper.getText())});
                textMap.add(new String[]{"Robot Length w/ bumper",String.valueOf(RobotLength.getText())});
                textMap.add(new String[]{"Cycle Time",String.valueOf(CycleTime.getText())});
                textMap.add(new String[]{"Drive Type",String.valueOf(DriveType.getText())});
                textMap.add(new String[]{"Can hang on chain",String.valueOf(CanHangOnChain.isChecked())});
                textMap.add(new String[]{"Can score in speaker",String.valueOf(CanSpeaker.isChecked())});
                textMap.add(new String[]{"Can score in amp",String.valueOf(CanAmp.isChecked())});
                textMap.add(new String[]{"Can pick up notes from ground",String.valueOf(CanPickNotesGround.isChecked())});
                textMap.add(new String[]{"Can pick up notes from loading",String.valueOf(CanPickNotesLoading.isChecked())});
                QRCode.generateQRCode(textMap,QRCodeImage);
            }
        });
        generateQRCode_match.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String[]> textMap = new ArrayList<String[]>();
                textMap.add(new String[]{"Team Name",String.valueOf(TeamName.getText())});
                textMap.add(new String[]{"Match #",String.valueOf(matchNumber.getText())});
                if (teamBlue.isChecked()) textMap.add(new String[]{"Team","Blue"});
                else textMap.add(new String[]{"Team","Red"});
                textMap.add(new String[]{"Auto: Left Starting",String.valueOf(AutoLeftStartingArea.isChecked())});

                //auto section
                for (AdditionButton widget : autoAdditionButtons){
                    textMap.add(new String[]{String.valueOf(widget.name),String.valueOf(widget.value)});
                }

                //teleop
                for (AdditionButton widget : teleopAdditionButtons){
                    textMap.add(new String[]{String.valueOf(widget.name),String.valueOf(widget.value)});
                }
                textMap.add(new String[]{"Teleop: Completed Climb", String.valueOf(CompletedClimb.isChecked())});
                textMap.add(new String[]{"Teleop: Completed Trap", String.valueOf(CompletedTrap.isChecked())});
                textMap.add(new String[]{"Teleop: Broke Down but Restarted", String.valueOf(BrokeDownRestarted.isChecked())});
                textMap.add(new String[]{"Teleop: Broke Down not restarted", String.valueOf(BrokeDownFully.isChecked())});
                EditText DefenseScoreView = findViewById(R.id.DefenseScore);
                textMap.add(new String[]{"Teleop: Defense Score", String.valueOf(String.valueOf(DefenseScoreView.getText()))});
                QRCode.generateQRCode(textMap,QRCodeImage);
            }
        });
        QRCodeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRCodeImage.setVisibility(View.INVISIBLE);
            }
        });
        findViewById(R.id.resetButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TeamName.setText("");
                matchNumber.setText("");
                RobotWidth.setText("");
                RobotWidthBumper.setText("");
                RobotLength.setText("");
                RobotLengthBumper.setText("");
                CycleTime.setText("");
                DriveType.setText("");
                EditText defense = findViewById(R.id.DefenseScore);
                defense.setText("");
                CanHangOnChain.setChecked(false);
                CanSpeaker.setChecked(false);
                CanAmp.setChecked(false);
                CanPickNotesGround.setChecked(false);
                CanPickNotesLoading.setChecked(false);
                //auto
                for (AdditionButton button : autoAdditionButtons){
                    button.value=0;
                    button.text.setText(String.valueOf(0));
                }
                AutoLeftStartingArea.setChecked(false);
                //teleop
                for (AdditionButton button : teleopAdditionButtons){
                    button.value=0;
                    button.text.setText(String.valueOf(0));
                }
                CompletedClimb.setChecked(false);
                CompletedTrap.setChecked(false);
                BrokeDownFully.setChecked(false);
                BrokeDownRestarted.setChecked(false);

            }
        });
    }
}