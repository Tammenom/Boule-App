package com.example.myapplication.DataClasses;

import java.util.ArrayList;

public class GameData {
    private static final GameData OBJ =new GameData();
    private GameData() {
        System.out.println("Objekt gebildet...");
    }

    public static GameData getInstance() {
        return OBJ;
    }

    public int halloWorld = 42;

    public ArrayList<PlayRoundData> gameRounds = new ArrayList<PlayRoundData>();


}

