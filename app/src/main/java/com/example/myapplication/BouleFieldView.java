package com.example.myapplication;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.myapplication.DataClasses.BallData;
import com.example.myapplication.DataClasses.GameData;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class BouleFieldView extends View {
    public BouleFieldView(Context context) {
        super(context);
        init(null);
    }

    public BouleFieldView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public BouleFieldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);
    }

    public BouleFieldView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(attrs);
    }

    private void init (@Nullable AttributeSet set){

    }



    public int gRound = 0;
    public  float [] ballInfo = new float[5];

    public  ArrayList<BallData> ballDatas = new ArrayList<BallData>();


    public void ThrowButtonClicked(){
        AddNewBallTest();
    }

    public boolean teamOneTurn = true;

    //Returns a Random X-Velocity, used for testing.
    public float AddRandomXVelocity(){
        Random r = new Random();
        float ballVelX  = 0 + r.nextFloat() * (3 - 0);
        int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);
        if(randomNum ==0){
            ballVelX = -ballVelX;
        }
        return  ballVelX;
    }

    //Returns a Random Y-Velocity, used for testing.
    public  float AddRandomYVelocity(){
        Random r = new Random();
        float ballVelY  = 4 + r.nextFloat() * (24 - 4);
        return ballVelY;
    }

    public void AddNewBallTest(){
        //d
        String newTeamInfo = "neutral";

        if (teamOneTurn)
            teamOneTurn =false;
        else
            teamOneTurn = true;

        if (teamOneTurn)
            newTeamInfo = "team 1";
        else
            newTeamInfo = "team 2";

        if (gRound == 0)
            newTeamInfo = "neutral";

        BallData newBall = new BallData(AddRandomXVelocity(), AddRandomYVelocity(), newTeamInfo);
        gRound ++;
        //Log.d("App", "BallInfo, Ball Velocity X:" + newBall.ballVelX);
        ballDatas.add(newBall);
        postInvalidate();

    }
    public void calculateBallThrowTest(){
        boolean drawNew = false;

        for(int i = 0; i < ballDatas.size(); i++){

            BallData ballData = ballDatas.get(i);

            if (ballData.ballVelX > 0.1 || ballData.ballVelY > 0.1){

                drawNew = true;

                ballData.ballVelX = ballData.ballVelX * 0.98f;
                ballData.ballVelY = ballData.ballVelY * 0.98f;

                ballData.ballPosX = ballData.ballPosX + ballData.ballVelX;
                ballData.ballPosY = ballData.ballPosY - ballData.ballVelY;

            }


        }

        if (drawNew){
            postInvalidate();

        }else{
            for(int i = 1; i < ballDatas.size(); i++){

                ballDatas.get(i).distanceToPiggy = CalculateDistanceToPiggy(ballDatas.get(0), ballDatas.get(i));
                Log.d("App", "Ball " + i + ",Distance to piggy: " +  ballDatas.get(i).distanceToPiggy);
            }

        }
    }

    public void ThrowBalls(){



    }


    @Override
    protected void onDraw(Canvas canvas) {
        float startPosX = canvas.getWidth() /2;
        float startPosY = canvas.getHeight();
        canvas.drawColor(getResources().getColor(R.color.lightBeige));
        //canvas.drawColor(Color.argb(1,234, 210, 168));
        Paint paint = new Paint();

        for (int i =0; i < ballDatas.size(); i++){
            int ballSize = 25;

            BallData ballData = ballDatas.get(i);


            if (ballData.ballTeam == "neutral"){
                paint.setColor(Color.GREEN);
                ballSize = 20;
            }else if (ballData.ballTeam == "team 1"){
                paint.setColor(Color.RED);
                ballSize = 25;
            }else if (ballData.ballTeam == "team 2"){
                paint.setColor(Color.BLUE);
                ballSize = 25;
            }

            float ballPosX = ballData.ballPosX + startPosX;
            float ballPosY = ballData.ballPosY + startPosY;
            //Log.d("App", "Ball " + i + ",official Ball Position X:" + ballPosX + "Ball Position Y:" + ballPosY);
           // Log.d("App", "Ball " + i + ",internal Ball Position X:" + ballData.ballPosX + "Ball Position Y:" + ballData.ballPosY);
            //Log.d("App", "Ball " + i + ",internal Ball Velocity X:" + ballData.ballVelX+ "Ball Velocity Y:" + ballData.ballVelY);

            canvas.drawCircle(ballPosX,ballPosY,ballSize,paint);

        }

        calculateBallThrowTest();
    }

    public float CalculateDistanceToPiggy(BallData piggyData, BallData nBallData ) {
        float cathetiX = piggyData.ballPosX - nBallData.ballPosX;
        float cathetiY = piggyData.ballPosY - nBallData.ballPosY;
        double hypotenuse = Math.sqrt((cathetiX * cathetiX) + (cathetiY * cathetiY));
        float distance = (float)hypotenuse;
        return distance;
    }



   /* public void AddNewBallTest(){
        //d
        if (teamOneTurn)
            teamOneTurn =false;
        else
            teamOneTurn = true;

        ballInfo[2] = 0;
        ballInfo[3] = 0;

        Random r = new Random();
        float ballVelX  = 0 + r.nextFloat() * (3 - 0);
        int randomNum = ThreadLocalRandom.current().nextInt(0, 1 + 1);
        if(randomNum ==0){
            ballVelX = -ballVelX;
        }

        r = new Random();
        float ballVelY  = 4 + r.nextFloat() * (24 - 4);

        ballInfo[0] = ballVelX;
        ballInfo[1] = ballVelY;
        float [] newBallInfo = new float[5];
        newBallInfo[0] = ballInfo[0];
        newBallInfo[1] = ballInfo[1];
        newBallInfo[2] = ballInfo[2];
        newBallInfo[3] = ballInfo[3];
        if (teamOneTurn)
            newBallInfo[4] = 0;
        else
            newBallInfo[4] = 1;

        if (gRound == 0)
            newBallInfo[4] = 3;

        gRound ++;
        ballInfos.add(newBallInfo);
        postInvalidate();



    public void calculateBallThrowTest(){
        boolean drawNew = false;

        for(int i = 0; i < ballInfos.size(); i++){

            float [] ballData = ballInfos.get(i);

            if (ballData[0] > 0.1 || ballData[1] > 0.1){

                drawNew = true;

                ballData[0] = ballData[0] * 0.98f;
                ballData[1] = ballData[1] * 0.98f;

                ballData[2] = ballData[2] + ballData[0];
                ballData[3] = ballData[3] - ballData[1];
            }
        }

        if (drawNew){
            postInvalidate();

        }
    }

    public void ThrowBalls(){


    }


    @Override
    protected void onDraw(Canvas canvas) {
        float startPosX = canvas.getWidth() /2;
        float startPosY = canvas.getHeight();
                canvas.drawColor(getResources().getColor(R.color.lightBeige));
        //canvas.drawColor(Color.argb(1,234, 210, 168));
        Paint paint = new Paint();

        for (int i =0; i < ballInfos.size(); i++){
            int ballSize = 25;

            float [] ballData = ballInfos.get(i);
            float ballColor = ballData[4];

            if (ballColor == 3){
                paint.setColor(Color.GREEN);
                ballSize = 20;
            }else if (ballColor == 0){
                paint.setColor(Color.RED);
                ballSize = 25;
            }else if (ballColor == 1){
                paint.setColor(Color.BLUE);
                ballSize = 25;
            }

            float ballPosX = ballData[2] + startPosX;
            float ballPosY = ballData[3] + startPosY;

            canvas.drawCircle(ballPosX,ballPosY,ballSize,paint);

        }

        calculateBallThrowTest();
    }

    */
}
