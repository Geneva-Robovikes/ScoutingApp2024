package com.robovikes.scoutingapp2024;

import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SectionObject {
    Button sectionButton;
    LinearLayout layout;

    public SectionObject(Button sectionButton, LinearLayout layout){
        this.sectionButton = sectionButton;
        this.layout = layout;

        TextView test = new TextView();
        test.setText("hey there!");
        this.layout.addView(test);
    }
}
