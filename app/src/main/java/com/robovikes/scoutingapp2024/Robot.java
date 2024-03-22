package com.robovikes.scoutingapp2024;

public class Robot {
    /*
    * REQUIREMENTS:
    * Sort robots by
    *   -Amp Score
    *   -Speaker Score
    *   -Defense
    *   -Scoring Consistency
    *   -Support
     */
    //points designates points made in a match, score represents how a robot is valued in that field
    public String robotName;
    public int defenseScore;
    public int cycleTime;
    public int[] ampPoints;
    public int[] speakerPoints;

    //ranking points
    public double ampScore;
    public double speakerScore;

    public Robot(int[] ampPoints, int[] speakerPoints){
        this.ampPoints = ampPoints;
        this.speakerPoints = speakerPoints;
        ampScore = averageBest(ampPoints);
        speakerScore = averageBest(speakerPoints);
    }
    // Condition:
    // @return average of top three results from array
    // average of top three scores are used to find a good measure of robot performance when they try
    public double averageBest(int[] array){
        int[] newArray = sort(array);
        int sum = 0;
        if (newArray.length > 3){
            for (int i = newArray.length-1;i>newArray.length-4;i--){
                sum += newArray[i];
            }
            return ((double)sum/3);
        }
        else{
            for (int i = 0; i < newArray.length;i++){
                sum += newArray[i];
            }
            return ((double) sum) / newArray.length;
        }
    }
    //sorts using bubble sort
    //if this doesn't work then complain to www.geeksforgeeks.org because its copied from there
    public int[] sort(int[] array){
        int[] returnedArray = array.clone();
        int n = returnedArray.length;
        for (int i = 0; i < n -1; i++){
            for (int j = 0; j < n -1;j++){
                if (returnedArray[j] > returnedArray[j+1]){
                    int temp = returnedArray[j];
                    returnedArray[j] = returnedArray[j+1];
                    returnedArray[j+1] = temp;
                }
            }
        }
        return returnedArray;
    }
}
