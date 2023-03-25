package com.example.myapplication.Models;

import android.util.Log;
import android.view.View;

import com.example.myapplication.Controller.GameController;
import com.example.myapplication.DataClasses.BallData;
import com.example.myapplication.DataClasses.GameData;
import com.example.myapplication.DataClasses.ThrowData;

public class GameModule {

    private static GameModule OBJ;
    private GameModule() {
        System.out.println("Objekt gebildet GameModel");
    }

    public static GameModule getInstance() {
        if (OBJ == null){
            OBJ = new GameModule();
        }

        return OBJ;
    }

    private GameData gameData;
    private GameSettingsModule gameSettings;
    private GameBallModule ballManager;
    private GameRoundModule gameRoundManager;
    private GameController gameController = GameController.getInstance();

    //Initializes the game withe the selected amount of Players
    public void InitializeGame(String newGameMode){
        gameData = GameData.getInstance();
        gameSettings = new GameSettingsModule();
        gameRoundManager = new GameRoundModule();
        ballManager = new GameBallModule();
        gameSettings.ResetGameSettings();
        gameSettings.SetGameSettingsToGameMode(newGameMode);
        UpdateCurrentGame();
    }

    public void UpdateCurrentGame(){
        gameSettings.UpdateGameData();
        if(CheckIfGameHasEnded()){
            SetNextButtonTextToGameWon();
        }
        SetGameOverviewTeamsList();
        SetGameOverviewRounds();
        SetGameOverviewPlayerPoints();
    }

    public void NextGameRound(){
        if(CheckIfGameHasEnded()){
            gameSettings.ResetGameSettings();
            SetNextButtonTextToDefault();
        }
        gameRoundManager.NextGameRound();
        UpdatePlayerTurnView();
        UpdateTeamBoulesLeftView();
    }

    public void endGameRound(){
        gameRoundManager.endGameRound();
        gameController.FinishGameRoundActivity();
        UpdateCurrentGame();
    }

    public void ExitGameOverview(){
        gameSettings.ResetGameSettings();
    }

    public void ThrowButtonClicked(ThrowData nThrowData ){
        if(!gameData.gameRoundData.gameHasEnded){
            ballManager.CalculateVelocity( nThrowData);
            gameSettings.UpdatePlayroundPoints();
            ballManager.CalculateBoulsLeft();
            ballManager.CheckIfPlayroundHasEnded();
            ballManager.UpdateCurentPlayer();
            UpdateTeamBoulesLeftView();
            UpdatePlayroundTeamPointsView();
            UpdatePlayerTurnView();
        }

    }

    public void SetNextButtonTextToGameWon(){
        if(gameData.team1Points >= gameData.pointsToWin){
            gameController.SetNextRoundButtonText("TEAM 1 HAS WON THE GAME!\nClick to start a new game.");
        }
        if(gameData.team2Points >= gameData.pointsToWin){
            gameController.SetNextRoundButtonText("TEAM 2 HAS WON THE GAME!\nClick to start a new game.");
        }
    }

    public void SetNextButtonTextToDefault(){
        {
            gameController.SetNextRoundButtonText("CLICK TO START A NEW ROUND.");
        }
    }

    public boolean CheckIfGameHasEnded(){
        if (gameData.team1Points >= gameData.pointsToWin || gameData.team2Points >= gameData.pointsToWin){
            return true;
        }else{
            return false;
        }
    }


    //Sets the Members of both Teams.
    public void SetGameOverviewTeamsList(){
        String team1Content = "";
        String team2Content = "";
        for (int i =0; i< gameData.teamOnePlayers.size(); i++){
                team1Content = team1Content + gameData.teamOnePlayers.get(i) + "\n";
            }
        for (int i =0; i< gameData.teamTwoPlayers.size(); i++){
            team2Content = team2Content + gameData.teamTwoPlayers.get(i) + "\n";
        }

        gameController.SetGameOverviewTeamPlayerList("Team 1", team1Content);
        gameController.SetGameOverviewTeamPlayerList("Team 2", team2Content);
    }

    public void UpdatePlayroundTeamPointsView(){
        gameController.SetPlayroundTeamPointsView("Team 1", gameData.gameRoundData.pointsTeamOne +" Points");
        gameController.SetPlayroundTeamPointsView("Team 2", gameData.gameRoundData.pointsTeamTwo +" Points");
    }

    public void UpdateTeamBoulesLeftView(){
        gameController.SetTeamBoulesLeftView("Team 1", gameData.gameRoundData.boulesTeamOne +" left.");
        gameController.SetTeamBoulesLeftView("Team 2", gameData.gameRoundData.boulesTeamTwo +" left.");
    }

    public void UpdatePlayerTurnView(){
        if(!gameData.gameRoundData.gameHasEnded){
            if (gameData.gameRoundData.currentPlayer == 0){
                gameController.SetPlayerTurnView("Throw the Jack!");
            }else{
                gameController.SetPlayerTurnView("PLAYER " + gameData.gameRoundData.currentPlayer + " TURN.");
            }
        }else{
            gameController.SetPlayerTurnView("GAME ROUND END");
        }
    }

    public void UpdateBouleFieldView(BallData nBallData){
        gameController.UpdateBouleFieldView(nBallData);
    }

    public void SetGameOverviewRounds(){
        String roundsTotal = Integer.toString(gameData.roundCount);
        String roundsListView = "";
        for(int i = 0; i < gameData.finishedGameRounds.size(); i++){
            roundsListView += Integer.toString(i +1)+"\n";
        }
        gameController.SetGameOverviewTotalRoundCount(roundsTotal);
        gameController.SetGameOverviewRoundCountList(roundsListView);
    }

    public void SetGameOverviewPlayerPoints(){
        int team1TotalPoints = 0;
        int team2TotalPoints = 0;
        String team1RoundPointsList = "";
        String team2RoundPointsList = "";

        for(int i = 0; i < gameData.team1RoundPoints.size(); i++){
            team1TotalPoints += gameData.team1RoundPoints.get(i);
            team1RoundPointsList += Integer.toString(gameData.team1RoundPoints.get(i))+"\n";
        }
        for(int i = 0; i < gameData.team2RoundPoints.size(); i++){
            team2TotalPoints += gameData.team2RoundPoints.get(i);
            team2RoundPointsList += Integer.toString(gameData.team2RoundPoints.get(i))+"\n";
        }
        gameController.SetGameOverviewTeamTotalPoints("Team 1", Integer.toString(team1TotalPoints));
        gameController.SetGameOverviewTeamTotalPoints("Team 2", Integer.toString(team2TotalPoints));

        gameController.SetGameOverviewTeamListPoints("Team 1", team1RoundPointsList);
        gameController.SetGameOverviewTeamListPoints("Team 2", team2RoundPointsList);
    }

    //Close the current Game
    public void EndGame(View view){



    }



}
