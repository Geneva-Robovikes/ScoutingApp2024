package com.robovikes.scoutingapp2024;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SectionObject {
    Button sectionButton;
    LinearLayout layout;

    public SectionObject(Button sectionButton, LinearLayout layout, Context context){
        this.sectionButton = sectionButton;
        this.layout = layout;

        TextView test = new TextView(context);
        test.setText("hey there!");
        this.layout.addView(context);
    }
}
