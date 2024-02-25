package com.robovikes.scoutingapp2024;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
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

        //auto
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

        Queue combo = new Queue();
        //auto
        AdditionButton AutoAmpsScored = new AdditionButton(findViewById(R.id.autoAmpsScoredPlus),findViewById(R.id.autoAmpsScoredMinus),findViewById(R.id.autoAmpsScored),"Auto: Notes in Amp Scored");
        AdditionButton AutoSpeakersScored = new AdditionButton(findViewById(R.id.autoSpeakerScoredPlus),findViewById(R.id.autoSpeakerScoredMinus),findViewById(R.id.autoSpeakerScored),"Auto: Notes in Speaker Scored");
        AdditionButton AutoShotsAttempted = new AdditionButton(findViewById(R.id.autoShotsAttemptedPlus),findViewById(R.id.autoShotsAttemptedMinus),findViewById(R.id.autoShotsAttempted),"Auto: Shots Attempted");

        AdditionButton[] additionButtons = {AutoAmpsScored,AutoSpeakersScored,AutoShotsAttempted};

        for (AdditionButton widget : additionButtons){
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

        //REMOVE LATER
        TextView testingTitle = findViewById(R.id.TESTINGTITLE);
        SectionButton[] sectionButtons = {PitSectionButton,AutoSectionButton,TeleopSection};
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

                    testingTitle.setText(String.join("-",combo.list));
                    if (Arrays.equals(new String[]{"1", "2", "3", "1", "1", "2", "1", "3", "1", "1"},combo.list)){
                        findViewById(R.id.cat).setVisibility(View.VISIBLE);
                    }
                }
            });

        }
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


        generateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on button press generate json code and show QR code as image
                //this is a mess and im sorry
                ArrayList<String[]> textMap = new ArrayList<String[]>();
                String csvFile = "";
                /*
                teamName = String.valueOf(TeamName.getText());
                for (AdditionButton thingy : ploos){
                    textMap.add(new String[]{String.valueOf(thingy.name)+",",String.valueOf(thingy.value)});
                }
                for (NoteData noot : noteArray){
                    textMap.add(new String[]{String.valueOf(noot.name)+",",String.valueOf(noot.state)});
                }

                 */

                QRCode.setVisibility(View.VISIBLE);
                //turn arraylist to string

                for (String[] row : textMap){
                    for (int i = 0; i < row.length; i++){
                        csvFile+=row[i];
                    }
                    csvFile += ",";
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