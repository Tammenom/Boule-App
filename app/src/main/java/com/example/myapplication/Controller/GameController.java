package com.example.myapplication.Controller;

import com.example.myapplication.DataClasses.BallData;
import com.example.myapplication.DataClasses.ThrowData;
import com.example.myapplication.Views.GameOverviewActivity;
import com.example.myapplication.Models.GameManager;
import com.example.myapplication.Views.GameRoundActivity;

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
    private GameManager gameManager;
    public void SetGameOverviewActivity(GameOverviewActivity nGameOverviewActivity){gameOverviewActivity = nGameOverviewActivity;}
    public void SetPlayRoundActivity(GameRoundActivity nGameRoundActivity){gameRoundActivity = nGameRoundActivity;}
    public void InitializeNewGame(String gameMode){gameManager = GameManager.getInstance();gameManager.InitializeGame(gameMode);}
    public void SetGameOverviewTeamPlayerList(String teamName, String listContent){gameOverviewActivity.SetTeamPlayerList(teamName, listContent);}
    public void SetGameOverviewTotalRoundCount( String content){gameOverviewActivity.SetGameOverviewTotalRoundCount(content);}
    public void SetGameOverviewRoundCountList( String listContent){gameOverviewActivity.SetGameOverviewRoundCountList(listContent);}
    public void SetGameOverviewTeamTotalPoints(String teamName, String content){gameOverviewActivity.SetGameOverviewTeamTotalPoints(teamName,content);}
    public void SetGameOverviewTeamListPoints(String teamName, String listContent){gameOverviewActivity.SetGameOverviewTeamListPoints(teamName,listContent);}
    public void SetBouleFieldView(BallData nBallDatas){gameRoundActivity.UpdateBouleFieldView(nBallDatas);}
    public void SetGameRoundTeamPointsView(String teamName, String listContent){gameRoundActivity.SetGameRoundTeamPointsView(teamName, listContent);}
    public void SetTeamBoulesLeftView(String teamName, String listContent){gameRoundActivity.SetTeamBoulesLeftView(teamName,listContent);}
    public void SetPlayerTurnView(String textPlayerTurn){gameRoundActivity.SetPlayerTurnView(textPlayerTurn);}
    public void SetNextRoundButtonText(String textGameWon) {gameOverviewActivity.SetNextButtonText(textGameWon);}
    public void FinishGameRoundActivity(){
        gameRoundActivity.finishActivity();
    }
    public void NextRound(){gameManager.NextGameRound();}
    public void EndGameRound(){
        gameManager.EndGameRound();
    }
    public void NewThrowEvent(ThrowData nThrowData){
        gameManager.NewThrow(nThrowData);
    }
    public void UpdateBouleFieldSize(float sizeX, float sizeY){gameManager.SetBouleFieldSize(sizeX,sizeY);}


}
