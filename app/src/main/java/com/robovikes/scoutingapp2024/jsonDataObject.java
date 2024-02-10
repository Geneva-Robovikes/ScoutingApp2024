package com.robovikes.scoutingapp2024;

//class that contains all the data, should be able to be written to json
public class jsonDataObject{
    private String teamName;
    private boolean auto_canLeaveStartingZone = false;
    private boolean auto_canShootNoteintoSpeaker = false;
    private boolean auto_canShootintoAmp = false;
    private int speakerScore = 0;
    private int ampScore = 0;
    private boolean onStage = false;
    private boolean scoreTrap = false;
    private boolean spotlight = false;

    public jsonDataObject(String team, boolean autoLeave, boolean autoSpeaker, boolean autoAmp, int speaker, int amp, boolean stage, boolean trap, boolean spot) {
        teamName = team;
        auto_canLeaveStartingZone = autoLeave;
        auto_canShootNoteintoSpeaker = autoSpeaker;
        auto_canShootintoAmp = autoAmp;
        speakerScore = speaker;
        ampScore = amp;
        onStage = stage;
        scoreTrap = trap;
        spotlight = spot;
    }
}
