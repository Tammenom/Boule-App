package com.example.myapplication.Models;

import android.util.Log;

import com.example.myapplication.DataClasses.GameData;

public class SettingsManager {

    private GameData gameData = GameData.getInstance();

    public void SetBouleFieldSize(float sizeX, float sizeY){
        gameData.fieldSizeX = sizeX;
        gameData.fieldSizeY = sizeY;
    }



    //Sets the "Number of Player" variable in GameData. Calls the "SetPlayerList" function.
    public void SetGameSettingsToGameMode(String gMode){
        String gameMode = gMode;
        int numPlayers = 0;
        switch(gMode){

            case "1 vs.1":
                numPlayers= 2;
                break;
            case "2 vs.2":
                numPlayers= 4;
                break;
            case "3 vs.3":
                numPlayers= 6;
                break;
            default:

                break;
        }
        gameData.numPlayers = numPlayers;
        SetPlayerLists(numPlayers);
    }

    //Adds the Players to the Lists that contain all members of a team.
    public void SetPlayerLists(int nnumPlayers){
        boolean team1 = true;

        for (int i =0; i< nnumPlayers; i++){
            if (team1){
                gameData.teamOnePlayers.add( "Player "+ String.valueOf(i+1));
                team1 = false;

            }else{
                gameData.teamTwoPlayers.add( "Player "+ String.valueOf(i+1));
                team1 = true;
            }
        }
    }

    //Reads the "finishedGameRound" list and updates the variables for the total team-score and the score in each round.
    public void UpdateGameData(){
        gameData.roundCount =0;
        gameData.team1TotalScore =0;
        gameData.team2TotalScore =0;
        gameData.team1RoundScore.clear();
        gameData.team2RoundScore.clear();
        for (int i =0; i< gameData.finishedGameRounds.size(); i++){
            gameData.team1RoundScore.add(gameData.finishedGameRounds.get(i).scoreTeamOne);
            gameData.team2RoundScore.add(gameData.finishedGameRounds.get(i).scoreTeamTwo);
            gameData.team1TotalScore += gameData.finishedGameRounds.get(i).scoreTeamOne;
            gameData.team2TotalScore += gameData.finishedGameRounds.get(i).scoreTeamTwo;
        }
        gameData.roundCount = (gameData.finishedGameRounds.size());
    }



    //Resets the GameData
    public void ResetGame(){
        gameData.roundCount =0;
        gameData.team1TotalScore = 0;
        gameData.team2TotalScore = 0;
        gameData.team1RoundScore.clear();
        gameData.team2RoundScore.clear();
        gameData.numPlayers = 0;
        gameData.teamOnePlayers.clear();
        gameData.teamTwoPlayers.clear();
        gameData.finishedGameRounds.clear();

    }

}
