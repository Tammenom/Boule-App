package com.example.myapplication.Models;

import android.util.Log;

import com.example.myapplication.DataClasses.GameData;
import com.example.myapplication.DataClasses.PlayRoundData;

public class GameRoundModule {

    GameData gameData = GameData.getInstance();
    public void NextGameRound(){
        gameData.gameRoundData = new PlayRoundData();

        switch(gameData.numPlayers){

            case 2:
                gameData.gameRoundData.boulesTeamOne = 3;
                gameData.gameRoundData.boulesTeamTwo = 3;
                gameData.gameRoundData.maxPlayers = 2;
                break;
            case 4:
                gameData.gameRoundData.maxPlayers = 4;
            case 6:
                gameData.gameRoundData.maxPlayers = 6;
                gameData.gameRoundData.boulesTeamOne = 6;
                gameData.gameRoundData.boulesTeamTwo = 6;
                break;
            default:
                Log.e("GameInfo","Set Boules per Team Error");
                break;
        }
        gameData.gameRoundData.playerTeams.add("neutral");
        for(int i =0; i < gameData.teamOnePlayers.size(); i++){
            gameData.gameRoundData.playerTeams.add("team 1");

            if (gameData.teamOnePlayers.get(i) != null){
                gameData.gameRoundData.playerTeams.add("team 2");
            }
        }

    }

    public void endGameRound(){
        if (gameData.gameRoundData.gameHasEnded)
            gameData.finishedGameRounds.add(gameData.gameRoundData);
    }

}
