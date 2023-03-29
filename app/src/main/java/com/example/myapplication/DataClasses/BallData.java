package com.example.myapplication.DataClasses;

public class BallData {
    //X-Velocity of the Ball
    public float ballVelX = 0;
    //Ý-Velocity of the Ball
    public float ballVelY = 0;
    //X-Position of the Ball
    public float ballPosX = 0;
    //Y-Position of the Ball
    public float ballPosY = 0;
    //The Team, the Ball belongs to.
    public String ballTeam = "neutral";
    public  float distanceToJack = 99999999;

    public BallData(float nBallVelX, float nBallVelY, String nTeam ){
        ballVelX = nBallVelX;
        ballVelY = nBallVelY;
        ballTeam = nTeam;
    }
}
