package com.example.myapplication.DataClasses;

import java.util.ArrayList;

public class GameRoundData {

    //All thrown Balls in this round in chronological order.
    public ArrayList<BallData> thrownBalls = new ArrayList<BallData>();
    //All thrown Balls sorted by their distance to the jack.
    public ArrayList<BallData> thrownBallsSorted = new ArrayList<BallData>();
    //All player names sorted by the order in which they are allowed to throw the boules.
    public ArrayList<String> playerNames = new ArrayList<String>();
    //If true, the gameRound has ended.
    public boolean gameHasEnded = false;
    //Scores of both teams in this GameRound
    public int scoreTeamOne = 0;
    public int scoreTeamTwo = 0;
    //The Boules left for each team in this GameRound
    public int boulesTeamOne = 0;
    public int boulesTeamTwo = 0;
    //The max amount of players partaking in this round.
    public int maxPlayers = 2;
    //The current Player who should throw a boule.
    public int currentPlayer = 0;
}
