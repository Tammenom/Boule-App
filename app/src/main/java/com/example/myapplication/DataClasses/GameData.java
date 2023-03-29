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

    //Score that is needed to win the game.
    public int scoreToWin = 13;
    //Total score of both Teams in current Game.
    public int team1TotalScore = 0;
    public int team2TotalScore = 0;
    //Lists containing the game-round-scores, for both Teams in current Game.
    public ArrayList<Integer> team1RoundScore = new ArrayList<Integer>();
    public ArrayList<Integer> team2RoundScore = new ArrayList<Integer>();

    //Numer of Players in current Game
    public int numPlayers = 0;

    //Lists containing the player names of both teams.
    public ArrayList<String> teamOnePlayers = new ArrayList<String>();
    public ArrayList<String> teamTwoPlayers = new ArrayList<String>();

    //Count of Rounds in current Game
    public int roundCount = 0;
    //List containing all finished game-rounds in the current game.
    public ArrayList<GameRoundData> finishedGameRounds = new ArrayList<GameRoundData>();
    //Containg all relevant data for current gameround.
    public GameRoundData currentGameRound = new GameRoundData();

    //Size of the depicted Game Field in current Game
    public float fieldSizeX = 0;
    public float fieldSizeY = 0;





}

