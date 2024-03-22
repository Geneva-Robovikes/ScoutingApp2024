package com.robovikes.scoutingapp2024;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class QRCode {
    public static void generateQRCode(ArrayList<String[]> array, ImageView QRCODE){
        String csvFile = "";
        for (String[] row : array){
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
            QRCODE.setImageBitmap(mBitmap);//Setting generated QR code to imageView
        } catch (WriterException e) {
            e.printStackTrace();
        }
        QRCODE.setVisibility(View.VISIBLE);
    }
    public static void createButtons(HashMap<Button,LinearLayout> headerButtons){
        ArrayList<SectionObject> sectionArray = new ArrayList<>();
        for (Map.Entry<Button, LinearLayout> set : headerButtons.entrySet()){
            sectionArray.add(new SectionObject(set.getKey(),set.getValue()));
        }
        for (SectionObject element : sectionArray){
            element.sectionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (SectionObject EME: sectionArray) EME.layout.setVisibility(View.GONE);
                    element.layout.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}
