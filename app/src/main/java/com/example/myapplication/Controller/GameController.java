package com.example.myapplication.Controller;

import android.util.Log;

import com.example.myapplication.GameOverviewActivity;
import com.example.myapplication.Models.GameModel;

public class GameController {
    private static GameController OBJ;
    private  GameController() {
        System.out.println("Objekt gebildet GameController");
    }

    public static GameController getInstance() {
        if (OBJ == null){
            OBJ = new GameController();
        }
        return OBJ;
    }

    private GameOverviewActivity gameOverviewActivity;

    private GameModel model;

    public void SetGameOverviewActivity(GameOverviewActivity nGameOverviewActivity){
       gameOverviewActivity = nGameOverviewActivity;
    }

    public void InitializeNewGame(String gameMode){
        model= GameModel.getInstance();
        model.InitializeGame(gameMode);
        Log.d("App", "model.InitializeGame in GameController");
    }

    public void SetGameOverviewTeamPlayerList(String teamName, String listContent){gameOverviewActivity.SetTeamPlayerList(teamName, listContent);}
    public void SetGameOverviewTotalRoundCount( String content){gameOverviewActivity.SetGameOverviewTotalRoundCount(content);}
    public void SetGameOverviewRoundCountList( String listContent){gameOverviewActivity.SetGameOverviewRoundCountList(listContent);}
    public void SetGameOverviewTeamTotalPoints(String teamName, String content){gameOverviewActivity.SetGameOverviewTeamTotalPoints(teamName,content);}
    public void SetGameOverviewTeamListPoints(String teamName, String listContent){gameOverviewActivity.SetGameOverviewTeamListPoints(teamName,listContent);}

    public void NextRound(){model.NextGameRound();}





}
