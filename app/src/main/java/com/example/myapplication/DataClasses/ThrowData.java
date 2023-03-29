package com.example.myapplication.DataClasses;

import java.util.ArrayList;

public class ThrowData {

    public float timeStampStart = 0;
    public float timeStampEnd = 0;
    public ArrayList<float []> throwVectors = new ArrayList<float []>();

    public ThrowData(float nTimeStampStart, float nTimeStampEnd, ArrayList<float []> nThrowVectors ){

        timeStampStart = nTimeStampStart;
        timeStampEnd = nTimeStampEnd;
        throwVectors = nThrowVectors;
    }
}

