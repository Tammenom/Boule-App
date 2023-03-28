package com.example.myapplication.Models;

import android.util.Log;

import com.example.myapplication.DataClasses.BallData;
import com.example.myapplication.DataClasses.GameData;
import com.example.myapplication.DataClasses.ThrowData;

import java.util.ArrayList;

public class GameLogic {

    private GameData gameData = GameData.getInstance();
    private GameManager gameManager = GameManager.getInstance();


    public String CheckForCurrentTeam(){
        return gameData.currentGameRound.playerNames.get(gameData.currentGameRound.currentPlayer);
    }
    public void ProcessNewThrow(ThrowData nThrowData){
        float SecondMovementFilter = 2f;
        float firstTimeStamp = nThrowData.timeStampStart;
        float secondTimeStamp = nThrowData.timeStampEnd;
        ArrayList<float []> throwVectors = nThrowData.throwVectors;
        float xv = 0;
        float yv = 0;
        float zv = 0;
        boolean checkForXpos = false;
        boolean xPositiv = false;

        for( int i = 0; i < throwVectors.size(); i++){
            if(!checkForXpos){
                if(throwVectors.get(i)[0] >= SecondMovementFilter){
                    xPositiv = true;
                    checkForXpos = true;
                }else if(throwVectors.get(i)[0] <= -SecondMovementFilter) {

                    xPositiv = false;
                    checkForXpos = true;
                }
            }}
        for( int i = 0; i < throwVectors.size(); i++){
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

        float throwTime =  Math.round((secondTimeStamp - firstTimeStamp) / 100000000.0); //in Millisec
        AddNewBall(xv,yv,zv,throwTime);
    }

    //Boule Field View

    public void AddNewBall(float nVelX, float nVelY, float nVelZ, float nTime){
        String newTeamInfo = CheckForCurrentTeam();

        BallData newBall = new BallData(CalculateVelocity(nVelX,nTime, 0), CalculateVelocity(nVelY,nTime, 1), newTeamInfo);
        BallData newBall2 = new BallData(CalculateVelocity(nVelX,nTime, 0), CalculateVelocity(nVelY,nTime, 1), newTeamInfo);

        gameData.currentGameRound.thrownBalls.add(newBall);
        gameManager.UpdateBouleFieldView(newBall2);
        CalculateBallThrow();

    }

    public float CalculateVelocity(float nforce, float ntime, int ntype){
        //is X if type = 0, Y if type is 1, Z if type is 2;
        int forwardForceMultiplier = 3;
        int type=ntype;
        float velocity = 0;

        switch(ntype){
            case 0:
                velocity = nforce/ntime;
                break;
            case 1:
                velocity = (nforce/ntime)*forwardForceMultiplier;
                break;
            case 2:
                velocity = nforce/ntime;
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
            float counterforce = 0.98f;
            float minimalVelocity = 0.1f;
            float bouleFieldSizeX = gameData.fieldSizeX;
            float bouleFieldSizeY = gameData.fieldSizeY;
            BallData ballData = gameData.currentGameRound.thrownBalls.get(i);

            if (ballData.ballPosX > -bouleFieldSizeX && ballData.ballPosX < bouleFieldSizeX && ballData.ballPosY > -bouleFieldSizeY){
                if (ballData.ballVelX > minimalVelocity || ballData.ballVelY > minimalVelocity){

                    drawNew = true;

                    ballData.ballVelX = ballData.ballVelX * counterforce;
                    ballData.ballVelY = ballData.ballVelY * counterforce;
                    ballData.ballPosX = ballData.ballPosX + ballData.ballVelX;
                    ballData.ballPosY = ballData.ballPosY - ballData.ballVelY;
                }
            }else{
                ballData.ballVelX = 0;
                ballData.ballVelY =0;
            }
        }
        if (drawNew){
            CalculateBallThrow();

        }else{
            ArrayList<BallData> thrownBalls = new ArrayList<BallData>();

            for(int i = 1; i < gameData.currentGameRound.thrownBalls.size(); i++){

                gameData.currentGameRound.thrownBalls.get(i).distanceToJack =
                        CalculateDistanceToJack(gameData.currentGameRound.thrownBalls.get(0), gameData.currentGameRound.thrownBalls.get(i));
                thrownBalls.add(gameData.currentGameRound.thrownBalls.get(i));
            }
            UpdateSortedList(InsertSortBallThrows(thrownBalls));
        }
    }
    private void UpdateSortedList(ArrayList<BallData> nList){
        gameData.currentGameRound.thrownBallsSorted = InsertSortBallThrows(nList);
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

    public float CalculateDistanceToJack(BallData jackData, BallData nBallData ) {
        float cathetiX = jackData.ballPosX - nBallData.ballPosX;
        float cathetiY = jackData.ballPosY - nBallData.ballPosY;
        double hypotenuse = Math.sqrt((cathetiX * cathetiX) + (cathetiY * cathetiY));
        float distance = (float)hypotenuse;
        return distance;
    }

}
