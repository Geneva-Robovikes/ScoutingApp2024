package com.robovikes.scoutingapp2024;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.icu.text.CaseMap;
import android.media.Image;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.Layout;
import android.text.TextWatcher;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Random;

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
    int shotsAttempted = 0;
    int shotsMade = 0;
    boolean auto_canLeaveStartingZone = false;
    boolean auto_canShootNoteintoSpeaker = false;
    boolean auto_canShootintoAmp = false;
    int speakerScore = 0;
    int ampScore = 0;
    boolean onStage = false;
    boolean scoreTrap = false;
    int cookies = 0;
    boolean spotlight = false;
    int autoSpeakerScore = 0;
    int autoAmpScore = 0;
    boolean cat = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //declaring edit texts
        Button generateQRCode = (Button)findViewById(R.id.generateQRCODE);
        EditText TeamName = (EditText)findViewById(R.id.editTeamName);
        //qr code
        ImageView QRCode = (ImageView) findViewById(R.id.QRCODE);
        Queue combo = new Queue();

        EditText RobotHeight = findViewById(R.id.robotHeight);
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
        AdditionButton ShotsMissed = new AdditionButton(findViewById(R.id.ShotsMissedPlus),findViewById(R.id.ShotsMissedMinus),findViewById(R.id.ShotsMissed),"Teleop: Shots Attempted");


        CheckBox CompletedClimb = findViewById(R.id.Climb);
        CheckBox CompletedTrap = findViewById(R.id.Trap);
        CheckBox BrokeDownFully = findViewById(R.id.BrokeDownFully);
        CheckBox BrokeDownRestarted = findViewById(R.id.BrokeDownRestarted);
        AdditionButton[] teleopAdditionButtons = {AmpsScored,SpeakersScored,ShotsMissed};

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

        //Note buttons
        NoteButton Mid1 = new NoteButton(findViewById(R.id.Mid1),"Mid 1");
        NoteButton Mid2 = new NoteButton(findViewById(R.id.Mid2),"Mid 2");
        NoteButton Mid3 = new NoteButton(findViewById(R.id.Mid3),"Mid 3");
        NoteButton Mid4 = new NoteButton(findViewById(R.id.Mid4),"Mid 4");
        NoteButton Mid5 = new NoteButton(findViewById(R.id.Mid5),"Mid 5");

        NoteButton Right1 = new NoteButton(findViewById(R.id.Right1),"Right 1");
        NoteButton Right2 = new NoteButton(findViewById(R.id.Right2),"Right 2");
        NoteButton Right3 = new NoteButton(findViewById(R.id.Right3),"Right 3");

        NoteButton[] noteButtons = {Mid1, Mid2, Mid3, Mid4, Mid5, Right1, Right2, Right3};

        for (NoteButton note : noteButtons){
            note.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    note.changeState();
                }
            });
        }

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

        MapButtonToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.dataLayout).setVisibility(View.GONE);
                findViewById(R.id.mapLayout).setVisibility(View.VISIBLE);
            }
        });

        MapButtonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.dataLayout).setVisibility(View.VISIBLE);
                findViewById(R.id.mapLayout).setVisibility(View.GONE);
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
        generateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on button press generate json code and show QR code as image
                //this is a mess and im sorry
                ArrayList<String[]> textMap = new ArrayList<String[]>();
                String csvFile = "";

                textMap.add(new String[]{"Team Name",String.valueOf(TeamName.getText())});
                textMap.add(new String[]{"Match #",String.valueOf(matchNumber.getText())});
                if (teamBlue.isChecked()) textMap.add(new String[]{"Team","Blue"});
                else textMap.add(new String[]{"Team","Red"});

                //auto section
                textMap.add(new String[]{"Robot Height",String.valueOf(RobotHeight.getText())});
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
                //auto section
                for (AdditionButton widget : autoAdditionButtons){
                    textMap.add(new String[]{String.valueOf(widget.name),String.valueOf(widget.value)});
                }
                textMap.add(new String[]{"Auto: Left Starting Area", String.valueOf(AutoLeftStartingArea.isChecked())});

                //teleop
                for (AdditionButton widget : teleopAdditionButtons){
                    textMap.add(new String[]{String.valueOf(widget.name),String.valueOf(widget.value)});
                }
                textMap.add(new String[]{"Teleop: Completed Climb", String.valueOf(CompletedClimb.isChecked())});
                textMap.add(new String[]{"Teleop: Completed Trap", String.valueOf(CompletedTrap.isChecked())});
                textMap.add(new String[]{"Teleop: Broke Down but Restarted", String.valueOf(BrokeDownRestarted.isChecked())});
                textMap.add(new String[]{"Teleop: Broke Down not restarted", String.valueOf(BrokeDownFully.isChecked())});

                //notes map
                for (NoteButton noot: noteButtons){
                    textMap.add(new String[]{String.valueOf(noot.name),String.valueOf(noot.state)});
                }

                QRCode.setVisibility(View.VISIBLE);
                //turn arraylist to string

                for (String[] row : textMap){
                    for (int i = 0; i < row.length; i++){
                        csvFile+=row[i];
                        csvFile+=",";
                    }
                    csvFile += "\n";
                }

                MultiFormatWriter mWriter = new MultiFormatWriter();
                try {
                    //BitMatrix class to encode entered text and set Width & Height
                    BitMatrix mMatrix = mWriter.encode(csvFile, BarcodeFormat.QR_CODE, 300, 300);
                    BarcodeEncoder mEncoder = new BarcodeEncoder();
                    Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
                    QRCode.setImageBitmap(mBitmap);//Setting generated QR code to imageView
                } catch (WriterException e) {
                    e.printStackTrace();
                }
            }
        });
        QRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                QRCode.setVisibility(View.INVISIBLE);
            }
        });
    }
}