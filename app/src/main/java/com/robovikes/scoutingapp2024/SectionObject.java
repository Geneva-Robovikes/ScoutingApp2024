package com.robovikes.scoutingapp2024;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.zxing.RGBLuminanceSource;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SectionObject {
    Button sectionButton;
    ScrollView layout;
    TableLayout tableLayout;
    Context context;
    TableRow.LayoutParams leftParams;
    TableRow.LayoutParams rightParams;
    TableRow.LayoutParams tableRowParams;

    public SectionObject(Button sectionButton, ScrollView layout, Context context, ArrayList<Robot> robotArray,int comparison){
        this.sectionButton = sectionButton;
        this.layout = layout;
        this.context = context;
        tableLayout = new TableLayout(context);
        TableRow.LayoutParams tableLayoutParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,TableRow.LayoutParams.MATCH_PARENT);
        tableLayout.setLayoutParams(tableLayoutParams);

        tableLayout.setPadding(24,16,24,16);
        this.layout.addView(tableLayout);

        //params for stuck left and right right
        leftParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
        leftParams.weight = 1;
        leftParams.gravity = Gravity.LEFT;

        rightParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT,TableRow.LayoutParams.WRAP_CONTENT);
        rightParams.gravity = Gravity.RIGHT;

        //table row params
        tableRowParams = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT,100);
        //add header row
        String headerRight = "";
        if (comparison == 1) headerRight = "Highest Average Amp Score";
        if (comparison == 2) headerRight = "Highest Average Speaker Score";
        if (comparison == 3) headerRight = "Highest Average Defense Score";
        if (comparison == 4) headerRight = "Highest Average Consistency Score";
        if (comparison == 5) headerRight = "Highest Average Points Score";
        createRow("Team","Score"); //for some god damn reason it cuts off the right side if the left side is too long
        for (int i = 0; i < robotArray.size();i++){
            String value = "";
            //@parameters 1=ampScore,2=speakerScore,3=defenseScore,4=consistencyScore,5=scoringScore
            if (comparison == 1) value = String.valueOf(robotArray.get(i).ampScore);
            if (comparison == 2) value = String.valueOf(robotArray.get(i).speakerScore);
            if (comparison == 3) value = String.valueOf(robotArray.get(i).defenseScore);
            if (comparison == 4) value = String.valueOf(robotArray.get(i).consistencyScore);
            if (comparison == 5) value = String.valueOf(robotArray.get(i).scoringScore);

            createRow((i+1) + ". " + robotArray.get(i).teamName,value);
        }
    }
    public void createRow(String leftText, String rightText){
        TableRow tableRow = new TableRow(context);
        tableRow.setGravity(Gravity.CENTER_VERTICAL);
        tableRow.setPadding(8,8,8,8);
        TextView leftTextView = createTextView(leftText);
        leftTextView.setLayoutParams(leftParams);
        leftTextView.setText(leftText);


        TextView rightTextView = createTextView(rightText);
        rightTextView.setLayoutParams(rightParams);
        rightTextView.setText(rightText);


        tableRow.addView(leftTextView);
        tableRow.addView(rightTextView);

        tableLayout.addView(tableRow);
    }
    public TextView createTextView(String text){
        TextView newTextView = new TextView(context);
        newTextView.setText(text);
        newTextView.setTextSize(24);
        newTextView.setTextColor(Color.WHITE);
        return newTextView;
    }
}
