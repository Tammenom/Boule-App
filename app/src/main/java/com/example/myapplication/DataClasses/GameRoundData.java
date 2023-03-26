package com.example.myapplication.DataClasses;

import java.util.ArrayList;

public class GameRoundData {

    public ArrayList<BallData> thrownBalls = new ArrayList<BallData>();
    public ArrayList<BallData> thrownBallsSorted = new ArrayList<BallData>();
    public ArrayList<String> playerTeams = new ArrayList<String>();
    public boolean gameHasEnded = false;

    public int scoreTeamOne = 0;
    public int scoreTeamTwo = 0;

    public int boulesTeamOne = 0;
    public int boulesTeamTwo = 0;

    public int maxPlayers = 2;
    public int currentPlayer = 0;
}
