package com.example.rainbowapps.datacontroller01.Values;

public class ContentsData {

    public String mName;
    public String mTime;
    public String mPlay;
    public String mSize;
    public boolean isChecked;

    public ContentsData(String name, String time, String play, String size) {
        mName = name;
        mSize = size;
        mTime = time;
        mPlay = play;
        isChecked = true;
    }

    public ContentsData(String name, String time) {
        mName = name;
        mTime = time;
        isChecked = true;
    }

    public ContentsData(String name) {
        mName = name;
        isChecked = true;
    }

    public String getName() { return mName; }
    public String getTime() {
        return mTime;
    }
}
