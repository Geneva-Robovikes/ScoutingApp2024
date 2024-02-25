package com.robovikes.scoutingapp2024;

import android.widget.ImageButton;

public class NoteButton {
    public boolean state = false;
    public String name;
    public ImageButton button;
    public NoteButton(ImageButton button,String name){
        this.button = button;
        this.name = name;
    }

}
