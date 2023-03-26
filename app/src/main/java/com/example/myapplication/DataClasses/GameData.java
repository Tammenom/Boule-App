package com.example.myapplication.DataClasses;

import java.util.ArrayList;

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

    //Game Points of both Teams in current Game
    public int scoreToWin = 13;
    public int team1TotalScore = 0;
    public int team2TotalScore = 0;
    public ArrayList<Integer> team1RoundScore = new ArrayList<Integer>();
    public ArrayList<Integer> team2RoundScore = new ArrayList<Integer>();


    public int numPlayers = 0;
    public ArrayList<String> teamOnePlayers = new ArrayList<String>();
    public ArrayList<String> teamTwoPlayers = new ArrayList<String>();
    public String lastChoosenGameMode;
    //Numer of Players in current Game

    //Count of Rounds in current Game
    public int roundCount = 0;
    public ArrayList<GameRoundData> finishedGameRounds = new ArrayList<GameRoundData>();
    public GameRoundData currentGameRound = new GameRoundData();

    //Size of the depicted Game Field in current Game
    public int fieldSizeX;
    public int fieldSizeY;
    //Boule Field View




}

