package com.example.myapplication.DataClasses;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private static GameData OBJ =new GameData();
    private GameData() {
        System.out.println("Objekt gebildet GameData");
    }

    public static GameData getInstance() {
        if (OBJ == null){
            OBJ = new GameData();
        }

        return OBJ;
    }


    public int roundCount = 0;

    public int pointsToWin = 13;
    public ArrayList<Integer> team1RoundPoints = new ArrayList<Integer>();
    public ArrayList<Integer> team2RoundPoints = new ArrayList<Integer>();
    public ArrayList<PlayRoundData> finishedGameRounds = new ArrayList<PlayRoundData>();
    public ArrayList<String> teamOnePlayers = new ArrayList<String>();
    public ArrayList<String> teamTwoPlayers = new ArrayList<String>();

    public String lastChoosenGameMode;

    //Numer of Players in current Game
    public int numPlayers = 0;

    //Count of Rounds in current Game


    //Game Points of both Teams in current Game
    public int team1Points = 0;
    public int team2Points = 0;

    //Size of the depicted Game Field in current Game
    public int fieldSizeX;
    public int fieldSizeY;

    public int maxBoulesPerTeam = 3;

    public ArrayList<PlayRoundData> gameRounds = new ArrayList<PlayRoundData>();

    //Boule Field View
    public PlayRoundData gameRoundData = new PlayRoundData();

    public  float [] ballInfo = new float[5];
    public  ArrayList<BallData> ballDatas = new ArrayList<BallData>();
    public boolean teamOneTurn = true;


}

