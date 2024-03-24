package com.robovikes.scoutingapp2024;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;

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
    //REMOVE LATER
    /*
    public static void createButtons(HashMap<Button, ScrollView> headerButtons, Context context, ArrayList<Robot> robotArray){
        ArrayList<SectionObject> sectionArray = new ArrayList<>();
        for (Map.Entry<Button, ScrollView> set : headerButtons.entrySet()){
            sectionArray.add(new SectionObject(set.getKey(),set.getValue(),context,robotArray));
        }
        for (SectionObject element : sectionArray){
            element.sectionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (SectionObject EME: sectionArray) EME.layout.setVisibility(View.GONE);
                    element.layout.setVisibility(View.VISIBLE);
                }
            });
            element.layout.setVisibility(View.GONE);
        }
        sectionArray.get(0).layout.setVisibility(View.VISIBLE);
    }
     */
    //@parameters 1=ampScore,2=speakerScore,3=defenseScore,4=consistencyScore,5=scoringScore

    public static ArrayList<Robot> sortByValue(ArrayList<Robot> robotsArray,int comparison){
        ArrayList<Robot> returnedArray = new ArrayList<>(robotsArray);
        int n = returnedArray.size();
        for (int i = 0; i < n - 1; i++){
            for (int j = 0; j < n -1;j++){
                double compareValue = returnedArray.get(j).compare(returnedArray.get(j+1),comparison);
                if (compareValue<0){
                    Robot temp = returnedArray.get(j);
                    returnedArray.set(j,returnedArray.get(j+1));
                    returnedArray.set(j+1,temp);
                }
            }
        }
        return returnedArray;
    }
}
