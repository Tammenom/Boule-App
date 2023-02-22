package com.example.myapplication.Controller;

import com.example.myapplication.GameOverviewActivity;
import com.example.myapplication.Models.GameModel;

public class GameController {

    private GameOverviewActivity gameOverviewActivity;

    private GameModel model;

    public GameController(GameOverviewActivity nGameOverviewActivity){

        model = new GameModel(this);
       this.gameOverviewActivity = nGameOverviewActivity;
    }

    public void InitializeNewGame(String gameMode){model.InitializeGame(gameMode);
    }

    public void SetGameOverviewTeamPlayerList(String teamName, String listContent){this.gameOverviewActivity.SetTeamPlayerList(teamName, listContent);}
    public void SetGameOverviewTotalRoundCount( String content){this.gameOverviewActivity.SetGameOverviewTotalRoundCount(content);}
    public void SetGameOverviewRoundCountList( String listContent){this.gameOverviewActivity.SetGameOverviewRoundCountList(listContent);}
    public void SetGameOverviewTeamTotalPoints(String teamName, String content){this.gameOverviewActivity.SetGameOverviewTeamTotalPoints(teamName,content);}
    public void SetGameOverviewTeamListPoints(String teamName, String listContent){this.gameOverviewActivity.SetGameOverviewTeamListPoints(teamName,listContent);}

    public void NextRound(){model.NextGameRound();}





}
