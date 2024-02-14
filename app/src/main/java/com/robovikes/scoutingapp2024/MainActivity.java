package com.robovikes.scoutingapp2024;

import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {
    //declaration statements

    //information
    //scoring type
    //robot build
    //input type
    //accuracy per shot
    //mechanical problems
    //driveability
    //change auto to integers
    //accuracy
    //park
    //auto speciality
    //auto pathing to notes (which ones?) for 8 notes
    //assign positions

    //up and down arrows

    //OVERALL
    //Average points
    //Standard deviation of points
    String teamName;
    boolean auto_canLeaveStartingZone = false;
    boolean auto_canShootNoteintoSpeaker = false;
    boolean auto_canShootintoAmp = false;
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
        EditText TeamName = (EditText)findViewById(R.id.editTeamName);
        EditText SpeakerScore = (EditText) findViewById(R.id.editSpeakerScore);
        EditText AmpScore = (EditText)findViewById(R.id.editAmpScore);
        CheckBox OnStage = (CheckBox)findViewById(R.id.onStageCheckbox);
        CheckBox ScoreTrap = (CheckBox)findViewById(R.id.scoreTrapCheckbox);
        //auto
        CheckBox AutoSpeaker = (CheckBox) findViewById(R.id.autospeakerCheckbox);
        CheckBox AutoAmp = (CheckBox) findViewById(R.id.autoampCheckbox);
        CheckBox Spotlight = (CheckBox) findViewById(R.id.spotlightCheckbox);
        //qr code
        ImageView QRCode = (ImageView) findViewById(R.id.QRCODE);

        generateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on button press generate json code and show QR code as image
                //this is a mess and im sorry

                if (String.valueOf(SpeakerScore.getText()).equals("") || String.valueOf(AmpScore.getText()).equals("") || String.valueOf(TeamName.getText()).equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    builder.setTitle("Error");
                    builder.setMessage("QR Code can't be generated without data");
                    builder.setCancelable(true);
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
                else{
                    teamName = String.valueOf(TeamName.getText());
                    //auto_canLeaveStartingZone = AutoLeave.isChecked();
                    auto_canShootNoteintoSpeaker = AutoSpeaker.isChecked();
                    auto_canShootintoAmp = AutoAmp.isChecked();
                    speakerScore = Integer.parseInt(String.valueOf(SpeakerScore.getText())); //this conversion is stupid and i don't understand it
                    ampScore = Integer.parseInt(String.valueOf(AmpScore.getText())); //why can't i just return a numbers only string as an int?
                    onStage = OnStage.isChecked();
                    scoreTrap = ScoreTrap.isChecked();
                    spotlight = Spotlight.isChecked();
                    QRCode.setVisibility(View.VISIBLE);
                    //generate qr code
                    String csvFile = "Team Name," + teamName +
                            "\nAuto: Can Leave Start," + auto_canLeaveStartingZone +
                            "\nAuto: Can Shoot in Speaker," + auto_canShootNoteintoSpeaker +
                            "\nSpeaker Score," + speakerScore +
                            "\nAmp Score," + ampScore +
                            "\nOn Stage," + onStage +
                            "\nScore Trap," + scoreTrap +
                            "\nSpotlight," + spotlight;
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