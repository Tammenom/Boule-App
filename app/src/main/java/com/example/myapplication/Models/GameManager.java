package com.example.myapplication.Models;

import android.view.View;

import com.example.myapplication.Controller.GameController;
import com.example.myapplication.DataClasses.BallData;
import com.example.myapplication.DataClasses.GameData;
import com.example.myapplication.DataClasses.ThrowData;

public class GameManager {

    private static GameManager OBJ;
    private GameManager() {
        System.out.println("Objekt gebildet GameModel");
    }

    public static GameManager getInstance() {
        if (OBJ == null){
            OBJ = new GameManager();
        }

        return OBJ;
    }

    private GameData gameData;
    private SettingsManager settingsManager;
    private GameLogic gameLogic;
    private GameRoundManager gameRoundManager;
    private GameController gameController = GameController.getInstance();

    //Initializes the game withe the selected amount of Players
    public void InitializeGame(String newGameMode){
        gameData = GameData.getInstance();
        settingsManager = new SettingsManager();
        gameRoundManager = new GameRoundManager();
        gameLogic = new GameLogic();
        settingsManager.ResetGame();
        settingsManager.SetGameSettingsToGameMode(newGameMode);
        UpdateCurrentGame();
    }

    public void UpdateCurrentGame(){
        settingsManager.UpdateGameData();
        if(CheckIfGameHasEnded()){
            SetNextButtonTextToGameWon();
        }
        SetGameOverviewTeamsList();
        SetGameOverviewRounds();
        SetGameOverviewPlayerPoints();
    }

    public void NextGameRound(){
        if(CheckIfGameHasEnded()){
            settingsManager.ResetGame();
            SetNextButtonTextToDefault();
        }
        gameRoundManager.NextGameRound();
        UpdatePlayerTurnView();
        UpdateTeamBoulesLeftView();
    }

    public void EndGameRound(){
        gameRoundManager.AddCurrentGameRoundToFinishedGameRounds();
        gameController.FinishGameRoundActivity();
        UpdateCurrentGame();
    }

    public void ExitGameOverview(){
        settingsManager.ResetGame();
    }

    public void NewThrow(ThrowData nThrowData ){
        if(!gameData.currentGameRound.gameHasEnded){
            gameLogic.ProcessNewThrow( nThrowData);
            gameRoundManager.UpdateGameRoundScore();
            gameRoundManager.CalculateBoulsLeft();
            gameRoundManager.UpdateCurentPlayer();
            gameRoundManager.CheckIfGameRoundHasEnded();

            UpdateTeamBoulesLeftView();
            UpdateGameRoundTeamPointsView();
            UpdatePlayerTurnView();
        }

    }

    public void SetNextButtonTextToGameWon(){
        if(gameData.team1TotalScore >= gameData.scoreToWin){
            gameController.SetNextRoundButtonText("TEAM 1 HAS WON THE GAME!\nClick to start a new game.");
        }
        if(gameData.team2TotalScore >= gameData.scoreToWin){
            gameController.SetNextRoundButtonText("TEAM 2 HAS WON THE GAME!\nClick to start a new game.");
        }
    }

    public void SetNextButtonTextToDefault(){
        {
            gameController.SetNextRoundButtonText("CLICK TO START A NEW ROUND.");
        }
    }

    public boolean CheckIfGameHasEnded(){
        if (gameData.team1TotalScore >= gameData.scoreToWin || gameData.team2TotalScore >= gameData.scoreToWin){
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

    public void UpdateGameRoundTeamPointsView(){
        gameController.SetGameRoundTeamPointsView("Team 1", gameData.currentGameRound.scoreTeamOne +" Points");
        gameController.SetGameRoundTeamPointsView("Team 2", gameData.currentGameRound.scoreTeamTwo +" Points");
    }

    public void UpdateTeamBoulesLeftView(){
        gameController.SetTeamBoulesLeftView("Team 1", gameData.currentGameRound.boulesTeamOne +" left.");
        gameController.SetTeamBoulesLeftView("Team 2", gameData.currentGameRound.boulesTeamTwo +" left.");
    }

    public void UpdatePlayerTurnView(){
        if(!gameData.currentGameRound.gameHasEnded){
            if (gameData.currentGameRound.currentPlayer == 0){
                gameController.SetPlayerTurnView("Throw the Jack!");
            }else{
                gameController.SetPlayerTurnView("PLAYER " + gameData.currentGameRound.currentPlayer + " TURN.");
            }
        }else{
            gameController.SetPlayerTurnView("GAME ROUND END");
        }
    }

    public void SetBouleFieldSize(float sizeX, float sizeY){
        settingsManager.SetBouleFieldSize(sizeX,sizeY);
    }

    public void UpdateBouleFieldView(BallData nBallData){
        gameController.SetBouleFieldView(nBallData);
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

        for(int i = 0; i < gameData.team1RoundScore.size(); i++){
            team1TotalPoints += gameData.team1RoundScore.get(i);
            team1RoundPointsList += Integer.toString(gameData.team1RoundScore.get(i))+"\n";
        }
        for(int i = 0; i < gameData.team2RoundScore.size(); i++){
            team2TotalPoints += gameData.team2RoundScore.get(i);
            team2RoundPointsList += Integer.toString(gameData.team2RoundScore.get(i))+"\n";
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
