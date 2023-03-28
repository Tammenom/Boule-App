package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

import com.example.myapplication.Controller.GameController;
import com.example.myapplication.DataClasses.BallData;
import com.example.myapplication.DataClasses.ThrowData;

import java.util.ArrayList;

public class GameRoundActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mSensorManager;
    private Sensor mAccelerometer;
    private ArrayList<float []> throwVectors = new ArrayList<float []>();
    private GameController gameController;
    private float movementfilter = 0.8f;
    private int maxLengthList = 300;
    private boolean checkForXpos = false;
    private boolean timeStampCheck = false;

    private long startTimeStamp = 0;
    private long endTimeStamp = 0;

    private boolean startListening = false;
    private boolean endListening = false;

    private BouleFieldView bouleView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_round);
        bouleView = (BouleFieldView) findViewById(R.id.bouleFieldView);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
        gameController = GameController.getInstance();
        gameController.SetPlayRoundActivity(this);
        gameController.NextRound();
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    public void endGameRound(View v){
        gameController.EndGameRound();
    }

    //Method is called trough the "End Game" Button, finishes the current GameRound Activity.
    public void finishActivity(){
        finish();
    }

    //If Volume Keys are Pressed, the Acceleration Sensor starts/continues collecting Data.
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            if(!startListening && !endListening){
                startListening = true;
                onResume();}
        }
        return true;
    }

    // If Volume Key is released, the Acceleration Sensor stops collecting Data.
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_VOLUME_DOWN || keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            if(startListening && !endListening){
                endListening = true;}
        }
        return true;
    }

    //Collects the Acceleration Data and saves it in the "ArrayList<float []> throwVectors".
    //After Data Collection is finished, the Array-List is passed trough the GameController to the GameModule.
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float[] values = sensorEvent.values;

        if(startListening && !endListening&& throwVectors.size() <maxLengthList){
            if(!timeStampCheck){
                startTimeStamp = sensorEvent.timestamp;
                timeStampCheck = true;
            }

            if(values[0]>=movementfilter || values[0]<=-movementfilter || values[1]>=movementfilter || values[2]>=movementfilter || values[1]<=-movementfilter || values[2]<=-movementfilter){
                float [] throwVec = new float[3];
                throwVec[0] = values[0];
                throwVec[1] = values[1];
                throwVec[2] = values[2] ;
                throwVectors.add(throwVec);
            }
        }

        if(startListening && endListening){
            endTimeStamp = sensorEvent.timestamp;
            checkForXpos = false;
            ThrowData throwData = new ThrowData(startTimeStamp, endTimeStamp, throwVectors);
            SendThrowEvent(throwData);
            timeStampCheck = false;
            startListening = false;
            endListening = false;
            throwVectors.clear();
            onPause();
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {}

    public void SendThrowEvent(ThrowData nThrowData){
        gameController.NewThrowEvent(nThrowData);
    }

    //Calls the Update Method in the BouleFieldView.
    public void UpdateBouleFieldView(BallData nBallDatas){
        bouleView.UpdateBouleFieldView(nBallDatas);
    }

    //Sets the TextView for the teams scores, to the String that is passed into the function.
    public void SetGameRoundTeamPointsView(String teamName, String listContent){
        if(teamName == "Team 1"){
            ((TextView)findViewById(R.id.Team1Points)).setText(listContent);
        }
        if(teamName == "Team 2"){
            ((TextView)findViewById(R.id.Team2Points)).setText(listContent);
        }
    }

    //Sets the TextView for for the teams remaining boules (balls) to the String that is passed into the function.
    public void  SetTeamBoulesLeftView(String teamName, String listContent){
        if(teamName == "Team 1"){
            ((TextView)findViewById(R.id.Team1BoulesLeft)).setText(listContent);
        }
        if(teamName == "Team 2"){
            ((TextView)findViewById(R.id.Team2BoulesLeft)).setText(listContent);
        }
    }

    //Sets the TextView for the current game state of the game round. (First Round, Current Player turn, End Round, ect.)
    public void SetPlayerTurnView(String textPlayerTurn){
        ((TextView)findViewById(R.id.TurnView)).setText(textPlayerTurn);
    }
}