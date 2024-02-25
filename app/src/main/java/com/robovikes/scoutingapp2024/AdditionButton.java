package com.robovikes.scoutingapp2024;

import android.widget.Button;
import android.widget.TextView;

public class AdditionButton {
    public Button minus;
    public Button plus;
    public int value = 0;
    public String name;
    public TextView text;
    public AdditionButton(Button plus, Button minus, TextView text, String name){
        this.minus = minus;
        this.plus = plus;
        this.text = text;
        this.name = name;
    }
}
