package com.robovikes.scoutingapp2024;

public class Queue {
    public int length;
    String[] list = new String[10];
    public void push(String element){
        String[] temp = list.clone();

        for (int i = 1; i < list.length;i++){
            list[i] = temp[i-1];
        }
        list[0] = element;
    }
}
