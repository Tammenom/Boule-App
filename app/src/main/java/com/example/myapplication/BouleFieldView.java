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
import com.example.myapplication.DataClasses.PlayRoundData;

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

    public PlayRoundData gameRoundData = new PlayRoundData();



    public int gRound = 0;
    public  float [] ballInfo = new float[5];

    public  ArrayList<BallData> ballDatas = new ArrayList<BallData>();


    public void ThrowButtonClicked(){



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
            gameRoundData.thrownBalls = new ArrayList<BallData>();

            for(int i = 1; i < ballDatas.size(); i++){

                ballDatas.get(i).distanceToPiggy = CalculateDistanceToPiggy(ballDatas.get(0), ballDatas.get(i));
                gameRoundData.thrownBalls.add(ballDatas.get(i));
                //Log.d("App", "Ball " + i + ",Distance to piggy: " +  ballDatas.get(i).distanceToPiggy);
                        }
            ArrayList<BallData> nList = new ArrayList<BallData>();
            nList = gameRoundData.thrownBalls;
            //gameRoundData.thrownBallsSorted = InsertSortBallThrows(nList);

            Log.d("App", "Unsorted List ");
            for(int i = 0; i < gameRoundData.thrownBalls.size(); i++){

                Log.d("App", "Ball " + i + ", Team: "+ gameRoundData.thrownBalls.get(i).ballTeam +",Distance to piggy: " +  gameRoundData.thrownBalls.get(i).distanceToPiggy);
                           }
           // Log.d("App", "Sorted List ");
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




}
