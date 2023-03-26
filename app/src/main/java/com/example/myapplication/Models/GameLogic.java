package com.example.myapplication.Models;

import android.util.Log;

import com.example.myapplication.DataClasses.BallData;
import com.example.myapplication.DataClasses.GameData;
import com.example.myapplication.DataClasses.ThrowData;

import java.util.ArrayList;

public class GameLogic {

    private GameData gameData = GameData.getInstance();
    private GameManager gameManager = GameManager.getInstance();


    public void UpdateCurentPlayer(){

        if (gameData.currentGameRound.currentPlayer < gameData.currentGameRound.playerTeams.size() -1){
            gameData.currentGameRound.currentPlayer ++;
        }else{
            gameData.currentGameRound.currentPlayer = 1;
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

    public void CheckIfPlayroundHasEnded(){
        if(gameData.currentGameRound.boulesTeamOne <=0 && gameData.currentGameRound.boulesTeamTwo <=0 ){
            Log.d("Sensor-App", "Play Round has ended!");
            gameData.currentGameRound.gameHasEnded = true;
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
        return gameData.currentGameRound.playerTeams.get(gameData.currentGameRound.currentPlayer);
    }

    public void AddNewBall(float nVelX, float nVelY, float nVelZ, float nTime){
        String newTeamInfo = CheckForCurrentTeam();

        BallData newBall = new BallData(CalculateVelocity(nVelX,nTime, 0), CalculateVelocity(nVelY,nTime, 1), newTeamInfo);
        BallData newBall2 = new BallData(CalculateVelocity(nVelX,nTime, 0), CalculateVelocity(nVelY,nTime, 1), newTeamInfo);

        //Log.d("App", "BallInfo, Ball Velocity X:" + newBall.ballVelX);
        gameData.currentGameRound.thrownBalls.add(newBall);
        gameManager.UpdateBouleFieldView(newBall2);
        CalculateBallThrow();

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

    public void CalculateBallThrow(){
        boolean drawNew = false;

        for(int i = 0; i < gameData.currentGameRound.thrownBalls.size(); i++){

            BallData ballData = gameData.currentGameRound.thrownBalls.get(i);

            if (ballData.ballVelX > 0.1 || ballData.ballVelY > 0.1){

                drawNew = true;

                ballData.ballVelX = ballData.ballVelX * 0.98f;
                ballData.ballVelY = ballData.ballVelY * 0.98f;

                ballData.ballPosX = ballData.ballPosX + ballData.ballVelX;
                ballData.ballPosY = ballData.ballPosY - ballData.ballVelY;

            }


        }

        if (drawNew){
            CalculateBallThrow();

        }else{
            ArrayList<BallData> thrownBalls = new ArrayList<BallData>();

            for(int i = 1; i < gameData.currentGameRound.thrownBalls.size(); i++){

                gameData.currentGameRound.thrownBalls.get(i).distanceToJack = CalculateDistanceToJack(gameData.currentGameRound.thrownBalls.get(0), gameData.currentGameRound.thrownBalls.get(i));
                thrownBalls.add(gameData.currentGameRound.thrownBalls.get(i));
                //Log.d("App", "Ball " + i + ",Distance to piggy: " +  ballDatas.get(i).distanceToPiggy);
            }
            gameData.currentGameRound.thrownBallsSorted = InsertSortBallThrows(thrownBalls);

            Log.d("App", "Unsorted List ");
            for(int i = 0; i < gameData.currentGameRound.thrownBalls.size(); i++){

                Log.d("App", "Ball " + i + ", Team: "+ gameData.currentGameRound.thrownBalls.get(i).ballTeam +",Distance to piggy: " +  gameData.currentGameRound.thrownBalls.get(i).distanceToJack);
            }
            Log.d("App", "Sorted List ");
            for(int i = 0; i < gameData.currentGameRound.thrownBallsSorted.size(); i++){

                Log.d("App", "Ball " + i + ", Team: "+ gameData.currentGameRound.thrownBallsSorted.get(i).ballTeam +",Distance to piggy: " +  gameData.currentGameRound.thrownBallsSorted.get(i).distanceToJack);
            }


        }

    }
    private  ArrayList<BallData> InsertSortBallThrows (ArrayList<BallData> newUnsortedList){

        ArrayList<BallData> unsortedList = newUnsortedList;

        BallData k;
        for (int i = 0; i < unsortedList.size(); i++) {
            for (int j = unsortedList.size()-1; j > 0; j--) {
                if (unsortedList.get(j-1).distanceToJack > unsortedList.get(j).distanceToJack) {
                    k = unsortedList.get(j);
                    unsortedList.set(j, unsortedList.get(j -1));
                    unsortedList.set(j -1, k);
                }
            }
        }

        return unsortedList;
    }



    public float CalculateDistanceToJack(BallData piggyData, BallData nBallData ) {
        float cathetiX = piggyData.ballPosX - nBallData.ballPosX;
        float cathetiY = piggyData.ballPosY - nBallData.ballPosY;
        double hypotenuse = Math.sqrt((cathetiX * cathetiX) + (cathetiY * cathetiY));
        float distance = (float)hypotenuse;
        return distance;
    }

}
