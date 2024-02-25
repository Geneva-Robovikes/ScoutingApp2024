package com.robovikes.scoutingapp2024;

import android.widget.Button;
import android.widget.ScrollView;

public class SectionButton {
    Button button;
    ScrollView toSection;
    ScrollView[] awaySection = new ScrollView[2];
    String id;
    public SectionButton(Button button, ScrollView toSection, ScrollView[] awaySection, String id){
        this.button = button;
        this.toSection = toSection;
        this.awaySection = awaySection;
        this.id = id;
    }
}
