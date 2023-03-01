package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.util.Log;

import com.example.myapplication.DataClasses.BallData;

import java.util.ArrayList;

public class PlayRoundActivity extends AppCompatActivity implements SensorEventListener{
    private  SensorManager mSensorManager;
    public   Sensor mAccelerometer;
    public ArrayList<float []> throwVectors = new ArrayList<float []>();


    BouleFieldView bouleView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_round);
         bouleView = (BouleFieldView) findViewById(R.id.bouleFieldView);
        mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
        mAccelerometer = mSensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);
    }

    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(this, mAccelerometer, SensorManager.SENSOR_DELAY_GAME);
    }

    protected void onPause() {
        super.onPause();
        mSensorManager.unregisterListener(this);
    }
    private long firstTimeStamp = 0;
    private long secondTimeStamp = 0;

    public boolean startListening = false;
    public boolean endListening = false;
    public void throwANewBall(View v){

        if(!startListening && !endListening){
             long firstTimeStamp = 0;
             long secondTimeStamp = 0;
            startListening = true;
            onResume();

        } else if(startListening && !endListening){

            endListening = true;
        }



    }
    /*float x = 0;
    float y = 0;
    float z = 0;
    int count = 0;*/
    float xprev=0;
    float yprev=0;
    float zprev=0;

    boolean xPositiv = false;
    boolean yPositiv = false;
    boolean checkForXpos = false;
    boolean timeStamoCheck = false;

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        float[] values = sensorEvent.values;

        //if(startListening && !endListening&& count <100) {}


        if(startListening && !endListening&& throwVectors.size() <200){
            if(!timeStamoCheck){
                firstTimeStamp = sensorEvent.timestamp;
                timeStamoCheck = true;
            }

            //Log.d("Sensor-App", "x:" + values[0] + " y:" + values[1] + " z:" + values[2]);
            if(values[0]>=0.8 || values[0]<=-0.8 || values[1]>=0.8 || values[2]>=0.8 || values[1]<=-0.8 || values[2]<=-0.8){
                float [] throwVec = new float[3];
                throwVec[0] = values[0] -xprev;
                throwVec[1] = values[1] -yprev;
                throwVec[2] = values[2] -zprev;
                //Log.d("Sensor-App", "x:" + throwVec[0] + " y:" + throwVec[1] + " z:" + throwVec[2]);
                throwVectors.add(throwVec);
            }



        }else             if(values[0]>=0.8 || values[0]<=-0.8 || values[1]>=0.8 || values[2]>=0.8 || values[1]<=-0.8 || values[2]<=-0.8){
            float [] throwVec = new float[3];
            throwVec[0] = values[0] -xprev;
            throwVec[1] = values[1] -yprev;
            throwVec[2] = values[2] -zprev;
            //Log.d("Sensor-App", "x:" + throwVec[0] + " y:" + throwVec[1] + " z:" + throwVec[2]);
            throwVectors.add(throwVec);
        }

        if(startListening && endListening){
            secondTimeStamp = sensorEvent.timestamp;

            float xv = 0;
            float yv = 0;
            float zv = 0;
            checkForXpos = false;


            for( int i = 0; i < throwVectors.size(); i++){
                Log.d("Sensor-App", "Counter: x:" +  xv + " y:" +  yv + " z:" +  zv);

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



                //xv = (xv + throwVectors.get(i)[0]);
                //yv = (yv + throwVectors.get(i)[1]);


                String sX = String.format("%.2f", xv + throwVectors.get(i)[0]);
                String sY = String.format("%.2f", xv + throwVectors.get(i)[1]);
                String sZ = String.format("%.2f", xv + throwVectors.get(i)[2]);

                Log.d("Sensor-App", "x:" +  sX + " y:" +  sY + " z:" +  sZ);

            }

            float throwTime =  Math.round((secondTimeStamp - firstTimeStamp) / 100000000.0);

            String sX = String.format("%.2f", xv);
            String sY = String.format("%.2f", yv);
            String sZ = String.format("%.2f", zv);

            Log.d("Sensor-App", "Combined Vector is: " + "x:" +  sX + " y:" +  sY + " z:" +  sZ + ". Time in Millisec: " + throwTime);
            bouleView.ThrowButtonClicked(xv,yv, zv, throwTime);

            timeStamoCheck = false;
            startListening = false;
            endListening = false;
            throwVectors.clear();
            onPause();
        }



    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}