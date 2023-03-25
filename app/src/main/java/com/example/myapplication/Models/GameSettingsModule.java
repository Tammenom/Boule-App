package com.example.myapplication.Models;

import android.util.Log;

import com.example.myapplication.DataClasses.GameData;

import java.util.ArrayList;

public class GameSettingsModule {

    GameData gameData = GameData.getInstance();

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

    public void UpdateGameData(){
        gameData.roundCount =0;
        gameData.team1Points =0;
        gameData.team2Points =0;
        gameData.team1RoundPoints.clear();
        gameData.team2RoundPoints.clear();
        for (int i =0; i< gameData.finishedGameRounds.size(); i++){
            gameData.team1RoundPoints.add(gameData.finishedGameRounds.get(i).pointsTeamOne);
            gameData.team2RoundPoints.add(gameData.finishedGameRounds.get(i).pointsTeamTwo);
            gameData.team1Points += gameData.finishedGameRounds.get(i).pointsTeamOne;
            gameData.team2Points += gameData.finishedGameRounds.get(i).pointsTeamTwo;
        }
        gameData.roundCount = (gameData.finishedGameRounds.size());
    }

    public void UpdatePlayroundPoints(){
        if (gameData.gameRoundData.thrownBallsSorted != null && gameData.gameRoundData.thrownBallsSorted.size() >= 1){
            String firstBouleTeam = gameData.gameRoundData.thrownBallsSorted.get(0).ballTeam;
            int totalPoints = 0;
            for (int i =0; i< gameData.gameRoundData.thrownBallsSorted.size(); i++){
                if ((gameData.gameRoundData.thrownBallsSorted.get(i).ballTeam == firstBouleTeam) && totalPoints <4){
                    totalPoints++;
                }else{
                    i = gameData.gameRoundData.thrownBallsSorted.size();
                }
            }
            switch(firstBouleTeam){
                case "neutral":

                    break;
                case "team 1":
                    gameData.gameRoundData.pointsTeamOne = totalPoints;
                    gameData.gameRoundData.pointsTeamTwo = 0;
                    break;
                case "team 2":
                    gameData.gameRoundData.pointsTeamTwo = totalPoints;
                    gameData.gameRoundData.pointsTeamOne = 0;
                    break;
                default:

                    break;
            }
        }
    }

    public void ResetGameSettings(){
        gameData.roundCount =0;
        gameData.team1Points = 0;
        gameData.team2Points = 0;
        gameData.team1RoundPoints = new ArrayList<>();
        gameData.team2RoundPoints = new ArrayList<>();
    }

}
