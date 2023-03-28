package com.example.myapplication.Models;

import android.util.Log;

import com.example.myapplication.DataClasses.GameData;
import com.example.myapplication.DataClasses.GameRoundData;

public class GameRoundManager {

    private GameData gameData = GameData.getInstance();

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
