package com.example.myapplication.DataClasses;

import java.util.ArrayList;

public class ThrowData {

    public float timeStampOne = 0;
    public float timeStampTwo = 0;
    public ArrayList<float []> throwVectors = new ArrayList<float []>();

    public ThrowData(float nTimeStampOne, float nTimeStampTwo, ArrayList<float []> nThrowVectors ){

        timeStampOne = nTimeStampOne;
        timeStampTwo = nTimeStampTwo;
        throwVectors = nThrowVectors;
        
    }
}

