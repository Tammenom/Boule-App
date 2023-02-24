package com.example.myapplication.DataClasses;

import java.util.ArrayList;

public class GameData {
    private static GameData OBJ =new GameData();
    private GameData() {
        System.out.println("Objekt gebildet GameData");
    }

    public static GameData getInstance() {
        return OBJ;
    }

    public int halloWorld = 42;

    public ArrayList<PlayRoundData> gameRounds = new ArrayList<PlayRoundData>();


}

