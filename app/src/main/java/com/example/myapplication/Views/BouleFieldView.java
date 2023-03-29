package com.example.myapplication.Views;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import androidx.annotation.Nullable;

import com.example.myapplication.Controller.GameController;
import com.example.myapplication.DataClasses.BallData;
import com.example.myapplication.R;

import java.util.ArrayList;


public class BouleFieldView extends View {
    private float minimalVelocity = 0.1f;
    public float counterforce = 0.975f;
    public  ArrayList<BallData> ballDatas = new ArrayList<BallData>();
    private GameController gameController = GameController.getInstance();
    private boolean fieldSizeInitizialized = false;
    private float bouleFieldSizeX = 0;
    private float bouleFieldSizeY = 0;

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



    public void UpdateBouleFieldView(BallData nBallData){
        ballDatas.add(nBallData);
        postInvalidate();
    }

    private void UpdateBouleFieldSize(Canvas canvas){
        bouleFieldSizeX = canvas.getWidth()/2;
        bouleFieldSizeY = canvas.getHeight();

        if (!fieldSizeInitizialized){
            gameController.UpdateBouleFieldSize(canvas.getWidth()/2, canvas.getHeight());
            fieldSizeInitizialized = true;
        }
    }


    public void CalculateVisualBallThrows(){
        boolean drawNew = false;

        for(int i = 0; i < ballDatas.size(); i++){

            BallData ballData = ballDatas.get(i);

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
            postInvalidate();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        UpdateBouleFieldSize(canvas);
        float startPosX = canvas.getWidth() /2;
        float startPosY = canvas.getHeight();
        canvas.drawColor(getResources().getColor(R.color.lightBeige));

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

        CalculateVisualBallThrows();
    }
}
