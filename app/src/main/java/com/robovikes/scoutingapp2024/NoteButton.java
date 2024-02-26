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
    public void changeState(){
        if (state == false){
            state = true;
            button.setImageResource(R.drawable.checked_circle_foreground);
        }
        else {
            state = false;
            button.setImageResource(R.drawable.empty_circle_foreground);
        }
    }

}
