package com.example.myapplication.DataClasses;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    
    List<Integer> roundsList;
    List<Integer> team1RoundPoints;
    List<Integer> team2RoundPoints;
    public ArrayList<PlayRoundData> finishedGameRounds = new ArrayList<PlayRoundData>();
    public ArrayList<String> teamOnePlayers = new ArrayList<String>();
    public ArrayList<String> teamTwoPlayers = new ArrayList<String>();

    public String gameMode;

    //Numer of Players in current Game
    public int numPlayers = 0;

    //Count of Rounds in current Game
    public int roundCount = 0;

    //Game Points of both Teams in current Game
    public int team1Points = 0;
    public int team2Points = 0;

    //Size of the depicted Game Field in current Game
    public int fieldSizeX;
    public int fieldSizeY;

    public int maxBoulesPerTeam = 3;

    public ArrayList<PlayRoundData> gameRounds = new ArrayList<PlayRoundData>();


}

