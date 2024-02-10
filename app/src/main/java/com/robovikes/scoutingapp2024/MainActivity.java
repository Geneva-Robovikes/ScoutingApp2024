package com.robovikes.scoutingapp2024;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class MainActivity extends AppCompatActivity {
    //declaration statements
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
        CheckBox AutoLeave = (CheckBox) findViewById(R.id.autoleaveCheckbox);
        CheckBox Spotlight = (CheckBox) findViewById(R.id.spotlightCheckbox);
        //qr code
        ImageView QRCode = (ImageView) findViewById(R.id.QRCODE);

        generateQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on button press generate json code and show QR code as image
                //this is a mess and im sorry
                teamName = String.valueOf(TeamName.getText());
                auto_canLeaveStartingZone = AutoLeave.isChecked();
                auto_canShootNoteintoSpeaker = AutoSpeaker.isChecked();
                auto_canShootintoAmp = AutoAmp.isChecked();
                speakerScore = Integer.parseInt(String.valueOf(SpeakerScore.getText())); //this conversion is stupid and i don't understand it
                ampScore = Integer.parseInt(String.valueOf(AmpScore.getText())); //why can't i just return a numbers only string as an int?
                onStage = OnStage.isChecked();
                scoreTrap = ScoreTrap.isChecked();
                spotlight = Spotlight.isChecked();
                //generate qr code
                MultiFormatWriter mWriter = new MultiFormatWriter();
                try {
                    //BitMatrix class to encode entered text and set Width & Height
                    BitMatrix mMatrix = mWriter.encode(teamName, BarcodeFormat.QR_CODE, 400,400);
                    BarcodeEncoder mEncoder = new BarcodeEncoder();
                    Bitmap mBitmap = mEncoder.createBitmap(mMatrix);//creating bitmap of code
                    QRCode.setImageBitmap(mBitmap);//Setting generated QR code to imageView
                    } catch (WriterException e) {
                        e.printStackTrace();
                }
            }
        });
    }
}