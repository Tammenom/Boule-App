package com.example.myapplication.Controller;

import com.example.myapplication.DataClasses.BallData;
import com.example.myapplication.DataClasses.ThrowData;
import com.example.myapplication.GameOverviewActivity;
import com.example.myapplication.Models.GameModule;
import com.example.myapplication.GameRoundActivity;

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
    private GameRoundActivity gameRoundActivity;
    private GameModule gameModule;

    public void SetGameOverviewActivity(GameOverviewActivity nGameOverviewActivity){
       gameOverviewActivity = nGameOverviewActivity;
    }

    public void SetPlayRoundActivity(GameRoundActivity nGameRoundActivity){
        gameRoundActivity = nGameRoundActivity;
    }

    public void InitializeNewGame(String gameMode){
        gameModule = GameModule.getInstance();
        gameModule.InitializeGame(gameMode);
    }

    public void SetGameOverviewTeamPlayerList(String teamName, String listContent){gameOverviewActivity.SetTeamPlayerList(teamName, listContent);}
    public void SetGameOverviewTotalRoundCount( String content){gameOverviewActivity.SetGameOverviewTotalRoundCount(content);}
    public void SetGameOverviewRoundCountList( String listContent){gameOverviewActivity.SetGameOverviewRoundCountList(listContent);}
    public void SetGameOverviewTeamTotalPoints(String teamName, String content){gameOverviewActivity.SetGameOverviewTeamTotalPoints(teamName,content);}
    public void SetGameOverviewTeamListPoints(String teamName, String listContent){gameOverviewActivity.SetGameOverviewTeamListPoints(teamName,listContent);}

    public void UpdateBouleFieldView(BallData nBallDatas){gameRoundActivity.UpdateBouleFieldView(nBallDatas);}
    public void SetPlayroundTeamPointsView(String teamName, String listContent){gameRoundActivity.SetGameRoundTeamPointsView(teamName, listContent);}
    public void SetTeamBoulesLeftView(String teamName, String listContent){gameRoundActivity.SetTeamBoulesLeftView(teamName,listContent);}
    public void SetPlayerTurnView(String textPlayerTurn){gameRoundActivity.SetPlayerTurnView(textPlayerTurn);}

    public void FinishGameRoundActivity(){
        gameRoundActivity.finishActivity();
    }

    public void NextRound(){gameModule.NextGameRound();}
    public void EndGameRound(){
        gameModule.endGameRound();
    }
    public void ThrowButtonClicked(ThrowData nThrowData){
        gameModule.ThrowButtonClicked(nThrowData);
    }


}
