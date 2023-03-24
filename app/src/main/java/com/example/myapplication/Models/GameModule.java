package com.example.myapplication.Models;

import android.util.Log;
import android.view.View;

import com.example.myapplication.Controller.GameController;
import com.example.myapplication.DataClasses.BallData;
import com.example.myapplication.DataClasses.GameData;
import com.example.myapplication.DataClasses.PlayRoundData;
import com.example.myapplication.DataClasses.ThrowData;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

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



    List<Integer> roundsList;
    List<Integer> team1RoundPoints;
    List<Integer> team2RoundPoints;


    //Boule Field View
    public PlayRoundData gameRoundData = new PlayRoundData();
    public int gRound = 0;
    public  float [] ballInfo = new float[5];
    public  ArrayList<BallData> ballDatas = new ArrayList<BallData>();
    public boolean teamOneTurn = true;

    //Initializes the game withe the selected amount of Players
    public void InitializeGame(String newGameMode){
        gameData = GameData.getInstance();
        gameSettings = new GameSettingsModule();
        gameRoundManager = new GameRoundModule();
        gameSettings.ResetGameSettings();
        gameSettings.SetGameSettingsToGameMode(newGameMode);
        Log.d("App", "InitializeGame in GameModel");
        UpdateCurrentGame();
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
        gameController.SetPlayroundTeamPointsView("Team 1", gameRoundData.pointsTeamOne +" Points");
        gameController.SetPlayroundTeamPointsView("Team 2", gameRoundData.pointsTeamTwo +" Points");
    }

    public void UpdateTeamBoulesLeftView(){
        gameController.SetTeamBoulesLeftView("Team 1", gameRoundData.boulesTeamOne +" left.");
        gameController.SetTeamBoulesLeftView("Team 2", gameRoundData.boulesTeamTwo +" left.");
    }

    public void UpdatePlayerTurnView(){
        if(!gameRoundData.gameHasEnded){
            if (gameRoundData.currentPlayer == 0){
                gameController.SetPlayerTurnView("Throw the Jack!");
            }else{
                gameController.SetPlayerTurnView("PLAYER " + gameRoundData.currentPlayer + " TURN.");
            }
        }else{
            gameController.SetPlayerTurnView("GAME ROUND END");
        }

    }

    //<-------------------------------->

    public void NextGameRound(){
        gameRoundManager.NextGameRound();
        UpdatePlayerTurnView();
    }

    public void endGameRound(){
        gameRoundManager.endGameRound();
        gameController.FinishGameRoundActivity();
        UpdateCurrentGame();
    }







    public void NewThrow(ThrowData nThrowData ){
        Log.d("Sensor-App", "Current Player: " + gameRoundData.currentPlayer);
        if(!gameRoundData.gameHasEnded){
            CalculateVelocity( nThrowData);
            gameSettings.UpdatePlayroundPoints();
            CalculateBoulsLeft();
            CheckIfPlayroundHasEnded();
            UpdateCurentPlayer();
            UpdateTeamBoulesLeftView();
            UpdatePlayroundTeamPointsView();
            UpdatePlayerTurnView();
        }

    }



    public void UpdateCurrentGame(){
        gameSettings.UpdateGameData();
        SetGameOverviewTeamsList();
        SetGameOverviewRounds();
        SetGameOverviewPlayerPoints();
    }

    public void SetGameOverviewRounds(){
        String roundsTotal = Integer.toString(gameData.roundCount);
        String roundsListView = "";
        for(int i = 0; i < gameData.finishedGameRounds.size(); i++){
            roundsListView += Integer.toString(gameData.roundsList.get(i))+"\n";
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
            team1TotalPoints += team1RoundPoints.get(i);
            team1RoundPointsList += Integer.toString(team1RoundPoints.get(i))+"\n";
        }
        for(int i = 0; i < team2RoundPoints.size(); i++){
            team2TotalPoints += team2RoundPoints.get(i);
            team2RoundPointsList += Integer.toString(team2RoundPoints.get(i))+"\n";
        }
        gameController.SetGameOverviewTeamTotalPoints("Team 1", Integer.toString(team1TotalPoints));
        gameController.SetGameOverviewTeamTotalPoints("Team 2", Integer.toString(team2TotalPoints));

        gameController.SetGameOverviewTeamListPoints("Team 1", team1RoundPointsList);
        gameController.SetGameOverviewTeamListPoints("Team 2", team2RoundPointsList);

    }





    //Close the current Game
    public void EndGame(View view){



    }

    public int RandomIntReturn(){
        int randomNum = ThreadLocalRandom.current().nextInt(0, 4);
        return randomNum;
    }

    public void UpdateCurentPlayer(){

        if (gameRoundData.currentPlayer < gameRoundData.playerTeams.size() -1){
            gameRoundData.currentPlayer ++;
        }else{
            gameRoundData.currentPlayer = 1;
        }
    }

    public void CalculateBoulsLeft(){

            switch(CheckForCurrentTeam()){
                case "neutral":

                    break;
                case "team 1":
                    gameRoundData.boulesTeamOne --;
                    Log.d("Sensor-App", "Boules left Team 1: " + gameRoundData.boulesTeamOne);
                    break;
                case "team 2":
                    gameRoundData.boulesTeamTwo --;
                    Log.d("Sensor-App", "Boules left Team 2: " + gameRoundData.boulesTeamTwo);
                    break;
                default:

                    break;
            }

    }

    public void CheckIfPlayroundHasEnded(){
        if(gameRoundData.boulesTeamOne <=0 && gameRoundData.boulesTeamTwo <=0 ){
            Log.d("Sensor-App", "Play Round has ended!");
            gameRoundData.gameHasEnded = true;
        }

    }







    public void CalculateVelocity(ThrowData nThrowData){

        float firstTimeStamp = nThrowData.timeStampOne;
        float secondTimeStamp = nThrowData.timeStampTwo;
        ArrayList<float []> throwVectors = nThrowData.throwVectors;
        float xv = 0;
        float yv = 0;
        float zv = 0;
        boolean checkForXpos = false;
        boolean xPositiv = false;
        boolean yPositiv = false;
        ThrowData throwData = new ThrowData(firstTimeStamp, secondTimeStamp, throwVectors);


        for( int i = 0; i < throwVectors.size(); i++){
            //Log.d("Sensor-App", "Counter: x:" +  xv + " y:" +  yv + " z:" +  zv);

            if(!checkForXpos){
                if(throwVectors.get(i)[0] >= 1.5){
                    xPositiv = true;
                    checkForXpos = true;
                }else if(throwVectors.get(i)[0] <= -1.5) {

                    xPositiv = false;
                    checkForXpos = true;
                }
            }

            if(xPositiv && checkForXpos) {
                if(throwVectors.get(i)[0] > 0)
                    xv = (xv + throwVectors.get(i)[0]);
            }
            if(!xPositiv && checkForXpos) {
                if(throwVectors.get(i)[0] < 0)
                    xv = (xv + throwVectors.get(i)[0]);
            }


            if(throwVectors.get(i)[1] > 0)
                yv = (yv + throwVectors.get(i)[1]);

            if(throwVectors.get(i)[2] > 0)
                zv = (zv + throwVectors.get(i)[2]);
        }

        float throwTime =  Math.round((secondTimeStamp - firstTimeStamp) / 100000000.0);

        String sX = String.format("%.2f", xv);
        String sY = String.format("%.2f", yv);
        String sZ = String.format("%.2f", zv);

        Log.d("Sensor-App", "Combined Vector is: " + "x:" +  sX + " y:" +  sY + " z:" +  sZ + ". Time in Millisec: " + throwTime);

        AddNewBall(xv,yv,zv,throwTime);
    }

    //Boule Field View
    public String CheckForCurrentTeam(){
        return gameRoundData.playerTeams.get(gameRoundData.currentPlayer);
    }

    public void AddNewBall(float nVelX, float nVelY, float nVelZ, float nTime){
            String newTeamInfo = CheckForCurrentTeam();

        BallData newBall = new BallData(CalculateVelocity(nVelX,nTime, 0), CalculateVelocity(nVelY,nTime, 1), newTeamInfo);
        BallData newBall2 = new BallData(CalculateVelocity(nVelX,nTime, 0), CalculateVelocity(nVelY,nTime, 1), newTeamInfo);
        gRound ++;
        //Log.d("App", "BallInfo, Ball Velocity X:" + newBall.ballVelX);
        gameRoundData.thrownBalls.add(newBall);
        gameController.UpdateBouleFieldView(newBall2);
        calculateBallThrowTest();

    }



    public float CalculateVelocity(float nforce, float ntime, int ntype){

        //is X if type = 0, Y if type is 1, z if type is 2;
        int type=ntype;
        float velocity = 0;

        switch(ntype){
            case 0:
                velocity = nforce/ntime;
                Log.d("App", "Velocity X: " + velocity);
                break;
            case 1:
                velocity = (nforce/ntime)*3;
                Log.d("App", "Velocity Y: " + velocity);
                break;
            case 2:
                velocity = nforce/ntime;
                Log.d("App", "Velocity Z: " + velocity);
                break;
            default:
                velocity = 0;
                break;
        }

        return velocity;
    }

    public void calculateBallThrowTest(){
        boolean drawNew = false;

        for(int i = 0; i < gameRoundData.thrownBalls.size(); i++){

            BallData ballData = gameRoundData.thrownBalls.get(i);

            if (ballData.ballVelX > 0.1 || ballData.ballVelY > 0.1){

                drawNew = true;

                ballData.ballVelX = ballData.ballVelX * 0.98f;
                ballData.ballVelY = ballData.ballVelY * 0.98f;

                ballData.ballPosX = ballData.ballPosX + ballData.ballVelX;
                ballData.ballPosY = ballData.ballPosY - ballData.ballVelY;

            }


        }

        if (drawNew){
            calculateBallThrowTest();

        }else{
            ArrayList<BallData> thrownBalls = new ArrayList<BallData>();

            for(int i = 1; i < gameRoundData.thrownBalls.size(); i++){

                gameRoundData.thrownBalls.get(i).distanceToPiggy = CalculateDistanceToPiggy(gameRoundData.thrownBalls.get(0), gameRoundData.thrownBalls.get(i));
                thrownBalls.add(gameRoundData.thrownBalls.get(i));
                //Log.d("App", "Ball " + i + ",Distance to piggy: " +  ballDatas.get(i).distanceToPiggy);
            }
            gameRoundData.thrownBallsSorted = InsertSortBallThrows(thrownBalls);

            Log.d("App", "Unsorted List ");
            for(int i = 0; i < gameRoundData.thrownBalls.size(); i++){

                Log.d("App", "Ball " + i + ", Team: "+ gameRoundData.thrownBalls.get(i).ballTeam +",Distance to piggy: " +  gameRoundData.thrownBalls.get(i).distanceToPiggy);
            }
            Log.d("App", "Sorted List ");
            for(int i = 0; i < gameRoundData.thrownBallsSorted.size(); i++){

                Log.d("App", "Ball " + i + ", Team: "+ gameRoundData.thrownBallsSorted.get(i).ballTeam +",Distance to piggy: " +  gameRoundData.thrownBallsSorted.get(i).distanceToPiggy);
            }


        }

    }
    public ArrayList<BallData> InsertSortBallThrows (ArrayList<BallData> newUnsortedList){

        ArrayList<BallData> unsortedList = newUnsortedList;

        BallData k;
        for (int i = 0; i < unsortedList.size(); i++) {
            for (int j = unsortedList.size()-1; j > 0; j--) {
                if (unsortedList.get(j-1).distanceToPiggy > unsortedList.get(j).distanceToPiggy) {
                    k = unsortedList.get(j);
                    unsortedList.set(j, unsortedList.get(j -1));
                    unsortedList.set(j -1, k);
                }
            }
        }

        return unsortedList;
    }



    public float CalculateDistanceToPiggy(BallData piggyData, BallData nBallData ) {
        float cathetiX = piggyData.ballPosX - nBallData.ballPosX;
        float cathetiY = piggyData.ballPosY - nBallData.ballPosY;
        double hypotenuse = Math.sqrt((cathetiX * cathetiX) + (cathetiY * cathetiY));
        float distance = (float)hypotenuse;
        return distance;
    }

}
