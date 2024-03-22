package com.robovikes.scoutingapp2024;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TeamsContainer {
    ArrayList<SectionObject> sectionArray = new ArrayList<>();
    public TeamsContainer(HashMap<Button,LinearLayout> headerButtons){
        //create list of section buttons
        for (Map.Entry<Button,LinearLayout> set : headerButtons.entrySet()){
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
};