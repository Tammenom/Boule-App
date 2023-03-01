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

    public  ArrayList<BallData> ballDatas = new ArrayList<BallData>();

    public void UpdateBouleFieldView(ArrayList<BallData> nBallDatas){
        ballDatas = nBallDatas;
        postInvalidate();
    }


    public void calculateVisualBallThrows(){
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
        }
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
            canvas.drawCircle(ballPosX,ballPosY,ballSize,paint);

        }

        calculateVisualBallThrows();
    }
}
