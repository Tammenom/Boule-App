package com.example.myapplication.Models;

import android.util.Log;

import com.example.myapplication.DataClasses.GameData;
import com.example.myapplication.DataClasses.GameRoundData;

public class GameRoundManager {

    private GameData gameData = GameData.getInstance();

    public void UpdateCurentPlayer(){

        if (gameData.currentGameRound.currentPlayer < gameData.currentGameRound.playerNames.size() -1){
            gameData.currentGameRound.currentPlayer ++;
        }else{
            gameData.currentGameRound.currentPlayer = 1;
        }
    }

    public void CheckIfGameRoundHasEnded(){
        if(gameData.currentGameRound.boulesTeamOne <=0 && gameData.currentGameRound.boulesTeamTwo <=0 ){
            Log.d("Sensor-App", "Play Round has ended!");
            gameData.currentGameRound.gameHasEnded = true;
        }

    }

    //Reads the throws from the sorted ball throw lists and adds the score to the current Game Round Score.
    public void UpdateGameRoundScore(){
        if (gameData.currentGameRound.thrownBallsSorted != null && gameData.currentGameRound.thrownBallsSorted.size() >= 1){
            String firstBouleTeam = gameData.currentGameRound.thrownBallsSorted.get(0).ballTeam;
            int totalPoints = 0;
            for (int i = 0; i< gameData.currentGameRound.thrownBallsSorted.size(); i++){
                if ((gameData.currentGameRound.thrownBallsSorted.get(i).ballTeam == firstBouleTeam) && totalPoints <6){
                    totalPoints++;
                }else{
                    i = gameData.currentGameRound.thrownBallsSorted.size();
                }
            }
            switch(firstBouleTeam){
                case "neutral":

                    break;
                case "team 1":
                    gameData.currentGameRound.scoreTeamOne = totalPoints;
                    gameData.currentGameRound.scoreTeamTwo = 0;
                    break;
                case "team 2":
                    gameData.currentGameRound.scoreTeamTwo = totalPoints;
                    gameData.currentGameRound.scoreTeamOne = 0;
                    break;
                default:

                    break;
            }
        }
    }

    public void CalculateBoulsLeft(){

        switch(CheckForCurrentTeam()){
            case "neutral":

                break;
            case "team 1":
                gameData.currentGameRound.boulesTeamOne --;
                Log.d("Sensor-App", "Boules left Team 1: " + gameData.currentGameRound.boulesTeamOne);
                break;
            case "team 2":
                gameData.currentGameRound.boulesTeamTwo --;
                Log.d("Sensor-App", "Boules left Team 2: " + gameData.currentGameRound.boulesTeamTwo);
                break;
            default:

                break;
        }

    }
    public String CheckForCurrentTeam(){
        return gameData.currentGameRound.playerNames.get(gameData.currentGameRound.currentPlayer);
    }

    //Sets the Settings for the current Game Round (Boules per Team and Team-Members).
    public void NextGameRound(){
        gameData.currentGameRound = new GameRoundData();

        switch(gameData.numPlayers){

            case 2:
                gameData.currentGameRound.boulesTeamOne = 3;
                gameData.currentGameRound.boulesTeamTwo = 3;
                gameData.currentGameRound.maxPlayers = 2;
                break;
            case 4:
                gameData.currentGameRound.maxPlayers = 4;
                gameData.currentGameRound.boulesTeamOne = 6;
                gameData.currentGameRound.boulesTeamTwo = 6;
            case 6:
                gameData.currentGameRound.maxPlayers = 6;
                gameData.currentGameRound.boulesTeamOne = 6;
                gameData.currentGameRound.boulesTeamTwo = 6;
                break;
            default:
                Log.e("GameInfo","Set Boules per Team Error");
                break;
        }
        gameData.currentGameRound.playerNames.add("neutral");
        for(int i =0; i < gameData.teamOnePlayers.size(); i++){
            gameData.currentGameRound.playerNames.add("team 1");

            if (gameData.teamOnePlayers.get(i) != null){
                gameData.currentGameRound.playerNames.add("team 2");
            }
        }

    }

    //Add the current Gameround data to the finished Game Round list.
    public void AddCurrentGameRoundToFinishedGameRounds(){
        if (gameData.currentGameRound.gameHasEnded)
            gameData.finishedGameRounds.add(gameData.currentGameRound);
    }

}
