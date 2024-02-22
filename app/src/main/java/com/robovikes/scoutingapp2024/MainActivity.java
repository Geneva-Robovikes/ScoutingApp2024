package com.robovikes.scoutingapp2024;

import android.content.DialogInterface;
import android.graphics.Bitmap;
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
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        //declaring edit texts
        Button generateQRCode = (Button)findViewById(R.id.generateQRCODE);
        EditText TeamName = (EditText)findViewById(R.id.editTeamName);
        CheckBox OnStage = (CheckBox)findViewById(R.id.onStageCheckbox);
        CheckBox ScoreTrap = (CheckBox)findViewById(R.id.scoreTrapCheckbox);
        //auto


        CheckBox Spotlight = (CheckBox) findViewById(R.id.spotlightCheckbox);
        //qr code
        ImageView QRCode = (ImageView) findViewById(R.id.QRCODE);

        //Auto speaker score
        TextView AutoSpeaker = (TextView) findViewById(R.id.autospeakerValue);
        Button AutoSpeakerPlus = (Button) findViewById(R.id.autospeakerPlus);
        Button AutoSpeakerMinus = (Button) findViewById(R.id.autospeakerMinus);
        AutoSpeakerPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoSpeakerScore += 1;
                AutoSpeaker.setText(String.valueOf(autoSpeakerScore));
            }
        });
        AutoSpeakerMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoSpeakerScore--;
                AutoSpeaker.setText(String.valueOf(autoSpeakerScore));
            }
        });
        //Auto Amp Score
        TextView AutoAmp = (TextView) findViewById(R.id.autoamp);
        Button AutoAmpPlus = (Button) findViewById(R.id.autoampPlus);
        Button AutoAmpMinus = (Button) findViewById(R.id.autoampMinus);
        AutoAmpPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoAmpScore++;
                AutoAmp.setText(String.valueOf(autoAmpScore));
            }
        });
        AutoAmpMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                autoAmpScore--;
                AutoAmp.setText(String.valueOf(autoAmpScore));
            }
        });
        //speaker
        TextView SpeakerScore = (TextView) findViewById(R.id.speakerValue);
        Button SpeakerMinus = (Button) findViewById(R.id.speakerMinus);
        Button SpeakerPlus = (Button) findViewById(R.id.speakerPlus);

        SpeakerPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerScore++;
                SpeakerScore.setText(String.valueOf(speakerScore));
            }
        });
        SpeakerMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakerScore--;
                SpeakerScore.setText(String.valueOf(speakerScore));
            }
        });

        //amp
        TextView AmpScore = (TextView) findViewById(R.id.ampValue);
        Button AmpMinus = (Button) findViewById(R.id.ampMinus);
        Button AmpPlus = (Button) findViewById(R.id.ampPlus);

        AmpPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ampScore++;
                AmpScore.setText(String.valueOf(ampScore));
            }
        });
        AmpMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ampScore--;
                AmpScore.setText(String.valueOf(ampScore));
            }
        });

        //shots attempted
        TextView ShotsAttempted = (TextView) findViewById(R.id.shotsAttemptedValue);
        Button ShotsAttemptedPlus = (Button) findViewById(R.id.shotsAttemptedPlus);
        Button ShotsAttemptedMinus = (Button) findViewById(R.id.shotsAttemptedMinus);

        ShotsAttemptedMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shotsAttempted--;
                ShotsAttempted.setText(String.valueOf(shotsAttempted));
            }
        });
        ShotsAttemptedPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shotsAttempted++;
                ShotsAttempted.setText(String.valueOf(shotsAttempted));
            }
        });
        //shots made

        TextView ShotsMade = (TextView) findViewById(R.id.shotsMadeValue);
        Button ShotsMadePlus = (Button) findViewById(R.id.shotsMadePlus);
        Button ShotsMadeMinus = (Button) findViewById(R.id.shotsMadeMinus);

        ShotsMadeMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shotsMade--;
                ShotsMade.setText(String.valueOf(shotsMade));
            }
        });
        ShotsMadePlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shotsMade++;
                ShotsMade.setText(String.valueOf(shotsMade));
            }
        });
        //map buttons
        ImageButton MapButtonToMap = (ImageButton) findViewById(R.id.mapButtonToMap);
        ImageButton MapButtonToMain = (ImageButton) findViewById(R.id.mapButtonToMain);

        MapButtonToMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.mainLayout).setVisibility(View.GONE);
                findViewById(R.id.mapLayout).setVisibility(View.VISIBLE);
            }
        });
        MapButtonToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.mainLayout).setVisibility(View.VISIBLE);
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

                teamName = String.valueOf(TeamName.getText());
                textMap.add(new String[]{"Team Name,", teamName});
                textMap.add(new String[]{"Auto Speaker Score,", String.valueOf(autoSpeakerScore)});
                textMap.add(new String[]{"Auto Amp Score,", String.valueOf(autoAmpScore)});
                textMap.add(new String[]{"Speaker Score", String.valueOf(speakerScore)});
                textMap.add(new String[]{"Amp Score:",String.valueOf(ampScore)});
                textMap.add(new String[]{"Shots Attempted:",String.valueOf(shotsAttempted)});
                textMap.add(new String[]{"Shots Made:",String.valueOf(shotsMade)});
                onStage = OnStage.isChecked();
                scoreTrap = ScoreTrap.isChecked();
                spotlight = Spotlight.isChecked();
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